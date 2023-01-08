package com.js.oa.personalwork.setup.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class WorkProxyActionForm extends ActionForm {
  private String beginTime;
  
  private String con;
  
  private String done;
  
  private String editId;
  
  private String endTime;
  
  private String proxyEmpId;
  
  private String proxyEmpName;
  
  private String proxystate;
  
  private String proxyOrgId;
  
  private String proxyOrgName;
  
  private String proxyAllProcess;
  
  private String proxyProcess;
  
  private String empId;
  
  private String empName;
  
  private String createEmpId;
  
  private String createEmpName;
  
  private String createTime;
  
  public String getBeginTime() {
    return this.beginTime;
  }
  
  public void setBeginTime(String beginTime) {
    this.beginTime = beginTime;
  }
  
  public String getCon() {
    return this.con;
  }
  
  public void setCon(String con) {
    this.con = con;
  }
  
  public String getDone() {
    return this.done;
  }
  
  public void setDone(String done) {
    this.done = done;
  }
  
  public String getEditId() {
    return this.editId;
  }
  
  public void setEditId(String editId) {
    this.editId = editId;
  }
  
  public String getEndTime() {
    return this.endTime;
  }
  
  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }
  
  public String getProxyEmpId() {
    return this.proxyEmpId;
  }
  
  public void setProxyEmpId(String proxyEmpId) {
    this.proxyEmpId = proxyEmpId;
  }
  
  public String getProxyEmpName() {
    return this.proxyEmpName;
  }
  
  public void setProxyEmpName(String proxyEmpName) {
    this.proxyEmpName = proxyEmpName;
  }
  
  public String getProxystate() {
    return this.proxystate;
  }
  
  public void setProxystate(String proxystate) {
    this.proxystate = proxystate;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getProxyOrgId() {
    return this.proxyOrgId;
  }
  
  public void setProxyOrgId(String proxyOrgId) {
    this.proxyOrgId = proxyOrgId;
  }
  
  public String getProxyOrgName() {
    return this.proxyOrgName;
  }
  
  public void setProxyOrgName(String proxyOrgName) {
    this.proxyOrgName = proxyOrgName;
  }
  
  public String getProxyAllProcess() {
    return this.proxyAllProcess;
  }
  
  public void setProxyAllProcess(String proxyAllProcess) {
    this.proxyAllProcess = proxyAllProcess;
  }
  
  public String getProxyProcess() {
    return this.proxyProcess;
  }
  
  public void setProxyProcess(String proxyProcess) {
    this.proxyProcess = proxyProcess;
  }
  
  public String getEmpId() {
    return this.empId;
  }
  
  public void setEmpId(String empId) {
    this.empId = empId;
  }
  
  public String getEmpName() {
    return this.empName;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public String getCreateEmpId() {
    return this.createEmpId;
  }
  
  public void setCreateEmpId(String createEmpId) {
    this.createEmpId = createEmpId;
  }
  
  public String getCreateEmpName() {
    return this.createEmpName;
  }
  
  public void setCreateEmpName(String createEmpName) {
    this.createEmpName = createEmpName;
  }
  
  public String getCreateTime() {
    return this.createTime;
  }
  
  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }
}
