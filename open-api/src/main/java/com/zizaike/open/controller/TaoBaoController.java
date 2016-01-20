/**  
 * Project Name:app-api  <br/>
 * File Name:RoomController.java  <br/>
 * Package Name:com.zizaike.app.api.controller.search  <br/>
 * Date:2015年11月23日下午4:50:13  <br/>
 * Copyright (c) 2015, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.controller;  

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.open.BaseAjaxController;
import com.zizaike.open.service.TaobaoService;

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
public class TaoBaoController extends BaseAjaxController {
    @Autowired
    private TaobaoService taobaoService;
    @RequestMapping(value = "", method = RequestMethod.POST,produces={"application/xml"},consumes={"application/xml"})
    @ResponseBody
    public String getSearchResult(@RequestBody String xml ) throws ZZKServiceException, Exception, IOException {
        
       return taobaoService.service(xml);
    }
}
  
