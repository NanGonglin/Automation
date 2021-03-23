package com.testing.class3;

import com.testing.shop.ShopWebKeyWord;

/**
 * @Classname UserInfo
 * @Description 类型说明
 * @Date 2021/3/15 21:04
 * @Created by 小白sy
 */
public class ShopUserInfo {
    public static void main(String[] args) {
        ShopWebKeyWord web=new ShopWebKeyWord();
        //1.打开浏览器，登录个人账号
        web.openChromeBrowser();
        web.visitURL("http://www.testingedu.com.cn:8000\n");
        web.click("//a[text()='登录']");
        web.input("//input[@id='username']", "1766342177@qq.com");
        web.input("//input[@id='password']", "123456");
        web.input("//input[@id='verify_code']", "111");
        web.click("//a[@name='sbtbutton']");

        //2.点击个人信息
        updateUserInfo(web);

        //4.关闭浏览器
        web.closeBrowser();
    }

    public static void updateUserInfo(ShopWebKeyWord web) {
        web.click("//li/a[text()='个人信息']");

        //3.修改昵称和性别后点击确认保存
        web.input("//input[@id='nickname']", "海棠依旧");
        web.click("//input[@id='woman']");
        web.click("//input[@value='确认保存']");
    }
}
