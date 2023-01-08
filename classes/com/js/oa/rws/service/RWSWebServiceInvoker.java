package com.js.oa.rws.service;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

public class RWSWebServiceInvoker {
  public String invokeService(String endpoint, String method, String para, String nameSpace) {
    try {
      Service service = new Service();
      Call call = (Call)service.createCall();
      call.setTargetEndpointAddress(new URL(endpoint));
      call.setOperationName(new QName(nameSpace, method));
      if ("".equals(para)) {
        call.addParameter(new QName(nameSpace, method), 
            XMLType.XSD_STRING, ParameterMode.IN);
      } else {
        call.addParameter("GUID", XMLType.XSD_STRING, 
            ParameterMode.IN);
      } 
      call.setUseSOAPAction(true);
      call.setReturnType(XMLType.SOAP_STRING);
      call.setSOAPActionURI(String.valueOf(nameSpace) + method);
      String ret = (String)call.invoke(new Object[] { para });
      return ret;
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    } 
  }
}
