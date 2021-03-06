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
import com.zizaike.is.open.TaobaoService;
import com.zizaike.open.BaseXMLController;

/**
 * 
 * ClassName: TaoBaoController <br/>  
 * Function: taobao. <br/>  
 * date: 2016年1月14日 上午11:03:23 <br/>  
 *  
 * @author snow.zhang  
 * @version   
 * @since JDK 1.7
 */
@Controller
@RequestMapping("/taobaoService")
public class TaoBaoController extends BaseXMLController {
    protected final Logger LOG = LoggerFactory.getLogger(TaoBaoController.class);
    @Autowired
    private TaobaoService taobaoService;
    @RequestMapping(value = "", method = RequestMethod.POST,produces={"text/xml"},consumes={"text/xml"})
    @ResponseBody
    public String getSearchResult(@RequestBody String xml ) throws ZZKServiceException {
        LOG.info("taobaoService xml:{}",xml);
       return taobaoService.service(xml);
    }
}
  
