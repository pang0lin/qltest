package com.js.oa.message.action;

import org.apache.struts.action.ActionForm;

public class MsManageActionForm extends ActionForm {
  private static final long serialVersionUID = 1L;
  
  private Long msId;
  
  private String msTitle;
  
  private String userRange;
  
  private String userRangeCh;
  
  private String msRemark;
  
  private String domainId;
  
  public Long getMsId() {
    return this.msId;
  }
  
  public void setMsId(Long msId) {
    this.msId = msId;
  }
  
  public String getMsTitle() {
    return this.msTitle;
  }
  
  public void setMsTitle(String msTitle) {
    this.msTitle = msTitle;
  }
  
  public String getMsRemark() {
    return this.msRemark;
  }
  
  public void setMsRemark(String msRemark) {
    this.msRemark = msRemark;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public String getUserRange() {
    return this.userRange;
  }
  
  public void setUserRange(String userRange) {
    this.userRange = userRange;
  }
  
  public String getUserRangeCh() {
    return this.userRangeCh;
  }
  
  public void setUserRangeCh(String userRangeCh) {
    this.userRangeCh = userRangeCh;
  }
}
