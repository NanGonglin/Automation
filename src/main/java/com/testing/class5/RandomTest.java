package com.testing.class5;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Classname RandomTest
 * @Description 类型说明
 * @Date 2021/3/19 22:04
 * @Created by 小白sy
 */
public class RandomTest {
    public static void main(String[] args) {
//        Random random=new Random();
//        String[] availStr=new String[]{"Roy","will","土匪","卡卡"};
//        int number=random.nextInt(availStr.length-1);
//        System.out.println(availStr[number]);

        //将当前时间转换为时间戳形式
        Date now=new Date();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        String format=sdf.format(now);
        System.out.println(format);
    }
}
