package com.testing.class1;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.testing.class1.ChromeDemo.searchBaidu;

/**
 * @Classname BrowserDemo
 * @Description 类型说明
 * @Date 2021/3/10 19:26
 * @Created by 小白sy
 */
public class BrowserDemo {
    public static void main(String[] args) throws InterruptedException {
        //chrome浏览器
        //指定webdriver的路径，用相对路径，方便项目移植给别人用
        System.setProperty("webdriver.chrome.driver", "E:\\测试开发课堂笔记\\第3部分 java自动化\\Java自动化第1课-初识selenium\\driver\\chromedriver.exe");
        //实例化webdriver，向上转型，chromedriver是子类
        WebDriver chromeDriver = new ChromeDriver();
        searchBaidu(chromeDriver);
        chromeDriver.quit();

        //edge浏览器
//        System.setProperty("webdriver.edge.driver", "WebDriverExe\\msedgedriver.exe");
//        //实例化webdriver，向上转型，chromedriver是子类
//        WebDriver edgeDriver = new EdgeDriver();
//        try {
//            searchBaidu(edgeDriver);
//            edgeDriver.quit();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        //IE浏览器
//        System.setProperty("webdriver.ie.driver", "WebDriverExe\\IEDriverServer.exe");
//        WebDriver internetExplorerDriver=new InternetExplorerDriver();
//        searchBaidu(internetExplorerDriver);
//        internetExplorerDriver.quit();
    }
    public static void searchBaidu(WebDriver driver) throws InterruptedException {
        //1.访问百度网站,url最好复制过来
        driver.get("https://www.baidu.com/");
        driver.navigate().to("https://www.zhihu.com/");
        //后退
        driver.navigate().back();

        //2.输入搜索的内容：测试开发工程师
        //2.1找到输入框，bg s_ipt_wr quickdelete-wrap
        WebElement searchInput = driver.findElement(By.xpath("//input[@id='kw']"));

        //2.2在输入框中输入内容
        searchInput.sendKeys("测试开发工程师");

        //3.执行搜索
        //点击百度一下
        driver.findElement(By.cssSelector("#su")).click();

        //4.查看搜索结果是否正确
        //进程休眠，单位师毫秒
        Thread.sleep(2000);
        //4.1验证页面标题是否正确
        String title = driver.getTitle();

        System.out.println("当前的页面标题是："+title);
        if("测试开发工程师_百度搜索".equals(title)){
            System.out.println("结果正确");
        }
        else{
            System.out.println("测试结果与预期目标不相符");
        }
        //4.2验证url中是否包含测试开发工程师
        String currentUrl = driver.getCurrentUrl();
        System.out.println("当前页面的url是"+currentUrl);
        try {
            String 测试开发工程师 = URLEncoder.encode("测试开发工程师", "utf-8");
            System.out.println("转换后的编码是："+测试开发工程师);
            System.out.println(currentUrl.contains(测试开发工程师));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //3.获取完整的页面源码内容
        String pageSource = driver.getPageSource();
        System.out.println(pageSource.contains("测试开发工程师"));

        //4.判断某个元素的内容，是预期值
        //判断第二条搜索记录的文本内容，包含测试开发工程师
        //第二条搜索记录这个业务逻辑，通过定位表达式来进行表达。
        //div[@id='content_left']/div/h3/a/em
        String text = driver.findElement(By.xpath("//div[@id='content_left']/div/h3/a/em")).getText();
        System.out.println(text);

        //5.判断元素的属性
        String href = driver.findElement(By.xpath("//div[@id='content_left']/div/h3/a")).getAttribute("href");
        System.out.println(href);

//        driver.findElement(By.xpath("//a[text()='百度首页']")).click();

        //6.页面跳转
        driver.navigate().back();
        String backtitle = driver.getTitle();
        System.out.println(backtitle);
        driver.navigate().forward();
        Thread.sleep(2000);
        String forwardtitle = driver.getTitle();
        System.out.println(forwardtitle);


    }
}
