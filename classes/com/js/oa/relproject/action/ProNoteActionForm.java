package com.js.oa.relproject.action;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ProNoteActionForm extends ActionForm {
  private Long id;
  
  private Long projectId;
  
  private Long empId;
  
  private String empName;
  
  private String title;
  
  private String content;
  
  private Date sendTime = null;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getTitle() {
    return this.title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public Long getProjectId() {
    return this.projectId;
  }
  
  public void setProjectId(Long projectId) {
    this.projectId = projectId;
  }
  
  public Long getEmpId() {
    return this.empId;
  }
  
  public void setEmpId(Long empId) {
    this.empId = empId;
  }
  
  public String getEmpName() {
    return this.empName;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public String getContent() {
    return this.content;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
  
  public Date getSendTime() {
    return this.sendTime;
  }
  
  public void setSendTime(Date sendTime) {
    this.sendTime = sendTime;
  }
}
