/**  
 * Project Name:open-service  <br/>
 * File Name:CtripSOAPService.java  <br/>
 * Package Name:com.zizaike.open.service  <br/>
 * Date:2016年3月10日下午6:32:33  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.service;  

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;


/**  
 * ClassName:CtripSOAPService <br/>  
 * Function: soap服务 . <br/>  
 * Date:     2016年3月10日 下午6:32:33 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
//@SOAPBinding(style=Style.RPC)
//@WebService()
public interface CtripSOAPService {
    String invoke(@WebParam(name = "xml") String xml,@WebParam(name = "Invoketype") String invoketype);
}
  
