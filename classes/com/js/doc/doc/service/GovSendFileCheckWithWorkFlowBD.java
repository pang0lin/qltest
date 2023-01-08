package com.js.doc.doc.service;

import com.js.doc.doc.bean.GovSendFileCheckWithWorkFlowEJBHome;
import com.js.doc.doc.po.GovSendFileCheckWithWorkFlowPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.Map;

public class GovSendFileCheckWithWorkFlowBD {
  public Long save(GovSendFileCheckWithWorkFlowPO paraPO, String[] displayName, String[] saveName) {
    Long id = new Long(-1L);
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "GovSendFileCheckWithWorkFlowEJB", 
          "GovSendFileCheckWithWorkFlowEJBLocal", 
          GovSendFileCheckWithWorkFlowEJBHome.class);
      pg.put(paraPO, GovSendFileCheckWithWorkFlowPO.class);
      pg.put(displayName, String[].class);
      pg.put(saveName, String[].class);
      id = (Long)ejbProxy.invoke("save", pg.getParameters());
    } catch (Exception e) {
      id = new Long(-1L);
      e.printStackTrace();
    } 
    return id;
  }
  
  public Map getDossierInfo(String sendFileCheckId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Map result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "GovSendFileCheckWithWorkFlowEJB", 
          "GovSendFileCheckWithWorkFlowEJBLocal", 
          GovSendFileCheckWithWorkFlowEJBHome.class);
      pg.put(sendFileCheckId, String.class);
      result = (Map)ejbProxy.invoke("getDossierInfo", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  public Long update(GovSendFileCheckWithWorkFlowPO paraPO, String[] displayName, String[] saveName) {
    Long id = new Long(-1L);
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "GovSendFileCheckWithWorkFlowEJB", 
          "GovSendFileCheckWithWorkFlowEJBLocal", 
          GovSendFileCheckWithWorkFlowEJBHome.class);
      pg.put(paraPO, GovSendFileCheckWithWorkFlowPO.class);
      pg.put(displayName, String[].class);
      pg.put(saveName, String[].class);
      id = (Long)ejbProxy.invoke("update", pg.getParameters());
    } catch (Exception e) {
      id = new Long(-1L);
      e.printStackTrace();
    } 
    return id;
  }
  
  public ArrayList load(Long paraId) {
    ArrayList tmpList = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "GovSendFileCheckWithWorkFlowEJB", 
          "GovSendFileCheckWithWorkFlowEJBLocal", 
          GovSendFileCheckWithWorkFlowEJBHome.class);
      pg.put(paraId, "Long");
      tmpList = (ArrayList)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      tmpList = null;
      e.printStackTrace();
    } 
    return tmpList;
  }
  
  public void deleteBatch(String ids) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "GovSendFileCheckWithWorkFlowEJB", 
          "GovSendFileCheckWithWorkFlowEJBLocal", 
          GovSendFileCheckWithWorkFlowEJBHome.class);
      pg.put(ids, "String");
      ejbProxy.invoke("deleteBatch", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public boolean isPigeonholed(String sendFileCheckId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Boolean result = Boolean.FALSE;
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "GovSendFileCheckWithWorkFlowEJB", 
          "GovSendFileCheckWithWorkFlowEJBLocal", 
          GovSendFileCheckWithWorkFlowEJBHome.class);
      pg.put(sendFileCheckId, String.class);
      result = (Boolean)ejbProxy.invoke("isPigeonholed", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result.booleanValue();
  }
  
  public Integer setPigeonholed(String sendFileCheckId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Integer result = Integer.valueOf("0");
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "GovSendFileCheckWithWorkFlowEJB", 
          "GovSendFileCheckWithWorkFlowEJBLocal", 
          GovSendFileCheckWithWorkFlowEJBHome.class);
      pg.put(sendFileCheckId, String.class);
      result = (Integer)ejbProxy.invoke("setPigeonholed", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  public Long completeSendFileCheck(String sendFileCheckId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Long result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "GovSendFileCheckWithWorkFlowEJB", 
          "GovSendFileCheckWithWorkFlowEJBLocal", 
          GovSendFileCheckWithWorkFlowEJBHome.class);
      pg.put(sendFileCheckId, String.class);
      result = (Long)ejbProxy.invoke("completeSendFileCheck", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
}
