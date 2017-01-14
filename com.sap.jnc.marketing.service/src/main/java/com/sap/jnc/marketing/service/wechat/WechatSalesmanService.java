package com.sap.jnc.marketing.service.wechat;

import java.util.List;

import com.sap.jnc.marketing.dto.request.wechat.salesman.SalesOrderRequest;
import com.sap.jnc.marketing.dto.request.wechat.salesman.SalesTerminalsRequest;
import com.sap.jnc.marketing.persistence.enumeration.TerminalOrderStatus;
import com.sap.jnc.marketing.persistence.model.Product;
import com.sap.jnc.marketing.persistence.model.ProductDmsCategory;
import com.sap.jnc.marketing.persistence.model.Terminal;
import com.sap.jnc.marketing.persistence.model.TerminalOrder;

/**
 * @author C5245167 Xiao Qi
 */
public interface WechatSalesmanService {

	String createOrder(SalesOrderRequest request);

	List<TerminalOrder> getOrders(String terminalId, String salesmanId, TerminalOrderStatus status);

	TerminalOrder viewOrder(String orderId);

	List<ProductDmsCategory> getCategory(String salesCategoryId);

	List<Product> getProductsByCategoryId(String dmsCategoryId, String channelId);

	List<Terminal> getTerminals(SalesTerminalsRequest request);

	TerminalOrder cancelOrder(String orderId);
}
