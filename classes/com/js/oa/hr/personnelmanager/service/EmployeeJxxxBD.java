package com.js.oa.hr.personnelmanager.service;

import com.js.oa.hr.personnelmanager.bean.EmployeeJxxxEJBHome;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import org.apache.log4j.Logger;

public class EmployeeJxxxBD {
  private static Logger logger = Logger.getLogger(EmployeeJxxxBD.class
      .getName());
  
  public String[][] list(String logonName) {
    String[][] result = (String[][])null;
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeJxxxEJB", 
          "EmployeeJxxxEJBLocal", 
          EmployeeJxxxEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(logonName, String.class);
      result = (String[][])ejbProxy.invoke("list", pg.getParameters());
    } catch (Exception e) {
      logger.info("保存教育经历时出错:" + e);
    } 
    return result;
  }
}
