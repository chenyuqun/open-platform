/**  
 * Project Name:open-service  <br/>
 * File Name:CtripServiceImpl.java  <br/>
 * Package Name:com.zizaike.open.service.impl  <br/>
 * Date:2016年2月4日下午3:11:16  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.service.impl;  

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.open.RoomTypeMapping;
import com.zizaike.entity.open.User;
import com.zizaike.entity.open.alibaba.Rates;
import com.zizaike.entity.open.alibaba.response.ResponseData;
import com.zizaike.entity.open.alibaba.response.ValidateRQResponse;
import com.zizaike.entity.open.ctrip.BalanceType;
import com.zizaike.entity.open.ctrip.RoomPrice;
import com.zizaike.entity.open.ctrip.RoomPrices;
import com.zizaike.entity.open.ctrip.request.DomesticCheckRoomAvailReq;
import com.zizaike.entity.open.ctrip.request.DomesticCheckRoomAvailRequest;
import com.zizaike.entity.open.ctrip.response.DomesticCheckRoomAvailResp;
import com.zizaike.entity.open.ctrip.response.DomesticCheckRoomAvailResponse;
import com.zizaike.entity.order.request.ValidateOrderRequest;
import com.zizaike.entity.order.response.InventoryPrice;
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
        DomesticCheckRoomAvailReq domesticCheckRoomAvailReq=  domesticCheckRoomAvailRequest.getDomesticCheckRoomAvail();
       if(domesticCheckRoomAvailReq.getBalanceType()!=BalanceType.PP){
           throw new IllegalParamterException("BalanceType is not PP");
       }
       RoomTypeMapping roomTypeMapping= roomTypeMappingService.queryByHotelIdAndOpenRoomTypeId(domesticCheckRoomAvailReq.getHotel(), domesticCheckRoomAvailReq.getRoom());
       ValidateOrderRequest validateRQRequest = new ValidateOrderRequest();
       validateRQRequest.setHotelId(roomTypeMapping.getHotelId());
       validateRQRequest.setRoomTypeId(roomTypeMapping.getRoomTypeId());
       validateRQRequest.setCheckIn(domesticCheckRoomAvailReq.getArrival());
       validateRQRequest.setCheckOut(domesticCheckRoomAvailReq.getDeparture());
       validateRQRequest.setRoomNum(domesticCheckRoomAvailReq.getRoomNumber());
       JSONObject result = orderService.validateRQ(validateRQRequest);
       DomesticCheckRoomAvailResponse domesticCheckRoomAvailResponse = new DomesticCheckRoomAvailResponse();
       DomesticCheckRoomAvailResp domesticCheckRoomAvailResp = new DomesticCheckRoomAvailResp();
       ValidateRQResponse validateRQResponse = new ValidateRQResponse();
       Integer isBookable = 1;//可订
       RoomPrices roomPrices = new RoomPrices();
       List<RoomPrice> roomPriceList = new ArrayList<RoomPrice>();
       Float countPrice = (float) 0;
       if(result.getString("resultCode").equals("200")){
           List<InventoryPrice> inventoryPriceList = JSON.parseArray(result.getJSONObject("info").getString("inventoryPrice"),InventoryPrice.class);
           for(int i=0;i<inventoryPriceList.size();i++){
               RoomPrice roomPrice = new RoomPrice();
               InventoryPrice inventory=inventoryPriceList.get(i);
               if(inventory.getQuota()<validateRQRequest.getRoomNum()){
                   isBookable = 0;//不可订
               };
               roomPrice.setBreakFast(inventory.getBreakFast()?inventory.getMaxPerson():0);
               roomPrice.setCost(Float.valueOf(inventory.getPrice()+"")/100);
               countPrice+=Float.valueOf(inventory.getPrice()+"")/100;
               roomPrice.setEffectDate(inventory.getDate());
               roomPriceList.add(roomPrice);
           }
           roomPrices.setRoomPrices(roomPriceList);
           domesticCheckRoomAvailResp.setRoomPrices(roomPrices);
           domesticCheckRoomAvailResp.setInterFaceAmount(countPrice);
       }
       domesticCheckRoomAvailResp.setRoom(domesticCheckRoomAvailReq.getRoom());
       domesticCheckRoomAvailResp.setIsBookable(isBookable);
        return domesticCheckRoomAvailResponse;
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
    @Override
    public void updateRates(Rates object) {
            // TODO 房价房态
            object.getRateInventoryPriceMap();
        
        
        
    }
    

}
  
