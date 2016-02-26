/**  
 * Project Name:open-service  <br/>
 * File Name:RatesModifyRmqConsumer.java  <br/>
 * Package Name:com.zizaike.open  <br/>
 * Date:2016年1月27日上午10:08:38  <br/>
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
import com.zizaike.entity.open.alibaba.Rates;
import com.zizaike.open.domain.event.RatesApplicationEvent;

/**  
 * ClassName:RatesModifyRmqConsumer <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2016年1月27日 上午10:08:38 <br/>  
 * @author   alex  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
@Service("ratesRmqConsumer")
public class RatesRmqConsumer {
    protected final Logger LOG = LoggerFactory.getLogger(RatesRmqConsumer.class);
    @Autowired
    ApplicationContext applicationContext;
    
    public void reveiveRatesMessage(Rates rates) throws ApiException, ZZKServiceException {
        if (rates == null) {
            throw new IllegalParamterException("rates is null");
        }
        if (rates.getAction() == null) {
            throw new IllegalParamterException("rates.action is null");
        }
        applicationContext.publishEvent(new RatesApplicationEvent(rates));
    }

}
  
