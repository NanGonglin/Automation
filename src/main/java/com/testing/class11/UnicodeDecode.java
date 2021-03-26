package com.testing.class11;

import org.apache.xmlbeans.impl.regex.Match;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname UnicodeDecode
 * @Description 类型说明
 * @Date 2021/3/25 16:04
 * @Created by 测试园
 */
public class UnicodeDecode {
    public static void main(String[] args) {
        //unicode编码,其实是u+4位16进制数
        //转换为16进制数为10进制
        int i = Integer.parseInt("7f8e", 16);
        //强转为char格式,因为java用的就是Unicode编码，所以直接把数字转成char，就能得到unicode结果。
        System.out.println((char)i);
        String s = unicodeDecode("\\u7f8e\\u56fd \\u4e9a\\u9a6c\\u900a\\u4e91");
        System.out.println(s);
    }
    public static String unicodeDecode(String origin){
        //找到字符串中的u4位16进制数的格式，进行匹配
        //使用正则表达式进行查找匹配
        //由于替换的时候只需要用到4位数字，所以加上()表示是一个分组，进行获取
        Pattern uni=Pattern.compile("\\\\u([0-9a-zA-Z]{4})");
        Matcher m= uni.matcher(origin);
        //创建stringbuffer对象，声明长度为原本的字符串长度
        StringBuffer stringBuffer=new StringBuffer(origin.length());
        while (m.find()){
            m.appendReplacement(stringBuffer, Character.toString((char)Integer.parseInt(m.group(1),16)));
        }
        m.appendTail(stringBuffer);
        return stringBuffer.toString() ;
    }
}
