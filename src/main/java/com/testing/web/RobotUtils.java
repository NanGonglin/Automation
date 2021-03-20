package com.testing.web;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @Classname RobotUtils
 * @Description 类型说明
 * @Date 2021/3/16 20:56
 * @Created by 小白sy
 */
public class RobotUtils {
    Robot rb;

    //构造方法
    public RobotUtils() {
        try {
            rb = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
            System.out.println("创建实例失败");
        }
    }

    /**
     * 鼠标移动指定位置并点击鼠标左键后释放鼠标
     *
     * @param x 指定位置的x偏移量
     * @param y 指定位置的y偏移量
     */
    public void moveToClick(int x, int y) {
            for (int i = 0; i <= 10; i++) {
                rb.mouseMove(x, y);
            }
            rb.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
            rb.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
            rb.delay(500);
            //调试定位位置
//            System.out.println(MouseInfo.getPointerInfo().getLocation().getX() + " , "
//
//                    + MouseInfo.getPointerInfo().getLocation().getY());
    }

}
