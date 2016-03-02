/**  
 * Project Name:open-service  <br/>
 * File Name:OrderService.java  <br/>
 * Package Name:com.zizaike.open.gateway  <br/>
 * Date:2016年2月19日上午11:36:00  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.gateway;  

import com.alibaba.fastjson.JSONObject;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.order.request.BookOrderRequest;
import com.zizaike.entity.order.request.CancelOrderRequest;
import com.zizaike.entity.order.request.ValidateOrderRequest;
import com.zizaike.entity.order.request.QueryStatusOrderRequest;

/**  
 * ClassName:OrderService <br/>  
 * Function:. <br/>  
 * Date:     2016年2月19日 上午11:36:00 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public interface OrderService {
    /**
     * 
     * validateRQ:验证价格库存. <br/>  
     *  
     * @author snow.zhang  
     * @param validateOrderRequest
     * @return
     * @throws ZZKServiceException  
     * @since JDK 1.7
     */
    JSONObject validateRQ(ValidateOrderRequest validateOrderRequest)throws ZZKServiceException;
    /**
     * 
     * bookRQ:订单预定. <br/>  
     * @author alex 
     * @param bookOrderRequest
     * @return
     * @throws ZZKServiceException  
     * @since JDK 1.7
     */
    JSONObject bookRQ(BookOrderRequest bookOrderRequest)throws ZZKServiceException;
     /**
     * 
     * QueryStatusOrder:查询订单. <br/>  
     *  
     * @author snow.zhang  
     * @param queryStatusOrderRequest
     * @return
     * @throws ZZKServiceException  
     * @since JDK 1.7
     */
    JSONObject aueryStatusOrder(QueryStatusOrderRequest queryStatusOrderRequest)throws ZZKServiceException;
    /**
     * 
     * cancelRQ:订单取消. <br/>  
     * @author alex 
     * @param cancelOrderRequest
     * @return
     * @throws ZZKServiceException  
     * @since JDK 1.7
     */
    JSONObject cancelRQ(CancelOrderRequest cancelOrderRequest)throws ZZKServiceException;
    
}