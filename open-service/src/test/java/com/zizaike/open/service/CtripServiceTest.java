package com.zizaike.open.service;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.open.ctrip.GetMappingInfoType;
import com.zizaike.entity.open.ctrip.SetMappingOperateType;
import com.zizaike.entity.open.ctrip.vo.HotelGroupInterfaceRoomTypeVo;
import com.zizaike.entity.open.ctrip.vo.MappingInfoVo;
import com.zizaike.entity.open.ctrip.vo.SetMappingInfoVo;
import com.zizaike.is.open.CtripService;
import com.zizaike.open.bastest.BaseTest;

public class CtripServiceTest extends BaseTest {
    @Autowired
    private CtripService ctipCtripService;
    
    @Test(description = "试单请求单元测试")
    public void domesticCheckRoomAvail() throws ZZKServiceException, DocumentException {
//        String xml = "<Request>"
//                + "<HeaderInfo UserID='25' RequestorId='Ctrip.com' AsyncRequest='false' TimeStamp='2012-8-6 3:54:24'>"
//                + "<Authentication UserName='zhilianjishuzhuanshu' Password='zhilianzhuanshu11!!'/>"
//                + "<RequestType Name='DomesticCheckRoomAvailRequest' Version='1.0'/>" + "</HeaderInfo>"
//                + "<DomesticCheckRoomAvailRequest>" + "<!--Ctrip酒店编号,供应商需要转成自己的对应编号 -->" + "<Hotel>4504433</Hotel>"
//                + "<!--到店日期-->" + "<Arrival>2016-05-05T00:00:00</Arrival>" + "<!--离店日期-->"
//                + "<Departure>2016-05-07T00:00:00</Departure>" + "<!--Ctrip房型编号,供应商需要转成自己的对应编号-->"
//                + "<Room>24658621</Room>" + "<!--预订数量-->" + "<RoomNumber>1</RoomNumber>" + "<!--币种-->"
//                + "<CurrencyCode>RMB</CurrencyCode>" + "<!--入住人数-->" + "<Person>1</Person>" + "<!--付款类型-->"
//                + "<BalanceType>PP</BalanceType>" + "<RoomPrices>" + "<!--每个RoomPrice对应一天价格-->" + "<RoomPrice>"
//                + "<!--入住日期-->" + "<EffectDate>2016-05-05T00:00:00</EffectDate>" + "<!--卖价-->" + "<Price>618</Price>"
//                + "<!--汇率后的卖价-->" + "<CNYPrice>0</CNYPrice>" + "<!--底价-->" + "<Cost>525</Cost>" + "<!--汇率后的底价-->"
//                + "<CNYCost>0</CNYCost>" + "<!--含早餐数-->" + "<BreakFast>0</BreakFast>" + "</RoomPrice>" + "<RoomPrice>"
//                + "<!--入住日期-->" + "<EffectDate>2016-05-07T00:00:00</EffectDate>" + "<!--卖价-->" + "<Price>618</Price>"
//                + "<!--汇率后的卖价-->" + "<CNYPrice>0</CNYPrice>" + "<!--底价-->" + "<Cost>525</Cost>" + "<!--汇率后的底价-->"
//                + "<CNYCost>0</CNYCost>" + "<!--含早餐数-->" + "<BreakFast>0</BreakFast>"+ "</RoomPrice>" 
//                + "</RoomPrices>" + "</DomesticCheckRoomAvailRequest>" + "</Request>";
        String xml = "<?xml version='1.0' encoding='utf-8'?>"
                +"<Request>"
                +"<HeaderInfo UserID='25' RequestorId='Ctrip.com' AsyncRequest='false' TimeStamp='2012-8-6 3:54:24'>"
                  +"<Authentication UserName='zhilianjishuzhuanshu' Password='zhilianzhuanshu11!!'/>"
                  +"<RequestType Name='DomesticCheckRoomAvailRequest' Version='1.0'/>"
                +"</HeaderInfo>"
                +"<DomesticCheckRoomAvailRequest>"
                  +"<!--Ctrip酒店编号,供应商需要转成自己的对应编号 -->"
                  +"<Hotel>4504433</Hotel>"
                  +"<!--到店日期-->"
                  +"<Arrival>2016-05-03T00:00:00</Arrival>"
                  +"<!--离店日期-->"
                  +"<Departure>2016-05-04T00:00:00</Departure>"
                  +"<!--Ctrip房型编号,供应商需要转成自己的对应编号-->"
                  +"<Room>24658621</Room>"
                  +"<!--预订数量-->"
                  +"<RoomNumber>1</RoomNumber>"
                  +"<!--币种-->"
                  +"<CurrencyCode>RMB</CurrencyCode>"
                  +"<!--入住人数-->"
                  +"<Person>1</Person>"
                  +"<!--付款类型-->"
                  +"<BalanceType>PP</BalanceType>"
                  +"<RoomPrices>"
                    +"<!--每个RoomPrice对应一天价格-->"
                    +"<RoomPrice>"
                      +"<!--入住日期-->"
                      +"<EffectDate>2016-05-03T00:00:00</EffectDate>"
                      +"<!--卖价-->"
                      +"<Price>618</Price>"
                      +"<!--汇率后的卖价-->"
                      +"<CNYPrice>0</CNYPrice>"
                      +"<!--底价-->"
                      +"<Cost>525</Cost>"
                      +"<!--汇率后的底价-->"
                      +"<CNYCost>0</CNYCost>"
                      +"<!--含早餐数-->"
                      +"<BreakFast>0</BreakFast>"
                    +"</RoomPrice>"
                    +"<RoomPrice>"
                      +"<!--入住日期-->"
                      +"<EffectDate>2016-05-04T00:00:00</EffectDate>"
                      +"<!--卖价-->"
                      +"<Price>618</Price>"
                      +"<!--汇率后的卖价-->"
                      +"<CNYPrice>0</CNYPrice>"
                      +"<!--底价-->"
                      +"<Cost>525</Cost>"
                      +"<!--汇率后的底价-->"
                      +"<CNYCost>0</CNYCost>"
                      +"<!--含早餐数-->"
                      +"<BreakFast>0</BreakFast>"
                    +"</RoomPrice>"
                  +"</RoomPrices>"
                +"</DomesticCheckRoomAvailRequest>"
               +"</Request>";

        System.err.println(ctipCtripService.service(xml));
    }
    
    
    @Test(description = "下单请求单元测试")
    public void domesticSubmitNewHotelOrder() throws ZZKServiceException, DocumentException {
        String xml = "<Request><HeaderInfo UserID=\"204\" RequestorId=\"Ctrip.com\" AsyncRequest=\"false\" TimeStamp=\"2016-05-04 10:14:23\">" +
                "<Authentication UserName=\"zhilianjishuzhuanshu\" Password=\"zhilianzhuanshu11!!\" /><" +
                "RequestType Name=\"DomesticSubmitNewHotelOrder\" Version=\"1.0\" /></HeaderInfo><DomesticSubmitNewHotelOrderRequest>" +
                "<OrderID>2323421510</OrderID><InterFaceSendID>45174357</InterFaceSendID><Hotel>4687002</Hotel><HotelName>花莲>花逸宿光民宿（Flower 85 B&B）</HotelName>" +
                "<Arrival>2016-06-17T00:00:00</Arrival><Departure>2016-06-20T00:00:00</Departure><EarlyArrivalTime>2016-06-17T14:00:00</EarlyArrivalTime><LastArrivalTime>2016-06-17T23:59:00</LastArrivalTime><Person>1</Person><Notice> </Notice><IsHoldRoom>F</IsHoldRoom><Guests><GuestEntity><FirstName>JUE</FirstName><LastName>WANG</LastName><ChinesName>王珏</ChinesName></GuestEntity></Guests><MobilePhone>10106666</MobilePhone><Currency>RMB</Currency><Amount>934</Amount><CNYAmount>934</CNYAmount><GuaranteeType /><BalanceType>PP</BalanceType><Room>28861835</Room><RoomPerson>2</RoomPerson><RoomName>可爱的猫双人房</RoomName><Quantity>1</Quantity><CostAmount>841</CostAmount><CNYCostAmount>841</CNYCostAmount><RoomPrices><RoomPrice><EffectDate>2016-06-17T00:00:00</EffectDate><Price>332</Price><CNYPrice>0</CNYPrice><Cost>299</Cost><CNYCost>0</CNYCost><BreakFast>0</BreakFast></RoomPrice><RoomPrice><EffectDate>2016-06-18T00:00:00</EffectDate><Price>332</Price><CNYPrice>0</CNYPrice><Cost>299</Cost><CNYCost>0</CNYCost><BreakFast>0</BreakFast></RoomPrice><RoomPrice><EffectDate>2016-06-19T00:00:00</EffectDate><Price>270</Price><CNYPrice>0</CNYPrice><Cost>243</Cost><CNYCost>0</CNYCost><BreakFast>0</BreakFast></RoomPrice></RoomPrices><AddOptionals /><RemarkInfo>{HoldTime:2016-06-17 23:59:00,LateCxlTime:2016-06-04 00:00:00,CxlPenaltyAmount:934.00,Currency:RMB}</RemarkInfo></DomesticSubmitNewHotelOrderRequest></Request>";
        System.err.println(ctipCtripService.service(xml));
    }
    
