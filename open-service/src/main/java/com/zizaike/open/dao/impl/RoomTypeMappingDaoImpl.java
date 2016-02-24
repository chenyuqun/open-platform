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
    public RoomTypeMapping queryByHotelIdAndOpenRoomTypeId(String openHotelId, String openRoomTypeId)
            throws ZZKServiceException {
        RoomTypeMapping query = new RoomTypeMapping();
        query.setOpenHotelId(openHotelId);
        query.setOpenRoomTypeId(openRoomTypeId);
        RoomTypeMapping result = this.getSqlSession().selectOne(NAMESPACE+"queryByHotelIdAndOpenRoomTypeId", query);
        return result;
    }

}
  
