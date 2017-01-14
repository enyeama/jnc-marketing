package com.sap.jnc.marketing.dto.response.banquet;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.sap.jnc.marketing.persistence.model.Product;

/**
 * @author I332242 Zhu Qiang
 */
public class ProductInfoResponse implements Serializable {

	private static final long serialVersionUID = -6045858023384714095L;

	private String id;

	private String name;

	private String description;
	
	private String channelName;
	
	public ProductInfoResponse () {
		
	}
	
	public ProductInfoResponse(Product product) {
		if (null == product) {
			return;
		}
		if (null != product.getId()) {
			this.setId(product.getId());
		}
		if (StringUtils.isNotBlank(product.getName())) {
			this.setName(product.getName());
		}
		if (StringUtils.isNotBlank(product.getDescription())) {
			this.setName(product.getDescription());
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	@Override
	public boolean equals(Object obj){
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (obj instanceof ProductInfoResponse) {
			ProductInfoResponse other = (ProductInfoResponse) obj;
			return other.id.equals(this.id) ;
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return this.id.hashCode();
		
	}
}
