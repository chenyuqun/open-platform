/**  
 * Project Name:open-api  <br/>
 * File Name:RoomUpdateRmqConsumer.java  <br/>
 * Package Name:com.zizaike.open  <br/>
 * Date:2016年1月6日下午2:23:58  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open;  

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
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
@Service("roomTypeModifyRmqConsumer")
public class RoomTypeModifyRmqConsumer {
    @Value("${alibaba.appkey}")
    private String appkey;
    @Value("${alibaba.sessionKey}")
    private String sessionKey;
    @Value("${alibaba.secret}")
    private String secret;   
    private String url="http://gw.api.tbsandbox.com/router/rest";
    TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
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
        TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret); 
        XhotelRoomtypeAddResponse response = client.execute(req , sessionKey);
        System.out.println(response.getBody());       
        System.err.println("reveiveRoomModifyMessage"+object);
    }
}
  
