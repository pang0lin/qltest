package com.js.oa.relproject.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class RelProjectActionForm extends ActionForm {
  private Long id;
  
  private String title;
  
  private String catalog;
  
  private String proDesc;
  
  private Integer status;
  
  private Integer rate;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getTitle() {
    return this.title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public String getCatalog() {
    return this.catalog;
  }
  
  public void setCatalog(String catalog) {
    this.catalog = catalog;
  }
  
  public String getProDesc() {
    return this.proDesc;
  }
  
  public void setProDesc(String proDesc) {
    this.proDesc = proDesc;
  }
  
  public Integer getStatus() {
    return this.status;
  }
  
  public void setStatus(Integer status) {
    this.status = status;
  }
  
  public Integer getRate() {
    return this.rate;
  }
  
  public void setRate(Integer rate) {
    this.rate = rate;
  }
}
