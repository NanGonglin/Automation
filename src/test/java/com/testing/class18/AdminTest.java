package com.testing.class18;

import com.testing.shop.ShopWebKeyWord;
import org.testng.annotations.*;

/**
 * @Classname AdminTest
 * @Description 类型说明
 * @Date 2021/4/1 10:42
 * @Created by 测试园
 */
public class AdminTest {

    public static ShopWebKeyWord web;

    @BeforeSuite
    public void openBrowser(){
        web=new ShopWebKeyWord();
        web.openChromeBrowser();
    }

    @Parameters({"baiduUrl"})
    @Test(priority = 0)
    public void visit(String baiduUrl){
        web.visitURL(baiduUrl);
    }
    @Test(priority = 1)
    public void visitAdmin(){
        web.visitURL("http://www.testingedu.com.cn:8000/Admin/Admin/login");
        web.imWait(3);
    }

    @Parameters({"password"})
    @Test(priority = 2)
    public void adminLogin(String password){
        web.input("//input[@name='username']", "admin");
        web.input("//input[@name='password']", password);
        web.input("//input[@name='vertify']", "111");
        web.click("//input[@name='submit']");
    }

    @AfterSuite
    public void closeBrowser(){
        web.closeBrowser();
    }
}
