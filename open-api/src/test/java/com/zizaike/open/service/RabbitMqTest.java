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
import com.zizaike.entity.solr.Room;
import com.zizaike.open.basetest.BaseTest;

public class RabbitMqTest extends BaseTest {
    @Autowired
    AmqpTemplate modifyRoomTemplate;
    @Autowired
    AmqpTemplate modifyOrderTemplate;

    @Test(description = "rabbitmq room convertAndSend 测试")
    public void roomConvertAndSend() throws ZZKServiceException, InterruptedException {
            Room room = new Room();
            room.setUserAddress("测试地址 room");
            modifyRoomTemplate.convertAndSend(room);
    }
    @Test(description = "rabbitmq order convertAndSend 测试")
    public void orderConvertAndSend() throws ZZKServiceException, InterruptedException {
        Room room = new Room();
        room.setUserAddress("测试地址 order");
        modifyOrderTemplate.convertAndSend(room);
    }
//    @Test(description = "rabbitmq receiveAndConvert 测试")
//    public void receiveAndConvert() throws ZZKServiceException, InterruptedException {
//        System.err.println(modifyRoomTemplate.receiveAndConvert());
//    }
}
