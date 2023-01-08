package com.js.oa.scheme.event.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class EventActionForm extends ActionForm {
  private String attendEmp;
  
  private String attendName;
  
  private String eventAddress;
  
  private String eventContent;
  
  private Integer eventFullDay = new Integer(0);
  
  private Integer eventRemind = new Integer(0);
  
  private String eventTitle;
  
  private Integer popRemindMode = new Integer(0);
  
  private Integer noteRemindMode = new Integer(0);
  
  public String getAttendEmp() {
    return this.attendEmp;
  }
  
  public void setAttendEmp(String attendEmp) {
    this.attendEmp = attendEmp;
  }
  
  public String getAttendName() {
    return this.attendName;
  }
  
  public void setAttendName(String attendName) {
    this.attendName = attendName;
  }
  
  public String getEventAddress() {
    return this.eventAddress;
  }
  
  public void setEventAddress(String eventAddress) {
    this.eventAddress = eventAddress;
  }
  
  public String getEventContent() {
    return this.eventContent;
  }
  
  public void setEventContent(String eventContent) {
    this.eventContent = eventContent;
  }
  
  public Integer getEventFullDay() {
    return this.eventFullDay;
  }
  
  public void setEventFullDay(Integer eventFullDay) {
    this.eventFullDay = eventFullDay;
  }
  
  public Integer getEventRemind() {
    return this.eventRemind;
  }
  
  public void setEventRemind(Integer eventRemind) {
    this.eventRemind = eventRemind;
  }
  
  public String getEventTitle() {
    return this.eventTitle;
  }
  
  public Integer getPopRemindMode() {
    return this.popRemindMode;
  }
  
  public Integer getNoteRemindMode() {
    return this.noteRemindMode;
  }
  
  public void setEventTitle(String eventTitle) {
    this.eventTitle = eventTitle;
  }
  
  public void setPopRemindMode(Integer popRemindMode) {
    this.popRemindMode = popRemindMode;
  }
  
  public void setNoteRemindMode(Integer noteRemindMode) {
    this.noteRemindMode = noteRemindMode;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.attendEmp = null;
    this.attendName = null;
    this.eventAddress = null;
    this.eventContent = null;
    this.eventFullDay = new Integer(0);
    this.eventRemind = new Integer(0);
    this.popRemindMode = new Integer(0);
    this.noteRemindMode = new Integer(0);
    this.eventTitle = null;
  }
}
