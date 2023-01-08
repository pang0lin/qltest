package com.js.oa.security.log.service;

import com.js.oa.security.log.bean.LogEJBBean;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

public class LogBD {
  private static Logger logger = Logger.getLogger(LogBD.class.getName());
  
  private LogEJBBean bean = new LogEJBBean();
  
  public boolean log(String userId, String userName, String userOrgName, String moduleSerial, String subModule, Date oprStartTime, Date oprEndTime, String oprType, String oprContent, String logIP, String domainId) {
    boolean result = false;
    try {
      result = this.bean.log(userId, userName, userOrgName, moduleSerial, subModule, oprStartTime, oprEndTime, oprType, oprContent, logIP, domainId).booleanValue();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return result;
  }
  
  public List export(String where) {
    List list = new ArrayList();
    try {
      list = this.bean.export(where);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return list;
  }
  
  public List moduleList(String domainId) {
    List list = new ArrayList();
    try {
      list = this.bean.moduleList(domainId);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return list;
  }
  
  public void deleteLog(String where, String tableNameString) {
    try {
      this.bean.deleteLog(where, tableNameString);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public static void main() {
    LogBD bd = new LogBD();
    bd.log("111", "", "九思科技.开发部", "gw", "栏目级别", new Date(), 
        new Date(), "1", "", null, "0");
  }
}
