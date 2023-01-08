package com.js.oa.webservice.langxin;

import javax.xml.namespace.QName;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

public class SSOService {
  public static String invokeWebService(String url, String method, String nameSpace, Object[] paras, Class[] classes) {
    String returnValue = "";
    try {
      RPCServiceClient serviceClient = new RPCServiceClient();
      Options options = serviceClient.getOptions();
      EndpointReference targetEPR = new EndpointReference(url);
      options.setTo(targetEPR);
      QName opQName = new QName(nameSpace, method);
      Object[] obj = serviceClient.invokeBlocking(opQName, paras, classes);
      returnValue = (String)obj[0];
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return returnValue;
  }
}
