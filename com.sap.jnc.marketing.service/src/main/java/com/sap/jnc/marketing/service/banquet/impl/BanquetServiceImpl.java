package com.sap.jnc.marketing.service.banquet.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.sap.jnc.marketing.dto.request.banquet.BanquetApproveRequest;
import com.sap.jnc.marketing.dto.request.banquet.BanquetMergeRequest;
import com.sap.jnc.marketing.dto.response.banquet.BanquetChannelRelProduct;
import com.sap.jnc.marketing.dto.response.banquet.BanquetEnumInfoResponse;
import com.sap.jnc.marketing.dto.response.banquet.BanquetRelatedInfoResponse;
import com.sap.jnc.marketing.dto.response.banquet.BanquetTypeInfoResponse;
import com.sap.jnc.marketing.dto.response.banquet.ChannelInfoResponse;
import com.sap.jnc.marketing.dto.response.banquet.DealerInfoResponse;
import com.sap.jnc.marketing.dto.response.banquet.EmployeeInfoResponse;
import com.sap.jnc.marketing.dto.response.banquet.ProductInfoResponse;
import com.sap.jnc.marketing.dto.response.banquet.TerminalInfoResponse;
import com.sap.jnc.marketing.persistence.criteria.banquet.BanquetSearchKeywordNode;
import com.sap.jnc.marketing.persistence.enumeration.BanquetApplicationType;
import com.sap.jnc.marketing.persistence.enumeration.BanquetScanType;
import com.sap.jnc.marketing.persistence.enumeration.BanquetStatus;
import com.sap.jnc.marketing.persistence.enumeration.BanquetTimePoint;
import com.sap.jnc.marketing.persistence.enumeration.TerminalType;
import com.sap.jnc.marketing.persistence.model.Banquet;
import com.sap.jnc.marketing.persistence.model.BanquetItem;
import com.sap.jnc.marketing.persistence.model.BanquetRebateConfig;
import com.sap.jnc.marketing.persistence.model.BanquetType;
import com.sap.jnc.marketing.persistence.model.Channel;
import com.sap.jnc.marketing.persistence.model.Dealer;
import com.sap.jnc.marketing.persistence.model.Department;
import com.sap.jnc.marketing.persistence.model.DepartmentView;
import com.sap.jnc.marketing.persistence.model.Employee;
import com.sap.jnc.marketing.persistence.model.EmployeeView;
import com.sap.jnc.marketing.persistence.model.PlanQuantity;
import com.sap.jnc.marketing.persistence.model.Position;
import com.sap.jnc.marketing.persistence.model.PositionView;
import com.sap.jnc.marketing.persistence.model.Product;
import com.sap.jnc.marketing.persistence.model.Terminal;
import com.sap.jnc.marketing.persistence.repository.BanquetItemRepository;
import com.sap.jnc.marketing.persistence.repository.BanquetRebateConfigRepository;
import com.sap.jnc.marketing.persistence.repository.BanquetRepository;
import com.sap.jnc.marketing.persistence.repository.BanquetTypeRepository;
import com.sap.jnc.marketing.persistence.repository.ChannelRepository;
import com.sap.jnc.marketing.persistence.repository.DealerRepository;
import com.sap.jnc.marketing.persistence.repository.DepartmentViewRepository;
import com.sap.jnc.marketing.persistence.repository.EmployeeRepository;
import com.sap.jnc.marketing.persistence.repository.EmployeeViewRepository;
import com.sap.jnc.marketing.persistence.repository.ProductRepository;
import com.sap.jnc.marketing.persistence.repository.TerminalRepository;
import com.sap.jnc.marketing.service.banquet.BanquetService;
import com.sap.jnc.marketing.service.exception.FieldErrorBodyAudit;
import com.sap.jnc.marketing.service.exception.ResponseBodyDBAuditException;
import com.sap.jnc.marketing.service.exception.validation.SpecifiedEntityNotFoundException;

