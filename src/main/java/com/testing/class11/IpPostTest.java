package com.testing.class11;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;

/**
 * @Classname IpPostTest
 * @Description 类型说明
 * @Date 2021/3/26 10:47
 * @Created by 测试园
 */
public class IpPostTest {
    public static void main(String[] args) throws Exception {
        //创建httpclient对象，当做客户端使用
        CloseableHttpClient client= HttpClients.createDefault();
        //设置请求的url
        HttpPost ippost=new HttpPost("https://gwgp-kk6owjrbujz.i.bdcloudapi.com/ip2location/retrieve");
        //设置请求头
        StringEntity ipParam=new StringEntity("{\"ip\":\"33.44.2.222\"}");
        //设置请求头的格式
//        ipParam.setContentType("application/json");
//        ipParam.setContentEncoding("UTF-8");
        ippost.setHeader("contentType", "application/json;charset=UTF-8");
        //封装请求包
        ippost.setEntity(ipParam);
        //执行发包操作，获得返回体
        CloseableHttpResponse ipresponse = client.execute(ippost);
        System.out.println(ipresponse);
        //提取返回结果
        String ipresult = EntityUtils.toString(ipresponse.getEntity(), "UTF-8");
        System.out.println(ipresult);
    }
}
