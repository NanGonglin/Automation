package com.testing.class18;

import com.testing.common.ExcelReader;
import com.testing.common.ExcelWriter;
import com.testing.web.DDTofWeb;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Classname LoginTestWithMultiData
 * @Description 类型说明
 * @Date 2021/4/1 12:30
 * @Created by 测试园
 */
public class LoginTestWithMultiData {
    public DDTofWeb web;
    public ExcelReader caseExcel;
    public ExcelWriter resultExcel;


    @Test(dataProvider = "dp")
    public void f(String rowNo,String loginName,String passWord,String verifyCode){
        System.out.println(rowNo+loginName+passWord+verifyCode);
        int row=Integer.parseInt(rowNo);

        web.setLine(row);
        web.openChromeBrowser();
        web.visitURL("http://www.testingedu.com.cn:8000/Home/user/login.html");
        web.halt("3");
        web.input("//input[@id='username']",loginName);
        web.input("//input[@id='password']",passWord);
        web.input("//input[@id='verify_code']",verifyCode);
        web.click("//a[@name='sbtbutton']");
        web.closeBrowser();
    }

    @BeforeSuite
    public void initiallize(){
        //设置当前的日期保存成为excel结果文件的名字
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd+HH-mm-ss");
        String createdate=sdf.format(date);
        String rootpath=System.getProperty("user.dir");
        caseExcel =new ExcelReader(rootpath+"\\Cases\\TestNGLoginTest.xlsx");
        resultExcel =new ExcelWriter(rootpath+"\\Cases\\TestNGLoginTest.xlsx", rootpath+"\\Cases\\result-"+createdate+"TestNGLoginTest");
        web=new DDTofWeb(resultExcel);
    }
    @AfterSuite
    public void afterMethod(){
        caseExcel.close();
        resultExcel.save();
    }

    //把excel文件读取成功二维数组类型
    @DataProvider
    public Object[][] dp(){
//        for(Object[] ob:caseExcel.readAsMatrix()){
//            System.out.println("一行的内容是");
//            for (Object o : ob) {
//                System.out.println(o+" ");
//            }
//        }
        return caseExcel.readAsMatrix();
    }
}