/**
 * @author I332242 Zhu Qiang
 */
@Service
@Transactional
public class BanquetServiceImpl implements BanquetService {
	
	private static final String OFFICE_DIRECTOR_JOB = "办事处主任";
	
	private static final String APPROVE_COMMENT = "批量合格";
	
	private static final String NOT_APPROVE_COMMENT = "批量不合格";

    @Autowired
    private BanquetRepository banquetRepository;
    
    @Autowired
    private BanquetTypeRepository banquetTypeRepository;
    
    @Autowired
    private EmployeeViewRepository employeeViewRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private DepartmentViewRepository departmentViewRepository;
    
    @Autowired
    private BanquetRebateConfigRepository banquetRebateConfigRepository;
    
    @Autowired
    private TerminalRepository terminalRepository;
    
    @Autowired
    private DealerRepository dealerRepository;
    
    @Autowired
    private ChannelRepository channelRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private BanquetItemRepository banquetItemRepository;
    
    @Override
    @Transactional(readOnly = true)
    public Banquet findOne(Long id) {
        final Banquet entity = this.banquetRepository.findOne(id);
        if (entity == null) {
			throw this.getExceptionEntity(new RuntimeException(), "banquetId", String.valueOf(id), "该宴会不存在");
        }
        return entity;
    }

    @Override
    @Transactional
    public Banquet create(BanquetMergeRequest request) {
        // Validation
        if (request == null) {
            return null;
        }
        final Banquet entity = new Banquet();
        entity.setStatus(BanquetStatus.APPLIED);
        this.assignBanquetScalarFields(request, entity);
        if (entity.getCreator() != null) {
        	entity.setHandler(entity.getCreator());
        }
        // Save and return
        return this.banquetRepository.saveAndFlush(entity);
    }

