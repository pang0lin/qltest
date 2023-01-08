package com.js.oa.hr.personnelmanager.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class HprecordActionForm extends ActionForm {
  private String saveType;
  
  private Long id;
  
  private String hpTitle;
  
  private Long hpType;
  
  private String hpPersonnel;
  
  private String hpPersonnelName;
  
  private String hpDate;
  
  private String hpExplain;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private Long domain;
  
  private String beginDate;
  
  private String endDate;
  
  private String htrq;
  
  public String getSaveType() {
    return this.saveType;
  }
  
  public void setSaveType(String saveType) {
    this.saveType = saveType;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public String getHpTitle() {
    return this.hpTitle;
  }
  
  public Long getHpType() {
    return this.hpType;
  }
  
  public String getHpPersonnel() {
    return this.hpPersonnel;
  }
  
  public String getHpPersonnelName() {
    return this.hpPersonnelName;
  }
  
  public String getHpDate() {
    return this.hpDate;
  }
  
  public String getHpExplain() {
    return this.hpExplain;
  }
  
  public Long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public Long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public Long getDomain() {
    return this.domain;
  }
  
  public void setDomain(Long domain) {
    this.domain = domain;
  }
  
  public void setCreatedorg(Long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public void setCreatedEmp(Long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public void setHpExplain(String hpExplain) {
    this.hpExplain = hpExplain;
  }
  
  public void setHpDate(String hpDate) {
    this.hpDate = hpDate;
  }
  
  public void setHpPersonnelName(String hpPersonnelName) {
    this.hpPersonnelName = hpPersonnelName;
  }
  
  public void setHpPersonnel(String hpPersonnel) {
    this.hpPersonnel = hpPersonnel;
  }
  
  public void setHpType(Long hpType) {
    this.hpType = hpType;
  }
  
  public void setHpTitle(String hpTitle) {
    this.hpTitle = hpTitle;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getBeginDate() {
    return this.beginDate;
  }
  
  public void setBeginDate(String beginDate) {
    this.beginDate = beginDate;
  }
  
  public String getEndDate() {
    return this.endDate;
  }
  
  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }
  
  public String getHtrq() {
    return this.htrq;
  }
  
  public void setHtrq(String htrq) {
    this.htrq = htrq;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.saveType = null;
    this.hpDate = null;
    this.hpTitle = null;
    this.hpType = null;
    this.hpPersonnel = null;
    this.hpExplain = null;
    this.hpPersonnelName = null;
  }
}
