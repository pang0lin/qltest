package com.js.oa.jsflow.service;

import com.js.oa.jsflow.bean.WorkFlowEJBHome;
import com.js.oa.jsflow.vo.ModuleVO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import org.apache.log4j.Logger;

public class ModuleBD {
  private static Logger logger = Logger.getLogger(ModuleBD.class.getName());
  
  public ModuleVO getModule(int moduleId) {
    ModuleVO moduleVO = new ModuleVO();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(new Integer(moduleId), Integer.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      moduleVO = (ModuleVO)ejbProxy.invoke("getModule", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getModule information :" + e.getMessage());
    } 
    return moduleVO;
  }
}
