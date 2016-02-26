/**  
 * Project Name:open-service  <br/>
 * File Name:HotelApplicationEvent.java  <br/>
 * Package Name:com.zizaike.open.domain.event  <br/>
 * Date:2016年2月26日上午10:46:36  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.domain.event;  

import org.springframework.context.ApplicationEvent;

import com.zizaike.entity.open.alibaba.Hotel;

/**  
 * ClassName:HotelApplicationEvent <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2016年2月26日 上午10:46:36 <br/>  
 * @author   alex  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public class HotelApplicationEvent extends ApplicationEvent{
   
    private static final long serialVersionUID = 2112571237739999129L;

    public HotelApplicationEvent(Hotel hotel){
        super(hotel);
    }
}
  
