package com.js.oa.hgydyy.pojo;

public class DyyRole extends DyyObject {
  private String roleName;
  
  private String roleDescription;
  
  private String guid;
  
  public String getRoleName() {
    return this.roleName;
  }
  
  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }
  
  public String getRoleDescription() {
    return this.roleDescription;
  }
  
  public void setRoleDescription(String roleDescription) {
    this.roleDescription = roleDescription;
  }
  
  public String getGuid() {
    return this.guid;
  }
  
  public void setGuid(String guid) {
    this.guid = guid;
  }
}
