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
import com.zizaike.core.framework.springext.database.Slave;
import com.zizaike.entity.open.alibaba.Area;

/**  
 * ClassName:UserDao <br/>  
 * Function: 地址. <br/>  
 * Date:     2016年1月20日 下午2:34:09 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public interface AreaDao {
    /**
     * 
     * checkUser:地址查询. <br/>  
     *  
     * @author snow.zhang  
     * @param user
     * @return  
     * @since JDK 1.7
     */
    @Slave
    Area queryByTypeCode(String typeCode) throws ZZKServiceException;
}
  
