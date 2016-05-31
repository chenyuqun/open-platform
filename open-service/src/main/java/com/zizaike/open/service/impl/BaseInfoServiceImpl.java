/**  
 * Project Name:open-service  <br/>
 * File Name:BaseInfoServiceImpl.java  <br/>
 * Package Name:com.zizaike.open.service.impl  <br/>
 * Date:2016年3月7日下午6:39:58  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.service.impl;  

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zizaike.core.common.util.http.HttpProxyUtil;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.open.OpenDiscount;
import com.zizaike.entity.open.QunarRoomInfoDto;
import com.zizaike.entity.open.RoomInfoDto;
import com.zizaike.is.open.BaseInfoService;
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
    @Autowired
    private HttpProxyUtil httpProxy;
    @Value("${zizaike.calendar.obtain.host}")
    private String calendarHost;
    @Override
    public RoomInfoDto getRefundAndBreakfast(int nid) {
        RoomInfoDto query=baseInfoDao.getRefundAndBreakfast(nid);         
        return query;
    }

    @Override
    public JSONObject getZizaikePrice(String roomId,String checkIn,String checkOut){
        Map<String,String> map = new HashMap<String, String>();
        map.put("rid", roomId);
        map.put("start_date",checkIn);
        map.put("end_date",checkOut);
        map.put("auth_type", "internal");
        map.put("auth_key","9371d966ed8749fd959b8dfed2de7f");
        JSONObject result = null;
        try {
            result = httpProxy.httpGet(calendarHost, map);
            //LOG.info("getZizaikePrice return{}",result);
        } catch (IOException e) {
            LOG.error("getZizaikePrice IOException {}", e.toString());
        }
        return result;
    }

    @Override
    public QunarRoomInfoDto getQunarRoomInfo(int nid){
        QunarRoomInfoDto query=baseInfoDao.getQunarRoomInfoDto(nid);
        return query;
    }
    public OpenDiscount getOpenDiscount(OpenDiscount openDiscount) throws ZZKServiceException {
        OpenDiscount query=baseInfoDao.getOpenDiscount(openDiscount);
        return query;
    }
}
  
