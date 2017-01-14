package com.sap.jnc.marketing.service.activity.impl;

import com.sap.jnc.marketing.dto.request.activity.GainWineRequest;
import com.sap.jnc.marketing.persistence.model.WechatPromotionActivity;
import com.sap.jnc.marketing.persistence.repository.ProductDmsCategoryRepository;
import com.sap.jnc.marketing.persistence.repository.WechatPromotionActivityRepository;
import com.sap.jnc.marketing.service.activity.PromotionActivityService;
import com.sap.jnc.marketing.service.security.portal.PortalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

/**
 * Created by ddtang on 16/6/23.
 */
@Service
@Transactional(readOnly = true)
public class PromotionActivityServiceImpl implements PromotionActivityService{

    @Autowired
    private WechatPromotionActivityRepository wechatPromotionActivityRepository;

    /**
     *
     * @param openId
     * @return 0 didn't gain wine in five days
     */
    @Override
    public boolean checkFive(String openId) {
        return wechatPromotionActivityRepository.countByDaysAndOpenId(5, openId) == 0l;
    }

    @Transactional(readOnly = false)
    @Override
    public void create(GainWineRequest request) {
        WechatPromotionActivity activity = new WechatPromotionActivity();
        activity.setActivityId("1");
        activity.setAddressAdcode(request.getAddressAdcode());
        activity.setAddressCity(request.getAddressCity());
        activity.setAddressAdcode(request.getAddressAdcode());
        activity.setAddressDistrict(request.getAddressDistrict());
        activity.setAddressNation(request.getAddressNation());
        activity.setAddressProvince(request.getAddressProvince());
        activity.setAddressRecommend(request.getAddressRecommend());
        activity.setReceiveDate(Calendar.getInstance());
        activity.setAddressStreetNumber(request.getAddressStreetNumber());
        activity.setAddressStreet(request.getAddressStreet());
        activity.setUrl(request.getUrl());
        activity.setWechatName(request.getWxUserName());
        activity.setWechatOpenID(request.getWxOpenId());
        activity.setLongitude(request.getLongitude());
        activity.setLatitude(request.getLatitude());
        wechatPromotionActivityRepository.save(activity);
    }
}
