package com.testing.class18;

import com.testing.class5.AdminPageTest;
import com.testing.shop.ShopWebKeyWord;
import org.omg.CORBA.TIMEOUT;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * @Classname TpShopTest
 * @Description 类型说明
 * @Date 2021/3/31 21:26
 * @Created by 测试园
 */
public class TpShopTest {

    public WebDriver driver;

    @BeforeClass
    public void openBrowser(){
        driver= AdminTest.web.getDriver();
    }

    //两条用例，一条访问百度
    @Parameters({"baiduUrl"})
    @Test(priority = 0)
    public void visit(String baiduUrl){
        driver.get(baiduUrl);
    }

    //一条访问电商进行登录
    @Parameters({"password"})
    @Test(priority = 1)
    public void login(String password){
        driver.get("http://www.testingedu.com.cn:8000");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//a[text()='登录']")).click();
        driver.findElement(By.xpath("//input[@id='username']")).sendKeys("1766342177@qq.com");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys(password);
        driver.findElement(By.xpath("//input[@id='verify_code']")).sendKeys("1");
        driver.findElement(By.xpath("//a[@name='sbtbutton']")).click();
        //断言某个条件是否成立
//        Assert.assertTrue(driver.getPageSource().contains("退出"));
    }

    @AfterClass
    public void closeBrowser(){
        driver.quit();
    }


}
