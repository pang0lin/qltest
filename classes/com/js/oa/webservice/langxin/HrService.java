package com.js.oa.webservice.langxin;

public class HrService {
  public String OrgAdd(String xml) {
    return (new OrgOperate()).orgAdd(xml);
  }
  
  public String OrgEdit(String xml) {
    return (new OrgOperate()).orgEdit(xml);
  }
  
  public String OrgDelete(String xml) {
    return (new OrgOperate()).orgDelete(xml);
  }
  
  public String OrgDeleteLogic(String xml) {
    return (new OrgOperate()).orgDeleteLogic(xml);
  }
  
  public String OrgMerge(String fromXml, String toXml) {
    return (new OrgOperate()).orgMerge(fromXml, toXml);
  }
  
  public String PersonAdd(String xml) {
    return (new UserOperate()).personAdd(xml);
  }
  
  public String PersonEdit(String xml) {
    return (new UserOperate()).personEdit(xml);
  }
  
  public String PersonDelete(String xml) {
    return (new UserOperate()).personDelete(xml);
  }
  
  public String PersonDeleteLogic(String xml) {
    return (new UserOperate()).personDeleteLogic(xml);
  }
}
