package com.sap.jnc.marketing.service.order.impl;

import com.sap.jnc.marketing.service.order.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by guokai on 16/6/22.
 */
@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {


    @Override
    public void scanQrCode(Long orderId, List<String> codes) {

    }
}
