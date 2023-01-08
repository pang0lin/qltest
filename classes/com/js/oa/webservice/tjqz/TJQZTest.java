package com.js.oa.webservice.tjqz;

import javax.xml.namespace.QName;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

public class TJQZTest {
  public static void main(String[] args) {
    String url = "http://localhost:8081/jsoa/services/tjqzService?wsdl";
    String nameSpace = "http://js.webServices.tjqz/";
    String method = "getDataInfoList";
    String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?><ROOT><Field ColName=\"userId\" Value=\"zhangxiang\"></Field><Field ColName=\"dataType\" Value=\"hysq\"></Field><Field ColName=\"channelId\" Value=\"102\"></Field></ROOT>";
    Object[] paras = { xml };
    Class[] classes = { String.class };
    invokeWebService(url, method, nameSpace, paras, classes);
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
