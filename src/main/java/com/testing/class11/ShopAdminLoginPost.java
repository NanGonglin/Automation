package com.testing.class11;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;

/**
 * @Classname ShopAdminLoginPost
 * @Description 类型说明
 * @Date 2021/3/25 20:19
 * @Created by 测试园
 */
public class ShopAdminLoginPost {
    public static void main(String[] args) throws Exception {
        CloseableHttpClient sy= HttpClients.createDefault();

        //url
        HttpPost loginpost=new HttpPost("http://www.testingedu.com.cn:8000/Admin/Admin/login");
        //请求头
        StringEntity loginparam=new StringEntity("username=admin&password=123456&vertify=1");
        //设置请求头的格式
        loginparam.setContentType("application/x-www-form-urlencoded");
        loginparam.setContentEncoding("UTF-8");
        //请求头封装在请求包里面
        loginpost.setEntity(loginparam);
        //执行发包
        CloseableHttpResponse loginResponse = sy.execute(loginpost);

        //将返回结果提取出来
        String loginresult = EntityUtils.toString(loginResponse.getEntity(), "UTF-8");
        //转换Unicode编码
        String result = UnicodeDecode.unicodeDecode(loginresult);
        //输出返回结果
        System.out.println(result);
    }
}
