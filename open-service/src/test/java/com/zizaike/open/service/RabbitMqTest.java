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
import com.zizaike.entity.open.alibaba.Action;
import com.zizaike.entity.open.alibaba.Hotel;
import com.zizaike.entity.open.alibaba.RoomType;
import com.zizaike.open.bastest.BaseTest;

public class RabbitMqTest extends BaseTest {
    @Autowired
    AmqpTemplate modifyHotelTemplate;
    @Autowired
    AmqpTemplate modifyRoomTypeTemplate;

    @Test(description = "rabbitmq hotel convertAndSend 测试")
    public void hotelConvertAndSend() throws ZZKServiceException, InterruptedException {
            Hotel hotel = new Hotel();
            hotel.setAction(Action.ADD);
            hotel.setAddress("中国 上海,测试");
            modifyHotelTemplate.convertAndSend(hotel);
    }
    @Test(description = "rabbitmq roomType convertAndSend 测试")
    public void roomTypeConvertAndSend() throws ZZKServiceException, InterruptedException {
        RoomType roomType = new RoomType();
        roomType.setArea("中国测试");
        modifyRoomTypeTemplate.convertAndSend(roomType);
    }
//    @Test(description = "rabbitmq receiveAndConvert 测试")
//    public void receiveAndConvert() throws ZZKServiceException, InterruptedException {
//        System.err.println(modifyRoomTemplate.receiveAndConvert());
//    }
}
