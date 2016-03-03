/**  
 * Project Name:open-api  <br/>
 * File Name:UserServiceImpl.java  <br/>
 * Package Name:com.zizaike.open.service.impl  <br/>
 * Date:2016年1月20日下午2:14:31  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.service.impl;  

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.core.framework.exception.open.UserNameOrPasswordException;
import com.zizaike.core.framework.exception.open.UserNotFoundException;
import com.zizaike.entity.open.User;
import com.zizaike.is.open.UserService;
import com.zizaike.open.dao.UserDao;

/**  
 * ClassName:UserServiceImpl <br/>  
 * Function: 用户校验. <br/>  
 * Date:     2016年1月20日 下午2:14:31 <br/>  
 * @author   snow.zhang  
 * @version 
 * @since    JDK 1.7  
 * @see        
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public void checkUser(User user)throws ZZKServiceException {
          if(user==null){
              throw new IllegalParamterException("user is null");
          }
          if(StringUtils.isEmpty(user.getUsername())){
              throw new IllegalParamterException("username is null");
          }
          User query = userDao.queryByUsername(user.getUsername());
          if(query == null){
              throw new UserNotFoundException();
          }
          if(!query.getPassword().equals(user.getPassword())){
              throw new UserNameOrPasswordException();
          }
          
    }

}
  
