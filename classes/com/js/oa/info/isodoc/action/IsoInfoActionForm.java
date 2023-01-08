package com.js.oa.info.isodoc.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class IsoInfoActionForm extends ActionForm {
  private String informationName;
  
  private String informationVersion;
  
  private String documentNo;
  
  private String paperStatus;
  
  private String searchDate;
  
  private String searchDate2;
  
  private String orderBy;
  
  private String sortType;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getInformationVersion() {
    return this.informationVersion;
  }
  
  public String getInformationName() {
    return this.informationName;
  }
  
  public void setDocumentNo(String documentNo) {
    this.documentNo = documentNo;
  }
  
  public void setInformationVersion(String informationVersion) {
    this.informationVersion = informationVersion;
  }
  
  public void setInformationName(String informationName) {
    this.informationName = informationName;
  }
  
  public void setSearchDate2(String searchDate2) {
    this.searchDate2 = searchDate2;
  }
  
  public void setSearchDate(String searchDate) {
    this.searchDate = searchDate;
  }
  
  public void setPaperStatus(String paperStatus) {
    this.paperStatus = paperStatus;
  }
  
  public void setSortType(String sortType) {
    this.sortType = sortType;
  }
  
  public void setOrderBy(String orderBy) {
    this.orderBy = orderBy;
  }
  
  public String getDocumentNo() {
    return this.documentNo;
  }
  
  public String getSearchDate2() {
    return this.searchDate2;
  }
  
  public String getSearchDate() {
    return this.searchDate;
  }
  
  public String getPaperStatus() {
    return this.paperStatus;
  }
  
  public String getSortType() {
    return this.sortType;
  }
  
  public String getOrderBy() {
    return this.orderBy;
  }
}
