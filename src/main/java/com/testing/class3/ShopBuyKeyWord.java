package com.testing.class3;

import com.testing.shop.ShopWebKeyWord;

/**
 * @Classname ShopBuyKeyWord
 * @Description 类型说明
 * @Date 2021/3/15 14:17
 * @Created by 小白sy
 */
public class ShopBuyKeyWord {
    public static void main(String[] args) {
        ShopWebKeyWord web=new ShopWebKeyWord();
        web.openChromeBrowser();
        visitShop(web);

        //1.用户登录
        shopLogin(web);

        //2.购买商品
        shopBuy(web);

        //8.关闭浏览器
        web.closeBrowser();
    }

    public static void visitShop(ShopWebKeyWord web) {
        web.visitURL("http://www.testingedu.com.cn:8000");
    }

    public static void shopBuy(ShopWebKeyWord web) {
        //点击返回商城首页
        web.click("//a[text()='返回商城首页']");

        //2.商城首页找到手机数码中的手机通讯进行点击
        web.hover("//a[text()='手机数码']");
        web.click("//a[text()='手机通讯' and @target]");

        //3.切换窗口
        web.switchWindows("商品列表");

        //4.选择第5个商品，点击加入购物车
        web.click("//div[@class='shop-list-splb p']/ul/li[5]//a[text()='加入购物车']");

        //5.进入商品详情页面，点击加入购物车结算
        web.click("//a[@id='join_cart']");

        //6.点击去结算
        web.switchIframe("//div[@class='layui-layer-content']/iframe");

        //7.点击去购物车结算
        web.click("//a[text()='去购物车结算']");
    }

    public static void shopLogin(ShopWebKeyWord web) {
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
    }
}
