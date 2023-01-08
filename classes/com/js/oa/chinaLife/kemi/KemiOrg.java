package com.js.oa.chinaLife.kemi;

public class KemiOrg {
  private String OrgID;
  
  private String Code;
  
  private String Name;
  
  private String Pcode;
  
  private String Pname;
  
  public String toString() {
    String toString = "<TB><REC><OrgID>" + 
      
      getOrgID() + "</OrgID>" + 
      "<Code>" + getCode() + "</Code>" + 
      "<Name>" + getName() + "</Name>" + 
      "<Pcode>" + getPcode() + "</Pcode>" + 
      "<Pname>" + getPname() + "</Pname>" + 
      "</REC>" + 
      "</TB>";
    return toString;
  }
  
  public String getOrgID() {
    return this.OrgID;
  }
  
  public void setOrgID(String orgID) {
    this.OrgID = orgID;
  }
  
  public String getCode() {
    return this.Code;
  }
  
  public void setCode(String code) {
    this.Code = code;
  }
  
  public String getName() {
    return this.Name;
  }
  
  public void setName(String name) {
    this.Name = name;
  }
  
  public String getPcode() {
    return this.Pcode;
  }
  
  public void setPcode(String pcode) {
    this.Pcode = pcode;
  }
  
  public String getPname() {
    return this.Pname;
  }
  
  public void setPname(String pname) {
    this.Pname = pname;
  }
}
