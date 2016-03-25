/**  
 * Project Name:open-service  <br/>
 * File Name:AreaServiceImpl.java  <br/>
 * Package Name:com.zizaike.open.service.impl  <br/>
 * Date:2016年1月26日下午1:55:31  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
 */

package com.zizaike.open.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.core.framework.exception.open.AreaNotFoundException;
import com.zizaike.entity.open.alibaba.Area;
import com.zizaike.is.open.AreaService;
import com.zizaike.open.dao.AreaDao;

/**
 * ClassName:AreaServiceImpl <br/>
 * Function: 地址服务. <br/>
 * Date: 2016年1月26日 下午1:55:31 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 * @see
 */
@Service
public class AreaServiceImpl implements AreaService {
    protected final Logger LOG = LoggerFactory.getLogger(AreaServiceImpl.class);
    @Autowired
    private AreaDao areaDao;

    @Override
    public Area getAreaCodeTypeCode(String typeCode) throws ZZKServiceException {

        Area area = areaDao.queryByTypeCode(typeCode);
        if (area == null) {
            LOG.error("area not found");
            throw new AreaNotFoundException();
        }
        return area;
    }

}
