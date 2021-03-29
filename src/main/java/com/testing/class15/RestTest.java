package com.testing.class15;

import com.testing.inter.InterKw;

/**
 * @Classname RestTest
 * @Description 类型说明
 * @Date 2021/3/29 18:43
 * @Created by 测试园
 */
public class RestTest {

    public static void main(String[] args) {
        InterKw sy=new InterKw();
        //auth接口
        sy.doposturl("http://192.168.217.133:8080/Inter/REST/auth", "");
        sy.saveParam("tokenV", "$.token");
        sy.saveRandomParam("userR", "yuan");
        sy.saveConstant("pwd", "123456");
        sy.addHeader("{\"token\":\"{tokenV}\"}");

        //注册
        sy.doposturl("http://192.168.217.133:8080/Inter/REST/user/register", "{\"username\":\"{userR}\",\"pwd\":\"{pwd}\",\"nickname\":\"test register\",\"describe\":\"study\"}");
        //登录接口
        sy.doposturl("http://192.168.217.133:8080/Inter/REST/login/{userR}/{pwd}","");

        //getUserInfo接口
        sy.saveParam("idV","$.userid");
        sy.doposturl("http://192.168.217.133:8080/Inter/REST/login/{idV}", "");

        //注销接口
        sy.doposturl("http://192.168.217.133:8080/Inter/REST/login", "");
    }
}
