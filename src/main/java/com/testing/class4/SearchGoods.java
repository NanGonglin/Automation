package com.testing.class4;

import com.testing.shop.ShopWebKeyWord;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

/**
 * @Classname SearchGoods
 * @Description 类型说明
 * @Date 2021/3/21 10:44
 * @Created by 小白sy
 */
public class SearchGoods {
    public static void main(String[] args) {
        ShopWebKeyWord web=new ShopWebKeyWord();

        web.openChromeBrowser();
        web.visitURL("http://www.testingedu.com.cn:8000/");

        web.click("//a[text()='电器工具']");
        web.switchWindows("商品列表");
        web.click("//a/span[text()='生活电器']");
        web.click("//a/span[text()='多选']");
        web.click("//a/span[text()='藏青色']");
        web.click("//a/span[text()='宝蓝色']");
        web.click("//a/span[text()='橙子色']");
        web.click("//div/a[text()='确定']");
        web.click("//a/span[text()='净水器']");

        List<WebElement> texts = web.driver.findElements(By.xpath("//div[@class='shop-list-splb p']/ul/li/div/div[4]/a"));
        for(WebElement text:texts){
            System.out.println(text.getText());
            if(!text.getText().contains("净水器")){
                System.out.println("查询结果不正确");
            }
        }
    }
}
