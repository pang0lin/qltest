package com.js.oa.hr.personnelmanager.service;

import com.js.oa.hr.personnelmanager.bean.EmployeeOtherInfoEJBHome;
import com.js.system.vo.usermanager.EmployeeOtherInfoVO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import org.apache.log4j.Logger;

public class EmployeeOtherInfoBD {
  private static Logger logger = Logger.getLogger(EmployeeOtherInfoBD.class
      .getName());
  
  public EmployeeOtherInfoVO load(Long id) {
    EmployeeOtherInfoVO vo = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeOtherInfoEJB", 
          "EmployeeOtherInfoEJBLocal", 
          EmployeeOtherInfoEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      vo = (EmployeeOtherInfoVO)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      logger.info("根据ID获取JcxxVO对象时出错:" + e);
    } 
    return vo;
  }
}
