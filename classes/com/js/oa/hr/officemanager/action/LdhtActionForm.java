package com.js.oa.hr.officemanager.action;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class LdhtActionForm extends ActionForm implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private String kssj;
  
  private String jssj;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public String getKssj() {
    return this.kssj;
  }
  
  public void setKssj(String kssj) {
    this.kssj = kssj;
  }
  
  public String getJssj() {
    return this.jssj;
  }
  
  public void setJssj(String jssj) {
    this.jssj = jssj;
  }
}
