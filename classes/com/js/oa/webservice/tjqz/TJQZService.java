package com.js.oa.webservice.tjqz;

public class TJQZService {
  public String addOrganization(String xml) {
    return (new OrgOperate()).orgAdd(xml);
  }
  
  public String delOrganization(String xml) {
    return (new OrgOperate()).orgDeleteLogic(xml);
  }
  
  public String updateOrganization(String xml) {
    return (new OrgOperate()).orgEdit(xml);
  }
  
  public String addEmployee(String xml) {
    return (new UserOperate()).personAdd(xml);
  }
  
  public String delEmployee(String xml) {
    return (new UserOperate()).personDeleteLogic(xml);
  }
  
  public String updateEmployee(String xml) {
    return (new UserOperate()).personEdit(xml);
  }
  
  public String getDataInfoList(String xml) {
    return (new DataInfoList()).getDataInfoList(xml);
  }
}
