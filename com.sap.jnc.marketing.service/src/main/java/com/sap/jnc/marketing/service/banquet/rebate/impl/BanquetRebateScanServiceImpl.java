package com.sap.jnc.marketing.service.banquet.rebate.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.sap.jnc.marketing.dto.request.banquet.banquetrebate.BanquetRebateScanRequest;
import com.sap.jnc.marketing.dto.response.banquet.banquetrebate.BanquetRebateResponse;
import com.sap.jnc.marketing.dto.shared.banquet.banquetrebate.BanquetRebateConfigInfo;
import com.sap.jnc.marketing.dto.shared.banquet.banquetrebate.BanquetRebateSingleInfo;
import com.sap.jnc.marketing.persistence.enumeration.BanquetScanType;
import com.sap.jnc.marketing.persistence.enumeration.BanquetStatus;
import com.sap.jnc.marketing.persistence.enumeration.RebateTargetType;
import com.sap.jnc.marketing.persistence.model.Banquet;
import com.sap.jnc.marketing.persistence.model.BanquetItem;
import com.sap.jnc.marketing.persistence.model.BanquetRebate;
import com.sap.jnc.marketing.persistence.model.BanquetRebateConfig;
import com.sap.jnc.marketing.persistence.model.BasicProduct;
import com.sap.jnc.marketing.persistence.model.IndividualProduct;
import com.sap.jnc.marketing.persistence.model.Product;
import com.sap.jnc.marketing.persistence.repository.BanquetItemRepository;
import com.sap.jnc.marketing.persistence.repository.BanquetRebateConfigRepository;
import com.sap.jnc.marketing.persistence.repository.BanquetRebateRepository;
import com.sap.jnc.marketing.persistence.repository.BanquetRepository;
import com.sap.jnc.marketing.persistence.repository.IndividualProductRepository;
import com.sap.jnc.marketing.service.banquet.rebate.BanquetRebateScanService;

import me.chanjar.weixin.common.util.StringUtils;

/**
 * @author Joel.Cheng I310645
 *
 */
@Service
@Transactional
public class BanquetRebateScanServiceImpl implements BanquetRebateScanService {

	private static final String NOT_FOUND = "NOT_FOUND";

	private static final String MORE_THAN_ON_RECORD = "MORE_THAN_ON_RECORD";

	private static final String SUCCESS = "SUCCESS";

	private static final String ALREADY_PAID = "ALREADY_PAID";

	private static final String UNKNOWN_STATUS = "UNKNOWN_STATUS";

	private static final String BANQUET_STATUS_WRONG = "BANQUET_STATUS_WRONG";

	private static final String BANQUET_STAUTS_SHOULD_BE = "Banquet stauts should be ";

	private static final String CURRENT_STATUS = ", current status ";

	private static final String IS_WRONG = " is wrong";

	private static final String NO_ITEM_LIST = "NO_ITEM_LIST";

	private static final String BANQUET_ITEM_LIST_CANNOT_BE_EMPTY = "Banquet item list cannot be empty";

	private static final String BANQUET_ID_INCONSISTENT = "BANQUET_ID_INCONSISTENT";

	private static final String BANQUET_ID_IN_ITEM = "Banquet ID in item ";

	private static final String IS = " is ";

	private static final String WHILE_BANQUET_ID_INPUT_IS = ", while Banquet ID input is ";

	@Autowired
	BanquetItemRepository banquetItemRepository;

	@Autowired
	BanquetRepository banquetRepository;

	@Autowired
	BanquetRebateConfigRepository banquetRebateConfigRepository;

	@Autowired
	BanquetRebateRepository banquetRebateRepository;

	@Autowired
	IndividualProductRepository individualProductRepository;

