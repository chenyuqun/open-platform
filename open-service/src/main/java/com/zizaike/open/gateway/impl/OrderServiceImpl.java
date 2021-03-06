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
import com.zizaike.entity.order.request.BookOrderRequest;
import com.zizaike.entity.order.request.CancelOrderRequest;
import com.zizaike.entity.order.request.OrderGuest;
import com.zizaike.entity.order.request.QueryStatusOrderRequest;
import com.zizaike.entity.order.request.ValidateOrderRequest;
import com.zizaike.open.gateway.OrderService;
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
        if(validateOrderRequest.getOpenChannelType()==null){
            throw new IllegalParamterException("openChannelType is null");
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
        map.put("channelType",validateOrderRequest.getOpenChannelType()+"");
        map.put("roomTypeId", validateOrderRequest.getRoomTypeId());
        map.put("openHotelId", validateOrderRequest.getOpenHotelId());
        if(validateOrderRequest.getOpenRatePlanId()!=null){
            map.put("openRatePlanId", validateOrderRequest.getOpenRatePlanId().toString());
        }
        if(validateOrderRequest.getRatePlanCode()!=null){
            map.put("ratePlanCode", validateOrderRequest.getRatePlanCode());
        }
        if(validateOrderRequest.getOpenGid()!=null){
            map.put("openGid", validateOrderRequest.getOpenGid());
        }
        map.put("checkIn", simpleDateFormat.format(validateOrderRequest.getCheckIn()));
        map.put("checkOut", simpleDateFormat.format(validateOrderRequest.getCheckOut()));
        map.put("roomNum", validateOrderRequest.getRoomNum().toString());
        map.put("paymentType", validateOrderRequest.getPaymentType().toString());
        map.put("extensions", validateOrderRequest.getExtensions());
        JSONObject result = null;
        try {
            result = httpProxy.httpGet(alitripHost + "validateRQ", map);
            LOG.info("validateRQ return{}",result);
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
        if(bookOrderRequest.getOpenChannelType()==null){
            throw new IllegalParamterException("openChannelType is null");
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
      map.put("openOrderId", bookOrderRequest.getOpenChannelType()+bookOrderRequest.getOpenOrderId());
      map.put("openHotelId",  bookOrderRequest.getOpenChannelType()+bookOrderRequest.getOpenHotelId());
      map.put("hotelId", bookOrderRequest.getHotelId());
      map.put("openRoomTypeId", bookOrderRequest.getOpenRoomTypeId());
      map.put("roomTypeId", bookOrderRequest.getRoomTypeId());
      map.put("openRatePlanId", Long.toString(bookOrderRequest.getOpenRatePlanId()));
      map.put("ratePlanCode", bookOrderRequest.getRatePlanCode());
      map.put("openGid", Long.toString(bookOrderRequest.getOpenGid()));
      map.put("checkIn", simpleDateFormat.format(bookOrderRequest.getCheckIn()));
      map.put("checkOut", simpleDateFormat.format(bookOrderRequest.getCheckOut()));
      map.put("hourRent", bookOrderRequest.getHourRent());
        if(bookOrderRequest.getEarliestArriveTime()!=null&&bookOrderRequest.getLatestArriveTime()!=null){
          map.put("earliestArriveTime", simpleDateFormatAccurate.format(bookOrderRequest.getEarliestArriveTime()));
          map.put("latestArriveTime", simpleDateFormatAccurate.format(bookOrderRequest.getLatestArriveTime()));
        }
      map.put("roomNum", Integer.toString(bookOrderRequest.getRoomNum()));
      map.put("totalPrice", Long.toString(bookOrderRequest.getTotalPrice()));
      map.put("sellerDiscount", Long.toString(bookOrderRequest.getSellerDiscount()));
      map.put("alitripDiscount", Long.toString(bookOrderRequest.getOpenDiscount()));
      map.put("currency", bookOrderRequest.getCurrency());
      map.put("paymentType", Integer.toString(bookOrderRequest.getPaymentType()));
      map.put("contactName", bookOrderRequest.getContactName());
      map.put("contactTel", bookOrderRequest.getContactTel());
      map.put("contactEmail", bookOrderRequest.getContactEmail());
        if(bookOrderRequest.getDailyInfos()!=null){
      map.put("dailyInfos", JSON.toJSON(bookOrderRequest.getDailyInfos()).toString());
        }
      map.put("orderGuests", JSON.toJSON(bookOrderRequest.getOrderGuests()).toString());
      map.put("comment", bookOrderRequest.getComment());
      map.put("memberCardNo", bookOrderRequest.getMemberCardNo());
      map.put("guaranteeType", bookOrderRequest.getGuaranteeType());
      map.put("extensions", bookOrderRequest.getExtensions());
      map.put("openTradeNo", bookOrderRequest.getOpenTradeNo());
        /**
         * zizaike下单人数
         */
        if(null!=(bookOrderRequest.getOrderGuests())){
            List<OrderGuest> orderGuests= bookOrderRequest.getOrderGuests();
            map.put("guestNumber", orderGuests.size()>0?Integer.toString(orderGuests.size()):"1");
        }else{
            map.put("guestNumber","1");
        }
        JSONObject result = null;
        try {
            result = httpProxy.httpGet(alitripHost + "bookRQ", map);
            LOG.info("bookRQ openOrderId {} return{}",map.get("openOrderId"),result);
        } catch (IOException e) {
            e.printStackTrace();
            LOG.error("bookRQ openOrderId {} IOException {}", map.get("openOrderId"),e.toString());
            throw new ZZKServiceException(ErrorCodeFields.NETWORK_ERROR);
        }
        return result;
    }
    
    
    @Override
    public JSONObject cancelRQ(CancelOrderRequest cancelOrderRequest) throws ZZKServiceException {
        
        if (cancelOrderRequest == null) {
            throw new IllegalParamterException("cancelOrderRequest is null");
        }
        if(cancelOrderRequest.getOpenChannelType()==null){
            throw new IllegalParamterException("openChannelType is null");
        }
        if(StringUtils.isEmpty(cancelOrderRequest.getOpenOrderId())){
            throw new IllegalParamterException("openOrderId is null");
        }
        Map<String,String> map = new HashMap<String, String>();
          if(StringUtils.isNotEmpty(cancelOrderRequest.getOrderId())){
              map.put("orderId", cancelOrderRequest.getOrderId());
          }
         
          if(StringUtils.isNotEmpty(cancelOrderRequest.getHotelId())){
              map.put("hotelId", cancelOrderRequest.getHotelId());
          }
          if(StringUtils.isNotEmpty(cancelOrderRequest.getReason())){
              map.put("reason", cancelOrderRequest.getReason());
          }
          if(StringUtils.isNotEmpty(cancelOrderRequest.getHardCancel())){
              map.put("hardCancel", cancelOrderRequest.getHardCancel());
          }
          
          map.put("openOrderId",  cancelOrderRequest.getOpenChannelType()+cancelOrderRequest.getOpenOrderId());
        JSONObject result = null;
        try {
            result = httpProxy.httpGet(alitripHost + "cancelRQ", map);
            LOG.info("cancelRQ openOrderId {} ,return{}",map.get("openOrderId"),result);
        } catch (IOException e) {
            e.printStackTrace();
            LOG.error("cancelRQ openOrderId {} , IOException {}",map.get("openOrderId"), e.toString());
            throw new ZZKServiceException(ErrorCodeFields.NETWORK_ERROR);
        }
        return result;
    }
    
    @Override
    public JSONObject aueryStatusOrder(QueryStatusOrderRequest queryStatusOrderRequest) throws ZZKServiceException {
        if (queryStatusOrderRequest == null) {
            throw new IllegalParamterException("queryStatusOrderRequest is null");
        }
        if(queryStatusOrderRequest.getOpenChannelType()==null){
            throw new IllegalParamterException("openChannelType is null");
        }
        if(StringUtils.isEmpty(queryStatusOrderRequest.getHotelId())){
            throw new IllegalParamterException("hotelId is null");
        }
        if(StringUtils.isEmpty(queryStatusOrderRequest.getOpenOrderId())){
            throw new IllegalParamterException("openOrderId is null");
        }
        Map<String,String> map = new HashMap<String, String>();
        map.put("orderId", queryStatusOrderRequest.getOrderId());
        map.put("openOrderId", queryStatusOrderRequest.getOpenChannelType()+queryStatusOrderRequest.getOpenOrderId());
        map.put("hotelId", queryStatusOrderRequest.getHotelId());
        JSONObject result = null;
        try {
            result=httpProxy.httpGet(alitripHost+"queryStatusRQ", map);
            LOG.info("aueryStatusOrder openOrderId {} , return{}",map.get("openOrderId"),result);
        } catch (IOException e) {
            e.printStackTrace();
            LOG.error("aueryStatusOrder openOrderId {} , IOException {}",map.get("openOrderId"), e.toString());
            throw new ZZKServiceException(ErrorCodeFields.NETWORK_ERROR);
            
        }
        return result;
    }
}


