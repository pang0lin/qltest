package com.js.oa.scheme.util;

public class ConversionString {
  private String orgIdString;
  
  private String groupIdString;
  
  private String userIdString;
  
  private String orgString;
  
  private String groupString;
  
  private String userString;
  
  public ConversionString(String originIdString) {
    this.orgIdString = "";
    this.groupIdString = "";
    this.userIdString = "";
    this.orgString = "";
    this.groupString = "";
    this.userString = "";
    split(originIdString);
    splitByChar(originIdString);
  }
  
  public String getOrgIdString() {
    if (this.orgIdString.equals(""))
      return ""; 
    return this.orgIdString.substring(0, this.orgIdString.length() - 1);
  }
  
  public String getGroupIdString() {
    if (this.groupIdString.equals(""))
      return ""; 
    return this.groupIdString.substring(0, this.groupIdString.length() - 1);
  }
  
  public String getUserIdString() {
    if (this.userIdString.equals(""))
      return ""; 
    return this.userIdString.substring(0, this.userIdString.length() - 1);
  }
  
  private void split(String originIdString) {
    int start = 0;
    int end = 0;
    char[] charArray = originIdString.toCharArray();
    String tempStr = "";
    for (int i = 0; i < charArray.length; i++) {
      tempStr = String.valueOf(tempStr) + charArray[i];
      end++;
      if (charArray[i] == '$' && start + 1 != end) {
        tempStr = tempStr.substring(1, tempStr.length() - 1);
        this.userIdString = String.valueOf(this.userIdString) + tempStr + ",";
        tempStr = "";
        start = end;
      } else if (charArray[i] == '*' && start + 1 != end) {
        tempStr = tempStr.substring(1, tempStr.length() - 1);
        this.orgIdString = String.valueOf(this.orgIdString) + tempStr + ",";
        tempStr = "";
        start = end;
      } else if (charArray[i] == '@' && start + 1 != end) {
        tempStr = tempStr.substring(1, tempStr.length() - 1);
        this.groupIdString = String.valueOf(this.groupIdString) + tempStr + ",";
        tempStr = "";
        start = end;
      } 
    } 
  }
  
  private void splitByChar(String originIdString) {
    int start = 0;
    int end = 0;
    char[] charArray = originIdString.toCharArray();
    String tempStr = "";
    for (int i = 0; i < charArray.length; i++) {
      tempStr = String.valueOf(tempStr) + charArray[i];
      end++;
      if (charArray[i] == '$' && start + 1 != end) {
        tempStr = tempStr.substring(0, tempStr.length());
        this.userString = String.valueOf(this.userString) + tempStr;
        tempStr = "";
        start = end;
      } else if (charArray[i] == '*' && start + 1 != end) {
        tempStr = tempStr.substring(0, tempStr.length());
        this.orgString = String.valueOf(this.orgString) + tempStr;
        tempStr = "";
        start = end;
      } else if (charArray[i] == '@' && start + 1 != end) {
        tempStr = tempStr.substring(0, tempStr.length());
        this.groupString = String.valueOf(this.groupString) + tempStr;
        tempStr = "";
        start = end;
      } 
    } 
  }
  
  public String getOrgString() {
    return this.orgString;
  }
  
  public String getGroupString() {
    return this.groupString;
  }
  
  public String getUserString() {
    return this.userString;
  }
}
