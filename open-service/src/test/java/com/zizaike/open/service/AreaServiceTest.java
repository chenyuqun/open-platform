/**  
 * Project Name:open-service  <br/>
 * File Name:Alibaba.java  <br/>
 * Package Name:com.zizaike.open.service  <br/>
 * Date:2016年1月11日下午4:51:02  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.service;  

import com.zizaike.entity.open.alibaba.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.taobao.api.ApiException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.is.open.AreaService;
import com.zizaike.open.bastest.BaseTest;

/**  
 * ClassName:Alibaba <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Date:     2016年1月11日 下午4:51:02 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public class AreaServiceTest extends BaseTest{
    @Autowired
    private AreaService areaService;
    @Test(description = "checkUser")
    public void queryByTypeCode() throws ZZKServiceException, ApiException {
       Area area =  areaService.getAreaCodeTypeCode("1000111");
       Assert.assertNotNull(area);
    }
    
   
    

}
  
