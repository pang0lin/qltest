package com.js.util.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParameterFilter {
  private static String[][] replaceChar = new String[][] { { "&", "＆" } };
  
  private static List ignoreList = new ArrayList();
  
  private static List ignorePara = new ArrayList();
  
  private static List<String> managerActionURL = new ArrayList<String>();
  
  private static Map<String, String> managerActionRight = new HashMap<String, String>();
  
  static {
    ignoreList.add("/CustomFormAction.jspx");
    ignoreList.add("/CustomFormAction.do");
    ignoreList.add("/TableAction.do");
    ignoreList.add("/EFormPageAction.do");
    ignoreList.add("/TableInfoAction.do");
    ignoreList.add("/GetNextActivityAction.jspx");
    ignoreList.add("/GetNextActivityAction.do");
    ignoreList.add("/GetNextActivityForWeiXinAction.do");
    ignoreList.add("/BodyAction.jspx");
    ignoreList.add("/BodyAction.do");
    ignoreList.add("/WFProcessAction.do");
    ignoreList.add("/WFProcessAction.jspx");
    ignoreList.add("/WFProcessAction.do");
    ignoreList.add("/ActivityAction.jspx");
    ignoreList.add("/ActivityAction.do");
    ignoreList.add("/WorkflowButtonAction.do");
    ignoreList.add("/WorkflowButtonForWeiXinAction.do");
    ignoreList.add("/InformationAction.jspx");
    ignoreList.add("/InformationAction.do");
    ignoreList.add("/ModuleSetAction.do");
    ignoreList.add("/GovCustomAction.jspx");
    ignoreList.add("/GovCustomAction.do");
    ignoreList.add("/ModuleSetAction.jspx");
    ignoreList.add("/ModuleSetAction.do");
    ignoreList.add("/BoardRoomAction.do");
    ignoreList.add("/ReportAction.jspx");
    ignoreList.add("/ReportAction.do");
    ignoreList.add("/wap.do");
    ignoreList.add("/TemplateAction.jspx");
    ignoreList.add("/TemplateAction.do");
    ignoreList.add("/GovReceiveFileAction.do");
    ignoreList.add("/WorkflowCommonAction.do");
    ignoreList.add("/WorkReportTemplateAction.jspx");
    ignoreList.add("/WorkReportTemplateAction.do");
    ignoreList.add("/WorkFlowProcAction.do");
    ignoreList.add("/module/template/grant.jsp");
    ignoreList.add("/ModuleExtendsAction.jspx");
    ignoreList.add("/ModuleExtendsAction.do");
    ignoreList.add("/portal2/saveScratchpad.jsp");
    ignoreList.add("/jsflow/workflow_myflow_add_gd.jsp");
    ignoreList.add("/jsflow/workflow_common.jsp");
    ignoreList.add("/userdb/setInputSearch.jsp");
    ignoreList.add("/public/jsp/template_error.jsp");
    ignoreList.add("/portal2/saveUserData.jsp");
    ignoreList.add("/portal2/editor.jsp");
    ignoreList.add("/eform/form_setfieldvalue.jsp");
    ignoreList.add("/fPageAction.do");
    ignoreList.add("/GovSendFileAction.do");
    ignoreList.add("/gridreport/data/xmlSQLParam.jsp");
    ignoreList.add("/jsflow/jsflow_relation.jsp");
    ignoreList.add("/doc/doc/sendfile_gd.jsp");
    ignoreList.add("/doc/doc/receivefile_gd.jsp");
    ignoreList.add("/iSignatureHTML.jsp/Service.jsp");
    ignoreList.add("/WorkReportAction.jspx");
    ignoreList.add("/WorkReportAction.do");
    ignoreList.add("/WorkReportProductAction.jspx");
    ignoreList.add("/WorkReportProductAction.do");
    ignoreList.add("/MyInfoAction.jspx");
    ignoreList.add("/MyInfoAction.do");
    ignoreList.add("/webMail.do");
    ignoreList.add("/FestalCardAction.jspx");
    ignoreList.add("/FestalCardAction.do");
    ignoreList.add("/BirthCardAction.jspx");
    ignoreList.add("/BirthCardAction.do");
    ignoreList.add("/jsflow/wf_stepanduser_org.jsp");
    ignoreList.add("/jsflow/wf_stepanduser_orgselfsend.jsp");
    ignoreList.add("/eform/form_setbatchfieldvalue.jsp");
    ignorePara.add("informationContent");
    ignorePara.add("forumContent1");
    ignorePara.add("content1");
    ignorePara.add("contexeyinyong");
    ignorePara.add("content");
    managerActionURL.add("/setup/systemmanager_sysSetup.jsp");
    managerActionRight.put("/setup/systemmanager_sysSetup.jsp", "00*01*01");
    managerActionURL.add("/OrganizationAction.do");
    managerActionRight.put("/OrganizationAction.do", "00*01*01,00*01*02");
    managerActionURL.add("/UserAction.do");
    managerActionRight.put("/UserAction.do", "00*01*01,00*01*02");
    managerActionURL.add("/DutyAction.do");
    managerActionRight.put("/DutyAction.do", "00*01*01,00*01*02,07*02*01");
    managerActionURL.add("/StationAction.do");
    managerActionRight.put("/StationAction.do", "00*01*01,00*01*02,07*02*01");
    managerActionURL.add("/ListUserAction.do");
    managerActionRight.put("/ListUserAction.do", "00*01*01,00*01*02");
    managerActionURL.add("/RoleAction.do");
    managerActionRight.put("/RoleAction.do", "00*01*01,00*01*02");
    managerActionURL.add("/ModuleSetAction.do");
    managerActionRight.put("/ModuleSetAction.do", "00*01*01,00*01*03");
    managerActionURL.add("/MenuAction.do");
    managerActionRight.put("/MenuAction.do", "00*01*01,00*01*03");
    managerActionURL.add("/ipAction.do");
    managerActionRight.put("/ipAction.do", "00*01*01");
  }
  
  public static boolean checkParameter(String value) {
    if (value == null || "null".equals(value))
      return true; 
    value = value.toLowerCase();
    if ((value.indexOf("or ") >= 0 || value.indexOf("or(") >= 0 || value.indexOf("and ") >= 0 || value.indexOf("and(") >= 0) && (
      value.indexOf("=") > 0 || value.indexOf("<") > 0 || 
      value.indexOf(">") > 0 || value.indexOf("like") > 0 || 
      value.indexOf("in") > 0 || value.indexOf("exist") > 0 || 
      value.indexOf("'") > 0))
      return false; 
    if ((value.indexOf(" ") >= 0 || value.indexOf("(") >= 0) && (
      value.indexOf("information_schema.columns") > 0 || 
      value.indexOf("table_schema") > 0 || 
      value.indexOf("xp_cmdshell") > 0 || 
      value.indexOf("truncate") > 0))
      return false; 
    if ((value.indexOf("union ") >= 0 || value.indexOf("union(") >= 0) && (
      value.indexOf("select") > 0 || value.indexOf("from") > 0))
      return false; 
    if (value.indexOf("select ") >= 0 && value.indexOf("sys.dbms_") > 0)
      return false; 
    if (value.indexOf(" ") >= 0 && value.indexOf("--") > 0)
      return false; 
    int chrCount = charAppearTime(value, "chr");
    if (value.indexOf("chr") > 0 && value.indexOf("(") > 0 && value.indexOf(")") > 0 && (
      value.indexOf("||") > 0 || chrCount > 2))
      return false; 
    if (value.indexOf("<script") >= 0 && value.indexOf("script>") > 0)
      return false; 
    if (value.indexOf("<") >= 0 && value.indexOf(">") > 0 && value.indexOf("script") > 0 && value.indexOf("script") > 0)
      return false; 
    if (value.indexOf("=") >= 0) {
      if (value.indexOf("<iframe") >= 0 && value.indexOf("src") > 0)
        return false; 
      if (value.indexOf("src") >= 0 && (value.indexOf("http") > 0 || value.indexOf("/") > 0))
        return false; 
      if (value.indexOf("onmouse") >= 0 || value.indexOf("onclick") >= 0 || value.indexOf("onload") >= 0 || 
        value.indexOf("onerror") >= 0)
        return false; 
    } 
    if (value.indexOf(".cookie") > 0 || value.indexOf("document.write") >= 0)
      return false; 
    if (value.indexOf("javascript") >= 0 && value.indexOf(":") > 0 && (value.indexOf("'") > 0 || 
      value.indexOf("\"") > 0 || value.indexOf("(") >= 0))
      return false; 
    return true;
  }
  
  public static boolean isNumber(String str) {
    if (str != null && !"null".equals(str) && !"".equals(str)) {
      if (str.length() > 36)
        return false; 
      return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    } 
    return true;
  }
  
  public static List getIgnoreList() {
    return ignoreList;
  }
  
  public static List getIgnorePara() {
    return ignorePara;
  }
  
  public static List<String> getManagerActionURL() {
    return managerActionURL;
  }
  
  public static Map<String, String> getManagerActionRight() {
    return managerActionRight;
  }
  
  public static void addIgnoreList(String path) {
    ignoreList.add(path);
  }
  
  public static void addIgnorePara(String name) {
    ignoreList.add(name);
  }
  
  public static void main(String[] args) {
    System.out.println("123.33 :" + isNumber("123.23"));
    System.out.println("1.23.33 :" + isNumber("1.23.23"));
    System.out.println("-123.33 :" + isNumber("-123.33"));
    System.out.println("11111111111111123.33 :" + isNumber("11111111111111123.33"));
    System.out.println("11111111111111123.33 :" + isNumber("11111111111111123.33"));
    System.out.println("1111111<1111111123.33 :" + isNumber("1111111<1111111123.33"));
    System.out.println("111111111111>11123.33 :" + isNumber("111111111111>11123.33"));
    System.out.println("11111111 11123.331111  :" + isNumber("11111111 11123.331111"));
    System.out.println("1111111333333333333333%33%333333  :" + isNumber("11111113333333%3%333333333333333"));
    System.out.println("1111111d1111111123.33 :" + isNumber("1111111d1111111123.33"));
  }
  
  private static int charAppearTime(String str, String filterChar) {
    String[] arr = str.split(filterChar);
    return arr.length - 1;
  }
  
  private static String replaceString(String a, String b, String c) {
    a = a.replaceAll("(?i)" + b, c);
    return a;
  }
  
  public static String replaceString(String para) {
    if (para == null)
      return null; 
    for (int i = 0; i < replaceChar.length; i++)
      para = para.replaceAll(replaceChar[i][0], replaceChar[i][1]); 
    return para;
  }
}
