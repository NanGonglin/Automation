package com.testing.class5;

import com.testing.common.AutoLogger;
import com.testing.pageAdmin.AddGoodsPage;
import com.testing.pageAdmin.AdminLoginPage;
import com.testing.pageShop.HomePage;
import com.testing.pageShop.LoginPage;
import com.testing.shop.ShopWebKeyWord;

/**
 * @Classname TestSeleniumPOFactory
 * @Description 类型说明
 * @Date 2021/3/23 19:12
 * @Created by 小白sy
 */
public class TestSeleniumPOFactory {
    public static void main(String[] args) {
        ShopWebKeyWord web=new ShopWebKeyWord();
        web.openChromeBrowser();
        AutoLogger.log.info("--------------执行后台登录界面测试----------------------");
        AdminLoginPage adminLoginPage=new AdminLoginPage(web.getDriver());
        adminLoginPage.load();
        adminLoginPage.login();
        AutoLogger.log.info("--------------执行后台添加商品测试----------------------");
        AddGoodsPage addGoodsPage=new AddGoodsPage(web);
        addGoodsPage.load();
        addGoodsPage.addGoods();
        AutoLogger.log.info("--------------执行前台用户登录界面测试----------------------");
        LoginPage loginPage=new LoginPage(web);
        loginPage.load();
        loginPage.login();
        AutoLogger.log.info("--------------执行前台用户购买商品测试----------------------");
        HomePage homePage=new HomePage(web);
        homePage.load();
        homePage.joinCart();
    }
}
