package com.js.oa.hntdxy;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

public class WebServiceTest {
  public static void main(String[] args) {
    try {
      String a = "a";
      String endpoint = "http://127.0.0.1/jsoa/services/hntdService?wsdl";
      Service service = new Service();
      Call call = (Call)service.createCall();
      call.setTargetEndpointAddress(new URL(endpoint));
      call.setOperationName(new QName("http://hntdxy.oa.js.com", "inform_Oa"));
      call.addParameter(new QName("http://hntdxy.oa.js.com", "inform_Oa"), 
          XMLType.XSD_STRING, ParameterMode.IN);
      call.setUseSOAPAction(true);
      call.setReturnType(XMLType.SOAP_STRING);
      call.setSOAPActionURI("http://hntdxy.oa.js.com/inform_Oa");
      String ret = (String)call.invoke(new Object[] { "20151142" });
      System.out.println(ret);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
