/**  
 * Project Name:open-service  <br/>
 * File Name:RoomTypeMappingDaoImpl.java  <br/>
 * Package Name:com.zizaike.open.dao.impl  <br/>
 * Date:2016年2月24日上午10:42:47  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.dao.impl;  

import org.springframework.stereotype.Repository;

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.core.framework.mybatis.impl.GenericMyIbatisDao;
import com.zizaike.entity.open.OpenChannelType;
import com.zizaike.entity.open.RoomTypeMapping;
import com.zizaike.open.dao.RoomTypeMappingDao;

/**  
 * ClassName:RoomTypeMappingDaoImpl <br/>  
 * Function: 房型. <br/>  
 * Date:     2016年2月24日 上午10:42:47 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
@Repository
public class RoomTypeMappingDaoImpl extends GenericMyIbatisDao<RoomTypeMapping, Integer> implements RoomTypeMappingDao {
    private static final String NAMESPACE = "com.zizaike.open.dao.RoomTypeMappingMapper." ;
    @Override
    public RoomTypeMapping queryByOpenHotelIdAndOpenRoomTypeId(String openHotelId, String openRoomTypeId,OpenChannelType openChannelType)
            throws ZZKServiceException {
        RoomTypeMapping query = new RoomTypeMapping();
        query.setOpenHotelId(openHotelId);
        query.setOpenRoomTypeId(openRoomTypeId);
        query.setChannel(openChannelType);
        RoomTypeMapping result = this.getSqlSession().selectOne(NAMESPACE+"queryByOpenHotelIdAndOpenRoomTypeId", query);
        return result;
    }
    
    @Override
    public RoomTypeMapping queryByRoomTypeId(String roomTypeId)
            throws ZZKServiceException {
        RoomTypeMapping query = new RoomTypeMapping();
        query.setRoomTypeId(roomTypeId);
        RoomTypeMapping result = this.getSqlSession().selectOne(NAMESPACE+"queryByRoomTypeId", query);
        return result;
    }

    @Override
    public void updateByHotelIdAndRoomTypeID(RoomTypeMapping roomTypeMapping) throws ZZKServiceException {
       this.getSqlSession().update(NAMESPACE+"updateByHotelIdAndRoomTypeID", roomTypeMapping);   
    }

    @Override
    public void add(RoomTypeMapping roomTypeMapping) throws ZZKServiceException {
        this.getSqlSession().insert(NAMESPACE+"insertSelective", roomTypeMapping);   
    }

    @Override
    public RoomTypeMapping queryByHotelIdAndRoomTypeId(String hotelId, String roomTypeId,OpenChannelType openChannelType) throws ZZKServiceException {
        RoomTypeMapping query = new RoomTypeMapping();
        query.setHotelId(hotelId);
        query.setRoomTypeId(roomTypeId);
        query.setChannel(openChannelType);
        RoomTypeMapping result = this.getSqlSession().selectOne(NAMESPACE+"queryByHotelIdAndRoomTypeId", query);
        return result;
    }
    
}
  
