package com.testing.class11;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @Classname ShopCartGet
 * @Description 类型说明
 * @Date 2021/3/25 20:18
 * @Created by 测试园
 */
public class ShopCartGet {
    public static void main(String[] args) throws Exception {
        //创建httpclient对象，作为发送请求的客户端
        CloseableHttpClient sy= HttpClients.createDefault();

        //请求内容的四大要素：url、请求方法，请求头，请求体
        HttpGet cartGet=new HttpGet("http://www.testingedu.com.cn:8000/Home/User/historyLog.html");


        CloseableHttpResponse ipResponse = sy.execute(cartGet);

        HttpEntity cartentity = ipResponse.getEntity();

        String cartResult = EntityUtils.toString(cartentity, "UTF-8");

        String s = UnicodeDecode.unicodeDecode(cartResult);

        System.out.println(s);


    }
}
