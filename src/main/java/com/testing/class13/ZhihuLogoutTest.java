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
        HttpClientUtils sy=new HttpClientUtils();
        String loginresult = sy.doPost("http://localhost:8080/zhihu/Login", "username=Roy&password=123456", "urlencoded");
        String logoutresult = sy.doPost("http://localhost:8080/zhihu/Logout", "", "");
        AutoLogger.log.info("默认注销的结果是"+logoutresult);

        sy.notusecookie();
        String loginresult2 = sy.doPost("http://localhost:8080/zhihu/Login", "username=Roy&password=123456", "urlencoded");
        String logoutresult2 = sy.doPost("http://localhost:8080/zhihu/Logout", "", "");
        AutoLogger.log.info("不带cookie注销的结果是"+logoutresult2);

        sy.usecookie();
        String loginresult3 = sy.doPost("http://localhost:8080/zhihu/Login", "username=Roy&password=123456", "urlencoded");
        String logoutresult3 = sy.doPost("http://localhost:8080/zhihu/Logout", "", "");
        AutoLogger.log.info("带cookie注销的结果是"+logoutresult3);
    }
}
