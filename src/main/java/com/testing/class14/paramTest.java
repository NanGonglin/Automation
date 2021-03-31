package com.testing.class14;

import com.alibaba.fastjson.JSONPath;
import com.testing.common.AutoLogger;
import com.testing.inter.HttpClientUtils;
import org.apache.commons.collections.map.HashedMap;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname paramTest
 * @Description 类型说明
 * @Date 2021/3/28 20:13
 * @Created by 测试园
 */
public class paramTest {
    public static  Map<String,String> paramMap;
    public static void main(String[] args) {
        paramMap= new HashMap<>();
        //完成对象的实例化，包括client,cookiestore,headeccrmap
        HttpClientUtils sy=new HttpClientUtils();

        //auth接口
        String authResult = sy.doPostUrl("http://testingedu.com.cn:8081/inter/HTTP/auth", "");
        AutoLogger.log.info("auth的返回结果是"+authResult);
        saveParam("tokenV", authResult, "$.token");

        String finish=useParam("{\"yuan\":\"testToken\",\"token\":\"{tokenV}\"}");
        AutoLogger.log.info("替换之后的结果是"+finish);
        //添加头域
        sy.addHeader(finish);

        //login接口
        String loginResult = sy.doPostUrl("http://testingedu.com.cn:8081/inter/HTTP/login", "username=yuan&password=123456");
        AutoLogger.log.info("login接口返回结果是"+loginResult);

        //logout请求
        String logoutResult = sy.doPostUrl("http://testingedu.com.cn:8081/inter/HTTP//logout", "");
        AutoLogger.log.info("logout的返回结果是"+logoutResult);
    }

    //把返回结果，存储到参数map中
    public static void saveParam(String paramKey,String result,String jsonPath){
        String paramValue = JSONPath.read(result, jsonPath).toString();
        paramMap.put(paramKey,paramValue);
    }

    //将字符串中的变量名替换为它在parammap中存储的变量值
    public static String useParam(String origin){
        //遍历parammap里面的每个键，在origin字符串中找到{key}替换它
        for (String key : paramMap.keySet()) {
            //替换的键的格式是{key}，replaceall用的是正则，所以要转义
            //每一轮循环，都会替换这一轮的key键值对，替换完之后，把原字符串修改完再进行下一次的循环
            origin=origin.replaceAll("\\{"+key+"\\}",paramMap.get(key));
        }
        return origin;
    }
}
