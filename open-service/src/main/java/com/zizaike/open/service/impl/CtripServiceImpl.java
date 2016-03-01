/**  
 * Project Name:open-service  <br/>
 * File Name:CtripServiceImpl.java  <br/>
 * Package Name:com.zizaike.open.service.impl  <br/>
 * Date:2016年2月4日下午3:11:16  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
 */

package com.zizaike.open.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zizaike.core.common.util.http.SoapFastUtil;
import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.open.OpenChannelType;
import com.zizaike.entity.open.RoomTypeMapping;
import com.zizaike.entity.open.User;
import com.zizaike.entity.open.alibaba.Data;
import com.zizaike.entity.open.alibaba.RateInventoryPrice;
import com.zizaike.entity.open.alibaba.Rates;
import com.zizaike.entity.open.alibaba.response.ResponseData;
import com.zizaike.entity.open.ctrip.BalanceType;
import com.zizaike.entity.open.ctrip.PriceInfo;
import com.zizaike.entity.open.ctrip.RoomInfoItem;
import com.zizaike.entity.open.ctrip.RoomPrice;
import com.zizaike.entity.open.ctrip.RoomPrices;
import com.zizaike.entity.open.ctrip.SetRoomInfoRequest;
import com.zizaike.entity.open.ctrip.SetRoomPriceItem;
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
    
    @Value("${ctrip.url}")
    private String url;
    @Value("${ctrip.username}")
    private String username;
    @Value("${ctrip.password}")
    private String password;
    private String prefix = "soap_template/ctrip";
    @Autowired
    private SoapFastUtil soapFastUtil;

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
    public void updateRates(Rates object) throws ZZKServiceException{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            // TODO 房价房态
            List<RateInventoryPrice> rateInventoryPriceList=object.getRateInventoryPriceMap();
            List<SetRoomPriceItem> setRoomPriceItems = new ArrayList<SetRoomPriceItem>();
            String hotelID="";
            for(int i=0;i<rateInventoryPriceList.size();i++){
                RateInventoryPrice rateInventoryPrice=new RateInventoryPrice();
                SetRoomPriceItem setRoomPriceItem=new SetRoomPriceItem();
                //房态接口一次只允许更新一个房型
                SetRoomInfoRequest setRoomInfoRequest=new SetRoomInfoRequest();
                rateInventoryPrice=rateInventoryPriceList.get(i);
                //房型               
                RoomTypeMapping roomTypeMapping = roomTypeMappingService.queryByRoomTypeId(
                        rateInventoryPrice.getOutRid());
                setRoomPriceItem.setRoomID(Integer.parseInt(roomTypeMapping.getOpenRoomTypeId()));
                hotelID=roomTypeMapping.getOpenHotelId();
                
                setRoomPriceItem.setCurrency("CNY");
                List<PriceInfo> priceInfos = new ArrayList<PriceInfo>();
                List<RoomInfoItem> roomInfoItems = new ArrayList<RoomInfoItem>();
                //房态房价
                Data data=rateInventoryPrice.getData();
                int firstDay=1;
                for(com.zizaike.entity.open.alibaba.InventoryPrice inventoryPrice:data.getInventoryPrice()){
                    PriceInfo priceInfo=new PriceInfo();
                    RoomInfoItem roomInfoItem=new RoomInfoItem();
                    if(firstDay==1){
                        setRoomPriceItem.setStartDate(sdf.format(inventoryPrice.getDate()));
                        firstDay=0;
                    }
                    priceInfo.setAmountAfterTaxFee(inventoryPrice.getPrice());
                    priceInfo.setAmountBeforeTaxFee(inventoryPrice.getPrice());
                    /**
                     * 适用于地区(适用人群) 默认111111
                     */
                    priceInfo.setApplicability("111111");
                    /**
                     * PP  预付， FG 现付，PKG包价
                     */
                    priceInfo.setBlanceType("PP");
                    /**
                     * 早餐
                     */
                    //priceInfo.setBreakfast(breakfast);
                    priceInfo.setCostAmountAfterTaxFee(inventoryPrice.getPrice());
                    priceInfo.setCostAmountBeforeTaxFee(inventoryPrice.getPrice());
                    /**
                     * 连住天数，暂不用，默认为1       
                     */
                    priceInfo.setDay(1);
                   /**
                    * 不需要变价审核设置： Sell， Cost，Both；需 要变价审核设 置：ACost， ASell，ABoth
                    */
                    priceInfo.setPriceType("Sell");
                    /**
                     * 连住天数，暂不用，默认为1
                     */
                    priceInfo.setStays(1);
                    priceInfos.add(priceInfo);
                    
                    inventoryPrice.getStatus();
                    inventoryPrice.getQuota();
                    //现付使用
                    //roomInfoItem.setAllNeedGuarantee(allNeedGuarantee);
                    /**
                     * 是否同时修改房态默认值(F-否，T-是)
                     */
                    roomInfoItem.setChangeDefault("F");
                    /**
                     * 住店控制(C-进店统计,S-住店统计)
                     */
                    roomInfoItem.setCheckType("S");
                    /**
                     * 预付房型扣款类型(C-全部扣款,F-首日扣款)
                     */
                    roomInfoItem.setDeductType("C");
                    /**
                     * 担保
                     */
//                    roomInfoItem.setGuarantee(guarantee);
//                    roomInfoItem.setGuaranteeLCT(guaranteeLCT);
                    /**
                     * 保留时间(9999-无规定,1000-10:00,...,2330-23:30)
                     */
                    roomInfoItem.setHoldDeadline(9999);
                    /**
                     * 订单最晚预订时间，计算公式同ReserveTime
                     */
                    roomInfoItem.setLateReserveTime(6);
                    /**
                     * 备注
                     */
                    roomInfoItem.setNote("");
                    /**
                     * 预付最晚取消时间 【预付产品使用】小时数，计算公式同ReserveTime，不可 取消设置为23988
                     */
                    roomInfoItem.setPrepayLCT(720);
                    /**
                     * 国内推荐级别(5-特推,4-主推,3-可推,2-可订,1-不推,0-不可订
                     */
                    roomInfoItem.setRecommend(2);
                    /**
                     * 国外推荐级别(5-特推,4-主推,3-可推,2-可订,1-不推,0-不可订
                     */
                    roomInfoItem.setRecommendIntl(2);
                    /**
                     * 保留房用最晚预订时间，计算公式：订单入住日24点向后推算，如果
                     * 设置为6，则当天保留房最晚预订时间为24点向后倒推6小时，为18点。 
                     * 如果设置为36，则保留房最晚预订时间为24点向后倒推36小时，表示入住日前一 天的中午12点
                     */
                    roomInfoItem.setReserveTime(6);
                    /**
                     * 保留房可否恢复(T-可，F-不可)
                     */
                    roomInfoItem.setRestorable("F");
                    /**
                     * 房型礼品ID，默认值-2147483648
                     */
                    roomInfoItem.setRoomGiftID(-2147483648);
                    /**
                     * 临时保留房总数
                     */
                    roomInfoItem.setRoomsInv(2);
                    /**
                     * 房态(N-满房 ,G-良好)
                     */
                    roomInfoItem.setRoomStatus("G");
                    /**
                     * 连住，暂不用，默认为1
                     */
                    roomInfoItem.setStays(1);
                    /**
                     * 预订限制(2-超时担保,3-一律担保,9999-无限制)
                     */
                    roomInfoItem.setUserLimited(2);
                    roomInfoItems.add(roomInfoItem);
                }
                Date startDay = null;
                try {
                    startDay = sdf.parse(setRoomPriceItem.getStartDate());
                } catch (ParseException e) {
                      
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                    
                }
                Calendar rightNow = Calendar.getInstance();
                rightNow.setTime(startDay);
                //有多少房态房价结束日期加多少天
                rightNow.add(Calendar.DAY_OF_YEAR,data.getInventoryPrice().size());
                Date endDay=rightNow.getTime();
                setRoomPriceItem.setEndDate(sdf.format(endDay));
                setRoomPriceItem.setPriceInfos(priceInfos);
                setRoomPriceItems.add(setRoomPriceItem);
                //更新房态
                setRoomInfo(roomInfoItems, roomTypeMapping.getOpenRoomTypeId(),sdf.format(startDay),sdf.format(endDay));
            } 
          //更新价格
            setRoomPrice(setRoomPriceItems,hotelID);
           
         
    }

    @Override
    public DomesticCheckRoomAvailResponse domesticCheckRoomAvail(
            DomesticCheckRoomAvailRequest domesticCheckRoomAvailRequest) throws ZZKServiceException {
          
        // TODO Auto-generated method stub  
        return null;
    }
    
    public void setRoomPrice(List<SetRoomPriceItem> setRoomPriceItems,String hotelID){
        /**
         * SetRoomPrice.vm
         */
        String template = "SetRoomPrice.vm";
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        Map pricemap = new HashMap();
        pricemap.put("userName", username);
        pricemap.put("password", password);
        pricemap.put("userId", 204);
        pricemap.put("date", dateString);
        pricemap.put("setRoomPriceItems", setRoomPriceItems);
        pricemap.put("hotelID", hotelID);
        pricemap.put("title", "");
        pricemap.put("submitor", "zizaike");
        pricemap.put("priority", "N");
        try {
            long start = System.currentTimeMillis();
            String xmlStr = soapFastUtil.post(pricemap, prefix, template, url, "");
            System.out.println(xmlStr);
            System.err.println(System.currentTimeMillis() - start);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setRoomInfo(List<RoomInfoItem> roomInfoItems,String roomID,String startDate,String endDate){
        /**
         * SetRoomInfo.vm
         */
        String template = "SetRoomInfo.vm";
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        Map map = new HashMap();
        map.put("userName", username);
        map.put("password", password);
        map.put("userId", 204);
        map.put("date", dateString);
        
        map.put("roomInfoItems", roomInfoItems);
        map.put("roomID", roomID);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("editer", "zizaike");
        try {
            long start = System.currentTimeMillis();
            String xmlStr = soapFastUtil.post(map, prefix, template, url, "");
            System.out.println(xmlStr);
            System.err.println(System.currentTimeMillis() - start);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

