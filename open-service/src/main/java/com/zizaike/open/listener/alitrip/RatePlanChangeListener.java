
/**  
 * Project Name:open-service  <br/>
 * File Name:RatePlanChangeListener.java  <br/>
 * Package Name:com.zizaike.open.listener.taobao  <br/>
 * Date:2016年2月26日下午2:32:07  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.listener.alitrip;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.zizaike.entity.open.alibaba.Action;
import com.zizaike.entity.open.alibaba.RatePlan;
import com.zizaike.is.open.TaobaoService;
import com.zizaike.open.domain.event.RatePlanApplicationEvent;


/**  
 * ClassName:RatePlanChangeListener <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2016年2月26日 下午2:32:07 <br/>  
 * @author   alex  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
@Component
public class RatePlanChangeListener implements ApplicationListener<RatePlanApplicationEvent> {
    @Autowired
    private TaobaoService taobaoService;
    @Async
    @Override
    public void onApplicationEvent(RatePlanApplicationEvent event) {
        
        RatePlan ratePlan =  (RatePlan) event.getSource();
            if (ratePlan.getAction() == Action.ADD) {
                taobaoService.addRatePlan(ratePlan);
            } else if (ratePlan.getAction() == Action.UPDATE) {
                taobaoService.updateRatePlan(ratePlan);
            }
    }
}
  
