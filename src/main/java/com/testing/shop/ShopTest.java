package com.testing.shop;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

import java.util.Set;

/**
 * @Classname ShopTest
 * @Description 类型说明
 * @Date 2021/3/12 19:51
 * @Created by 小白sy
 */
public class ShopTest {
    public static void main(String[] args) {
        ShopWebKeyWord web=new ShopWebKeyWord();
        web.openChromeBrowser();
        web.visitURL("http://www.testingedu.com.cn:8000");
        //点击登录按钮
        web.click("//a[text()='登录']");
        //输入用户名
        web.input("//input[@id='username']","1766342177@qq.com");
        //输入密码
        web.input("//input[@id='password']","123456");
        //输入验证码
        web.input("//input[@id='verify_code']","123456");
        //点击登录按钮
        web.click("//a[@name='sbtbutton']");
        //点击返回商城首页
        web.click("//a[text()='返回商城首页']");

        //2.1商城首页找到手机数码中的手机通讯中进行点击
        Actions actions=new Actions(web.driver);
        //悬停到某个元素上
        actions.moveToElement(web.getDriver().findElement(By.xpath("//a[text()='手机数码']"))).perform();
        //拖拽元素
        //actions.dragAndDropBy(web.getDriver().findElement(By.xpath("//span[text()='我的购物车']")),400,800);
        //组合式的拖拽
        //actions.clickAndHold(web.getDriver().findElement(By.xpath("//span[text()='我的购物车']"))).moveByOffset(200, 300).perform();

        //2.2通过执行js来完成对手机通讯的点击
        //强制转换
//        JavascriptExecutor runner =(JavascriptExecutor)web.getDriver();
        //2.2.1通过css选择器,使用document.queryselector()方法来定位元素，并点击
        //runner.executeScript("document.querySelector('dl.clearfix a').click");

        //2.2.2通过js executeScript,执行的时候，传递可变参数列表，通过js脚本中arguments[0]来调用参数列表中的内容
//        WebElement mobilePhone=web.getDriver().findElement(By.xpath("//a[text()='手机通讯' and @target]"));
//        runner.executeScript("arguments[0].click()",mobilePhone);
//        String result = runner.executeScript("return arguments[0].innerText", mobilePhone).toString();
//        System.out.println(result);

        //打开新窗口之前，获取窗口的句柄信息，句柄就是一个字符串
        String homepage=web.getDriver().getWindowHandle();
        System.out.println("首页的句柄是："+homepage);

        web.halt(10);
        web.click("//a[text()='手机通讯' and @target]");
        //所有窗口是一个set集合
        Set<String> windowhandles=web.getDriver().getWindowHandles();
        System.out.println("所有窗口的句柄是："+windowhandles);

        //通过计数器来计算当前句柄是set中的第几个
        int count=0;
        for(String window:windowhandles){
            count++;
            if(count==2){
                System.out.print("这是第二个窗口的句柄");
                System.out.println(window);
            }
        }
//        web.halt(2);

        Set<String> newHandles=web.getDriver().getWindowHandles();
        System.out.println("切换窗口之后的所有句柄："+newHandles);
        //切回之前的窗口
        web.getDriver().switchTo().window(homepage);

//        web.halt(2);

        //基于页面标题切换，来判断相应句柄进行切换
        for(String handle:newHandles){
            //先切换到当前窗口
            web.getDriver().switchTo().window(handle);
            //获取当前窗口的标题
            String title = web.getDriver().getTitle();
            if("商品列表".equals(title)){
                break;
            }
        }

        //3.选择第5个商品，点击加入购物车
        web.click("//div[@class='shop-list-splb p']/ul/li[5]//a[text()='加入购物车']");

        //4.进入商品详情页面，点击加入购物车结算
        web.click("//a[@id='join_cart']");



        //5.点击去结算
        web.getDriver().switchTo().frame("layui-layer-iframe1");
        //不推荐使用Element
//        web.getDriver().switchTo().frame(web.getDriver().findElement(By.xpath("//iframe[contains(@id,'layui')]")));
        web.click("//a[text()='去购物车结算']");

        //滚动页面，按绝对位置滚动
        web.runJS("window.scrollTo(0,600)");
        //将某个元素滚动到出现在页面中
        web.runJsWithElement("scrollIntoView()", "//a[text()='加入购物车']");



    }
}
