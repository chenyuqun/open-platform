/**  
 * Project Name:open-service  <br/>
 * File Name:RatesChangeListener.java  <br/>
 * Package Name:com.zizaike.open.listener.taobao  <br/>
 * Date:2016年2月26日上午11:22:11  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.listener.taobao;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;

import com.zizaike.entity.open.alibaba.Rates;
import com.zizaike.is.open.TaobaoService;
import com.zizaike.open.domain.event.RatesApplicationEvent;

/**  
 * ClassName:RatesChangeListener <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2016年2月26日 上午11:22:11 <br/>  
 * @author   alex  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public class RatesChangeListener implements ApplicationListener<RatesApplicationEvent> {
    @Autowired
    private TaobaoService taobaoService;
    @Async
    @Override
    public void onApplicationEvent(RatesApplicationEvent event) {
          
        Rates rates =  (Rates) event.getSource();          
        taobaoService.updateRates(rates);        
    }
}
  
