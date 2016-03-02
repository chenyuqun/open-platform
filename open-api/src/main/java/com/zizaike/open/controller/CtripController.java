/**  
 * Project Name:app-api  <br/>
 * File Name:RoomController.java  <br/>
 * Package Name:com.zizaike.app.api.controller.search  <br/>
 * Date:2015年11月23日下午4:50:13  <br/>
 * Copyright (c) 2015, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.controller;  

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.is.open.CtripService;
import com.zizaike.open.BaseXMLController;

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
public class CtripController extends BaseXMLController {
    protected final Logger LOG = LoggerFactory.getLogger(CtripController.class);
    @Autowired
    private CtripService ctripService;
    @RequestMapping(value = "", method = RequestMethod.POST,produces={"application/xml"},consumes={"application/xml"})
    @ResponseBody
    public String getSearchResult(@RequestBody String xml ) throws ZZKServiceException {
        LOG.info("ctripService xml:{}",xml);
        return ctripService.service(xml);
    }
}
  
