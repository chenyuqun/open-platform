/**  
 * Project Name:open-api  <br/>
 * File Name:CtripSOAPServiceTest.java  <br/>
 * Package Name:com.zizaike.open.basetest  <br/>
 * Date:2016年3月11日下午2:18:38  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.service;  

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.testng.annotations.Test;

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.open.basetest.BaseTest;


/**  
 * ClassName:CtripSOAPServiceTest <br/>  
 * Function: . <br/>  
 * Date:     2016年3月11日 下午2:18:38 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public class CtripSOAPServiceTest extends BaseTest{
    @Test(description = "invoke")
    public void invoke() throws ZZKServiceException {
        //调用WebService
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(CtripSOAPService.class);
        factory.setAddress("http://localhost:8088/CXFWebService/Users");
        
        CtripSOAPService service = (CtripSOAPService) factory.create();
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
        String returnXml = service.invoke(xml, "DomesticCheckRoomAvail");
        System.err.println(returnXml);
    }
}
  
