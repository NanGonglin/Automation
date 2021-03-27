package com.testing.class13;

import com.testing.class11.UnicodeDecode;
import com.testing.common.AutoLogger;
import com.testing.inter.HttpClientUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @Classname ZhihuUserInfoPost
 * @Description 类型说明
 * @Date 2021/3/25 20:22
 * @Created by 测试园
 */
public class ZhihuUserInfoTest {
    public static void main(String[] args) throws IOException {
        //创建client对象，使用默认的，即带cookie
        CloseableHttpClient sy=HttpClients.createDefault();
        //设置请求的url
        HttpPost loginpost=new HttpPost("http://localhost:8080/zhihu/Login");
        //设置请求头及格式
        StringEntity loginparam=new StringEntity("username=Roy&password=123456");
        loginparam.setContentType("application/x-www-form-urlencoded");
        loginparam.setContentEncoding("UTF-8");
        //封装请求体
        loginpost.setEntity(loginparam);
        //执行发包操作并返回包
        CloseableHttpResponse response = sy.execute(loginpost);
        //提取返回结果
        String result = EntityUtils.toString(response.getEntity(), "UTF-8");
        String s = UnicodeDecode.unicodeDecode(result);
        AutoLogger.log.info("登录后的结果是"+s);

        //设置个人信息的url
        HttpPost userpost=new HttpPost("http://localhost:8080/zhihu/GetUserInfo");
        //执行发包操作
        CloseableHttpResponse userresponse = sy.execute(userpost);
        //提取个人信息结果
        String userresult = EntityUtils.toString(userresponse.getEntity(), "UTF-8");
        AutoLogger.log.info("获取的个人信息是"+UnicodeDecode.unicodeDecode(userresult));

        //没有cookie的新的客户端
        CloseableHttpClient client=HttpClients.custom().build();
        //设置个人信息的url
        HttpPost userpost2=new HttpPost("http://localhost:8080/zhihu/GetUserInfo");
        //执行发包操作
        CloseableHttpResponse userresponse2 = client.execute(userpost2);
        //提取个人信息结果
        String userresult2 = EntityUtils.toString(userresponse2.getEntity(), "UTF-8");
        AutoLogger.log.info("新的客户端获取的个人信息是"+UnicodeDecode.unicodeDecode(userresult2));
    }
}
