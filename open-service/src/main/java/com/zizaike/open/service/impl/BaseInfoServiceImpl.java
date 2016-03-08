/**  
 * Project Name:open-service  <br/>
 * File Name:BaseInfoServiceImpl.java  <br/>
 * Package Name:com.zizaike.open.service.impl  <br/>
 * Date:2016年3月7日下午6:39:58  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.service.impl;  

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.core.framework.exception.open.RoomTypeNotMappingException;
import com.zizaike.entity.open.RoomInfoDto;
import com.zizaike.entity.open.RoomTypeMapping;
import com.zizaike.is.open.BaseInfoService;
import com.zizaike.open.dao.AreaDao;
import com.zizaike.open.dao.BaseInfoDao;

/**  
 * ClassName:BaseInfoServiceImpl <br/>  
 * Date:     2016年3月7日 下午6:39:58 <br/>  
 * @author   alex  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
@Service
public class BaseInfoServiceImpl implements BaseInfoService {
    protected final Logger LOG = LoggerFactory.getLogger(BaseInfoServiceImpl.class);
    @Autowired
    private BaseInfoDao baseInfoDao;
    
    @Override
    public RoomInfoDto getRefundAndBreakfast(int nid) {
        RoomInfoDto query=baseInfoDao.getRefundAndBreakfast(nid);         
        return query;
    }

}
  
