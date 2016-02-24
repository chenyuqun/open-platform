/**  
 * Project Name:open-service  <br/>
 * File Name:RoomTypeMappingServiceImpl.java  <br/>
 * Package Name:com.zizaike.open.service.impl  <br/>
 * Date:2016年2月24日上午10:49:13  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.service.impl;  

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.core.framework.exception.open.RoomTypeNotMappingException;
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
    public RoomTypeMapping queryByHotelIdAndOpenRoomTypeId(String openHotelId, String openRoomTypeId)
            throws ZZKServiceException {
        if(StringUtils.isEmpty(openHotelId)){
            throw new IllegalParamterException("openHotelId is null");
        }
        if(StringUtils.isEmpty(openRoomTypeId)){
            throw new IllegalParamterException("openRoomTypeId is null");
        }
        RoomTypeMapping query = roomTypeMappingDao.queryByHotelIdAndOpenRoomTypeId(openHotelId, openRoomTypeId);
        if(query == null){
            throw new RoomTypeNotMappingException();
        }
        return query;
    }

}
  
