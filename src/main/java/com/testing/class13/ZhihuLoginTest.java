package com.testing.class13;

import com.testing.common.AutoLogger;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @Classname ZhihuLogin
 * @Description 类型说明
 * @Date 2021/3/25 20:21
 * @Created by 测试园
 */
public class ZhihuLoginTest {
    public static void main(String[] args) throws IOException {
        //创建一个共用的cookiestore，类似于存储cookie的钱包
        BasicCookieStore purse=new BasicCookieStore();
        //第一次请求，sy带上purse请求，把cookie装到purse里面
        CloseableHttpClient sy= HttpClients.custom().setDefaultCookieStore(purse).build();
        HttpPost login=new HttpPost("http://localhost:8080/zhihu/Login");
        StringEntity loginparam=new StringEntity("username=Roy&password=123456");
        loginparam.setContentEncoding("UTF-8");
        loginparam.setContentType("application/x-www-form-urlencoded");
        login.setEntity(loginparam);
        CloseableHttpResponse loginResponse = sy.execute(login);
        String loginresult = EntityUtils.toString(loginResponse.getEntity(), "UTF-8");
        AutoLogger.log.info("第一次登录的结果是"+loginresult);
        //遍历cookie池purse中的所有cookie，输出他们的键值对，作用域
        for (Cookie cookie : purse.getCookies()) {
            System.out.println(cookie.getName()+cookie.getValue()+cookie.getDomain());
        }

        //进行二次传递
        //将cookiestore传递给第二个client来使用
        CloseableHttpClient ht=HttpClients.custom().setDefaultCookieStore(purse).build();
        HttpPost login1=new HttpPost("http://localhost:8080/zhihu/Login");
        StringEntity loginparam1=new StringEntity("username=Roy&password=123456");
        loginparam1.setContentEncoding("UTF-8");
        loginparam1.setContentType("application/x-www-form-urlencoded");
        login1.setEntity(loginparam1);
        CloseableHttpResponse loginResponse1 = ht.execute(login1);
        String loginresult1 = EntityUtils.toString(loginResponse1.getEntity(), "UTF-8");
        AutoLogger.log.info("第二次登录的结果是"+loginresult1);

        //第三次发送请求
        //不使用已经保存的cookies
        CloseableHttpClient kk=HttpClients.custom().build();
        HttpPost login2=new HttpPost("http://localhost:8080/zhihu/Login");
        StringEntity loginparam2=new StringEntity("username=Roy&password=123456");
        loginparam2.setContentEncoding("UTF-8");
        loginparam2.setContentType("application/x-www-form-urlencoded");
        login2.setEntity(loginparam2);
        CloseableHttpResponse loginResponse2 = kk.execute(login2);
        String loginresult2 = EntityUtils.toString(loginResponse2.getEntity(), "UTF-8");
        AutoLogger.log.info("第三次登录的结果是"+loginresult2);
    }

    /**
     * 手动添加cookie的操作方式，第二次登录失败
     * @param args
     * @throws Exception
     */
    public static void main3(String[] args) throws Exception {

        //两次请求使用不同的client,第一次请求的setcookie作为第二次的cookie，添加到第二次在请求包中
        CloseableHttpClient client= HttpClients.createDefault();
        HttpPost login=new HttpPost("http://localhost:8080/zhihu/Login");
        StringEntity loginparam=new StringEntity("username=Roy&password=123456");
        loginparam.setContentEncoding("UTF-8");
        loginparam.setContentType("application/x-www-form-urlencoded");
        login.setEntity(loginparam);
        //获取返回包
        CloseableHttpResponse loginResponse = client.execute(login);
        //获取返回头
        Header[] headers = loginResponse.getHeaders("Set-Cookie");
        String value="";
        for (Header header : headers) {
            System.out.println("头域的键是"+header.getName()+",值是"+header.getValue());
            value = header.getValue();
            //获取分号之前的内容
            value= value.substring(0, value.indexOf(";"));
            System.out.println("取出来的结果是"+value);
        }

        String loginresult = EntityUtils.toString(loginResponse.getEntity(), "UTF-8");
        AutoLogger.log.info("第一次登录的结果是"+loginresult);
        //Cookie: JSESSIONID=C0F34A00BC6CC709D24E348046D41F35

        CloseableHttpClient client1= HttpClients.createDefault();
        HttpPost login1=new HttpPost("http://localhost:8080/zhihu/Login");
        StringEntity loginparam1=new StringEntity("username=Roy&password=123456");
        loginparam1.setContentEncoding("UTF-8");
        loginparam1.setContentType("application/x-www-form-urlencoded");
        login1.setEntity(loginparam1);
        //往请求包中添加头域
        login1.addHeader("Cookie", value);
        CloseableHttpResponse loginResponse1 = client1.execute(login1);
        String loginresult1 = EntityUtils.toString(loginResponse1.getEntity(), "UTF-8");
        AutoLogger.log.info("第二次登录的结果是"+loginresult1);
    }


