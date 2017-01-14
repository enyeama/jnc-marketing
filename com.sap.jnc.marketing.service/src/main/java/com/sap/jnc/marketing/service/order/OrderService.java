package com.sap.jnc.marketing.service.order;

import java.util.List;

/**
 * Created by guokai on 16/6/22.
 */
public interface OrderService {

    void scanQrCode(Long orderId, List<String> codes);
}
