package com.testing.shop;

import com.testing.common.AutoLogger;
import com.testing.web.RobotUtils;
import com.testing.webDriver.GoogleDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Classname ShopWeb
 * @Description 类型说明
 * @Date 2021/3/12 19:40
 * @Created by 小白sy
 */
public class ShopWebKeyWord {
    public WebDriver driver=null;
    /**
     * 打开谷歌浏览器
     */
    public void openChromeBrowser(){
        GoogleDriver gg=new GoogleDriver("E:\\测试开发课堂笔记\\第3部分 java自动化\\Java自动化第1课-初识selenium\\driver\\chromedriver.exe");
        driver=gg.getdriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        AutoLogger.log.trace("*******浏览器启动了********");
    }

    /**
     * 网址栏输入要访问的网址
     * @param url 网址的值
     */
    public void visitURL(String url){
        driver.get(url);
    }

    /**
     * 隐式等待
     * @param seconds 等待的时间，单位是秒
     */
    public void imWait(int seconds){
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    /**
     * 点击按钮
     * @param xpath 按钮的位置
     */
    public void click(String xpath){
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(By.xpath(xpath)).click();
    }

    /**
     * 悬停方法
     * @param xpath 定位位置
     */
    public void hover(String xpath){
        try {
            Actions actions=new Actions(driver);
            actions.moveToElement(driver.findElement(By.xpath(xpath))).perform();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 拖拽元素
     * @param xpath 定位元素
     * @param x 拖拽的x偏移量
     * @param y 拖拽的y偏移量
     */
    public void dragAndDrop(String xpath,int x,int y){
        Actions actions=new Actions(driver);
        actions.dragAndDropBy(driver.findElement(By.xpath(xpath)),x,y).perform();
    }

    /**
     * 组合拖拽
     * @param xpath 定位元素
     * @param x 拖拽的x偏移量
     * @param y 拖拽的y偏移量
     */
    public void clickAndHold(String xpath,int x,int y){
        Actions actions=new Actions(driver);
        actions.clickAndHold(driver.findElement(By.xpath(xpath))).moveByOffset(x,y).perform();
    }

    /**
     * 执行js
     * @param jsScript
     */
    public void runJS(String jsScript){
        try {
            JavascriptExecutor runner=(JavascriptExecutor)driver;
            runner.executeScript(jsScript);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将元素通过xpath进行定位，然后再调用js
     * @param method 调用的方法，注意带上(),比如click()或者innerText="xxx"
     * @param xpath
     */
    public void runJsWithElement(String method,String xpath){
        WebElement element = driver.findElement(By.xpath(xpath));
        JavascriptExecutor runner=(JavascriptExecutor)driver;
        runner.executeScript("arguments[0]."+method,element);
    }

    /**
     * 强制等待
     *
     * @param seconds
     * @throws InterruptedException
     */
    public void halt(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("等待失败");
        }
    }

    /**
     * 找到要输入的位置，进行输入
     * @param xpath 输入的位置
     * @param content 输入的内容
     */
    public void input(String xpath,String content){
        WebElement input = driver.findElement(By.xpath(xpath));
        input.clear();
        input.sendKeys(content);
    }
    //清空之前的值
    public void clear(String xpath){
        WebElement input=driver.findElement(By.xpath(xpath));
        input.clear();
    }

    /**
     * 返回driver用于调试
     * @return
     */
    public WebDriver getDriver(){
        return driver;
    }

    /**
     * 切换新窗口
     * @param windowTitle 要切换的窗口的标题
     */
    public void switchWindows(String windowTitle){
        Set<String> newHandles=driver.getWindowHandles();
        for(String handle:newHandles){
            //先切换到当前窗口
           driver.switchTo().window(handle);
            //获取当前窗口的标题
            String title = driver.getTitle();
            if(windowTitle.equals(title)){
                break;
            }
        }
    }

    /**
     * 切换iframe
     * @param xpath 要切换的iframe
     */
    public void switchIframe(String xpath){
        try {
            driver.switchTo().frame(driver.findElement(By.xpath(xpath)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 下拉框选择值
     * @param xpath 定位元素
     * @param via 通过value还是text来选择值
     * @param content 选择的值
     */
    public void select(String xpath,String via,String content){
        WebElement element = driver.findElement(By.xpath(xpath));
        Select userSelected=new Select(element);
        switch (via){
            case "value":
                userSelected.selectByValue(content);
                break;
            case "content":
                userSelected.selectByVisibleText(content);
                break;
        }
    }
    /**
     * 用键盘的方法使鼠标移动到指定的位置，然后点击鼠标左键
     * @param x 指定位置x的偏移量
     * @param y 指定位置y的偏移量
     */
    public void moveToClick(int x,int y){
        try {
            RobotUtils rb= new RobotUtils();
            rb.moveToClick(x, y);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("封装失败");
        }
    }

    /**
     * 切换到上一层级的iframe
     */
    public void switchUpIframe(){
        driver.switchTo().parentFrame();
    }

    /**
     * 切换到最外层的iframe
     */
    public void swichOutIframe(){
        driver.switchTo().defaultContent();
    }

    /**
     * 关闭浏览器
     */
    public void closeBrowser(){
        driver.quit();
    }

}
