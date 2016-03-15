/**  
 * Project Name:open-service  <br/>
 * File Name:RoomTypeMappingServiceImpl.java  <br/>
 * Package Name:com.zizaike.open.service.impl  <br/>
 * Date:2016年2月24日上午10:49:13  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.service.impl;  

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.core.framework.exception.open.RoomTypeNotMappingException;
import com.zizaike.entity.open.OpenChannelType;
import com.zizaike.entity.open.RoomTypeMapping;
import com.zizaike.is.open.RoomTypeMappingService;
import com.zizaike.open.dao.RoomTypeMappingDao;

/**  
 * ClassName:RoomTypeMappingServiceImpl <br/>  
 * Function: 房型关系. <br/>  
 * Date:     2016年2月24日 上午10:49:13 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
@Service
public class RoomTypeMappingServiceImpl implements RoomTypeMappingService {
    @Autowired
    private RoomTypeMappingDao roomTypeMappingDao;
    @Override
    public RoomTypeMapping queryByHotelIdAndOpenRoomTypeId(String openHotelId, String openRoomTypeId,OpenChannelType openChannelType)
            throws ZZKServiceException {
        if(StringUtils.isEmpty(openHotelId)){
            throw new IllegalParamterException("openHotelId is null");
        }
        if(StringUtils.isEmpty(openRoomTypeId)){
            throw new IllegalParamterException("openRoomTypeId is null");
        }
        if(openChannelType==null){
            throw new IllegalParamterException("openChannelType is null");
        }
        RoomTypeMapping query = roomTypeMappingDao.queryByOpenHotelIdAndOpenRoomTypeId(openHotelId, openRoomTypeId,openChannelType);
        if(query == null){
            throw new RoomTypeNotMappingException();
        }
        return query;
    }
    @Override
    public RoomTypeMapping queryByRoomTypeId(String roomTypeId) throws ZZKServiceException {
        if(StringUtils.isEmpty(roomTypeId)){
            throw new IllegalParamterException("roomTypeId is null");
        }
        RoomTypeMapping query = roomTypeMappingDao.queryByRoomTypeId(roomTypeId);
        if(query == null){
            throw new RoomTypeNotMappingException();
        }
        return query;
    }
    @Override
    public void addOrUpdate(RoomTypeMapping roomTypeMapping) throws ZZKServiceException {
        if(roomTypeMapping==null){
            throw new IllegalParamterException("roomTypeMapping is not null");
        }
        if(StringUtils.isEmpty(roomTypeMapping.getHotelId())){
            throw new IllegalParamterException("roomTypeMapping getHotelId is not null");
        }
        if(StringUtils.isEmpty(roomTypeMapping.getOpenRoomTypeId())){
            throw new IllegalParamterException("roomTypeMapping getOpenRoomTypeId is not null");
        }
        if(roomTypeMapping.getChannel()==null){
            throw new IllegalParamterException("roomTypeMapping getChannel is not null");
        }
        if(StringUtils.isEmpty(roomTypeMapping.getOpenHotelId())){
            throw new IllegalParamterException("roomTypeMapping getOpenHotelId is not null");
        }
        if(StringUtils.isEmpty(roomTypeMapping.getOpenRoomTypeId())){
            throw new IllegalParamterException("roomTypeMapping getOpenRoomTypeId is not null");
        }
        roomTypeMapping.setActive(1);
        roomTypeMapping.setCreateAt(new Date());
        roomTypeMapping.setUpdateAt(new Date());
        RoomTypeMapping query = roomTypeMappingDao.queryByHotelIdAndRoomTypeId(roomTypeMapping.getHotelId(), roomTypeMapping.getRoomTypeId(), roomTypeMapping.getChannel());
        if(query == null){
            roomTypeMappingDao.add(roomTypeMapping);
        }else{
            roomTypeMappingDao.updateByHotelIdAndRoomTypeID(roomTypeMapping);
        }
    } 

}
  
