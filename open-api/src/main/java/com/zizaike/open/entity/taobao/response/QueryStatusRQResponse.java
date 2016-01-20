/**  
 * Project Name:open-api  <br/>
 * File Name:QueryStatusRQResponse.java  <br/>
 * Package Name:com.zizaike.open.entity.taobao.response  <br/>
 * Date:2016年1月15日下午2:14:32  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.entity.taobao.response;  

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**  
 * ClassName:QueryStatusRQResponse <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2016年1月15日 下午2:14:32 <br/>  
 * @author   alex  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
@XStreamAlias("Result")
public class QueryStatusRQResponse {
    @XStreamAlias("TaoBaoOrderId")
    private Long taoBaoOrderId;
    @XStreamAlias("OrderId")
    private String orderId;
    @XStreamAlias("Status")
    private String status;
    @XStreamAlias("OrderInfo")
    private OrderInfo orderInfo;
    @XStreamAlias("BillInfo")
    private BillInfo billInfo;
    public Long getTaoBaoOrderId() {
        return taoBaoOrderId;
    }
    public void setTaoBaoOrderId(Long taoBaoOrderId) {
        this.taoBaoOrderId = taoBaoOrderId;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public OrderInfo getOrderInfo() {
        return orderInfo;
    }
    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }
    public BillInfo getBillInfo() {
        return billInfo;
    }
    public void setBillInfo(BillInfo billInfo) {
        this.billInfo = billInfo;
    }
    
    
}
  
