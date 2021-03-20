package com.testing.class2;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.testing.web.WebKeyWord;

import java.util.Map;

/**
 * @Classname KeyWordTest
 * @Description 类型说明
 * @Date 2021/3/11 23:02
 * @Created by 小白sy
 */
public class KeyWordTest {
    public static void main(String[] args) throws InterruptedException {
        WebKeyWord web=new WebKeyWord();
        web.openBrowser("chrome");
        web.visitURL("https://www.baidu.com/");
        web.input("//input[@id='kw']","数据结构和算法");
        web.click("id", "su");
        web.halt(2);
        System.out.println(web.getTitle());
        web.getText("//div[@id='content_left']/div/h3/a");
        web.assertPageContains("数据结构和算法");
        web.assertText("//div[@id='content_left']/div/h3/a","contains","数据结构");
        //可变参数0个
        Map<String,Boolean> textandresult0= web.getText("//div[@id='content_left']/div/h3/a/em");
        System.out.println(textandresult0);
        web.exWaitTextToContains();
        //可变参数1个，不包含
        Map<String,Boolean> textandresult1= web.getText("//div[@id='content_left']/div/h3/a/em","数据结构");
        System.out.println(textandresult1);
        //可变参数多个，包含预期内容
        Map<String,Boolean> textandresult3= web.getText("//div[@id='content_left']/div/h3/a/em","哔哩哔哩","数据结构","知乎");
        System.out.println(textandresult3);
        System.out.println("获取所有的搜索结果是"+web.getAllText("//div[@id='content_left']/div//h3/a"));

        web.closeBrower();
    }
}
