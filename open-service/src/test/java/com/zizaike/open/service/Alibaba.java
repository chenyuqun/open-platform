/**  
 * Project Name:open-service  <br/>
 * File Name:Alibaba.java  <br/>
 * Package Name:com.zizaike.open.service  <br/>
 * Date:2016年1月11日下午4:51:02  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.service;  

import org.testng.annotations.Test;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.XhotelGetRequest;
import com.taobao.api.response.XhotelGetResponse;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.open.bastest.BaseTest;

/**  
 * ClassName:Alibaba <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2016年1月11日 下午4:51:02 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public class Alibaba extends BaseTest{
    @Test(description = "XhotelGet")
    public void xhotelGet() throws ZZKServiceException, ApiException {
        String url="http://gw.api.tbsandbox.com/router/rest";
        String secret="sandboxe7755267c0ea85fa5526bfb35";
        String appkey="1023297477";
        String sessionKey="6100030eeabdd52c1036901aa8eb229d6eaf361c1b270c33651882626";
        TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
        XhotelGetRequest req = new XhotelGetRequest();
        req.setOuterId("1");
        XhotelGetResponse response = client.execute(req , sessionKey);
        System.out.println(response.getBody());

        
    }
}
  
