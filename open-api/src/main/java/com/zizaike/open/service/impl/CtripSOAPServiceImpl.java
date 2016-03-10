/**  
 * Project Name:open-service  <br/>
 * File Name:CtripSOAPServiceImpl.java  <br/>
 * Package Name:com.zizaike.open.service.impl  <br/>
 * Date:2016年3月10日下午6:39:02  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.service.impl;  

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.is.open.CtripService;
import com.zizaike.open.service.CtripSOAPService;

/**  
 * ClassName:CtripSOAPServiceImpl <br/>  
 * Function: CtripSoapService. <br/>  
 * Date:     2016年3月10日 下午6:39:02 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
@WebService(endpointInterface="com.zizaike.open.service.CtripSOAPService")
public class CtripSOAPServiceImpl implements CtripSOAPService {
    @Autowired
    private CtripService ctripService;
    @Override
    public String invoke(String xml) {
        String returnXml = null;
        try {
            returnXml = ctripService.service(xml);
        } catch (ZZKServiceException e) {
            e.printStackTrace();  
        }
        return returnXml;
    }

}
  
