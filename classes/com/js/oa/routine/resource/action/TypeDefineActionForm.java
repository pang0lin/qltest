package com.js.oa.routine.resource.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class TypeDefineActionForm extends ActionForm {
  private Long id;
  
  private String typeDefineName;
  
  private String typeDefineMode;
  
  private String typeDefineModeName;
  
  private String con;
  
  private String done;
  
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
  
  public String getTypeDefineMode() {
    return this.typeDefineMode;
  }
  
  public void setTypeDefineMode(String typeDefineMode) {
    this.typeDefineMode = typeDefineMode;
  }
  
  public String getTypeDefineModeName() {
    if (this.typeDefineMode.equals("1"))
      return "采购进货"; 
    if (this.typeDefineMode.equals("2"))
      return "领用出库"; 
    if (this.typeDefineMode.equals("3"))
      return "物品退库"; 
    if (this.typeDefineMode.equals("4"))
      return "物品退货"; 
    return this.typeDefineModeName;
  }
  
  public void setTypeDefineModeName(String typeDefineModeName) {
    this.typeDefineModeName = typeDefineModeName;
  }
  
  public String getTypeDefineName() {
    return this.typeDefineName;
  }
  
  public void setTypeDefineName(String typeDefineName) {
    this.typeDefineName = typeDefineName;
  }
  
  public String getDone() {
    return this.done;
  }
  
  public void setDone(String done) {
    this.done = done;
  }
  
  public String getCon() {
    return this.con;
  }
  
  public void setCon(String con) {
    this.con = con;
  }
}
