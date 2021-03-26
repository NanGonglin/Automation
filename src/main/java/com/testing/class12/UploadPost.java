package com.testing.class12;

import com.alibaba.fastjson.JSONPath;
import com.testing.common.AutoLogger;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;

/**
 * @Classname UploadPost
 * @Description 类型说明
 * @Date 2021/3/26 13:30
 * @Created by 测试园
 */
public class UploadPost {
    public static void main(String[] args) throws IOException {
        CloseableHttpClient client= HttpClients.createDefault();
        HttpPost uploadpost=new HttpPost("http://www.testingedu.com.cn:8000/index.php/home/Uploadify/imageUp/savepath/head_pic/pictitle/banner/dir/images.html");

        //mutipart请求体的设置，通过multipartentitybuilder来设置
        MultipartEntityBuilder meb=MultipartEntityBuilder.create();
        meb.addTextBody("id", "WU_FILE_0");
        meb.addTextBody("name", "小米净水器主页图片.png");
        meb.addTextBody("type", "image/png");
        meb.addTextBody("lastModifiedDate", "Tue Mar 16 2021 18:59:38 GMT+0800 (中国标准时间)");
        //添加文件格式的参数
        meb.addBinaryBody("file", new File("E:\\小米净水器主页图片.png"));
        //完成multipart请求体的构建
        HttpEntity fileContent=meb.build();
        //将创建好的请求体，加到请求包里面去
        uploadpost.setEntity(fileContent);
        //由于boundry是MultipartEntityBuilder构造过程中自动生成的，所以会自动识别键值对的边界，不需要自己再去指定content-type
//        uploadpost.setHeader("Content-Type", "multipart/form-data;boundary=----WebKitFormBoundaryu2VyZTXxdO1cVsSy");

        CloseableHttpResponse execute = client.execute(uploadpost);
        String result = EntityUtils.toString(execute.getEntity(), "UTF-8");
        System.out.println(result);
        String state = JSONPath.read(result, "$.state").toString();
        System.out.println(state);
        if("SUCCESS".equals(state)){
            AutoLogger.log.info("pass");
        }
    }
}
