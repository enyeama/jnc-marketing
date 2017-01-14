/**
 *
 */
package com.sap.jnc.marketing.service.citymanager.config.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.dto.request.citymanager.config.ProductCategoryRelationRequest;
import com.sap.jnc.marketing.dto.request.citymanager.config.RelationSettingRequest;
import com.sap.jnc.marketing.dto.request.citymanager.config.SalesmanTerminalRelationRequest;
import com.sap.jnc.marketing.dto.response.ProductCategoryRelationResponse;
import com.sap.jnc.marketing.dto.response.RelationSettingResponse;
import com.sap.jnc.marketing.dto.response.SalesmanTerminalRelationResponse;
import com.sap.jnc.marketing.persistence.model.Dealer;
import com.sap.jnc.marketing.persistence.model.PositionView;
import com.sap.jnc.marketing.persistence.model.ProductErpCategory;
import com.sap.jnc.marketing.persistence.model.ProductSalesCategory;
import com.sap.jnc.marketing.persistence.model.Terminal;
import com.sap.jnc.marketing.persistence.repository.DealerRepository;
import com.sap.jnc.marketing.persistence.repository.PositionViewRepository;
import com.sap.jnc.marketing.persistence.repository.ProductErpCategoryRepository;
import com.sap.jnc.marketing.persistence.repository.ProductSalesCategoryRepository;
import com.sap.jnc.marketing.persistence.repository.TerminalRepository;
import com.sap.jnc.marketing.service.citymanager.config.RelationSettingService;

/**
 * @author Quansheng Liu I075496
 */
@Service
@Transactional
public class RelationSettingServiceImpl implements RelationSettingService {

	@Autowired
	private PositionViewRepository positionViewRepository;

	@Autowired
	private ProductSalesCategoryRepository productSalesCategoryRepository;

	@Autowired
	private DealerRepository dealerRepository;

	@Autowired
	private TerminalRepository terminalRepository;

	@Autowired
	private ProductErpCategoryRepository productErpCategoryRepository;

	@Override
	@Transactional
	public void createRelation(RelationSettingRequest relationRequest) {
		final PositionView positionView = this.positionViewRepository.findOne(relationRequest.getPositionId() + "");
		final Dealer dealer = this.dealerRepository.findOne(relationRequest.getDealerId());
		final ProductSalesCategory productSalesCategory = this.productSalesCategoryRepository.findOne(relationRequest.getSalesCategoryId());
		if ((positionView == null) || (dealer == null) || (productSalesCategory == null)) {
			return;
		}
		if (dealer.getLeaders() == null) {
			dealer.setLeaders(new ArrayList<>());
		}
		if ((positionView.getDealer() == null) || (positionView.getDealer().getId() != relationRequest.getDealerId())) {
			dealer.getLeaders().add(positionView);
			positionView.setDealer(dealer);
		}
		if (productSalesCategory.getPositions() == null) {
			productSalesCategory.setPositions(new ArrayList<>());
		}
		if ((positionView.getProductSalesCategory() == null) || (positionView.getProductSalesCategory().getId() != relationRequest
			.getSalesCategoryId())) {
			productSalesCategory.getPositions().add(positionView);
			positionView.setProductSalesCategory(productSalesCategory);
		}
		this.positionViewRepository.save(positionView);
		this.positionViewRepository.flush();
	}

	@Override
	@Transactional
	public void updateRelation(RelationSettingRequest relationRequest) {
		final PositionView positionView = this.positionViewRepository.findOne(relationRequest.getPositionId() + "");
		final Dealer dealer = this.dealerRepository.findOne(relationRequest.getDealerId());
		final ProductSalesCategory productSalesCategory = this.productSalesCategoryRepository.findOne(relationRequest.getSalesCategoryId());
		if ((positionView == null) || (dealer == null) || (productSalesCategory == null)) {
			return;
		}
		if (dealer.getLeaders() == null) {
			dealer.setLeaders(new ArrayList<>());
		}
		if ((positionView.getDealer() == null) || (positionView.getDealer().getId() != relationRequest.getDealerId())) {
			dealer.getLeaders().add(positionView);
			positionView.setDealer(dealer);
		}
		if (productSalesCategory.getPositions() == null) {
			productSalesCategory.setPositions(new ArrayList<>());
		}
		if ((positionView.getProductSalesCategory() == null) || (positionView.getProductSalesCategory().getId() != relationRequest
			.getSalesCategoryId())) {
			productSalesCategory.getPositions().add(positionView);
			positionView.setProductSalesCategory(productSalesCategory);
		}
		this.positionViewRepository.save(positionView);
		this.positionViewRepository.flush();
	}

