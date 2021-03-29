package com.testing.class15;

import com.testing.common.Encrypt;
import com.testing.inter.InterKw;
import com.testing.shop.ShopWebKeyWord;

/**
 * @Classname EnPwdTest
 * @Description 类型说明
 * @Date 2021/3/29 12:43
 * @Created by 测试园
 */
public class EnPwdTest {
    public static void main(String[] args) {
        InterKw sy=new InterKw();
        //调用auth接口获取token，后面的操作都需要token
        sy.doposturl("http://192.168.217.133:8080/Inter/HTTP/auth","");
        sy.saveParam("tokenV","$.token");
        sy.addHeader("{\"yuan\":\"testToken\",\"token\":\"{tokenV}\"}");
        sy.saveRandomParam("userR", "yuan");
        sy.saveConstant("pwd", "123456");
        //注册
        sy.doposturl("http://192.168.217.133:8080/Inter/HTTP//register", "username={userR}&pwd={pwd}&nickname=student&describe=Test student");

        sy.saveEncPwd("encPwd", "123456");
        System.out.println(sy.paramMap);
        sy.doposturl("http://192.168.217.133:8080/Inter/HTTP/login", "username={userR}&password={encPwd}");

        sy.saveParam("idV", "$.userid");
        sy.doposturl("http://192.168.217.133:8080/Inter/HTTP/getUserInfo","id={idV}");
        sy.doposturl("http://192.168.217.133:8080/Inter/HTTP//logout", "");
    }
}
