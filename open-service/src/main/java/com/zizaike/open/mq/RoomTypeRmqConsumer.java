/**  
 * Project Name:open-api  <br/>
 * File Name:RoomUpdateRmqConsumer.java  <br/>
 * Package Name:com.zizaike.open  <br/>
 * Date:2016年1月6日下午2:23:58  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.mq;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.XhotelRoomtypeAddRequest;
import com.taobao.api.response.XhotelRoomtypeAddResponse;
import com.zizaike.entity.open.alibaba.RoomType;


/**  
 * ClassName:RoomModifyRmqConsumer <br/>  
 * Function: 房型更新. <br/>  
 * Date:     2016年1月6日 下午2:23:58 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
@Service("roomTypeRmqConsumer")
public class RoomTypeRmqConsumer {
    @Value("${alibaba.appKey}")
    private String appkey;
    @Value("${alibaba.sessionKey}")
    private String sessionKey;
    @Value("${alibaba.appSecret}")
    private String secret;
    @Value("${alibaba.serverUrl}")
    private String url;
    @Autowired
    private TaobaoClient taobaoClient;
    public void reveiveRoomTypeModifyMessage(RoomType object) throws ApiException{
        XhotelRoomtypeAddRequest req = new XhotelRoomtypeAddRequest();
        req.setOuterId(object.getOuterId());
        //req.setHid((long)123456);
        req.setName(object.getName());
        req.setMaxOccupancy(object.getMaxOccupancy());
        req.setArea(object.getArea());
        req.setFloor(object.getFloor());
        req.setBedType(object.getBedType());
        req.setBedSize(object.getBedSize());
        req.setInternet(object.getInternet());
        req.setService(object.getService());
        req.setExtend(object.getExtend());
        req.setWindowType(object.getWindowType());
        req.setOutHid(object.getOutHid());;
        req.setPics(object.getPics());
        XhotelRoomtypeAddResponse response = taobaoClient.execute(req , sessionKey);
        System.out.println(response.getBody());       
        System.err.println("reveiveRoomModifyMessage"+object);
    }
}
  
