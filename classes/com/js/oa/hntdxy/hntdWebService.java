package com.js.oa.hntdxy;

import java.util.ArrayList;
import java.util.List;

public class hntdWebService {
  public String toDealDatas(String useraccount) {
    String xml = "";
    String userAccount = "";
    String operate = "deal";
    PersonalInfo perinfo = new PersonalInfo();
    userAccount = perinfo.getPersonalInfo(useraccount);
    System.out.println("老账号userAccount:" + userAccount);
    if (userAccount != null && !"null".equals(userAccount) && !"".equals(userAccount)) {
      List<String> list = perinfo.getPersonalEmpId(userAccount);
      xml = getXmlData(operate, list, "-1");
    } 
    System.out.println("待办列表xml:" + xml);
    return xml;
  }
  
  public String inform_Oa(String useraccount) {
    String xml = "";
    String userAccount = "";
    String operate = "inform";
    PersonalInfo perinfo = new PersonalInfo();
    userAccount = perinfo.getPersonalInfo(useraccount);
    System.out.println("老账号userAccount:" + userAccount);
    if (userAccount != null && !"null".equals(userAccount) && !"".equals(userAccount)) {
      List<String> list = perinfo.getPersonalEmpId(userAccount);
      xml = getXmlData(operate, list, "-1");
    } 
    System.out.println("公告列表xml:" + xml);
    return xml;
  }
  
  public String inform_OaContent(String useraccount, String inforid) {
    String xml = "";
    String operate = "informContent";
    List<String> list = new ArrayList<String>();
    System.out.println("1.公告id：" + inforid);
    xml = getXmlData(operate, list, inforid);
    System.out.println("公告内容xml:" + xml);
    return xml;
  }
  
  public String news_Oa(String useraccount) {
    String xml = "";
    String userAccount = "";
    String operate = "news";
    PersonalInfo perinfo = new PersonalInfo();
    userAccount = perinfo.getPersonalInfo(useraccount);
    System.out.println("老账号userAccount:" + userAccount);
    if (userAccount != null && !"null".equals(userAccount) && !"".equals(userAccount)) {
      List<String> list = perinfo.getPersonalEmpId(userAccount);
      xml = getXmlData(operate, list, "-1");
    } 
    System.out.println("新闻列表xml:" + xml);
    return xml;
  }
  
  public String news_OaContent(String useraccount, String inforid) {
    String xml = "";
    String operate = "newsContent";
    List<String> list = new ArrayList<String>();
    System.out.println("新闻id：" + inforid);
    xml = getXmlData(operate, list, inforid);
    System.out.println("新闻内容xml:" + xml);
    return xml;
  }
  
  private String getXmlData(String operate, List<String> list, String inforid) {
    String xml = "";
    XmlData xmldata = new XmlData();
    if (list != null && list.size() > 0) {
      String empid = list.get(0);
      String orgid = list.get(1);
      String orgidstring = list.get(2);
      String domain_id = list.get(3);
      if ("deal".equals(operate))
        xml = xmldata.getDeaWithData(empid, domain_id, "0", "-1", "1"); 
      if ("inform".equals(operate))
        xml = xmldata.getFormDataList("101", empid, orgid, orgidstring, domain_id); 
      if ("news".equals(operate))
        xml = xmldata.getFormDataList("100", empid, orgid, orgidstring, domain_id); 
    } 
    if ("informContent".equals(operate))
      xml = xmldata.getFormContentData(inforid, "101"); 
    if ("newsContent".equals(operate))
      xml = xmldata.getFormContentData(inforid, "100"); 
    return xml;
  }
}
