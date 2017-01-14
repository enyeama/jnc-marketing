package com.sap.jnc.marketing.api.wechat.web.controller.maintenance;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sap.jnc.marketing.api.wechat.web.controller.GeneralController;
import com.sap.jnc.marketing.dto.request.mainten.LeaderTerminalRequest;
import com.sap.jnc.marketing.dto.respose.mainten.MaintenSalesmanResponse;
import com.sap.jnc.marketing.dto.respose.mainten.MaintenTernimalResponse;
import com.sap.jnc.marketing.persistence.model.Terminal;
import com.sap.jnc.marketing.service.relationmainten.LeaderTerminalService;
/*
 * @author Maggie Liu
 * */
@RestController
public class LeaderTerminalController extends GeneralController{
	@Autowired
	LeaderTerminalService leaderTerminalService;
	
	@RequestMapping(value = { "/leaderterminalmaintenance/salesmen" }, method = { RequestMethod.GET })
	public @ResponseBody Collection<MaintenSalesmanResponse> findSalesmans(@RequestParam String leaderEmployeeId){
		return leaderTerminalService.findAllSalesmansByLeaderEmployeeId(leaderEmployeeId);
		
	}
	
	@RequestMapping(value = { "/leaderterminalmaintenance/terminals" }, method = { RequestMethod.GET })
	public @ResponseBody Collection<MaintenTernimalResponse> findTernimals(@RequestParam String leaderEmployeeId){
		
		Collection<Terminal> terminals=leaderTerminalService.findAllTerminalsByLeaderEmployeeId(leaderEmployeeId);
		if(!CollectionUtils.isEmpty(terminals)){
			return terminals.stream().distinct().map(terminal->new MaintenTernimalResponse(terminal)).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}
	
	@RequestMapping(value={"/leaderterminalmaintenance/relationships"},method={RequestMethod.POST})
	public boolean createRelations(@RequestBody LeaderTerminalRequest leaderTerminalRequest){
		return leaderTerminalService.createSalesmanTerminalRelation(leaderTerminalRequest.getSalesmanExternalId(), leaderTerminalRequest.getTerminalId());
	}
	
	@RequestMapping(value={"/leaderterminalmaintenance/terminal/salesmen"},method={RequestMethod.GET})
	public @ResponseBody Collection<MaintenSalesmanResponse> terminalFindSalesmen(@RequestParam Long terminalId){
		return leaderTerminalService.findAllSalesmenByTerminalId(terminalId);
	}
	
	@RequestMapping(value={"/leaderterminalmaintenance/relationships/config"},method={RequestMethod.POST})
	public boolean configRelations(@RequestBody LeaderTerminalRequest leaderTerminalRequest){
		return leaderTerminalService.deleteSalesmanTerminalRelation(leaderTerminalRequest.getSalesmanExternalId(), leaderTerminalRequest.getTerminalId());
	}
	
	@RequestMapping(value = { "/leaderterminalmaintenance/terminal" }, method = { RequestMethod.GET })
	public @ResponseBody MaintenTernimalResponse findTernimal(@RequestParam Long terminalId){
		return new MaintenTernimalResponse(leaderTerminalService.findOneTerminalByTerminalId(terminalId));  
	}
}
