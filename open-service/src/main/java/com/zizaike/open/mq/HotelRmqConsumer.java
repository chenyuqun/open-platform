/**  
 * Project Name:open-api  <br/>
 * File Name:RoomUpdateRmqConsumer.java  <br/>
 * Package Name:com.zizaike.open  <br/>
 * Date:2016年1月6日下午2:23:58  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
 */

package com.zizaike.open.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.open.alibaba.Hotel;
import com.zizaike.is.open.AreaService;
import com.zizaike.open.domain.event.HotelApplicationEvent;

/**
 * ClassName:RoomModifyRmqConsumer <br/>
 * Function: 房间信息更新. <br/>
 * Date: 2016年1月6日 下午2:23:58 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 * @see
 */
@Service("hotelRmqConsumer")
public class HotelRmqConsumer {
    protected final Logger LOG = LoggerFactory.getLogger(HotelRmqConsumer.class);
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    private AreaService areaService;
    
    public void reveiveHotelMessage(Hotel hotel) throws ApiException, ZZKServiceException {
        if (hotel == null) {
            throw new IllegalParamterException("hotel is null");
        }
        if (hotel.getAction() == null) {
            throw new IllegalParamterException("hotel.action is null");
        }
        if(hotel.getCity()!=null){
            hotel.setCity(Long.valueOf(areaService.getAreaCodeTypeCode(""+hotel.getCity())));
        }
        applicationContext.publishEvent(new HotelApplicationEvent(hotel));
    }
}
