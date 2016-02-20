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
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zizaike.core.common.util.http.HttpProxyUtil;
import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.core.framework.exception.open.ErrorCodeFields;
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

}
