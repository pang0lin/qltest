package com.js.oa.webmail.service;

import com.js.oa.webmail.bean.AffixEJBHome;
import com.js.oa.webmail.po.Affix;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.Collection;
import java.util.List;
import org.apache.log4j.Logger;

public class AffixBD {
  private static Logger logger = Logger.getLogger(AffixBD.class.getName());
  
  public Affix getAffixByPath(String oldPath) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Affix affix = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("AffixEJB", "AffixEJBLocal", AffixEJBHome.class);
      pg.put(oldPath, String.class);
      affix = (Affix)ejbProxy.invoke("getAffixByPath", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to add Mail information:" + e.getMessage());
    } 
    return affix;
  }
  
  public List getAffixList() {
    List list = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("AffixEJB", "AffixEJBLocal", AffixEJBHome.class);
      list = (List)ejbProxy.invoke("getAffixList", (Object[][])null);
    } catch (Exception e) {
      logger.error("Error to add Mail information:" + e.getMessage());
    } 
    return list;
  }
  
  public List getAffixListByMailId(String mailId) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("AffixEJB", "AffixEJBLocal", AffixEJBHome.class);
      pg.put(mailId, String.class);
      list = (List)ejbProxy.invoke("getAffixListByMailId", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to add Mail information:" + e.getMessage());
    } 
    return list;
  }
  
  public void delMailAffix(String[] ids) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(ids, String[].class);
      EJBProxy ejbProxy = new EJBProxy("AffixEJB", "AffixEJBLocal", AffixEJBHome.class);
      ejbProxy.invoke("delMailAffix", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select Mail information:" + e.getMessage());
    } 
  }
  
  public void createAttach(List attachList) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("AffixEJB", "AffixEJBLocal", AffixEJBHome.class);
      pg.put(attachList, Collection.class);
      ejbProxy.invoke("createAttach", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to add Mail information:" + e.getMessage());
    } 
  }
  
  public List getAttachList() {
    List list = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("AffixEJB", "AffixEJBLocal", AffixEJBHome.class);
      list = (List)ejbProxy.invoke("getAttachList", (Object[][])null);
    } catch (Exception e) {
      logger.error("Error to add Mail information:" + e.getMessage());
    } 
    return list;
  }
}
