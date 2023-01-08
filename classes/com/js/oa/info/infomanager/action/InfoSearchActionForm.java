package com.js.oa.info.infomanager.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class InfoSearchActionForm extends ActionForm {
  private String keywordType;
  
  private String keyword;
  
  private String searchChannel;
  
  private String searchDate;
  
  private String searchBeginDate;
  
  private String searchEndDate;
  
  private String searchIssuerId;
  
  private String searchIssuerName;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getKeywordType() {
    return this.keywordType;
  }
  
  public void setKeywordType(String keywordType) {
    this.keywordType = keywordType;
  }
  
  public String getKeyword() {
    return this.keyword;
  }
  
  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }
  
  public String getSearchChannel() {
    return this.searchChannel;
  }
  
  public void setSearchChannel(String searchChannel) {
    this.searchChannel = searchChannel;
  }
  
  public String getSearchDate() {
    return this.searchDate;
  }
  
  public void setSearchDate(String searchDate) {
    this.searchDate = searchDate;
  }
  
  public String getSearchBeginDate() {
    return this.searchBeginDate;
  }
  
  public void setSearchBeginDate(String searchBeginDate) {
    this.searchBeginDate = searchBeginDate;
  }
  
  public String getSearchEndDate() {
    return this.searchEndDate;
  }
  
  public void setSearchEndDate(String searchEndDate) {
    this.searchEndDate = searchEndDate;
  }
  
  public String getSearchIssuerId() {
    return this.searchIssuerId;
  }
  
  public void setSearchIssuerId(String searchIssuerId) {
    this.searchIssuerId = searchIssuerId;
  }
  
  public String getSearchIssuerName() {
    return this.searchIssuerName;
  }
  
  public void setSearchIssuerName(String searchIssuerName) {
    this.searchIssuerName = searchIssuerName;
  }
}
