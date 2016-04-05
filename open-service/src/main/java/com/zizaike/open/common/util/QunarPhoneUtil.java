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
 * Function: 格式化手机号码. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2016年4月5日 上午10:14:14 <br/>  
 * @author   lin 
 * @version    
 * @since    JDK 1.7  
 * @see        
 */

public class QunarPhoneUtil {
    public String StandardPhoneUtil(String phone){
        String standardPhone = null;
        if(phone == null || phone == ""|| phone.equals(" , "))
            return null;
        String regNumber = "[^0-9]";
        String regEmail = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern patternEmail = Pattern.compile(regEmail);
        Matcher matcherEmail = patternEmail.matcher(phone);
        if(phone.length() < 20){
            Pattern patternNum = Pattern.compile(regNumber);
            Matcher matcherNum = patternNum.matcher(phone);
            standardPhone = matcherNum.replaceAll("");
        }
        else if(matcherEmail.matches()){
            return phone;
        }
        else{
            String reg = "微|或|;|；|/|、|,|，|\\s+";
            String[] newPhone = phone.split(reg);
            standardPhone = newPhone[0];
            Pattern patternNum = Pattern.compile(regNumber);
            Matcher matcherNum = patternNum.matcher(standardPhone);
            standardPhone = matcherNum.replaceAll("");
        }
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
        return standardPhone;
    }
}