	@Override
	@Transactional
	public void deleteRelation(RelationSettingRequest relationRequest) {
		final PositionView positionView = this.positionViewRepository.findOne(relationRequest.getPositionId() + "");
		final Dealer dealer = this.dealerRepository.findOne(relationRequest.getDealerId());
		final ProductSalesCategory productSalesCategory = this.productSalesCategoryRepository.findOne(relationRequest.getSalesCategoryId());
		if ((positionView == null) || (dealer == null) || (productSalesCategory == null)) {
			return;
		}
		if (dealer.getLeaders() == null) {
			dealer.setLeaders(new ArrayList<>());
		}
		if ((positionView.getDealer() != null) && (positionView.getDealer().getId() == relationRequest.getDealerId())) {
			final List<PositionView> posList = dealer.getLeaders();
			for (int i = 0; i < posList.size(); ++i) {
				if (posList.get(i).getId() == positionView.getId()) {
					posList.remove(i);
					break;
				}
			}
			positionView.setDealer(null);
		}
		if (productSalesCategory.getPositions() == null) {
			productSalesCategory.setPositions(new ArrayList<>());
		}
		if ((positionView.getProductSalesCategory() != null) && (positionView.getProductSalesCategory().getId() == relationRequest
			.getSalesCategoryId())) {
			final List<PositionView> posList = productSalesCategory.getPositions();
			for (int i = 0; i < posList.size(); ++i) {
				if (posList.get(i).getId() == positionView.getId()) {
					posList.remove(i);
					break;
				}
			}
			positionView.setProductSalesCategory(null);
		}
		this.positionViewRepository.save(positionView);
		this.positionViewRepository.flush();
		this.dealerRepository.save(dealer);
		this.dealerRepository.flush();
		this.productSalesCategoryRepository.save(productSalesCategory);
		this.productSalesCategoryRepository.flush();
	}

	@Override
	@Transactional(readOnly = true)
	public List<RelationSettingResponse> findAllRelations() {
		return Collections.emptyList();
	}

	@Override
	@Transactional
	public void createSalesmanTerminalRelation(SalesmanTerminalRelationRequest relationRequest) {
		final PositionView positionView = this.positionViewRepository.findOne(relationRequest.getPositionId() + "");
		final Terminal terminal = this.terminalRepository.findOne(relationRequest.getTerminalId());
		if ((positionView == null) || (terminal == null)) {
			return;
		}
		if (positionView.getTerminals() == null) {
			positionView.setTerminals(new ArrayList<>());
		}
		if (terminal.getSalesmen() == null) {
			terminal.setSalesmen(new ArrayList<>());
			terminal.getSalesmen().add(positionView);
			positionView.getTerminals().add(terminal);
			this.terminalRepository.saveAndFlush(terminal);
			this.positionViewRepository.saveAndFlush(positionView);
		}
		else {
			final List<PositionView> salesmanList = terminal.getSalesmen();
			int i = 0;
			for (; i < salesmanList.size(); ++i) {
				if (salesmanList.get(i).getId() == positionView.getId()) {
					break;
				}
			}
			if (i == salesmanList.size()) {
				terminal.getSalesmen().add(positionView);
				positionView.getTerminals().add(terminal);
				this.terminalRepository.saveAndFlush(terminal);
				this.positionViewRepository.saveAndFlush(positionView);
			}
		}
	}

