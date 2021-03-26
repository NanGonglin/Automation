package com.testing.class13;

import com.alibaba.fastjson.JSONPath;
import com.testing.class11.UnicodeDecode;
import com.testing.common.AutoLogger;
import com.testing.inter.HttpClientUtils;
import com.testing.inter.InterKw;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname ShopCartGet
 * @Description 类型说明
 * @Date 2021/3/25 20:18
 * @Created by 测试园
 */
public class ShopCartTest {
    public static void main(String[] args) throws Exception {
        HttpClientUtils sy=new HttpClientUtils();
        sy.createclient();
        //未登录购物车的时候请求购物车的接口
        String cartResult = sy.doGet("http://www.testingedu.com.cn:8000/index.php?m=Home&c=Cart&a=header_cart_list");
        AutoLogger.log.info("返回结果是"+cartResult);
        //正则表达式找出结果
        boolean b = InterKw.assertRegexEq("text\\('(.*?)'\\)", cartResult, "0");
        AutoLogger.log.info("断言的结果是"+b);


        //完成登录请求
        String loginResult = sy.doPost("http://www.testingedu.com.cn:8000/index.php?m=Home&c=User&a=do_login&t=0.8275829035046693", "username=1766342177%40qq.com&password=123456&verify_code=1", "urlencoded");
        AutoLogger.log.info("登录的结果是"+loginResult);

        boolean loginbl = InterKw.jsonValueCheck("$.msg", loginResult, "登录成功");

        //登录之后，再执行登录接口
        String after = sy.doGet("http://www.testingedu.com.cn:8000/index.php?m=Home&c=Cart&a=header_cart_list");
        AutoLogger.log.info("返回结果是"+after);
        //正则表达式找出结果
        boolean bb = InterKw.assertRegexEq("text\\('(.*?)'\\)", after, "2");
        AutoLogger.log.info("断言的结果是"+bb);

    }
}
