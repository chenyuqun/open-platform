/**  
 * Project Name:open-service  <br/>
 * File Name:QunarPhoneUtil.java  <br/>
 * Package Name:com.zizaike.open.common.util  <br/>
 * Date:2016年4月5日上午10:14:14  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
/**  
 * Project Name:open-service <br/> 
 * File Name:QunarPhoneUtil.java  <br/>
 * Package Name:com.zizaike.open.common.util  <br/>
 * Date:2016年4月5日上午10:14:14  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved. 
 *  
 */  
  
package com.zizaike.open.common.util;  

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**  
 * ClassName:QunarPhoneUtil <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2016年4月5日 上午10:14:14 <br/>  
 * @author   lin 
 * @version    
 * @since    JDK 1.7  
 * @see        
 */

public class QunarPhoneUtil {
    public String StandardPhoneUtil(String phone){
        String standardPhone = "不处理";
        if(phone == null || phone == "")
            return standardPhone;
        /**
         * 长度超过20的不处理,超过20的数据源特例太多，一次处理很难
         */
        if(phone.length() < 20) {
            /**
             * 只保留数字
             */
            String regNumber = "[^0-9]";
            Pattern patternNum = Pattern.compile(regNumber);
            Matcher matcher = patternNum.matcher(phone);
            standardPhone = matcher.replaceAll("");
            /**
             * 判断是否是00886，886开头
             */
             StringBuffer stringBuffer = new StringBuffer(standardPhone);
             if(standardPhone.startsWith("00886")){
                 stringBuffer.insert(5,"-");}
             else if(standardPhone.startsWith("886")){
                 stringBuffer.insert(0,"00");
                 stringBuffer.insert(5,"-");
             }
             else{
                 stringBuffer.insert(0,"00886-");
             }
             standardPhone = stringBuffer.toString();
        }
        /**
         * 长度超过20
         */
        else{
            
        }
        return standardPhone;
    }
}
