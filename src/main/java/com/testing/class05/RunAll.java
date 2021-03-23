package com.testing.class05;

import com.testing.class3.AddGoodsTest;
import com.testing.class3.ShopBuyKeyWord;
import com.testing.shop.ShopWebKeyWord;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @Classname RunAll
 * @Description 类型说明
 * @Date 2021/3/23 15:11
 * @Created by 小白sy
 */
public class RunAll {
    public static void main(String[] args) {
        //1.创建ShopWebKeyWord对象，用于调用浏览器
        ShopWebKeyWord basepage=new ShopWebKeyWord();
        basepage.openChromeBrowser();

        //2.浏览器执行用例时，操作的都是同一个浏览器，区别是对不同页面进行的方法调用不同
        //2.1基于浏览器完成admin页面中的方法调用操作
        AddGoodsTest.visitAdmin(basepage);
        AddGoodsTest.adminLogin(basepage);
        AddGoodsTest.adminAddGoods(basepage);

        //2.2基于浏览器完成shop页面中的方法调用操作
        ShopBuyKeyWord.visitShop(basepage);
        ShopBuyKeyWord.shopLogin(basepage);
        ShopBuyKeyWord.shopBuy(basepage);

        basepage.closeBrowser();
    }
}
