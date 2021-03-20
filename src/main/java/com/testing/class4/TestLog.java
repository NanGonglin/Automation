package com.testing.class4;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * @Classname TestLog
 * @Description 类型说明
 * @Date 2021/3/19 13:12
 * @Created by 小白sy
 */
public class TestLog {
    public static void main(String[] args) {
        Logger log= LogManager.getLogger(TestLog.class);
        log.trace("这是trace信息");
        log.debug("debug信息");
        log.info("比debug高的info级别");
        log.warn("警告级别");
        log.error("严重级别");
        log.fatal("致命级别");

    }
}
