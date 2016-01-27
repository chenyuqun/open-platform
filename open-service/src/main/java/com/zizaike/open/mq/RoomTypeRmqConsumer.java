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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.XhotelRoomtypeAddRequest;
import com.taobao.api.request.XhotelRoomtypeUpdateRequest;
import com.taobao.api.response.XhotelRoomtypeAddResponse;
import com.taobao.api.response.XhotelRoomtypeUpdateResponse;
import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.open.alibaba.Action;
import com.zizaike.entity.open.alibaba.RoomType;

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
    @Value("${alibaba.sessionKey}")
    private String sessionKey;
    @Autowired
    private TaobaoClient taobaoClient;

    public void reveiveRoomTypeMessage(RoomType roomType) throws ApiException, ZZKServiceException {
        if (roomType == null) {
            throw new IllegalParamterException("roomType is null");
        }
        if (roomType.getAction() == null) {
            throw new IllegalParamterException("roomType.action is null");
        }
        if (roomType.getAction() == Action.ADD) {
            addRoomType(roomType);
        } else if (roomType.getAction() == Action.UPDATE) {
            updateRoomType(roomType);
        }
    }

    public void addRoomType(RoomType object) throws ApiException {
        LOG.info("mqInfo {}", object.toString());
        XhotelRoomtypeAddRequest req = new XhotelRoomtypeAddRequest();
        req.setOuterId(object.getOuterId());
        // req.setHid((long)123456);
        req.setName(object.getName());
        req.setMaxOccupancy(object.getMaxOccupancy());
        req.setArea(object.getArea());
        req.setFloor(object.getFloor());
        req.setBedType(object.getBedType());
        req.setBedSize(object.getBedSize());
        req.setInternet(object.getInternet());
        req.setService(object.getService());
        req.setExtend(object.getExtend());
        req.setWindowType(object.getWindowType());
        req.setOutHid(object.getOutHid());
        ;
        req.setPics(object.getPics());
        LOG.info("XhotelRoomtypeAddRequest {}", req.toString());
        XhotelRoomtypeAddResponse response = taobaoClient.execute(req, sessionKey);
        LOG.info("XhotelRoomtypeAddResponse {}", response.getBody().toString());
    }

    public void updateRoomType(RoomType object) throws ApiException {
        XhotelRoomtypeUpdateRequest req = new XhotelRoomtypeUpdateRequest();
        req.setOuterId(object.getOuterId());
        // req.setHid((long)123456);
        req.setName(object.getName());
        req.setMaxOccupancy(object.getMaxOccupancy());
        req.setArea(object.getArea());
        req.setFloor(object.getFloor());
        req.setBedType(object.getBedType());
        req.setBedSize(object.getBedSize());
        req.setInternet(object.getInternet());
        req.setService(object.getService());
        req.setExtend(object.getExtend());
        req.setWindowType(object.getWindowType());
        req.setPics(object.getPics());
        LOG.info("XhotelRoomtypeUpdateRequest {}", req.toString());
        XhotelRoomtypeUpdateResponse response = taobaoClient.execute(req, sessionKey);
        LOG.info("XhotelRoomtypeUpdateResponse {}", response.getBody().toString());
    }
}
