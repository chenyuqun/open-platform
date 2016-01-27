/**  
 * Project Name:open-service  <br/>
 * File Name:RatesModifyRmqConsumer.java  <br/>
 * Package Name:com.zizaike.open  <br/>
 * Date:2016年1月27日上午10:08:38  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.mq;  

import org.springframework.stereotype.Service;

import com.zizaike.entity.open.alibaba.InventoryPriceMap;

/**  
 * ClassName:RatesModifyRmqConsumer <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2016年1月27日 上午10:08:38 <br/>  
 * @author   alex  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
@Service("ratesRmqConsumer")
public class RatesRmqConsumer {
    public void reveiveRatesModifyMessage(InventoryPriceMap object){
        System.err.println("reveiveRatesModifyMessage"+object);
    }
}
  
