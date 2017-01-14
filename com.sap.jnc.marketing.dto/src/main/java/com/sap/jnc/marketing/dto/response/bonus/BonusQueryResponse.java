package com.sap.jnc.marketing.dto.response.bonus;

/**
 * Created by dy on 16/6/27.
 */
public class BonusQueryResponse {

    private String mchBillno;

    private String mchId;

    private String detailId;

    private String status;

    private String openId;

    private String rcvTime;

    public String getMchBillno() {
        return mchBillno;
    }

    public void setMchBillno(String mchBillno) {
        this.mchBillno = mchBillno;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getRcvTime() {
        return rcvTime;
    }

    public void setRcvTime(String rcvTime) {
        this.rcvTime = rcvTime;
    }
}
