/**  
 * Project Name:open-service  <br/>
 * File Name:UserDao.java  <br/>
 * Package Name:com.zizaike.open.dao  <br/>
 * Date:2016年1月20日下午2:34:09  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.dao;  

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.core.framework.springext.database.Slave;
import com.zizaike.entity.open.RoomTypeMapping;

/**
 * 
 * ClassName: RoomTypeMappingDao <br/>  
 * Function: 房型映射. <br/>  
 * date: 2016年2月24日 上午10:38:13 <br/>  
 *  
 * @author snow.zhang  
 * @version   
 * @since JDK 1.7
 */
public interface RoomTypeMappingDao {
    /**
     * 
     * queryByHotelIdAndOpenRoomTypeId. <br/>  
     *  
     * @author snow.zhang  
     * @param openHotelId
     * @param openRoomTypeId
     * @return
     * @throws ZZKServiceException  
     * @since JDK 1.7
     */
    @Slave
    RoomTypeMapping queryByHotelIdAndOpenRoomTypeId(String openHotelId,String openRoomTypeId) throws ZZKServiceException;
}
  
