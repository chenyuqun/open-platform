/**  
 * Project Name:open-service  <br/>
 * File Name:RatePlanModifyRmqConsumer.java  <br/>
 * Package Name:com.zizaike.open  <br/>
 * Date:2016年1月27日上午10:07:22  <br/>
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

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.XhotelAddRequest;
import com.taobao.api.request.XhotelRateplanAddRequest;
import com.taobao.api.request.XhotelRateplanUpdateRequest;
import com.taobao.api.request.XhotelUpdateRequest;
import com.taobao.api.response.XhotelAddResponse;
import com.taobao.api.response.XhotelRateplanAddResponse;
import com.taobao.api.response.XhotelRateplanUpdateResponse;
import com.taobao.api.response.XhotelUpdateResponse;
import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.open.alibaba.Action;
import com.zizaike.entity.open.alibaba.Hotel;
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
@Service("ratePlanRmqConsumer")
public class RatePlanRmqConsumer {   
    protected final Logger LOG = LoggerFactory.getLogger(RatePlanRmqConsumer.class);
    @Value("${alibaba.sessionKey}")
    private String sessionKey;
    @Autowired
    private TaobaoClient taobaoClient;
    
    public void reveiveRatePlanMessage(RatePlan ratePlan) throws ApiException, ZZKServiceException {
        if (ratePlan == null) {
            throw new IllegalParamterException("ratePlan is null");
        }
        if (ratePlan.getAction() == null) {
            throw new IllegalParamterException("ratePlan.action is null");
        }
        if (ratePlan.getAction() == Action.ADD) {
            addRatePlan(ratePlan);
        } else if (ratePlan.getAction() == Action.UPDATE) {
            updateRatePlan(ratePlan);
        }
    }

    public void addRatePlan(RatePlan object) throws ApiException, ZZKServiceException {
        LOG.debug("addRatePlan mqInfo {}", object.toString());
        XhotelRateplanAddRequest req = new XhotelRateplanAddRequest();
        try {
            BeanUtils.copyProperties(req, object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            LOG.error("addRatePlan copyProperties exception{}", e);
        }
        LOG.debug("addRatePlan XhotelRateplanAddRequest {}", ToStringBuilder.reflectionToString(req));
        XhotelRateplanAddResponse response = taobaoClient.execute(req, sessionKey);
        LOG.debug("addRatePlan XhotelRateplanAddResponse {}", ToStringBuilder.reflectionToString(response));
    }

    public void updateRatePlan(RatePlan object) throws ApiException {
        LOG.debug("updateRatePlan mqInfo {}", object.toString());
        XhotelRateplanUpdateRequest req = new XhotelRateplanUpdateRequest();
        try {
            BeanUtils.copyProperties(req, object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            LOG.error("updateRatePlan copyProperties exception{}", e);
        }
        LOG.debug("updateRatePlan XhotelRateplanUpdateRequest {}", ToStringBuilder.reflectionToString(req));
        XhotelRateplanUpdateResponse response = taobaoClient.execute(req, sessionKey);
        LOG.debug("updateRatePlan XhotelRateplanUpdateResponse {}", ToStringBuilder.reflectionToString(response));
    }
    
}
  