	@Override
	@Transactional(readOnly = true)
	public BanquetRebateSingleInfo scan(BanquetRebateScanRequest request) {

		String scanResult = null;
		long banquetId = request.getBanquetId();
		String scanCode = request.getScanCode();

		Banquet banquet = this.banquetRepository.findOne(banquetId);

		List<BanquetItem> banquetItemList = findReportedBanquetItem(banquetId, scanCode, banquet);

		if (CollectionUtils.isEmpty(banquetItemList)) {
			scanResult = NOT_FOUND;// 1-not_found
		} else if (banquetItemList.size() > 1) {
			scanResult = MORE_THAN_ON_RECORD;// 2-more_than_one_record
		} else {
			Boolean paymentIndicator = banquetItemList.get(0).getIsPaid();
			if (!paymentIndicator) {
				scanResult = SUCCESS;// 0-success
			} else if (paymentIndicator) {
				scanResult = ALREADY_PAID;// 3-already_paid
			} else {
				scanResult = UNKNOWN_STATUS;// 4-unknown_status
			}
		}

		List<BanquetRebateConfigInfo> banquetRebateConfigInfoList = new ArrayList<BanquetRebateConfigInfo>();
		Long id = null;
		if (SUCCESS.equals(scanResult)) {
			// get config table
			BanquetItem banquetItem = banquetItemList.get(0);
			id = banquetItem.getId();
			BasicProduct basicProduct = null;
			if(banquetItem.getIndividualProduct()!=null){
				basicProduct = banquetItem.getIndividualProduct();}
			else{
				basicProduct = banquetItem.getOldIndividualProduct();
			}
			Product product = basicProduct.getProduct();
			String productId = product.getId();

			List<BanquetRebateConfig> banquetRebateConfigList = banquetRebateConfigRepository
					.queryByScanedProduct(banquet, productId);
			if (!CollectionUtils.isEmpty(banquetRebateConfigList)) {
				for (BanquetRebateConfig banquetRebateConfig : banquetRebateConfigList) {
					BanquetRebateConfigInfo banquetRebateConfigInfo = new BanquetRebateConfigInfo(banquetRebateConfig);
					banquetRebateConfigInfoList.add(banquetRebateConfigInfo);
				}
			}

		}
		BanquetRebateSingleInfo banquetRebateSingleInfo = new BanquetRebateSingleInfo();
		banquetRebateSingleInfo.setScanCode(scanCode);
		banquetRebateSingleInfo.setScanResult(scanResult);
		banquetRebateSingleInfo.setId(id);
		banquetRebateSingleInfo.setBanquetRebateConfigInfoList(banquetRebateConfigInfoList);
		return banquetRebateSingleInfo;
	}

	private List<BanquetItem> findReportedBanquetItem(long banquetId, String scanOrInputCode, Banquet banquet) {
		List<BanquetItem> entities = null;

		if (banquet.getScanType().equals(BanquetScanType.QRCODE)) {
			String capInnerCode = scanOrInputCode;
			entities = this.banquetItemRepository.findReportedByBanquetIdAndCapInnerCode(banquetId, capInnerCode);
		} else if (banquet.getScanType().equals(BanquetScanType.LOGISCODE)) {
			String boxId = scanOrInputCode;
			entities = this.banquetItemRepository.findReportedByBanquetIdAndBoxId(banquetId, boxId);
		}
		return entities;
	}

