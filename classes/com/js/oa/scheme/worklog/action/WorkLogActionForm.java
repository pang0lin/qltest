package com.js.oa.scheme.worklog.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class WorkLogActionForm extends ActionForm {
  private String classGroupRange;
  
  private String className;
  
  private String classOrgRange;
  
  private String classRange;
  
  private String classRangeID;
  
  private String classUserRange;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private String projectCode;
  
  private String hasProjectTask;
  
  private String stepName;
  
  private String projectName;
  
  private String projectRange;
  
  private String projectRangeID;
  
  private String projectUserRange;
  
  private String projectOrgRange;
  
  private String projectGroupRange;
  
  private Integer projectStatus;
  
  private String task_code;
  
  private String task_fathercode;
  
  private String task_name;
  
  private String task_description;
  
  private int task_level;
  
  public String getClassGroupRange() {
    return this.classGroupRange;
  }
  
  public void setClassGroupRange(String classGroupRange) {
    this.classGroupRange = classGroupRange;
  }
  
  public String getClassName() {
    return this.className;
  }
  
  public void setClassName(String className) {
    this.className = className;
  }
  
  public String getClassOrgRange() {
    return this.classOrgRange;
  }
  
  public void setClassOrgRange(String classOrgRange) {
    this.classOrgRange = classOrgRange;
  }
  
  public String getClassRange() {
    return this.classRange;
  }
  
  public void setClassRange(String classRange) {
    this.classRange = classRange;
  }
  
  public String getClassRangeID() {
    return this.classRangeID;
  }
  
  public void setClassRangeID(String classRangeID) {
    this.classRangeID = classRangeID;
  }
  
  public String getClassUserRange() {
    return this.classUserRange;
  }
  
  public void setClassUserRange(String classUserRange) {
    this.classUserRange = classUserRange;
  }
  
  public Long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public void setCreatedEmp(Long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public Long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(Long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public String getStepName() {
    return this.stepName;
  }
  
  public void setStepName(String stepName) {
    this.stepName = stepName;
  }
  
  public String getProjectGroupRange() {
    return this.projectGroupRange;
  }
  
  public void setProjectGroupRange(String projectGroupRange) {
    this.projectGroupRange = projectGroupRange;
  }
  
  public String getProjectName() {
    return this.projectName;
  }
  
  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }
  
  public String getProjectOrgRange() {
    return this.projectOrgRange;
  }
  
  public void setProjectOrgRange(String projectOrgRange) {
    this.projectOrgRange = projectOrgRange;
  }
  
  public String getProjectRange() {
    return this.projectRange;
  }
  
  public void setProjectRange(String projectRange) {
    this.projectRange = projectRange;
  }
  
  public String getProjectRangeID() {
    return this.projectRangeID;
  }
  
  public void setProjectRangeID(String projectRangeID) {
    this.projectRangeID = projectRangeID;
  }
  
  public Integer getProjectStatus() {
    return this.projectStatus;
  }
  
  public void setProjectStatus(Integer projectStatus) {
    this.projectStatus = projectStatus;
  }
  
  public String getProjectUserRange() {
    return this.projectUserRange;
  }
  
  public String getProjectCode() {
    return this.projectCode;
  }
  
  public String getHasProjectTask() {
    return this.hasProjectTask;
  }
  
  public String getTask_name() {
    return this.task_name;
  }
  
  public String getTask_fathercode() {
    return this.task_fathercode;
  }
  
  public String getTask_code() {
    return this.task_code;
  }
  
  public String getTask_description() {
    return this.task_description;
  }
  
  public int getTask_level() {
    return this.task_level;
  }
  
  public void setProjectUserRange(String projectUserRange) {
    this.projectUserRange = projectUserRange;
  }
  
  public void setProjectCode(String projectCode) {
    this.projectCode = projectCode;
  }
  
  public void setHasProjectTask(String hasProjectTask) {
    this.hasProjectTask = hasProjectTask;
  }
  
  public void setTask_name(String task_name) {
    this.task_name = task_name;
  }
  
  public void setTask_fathercode(String task_fathercode) {
    this.task_fathercode = task_fathercode;
  }
  
  public void setTask_code(String task_code) {
    this.task_code = task_code;
  }
  
  public void setTask_description(String task_description) {
    this.task_description = task_description;
  }
  
  public void setTask_level(int task_level) {
    this.task_level = task_level;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.classGroupRange = null;
    this.className = null;
    this.classOrgRange = null;
    this.classRange = null;
    this.classRangeID = null;
    this.classUserRange = null;
    this.createdEmp = null;
    this.createdOrg = null;
    this.stepName = null;
    this.projectName = null;
    this.projectRange = null;
    this.projectRange = null;
    this.projectRangeID = null;
    this.projectUserRange = null;
    this.projectOrgRange = null;
    this.projectGroupRange = null;
    this.projectStatus = new Integer(1);
  }
}
