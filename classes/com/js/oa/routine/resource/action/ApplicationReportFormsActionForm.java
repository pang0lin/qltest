package com.js.oa.routine.resource.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ApplicationReportFormsActionForm extends ActionForm {
  private String countType;
  
  private String searchStock;
  
  private String searchDept;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getCountType() {
    return this.countType;
  }
  
  public String getSearchStock() {
    return this.searchStock;
  }
  
  public void setCountType(String countType) {
    this.countType = countType;
  }
  
  public void setSearchStock(String searchStock) {
    this.searchStock = searchStock;
  }
  
  public String getSearchDept() {
    return this.searchDept;
  }
  
  public void setSearchDept(String searchDept) {
    this.searchDept = searchDept;
  }
}
