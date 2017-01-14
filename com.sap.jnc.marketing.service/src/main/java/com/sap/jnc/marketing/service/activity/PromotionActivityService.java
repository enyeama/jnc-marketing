package com.sap.jnc.marketing.service.activity;

import com.sap.jnc.marketing.dto.request.activity.GainWineRequest;

/**
 * Created by ddtang on 16/6/23.
 * deal for promotion activity
 */
public interface PromotionActivityService {

    boolean checkFive(String openId);

    void create(GainWineRequest request);
}
