package com.testing.pageShop;

import com.testing.class3.ShopBuyKeyWord;
import com.testing.shop.ShopWebKeyWord;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @Classname LoginPage
 * @Description 类型说明
 * @Date 2021/3/23 19:04
 * @Created by 小白sy
 */
public class LoginPage {
    public ShopWebKeyWord kw;
    public String Url = "http://www.testingedu.com.cn:8000/Home/user/login.html";

    @FindBy(xpath = "//input[@id='username']")
    public WebElement user;

    @FindBy(xpath = "//input[@id='password']")
    public WebElement password;

    @FindBy(xpath = "//input[@placeholder='验证码']")
    public WebElement verifyCode;

    @FindBy(xpath = "//a[@name='sbtbutton']")
    public WebElement submitBtn;

    public LoginPage(ShopWebKeyWord keyword) {
        kw=keyword;
        PageFactory.initElements(kw.driver, this);
    }

    public void load() {
        kw.visitURL(Url);
    }

    public void login() {
        try {
            user.sendKeys("1766342177@qq.com");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            password.sendKeys("123456");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        verifyCode.sendKeys("1");
        submitBtn.click();
        kw.halt("2");
    }
}
