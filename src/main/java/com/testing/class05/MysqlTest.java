package com.testing.class05;

import com.testing.web.MysqlUtils;

import java.sql.SQLException;

/**
 * @Classname MysqlTest
 * @Description 类型说明
 * @Date 2021/3/22 19:57
 * @Created by 小白sy
 */
public class MysqlTest {
    public static void main(String[] args) throws SQLException {
        MysqlUtils mysql=new MysqlUtils();
        mysql.creatConnector();
        System.out.println(mysql.queryResult("select goods_name,goods_id,shop_price from tp_goods where goods_name ='小米净水器西北区'"));
    }
}
