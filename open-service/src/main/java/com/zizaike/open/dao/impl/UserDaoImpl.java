/**  
 * Project Name:open-service  <br/>
 * File Name:UserDao.java  <br/>
 * Package Name:com.zizaike.open.dao  <br/>
 * Date:2016年1月20日下午2:34:09  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.dao.impl;  

import org.springframework.stereotype.Repository;

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.core.framework.mybatis.impl.GenericMyIbatisDao;
import com.zizaike.entity.open.User;
import com.zizaike.entity.recommend.DestConfig;
import com.zizaike.open.dao.UserDao;

/**  
 * ClassName:UserDao <br/>  
 * Function: 用户. <br/>  
 * Date:     2016年1月20日 下午2:34:09 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
@Repository
public class UserDaoImpl extends GenericMyIbatisDao<DestConfig, Integer> implements UserDao{
    private static final String NAMESPACE = "com.zizaike.open.dao.UserMapper." ;
    @Override
    public User queryByUsername(String username) throws ZZKServiceException {
        User query = this.getSqlSession().selectOne(NAMESPACE+"queryByUsername", username);
        return query;
    }
}
  
