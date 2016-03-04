/**  
 * Project Name:redis-service  <br/>
 * File Name:HotSearchStatisticsListener.java  <br/>
 * Package Name:com.zizaike.redis.listener  <br/>
 * Date:2015年12月8日下午8:40:59  <br/>
 * Copyright (c) 2015, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.listener.alitrip;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.zizaike.entity.open.alibaba.Action;
import com.zizaike.entity.open.alibaba.RoomType;
import com.zizaike.is.open.TaobaoService;
import com.zizaike.open.domain.event.RoomTypeApplicationEvent;

/**  
 * ClassName:RoomTypeChangeListener <br/>  
 * Function: taobaoRoomTypechange. <br/>  
 * Date:     2015年12月8日 下午8:40:59 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
@Component
public class RoomTypeChangeListener implements ApplicationListener<RoomTypeApplicationEvent>{
    @Autowired
    private TaobaoService taobaoService;
    @Async
    @Override
    public void onApplicationEvent(RoomTypeApplicationEvent event) {
          
        RoomType roomType =  (RoomType) event.getSource();
            if (roomType.getAction() == Action.ADD) {
                taobaoService.addRoomType(roomType);
            } else if (roomType.getAction() == Action.UPDATE) {
                taobaoService.updateRoomType(roomType);
            }
    }

}
  
