package com.sap.jnc.marketing.dto.request.activity;

import java.math.BigDecimal;

/**
 * Created by ddtang on 16/6/24.
 */
public class GainWineRequest {
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String url;
    private String addressRecommend;
    private String addressAdcode;
    private String addressNation;
    private String addressProvince;
    private String addressCity;
    private String addressDistrict;
    private String addressStreet;
    private String addressStreetNumber;
    private String wxUserName;
    private String wxOpenId;

    public String getWxUserName() {
        return wxUserName;
    }

    public void setWxUserName(String wxUserName) {
        this.wxUserName = wxUserName;
    }

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAddressRecommend() {
        return addressRecommend;
    }

    public void setAddressRecommend(String addressRecommend) {
        this.addressRecommend = addressRecommend;
    }

    public String getAddressAdcode() {
        return addressAdcode;
    }

    public void setAddressAdcode(String addressAdcode) {
        this.addressAdcode = addressAdcode;
    }

    public String getAddressNation() {
        return addressNation;
    }

    public void setAddressNation(String addressNation) {
        this.addressNation = addressNation;
    }

    public String getAddressProvince() {
        return addressProvince;
    }

    public void setAddressProvince(String addressProvince) {
        this.addressProvince = addressProvince;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressDistrict() {
        return addressDistrict;
    }

    public void setAddressDistrict(String addressDistrict) {
        this.addressDistrict = addressDistrict;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressStreetNumber() {
        return addressStreetNumber;
    }

    public void setAddressStreetNumber(String addressStreetNumber) {
        this.addressStreetNumber = addressStreetNumber;
    }
}
