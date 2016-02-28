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
import com.zizaike.entity.open.OpenChannelType;
import com.zizaike.entity.open.RoomTypeMapping;
import com.zizaike.entity.open.User;
import com.zizaike.entity.open.alibaba.Rates;
import com.zizaike.entity.open.alibaba.response.ResponseData;
import com.zizaike.entity.open.ctrip.BalanceType;
import com.zizaike.entity.open.ctrip.RoomPrice;
import com.zizaike.entity.open.ctrip.RoomPrices;
import com.zizaike.entity.open.ctrip.request.DomesticCancelHotelOrderReq;
import com.zizaike.entity.open.ctrip.request.DomesticCancelHotelOrderRequest;
import com.zizaike.entity.open.ctrip.request.DomesticCheckRoomAvailReq;
import com.zizaike.entity.open.ctrip.request.DomesticCheckRoomAvailRequest;
import com.zizaike.entity.open.ctrip.request.DomesticSubmitNewHotelOrderReq;
import com.zizaike.entity.open.ctrip.request.DomesticSubmitNewHotelOrderRequest;
import com.zizaike.entity.open.ctrip.request.GuestEntity;
import com.zizaike.entity.open.ctrip.response.DomesticCancelHotelOrderResp;
import com.zizaike.entity.open.ctrip.response.DomesticCancelHotelOrderResponse;
import com.zizaike.entity.open.ctrip.response.DomesticCheckRoomAvailResp;
import com.zizaike.entity.open.ctrip.response.DomesticCheckRoomAvailResponse;
import com.zizaike.entity.open.ctrip.response.DomesticSubmitNewHotelOrderResp;
import com.zizaike.entity.open.ctrip.response.DomesticSubmitNewHotelOrderResponse;
import com.zizaike.entity.order.request.BookOrderRequest;
import com.zizaike.entity.order.request.CancelOrderRequest;
import com.zizaike.entity.order.request.DailyInfo;
import com.zizaike.entity.order.request.OrderGuest;
import com.zizaike.entity.order.request.ValidateOrderRequest;
import com.zizaike.entity.order.response.InventoryPrice;
import com.zizaike.entity.order.response.OrderStatus;
import com.zizaike.is.open.CtripService;
import com.zizaike.is.open.RoomTypeMappingService;
import com.zizaike.is.open.UserService;
import com.zizaike.open.common.util.XstreamUtil;
import com.zizaike.open.gateway.OrderService;

