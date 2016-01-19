package com.zizaike.open.entity.taobao.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * ClassName: RequestData <br/>  
 * Function: 父类. <br/>  
 * date: 2016年1月14日 下午3:17:22 <br/>  
 *  
 * @author snow.zhang  
 * @version   
 * @since JDK 1.7
 */
@XStreamAlias("Result")
public class ResponseExceptionData extends ResponseData{
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
    public ResponseExceptionData() {
    }
    @Override
    public String toString() {
        return "ResponseData [message=" + message + ", resultCode=" + resultCode + "]";
    }
    
}