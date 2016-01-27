/**  
 * Project Name:open-api  <br/>
 * File Name:RoomUpdateRmqConsumer.java  <br/>
 * Package Name:com.zizaike.open  <br/>
 * Date:2016年1月6日下午2:23:58  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.mq;  

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.XhotelRateplanAddRequest;
import com.taobao.api.request.XhotelRateplanUpdateRequest;
import com.taobao.api.request.XhotelRoomtypeAddRequest;
import com.taobao.api.response.XhotelRateplanAddResponse;
import com.taobao.api.response.XhotelRateplanUpdateResponse;
import com.taobao.api.response.XhotelRoomtypeAddResponse;
import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.open.alibaba.Action;
import com.zizaike.entity.open.alibaba.RoomType;
import com.zizaike.entity.open.alibaba.RoomType;


/**  
 * ClassName:RoomModifyRmqConsumer <br/>  
 * Function: 房型更新. <br/>  
 * Date:     2016年1月6日 下午2:23:58 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
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

    public void addRoomType(RoomType object) throws ApiException, ZZKServiceException {
        LOG.info("addRoomType mqInfo {}", object.toString());
        XhotelRateplanAddRequest req = new XhotelRateplanAddRequest();
        try {
            BeanUtils.copyProperties(req, object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            LOG.error("addRoomType copyProperties exception{}", e);
        }
        LOG.info("addRoomType XhotelRateplanAddRequest {}", req.toString());
        XhotelRateplanAddResponse response = taobaoClient.execute(req, sessionKey);
        LOG.info("addRoomType XhotelRateplanAddResponse {}", response.getBody().toString());
    }

    public void updateRoomType(RoomType object) throws ApiException {
        LOG.info("updateRoomType mqInfo {}", object.toString());
        XhotelRateplanUpdateRequest req = new XhotelRateplanUpdateRequest();
        try {
            BeanUtils.copyProperties(req, object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            LOG.error("updateRoomType copyProperties exception{}", e);
        }
        LOG.info("updateRoomType XhotelRateplanUpdateRequest {}", req.toString());
        XhotelRateplanUpdateResponse response = taobaoClient.execute(req, sessionKey);
        LOG.info("updateRoomType XhotelRateplanUpdateResponse {}", response.getBody().toString());
    }
}
  
