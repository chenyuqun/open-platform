/**  
 * Project Name:open-service  <br/>
 * File Name:RatePlanModifyEventMessageConverter.java  <br/>
 * Package Name:com.zizaike.open.repository  <br/>
 * Date:2016年1月26日下午6:21:51  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.repository;  

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import com.alibaba.fastjson.JSON;
import com.zizaike.entity.open.alibaba.Hotel;
import com.zizaike.entity.open.alibaba.RatePlan;

/**  
 * ClassName:RatePlanModifyEventMessageConverter <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2016年1月26日 下午6:21:51 <br/>  
 * @author   alex  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public class RatePlanModifyEventMessageConverter implements MessageConverter {
    private static final Logger LOG = LoggerFactory.getLogger(RatePlanModifyEventMessageConverter.class);
    private String encoding = "utf-8";
    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        RatePlan ratePlan = new RatePlan();
        
        if(message == null){
            return ratePlan;
        }
        String body;
        try {
            body = new String(message.getBody(), encoding);
            ratePlan = JSON.parseObject(body, RatePlan.class);
            return ratePlan;
        } catch (Exception e) {
            LOG.error("could not parse message", e);
           return ratePlan;
        }
    }

    @Override
    public Message toMessage(Object arg0, MessageProperties arg1) throws MessageConversionException {
        throw new UnsupportedOperationException("user password changed event should not publish by this service");
    }
}
  
