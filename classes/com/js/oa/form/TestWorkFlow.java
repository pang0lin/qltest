package com.js.oa.form;

import com.js.db.FileToDB;
import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

public class TestWorkFlow extends Workflow {
  public boolean beforeSave(HttpServletRequest request) {
    System.out.println("保存之前...");
    return true;
  }
  
  public boolean afterSave(HttpServletRequest request) {
    System.out.println("保存之后...");
    return true;
  }
  
  public boolean beforeUpdate(HttpServletRequest request) {
    try {
      RPCServiceClient serviceClient = new RPCServiceClient();
      Options options = serviceClient.getOptions();
      EndpointReference targetEPR = new EndpointReference("http://192.168.0.100:8080/LandrayOA/services/LandRayOA");
      options.setTo(targetEPR);
      Object[] opAddEntryArgs = { Integer.valueOf(1), "a", "b", "c" };
      Class[] classes = { Integer.class, String.class, String.class, String.class };
      QName opAddEntry = new QName("http://oa.pmo.citics.com", "LandRayOATest");
      Object obj = serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs, classes);
      String result = (String)obj;
      System.out.println(result);
    } catch (Exception exception) {}
    return true;
  }
  
  public boolean afterUpdate(HttpServletRequest request) {
    return true;
  }
  
  public boolean beforeComplete(HttpServletRequest request) {
    return true;
  }
  
  public boolean afterComplete(HttpServletRequest request) {
    FileToDB fileToDB = new FileToDB();
    fileToDB.wordToBLOB("c:/test.doc");
    return true;
  }
}
