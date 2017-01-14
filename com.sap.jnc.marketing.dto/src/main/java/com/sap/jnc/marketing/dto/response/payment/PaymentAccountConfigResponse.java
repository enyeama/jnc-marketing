package com.sap.jnc.marketing.dto.response.payment;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.sap.jnc.marketing.persistence.model.PaymentAccountConfig;

public class PaymentAccountConfigResponse extends PageImpl<PaymentAccountConfigResponse.Item> implements Serializable {

	private static final long serialVersionUID = -1928720649790894206L;

	public PaymentAccountConfigResponse(Page<PaymentAccountConfig> pages, PageRequest pageRequest) {
		super(
			// Content
			pages.getContent().stream().map(pac -> new Item(pac)).collect(Collectors.toList()),
			// Page Request
			pageRequest,
			// Total
			pages.getTotalElements());
	}

	public static class Item implements Serializable {

		private static final long serialVersionUID = -1794750988843748693L;

		private Long id;
		/** 默认支付账户商户号 */
		private Long defaultAccountId;
		/** 默认支付账户公众账号appId */
		private String defaultAccountOpenId;

		/** 均值生效时间 */
		private String validFrom;
		/** 均值失效时间 */
		private String validTo;

		public Item(PaymentAccountConfig pac) {
			if (pac == null) {
				return;
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			this.setId(pac.getId());
			this.setDefaultAccountId(pac.getDefaultAccountId());
			this.setDefaultAccountOpenId(pac.getDefaultAccountOpenId());
			this.setValidFrom(sdf.format(pac.getValidityPeriod().getValidFrom().getTime()));
			this.setValidTo(sdf.format(pac.getValidityPeriod().getValidTo().getTime()));

		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getDefaultAccountId() {
			return defaultAccountId;
		}

		public void setDefaultAccountId(Long defaultAccountId) {
			this.defaultAccountId = defaultAccountId;
		}

		public String getDefaultAccountOpenId() {
			return defaultAccountOpenId;
		}

		public void setDefaultAccountOpenId(String defaultAccountOpenId) {
			this.defaultAccountOpenId = defaultAccountOpenId;
		}

		public String getValidFrom() {
			return validFrom;
		}

		public void setValidFrom(String validFrom) {
			this.validFrom = validFrom;
		}

		public String getValidTo() {
			return validTo;
		}

		public void setValidTo(String validTo) {
			this.validTo = validTo;
		}

	}

}
