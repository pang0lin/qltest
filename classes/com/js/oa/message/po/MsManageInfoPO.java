package com.js.oa.message.po;

public class MsManageInfoPO {
  private Long msInfoId;
  
  private MsManagePO msManage;
  
  private String grantId;
  
  private String grantName;
  
  private String grantType;
  
  public Long getMsInfoId() {
    return this.msInfoId;
  }
  
  public void setMsInfoId(Long msInfoId) {
    this.msInfoId = msInfoId;
  }
  
  public MsManagePO getMsManage() {
    return this.msManage;
  }
  
  public void setMsManage(MsManagePO msManage) {
    this.msManage = msManage;
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
  
  public String getGrantName() {
    return this.grantName;
  }
  
  public void setGrantName(String grantName) {
    this.grantName = grantName;
  }
}
