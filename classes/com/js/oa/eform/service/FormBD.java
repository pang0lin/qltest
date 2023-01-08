package com.js.oa.eform.service;

import com.js.oa.eform.bean.FormPageEJBBean;
import com.js.oa.eform.bean.FormPageEJBHome;
import com.js.oa.eform.po.TPagePO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.List;
import org.apache.log4j.Logger;

public class FormBD {
  private static Logger logger = Logger.getLogger(FormBD.class.getName());
  
  public Long save(TPagePO pagePO) {
    Long pageId = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(pagePO, TPagePO.class);
      EJBProxy ejbProxy = new EJBProxy("FormPageEJB", "FormPageEJBLocal", FormPageEJBHome.class);
      pageId = (Long)ejbProxy.invoke("save", pg.getParameters());
    } catch (Exception e) {
      logger.error("FormBD error to save information :" + e.getMessage());
    } 
    return pageId;
  }
  
  public List search(String pageName, String domainId) {
    List result = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(pageName, String.class);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("FormPageEJB", "FormPageEJBLocal", FormPageEJBHome.class);
      result = (List)ejbProxy.invoke("search", pg.getParameters());
    } catch (Exception e) {
      logger.error("FormBD error to search information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public List getSingleForm(String id) {
    List result = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("FormPageEJB", "FormPageEJBLocal", FormPageEJBHome.class);
      result = (List)ejbProxy.invoke("getSingleForm", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("FormBD error to getSingleForm information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public List getFeildsList(String id) {
    List result = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("FormPageEJB", "FormPageEJBLocal", FormPageEJBHome.class);
      result = (List)ejbProxy.invoke("getFeildsList", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("FormBD error to getFeildsList information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public Boolean update(TPagePO pagePO) {
    Boolean success = new Boolean(true);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(pagePO, TPagePO.class);
      EJBProxy ejbProxy = new EJBProxy("FormPageEJB", "FormPageEJBLocal", FormPageEJBHome.class);
      success = (Boolean)ejbProxy.invoke("update", pg.getParameters());
    } catch (Exception e) {
      logger.error("FormBD error to update information :" + e.getMessage());
    } 
    return success;
  }
  
  public Boolean delete(String pageIds) {
    Boolean success = new Boolean(true);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(pageIds, String.class);
      EJBProxy ejbProxy = new EJBProxy("FormPageEJB", "FormPageEJBLocal", FormPageEJBHome.class);
      success = (Boolean)ejbProxy.invoke("delete", pg.getParameters());
    } catch (Exception e) {
      logger.error("FormBD error to delete information :" + e.getMessage());
    } 
    return success;
  }
  
  public List getFormBaseInfo(String domainId) {
    List result = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("FormPageEJB", "FormPageEJBLocal", FormPageEJBHome.class);
      result = (List)ejbProxy.invoke("getFormBaseInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("FormBD error to getFormBaseInfo information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public List getFormBaseInfoByRange(String domainId, String userId, String orgId) {
    List result = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(domainId, String.class);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      EJBProxy ejbProxy = new EJBProxy("FormPageEJB", "FormPageEJBLocal", FormPageEJBHome.class);
      result = (List)ejbProxy.invoke("getFormBaseInfoByRange", pg.getParameters());
    } catch (Exception e) {
      logger.error("FormBD error to getFormBaseInfo information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public String getSelectedSubField(String pageId) {
    String result = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(pageId, String.class);
      EJBProxy ejbProxy = new EJBProxy("FormPageEJB", "FormPageEJBLocal", FormPageEJBHome.class);
      result = (String)ejbProxy.invoke("getSelectedSubField", pg.getParameters());
    } catch (Exception e) {
      logger.error("FormBD error to getFormBaseInfo information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public void updatePageEmp(String pageId, String empId) {
    try {
      (new FormPageEJBBean()).updatePageEmp(pageId, empId);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public String getPageEmp(String pageId) {
    try {
      return (new FormPageEJBBean()).getPageEmp(pageId);
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    } 
  }
  
  public TPagePO getPageFromPageId(Long pageId) throws Exception {
    TPagePO tPagePO = new TPagePO();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(pageId, Long.class);
      EJBProxy ejbProxy = new EJBProxy("FormPageEJB", "FormPageEJBLocal", FormPageEJBHome.class);
      tPagePO = (TPagePO)ejbProxy.invoke("getPageFromPageId", pg.getParameters());
    } catch (Exception e) {
      logger.error("FormBD error to getPageFromPageId information :" + e.getMessage());
    } finally {}
    return tPagePO;
  }
}
