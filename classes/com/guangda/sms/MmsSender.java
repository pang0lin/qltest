package com.guangda.sms;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

@WebServiceClient(name = "MmsSender", wsdlLocation = "http://10.1.131.35:9007/MmsSender.asmx?WSDL", targetNamespace = "http://cebbank.com/")
public class MmsSender extends Service {
  public static final URL WSDL_LOCATION = null;
  
  public static final QName SERVICE = new QName("http://cebbank.com/", "MmsSender");
  
  public static final QName MmsSenderSoap = new QName("http://cebbank.com/", "MmsSenderSoap");
  
  public static final QName MmsSenderSoap12 = new QName("http://cebbank.com/", "MmsSenderSoap12");
  
  static {
    URL url = null;
    try {
      url = new URL("http://10.1.131.35:9007/MmsSender.asmx?WSDL");
    } catch (MalformedURLException e) {
      Logger.getLogger(MmsSender.class.getName())
        .log(Level.INFO, 
          "Can not initialize the default wsdl from {0}", "http://10.1.131.35:9007/MmsSender.asmx?WSDL");
    } 
    WSDL_LOCATION = url;
  }
  
  public MmsSender(URL wsdlLocation) {
    super(wsdlLocation, SERVICE);
  }
  
  public MmsSender(URL wsdlLocation, QName serviceName) {
    super(wsdlLocation, serviceName);
  }
  
  public MmsSender() {
    super(WSDL_LOCATION, SERVICE);
  }
  
  public MmsSender(WebServiceFeature... features) {
    super(WSDL_LOCATION, SERVICE);
  }
  
  public MmsSender(URL wsdlLocation, WebServiceFeature... features) {
    super(wsdlLocation, SERVICE);
  }
  
  public MmsSender(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
    super(wsdlLocation, serviceName);
  }
  
  @WebEndpoint(name = "MmsSenderSoap")
  public MmsSenderSoap getMmsSenderSoap() {
    return (MmsSenderSoap)getPort(MmsSenderSoap, MmsSenderSoap.class);
  }
  
  @WebEndpoint(name = "MmsSenderSoap")
  public MmsSenderSoap getMmsSenderSoap(WebServiceFeature... features) {
    return (MmsSenderSoap)getPort(MmsSenderSoap, MmsSenderSoap.class, features);
  }
  
  @WebEndpoint(name = "MmsSenderSoap12")
  public MmsSenderSoap getMmsSenderSoap12() {
    return (MmsSenderSoap)getPort(MmsSenderSoap12, MmsSenderSoap.class);
  }
  
  @WebEndpoint(name = "MmsSenderSoap12")
  public MmsSenderSoap getMmsSenderSoap12(WebServiceFeature... features) {
    return (MmsSenderSoap)getPort(MmsSenderSoap12, MmsSenderSoap.class, features);
  }
}
