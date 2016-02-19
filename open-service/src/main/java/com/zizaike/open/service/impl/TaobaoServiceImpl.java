/**  
 * Project Name:open-api  <br/>
 * File Name:TaoBaoServiceImpl.java  <br/>
 * Package Name:com.zizaike.open.service.impl  <br/>
 * Date:2016年1月13日下午4:11:16  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.service.impl;  

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zizaike.core.common.util.http.HttpProxyUtil;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.core.framework.exception.open.ErrorCodeFields;
import com.zizaike.core.framework.exception.open.SystemException;
import com.zizaike.entity.open.User;
import com.zizaike.entity.open.alibaba.InventoryPrice;
import com.zizaike.entity.open.alibaba.request.BookRQRequest;
import com.zizaike.entity.open.alibaba.request.CancelRQRequest;
import com.zizaike.entity.open.alibaba.request.QueryStatusRQRequest;
import com.zizaike.entity.open.alibaba.request.ValidateRQRequest;
import com.zizaike.entity.open.alibaba.response.BillInfo;
import com.zizaike.entity.open.alibaba.response.BookRQResponse;
import com.zizaike.entity.open.alibaba.response.CancelRQResponse;
import com.zizaike.entity.open.alibaba.response.OrderInfo;
import com.zizaike.entity.open.alibaba.response.QueryStatusRQResponse;
import com.zizaike.entity.open.alibaba.response.ResponseData;
import com.zizaike.entity.open.alibaba.response.ValidateRQResponse;
import com.zizaike.is.open.TaobaoService;
import com.zizaike.is.open.UserService;
import com.zizaike.open.common.util.XstreamUtil;

/**  
 * ClassName:TaoBaoServiceImpl <br/>  
 * Function: 订单业务. <br/>  
 * Date:     2016年1月13日 下午4:11:16 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
@Service
public class TaobaoServiceImpl implements TaobaoService {
    protected final Logger LOG = LoggerFactory.getLogger(TaobaoServiceImpl.class);
    @Value("${zizaike.open.alitrip.host}")
    private String alitripHost;
    @Autowired
    private HttpProxyUtil httpProxy;
    @Autowired
    private UserService userService;
    
    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat simpleDateFormatAccurate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    public ValidateRQResponse validateRQ(ValidateRQRequest validateRQRequest) throws ZZKServiceException {  
        try {
            
            Map<String,String> map = new HashMap<String, String>();
            map.put("roomTypeId", validateRQRequest.getRoomTypeId());
            map.put("openHotelId", validateRQRequest.getTaoBaoHotelId().toString());
            map.put("openRatePlanId", validateRQRequest.getTaoBaoRatePlanId().toString());
            map.put("ratePlanCode", validateRQRequest.getRatePlanCode());
            map.put("openGid", validateRQRequest.getTaoBaoGid().toString());
            map.put("checkIn", simpleDateFormat.format(validateRQRequest.getCheckIn()));
            map.put("checkOut", simpleDateFormat.format(validateRQRequest.getCheckOut()));
            map.put("roomNum", validateRQRequest.getRoomNum().toString());
            map.put("paymentType", validateRQRequest.getPaymentType().toString());
            map.put("extensions", validateRQRequest.getExtensions());
            JSONObject result=httpProxy.httpGet(alitripHost+"validateRQ", map);
            ValidateRQResponse validateRQResponse = new ValidateRQResponse();
            ErrorCodeFields errorCodeFields;
            if(result.getString("resultCode").equals("200")){
                /**
                 * 解析返回价格参数
                 */
                List<InventoryPrice> inventoryPriceList = JSON.parseArray(result.getJSONObject("info").getString("inventoryPrice"),InventoryPrice.class);
                for(int i=0;i<inventoryPriceList.size();i++){
                    InventoryPrice inventory=inventoryPriceList.get(i);
                    if(inventory.getQuota()<validateRQRequest.getRoomNum()){
                        errorCodeFields= ErrorCodeFields.ROOM_FULL_NOT_BOOK_ERROR;
                        throw new ZZKServiceException(errorCodeFields);
                    };
                }
                validateRQResponse.setInventoryPrice(result.getJSONObject("info").getString("inventoryPrice"));              
                return validateRQResponse;  
            }else{
                
                switch (result.getString("resultCode")) {
                case "408":
                    errorCodeFields = ErrorCodeFields.ROOM_FULL_NOT_BOOK_ERROR;
                    break;
                case "206":
                    errorCodeFields = ErrorCodeFields.RP_ERROR;
                    break;
                default:
                    errorCodeFields =  ErrorCodeFields.OTHER_NOT_BOOK_ERROR;
                    break;
                }
                throw new ZZKServiceException(errorCodeFields);
            }
        } catch (IOException e) {
            LOG.error("IOException, ex={}", e);
            throw new SystemException();
        }
    }

    @Override
    public BookRQResponse bookRQ(BookRQRequest bookRQRequest) throws ZZKServiceException {
        try {
            Map<String,String> map = new HashMap<String,String>();
            map.put("openOrderId", Long.toString(bookRQRequest.getTaoBaoOrderId()));
            map.put("openHotelId", Long.toString(bookRQRequest.getTaoBaoHotelId()));
            map.put("hotelId", bookRQRequest.getHotelId());
            map.put("openRoomTypeId", Long.toString(bookRQRequest.getTaoBaoRoomTypeId()));
            map.put("roomTypeId", bookRQRequest.getRoomTypeId());
            map.put("openRatePlanId", Long.toString(bookRQRequest.getTaoBaoRatePlanId()));
            map.put("ratePlanCode", bookRQRequest.getRatePlanCode());
            map.put("openGid", Long.toString(bookRQRequest.getTaoBaoGid()));
            map.put("checkIn", simpleDateFormat.format(bookRQRequest.getCheckIn()));
            map.put("checkOut", simpleDateFormat.format(bookRQRequest.getCheckOut()));
            map.put("hourRent", bookRQRequest.getHourRent());
            map.put("earliestArriveTime", simpleDateFormatAccurate.format(bookRQRequest.getEarliestArriveTime()));
            map.put("latestArriveTime", simpleDateFormatAccurate.format(bookRQRequest.getLatestArriveTime()));
            map.put("roomNum", Integer.toString(bookRQRequest.getRoomNum()));
            map.put("totalPrice", Long.toString(bookRQRequest.getTotalPrice()));
            map.put("sellerDiscount", Long.toString(bookRQRequest.getSellerDiscount()));
            map.put("alitripDiscount", Long.toString(bookRQRequest.getAlitripDiscount()));
            map.put("currency", bookRQRequest.getCurrency());
            map.put("paymentType", Integer.toString(bookRQRequest.getPaymentType()));
            map.put("contactName", bookRQRequest.getContactName());
            map.put("contactTel", bookRQRequest.getContactTel());
            map.put("contactEmail", bookRQRequest.getContactEmail());
            map.put("dailyInfos", JSON.toJSON(bookRQRequest.getDailyInfos()).toString());
//            map.put("day", bookRQRequest.getExtensions());
//            map.put("price", bookRQRequest.getPrice());
            map.put("orderGuests", JSON.toJSON(bookRQRequest.getOrderGuests()).toString());
//            map.put("name", bookRQRequest);
//            map.put("roomPos", bookRQRequest.getR);
            map.put("comment", bookRQRequest.getComment());
            map.put("memberCardNo", bookRQRequest.getMemberCardNo());
            map.put("guaranteeType", bookRQRequest.getGuaranteeType());
            map.put("extensions", bookRQRequest.getExtensions());
            map.put("openTradeNo", bookRQRequest.getAlipayTradeNo());
            /**
             * zizaike下单人数
             */
            if(null!=(bookRQRequest.getOrderGuests().getOrderGuests())){
                List<com.zizaike.entity.open.alibaba.request.BookRQRequest.OrderGuest> orderGuests= bookRQRequest.getOrderGuests().getOrderGuests();
                map.put("guestNumber", orderGuests.size()>0?Integer.toString(orderGuests.size()):"1");
            }else{
                map.put("guestNumber","1");
            }
            
            JSONObject result=httpProxy.httpGet(alitripHost+"bookRQ", map);
            BookRQResponse bookRQResponse = new BookRQResponse();
            ErrorCodeFields errorCodeFields;
            /**
             * 200是success 201是网站端口下单成功[非速订]
             */
            if(result.getString("resultCode").equals("200")||result.getString("resultCode").equals("201")){           
                bookRQResponse.setOrderId(result.getJSONObject("info").getString("orderId"));
                bookRQResponse.setPmsResID(result.getJSONObject("info").getString("pmsResId"));
                return bookRQResponse;  
            }else{
                
                switch (result.getString("resultCode")) {
                case "400":
                    errorCodeFields = ErrorCodeFields.BOOK_PARAMS_ERROR;
                    break;
                case "401":
                    errorCodeFields = ErrorCodeFields.BOOK_ROOMTYPE_NOT_EXIST;
                    break;
                case "402":
                    errorCodeFields = ErrorCodeFields.BOOK_HOTEL_NOT_EXIST;
                    break;
                case "403":
                    errorCodeFields = ErrorCodeFields.BOOK_CONTACT_NAME_ERROR;
                    break;
                case "404":
                    errorCodeFields = ErrorCodeFields.BOOK_CONTACT_PHONE_ERROR;
                    break;
                case "406":
                    errorCodeFields = ErrorCodeFields.BOOK_CHECKIN_ERROR;
                    break;                    
                case "407":
                    errorCodeFields = ErrorCodeFields.BOOK_CHECKOUT_ERROR;
                    break;
                case "408":
                    errorCodeFields = ErrorCodeFields.BOOK_OVER_ROOM_LIMIT;
                    break;
//                    /**
//                     * 409表示房间连住条件不满足
//                     */
//                case "409":
//                    errorCodeFields = ErrorCodeFields.OTHER_NOT_BOOK_ERROR;
//                    break;
//                case "2":
//                    errorCodeFields = ErrorCodeFields.OTHER_NOT_BOOK_ERROR;
//                    break;
//                case "206":
//                    errorCodeFields = ErrorCodeFields.OTHER_NOT_BOOK_ERROR;
//                    break;
//                case "205":
//                    errorCodeFields = ErrorCodeFields.OTHER_NOT_BOOK_ERROR;
//                    break;
                default:
                    errorCodeFields =  ErrorCodeFields.BOOK_FAILURE;
                    break;
                }
                throw new ZZKServiceException(errorCodeFields);
            }
        } catch (IOException e) {
            LOG.error("IOException, ex={}", e);
            throw new SystemException();
        }
    }

    @Override
    public QueryStatusRQResponse queryStatusRQ(QueryStatusRQRequest queryStatusRQRequest) throws ZZKServiceException{
        try {            
            Map<String,String> map = new HashMap<String, String>();
            map.put("orderId", queryStatusRQRequest.getOrderId());
            map.put("openOrderId", Long.toString(queryStatusRQRequest.getTaoBaoOrderId()));
            map.put("hotelId", queryStatusRQRequest.getHotelId());
            JSONObject result=httpProxy.httpGet(alitripHost+"queryStatusRQ", map);
            QueryStatusRQResponse queryStatusRQResponse = new QueryStatusRQResponse();
            ErrorCodeFields errorCodeFields;     
            if(result.getString("resultCode").equals("200")){
                
                queryStatusRQResponse.setOrderId(result.getJSONObject("info").getString("orderId"));
                queryStatusRQResponse.setTaoBaoOrderId(Long.parseLong(result.getJSONObject("info").getString("openOrderId")));
                /**
                 * 订单状态转义
                 */
                if("2".equals(result.getJSONObject("info").getString("status"))||"6".equals(result.getJSONObject("info").getString("status"))){
                    queryStatusRQResponse.setStatus("1");
                }else{
                    queryStatusRQResponse.setStatus("6");
                }
                queryStatusRQResponse.setBillInfo(JSON.parseObject(result.getJSONObject("info").getString("billInfo"), BillInfo.class));
                queryStatusRQResponse.setOrderInfo(JSON.parseObject(result.getJSONObject("info").getString("orderInfo"), OrderInfo.class));
                return queryStatusRQResponse;  
            }else{
                
                switch (result.getString("resultCode")) {
                case "204":
                    errorCodeFields = ErrorCodeFields.QUERY_ORDER_NOT_EXIST;
                    break;
                default:
                    errorCodeFields =  ErrorCodeFields.QUERY_FAILURE;                  
                    break;
                }
                throw new ZZKServiceException(errorCodeFields);
            }
        } catch (IOException e) {
            LOG.error("IOException, ex={}", e);
            throw new SystemException();
        }
    }

    @Override
    public CancelRQResponse cancelRQ(CancelRQRequest cancelRQRequest) throws ZZKServiceException{         
        try {            
            Map<String,String> map = new HashMap<String, String>();
            map.put("orderId", cancelRQRequest.getOrderId());
            map.put("openOrderId", Long.toString(cancelRQRequest.getTaoBaoOrderId()));
            map.put("hotelId", cancelRQRequest.getHotelId());
            map.put("reason", cancelRQRequest.getReason());
            map.put("hardCancel", cancelRQRequest.getHardCancel());
            JSONObject result=httpProxy.httpGet(alitripHost+"cancelRQ", map);
            CancelRQResponse cancelRQResponse = new CancelRQResponse();
            ErrorCodeFields errorCodeFields;     
            if(result.getString("resultCode").equals("200")){
                cancelRQResponse.setOrderId(result.getJSONObject("info").getString("orderId"));
                return cancelRQResponse;  
            }else{
                switch (result.getString("resultCode")) {
                case "205":
                    errorCodeFields = ErrorCodeFields.CANCEL_ORDER_ALREADY_CANCELLED;
                    break;
                case "204":
                    errorCodeFields = ErrorCodeFields.CANCEL_ORDER_NOT_EXIST;
                    break;
                default:
                    errorCodeFields = ErrorCodeFields.CANCEL_FAILURE;
                    break;
                      
                }
                throw new ZZKServiceException(errorCodeFields);
            }
        } catch (IOException e) {
            LOG.error("IOException, ex={}", e);
            throw new SystemException();
        }
    }

    @Override
    public String service(String xml) throws ZZKServiceException {
        
        Document doc = null;
        ResponseData responseData = null;
        try {
            doc = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            e.printStackTrace();  
        }
        Element root = doc.getRootElement();
        checkUser(root);
        switch (root.getQualifiedName()) {
        case "ValidateRQ":
            responseData= validateRQ((ValidateRQRequest)XstreamUtil.getXml2Bean(xml, ValidateRQRequest.class));
            break;
        case "BookRQ":
            responseData= bookRQ((BookRQRequest)XstreamUtil.getXml2Bean(xml, BookRQRequest.class));
            break;
        case "QueryStatusRQ":
            responseData= queryStatusRQ((QueryStatusRQRequest)XstreamUtil.getXml2Bean(xml, QueryStatusRQRequest.class));
            break;
        case "CancelRQ":
            responseData= cancelRQ((CancelRQRequest)XstreamUtil.getXml2Bean(xml, CancelRQRequest.class));
            break;
        default:
            break;
        }
        return XstreamUtil.getResponseXml(responseData);
    }
    
    public void checkUser(Element root)throws ZZKServiceException{
        Element authenticationToken = root.element("AuthenticationToken");
        User user = new User();
        user.setUsername(authenticationToken.element("Username").getText());
        user.setPassword(authenticationToken.element("Password").getText());
        userService.checkUser(user);
    }


}
  
