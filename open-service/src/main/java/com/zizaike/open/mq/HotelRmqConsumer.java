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
import com.taobao.api.request.XhotelUpdateRequest;
import com.taobao.api.response.XhotelUpdateResponse;
import com.zizaike.entity.open.alibaba.Hotel;


/**  
 * ClassName:RoomModifyRmqConsumer <br/>  
 * Function: 房间信息更新. <br/>  
 * Date:     2016年1月6日 下午2:23:58 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
@Service("hotelRmqConsumer")
public class HotelRmqConsumer {
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
    public void reveiveHotelModifyMessage(Hotel object) throws ApiException{
        XhotelUpdateRequest req = new XhotelUpdateRequest();
        
        req.setOuterId(object.getOuterId());
        req.setName(object.getName());
        req.setCity(object.getCity());
        req.setAddress(object.getAddress());
        req.setLatitude(object.getLatitude());
        req.setLongitude(object.getLongitude());
        req.setPositionType(object.getPositionType());
        req.setTel(object.getTel());
//        req.setService("{\"receiveForeignGuests\":\"true\",\"morningCall\":\"true\",\"breakfast\":\"true\"}");
//        req.setHotelPolicies("{\"children_age_from\":\"2\",\"children_age_to\":\"3\",\"children_stay_free\":\"True\",\"infant_age\":\"1\",\"min_guest_age\":\"4\"}");
        XhotelUpdateResponse response = taobaoClient.execute(req , sessionKey);
        System.out.println("test");   
        System.out.println(response.getBody());       
        System.err.println("reveiveHotelModifyMessage"+object);
    }
}
  
