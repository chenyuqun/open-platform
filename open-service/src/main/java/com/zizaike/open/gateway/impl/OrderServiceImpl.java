/**  
 * Project Name:open-service  <br/>
 * File Name:OrderServiceImpl.java  <br/>
 * Package Name:com.zizaike.open.gateway.impl  <br/>
 * Date:2016年2月20日上午9:51:02  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
 */

package com.zizaike.open.gateway.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zizaike.core.common.util.http.HttpProxyUtil;
import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.core.framework.exception.open.ErrorCodeFields;
import com.zizaike.entity.open.alibaba.request.BookRQRequest;
import com.zizaike.entity.order.request.BookOrderRequest;
import com.zizaike.entity.order.request.CancelOrderRequest;
import com.zizaike.entity.order.request.ValidateOrderRequest;
import com.zizaike.open.gateway.OrderService;
import com.zizaike.entity.order.request.QueryStatusOrderRequest;
/**
 * ClassName:OrderServiceImpl <br/>
 * Function: 订单服务. <br/>
 * Date: 2016年2月20日 上午9:51:02 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 * @see
 */
@Service
public class OrderServiceImpl implements OrderService {
    protected final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Value("${zizaike.open.alitrip.host}")
    private String alitripHost;
    @Autowired
    private HttpProxyUtil httpProxy;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat simpleDateFormatAccurate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public JSONObject validateRQ(ValidateOrderRequest validateOrderRequest) throws ZZKServiceException {
        if (validateOrderRequest == null) {
            throw new IllegalParamterException("validateOrderRequest is null");
        }
        if(StringUtils.isEmpty(validateOrderRequest.getRoomTypeId())){
            throw new IllegalParamterException("roomTypeId is null");
        }
        if(validateOrderRequest.getRoomNum()==0){
            throw new IllegalParamterException("roomNum is 0");
        }
        if(validateOrderRequest.getCheckIn()==null){
            throw new IllegalParamterException("checkIn is null");
        }
        if(validateOrderRequest.getCheckOut()==null){
            throw new IllegalParamterException("checkOut is null");
        }
        if(validateOrderRequest.getPaymentType()!=1){
            throw new IllegalParamterException("paymentType is not 1");
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("roomTypeId", validateOrderRequest.getRoomTypeId());
        map.put("openHotelId", validateOrderRequest.getOpenHotelId());
        map.put("openRatePlanId", validateOrderRequest.getOpenRatePlanId().toString());
        map.put("ratePlanCode", validateOrderRequest.getRatePlanCode());
        map.put("openGid", validateOrderRequest.getOpenGid().toString());
        map.put("checkIn", simpleDateFormat.format(validateOrderRequest.getCheckIn()));
        map.put("checkOut", simpleDateFormat.format(validateOrderRequest.getCheckOut()));
        map.put("roomNum", validateOrderRequest.getRoomNum().toString());
        map.put("paymentType", validateOrderRequest.getPaymentType().toString());
        map.put("extensions", validateOrderRequest.getExtensions());
        JSONObject result = null;
        try {
            result = httpProxy.httpGet(alitripHost + "validateRQ", map);
        } catch (IOException e) {
            e.printStackTrace();
            LOG.error("validateRQ IOException {}", e.toString());
            throw new ZZKServiceException(ErrorCodeFields.NETWORK_ERROR);
        }
        return result;
    }
    
    @Override
    public JSONObject bookRQ(BookOrderRequest bookOrderRequest) throws ZZKServiceException {
        
        if (bookOrderRequest == null) {
            throw new IllegalParamterException("bookOrderRequest is null");
        }
        if(StringUtils.isEmpty(bookOrderRequest.getRoomTypeId())){
            throw new IllegalParamterException("roomTypeId is null");
        }
        if(bookOrderRequest.getRoomNum()==0){
            throw new IllegalParamterException("roomNum is 0");
        }
        if(bookOrderRequest.getCheckIn()==null){
            throw new IllegalParamterException("checkIn is null");
        }
        if(bookOrderRequest.getCheckOut()==null){
            throw new IllegalParamterException("checkOut is null");
        }
        if(bookOrderRequest.getPaymentType()!=1){
            throw new IllegalParamterException("paymentType is not 1");
        }
        Map<String, String> map = new HashMap<String, String>();
      map.put("openOrderId", Long.toString(bookOrderRequest.getOpenOrderId()));
      map.put("openHotelId", Long.toString(bookOrderRequest.getOpenHotelId()));
      map.put("hotelId", bookOrderRequest.getHotelId());
      map.put("openRoomTypeId", Long.toString(bookOrderRequest.getOpenRoomTypeId()));
      map.put("roomTypeId", bookOrderRequest.getRoomTypeId());
      map.put("openRatePlanId", Long.toString(bookOrderRequest.getOpenRatePlanId()));
      map.put("ratePlanCode", bookOrderRequest.getRatePlanCode());
      map.put("openGid", Long.toString(bookOrderRequest.getOpenGid()));
      map.put("checkIn", simpleDateFormat.format(bookOrderRequest.getCheckIn()));
      map.put("checkOut", simpleDateFormat.format(bookOrderRequest.getCheckOut()));
      map.put("hourRent", bookOrderRequest.getHourRent());
      map.put("earliestArriveTime", simpleDateFormatAccurate.format(bookOrderRequest.getEarliestArriveTime()));
      map.put("latestArriveTime", simpleDateFormatAccurate.format(bookOrderRequest.getLatestArriveTime()));
      map.put("roomNum", Integer.toString(bookOrderRequest.getRoomNum()));
      map.put("totalPrice", Long.toString(bookOrderRequest.getTotalPrice()));
      map.put("sellerDiscount", Long.toString(bookOrderRequest.getSellerDiscount()));
      map.put("alitripDiscount", Long.toString(bookOrderRequest.getAlitripDiscount()));
      map.put("currency", bookOrderRequest.getCurrency());
      map.put("paymentType", Integer.toString(bookOrderRequest.getPaymentType()));
      map.put("contactName", bookOrderRequest.getContactName());
      map.put("contactTel", bookOrderRequest.getContactTel());
      map.put("contactEmail", bookOrderRequest.getContactEmail());
      map.put("dailyInfos", JSON.toJSON(bookOrderRequest.getDailyInfos()).toString());
      map.put("orderGuests", JSON.toJSON(bookOrderRequest.getOrderGuests()).toString());
      map.put("comment", bookOrderRequest.getComment());
      map.put("memberCardNo", bookOrderRequest.getMemberCardNo());
      map.put("guaranteeType", bookOrderRequest.getGuaranteeType());
      map.put("extensions", bookOrderRequest.getExtensions());
      map.put("openTradeNo", bookOrderRequest.getAlipayTradeNo());
      /**
       * zizaike下单人数
       */
      if(null!=(bookOrderRequest.getOrderGuests().getOrderGuests())){
          List<BookRQRequest.OrderGuest> orderGuests= bookOrderRequest.getOrderGuests().getOrderGuests();
          map.put("guestNumber", orderGuests.size()>0?Integer.toString(orderGuests.size()):"1");
      }else{
          map.put("guestNumber","1");
      }
        JSONObject result = null;
        try {
            result = httpProxy.httpGet(alitripHost + "bookRQ", map);
        } catch (IOException e) {
            e.printStackTrace();
            LOG.error("bookRQ IOException {}", e.toString());
            throw new ZZKServiceException(ErrorCodeFields.NETWORK_ERROR);
        }
        return result;
    }
    
    
    @Override
    public JSONObject cancelRQ(CancelOrderRequest cancelOrderRequest) throws ZZKServiceException {
        
        if (cancelOrderRequest == null) {
            throw new IllegalParamterException("cancelOrderRequest is null");
        }
        if(cancelOrderRequest.getOpenOrderId()==0){
            throw new IllegalParamterException("openOrderId is null");
        }
        Map<String,String> map = new HashMap<String, String>();
          map.put("orderId", cancelOrderRequest.getOrderId());
          map.put("openOrderId", Long.toString(cancelOrderRequest.getOpenOrderId()));
          map.put("hotelId", cancelOrderRequest.getHotelId());
          map.put("reason", cancelOrderRequest.getReason());
          map.put("hardCancel", cancelOrderRequest.getHardCancel());
        JSONObject result = null;
        try {
            result = httpProxy.httpGet(alitripHost + "cancelRQ", map);
        } catch (IOException e) {
            e.printStackTrace();
            LOG.error("bookRQ IOException {}", e.toString());
            throw new ZZKServiceException(ErrorCodeFields.NETWORK_ERROR);
        }
        return result;
    }
    
    @Override
    public JSONObject aueryStatusOrder(QueryStatusOrderRequest queryStatusOrderRequest) throws ZZKServiceException {
        if (queryStatusOrderRequest == null) {
            throw new IllegalParamterException("queryStatusOrderRequest is null");
        }
        if(StringUtils.isEmpty(queryStatusOrderRequest.getHotelId())){
            throw new IllegalParamterException("hotelId is null");
        }
        if(StringUtils.isEmpty(queryStatusOrderRequest.getOpenOrderId())){
            throw new IllegalParamterException("openOrderId is null");
        }
        Map<String,String> map = new HashMap<String, String>();
        map.put("orderId", queryStatusOrderRequest.getOrderId());
        map.put("openOrderId", queryStatusOrderRequest.getOpenOrderId());
        map.put("hotelId", queryStatusOrderRequest.getHotelId());
        JSONObject result = null;
        try {
            result=httpProxy.httpGet(alitripHost+"queryStatusRQ", map);
        } catch (IOException e) {
            e.printStackTrace();
            LOG.error("QueryStatusOrder IOException {}", e.toString());
            throw new ZZKServiceException(ErrorCodeFields.NETWORK_ERROR);
            
        }
        return result;
    }
}


