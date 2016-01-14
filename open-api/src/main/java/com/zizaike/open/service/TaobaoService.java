/**  
 * Project Name:open-api  <br/>
 * File Name:TaobaoService.java  <br/>
 * Package Name:com.zizaike.open.service  <br/>
 * Date:2016年1月13日下午3:48:27  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.service;  

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.zizaike.open.entity.taobao.request.ValidateRQRequest;

/**  
 * ClassName:TaobaoService <br/>  
 * Function: . <br/>  
 * Date:     2016年1月13日 下午3:48:27 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public interface TaobaoService {
   String  validateRQ(ValidateRQRequest v);
}
  
