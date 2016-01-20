/**  
 * Project Name:open-api  <br/>
 * File Name:TaoBaoServiceImpl.java  <br/>
 * Package Name:com.zizaike.open.service.impl  <br/>
 * Date:2016年1月13日下午4:11:16  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.service.impl;  

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.zizaike.open.common.util.XstreamUtil;
import com.zizaike.open.entity.taobao.request.BookRQRequest;
import com.zizaike.open.entity.taobao.request.CancelRQRequest;
import com.zizaike.open.entity.taobao.request.QueryStatusRQRequest;
import com.zizaike.open.entity.taobao.request.ValidateRQRequest;
import com.zizaike.open.entity.taobao.response.BookRQResponse;
import com.zizaike.open.entity.taobao.response.CancelRQResponse;
import com.zizaike.open.entity.taobao.response.QueryStatusRQResponse;
import com.zizaike.open.entity.taobao.response.ResponseData;
import com.zizaike.open.entity.taobao.response.ValidateRQResponse;
import com.zizaike.open.service.TaobaoService;

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

    @Override
    public ValidateRQResponse validateRQ(ValidateRQRequest validateRQRequest) {
          
        ValidateRQResponse validateRQResponse = new ValidateRQResponse();
        validateRQResponse.setMessage("这是一个test试单请求");
        return validateRQResponse;
    }

    @Override
    public BookRQResponse bookRQ(BookRQRequest bookRQRequest) {
          
        BookRQResponse bookRQResponse = new BookRQResponse();
        bookRQResponse.setMessage("这是一个test下单请求");
        return bookRQResponse;
    }

    @Override
    public QueryStatusRQResponse queryStatusRQ(QueryStatusRQRequest queryStatusRQRequest) {
          
        QueryStatusRQResponse queryStatusRQResponse = new QueryStatusRQResponse();
        queryStatusRQResponse.setMessage("这是一个test查询请求");
        return queryStatusRQResponse;
    }

    @Override
    public CancelRQResponse cancelRQ(CancelRQRequest cancelRQRequest) {
          
        CancelRQResponse cancelRQResponse = new CancelRQResponse();
        cancelRQResponse.setMessage("这是一个test取消请求");;
        return cancelRQResponse;
    }

    @Override
    public String service(String xml) {
        Document doc = null;
        ResponseData responseData = null;
        try {
            doc = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            e.printStackTrace();  
        }
        Element root = doc.getRootElement();
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


}
  
