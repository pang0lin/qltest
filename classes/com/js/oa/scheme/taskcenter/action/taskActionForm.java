package com.js.oa.scheme.taskcenter.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class taskActionForm extends ActionForm {
  private String className;
  
  private String taskTitle;
  
  private Long taskChecker;
  
  private String taskCheckerName;
  
  private Long taskPrincipal;
  
  private String taskPrincipalName;
  
  private Integer taskPriority;
  
  private Integer taskStatus;
  
  private String taskJoinedEmp;
  
  private String taskJoinedEmpName;
  
  private String taskJoineOrg;
  
  private String taskJoinOrgName;
  
  private String ifAwake;
  
  private String taskDescription;
  
  private String taskAppend;
  
  private String taskCancelReason;
  
  private String useScope;
  
  private String useScopeId;
  
  private String orderCode;
  
  public String getOrderCode() {
    return this.orderCode;
  }
  
  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }
  
  public String getClassName() {
    return this.className;
  }
  
  public void setClassName(String className) {
    this.className = className;
  }
  
  public String getTaskTitle() {
    return this.taskTitle;
  }
  
  public void setTaskTitle(String taskTitle) {
    this.taskTitle = taskTitle;
  }
  
  public Long getTaskChecker() {
    return this.taskChecker;
  }
  
  public void setTaskChecker(Long taskChecker) {
    this.taskChecker = taskChecker;
  }
  
  public String getTaskCheckerName() {
    return this.taskCheckerName;
  }
  
  public void setTaskCheckerName(String taskCheckerName) {
    this.taskCheckerName = taskCheckerName;
  }
  
  public Long getTaskPrincipal() {
    return this.taskPrincipal;
  }
  
  public void setTaskPrincipal(Long taskPrincipal) {
    this.taskPrincipal = taskPrincipal;
  }
  
  public String getTaskPrincipalName() {
    return this.taskPrincipalName;
  }
  
  public void setTaskPrincipalName(String taskPrincipalName) {
    this.taskPrincipalName = taskPrincipalName;
  }
  
  public Integer getTaskPriority() {
    return this.taskPriority;
  }
  
  public void setTaskPriority(Integer taskPriority) {
    this.taskPriority = taskPriority;
  }
  
  public Integer getTaskStatus() {
    return this.taskStatus;
  }
  
  public void setTaskStatus(Integer taskStatus) {
    this.taskStatus = taskStatus;
  }
  
  public String getTaskJoinedEmp() {
    return this.taskJoinedEmp;
  }
  
  public void setTaskJoinedEmp(String taskJoinedEmp) {
    this.taskJoinedEmp = taskJoinedEmp;
  }
  
  public String getTaskJoinedEmpName() {
    return this.taskJoinedEmpName;
  }
  
  public void setTaskJoinedEmpName(String taskJoinedEmpName) {
    this.taskJoinedEmpName = taskJoinedEmpName;
  }
  
  public String getTaskJoineOrg() {
    return this.taskJoineOrg;
  }
  
  public void setTaskJoineOrg(String taskJoineOrg) {
    this.taskJoineOrg = taskJoineOrg;
  }
  
  public String getTaskJoinOrgName() {
    return this.taskJoinOrgName;
  }
  
  public void setTaskJoinOrgName(String taskJoinOrgName) {
    this.taskJoinOrgName = taskJoinOrgName;
  }
  
  public String getIfAwake() {
    return this.ifAwake;
  }
  
  public void setIfAwake(String ifAwake) {
    this.ifAwake = ifAwake;
  }
  
  public String getTaskDescription() {
    return this.taskDescription;
  }
  
  public void setTaskDescription(String taskDescription) {
    this.taskDescription = taskDescription;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.className = null;
    this.taskTitle = null;
    this.taskChecker = null;
    this.taskCheckerName = null;
    this.taskPrincipal = null;
    this.taskPrincipalName = null;
    this.taskPriority = new Integer(0);
    this.taskStatus = new Integer(0);
    this.taskJoinedEmp = null;
    this.taskJoinedEmpName = null;
    this.taskJoineOrg = null;
    this.taskJoinOrgName = null;
    this.ifAwake = null;
    this.taskDescription = null;
    this.taskAppend = null;
  }
  
  public String getTaskAppend() {
    return this.taskAppend;
  }
  
  public String getTaskCancelReason() {
    return this.taskCancelReason;
  }
  
  public String getUseScope() {
    return this.useScope;
  }
  
  public String getUseScopeId() {
    return this.useScopeId;
  }
  
  public void setTaskAppend(String taskAppend) {
    this.taskAppend = taskAppend;
  }
  
  public void setTaskCancelReason(String taskCancelReason) {
    this.taskCancelReason = taskCancelReason;
  }
  
  public void setUseScope(String useScope) {
    this.useScope = useScope;
  }
  
  public void setUseScopeId(String useScopeId) {
    this.useScopeId = useScopeId;
  }
}
