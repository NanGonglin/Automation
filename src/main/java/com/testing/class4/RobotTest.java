package com.testing.class4;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @Classname RobotTest
 * @Description 类型说明
 * @Date 2021/3/16 18:58
 * @Created by 小白sy
 */
public class RobotTest {
    public static void main(String[] args) throws AWTException {
        Robot rb=new Robot();
        //鼠标移动到指定位置,这里如果用鼠标右键运行就不是在这个偏移量位置输入4了
        rb.mouseMove(848, 195);
        //点击鼠标左键
        rb.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
        //松开鼠标左键
        rb.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
        //按下键盘4
        rb.keyPress(KeyEvent.VK_4);
        //释放按下
        rb.keyRelease(KeyEvent.VK_4);
        //暂停2秒钟
        rb.delay(2000);
        //按下删除键
//        rb.keyPress(KeyEvent.VK_BACK_SPACE);
    }
}
