package com.testing.class12;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname IpGetTest
 * @Description 类型说明
 * @Date 2021/3/25 15:23
 * @Created by 测试园
 */
public class IpjsonParse {
    public static void main(String[] args) {
        //创建httpclient对象，作为发送请求的客户端
        CloseableHttpClient sy= HttpClients.createDefault();

        //拼接请求内容
        //四大要素：http请求方法、url、请求头、请求体
        HttpGet ipget=new HttpGet("https://www.baidu.com/sugrec?pre=1&p=3&ie=utf-8&json=1&prod=pc&from=pc_web&sugsid=33256,33344,31660,33392,33695,33714,26350&wd=i&req=2&csor=1&cb=jQuery11020016567752707456584_1616729416184&_=1616729416185");
        try {
            CloseableHttpResponse ipresponse=sy.execute(ipget);
            //返回包输出
            System.out.println(ipresponse);

            HttpEntity ipentity = ipresponse.getEntity();
            System.out.println("状态码是"+ipresponse.getStatusLine().getStatusCode());

            System.out.println("返回体是"+ipentity);

            String ipresult = EntityUtils.toString(ipentity, "UTF-8");
            System.out.println("返回结果是"+ipresult);
            //转换unicode编码
//            ipresult=UnicodeDecode.unicodeDecode(ipresult);

//            //断言，对返回体字符串进行处理
//            if(ipresult.contains("美国")){
//                System.out.println("测试成功");
//            }else{
//                System.out.println("测试失败");
//            }
//            //1.利用正则表达式取出json的内容,再用fastjson逐层解析获取g的值
            Pattern p=Pattern.compile("\\((.*?)\\)");
            Matcher m=p.matcher(ipresult);
            System.out.println(m.find());
            System.out.println(m.group(1));
            String ipjson = m.group(1);

            //2.1获取结果信息中g键对应得值
            JSONObject jsonObject= JSON.parseObject(ipjson);
            //获取第一层，也就是result,是一个数组
            JSONArray result = jsonObject.getJSONArray("g");
            //数组按下标获取元素
            JSONObject resultjson = result.getJSONObject(0);
            String q = resultjson.get("q").toString();
            System.out.println("fastjson逐层解析获取g下面第一个q的值是"+q);

            //2.2正则表达式直接获取g键的值
            Pattern gp=Pattern.compile(",\"q\":\"(.*?)\"\\},");
            Matcher gm=gp.matcher(ipresult);
            gm.find();
            System.out.println("通过正则表达式直接获取g下面第一个q的值是"+gm.group(1));


            //2.3.jsonpath解析:也要基于字符串是个标准格式
            String jsonpath = JSONPath.read(ipjson, "$.g[0].q").toString();
            System.out.println("通过jsonpath获取g下面第一个q值是"+jsonpath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
