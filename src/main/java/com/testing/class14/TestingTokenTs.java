package com.testing.class14;

import com.alibaba.fastjson.JSONPath;
import com.testing.class11.UnicodeDecode;
import com.testing.common.AutoLogger;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import sun.net.www.http.HttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;

/**
 * @Classname TestingTokenTs
 * @Description 类型说明
 * @Date 2021/3/27 13:30
 * @Created by 测试园
 */
public class TestingTokenTs {
    public static void main(String[] args) {
        CloseableHttpClient client= HttpClients.createDefault();

        //随机数生成
        Random random=new Random();
        int randomint=random.nextInt(100);
        String username="yuan"+randomint;
        AutoLogger.log.info("随机的用户名是"+username);

        //auth接口请求
        HttpPost auth=new HttpPost("http://testingedu.com.cn:8081/inter/HTTP/auth");
        String authResult = postResult(client, auth);
        AutoLogger.log.info("auth接口的返回是"+authResult);
        //获取auth接口返回的token
        String tokenValue = JSONPath.read(authResult, "$.token").toString();
        AutoLogger.log.info("token的值是"+tokenValue);

        //register请求
        HttpPost regpost=new HttpPost("http://testingedu.com.cn:8081/inter/HTTP//register");
        regpost.addHeader("token",tokenValue);
        String urlnickname= null;
        try {
            urlnickname = URLEncoder.encode(username+"在学习测试开发","UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            AutoLogger.log.info("nickname拼接失败");
        }
        //注册的用户名为随机编号
        StringEntity regEn = createEntity("username="+username+"&pwd=123456&nickname="+urlnickname+"&describe=%E6%88%91%E8%BF%98%E5%9C%A8%E5%AD%A6%E4%B9%A0%E6%B5%8B%E8%AF%95%E5%BC%80%E5%8F%91");
        regpost.setEntity(regEn);
        String regResult = postResult(client, regpost);
        AutoLogger.log.info("register接口的返回是"+regResult);

        //login请求，设置请求头
        HttpPost loginpost=new HttpPost("http://testingedu.com.cn:8081/inter/HTTP/login");
        //请求头添加token
        loginpost.addHeader("token", tokenValue);
        //设置请求体
        StringEntity loginEn=createEntity("username="+username+"&password=123456");
        loginpost.setEntity(loginEn);
        String loginResult = postResult(client, loginpost);
        AutoLogger.log.info("login接口的返回是"+loginResult);
        //获取userid的值
        String idValue = JSONPath.read(loginResult, "$.userid").toString();
        AutoLogger.log.info("userid的值是"+idValue);

        //getuserinfo接口
        HttpPost userpost=new HttpPost("http://testingedu.com.cn:8081/inter/HTTP/getUserInfo");
        //添加头域token
        userpost.addHeader("token",tokenValue);
        //设置请求头id的值
        StringEntity userEn = createEntity("id=" + idValue);
        userpost.setEntity(userEn);
        String userResult = postResult(client, userpost);
        AutoLogger.log.info("getUserInfo接口返回的值是"+userResult);

        //logout接口请求
        HttpPost logoutpost=new HttpPost("http://testingedu.com.cn:8081/inter/HTTP//logout");
        logoutpost.addHeader("token",tokenValue);
        String logoutResult = postResult(client, logoutpost);
        AutoLogger.log.info("logout接口的返回是"+logoutResult);

    }
    public static String postResult(CloseableHttpClient client,HttpPost post){
        try {
            CloseableHttpResponse execute = client.execute(post);
            String result = EntityUtils.toString(execute.getEntity(), "UTF-8");
            String s = UnicodeDecode.unicodeDecode(result);
            return s;
        } catch (IOException e) {
            return e.fillInStackTrace().toString();
        }
    }

    public static StringEntity createEntity(String param){
        StringEntity en=null;
        try {
            en=new StringEntity(param);
            en.setContentEncoding("UTF-8");
            en.setContentType("application/x-www-form-urlencoded");
            return en;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return en;
        }
    }
}
