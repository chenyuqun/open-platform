/**  
 * Project Name:open-service  <br/>
 * File Name:CtripSOAPServiceImpl.java  <br/>
 * Package Name:com.zizaike.open.service.impl  <br/>
 * Date:2016年3月10日下午6:39:02  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.service.impl;  

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.ws.rs.Path;

import org.restlet.resource.Post;
import org.springframework.beans.factory.annotation.Autowired;

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.is.open.CtripService;

/**  
 * ClassName:CtripSOAPServiceImpl <br/>  
 * Function: CtripSoapService. <br/>  
 * Date:     2016年3月10日 下午6:39:02 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
@SOAPBinding(style=Style.RPC)
@WebService(serviceName="ctrip",portName="ctrip",targetNamespace="http://service.open.zizaike.com/"/**,endpointInterface="com.zizaike.open.service.CtripSOAPService"**/)
public class CtripSOAPServiceImpl  {
    @Autowired
    private CtripService ctripService;
    public String Invoke(@WebParam(name = "xml") String xml,@WebParam(name = "Invoketype") String invoketype) {
        String returnXml = null;
        try {
            returnXml = ctripService.service(xml);
        } catch (ZZKServiceException e) {
            e.printStackTrace();  
        }
        return returnXml;
    }

}
  
