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

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.zizaike.core.common.util.http.SoapFastUtil;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.open.ctrip.PriceInfo;
import com.zizaike.entity.open.ctrip.RoomInfoItem;
import com.zizaike.entity.open.ctrip.SetRoomPriceItem;
import com.zizaike.open.bastest.BaseTest;

/**  
 * ClassName:httpUrlConnectionTest <br/>  
 * Function: xml 连接 <br/>  
 * Date:     2016年1月7日 下午5:29:35 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public class CtripConnectionTest extends BaseTest{
    @Autowired
    private SoapFastUtil soapFastUtil;
    @Test(description = "获得房型对照")
    public void getCtripRoomTypeInfo() throws ZZKServiceException, MalformedURLException {
        String url = "http://58.221.127.196:8089/hotel/hotel-vendor-commonreceive/commonreceiveservice.asmx";
        String prefix = "soap_template/ctrip";
        String template = "GetCtripRoomTypeInfo.vm";
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        Map map = new HashMap();
        map.put("userName", "zhilianjishuzhuanshu");
        map.put("password", "zhilianzhuanshu11!!");
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
        String url = "http://58.221.127.196:8089/hotel/hotel-vendor-commonreceive/commonreceiveservice.asmx";
        String prefix = "soap_template/ctrip";
        String template = "SetRoomPrice.vm";
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        Map map = new HashMap();
        map.put("userName", "zhilianjishuzhuanshu");
        map.put("password", "zhilianzhuanshu11!!");
        map.put("userId", 204);
        map.put("date", dateString);
        /*
         * params
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        List<SetRoomPriceItem> setRoomPriceItems=new ArrayList<SetRoomPriceItem>();
        SetRoomPriceItem setRoomPriceItem=new SetRoomPriceItem();
        setRoomPriceItem.setRoomID(440241);         
        setRoomPriceItem.setStartDate("2014-08-21T18:11:35");   
        setRoomPriceItem.setEndDate("2014-08-22T18:11:35");   
        setRoomPriceItem.setCurrency("CNY");      
        List<PriceInfo> priceInfos=new ArrayList<PriceInfo>();
        priceInfos.add(new PriceInfo("111111",2,"Sell", "FG", 1,257,257, 257, 257,1));
        priceInfos.add(new PriceInfo("111111",2,"Sell", "FG", 1,259,259, 259, 259,1));
        setRoomPriceItem.setPriceInfos(priceInfos);
        setRoomPriceItems.add(setRoomPriceItem);
        
        SetRoomPriceItem setRoomPriceItem2=new SetRoomPriceItem();
        setRoomPriceItem2.setRoomID(550245);             
        setRoomPriceItem2.setStartDate("2014-08-21T18:11:35");   
        setRoomPriceItem2.setEndDate("2014-08-22T18:11:35");
        
        setRoomPriceItem.setCurrency("CNY");      
        List<PriceInfo> priceInfos2=new ArrayList<PriceInfo>();
        priceInfos2.add(new PriceInfo("111111",2,"Sell", "FG", 1,328,328, 328, 328,1));
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
        String url = "http://58.221.127.196:8089/hotel/hotel-vendor-commonreceive/commonreceiveservice.asmx";
        String prefix = "soap_template/ctrip";
        String template = "SetRoomInfo.vm";
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        Map map = new HashMap();
        map.put("userName", "zhilianjishuzhuanshu");
        map.put("password", "zhilianzhuanshu11!!");
        map.put("userId", 204);
        map.put("date", dateString);
        List<RoomInfoItem> roomInfoItems=new ArrayList<RoomInfoItem>();
        roomInfoItems.add(new RoomInfoItem(1, "F", 2, 2, 1900,
                "G", 4, 4, "7days", 6, "T",
                "F", "S", 6, 36 , "F", -2147483648,
                0, "F"));
        roomInfoItems.add(new RoomInfoItem(1, "F", 2, 2, 1900,
                "N", 6, 2, "7days", 6, "T",
                "F", "S", 6, 36 , "F", -2147483648,
                0, "F"));
        map.put("roomInfoItems", roomInfoItems);
        map.put("roomID", 438864);
        map.put("startDate","2012-08-15T00:00:00" );
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
        String url = "http://58.221.127.196:8089/hotel/hotel-vendor-commonreceive/commonreceiveservice.asmx";
        String prefix = "soap_template/ctrip";
        String template = "DomesticPushOrderStatus.vm";
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        Map map = new HashMap();
        map.put("userName", "zhilianjishuzhuanshu");
        map.put("password", "zhilianzhuanshu11!!");
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
}
  
