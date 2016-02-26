/**  
 * Project Name:open-service  <br/>
 * File Name:RatePlanModifyRmqConsumer.java  <br/>
 * Package Name:com.zizaike.open  <br/>
 * Date:2016年1月27日上午10:07:22  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.mq;  

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.open.alibaba.RatePlan;
import com.zizaike.open.domain.event.RatePlanApplicationEvent;

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
@Service("ratePlanRmqConsumer")
public class RatePlanRmqConsumer {   
    protected final Logger LOG = LoggerFactory.getLogger(RatePlanRmqConsumer.class);
    @Autowired
    ApplicationContext applicationContext;
    public void reveiveRoomTypeMessage(RatePlan ratePlan) throws ApiException, ZZKServiceException {
        if (ratePlan == null) {
            throw new IllegalParamterException("ratePlan is null");
        }
        if (ratePlan.getAction() == null) {
            throw new IllegalParamterException("ratePlan.action is null");
        }
        applicationContext.publishEvent(new RatePlanApplicationEvent(ratePlan));
    }
}
  
