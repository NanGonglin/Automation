package com.testing.class11;

import com.testing.common.AutoLogger;
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
        CloseableHttpClient client= HttpClients.createDefault();

        //1.登录商城
        //url
        HttpPost loginpost=new HttpPost("http://www.testingedu.com.cn:8000/index.php?m=Home&c=User&a=do_login&t=0.76009460417314");
        //请求头
        StringEntity loginparam=new StringEntity("username=1766342177%40qq.com&password=123456&verify_code=1");
        //设置请求头的格式
        loginparam.setContentType("application/x-www-form-urlencoded");
        loginparam.setContentEncoding("UTF-8");
        //请求头封装在请求包里面
        loginpost.setEntity(loginparam);
        //执行发包
        CloseableHttpResponse loginResponse = client.execute(loginpost);

        //将返回结果提取出来
        String loginresult = EntityUtils.toString(loginResponse.getEntity(), "UTF-8");
        //转换Unicode编码
        String result = UnicodeDecode.unicodeDecode(loginresult);
        //输出返回结果
        AutoLogger.log.info("商城用户登录的结果是"+result);

        //2.添加购物车

        HttpPost cartpost=new HttpPost("http://www.testingedu.com.cn:8000/index.php?m=Home&c=Cart&a=ajaxAddCart");
        //请求头
        StringEntity cartparam=new StringEntity("goods_id=294&goods_num=1");
        //设置请求头的格式
        cartparam.setContentType("application/x-www-form-urlencoded");
        cartparam.setContentEncoding("UTF-8");
        //请求头封装在请求包里面
        cartpost.setEntity(cartparam);
        //执行发包
        CloseableHttpResponse cartResponse = client.execute(cartpost);

        //将返回结果提取出来
        String cartresult = EntityUtils.toString(cartResponse.getEntity(), "UTF-8");
        //转换Unicode编码
        String addresult = UnicodeDecode.unicodeDecode(cartresult);
        //输出返回结果
        AutoLogger.log.info("添加购物车的结果是"+addresult);
    }
}
