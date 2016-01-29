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
import com.taobao.api.request.XhotelAddRequest;
import com.taobao.api.request.XhotelUpdateRequest;
import com.taobao.api.response.XhotelAddResponse;
import com.taobao.api.response.XhotelUpdateResponse;
import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.open.alibaba.Action;
import com.zizaike.entity.open.alibaba.Hotel;
import com.zizaike.is.open.AreaService;

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
    @Value("${alibaba.sessionKey}")
    private String sessionKey;
    @Autowired
    private TaobaoClient taobaoClient;
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
        if (hotel.getAction() == Action.ADD) {
            addHotel(hotel);
        } else if (hotel.getAction() == Action.UPDATE) {
            updateHotel(hotel);
        }
    }

    public void addHotel(Hotel object) throws ApiException, ZZKServiceException {
        LOG.info("addHotel mqInfo {}", object.toString());
        XhotelAddRequest req = new XhotelAddRequest();
        try {
            BeanUtils.copyProperties(req, object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            LOG.error("addHotel copyProperties exception{}", e);
        }
        // req.setOuterId(object.getOuterId());
        // req.setName(object.getName());
        // req.setCity(object.getCity());
        // req.setAddress(object.getAddress());
        // req.setLatitude(object.getLatitude());
        // req.setLongitude(object.getLongitude());
        // req.setPositionType(object.getPositionType());
        // req.setTel(object.getTel());
        // req.setService("{\"receiveForeignGuests\":\"true\",\"morningCall\":\"true\",\"breakfast\":\"true\"}");
        // req.setHotelPolicies("{\"children_age_from\":\"2\",\"children_age_to\":\"3\",\"children_stay_free\":\"True\",\"infant_age\":\"1\",\"min_guest_age\":\"4\"}");
        LOG.info("addHotel XhotelAddRequest {}", req.toString());
        XhotelAddResponse response = taobaoClient.execute(req, sessionKey);
        LOG.info("addHotel XhotelAddResponse {}", response.toString());
    }

    public void updateHotel(Hotel object) throws ApiException {
        LOG.info("updateHotel mqInfo {}", object.toString());
        XhotelUpdateRequest req = new XhotelUpdateRequest();
        try {
            BeanUtils.copyProperties(req, object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            LOG.error("updateHotel copyProperties exception{}", e);
        }
        // req.setOuterId(object.getOuterId());
        // req.setName(object.getName());
        // req.setCity(object.getCity());
        // req.setAddress(object.getAddress());
        // req.setLatitude(object.getLatitude());
        // req.setLongitude(object.getLongitude());
        // req.setPositionType(object.getPositionType());
        // req.setTel(object.getTel());
        // req.setService("{\"receiveForeignGuests\":\"true\",\"morningCall\":\"true\",\"breakfast\":\"true\"}");
        // req.setHotelPolicies("{\"children_age_from\":\"2\",\"children_age_to\":\"3\",\"children_stay_free\":\"True\",\"infant_age\":\"1\",\"min_guest_age\":\"4\"}");
        LOG.info("updateHotel XhotelAddRequest {}", req.toString());
        XhotelUpdateResponse response = taobaoClient.execute(req, sessionKey);
        LOG.info("updateHotel response {}", response.toString());
    }
}
