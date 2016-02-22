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

}
