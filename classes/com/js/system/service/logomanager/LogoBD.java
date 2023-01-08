package com.js.system.service.logomanager;

import com.js.system.bean.logomanager.LogoEJBBean;
import com.js.system.bean.logomanager.LogoEJBHome;
import com.js.system.vo.logomanager.LogoVO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class LogoBD {
  private static Logger logger = Logger.getLogger(LogoBD.class.getName());
  
  public static Map logoMap = new HashMap<Object, Object>();
  
  public List getLogoList() {
    List list = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("LogoEJB", "LogoEJBLocal", LogoEJBHome.class);
      list = (List)ejbProxy.invoke("getLogoList", (Object[][])null);
    } catch (Exception e) {
      logger.error("Error to select logo information:" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getBranchLogo(String orgId) {
    List list = null;
    try {
      list = (new LogoEJBBean()).getBranchLogo(orgId);
    } catch (Exception e) {
      logger.error("Error to select logo information:" + e.getMessage());
    } finally {}
    return list;
  }
  
  public LogoVO getLogo(String orgId) {
    LogoVO logoVO = null;
    try {
      logoVO = (new LogoEJBBean()).getLogo(orgId);
    } catch (Exception e) {
      logger.error("Error to select logo information:" + e.getMessage());
    } finally {}
    return logoVO;
  }
  
  public List getAllLogo() {
    List list = null;
    try {
      list = (new LogoEJBBean()).getAllLogo();
    } catch (Exception e) {
      logger.error("Error to select logo information:" + e.getMessage());
    } finally {}
    return list;
  }
  
  public LogoVO loadLogo(String logoId) throws Exception {
    LogoVO logoVO = new LogoVO();
    try {
      logoVO = (new LogoEJBBean()).loadLogo(logoId);
    } catch (Exception e) {
      throw e;
    } 
    return logoVO;
  }
  
  public void addLogo(LogoVO logoVO) throws Exception {
    try {
      (new LogoEJBBean()).addyLogo(logoVO);
    } catch (Exception e) {
      throw e;
    } 
  }
  
  public void modifyLogo(LogoVO logoVO) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(logoVO, LogoVO.class);
      EJBProxy ejbProxy = new EJBProxy("LogoEJB", "LogoEJBLocal", LogoEJBHome.class);
      ejbProxy.invoke("modifyLogo", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select logo information:" + e.getMessage());
    } 
  }
}
