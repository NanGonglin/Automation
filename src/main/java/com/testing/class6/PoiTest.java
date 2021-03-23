package com.testing.class6;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Classname PoiTest
 * @Description 类型说明
 * @Date 2021/3/23 23:38
 * @Created by 小白sy
 */
public class PoiTest {
    public static void main(String[] args) throws IOException, InvalidFormatException {
        //打开测试用例文件
        File excel=new File("Cases/WebCases.xlsx");
        //通过文件输入流，来打开excel文件
        FileInputStream inputStream=new FileInputStream(excel);
        //直接用文件，作为参数实例化workbook
        Workbook workbook=new XSSFWorkbook(excel);
    }
}
