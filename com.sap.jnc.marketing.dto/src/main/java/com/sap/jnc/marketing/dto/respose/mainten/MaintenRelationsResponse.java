package com.sap.jnc.marketing.dto.respose.mainten;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.sap.jnc.marketing.persistence.model.Dealer;
import com.sap.jnc.marketing.persistence.model.PositionView;
import com.sap.jnc.marketing.persistence.model.Product;
/**
 * @author Maggie Liu
 */
public class MaintenRelationsResponse extends PageImpl<MaintenRelationsResponse.Body> implements Serializable {
	private static final long serialVersionUID = 6824889108470865103L;
	private static final List<Body> EMPTY_LIST = new ArrayList<>(0);

	public MaintenRelationsResponse(PageRequest pageRequest) {
		super(
			// Content
			EMPTY_LIST,
			// Page Request
			pageRequest,
			// Total
			0);
	}

	public static List<Body> getBodys(Page<PositionView> pages) {
		List<Body> bodies = new ArrayList<Body>();
		if(CollectionUtils.isEmpty(pages.getContent())){
			return Collections.emptyList();
		}
		for (PositionView positionView : pages.getContent()) {
			if(positionView.getDealer()==null){
				continue;
			}
			if(CollectionUtils.isEmpty(positionView.getProducts())){
				continue;
			}
			int size = positionView.getProducts().size();
			for (int i = 0; i < size; i++) {
				Body body=new Body(positionView.getDealer(), positionView, positionView.getProducts().get(i));
				
				bodies.add(body);
			}
			
		}
		return bodies;

	}

	public MaintenRelationsResponse(Page<PositionView> pages, PageRequest pageRequest) {

		super(
			// Content
			getBodys(pages).stream().collect(Collectors.toList()),
			// Page Request
			pageRequest,
			// Total
			pages.getTotalElements());
	}

	public static class Body implements Serializable {
		private String dealerId;
		private String dealerName="";
		private String leaderId="";
		private String leaderName="";
		private String productId="";
		private String productName="";

		public Body(Dealer dealer,PositionView leader,Product product) {
			this.dealerId = dealer.getExternalId()!=null?dealer.getExternalId():"";
			this.dealerName = dealer.getName()!=null?dealer.getName():"";
			this.leaderId = leader.getExternalId()!=null?leader.getExternalId():"";
			if(leader.getEmployee()!=null){
				this.leaderName = leader.getEmployee().getName()!=null?leader.getEmployee().getName():"";
			}
			this.productId = product.getId()!=null?product.getId():"";
			this.productName = product.getName()!=null?product.getName():"";
		}

		public String getDealerId() {
			return dealerId;
		}

		public void setDealerId(String dealerId) {
			this.dealerId = dealerId;
		}

		public String getDealerName() {
			return dealerName;
		}

		public void setDealerName(String dealerName) {
			this.dealerName = dealerName;
		}

		public String getLeaderId() {
			return leaderId;
		}

		public void setLeaderId(String leaderId) {
			this.leaderId = leaderId;
		}

		public String getLeaderName() {
			return leaderName;
		}

		public void setLeaderName(String leaderName) {
			this.leaderName = leaderName;
		}

		public String getProductId() {
			return productId;
		}

		public void setProductId(String productId) {
			this.productId = productId;
		}

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

	}

}
