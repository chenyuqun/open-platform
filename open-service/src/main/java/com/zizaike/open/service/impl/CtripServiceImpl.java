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
import java.util.List;
import java.util.Map;

import com.zizaike.entity.open.*;
import org.apache.commons.lang.StringUtils;
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
import com.zizaike.core.framework.exception.open.OpenReturnException;
import com.zizaike.entity.open.alibaba.Data;
import com.zizaike.entity.open.alibaba.RateInventoryPrice;
import com.zizaike.entity.open.alibaba.Rates;
import com.zizaike.entity.open.alibaba.response.ResponseData;
import com.zizaike.entity.open.ctrip.BalanceType;
import com.zizaike.entity.open.ctrip.GetHotelInfoResponse;
import com.zizaike.entity.open.ctrip.GetMappingInfoResponseList;
import com.zizaike.entity.open.ctrip.HotelGroupInterfaceRoomTypeEntity;
import com.zizaike.entity.open.ctrip.MappingType;
import com.zizaike.entity.open.ctrip.PriceInfo;
import com.zizaike.entity.open.ctrip.RoomInfoItem;
import com.zizaike.entity.open.ctrip.RoomPrice;
import com.zizaike.entity.open.ctrip.RoomPrices;
import com.zizaike.entity.open.ctrip.SetMappingOperateType;
import com.zizaike.entity.open.ctrip.SetRoomInfoRequest;
import com.zizaike.entity.open.ctrip.SetRoomPriceItem;
import com.zizaike.entity.open.ctrip.request.DomesticCancelHotelOrderReq;
import com.zizaike.entity.open.ctrip.request.DomesticCancelHotelOrderRequest;
import com.zizaike.entity.open.ctrip.request.DomesticCheckRoomAvailReq;
import com.zizaike.entity.open.ctrip.request.DomesticCheckRoomAvailRequest;
import com.zizaike.entity.open.ctrip.request.DomesticSubmitNewHotelOrderReq;
import com.zizaike.entity.open.ctrip.request.DomesticSubmitNewHotelOrderRequest;
import com.zizaike.entity.open.ctrip.request.GuestEntity;
import com.zizaike.entity.open.ctrip.response.AvailRoomQuantity;
import com.zizaike.entity.open.ctrip.response.AvailRoomQuantitys;
import com.zizaike.entity.open.ctrip.response.DomesticCancelHotelOrderResp;
import com.zizaike.entity.open.ctrip.response.DomesticCancelHotelOrderResponse;
import com.zizaike.entity.open.ctrip.response.DomesticCheckRoomAvailResp;
import com.zizaike.entity.open.ctrip.response.DomesticCheckRoomAvailResponse;
import com.zizaike.entity.open.ctrip.response.DomesticSubmitNewHotelOrderResp;
import com.zizaike.entity.open.ctrip.response.DomesticSubmitNewHotelOrderResponse;
import com.zizaike.entity.open.ctrip.vo.HotelGroupInterfaceRoomTypeVo;
import com.zizaike.entity.open.ctrip.vo.MappingInfoVo;
import com.zizaike.entity.open.ctrip.vo.SetMappingInfoVo;
import com.zizaike.entity.order.request.BookOrderRequest;
import com.zizaike.entity.order.request.CancelOrderRequest;
import com.zizaike.entity.order.request.DailyInfo;
import com.zizaike.entity.order.request.OrderGuest;
import com.zizaike.entity.order.request.ValidateOrderRequest;
import com.zizaike.entity.order.response.InventoryPrice;
import com.zizaike.entity.order.response.OrderStatus;
import com.zizaike.is.open.BaseInfoService;
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
    @Autowired
    private BaseInfoService baseInfoService;
    
    @Value("${ctrip.userId}")
    private String userId;
    @Value("${ctrip.supplierID}")
    private String supplierID;
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
    private DomesticCheckRoomAvailResp domesticCheckRoomAvail(DomesticCheckRoomAvailRequest domesticCheckRoomAvailRequest)
            throws ZZKServiceException {
        if (domesticCheckRoomAvailRequest.getBalanceType() != BalanceType.PP) {
            throw new IllegalParamterException("BalanceType is not PP");
        }
        RoomTypeMapping roomTypeMapping = roomTypeMappingService.queryByHotelIdAndOpenRoomTypeId(
                domesticCheckRoomAvailRequest.getHotel(), domesticCheckRoomAvailRequest.getRoom(),OpenChannelType.CTRIP);
        ValidateOrderRequest validateRQRequest = new ValidateOrderRequest();
        validateRQRequest.setHotelId(roomTypeMapping.getHotelId());
        validateRQRequest.setRoomTypeId(roomTypeMapping.getRoomTypeId());
        validateRQRequest.setCheckIn(domesticCheckRoomAvailRequest.getArrival());
        validateRQRequest.setCheckOut(domesticCheckRoomAvailRequest.getDeparture());
        validateRQRequest.setRoomNum(domesticCheckRoomAvailRequest.getRoomNumber());
        validateRQRequest.setPaymentType(1);
        validateRQRequest.setOpenChannelType(OpenChannelType.CTRIP);
        JSONObject result = orderService.validateRQ(validateRQRequest);
        DomesticCheckRoomAvailResp domesticCheckRoomAvailResp = new DomesticCheckRoomAvailResp();
        DomesticCheckRoomAvailResponse domesticCheckRoomAvailResponse = new DomesticCheckRoomAvailResponse();
        Integer isBookable = 1;// 可订
        RoomPrices roomPrices = new RoomPrices();
        List<RoomPrice> roomPriceList = new ArrayList<RoomPrice>();
        AvailRoomQuantitys availRoomQuantitys = new AvailRoomQuantitys();
        List<AvailRoomQuantity> availRoomQuantityList = new ArrayList<AvailRoomQuantity>();
        Float countPrice = (float) 0;
        if (result.getString("resultCode").equals("200")) {
            List<InventoryPrice> inventoryPriceList = JSON.parseArray(
                    result.getJSONObject("info").getString("inventoryPrice"), InventoryPrice.class);
            for (int i = 0; i < inventoryPriceList.size(); i++) {
                InventoryPrice inventory = inventoryPriceList.get(i);
                if (inventory.getQuota() < validateRQRequest.getRoomNum()) {
                    isBookable = 0;// 不可订
                }
                RoomPrice roomPrice = new RoomPrice();
                roomPrice.setBreakFast(inventory.getBreakFast() ? inventory.getMaxPerson() : 0);
                roomPrice.setCost(inventory.getPrice() / 100);
                countPrice += Float.valueOf(inventory.getPrice() + "") / 100;
                roomPrice.setEffectDate(new Date(inventory.getDate().getTime()));
                roomPriceList.add(roomPrice);
                
                AvailRoomQuantity availRoomQuantity = new AvailRoomQuantity();
                availRoomQuantity.setAvailQuantity(inventory.getQuota());
                availRoomQuantity.setEffectDate(new Date(inventory.getDate().getTime()));
                availRoomQuantityList.add(availRoomQuantity);
                
            }
            availRoomQuantitys.setAvailRoomQuantitys(availRoomQuantityList);
            domesticCheckRoomAvailResponse.setAvailRoomQuantitys(availRoomQuantitys);
            roomPrices.setRoomPrices(roomPriceList);
            domesticCheckRoomAvailResponse.setRoomPrices(roomPrices);
            domesticCheckRoomAvailResponse.setInterFaceAmount(countPrice);
        } else {
            isBookable = 0;

            switch (result.getString("resultCode")) {
            case "408":
                domesticCheckRoomAvailResp.setMessage("酒店满房/房量不足");
                domesticCheckRoomAvailResp.setResultCode("101");
                break;
            case "206":
                domesticCheckRoomAvailResp.setMessage("其他");
                domesticCheckRoomAvailResp.setResultCode("9999");
                break;
            default:
                domesticCheckRoomAvailResp.setMessage("其他");
                domesticCheckRoomAvailResp.setResultCode("9999");
                break;
            }
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
     * @param DomesticSubmitNewHotelOrderRequest
     * @return
     * @since JDK 1.7
     */
    private DomesticSubmitNewHotelOrderResp domesticSubmitNewHotelOrder(
            DomesticSubmitNewHotelOrderRequest domesticSubmitNewHotelOrderReqeust) throws ZZKServiceException{
        RoomTypeMapping roomTypeMapping = null;
        try {
            roomTypeMapping = roomTypeMappingService.queryByHotelIdAndOpenRoomTypeId(
                    domesticSubmitNewHotelOrderReqeust.getHotel(), domesticSubmitNewHotelOrderReqeust.getRoom(),OpenChannelType.CTRIP);
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
        bookOrderRequest.setTotalPrice((Long)(domesticSubmitNewHotelOrderReqeust.getCNYCostAmount()*100));//元转成分
        bookOrderRequest.setCurrency("CNY");//只支持人民币
        bookOrderRequest.setPaymentType(1);
        bookOrderRequest.setContactTel(domesticSubmitNewHotelOrderReqeust.getMobilePhone());
        List<OrderGuest> orderGuests = new ArrayList<OrderGuest>();
       
        List<GuestEntity> guests = domesticSubmitNewHotelOrderReqeust.getGuests().getGuests();
        StringBuffer contactName = new StringBuffer();
        for (GuestEntity guestEntity : guests) {
            OrderGuest orderGuest = new OrderGuest();
            contactName.append("&"+guestEntity.getLastName()+"/"+guestEntity.getFirstName()+" "+(StringUtils.isNotEmpty(guestEntity.getChinesName()) ?guestEntity.getChinesName():"")+" ");
            orderGuest.setName(guestEntity.getLastName()+"/"+guestEntity.getFirstName()+" "+(StringUtils.isNotEmpty(guestEntity.getChinesName()) ?guestEntity.getChinesName():"")+" ");
            orderGuests.add(orderGuest);
        }
        bookOrderRequest.setContactName(contactName.toString().replaceFirst("&", ""));
        bookOrderRequest.setOrderGuests(orderGuests);
        List<DailyInfo> dailyInfos = new ArrayList<DailyInfo>(); 
        for (RoomPrice roomPrice : domesticSubmitNewHotelOrderReqeust.getRoomPrices().getRoomPrices()) {
            DailyInfo dailyInfo = new DailyInfo();
            dailyInfo.setDay(roomPrice.getEffectDate());
            dailyInfo.setPrice((long) (roomPrice.getCNYCost()*100));
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
            domesticSubmitNewHotelOrderResponse.setInterFaceConfirmNO(result.getJSONObject("info").getString("orderId"));
            domesticSubmitNewHotelOrderResponse.setInterFaceAmount(domesticSubmitNewHotelOrderReqeust.getCNYCostAmount()+"");
        }else{
            switch (result.getString("resultCode")) {
            case "400":
                domesticSubmitNewHotelOrderResp.setMessage("其他错误");
                domesticSubmitNewHotelOrderResp.setResultCode("9999");
                break;
            case "401":
                domesticSubmitNewHotelOrderResp.setMessage("其他错误");
                domesticSubmitNewHotelOrderResp.setResultCode("9999");
                break;
            case "402":
                domesticSubmitNewHotelOrderResp.setMessage("其他错误");
                domesticSubmitNewHotelOrderResp.setResultCode("9999");
                break;
            case "403":
                domesticSubmitNewHotelOrderResp.setMessage("其他错误");
                domesticSubmitNewHotelOrderResp.setResultCode("9999");
                break;
            case "404":
                domesticSubmitNewHotelOrderResp.setMessage("其他错误");
                domesticSubmitNewHotelOrderResp.setResultCode("9999");
                break;
            case "406":
                domesticSubmitNewHotelOrderResp.setMessage("其他错误");
                domesticSubmitNewHotelOrderResp.setResultCode("9999");
                break;                    
            case "407":
                domesticSubmitNewHotelOrderResp.setMessage("其他错误");
                domesticSubmitNewHotelOrderResp.setResultCode("9999");
                break;
            case "408":
                domesticSubmitNewHotelOrderResp.setMessage("酒店满房/房量不足");
                domesticSubmitNewHotelOrderResp.setResultCode("101");
                break;
            /**
             * 价格校验失败
             */
            case "207":
                domesticSubmitNewHotelOrderResp.setMessage("其他错误");
                domesticSubmitNewHotelOrderResp.setResultCode("9999");  
        }
        }
        domesticSubmitNewHotelOrderResp.setDomesticSubmitNewHotelOrderResponse(domesticSubmitNewHotelOrderResponse);
        return domesticSubmitNewHotelOrderResp;
    }

    @Override
    public String service(String xml) throws ZZKServiceException {
        xml = xml.replaceAll("&amp;", "&").replaceAll("&lt;", "<").replaceAll("&gt;", ">");
        xml = xml.replaceAll("&", " ");
        LOG.info("CTRIP xml replace info {}",xml);
        Document doc = null;
        ResponseData responseData = null;
        try {
            doc = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            e.printStackTrace();
            LOG.error("service DocumentHelper exception{}",e);
        }
        Element root = doc.getRootElement();
        checkUser(root);
        String request = root.element("HeaderInfo").element("RequestType").attributeValue("Name");
        switch (request) {
        case "DomesticCheckRoomAvail":
            responseData = domesticCheckRoomAvail((DomesticCheckRoomAvailRequest) XstreamUtil.getXml2Bean(root.element("DomesticCheckRoomAvailRequest").asXML(),
                    DomesticCheckRoomAvailRequest.class));
            break;
        case "DomesticSubmitNewHotelOrder":
            responseData = domesticSubmitNewHotelOrder((DomesticSubmitNewHotelOrderRequest) XstreamUtil.getXml2Bean(root.element("DomesticSubmitNewHotelOrderRequest").asXML(),
                    DomesticSubmitNewHotelOrderRequest.class));
            break;
        case "DomesticCancelHotelOrder":
            responseData = domesticCancelHotelOrder((DomesticCancelHotelOrderRequest) XstreamUtil.getXml2Bean(root.element("DomesticCancelHotelOrderRequest").asXML(),
                    DomesticCancelHotelOrderRequest.class));
            break;
        default:
            break;
        }
        return XstreamUtil.getCtripResponseXml(responseData);
    }

    private DomesticCancelHotelOrderResp domesticCancelHotelOrder(DomesticCancelHotelOrderRequest domesticCancelHotelOrderRequest)throws ZZKServiceException {
        CancelOrderRequest cancelOrderRequest = new CancelOrderRequest();
        cancelOrderRequest.setOpenChannelType(OpenChannelType.CTRIP);
        cancelOrderRequest.setOpenOrderId(domesticCancelHotelOrderRequest.getOrderID());
        cancelOrderRequest.setHotelId(domesticCancelHotelOrderRequest.getHotel());
        JSONObject result = orderService.cancelRQ(cancelOrderRequest);
        DomesticCancelHotelOrderResponse domesticCancelHotelOrderResponse = new DomesticCancelHotelOrderResponse();
        DomesticCancelHotelOrderResp domesticCancelHotelOrderResp = new DomesticCancelHotelOrderResp();
        if(result.getString("resultCode").equals("200")){
            domesticCancelHotelOrderResponse.setOrderID(result.getJSONObject("info").getString("orderId"));
            domesticCancelHotelOrderResponse.setInterFaceConfirmNO(domesticCancelHotelOrderRequest.getInterFaceConfirmNO());
            domesticCancelHotelOrderResponse.setOrderStatus(OrderStatus.HOTEL_CONFIRM_SUCCESS);
            domesticCancelHotelOrderResponse.setReturnCode("0");
            domesticCancelHotelOrderResponse.setReturnDescript("取消成功");
        }else{
            switch (result.getString("resultCode")) {
            case "205":
                domesticCancelHotelOrderResp.setResultCode("9999");
                domesticCancelHotelOrderResp.setMessage("其他");
                break;
            case "204":
                domesticCancelHotelOrderResp.setResultCode("9999");
                domesticCancelHotelOrderResp.setMessage("其他");
                break;
            default:
                domesticCancelHotelOrderResp.setResultCode("9999");
                domesticCancelHotelOrderResp.setMessage("其他");
                break;
                  
            }
        }
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
                //根据房间号获得额外信息 目前有退款政策，早餐信息，最大入住人数
                RoomInfoDto roomInfoDto=baseInfoService.getRefundAndBreakfast(Integer.parseInt(rateInventoryPrice.getOutRid()));
                setRoomPriceItem.setRoomID(Integer.parseInt(roomTypeMapping.getOpenRoomTypeId()));
                hotelID=roomTypeMapping.getOpenHotelId();

                setRoomPriceItem.setCurrency("CNY");
                List<PriceInfo> priceInfos = new ArrayList<PriceInfo>();
                List<RoomInfoItem> roomInfoItems = new ArrayList<RoomInfoItem>();
                OpenDiscount openDiscount=new OpenDiscount();
                openDiscount.setChannel("CTRIP");
                openDiscount.setRoomTypeId(Integer.parseInt(roomTypeMapping.getRoomTypeId()));
                Float zzkRate = 1f;
                OpenDiscount discountInfo = baseInfoService.getOpenDiscount(openDiscount);
                if (discountInfo == null) {
                    zzkRate = 1f;
                } else {
                    zzkRate = discountInfo.getRate();
                }
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
                    priceInfo.setAmountAfterTaxFee((int)(zzkRate*inventoryPrice.getPrice()/100));
                    priceInfo.setAmountBeforeTaxFee((int)(zzkRate*inventoryPrice.getPrice()/100));
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
                    if(roomInfoDto.getValue()==1){
                        //有早餐 取人数
                        if(roomInfoDto.getName().equals("10+")){
                            priceInfo.setBreakfast(10);
                        }else{
                            priceInfo.setBreakfast(Integer.parseInt(roomInfoDto.getName()));
                        }
                    }else{
                        priceInfo.setBreakfast(0);
                    }
                    
                    priceInfo.setCostAmountAfterTaxFee((int)(zzkRate*inventoryPrice.getPrice()/100));
                    priceInfo.setCostAmountBeforeTaxFee((int)(zzkRate*inventoryPrice.getPrice()/100));
                    /**
                     * 连住天数，暂不用，默认为1       
                     */
                    priceInfo.setDay(1);
                   /**
                    * 不需要变价审核设置： Sell， Cost，Both；需 要变价审核设 置：ACost， ASell，ABoth
                    */
                    priceInfo.setPriceType("Cost");
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
                    /**
                     * refundRule来判断 {"type":0,"refund_list":{"1":{"day":"20"},"2":{"per":"20","day":"9"}}}
                     */
                    if(StringUtils.isEmpty(roomInfoDto.getRefundRule())){
                    roomInfoItem.setPrepayLCT(720);
                    }else{
                        JSONObject refundRule=JSON.parseObject(roomInfoDto.getRefundRule());
                        if(refundRule.get("type").equals(0)){
                            JSONObject refundList=(JSONObject) refundRule.get("refund_list");
                            JSONObject firstrefund=(JSONObject) refundList.get("1");
                            roomInfoItem.setPrepayLCT(firstrefund.getIntValue("day")*24);
                        }else{
                            roomInfoItem.setPrepayLCT(23988);
                        }
                    }
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
                    if(inventoryPrice.getQuota()>0){
                        roomInfoItem.setRoomStatus("G");
                    }else{
                        roomInfoItem.setRoomStatus("N");
                    }
                    
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
        pricemap.put("userId", userId);
        pricemap.put("date", dateString);
        pricemap.put("setRoomPriceItems", setRoomPriceItems);
        pricemap.put("hotelID", hotelID);
        pricemap.put("title", "");
        pricemap.put("submitor", "zizaike");
        pricemap.put("priority", "N");
        try {
            long start = System.currentTimeMillis();
            String xmlStr = soapFastUtil.post(pricemap, prefix, template, url, "");
            LOG.info(xmlStr);
            LOG.info("setRoomPrice excute time {} ms",System.currentTimeMillis() - start);
        } catch (Exception e) {
            LOG.error("setRoomPrice exception{}",e);
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
        map.put("userId", userId);
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
            LOG.info("setRoomInfo excute time {} ms",System.currentTimeMillis() - start);
        } catch (Exception e) {
            LOG.error("setRoomInfo exception{}",e);
            e.printStackTrace();
            
        }
    }

    @Override
    public void setMappingInfo(SetMappingInfoVo setMappingInfoVo) throws ZZKServiceException {
        if(setMappingInfoVo==null){
            throw new IllegalParamterException("setMappingInfoVo is not  null");
        }
        if(setMappingInfoVo.getSetMappingOperateType()==null){
            throw new IllegalParamterException("setMappingInfoVo  getSetMappingOperateType is not  null");
        }
        Map map = new HashMap();
        if(setMappingInfoVo.getSetMappingOperateType()==SetMappingOperateType.MAP_EXISTING_HOTEL_AND_ROOM_ID){
           if(StringUtils.isEmpty(setMappingInfoVo.getHotel())){
               throw new IllegalParamterException("setMappingInfoVo  getHotel is not  null");
           }
           if(StringUtils.isEmpty(setMappingInfoVo.getRoom())){
               throw new IllegalParamterException("setMappingInfoVo  getRoom is not  null");
           }
           if(StringUtils.isEmpty(setMappingInfoVo.getHotelGroupRoomTypeCode())){
               throw new IllegalParamterException("setMappingInfoVo  getHotelGroupRoomTypeCode is not  null");
           }
           if(StringUtils.isEmpty(setMappingInfoVo.getHotelGroupHotelCode())){
               throw new IllegalParamterException("setMappingInfoVo  getHotelGroupHotelCode is not  null");
           }
           if(StringUtils.isEmpty(setMappingInfoVo.getHotelGroupRatePlanCode())){
               throw new IllegalParamterException("setMappingInfoVo  getHotelGroupRatePlanCode is not  null");
           }
           if(StringUtils.isEmpty(setMappingInfoVo.getHotelGroupRoomName())){
               throw new IllegalParamterException("setMappingInfoVo  getHotelGroupRoomName is not  null");
           }
           map.put("setType",1);
           map.put("hotel", setMappingInfoVo.getHotel());
           map.put("room", setMappingInfoVo.getRoom());
           map.put("hotelGroupRoomTypeCode", setMappingInfoVo.getHotelGroupRoomTypeCode());
           map.put("hotelGroupHotelCode", setMappingInfoVo.getHotelGroupHotelCode());
           map.put("hotelGroupRatePlanCode", setMappingInfoVo.getHotelGroupRatePlanCode());
           map.put("hotelGroupRoomName", setMappingInfoVo.getHotelGroupRoomName());
        }else if(setMappingInfoVo.getSetMappingOperateType()==SetMappingOperateType.REQUEST_A_NEW_CTRIP_HOTEL){
            if(StringUtils.isEmpty(setMappingInfoVo.getMasterHotel())){
                throw new IllegalParamterException("setMappingInfoVo  getMasterHotel is not  null");
            }
            if(StringUtils.isEmpty(setMappingInfoVo.getMasterRoom())){
                throw new IllegalParamterException("setMappingInfoVo  getMasterRoom is not  null");
            }
            if(StringUtils.isEmpty(setMappingInfoVo.getRatePlanCode())){
                throw new IllegalParamterException("setMappingInfoVo  getRatePlanCode is not  null");
            }
            if(StringUtils.isEmpty(setMappingInfoVo.getHotelGroupRoomTypeCode())){
                throw new IllegalParamterException("setMappingInfoVo  getHotelGroupRoomTypeCode is not  null");
            }
            if(StringUtils.isEmpty(setMappingInfoVo.getHotelGroupHotelCode())){
                throw new IllegalParamterException("setMappingInfoVo  getHotelGroupHotelCode is not  null");
            }
            if(StringUtils.isEmpty(setMappingInfoVo.getHotelGroupRatePlanCode())){
                throw new IllegalParamterException("setMappingInfoVo  getHotelGroupRatePlanCode is not  null");
            }
            if(StringUtils.isEmpty(setMappingInfoVo.getHotelGroupRoomName())){
                throw new IllegalParamterException("setMappingInfoVo  getHotelGroupRoomName is not  null");
            }
            map.put("setType",1);
            map.put("masterHotel", setMappingInfoVo.getMasterHotel());
            map.put("masterRoom", setMappingInfoVo.getMasterRoom());
            map.put("ratePlanCode", setMappingInfoVo.getRatePlanCode());
            map.put("hotelGroupRoomTypeCode", setMappingInfoVo.getHotelGroupRoomTypeCode());
            map.put("hotelGroupHotelCode", setMappingInfoVo.getHotelGroupHotelCode());
            map.put("hotelGroupRatePlanCode", setMappingInfoVo.getHotelGroupRatePlanCode());
            map.put("hotelGroupRoomName", setMappingInfoVo.getHotelGroupRoomName());
        }else if(setMappingInfoVo.getSetMappingOperateType()==SetMappingOperateType.UN_MAPPING_ROOM_ID_DELETE_PRICE){
            if(StringUtils.isEmpty(setMappingInfoVo.getHotel())){
                throw new IllegalParamterException("setMappingInfoVo  getHotel is not  null");
            }
            if(StringUtils.isEmpty(setMappingInfoVo.getRoom())){
                throw new IllegalParamterException("setMappingInfoVo  getRoom is not  null");
            }
            if(StringUtils.isEmpty(setMappingInfoVo.getHotelGroupRoomTypeCode())){
                throw new IllegalParamterException("setMappingInfoVo  getHotelGroupRoomTypeCode is not  null");
            }
            if(StringUtils.isEmpty(setMappingInfoVo.getHotelGroupHotelCode())){
                throw new IllegalParamterException("setMappingInfoVo  getHotelGroupHotelCode is not  null");
            }
            if(StringUtils.isEmpty(setMappingInfoVo.getHotelGroupRatePlanCode())){
                throw new IllegalParamterException("setMappingInfoVo  getHotelGroupRatePlanCode is not  null");
            }
            if(StringUtils.isEmpty(setMappingInfoVo.getHotelGroupRoomName())){
                throw new IllegalParamterException("setMappingInfoVo  getHotelGroupRoomName is not  null");
            }
            map.put("setType",-2);
            map.put("hotel", setMappingInfoVo.getHotel());
            map.put("room", setMappingInfoVo.getRoom());
            map.put("hotelGroupRoomTypeCode", setMappingInfoVo.getHotelGroupRoomTypeCode());
            map.put("hotelGroupHotelCode", setMappingInfoVo.getHotelGroupHotelCode());
            map.put("hotelGroupRatePlanCode", setMappingInfoVo.getHotelGroupRatePlanCode());
            map.put("hotelGroupRoomName", setMappingInfoVo.getHotelGroupRoomName());
        }
        String template = "SetMappingInfo.vm";
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        map.put("userName", username);
        map.put("password", password);
        map.put("date", dateString);
        map.put("userId", userId);
        map.put("supplierID", supplierID);
        map.put("balanceType", BalanceType.PP);
        //相互mapping
        map.put("mappingType", MappingType.MUTUAL_MAPPING.getValue());
        map.put("setMappingOperateType", setMappingInfoVo.getSetMappingOperateType());
            long start = System.currentTimeMillis();
            Document doc = null;
            String xml = null;
            try{
                String xmlStr = soapFastUtil.post(map, prefix, template, url, "");
                LOG.info("setMappingInfo not replaceAll  response xml {}",xmlStr);
                 xml = xmlStr.replaceAll("&amp;", "&").replaceAll("&lt;", "<").replaceAll("&gt;", ">");
                LOG.info("setMappingInfo  response xml {}",xml);
                doc = DocumentHelper.parseText(xml);
            } catch (Exception e) {
                LOG.error("setMappingInfo exception{}",e);
                e.printStackTrace();
            }
           
            Element root = doc.getRootElement();
            Element requestResult = root.element("Body").element("AdapterRequestResponse")
                    .element("AdapterRequestResult").element("RequestResponse").element("RequestResult");
            if(!requestResult.element("ResultCode").getText().equals("0")){
                throw new OpenReturnException(xml);
            }  
            LOG.info("setMappingInfo excute time {} ms",System.currentTimeMillis() - start);
       
        
    }

    @Override
    public GetHotelInfoResponse getHotelInfo(Integer currentPage) throws ZZKServiceException {
        String template = "GetHotelInfo.vm";
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        Map map = new HashMap();
        map.put("userName", username);
        map.put("password", password);
        map.put("date", dateString);
        map.put("userId", userId);
        map.put("supplierID", supplierID);
        map.put("currentPage", currentPage==null || currentPage==0 ? 1: currentPage);
        GetHotelInfoResponse getHotelInfoResponse = null;
        try {
            long start = System.currentTimeMillis();
            String xmlStr = soapFastUtil.post(map, prefix, template, url, "");
            String xml = xmlStr.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
            LOG.info("getHotelInfo  response xml {}",xml);
            Document doc = null;
            try {
                doc = DocumentHelper.parseText(xml);
            } catch (DocumentException e) {
                LOG.error("getHotelInfo exception{}",e);
                e.printStackTrace();  
            }
            Element root = doc.getRootElement();
            Element requestResult = root.element("Body").element("AdapterRequestResponse")
            .element("AdapterRequestResult").element("RequestResponse").element("RequestResult");
            if(!requestResult.element("ResultCode").getText().equals("0")){
                throw new OpenReturnException(xml);
            } 
            if(requestResult.element("ResultCode").getText().equals("0")){
                String xmlS = requestResult.element("Response").element("GetHotelInfoResponse").asXML();
                getHotelInfoResponse = (GetHotelInfoResponse) XstreamUtil.getXml2Bean(xmlS,GetHotelInfoResponse.class);
            }    
            LOG.info("getHotelInfo excute time {} ms",System.currentTimeMillis() - start);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getHotelInfoResponse;
    }

    @Override
    public GetMappingInfoResponseList getMappingInfo(MappingInfoVo mappingInfoEntity)
            throws ZZKServiceException {
        if(mappingInfoEntity ==null){
            throw new IllegalParamterException("getMappingInfo mappingInfoEntity is not null");
        }
        if(mappingInfoEntity.getHotels().size()==0){
            throw new IllegalParamterException("getMappingInfo hotels is not null");
        }
        if(mappingInfoEntity.getGetMappingInfoType()==null){
            throw new IllegalParamterException("getMappingInfo getMappingInfoType is not null");
        }
        String template = "GetMappingInfo.vm";
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        Map map = new HashMap();
        map.put("userName", username);
        map.put("password", password);
        map.put("date", dateString);
        map.put("userId", userId);
        map.put("supplierID", supplierID);
        //获取信息类型：UnMapping表示  获取未对接的信息， Appoint表示获取指定信息
        map.put("getMappingInfoType", mappingInfoEntity.getGetMappingInfoType());
        map.put("hotels", mappingInfoEntity.getHotels());
        GetMappingInfoResponseList getMappingInfoResponseList = null;
            long start = System.currentTimeMillis();
            Document doc = null; 
            String xml =null;
            try{
            String xmlStr = soapFastUtil.post(map, prefix, template, url, "");
            LOG.info("getMappingInfo  response not replaceAll xml {}",xmlStr);
            xml = xmlStr.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
            LOG.info("getMappingInfo  response xml {}",xml);
            doc = DocumentHelper.parseText(xml);
            
            } catch (Exception e) {
                LOG.error("getMappingInfo exception{}",e);
                e.printStackTrace();
            }
            Element root = doc.getRootElement();
            Element requestResult = root.element("Body").element("AdapterRequestResponse")
                    .element("AdapterRequestResult").element("RequestResponse").element("RequestResult");
            if(!requestResult.element("ResultCode").getText().equals("0")){
                throw new OpenReturnException(xml);
            } 
                    if(requestResult.element("ResultCode").getText().equals("0")){
                        String xmlS = requestResult.element("Response").element("GetMappingInfoResponseList").asXML();
                        getMappingInfoResponseList = (GetMappingInfoResponseList) XstreamUtil.getXml2Bean(xmlS,GetMappingInfoResponseList.class);
                    }    
                    LOG.info("getMappingInfo excute time {} ms",System.currentTimeMillis() - start);
       
        return getMappingInfoResponseList;
    }
    @Override
    public HotelGroupInterfaceRoomTypeEntity getCtripRoomTypeInfo(HotelGroupInterfaceRoomTypeVo hotelGroupInterfaceRoomTypeVo)throws ZZKServiceException{
        if(hotelGroupInterfaceRoomTypeVo ==null){
            throw new IllegalParamterException("getCtripRoomTypeInfo hotelGroupInterfaceRoomTypeVo is not null");
        }
        if(hotelGroupInterfaceRoomTypeVo.getHotelGroupHotelCode()==null){
            throw new IllegalParamterException("getCtripRoomTypeInfo hotelGroupHotelCode is not null");
        }
        if(hotelGroupInterfaceRoomTypeVo.getHotelGroupRoomTypeCode()==null){
            throw new IllegalParamterException("getCtripRoomTypeInfo getHotelGroupRoomTypeCode is not null");
        }
        if(hotelGroupInterfaceRoomTypeVo.getHotelGroupRatePlanCode()==null){
            throw new IllegalParamterException("getCtripRoomTypeInfo getHotelGroupRatePlanCode is not null");
        }
        String template = "GetCtripRoomTypeInfo.vm";
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        Map map = new HashMap();
        map.put("userName", username);
        map.put("password", password);
        map.put("userId", userId);
        map.put("date", dateString);
        map.put("hotelGroupRoomTypeCode", hotelGroupInterfaceRoomTypeVo.getHotelGroupRoomTypeCode());
        map.put("hotelGroupHotelCode", hotelGroupInterfaceRoomTypeVo.getHotelGroupHotelCode());
        map.put("hotelGroupRatePlanCode", hotelGroupInterfaceRoomTypeVo.getHotelGroupRatePlanCode());
        HotelGroupInterfaceRoomTypeEntity hotelGroupInterfaceRoomTypeEntity = null;
            long start = System.currentTimeMillis();
            String xml = null;
            Document doc = null;
            try{
                xml = soapFastUtil.post(map, prefix, template, url, "");
                LOG.info("getCtripRoomTypeInfo  response xml {}",xml);
                doc = DocumentHelper.parseText(xml);
            } catch (Exception e) {
                LOG.error("getCtripRoomTypeInfo exception{}",e);
                e.printStackTrace();
            }
               
            Element root = doc.getRootElement();
            Element requestResult = root.element("Body").element("RequestResponse").element("RequestResult");
            if(!requestResult.element("ResultCode").getText().equals("0")){
                throw new OpenReturnException(xml);
            } 
            if(requestResult.element("ResultCode").getText().equals("0")){
                if(requestResult.element("Response").element("HotelGroupInterfaceRoomTypeListResponse").element("HotelGroupInterfaceRoomTypeList").element("HotelGroupInterfaceRoomTypeEntity")==null){
                    return null;
                }
                String xmlS = requestResult.element("Response").element("HotelGroupInterfaceRoomTypeListResponse").element("HotelGroupInterfaceRoomTypeList").element("HotelGroupInterfaceRoomTypeEntity").asXML();
                hotelGroupInterfaceRoomTypeEntity = (HotelGroupInterfaceRoomTypeEntity) XstreamUtil.getXml2Bean(xmlS,HotelGroupInterfaceRoomTypeEntity.class);
            }    
            LOG.info("getCtripRoomTypeInfo excute time {} ms",System.currentTimeMillis() - start);
       
        RoomTypeMapping roomTypeMapping = new RoomTypeMapping();
        roomTypeMapping.setChannel(OpenChannelType.CTRIP);
        roomTypeMapping.setHotelId(hotelGroupInterfaceRoomTypeEntity.getHotelGroupHotelCode()+"");
        roomTypeMapping.setRoomTypeId(hotelGroupInterfaceRoomTypeEntity.getHotelGroupRoomTypeCode()+"");
        roomTypeMapping.setRoomName(hotelGroupInterfaceRoomTypeEntity.getHotelGroupRoomName());
        roomTypeMapping.setOpenRoomName(hotelGroupInterfaceRoomTypeEntity.getRoomName());
        roomTypeMapping.setOpenHotelId(hotelGroupInterfaceRoomTypeEntity.getHotel()+"");
        roomTypeMapping.setOpenRoomTypeId(hotelGroupInterfaceRoomTypeEntity.getRoom()+"");
        roomTypeMappingService.addOrUpdate(roomTypeMapping);
        return hotelGroupInterfaceRoomTypeEntity;
        
    }
}
