package com.js.oa.hr.personnelmanager.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class TrainClassActionForm extends ActionForm {
  private Long id;
  
  private String trainName;
  
  private String trainDescribe;
  
  private String trainContent;
  
  private String trainAim;
  
  private Long domain;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public Long getDomain() {
    return this.domain;
  }
  
  public void setDomain(Long domain) {
    this.domain = domain;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getTrainAim() {
    return this.trainAim;
  }
  
  public void setTrainAim(String trainAim) {
    this.trainAim = trainAim;
  }
  
  public String getTrainContent() {
    return this.trainContent;
  }
  
  public void setTrainContent(String trainContent) {
    this.trainContent = trainContent;
  }
  
  public String getTrainDescribe() {
    return this.trainDescribe;
  }
  
  public void setTrainDescribe(String trainDescribe) {
    this.trainDescribe = trainDescribe;
  }
  
  public String getTrainName() {
    return this.trainName;
  }
  
  public void setTrainName(String trainName) {
    this.trainName = trainName;
  }
}
