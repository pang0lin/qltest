package com.js.oa.hr.resume.service;

import com.js.oa.hr.resume.bean.ResumeEJBHome;
import com.js.oa.hr.resume.po.ResumePO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;

public class ResumeBD {
  public ResumePO getResumeInfo(String id) {
    ResumePO result = new ResumePO();
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("ResumeEJB", 
          "ResumeEJBLocal", ResumeEJBHome.class);
      result = (ResumePO)ejbProxy.invoke("getResumeInfo", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("getResumeInfo Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public void save(String id, String zt, String bz) {
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      pg.put(id, String.class);
      pg.put(zt, String.class);
      pg.put(bz, String.class);
      EJBProxy ejbProxy = new EJBProxy("ResumeEJB", 
          "ResumeEJBLocal", ResumeEJBHome.class);
      ejbProxy.invoke("save", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("save Exception:" + 
          ex.getMessage());
    } 
  }
}
