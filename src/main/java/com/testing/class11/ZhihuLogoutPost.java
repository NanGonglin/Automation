package com.testing.class11;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;

/**
 * @Classname ZhihuLogoutPost
 * @Description 类型说明
 * @Date 2021/3/25 20:22
 * @Created by 测试园
 */
public class ZhihuLogoutPost {
    public static void main(String[] args) throws Exception {
        //创建httpclient对象，当做client使用
        CloseableHttpClient sy= HttpClients.createDefault();
        //设置请求的url
        HttpPost logoutpost=new HttpPost("http://localhost:8080/zhihu/Logout");
        //设置请求头
        StringEntity logoutparam=new StringEntity("");
        //设置请求头的格式
        logoutparam.setContentEncoding("UTF-8");
//        logoutparam.setContentType("");

        //封装请求体
        logoutpost.setEntity(logoutparam);
        //执行发包操作，获得返回结果
        CloseableHttpResponse logoutResponse = sy.execute(logoutpost);
        //提取返回结果
        String logoutresult = EntityUtils.toString(logoutResponse.getEntity(), "UTF-8");
        System.out.println(logoutresult);


    }
}
