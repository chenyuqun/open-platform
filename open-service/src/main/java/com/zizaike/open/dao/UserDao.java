/**  
 * Project Name:open-service  <br/>
 * File Name:UserDao.java  <br/>
 * Package Name:com.zizaike.open.dao  <br/>
 * Date:2016年1月20日下午2:34:09  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.dao;  

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.open.User;

/**  
 * ClassName:UserDao <br/>  
 * Function: 用户. <br/>  
 * Date:     2016年1月20日 下午2:34:09 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public interface UserDao {
    /**
     * 
     * checkUser:校验用户信息. <br/>  
     *  
     * @author snow.zhang  
     * @param user
     * @return  
     * @since JDK 1.7
     */
    User queryByUsername(String username) throws ZZKServiceException;
}
  
