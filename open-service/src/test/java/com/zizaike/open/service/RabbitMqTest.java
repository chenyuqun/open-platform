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

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.XhotelRoomtypeAddRequest;
import com.taobao.api.request.XhotelUpdateRequest;
import com.taobao.api.response.XhotelRoomtypeAddResponse;
import com.taobao.api.response.XhotelUpdateResponse;
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
    XhotelUpdateRequest req = new XhotelUpdateRequest();
   
    @Test(description = "rabbitmq hotel convertAndSend 测试")
    public void hotelConvertAndSend() throws ZZKServiceException, InterruptedException {
            Hotel hotel = new Hotel();
            hotel.setAction(Action.ADD);
            hotel.setOuterId("12345678");
            hotel.setName("自在客总部");
            hotel.setCity((long) 310100);
            hotel.setAddress("浦东金科路");
            hotel.setLatitude("31.20624");
            hotel.setLongitude("121.60190");
            hotel.setPositionType("G");
            hotel.setTel("4008886232");
            modifyHotelTemplate.convertAndSend(hotel);
    }
    @Test(description = "rabbitmq roomType convertAndSend 测试")
    public void roomTypeConvertAndSend() throws ZZKServiceException, InterruptedException {
        
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
//        req.setSrid(123123L);
        roomType.setOutHid("534");
        //req.setVendor("taobao");
        req.setPics("[{\"url\":\"http://http://img1.zzkcdn.com/c9495cb6542a1ecc3b88a117df4a750dzzkcopr/2000x1500.jpg-homepic800x600.jpg\",\"ismain\":\"true\"}]");
        modifyRoomTypeTemplate.convertAndSend(roomType);
    }
    
    @Test(description = "rabbitmq ratePlan convertAndSend 测试")
    public void ratePlanConvertAndSend() throws ZZKServiceException, InterruptedException {
        
        RoomType roomType = new RoomType();
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
//        req.setSrid(123123L);
        roomType.setOutHid("534");
        //req.setVendor("taobao");
        req.setPics("[{\"url\":\"http://http://img1.zzkcdn.com/c9495cb6542a1ecc3b88a117df4a750dzzkcopr/2000x1500.jpg-homepic800x600.jpg\",\"ismain\":\"true\"}]");
        modifyRoomTypeTemplate.convertAndSend(roomType);
    }
//    @Test(description = "rabbitmq receiveAndConvert 测试")
//    public void receiveAndConvert() throws ZZKServiceException, InterruptedException {
//        System.err.println(modifyRoomTemplate.receiveAndConvert());
//    }
}
