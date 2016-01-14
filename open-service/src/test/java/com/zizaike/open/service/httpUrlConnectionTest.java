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
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.open.bastest.BaseTest;
import com.zizaike.open.common.util.SoapFastUtil;

/**  
 * ClassName:httpUrlConnectionTest <br/>  
 * Function: xml 连接 <br/>  
 * Date:     2016年1月7日 下午5:29:35 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public class httpUrlConnectionTest extends BaseTest{
    @Test(description = "httpUrl")
    public void xmlSender() throws ZZKServiceException, MalformedURLException {
        URL url = new URL("http://58.221.127.196:8089/hotel/hotel-vendor-commonreceive/commonreceiveservice.asmx");
        String prefix = "soap_template/ctrip";
        String template = "SetRoomPrice.vm";
        String SOAPAction = "http://tempuri.org/CheckMember";
        try {
          String xmlStr = SoapFastUtil.post(null, prefix, template, url, SOAPAction);
          System.err.println(xmlStr);
        } catch (Exception e) {
          e.printStackTrace();
        }
    }
    @Test(description = "大宁查询会员信息")
    public void checkMember() {
      Map model = new HashMap();
      model.put("callUserCode", "TTPOS");
      model.put("callPassword", "TTPOS");
      model.put("telephone", "13757123287");
      // model.put("telephone", "13793395120");
      String urlString = "http://180.168.67.66:13252/crm.asmx";
      URL url = null;
      try {
        url = new URL(urlString);
      } catch (MalformedURLException e) {
        e.printStackTrace();

      }
      String prefix = "soap_template/daning";
      String template = "checkMember.vm";
      String SOAPAction = "http://tempuri.org/CheckMember";
      try {
        String xmlStr = SoapFastUtil.post(model, prefix, template, url, SOAPAction);
        System.err.println(xmlStr);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
   
}
  
