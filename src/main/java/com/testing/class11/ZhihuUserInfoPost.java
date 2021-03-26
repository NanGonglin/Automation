package com.testing.class11;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @Classname ZhihuUserInfoPost
 * @Description 类型说明
 * @Date 2021/3/25 20:22
 * @Created by 测试园
 */
public class ZhihuUserInfoPost {
    public static void main(String[] args) throws IOException {
        //创建httpclient对象，当做客户机使用
        CloseableHttpClient sy= HttpClients.createDefault();
        //设置请求的url
        HttpPost userpost=new HttpPost("http://localhost:8080/zhihu/GetUserInfo");
        //设置请求头
        StringEntity userparam=new StringEntity("");
        //设置请求头的格式
        userparam.setContentType("");
        userparam.setContentEncoding("UTF-8");
        //封装请求体
        userpost.setEntity(userparam);

        //执行发包，获取返回结果
        CloseableHttpResponse userRespnse = sy.execute(userpost);
        //提取返回结果
        String userresult = EntityUtils.toString(userRespnse.getEntity(), "UTF-8");
        System.out.println(userresult);


    }
}
