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
import com.taobao.api.request.XhotelAddRequest;
import com.taobao.api.request.XhotelGetRequest;
import com.taobao.api.request.XhotelRateGetRequest;
import com.taobao.api.request.XhotelRateRelationshipwithroomGetRequest;
import com.taobao.api.request.XhotelRateRelationshipwithrpGetRequest;
import com.taobao.api.request.XhotelRateUpdateRequest;
import com.taobao.api.request.XhotelRateplanUpdateRequest;
import com.taobao.api.request.XhotelRateplanAddRequest;
import com.taobao.api.request.XhotelRateplanGetRequest;
import com.taobao.api.request.XhotelRoomsIncrementRequest;
import com.taobao.api.request.XhotelRoomtypeAddRequest;
import com.taobao.api.request.XhotelRoomtypeGetRequest;
import com.taobao.api.request.XhotelUpdateRequest;
import com.taobao.api.response.XhotelAddResponse;
import com.taobao.api.response.XhotelGetResponse;
import com.taobao.api.response.XhotelRateGetResponse;
import com.taobao.api.response.XhotelRateRelationshipwithroomGetResponse;
import com.taobao.api.response.XhotelRateRelationshipwithrpGetResponse;
import com.taobao.api.response.XhotelRateUpdateResponse;
import com.taobao.api.response.XhotelRateplanUpdateResponse;
import com.taobao.api.response.XhotelRateplanAddResponse;
import com.taobao.api.response.XhotelRateplanGetResponse;
import com.taobao.api.response.XhotelRoomsIncrementResponse;
import com.taobao.api.response.XhotelRoomtypeAddResponse;
import com.taobao.api.response.XhotelRoomtypeGetResponse;
import com.taobao.api.response.XhotelUpdateResponse;
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
    private String url="http://gw.api.tbsandbox.com/router/rest";
    TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
    @Test(description = "XhotelGet")
    public void XhotelGet() throws ZZKServiceException, ApiException {
        XhotelGetRequest req = new XhotelGetRequest();
        req.setOuterId("12345678");       
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
    
    @Test(description = "酒店添加")
    public void xhotelAdd() throws ZZKServiceException, ApiException {
        XhotelAddRequest req = new XhotelAddRequest();
        req.setOuterId("12345678");
        req.setName("自在客");
        req.setCity((long) 310000);
        XhotelAddResponse response = client.execute(req , sessionKey);
        System.out.println(response.getBody());       
    }
    
    @Test(description = "酒店更新")
    public void xhotelUpdate() throws ZZKServiceException, ApiException {
        XhotelUpdateRequest req = new XhotelUpdateRequest();
        req.setOuterId("12345678");
        req.setName("自在客台湾");
        req.setCity((long) 310100);
        req.setAddress("浦东金科路");
        XhotelUpdateResponse response = client.execute(req , sessionKey);
        System.out.println(response.getBody());       
    }
    
    @Test(description = "房型添加")
    public void xhotelRoomtypeAdd() throws ZZKServiceException, ApiException {
        XhotelRoomtypeAddRequest req = new XhotelRoomtypeAddRequest();
        req.setOuterId("12345678_124");
        //req.setHid((long)123456);
        req.setName("自在客CYQ测试房间");
        req.setMaxOccupancy(2L);
        req.setArea("10平方米");
        req.setFloor("3-5层");
        req.setBedType("大床");
        req.setBedSize("1.8米");
        req.setInternet("A");
        req.setService("{\"bar\":true,\"catv\":false,\"ddd\":false,\"idd\":false,\"pubtoilet\":false,\"toilet\":false}");
        req.setExtend("空");
        req.setWindowType(1L);
//        req.setSrid(123123L);
        req.setOutHid("12345678");
        //req.setVendor("taobao");
        req.setPics("[{\"url\":\"http://123.jpg\",\"ismain\":\"true\"},{\"url\":\"http://456.jpg\",\"ismain\":\"false\"},{\"url\":\"http://789.jpg\",\"ismain\":\"false\"}]");
        XhotelRoomtypeAddResponse response = client.execute(req , sessionKey);
        System.out.println(response.getBody());       
    }
    
    @Test(description = "房型查询")
    public void xhotelRoomtypeGet() throws ZZKServiceException, ApiException {
        XhotelRoomtypeGetRequest req = new XhotelRoomtypeGetRequest();
        req.setOuterId("12345678_124");
        XhotelRoomtypeGetResponse response = client.execute(req , sessionKey);
        System.out.println(response.getBody());       
    }
    
    @Test(description = "房型更新")
    public void xhotelRoomtypeUpdate() throws ZZKServiceException, ApiException {
        XhotelRoomtypeAddRequest req = new XhotelRoomtypeAddRequest();
        req.setOuterId("12345678_123");
        //req.setHid((long)123456);
        req.setName("自在客测试房间");
        req.setMaxOccupancy(4L);
        req.setArea("15平方米");
        req.setFloor("3-5层");
        req.setBedType("大床");
        req.setBedSize("1.8米");
        req.setInternet("A");
        req.setService("{\"bar\":false,\"catv\":false,\"ddd\":false,\"idd\":false,\"pubtoilet\":false,\"toilet\":true}");
        req.setExtend("空");
        req.setWindowType(1L);
//        req.setSrid(123123L);
        req.setOutHid("12345678");
        //req.setVendor("taobao");
        req.setPics("[{\"url\":\"http://123.jpg\",\"ismain\":\"true\"},{\"url\":\"http://456.jpg\",\"ismain\":\"false\"},{\"url\":\"http://789.jpg\",\"ismain\":\"false\"}]");
        XhotelRoomtypeAddResponse response = client.execute(req , sessionKey);
        System.out.println(response.getBody());       
    }
    
    @Test(description = "房型库存推送接口（批量全量）")
    public void xhotelRoomsIncrement() throws ZZKServiceException, ApiException {
        XhotelRoomsIncrementRequest req = new XhotelRoomsIncrementRequest();
        req.setRoomQuotaMap("[{\"out_rid\":\"12345678_123\",\"vendor\":\"\","
                + "\"roomQuota\":[{\"date\":2010-01-28,\"quota\":10},{\"date\":2010-01-29,\"quota\":10}]},"
                + "{\"out_rid\":\"12345678_124\",\"vendor\":\"\",\"roomQuota\":[{\"date\":2010-01-28,\"quota\":20},{\"date\":2010-01-29,\"quota\":20}]}]");
        XhotelRoomsIncrementResponse response = client.execute(req , sessionKey);
        System.out.println(response.getBody());       
    }
    
    @Test(description = "酒店产品库rateplan添加")
    public void xhotelRateplanAdd() throws ZZKServiceException, ApiException {
        XhotelRateplanAddRequest req = new XhotelRateplanAddRequest();
        req.setRateplanCode("12345AAAA");
        req.setName("含早提前3天");
        req.setPaymentType(1L);
        req.setBreakfastCount(1L);
//        req.setFeeBreakfastCount(1L);
//        req.setFeeBreakfastAmount(1L);
//        req.setFeeGovTaxAmount(1L);
//        req.setFeeGovTaxPercent(1L);
//        req.setFeeServiceAmount(150L);
//        req.setFeeServicePercent(15L);
//        req.setExtendFee("aaa");
//        req.setMinDays(1L);
//        req.setMaxDays(90L);
//        req.setMinAmount(1L);
//        req.setMinAdvHours(1L);
//        req.setMaxAdvHours(3L);
//        req.setStartTime("00:00");
//        req.setEndTime("00:00");
          req.setCancelPolicy("{\"cancelPolicyType\":1}");
//        req.setExtend("1");
          req.setStatus(1L);
//        req.setEnglishName("zzk");
//        req.setGuaranteeType(1L);
//        req.setGuaranteeStartTime("18:00");
//        req.setMemberLevel("1,2,3,4,5");
//        req.setChannel("A");
//        req.setOccupancy(3L);
        //req.setVendor("taobao");
//        req.setFirstStay(1L);
//        req.setAgreement(1L);
//        req.setBreakfastCal("[{\"date\":\"yyyy-MM-dd\",\"startDate\":\"yyyy-MM-dd\",\"endDate\":\"yyyy-MM-dd\",\"breakfast_count\":0},{\"date\":\"yyyy-MM-dd\",\"startDate\":\"yyyy-MM-dd\",\"endDate\":\"yyyy-MM-dd\",\"breakfast_count\":1}]");
//        req.setCancelPolicyCal("[{\"date\":\"yyyy-MM-dd\",\"startDate\":\"yyyy-MM-dd\",\"endDate\":\"yyyy-MM-dd\",\"cancel_policy\":{\"cancelPolicyType\":1} },{\"date\":\"yyyy-MM-dd\",\"startDate\":\"yyyy-MM-dd\",\"endDate\":\"yyyy-MM-dd\",\"cancel_policy\":{\"cancelPolicyType\":4,\"policyInfo\":{\"48\":10,\"24\":20}}}]");
//        req.setGuaranteeCal("[{\"date\":\"yyyy-MM-dd\",\"startDate\":\"yyyy-MM-dd\",\"endDate\":\"yyyy-MM-dd\",\"guarantee\":{\"guaranteeType\":2,\"guaranteeStartTime\":\"HH:mm\"}},{\"date\":\"yyyy-MM-dd\",\"startDate\":\"yyyy-MM-dd\",\"endDate\":\"yyyy-MM-dd\",\"guarantee\":{\"guaranteeType\":3,\"guaranteeStartTime\":\"HH:mm\"}}]");
//        req.setCancelBeforeHour("6");
//        req.setCancelBeforeDay(2L);
//        req.setEffectiveTime(StringUtils.parseDateTime("2016-01-01 00:00:00"));
//        req.setDeadlineTime(StringUtils.parseDateTime("2016-03-10 00:00:00"));
//        req.setRpType("1");
//        req.setHourage("4");
//        req.setCanCheckinEnd("08:00");
//        req.setCanCheckinStart("16:00");
        XhotelRateplanAddResponse response = client.execute(req , sessionKey);
        System.out.println(response.getBody());       
    }
    
    @Test(description = "价格计划rateplan查询")
    public void xhotelRateplanGet() throws ZZKServiceException, ApiException {
        XhotelRateplanGetRequest req = new XhotelRateplanGetRequest();
        req.setRpid(5069252626L);
        req.setRateplanCode("12345AAAA");
        //req.setVendor("taobao");
        XhotelRateplanGetResponse response = client.execute(req , sessionKey);
        System.out.println(response.getBody());       
    }
    
    @Test(description = "酒店产品库rate查询")
    public void xhotelRateGet() throws ZZKServiceException, ApiException {
        XhotelRateGetRequest req = new XhotelRateGetRequest();
        req.setRpid(5069252626L);
        req.setRateplanCode("12345AAAA");
        //req.setVendor("taobao");
        XhotelRateGetResponse response = client.execute(req , sessionKey);
        System.out.println(response.getBody());       
    }
    
    @Test(description = "价格推送接口（全量更新）")
    public void xhotelRateUpdate() throws ZZKServiceException, ApiException {
        XhotelRateUpdateRequest req = new XhotelRateUpdateRequest();
        req.setRpid(5069252626L);
        req.setRateplanCode("12345AAAA");
        req.setGid(12568017626L);
        req.setOutRid("12345678_123");
        req.setInventoryPrice("{\"use_room_inventory\":false,\"inventory_price\":[{\"date\":2016-01-28,\"quota\":10,\"price\":100},{\"date\":2016-01-29,\"quota\":10,\"price\":200}]}");
        XhotelRateUpdateResponse response = client.execute(req , sessionKey);
        System.out.println(response.getBody());       
    }
    
    
    @Test(description = "根据gid查询卖家下所有的rpId")
    public void xhotelRateRelationshipwithrpGet() throws ZZKServiceException, ApiException {
        XhotelRateRelationshipwithrpGetRequest req = new XhotelRateRelationshipwithrpGetRequest();
        req.setGid(12568017626L);
        req.setPageNo(1L);
        XhotelRateRelationshipwithrpGetResponse response = client.execute(req , sessionKey);
        System.out.println(response.getBody());       
    }
    
    //阿里的描述似乎有问题 暂时先以阿里的描述来 方便查询文档
    @Test(description = "查询rpId")
    public void xhotelRateRelationshipwithroomGet() throws ZZKServiceException, ApiException {
        XhotelRateRelationshipwithroomGetRequest req = new XhotelRateRelationshipwithroomGetRequest();
        req.setRpId(5069252626L);
        req.setPageNo(1L);
        XhotelRateRelationshipwithroomGetResponse response = client.execute(req , sessionKey);
        System.out.println(response.getBody());       
    }
    

}
  
