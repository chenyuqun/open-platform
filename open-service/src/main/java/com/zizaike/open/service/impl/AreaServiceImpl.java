/**  
 * Project Name:open-service  <br/>
 * File Name:AreaServiceImpl.java  <br/>
 * Package Name:com.zizaike.open.service.impl  <br/>
 * Date:2016年1月26日下午1:55:31  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.service.impl;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.open.alibaba.Area;
import com.zizaike.is.open.AreaService;
import com.zizaike.open.dao.AreaDao;

/**  
 * ClassName:AreaServiceImpl <br/>  
 * Function: 地址服务. <br/>  
 * Date:     2016年1月26日 下午1:55:31 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaDao areaDao;
    @Override
    public Area queryByTypeCode(String typeCode) throws ZZKServiceException {

        return areaDao.queryByTypeCode(typeCode);
    }

}
  
