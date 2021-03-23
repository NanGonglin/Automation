package com.testing.web;

import java.sql.*;
import java.util.*;

/**
 * @Classname MysqlUtils
 * @Description 类型说明
 * @Date 2021/3/5 17:09
 * @Created by 小白sy
 */
public class MysqlUtils {
    //连接的成员变量，后面的方法都会使用这个创建的连接
    private Connection myconnector;

    //建立连接
    public void creatConnector() {
        try {
            Properties sy=new Properties();
            //读取resources中的文件
            sy.load(com.testing.web.MysqlUtils.class.getResourceAsStream("/shopdb.properties"));

            String classname=sy.getProperty("driverclass");
            String jdbcurl=sy.getProperty("jdbcurl");
            String username=sy.getProperty("username");
            String password=sy.getProperty("password");
            Class.forName(classname);
            myconnector=DriverManager.getConnection(jdbcurl, username,password);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("mysql数据库连接失败");
        }
    }

    /**
     * 通过user和pwd在数据库中查询，将查询到的结果先保存到map,再插入到list中
     *
     * @param sql 要查询的sql语句
     * @return 返回数据类型是List<Map<String, String>>
     * @throws SQLException
     */
    public List<Map<String, String>> queryResult(String sql) {
        try {
            //3、创建查询
            Statement roystatement = myconnector.createStatement();

            //4、执行查询
            ResultSet resultSet = roystatement.executeQuery(sql);

            //获取表头信息，方便遍历每一列的内容
            ResultSetMetaData thead = resultSet.getMetaData();

            List<Map<String, String>> resultlist = new ArrayList<>();
            //如果查询到多个结果，用while查询
            while (resultSet.next()) {
                //存储到map中
                Map<String, String> resultmap = new HashMap<>();
                for (int column = 1; column <= thead.getColumnCount(); column++) {
                    resultmap.put(thead.getColumnName(column), resultSet.getString(column));
    //                System.out.println("第" + column + "列的字段名是" + thead.getColumnName(column) + ",字段值是" + resultSet.getString(column));

                }
    //            System.out.println(resultmap);
                resultlist.add(resultmap);
            }
            return resultlist;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}