	@Override
	@Transactional(readOnly = true)
	public List<BanquetItem> getItemList(Long banquetId) {
		return this.banquetItemRepository.findReportedByBanquetId(banquetId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> getItemBoxIdList(Long banquetId) {
		List<BanquetItem> banquetItemList = this.banquetItemRepository.findReportedByBanquetId(banquetId);
		List<String> result = new ArrayList<String>();
		for (BanquetItem banquetItem : banquetItemList) {
			result.add(banquetItem.getBoxId());
		}
		return result;
	}

	@Override
	@Transactional
	public BanquetRebateResponse rebate(long banquetId, List<BanquetRebateSingleInfo> banquetRebateSingleInfoList) {
		BanquetRebateResponse banquetRebateResponse = new BanquetRebateResponse();
		Banquet banquet = this.banquetRepository.findOne(banquetId);

		// Validate input data, start
		rebateValidate(banquet, banquetRebateSingleInfoList, banquetRebateResponse);
		if (StringUtils.isNotBlank(banquetRebateResponse.getResultCode())) {
			return banquetRebateResponse;
		}
		// Validate input data, end

		Calendar rebateTime = Calendar.getInstance();
		for (BanquetRebateSingleInfo banquetRebateSingleInfo : banquetRebateSingleInfoList) {
			long id = banquetRebateSingleInfo.getId();
			BanquetItem banquetItem = this.banquetItemRepository.findbyId(id);

			List<BanquetRebateConfigInfo> banquetRebateConfigInfoList = banquetRebateSingleInfo
					.getBanquetRebateConfigInfoList();
			if (!CollectionUtils.isEmpty(banquetRebateConfigInfoList)) {
				List<BanquetRebate> banquetRebateList = new ArrayList<BanquetRebate>();
				for (BanquetRebateConfigInfo banquetRebateConfigInfo : banquetRebateConfigInfoList) {
					BanquetRebate banquetRebate = new BanquetRebate();
					banquetRebate.setExternalId(banquetItem.getCapOuterCode());
					RebateTargetType rebateTargetType = banquetRebateConfigInfo.getRebateTargetType();
					banquetRebate.setRebateTargetType(rebateTargetType);
					switch (rebateTargetType) {
					case CONSUMER:
						banquetRebate.setRebateObjectId(banquet.getCustomerPhone());
						break;
					case AGENTSHOP:
						banquetRebate.setRebateObjectId(banquet.getTerminal().getCashPersonOpenId());
						break;
					case AGENTHOTEL:
						banquetRebate.setRebateObjectId(banquet.getHotelKeyPersonPhone());
						break;
					case MERCHANDISER:
						banquetRebate.setRebateObjectId(banquet.getHandler().getExternalId());
						break;
					default:
						break;
					}
					banquetRebate.setRebateTime(rebateTime);

					banquetRebate.setAmount(banquetRebateConfigInfo.getAmount());

					banquetRebate.setBanquet(banquet);

					banquetRebate.setIndividualProduct(banquetItem.getIndividualProduct());
					banquetRebateList.add(banquetRebate);
				}

				// 1.插入T_BANQUET_REBATE纪录
				this.banquetRebateRepository.save(banquetRebateList);
				// 2.更新T_BANQUET_ITEM.IS_PAID状态
				banquetItem.setIsPaid(true);
				this.banquetItemRepository.save(banquetItem);
			}
		}
		// 3.解锁T_INDIVIDUAL_PRODUCT里本次报备中未被兑付的记录，同理T_OLD_INDIVIDUAL_PRODUCT
		List<BanquetItem> allList = this.banquetItemRepository.findReportedByBanquetId(banquetId);
		if (!CollectionUtils.isEmpty(allList)) {
			for (BanquetItem item : allList) {
				if (!item.getIsPaid()) {
					item.setIndividualProduct(null);
					item.setOldIndividualProduct(null);
					this.banquetItemRepository.save(item);
				}
			}
		}

		// 4.T_BANQUET状态更新
		banquet.setStatus(BanquetStatus.PAID);
		banquet.setCashTime(rebateTime);
		this.banquetRepository.save(banquet);

		// 5. 微信红包接口预留，需要update回

		banquetRebateResponse.setResultCode(SUCCESS);
		banquetRebateResponse.setReturnMessage("");

		return banquetRebateResponse;
	}

	/**
	 * @param banquet
	 * @param banquetRebateSingleInfoList
	 * @param banquetRebateResponse
	 * @return
	 */
	private void rebateValidate(Banquet banquet, List<BanquetRebateSingleInfo> banquetRebateSingleInfoList,
			BanquetRebateResponse banquetRebateResponse) {
		// check banquet status
		if (!BanquetStatus.APPROVED.equals(banquet.getStatus())) {
			banquetRebateResponse.setResultCode(BANQUET_STATUS_WRONG);
			banquetRebateResponse.setReturnMessage(BANQUET_STAUTS_SHOULD_BE + BanquetStatus.APPLIED
					+ CURRENT_STATUS + banquet.getStatus() + IS_WRONG);
			return;
		}
		// check include list content
		if (CollectionUtils.isEmpty(banquetRebateSingleInfoList)) {
			banquetRebateResponse.setResultCode(NO_ITEM_LIST);
			banquetRebateResponse.setReturnMessage(BANQUET_ITEM_LIST_CANNOT_BE_EMPTY);
			return;
		}
		// check items are in the same input banquet
		for (BanquetRebateSingleInfo banquetRebateSingleInfo : banquetRebateSingleInfoList) {
			long id = banquetRebateSingleInfo.getId();
			BanquetItem banquetItem = this.banquetItemRepository.findbyId(id);
			Long itemBanquetId = banquetItem.getBanquet().getId();
			if (!banquet.getId().equals(itemBanquetId)) {
				banquetRebateResponse.setResultCode(BANQUET_ID_INCONSISTENT);
				banquetRebateResponse.setReturnMessage(BANQUET_ID_IN_ITEM + banquetRebateSingleInfo.getScanCode()
						+ IS + itemBanquetId + WHILE_BANQUET_ID_INPUT_IS + banquet.getId());
				return;

			}
		}
	}

}
