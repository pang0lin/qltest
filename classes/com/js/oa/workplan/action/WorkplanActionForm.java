package com.js.oa.workplan.action;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class WorkplanActionForm extends ActionForm implements Serializable {
  private int id;
  
  private String statusname;
  
  private String leadername;
  
  private String leaderid;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public final String getLeadername() {
    return this.leadername;
  }
  
  public final void setLeadername(String leadername) {
    this.leadername = leadername;
  }
  
  public final String getLeaderid() {
    return this.leaderid;
  }
  
  public final void setLeaderid(String leaderid) {
    this.leaderid = leaderid;
  }
  
  public int getId() {
    return this.id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  public String getStatusname() {
    return this.statusname;
  }
  
  public void setStatusname(String statusname) {
    this.statusname = statusname;
  }
}
