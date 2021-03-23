package com.testing.web;

import com.testing.shop.ShopWebKeyWord;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * @Classname UserPage
 * @Description 类型说明
 * @Date 2021/3/23 16:14
 * @Created by 小白sy
 */
public class UserPage extends ShopWebKeyWord {
    /**
     * 前台用户登录商城
     * @param user
     * @param pwd
     * @param verifyCode
     */
    public void userLogin(String user,String pwd,String verifyCode){
        click("//a[text()='登录']");
        input("//input[@id='username']",user);
        input("//input[@id='password']",pwd);
        input("//input[@id='verify_code']",verifyCode);
        click("//a[@name='sbtbutton']");
    }

    /**
     * 用户购买商品
     */
    public  void shopBuy() {
        //点击返回商城首页
        click("//a[text()='返回商城首页']");

        //2.商城首页找到手机数码中的手机通讯进行点击
        hover("//a[text()='手机数码']");
        click("//a[text()='手机通讯' and @target]");

        //3.切换窗口
        switchWindows("商品列表");

        //4.选择第5个商品，点击加入购物车
        click("//div[@class='shop-list-splb p']/ul/li[5]//a[text()='加入购物车']");

        //5.进入商品详情页面，点击加入购物车结算
        click("//a[@id='join_cart']");

        //6.点击去结算
        switchIframe("//div[@class='layui-layer-content']/iframe");

        //7.点击去购物车结算
        click("//a[text()='去购物车结算']");

        //8.返回上一层iframe
        switchUpIframe();
    }

    /**
     * 用户修改个人信息
     */
    public void updateUserInfo() {
        click("//li/a[text()='个人信息']");

        //3.修改昵称和性别后点击确认保存
        input("//input[@id='nickname']", "海棠依旧");
        click("//input[@id='woman']");
        click("//input[@value='确认保存']");
        imWait(4);
        click("//a[text()='返回商城首页']");
    }

    /**
     * 用户在前台搜索商品
     */
    public  void searchGoods(ShopWebKeyWord web){
        click("//a[text()='电器工具']");
        switchWindows("商品列表");
        click("//a/span[text()='生活电器']");
        click("//a/span[text()='多选']");
        click("//a/span[text()='藏青色']");
        click("//a/span[text()='宝蓝色']");
        click("//a/span[text()='橙子色']");
        click("//div/a[text()='确定']");
        click("//a/span[text()='净水器']");

        List<WebElement> texts = web.driver.findElements(By.xpath("//div[@class='shop-list-splb p']/ul/li/div/div[4]/a"));
        for(WebElement text:texts){
            if(!text.getText().contains("净水器")){
                System.out.println("查询结果不正确");
            }
        }
    }

    /**
     * 用户搜索自己的订单
     */
    public  void searchOrder() {
        click("//a[text()='我的订单']");
        switchWindows("我的订单");
        //输入要查询的订单号
        input("//input[@id='search_key']", "202103122135142663");
        click("//input[@value='查询']");
        assertText("//div/span[text()='订单编号：']/em", "202103122135142663","equals");
    }
}
