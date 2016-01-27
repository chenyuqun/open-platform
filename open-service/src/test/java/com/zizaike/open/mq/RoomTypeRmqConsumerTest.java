/**  
 * Project Name:open-api  <br/>
 * File Name:RabbitMqTest.java  <br/>
 * Package Name:com.zizaike.open.service  <br/>
 * Date:2016年1月4日下午7:18:26  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
 */

package com.zizaike.open.mq;


import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.taobao.api.ApiException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.open.alibaba.Action;
import com.zizaike.entity.open.alibaba.RoomType;
import com.zizaike.open.bastest.BaseTest;

public class RoomTypeRmqConsumerTest extends BaseTest {
    @Autowired
    private RoomTypeRmqConsumer roomTypeRmqConsumer;
    @Test(description = "rabbitmq hotel convertAndSend 测试")
    public void reveiveRoomTypeModifyMessage() throws ZZKServiceException, InterruptedException, ApiException {
        RoomType roomType = new RoomType();
        roomType.setAction(Action.ADD);
        roomType.setOuterId("534_123");
        //req.setHid((long)123456);
        roomType.setName("阮佳佳的别墅");
        roomType.setMaxOccupancy(2L);
        roomType.setArea("10平方米");
        roomType.setFloor("3-5层");
        roomType.setBedType("大床");
        roomType.setBedSize("2.1米");
        roomType.setInternet("A");
        roomType.setService("{\"bar\":true,\"catv\":false,\"ddd\":false,\"idd\":false,\"pubtoilet\":false,\"toilet\":false}");
        roomType.setExtend("空");
        roomType.setWindowType(1L);
        roomType.setOutHid("534");
            roomTypeRmqConsumer.reveiveRoomTypeModifyMessage(roomType);
    }
    
}
