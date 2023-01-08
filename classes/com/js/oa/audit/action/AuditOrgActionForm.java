package com.js.oa.audit.action;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class AuditOrgActionForm extends ActionForm {
  private long orgId;
  
  private String currentOrderCode;
  
  private String parentIdString;
  
  private String orgDescripte;
  
  private String orgManagerEmpId;
  
  private String orgIdString;
  
  private String orgManagerEmpName;
  
  private String orgName;
  
  private long orgParentOrgId;
  
  private int orgSort;
  
  private Date orgFoundDate = null;
  
  private int orgLevel;
  
  private int orgHasChannel;
  
  private int orgChannelStyle;
  
  private String orgSimpleName;
  
  private String orgSerial;
  
  private String orgType = "1";
  
  private String orgChannelType = "0";
  
  private String orgChannelUrl;
  
  private String guid;
  
  private String orgNameString;
  
  public String getGuid() {
    return this.guid;
  }
  
  public void setGuid(String guid) {
    this.guid = guid;
  }
  
  public String getOrgNameString() {
    return this.orgNameString;
  }
  
  public void setOrgNameString(String orgNameString) {
    this.orgNameString = orgNameString;
  }
  
  public void setOrgId(long orgId) {
    this.orgId = orgId;
  }
  
  public long getOrgId() {
    return this.orgId;
  }
  
  public int getOrgLevel() {
    return this.orgLevel;
  }
  
  public void setOrgLevel(int orgLevel) {
    this.orgLevel = orgLevel;
  }
  
  public String getOrgDescripte() {
    return this.orgDescripte;
  }
  
  public void setOrgDescripte(String orgDescripte) {
    this.orgDescripte = orgDescripte;
  }
  
  public String getOrgManagerEmpName() {
    return this.orgManagerEmpName;
  }
  
  public void setOrgManagerEmpName(String orgManagerEmpName) {
    this.orgManagerEmpName = orgManagerEmpName;
  }
  
  public String getOrgName() {
    return this.orgName;
  }
  
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
  
  public long getOrgParentOrgId() {
    return this.orgParentOrgId;
  }
  
  public void setOrgParentOrgId(long orgParentOrgId) {
    this.orgParentOrgId = orgParentOrgId;
  }
  
  public int getOrgSort() {
    return this.orgSort;
  }
  
  public void setOrgSort(int orgSort) {
    this.orgSort = orgSort;
  }
  
  public void setOrgFoundDate(Date orgFoundDate) {
    this.orgFoundDate = orgFoundDate;
  }
  
  public Date getOrgFoundDate() {
    return this.orgFoundDate;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.orgDescripte = null;
    this.orgManagerEmpId = "";
    this.orgManagerEmpName = null;
    this.orgName = null;
    this.orgSimpleName = null;
    this.orgSerial = null;
    this.orgParentOrgId = 0L;
    this.parentIdString = "";
    this.currentOrderCode = "-1";
    this.orgSort = 0;
  }
  
  public String getOrgManagerEmpId() {
    return this.orgManagerEmpId;
  }
  
  public void setOrgManagerEmpId(String orgManagerEmpId) {
    this.orgManagerEmpId = orgManagerEmpId;
  }
  
  public int getOrgHasChannel() {
    return this.orgHasChannel;
  }
  
  public void setOrgHasChannel(int orgHasChannel) {
    this.orgHasChannel = orgHasChannel;
  }
  
  public String getCurrentOrderCode() {
    return this.currentOrderCode;
  }
  
  public void setCurrentOrderCode(String currentOrderCode) {
    this.currentOrderCode = currentOrderCode;
  }
  
  public String getParentIdString() {
    return this.parentIdString;
  }
  
  public void setParentIdString(String parentIdString) {
    this.parentIdString = parentIdString;
  }
  
  public int getOrgChannelStyle() {
    return this.orgChannelStyle;
  }
  
  public void setOrgChannelStyle(int orgChannelStyle) {
    this.orgChannelStyle = orgChannelStyle;
  }
  
  public String getOrgSimpleName() {
    return this.orgSimpleName;
  }
  
  public void setOrgSimpleName(String orgSimpleName) {
    this.orgSimpleName = orgSimpleName;
  }
  
  public String getOrgSerial() {
    return this.orgSerial;
  }
  
  public void setOrgSerial(String orgSerial) {
    this.orgSerial = orgSerial;
  }
  
  public String getOrgType() {
    return this.orgType;
  }
  
  public String getOrgChannelUrl() {
    return this.orgChannelUrl;
  }
  
  public String getOrgChannelType() {
    return this.orgChannelType;
  }
  
  public void setOrgType(String orgType) {
    this.orgType = orgType;
  }
  
  public void setOrgChannelUrl(String orgChannelUrl) {
    this.orgChannelUrl = orgChannelUrl;
  }
  
  public void setOrgChannelType(String orgChannelType) {
    this.orgChannelType = orgChannelType;
  }
  
  public String getOrgIdString() {
    return this.orgIdString;
  }
  
  public void setOrgIdString(String orgIdString) {
    this.orgIdString = orgIdString;
  }
}
