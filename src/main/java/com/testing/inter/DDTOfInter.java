package com.testing.inter;

import com.alibaba.fastjson.JSONPath;
import com.testing.common.AutoLogger;
import com.testing.common.Encrypt;
import com.testing.common.ExcelWriter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname InterKw
 * @Description 类型说明
 * @Date 2021/3/26 18:42
 * @Created by 测试园
 */
public class DDTOfInter {
    //完成结果写入excel文件的writer对象
    ExcelWriter results;

    public Map<String,String> paramMap;
//    public Map<String,String> paramMap=new HashMap<>();

    //由于返回结果是所有方法都要使用的，所以设为成员变量
    public static String responseResult;

    //成员变量httpClientUtils
    public HttpClientUtils httpClientUtils;
//    public HttpClientUtils httpClientUtils=new HttpClientUtils();

    //定义常量PASS和FAIL
    public static final String PASS="pass";
    public static final String FAIL="fail";
    public static final  int RESULT_COL=10;
    public static final  int ACTUAL_COL=11;

    //当前操作的行号
    public  int line;

    //设置行号的方法
    public void setLine(int nowLine){
        line=nowLine;
    }

    //构造方法，完成实例化
    public  DDTOfInter(ExcelWriter result){
        paramMap=new HashMap<>();
        httpClientUtils=new HttpClientUtils();
        results=result;
    }
    //成功时候的写入操作
    public void setPass(){
        //写入实际返回结果
        results.writeCell(line, ACTUAL_COL, responseResult);
        results.writeCell(line, RESULT_COL, PASS);
    }
    //失败时候的写入操作
    public void setFail(Exception e){
        //失败的时候，在日志中记录异常信息
        AutoLogger.log.error(e,e.fillInStackTrace());
        //往结果文件写入实际返回值和执行结果
        results.writeFailCell(line, ACTUAL_COL, responseResult);
        results.writeFailCell(line, RESULT_COL, FAIL);
    }
    //无异常报错的执行失败
    public void setFail(){
        //往结果文件写入实际返回值和执行结果
        results.writeFailCell(line, ACTUAL_COL, responseResult);
        results.writeFailCell(line, RESULT_COL, FAIL);
    }


    //有可能牵涉到使用参数，所以先进行替换
    public void addHeader(String headerJson){
        try {
            //先替换变量参数
            headerJson=useParam(headerJson);
            httpClientUtils.addHeader(headerJson);
            setPass();
        } catch (Exception e) {
            setFail(e);
        }
    }

    //将字符串中的变量名替换为它在parammap中存储的变量值
    public  String useParam(String origin){

            //遍历parammap里面的每个键，在origin字符串中找到{key}替换它
            for (String key : paramMap.keySet()) {
                //替换的键的格式是{key}，replaceall用的是正则，所以要转义
                //每一轮循环，都会替换这一轮的key键值对，替换完之后，把原字符串修改完再进行下一次的循环
                origin = origin.replaceAll("\\{"+key+"\\}", paramMap.get(key));
            }
            setPass();
            return origin;
    }

    //dopost请求，得到返回结果
    public void doPostUrl(String url, String param) {
        try {
            //可能含有变量，先替换
            url = useParam(url);
            param = useParam(param);
            responseResult = httpClientUtils.doPostUrl(url, param);
            AutoLogger.log.info(responseResult);
            setPass();
        } catch (Exception e) {
            setFail(e);
        }
    }
    //soap接口,执行返回
    public void doPostSoap(String url, String param) {
        try {
            //可能含有变量，先替换
            url = useParam(url);
            param = useParam(param);
            responseResult = httpClientUtils.doPostSoap(url, param);
            AutoLogger.log.info(responseResult);
            setPass();
        } catch (Exception e) {
            setFail(e);
        }
    }

    //把返回结果，存储到参数map中
    public  void saveParam(String paramKey,String jsonPath){
        try {
            String paramValue = JSONPath.read(responseResult, jsonPath).toString();
            paramMap.put(paramKey,paramValue);
            setPass();
        } catch (Exception e) {
            setFail(e);
        }
    }

    //进行加密，得到加密之后的密码字符串，存到paramMap中使用
    public void saveEncPwd(String paramKey,String originPwd){
        try {
            //创建加密对象
            Encrypt enc=new Encrypt();
            //对原始的密码进行加密，存储为param的值
            String paramValue = enc.enCrypt(useParam(originPwd));
            paramMap.put(paramKey,paramValue);
            setPass();
        } catch (Exception e) {
            setFail(e);
        }
    }
    //生成随机的用户名，这里使用时间戳
    public void saveRandomParam(String paramKey,String originString){
        //基于时间内容，拼接一个时间戳
        Date now=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("HHmmss");
        String timeStamp = sdf.format(now);
        String paramValue=originString+timeStamp;
        //将参数存储到paramMap中
        paramMap.put(paramKey,paramValue);
    }
    //存储固定的之后要用的参数
    public void saveConstant(String paramKey,String paramValue){
        try {
            paramMap.put(paramKey,paramValue);
            setPass();
        } catch (Exception e) {
            setFail(e);
        }
    }

    /**
     * 断言返回结果通过正则表达式获取结果是否符合预期
     * @param regex 断言时使用的正则表达式
     * @param expect 预期结果
     * @return
     */
    public  boolean assertRegexEq(String regex,String expect){
        //正则表达式找出结果
        Pattern p=Pattern.compile(regex);
        Matcher m=p.matcher(responseResult);
        String result="";
        if(m.find()){
            result= m.group(1);
        }
        AutoLogger.log.info("提取后的结果"+result);
        if(expect.equals(result)){
            AutoLogger.log.info("pass");
            setPass();
            return true;
        }
        else{
            setFail();
            return false;
        }

    }

    /**
     * 通过jsonpath断言json解析结果是否等于预期
     * @param jsonpath jsonpath表达式
     * @param expect    预期结果
     * @return
     */
    public  boolean jsonValueCheck(String jsonpath,String expect){
        String jsonValue = JSONPath.read(responseResult, jsonpath).toString();
        if(expect.equals(jsonValue)){
            AutoLogger.log.info("解析结果是"+jsonValue);
            setPass();
            return true;
        }
        else {
            setFail();
            return false;
        }

    }
    /**
     * 通过jsonpath断言json解析结果是否被包含在预期中
     * @param jsonpath jsonpath表达式
     * @param expect    预期结果
     * @return
     */
    public  boolean assertJsonContains(String jsonpath,String expect){
        String jsonValue = JSONPath.read(responseResult, jsonpath).toString();
        if(expect.contains(jsonValue)){
            AutoLogger.log.info("解析结果是"+jsonValue);
            setPass();
            return true;
        }
        else {
            setFail();
            return false;
        }
    }
    /**
     * 清空headerMap的值
     */
    public void clearHeader(){
        //重新实例化，将headerMap清空
        httpClientUtils.clearHeader();
        setPass();
    }
}
