/**  
 * Project Name:open-service  <br/>
 * File Name:RatePlanApplicationEvent.java  <br/>
 * Package Name:com.zizaike.open.domain.event  <br/>
 * Date:2016年2月26日上午10:48:12  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.domain.event;  

import org.springframework.context.ApplicationEvent;

import com.zizaike.entity.open.alibaba.RatePlan;

/**  
 * ClassName:RatePlanApplicationEvent <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2016年2月26日 上午10:48:12 <br/>  
 * @author   alex  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public class RatePlanApplicationEvent extends ApplicationEvent{
    
    private static final long serialVersionUID = -5967959950560020358L;
    public RatePlanApplicationEvent(RatePlan ratePlan){
        super(ratePlan);
    }

}
  