    /**
     * 两次使用同一个client执行发包操作，第二次登录失败
     * @param args
     * @throws Exception
     */
    public static void main2(String[] args) throws Exception {
        //创建client对象，当做客户端使用，两次请求用相同的client执行
        CloseableHttpClient client= HttpClients.createDefault();
        HttpPost login=new HttpPost("http://localhost:8080/zhihu/Login");
        StringEntity loginparam=new StringEntity("username=Roy&password=123456");
        loginparam.setContentEncoding("UTF-8");
        loginparam.setContentType("application/x-www-form-urlencoded");
        login.setEntity(loginparam);
        CloseableHttpResponse loginResponse = client.execute(login);
        String loginresult = EntityUtils.toString(loginResponse.getEntity(), "UTF-8");
        AutoLogger.log.info("第一次登录的结果是"+loginresult);

        //第二次登录，如果用相同的client执行，那么client会保存之前请求的cookie,并且在之后的请求中带上
        HttpPost login1=new HttpPost("http://localhost:8080/zhihu/Login");
        StringEntity loginparam1=new StringEntity("username=Roy&password=123456");
        loginparam1.setContentEncoding("UTF-8");
        loginparam1.setContentType("application/x-www-form-urlencoded");
        login1.setEntity(loginparam1);
        CloseableHttpResponse loginResponse1 = client.execute(login1);
        String loginresult1 = EntityUtils.toString(loginResponse1.getEntity(), "UTF-8");
        AutoLogger.log.info("第二次登录的结果是"+loginresult1);
    }

    /**
     * 两次使用不同的client发包，第二次登录也成功了
     * @param args
     * @throws Exception
     */
    public static void main1(String[] args) throws Exception {
        //创建client对象，当做客户端使用，两次请求用相同的client执行
        CloseableHttpClient client= HttpClients.createDefault();
        HttpPost login=new HttpPost("http://localhost:8080/zhihu/Login");
        StringEntity loginparam=new StringEntity("username=Roy&password=123456");
        loginparam.setContentEncoding("UTF-8");
        loginparam.setContentType("application/x-www-form-urlencoded");
        login.setEntity(loginparam);
        CloseableHttpResponse loginResponse = client.execute(login);
        String loginresult = EntityUtils.toString(loginResponse.getEntity(), "UTF-8");
        AutoLogger.log.info("第一次登录的结果是"+loginresult);

        //第二次登录
        CloseableHttpClient sy= HttpClients.createDefault();
        HttpPost login1=new HttpPost("http://localhost:8080/zhihu/Login");
        StringEntity loginparam1=new StringEntity("username=Roy&password=123456");
        loginparam1.setContentEncoding("UTF-8");
        loginparam1.setContentType("application/x-www-form-urlencoded");
        login1.setEntity(loginparam1);
        CloseableHttpResponse loginResponse1 = sy.execute(login1);
        String loginresult1 = EntityUtils.toString(loginResponse1.getEntity(), "UTF-8");
        AutoLogger.log.info("第二次登录的结果是"+loginresult1);
    }
}
