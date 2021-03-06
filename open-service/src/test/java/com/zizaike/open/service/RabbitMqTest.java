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

import com.taobao.api.request.XhotelUpdateRequest;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.open.alibaba.Action;
import com.zizaike.entity.open.alibaba.Hotel;
import com.zizaike.entity.open.alibaba.RatePlan;
import com.zizaike.entity.open.alibaba.Rates;
import com.zizaike.entity.open.alibaba.RoomType;
import com.zizaike.open.bastest.BaseTest;

public class RabbitMqTest extends BaseTest {
    @Autowired
    AmqpTemplate modifyHotelTemplate;
    @Autowired
    AmqpTemplate modifyRoomTypeTemplate;
    @Autowired
    AmqpTemplate modifyRatesTemplate;
    @Autowired
    AmqpTemplate modifyRatePlanTemplate;
    XhotelUpdateRequest req = new XhotelUpdateRequest();
   
    @Test(description = "rabbitmq hotel convertAndSend 测试")
    public void hotelConvertAndSend() throws ZZKServiceException, InterruptedException {
            Hotel hotel = new Hotel();
            hotel.setAction(Action.ADD);
            hotel.setOuterId("12345678");
            hotel.setName("自在客总部");
            hotel.setCity((long) 1100128);
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
        
        RatePlan ratePlan=new RatePlan();
        ratePlan.setAction(Action.ADD);
        ratePlan.setRateplanCode("12345AAAAA");
        ratePlan.setName("含早提前3天");
        ratePlan.setPaymentType(1L);
        ratePlan.setBreakfastCount(1L);
        ratePlan.setCancelPolicy("{\"cancelPolicyType\":1}");
        ratePlan.setStatus(1L);       
        modifyRatePlanTemplate.convertAndSend(ratePlan);
    }
    
    @Test(description = "rabbitmq rates convertAndSend 测试")
    public void ratesConvertAndSend() throws ZZKServiceException, InterruptedException {
        Rates rates=new Rates();
        //XhotelRatesUpdateRequest req = new XhotelRatesUpdateRequest();
//        rates.setRateInventoryPrcie("[{\"out_rid\":\"12345678_123\",\"rateplan_code\":\"ZIZAIKE_1\",\"vendor\":\"\","
//                + "\"data\":{\"use_room_inventory\":false,\"inventory_price\":[{\"date\":2016-01-28,\"quota\":3,\"price\":1500,\"status\":1},{\"date\":2016-01-29,\"quota\":1,\"price\":2500,\"status\":1}]}},"
//                + "{\"out_rid\":\"12345678_124\",\"rateplan_code\":\"ZIZAIKE_2\",\"vendor\":\"\","
//                + "\"data\":{\"use_room_inventory\":false,\"inventory_price\":[{\"date\":2016-01-28,\"quota\":10,\"price\":2000,\"status\":1},{\"date\":2016-01-29,\"quota\":10,\"price\":4000,\"status\":1}]}}]");
//        rates.setAction(Action.ADD);
        modifyRatesTemplate.convertAndSend(rates);
    }
}
