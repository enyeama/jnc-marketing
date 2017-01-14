package com.sap.jnc.marketing.service.wechat.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.dto.request.wechat.salesman.SalesOrderRequest;
import com.sap.jnc.marketing.dto.request.wechat.salesman.SalesTerminalsRequest;
import com.sap.jnc.marketing.persistence.enumeration.ProductDmsCategoryLevel;
import com.sap.jnc.marketing.persistence.enumeration.TerminalOrderCreaterType;
import com.sap.jnc.marketing.persistence.enumeration.TerminalOrderStatus;
import com.sap.jnc.marketing.persistence.enumeration.TerminalOrderType;
import com.sap.jnc.marketing.persistence.model.Contract;
import com.sap.jnc.marketing.persistence.model.Dealer;
import com.sap.jnc.marketing.persistence.model.EmployeeView;
import com.sap.jnc.marketing.persistence.model.Position;
import com.sap.jnc.marketing.persistence.model.PositionView;
import com.sap.jnc.marketing.persistence.model.Product;
import com.sap.jnc.marketing.persistence.model.ProductDmsCategory;
import com.sap.jnc.marketing.persistence.model.Terminal;
import com.sap.jnc.marketing.persistence.model.TerminalOrder;
import com.sap.jnc.marketing.persistence.repository.ContractRepository;
import com.sap.jnc.marketing.persistence.repository.EmployeeViewRepository;
import com.sap.jnc.marketing.persistence.repository.PositionViewRepository;
import com.sap.jnc.marketing.persistence.repository.ProductDmsCategoryRepository;
import com.sap.jnc.marketing.persistence.repository.ProductRepository;
import com.sap.jnc.marketing.persistence.repository.TerminalOrderRepository;
import com.sap.jnc.marketing.persistence.repository.TerminalRepository;
import com.sap.jnc.marketing.service.wechat.WechatSalesmanService;

/**
 * @author C5245167 Xiao Qi
 */
@Service
@Transactional
public class WechatSalesmanServiceImpl implements WechatSalesmanService {

	@Autowired
	private TerminalRepository terminalRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private TerminalOrderRepository terminalOrderRepository;

	@Autowired
	private ProductDmsCategoryRepository productDmsCategoryRepository;
	
	@Autowired
	private PositionViewRepository positionViewRepository;
	
	@Autowired
	private ContractRepository contractRepository;
	
	@Autowired
	private EmployeeViewRepository employeeViewRepository;

	@Override
	@Transactional
	public String createOrder(SalesOrderRequest request) {

		// get terminal entity
		final Terminal terminalEntity = this.terminalRepository.findOne(Long.valueOf(request.getTerminalID()));
		// get product entity
		final Product productEntity = this.productRepository.findOne(request.getMaterialID());
		// get salesmane entity
		final EmployeeView employeeViewEntity = this.employeeViewRepository.findOneByExternalId(request.getSalesId());
		// get leader position
		final PositionView leaderPosition = this.positionViewRepository.findLeadersBySalesId(request.getSalesId());

		Dealer dealer = null;
		if (leaderPosition == null) {
			Long channelId = terminalEntity.getChannel() == null ? null : terminalEntity.getChannel().getId();
			Long regionId = terminalEntity.getRegion() == null ? null : terminalEntity.getRegion().getId();
			Long productId = Long.valueOf(productEntity.getId());
			// get responsible dealer
			Collection<Contract> contracts = contractRepository.findContractByTerminalOrderInfo(channelId, productId, regionId);
			if (!CollectionUtils.isEmpty(contracts)) {
				List<Contract> contractArr = new ArrayList<Contract>(contracts);
				dealer = contractArr.get(0).getDealer();
			}
		}

		// assign terminal header entity
		final TerminalOrder orderEntity = new TerminalOrder();
		this.assignTMOrderEntity(orderEntity, terminalEntity, productEntity, request, leaderPosition, dealer, employeeViewEntity);
		final TerminalOrder rstEntity = this.terminalOrderRepository.saveAndFlush(orderEntity);

		return rstEntity.getId().toString();
	}

	@Override
	@Transactional(readOnly = true)
	public List<TerminalOrder> getOrders(String terminalId, String salesmanId, TerminalOrderStatus status) {
		// get salesman position
		Position salesPosition = employeeViewRepository.findOneByExternalId(salesmanId).getPosition().getPosition();
		List<TerminalOrderType> orderType = new ArrayList<TerminalOrderType>();
		orderType.add(TerminalOrderType.TERMINALORDER);
		orderType.add(TerminalOrderType.VTERMINALORDER);
		if (status == TerminalOrderStatus.CANCEL) {
			return terminalOrderRepository.findOrders(terminalId, salesPosition.getExternalId(), orderType);
		}
		else {
			return terminalOrderRepository.findOrdersByStatus(terminalId, salesPosition.getExternalId(), status, orderType);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Terminal> getTerminals(SalesTerminalsRequest request) {
		// get salesman position
		Position salesPosition = employeeViewRepository.findOneByExternalId(request.getCreaterId()).getPosition().getPosition();
		return this.terminalRepository.findBySalesman(salesPosition.getExternalId(), request.getTitleKey());
	}

	@Override
	@Transactional(readOnly = true)
	public TerminalOrder viewOrder(String orderId) {
		return this.terminalOrderRepository.findOneWithCategory(Long.valueOf(orderId));
	}

	@Override
	@Transactional
	public TerminalOrder cancelOrder(String orderId) {
		final TerminalOrder terminalOrderEntity = this.terminalOrderRepository.findOne(Long.valueOf(orderId));
		// set status to cancel
		terminalOrderEntity.setStatus(TerminalOrderStatus.CANCEL);
		return this.terminalOrderRepository.saveAndFlush(terminalOrderEntity);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductDmsCategory> getCategory(String salesCategoryId) {
		// get THIRD_LEVEL category
		return this.productDmsCategoryRepository.findBySalesCategory(salesCategoryId, ProductDmsCategoryLevel.valueOf(2));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Product> getProductsByCategoryId(String dmsCategoryId, String channelId) {
		return this.productRepository.findForSalesAddOrder(dmsCategoryId, Long.valueOf(channelId));
	}

	private void assignTMOrderEntity(
		final TerminalOrder entity, 
		Terminal terminal, 
		Product product, 
		SalesOrderRequest request, 
		PositionView position,
		Dealer dealer,
		EmployeeView salesView) {

		// 订单编号
		entity.setType(TerminalOrderType.TERMINALORDER);
		entity.setCreaterId(request.getSalesId());
		entity.setCreaterEmployee(salesView.getEmployee());
		entity.setCreaterPosition(salesView.getPositions().get(0).getPosition());
		entity.setCreaterType(TerminalOrderCreaterType.SALESMAN);
		entity.setTerminal(terminal);
		entity.setStatus(TerminalOrderStatus.WAITFORDELIVERY);
		entity.setProduct(product);
		entity.setQuantity(Integer.valueOf(request.getQuantity()));

		if (position != null) {
			entity.setResponsibleLeader(position.getEmployee().getEmployee());
			entity.setResponsibleLeaderPosition(position.getPosition());
		} else {
			entity.setDealer(dealer);
		}
	}
}
