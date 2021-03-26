package com.testing.inter;

import com.alibaba.fastjson.JSONPath;
import com.testing.common.AutoLogger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname InterKw
 * @Description 类型说明
 * @Date 2021/3/26 18:42
 * @Created by 测试园
 */
public class InterKw {
    public static void main(String[] args) {

    }

    /**
     * 断言返回结果通过正则表达式获取结果是否符合预期
     * @param regex 断言时使用的正则表达式
     * @param origin 原本的输入结果
     * @param expect 预期结果
     * @return
     */
    public static boolean assertRegexEq(String regex,String origin,String expect){
        //正则表达式找出结果
        Pattern p=Pattern.compile(regex);
        Matcher m=p.matcher(origin);
        String result="";
        if(m.find()){
            result= m.group(1);
        }
        AutoLogger.log.info("提取后的结果"+result);
        if(expect.equals(result)){
            AutoLogger.log.info("pass");
            return true;
        }
        return false;
    }

    /**
     * 通过jsonpath断言json解析结果是否符合预期
     * @param jsonpath jsonpath表达式
     * @param origin    输入返回结果
     * @param expect    预期结果
     * @return
     */
    public static boolean jsonValueCheck(String jsonpath,String origin,String expect){
        String jsonValue = JSONPath.read(origin, jsonpath).toString();
        if(expect.equals(jsonValue)){
            AutoLogger.log.info("pass");
            return true;
        }
        return false;
    }
}
