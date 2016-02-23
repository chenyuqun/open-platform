package com.zizaike.open.service;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.is.open.CtripService;
import com.zizaike.open.bastest.BaseTest;

public class CtripServiceTest extends BaseTest {
    @Autowired
    private CtripService ctipCtripService;
    
    @Test(description = "试单请求单元测试")
    public void testValidateRQ() throws ZZKServiceException, DocumentException {
        String xml = "<Request>"
                + "<HeaderInfo UserID='25' RequestorId='Ctrip.com' AsyncRequest='false' TimeStamp='2012-8-6 3:54:24'>"
                + "<Authentication UserName='zhilianjishuzhuanshu' Password='zhilianzhuanshu11!!'/>"
                + "<RequestType Name='DomesticCheckRoomAvailRequest' Version='1.0'/>" + "</HeaderInfo>"
                + "<DomesticCheckRoomAvailRequest>" + "<!--Ctrip酒店编号,供应商需要转成自己的对应编号 -->" + "<Hotel>778821</Hotel>"
                + "<!--到店日期-->" + "<Arrival>2014-07-05T00:00:00</Arrival>" + "<!--离店日期-->"
                + "<Departure>2014-07-07T00:00:00</Departure>" + "<!--Ctrip房型编号,供应商需要转成自己的对应编号-->"
                + "<Room>4809658</Room>" + "<!--预订数量-->" + "<RoomNumber>1</RoomNumber>" + "<!--币种-->"
                + "<CurrencyCode>RMB</CurrencyCode>" + "<!--入住人数-->" + "<Person>1</Person>" + "<!--付款类型-->"
                + "<BalanceType>FG</BalanceType>" + "<RoomPrices>" + "<!--每个RoomPrice对应一天价格-->" + "<RoomPrice>"
                + "<!--入住日期-->" + "<EffectDate>2014-07-05T00:00:00</EffectDate>" + "<!--卖价-->" + "<Price>618</Price>"
                + "<!--汇率后的卖价-->" + "<CNYPrice>0</CNYPrice>" + "<!--底价-->" + "<Cost>525</Cost>" + "<!--汇率后的底价-->"
                + "<CNYCost>0</CNYCost>" + "<!--含早餐数-->" + "<BreakFast>0</BreakFast>" + "</RoomPrice>" + "<RoomPrice>"
                + "<!--入住日期-->" + "<EffectDate>2014-07-06T00:00:00</EffectDate>" + "<!--卖价-->" + "<Price>618</Price>"
                + "<!--汇率后的卖价-->" + "<CNYPrice>0</CNYPrice>" + "<!--底价-->" + "<Cost>525</Cost>" + "<!--汇率后的底价-->"
                + "<CNYCost>0</CNYCost>" + "<!--含早餐数-->" + "<BreakFast>0</BreakFast>"+ "</RoomPrice>" 
                + "</RoomPrices>" + "</DomesticCheckRoomAvailRequest>" + "</Request>";
        System.err.println(ctipCtripService.service(xml));
    }
    
   
    
}
