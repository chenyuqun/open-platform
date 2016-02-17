/**  
 * Project Name:open-api  <br/>
 * File Name:RoomUpdateRmqConsumer.java  <br/>
 * Package Name:com.zizaike.open  <br/>
 * Date:2016年1月6日下午2:23:58  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
 */

package com.zizaike.open.mq;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.XhotelAddRequest;
import com.taobao.api.request.XhotelRoomtypeAddRequest;
import com.taobao.api.request.XhotelRoomtypeUpdateRequest;
import com.taobao.api.response.XhotelAddResponse;
import com.taobao.api.response.XhotelRoomtypeAddResponse;
import com.taobao.api.response.XhotelRoomtypeUpdateResponse;
import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.open.alibaba.Action;
import com.zizaike.entity.open.alibaba.RoomType;

/**
 * ClassName:RoomModifyRmqConsumer <br/>
 * Function: 房型更新. <br/>
 * Date: 2016年1月6日 下午2:23:58 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 * @see
 */
@Service("roomTypeRmqConsumer")
public class RoomTypeRmqConsumer {
    protected final Logger LOG = LoggerFactory.getLogger(RoomTypeRmqConsumer.class);
    @Value("${alibaba.sessionKey}")
    private String sessionKey;
    @Autowired
    private TaobaoClient taobaoClient;

    public void reveiveRoomTypeMessage(RoomType roomType) throws ApiException, ZZKServiceException {
        if (roomType == null) {
            throw new IllegalParamterException("roomType is null");
        }
        if (roomType.getAction() == null) {
            throw new IllegalParamterException("roomType.action is null");
        }
        if (roomType.getAction() == Action.ADD) {
            addRoomType(roomType);
        } else if (roomType.getAction() == Action.UPDATE) {
            updateRoomType(roomType);
        }
    }

    public void addRoomType(RoomType object) throws ApiException {
        LOG.debug("addRoomType mqInfo {}", object.toString());
        XhotelRoomtypeAddRequest req = new XhotelRoomtypeAddRequest();
        /**
         * 互联网
         */
        if(StringUtils.isNotEmpty(object.getInternet())){
            if(object.getInternet().equals("1")){
                object.setInternet("B");
            }else if(object.getInternet().equals("0")){
                object.setInternet("A");
            }else{
                object.setInternet(null);
            }
        };
        /**
         * 面积
         */
        if(StringUtils.isNotEmpty(object.getArea())){
            Pattern p =Pattern.compile("[^0-9]");
            Matcher m=p.matcher(object.getArea());
            object.setArea(m.replaceAll("").trim());
        }
        /**
         * 服务
         */
        if(StringUtils.isNotEmpty(object.getService())){
            HashMap map=JSON.parseObject(object.getService(), new TypeReference<HashMap<String,String>>(){});
            HashMap serviceMap=new HashMap();
            if(map.get("1")!=null&&map.get("1").equals("1")){
                serviceMap.put("catv", "true");
            }else{
                serviceMap.put("catv", "false");
            }
            object.setService(serviceMap.toString());
            //object.getService()object.getService()
        };
        /**
         * 图片转义
         */
        if(StringUtils.isNotEmpty(object.getPics())){
            HashMap<String,String> hashmap=JSON.parseObject(object.getPics(), new TypeReference<HashMap<String,String>>(){});
            List<Map<String,String>> pics = new ArrayList<>();
            Boolean isMain = true;
            for (String key : hashmap.keySet()) 
                {
                Map map = new HashMap();
                map.put("url", hashmap.get(key));
                if(isMain==true){
                    map.put("isMain", "true");
                    isMain=false;
                }else{
                    map.put("isMain", "false");
                }     
                 pics.add(map);
                }
              
            object.setPics(JSON.toJSONString(pics));
        }
        try {
            BeanUtils.copyProperties(req, object);     
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            LOG.error("addRoomType copyProperties exception{}", e);
        }
        LOG.debug("XhotelRoomtypeAddRequest {}", ToStringBuilder.reflectionToString(req));
        XhotelRoomtypeAddResponse response = taobaoClient.execute(req, sessionKey);
        LOG.debug("XhotelRoomtypeAddResponse {}", ToStringBuilder.reflectionToString(response));
    }

    public void updateRoomType(RoomType object) throws ApiException {
        LOG.debug("updateRoomType mqInfo {}", object.toString());
        XhotelRoomtypeUpdateRequest req = new XhotelRoomtypeUpdateRequest();
        /**
         * 网络
         */
        if(StringUtils.isNotEmpty(object.getInternet())){
            if(object.getInternet().equals("1")){
                object.setInternet("B");
            }else if(object.getInternet().equals("0")){
                object.setInternet("A");
            }else{
                object.setInternet(null);
            }
        };
        /**
         * 面积
         */
        if(StringUtils.isNotEmpty(object.getArea())){
            Pattern p =Pattern.compile("[^0-9]");
            Matcher m=p.matcher(object.getArea());
            object.setArea(m.replaceAll("").trim());
        }
        /**
         * 服务
         */
        if(StringUtils.isNotEmpty(object.getService())){
            HashMap map=JSON.parseObject(object.getService(), new TypeReference<HashMap<String,String>>(){});
            HashMap serviceMap=new HashMap();
            if(map.get("1")!=null&&map.get("1").equals("1")){
                serviceMap.put("catv", "true");
            }else{
                serviceMap.put("catv", "false");
            }
            object.setService(serviceMap.toString());
            //object.getService()object.getService()
        };
        /**
         * 图片转义
         */
        if(StringUtils.isNotEmpty(object.getPics())){
            HashMap<String,String> hashmap=JSON.parseObject(object.getPics(), new TypeReference<HashMap<String,String>>(){});
            List<Map<String,String>> pics = new ArrayList<>();
            Boolean isMain = true;
            for (String key : hashmap.keySet()) 
                {
                Map map = new HashMap();
                map.put("url", hashmap.get(key));
                if(isMain==true){
                    map.put("isMain", "true");
                    isMain=false;
                }else{
                    map.put("isMain", "false");
                }     
                 pics.add(map);
                }
              
            object.setPics(JSON.toJSONString(pics));
        }
        try {
            BeanUtils.copyProperties(req, object);     
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            LOG.error("update copyProperties exception{}", e);
        }
        LOG.debug("XhotelRoomtypeUpdateResponse {}", ToStringBuilder.reflectionToString(req));
        XhotelRoomtypeUpdateResponse response = taobaoClient.execute(req, sessionKey);
        LOG.debug("XhotelRoomtypeUpdateResponse {}", ToStringBuilder.reflectionToString(response));
    }
}
