package com.js.oa.webservice.appservice;

import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.namespace.QName;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

public class TestAppService {
  public static void main(String[] args) {
    String url = "http://localhost:8081/jsoa/services/AppService?wsdl";
    String nameSpace = "http://appservice.webservice.oa.js.com";
    getCheckinCount(url, nameSpace);
  }
  
  private static void getCheckinCount(String url, String nameSpace) {
    String method = "getCheckinCount";
    String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><checkInfo><userId>xinqingsong</userId><signType>0</signType></checkInfo>";
    Object[] paras = { xml };
    Class[] classes = { String.class };
    invokeWebService(url, method, nameSpace, paras, classes);
  }
  
  private static void upload(String url, String nameSpace) {
    String method = "saveImg";
    String userId = "zhangxiang";
    FileInputStream fis = null;
    try {
      fis = new FileInputStream("f:/a.jpg");
      byte[] bytes = new byte[fis.available()];
      fis.read(bytes);
      Object[] paras = { userId, bytes };
      Class[] classes = { String.class };
      invokeWebService(url, method, nameSpace, paras, classes);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (fis != null)
          fis.close(); 
      } catch (IOException e) {
        e.printStackTrace();
      } 
    } 
  }
  
  private static void invokeWebService(String url, String method, String nameSpace, Object[] paras, Class[] classes) {
    try {
      RPCServiceClient serviceClient = new RPCServiceClient();
      Options options = serviceClient.getOptions();
      EndpointReference targetEPR = new EndpointReference(url);
      options.setTo(targetEPR);
      QName opQName = new QName(nameSpace, method);
      Object[] obj = serviceClient.invokeBlocking(opQName, paras, classes);
      String result = (String)obj[0];
      System.out.println(result);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