    @Override
    @Transactional
    public Banquet update(Long id, BanquetMergeRequest request) {
        // Validation
        if (id == null || (request == null)) {
            return null;
        }
        final Banquet entity = this.banquetRepository.findOne(id);
        if (entity == null) {
            throw new SpecifiedEntityNotFoundException(Banquet.class, id);
        }
        // Assignment
        this.assignBanquetScalarFields(request, entity);
        // Save and return
        return this.banquetRepository.saveAndFlush(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // Validation
        if (!this.banquetRepository.exists(id)) {
            throw new SpecifiedEntityNotFoundException(Banquet.class, id);
        }
        // Delete and return
        this.banquetRepository.delete(id);
    }

    @Transactional(readOnly = true)
    private void assignBanquetScalarFields(BanquetMergeRequest request, final Banquet entity) {
        if (null != request.getId()) {
        	entity.setId(request.getId());
        }
        // Banquet application type
        if (request.getApplicationType() != null) {
        	entity.setApplicationType(request.getApplicationType());
        }
        // Banquet terminal(网点
        if (request.getTerminal() != null) {
        	Terminal terminal = terminalRepository.findOne(request.getTerminal().getId());
        	if (terminal != null) {
        		entity.setTerminal(terminal);
        	} else {
        		throw this.getExceptionEntity(new RuntimeException(), "terminalId", String.valueOf(request.getTerminal().getId()), "对应终端不存在");
        	}
        }
        // Banquet dealer(经销商)
    	if (request.getDealer() != null) {
    		Dealer dealer = dealerRepository.findOne(request.getDealer().getId());
    		if (dealer != null) {
    			entity.setDealer(dealer);
    		} else {
    			throw this.getExceptionEntity(new RuntimeException(), "dealerId", String.valueOf(request.getDealer().getId()), "对应经销商不存在");
    		}
        }
    	// Banquet city manager
        if (request.getCityManager() != null) {
        	Employee emp = employeeRepository.findOne(request.getCityManager().getId());
        	EmployeeView empView = employeeViewRepository.findOne(emp.getExternalId());
        	Employee cityManager = new Employee();
        	Position cityManagerPosition = new Position();
        	Employee office = new Employee();
        	Position officePosition = new Position();
        	Department department = new Department();
        	if (null != empView) {
        		cityManager.setId(empView.getId());
        		cityManager.setName(empView.getName());
        		entity.setCityManager(cityManager);
        		PositionView citymanagerView = empView.getPositions().get(0);
        		
        		if (null != citymanagerView) {
        			cityManagerPosition.setId(citymanagerView.getId());
        			cityManagerPosition.setName(citymanagerView.getName());
        		}
        		entity.setCityManagerPosition(cityManagerPosition);
        		// 城市经理对应办事处
        		if (citymanagerView.getDepartment() != null && citymanagerView.getDepartment().getSuperior() != null) {
        			if (! CollectionUtils.isEmpty(citymanagerView.getDepartment().getSuperior().getPositions())) {
        				PositionView officePositionView = citymanagerView.getDepartment().getSuperior().getPositions().get(0);
        				if (null != officePositionView) {
        					if (! CollectionUtils.isEmpty(officePositionView.getEmployees())) {
        						EmployeeView officeView = officePositionView.getEmployees().get(0);
        						if (null != officeView) {
        							office.setId(officeView.getId());
        							office.setName(officeView.getName());
        							entity.setOfficeDirector(office);
        						}
        						DepartmentView departmentView = officePositionView.getDepartment();
        						if (departmentView != null) {
        							department.setId(departmentView.getId());
        							department.setName(departmentView.getName());
        							entity.setOffice(department);
        						}
        						officePosition.setId(officePositionView.getId());
        						officePosition.setName(officePositionView.getName());
        						entity.setOfficeDirectorPosition(officePosition);
        					}
        				} else {
        					throw this.getExceptionEntity(new RuntimeException(), "cityManagerId", String.valueOf(request.getCityManager().getId()), "城市经理对应办事处不存在");
        				}
        			}
        		} else {
        			throw this.getExceptionEntity(new RuntimeException(), "cityManagerId", String.valueOf(request.getCityManager().getId()), "城市经理对应部门不存在");
        		}
        	} else {
        		throw this.getExceptionEntity(new RuntimeException(), "cityManagerId", String.valueOf(request.getCityManager().getId()), "城市经理对应人员不存在");
        	}
        }
        // 消费者姓名
        if (StringUtils.isNotBlank(request.getCustomerName())) {
        	entity.setCustomerName(request.getCustomerName());
        }
        //消费者电话
        if (StringUtils.isNotBlank(request.getCustomerPhone())) {
        	entity.setCustomerPhone(request.getCustomerPhone());
        }
        // 订餐人
        if (StringUtils.isNotBlank(request.getReservationistName())) {
        	entity.setReservationistName(request.getReservationistName());
        }
        // 订餐人电话
        if (StringUtils.isNotBlank(request.getReservationistPhone())) {
        	entity.setReservationistPhone(request.getReservationistPhone());
        }
        // 宴会地址
        if (StringUtils.isNotBlank(request.getHostAddress())) {
        	entity.setHostAddress(request.getHostAddress());
        }
        // 场地电话
        if (StringUtils.isNotBlank(request.getHostPhone())) {
        	entity.setHostPhone(request.getHostPhone());
        }
        // 宴会日期
        if (request.getBanquetTime() != null) {
        	entity.setBanquetTime(request.getBanquetTime());
        }
        // 桌数
        if (request.getTableCount() != null) {
        	entity.setTableCount(request.getTableCount());
        }
        // 宴会类型
        if (request.getBanquetType() != null) {
        	entity.setType(request.getBanquetType());
        }
        // 宴会版本
        if (request.getChannel() != null) {
        	Channel channel = channelRepository.findOne(request.getChannel().getId());
        	if (channel != null){
        		entity.setMaterialChannel(channel);
        	} else {
        		throw this.getExceptionEntity(new RuntimeException(), "channelId", String.valueOf(request.getChannel().getId()), "对应渠道不存在");
        	}
        }
        // 商品名称
        if (request.getProduct() != null) {
        	Product product = productRepository.findOne(request.getProduct().getId());
        	if (product != null) {
        		entity.setProduct(product);
        	} else {
        		throw this.getExceptionEntity(new RuntimeException(), "productId", String.valueOf(request.getProduct().getId()), "对应商品不存在");
        	}
        }
        // 计划数量
        if (request.getPlanQuantity() != null) {
        	PlanQuantity planQuantity = new PlanQuantity();
        	planQuantity.setUnit(request.getPlanQuantity().getUnit());
        	planQuantity.setValue(request.getPlanQuantity().getValue());
        	entity.setPlanQuantity(planQuantity);
        }
        // 扫码类型
        if (request.getScanType() != null) {
        	entity.setScanType(request.getScanType());
        }
        // 宴会备注
        if (StringUtils.isNotBlank(request.getBanquetComment())) {
        	entity.setBanquetComment(request.getBanquetComment());
        }
        // 创建人
        if (request.getCreator() != null) {
        	Employee emp = employeeRepository.findOne(request.getCreator().getId());
        	if (emp != null) {
        		entity.setCreator(emp);
        	} else {
        		throw this.getExceptionEntity(new RuntimeException(), "employeeId", String.valueOf(request.getCreator().getId()), "业务员岗位对应人员不存在");
        	}
        }
        // 审批备注
        if (StringUtils.isNotBlank(request.getApproveComment())) {
        	entity.setApproveComment(request.getApproveComment());
        }
        // 具体使用时间(中午 上午 晚上)
        if (request.getTimePoint() != null) {
        	entity.setBanquetTimePoint(request.getTimePoint());
        }
    }

    @Override
    @Transactional(readOnly = true)
	public Page<Banquet> advanceSearch(BanquetSearchKeywordNode searchCriteria, PageRequest pageRequest) {
		return banquetRepository.advanceSearch(searchCriteria, pageRequest);
	}
	
	@Override
	@Transactional
	public List<Long> batchUpdate(BanquetApproveRequest banquetApproveRequest) {
		List<Banquet> banquets = new ArrayList<Banquet>();
		List<Long> banquetAuditIds = new ArrayList<Long>();   
		if (null != banquetApproveRequest) {
			List<Long> banquetIdList = banquetApproveRequest.getBanquetIds();
			BanquetStatus status = banquetApproveRequest.getStatus();
			for (Long banquetId : banquetIdList) {
				Banquet banquet = this.findOne(banquetId);
				if (null != banquet) {
					banquets.add(banquet);
				}
			}
			if (banquets.size() > 1) {
				for (Banquet banquet : banquets) {
					if (status.getIntValue() == BanquetStatus.NOT_APPROVED.getIntValue()) {
						banquet.setStatus(status);
						banquet.setApproveComment(NOT_APPROVE_COMMENT);
						// 审批不合格解锁报备
						// 解锁T_INDIVIDUAL_PRODUCT里本次报备中未被兑付的记录
						Banquet reportedBanquet = this.banquetRepository.saveAndFlush(banquet);
						if (reportedBanquet != null) {
							List<BanquetItem> allList = this.banquetItemRepository.findReportedByBanquetId(reportedBanquet.getId());
							if (!CollectionUtils.isEmpty(allList)) {
								for (BanquetItem item : allList) {
									if (!item.getIsPaid()) {
										item.setIndividualProduct(null);
										this.banquetItemRepository.saveAndFlush(item);
									}
								}
							}
						}
					} else if (status.getIntValue() == BanquetStatus.APPROVED.getIntValue()) {
						banquet.setStatus(status);
						banquet.setApproveComment(APPROVE_COMMENT);
						// 审批合格发起稽核
						Banquet auditBanquet = this.banquetRepository.saveAndFlush(banquet);
						if (auditBanquet != null) {
							banquetAuditIds.add(auditBanquet.getId());
						}
					}
				}
			} else if (banquets.size() == 1) {
				Banquet banquet = banquets.get(0);
				banquet.setStatus(status);
				if (StringUtils.isNotBlank(banquetApproveRequest.getApproveComment())) {
					banquet.setApproveComment(banquetApproveRequest.getApproveComment());
					Banquet tempBanquet = this.banquetRepository.saveAndFlush(banquet);
					if (tempBanquet != null) {
						if (tempBanquet.getStatus() != null) {
							if (tempBanquet.getStatus().getIntValue() == BanquetStatus.APPROVED.getIntValue()) {
								// 审核合格发起稽核
								banquetAuditIds.add(tempBanquet.getId());
							} else if (tempBanquet.getStatus().getIntValue() == BanquetStatus.NOT_APPROVED.getIntValue()) {
								List<BanquetItem> allList = this.banquetItemRepository.findReportedByBanquetId(tempBanquet.getId());
								if (!CollectionUtils.isEmpty(allList)) {
									for (BanquetItem item : allList) {
										if (!item.getIsPaid()) {
											item.setIndividualProduct(null);
											this.banquetItemRepository.save(item);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return banquetAuditIds;
	}

	@Override
	@Transactional(readOnly = true)
	public List<BanquetType> findBanquetType() {
		return this.banquetTypeRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public BanquetRelatedInfoResponse findBanquetRelatedInfo(Long empId) {
		BanquetRelatedInfoResponse banquetRelatedInfoResponse = new BanquetRelatedInfoResponse();
		
		this.buildBanquetEnumInfo(banquetRelatedInfoResponse);
		Employee emp = employeeRepository.findOne(empId);
		String externalId = emp.getExternalId();
		EmployeeView employeeView = employeeViewRepository.findOne(externalId);
		if (null != employeeView && ! CollectionUtils.isEmpty(employeeView.getPositions())) {
			PositionView positionView = employeeView.getPositions().get(0);
			if (positionView == null) {
				throw this.getExceptionEntity(new RuntimeException(), "employeeId", String.valueOf(empId), "业务员岗位对应职务不存在");
			}
			this.buildBanquetOrgInfo(positionView, banquetRelatedInfoResponse);
		} else {
			throw this.getExceptionEntity(new RuntimeException(), "employeeId", String.valueOf(empId), "业务员岗位对应人员不存在");
		}
		return banquetRelatedInfoResponse;
	}
	
	@Override
	@Transactional(readOnly = true)
	public BanquetChannelRelProduct findBanquetChannel (BanquetApplicationType banquetApplicationType) {
		BanquetChannelRelProduct banquetChannelRelProduct  = new BanquetChannelRelProduct();
		Set<ChannelInfoResponse> channels = new HashSet<ChannelInfoResponse>();
		Set<ProductInfoResponse> productsInfo = new HashSet<ProductInfoResponse>();
		List<BanquetRebateConfig> banquetRebateConfigs = banquetRebateConfigRepository.findByScanedProduct(banquetApplicationType);
		if (!CollectionUtils.isEmpty(banquetRebateConfigs)) {
			for (BanquetRebateConfig banquetRebateConfig : banquetRebateConfigs) {
				Product product = banquetRebateConfig.getProduct();
				Channel channel = banquetRebateConfig.getChannel();
				if (product != null) {
					ProductInfoResponse productInfoResponse = new ProductInfoResponse(product);
					if (channel != null) {
						productInfoResponse.setChannelName(channel.getName());
						
					}
					productsInfo.add(productInfoResponse);
					ChannelInfoResponse channelInfoResponse = new ChannelInfoResponse(channel);
					if (channels.contains(channelInfoResponse)) {
						continue;
					}
					channels.add(channelInfoResponse);
				}
			}
		}
		banquetChannelRelProduct.setChannel(channels);
		banquetChannelRelProduct.setProductInfoResponse(productsInfo);
		return banquetChannelRelProduct;
	}
	
	@Transactional(readOnly = true)
	private void buildBanquetOrgInfo(PositionView positionView, BanquetRelatedInfoResponse banquetRelatedInfoResponse) {
		List<EmployeeInfoResponse> cityManagerInfo = new ArrayList<EmployeeInfoResponse>();
		
		List<TerminalInfoResponse> terminaInfo = new ArrayList<TerminalInfoResponse>();
		List<Terminal> terminals =  new ArrayList<Terminal>();
		
		if (positionView.getDepartment() != null && positionView.getDepartment().getSuperior() != null
			&& ! CollectionUtils.isEmpty(positionView.getDepartment().getSuperior().getPositions())) {
			List<PositionView> views = positionView.getDepartment().getSuperior().getPositions();
			if (views != null) {
				for (PositionView view : views) {
					if (view != null && view.getIsHead()) {
						EmployeeInfoResponse employeeInfoResponse = new EmployeeInfoResponse(view.getEmployee());
						cityManagerInfo.add(employeeInfoResponse);
					}
				}
			} else {
				throw this.getExceptionEntity(new RuntimeException(), "positionId", String.valueOf(positionView.getId()), "业务员岗位对应城市经理不存在");
			} 
		} else {
			throw this.getExceptionEntity(new RuntimeException(), "positionId", String.valueOf(positionView.getId()), "业务员岗位对应部门不存在");
		}
		
		terminals =  positionView.getTerminals();
		for (Terminal terminal : terminals) {
			if (terminal != null) {
				if (terminal.getType().equals(TerminalType.SHOP)) {
					TerminalInfoResponse terminalInfoResponse = new TerminalInfoResponse(terminal);
					terminalInfoResponse.setTerminalType(TerminalType.SHOP.getLabel());
					terminaInfo.add(terminalInfoResponse);
				} else if (terminal.getType().equals(TerminalType.HOTEL)) {
					TerminalInfoResponse terminalInfoResponse = new TerminalInfoResponse(terminal);
					terminalInfoResponse.setTerminalType(TerminalType.HOTEL.getLabel());
					terminaInfo.add(terminalInfoResponse);
				}
			} else {
				throw this.getExceptionEntity(new RuntimeException(), "positionId", String.valueOf(positionView.getId()), "业务员岗位对应终端不存在");
			}
		}
		
		Dealer dealer = positionView.getDealer();
		if (null != dealer) {
			DealerInfoResponse dealerInfoResponse = new DealerInfoResponse(dealer);
			banquetRelatedInfoResponse.setDealerInfoResponse(dealerInfoResponse);
		} else {
			throw this.getExceptionEntity(new RuntimeException(), "positionId", String.valueOf(positionView.getId()), "业务员岗位对应经销商不存在");
		}
		
		banquetRelatedInfoResponse.setCityManagerInfoResponse(cityManagerInfo);
		banquetRelatedInfoResponse.setTerminalInfoResponse(terminaInfo);
	}
	
	@Transactional(readOnly = true)
	private void buildBanquetEnumInfo(BanquetRelatedInfoResponse banquetRelatedInfoResponse) {
		List<BanquetType> banquetTypes = this.banquetTypeRepository.findAll();
		List<BanquetTypeInfoResponse> banquetTypeInfoResponse = banquetTypes.stream().map(banquetType -> new BanquetTypeInfoResponse(banquetType)).collect(Collectors.toList());
		
		List<BanquetApplicationType> banquetApplicationTypes = new ArrayList<BanquetApplicationType>();
		List<BanquetScanType> banquetScanTypes = new ArrayList<BanquetScanType>();
		List<TerminalType> terminalTypes = new ArrayList<TerminalType>();
		List<BanquetTimePoint> timePointTypes = new ArrayList<BanquetTimePoint>();
		
		
		BanquetApplicationType[] applyArray = BanquetApplicationType.values();
		for (BanquetApplicationType banquetApplicationType : applyArray) {
			banquetApplicationTypes.add(banquetApplicationType);
		}
		
		BanquetScanType[] scanArray = BanquetScanType.values();
		for (BanquetScanType banquetScanType : scanArray) {
			banquetScanTypes.add(banquetScanType);
		}
		
		TerminalType[] terminalArray = TerminalType.values();
		for (TerminalType terminalType : terminalArray) {
			terminalTypes.add(terminalType);
		}
		
		BanquetTimePoint[] timePointArray = BanquetTimePoint.values();
		for (BanquetTimePoint timePointType : timePointArray) {
			timePointTypes.add(timePointType);
		}
		
		banquetRelatedInfoResponse.setBanquetTypes(banquetTypeInfoResponse);
		banquetRelatedInfoResponse.setBanquetApplyTypes(banquetApplicationTypes);
		banquetRelatedInfoResponse.setBanquetScanTypes(banquetScanTypes);
		banquetRelatedInfoResponse.setTerminalTypes(terminalTypes);
		banquetRelatedInfoResponse.setTimePointTypes(timePointTypes);
	}

	@Override
	public List<BanquetEnumInfoResponse> findBanquetEnumType(String type) {
		List<BanquetEnumInfoResponse> list = new ArrayList<BanquetEnumInfoResponse>();
		if (type.equals("BanquetApply")) {
			BanquetApplicationType[] array = BanquetApplicationType.values();
			for (BanquetApplicationType banquetApplicationType : array) {
				BanquetEnumInfoResponse applicationType = new BanquetEnumInfoResponse();
				applicationType.setValue(banquetApplicationType.getIntValue());
				applicationType.setLabel(banquetApplicationType.getLabel());
				list.add(applicationType);
			} 
		} else if (type.equals("BanquetStatus")) {
			BanquetStatus[] array = BanquetStatus.values();
			for (BanquetStatus banquetStatus : array) {
				BanquetEnumInfoResponse applicationType = new BanquetEnumInfoResponse();
				applicationType.setValue(banquetStatus.getIntValue());
				applicationType.setLabel(banquetStatus.getLabel());
				list.add(applicationType);
			}
		} else if (type.equals("ScanType")) {
			BanquetScanType[] array = BanquetScanType.values();
			for (BanquetScanType banquetScanType : array) {
				BanquetEnumInfoResponse applicationType = new BanquetEnumInfoResponse();
				applicationType.setValue(banquetScanType.getIntValue());
				applicationType.setLabel(banquetScanType.getLabel());
				list.add(applicationType);
			}
		}
		return list;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<EmployeeInfoResponse> findHandlerByCreatorId(Long creatorId) {
		List<EmployeeInfoResponse> salesManInfo = new ArrayList<EmployeeInfoResponse>();
		Employee emp = employeeRepository.findOne(creatorId);
		String externalId = emp.getExternalId();
		EmployeeView employeeView = employeeViewRepository.findOne(externalId);
		if (null != employeeView && ! CollectionUtils.isEmpty(employeeView.getPositions())) {
			PositionView positionView = employeeView.getPositions().get(0);
			if (null != positionView.getDepartment()) {
				List<PositionView> salesMenPosition = positionView.getDepartment().getPositions();
				for (PositionView salesManPosition : salesMenPosition) {
					if (salesManPosition != null && salesManPosition.getIsHead()) {
						continue;
					}
					salesManInfo = salesManPosition.getEmployees().stream().map(empInfo -> new EmployeeInfoResponse(empInfo)).collect(Collectors.toList());
				}
			} else {
				throw this.getExceptionEntity(new RuntimeException(), "employeeId", String.valueOf(creatorId), "业务员岗位对应部门不存在");
			}
		} else {
			throw this.getExceptionEntity(new RuntimeException(), "employeeId", String.valueOf(creatorId), "业务员岗位对应人员不存在");
		}
		return salesManInfo;
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> findAllOffice() {
		    List<String> departmentNames = new ArrayList<String>();
			List<DepartmentView> departments = departmentViewRepository.findAll();
			if (! CollectionUtils.isEmpty(departments)) {
				for (DepartmentView department : departments) {
					Boolean isOffice = false;
					if (department != null) {
						List<PositionView> positions = department.getPositions();
						if (! CollectionUtils.isEmpty(positions)) {
							for (PositionView position : positions) {
								if (position != null && position.getJob() != null) {
									if (OFFICE_DIRECTOR_JOB.equals(position.getJob().getName())) {
										isOffice = true;
										break;
									}
								}
							}
						}
						if (isOffice) {
							departmentNames.add(department.getName());
						}
					}
				}
			}
			return departmentNames;
	}

	@Override
	@Transactional
	public Banquet assignHandler(BanquetMergeRequest request) {
		if (request == null) {
			return null;
		} 
		if (request.getHandler() == null && request.getId() == null) {
			return null;
		}
		Banquet banquet = this.findOne(request.getId());
		Employee emp = employeeRepository.findOne(request.getHandler().getId());
		if (emp != null) {
			EmployeeView empView = employeeViewRepository.findOne(emp.getExternalId());
			if (empView != null) {
				Employee handler = new Employee();
				handler.setId(empView.getId());
				handler.setName(empView.getName());
				banquet.setHandler(handler);
			}
		} else {
			throw this.getExceptionEntity(new RuntimeException(), "employeeId", String.valueOf(request.getHandler().getId()), "业务员岗位对应人员不存在");
		}
		return this.banquetRepository.saveAndFlush(banquet);
	}

	@Override
	public Banquet cancelBanquet(Long id) {
		if (id == null || id == 0) {
			return null;
		}
		Banquet banquet = this.findOne(id);
		if (banquet == null) {
			return null;
		}
		if (banquet.getCashTime() != null) {
			throw this.getExceptionEntity(new RuntimeException(), "banquetId", String.valueOf(banquet.getId()), "已兑付的宴会不能够取消");
		}
		banquet.setStatus(BanquetStatus.CANCELLED);
		Banquet canceledBanquet = this.banquetRepository.saveAndFlush(banquet);
		if (canceledBanquet != null) {
			// 解锁T_INDIVIDUAL_PRODUCT里本次报备中未被兑付的记录
			List<BanquetItem> allList = this.banquetItemRepository.findReportedByBanquetId(canceledBanquet.getId());
			if (!CollectionUtils.isEmpty(allList)) {
				for (BanquetItem item : allList) {
					if (!item.getIsPaid()) {
						item.setIndividualProduct(null);
						this.banquetItemRepository.save(item);
					}
				}
			} 
		}
		return banquet;
	}
	
	/*
	 * This method is used to construct the error format of DB operation. The error will be returned to client via the exception handler in Controller
	 */
	private ResponseBodyDBAuditException getExceptionEntity(RuntimeException e, String field, String rejectValue, String message) {
		FieldErrorBodyAudit errors = new FieldErrorBodyAudit();
		List<FieldErrorBodyAudit.FieldErrorBodyItems> itemsBody = new ArrayList<FieldErrorBodyAudit.FieldErrorBodyItems>(1);
		FieldErrorBodyAudit.FieldErrorBodyItems itemBody = errors.new FieldErrorBodyItems(field, rejectValue, message);
		itemsBody.add(itemBody);
		errors.setError(itemsBody);
		return new ResponseBodyDBAuditException(e, errors);
	}
}
