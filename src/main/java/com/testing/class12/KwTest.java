package com.testing.class12;

import com.testing.inter.HttpClientUtils;
import org.apache.http.impl.client.HttpClients;

/**
 * @Classname KwTest
 * @Description 类型说明
 * @Date 2021/3/26 17:56
 * @Created by 测试园
 */
public class KwTest {
    public static void main(String[] args) {
        HttpClientUtils sy= new HttpClientUtils();
        sy.createclient();
        String s = sy.doGet("https://www.baidu.com/sugrec?pre=1&p=3&ie=utf-8&json=1&prod=pc&from=pc_web&sugsid=33256,33344,31660,33392,33695,33714,26350&wd=i&req=2&csor=1&cb=jQuery11020016567752707456584_1616729416184&_=1616729416185");
        System.out.println(s);

        String fileparam="{\n" +
                "  \"text\": {\"id\": \"WU_FILE_0\",\"name\": \"sy\"},\n" +
                "  \"bin\": {\"file\": \"E:\\\\\\\\小米净水器主页图片.png\"}\n" +
                "}";
        String file = sy.doPost("http://www.testingedu.com.cn:8000/index.php/home/Uploadify/imageUp/savepath/head_pic/pictitle/banner/dir/images.html", fileparam, "file");
        System.out.println(file);
    }
}
