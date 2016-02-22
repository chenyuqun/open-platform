/**  
 * Project Name:app-api  <br/>
 * File Name:RoomController.java  <br/>
 * Package Name:com.zizaike.app.api.controller.search  <br/>
 * Date:2015年11月23日下午4:50:13  <br/>
 * Copyright (c) 2015, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.controller;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.is.open.TaobaoService;
import com.zizaike.open.BaseAjaxController;

/**
 * 
 * ClassName: CtripController <br/>  
 * Function: ctrip调用服务. <br/>  
 * date: 2016年2月3日 下午3:04:25 <br/>  
 *  
 * @author snow.zhang  
 * @version   
 * @since JDK 1.7
 */
@Controller
@RequestMapping("/ctrip")
public class CtripController extends BaseAjaxController {
    @Autowired
    private TaobaoService taobaoService;
    @RequestMapping(value = "", method = RequestMethod.POST,produces={"application/xml"},consumes={"application/xml"})
    @ResponseBody
    public String getSearchResult(@RequestBody String xml ) throws ZZKServiceException {
       return taobaoService.service(xml);
    }
}
  
