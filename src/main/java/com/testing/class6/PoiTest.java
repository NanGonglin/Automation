package com.testing.class6;

import com.testing.shop.ShopWebKeyWord;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname PoiTest
 * @Description 类型说明
 * @Date 2021/3/23 23:38
 * @Created by 小白sy
 */
public class PoiTest {
    public static  final  int  KEYCOL=3;
    public static void main(String[] args) throws IOException, InvalidFormatException {

        ShopWebKeyWord web=new ShopWebKeyWord();
        //打开测试用例文件
        File excel=new File("Cases/WebCases.xlsx");
        //通过文件输入流，来打开excel文件
        FileInputStream inputStream=new FileInputStream(excel);
        //直接用文件，作为参数实例化workbook
        Workbook workbook=new XSSFWorkbook(excel);
        System.out.println(workbook);
        System.out.println(workbook.getSheetName(0));
        System.out.println(workbook.getSheetName(1));
        //调用超出范围的sheet,会报错
//        System.out.println(workbook.getSheetName(2));
        //读取excel中所有行的内容
        //1.遍历sheet输出
        for(int sheetNo=0;sheetNo<workbook.getNumberOfSheets();sheetNo++){
            System.out.println("当前打开的sheet页是"+workbook.getSheetName(sheetNo));
            //使用当前的sheet页
            Sheet nowSheet = workbook.getSheetAt(sheetNo);
            System.out.println(nowSheet);
//            //读取一行的操作
//            Row row = nowSheet.getRow(4);
//            System.out.println(row);
//            //读取一个单元格的操作
//            Cell cell = row.getCell(4);
//            System.out.println(cell.toString());
//

            //2.读取所有行
            for(int rowNo=0;rowNo<nowSheet.getPhysicalNumberOfRows();rowNo++){
                //存储每行的内容
                List<String> rowContent=new ArrayList<>();
                Row nowRow=nowSheet.getRow(rowNo);
                //3.遍历一行中的所有单元格
                for(int colNo=0;colNo<nowRow.getPhysicalNumberOfCells();colNo++){
                    Cell cell = nowRow.getCell(colNo);
                    String cellValue="";
                    if(cell.getCellTypeEnum().equals(CellType.NUMERIC)){
                        cellValue=(long)(cell.getNumericCellValue())+"";
                    }else{
                        cellValue=cell.getStringCellValue();
                    }


                    //13800138006会变成科学计数法，所以数字需要处理一下
//                    rowContent.add(cell.toString()+"格式是"+cell.getCellTypeEnum());
                    rowContent.add(cellValue);
                }
                System.out.println("第"+rowNo+"行的内容是"+rowContent);
                //调用用例执行:基于关键字列进行判断，调用对应的方法
                //如果不加break,
                switch (rowContent.get(KEYCOL)){
                    case "openBrowser":
                        web.openChromeBrowser();
                        break;
                    case "visitWeb":
                        web.visitURL(rowContent.get(KEYCOL+1));
                        break;
                    case "input":
                        web.input(rowContent.get(KEYCOL+1),rowContent.get(KEYCOL+2));
                        break;
                    case "click":
                        web.click(rowContent.get(KEYCOL+1));
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
    }
}
