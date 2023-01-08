package com.js.oa.message.service;

import com.js.oa.message.bean.MsHistoryEJBHome;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import org.apache.log4j.Logger;

public class MsHistoryBD {
  private static Logger logger = Logger.getLogger(MsHistoryBD.class.getName());
  
  public Boolean saveMsHistory(String fromUserId, String fromOrgId, String sendToPerson, String sendTime, String msContext, String result) throws Exception {
    Boolean flag = null;
    ParameterGenerator p = new ParameterGenerator(6);
    try {
      EJBProxy ejbProxy = new EJBProxy("MsHistoryEJB", "MsHistoryEJBLocal", MsHistoryEJBHome.class);
      p.put(fromUserId, String.class);
      p.put(fromOrgId, String.class);
      p.put(sendToPerson, String.class);
      p.put(sendTime, String.class);
      p.put(msContext, String.class);
      p.put(result, String.class);
      flag = (Boolean)ejbProxy.invoke("saveMsHistory", p.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return flag;
  }
  
  public String genExtendCode() throws Exception {
    String extendCode = null;
    ParameterGenerator p = new ParameterGenerator(0);
    try {
      EJBProxy ejbProxy = new EJBProxy("MsHistoryEJB", "MsHistoryEJBLocal", MsHistoryEJBHome.class);
      extendCode = (String)ejbProxy.invoke("genExtendCode", p.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return extendCode;
  }
  
  public Boolean saveMsHistory(String fromUserId, String fromOrgId, String sendToPerson, String sendTime, String msContext, String result, String receiveCode, String extendCode) throws Exception {
    Boolean flag = null;
    ParameterGenerator p = new ParameterGenerator(8);
    try {
      EJBProxy ejbProxy = new EJBProxy("MsHistoryEJB", 
          "MsHistoryEJBLocal", MsHistoryEJBHome.class);
      p.put(fromUserId, String.class);
      p.put(fromOrgId, String.class);
      p.put(sendToPerson, String.class);
      p.put(sendTime, String.class);
      p.put(msContext, String.class);
      p.put(result, String.class);
      p.put(receiveCode, String.class);
      p.put(extendCode, String.class);
      flag = (Boolean)ejbProxy
        .invoke("saveMsHistory", p.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return flag;
  }
}
