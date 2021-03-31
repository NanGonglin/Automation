package com.testing.class6;

import com.testing.common.ExcelReader;
import com.testing.common.ExcelWriter;
import com.testing.shop.ShopWebKeyWord;
import com.testing.web.DDTofWeb;

import java.util.List;

/**
 * @Classname DDTwithWriter
 * @Description 类型说明
 * @Date 2021/3/24 21:57
 * @Created by 测试园
 */
public class DDTwithWriter {
    public static final int KEYCOL=3;
    public static void main(String[] args) {
        ShopWebKeyWord tool=new ShopWebKeyWord();
        ExcelReader casefile=new ExcelReader("Cases/WebCases.xlsx");
        ExcelWriter resultfile=new ExcelWriter("Cases/WebCases.xlsx","Cases/Result/resultOfWeb"+tool.createTime("MMdd+HH：mm：ss"));
        DDTofWeb web=new DDTofWeb(resultfile);
        for(int sheetNo=0;sheetNo<casefile.getTotalSheetNo();sheetNo++){
//            System.out.println(casefile.getSheetName(sheetNo));
            //指定使用当前的sheet页
            casefile.useSheetByIndex(sheetNo);
            resultfile.useSheetByIndex(sheetNo);
            System.out.println("当前的sheet页是"+casefile.getSheetName(sheetNo));
            for(int rowNo=0;rowNo<casefile.getRowNo();rowNo++){
                //每一行读取对应得内容
                List<String> rowContent=casefile.readLine(rowNo);
                //设置当前的行号，指定关键字写入的地方
                web.setLine(rowNo);

                //基于每行内容，执行测试用例
                switch (rowContent.get(KEYCOL)){
                    case "openBrowser":
                        web.openChromeBrowser();
                        break;
                    case "visitWeb":
                        boolean url = web.visitURL(rowContent.get(KEYCOL + 1));
                        break;
                    case "input":
                        boolean input = web.input(rowContent.get(KEYCOL + 1), rowContent.get(KEYCOL + 2));
                        break;
                    case "click":
                        boolean click = web.click(rowContent.get(KEYCOL + 1));
                        break;
                    case "switchIframebyele":
                    case "intoIframe":
                        boolean iframe = web.switchIframe(rowContent.get(KEYCOL + 1));
                        break;
                    case "halt":
                        web.halt(rowContent.get(KEYCOL+1));
                        break;
                    case "selectByValue":
                        web.select(rowContent.get(KEYCOL+1), rowContent.get(KEYCOL+2), rowContent.get(KEYCOL+3));
                        break;
                    case "assertEleTextContains":
                        boolean assertText = web.assertText(rowContent.get(KEYCOL + 1), rowContent.get(KEYCOL + 2), rowContent.get(KEYCOL + 3));
                        break;
                    case "closeBrowser":
                        web.closeBrowser();
                        break;

                }

            }
        }
        //释放资源，保存结果文件
        resultfile.save();
        casefile.close();
    }
}
