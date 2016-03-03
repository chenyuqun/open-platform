/**  
 * Project Name:redis-service  <br/>
 * File Name:SearchApplicationEvent.java  <br/>
 * Package Name:com.zizaike.redis.domain.event  <br/>
 * Date:2015年12月8日下午7:58:16  <br/>
 * Copyright (c) 2015, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.domain.event;  

import org.springframework.context.ApplicationEvent;

import com.zizaike.entity.open.alibaba.RoomType;

/**
 * ClassName: RoomTypeApplicationEvent <br/>  
 * Function: 房型事件. <br/>  
 * date: 2016年2月25日 下午1:46:15 <br/>  
 *  
 * @author snow.zhang  
 * @version   
 * @since JDK 1.7
 */
public class RoomTypeApplicationEvent extends ApplicationEvent {
    private static final long serialVersionUID = -7162466134435519889L;

    public RoomTypeApplicationEvent(RoomType roomType) {
        super(roomType);  
    }


}
  
