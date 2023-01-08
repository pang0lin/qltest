package com.js.oa.rws.service;

import javax.xml.namespace.QName;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

public class WebServiceInvoker {
  public static String invokeWebService(String url, String method, String para, String nameSpace) {
    String result = "";
    Object[] paras = new Object[1];
    Class[] classes = new Class[1];
    paras[0] = para;
    classes[0] = String.class;
    try {
      RPCServiceClient serviceClient = new RPCServiceClient();
      Options options = serviceClient.getOptions();
      EndpointReference targetEPR = new EndpointReference(url);
      options.setTo(targetEPR);
      QName opQName = new QName(nameSpace, method);
      Object[] obj = serviceClient.invokeBlocking(opQName, new Object[0], new Class[0]);
      result = (String)obj[0];
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
}
