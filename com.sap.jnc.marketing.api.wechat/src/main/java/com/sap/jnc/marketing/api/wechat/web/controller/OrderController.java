package com.sap.jnc.marketing.api.wechat.web.controller;

import com.sap.jnc.marketing.infrastructure.shared.constant.ApiResult;
import com.sap.jnc.marketing.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by guokai on 16/6/22.
 */
@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @RequestMapping("scanQrCode")
    public ApiResult storage(@RequestParam(value = "orderId")Long orderId,
                             @RequestParam("code[]")List<String> codes){
        orderService.scanQrCode(orderId, codes);
        return ApiResult.SUCCESS;
    }
}
