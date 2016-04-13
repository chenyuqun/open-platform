package com.zizaike.open.common.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Project Name: code <br/>
 * Function:QunarListUtil. <br/>
 * date: 2016/4/12 14:11 <br/>
 *
 * @author alex
 * @since JDK 1.7
 */
public class QunarListUtil {
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

    public static void main(String[] args) {
        String a = "1|2|3|4|5|6|7";
        List<String> c = QunarListUtil.stringToList(a);
        System.out.println(QunarListUtil.stringToList(a));
        List<Integer> b = new ArrayList<Integer>();
        b.add(1);
        b.add(2);
        b.add(3);
        System.out.println(QunarListUtil.listToString(b));
        System.out.println(QunarListUtil.repeatToString(7, 3));
        System.out.println("~~~~");

    }
}
