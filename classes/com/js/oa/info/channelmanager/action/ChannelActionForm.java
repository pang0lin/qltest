package com.js.oa.info.channelmanager.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ChannelActionForm extends ActionForm {
  private String searchChannelType;
  
  private String searchChannelName;
  
  private String channelName;
  
  private String channelType;
  
  private String channelParentId;
  
  private String channelOrderId;
  
  private String radiobutton;
  
  private String channelIssuer;
  
  private String channelIssuerOrg;
  
  private String channelIssuerGroup;
  
  private String channelReader;
  
  private String channelReaderOrg;
  
  private String channelReaderGroup;
  
  private String channelId;
  
  private String issuerId;
  
  private String readerId;
  
  private String channelIssuerName;
  
  private String channelReaderName;
  
  private String channelShowType;
  
  private String includeChild;
  
  private String channelManagerOrg;
  
  private String channelManagerGroup;
  
  private String channelManagerName;
  
  private String channelManager;
  
  private String managerId;
  
  private String isAllowReview;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.includeChild = "0";
    this.isAllowReview = "0";
  }
  
  public String getSearchChannelType() {
    return this.searchChannelType;
  }
  
  public void setSearchChannelType(String searchChannelType) {
    this.searchChannelType = searchChannelType;
  }
  
  public String getSearchChannelName() {
    return this.searchChannelName;
  }
  
  public void setSearchChannelName(String searchChannelName) {
    this.searchChannelName = searchChannelName;
  }
  
  public String getChannelName() {
    return this.channelName;
  }
  
  public void setChannelName(String channelName) {
    this.channelName = channelName;
  }
  
  public String getChannelType() {
    return this.channelType;
  }
  
  public void setChannelType(String channelType) {
    this.channelType = channelType;
  }
  
  public String getChannelParentId() {
    return this.channelParentId;
  }
  
  public void setChannelParentId(String channelParentId) {
    this.channelParentId = channelParentId;
  }
  
  public String getChannelOrderId() {
    return this.channelOrderId;
  }
  
  public void setChannelOrderId(String channelOrderId) {
    this.channelOrderId = channelOrderId;
  }
  
  public String getRadiobutton() {
    return this.radiobutton;
  }
  
  public void setRadiobutton(String radiobutton) {
    this.radiobutton = radiobutton;
  }
  
  public String getChannelIssuer() {
    return this.channelIssuer;
  }
  
  public void setChannelIssuer(String channelIssuer) {
    this.channelIssuer = channelIssuer;
  }
  
  public String getChannelIssuerOrg() {
    return this.channelIssuerOrg;
  }
  
  public void setChannelIssuerOrg(String channelIssuerOrg) {
    this.channelIssuerOrg = channelIssuerOrg;
  }
  
  public String getChannelIssuerGroup() {
    return this.channelIssuerGroup;
  }
  
  public void setChannelIssuerGroup(String channelIssuerGroup) {
    this.channelIssuerGroup = channelIssuerGroup;
  }
  
  public String getChannelReader() {
    return this.channelReader;
  }
  
  public void setChannelReader(String channelReader) {
    this.channelReader = channelReader;
  }
  
  public String getChannelReaderOrg() {
    return this.channelReaderOrg;
  }
  
  public void setChannelReaderOrg(String channelReaderOrg) {
    this.channelReaderOrg = channelReaderOrg;
  }
  
  public String getChannelReaderGroup() {
    return this.channelReaderGroup;
  }
  
  public void setChannelReaderGroup(String channelReaderGroup) {
    this.channelReaderGroup = channelReaderGroup;
  }
  
  public String getChannelId() {
    return this.channelId;
  }
  
  public void setChannelId(String channelId) {
    this.channelId = channelId;
  }
  
  public String getIssuerId() {
    return this.issuerId;
  }
  
  public void setIssuerId(String issuerId) {
    this.issuerId = issuerId;
  }
  
  public String getReaderId() {
    return this.readerId;
  }
  
  public void setReaderId(String readerId) {
    this.readerId = readerId;
  }
  
  public String getChannelIssuerName() {
    return this.channelIssuerName;
  }
  
  public void setChannelIssuerName(String channelIssuerName) {
    this.channelIssuerName = channelIssuerName;
  }
  
  public String getChannelReaderName() {
    return this.channelReaderName;
  }
  
  public void setChannelReaderName(String channelReaderName) {
    this.channelReaderName = channelReaderName;
  }
  
  public String getChannelShowType() {
    return this.channelShowType;
  }
  
  public void setChannelShowType(String channelShowType) {
    this.channelShowType = channelShowType;
  }
  
  public String getIncludeChild() {
    return this.includeChild;
  }
  
  public void setIncludeChild(String includeChild) {
    this.includeChild = includeChild;
  }
  
  public String getChannelManagerOrg() {
    return this.channelManagerOrg;
  }
  
  public void setChannelManagerOrg(String channelManagerOrg) {
    this.channelManagerOrg = channelManagerOrg;
  }
  
  public String getChannelManagerGroup() {
    return this.channelManagerGroup;
  }
  
  public void setChannelManagerGroup(String channelManagerGroup) {
    this.channelManagerGroup = channelManagerGroup;
  }
  
  public String getChannelManagerName() {
    return this.channelManagerName;
  }
  
  public void setChannelManagerName(String channelManagerName) {
    this.channelManagerName = channelManagerName;
  }
  
  public String getChannelManager() {
    return this.channelManager;
  }
  
  public void setChannelManager(String channelManager) {
    this.channelManager = channelManager;
  }
  
  public String getManagerId() {
    return this.managerId;
  }
  
  public void setManagerId(String managerId) {
    this.managerId = managerId;
  }
  
  public String getIsAllowReview() {
    return this.isAllowReview;
  }
  
  public void setIsAllowReview(String isAllowReview) {
    this.isAllowReview = isAllowReview;
  }
}
