
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
        String xml = "<Request>"
                +"<HeaderInfo UserID='25' RequestorId='Ctrip.com' AsyncRequest='false' TimeStamp='2012-8-6 3:54:24'>"
                +"<Authentication UserName='zhilianjishuzhuanshu' Password='zhilianzhuanshu11!!'/>"
                +"<RequestType Name='DomesticSubmitNewHotelOrderRequest' Version='1.0'/>"
              +"</HeaderInfo>"
              +"<DomesticSubmitNewHotelOrderRequest>"
                +"<OrderID>244512466</OrderID>"
                +"<InterFaceSendID>14112314</InterFaceSendID>"
                +"<Hotel>4504433</Hotel>"
                +"<HotelName>桔子酒店精选(苏州金鸡湖店)</HotelName>"
                +"<Arrival>2015-05-05T00:00:00</Arrival>"
                +"<Departure>2016-05-06T00:00:00</Departure>"
                +"<EarlyArrivalTime>2016-05-05T18:37:07</EarlyArrivalTime>"
                +"<LastArrivalTime>2016-05-06T23:59:00</LastArrivalTime>"
                +"<Person>2</Person>"
                +"<Notice/>"
                +"<Guests>"
                  +"<GuestEntity>"
                    +"<FirstName>kobe</FirstName>"
                    +"<LastName>bryant</LastName>"
                    +"<ChinesName/>"
                  +"</GuestEntity>"
                  +"<GuestEntity>"
                    +"<FirstName>Robert</FirstName>"
                    +"<LastName>Jiang</LastName>"
                    +"<ChinesName/>"
                  +"</GuestEntity>"
                +"</Guests>"
                +"<MobilePhone>10106666</MobilePhone>"
                +"<Currency>RMB</Currency>"
                +"<Amount>788</Amount>"
                +"<CNYAmount>788</CNYAmount>"
                +"<GuaranteeType>OVER</GuaranteeType>"
                +"<BalanceType>PP</BalanceType>"
                +"<Room>24658621</Room>"
                +"<RoomPerson>2</RoomPerson>"
                +"<RoomName>榻榻米大床房</RoomName>"
                +"<Quantity>1</Quantity>"
                +"<CostAmount>11684</CostAmount>"
                +"<CNYCostAmount>11684</CNYCostAmount>"
                +"<RoomPrices>"
                  +"<RoomPrice>"
                    +"<EffectDate>2016-05-05T00:00:00</EffectDate>"
                    +"<Price>1389</Price>"
                    +"<CNYPrice>0</CNYPrice>"
                    +"<Cost>1342</Cost>"
                    +"<CNYCost>0</CNYCost>"
                    +"<BreakFast>0</BreakFast>"
                  +"</RoomPrice>"
                  +"<RoomPrice>"
                    +"<EffectDate>2016-05-06T00:00:00</EffectDate>"
                    +"<Price>1389</Price>"
                    +"<CNYPrice>0</CNYPrice>"
                    +"<Cost>1342</Cost>"
                    +"<CNYCost>0</CNYCost>"
                    +"<BreakFast>0</BreakFast>"
                  +"</RoomPrice>"
                +"</RoomPrices>"
                +"<RemarkInfo>{HoldTime:2014-07-03T23:59:00,NeedGua:true,LateCxlTime:20 14-07-03T18:00:00,CxlPenaltyAmount:389,Currency:RMB}</RemarkInfo>"
              +"</DomesticSubmitNewHotelOrderRequest>"
            +"</Request>";
        System.err.println(ctipCtripService.service(xml));
    }
    
    @Test(description = "取消订单请求单元测试")
    public void domesticCancelHotelOrder() throws ZZKServiceException, DocumentException {
        String xml = "<?xml version='1.0' encoding='utf-8'?>"
                +"<Request>"
                +"<HeaderInfo UserID='25' RequestorId='Ctrip.com' AsyncRequest='false' TimeStamp='2012-8-6 3:54:24'>"
                  +"<Authentication UserName='zhilianjishuzhuanshu' Password='zhilianzhuanshu11!!'/>" 
                  +"<RequestType Name='DomesticSubmitNewHotelOrderRequest' Version='1.0'/>"
                +"</HeaderInfo>" 
                +"<DomesticSubmitNewHotelOrderRequest>"
                  +"<!--订单号-->" 
                  +"<OrderID>244512466</OrderID>" 
                  +"<!--订单接口发送号,同一订单每次发送编号会不同,接收方需落地保存-->" 
                  +"<InterFaceSendID>14112314</InterFaceSendID>" 
                  +"<!--Ctrip酒店编号,供应商需要转成自己的对应编号 -->" 
                  +"<Hotel>4504433</Hotel>" 
                  +"<!--酒店名-->" 
                  +"<HotelName>桔子酒店精选(苏州金鸡湖店)</HotelName>" 
                  +"<!--入住日期-->" 
                  +"<Arrival>2016-05-03T00:00:00</Arrival>" 
                  +"<!--离店日期-->" 
                  +"<Departure>2016-05-05T00:00:00</Departure>" 
                  +"<!--最早到店时间-->" 
                  +"<EarlyArrivalTime>2016-05-03T18:37:07</EarlyArrivalTime>" 
                  +"<!--最晚到店时间-->" 
                  +"<LastArrivalTime>2016-05-05T23:59:00</LastArrivalTime>" 
                  +"<!--实际入住人数-->" 
                  +"<Person>2</Person>" 
                  +"<!--备注-->" 
                  +"<Notice/>" 
                  +"<Guests>"
                    +"<GuestEntity>"
                      +"<!--入住人英文名-->" 
                      +"<FirstName>kobe</FirstName>" 
                      +"<!--入住人英文姓-->" 
                      +"<LastName>bryant</LastName>" 
                      +"<!--入住人中文姓名-->" 
                      +"<ChinesName/>"
                    +"</GuestEntity>" 
                    +"<GuestEntity>"
                      +"<FirstName>Robert</FirstName>" 
                      +"<LastName>Jiang</LastName>" 
                      +"<ChinesName/>"
                    +"</GuestEntity>"
                  +"</Guests>" 
                  +"<!--携程联系电话-->" 
                  +"<MobilePhone>10106666</MobilePhone>" 
                  +"<!--订单币种-->" 
                  +"<Currency>RMB</Currency>" 
                  +"<!--订单卖价总金额(原币种),预付一般不传或者传0-->" 
                  +"<Amount>78866</Amount>" 
                  +"<!--订单卖价总金额(人民币),预付一般不传或者传0-->" 
                  +"<CNYAmount>78866</CNYAmount>" 
                  +"<!--担保方式-->" 
                  +"<GuaranteeType>OVER</GuaranteeType>" 
                  +"<!--支付方式-->" 
                  +"<BalanceType>PP</BalanceType>" 
                  +"<!--Ctrip房型编号,供应商需要转成自己的对应编号-->" 
                  +"<Room>24658621</Room>" 
                  +"<!--房型最大入住人数-->" 
                  +"<RoomPerson>2</RoomPerson>" 
                  +"<!--房型名称-->" 
                  +"<RoomName>榻榻米大床房</RoomName>" 
                  +"<!--预订间数-->" 
                  +"<Quantity>1</Quantity>" 
                  +"<!--订单底价金额(原币种),现付一般不传或者传0-->" 
                  +"<CostAmount>684</CostAmount>" 
                  +"<!--订单底价金额(人民币),现付一般不传或者传0-->" 
                  +"<CNYCostAmount>684</CNYCostAmount>" 
                  +"<RoomPrices>"
                    +"<!--每个RoomPrice对应一天价格-->" 
                    +"<RoomPrice>"
                      +"<EffectDate>2016-05-03T00:00:00</EffectDate>" 
                      +"<!--订单卖价金额(原币种),预付一般不传或者传0-->" 
                      +"<Price>3899</Price>" 
                      +"<!--订单卖价金额(人民币),预付一般不传或者传0-->" 
                      +"<CNYPrice>0</CNYPrice>" 
                      +"<!--订单底价金额(原币种),现付一般不传或者传0-->" 
                      +"<Cost>342</Cost>" 
                      +"<!--订单底价金额(人民币),现付一般不传或者传0-->" 
                      +"<CNYCost>0</CNYCost>" 
                      +"<!--早餐数量-->" 
                      +"<BreakFast>0</BreakFast>"
                    +"</RoomPrice>" 
                    +"<RoomPrice>"
                      +"<EffectDate>2016-05-04T00:00:00</EffectDate>" 
                      +"<!--订单卖价金额(原币种),预付一般不传或者传0-->" 
                      +"<Price>3899</Price>" 
                      +"<!--订单卖价金额(人民币),预付一般不传或者传0-->" 
                      +"<CNYPrice>0</CNYPrice>" 
                      +"<!--订单底价金额(原币种),现付一般不传或者传0-->" 
                      +"<Cost>342</Cost>" 
                      +"<!--###############返回响应###############-->" 
                      +"<!--订单底价金额(人民币),现付一般不传或者传0-->" 
                      +"<CNYCost>0</CNYCost>" 
                      +"<!--早餐数量-->" 
                      +"<BreakFast>0</BreakFast>"
                    +"</RoomPrice>"
                  +"</RoomPrices>" 
                  +"<!--备注信息-->" 
                  +"<RemarkInfo>{HoldTime:2014-07-03T23:59:00,NeedGua:true,LateCxlTime:20 14-07-03T18:00:00,CxlPenaltyAmount:389,Currency:RMB}</RemarkInfo>"
                +"</DomesticSubmitNewHotelOrderRequest>"
               +"</Request>";

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
        System.err.println(ctipCtripService.getHotelInfo());
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
