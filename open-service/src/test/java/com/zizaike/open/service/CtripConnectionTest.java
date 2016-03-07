/**  
* Project Name:open-api  <br/>
 * File Name:httpUrlConnectionTest.java  <br/>
 * Package Name:com.zizaike.open.service  <br/>
 * Date:2016年1月7日下午5:29:35  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
 */

package com.zizaike.open.service;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.testng.annotations.Test;

import com.zizaike.core.common.util.http.SoapFastUtil;
import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.open.ctrip.GetHotelInfoResponse;
import com.zizaike.entity.open.ctrip.GetMappingInfoResponseList;
import com.zizaike.entity.open.ctrip.GetMappingInfoType;
import com.zizaike.entity.open.ctrip.HotelGroupInterfaceRoomTypeEntity;
import com.zizaike.entity.open.ctrip.MappingType;
import com.zizaike.entity.open.ctrip.PriceInfo;
import com.zizaike.entity.open.ctrip.RoomInfoItem;
import com.zizaike.entity.open.ctrip.SetMappingOperateType;
import com.zizaike.entity.open.ctrip.SetRoomPriceItem;
import com.zizaike.entity.open.ctrip.SetType;
import com.zizaike.open.bastest.BaseTest;
import com.zizaike.open.common.util.XstreamUtil;

/**
 * ClassName:httpUrlConnectionTest <br/>
 * Function: xml 连接 <br/>
 * Date: 2016年1月7日 下午5:29:35 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 * @see
 */