    @Test(description = "取消订单请求单元测试")
    public void domesticCancelHotelOrder() throws ZZKServiceException, DocumentException {
        String xml = "<Request>"
                      + "<HeaderInfo UserID='25' RequestorId='Ctrip.com' AsyncRequest='false' TimeStamp='2012-8-6 3:54:24'>"
                        + "<Authentication UserName='zhilianjishuzhuanshu' Password='zhilianzhuanshu11!!'/>" 
                        + "<RequestType Name='DomesticCancelHotelOrderRequest' Version='1.0'/>"
                      + "</HeaderInfo>" 
                      + "<DomesticCancelHotelOrderRequest>"
                        + "<OrderID>242822293</OrderID>" 
                        + "<OldOrderID>0</OldOrderID>" 
                        + "<Ori_OrderID>0</Ori_OrderID>" 
                        + "<InterFaceSendID>14112314</InterFaceSendID>" 
                        + "<InterFaceConfirmNO>1829595</InterFaceConfirmNO>" 
                        + "<Hotel>548892</Hotel>" 
                        + "<HotelName>桔子酒店精选(苏州金鸡湖店)</HotelName>" 
                        + "<Arrival>2014-07-05T00:00:00</Arrival>" 
                        + "<Departure>2014-07-06T00:00:00</Departure>"
                        + "<Person>1</Person>"
                        + "<Notice/>"
                        + "<ClientName>李星星</ClientName>"
                        + "<Room>4240140</Room>"
                        + "<RoomName>大床房(限量促销)</RoomName>"
                        + "<Quantity>1</Quantity>"
                        + "<GuaranteeType/>"
                      + "</DomesticCancelHotelOrderRequest>"
                    + "</Request>";
        System.err.println(ctipCtripService.service(xml));
    }
    
