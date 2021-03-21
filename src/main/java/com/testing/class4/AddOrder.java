package com.testing.class4;

import com.testing.shop.ShopWebKeyWord;

/**
 * @Classname AddOrder
 * @Description 类型说明
 * @Date 2021/3/21 11:55
 * @Created by 小白sy
 */
public class AddOrder {
    public static void main(String[] args) {
        ShopWebKeyWord web=new ShopWebKeyWord();
        //1.管理员登录
        web.openChromeBrowser();
        web.visitURL("http://www.testingedu.com.cn:8000/Admin/Admin/login");
        web.input("//input[@name='username']", "admin");
        web.input("//input[@name='password']", "123456");
        web.input("//input[@name='vertify']", "111");
        web.click("//input[@name='submit']");

        //2.进入添加订单页面
        web.click("//h3[text()='订单']");
        web.click("//a[text()='添加订单']");

        //3.录入订单信息
        web.switchIframe("//iframe[@id='workspace']");
        web.input("//input[@id='user_name']", "海棠依旧");
        web.click("//select[@id='user_id']/following-sibling::a");
        //3.1输入用户
        web.input("//input[@id='consignee']","海棠依旧");
        web.input("//input[@id='mobile']", "075523423423");
        web.select("//select[@id='province']", "content", "北京市");
        web.select("//select[@id='city']", "content", "市辖区");
        web.select("//select[@id='district']", "content", "海淀区");
        web.input("//input[@id='address']", "北京邮电大学");
        web.input("//input[@id='zipcode']", "100000");



        web.input("//input[@placeholder='发票抬头']", "海棠依旧测试的新增订单");


        //4.选择商品
        web.click("//a[text()='添加商品']");
        web.switchIframe("//div[@class='layui-layer-content']/iframe");
        web.select("//select[@id='cat_id']", "value","367");
        web.click("//input[@value='搜索']");
        web.click("//div[text()='小米净水器华南区']");
        web.click("//input[@value='确定']");

        web.switchUpIframe();

        web.input("//textarea[@id='admin_note']", "管理员海棠依旧添加订单");

        web.click("//a[text()='确认提交']");
    }
}
