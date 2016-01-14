/**  
 * Project Name:open-api  <br/>
 * File Name:TaoBaoServiceImpl.java  <br/>
 * Package Name:com.zizaike.open.service.impl  <br/>
 * Date:2016年1月13日下午4:11:16  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.service.impl;  

import javax.jws.WebService;

import com.zizaike.open.entity.taobao.request.ValidateRQRequest;
import com.zizaike.open.service.TaobaoService;

/**  
 * ClassName:TaoBaoServiceImpl <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2016年1月13日 下午4:11:16 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public class TaobaoServiceImpl implements TaobaoService {

    @Override
    public String validateRQ(ValidateRQRequest v) {
       return v.toString();
    }

}
  
