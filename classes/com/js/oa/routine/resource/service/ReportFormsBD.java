package com.js.oa.routine.resource.service;

import com.js.oa.routine.resource.bean.ReportFormsEJBHome;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class ReportFormsBD {
  private static Logger logger = Logger.getLogger(ReportFormsBD.class.getName());
  
  public List getReportForms(String[] para, String domainid) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(para, String[].class);
      pg.put(domainid, String.class);
      EJBProxy ejbProxy = new EJBProxy("ReportFormsEJB", "ReportFormsEJBLocal", ReportFormsEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getReportForms", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getReportForms information :" + e.getMessage());
    } 
    return alist;
  }
}
