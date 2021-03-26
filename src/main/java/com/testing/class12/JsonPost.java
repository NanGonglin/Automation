package com.testing.class12;

import com.alibaba.fastjson.JSONPath;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @Classname JsonPost
 * @Description 类型说明
 * @Date 2021/3/26 12:49
 * @Created by 测试园
 */
public class JsonPost {
    public static void main(String[] args) throws IOException {
        CloseableHttpClient client= HttpClients.createDefault();

        HttpPost jsonpost=new HttpPost("https://b.zhulogic.com/designer_api/account/login_quick_pc");
        StringEntity jsonparam=new StringEntity("{\"phone\":\"15829254932\",\"code\":\"7756\",\"messageType\":3,\"registration_type\":1,\"channel\":\"zhulogic\",\"unionid\":\"\"}");
//        jsonpost.setHeader("content-Type","application/json;charset=UTF-8");
        //等价于下面两行
        jsonparam.setContentType("application/json");
        jsonparam.setContentEncoding("UTF-8");
        jsonpost.setEntity(jsonparam);
        CloseableHttpResponse response = client.execute(jsonpost);
        String result = EntityUtils.toString(response.getEntity(), "UTF-8");
        System.out.println("返回结果是"+result);
        //用jsonpath获取json内容
        String message = JSONPath.read(result, "$.message").toString();

        if(message.equals("验证码错误")){
            System.out.println("测试成功");
        }  else{
            System.out.println("测试失败");
        }
    }
}
