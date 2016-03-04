/**  
 * Project Name:open-service  <br/>
 * File Name:HotelChangeListener.java  <br/>
 * Package Name:com.zizaike.open.listener.taobao  <br/>
 * Date:2016年2月26日下午2:16:33  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.listener.alitrip;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.zizaike.entity.open.alibaba.Action;
import com.zizaike.entity.open.alibaba.Hotel;
import com.zizaike.is.open.TaobaoService;
import com.zizaike.open.domain.event.HotelApplicationEvent;

/**  
 * ClassName:HotelChangeListener <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2016年2月26日 下午2:16:33 <br/>  
 * @author   alex  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
@Component
public class HotelChangeListener implements ApplicationListener<HotelApplicationEvent> {
    @Autowired
    private TaobaoService taobaoService;
    @Async
    @Override
    public void onApplicationEvent(HotelApplicationEvent event) {
        
        Hotel hotel =  (Hotel) event.getSource();
            if (hotel.getAction() == Action.ADD) {
                taobaoService.addHotel(hotel);
            } else if (hotel.getAction() == Action.UPDATE) {
                taobaoService.updateHotel(hotel);
            }
    }
}
  
