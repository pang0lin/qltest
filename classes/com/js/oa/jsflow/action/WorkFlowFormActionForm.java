package com.js.oa.jsflow.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class WorkFlowFormActionForm extends ActionForm {
  private String processName;
  
  private String tableId;
  
  private String processId;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getProcessName() {
    return this.processName;
  }
  
  public void setProcessName(String processName) {
    this.processName = processName;
  }
  
  public String getTableId() {
    return this.tableId;
  }
  
  public void setTableId(String tableId) {
    this.tableId = tableId;
  }
  
  public String getProcessId() {
    return this.processId;
  }
  
  public void setProcessId(String processId) {
    this.processId = processId;
  }
}
