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
import com.zizaike.entity.open.alibaba.Hotel;
import com.zizaike.open.bastest.BaseTest;

public class HotelRmqConsumerTest extends BaseTest {
    @Autowired
    private HotelRmqConsumer hotelRmqConsumer;
    @Test
    public void addHotelMessage() throws ZZKServiceException, InterruptedException, ApiException {
            Hotel hotel = new Hotel();
            hotel.setAction(Action.ADD);
            hotel.setOuterId("12345678");
            hotel.setName("自在客总部");
            hotel.setCity((long) 1000111);
            hotel.setAddress("浦东金科路");
            hotel.setLatitude("31.20624");
            hotel.setLongitude("121.60190");
            hotel.setPositionType("G");
            hotel.setTel("4008886232");
            hotelRmqConsumer.reveiveHotelMessage(hotel);
    }
    @Test
    public void updateHotelMessage() throws ZZKServiceException, InterruptedException, ApiException {
        Hotel hotel = new Hotel();
        hotel.setAction(Action.UPDATE);
        hotel.setOuterId("12345678");
        hotel.setName("自在客总部");
        hotel.setCity((long) 1000111);
        hotel.setAddress("浦东金科路");
        hotel.setLatitude("31.20624");
        hotel.setLongitude("121.60190");
        hotel.setPositionType("G");
        hotel.setTel("4008886232");
        hotelRmqConsumer.reveiveHotelMessage(hotel);
    }
    
}
