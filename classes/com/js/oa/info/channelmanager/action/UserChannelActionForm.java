package com.js.oa.info.channelmanager.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class UserChannelActionForm extends ActionForm {
  private String userChannelName;
  
  private String userChannelId;
  
  private String userChannelOrder;
  
  private String channelReader;
  
  private String channelReadOrg;
  
  private String channelReadGroup;
  
  private String channelReadName;
  
  public String getUserChannelName() {
    return this.userChannelName;
  }
  
  public void setUserChannelName(String userChannelName) {
    this.userChannelName = userChannelName;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getUserChannelId() {
    return this.userChannelId;
  }
  
  public void setUserChannelId(String userChannelId) {
    this.userChannelId = userChannelId;
  }
  
  public String getUserChannelOrder() {
    return this.userChannelOrder;
  }
  
  public void setUserChannelOrder(String userChannelOrder) {
    this.userChannelOrder = userChannelOrder;
  }
  
  public String getChannelReader() {
    return this.channelReader;
  }
  
  public void setChannelReader(String channelReader) {
    this.channelReader = channelReader;
  }
  
  public String getChannelReadOrg() {
    return this.channelReadOrg;
  }
  
  public void setChannelReadOrg(String channelReadOrg) {
    this.channelReadOrg = channelReadOrg;
  }
  
  public String getChannelReadGroup() {
    return this.channelReadGroup;
  }
  
  public void setChannelReadGroup(String channelReadGroup) {
    this.channelReadGroup = channelReadGroup;
  }
  
  public String getChannelReadName() {
    return this.channelReadName;
  }
  
  public void setChannelReadName(String channelReadName) {
    this.channelReadName = channelReadName;
  }
}