/**
 * ClassName:CtripServiceImpl <br/>
 * Function: 携程服务. <br/>
 * Date: 2016年2月4日 下午3:11:16 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
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

    /**
     * 
     * domesticCheckRoomAvail:试单. <br/>
     * 
     * @author snow.zhang
     * @param domesticCheckRoomAvailRequest
     * @return
     * @throws ZZKServiceException
     * @since JDK 1.7
     */
    private DomesticCheckRoomAvailResp domesticCheckRoomAvail(DomesticCheckRoomAvailReq domesticCheckRoomAvailReq)
            throws ZZKServiceException {
        DomesticCheckRoomAvailRequest domesticCheckRoomAvailRequest = domesticCheckRoomAvailReq
                .getDomesticCheckRoomAvail();
        if (domesticCheckRoomAvailRequest.getBalanceType() != BalanceType.PP) {
            throw new IllegalParamterException("BalanceType is not PP");
        }
        RoomTypeMapping roomTypeMapping = roomTypeMappingService.queryByHotelIdAndOpenRoomTypeId(
                domesticCheckRoomAvailRequest.getHotel(), domesticCheckRoomAvailRequest.getRoom());
        ValidateOrderRequest validateRQRequest = new ValidateOrderRequest();
        validateRQRequest.setHotelId(roomTypeMapping.getHotelId());
        validateRQRequest.setRoomTypeId(roomTypeMapping.getRoomTypeId());
        validateRQRequest.setCheckIn(domesticCheckRoomAvailRequest.getArrival());
        validateRQRequest.setCheckOut(domesticCheckRoomAvailRequest.getDeparture());
        validateRQRequest.setRoomNum(domesticCheckRoomAvailRequest.getRoomNumber());
        validateRQRequest.setPaymentType(1);
        JSONObject result = orderService.validateRQ(validateRQRequest);
        DomesticCheckRoomAvailResp domesticCheckRoomAvailResp = new DomesticCheckRoomAvailResp();
        DomesticCheckRoomAvailResponse domesticCheckRoomAvailResponse = new DomesticCheckRoomAvailResponse();
        Integer isBookable = 1;// 可订
        RoomPrices roomPrices = new RoomPrices();
        List<RoomPrice> roomPriceList = new ArrayList<RoomPrice>();
        Float countPrice = (float) 0;
        if (result.getString("resultCode").equals("200")) {
            List<InventoryPrice> inventoryPriceList = JSON.parseArray(
                    result.getJSONObject("info").getString("inventoryPrice"), InventoryPrice.class);
            for (int i = 0; i < inventoryPriceList.size(); i++) {
                RoomPrice roomPrice = new RoomPrice();
                InventoryPrice inventory = inventoryPriceList.get(i);
                if (inventory.getQuota() < validateRQRequest.getRoomNum()) {
                    isBookable = 0;// 不可订
                }
                ;
                roomPrice.setBreakFast(inventory.getBreakFast() ? inventory.getMaxPerson() : 0);
                roomPrice.setCost(Float.valueOf(inventory.getPrice() + "") / 100);
                countPrice += Float.valueOf(inventory.getPrice() + "") / 100;
                roomPrice.setEffectDate(inventory.getDate());
                roomPriceList.add(roomPrice);
            }
            roomPrices.setRoomPrices(roomPriceList);
            domesticCheckRoomAvailResponse.setRoomPrices(roomPrices);
            domesticCheckRoomAvailResponse.setInterFaceAmount(countPrice);
        } else {
            isBookable = 0;
        }
        domesticCheckRoomAvailResponse.setRoom(domesticCheckRoomAvailRequest.getRoom());
        domesticCheckRoomAvailResponse.setIsBookable(isBookable);
        domesticCheckRoomAvailResp.setDomesticCheckRoomAvailResponse(domesticCheckRoomAvailResponse);
        return domesticCheckRoomAvailResp;
    }

    /**
     * 
     * DomesticSubmitNewHotelOrder:新订订单. <br/>
     * 
     * @author snow.zhang
     * @param domesticSubmitNewHotelOrderRequest
     * @return
     * @since JDK 1.7
     */
    private DomesticSubmitNewHotelOrderResp domesticSubmitNewHotelOrder(
            DomesticSubmitNewHotelOrderReq domesticSubmitNewHotelOrderReq) throws ZZKServiceException{
        DomesticSubmitNewHotelOrderRequest domesticSubmitNewHotelOrderReqeust = domesticSubmitNewHotelOrderReq
                .getDomesticSubmitNewHotelOrderReqeust();
        RoomTypeMapping roomTypeMapping = null;
        try {
            roomTypeMapping = roomTypeMappingService.queryByHotelIdAndOpenRoomTypeId(
                    domesticSubmitNewHotelOrderReqeust.getHotel(), domesticSubmitNewHotelOrderReqeust.getRoom());
        } catch (ZZKServiceException e) {
            LOG.error("domesticSubmitNewHotelOrder queryByHotelIdAndOpenRoomTypeId Exception{}", e);
            e.printStackTrace();
        }
        if(domesticSubmitNewHotelOrderReqeust.getBalanceType()!=BalanceType.PP){
            throw new IllegalParamterException("domesticSubmitNewHotelOrder balanceType is not PP");
        }
        BookOrderRequest bookOrderRequest = new BookOrderRequest();
        bookOrderRequest.setOpenChannelType(OpenChannelType.CTRIP);
        bookOrderRequest.setOpenOrderId(domesticSubmitNewHotelOrderReqeust.getOrderID());
        bookOrderRequest.setOpenHotelId(domesticSubmitNewHotelOrderReqeust.getHotel());
        bookOrderRequest.setOpenRoomTypeId(domesticSubmitNewHotelOrderReqeust.getRoom());
        bookOrderRequest.setHotelId(roomTypeMapping.getHotelId());
        bookOrderRequest.setRoomTypeId(roomTypeMapping.getRoomTypeId());
        bookOrderRequest.setCheckIn(domesticSubmitNewHotelOrderReqeust.getArrival());
        bookOrderRequest.setCheckOut(domesticSubmitNewHotelOrderReqeust.getDeparture());
        bookOrderRequest.setEarliestArriveTime(domesticSubmitNewHotelOrderReqeust.getEarlyArrivalTime());
        bookOrderRequest.setLatestArriveTime(domesticSubmitNewHotelOrderReqeust.getLastArrivalTime());
        bookOrderRequest.setRoomNum(Integer.parseInt(domesticSubmitNewHotelOrderReqeust.getQuantity()));
        //价格
        bookOrderRequest.setTotalPrice(Long.parseLong(domesticSubmitNewHotelOrderReqeust.getCNYCostAmount()*100+""));//元转成分
        bookOrderRequest.setCurrency("CNY");//只支持人民币
        bookOrderRequest.setPaymentType(1);
        bookOrderRequest.setContactTel(domesticSubmitNewHotelOrderReqeust.getMobilePhone());
        List<OrderGuest> orderGuests = new ArrayList<OrderGuest>();
       
        List<GuestEntity> guests = domesticSubmitNewHotelOrderReqeust.getGuests().getGuests();
        for (GuestEntity guestEntity : guests) {
            OrderGuest orderGuest = new OrderGuest();
            orderGuest.setName(guestEntity.getFirstName()+" "+guestEntity.getLastName());
            orderGuests.add(orderGuest);
        }
        bookOrderRequest.setOrderGuests(orderGuests);
        List<DailyInfo> dailyInfos = new ArrayList<DailyInfo>(); 
        for (RoomPrice roomPrice : domesticSubmitNewHotelOrderReqeust.getRoomPrices().getRoomPrices()) {
            DailyInfo dailyInfo = new DailyInfo();
            dailyInfo.setDay(roomPrice.getEffectDate());
            dailyInfo.setPrice(Long.parseLong(roomPrice.getCNYCost()*100+""));
            dailyInfos.add(dailyInfo);
        }
        bookOrderRequest.setDailyInfos(dailyInfos);
        JSONObject result = orderService.bookRQ(bookOrderRequest);
        /**
         * 200是success 201是网站端口下单成功[非速订]
         */
        DomesticSubmitNewHotelOrderResp domesticSubmitNewHotelOrderResp = new DomesticSubmitNewHotelOrderResp();
        DomesticSubmitNewHotelOrderResponse domesticSubmitNewHotelOrderResponse = new DomesticSubmitNewHotelOrderResponse();
        if(result.getString("resultCode").equals("200")||result.getString("resultCode").equals("201")){           
            domesticSubmitNewHotelOrderResponse.setHotelConfirmNo(result.getJSONObject("info").getString("orderId"));
            domesticSubmitNewHotelOrderResponse.setOrderStatus(OrderStatus.HOTEL_CONFIRM_SUCCESS);
        }
        domesticSubmitNewHotelOrderResp.setDomesticSubmitNewHotelOrderResponse(domesticSubmitNewHotelOrderResponse);
        return domesticSubmitNewHotelOrderResp;
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
            responseData = domesticCheckRoomAvail((DomesticCheckRoomAvailReq) XstreamUtil.getXml2Bean(xml,
                    DomesticCheckRoomAvailReq.class));
            break;
        case "DomesticSubmitNewHotelOrderRequest":
            responseData = domesticSubmitNewHotelOrder((DomesticSubmitNewHotelOrderReq) XstreamUtil.getXml2Bean(xml,
                    DomesticSubmitNewHotelOrderReq.class));
            break;
        case "DomesticCancelHotelOrderRequest":
            responseData = domesticCancelHotelOrder((DomesticCancelHotelOrderReq) XstreamUtil.getXml2Bean(xml,
                    DomesticCancelHotelOrderReq.class));
            break;
        default:
            break;
        }
        return XstreamUtil.getResponseXml(responseData);
    }

    private DomesticCancelHotelOrderResp domesticCancelHotelOrder(DomesticCancelHotelOrderReq domesticCancelHotelOrderReq)throws ZZKServiceException {
        DomesticCancelHotelOrderRequest domesticCancelHotelOrderRequest = domesticCancelHotelOrderReq.getDomesticCancelHotelOrderRequest();
        CancelOrderRequest cancelOrderRequest = new CancelOrderRequest();
        cancelOrderRequest.setOpenChannelType(OpenChannelType.CTRIP);
        cancelOrderRequest.setOpenOrderId(domesticCancelHotelOrderRequest.getOrderID());
        cancelOrderRequest.setHotelId(domesticCancelHotelOrderRequest.getHotel());
        JSONObject result = orderService.cancelRQ(cancelOrderRequest);
        DomesticCancelHotelOrderResponse domesticCancelHotelOrderResponse = new DomesticCancelHotelOrderResponse();
        if(result.getString("resultCode").equals("200")){
            domesticCancelHotelOrderResponse.setOrderID(result.getJSONObject("info").getString("orderId"));
            domesticCancelHotelOrderResponse.setInterFaceConfirmNO(domesticCancelHotelOrderRequest.getInterFaceConfirmNO());
            domesticCancelHotelOrderResponse.setOrderStatus(OrderStatus.HOTEL_CONFIRM_SUCCESS);
            domesticCancelHotelOrderResponse.setReturnCode("0");
            domesticCancelHotelOrderResponse.setReturnDescript("取消成功");
        }
        DomesticCancelHotelOrderResp domesticCancelHotelOrderResp = new DomesticCancelHotelOrderResp();
        domesticCancelHotelOrderResp.setDomesticCancelHotelOrderResponse(domesticCancelHotelOrderResponse);
        return domesticCancelHotelOrderResp;
    }

    public void checkUser(Element root) throws ZZKServiceException {
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

    @Override
    public DomesticCheckRoomAvailResponse domesticCheckRoomAvail(
            DomesticCheckRoomAvailRequest domesticCheckRoomAvailRequest) throws ZZKServiceException {
          
        // TODO Auto-generated method stub  
        return null;
    }

}

