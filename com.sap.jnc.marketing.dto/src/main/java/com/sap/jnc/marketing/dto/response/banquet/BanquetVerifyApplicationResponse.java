package com.sap.jnc.marketing.dto.response.banquet;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;

import com.sap.jnc.marketing.persistence.enumeration.BanquetVerifyApplicationStatus;
import com.sap.jnc.marketing.persistence.model.BanquetVerifyApplication;
import com.sap.jnc.marketing.persistence.model.Department;
import com.sap.jnc.marketing.persistence.model.Employee;
import com.sap.jnc.marketing.persistence.model.VerifiedBottleQuantity;

/**
 * @author I332242 Zhu Qiang
 */
public class BanquetVerifyApplicationResponse implements Serializable {

	private static final long serialVersionUID = 1277089986876165736L;

	private Long id;

	private String expressNO;

	private Calendar expressTime;

	private Calendar planArriveTime;

	private Calendar applyTime;

	private String expressCompany;

	private BanquetVerifyApplicationStatus status;

	private String verifier;

	private Calendar verifiedFinishTime;

	private BigDecimal bottleQuantityValue;

	private String bottleQuantityUnit;

	private VerifiedBottleQuantity verifiedBottleQuantity;

	private Long creatorId;

	private String creatorName;

	private String officeName;

	private Integer boxQuantity;
	
	private BanquetInfoResponse banquet;
	
	private Calendar createOn;
	
	private Long banquetId;

	public BanquetVerifyApplicationResponse(BanquetVerifyApplication entity) {
		if (entity.getId() != null) {
			this.setId(entity.getId());
		}
		// 快递公司
		if (StringUtils.isNotBlank(entity.getExpressCompany())) {
			this.setExpressCompany(entity.getExpressCompany());
		}
		// 快递单号
		if (StringUtils.isNotBlank(entity.getExpressNO())) {
			this.setExpressNO(entity.getExpressNO());
		}
		// 宴会报备详情
		if (entity.getBanquet() != null) {
			// 宴会报备单号
			if (entity.getBanquet() != null) {
				this.setBanquetId(entity.getBanquet().getId());
			}
			this.setBanquet(new BanquetInfoResponse(entity.getBanquet()));
		}
		// 邮寄日期
		if (entity.getExpressTime() != null) {
			this.setExpressTime(entity.getExpressTime());
		}
		// 预计到达日期
		if (entity.getPlanArriveTime() != null) {
			this.setPlanArriveTime(entity.getPlanArriveTime());
		}
		// 邮寄人
		if (entity.getCreator() != null) {
			Employee creator = entity.getCreator();
			if (StringUtils.isNotBlank(creator.getName())) {
				this.setCreatorName(creator.getName());
			}
			if (creator.getId() != null) {
				this.setCreatorId(creator.getId());
			}
		}
		// 办事处
		if (entity.getOffice() != null) {
			Department department = entity.getOffice();
			if (StringUtils.isNotBlank(department.getName())) {
				this.setOfficeName(department.getName());

			}
		}
		// 邮寄瓶盖数
		if (entity.getBottleQuantity() != null) {
			if (entity.getBottleQuantity().getValue() != null) {
				this.setBottleQuantityValue(bottleQuantityValue);
			}
		}
        // 邮寄盒盖数
		if (entity.getBoxQuantity() != null) {
			this.setBoxQuantity(entity.getBoxQuantity());
		}
		// 核销申请状态
		if (entity.getStatus() != null) {
			this.setStatus(entity.getStatus());
		}
		// 核销申请创建时间
		if (entity.getCreateOn() != null) {
			this.setCreateOn(entity.getCreateOn());
		}
		// 核销申请提报时间
		if (entity.getApplyTime() != null) {
			this.setApplyTime(entity.getApplyTime());
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExpressNO() {
		return expressNO;
	}

	public void setExpressNO(String expressNO) {
		this.expressNO = expressNO;
	}

	public Calendar getExpressTime() {
		return expressTime;
	}

	public void setExpressTime(Calendar expressTime) {
		this.expressTime = expressTime;
	}

	public Calendar getPlanArriveTime() {
		return planArriveTime;
	}

	public void setPlanArriveTime(Calendar planArriveTime) {
		this.planArriveTime = planArriveTime;
	}

	public Calendar getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Calendar applyTime) {
		this.applyTime = applyTime;
	}

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public BanquetVerifyApplicationStatus getStatus() {
		return status;
	}

	public void setStatus(BanquetVerifyApplicationStatus status) {
		this.status = status;
	}

	public String getVerifier() {
		return verifier;
	}

	public void setVerifier(String verifier) {
		this.verifier = verifier;
	}

	public Calendar getVerifiedFinishTime() {
		return verifiedFinishTime;
	}

	public void setVerifiedFinishTime(Calendar verifiedFinishTime) {
		this.verifiedFinishTime = verifiedFinishTime;
	}

	public BigDecimal getBottleQuantityValue() {
		return bottleQuantityValue;
	}

	public void setBottleQuantityValue(BigDecimal bottleQuantityValue) {
		this.bottleQuantityValue = bottleQuantityValue;
	}

	public String getBottleQuantityUnit() {
		return bottleQuantityUnit;
	}

	public void setBottleQuantityUnit(String bottleQuantityUnit) {
		this.bottleQuantityUnit = bottleQuantityUnit;
	}

	public VerifiedBottleQuantity getVerifiedBottleQuantity() {
		return verifiedBottleQuantity;
	}

	public void setVerifiedBottleQuantity(VerifiedBottleQuantity verifiedBottleQuantity) {
		this.verifiedBottleQuantity = verifiedBottleQuantity;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public BanquetInfoResponse getBanquet() {
		return banquet;
	}

	public void setBanquet(BanquetInfoResponse banquet) {
		this.banquet = banquet;
	}

	public Integer getBoxQuantity() {
		return boxQuantity;
	}

	public void setBoxQuantity(Integer boxQuantity) {
		this.boxQuantity = boxQuantity;
	}

	public Calendar getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Calendar createOn) {
		this.createOn = createOn;
	}

	public Long getBanquetId() {
		return banquetId;
	}

	public void setBanquetId(Long banquetId) {
		this.banquetId = banquetId;
	}

}
