package com.testing.class13;

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
        HttpClientUtils sy=new HttpClientUtils();
        String loginresult = sy.doPost("http://localhost:8080/zhihu/Login", "username=Roy&password=123456", "urlencoded");
        AutoLogger.log.info("登录的结果是"+loginresult);
        String userinforesult = sy.doPost("http://localhost:8080/zhihu/GetUserInfo", "", "");
        AutoLogger.log.info("默认带cookie查看个人信息的结果是"+userinforesult);

        sy.notusecookie();
        String userinforesult2 = sy.doPost("http://localhost:8080/zhihu/GetUserInfo", "", "");
        AutoLogger.log.info("不带cookie查看个人信息的结果是"+userinforesult2);

        sy.usecookie();
        String userinforesult3 = sy.doPost("http://localhost:8080/zhihu/GetUserInfo", "", "");
        AutoLogger.log.info("带cookie查看个人信息的结果是"+userinforesult3);
    }
}
