/**  
 * Project Name:open-service  <br/>
 * File Name:BaseInfoDao.java  <br/>
 * Package Name:com.zizaike.open.dao  <br/>
 * Date:2016年3月7日下午6:42:21  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.dao;  

import com.zizaike.core.framework.springext.database.Slave;
import com.zizaike.entity.open.QunarRoomInfoDto;
import com.zizaike.entity.open.RoomInfoDto;

/**  
 * ClassName:BaseInfoDao <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2016年3月7日 下午6:42:21 <br/>  
 * @author   alex  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public interface BaseInfoDao {
    /**
     * 
     * getRefundAndBreakfast: 退款政策和早餐<br/>  
     */
    @Slave
    RoomInfoDto getRefundAndBreakfast(int nid);

    /**
     * getQunarRoomInfoDto
     * @param nid
     * @return
     */
    @Slave
    QunarRoomInfoDto getQunarRoomInfoDto(int nid);
}
  
