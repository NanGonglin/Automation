package com.testing.class11;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;

/**
 * @Classname ZhihuLogin
 * @Description 类型说明
 * @Date 2021/3/25 20:21
 * @Created by 测试园
 */
public class ZhihuLoginPost {
    public static void main(String[] args) throws Exception {
        //创建client对象，当做客户端使用
        CloseableHttpClient sy= HttpClients.createDefault();

        //设置请求的url
        HttpPost loginpost=new HttpPost("http://localhost:8080/zhihu/Login");

        //设置请求头
        StringEntity loginparam=new StringEntity("username=Roy&password=123456");

        //设置请求头的格式
        loginparam.setContentEncoding("UTF-8");
        loginparam.setContentType("application/x-www-form-urlencoded");

        //把请求体封装
        loginpost.setEntity(loginparam);

        //执行发包操作，得到返回结果
        CloseableHttpResponse loginResponse = sy.execute(loginpost);

        //提取返回结果转为字符串格式
        String loginresult = EntityUtils.toString(loginResponse.getEntity(), "UTF-8");

        System.out.println(loginresult);

    }
}