    @Test(description = "已经存的hotel and room建立mapping MAP_EXISTING_HOTEL_AND_ROOM_ID")
    public void setMappingInfo_MAP_EXISTING_HOTEL_AND_ROOM_ID() throws ZZKServiceException, DocumentException {
        SetMappingInfoVo setMappingInfoVo = new SetMappingInfoVo();
        setMappingInfoVo.setHotel("436553");
        setMappingInfoVo.setRoom("749540");
        setMappingInfoVo.setRatePlanCode("902548");
        setMappingInfoVo.setHotelGroupHotelCode("328");
        setMappingInfoVo.setHotelGroupRoomTypeCode("3924");
        setMappingInfoVo.setHotelGroupRatePlanCode("3924");
        setMappingInfoVo.setHotelGroupRoomName("三人套房-105(三小床.有窗)");
        setMappingInfoVo.setSetMappingOperateType(SetMappingOperateType.MAP_EXISTING_HOTEL_AND_ROOM_ID);
        ctipCtripService.setMappingInfo(setMappingInfoVo);
    }
    @Test(description = "mapping 不存的子酒店与子房型的关联 REQUEST_A_NEW_CTRIP_HOTEL")
    public void setMappingInfo_REQUEST_A_NEW_CTRIP_HOTEL() throws ZZKServiceException, DocumentException {
        SetMappingInfoVo setMappingInfoVo = new SetMappingInfoVo();
        setMappingInfoVo.setMasterHotel("436553");
        setMappingInfoVo.setMasterRoom("749540");
        setMappingInfoVo.setRatePlanCode("4");
        setMappingInfoVo.setHotelGroupHotelCode("328111");
        setMappingInfoVo.setHotelGroupRoomTypeCode("3924111");
        setMappingInfoVo.setHotelGroupRatePlanCode("3924111");
        setMappingInfoVo.setHotelGroupRoomName("测试测试测试");
        setMappingInfoVo.setSetMappingOperateType(SetMappingOperateType.REQUEST_A_NEW_CTRIP_HOTEL);
        ctipCtripService.setMappingInfo(setMappingInfoVo);
    }
    @Test(description = "删除 UN_MAPPING_ROOM_ID_DO_NOT_DELETE_PRICE")
    public void setMappingInfo_UN_MAPPING_ROOM_ID_DO_NOT_DELETE_PRICE() throws ZZKServiceException, DocumentException {

        SetMappingInfoVo setMappingInfoVo = new SetMappingInfoVo();
        setMappingInfoVo.setHotel("4504433");
        setMappingInfoVo.setRoom("24658619");
        setMappingInfoVo.setRatePlanCode("3924111");
        setMappingInfoVo.setHotelGroupHotelCode("328111");
        setMappingInfoVo.setHotelGroupRoomTypeCode("3924111");
        setMappingInfoVo.setHotelGroupRatePlanCode("3924111");
        setMappingInfoVo.setHotelGroupRoomName("testing testing...");
        setMappingInfoVo.setSetMappingOperateType(SetMappingOperateType.UN_MAPPING_ROOM_ID_DO_NOT_DELETE_PRICE);
        ctipCtripService.setMappingInfo(setMappingInfoVo);
    }
    
