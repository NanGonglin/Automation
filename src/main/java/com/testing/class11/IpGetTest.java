package com.testing.class11;

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
import org.apache.xmlbeans.impl.piccolo.xml.Entity;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname IpGetTest
 * @Description 类型说明
 * @Date 2021/3/25 15:23
 * @Created by 测试园
 */
public class IpGetTest {
    public static void main(String[] args) {
        //创建httpclient对象，作为发送请求的客户端
        CloseableHttpClient sy= HttpClients.createDefault();

        //拼接请求内容
        //四大要素：http请求方法、url、请求头、请求体
        HttpGet ipget=new HttpGet("https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?query=3.3.3.3&co=&resource_id=5809&t=1612267942405&ie=utf8&oe=gbk&cb=op_aladdin_callback&format=json&tn=baidu&cb=jQuery110208873521310280592_1612267928253&_=1612267928259");
        try {
            CloseableHttpResponse ipresponse=sy.execute(ipget);
            //返回包输出
            System.out.println(ipresponse);

            HttpEntity ipentity = ipresponse.getEntity();
            System.out.println("状态码是"+ipresponse.getStatusLine().getStatusCode());

            System.out.println("返回体是"+ipentity);

            String ipresult = EntityUtils.toString(ipentity, "UTF-8");

            //转换unicode编码
            ipresult=UnicodeDecode.unicodeDecode(ipresult);
            System.out.println("转码后返回结果是"+ipresult);

//            //断言，对返回体字符串进行处理
//            if(ipresult.contains("美国")){
//                System.out.println("测试成功");
//            }else{
//                System.out.println("测试失败");
//            }
//            //1.利用正则表达式取出json的内容,再用fastjson逐层解析获取g的值
            Pattern p=Pattern.compile("\\((.*?)\\)");
            Matcher m=p.matcher(ipresult);
            m.find();
            String ipjson = m.group(1);

            //2.1获取结果信息中g键对应得值
            JSONObject jsonObject= JSON.parseObject(ipjson);
            //获取第一层，也就是result,是一个数组
            JSONArray result = jsonObject.getJSONArray("Result");
            //数组按下标获取元素
            JSONObject resultjson = result.getJSONObject(0);
            String resultData = resultjson.getJSONObject("DisplayData").getJSONObject("resultData").getJSONObject("tplData").get("location").toString();
            System.out.println("fastjson逐层解析获取location的值是"+resultData);

            //2.2正则表达式直接获取g键的值
            Pattern locationp=Pattern.compile(",\"location\":\"(.*?)\",");
            Matcher locationm=locationp.matcher(ipresult);
            locationm.find();
            System.out.println("通过正则表达式直接获取location的值是"+locationm.group(1));


            //3.jsonpath解析:也要基于字符串是个标准格式
            String jsonpath = JSONPath.read(ipjson, "$.Result[0].DisplayData.resultData.tplData.location").toString();
            System.out.println("通过jsonpath获取location的值是"+jsonpath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
