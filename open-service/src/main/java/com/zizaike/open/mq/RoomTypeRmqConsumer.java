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
import com.zizaike.entity.open.alibaba.RoomType;
import com.zizaike.open.domain.event.RoomTypeApplicationEvent;

/**
 * ClassName:RoomModifyRmqConsumer <br/>
 * Function: 房型更新. <br/>
 * Date: 2016年1月6日 下午2:23:58 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 * @see
 */
@Service("roomTypeRmqConsumer")
public class RoomTypeRmqConsumer {
    protected final Logger LOG = LoggerFactory.getLogger(RoomTypeRmqConsumer.class);
    @Autowired
    ApplicationContext applicationContext;
    public void reveiveRoomTypeMessage(RoomType roomType) throws ApiException, ZZKServiceException {
        if (roomType == null) {
            throw new IllegalParamterException("roomType is null");
        }
        if (roomType.getAction() == null) {
            throw new IllegalParamterException("roomType.action is null");
        }
        applicationContext.publishEvent(new RoomTypeApplicationEvent(roomType));
    }
}
