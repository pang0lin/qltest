package com.js.message.lava;

import java.util.List;

public class GKOrganization {
  private GKUtilClass gKUtilClass = new GKUtilClass();
  
  public void close() {
    this.gKUtilClass.close();
  }
  
  public boolean addOrganization(Organization organization) {
    boolean flag = false;
    String str = 
      "<request type='ug' subtype='addug' msid=''>\r<message>\r<ug \rcode='" + 

      
      organization.getCode() + "' \r" + 
      "name='" + organization.getName() + "' \r" + 
      "parent_code='" + organization.getParentcode() + "' \r" + 
      "sign='" + organization.getSign() + "' \r" + 
      "location='" + organization.getLocation() + "' \r" + 
      "email='" + organization.getEmail() + "' \r" + 
      "remark='" + organization.getRemark() + "' /> \r" + 
      "</message> \r" + 
      "</request> \r";
    flag = this.gKUtilClass.getFlag(str);
    if (!flag) {
      System.out.print("-Error code=" + organization.getCode());
      System.out.println("-Error name=" + organization.getName());
    } 
    return flag;
  }
  
  public boolean delOrganization(String code) {
    boolean flag = false;
    if (code == null || "".equals(code))
      return flag; 
    String str = 
      "<request type='ug' subtype='delug' msid=''><message><ug code='" + 
      code + "'/></message></request>";
    flag = this.gKUtilClass.getFlag(str);
    return flag;
  }
  
  public boolean modOrganization(Organization organization) {
    boolean flag = false;
    String str = 
      "<request type='ug' subtype='updug' msid=''>\r<message>\r<ug \rcode='" + 

      
      organization.getCode() + "' \r" + 
      "name='" + organization.getName() + "' \r" + 
      "parent_code='" + organization.getParentcode() + "' \r" + 
      "sign='" + organization.getSign() + "' \r" + 
      "location='" + organization.getLocation() + "' \r" + 
      "email='" + organization.getEmail() + "' \r" + 
      "remark='" + organization.getRemark() + "' /> \r" + 
      "</message> \r" + 
      "</request> \r";
    flag = this.gKUtilClass.getFlag(str);
    return flag;
  }
  
  public boolean saveOrganization(Organization organization) {
    boolean flag = false;
    flag = addOrganization(organization);
    return flag;
  }
  
  public boolean organizationIsExist(String orgId) {
    boolean rs = false;
    String str = 
      "<request type='ug' subtype='getuginfo' msid=''><message><ug code='" + 
      orgId + "'/></message></request>";
    try {
      if (this.gKUtilClass.getFlag(str))
        rs = true; 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return rs;
  }
  
  public List quaryOrganization(String code) {
    String str = 
      "<request type='ug' subtype='getuginfo' msid=''><message><ug code='" + 
      code + "'/></message></request>";
    List list = null;
    return list;
  }
  
  public List quarySubOrganization(String code) {
    String str = 
      "<request type='ug' subtype='getchildug' msid=''><message><ug code='" + 
      code + "'/></message></request>";
    List list = null;
    return list;
  }
  
  public boolean orgSignUser(String code, String account) {
    boolean flag = false;
    String str = 
      "<request type='ug' subtype='addusertoug' msid=''><message><ug code='" + 
      code + "'/>" + 
      "<user account='" + account + "'/></message></request>";
    flag = this.gKUtilClass.getFlag(str);
    return flag;
  }
  
  public boolean orgCancelUser(String code, String account) {
    boolean flag = false;
    String str = 
      "<request type='ug' subtype='deluserfromug' msid=''><message><ug code='" + 
      code + "'/>" + 
      "<user account='" + account + "'/></message>" + 
      "</request>";
    flag = this.gKUtilClass.getFlag(str);
    return flag;
  }
  
  public List quaryOrgSubUser(String code) {
    String str = 
      "<request type='ug' subtype='getchilduser' msid=''><message><ug code='" + 
      code + "'/></message></request>";
    List list = null;
    return list;
  }
  
  public List quaryOrgUser(String account) {
    String str = 
      "<request type='ug' subtype='getuserugs' msid=''><message><user account='" + 
      account + "'/></message>" + 
      "</request>";
    List list = null;
    return list;
  }
}
