package com.js.oa.module.util;

import com.js.oa.module.service.ModuleMenuService;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class CommPagesGeneration {
  private String searchSql;
  
  private String listSql;
  
  private boolean logSwitch = true;
  
  private ModuleMenuService bd = null;
  
  private static Logger logger = Logger.getLogger(CommPagesGeneration.class
      .getName());
  
  public void setSearchSql(String searchSql) {
    this.searchSql = searchSql;
  }
  
  public String getSearchSql() {
    return this.searchSql;
  }
  
  public String generateSearchPart(String domainId, String menuSearchBound, HttpServletRequest request) throws Exception {
    if (menuSearchBound != null)
      try {
        ModuleMenuService bd = new ModuleMenuService();
        String[][] fieldsMap = tokenFieldsForSearch(menuSearchBound, true);
        return concateControlsIntoSearchSement(fieldsMap);
      } catch (Exception ex) {
        ex.printStackTrace();
        throw ex;
      }  
    return "";
  }
  
  public String generHtmlTable(List<Object[]> list) {
    StringBuffer sb = new StringBuffer("");
    if (list != null && list.size() > 0) {
      sb.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" class=\"listTable outTopline\">");
      sb.append("<tr><td class=\"listTableHead\" width=\"8%\" nowrap>序号</td><td class=\"listTableHead\">子菜单名称</td><td class=\"listTableHead\" width=\"8%\" nowrap>操作</td></tr>");
      String listClass = "listTableLine1";
      String menuLevel = "";
      String space = "";
      for (int i = 0; i < list.size(); i++) {
        menuLevel = "";
        space = "";
        Object[] obj = list.get(i);
        if (i % 2 != 0) {
          listClass = "listTableLine2";
        } else {
          listClass = "listTableLine1";
        } 
        sb.append("<tr>");
        sb.append("<td class='" + listClass + "'>" + 
            "<input type=\"checkbox\" style=\"cursor:'hand'\" name=\"batchDel\" value='" + obj[0] + "'>" + (i + 1) + "</td>");
        menuLevel = obj[4].toString();
        for (int j = 0; j < menuLevel.length(); j++) {
          if (menuLevel.charAt(j) == '-')
            space = String.valueOf(space) + "&nbsp;&nbsp;&nbsp;&nbsp;"; 
        } 
        sb.append("<td class='" + listClass + "'>" + space + obj[1] + "</td>");
        sb.append("<td class='" + listClass + " listTableLineLastTD'><img  style=\"cursor:hand\" border=\"0\" src=\"images/modi.gif\" alt=\"修改\" onclick=\"editCustome('" + obj[0] + "','" + obj[4] + "')\">" + 
            "<img  style=\"cursor:hand\" border=\"0\" src=\"images/del.gif\" alt=\"删除\" onclick=\"delCustome('" + obj[0] + "','" + obj[4] + "')\" >" + 
            "</td>");
        sb.append("</tr>");
      } 
      sb.append("</table>");
    } 
    return sb.toString();
  }
  
  public String[][] getCustomerSearchFields(String domainId, String menuSearchBound) {
    if (this.bd == null)
      this.bd = new ModuleMenuService(); 
    String[][] list = this.bd.getQueryFields(domainId, menuSearchBound);
    if (list != null && list.length > 0) {
      this.searchSql = list[0][1].toString();
      this.listSql = list[0][0].toString();
    } 
    return list;
  }
  
  private boolean saveSegMent(String segmentStr, String segmentName, HttpServletRequest request) throws Exception {
    FileWriter fw = null;
    boolean flag = false;
    try {
      String path = String.valueOf(request.getContextPath()) + "/customize/template";
      File f = new File(path, segmentName);
      if (f.exists()) {
        f.delete();
      } else {
        f.createNewFile();
      } 
      fw = new FileWriter(f);
      fw.write(segmentStr);
      fw.flush();
      flag = true;
    } catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    } finally {}
    fw.close();
    return flag;
  }
  
  public String[][] tokenFieldsForSearch(String searchSql, boolean flag) throws Exception {
    if (searchSql != null && searchSql.length() > 0) {
      String[][] retStr = (String[][])null;
      try {
        StringTokenizer st = null;
        if (flag) {
          String tmpStr = searchSql.toLowerCase();
          tmpStr = tmpStr.substring(tmpStr.indexOf("select") + 
              "select".length(), 
              tmpStr.lastIndexOf("from"));
          st = new StringTokenizer(tmpStr, ",");
        } else {
          st = new StringTokenizer(searchSql, ",");
        } 
        retStr = new String[st.countTokens()][5];
        int i = 0;
        while (st.hasMoreTokens()) {
          String tblAndField = (String)st.nextElement();
          if (tblAndField != null && tblAndField.length() > 0) {
            StringTokenizer stInn = new StringTokenizer(tblAndField, 
                ".");
            retStr[i][0] = stInn.nextToken().trim();
            retStr[i][1] = stInn.nextToken().trim();
            retStr[i][2] = tblAndField.trim();
            i++;
          } 
        } 
        confimgFieldsDispType(retStr);
        return retStr;
      } catch (Exception ex) {
        ex.printStackTrace();
        throw ex;
      } 
    } 
    return null;
  }
  
  private void confimgFieldsDispType(String[][] retStr) {
    if (retStr != null && retStr.length > 0) {
      if (this.bd == null)
        this.bd = new ModuleMenuService(); 
      String[][] list = this.bd.getFieldsTypes(pickupFieldNames(retStr));
      if (list != null && list.length > 0)
        for (int i = 0; i < retStr.length; i++) {
          for (int j = 0; j < list.length; j++) {
            if (list[j][0].equals(retStr[i][1])) {
              retStr[i][3] = list[j][1];
              retStr[i][4] = list[j][2];
              break;
            } 
          } 
        }  
    } 
  }
  
  private String pickupFieldNames(String[][] retStr) {
    String retNames = "";
    if (retStr != null && retStr.length > 0)
      for (int i = 0; i < retStr.length; i++)
        retNames = String.valueOf(retNames) + "'" + retStr[i][1] + "',";  
    return retNames.substring(0, retNames.lastIndexOf(","));
  }
  
  private String concateControlsIntoSearchSement(String[][] fieldsMap) throws Exception {
    if (fieldsMap == null || fieldsMap.length <= 0)
      throw new Exception("fieldsMap is null"); 
    String segMentTableBe = "<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"1\" cellspacing=\"1\"> ";
    String segMentTableEn = "</table>";
    String segMentMid = "";
    int i = 0;
    while (i < fieldsMap.length) {
      segMentMid = String.valueOf(segMentMid) + "<tr align='center'><td align='right'><font color=\"#FFFFFF\">" + 
        
        fieldsMap[i][4] + ":" + 
        "</font></td>" + 
        "<td align='left'><font color=\"#FFFFFF\">" + 
        getControlFromShowID(fieldsMap[i][3], fieldsMap[i][1]) + 
        " " + 
        "</font></td>";
      i++;
      if (i < fieldsMap.length) {
        segMentMid = String.valueOf(segMentMid) + "<td align='left'><font color=\"#FFFFFF\">" + 
          fieldsMap[i][4] + ":" + 
          "</font></td>" + 
          "<td align='left'><font color=\"#FFFFFF\">" + 
          getControlFromShowID(fieldsMap[i][3], fieldsMap[i][1]) + 
          "</font></td>";
        i++;
      } 
      segMentMid = String.valueOf(segMentMid) + "</tr>";
    } 
    segMentMid = String.valueOf(segMentMid) + "<tr><td colspan='4' align='right'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='button' value='搜索' onclick='searchAction();'/>&nbsp;<input type='button' value='取消' onclick='cancle();'/></td></tr>";
    logInfo("concateControlsIntoSearchSement", 
        String.valueOf(segMentTableBe) + segMentMid + segMentTableEn);
    return String.valueOf(segMentTableBe) + segMentMid + segMentTableEn;
  }
  
  private String getControlFromShowID(String showId, String fieldId) {
    if (showId != null && showId.length() > 0) {
      String htmlStr = "";
      if (showId.equals("101")) {
        htmlStr = "<input type='text' id='" + fieldId + "'/>";
      } else if (showId.equals("103")) {
        htmlStr = "<input type='radio' id='" + fieldId + "'/>";
      } else if (showId.equals("104")) {
        htmlStr = "<input type='checkbox' id='" + fieldId + "'/>";
      } else if (showId.equals("105")) {
        htmlStr = "<select id='" + fieldId + "'/>";
      } else if (showId.equals("107")) {
        htmlStr = "<script language=javascript> var dtpDate = createDatePicker('" + 
          fieldId + 
          "' ,<%=year%>,<%=month%>,<%=day%>); </script>";
      } else if (showId.equals("108")) {
        htmlStr = "<script language=javascript> var dtpDate = createDatePicker('" + 
          fieldId + 
          "' ,<%=year%>,<%=month%>,<%=day%>); </script>";
      } else if (showId.equals("109")) {
        htmlStr = "<script language=javascript> var dtpDate = createDatePicker('" + 
          fieldId + 
          "' ,<%=year%>,<%=month%>,<%=day%>); </script>";
      } else if (showId.equals("110")) {
        htmlStr = "<textarea id='" + fieldId + "' rows='' cols=''/>";
      } 
      return htmlStr;
    } 
    return null;
  }
  
  private String appendId(String fieldMapControl, String fieldId) {
    if (fieldMapControl != null && fieldId != null) {
      fieldMapControl = fieldMapControl.toLowerCase();
      String midStr = fieldMapControl.substring(0, 
          fieldMapControl.indexOf("input") + 
          "input".length());
      midStr = String.valueOf(midStr) + " id='" + fieldId + "' " + " name='" + fieldId + "'";
      midStr = String.valueOf(midStr) + 
        fieldMapControl.substring(fieldMapControl.indexOf("input") + 
          "input".length() + 1, 
          fieldMapControl.length());
      return midStr;
    } 
    return "";
  }
  
  public String generateListPart(String menuSearchBound) throws Exception {
    if (menuSearchBound != null)
      try {
        String[][] fieldsMap = tokenFieldsForSearch(this.listSql.trim(), false);
        return concateControlsIntoDispSement(fieldsMap);
      } catch (Exception ex) {
        ex.printStackTrace();
        throw ex;
      }  
    return null;
  }
  
  private String concateControlsIntoDispSement(String[][] fieldsMap) throws Exception {
    if (fieldsMap == null || fieldsMap.length <= 0)
      throw new Exception("fieldsMap is null"); 
    String titleBe = "<tr align=\"center\"> <td width=\"10%\" height=\"25\" background=\"/jsoa/images/gwbg2.jpg\"><font color=\"#FFFFFF\">  序号</font></td> ";
    String titleEn = "<td width=\"8%\" background=\"/jsoa/images/gwbg2.jpg\"><font color=\"#FFFFFF\">操作</font></td></tr><tr bgcolor=\"CFD5E4\"><td height=\"6\" colspan=\"6\"></td></tr>";
    String titleMId = "";
    for (int i = 0; i < fieldsMap.length; i++)
      titleMId = String.valueOf(titleMId) + 
        "<td background=\"/jsoa/images/gwbg2.jpg\"><font color=\"#FFFFFF\">" + 
        fieldsMap[i][4] + "</font></td>"; 
    return "";
  }
  
  private void logInfo(String logName, String logValue) {
    if (this.logSwitch) {
      logger.info("***********************");
      logger.info(String.valueOf(logName) + " : " + this.searchSql);
      logger.info("***********************");
    } 
  }
}
