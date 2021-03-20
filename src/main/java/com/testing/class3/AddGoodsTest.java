package com.testing.class3;

import com.testing.common.AutoLogger;
import com.testing.shop.ShopWebKeyWord;

import java.io.IOException;
import java.util.Random;

/**
 * @Classname AddGoods
 * @Description 类型说明
 * @Date 2021/3/15 21:37
 * @Created by 小白sy
 */
public class AddGoodsTest {
    public static void main(String[] args) throws IOException {
        ShopWebKeyWord web=new ShopWebKeyWord();
        AutoLogger.log.trace("自动化测试开始了");
        //1.打开网页，管理员登录
        web.openChromeBrowser();
        web.visitURL("http://www.testingedu.com.cn:8000/Admin/Admin/login");
        web.input("//input[@name='username']", "admin");
        web.input("//input[@name='password']", "123456");
        web.input("//input[@name='vertify']", "111");
        web.click("//input[@name='submit']");

        //2.点击商城，点击添加商品
        web.click("//a[text()='商城']");
        web.switchIframe("//iframe[@id='workspace']");
        web.click("//span[text()='添加商品']");

        //3.填写新增的商品信息
        Random random=new Random();
        String[] availStr=new String[]{"西北区","华北区","华南区","东北区"};
        int number=random.nextInt(availStr.length-1);
        System.out.println(availStr[number]);
        web.input("//input[@name='goods_name']", "小米净水器"+availStr[number]);
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
//        web.click("//div[text()='点击选择文件']/following-sibling::div/label");


//        //6.1通过robot类点击要上传的文件位置
//        web.halt(2);
//        web.moveToClick(329,454);
//        //点击打开按钮
//        web.moveToClick(737,562);
//        web.click("//div[text()='确定使用']");
        //6.2通过autoit，编译出exe文件，然后调用实现
//        web.halt(3);
//        Runtime runner=Runtime.getRuntime();
//        runner.exec("E:\\UPLOAD.EXE");

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

        //8.提交商品
        web.switchUpIframe();
        web.click("//a[text()='确认提交']");

        //9.关闭浏览器
//        web.closeBrowser();

    }
}
