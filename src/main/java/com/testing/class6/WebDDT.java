package com.testing.class6;

import com.testing.common.ExcelReader;
import com.testing.common.ExcelWriter;
import com.testing.shop.ShopWebKeyWord;

import java.util.List;

/**
 * @Classname WebDDT
 * @Description 类型说明
 * @Date 2021/3/24 18:43
 * @Created by 测试园
 */
public class WebDDT {
    public static final int KEYCOL=3;
    public static final int RESULTCOL=10;
    public static void main(String[] args) {
        ShopWebKeyWord web=new ShopWebKeyWord();
        ExcelReader casefile=new ExcelReader("Cases/WebCases.xlsx");
        ExcelWriter resultfile=new ExcelWriter("Cases/WebCases.xlsx","Cases/Result/resultOfWeb"+web.createTime("MMdd+HH：mm：ss"));
        for(int sheetNo=0;sheetNo<casefile.getTotalSheetNo();sheetNo++){
//            System.out.println(casefile.getSheetName(sheetNo));
            //指定使用当前的sheet页
            casefile.useSheetByIndex(sheetNo);
            resultfile.useSheetByIndex(sheetNo);
            System.out.println("当前的sheet页是"+casefile.getSheetName(sheetNo));
            for(int rowNo=0;rowNo<casefile.getRowNo();rowNo++){
                //每一行读取对应得内容
                List<String> rowContent=casefile.readLine(rowNo);
//                System.out.println(rowContent);
                //基于每行内容，执行测试用例
                switch (rowContent.get(KEYCOL)){
                    case "openBrowser":
                        web.openChromeBrowser();
                        resultfile.writeCell(rowNo, RESULTCOL, "PASS");
                        break;
                    case "visitWeb":
                        boolean url = web.visitURL(rowContent.get(KEYCOL + 1));
                        if (url) {
                            resultfile.writeFailCell(rowNo, RESULTCOL, "PASS");
                        } else {
                            resultfile.writeFailCell(rowNo, RESULTCOL, "FAIL");
                        }
                        resultfile.writeCell(rowNo, RESULTCOL, "PASS");
                        break;
                    case "input":
                        boolean input = web.input(rowContent.get(KEYCOL + 1), rowContent.get(KEYCOL + 2));
                        if (input) {
                            resultfile.writeFailCell(rowNo, RESULTCOL, "PASS");
                        } else {
                            resultfile.writeFailCell(rowNo, RESULTCOL, "FAIL");
                        }
                        resultfile.writeCell(rowNo, RESULTCOL, "PASS");
                        break;
                    case "click":
                        boolean click = web.click(rowContent.get(KEYCOL + 1));
                        if (click) {
                            resultfile.writeFailCell(rowNo, RESULTCOL, "PASS");
                        } else {
                            resultfile.writeFailCell(rowNo, RESULTCOL, "FAIL");
                        }
                        resultfile.writeCell(rowNo, RESULTCOL, "PASS");
                        break;
                    case "switchIframebyele":
                    case "intoIframe":
                        boolean iframe = web.switchIframe(rowContent.get(KEYCOL + 1));
                        if (iframe) {
                            resultfile.writeFailCell(rowNo, RESULTCOL, "PASS");
                        } else {
                            resultfile.writeFailCell(rowNo, RESULTCOL, "FAIL");
                        }
                        resultfile.writeCell(rowNo, RESULTCOL, "PASS");
                        break;
                    case "halt":
                        web.halt(rowContent.get(KEYCOL+1));
                        resultfile.writeCell(rowNo, RESULTCOL, "PASS");
                        break;
                    case "selectByValue":
                        web.select(rowContent.get(KEYCOL+1), rowContent.get(KEYCOL+2), rowContent.get(KEYCOL+3));
                        resultfile.writeCell(rowNo, RESULTCOL, "PASS");
                        break;
                    case "assertEleTextContains":
                        boolean assertText = web.assertText(rowContent.get(KEYCOL + 1), rowContent.get(KEYCOL + 2), rowContent.get(KEYCOL + 3));
                        if(assertText){
                            resultfile.writeCell(rowNo, RESULTCOL, "PASS");
                        }else{
                            resultfile.writeFailCell(rowNo, RESULTCOL, "FAIL");
                        }
                        break;
                    case "closeBrowser":
                        web.closeBrowser();
                        resultfile.writeCell(rowNo, RESULTCOL, "PASS");
                        break;

                }
            }
        }
        //释放资源，保存结果文件
        resultfile.save();
        casefile.close();
    }
}
