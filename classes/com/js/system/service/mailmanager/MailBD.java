package com.js.system.service.mailmanager;

import com.js.system.action.mailmanager.MailActionForm;
import com.js.system.bean.mailmanager.MailEJBHome;
import com.js.system.vo.mailmanager.MailVO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.List;
import org.apache.log4j.Logger;

public class MailBD {
  private static Logger logger = Logger.getLogger(MailBD.class.getName());
  
  public void add(MailVO mailVO) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("MailEJB", "MailEJBLocal", MailEJBHome.class);
      pg.put(mailVO, MailVO.class);
      ejbProxy.invoke("add", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to add Mail information:" + e.getMessage());
    } 
  }
  
  public void del(String mailId, String[] mailIds) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("MailEJB", "MailEJBLocal", MailEJBHome.class);
      pg.put(mailId, String.class);
      pg.put(mailIds, String[].class);
      ejbProxy.invoke("del", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to del Mail information:" + e.getMessage());
    } 
  }
  
  public void delAll() {
    try {
      EJBProxy ejbProxy = new EJBProxy("MailEJB", "MailEJBLocal", MailEJBHome.class);
      ejbProxy.invoke("delAll", (Object[][])null);
    } catch (Exception e) {
      logger.error("Error to delAll Mail information:" + e.getMessage());
    } 
  }
  
  public MailVO getSingleMail(String id) {
    MailVO mailVO = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("MailEJB", "MailEJBLocal", MailEJBHome.class);
      mailVO = (MailVO)ejbProxy.invoke("getSingleMail", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select Mail information:" + e.getMessage());
    } finally {}
    return mailVO;
  }
  
  public MailVO getSingleMailByFromUser(MailActionForm mailForm) {
    MailVO mailVO = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(mailForm, MailActionForm.class);
      EJBProxy ejbProxy = new EJBProxy("MailEJB", "MailEJBLocal", MailEJBHome.class);
      mailVO = (MailVO)ejbProxy.invoke("getSingleMailByFromUser", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select Mail information:" + e.getMessage());
    } finally {}
    return mailVO;
  }
  
  public List getMailList() {
    List list = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("MailEJB", "MailEJBLocal", MailEJBHome.class);
      list = (List)ejbProxy.invoke("getMailList", (Object[][])null);
    } catch (Exception e) {
      logger.error("Error to select Mail information:" + e.getMessage());
    } finally {}
    return list;
  }
  
  public void modMail(MailVO mailVO) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(mailVO, MailVO.class);
      EJBProxy ejbProxy = new EJBProxy("MailEJB", "MailEJBLocal", MailEJBHome.class);
      mailVO = (MailVO)ejbProxy.invoke("modMail", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select Mail information:" + e.getMessage());
    } 
  }
}
