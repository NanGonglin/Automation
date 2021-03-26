package com.testing.class13;

import com.testing.common.AutoLogger;
import com.testing.inter.HttpClientUtils;

/**
 * @Classname TestCookie
 * @Description 类型说明
 * @Date 2021/3/26 23:17
 * @Created by 测试园
 */
public class TestCookie {

    public static void main(String[] args) {

//        HttpClientUtils sy=new HttpClientUtils();
//        String result1 = sy.doPost("http://localhost:8080/zhihu/Login", "username=Roy&password=123456", "urlencoded");
//        AutoLogger.log.info("第1次登录的结果"+result1);
//        String result2 = sy.doPost("http://localhost:8080/zhihu/Login", "username=Roy&password=123456", "urlencoded");
//        AutoLogger.log.info("第2次登录的结果"+result2);
//        sy.notusecookie();
//        String result3 = sy.doPost("http://localhost:8080/zhihu/Login", "username=Roy&password=123456", "urlencoded");
//        AutoLogger.log.info("第3次登录的结果"+result3);

        HttpClientUtils kk=new HttpClientUtils();
        String loginResult = kk.doPost("http://www.testingedu.com.cn:8000/index.php?m=Home&c=User&a=do_login&t=0.8275829035046693", "username=1766342177%40qq.com&password=123456&verify_code=1", "urlencoded");
        AutoLogger.log.info("默认带cookie登录的结果是"+loginResult);
        String cartresult=kk.doGet("http://www.testingedu.com.cn:8000/index.php?m=Home&c=Cart&a=header_cart_list");
        AutoLogger.log.info("使用cookie查看购物车的结果是"+cartresult);

        String cartresult2=kk.doGet("http://www.testingedu.com.cn:8000/index.php?m=Home&c=Cart&a=header_cart_list","not");
        AutoLogger.log.info("不使用cookie查看购物车的结果是"+cartresult2);

        String cartresult3=kk.doGet("http://www.testingedu.com.cn:8000/index.php?m=Home&c=Cart&a=header_cart_list","use");
        AutoLogger.log.info("使用cookie查看购物车的结果是"+cartresult3);





    }
}
