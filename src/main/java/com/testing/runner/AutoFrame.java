package com.testing.runner;

import com.testing.common.*;
import com.testing.inter.DDTOfInter;
import com.testing.web.DDTofWeb;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @Classname AutoFrame
 * @Description 将web、app、接口测试集成到一个类中，通过输入参数来进行切换
 * @Date 2021/3/30 12:43
 * @Created by 测试园
 */
public class AutoFrame {

    //需要用到的关键字对象
    public static Object keyword;
    public static DDTOfInter inter;
    public static DDTofWeb web;
    //成员变量，指定要读写的文件位置
    public static ExcelReader casefile;
    public static ExcelWriter resultfile;


    public static void main(String[] args) {

        String type="web";

        //获取当前运行所在的路径,也就是运行类或者jar所在的绝对路径，比如：F:\Auto
        String runPath=System.getProperty("user.dir");
        //文件的名称前缀文件夹路径
        String casefilePath=runPath+"/Cases/";
        String resultfilePath=runPath+"/Cases/Result/";

        //执行时间
        String time= AutoTools.createTime("yyMMdd-HHmmss");
        String starttime=AutoTools.createTime("yyyy-MM-dd HH:mm:ss");

        //基于类型来完成excel文件的实例化操作，以及对应类型关键字的实例化
        switch (type){
            case "http":
                casefilePath+="InterCases.xlsx";
                resultfilePath+="resultofInter-"+time;
                casefile=new ExcelReader(casefilePath);
                resultfile=new ExcelWriter(casefilePath, resultfilePath);
                inter=new DDTOfInter(resultfile);
                keyword=inter;
                break;
            case "web":
                casefilePath+="WebCases.xlsx";
                resultfilePath+="resultofWeb-"+time;
                casefile=new ExcelReader(casefilePath);
                resultfile=new ExcelWriter(casefilePath, resultfilePath);
                web=new DDTofWeb(resultfile);
                keyword=web;
                break;
            default:
                AutoLogger.log.error("输入自动化测试类型错误！");
        }
        //执行测试
        execCase();
        //发送邮件
        Report.sendreport(resultfilePath+".xlsx", starttime);
    }
    public static void execCase(){
        //遍历sheet页
        for(int sheetNo=0;sheetNo<casefile.getTotalSheetNo();sheetNo++){
            casefile.useSheetByIndex(sheetNo);
            resultfile.useSheetByIndex(sheetNo);
            AutoLogger.log.info("当前的sheet页是:"+casefile.getSheetName(sheetNo));
            //遍历每一行
            for(int rowNo=0;rowNo<casefile.getRowNo();rowNo++){
                //读取每一行的内容
                List<String> rowContent = casefile.readLine(rowNo);
                //通过反射完成查找对应类型，修改当前执行的操作行，以完成对于改行结果的写入
                try {
                    Method setLine = keyword.getClass().getDeclaredMethod("setLine", int.class);
                    setLine.invoke(keyword,rowNo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //根据每一行中的关键字列，和断言列来进行关键字的调用和参数的传入
                //关键字调用
                AutoTools.invokeI(keyword,rowContent,3);
                //断言
                AutoTools.invokeI(keyword,rowContent,7);
            }
        }
        casefile.close();
        resultfile.save();
    }

}
