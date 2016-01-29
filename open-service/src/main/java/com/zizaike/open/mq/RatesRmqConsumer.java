/**  
 * Project Name:open-service  <br/>
 * File Name:RatesModifyRmqConsumer.java  <br/>
 * Package Name:com.zizaike.open  <br/>
 * Date:2016年1月27日上午10:08:38  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.mq;  

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.XhotelRatesUpdateRequest;
import com.taobao.api.response.XhotelRatesUpdateResponse;
import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.open.alibaba.Rates;

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
    @Value("${alibaba.sessionKey}")
    private String sessionKey;
    @Autowired
    private TaobaoClient taobaoClient;
    
    public void reveiveRatesMessage(Rates rates) throws ApiException, ZZKServiceException {
        if (rates == null) {
            throw new IllegalParamterException("rates is null");
        }
        if (rates.getAction() == null) {
            throw new IllegalParamterException("rates.action is null");
        }
//        if (inventoryPriceMap.getAction() == Action.ADD) {
//            addRates(inventoryPriceMap);
//        } else if (inventoryPriceMap.getAction() == Action.UPDATE) {
            updateRates(rates);
 //       }
    }

    public void updateRates(Rates object) throws ApiException, ZZKServiceException {
        LOG.info("updateRates mqInfo {}", object.toString());
        XhotelRatesUpdateRequest req = new XhotelRatesUpdateRequest();
        try {
            BeanUtils.copyProperties(req, object);
            req.setRateInventoryPriceMap(JSON.toJSONString(object.getRateInventoryPriceMap()));
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            LOG.error("updateRates copyProperties exception{}", e);
        }
        LOG.info("updateRates XhotelRatesUpdateRequest {}", ToStringBuilder.reflectionToString(req));
        XhotelRatesUpdateResponse response = taobaoClient.execute(req, sessionKey);
        LOG.info("updateRates XhotelRatesUpdateResponse {}", ToStringBuilder.reflectionToString(response));
    }

}
  
