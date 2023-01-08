package com.js.oa.portal.po;

import java.io.Serializable;

public class CustomDefaultPO implements Serializable {
  private Long id;
  
  private Long emp_id;
  
  private Long portal_id;
  
  private String portalname;
  
  private String viewrangename;
  
  private String vieworg;
  
  private String viewuser;
  
  private String viewgroup;
  
  private Long createdemp;
  
  private Long createdorg;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public Long getEmp_id() {
    return this.emp_id;
  }
  
  public void setEmp_id(Long emp_id) {
    this.emp_id = emp_id;
  }
  
  public Long getPortal_id() {
    return this.portal_id;
  }
  
  public void setPortal_id(Long portal_id) {
    this.portal_id = portal_id;
  }
  
  public String getVieworg() {
    return this.vieworg;
  }
  
  public void setVieworg(String vieworg) {
    this.vieworg = vieworg;
  }
  
  public String getViewuser() {
    return this.viewuser;
  }
  
  public void setViewuser(String viewuser) {
    this.viewuser = viewuser;
  }
  
  public String getViewgroup() {
    return this.viewgroup;
  }
  
  public void setViewgroup(String viewgroup) {
    this.viewgroup = viewgroup;
  }
  
  public Long getCreatedemp() {
    return this.createdemp;
  }
  
  public void setCreatedemp(Long createdemp) {
    this.createdemp = createdemp;
  }
  
  public Long getCreatedorg() {
    return this.createdorg;
  }
  
  public void setCreatedorg(Long createdorg) {
    this.createdorg = createdorg;
  }
  
  public String getPortalname() {
    return this.portalname;
  }
  
  public void setPortalname(String portalname) {
    this.portalname = portalname;
  }
  
  public String getViewrangename() {
    return this.viewrangename;
  }
  
  public void setViewrangename(String viewrangename) {
    this.viewrangename = viewrangename;
  }
}
