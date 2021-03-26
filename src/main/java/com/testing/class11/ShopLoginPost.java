package com.testing.class11;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


/**
 * @Classname ShopLoginPost
 * @Description 类型说明
 * @Date 2021/3/25 19:19
 * @Created by 测试园
 */
public class ShopLoginPost {
    public static void main(String[] args) throws Exception {
        CloseableHttpClient sy = HttpClients.createDefault();

        //四大要素:url、请求方法、请求头，请求体
        HttpPost loginpost = new HttpPost("http://www.testingedu.com.cn:8000/index.php?m=Home&c=User&a=do_login&t=0.8659232148397313");

        //设置请求体内容
        StringEntity loginparam=new StringEntity("username=1766342177%40qq.com&password=123456&verify_code=1","UTF-8");
        //设置设置好content-type请求头
        loginparam.setContentType("application/x-www-form-urlencoded");
        loginparam.setContentEncoding("UTF-8");
        //请求体封装到请求包里面
        loginpost.setEntity(loginparam);

        //执行发包
        CloseableHttpResponse loginResponse = sy.execute(loginpost);

        String result = EntityUtils.toString(loginResponse.getEntity(), "UTF-8");

        String loginResult = UnicodeDecode.unicodeDecode(result);
        System.out.println(loginResult);

    }
}
