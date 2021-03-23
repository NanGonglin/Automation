package com.testing.class4;

import com.testing.shop.ShopWebKeyWord;
import org.openqa.selenium.By;

import java.util.Set;

/**
 * @Classname CheckOrder
 * @Description 类型说明
 * @Date 2021/3/20 23:12
 * @Created by 小白sy
 */
public class SearchOrder {
    public static void main(String[] args) {
        ShopWebKeyWord web=new ShopWebKeyWord();
        web.openChromeBrowser();
        web.visitURL("http://www.testingedu.com.cn:8000/");

        web.click("//a[text()='登录']");
        web.input("//input[@id='username']","1766342177@qq.com");
        web.input("//input[@id='password']","123456");
        web.input("//input[@id='verify_code']","111");
        web.click("//a[@name='sbtbutton']");
        searchOrder(web);

    }

    public static void searchOrder(ShopWebKeyWord web) {
        web.click("//a[text()='我的订单']");
        web.switchWindows("我的订单");
        //输入要查询的订单号
//        web.click("//a[text()='查看全部订单']");
        web.input("//input[@id='search_key']", "202103122135142663");
        web.click("//input[@value='查询']");
        web.assertText("//div/span[text()='订单编号：']/em", "202103122135142663","equals");
    }
}