	@Override
	@Transactional
	public void updateSalesmanTerminalRelation(SalesmanTerminalRelationRequest relationRequest) {
		final PositionView positionView = this.positionViewRepository.findOne(relationRequest.getPositionId() + "");
		final Terminal terminal = this.terminalRepository.findOne(relationRequest.getTerminalId());
		if ((positionView == null) || (terminal == null)) {
			return;
		}
		if (positionView.getTerminals() == null) {
			positionView.setTerminals(new ArrayList<>());
		}
		if (terminal.getSalesmen() == null) {
			terminal.setSalesmen(new ArrayList<>());
			terminal.getSalesmen().add(positionView);
			positionView.getTerminals().add(terminal);
			this.terminalRepository.saveAndFlush(terminal);
			this.positionViewRepository.saveAndFlush(positionView);
		}
		else {
			final List<PositionView> salesmanList = terminal.getSalesmen();
			int i = 0;
			for (; i < salesmanList.size(); ++i) {
				if (salesmanList.get(i).getId() == positionView.getId()) {
					break;
				}
			}
			if (i == salesmanList.size()) {
				terminal.getSalesmen().add(positionView);
				positionView.getTerminals().add(terminal);
				this.terminalRepository.saveAndFlush(terminal);
				this.positionViewRepository.saveAndFlush(positionView);
			}
		}
	}

	@Override
	@Transactional
	public void deleteSalesmanTerminalRelation(SalesmanTerminalRelationRequest relationRequest) {
		final PositionView positionView = this.positionViewRepository.findOne(relationRequest.getPositionId() + "");
		final Terminal terminal = this.terminalRepository.findOne(relationRequest.getTerminalId());
		if ((positionView == null) || (terminal == null)) {
			return;
		}
		if (terminal.getSalesmen() != null) {
			final List<PositionView> salesmanList = terminal.getSalesmen();
			int i = 0;
			for (; i < salesmanList.size(); ++i) {
				if (salesmanList.get(i).getId() == positionView.getId()) {
					break;
				}
			}
			if (i != salesmanList.size()) {
				terminal.getSalesmen().remove(i);
				this.terminalRepository.saveAndFlush(terminal);
			}
			final List<Terminal> terminalList = positionView.getTerminals();
			int j = 0;
			for (; j < terminalList.size(); ++j) {
				if (terminalList.get(j).getId() == relationRequest.getTerminalId()) {
					break;
				}
			}
			if (j != terminalList.size()) {
				positionView.getTerminals().remove(j);
				this.positionViewRepository.saveAndFlush(positionView);
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<SalesmanTerminalRelationResponse> findAllPositionTerminalRelations() {
		return Collections.emptyList();
	}

	@Override
	@Transactional
	public void createProductCategoryRelation(ProductCategoryRelationRequest relationRequest) {
		final ProductSalesCategory productSalesCategory = this.productSalesCategoryRepository.findOne(relationRequest.getProductSalesCategoryId());
		final ProductErpCategory productErpCategory = this.productErpCategoryRepository.findOne(relationRequest.getProductErpCategoryId());
		if ((productErpCategory == null) || (productSalesCategory == null)) {
			return;
		}
		return;
	}

	@Override
	@Transactional
	public void updateProductCategoryRelation(ProductCategoryRelationRequest relationRequest) {
		final ProductSalesCategory productSalesCategory = this.productSalesCategoryRepository.findOne(relationRequest.getProductSalesCategoryId());
		final ProductErpCategory productErpCategory = this.productErpCategoryRepository.findOne(relationRequest.getProductErpCategoryId());
		if ((productErpCategory == null) || (productSalesCategory == null)) {
			return;
		}
		return;
	}

	@Override
	@Transactional
	public void deleteProductCategoryRelation(ProductCategoryRelationRequest relationRequest) {
		final ProductSalesCategory productSalesCategory = this.productSalesCategoryRepository.findOne(relationRequest.getProductSalesCategoryId());
		final ProductErpCategory productErpCategory = this.productErpCategoryRepository.findOne(relationRequest.getProductErpCategoryId());
		if ((productErpCategory == null) || (productSalesCategory == null)) {
			return;
		}
		return;

	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductCategoryRelationResponse> findAllProductCategoryRelations() {
		return Collections.emptyList();
	}
}
