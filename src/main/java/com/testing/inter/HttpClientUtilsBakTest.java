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
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname HttpClientUtils
 * @Description 类型说明
 * @Date 2021/3/26 14:06
 * @Created by 测试园
 */
public class HttpClientUtilsBakTest {
    //client对象，用于发包
    public CloseableHttpClient client;
    public  HttpPost clientpost;
    //成员变量，用于存储cookie
    Header[] cookies=null;

    //实例化client的方法
    public CloseableHttpClient creatclient(){
        client= HttpClients.createDefault();
        return client;
    }

    //get
    public String doGet(String uri){
        HttpGet clientget=new HttpGet(uri);
        try {
            if(cookies!=null){
                for (Header cookie : cookies) {
                    clientget.setHeader("Cookie",cookie.getValue());
                }
            }

            CloseableHttpResponse response=client.execute(clientget);
            //从返回包中获取头域，set-cookie
            cookies = response.getHeaders("Set-Cookie");
            for(Header cookie:cookies){
                AutoLogger.log.info("cookie的键是"+cookie.getName()+"cookie的值是"+cookie.getValue());
            }

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
        clientpost=new HttpPost(uri);
        try {
            if(cookies!=null){
                for (Header cookie : cookies) {
                    clientpost.addHeader("Cookie",cookie.getValue());
                }
            }
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
            for(Header cookie:cookies){
                AutoLogger.log.info("cookie的键是"+cookie.getName()+"cookie的值是"+cookie.getValue());
            }
            clientpost.setEntity(postparam);
            CloseableHttpResponse response = client.execute(clientpost);
//            cookies = response.getHeaders("Set-Cookie");
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
    public void encoding(String type){
        clientpost.setHeader("Content-Type",type);
    }

    //json格式发包
    public void json(String type){
        clientpost.setHeader("Content-Type",type);
    }
    //文件上传multipart/form-data
    public void multipart(String type){

    }

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
}
