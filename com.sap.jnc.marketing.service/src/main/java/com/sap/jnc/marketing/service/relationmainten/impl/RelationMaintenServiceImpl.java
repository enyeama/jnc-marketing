package com.sap.jnc.marketing.service.relationmainten.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.persistence.model.Contract;
import com.sap.jnc.marketing.persistence.model.ContractItem;
import com.sap.jnc.marketing.persistence.model.Dealer;
import com.sap.jnc.marketing.persistence.model.DepartmentView;
import com.sap.jnc.marketing.persistence.model.PositionView;
import com.sap.jnc.marketing.persistence.model.Product;
import com.sap.jnc.marketing.persistence.model.ProductDmsCategory;
import com.sap.jnc.marketing.persistence.repository.ContractRepository;
import com.sap.jnc.marketing.persistence.repository.DealerRepository;
import com.sap.jnc.marketing.persistence.repository.DepartmentViewRepository;
import com.sap.jnc.marketing.persistence.repository.PositionViewRepository;
import com.sap.jnc.marketing.persistence.repository.ProductDmsCategoryRepository;
import com.sap.jnc.marketing.persistence.repository.ProductRepository;
import com.sap.jnc.marketing.persistence.repository.RelationMaintenRepository;
import com.sap.jnc.marketing.service.relationmainten.RelationMaintenService;

/**
 * @author Maggie Liu
 */
@Service
@Transactional
public class RelationMaintenServiceImpl implements RelationMaintenService {

	@Autowired
	RelationMaintenRepository relationMaintenRepository;

	@Autowired
	@Qualifier("departmentViewRepositoryImpl")
	DepartmentViewRepository depatmentRepository;

	@Autowired
	@Qualifier("dealerRepositoryImpl")
	DealerRepository dealerRepository;

	@Autowired
	@Qualifier("productDmsCategoryRepositoryImpl")
	ProductDmsCategoryRepository productDmsCategoryRepository;

	@Autowired
	@Qualifier("productRepositoryImpl")
	ProductRepository productRepository;

	@Autowired
	@Qualifier("positionViewRepositoryImpl")
	PositionViewRepository positionViewRepository;

	@Autowired
	@Qualifier("contractRepositoryImpl")
	ContractRepository contractRepository;

	@Override
	public List<Dealer> findAllDealerByManager(String id) {
		List<Contract> contracts = contractRepository.findContratsByManager(id);
		if (!CollectionUtils.isEmpty(contracts)) {
			List<Dealer> dealers = new ArrayList<Dealer>();
			for (Contract c : contracts) {
				if (c.getDealer() != null) {
					dealers.add(c.getDealer());
				}
			}
			return dealers;
		}
		return Collections.emptyList();
	}

	@Override
	public List<Product> findAllProductsByManager(String id) {
		List<Contract> contracts = contractRepository.findContratsByManager(id);
		if (!CollectionUtils.isEmpty(contracts)) {
			List<ContractItem> contractItems = new ArrayList<ContractItem>();
			List<ProductDmsCategory> productDmsCategories = new ArrayList<ProductDmsCategory>();
			List<Product> products = new ArrayList<Product>();
			for (Contract contract : contracts) {
				if (!CollectionUtils.isEmpty(contract.getItems())) {
					contractItems.addAll(contract.getItems());
				}
			}
			for (ContractItem contractItem : contractItems) {
				if (contractItem.getDmsCategory() != null) {
					productDmsCategories.add(contractItem.getDmsCategory());
				}
			}
			for (ProductDmsCategory productDmsCategory : productDmsCategories) {
				if (!CollectionUtils.isEmpty(productDmsCategory.getProducts())) {
					products.addAll(productDmsCategory.getProducts());
				}
			}
			return products;
		}
		return Collections.emptyList();
	}

	@Override
	public List<PositionView> findAllLeaders(String id) {
		PositionView positionView = relationMaintenRepository.findPositionViewById(id);
		if (positionView != null) {
			DepartmentView department = positionView.getDepartment();
			List<DepartmentView> subDepartments = depatmentRepository.findDepartmentBysuper(department.getExternalId());
			if (!subDepartments.isEmpty()) {
				List<PositionView> leaders = new ArrayList<PositionView>();
				for (DepartmentView subdepartmentView : subDepartments) {
					leaders.addAll(relationMaintenRepository.findLeaderByDepartment(subdepartmentView.getExternalId()));
				}
				return leaders;
			}
		}
		return Collections.emptyList();
	}

	@Override
	public boolean createRelation(Long dealerId, String leaderId, String productId) {
		Dealer dealer = dealerRepository.findDealerById(dealerId);
		Product product = productRepository.findProductByExternalId(productId);
		PositionView leader = relationMaintenRepository.findPositionViewById(leaderId);

		if (dealer != null && product != null && leader != null) {
			Map<String, Boolean> isexsist = new HashMap<>();
			isexsist = isRecordExsist(leader, product, dealer);
			if (isexsist.get("leaderproduct") && isexsist.get("leaderdealer")) {
				return false;
			}
			if (!isexsist.get("leaderproduct")) {
				product.getLeaders().add(leader);
				leader.getProducts().add(product);
			}
			if (!isexsist.get("leaderdealer")) {
				dealer.getLeaders().add(leader);
				leader.setDealer(dealer);
			}
			dealerRepository.saveAndFlush(dealer);
			productRepository.saveAndFlush(product);
			positionViewRepository.saveAndFlush(leader);
			return true;
		}
		return false;
	}

	@Override
	public Page<PositionView> findAllDLPs(PageRequest pageRequest) {
		return relationMaintenRepository.findAllDLPRecords(pageRequest);
	}

	@Override
	public boolean deleteRelation(Long dealerId, String leaderId, String productId) {
		Dealer dealer = dealerRepository.findDealerById(dealerId);
		Product product = productRepository.findProductByExternalId(productId);
		PositionView leader = relationMaintenRepository.findPositionViewById(leaderId);
		if (dealer != null && product != null && leader != null) {
			dealer.getLeaders().remove(leader);
			product.getLeaders().remove(leader);
			leader.setDealer(null);
			leader.getProducts().remove(product);
			dealerRepository.saveAndFlush(dealer);
			productRepository.saveAndFlush(product);
			positionViewRepository.saveAndFlush(leader);
			return true;
		}
		return false;
	}

	Map<String, Boolean> isRecordExsist(PositionView leader, Product product, Dealer dealer) {
		Map<String, Boolean> result = new HashMap<>();
		result.put("leaderproduct", false);
		result.put("leaderdealer", false);
		if(!CollectionUtils.isEmpty(leader.getProducts())){
			if(leader.getProducts().contains(product)){
				result.replace("leaderproduct", true);
			}
		}
		if(leader.getDealer()!=null){
			if(leader.getDealer().equals(dealer)){
				result.replace("leaderdealer", true);
			}
		}
		return result;
	}
}
