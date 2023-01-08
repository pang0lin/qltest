package com.js.oa.search.client;

import javax.xml.namespace.QName;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class SearchServiceImpl {
  private static SearchServiceImpl instance;
  
  private static RPCServiceClient serviceClient = null;
  
  private static Options options = null;
  
  private static EndpointReference targetEPR = null;
  
  private static String ifActiveUpdateDelete;
  
  private static String iSearchSwitch;
  
  public static SearchServiceImpl getInstance() {
    if (instance == null) {
      init();
      instance = new SearchServiceImpl();
    } 
    return instance;
  }
  
  public static void init() {
    try {
      String path = System.getProperty("user.dir");
      String filePath = String.valueOf(path) + "/jsconfig/sysconfig.xml";
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(filePath);
      Element node = doc.getRootElement().getChild("ISearchService");
      String serverAddress = node.getChild("serverAddress").getAttribute("value").getValue();
      String serverName = node.getChild("serverName").getAttribute("value").getValue();
      ifActiveUpdateDelete = node.getChild("ifActiveUpdateDelete").getAttribute("value").getValue();
      if (ifActiveUpdateDelete != null && !"".equals(ifActiveUpdateDelete)) {
        ifActiveUpdateDelete = ifActiveUpdateDelete.toLowerCase();
        if (!"yes".equals(ifActiveUpdateDelete))
          ifActiveUpdateDelete = "no"; 
      } else {
        ifActiveUpdateDelete = "no";
      } 
      iSearchSwitch = node.getChild("ISearchSwitch").getAttribute("value").getValue();
      if (iSearchSwitch != null && !"".equals(iSearchSwitch)) {
        if (!"1".equals(iSearchSwitch))
          iSearchSwitch = "0"; 
      } else {
        iSearchSwitch = "0";
      } 
      serviceClient = new RPCServiceClient();
      options = serviceClient.getOptions();
      options.setTimeOutInMilliSeconds(40000L);
      targetEPR = new EndpointReference(
          "http://" + serverAddress + "/" + serverName + "/services/SearchFrService");
      options.setTo(targetEPR);
    } catch (Exception e) {
      ifActiveUpdateDelete = "no";
      iSearchSwitch = "0";
    } 
  }
  
  public static void addIndex(String id, String tableName) {
    try {
      Object[] opAddEntryArgs = { id, tableName };
      QName opAddEntry = new QName("http://service.webService.js.com", "addIndex");
      serviceClient.invokeRobust(opAddEntry, opAddEntryArgs);
    } catch (AxisFault e) {
      e.printStackTrace();
    } 
  }
  
  public static void updateIndex(String id, String tableName) {
    try {
      Object[] opAddEntryArgs = { id, tableName };
      QName opAddEntry = new QName("http://service.webService.js.com", "updateIndex");
      serviceClient.invokeRobust(opAddEntry, opAddEntryArgs);
    } catch (AxisFault e) {
      e.printStackTrace();
    } 
  }
  
  public static void deleteIndex(String id, String tableName) {
    try {
      Object[] opAddEntryArgs = { id, tableName };
      QName opAddEntry = new QName("http://service.webService.js.com", "deleteIndex");
      serviceClient.invokeRobust(opAddEntry, opAddEntryArgs);
    } catch (AxisFault e) {
      e.printStackTrace();
    } 
  }
  
  public static void reConstructIndex() {
    try {
      Object[] opAddEntryArgs = new Object[0];
      QName opAddEntry = new QName("http://service.webService.js.com", "reConstructIndex");
      serviceClient.invokeRobust(opAddEntry, opAddEntryArgs);
    } catch (AxisFault e) {
      e.printStackTrace();
    } 
  }
  
  public static void commit() {
    try {
      Object[] opAddEntryArgs = new Object[0];
      QName opAddEntry = new QName("http://service.webService.js.com", "commit");
      serviceClient.invokeRobust(opAddEntry, opAddEntryArgs);
    } catch (AxisFault e) {
      e.printStackTrace();
    } 
  }
  
  public static RPCServiceClient getServiceClient() {
    return serviceClient;
  }
  
  public static Options getOptions() {
    return options;
  }
  
  public static EndpointReference getTargetEPR() {
    return targetEPR;
  }
  
  public static String getIfActiveUpdateDelete() {
    return ifActiveUpdateDelete;
  }
  
  public static String getiSearchSwitch() {
    return iSearchSwitch;
  }
}
