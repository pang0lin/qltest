package com.js.oa.rws.service;

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
      String endpoint = "http://192.168.100.106/ws/DocmanSoft.DataPort.asmx";
      Service service = new Service();
      Call call = (Call)service.createCall();
      call.setTargetEndpointAddress(new URL(endpoint));
      call.setOperationName(new QName("http://www.haohai.com.cn/", "StartTransaction"));
      if ("".equals("")) {
        call.addParameter(new QName("http://www.haohai.com.cn/", "StartTransaction"), 
            XMLType.XSD_STRING, ParameterMode.IN);
      } else {
        call.addParameter("GUID", XMLType.XSD_STRING, 
            ParameterMode.IN);
      } 
      call.setUseSOAPAction(true);
      call.setReturnType(XMLType.SOAP_STRING);
      call.setSOAPActionURI("http://www.haohai.com.cn/StartTransaction");
      String ret = (String)call.invoke(new Object[] { a });
      System.out.println("--------" + ret);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
