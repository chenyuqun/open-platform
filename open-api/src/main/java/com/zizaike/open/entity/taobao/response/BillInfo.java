/**  
 * Project Name:open-api  <br/>
 * File Name:BillInfo.java  <br/>
 * Package Name:com.zizaike.open.entity.taobao.response  <br/>
 * Date:2016年1月15日下午2:28:14  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.entity.taobao.response;  

import java.util.List;

/**  
 * ClassName:BillInfo <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2016年1月15日 下午2:28:14 <br/>  
 * @author   alex  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public class BillInfo {
    private String roomNo;
    private long totalRoomPrice;
    private long otherFee;
    private String remark;
    private List<Unit> dailyPrice;
    private List<Unit> otherFeeDetail;
    public String getRoomNo() {
        return roomNo;
    }
    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }
    public long getTotalRoomPrice() {
        return totalRoomPrice;
    }
    public void setTotalRoomPrice(long totalRoomPrice) {
        this.totalRoomPrice = totalRoomPrice;
    }
    public long getOtherFee() {
        return otherFee;
    }
    public void setOtherFee(long otherFee) {
        this.otherFee = otherFee;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public List<Unit> getDailyPrice() {
        return dailyPrice;
    }
    public void setDailyPrice(List<Unit> dailyPrice) {
        this.dailyPrice = dailyPrice;
    }
    public List<Unit> getOtherFeeDetail() {
        return otherFeeDetail;
    }
    public void setOtherFeeDetail(List<Unit> otherFeeDetail) {
        this.otherFeeDetail = otherFeeDetail;
    }
    
}
  
