package com.testing.class14;

import com.testing.inter.InterKw;

/**
 * @Classname InterTest
 * @Description 类型说明
 * @Date 2021/3/28 22:57
 * @Created by 测试园
 */
public class InterTest {
    public static void main(String[] args) {
        InterKw sy=new InterKw();

//        sy.doPostUrl("http://testingedu.com.cn:8081/inter/HTTP/auth","");
//        sy.saveParam("tokenV","$.token");
//        sy.addHeader("{\"yuan\":\"testToken\",\"token\":\"{tokenV}\"}");
//        sy.doPostUrl("http://testingedu.com.cn:8081/inter/HTTP/login", "username=yuan&password=123456");
//        sy.doPostUrl("http://testingedu.com.cn:8081/inter/HTTP//logout", "");
        sy.clearHeader();
        sy.doPostUrl("http://www.testingedu.com.cn:8081/inter/HTTP/auth", "");
        sy.saveParam("token", "$.token");
        sy.addHeader("{\"token\":\"{token}\"}");
        sy.doPostUrl("http://www.testingedu.com.cn:8081/inter/HTTP/login", "password=123456");




    }
}
