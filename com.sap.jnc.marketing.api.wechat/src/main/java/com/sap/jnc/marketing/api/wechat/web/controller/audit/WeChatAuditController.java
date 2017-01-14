package com.sap.jnc.marketing.api.wechat.web.controller.audit;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sap.jnc.marketing.api.wechat.web.controller.GeneralController;
import com.sap.jnc.marketing.dto.request.audit.AuditBriefRequest;
import com.sap.jnc.marketing.dto.request.audit.AuditDetailRequest;
import com.sap.jnc.marketing.dto.request.audit.AuditStatusChangeRequest;
import com.sap.jnc.marketing.dto.request.audit.PendingAuditRequest;
import com.sap.jnc.marketing.dto.request.audit.WeChatAuditDetailRequest;
import com.sap.jnc.marketing.dto.response.audit.BanquetAuditResponse;
import com.sap.jnc.marketing.dto.response.audit.HotelTerminalAuditDetailResponse;
import com.sap.jnc.marketing.dto.response.audit.ShopTerminalAuditDetailResponse;
import com.sap.jnc.marketing.dto.response.audit.TerminalAuditBriefResponse;
import com.sap.jnc.marketing.dto.response.audit.WeChatAuditItemsResponse;
import com.sap.jnc.marketing.dto.response.audit.WeChatAuditPictureResponse;
import com.sap.jnc.marketing.persistence.model.AuditBanquet;
import com.sap.jnc.marketing.persistence.model.AuditItem;
import com.sap.jnc.marketing.persistence.model.AuditPicture;
import com.sap.jnc.marketing.persistence.model.AuditTerminal;
import com.sap.jnc.marketing.service.audit.AuditService;
import com.sap.jnc.marketing.service.qingcloud.QingCloudService;

/**
 * @author I330182 Vodka Li
 * @author C5231393 Xu Xiaolei
 */
@RestController
public class WeChatAuditController extends GeneralController {

	private static Logger LOGGER = LoggerFactory.getLogger(WeChatAuditController.class);

	@Autowired
	private AuditService auditService;

	@Autowired
	private QingCloudService qingCloudService;

	public Transformer<AuditTerminal, TerminalAuditBriefResponse> TermianlAuditBriefTransformaer = new Transformer<AuditTerminal, TerminalAuditBriefResponse>() {
		public TerminalAuditBriefResponse transform(AuditTerminal auditterminal) {
			return new TerminalAuditBriefResponse(auditterminal);
		}
	};

	public Transformer<AuditBanquet, BanquetAuditResponse> BanquetAuitTransformer = new Transformer<AuditBanquet, BanquetAuditResponse>() {
		public BanquetAuditResponse transform(AuditBanquet auditBanquet) {
			return new BanquetAuditResponse(auditBanquet);
		}
	};

	public Transformer<AuditItem, WeChatAuditItemsResponse> AuditItemTransformer = new Transformer<AuditItem, WeChatAuditItemsResponse>() {
		public WeChatAuditItemsResponse transform(AuditItem auditItem) {
			return new WeChatAuditItemsResponse(auditItem);
		}
	};

	public Transformer<AuditPicture, WeChatAuditPictureResponse> AuditPicutreTransformer = new Transformer<AuditPicture, WeChatAuditPictureResponse>() {
		public WeChatAuditPictureResponse transform(AuditPicture auditPicture) {
			return new WeChatAuditPictureResponse(auditPicture);
		}

	};

	@RequestMapping(value = "/audits/count", method = RequestMethod.POST)
	public Long getPendingAuitsNumber(@RequestBody PendingAuditRequest request) {
		LOGGER.info("Get the number of pending audits");
		return auditService.getPendingAuditCounts(request);
	}

	@RequestMapping(value = "/terminal/audits", method = RequestMethod.POST)
	public Collection<TerminalAuditBriefResponse> findAllTerminalAudit(@RequestBody AuditBriefRequest request) {
		LOGGER.info("Find Terminal Audits");
		return CollectionUtils.collect(auditService.findAllByTypeAndStatus(request), TermianlAuditBriefTransformaer);
	}

