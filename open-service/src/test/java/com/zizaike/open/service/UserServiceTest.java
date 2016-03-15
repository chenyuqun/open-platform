/**  
 * Project Name:open-service  <br/>
 * File Name:Alibaba.java  <br/>
 * Package Name:com.zizaike.open.service  <br/>
 * Date:2016年1月11日下午4:51:02  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.service;  

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.taobao.api.ApiException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.open.User;
import com.zizaike.is.open.UserService;
import com.zizaike.open.bastest.BaseTest;

/**  
 * ClassName:Alibaba <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2016年1月11日 下午4:51:02 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public class UserServiceTest extends BaseTest{
    @Autowired
    private UserService userService;
    @Test(description = "checkUser")
    public void checkUser() throws ZZKServiceException, ApiException {
        User user = new User();
        user.setUsername("alibaba_alitrip");
        user.setPassword("93d5a798a4a1693662b5a9da28b3b78f");
        userService.checkUser(user);
    }
    
   
    

}
  
