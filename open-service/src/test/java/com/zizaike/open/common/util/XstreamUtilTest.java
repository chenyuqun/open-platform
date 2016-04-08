package com.zizaike.open.common.util;

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.open.alibaba.request.BookRQRequest;
import com.zizaike.entity.open.alibaba.request.CancelRQRequest;
import com.zizaike.entity.open.alibaba.request.QueryStatusRQRequest;
import com.zizaike.entity.open.alibaba.request.ValidateRQRequest;
import com.zizaike.entity.open.alibaba.response.ResponseData;
import com.zizaike.entity.open.alibaba.response.ValidateRQResponse;
import com.zizaike.entity.open.ctrip.RoomPrice;
import com.zizaike.entity.open.ctrip.RoomPrices;
import com.zizaike.entity.open.ctrip.response.AvailRoomQuantity;
import com.zizaike.entity.open.ctrip.response.AvailRoomQuantitys;
import com.zizaike.entity.open.ctrip.response.DomesticCheckRoomAvailResp;
import com.zizaike.entity.open.ctrip.response.DomesticCheckRoomAvailResponse;
import com.zizaike.entity.open.qunar.request.BookingRequest;
import com.zizaike.entity.open.qunar.request.PriceRequest;
import com.zizaike.entity.open.qunar.response.*;
import com.zizaike.open.bastest.BaseTest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class XstreamUtilTest extends BaseTest {
    @Test
    public void getXml2BeanValidateRQ() throws ZZKServiceException {
        String xml = "<ValidateRQ>" + "<AuthenticationToken>" + "<Username>taobao</Username>"
                + "<Password>B75!jaJb[eO8</Password>"
                + "<CreateToken>22251178182015010620150107497867981843210904377</CreateToken>"
                + "</AuthenticationToken>" + "<TaoBaoHotelId>1357757818</TaoBaoHotelId>"
                + "<HotelId>3FENY3V11P</HotelId>" + "<TaoBaoRoomTypeId>5501264818</TaoBaoRoomTypeId>"
                + " <RoomTypeId>3FENY3V11P-RT1241</RoomTypeId>" + "<TaoBaoRatePlanId>4978679818</TaoBaoRatePlanId>"
                + "<RatePlanCode>3FENY3V11P-RT1241-RP846</RatePlanCode>" + " <TaoBaoGid>3824371818</TaoBaoGid>"
                + "<CheckIn>2015-01-06</CheckIn>" + "<CheckOut>2015-01-07</CheckOut>" + "<RoomNum>1</RoomNum>"
                + "<PaymentType>1</PaymentType>"
                + "<Extensions>{'searchid':'22251178182015010620150107497867981843210904377'}</Extensions>"
                + " </ValidateRQ>";
        ValidateRQRequest validateRQ = (ValidateRQRequest) XstreamUtil.getXml2Bean(xml, ValidateRQRequest.class);
        System.out.println(validateRQ.toString());
    }

    @Test
    public void getXml2BeanBookRQRequest() throws ZZKServiceException {
        String xml = "<BookRQ>"
                + "<AuthenticationToken>"
                + "<Username>taobao</Username>"
                + "<Password>taobao</Password>"
                + "<CreateToken>taobao1387784033263-1387784033266</CreateToken>"
                + "</AuthenticationToken>"
                + "<TaoBaoOrderId>1387784033263</TaoBaoOrderId>"
                + "<TaoBaoHotelId>123456789</TaoBaoHotelId>"
                + "<HotelId>80</HotelId>"
                + "<TaoBaoRoomTypeId>123456978</TaoBaoRoomTypeId>"
                + "<RoomTypeId>ST</RoomTypeId>"
                + "<TaoBaoRatePlanId >123000123</TaoBaoRatePlanId >"
                + "<RatePlanCode>VIP</RatePlanCode>"
                + "<TaoBaoGid>1234567890</TaoBaoGid>"
                + "<CheckIn>2013-12-24 00:00:00</CheckIn>"
                + "<CheckOut>2013-12-26 00:00:00</CheckOut>"
                + "<HourRent>false</HourRent>"
                + "<EarliestArriveTime>2013-12-24 20:00:00</EarliestArriveTime>"
                + "<LatestArriveTime>2013-12-24 22:00:00</LatestArriveTime>"
                + "<RoomNum>1</RoomNum>"
                + "<TotalPrice>63850</TotalPrice>"
                + "<SellerDiscount>6800</SellerDiscount >"
                + "<AlitripDiscount >800</AlitripDiscount >"
                + "<Currency>CNY</Currency>"
                + "<PaymentType>5</PaymentType>"
                + "<ContactName>测试联系人</ContactName>"
                + "<ContactTel>13920682209</ContactTel>"
                + "<ContactEmail >hello@taobao.com</ContactEmail >"
                + "<DailyInfos>"
                + "<DailyInfo>"
                + "<Day>2013-12-24</Day>"
                + "<Price>17800</Price>"
                + "</DailyInfo>"
                + "<DailyInfo>"
                + "<Day>2013-12-25</Day>"
                + "<Price>46050</Price>"
                + "</DailyInfo>"
                + "</DailyInfos>"
                + "<OrderGuests>"
                + "<OrderGuest>"
                + "<Name>入住人1</Name>"
                + "<RoomPos>1</RoomPos>"
                + "</OrderGuest>"
                + "<OrderGuest>"
                + "<Name>入住人2</Name>"
                + "<RoomPos>1</RoomPos>"
                + "</OrderGuest>"
                + "</OrderGuests>"
                + "<Comment>测试</Comment>"
                + "<GuaranteeType> 0</GuaranteeType>"
                + "<MemberCardNo >NB888888888</MemberCardNo>"
                + "<ReceiptInfo>"
                + "<ReceiptTitle>淘宝软件有限公司</ReceiptTitle>"
                + "<ReceiptType>房费</ReceiptType>"
                + "<ReceiptAddress>{\"name\":\"收件人\",\"address\":\"北京市\",\"postCode\":\"100022\",\"mobile\":\"13877779999\",\"phone\":\"01082828989\"}</ReceiptAddress>"
                + "</ReceiptInfo>" 
                + "<Extensions></Extensions>"
                + "</BookRQ>";
       BookRQRequest bookRQ = (BookRQRequest) XstreamUtil.getXml2Bean(xml, BookRQRequest.class);
       System.out.println(bookRQ.toString());
    }
    
    @Test
    public void getXml2BeanCancelRQ() throws ZZKServiceException {
        String xml = "<CancelRQ>"
                +"<AuthenticationToken>"
                + "<Username>taobao</Username>"
                + "<Password>taobao</Password>"
                + "<CreateToken>taobao125484778-1387789907859</CreateToken>"
                + "</AuthenticationToken>"
                + "<TaoBaoOrderId>21544874</TaoBaoOrderId>"
                + "<OrderId>21544874</OrderId>"
                + "<HotelId>HZJT01</HotelId>"
                + "<Reason>reason</Reason>"
                + "<CancelId>1387789907859</CancelId>"
                + "<HardCancel>true</HardCancel>"
                + "</CancelRQ>";
        CancelRQRequest cancelRQ = (CancelRQRequest) XstreamUtil.getXml2Bean(xml, CancelRQRequest.class);
        System.out.println(cancelRQ.toString());
    }
    
    @Test
    public void getXml2BeanQueryStatusRQ() throws ZZKServiceException {     
        String xml = "<QueryStatusRQ>"
                + "<AuthenticationToken>"
                + "<Username>taobao</Username>"
                + "<Password>taobao</Password>"
                + "<CreateToken>taobao1230123213-1387792484913</CreateToken>"
                + "</AuthenticationToken>"
                + " <OrderId>12321323</OrderId>"
                + "<TaoBaoOrderId>1230123213</TaoBaoOrderId>"
                + " <HotelId>123456</HotelId>"
                + "</QueryStatusRQ>";
        QueryStatusRQRequest queryStatusRQ = (QueryStatusRQRequest) XstreamUtil.getXml2Bean(xml, QueryStatusRQRequest.class);
        System.out.println(queryStatusRQ.toString());
    }
    @Test
    public void DomesticCheckRoomAvailResp() throws ZZKServiceException {     
        DomesticCheckRoomAvailResp test = new DomesticCheckRoomAvailResp();
        DomesticCheckRoomAvailResponse respose = new DomesticCheckRoomAvailResponse();
        AvailRoomQuantity availRoomQuantity = new AvailRoomQuantity();
        availRoomQuantity.setEffectDate(new Date());
        availRoomQuantity.setAvailQuantity(100);
        AvailRoomQuantity availRoomQuantity1 = new AvailRoomQuantity();
        availRoomQuantity1.setEffectDate(new Date());
        availRoomQuantity1.setAvailQuantity(100);
        AvailRoomQuantitys availRoomQuantitys = new AvailRoomQuantitys();
        List<AvailRoomQuantity> list = new ArrayList<AvailRoomQuantity>();
        list.add(availRoomQuantity);
        list.add(availRoomQuantity1);
        availRoomQuantitys.setAvailRoomQuantitys(list);
        respose.setAvailRoomQuantitys(availRoomQuantitys);
        RoomPrice roomPrice = new RoomPrice();
        roomPrice.setEffectDate(new Date());
        RoomPrice roomPrice1 = new RoomPrice();
        roomPrice1.setEffectDate(new Date());
        RoomPrices roomPrices = new RoomPrices();
        List<RoomPrice> roomPricesList = new ArrayList<RoomPrice>();
        roomPricesList.add(roomPrice);
        roomPricesList.add(roomPrice1);
        roomPrices.setRoomPrices(roomPricesList);
        respose.setRoomPrices(roomPrices);
        test.setDomesticCheckRoomAvailResponse(respose);
        ResponseData responseData = test;
        System.out.println( XstreamUtil.getCtripResponseXml(responseData));
    }
    
    @Test
    public void getParamXml() throws ZZKServiceException {
        ValidateRQResponse validateRQResponse = new ValidateRQResponse("满房", "-1","","[{\"date\":\"2015-01-01\",\"price\":21000,\"quota\":4},{\"date\":\"2015-01-02\",\"price\":22000,\"quota\":4},{\"date\":\"2015-01-03\",\"price\":25000,\"quota\":5}]","2","16","5","10");
        System.err.println(XstreamUtil.getResponseXml(validateRQResponse));
    }
     @Test
    public void xmlGetElement() throws ZZKServiceException, DocumentException {
        String xml = "<ValidateRQ>" + "<AuthenticationToken>" + "<Username>taobao</Username>"
                + "<Password>B75!jaJb[eO8</Password>"
                + "<CreateToken>22251178182015010620150107497867981843210904377</CreateToken>"
                + "</AuthenticationToken>" + "<TaoBaoHotelId>1357757818</TaoBaoHotelId>"
                + "<HotelId>3FENY3V11P</HotelId>" + "<TaoBaoRoomTypeId>5501264818</TaoBaoRoomTypeId>"
                + " <RoomTypeId>3FENY3V11P-RT1241</RoomTypeId>" + "<TaoBaoRatePlanId>4978679818</TaoBaoRatePlanId>"
                + "<RatePlanCode>3FENY3V11P-RT1241-RP846</RatePlanCode>" + " <TaoBaoGid>3824371818</TaoBaoGid>"
                + "<CheckIn>2015-01-06</CheckIn>" + "<CheckOut>2015-01-07</CheckOut>" + "<RoomNum>1</RoomNum>"
                + "<PaymentType>1</PaymentType>"
                + "<Extensions>{'searchid':'22251178182015010620150107497867981843210904377'}</Extensions>"
                + " </ValidateRQ>";
        Document doc = null;
        doc = DocumentHelper.parseText(xml);
        Element root = doc.getRootElement();
        System.err.println(root.getQualifiedName());
    }

    @Test
    public void getQunarParamXml() throws ZZKServiceException {

        List<Hotel> hotelList=new ArrayList<Hotel>();
        hotelList.add(new Hotel("1","shanghai","zizaike","jinke road","021-12345"));
        hotelList.add(new Hotel("2","shanghai","alex","pudong","021-12346"));
        HotelList hotelList1=new HotelList(hotelList);
        System.err.println(XstreamUtil.getResponseXml(hotelList1));
    }

    @Test
    public void qunarPriceRequest() throws  ZZKServiceException{
        String xml= "<priceRequest>" +
                "<hotelId>16166</hotelId>" +
                "<checkin>2014-12-28</checkin>" +
                "<checkout>2014-12-30</checkout>" +
                "<roomId>199</roomId>" +
                "<numberOfRooms>2</numberOfRooms>" +
                "<customerInfos>" +
                "<customerInfo seq=\"0\" numberOfAdults=\"2\" numberOfChildren=\"2\" childrenAges=\"8|12\" >" +
                "</customerInfo>" +
                "<customerInfo seq=\"1\" numberOfAdults=\"2\" numberOfChildren=\"0\" childrenAges=\"\" >" +
                "</customerInfo>" +
                "</customerInfos>" +
                "</priceRequest>";
        PriceRequest priceRequest = (PriceRequest) XstreamUtil.getXml2Bean(xml, PriceRequest.class);
        System.out.println(priceRequest.toString());
    }

    @Test(description = "测试qunar返回")
    public void qunarPriceResponse() throws ZZKServiceException{
        /**
         * 床型
         */
        BedType bedType=new BedType();
        bedType.setRelation("OR");
        List<Beds> bedsList=new ArrayList<Beds>();
        Beds beds=new Beds();
        beds.setSeq("1");
        beds.setCode(BedTypeCode.BUNK);
        beds.setDesc(BedTypeCode.getByCode("BUNK"));
        beds.setCount(2);
        beds.setSize("1.8m*2m");
        bedsList.add(beds);
        Beds beds2=new Beds();
        beds2.setSeq("2");
        beds2.setCode(BedTypeCode.SINGLE);
        beds2.setDesc(BedTypeCode.getByCode("SINGLE"));
        beds2.setCount(1);
        beds2.setSize("1.8m*1.5m");
        bedsList.add(beds2);
        bedType.setBeds(bedsList);
        /**
         * Meal
         */
        Meal meal=new Meal();
        Breakfast breakfast=new Breakfast("2|2","日式早餐");
        Lunch lunch=new Lunch("0|0","");
        Dinner dinner=new Dinner("0|0","");
        meal.setBreakfast(breakfast);
        meal.setLunch(lunch);
        meal.setDinner(dinner);
        /**
         * 退款
         */
        List<RefundRule> refundRules=new ArrayList<RefundRule>();
        refundRules.add(new RefundRule(29, RefundType.DEDUCT_BY_AMOUNT,"50"));
        refundRules.add(new RefundRule(20, RefundType.DEDUCT_BY_PERCENT,"70"));

        List<NonRefundableRange> nonRefundableRanges=new ArrayList<NonRefundableRange>();
        nonRefundableRanges.add(new NonRefundableRange("2015-10-01","2015-10-07"));

        Refund refund=new Refund();
        refund.setReturnable(Boolean.TRUE);
        refund.setTimeZone("GMT+9");
        refund.setRefundRules(refundRules);
        refund.setNonRefundableRanges(nonRefundableRanges);
        /**
         * 备注节点
         */
        Remarks remarks=new Remarks();
        List<Remark> remarkList=new ArrayList<Remark>();
        remarkList.add(new Remark(1,"the weather will be rainy in July, please prepare rain gears by yourself"));
        remarkList.add(new Remark(2,"no pets allowed please"));
        remarkList.add(new Remark(3,"free parking, but cannot make sure available parking lots any time"));
        remarks.setRemarks(remarkList);
        /**
         * optionRules节点 设施？
         */
        OptionRules optionRules=new OptionRules();
        List<OptionRule> optionRuleList=new ArrayList<OptionRule>();
        optionRuleList.add(new OptionRule(OptionRuleCode.HIGH_SPEED_NETWORK,OptionRuleCode.getByCode("HIGH_SPEED_NETWORK"),"10",UnitOfCharge.PER_DAY,Boolean.TRUE));
        optionRuleList.add(new OptionRule(OptionRuleCode.AIRPORT_PICKUP,OptionRuleCode.getByCode("AIRPORT_PICKUP"),"20",UnitOfCharge.PER_TIME,Boolean.FALSE));
        optionRules.setOptionRules(optionRuleList);
        /**
         * 促销节点
         */
        PromotionRules promotionRules=new PromotionRules();
        List<PromotionRule> promotionRuleList=new ArrayList<PromotionRule>();
        promotionRuleList.add(new PromotionRule(PromotionRuleCode.FREE_UPGRADE,PromotionRuleCode.getByCode("FREE_UPGRADE"),"0"));
        promotionRuleList.add(new PromotionRule(PromotionRuleCode.LAST,PromotionRuleCode.getByCode("LAST"),"3"));
        promotionRules.setPromotionRules(promotionRuleList);
        /**
         * 额外节点
         */
        Extras extras=new Extras();
        List<Extra> extraList=new ArrayList<Extra>();
        extraList.add(new Extra("TOKEN","ASDFJJJJ9999XXXXYYY"));
        extraList.add(new Extra("OTHER_KEY","XXXYYY"));
        extras.setExtras(extraList);
        /**
         * 房间节点
         */
        List<Room> roomList=new ArrayList<Room>();
        Room room=new Room();
        room.setBedType(bedType);
        room.setMeal(meal);
        room.setRefund(refund);
        room.setRemarks(remarks);
        room.setOptionRules(optionRules);
        room.setPromotionRules(promotionRules);
        room.setExtras(extras);
        room.setId("P_1");
        room.setArea("");
        room.setBroadband("FREE");
        room.setCheckinTime("12:00");
        room.setCheckoutTime("12:30");
        room.setCounts("5|5");
        room.setStatus("ACTIVE|DISABLED");
        room.setFreeChildrenAgeLimit(8);
        room.setFreeChildrenNumber(1);
        room.setInstantConfirmRoomCount("3|3");
        room.setPrices("200|200");
        room.setName("特色房");
        room.setMaxOccupancy(2);
        room.setOccupancyNumber(2);
        room.setPayType(PayType.PREPAY);
        room.setRoomRate("180|180");
        room.setWifi("FREE");
        room.setTaxAndFee("20|20");
        roomList.add(room);
        PriceResponse priceResponse=new PriceResponse();
        priceResponse.setCheckin("2015-05-01");
        priceResponse.setCheckout("2015-05-03");
        priceResponse.setCurrrencyCode("USD");
        priceResponse.setHotelAddress("1-3-3, Naka-machi, Machida-city, Tokyo");
        priceResponse.setHotelCity("tokyo");
        priceResponse.setHotelId("9987");
        priceResponse.setHotelPhone("0081-42-7281045");
        priceResponse.setHotelName("Toyoko Inn Tokyo Machida-eki Odakyu-sen Higashi-guchi");
        priceResponse.setRooms(roomList);
        System.err.println(XstreamUtil.getResponseXml(priceResponse));


    }
    @Test(description="test qunarBookingRequest")
    public void qunarBookingRequest()throws ZZKServiceException{
        String xml = "<bookingRequest>"
                + "<hotelId>16166</hotelId>"
                + "<checkin>2015-02-20</checkin>"
                + "<checkout>2015-02-22</checkout>"
                + "<totalPrice>380</totalPrice><!--  totalPrices = sum(prices) - promotionRule[@code=INSTANT_SUBTRACT]/value  -->"
                + "<currencyCode>USD</currencyCode>"
                + "<rmbPrice>2356.87</rmbPrice>"
                + "<customerArriveTime>16:00-18:00</customerArriveTime>"
                + "<specialRemarks>PREFER_NON_SMOKING,PREFER_HIGH_FLOOR</specialRemarks> <!-- preference from consumer -->"
                + "<numberOfRooms>2</numberOfRooms>"
                + "<bedChoice>1</bedChoice>"
                + "<instantConfirm>false</instantConfirm>"
                + "<requiredAction>CONFIRM_ROOM_SUCCESS/CONFIRM_ROOM_FAILURE</requiredAction>"
                + "<room id=\"9986\" name=\"特色房\" broadband=\"FREE\" payType=\"PREPAY\" prices=\"200|200\" status=\"ACTIVE|ACTIVE\" counts=\"5|5\" "
                + "roomRate=\"180|180\" taxAndFee=\"20|20\" maxOccupancy=\"2\" occupancyNumber=\"2\""
                + "freeChildrenNumber=\"1\" freeChildrenAgeLimit=\"8\" instantConfirmRoomCount=\"3|3\" wifi=\"FREE\" "
                + "checkinTime=\"\" checkoutTime=\"\" area=\"\" >"
                + "<bedType>"
                + "<beds seq=\"1\" code=\"SINGLE\" desc=\"单人床\" count=\"2\" size=\"1.2m*2m\" >"
                + "</beds>\""
                + "</bedType>\""
                + "<meal>\""
                + "<breakfast count=\"2|2\" desc=\"self-service breakfast\" />"
                + "<lunch count=\"0|0\" desc=\"\" /><dinner count=\"0|0\" desc=\"\" /></meal>"
                + "<promotionRules><promotionRule code=\"INSTANT_DEDUCT\" desc=\"立减\" value=\"20\"></promotionRule></promotionRules>"
                + "</room>"
                + "<customerInfos>"
                + "<customerInfo seq=\"1\" numberOfAdults=\"2\" numberOfChildren=\"2\" childrenAges=\"8|12\" >"
                + "<customer firstName=\"Deng\" lastName=\"Ziqiang\" nationality=\"CN\" gender=\"male\" />"
                //+ "<customer firstName=\"Li\" lastName=\"Xuejuan\" nationality=\"CN\" gender=\"female\" />"
                //+ "<customer firstName=\"Child\" lastName=\"One\" nationality=\"CN\" gender=\"female\" />"
                + "</customerInfo>"
                + "<customerInfo seq=\"2\" numberOfAdults=\"2\" numberOfChildren=\"0\" childrenAges=\"\" >"
               // + "<customer firstName=\"Li\" lastName=\"XoXo\" nationality=\"CN\" gender=\"male\" />"
                + "<customer firstName=\"Sun\" lastName=\"MoMo\" nationality=\"CN\" gender=\"female\" />"
                + "</customerInfo>" 
                + "</customerInfos>" 
                + "<qunarOrderInfo>"
                + "<orderNum>j3gm141219163017759</orderNum><!-- unique order id at Qunar  -->"
                + "<hotelSeq>osaka_2202</hotelSeq><!-- unique id for a hotel at Qunar -->"
                + "<hotelName>阪急阪神大阪国际酒店(Hotel Hankyu International)</hotelName>"
                + "<hotelAddress>19-19, Chayamachi, Kita-ku, Osaka 530-0013, Japan</hotelAddress>"
                + "<cityName>大阪</cityName>" 
                + "<hotelPhone>0081-6-63772100</hotelPhone>"
                + "<orderDate>2014-12-19 16:30:17</orderDate>" 
                + "<contactName>张三</contactName>"
                + "<contactPhone>1381****818</contactPhone>" 
                + "<contactEmail>miao.****@qunar.com</contactEmail>"
                + "<payType>PREPAY</payType>" 
                + "<customerIp>103.24.27.9</customerIp>"
                + "<invoiceCode>E</invoiceCode><!-- N=no require Y=paper invoice E=electronic receipt -->"
                + "</qunarOrderInfo>" 
                + "</bookingRequest>";
        
        BookingRequest bookingRequest = (BookingRequest) XstreamUtil.getXml2Bean(xml, BookingRequest.class);
        System.err.print(bookingRequest.toString());        
    }
}
