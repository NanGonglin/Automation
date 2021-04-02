package com.testing.class15;

//import com.sun.xml.internal.messaging.saaj.soap.Envelope;
import com.testing.inter.InterKw;

/**
 * @Classname SoapTest
 * @Description 类型说明
 * @Date 2021/3/29 19:31
 * @Created by 测试园
 */
public class SoapTest {

    public static void main(String[] args) {
        InterKw sy=new InterKw();
        //auth接口
        sy.doPostSoap("http://192.168.217.133:8080/Inter/SOAP?wsdl", "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soap=\"http://soap.testingedu.com/\"><soapenv:Header/><soapenv:Body><soap:auth></soap:auth></soapenv:Body></soapenv:Envelope>");
        sy.saveParam("tokenV", "$.token");
        sy.addHeader("{\"token\":\"{tokenV}\"}");

        //注册接口
        sy.saveRandomParam("userR", "yuan");
        sy.doPostSoap("http://192.168.217.133:8080/Inter/SOAP?wsdl","<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soap=\"http://soap.testingedu.com/\"><soapenv:Header/><soapenv:Body><soap:register><arg0>{userR}</arg0><arg1>123456</arg1><arg2>test register</arg2><arg3>study yuan</arg3></soap:register></soapenv:Body></soapenv:Envelope>");



        //登录接口
        sy.saveEncPwd("encPwd", "123456");
        sy.doPostSoap("http://192.168.217.133:8080/Inter/SOAP?wsdl", "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soap=\"http://soap.testingedu.com/\"><soapenv:Header/><soapenv:Body><soap:login><arg0>{userR}</arg0><arg1>{encPwd}</arg1></soap:login></soapenv:Body></soapenv:Envelope>");

        //getuserinfo接口
        sy.saveParam("idV", "$.userid");
        sy.doPostSoap("http://192.168.217.133:8080/Inter/SOAP?wsdl","<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soap=\"http://soap.testingedu.com/\"><soapenv:Header/><soapenv:Body><soap:getUserInfo><arg0>{idV}</arg0></soap:getUserInfo></soapenv:Body></soapenv:Envelope>");

        //注销接口
        sy.doPostSoap("http://192.168.217.133:8080/Inter/SOAP?wsdl","<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soap=\"http://soap.testingedu.com/\"><soapenv:Header/><soapenv:Body><soap:logout></soap:logout></soapenv:Body></soapenv:Envelope>");

    }
}
