package com.testing.class14;

import com.alibaba.fastjson.JSONPath;
import com.testing.common.AutoLogger;
import com.testing.inter.HttpClientUtils;

/**
 * @Classname HeaderTest
 * @Description 类型说明
 * @Date 2021/3/28 19:30
 * @Created by 测试园
 */
public class HeaderTest {
    public static void main(String[] args) {
        //完成对象的实例化，包括client,cookiestore,headermap
        HttpClientUtils sy=new HttpClientUtils();

        //auth接口
        String authResult = sy.doPostUrl("http://testingedu.com.cn:8081/inter/HTTP/auth", "");
        AutoLogger.log.info("auth的返回结果是"+authResult);
        String tokenValue = JSONPath.read(authResult, "$.token").toString();
        AutoLogger.log.info("tokenValue的值是"+tokenValue);

        //添加头域
        sy.addHeader("{\"yuan\":\"testToken\",\"token\":\""+tokenValue+"\"}");

        //login接口
        String loginResult = sy.doPostUrl("http://testingedu.com.cn:8081/inter/HTTP/login", "username=yuan&password=123456");
        AutoLogger.log.info("login接口返回结果是"+loginResult);

        //logout请求
        String logoutResult = sy.doPostUrl("http://testingedu.com.cn:8081/inter/HTTP//logout", "");
        AutoLogger.log.info("logout的返回结果是"+logoutResult);
    }
}
