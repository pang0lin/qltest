package com.js.oa.hr.resume.bean;

import com.js.oa.hr.resume.po.ResumePO;
import javax.ejb.EJBObject;

public interface ResumeEJB extends EJBObject {
  ResumePO getResumeInfo(String paramString) throws Exception;
  
  void save(String paramString1, String paramString2, String paramString3) throws Exception;
}
