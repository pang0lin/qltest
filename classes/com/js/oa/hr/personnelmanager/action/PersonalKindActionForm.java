package com.js.oa.hr.personnelmanager.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class PersonalKindActionForm extends ActionForm {
  private Long kindId;
  
  private String kindName;
  
  private String kindDescription;
  
  private Integer kindSort;
  
  public String getKindDescription() {
    return this.kindDescription;
  }
  
  public Integer getKindSort() {
    return this.kindSort;
  }
  
  public Long getKindId() {
    return this.kindId;
  }
  
  public void setKindName(String kindName) {
    this.kindName = kindName;
  }
  
  public void setKindDescription(String kindDescription) {
    this.kindDescription = kindDescription;
  }
  
  public void setKindSort(Integer kindSort) {
    this.kindSort = kindSort;
  }
  
  public void setKindId(Long kindId) {
    this.kindId = kindId;
  }
  
  public String getKindName() {
    return this.kindName;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
}
