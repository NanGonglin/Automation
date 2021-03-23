package com.testing.web;

import com.testing.shop.ShopWebKeyWord;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Classname AdminPage
 * @Description 类型说明
 * @Date 2021/3/23 16:01
 * @Created by 小白sy
 */
public class AdminPage extends ShopWebKeyWord {
    /**
     * 商城登录后台
     * @param user 登录的管理员账号
     * @param pwd  管理员密码
     * @param verifyCode  验证码
     */
    public void LoginAdmin(String user,String pwd,String verifyCode){
        input("//input[@name='username']", user);
        input("//input[@name='password']", pwd);
        input("//input[@name='vertify']", verifyCode);
        click("//input[@name='submit']");
    }
    public void adminAddGoods(ShopWebKeyWord web)  {
        //2.点击商城，点击添加商品
        web.click("//a[text()='商城']");
        web.switchIframe("//iframe[@id='workspace']");
        web.click("//span[text()='添加商品']");


        //3.填写新增的商品信息
        //生成日期格式拼接商品名
        Date now=new Date();
        SimpleDateFormat goodsNum=new SimpleDateFormat("ddHHmm");
        String format=goodsNum.format(now);
        System.out.println(format);
        String goodsName="净水器"+format;

        //截图操作
        //1.将driver转换为截图对象
        TakesScreenshot screenshot=(TakesScreenshot) (web.getDriver());
        //2.截取当前浏览器图片
        File screenPic=screenshot.getScreenshotAs(OutputType.FILE);
        //3.设置图片的保存位置
        File savePic=new File("logs/Screenshot/"+format+".png");
        //4.将图片存放到指定的位置
        try {
            com.google.common.io.Files.copy(screenPic,savePic);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("报错错误截图失败");
        }

        web.input("//input[@name='goods_name']",goodsName);


        web.input("//textarea[@name='goods_remark']", "小米净水器家用净水机H600G 双芯六级过滤 无罐直饮水 RO反渗透 双出水龙头 米家APP智能互联");

        //4.选择商品种类
        web.halt(1);
        web.select("//select[@id='cat_id']","content","电器");
        web.halt(1);
        web.select("//select[@id='cat_id_2']","content","生活电器");
        web.halt(1);
        web.select("//select[@id='cat_id_3']","content","净水器");

        //5.填写价格
        web.input("//input[@name='shop_price']", "15999");
        web.input("//input[@name='market_price']", "17999");

        //6.图片上传
        web.click("//input[@id='button1']/following-sibling::input");
        web.switchIframe("//iframe[@id='layui-layer-iframe1']");


        //6.3直接通过input元素输入内容即可
        web.input("//div[text()='点击选择文件']/following-sibling::div/input", "E:\\小米净水器主页图片.png");
        web.halt(1);
        web.click("//div[text()='确定使用']");

        //切换iframe，回到workspace
        //不能在iframe中直接切到另一个iframe，可以切回到上一级或者最外面，我们选择上一级，
        web.switchUpIframe();

        //7.是否包邮选择是
//        web.halt(1);
//        web.runJsWithElement("scrollIntoView()", "//label[text()='是否包邮']");
//        web.halt(1);
        web.click("//label[text()='是否包邮']/../following-sibling::dd//label[text()='是']");

        //8.富文本框输入内容
        web.switchIframe("//iframe[@id='ueditor_0']");
        web.input("//html/body/p", "测试输入");

        //9.提交商品
        web.switchUpIframe();
        web.click("//a[text()='确认提交']");

        web.assertMysqlData("select goods_id,shop_price,goods_name from tp_goods where goods_name='"+goodsName+"'", "goods_id");
    }

    /**
     * 管理员后台新增订单
     */
    public  void adminAddOrder() {
        switchUpIframe();
        //2.进入添加订单页面
        click("//h3[text()='订单']");
        click("//a[text()='添加订单']");

        //3.录入订单信息
        switchIframe("//iframe[@id='workspace']");
        input("//input[@id='user_name']", "海棠依旧");
        click("//select[@id='user_id']/following-sibling::a");
        //3.1输入用户
        input("//input[@id='consignee']","海棠依旧");
        input("//input[@id='mobile']", "075523423423");
        select("//select[@id='province']", "content", "北京市");
        select("//select[@id='city']", "content", "市辖区");
        select("//select[@id='district']", "content", "海淀区");
        input("//input[@id='address']", "北京邮电大学");
        input("//input[@id='zipcode']", "100000");
        input("//input[@placeholder='发票抬头']", "海棠依旧测试的新增订单");

        //4.选择商品
        click("//a[text()='添加商品']");
        switchIframe("//div[@class='layui-layer-content']/iframe");
        select("//select[@id='cat_id']", "value","367");

        input("//input[@name='keywords']","小米净水器华南区");
        click("//input[@value='搜索']");
        click("//div[text()='小米净水器华南区']");
        click("//input[@value='确定']");

        switchUpIframe();
        input("//textarea[@id='admin_note']", "管理员海棠依旧添加订单");
        click("//a[text()='确认提交']");
    }
}