public class CtripConnectionTest extends BaseTest {
    @Value("${ctrip.userId}")
    private String userId;
    @Value("${ctrip.supplierID}")
    private String supplierID;
    @Value("${ctrip.url}")
    private String url;
    @Value("${ctrip.username}")
    private String username;
    @Value("${ctrip.password}")
    private String password;
    private String prefix = "soap_template/ctrip";
    @Autowired
    private SoapFastUtil soapFastUtil;

   
    @Test(description = "设置房型价格")
    public void setRoomPrice() throws ZZKServiceException, MalformedURLException {
        String template = "SetRoomPrice.vm";
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        Map map = new HashMap();
        map.put("userName", username);
        map.put("password", password);
        map.put("userId", userId);
        map.put("date", dateString);
        /*
         * params
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        List<SetRoomPriceItem> setRoomPriceItems = new ArrayList<SetRoomPriceItem>();
        SetRoomPriceItem setRoomPriceItem = new SetRoomPriceItem();
        setRoomPriceItem.setRoomID(440241);
        setRoomPriceItem.setStartDate("2014-08-21T18:11:35");
        setRoomPriceItem.setEndDate("2014-08-22T18:11:35");
        setRoomPriceItem.setCurrency("CNY");
        List<PriceInfo> priceInfos = new ArrayList<PriceInfo>();
        priceInfos.add(new PriceInfo("111111", 2, "Sell", "FG", 1, 257, 257, 257, 257, 1));
        priceInfos.add(new PriceInfo("111111", 2, "Sell", "FG", 1, 259, 259, 259, 259, 1));
        setRoomPriceItem.setPriceInfos(priceInfos);
        setRoomPriceItems.add(setRoomPriceItem);

        SetRoomPriceItem setRoomPriceItem2 = new SetRoomPriceItem();
        setRoomPriceItem2.setRoomID(550245);
        setRoomPriceItem2.setStartDate("2014-08-21T18:11:35");
        setRoomPriceItem2.setEndDate("2014-08-22T18:11:35");

        setRoomPriceItem.setCurrency("CNY");
        List<PriceInfo> priceInfos2 = new ArrayList<PriceInfo>();
        priceInfos2.add(new PriceInfo("111111", 2, "Sell", "FG", 1, 328, 328, 328, 328, 1));
        setRoomPriceItem.setPriceInfos(priceInfos2);
        setRoomPriceItems.add(setRoomPriceItem2);
        map.put("setRoomPriceItems", setRoomPriceItems);
        map.put("hotelID", "115339");
        map.put("title", "");
        map.put("submitor", "tujia");
        map.put("priority", "N");

        try {
            long start = System.currentTimeMillis();
            String xmlStr = soapFastUtil.post(map, prefix, template, url, "");
            System.out.println(xmlStr);
            System.err.println(System.currentTimeMillis() - start);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(description = "设置房态")
    public void setRoomInfo() throws ZZKServiceException, MalformedURLException {
        String template = "SetRoomInfo.vm";
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        Map map = new HashMap();
        map.put("userName", username);
        map.put("password", password);
        map.put("userId", userId);
        map.put("date", dateString);
        List<RoomInfoItem> roomInfoItems = new ArrayList<RoomInfoItem>();
        roomInfoItems.add(new RoomInfoItem(1, "F", 2, 2, 1900, "G", 4, 4, "7days", 6, "T", "F", "S", 6, 36, "F",
                -2147483648, 0, "F"));
        roomInfoItems.add(new RoomInfoItem(1, "F", 2, 2, 1900, "N", 6, 2, "7days", 6, "T", "F", "S", 6, 36, "F",
                -2147483648, 0, "F"));
        map.put("roomInfoItems", roomInfoItems);
        map.put("roomID", 438864);
        map.put("startDate", "2012-08-15T00:00:00");
        map.put("endDate", "2012-08-16T00:00:00");
        map.put("editer", "tujia");
        try {
            long start = System.currentTimeMillis();
            String xmlStr = soapFastUtil.post(map, prefix, template, url, "");
            System.out.println(xmlStr);
            System.err.println(System.currentTimeMillis() - start);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(description = "订单状态推送")
    public void domesticPushOrderStatus() throws ZZKServiceException, MalformedURLException {
        String template = "DomesticPushOrderStatus.vm";
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        Map map = new HashMap();
        map.put("userName", username);
        map.put("password", password);
        map.put("userId", userId);
        map.put("date", dateString);
        map.put("orderID", "203766563");
        map.put("interFaceSendID", "14112314");
        map.put("orderStatus", 3);
        map.put("interfaceConfirmNo", "34282006");
        map.put("hotelConfirmNo", "825098");
        try {
            long start = System.currentTimeMillis();
            String xmlStr = soapFastUtil.post(map, prefix, template, url, "");
            System.out.println(xmlStr);
            System.err.println(System.currentTimeMillis() - start);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test(description = "获得Ctrip的酒店信息")
    public void getHotelInfo() throws ZZKServiceException, MalformedURLException {
        String template = "GetHotelInfo.vm";
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        Map map = new HashMap();
        map.put("userName", username);
        map.put("password", password);
        map.put("date", dateString);
        map.put("userId", userId); 
        map.put("supplierID", supplierID);
        try {
            long start = System.currentTimeMillis();
            String xmlStr = soapFastUtil.post(map, prefix, template, url, "");
            String xml = xmlStr.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
            System.err.println(xml);
            Document doc = null;
            try {
                doc = DocumentHelper.parseText(xml);
            } catch (DocumentException e) {
                e.printStackTrace();  
            }
            Element root = doc.getRootElement();
            Element requestResult = root.element("Body").element("AdapterRequestResponse")
            .element("AdapterRequestResult").element("RequestResponse").element("RequestResult");
            if(requestResult.element("ResultCode").getText().equals("0")){
                String xmlS = requestResult.element("Response").element("GetHotelInfoResponse").asXML();
                GetHotelInfoResponse getHotelInfoResponse = (GetHotelInfoResponse) XstreamUtil.getXml2Bean(xmlS,GetHotelInfoResponse.class);
                System.err.println(getHotelInfoResponse);
            }    
            System.err.println(System.currentTimeMillis() - start);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test(description = "设置Mapping 信息")
    public void setMappingInfo() throws ZZKServiceException, MalformedURLException {
        String template = "SetMappingInfo.vm";
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        Map map = new HashMap();
        map.put("userName", username);
        map.put("password", password);
        map.put("date", dateString);
        map.put("userId", userId);
        map.put("supplierID", supplierID);
        //京都酒店  标准潮式雙床房
        map.put("masterHotel", 436553);
        map.put("masterRoom", 1249250);
        
        map.put("ratePlanCode", "902548");
        map.put("hotelGroupHotelCode", 3281112);
        map.put("hotelGroupRoomTypeCode", 39241112);
        map.put("hotelGroupRatePlanCode", 39241112);
        map.put("hotelGroupRoomName", "三人套房-105(三小床.有窗)testing1");
        map.put("balanceType", "PP");
        map.put("mappingType", MappingType.MUTUAL_MAPPING.getValue());
        map.put("setType", SetType.ADD.getValue());
        map.put("setMappingOperateType", SetMappingOperateType.REQUEST_A_NEW_CTRIP_HOTEL);
        try {
            long start = System.currentTimeMillis();
            String xmlStr = soapFastUtil.post(map, prefix, template, url, "");
            String xml = xmlStr.replaceAll("&amp;", "&").replaceAll("&lt;", "<").replaceAll("&gt;", ">");
            System.err.println(xml);
            Document doc = null;
            doc = DocumentHelper.parseText(xml);
            Element root = doc.getRootElement();
            Element requestResult = root.element("Body").element("AdapterRequestResponse")
                    .element("AdapterRequestResult").element("RequestResponse").element("RequestResult");
            if(!requestResult.element("ResultCode").getText().equals("0")){
                throw new IllegalParamterException(xmlStr);
            }  
            System.err.println(System.currentTimeMillis() - start);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test(description = "获得Ctrip对接信息")
    public void getMappingInfo() throws ZZKServiceException, MalformedURLException {
        String template = "GetMappingInfo.vm";
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        Map map = new HashMap();
        map.put("userName", username);
        map.put("password", password);
        map.put("date", dateString);
        map.put("userId", userId);
        map.put("supplierID", supplierID);
        //获取信息类型：UnMapping表示  获取未对接的信息， Appoint表示获取指定信息
        map.put("getMappingInfoType", GetMappingInfoType.Appoint);
        List list = new ArrayList();
        list.add(4504433);
        map.put("hotels", list);
        try {
            long start = System.currentTimeMillis();
            String xmlStr = soapFastUtil.post(map, prefix, template, url, "");
            String xml = xmlStr.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
            System.err.println(xml);
            Document doc = null;
            doc = DocumentHelper.parseText(xml);
            Element root = doc.getRootElement();
            Element requestResult = root.element("Body").element("AdapterRequestResponse")
                    .element("AdapterRequestResult").element("RequestResponse").element("RequestResult");
                    if(requestResult.element("ResultCode").getText().equals("0")){
                        String xmlS = requestResult.element("Response").element("GetMappingInfoResponseList").asXML();
                        GetMappingInfoResponseList getMappingInfoResponseList = (GetMappingInfoResponseList) XstreamUtil.getXml2Bean(xmlS,GetMappingInfoResponseList.class);
                        System.err.println(getMappingInfoResponseList);
                    }    
            System.err.println(System.currentTimeMillis() - start);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test(description = "获得房型对照")
    public void getCtripRoomTypeInfo() throws ZZKServiceException, MalformedURLException {
        String template = "GetCtripRoomTypeInfo.vm";
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        Map map = new HashMap();
        map.put("userName", username);
        map.put("password", password);
        map.put("userId", userId);
        map.put("date", dateString);
        map.put("hotelGroupRoomTypeCode", 392411);
        map.put("hotelGroupHotelCode", 32811);
        map.put("hotelGroupRatePlanCode", 392411);
        try {
            long start = System.currentTimeMillis();
            String xml = soapFastUtil.post(map, prefix, template, url, "");
            System.out.println(xml);
            
            Document doc = null;
            try {
                doc = DocumentHelper.parseText(xml);
            } catch (DocumentException e) {
                e.printStackTrace();  
            }
            Element root = doc.getRootElement();
            Element requestResult = root.element("Body").element("RequestResponse").element("RequestResult");
            if(requestResult.element("ResultCode").getText().equals("0")){
                String xmlS = requestResult.element("Response").element("HotelGroupInterfaceRoomTypeListResponse").element("HotelGroupInterfaceRoomTypeList").element("HotelGroupInterfaceRoomTypeEntity").asXML();
                HotelGroupInterfaceRoomTypeEntity hotelGroupInterfaceRoomTypeEntity = (HotelGroupInterfaceRoomTypeEntity) XstreamUtil.getXml2Bean(xmlS,HotelGroupInterfaceRoomTypeEntity.class);
                System.err.println(hotelGroupInterfaceRoomTypeEntity);
            }    
            System.err.println(System.currentTimeMillis() - start);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}