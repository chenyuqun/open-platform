/**  
 * Project Name:open-api  <br/>
 * File Name:RoomModifyEventMessageConverter.java  <br/>
 * Package Name:com.zizaike.open.repository  <br/>
 * Date:2016年1月6日下午2:51:56  <br/>
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
import com.zizaike.entity.open.alibaba.RoomType;

/**  
 * ClassName:RoomModifyEventMessageConverter <br/>  
 * Function: 房间信息转换. <br/>  
 * Date:     2016年1月6日 下午2:51:56 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public class RoomTypeEventMessageConverter implements MessageConverter{
    private static final Logger LOG = LoggerFactory.getLogger(RoomTypeEventMessageConverter.class);
    private String encoding = "utf-8";
    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        RoomType room = new RoomType();
        
        if(message == null){
            return room;
        }
        String body;
        try {
            body = new String(message.getBody(), encoding);
            room = JSON.parseObject(body, RoomType.class);
            return room;
        } catch (Exception e) {
            LOG.error("could not parse message exception{},Message{}", e,message);
        }
        return null;
    }

    @Override
    public Message toMessage(Object arg0, MessageProperties arg1) throws MessageConversionException {
        throw new UnsupportedOperationException("user password changed event should not publish by this service");
    }

}
  
