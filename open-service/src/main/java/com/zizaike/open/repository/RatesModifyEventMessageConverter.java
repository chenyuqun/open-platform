/**  
 * Project Name:open-service  <br/>
 * File Name:RatesModifyEventMessageConverter.java  <br/>
 * Package Name:com.zizaike.open.repository  <br/>
 * Date:2016年1月26日下午5:59:24  <br/>
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
import com.zizaike.entity.open.alibaba.InventoryPriceMap;

/**  
 * ClassName:RatesModifyEventMessageConverter <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2016年1月26日 下午5:59:24 <br/>  
 * @author   alex  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public class RatesModifyEventMessageConverter implements MessageConverter {
    private static final Logger LOG = LoggerFactory.getLogger(RatesModifyEventMessageConverter.class);
    private String encoding = "utf-8";
    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        InventoryPriceMap inventoryPriceMap = new InventoryPriceMap();
        
        if(message == null){
            return inventoryPriceMap;
        }
        String body;
        try {
            body = new String(message.getBody(), encoding);
            inventoryPriceMap = JSON.parseObject(body, InventoryPriceMap.class);
            return inventoryPriceMap;
        } catch (Exception e) {
            LOG.error("could not parse message", e);
           return inventoryPriceMap;
        }
    }

    @Override
    public Message toMessage(Object arg0, MessageProperties arg1) throws MessageConversionException {
        throw new UnsupportedOperationException("user password changed event should not publish by this service");
    }
}
  
