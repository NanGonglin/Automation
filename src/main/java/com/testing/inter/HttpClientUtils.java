package com.testing.inter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.testing.class11.UnicodeDecode;
import com.testing.common.AutoLogger;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname HttpClientUtils
 * @Description 类型说明
 * @Date 2021/3/26 14:06
 * @Created by 测试园
 */
public class HttpClientUtils {
    //client对象，用于发包
    public CloseableHttpClient client;
    //成员变量cookiestore，用于存储cookie，并且方便进行client之间的传递
    BasicCookieStore cookieStore=new BasicCookieStore();
    //成员变量，用于决定是否使用cookiestore
    boolean isUseCookie=true;

    /**
     * unicode转码
     * @param origin 需要转码的字符串
     * @return
     */
    public static String unicodeDecode(String origin){
        //找到字符串中的u4位16进制数的格式，进行匹配
        //使用正则表达式进行查找匹配
        //由于替换的时候只需要用到4位数字，所以加上()表示是一个分组，进行获取
        Pattern uni=Pattern.compile("\\\\u([0-9a-zA-Z]{4})");
        Matcher m= uni.matcher(origin);
        //创建stringbuffer对象，声明长度为原本的字符串长度
        StringBuffer stringBuffer=new StringBuffer(origin.length());
        while (m.find()){
            m.appendReplacement(stringBuffer, Character.toString((char)Integer.parseInt(m.group(1),16)));
        }
        m.appendTail(stringBuffer);
        return stringBuffer.toString() ;
    }

    /**
     * 创建client,把cookie装到cookiestore
     * @return
     */
    public CloseableHttpClient creatCookieclient(){
        client=HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        return client;
    }

    /**
     * 创建client,不使用已保存的cookie
     * @return
     */
    public CloseableHttpClient creatNocookieClient(){
        client=HttpClients.custom().build();
        return client;
    }

    //创建client的方法，根据是否使用cookie决定创建client对象时要不要带cookie
    public CloseableHttpClient creatClient(){
       if(isUseCookie){
           client=creatCookieclient();
       }else{
           client=creatNocookieClient();
       }
       return client;
    }

    //get
    public String doGet(String uri,String useCookie){
        //基于输入参数，决定是否调用cookie
        if(useCookie.equals("use")){
            creatCookieclient();
        }
        else{
            creatNocookieClient();
        }
        HttpGet get=null;
        get=new HttpGet(uri);
        try {
            CloseableHttpResponse response=client.execute(get);
            String result = EntityUtils.toString(response.getEntity(), "UTF-8");
            //unicode转码
            result= UnicodeDecode.unicodeDecode(result);
            return result;
        } catch (IOException e) {
            //报错信息作为返回结果
            AutoLogger.log.error(e,e.fillInStackTrace());
            return e.fillInStackTrace().toString();
        }

    }
    //get
    public String doGet(String uri){
        //基于输入参数，决定是否调用cookie
        HttpGet get=null;
        get=new HttpGet(uri);
        try {
            CloseableHttpResponse response=client.execute(get);
            String result = EntityUtils.toString(response.getEntity(), "UTF-8");
            //unicode转码
            result= UnicodeDecode.unicodeDecode(result);
            return result;
        } catch (IOException e) {
            //报错信息作为返回结果
            AutoLogger.log.error(e,e.fillInStackTrace());
            return e.fillInStackTrace().toString();
        }
    }

    //post
    //输入参数，来指定不同的contentType
    public String doPost(String uri,String param,String contentType){
        creatClient();
        HttpPost post=new HttpPost(uri);
        try {
            HttpEntity postparam=null;
            switch (contentType){
                case "urlencoded":
                    StringEntity urlen=new StringEntity(param);
                    urlen.setContentEncoding("UTF-8");
                    urlen.setContentType("application/x-www-form-urlencoded");
                    //向上转型
                    postparam=urlen;
                    break;
                case "json":
                    StringEntity jsonEn=new StringEntity(param);
                    jsonEn.setContentEncoding("UTF-8");
                    jsonEn.setContentType("application/json");
                    //向上转型
                    postparam=jsonEn;
                    break;
                case "file":
                    //基于设计好的规则，解析json中的内容{"text":{"键":"值"},"bin":{"键":"值"}}
                    MultipartEntityBuilder meb=MultipartEntityBuilder.create();

                    JSONObject jsonObject= JSON.parseObject(param);
                    //如果text不为空，则遍历解析所有text的内容，进行添加
                    if(jsonObject.get("text")!=null){
                        for(String key:jsonObject.getJSONObject("text").keySet()){
                            //遍历text的每一个键值对，添加为textbody
                            meb.addTextBody(key, jsonObject.getJSONObject("text").getString(key));
                        }
                    }
                    //如果bin不为空，则解析所有bin的值
                    if(jsonObject.get("bin")!=null){
                        for(String key:jsonObject.getJSONObject("bin").keySet()){
                            meb.addBinaryBody(key,new File(jsonObject.getJSONObject("bin").getString(key)));
                        }
                    }
                    postparam=meb.build();
                    break;
            }
            post.setEntity(postparam);
            CloseableHttpResponse response = client.execute(post);
            String result = EntityUtils.toString(response.getEntity(), "UTF-8");
            result=UnicodeDecode.unicodeDecode(result);
            return result;
        } catch (IOException e) {
            //报错信息作为返回结果
            AutoLogger.log.error(e,e.fillInStackTrace());
            return e.fillInStackTrace().toString();
        }
    }


    //执行www-form-encoding格式的发包


    //json格式发包

    //文件上传multipart/form-data

    //usecookie方法，将isusecookie字段置为true,从而在创建client的时候知道如何创建
    public void usecookie(){
        isUseCookie=true;
    }
    public void notusecookie(){
        isUseCookie=false;
    }

    public void clearcookie(){
        isUseCookie=false;
        //通过重新实例化，完成清理之前的cookie操作
        cookieStore=new BasicCookieStore();
    }
    public CloseableHttpClient createclient(){
        client=HttpClients.createDefault();
        return client;
    }


}
