package com.testing.pageShop;

import com.testing.shop.ShopWebKeyWord;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @Classname HomePage
 * @Description 类型说明
 * @Date 2021/3/23 19:04
 * @Created by 小白sy
 */
public class HomePage {
    //将所有操作的页面元素和执行步骤分离开来。
    public ShopWebKeyWord kw;
    public String Url = "http://www.testingedu.com.cn:8000/Home/Index/index.html";

    //通过@findby注解中的定位表达式定位到的元素将传递给public WebElement searchInput;
    @FindBy(xpath = "//input[@id='q']")
    public WebElement searchInput;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement searchBtn;

    @FindBy(xpath = "//div[@class='shop-list-splb p']/ul/li[1]/div/div[1]/a")
    public WebElement firstGood;

    @FindBy(xpath = "//*[@id='join_cart']")
    public WebElement addCartBtn;

    //要让在homepage类中由@findby注解定义的成员变量生效，需要使用pagefactroy类进行页面初始化。
    public HomePage(ShopWebKeyWord keyword) {
        //将类中的成员变量kw类，赋值为传进来的参数。
        kw=keyword;

    }

    public void load() {
        kw.visitURL(Url);
        PageFactory.initElements(kw.driver, this);
    }

    //页面中进行的操作。
    public void joinCart() {
        searchInput.sendKeys("净水器");
        searchBtn.click();
        kw.halt("2");
        firstGood.click();
        addCartBtn.click();
    }
}