	@RequestMapping(value = "/terminal/audits/shop/{id}", method = RequestMethod.POST)
	public ShopTerminalAuditDetailResponse findOneShopTerminalById(@RequestBody WeChatAuditDetailRequest request) {
		LOGGER.info("Find Terminal Audits By Id");
		return new ShopTerminalAuditDetailResponse(auditService.findOneTerminalAuditById(request));
	}

	@RequestMapping(value = "/terminal/audits/hotel/{id}", method = RequestMethod.POST)
	public HotelTerminalAuditDetailResponse findOneHotelTerminalById(@RequestBody WeChatAuditDetailRequest request) {
		LOGGER.info("Find Terminal Audits By Id");
		return new HotelTerminalAuditDetailResponse(auditService.findOneTerminalAuditById(request));
	}

	@RequestMapping(value = "/audits/auditstatus", method = RequestMethod.POST)
	public void changeStatus(@RequestBody AuditStatusChangeRequest request) {
		if (request.getId() == null) {
			return;
		}
		auditService.changeStatus(request);
	}

	@RequestMapping(value = "/terminal/auditdetails/{id}", method = RequestMethod.POST)
	public void submmitlAuditDetails(@RequestBody AuditDetailRequest request) {
		LOGGER.info("Save terminal audits details");
		if (request == null) {
			return;
		}
		auditService.submitlAuditDetails(request);
	}

	@RequestMapping(value = "/banquet/audits", method = RequestMethod.POST)
	public Collection<BanquetAuditResponse> findAllBanquetAudit(@RequestBody AuditBriefRequest request) {
		LOGGER.info("Find Banquet Audits");
		return CollectionUtils.collect(auditService.findAllBanquetAuditByAuidtorIdAndTypeAndStatus(request), BanquetAuitTransformer);
	}

	@RequestMapping(value = "/banquet/audits/{id}", method = RequestMethod.POST)
	public BanquetAuditResponse findOneBanquetAuditById(@RequestBody WeChatAuditDetailRequest request) {
		return new BanquetAuditResponse(auditService.findOneBanquetAuditById(request));
	}

	@SuppressWarnings("resource")
	@RequestMapping(value = "/audits/files", method = RequestMethod.POST)
	public String uploadFiles(@RequestParam("file") MultipartFile file) throws IOException {
		String filePath = null;
		if (!file.isEmpty()) {
			try {
				filePath = "/jnctest01/" + UUID.randomUUID() + file.getOriginalFilename();
				File f = new File(file.getOriginalFilename());
				file.transferTo(f);
				ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
				BufferedInputStream in = null;
				in = new BufferedInputStream(new FileInputStream(f));
				int buf_size = 1024;
				byte[] buffer = new byte[buf_size];
				int len = 0;
				while (-1 != (len = in.read(buffer, 0, buf_size))) {
					bos.write(buffer, 0, len);
				}
				boolean success = qingCloudService.uploadFile(filePath, "image/jpeg", bos.toByteArray());
				if (success) {
					return filePath;
				}
				else {
					return null;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return filePath;
	}

	@RequestMapping(value = "/audits/files/{url}", method = RequestMethod.POST)
	public boolean deleteFiles(@PathVariable String url) throws IOException {
		boolean success = false;
		try {
			success = qingCloudService.deleteFile(url);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;
	}

	@RequestMapping(value = "/audit/item", method = RequestMethod.POST)
	public Collection<WeChatAuditItemsResponse> findAllAuditItems(@RequestBody AuditBriefRequest request) {
		return CollectionUtils.collect(auditService.findOneAuditItemListById(request), AuditItemTransformer);
	}

	@RequestMapping(value = "/audit/picture/{id}", method = RequestMethod.GET)
	public Collection<WeChatAuditPictureResponse> findAllAuditPicture(@PathVariable Long id) {
		return CollectionUtils.collect(auditService.findOneAuditPictureListById(id), AuditPicutreTransformer);
	}
}
