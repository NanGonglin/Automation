package com.testing.class5;

import com.testing.web.AdminPage;
import com.testing.web.UserPage;

/**
 * @Classname AdminPage
 * @Description 类型说明
 * @Date 2021/3/23 15:59
 * @Created by 小白sy
 */
public class AdminPageTest {
    public static void main(String[] args) {
        AdminPage admin=new AdminPage();
        admin.openChromeBrowser();
//        admin.visitURL("http://www.testingedu.com.cn:8000/Admin/Admin/login");
//        admin.LoginAdmin("admin", "123456", "111");
//        admin.adminAddGoods(admin);
//        admin.adminAddOrder();

        UserPage user=new UserPage();
        //调用自继承父类的setDriver方法，将userpage的driver赋值为adminpage的driver，从而使用同一个浏览器
        user.setDriver(admin);
        user.visitURL("http://www.testingedu.com.cn:8000/");
        user.userLogin("1766342177@qq.com","123456","111");
//        user.updateUserInfo();
//        user.searchGoods(admin);
        user.shopBuy();
        user.searchOrder();
    }
}
