/**  
 * Project Name:open-service  <br/>
 * File Name:BaseInfoDaoImpl.java  <br/>
 * Package Name:com.zizaike.open.dao.impl  <br/>
 * Date:2016年3月7日下午6:48:01  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.dao.impl;  

import com.zizaike.entity.open.QunarRoomInfoDto;
import com.zizaike.entity.open.OpenDiscount;
import org.springframework.stereotype.Repository;

import com.zizaike.core.framework.mybatis.impl.GenericMyIbatisDao;
import com.zizaike.entity.open.RoomInfoDto;
import com.zizaike.open.dao.BaseInfoDao;

/**  
 * ClassName:BaseInfoDaoImpl <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2016年3月7日 下午6:48:01 <br/>  
 * @author   alex  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
@Repository
public class BaseInfoDaoImpl extends GenericMyIbatisDao<RoomInfoDto, Integer> implements BaseInfoDao{
    private static final String NAMESPACE = "com.zizaike.open.dao.BaseInfoMapper." ;
    
    
    @Override
    public RoomInfoDto getRefundAndBreakfast(int roomId) {
          
        RoomInfoDto result =this.getSqlSession().selectOne(NAMESPACE+"getRefundAndBreakfast", roomId); 
        return result;
    }

    @Override
    public QunarRoomInfoDto getQunarRoomInfoDto(int roomId) {
        QunarRoomInfoDto result =this.getSqlSession().selectOne(NAMESPACE+"getQunarRoomInfoDto", roomId);
        return result;
    }
    public OpenDiscount getOpenDiscount(OpenDiscount openDiscount) {
        OpenDiscount result =this.getSqlSession().selectOne(NAMESPACE+"getOpenDiscount", openDiscount);
        return result;
    }
}
  
