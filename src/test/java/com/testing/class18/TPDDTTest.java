package com.testing.class18;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * @Classname TPDDTTest
 * @Description 类型说明
 * @Date 2021/4/1 12:08
 * @Created by 测试园
 */
public class TPDDTTest {
    public WebDriver driver;

    @BeforeClass
    public void openBrowser(){
        driver=new ChromeDriver();
    }

    //一条访问电商进行登录
    @Parameters({"password"})
    @Test(priority = 1,dataProvider = "loginData")
    public void login(String loginName,String passWord){
        driver.get("http://www.testingedu.com.cn:8000");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//a[text()='登录']")).click();
        driver.findElement(By.xpath("//input[@id='username']")).sendKeys(loginName);
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys(passWord);
        driver.findElement(By.xpath("//input[@id='verify_code']")).sendKeys("1");
        driver.findElement(By.xpath("//a[@name='sbtbutton']")).click();
        driver.findElement(By.xpath("")).click();
    }
    @DataProvider
    public Object[][] loginData(){
        return new Object[][]{

                {"","123456"} ,
                {"17","123456"} ,
                {"1766342177@qq.com","123456t4654565465756768768888888888888888766666666666"},
                {"1766342177@qq.com","123456"} ,

        };
    }

    @AfterClass
    public void closeBrowser(){
        driver.quit();
    }





}
