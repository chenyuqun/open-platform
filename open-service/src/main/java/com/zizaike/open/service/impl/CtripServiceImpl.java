/**  
 * Project Name:open-service  <br/>
 * File Name:CtripServiceImpl.java  <br/>
 * Package Name:com.zizaike.open.service.impl  <br/>
 * Date:2016年2月4日下午3:11:16  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.service.impl;  

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.open.RoomTypeMapping;
import com.zizaike.entity.open.User;
import com.zizaike.entity.open.alibaba.response.ResponseData;
import com.zizaike.entity.open.ctrip.BalanceType;
import com.zizaike.entity.open.ctrip.request.DomesticCheckRoomAvailRequest;
import com.zizaike.entity.open.ctrip.response.DomesticCheckRoomAvailResponse;
import com.zizaike.entity.order.request.ValidateOrderRequest;
import com.zizaike.is.open.CtripService;
import com.zizaike.is.open.RoomTypeMappingService;
import com.zizaike.is.open.UserService;
import com.zizaike.open.common.util.XstreamUtil;
import com.zizaike.open.gateway.OrderService;

/**  
 * ClassName:CtripServiceImpl <br/>  
 * Function: 携程服务. <br/>  
 * Date:     2016年2月4日 下午3:11:16 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
@Service
public class CtripServiceImpl implements CtripService {
    protected final Logger LOG = LoggerFactory.getLogger(CtripServiceImpl.class);
    
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private RoomTypeMappingService roomTypeMappingService;
    
    @Override
    public DomesticCheckRoomAvailResponse domesticCheckRoomAvail(
            DomesticCheckRoomAvailRequest domesticCheckRoomAvailRequest) throws ZZKServiceException {
        com.zizaike.entity.open.ctrip.request.DomesticCheckRoomAvailRequest.DomesticCheckRoomAvail domesticCheckRoomAvail=  domesticCheckRoomAvailRequest.getDomesticCheckRoomAvail();
       if(domesticCheckRoomAvail.getBalanceType()!=BalanceType.PP){
           throw new IllegalParamterException("BalanceType is not PP");
       }
       RoomTypeMapping roomTypeMapping= roomTypeMappingService.queryByHotelIdAndOpenRoomTypeId(domesticCheckRoomAvail.getHotel(), domesticCheckRoomAvail.getRoom());
       ValidateOrderRequest validateRQRequest = new ValidateOrderRequest();
       validateRQRequest.setHotelId(roomTypeMapping.getHotelId());
       validateRQRequest.setRoomTypeId(roomTypeMapping.getRoomTypeId());
       validateRQRequest.setCheckIn(domesticCheckRoomAvail.getArrival());
       validateRQRequest.setCheckOut(domesticCheckRoomAvail.getDeparture());
       validateRQRequest.setRoomNum(domesticCheckRoomAvail.getRoomNumber());
       JSONObject result = orderService.validateRQ(validateRQRequest);
        return null;
    }
    @Override
    public String service(String xml) throws ZZKServiceException {
        Document doc = null;
        ResponseData responseData = null;
        try {
            doc = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            e.printStackTrace();  
        }
        Element root = doc.getRootElement();
        checkUser(root);
        String request = root.element("HeaderInfo").element("RequestType").attributeValue("Name");
        switch (request) {
        case "DomesticCheckRoomAvailRequest":
            responseData= domesticCheckRoomAvail((DomesticCheckRoomAvailRequest)XstreamUtil.getXml2Bean(xml, DomesticCheckRoomAvailRequest.class));
            break;
        default:
            break;
        }
        return XstreamUtil.getResponseXml(responseData);
    }
    
    public void checkUser(Element root)throws ZZKServiceException{
        Element authenticationToken = root.element("HeaderInfo").element("Authentication");
        User user = new User();
        user.setUsername(authenticationToken.attributeValue("UserName"));
        user.setPassword(authenticationToken.attributeValue("Password"));
        userService.checkUser(user);
    }

}
  
