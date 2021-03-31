package com.testing.class16;

import com.testing.common.AutoLogger;
import com.testing.common.AutoTools;
import com.testing.common.ExcelReader;
import com.testing.common.ExcelWriter;
import com.testing.inter.DDTOfInter;

import java.util.List;

/**
 * @Classname InterDDTest
 * @Description 类型说明
 * @Date 2021/3/29 20:27
 * @Created by 测试园
 */
public class InterDDTest {
    public static void main(String[] args) {
        ExcelReader cases=new ExcelReader("Cases/InterCases.xlsx");
        ExcelWriter results=new ExcelWriter("Cases/InterCases.xlsx","Cases/Result/resultOfInter"+AutoTools.createTime("MMdd-HH：mm：ss"));

        DDTOfInter inter=new DDTOfInter(results);
        //遍历sheet页
        for(int sheetNo=0;sheetNo<cases.getTotalSheetNo();sheetNo++){
            cases.useSheetByIndex(sheetNo);
            results.useSheetByIndex(sheetNo);
            AutoLogger.log.info("当前的sheet页是:"+cases.getSheetName(sheetNo));
            //遍历每一行
            for(int rowNo=0;rowNo<cases.getRowNo();rowNo++){
                //读取每一行的内容
                List<String> rowContent = cases.readLine(rowNo);
                //指定当前执行的操作行，以完成对于改行结果的写入
                inter.setLine(rowNo);
                //根据每一行中的关键字列，和断言列来进行关键字的调用和参数的传入
                //关键字调用
                AutoTools.invokeI(inter,rowContent,3);
                //断言
                AutoTools.invokeI(inter,rowContent,7);
            }
        }
        cases.close();
        results.save();
    }
}
