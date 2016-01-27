/**  
 * Project Name:open-service  <br/>
 * File Name:RatePlanModifyRmqConsumer.java  <br/>
 * Package Name:com.zizaike.open  <br/>
 * Date:2016年1月27日上午10:07:22  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open;  

import org.springframework.stereotype.Service;

import com.zizaike.entity.open.alibaba.RatePlan;

/**  
 * ClassName:RatePlanModifyRmqConsumer <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2016年1月27日 上午10:07:22 <br/>  
 * @author   alex  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
@Service("ratePlanModifyRmqConsumer")
public class RatePlanModifyRmqConsumer {
    public void reveiveRatePlanModifyMessage(RatePlan object){
        System.err.println("reveiveRatePlanModifyMessage"+object);
    }
}
  
