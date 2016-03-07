/**  
 * Project Name:open-api  <br/>
 * File Name:httpUrlConnectionTest.java  <br/>
 * Package Name:com.zizaike.open.service  <br/>
 * Date:2016年1月7日下午5:29:35  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
 */

package com.zizaike.open.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.zizaike.core.common.util.http.HttpProxyUtil;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.open.bastest.BaseTest;

/**
 * ClassName:httpUrlConnectionTest <br/>
 * Function: http 连接 <br/>
 * Date: 2016年1月7日 下午5:29:35 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 * @see
 */
public class httpUtilTest extends BaseTest {
    @Autowired
    private HttpProxyUtil httpProxy;

    @Test(description = "httpUrl")
    public void xmlSender() throws ZZKServiceException, IOException {
        Map map = new HashMap();
        map.put("roomTypeId", "398");
        map.put("checkIn", "2016-03-14");
        map.put("checkOut", "2016-03-15");
        
        System.err.println(httpProxy.httpGet("http://api.test.zizaike.com/open/alitrip/validateRQ", map));

    }
    @Test(description = "httpUrl")
    public void httpPostXml() throws ZZKServiceException, IOException {
        String xml = "<soap:Envelope xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema'>"
  +"<soap:Body>"
    +"<AdapterRequest xmlns='http://www.ctrip.com/'>"
    +"<requestXml>"
    +"&lt;Request&gt;"
    +"&lt;HeaderInfo UserID='204' RequestorId='Ctrip.com' AsyncRequest='false' TimeStamp='2016-03-07 11:58:42'&gt;&lt;Authentication UserName='zhilianjishuzhuanshu' Password='zhilianzhuanshu11!!'/&gt;&lt;RequestType Name='SetMappingInfo' Version='1.2'/&gt;&lt;/HeaderInfo&gt;"
    +"&lt;SetMappingInfoRequest&gt;"
    +"&lt;SupplierID&gt;55&lt;/SupplierID&gt;"
    +"&lt;MasterHotel&gt;436553&lt;/MasterHotel&gt;"
    +"&lt;MasterRoom&gt;749540&lt;/MasterRoom&gt;"
    +"&lt;RatePlanCode&gt;4&lt;/RatePlanCode&gt;"
    +"&lt;HotelGroupRoomTypeCode&gt;3924111&lt;/HotelGroupRoomTypeCode&gt;"
    +"&lt;HotelGroupHotelCode&gt;328111&lt;/HotelGroupHotelCode&gt;"
    +"&lt;HotelGroupRatePlanCode&gt;3924111&lt;/HotelGroupRatePlanCode&gt;"
    +"&lt;HotelGroupRoomName&gt;测试测试测试123&lt;/HotelGroupRoomName&gt;"
    +"&lt;BalanceType&gt;PP&lt;/BalanceType&gt;"
    +"&lt;MappingType&gt;1&lt;/MappingType&gt;"
    +"&lt;SetType&gt;1&lt;/SetType&gt;"
    +"&lt;/SetMappingInfoRequest&gt;"
    +" &lt;/Request&gt;"
    +"</requestXml>"
    +" </AdapterRequest>"
    +"</soap:Body>"
    +"</soap:Envelope>";
        try {
            System.err.println(httpProxy.httpPostXml("http://58.221.127.196:8089/hotel/hotel-vendor-commonreceive/commonreceiveservice.asmx", xml));
        } catch (Exception e) {
            e.printStackTrace();  
            
        }
        
    }

}
