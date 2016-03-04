/**  
 * Project Name:open-service  <br/>
 * File Name:RatesChangeListener.java  <br/>
 * Package Name:com.zizaike.open.listener.ctrip  <br/>
 * Date:2016年2月26日下午3:51:04  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.listener.ctrip;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.open.alibaba.Rates;
import com.zizaike.is.open.CtripService;
import com.zizaike.open.domain.event.RatesApplicationEvent;

/**  
 * ClassName:RatesChangeListener <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2016年2月26日 下午3:51:04 <br/>  
 * @author   alex  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
@Component
public class CtripRatesChangeListener implements ApplicationListener<RatesApplicationEvent>{
    @Autowired
    private CtripService ctripService;
    @Async
    @Override
    public void onApplicationEvent(RatesApplicationEvent event) {
          
        Rates rates =  (Rates) event.getSource();          
        try {
            ctripService.updateRates(rates);
        } catch (ZZKServiceException e) {  
            
            e.printStackTrace();  
            
        }        
    }
}
  
