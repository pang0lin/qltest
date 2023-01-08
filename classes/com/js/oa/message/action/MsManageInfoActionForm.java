package com.js.oa.message.action;

import org.apache.struts.action.ActionForm;

public class MsManageInfoActionForm extends ActionForm {
  private static final long serialVersionUID = 1L;
  
  private Long msInfoId;
  
  private Long msId;
  
  private String grantId;
  
  private String grantType;
  
  public Long getMsInfoId() {
    return this.msInfoId;
  }
  
  public void setMsInfoId(Long msInfoId) {
    this.msInfoId = msInfoId;
  }
  
  public Long getMsId() {
    return this.msId;
  }
  
  public void setMsId(Long msId) {
    this.msId = msId;
  }
  
  public String getGrantId() {
    return this.grantId;
  }
  
  public void setGrantId(String grantId) {
    this.grantId = grantId;
  }
  
  public String getGrantType() {
    return this.grantType;
  }
  
  public void setGrantType(String grantType) {
    this.grantType = grantType;
  }
}
