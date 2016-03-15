/**  
 * Project Name:open-service  <br/>
 * File Name:RatesApplicationEvent.java  <br/>
 * Package Name:com.zizaike.open.domain.event  <br/>
 * Date:2016年2月26日上午10:35:17  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.domain.event;  

import org.springframework.context.ApplicationEvent;

import com.zizaike.entity.open.alibaba.Rates;

/**  
 * ClassName:RatesApplicationEvent <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2016年2月26日 上午10:35:17 <br/>  
 * @author   alex  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public class RatesApplicationEvent extends ApplicationEvent {

    private static final long serialVersionUID = 88582465705568891L;
    public RatesApplicationEvent(Rates rates) {
        super(rates);  
    }

}
  
