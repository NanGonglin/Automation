package com.testing.web;

import com.testing.webDriver.GoogleDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Classname WebKeyWord
 * @Description web自动化关键字封装
 * @Date 2021/3/11 22:28
 * @Created by 小白sy
 */
public class WebKeyWord {

    //所有方法都要用到的类，采用成员变量
    public WebDriver driver = null;

    /**
     * 打开浏览器的方法
     *
     * @param browser 对应得浏览器
     */
    public void openBrowser(String browser) {
        switch (browser) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "E:\\测试开发课堂笔记\\第3部分 java自动化\\Java自动化第1课-初识selenium\\driver\\chromedriver.exe");
                driver = new ChromeDriver();
                //隐式等待，作用是在给定的时间内查找内容
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                break;
            case "edge":
                System.setProperty("webdriver.edge.driver", "WebDriverExe\\msedgedriver.exe");
                driver = new EdgeDriver();
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                break;
            case "ie":
                System.setProperty("webdriver.ie.driver", "WebDriverExe\\IEDriverServer.exe");
                driver = new InternetExplorerDriver();
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                break;
            default:
                GoogleDriver defaultDriver = new GoogleDriver("E:\\测试开发课堂笔记\\第3部分 java自动化\\Java自动化第1课-初识selenium\\driver\\chromedriver.exe");
                driver = defaultDriver.getdriver();
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                break;
        }
    }

    /**
     * 访问指定url的网页
     *
     * @param url 要访问的网址
     */
    public void visitURL(String url) {
        try {
            driver.get(url);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("访问网页失败");
        }

    }

    /**
     *  输入内容
     * @param xpath 要输入的位置定位
     * @param content 要输入的内容
     */
    public void input(String xpath, String content) {
        try {
            WebElement input = driver.findElement(By.xpath(xpath));
            input.clear();
            input.sendKeys(content);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("向" + xpath + "输入内容时失败");
        }
    }

    /**
     *  点击某个元素
     * @param xpath 通过xpath路径定位元素
     */
    public void click(String xpath) {
        try {
            driver.findElement(By.xpath(xpath)).click();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("点击按钮失败");
        }
    }

    /**
     * 强制等待
     *
     * @param seconds
     * @throws InterruptedException
     */
    public void halt(int seconds) throws InterruptedException {
        Thread.sleep(seconds * 1000);
    }

    /**
     * 隐式等待
     */
    public void imWait(){
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    /**
     * 显示等待
     */
    public void exWaitTextToBe(){
        //设置一个10S的机会
        WebDriverWait wait=new WebDriverWait(driver,10);
        //指定等待的条件，直到在指定位置找到了数据结构与算法，若10S内未找到
        wait.until(ExpectedConditions.textToBe(By.xpath("//div[@id='content_left']/div/h3/a/em"), "数据结构与算法"));
    }
    /**
     * 自定义显示等待
     */
    public void exWaitTextToContains(){
        //设置一个10S的机会
        try {
            WebDriverWait wait=new WebDriverWait(driver,10);
            //指定等待的条件，直到在指定位置找到了数据结构与算法，若10S内未找到
            wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    try {
                        String text = driver.findElement(By.xpath("//div[@id='content_left']/div/h3/a/em")).getText();
                        return text.contains("数据结构与算法");
                    } catch (Exception var3) {
                        return false;
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  获取当前页面的标题
     * @return 返回标题内容
     * @throws InterruptedException
     */
    public String getTitle() throws InterruptedException {

        return driver.getTitle();
    }

    /**
     * 通过xpath方式获取元素内容
     * @param xpath 元素的定位表达式
     * @return
     */
//    public void getText(String xpath){
//        try {
//            String text=driver.findElement(By.xpath(xpath)).getText();
//            System.out.println("getText的结果是："+text);
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("获取文本内容失败");;
//        }
//    }

    /**
     * 获取多个元素的文本内容
     */
    public List<String> getAllText(String xpath) {
        List<WebElement> element = driver.findElements(By.xpath(xpath));
        //创建结果list
        List<String> result = new ArrayList<>();
        //遍历List里面的每一个元素，获取文本内容，添加到结果list中
        for (WebElement e : element) {
            result.add(e.getText());
//            e.click();
        }
        return result;
    }

    /**
     * 获取文本内容的同时，判断是否符合预期
     * 1、同时返回文本结果和判断结果
     * 2、可以选择是否判断(通过是否输入了预期值，判断要不要执行断言操作)
     * 通过可变参数，完成选择，如果输入参数个数大于0，则进行断言操作，否则不进行
     * 3、业务逻辑过程
     */
    public Map<String, Boolean> getText(String xpath, String... expect) {
        try {
            String text = driver.findElement(By.xpath(xpath)).getText();
            //默认返回结果是false,因为可能出现没比较的情况
            boolean result = false;
            //可变参数长度大于0，就要断言
            if (expect.length > 0) {
                //预期值可以填多个，只要这个结果是预期值中的其中一个，置断言结果为真
                for (String ex : expect) {
                    //针对每个预期值进行断言，只要其中有通过的，结果就置为false，否则不动
                    if (text.contains(ex)) {
                        result = true;
                    }
                }
            }
            Map<String, Boolean> textandResult = new HashMap<>();
            textandResult.put(text, result);
            return textandResult;
        } catch (Exception e) {
            e.printStackTrace();
            //返回自己指定内容的结果
            Map<String, Boolean> noresult = new HashMap<>();
            noresult.put("没有结果", false);
            return noresult;
        }
    }

    /**
     * 判断某个元素文本内容是否包含或者等于预期值
     */
    public void assertText(String xpath, String method, String expect) {
        //先对结果进行获取
        String text = "";
        try {
            text = driver.findElement(By.xpath(xpath)).getText();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取结果失败");
        }
        switch (method) {
            case "=":
                if (text.equals(expect)) {
                    System.out.println("assertText测试等于预期内容成功");
                } else {
                    System.out.println("assertText测试等于预期内容失败");
                }
                break;
            case "contains":
                if (text.contains(expect)) {
                    System.out.println("assertText测试包含预期内容成功");
                } else {
                    System.out.println("assertText测试包含预期内容失败");
                }
                break;
        }
    }

    /**
     * * 断言页面中包含指定内容
     * * @param expect 预期包含内容
     */
    public String assertPageContains(String expect) {
        if (driver.getPageSource().contains(expect)) {
            return "assertPageContains测试包含预期内容成功";
        } else {
            return "assertPageContains测试包含预期内容失败";
        }
    }

    /**
     * 常用的定位方法，能够基于输入，进行选择，用对应的方法调用定位表达式
     * @param by 通过哪种方法定位
     * @param locator 定位的内容
     */
    public void click(String by, String locator) {
        try {
            switch (by) {
                case "id":
                    driver.findElement(By.id(locator)).click();
                    break;
                case "name":
                    driver.findElement(By.name(locator)).click();
                    break;
                case "classname":
                    driver.findElement(By.className(locator)).click();
                    break;
                case "tagname":
                    driver.findElement(By.tagName(locator)).click();
                    break;
                case "xpath":
                    driver.findElement(By.xpath(locator)).click();
                    break;
                case "css":
                    driver.findElement(By.cssSelector(locator)).click();
                    break;
                default:
                    driver.findElement(By.xpath(locator)).click();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 关闭浏览器的操作
     */
    public void closeBrower() {
        driver.quit();
    }
}

