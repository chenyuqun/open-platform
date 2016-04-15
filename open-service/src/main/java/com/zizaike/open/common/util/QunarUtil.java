package com.zizaike.open.common.util;

import com.zizaike.entity.open.qunar.response.HotelList;
import com.zizaike.open.dao.HomestayDockingDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Project Name: code <br/>
 * Function:QunarListUtil. <br/>
 * date: 2016/4/12 14:11 <br/>
 *
 * @author alex
 * @since JDK 1.7
 */
public class QunarUtil {
    public static List<String> stringToList(String args) {
        String[] result = args.split("\\|");
        List<String> resultList = new ArrayList<String>();
        for (String arg : result) {
            resultList.add(arg);
        }
        return resultList;
    }

    public static <T> String listToString(List<T> params) {
        if (params == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (T t : params) {
            if (flag) {
                result.append("|");
            } else {
                flag = true;
            }
            result.append(t);
        }
        return result.toString();
    }

    public static <T> String repeatToString(T param, Integer times) {
        if (times < 0) {
            return null;
        } else {
            StringBuilder result = new StringBuilder();
            boolean flag = false;
            while (times > 0) {
                if (flag) {
                    result.append("|" + param);
                } else {
                    flag = true;
                    result.append(param);
                }
                times--;
            }
            return result.toString();
        }
    }

    public static String StandardPhoneUtil(String phone,Integer dest_id) {
        String standardPhone = null;
        if (phone == null || phone == "" || phone.equals(" , "))
            return null;
        String regNumber = "[^0-9]";
        String regEmail = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern patternEmail = Pattern.compile(regEmail);
        Matcher matcherEmail = patternEmail.matcher(phone);
        if (phone.length() < 20) {
            Pattern patternNum = Pattern.compile(regNumber);
            Matcher matcherNum = patternNum.matcher(phone);
            standardPhone = matcherNum.replaceAll("");
        } else if (matcherEmail.matches()) {
            return phone;
        } else {
            //去除前7位中有空格的
            StringBuffer stringBufferphone = new StringBuffer(phone);
            for( int i = 0 ;i< 7 ;i++) {
                if(String.valueOf(stringBufferphone.charAt(i))==" "){
                    stringBufferphone.deleteCharAt(i);
                }
            }
            phone = stringBufferphone.toString();
            String reg = "（|．|／|～|客|微|或|;|；|/|、|,|，|\\s+";
            String[] newPhone = phone.split(reg);
            standardPhone = newPhone[0];
            Pattern patternNum = Pattern.compile(regNumber);
            Matcher matcherNum = patternNum.matcher(standardPhone);
            standardPhone = matcherNum.replaceAll("");
        }
        StringBuffer stringBuffer = new StringBuffer(standardPhone);
        /**
         * 10 means Taiwan
         */
        if(dest_id.equals(10)){
            if (standardPhone.startsWith("00886")) {
            stringBuffer.insert(5, "-");
            }else if (standardPhone.startsWith("886")) {
                stringBuffer.insert(0, "00");
                stringBuffer.insert(5, "-");
            }else if(standardPhone.startsWith("09")){
                stringBuffer.replace(0, 1, "");
                stringBuffer.insert(0, "00886-");
            }else{
                stringBuffer.insert(0, "00886-");
                }
            }
        standardPhone = stringBuffer.toString();
        return standardPhone;
    }

    public static HotelList CoverPhoneNumber(HomestayDockingDao homestayDockingDao,HotelList hotelList) {
        for (int i = 0; i < hotelList.getHotel().size(); i++) {
            hotelList.getHotel().get(i).setTel(QunarUtil.StandardPhoneUtil(hotelList.getHotel().get(i).getTel(), 
                    homestayDockingDao.queryQunarHotel(hotelList.getHotel().get(i).getId()).getDest_id()));

        }
        return hotelList;

    }

    public static int dateDiff(String checkIn,String checkOut){
        try{
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal=Calendar.getInstance();
            cal.setTime(sdf.parse(checkIn));
            long time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(checkOut));
            long time2 = cal.getTimeInMillis();
            long time=(time2-time1)/(1000*3600*24);
            return Integer.parseInt(String.valueOf(time));
        }catch (ParseException e){
            return 0;
        }

    }
    public static void main(String[] args) {
        String a = "1|2|3|4|5|6|7";
        List<String> c = QunarUtil.stringToList(a);
        System.out.println(QunarUtil.stringToList(a));
        List<Integer> b = new ArrayList<Integer>();
        b.add(1);
        b.add(2);
        b.add(3);
        System.out.println(QunarUtil.listToString(b));
        System.out.println(QunarUtil.repeatToString(7, 3));
        System.out.println("~~~~");
    }
}
