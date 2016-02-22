/**  
 * Project Name:open-service  <br/>
 * File Name:AreaDaoImpl.java  <br/>
 * Package Name:com.zizaike.open.dao.impl  <br/>
 * Date:2016年1月26日下午1:57:07  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.dao.impl;  

import org.springframework.stereotype.Repository;

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.core.framework.mybatis.impl.GenericMyIbatisDao;
import com.zizaike.entity.open.alibaba.Area;
import com.zizaike.open.dao.AreaDao;

/**  
 * ClassName:AreaDaoImpl <br/>  
 * Function: 地址服务. <br/>  
 * Date:     2016年1月26日 下午1:57:07 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
@Repository
public class AreaDaoImpl extends GenericMyIbatisDao<Area, Integer> implements AreaDao {
    private static final String NAMESPACE = "com.zizaike.open.dao.AreaMapper." ;

    @Override
    public Area queryByTypeCode(String typeCode) throws ZZKServiceException {
          
        Area query = this.getSqlSession().selectOne(NAMESPACE+"queryByTypeCode", typeCode);
        return query;
    }

}
  
