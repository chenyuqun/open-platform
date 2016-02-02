/**  
 * Project Name:open-api  <br/>
 * File Name:httpUrlConnectionTest.java  <br/>
 * Package Name:com.zizaike.open.service  <br/>
 * Date:2016年1月7日下午5:29:35  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.service;  

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.zizaike.core.common.util.http.SoapFastUtil;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.open.bastest.BaseTest;

/**  
 * ClassName:httpUrlConnectionTest <br/>  
 * Function: xml 连接 <br/>  
 * Date:     2016年1月7日 下午5:29:35 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public class CtripConnectionTest extends BaseTest{
    @Autowired
    private SoapFastUtil soapFastUtil;
    @Test(description = "获得房型对照")
    public void xmlSender() throws ZZKServiceException, MalformedURLException {
        String url = "http://58.221.127.196:8089/hotel/hotel-vendor-commonreceive/commonreceiveservice.asmx";
        String prefix = "soap_template/ctrip";
        String template = "GetCtripRoomTypeInfo.vm";
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        Map map = new HashMap();
        map.put("userName", "zhilianjishuzhuanshu");
        map.put("password", "zhilianzhuanshu11!!");
        map.put("userId", 204);
        map.put("date", dateString);
        map.put("hotelGroupRoomTypeCode", 228215);
        map.put("hotelGroupHotelCode", 81687);
        map.put("hotelGroupRatePlanCode", "");
        try {
            long start = System.currentTimeMillis();
                String xmlStr = soapFastUtil.post(map, prefix, template, url, "");
                System.err.println(xmlStr);
              System.err.println(System.currentTimeMillis() - start);  
        } catch (Exception e) {
          e.printStackTrace();
        }
    }
}
  