    @Test(description = "getHotelInfo")
    public void getHotelInfo() throws ZZKServiceException, DocumentException {
        System.err.println(ctipCtripService.getHotelInfo(2));
    }
    
    @Test(description = "getMappingInfo")
    public void getMappingInfo() throws ZZKServiceException, DocumentException {
        MappingInfoVo mappingInfoEntity=new MappingInfoVo();
        mappingInfoEntity.setGetMappingInfoType(GetMappingInfoType.Appoint);
        List<Integer> hotels=new ArrayList<Integer>();
        hotels.add(816871);
        mappingInfoEntity.setHotels(hotels);
        System.err.println(ctipCtripService.getMappingInfo(mappingInfoEntity));
    }
    
    @Test(description = "getCtripRoomTypeInfo")
    public void getCtripRoomTypeInfo() throws ZZKServiceException, DocumentException {
        HotelGroupInterfaceRoomTypeVo hotelGroupInterfaceRoomTypeVo=new HotelGroupInterfaceRoomTypeVo();
        hotelGroupInterfaceRoomTypeVo.setHotelGroupHotelCode(816871);
        hotelGroupInterfaceRoomTypeVo.setHotelGroupRatePlanCode(816871);
        hotelGroupInterfaceRoomTypeVo.setHotelGroupRoomTypeCode(2282151);
        System.err.println(ctipCtripService.getCtripRoomTypeInfo(hotelGroupInterfaceRoomTypeVo));
    }
}