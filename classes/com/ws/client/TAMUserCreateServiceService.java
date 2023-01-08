package com.ws.client;

import com.js.oa.chinaLife.tam.TAMConfig;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;

@WebServiceClient(name = "TAMUserCreateServiceService", targetNamespace = "http://user.tam.clamc.com/", wsdlLocation = "http://192.168.32.137:9080/tamwebservice/TAMUserCreateServiceService/WEB-INF/wsdl/TAMUserCreateServiceService.wsdl")
public class TAMUserCreateServiceService extends Service {
  private static final URL TAMUSERCREATESERVICESERVICE_WSDL_LOCATION = null;
  
  private static final Logger logger = Logger.getLogger(TAMUserCreateServiceService.class
      .getName());
  
  static {
    URL url = null;
    try {
      URL baseUrl = TAMUserCreateServiceService.class
        .getResource(".");
      url = new URL(
          baseUrl, TAMConfig.getWsdlUrl());
    } catch (MalformedURLException e) {
      logger.warning("Failed to create URL for the wsdl Location: '" + TAMConfig.getWsdlUrl() + "', retrying as a local file");
      logger.warning(e.getMessage());
    } 
    TAMUSERCREATESERVICESERVICE_WSDL_LOCATION = url;
  }
  
  public TAMUserCreateServiceService(URL wsdlLocation, QName serviceName) {
    super(wsdlLocation, serviceName);
  }
  
  public TAMUserCreateServiceService() {
    super(TAMUSERCREATESERVICESERVICE_WSDL_LOCATION, new QName("http://user.tam.clamc.com/", "TAMUserCreateServiceService"));
  }
  
  @WebEndpoint(name = "TAMUserCreateServicePort")
  public TAMUserCreateServiceDelegate getTAMUserCreateServicePort() {
    return (TAMUserCreateServiceDelegate)
      getPort(new QName("http://user.tam.clamc.com/", 
          "TAMUserCreateServicePort"), 
        TAMUserCreateServiceDelegate.class);
  }
  
  public static void main(String[] args) {
    TAMUserCreateServiceService a = new TAMUserCreateServiceService();
    try {
      System.out.println(a.getTAMUserCreateServicePort().delTAMUserInfo("zjc"));
    } catch (Exception_Exception e) {
      e.printStackTrace();
    } 
  }
}
