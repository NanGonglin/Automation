package com.testing.inter;

import com.alibaba.fastjson.JSONPath;
import com.testing.common.AutoLogger;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname InterKw
 * @Description 类型说明
 * @Date 2021/3/26 18:42
 * @Created by 测试园
 */
public class InterKw {

//    public Map<String,String> paramMap;
    public Map<String,String> paramMap=new HashMap<>();

    //由于返回结果是所有方法都要使用的，所以设为成员变量
    public static String responseResult;

    //成员变量httpClientUtils
    public HttpClientUtils httpClientUtils=new HttpClientUtils();

    //构造方法，完成实例化
    public void InterKw(){
        paramMap=new HashMap<>();
        httpClientUtils=new HttpClientUtils();
    }

    //有可能牵涉到使用参数，所以先进行替换
    public void addHeader(String headerJson){
        //先替换变量参数
        headerJson=useParam(headerJson);
        httpClientUtils.addHeader(headerJson);
    }

    //将字符串中的变量名替换为它在parammap中存储的变量值
    public  String useParam(String origin){
        //遍历parammap里面的每个键，在origin字符串中找到{key}替换它
        for (String key : paramMap.keySet()) {
            //替换的键的格式是{key}，replaceall用的是正则，所以要转义
            //每一轮循环，都会替换这一轮的key键值对，替换完之后，把原字符串修改完再进行下一次的循环
            origin = origin.replaceAll("\\{" + key + "\\}", paramMap.get(key));
        }

        return origin;
    }

    //dopost请求，得到返回结果
    public void doposturl(String url,String param){
            //可能含有变量，先替换
            url=useParam(url);
            param=useParam(param);
            responseResult = httpClientUtils.doPostUrlEncoded(url, param);
            AutoLogger.log.info(responseResult);
    }

    //把返回结果，存储到参数map中
    public  void saveParam(String paramKey,String jsonPath){
        String paramValue = JSONPath.read(responseResult, jsonPath).toString();
        paramMap.put(paramKey,paramValue);
    }



    /**
     * 断言返回结果通过正则表达式获取结果是否符合预期
     * @param regex 断言时使用的正则表达式
     * @param expect 预期结果
     * @return
     */
    public static boolean assertRegexEq(String regex,String expect){
        //正则表达式找出结果
        Pattern p=Pattern.compile(regex);
        Matcher m=p.matcher(responseResult);
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
     * @param expect    预期结果
     * @return
     */
    public static boolean jsonValueCheck(String jsonpath,String expect){
        String jsonValue = JSONPath.read(responseResult, jsonpath).toString();
        if(expect.equals(jsonValue)){
            AutoLogger.log.info("pass");
            return true;
        }
        return false;
    }
}
