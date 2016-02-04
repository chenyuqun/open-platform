/**  
 * Project Name:open-service  <br/>
 * File Name:CtripServiceImpl.java  <br/>
 * Package Name:com.zizaike.open.service.impl  <br/>
 * Date:2016年2月4日下午3:11:16  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.service.impl;  

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.open.ctrip.request.DomesticCheckRoomAvailRequest;
import com.zizaike.entity.open.ctrip.response.DomesticCheckRoomAvailResponse;
import com.zizaike.is.open.CtripService;

/**  
 * ClassName:CtripServiceImpl <br/>  
 * Function: 携程服务. <br/>  
 * Date:     2016年2月4日 下午3:11:16 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public class CtripServiceImpl implements CtripService {

    @Override
    public DomesticCheckRoomAvailResponse domesticCheckRoomAvail(
            DomesticCheckRoomAvailRequest domesticCheckRoomAvailRequest) throws ZZKServiceException {

        return null;
    }

}
  
