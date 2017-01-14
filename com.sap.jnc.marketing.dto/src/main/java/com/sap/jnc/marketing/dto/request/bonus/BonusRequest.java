package com.sap.jnc.marketing.dto.request.bonus;

/**
 * Created by dy on 16/6/24.
 */
public class BonusRequest {

    private String openId;

    private String clientIp;

    private String verifyCode;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    @Override
    public String toString() {
        return "BonusRequest{" +
                "openId='" + openId + '\'' +
                ", clientIp='" + clientIp + '\'' +
                ", verifyCode='" + verifyCode + '\'' +
                '}';
    }
}
