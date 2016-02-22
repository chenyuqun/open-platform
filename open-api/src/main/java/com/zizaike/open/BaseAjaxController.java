package com.zizaike.open;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zizaike.core.framework.exception.IErrorCode;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.open.alibaba.response.ResponseData;
import com.zizaike.entity.open.alibaba.response.ResponseExceptionData;
import com.zizaike.open.common.util.XstreamUtil;

/**
 * 
 * ClassName: BaseAjaxController <br/>  
 * Function: 捕获业务异常. <br/>  
 * Reason: 捕获业务异常. <br/>  
 * date: 2015年10月28日 下午12:00:00 <br/>  
 *  
 * @author snow.zhang  
 * @version   
 * @since JDK 1.7
 */
public abstract class BaseAjaxController {

    protected  Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler
    @ResponseBody
    public  void handleException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ResponseExceptionData resultBean = new ResponseExceptionData();
        if (ex instanceof ZZKServiceException) {
            ZZKServiceException ue = (ZZKServiceException) ex;
            IErrorCode iErrorCode = ((ZZKServiceException) ex).getErrorCode();
            resultBean.setResultCode(iErrorCode.getErrorCode());
            resultBean.setMessage(iErrorCode.getErrorMsg());
            if(ue.getDescription() != null){
                resultBean.setMessage(ue.getDescription());
            }else{
                resultBean.setMessage(iErrorCode.getErrorMsg());
            }
            log.error("ZZKServiceException errorCode={},errorMsg={}", iErrorCode.getErrorCode(),iErrorCode.getErrorMsg());
        } else {
            log.error("Exception:", ex);
            log.error("system error ", ex.getCause());
            resultBean.setResultCode("500");
            resultBean.setMessage(ex.getMessage());
        }

        try {
            response.getWriter().write(jsonpBuilder(resultBean));
        } catch (IOException e) {
            log.error("IOException ", e);
        }
    }

    public String jsonpBuilder( ResponseData resultBean) {
            return XstreamUtil.getResponseXml(resultBean);
    }

}
