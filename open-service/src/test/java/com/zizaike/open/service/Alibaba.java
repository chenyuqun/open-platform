/**  
 * Project Name:open-service  <br/>
 * File Name:Alibaba.java  <br/>
 * Package Name:com.zizaike.open.service  <br/>
 * Date:2016年1月11日下午4:51:02  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.service;  

import org.springframework.beans.factory.annotation.Value;
import org.testng.annotations.Test;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.internal.util.StringUtils;
import com.taobao.api.request.XhotelGetRequest;
import com.taobao.api.request.XhotelRateplanUpdateRequest;
import com.taobao.api.response.XhotelGetResponse;
import com.taobao.api.response.XhotelRateplanUpdateResponse;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.open.bastest.BaseTest;

/**  
 * ClassName:Alibaba <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2016年1月11日 下午4:51:02 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public class Alibaba extends BaseTest{
    @Value("${alibaba.appkey}")
    private String appkey;
    @Value("${alibaba.sessionKey}")
    private String sessionKey;
    @Value("${alibaba.secret}")
    private String secret;
    @Test(description = "XhotelGet")
    public void xhotelGet() throws ZZKServiceException, ApiException {
        String url="http://gw.api.tbsandbox.com/router/rest";
        TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
        XhotelGetRequest req = new XhotelGetRequest();
        req.setOuterId("1");
        XhotelGetResponse response = client.execute(req , sessionKey);
        System.out.println(response.getBody());

        
    }
    @Test(description = "rateplanUpdate")
    public void rateplanUpdate() throws ZZKServiceException, ApiException {
        String url = "http://gw.api.tbsandbox.com/router/rest";
        TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
        XhotelRateplanUpdateRequest req = new XhotelRateplanUpdateRequest();
        req.setRpid(100000L);
        req.setName("含早提前3天");
        req.setBreakfastCount(1L);
        req.setFeeBreakfastAmount(1L);
        req.setFeeGovTaxAmount(1L);
        req.setFeeGovTaxPercent(1L);
        req.setFeeServiceAmount(150L);
        req.setFeeServicePercent(15L);
        req.setExtendFee("aaa");
        req.setMinDays(1L);
        req.setMaxDays(90L);
        req.setMinAmount(1L);
        req.setMinAdvHours(1L);
        req.setMaxAdvHours(3L);
        req.setStartTime("00:00");
        req.setEndTime("00:00");
        req.setCancelPolicy("{\"cancelPolicyType\":1}|{\"cancelPolicyType\":2}|{\"cancelPolicyType\":4,\"policyInfo\":{\"48\":10,\"24\":20}}|{\"cancelPolicyType\":5,\"policyInfo\":{\"timeBefore\":6}}|{\"cancelPolicyType\":6,\"policyInfo\":{\"14\":1}}");
        req.setExtend("1");
        req.setStatus(1L);
        req.setEnglishName("aaa");
        req.setFeeBreakfastCount(2L);
        req.setGuaranteeType(1L);
        req.setGuaranteeStartTime("18:00");
        req.setMemberLevel("1");
        req.setChannel("A");
        req.setOccupancy(3L);
        req.setVendor("taobao");
        req.setRateplanCode("12345AAA");
        req.setFirstStay(1L);
        req.setAgreement(1L);
        req.setBreakfastCal("[{\"date\":\"yyyy-MM-dd\",\"startDate\":\"yyyy-MM-dd\",\"endDate\":\"yyyy-MM-dd\",\"breakfast_count\":0},{\"date\":\"yyyy-MM-dd\",\"startDate\":\"yyyy-MM-dd\",\"endDate\":\"yyyy-MM-dd\",\"breakfast_count\":1}]");
        req.setCancelPolicyCal("[{\"date\":\"yyyy-MM-dd\",\"startDate\":\"yyyy-MM-dd\",\"endDate\":\"yyyy-MM-dd\",\"cancel_policy\":{\"cancelPolicyType\":1} },{\"date\":\"yyyy-MM-dd\",\"startDate\":\"yyyy-MM-dd\",\"endDate\":\"yyyy-MM-dd\",\"cancel_policy\":{\"cancelPolicyType\":4,\"policyInfo\":{\"48\":10,\"24\":20}}}]");
        req.setGuaranteeCal("[{\"date\":\"yyyy-MM-dd\",\"startDate\":\"yyyy-MM-dd\",\"endDate\":\"yyyy-MM-dd\",\"guarantee\":{\"guaranteeType\":2,\"guaranteeStartTime\":\"HH:mm\"}},{\"date\":\"yyyy-MM-dd\",\"startDate\":\"yyyy-MM-dd\",\"endDate\":\"yyyy-MM-dd\",\"guarantee\":{\"guaranteeType\":3,\"guaranteeStartTime\":\"HH:mm\"}}]");
        req.setCancelBeforeDay(2L);
        req.setCancelBeforeHour("6");
        req.setEffectiveTime(StringUtils.parseDateTime("2000-01-01 00:00:00"));
        req.setDeadlineTime(StringUtils.parseDateTime("2000-01-01 00:00:00"));
        req.setPaymentType(1L);
        req.setRpType("1");
        req.setHourage("4");
        req.setCanCheckinEnd("16:00");
        req.setCanCheckinStart("08:00");
        XhotelRateplanUpdateResponse rsp = client.execute(req, sessionKey);
        System.out.println(rsp.getBody());
        
        
    }
}
  
