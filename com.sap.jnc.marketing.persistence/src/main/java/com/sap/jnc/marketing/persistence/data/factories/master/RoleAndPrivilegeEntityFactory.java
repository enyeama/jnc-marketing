package com.sap.jnc.marketing.persistence.data.factories.master;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.sap.jnc.marketing.persistence.enumeration.PrivilegeType;
import com.sap.jnc.marketing.persistence.model.Privilege;
import com.sap.jnc.marketing.persistence.model.Role;
import com.sap.jnc.marketing.persistence.repository.PrivilegeRepository;
import com.sap.jnc.marketing.persistence.repository.RoleRepository;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RoleAndPrivilegeEntityFactory extends GeneralMasterDataEntityFactory<Role> {

	@SuppressWarnings("serial")
	public static final Map<String, Map<String, Map<String, PrivilegeType>>> INITIAL_ROLE_PRIVILEGES
	// Initial role and privileges
	= new HashMap<String, Map<String, Map<String, PrivilegeType>>>(64) {
		{
			this.put("KA专员", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("KA考核", new HashMap<String, PrivilegeType>() {
						{
							this.put("送货及时率", PrivilegeType.ADMIN);
							this.put("到货汇总", PrivilegeType.ADMIN);
						}
					});
					this.put("KA订单管理", new HashMap<String, PrivilegeType>() {
						{
							this.put("KA网点订单", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("KA事业部内勤", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("KA网点维护", new HashMap<String, PrivilegeType>() {
						{
							this.put("KA网点信息维护", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("产销协调办A", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("数据导入", new HashMap<String, PrivilegeType>() {
						{
							this.put("HR主数据", PrivilegeType.ADMIN);
							this.put("物料主数据", PrivilegeType.ADMIN);
							this.put("合同", PrivilegeType.ADMIN);
							this.put("经销商", PrivilegeType.ADMIN);
							this.put("旧物流码", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("人资部人力专员", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("移动访销", new HashMap<String, PrivilegeType>() {
						{
							this.put("人事子范围-DMS大类", PrivilegeType.ADMIN);
						}
					});
					this.put("全员营销", new HashMap<String, PrivilegeType>() {
						{
							this.put("账号管理", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("销管部促品处L", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("备用物料", new HashMap<String, PrivilegeType>() {
						{
							this.put("安全库存管理", PrivilegeType.ADMIN);
							this.put("兑付明细", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("大组长", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("宴会", new HashMap<String, PrivilegeType>() {
						{
							this.put("核销申请", PrivilegeType.ADMIN);
						}
					});
					this.put("备用物料", new HashMap<String, PrivilegeType>() {
						{
							this.put("发货明细", PrivilegeType.ADMIN);
							this.put("兑付明细", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("市管部内勤A", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("宴会", new HashMap<String, PrivilegeType>() {
						{
							this.put("宴会审批", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("市管部内勤B", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("宴会", new HashMap<String, PrivilegeType>() {
						{
							this.put("宴会全流程报表", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("市管部内勤C", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("稽核", new HashMap<String, PrivilegeType>() {
						{
							this.put("宴会复核导入", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("市管部内勤D", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("稽核", new HashMap<String, PrivilegeType>() {
						{
							this.put("稽核查询", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("市管部内勤E", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("稽核", new HashMap<String, PrivilegeType>() {
						{
							this.put("稽核超级管理", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("市管部内勤F", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("稽核", new HashMap<String, PrivilegeType>() {
						{
							this.put("稽核人员分配", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("市管部内勤G", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("报表", new HashMap<String, PrivilegeType>() {
						{
							this.put("物流环节返利报表", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("市管部内勤H", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("报表", new HashMap<String, PrivilegeType>() {
						{
							this.put("红包发放明细报表", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("拉环室内勤A", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("宴会", new HashMap<String, PrivilegeType>() {
						{
							this.put("签收核销申请", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("拉环室内勤B", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("宴会", new HashMap<String, PrivilegeType>() {
						{
							this.put("宴会拉环报表", PrivilegeType.ADMIN);
							this.put("拉环结果明细表", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("拉环室操作员A", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("宴会", new HashMap<String, PrivilegeType>() {
						{
							this.put("拉环核数", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("拉环室操作员B", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("宴会", new HashMap<String, PrivilegeType>() {
						{
							this.put("拉环验证", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("稽核部所有人", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("稽核", new HashMap<String, PrivilegeType>() {
						{
							this.put("稽核查询", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("财务部内勤", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("报表", new HashMap<String, PrivilegeType>() {
						{
							this.put("红包发放明细报表", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("销管部信息处A", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("移动访销", new HashMap<String, PrivilegeType>() {
						{
							this.put("经销商-组长-产品", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("销管部信息处C", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("业务配置", new HashMap<String, PrivilegeType>() {
						{
							this.put("红包金额配置", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("销管部客户处B", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("报表", new HashMap<String, PrivilegeType>() {
						{
							this.put("红包发放明细报表", PrivilegeType.ADMIN);
						}
					});
					this.put("业务配置", new HashMap<String, PrivilegeType>() {
						{
							this.put("支付账户配置", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("销管部核销处D", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("宴会", new HashMap<String, PrivilegeType>() {
						{
							this.put("宴会拉环报表", PrivilegeType.ADMIN);
							this.put("宴会全流程报表", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("销管部核销处G", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("宴会", new HashMap<String, PrivilegeType>() {
						{
							this.put("宴会照片审批", PrivilegeType.ADMIN);
						}
					});
					this.put("陈列", new HashMap<String, PrivilegeType>() {
						{
							this.put("陈列审核", PrivilegeType.ADMIN);
							this.put("陈列兑付", PrivilegeType.ADMIN);
						}
					});
					this.put("稽核", new HashMap<String, PrivilegeType>() {
						{
							this.put("稽核查询", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("销管部核销处H", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("宴会", new HashMap<String, PrivilegeType>() {
						{
							this.put("核销三项统计报表", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("销管部核销处J", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("陈列", new HashMap<String, PrivilegeType>() {
						{
							this.put("陈列核销", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("销管部核销处M", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("报表", new HashMap<String, PrivilegeType>() {
						{
							this.put("物流环节返利报表", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("销管部物料处E", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("宴会", new HashMap<String, PrivilegeType>() {
						{
							this.put("拉环结果明细表", PrivilegeType.ADMIN);
							this.put("宴会拉环报表", PrivilegeType.ADMIN);
							this.put("宴会全流程报表", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("销管部物料处F", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("宴会", new HashMap<String, PrivilegeType>() {
						{
							this.put("拉环复核", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("销管部物料处I", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("宴会", new HashMap<String, PrivilegeType>() {
						{
							this.put("宴会管理", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("销管部物料处K", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("备用物料", new HashMap<String, PrivilegeType>() {
						{
							this.put("微信扫码领酒记录", PrivilegeType.ADMIN);
							this.put("安全库存管理", PrivilegeType.ADMIN);
							this.put("兑付明细", PrivilegeType.ADMIN);
							this.put("发货明细", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("终端", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("移动访销", new HashMap<String, PrivilegeType>() {
						{
							this.put("订单管理", PrivilegeType.WECHAT);
							this.put("子帐号管理", PrivilegeType.WECHAT);
						}
					});
				}
			});
			this.put("组长", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("移动访销", new HashMap<String, PrivilegeType>() {
						{
							this.put("扫码领货", PrivilegeType.WECHAT);
							this.put("订单管理", PrivilegeType.WECHAT);
							this.put("终端管理", PrivilegeType.WECHAT);
						}
					});
					this.put("全员营销", new HashMap<String, PrivilegeType>() {
						{
							this.put("账号管理", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("平台经销商", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("收发货", new HashMap<String, PrivilegeType>() {
						{
							this.put("扫码收货", PrivilegeType.ADMIN);
							this.put("扫码发货", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("非平台经销商", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("收发货", new HashMap<String, PrivilegeType>() {
						{
							this.put("扫码收货", PrivilegeType.ADMIN);
							this.put("扫码发货", PrivilegeType.ADMIN);
						}
					});
					this.put("订单管理", new HashMap<String, PrivilegeType>() {
						{
							this.put("终端订单", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("业务员", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("活动管理", new HashMap<String, PrivilegeType>() {
						{
							this.put("宴会", PrivilegeType.WECHAT);
							this.put("陈列", PrivilegeType.WECHAT);
						}
					});
					this.put("移动访销", new HashMap<String, PrivilegeType>() {
						{
							this.put("终端管理", PrivilegeType.WECHAT);
							this.put("订单管理", PrivilegeType.WECHAT);
						}
					});
					this.put("全员营销", new HashMap<String, PrivilegeType>() {
						{
							this.put("订单管理", PrivilegeType.WECHAT);
						}
					});
					this.put("KA", new HashMap<String, PrivilegeType>() {
						{
							this.put("订单管理", PrivilegeType.WECHAT);
							this.put("门店管理", PrivilegeType.WECHAT);
						}
					});
				}
			});
			this.put("城市经理", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("备用物料", new HashMap<String, PrivilegeType>() {
						{
							this.put("发货明细", PrivilegeType.ADMIN);
							this.put("兑付明细", PrivilegeType.ADMIN);
							this.put("备用物料管理", PrivilegeType.WECHAT);
						}
					});
					this.put("KA订单管理", new HashMap<String, PrivilegeType>() {
						{
							this.put("KA网点订单", PrivilegeType.ADMIN);
						}
					});
					this.put("宴会", new HashMap<String, PrivilegeType>() {
						{
							this.put("核销申请", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("省区经理", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("稽核", new HashMap<String, PrivilegeType>() {
						{
							this.put("分配稽核任务", PrivilegeType.ADMIN);
							this.put("审核稽核结果", PrivilegeType.ADMIN);
						}
					});
				}
			});
			this.put("办事处主任", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("备用物料", new HashMap<String, PrivilegeType>() {
						{
							this.put("发货明细", PrivilegeType.ADMIN);
							this.put("兑付明细", PrivilegeType.ADMIN);
							this.put("备用物料管理", PrivilegeType.WECHAT);
						}
					});
				}
			});
			this.put("稽核员", new HashMap<String, Map<String, PrivilegeType>>() {
				{
					this.put("稽核", new HashMap<String, PrivilegeType>() {
						{
							this.put("稽核", PrivilegeType.WECHAT);
						}
					});
				}
			});
		}
	};

	@Autowired
	protected RoleRepository roleRepository;

	@Autowired
	protected PrivilegeRepository privilegeRepository;

	@Override
	protected void fillEntitiesForInitialCreation(List<Role> creatingEntities) {
		for (final Entry<String, Map<String, Map<String, PrivilegeType>>> initialRolePrivilege : INITIAL_ROLE_PRIVILEGES.entrySet()) {
			// Role
			Role role = new Role();
			role.setName(initialRolePrivilege.getKey());
			role.setDescription(initialRolePrivilege.getKey());
			role.setPrivileges(new ArrayList<>());
			role = this.roleRepository.save(role);
			for (final Entry<String, Map<String, PrivilegeType>> firstLevelPrivilege : initialRolePrivilege.getValue().entrySet()) {
				// 1st Level Privilege
				Privilege privilegeParent = new Privilege();
				privilegeParent.setName(firstLevelPrivilege.getKey());
				privilegeParent.setDescription(firstLevelPrivilege.getKey());
				privilegeParent.setType(PrivilegeType.MODULE);
				privilegeParent.setChildren(new ArrayList<>(firstLevelPrivilege.getValue().size()));
				if (privilegeParent.getRoles() == null) {
					privilegeParent.setRoles(new ArrayList<>());
				}
				privilegeParent.getRoles().add(role);
				privilegeParent.setChildren(new ArrayList<>());
				role.getPrivileges().add(privilegeParent);
				privilegeParent = this.privilegeRepository.save(privilegeParent);
				for (final Entry<String, PrivilegeType> secondLevelPrivilege : firstLevelPrivilege.getValue().entrySet()) {
					// 2nd Level Privilege
					Privilege privilegeChild = new Privilege();
					privilegeChild.setName(secondLevelPrivilege.getKey());
					privilegeChild.setDescription(secondLevelPrivilege.getKey());
					privilegeChild.setType(secondLevelPrivilege.getValue());
					if (privilegeChild.getRoles() == null) {
						privilegeChild.setRoles(new ArrayList<>());
					}
					privilegeChild.setParent(privilegeParent);
					privilegeParent.getChildren().add(privilegeChild);
					privilegeChild.getRoles().add(role);
					role.getPrivileges().add(privilegeChild);
					role = this.roleRepository.save(role);
					privilegeParent = this.privilegeRepository.save(privilegeParent);
					privilegeChild = this.privilegeRepository.save(privilegeChild);
				}
			}
			creatingEntities.add(role);
		}
		// Super user
		Role superUserRole = new Role();
		superUserRole.setName("超级管理员");
		superUserRole.setDescription("超级管理员");
		superUserRole.setPrivileges(new ArrayList<>(128));
		for (final Role role : creatingEntities) {
			for (Privilege privilege : role.getPrivileges()) {
				superUserRole.getPrivileges().add(privilege);
				privilege.getRoles().add(superUserRole);
				superUserRole = this.roleRepository.save(superUserRole);
				privilege = this.privilegeRepository.save(privilege);
			}
		}
		creatingEntities.add(superUserRole);
		this.roleRepository.flush();
	}

	@Override
	protected JpaRepository<Role, Long> getGeneralJpaRepository() {
		return this.roleRepository;
	}
}
