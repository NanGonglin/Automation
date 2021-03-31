package com.testing.common;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Classname AutoTools
 * @Description 通用工具
 * @Date 2021/3/30 9:44
 * @Created by 测试园
 */
public class AutoTools {
    /**
     * 用于生成指定格式的日期字符串
     * @param format 需要格式化的格式
     * @return 返回格式化后的值
     */
    public static String createTime(String format){
        Date now=new Date();
        SimpleDateFormat sdk=new SimpleDateFormat(format);
        String result = sdk.format(now);
        return result;
    }

    /**
     * 用于生成时间戳拼接的字符串
     * @param paramKey
     * @param originString
     */
    public void saveRandomParam(String paramKey,String originString){
        //基于时间内容，拼接一个时间戳
        Date now=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("HHmmss");
        String timeStamp = sdf.format(now);
        String paramValue=originString+timeStamp;
        //将参数存储到paramMap中
//        paramMap.put(paramKey,paramValue);
    }

    //利用反射，调用对应的方法
    public static void invokeI(Object keyword, List<String> rowContent, int col){
        //借助exception来进行查找判断，找到正确的参数个数

        //没有参数
        try {
            Method target = keyword.getClass().getDeclaredMethod(rowContent.get(col));
            target.invoke(keyword);
            //如果进入try并且成执行了，退出执行方法，不再去查找更多参数的可能
            return;
        } catch (Exception e) {
        }
        //一个参数
        try {
            Method target = keyword.getClass().getDeclaredMethod(rowContent.get(col), String.class);
            target.invoke(keyword, rowContent.get(col+1));
            return;
        } catch (Exception e) {
        }
        //两个参数
        try {
            Method target = keyword.getClass().getDeclaredMethod(rowContent.get(col), String.class,String.class);
            target.invoke(keyword, rowContent.get(col+1),rowContent.get(col+2));
            return;
        } catch (Exception e) {
        }
        //三个参数
        try {
            Method target = keyword.getClass().getDeclaredMethod(rowContent.get(col), String.class,String.class,String.class);
            target.invoke(keyword, rowContent.get(col+1),rowContent.get(col+2),rowContent.get(col+3));
            return;
        } catch (Exception e) {
        }
    }
}
