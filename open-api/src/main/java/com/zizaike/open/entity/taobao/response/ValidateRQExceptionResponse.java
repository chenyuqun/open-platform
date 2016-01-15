/**  
 * Project Name:open-api  <br/>
 * File Name:ValidateRQResponse.java  <br/>
 * Package Name:com.zizaike.open.entity.taobao.response  <br/>
 * Date:2016年1月14日下午3:40:43  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.entity.taobao.response;  

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**  
 * ClassName:ValidateRQResponse <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2016年1月14日 下午3:40:43 <br/>  
 * @author   zeuskingzb  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
@XStreamAlias("Result")
public class ValidateRQExceptionResponse extends ResponseData{
    @XStreamAlias("Message")
    private String message;
    @XStreamAlias("ResultCode")
    private String resultCode;
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
    

    public ValidateRQExceptionResponse(String message, String resultCode) {
        super();
        this.message = message;
        this.resultCode = resultCode;
    }

    @Override
    public String toString() {
        return "ValidateRQExceptionResponse [message=" + message + ", resultCode=" + resultCode + "]";
    }
    
}
  
