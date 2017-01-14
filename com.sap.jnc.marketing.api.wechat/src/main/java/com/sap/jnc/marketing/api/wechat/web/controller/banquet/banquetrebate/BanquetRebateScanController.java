package com.sap.jnc.marketing.api.wechat.web.controller.banquet.banquetrebate;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sap.jnc.marketing.api.wechat.web.controller.GeneralController;
import com.sap.jnc.marketing.dto.request.banquet.banquetrebate.BanquetRebateRequest;
import com.sap.jnc.marketing.dto.request.banquet.banquetrebate.BanquetRebateScanRequest;
import com.sap.jnc.marketing.dto.response.banquet.banquetrebate.BanquetRebateItemResponse;
import com.sap.jnc.marketing.dto.response.banquet.banquetrebate.BanquetRebateResponse;
import com.sap.jnc.marketing.dto.response.banquet.banquetrebate.BanquetRebateScanResponse;
import com.sap.jnc.marketing.dto.shared.banquet.banquetrebate.BanquetRebateSingleInfo;
import com.sap.jnc.marketing.service.banquet.rebate.BanquetRebateScanService;

/**
 * @author Joel.Cheng I310645
 *
 */
@RestController
public class BanquetRebateScanController extends GeneralController {
	
	@Autowired
	private BanquetRebateScanService banquetRebateScanService;
	
	@RequestMapping(path = "/banquet/rebate/scan/items", method = { RequestMethod.POST})
	public BanquetRebateScanResponse scan(@RequestBody BanquetRebateScanRequest request) {
		BanquetRebateSingleInfo result = this.banquetRebateScanService.scan(request);
		return new BanquetRebateScanResponse(result);
	}
	
	@Deprecated
	@RequestMapping(path = "/banquet/rebate/list/items/{banquetId}", method = { RequestMethod.GET})
	public List<BanquetRebateItemResponse> getItemList(@PathVariable Long banquetId) {
		return this.banquetRebateScanService.getItemList(banquetId).stream()
				.map(banquetItem -> new BanquetRebateItemResponse(banquetItem)).collect(Collectors.toList());
	}
	
	@RequestMapping(path = "/banquet/rebate/list/boxids/{banquetId}", method = { RequestMethod.GET})
	public List<String> getItemBoxIdList(@PathVariable Long banquetId) {
		return this.banquetRebateScanService.getItemBoxIdList(banquetId);
	}
	
	@RequestMapping(path = "/banquet/rebate/commit/items", method = { RequestMethod.POST})
	public BanquetRebateResponse rebate(@RequestBody BanquetRebateRequest request) {
		List<BanquetRebateSingleInfo> banquetRebateSingleInfoList = request.getBanquetRebateSingleInfoList();
		long banquetId = request.getBanquetId();
		return this.banquetRebateScanService.rebate(banquetId, banquetRebateSingleInfoList);
	}
}