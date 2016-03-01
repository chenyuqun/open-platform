/**  
 * Project Name:open-api  <br/>
 * File Name:httpUrlConnectionTest.java  <br/>
 * Package Name:com.zizaike.open.service  <br/>
 * Date:2016年1月7日下午5:29:35  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
 */

package com.zizaike.open.service;

import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.testng.annotations.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.zizaike.core.common.util.http.SoapFastUtil;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.open.ctrip.PriceInfo;
import com.zizaike.entity.open.ctrip.RoomInfoItem;
import com.zizaike.entity.open.ctrip.SetRoomPriceItem;
import com.zizaike.open.bastest.BaseTest;

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
    @Value("${ctrip.url}")
    private String url;
    @Value("${ctrip.username}")
    private String username;
    @Value("${ctrip.password}")
    private String password;
    private String prefix = "soap_template/ctrip";
    @Autowired
    private SoapFastUtil soapFastUtil;

    @Test(description = "获得房型对照")
    public void getCtripRoomTypeInfo() throws ZZKServiceException, MalformedURLException {
        String template = "GetCtripRoomTypeInfo.vm";
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        Map map = new HashMap();
        map.put("userName", username);
        map.put("password", password);
        map.put("userId", 204);
        map.put("date", dateString);
        map.put("hotelGroupRoomTypeCode", 228215);
        map.put("hotelGroupHotelCode", 81687);
        map.put("hotelGroupRatePlanCode", "");
        try {
            long start = System.currentTimeMillis();
            String xmlStr = soapFastUtil.post(map, prefix, template, url, "");
            System.out.println(xmlStr);
            System.err.println(System.currentTimeMillis() - start);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(description = "设置房型价格")
    public void setRoomPrice() throws ZZKServiceException, MalformedURLException {
        String template = "SetRoomPrice.vm";
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        Map map = new HashMap();
        map.put("userName", username);
        map.put("password", password);
        map.put("userId", 204);
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
        map.put("userId", 204);
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
        map.put("userId", 204);
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
        map.put("userId", 204);
        map.put("supplierID", 55);
        try {
            long start = System.currentTimeMillis();
            String xmlStr = soapFastUtil.post(map, prefix, template, url, "");
            String xml = xmlStr.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
            System.err.println(xml);
            Document doc = null;
            doc = DocumentHelper.parseText(xml);
            Element root = doc.getRootElement();
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
        map.put("userId", 204);
        map.put("supplierID", 55);
        map.put("getMappingInfoType", "UnMapping");
        Map value = new HashMap();
        value.put("id", 2075384);
        Map value1 = new HashMap();
        value1.put("id", 2075300);
        List list = new ArrayList();
        list.add(value);
        list.add(value1);
        map.put("hotels", list);
        try {
            long start = System.currentTimeMillis();
            String xmlStr = soapFastUtil.post(map, prefix, template, url, "");
            String xml = xmlStr.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
            System.err.println(xml);
            Document doc = null;
            doc = DocumentHelper.parseText(xml);
            Element root = doc.getRootElement();
            System.err.println(System.currentTimeMillis() - start);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws SOAPException {
        String xml = "<?xml version='1.0' encoding='utf-8'?>"
                +"<soap:Envelope xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema'>"
                 +"<soap:Body>"
                   +"<AdapterRequestResponse >"
                     +"<AdapterRequestResult>"
                       +"<RequestResponse>"
                         +"<RequestResult>"
                           +"<Message>接收成功</Message>"
                           +"<ResultCode>0</ResultCode>"
                           +"<Response>"
                             +"<HeaderInfo />"
                             +"<GetHotelInfoResponse>"
                               +"<TotalPage>27</TotalPage>"
                               +"<CurrentPage>2</CurrentPage>"
                               +"<TotalNum>1305</TotalNum>"
                               +"<HotelList>"
                                 +"<Hotel>2075278</Hotel>"
                                 +"<HotelName>杭州北苑客房</HotelName>"
                                 +"<CountryName>中国</CountryName>"
                                 +"<CityName>杭州</CityName>"
                                 +"<Address>0</Address>"
                                 +"<Telephone>0</Telephone>"
                               +"</HotelList>"
                               +"<HotelList>"
                                 +"<Hotel>2075300</Hotel>"
                                 +"<HotelName>杭州佑圣商务酒店</HotelName>"
                                 +"<CountryName>中国</CountryName>"
                                 +"<CityName>杭州</CityName>"
                                 +"<Address>0</Address>"
                                 +"<Telephone>0</Telephone>"
                               +"</HotelList>"
                               +"<HotelList>"
                                 +"<Hotel>2075384</Hotel>"
                                 +"<HotelName>成都林凯酒店</HotelName>"
                                 +"<CountryName>中国</CountryName>"
                                 +"<CityName>成都</CityName>"
                                 +"<Address>0</Address>"
                                 +"<Telephone>0</Telephone>"
                               +"</HotelList>"
                             +"</GetHotelInfoResponse>"
                           +"</Response>"
                         +"</RequestResult>"
                       +"</RequestResponse>"
                     +"</AdapterRequestResult>"
                   +"</AdapterRequestResponse>"
                +"</soap:Body>"
                +"</soap:Envelope>";
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            e.printStackTrace();  
        }
        Element root = doc.getRootElement();
        SOAPMessage msg = formatSoapString(xml);
        SOAPBody body = msg.getSOAPBody();
        Iterator<SOAPElement> iterator = body.getChildElements();
         PrintBody(iterator, null);
 
    }
    
    private static SOAPMessage formatSoapString(String soapString) {
               MessageFactory msgFactory;
                 try {
                     msgFactory = MessageFactory.newInstance();
                     SOAPMessage reqMsg = msgFactory.createMessage(new MimeHeaders(),
                             new ByteArrayInputStream(soapString.getBytes("UTF-8")));
                     reqMsg.saveChanges();
                     return reqMsg;
                 } catch (Exception e) {
                     e.printStackTrace();
                     return null;
                 }
             }
    private static void PrintBody(Iterator<SOAPElement> iterator, String side) {
        while (iterator.hasNext()) {
            SOAPElement element = (SOAPElement) iterator.next();
            System.out.println("Local Name:" + element.getLocalName());
            System.out.println("Node Name:" + element.getNodeName());
            System.out.println("Tag Name:" + element.getTagName());
            System.out.println("Value:" + element.getValue());
            if (null == element.getValue()
                    && element.getChildElements().hasNext()) {
                PrintBody(element.getChildElements(), side + "-----");
            }
        }
    }
}
