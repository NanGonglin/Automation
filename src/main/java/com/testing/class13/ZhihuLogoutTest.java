package com.testing.class13;

import com.testing.common.AutoLogger;
import com.testing.inter.HttpClientUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @Classname ZhihuLogoutPost
 * @Description 类型说明
 * @Date 2021/3/25 20:22
 * @Created by 测试园
 */
public class ZhihuLogoutTest {
    public static void main(String[] args) throws Exception {
        CloseableHttpClient sy=HttpClients.createDefault();
        HttpPost loginpost=new HttpPost("http://localhost:8080/zhihu/Login");
        StringEntity logintparam=new StringEntity("username=Roy&password=123456");
        logintparam.setContentType("application/x-www-form-urlencoded");
        logintparam.setContentEncoding("UTF-8");
        loginpost.setEntity(logintparam);
        CloseableHttpResponse response = sy.execute(loginpost);
        String logintResult = EntityUtils.toString(response.getEntity(), "UTF-8");
        AutoLogger.log.info("获取登录的结果是"+logintResult);

        //带cookie的注销操作
//        HttpPost logoutpost=new HttpPost("http://localhost:8080/zhihu/Logout");
//        CloseableHttpResponse logoutresponse = sy.execute(logoutpost);
//        String logoutresult = EntityUtils.toString(logoutresponse.getEntity(), "UTF-8");
//        AutoLogger.log.info("默认客户端注销的结果是"+logoutresult);

        //不带cookie,新客户端的注销操作
        CloseableHttpClient client=HttpClients.custom().build();
        HttpPost logoutpost2=new HttpPost("http://localhost:8080/zhihu/Logout");
        CloseableHttpResponse logoutresponse2 = client.execute(logoutpost2);
        String logoutresult2 = EntityUtils.toString(logoutresponse2.getEntity(), "UTF-8");
        AutoLogger.log.info("默认客户端注销的结果是"+logoutresult2);

    }
}
