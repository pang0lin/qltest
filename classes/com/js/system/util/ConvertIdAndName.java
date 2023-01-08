package com.js.system.util;

public class ConvertIdAndName {
  private EndowVO endowVO = new EndowVO();
  
  public EndowVO splitId(String commix) {
    if (commix == null || commix.equals("null"))
      return this.endowVO; 
    StringBuffer orgIdBuffer = new StringBuffer();
    StringBuffer empIdBuffer = new StringBuffer();
    StringBuffer groupIdBuffer = new StringBuffer();
    int preIndex = 0, sufIndex = 0, i = 0;
    do {
      i++;
      if (commix.startsWith("$")) {
        preIndex = commix.indexOf("$");
        String str = commix.substring(preIndex + 1);
        sufIndex = str.indexOf("$");
        empIdBuffer.append(str.substring(0, sufIndex));
        empIdBuffer.append(",");
        commix = str.substring(sufIndex + 1);
      } else if (commix.startsWith("*")) {
        preIndex = commix.indexOf("*");
        String str = commix.substring(preIndex + 1);
        sufIndex = str.indexOf("*");
        orgIdBuffer.append(str.substring(0, sufIndex));
        orgIdBuffer.append(",");
        commix = str.substring(sufIndex + 1);
      } else if (commix.startsWith("@")) {
        preIndex = commix.indexOf("@");
        String str = commix.substring(preIndex + 1);
        sufIndex = str.indexOf("@");
        groupIdBuffer.append(str.substring(0, sufIndex));
        groupIdBuffer.append(",");
        commix = str.substring(sufIndex + 1);
      } 
    } while (commix.length() > 0 && i < 50000);
    String temp = empIdBuffer.toString();
    if (temp.endsWith(","))
      temp = temp.substring(0, temp.length() - 1); 
    this.endowVO.setEmpIdArray(temp);
    temp = orgIdBuffer.toString();
    if (temp.endsWith(","))
      temp = temp.substring(0, temp.length() - 1); 
    this.endowVO.setOrgIdArray(temp);
    temp = groupIdBuffer.toString();
    if (temp.endsWith(","))
      temp = temp.substring(0, temp.length() - 1); 
    this.endowVO.setGroupIdArray(temp);
    return this.endowVO;
  }
}
