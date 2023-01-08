package com.js.oa.jsflow.util;

import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.vo.AccessTableVO;
import com.js.oa.jsflow.vo.ModuleVO;
import java.util.List;

public class Tools {
  public String escapeHTMLTags(String s) {
    if (s == null || s.length() == 0)
      return s; 
    StringBuffer stringbuffer = new StringBuffer(s.length() + 6);
    byte byte0 = 32;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      switch (c) {
        case '<':
          stringbuffer.append("&lt;");
          break;
        case '>':
          stringbuffer.append("&gt;");
          break;
        case '\r':
          stringbuffer.append("<br>&nbsp;&nbsp;");
          break;
        case '\'':
          stringbuffer.append("&acute;");
          break;
        case '"':
          stringbuffer.append("&quot;");
          break;
        default:
          stringbuffer.append(c);
          break;
      } 
    } 
    return stringbuffer.toString();
  }
  
  public String retrieveHTMLTags(String s) {
    if (s == null || s.length() == 0)
      return s; 
    for (int i = s.indexOf("&lt;"); i >= 0; i = s.indexOf("&lt;"))
      s = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(s.substring(0, i))))).append("<").append(s.substring(i + 4, s.length())))); 
    for (int j = s.indexOf("&gt;"); j >= 0; j = s.indexOf("&gt;"))
      s = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(s.substring(0, j))))).append(">").append(s.substring(j + 4, s.length())))); 
    for (int k = s.indexOf("<br>"); k >= 0; k = s.indexOf("<br>"))
      s = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(s.substring(0, k))))).append("\r\n").append(s.substring(k + 4, s.length())))); 
    for (int l = s.indexOf("&nbsp;"); l >= 0; l = s.indexOf("&nbsp;"))
      s = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(s.substring(0, l))))).append(" ").append(s.substring(l + 6, s.length())))); 
    for (int i1 = s.indexOf("&acute;"); i1 >= 0; i1 = s.indexOf("&acute;"))
      s = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(s.substring(0, i1))))).append('\'').append(s.substring(i1 + 6, s.length())))); 
    for (int j1 = s.indexOf("&quot;"); j1 >= 0; j1 = s.indexOf("&quot;"))
      s = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(s.substring(0, j1))))).append('"').append(s.substring(j1 + 5, s.length())))); 
    return s;
  }
  
  public String getUserOrg(String moduleId, String recordId) {
    WorkFlowBD workFlowBD = new WorkFlowBD();
    ModuleVO moduleVO = new ModuleVO();
    moduleVO.setId(Integer.parseInt(moduleId));
    moduleVO.setFormType(0);
    List<AccessTableVO> tmpList = workFlowBD.getAccessTable(moduleVO);
    AccessTableVO tableVO = new AccessTableVO();
    if (tmpList != null && tmpList.size() > 0)
      tableVO = tmpList.get(0); 
    String tableId = (new StringBuilder(String.valueOf(tableVO.getId()))).toString();
    tmpList = workFlowBD.getOperUserOrg(tableId, recordId);
    String[] org = (String[])null;
    StringBuffer orgName = new StringBuffer();
    for (int i = 0; i < tmpList.size(); i++) {
      org = (String[])tmpList.get(i);
      orgName.append(String.valueOf(org[1]) + " ");
    } 
    return orgName.toString();
  }
  
  public String getActiUserByActiName(String moduleId, String recordId, String activityName) {
    WorkFlowBD workFlowBD = new WorkFlowBD();
    ModuleVO moduleVO = new ModuleVO();
    moduleVO.setId(Integer.parseInt(moduleId));
    moduleVO.setFormType(0);
    List<AccessTableVO> tmpList = workFlowBD.getAccessTable(moduleVO);
    AccessTableVO tableVO = new AccessTableVO();
    if (tmpList != null && tmpList.size() > 0)
      tableVO = tmpList.get(0); 
    String tableId = (new StringBuilder(String.valueOf(tableVO.getId()))).toString();
    tmpList = workFlowBD.getActiUserByActiName(tableId, recordId, activityName);
    String[] emp = (String[])null;
    StringBuffer empName = new StringBuffer();
    if (tmpList != null && tmpList.size() > 0)
      for (int i = 0; i < tmpList.size(); i++) {
        emp = (String[])tmpList.get(i);
        empName.append(String.valueOf(emp[1]) + " ");
      }  
    return empName.toString();
  }
  
  public String escapFlowSymbol(String s) {
    if (s == null || "".equals(s))
      return ""; 
    return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
  }
  
  public String retrieveFlowSymbol(String s) {
    if (s == null || "".equals(s))
      return ""; 
    return s.replace("&amp;", "&").replace("&lt;", "<").replace("&gt;", ">");
  }
}
