/**  
 * Project Name:open-api  <br/>
 * File Name:RabbitMqTest.java  <br/>
 * Package Name:com.zizaike.open.service  <br/>
 * Date:2016年1月4日下午7:18:26  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
 */

package com.zizaike.open.service;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.recommend.AreaLevel;
import com.zizaike.entity.recommend.Loctype;
import com.zizaike.open.basetest.BaseTest;

public class RabbitMqTest extends BaseTest {
    @Autowired
    AmqpTemplate modifyRoomTemplate;

    @Test(description = "rabbitmq测试")
    public void convertAndSend() throws ZZKServiceException, InterruptedException {
        for (int i = 0; i < 1000; i++) {
            Loctype loc = new Loctype();
            loc.setAreaLevel(AreaLevel.CITY);
            loc.setDestId(i);
            modifyRoomTemplate.convertAndSend(loc);
        }
     
    }
}
