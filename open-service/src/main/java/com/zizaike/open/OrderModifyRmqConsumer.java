/**  
 * Project Name:open-api  <br/>
 * File Name:RoomUpdateRmqConsumer.java  <br/>
 * Package Name:com.zizaike.open  <br/>
 * Date:2016年1月6日下午2:23:58  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open;  

import org.springframework.stereotype.Service;

import com.zizaike.entity.solr.Room;


/**  
 * ClassName:RoomModifyRmqConsumer <br/>  
 * Function: 房间信息更新. <br/>  
 * Date:     2016年1月6日 下午2:23:58 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
@Service("orderModifyRmqConsumer")
public class OrderModifyRmqConsumer {
    public void reveiveOrderModifyMessage(Room object){
        System.err.println("reveiveOrderModifyMessage"+object);
    }
}
  
