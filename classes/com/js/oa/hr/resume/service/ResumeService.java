package com.js.oa.hr.resume.service;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import javax.sql.DataSource;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ResumeService {
  public String addResume(String paraIn) {
    if (!"chinaLift".equals(SystemCommon.getCustomerName()))
      return ""; 
    String id = "";
    String name = "";
    String sex = "";
    String age = "";
    String tel = "";
    String applyPosition = "";
    String topEducation = "";
    String topEducationSchool = "";
    String topDegree = "";
    String topDegreeSchool = "";
    String professionType = "";
    String professionName = "";
    String politicalStatus = "";
    String bachelorSchool = "";
    String bachelorProfession = "";
    String url = "";
    try {
      SAXReader saxReader = new SAXReader();
      Document doc = saxReader.read(new StringReader(paraIn));
      Element root = doc.getRootElement();
      List<Element> list = root.elements();
      for (int i = 0; i < list.size(); i++) {
        Element ele = list.get(i);
        if ("id".equalsIgnoreCase(ele.attributeValue("name"))) {
          id = ele.attributeValue("value");
        } else if ("name".equalsIgnoreCase(ele.attributeValue("name"))) {
          name = ele.attributeValue("value");
        } else if ("sex".equalsIgnoreCase(ele.attributeValue("name"))) {
          sex = ele.attributeValue("value");
        } else if ("age".equalsIgnoreCase(ele.attributeValue("name"))) {
          age = ele.attributeValue("value");
        } else if ("tel".equalsIgnoreCase(ele.attributeValue("name"))) {
          tel = ele.attributeValue("value");
        } else if ("applyPosition".equalsIgnoreCase(ele.attributeValue("name"))) {
          applyPosition = ele.attributeValue("value");
        } else if ("topEducation".equalsIgnoreCase(ele.attributeValue("name"))) {
          topEducation = ele.attributeValue("value");
        } else if ("topEducationSchool".equalsIgnoreCase(ele.attributeValue("name"))) {
          topEducationSchool = ele.attributeValue("value");
        } else if ("topDegree".equalsIgnoreCase(ele.attributeValue("name"))) {
          topDegree = ele.attributeValue("value");
        } else if ("topDegreeSchool".equalsIgnoreCase(ele.attributeValue("name"))) {
          topDegreeSchool = ele.attributeValue("value");
        } else if ("professionType".equalsIgnoreCase(ele.attributeValue("name"))) {
          professionType = ele.attributeValue("value");
        } else if ("professionName".equalsIgnoreCase(ele.attributeValue("name"))) {
          professionName = ele.attributeValue("value");
        } else if ("politicalStatus".equalsIgnoreCase(ele.attributeValue("name"))) {
          politicalStatus = ele.attributeValue("value");
        } else if ("bachelorSchool".equalsIgnoreCase(ele.attributeValue("name"))) {
          bachelorSchool = ele.attributeValue("value");
        } else if ("bachelorProfession".equalsIgnoreCase(ele.attributeValue("name"))) {
          bachelorProfession = ele.attributeValue("value");
        } else if ("url".equalsIgnoreCase(ele.attributeValue("name"))) {
          url = ele.attributeValue("value");
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      return "-1";
    } 
    Connection conn = null;
    Statement stmt = null;
    try {
      DataSource ds = (new DataSourceBase()).getDataSource();
      conn = ds.getConnection();
      stmt = conn.createStatement();
      String sql = "select * from temp_resume where id='" + id + "'";
      if (stmt.executeQuery(sql).next())
        return "-9"; 
      sql = "insert into temp_resume(id_dual,id,name,sex,age,tel,applyposition,topeducation,topeducationschool,topdegree,topdegreeschool,professiontype,professionname,politicalstatus,bachelorschool,bachelorprofession,url)values(" + 

        
        System.currentTimeMillis() + "," + 
        "'" + id + "','" + name + "','" + sex + "','" + age + "','" + tel + "'," + 
        "'" + applyPosition + "','" + topEducation + "','" + topEducationSchool + "','" + topDegree + "','" + topDegreeSchool + "'," + 
        "'" + professionType + "','" + professionName + "','" + politicalStatus + "','" + bachelorSchool + "','" + bachelorProfession + "'," + 
        "'" + url + "')";
      stmt.execute(sql);
      conn.commit();
      stmt.close();
      stmt = null;
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      return "-2";
    } finally {
      try {
        if (stmt != null)
          stmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    try {
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return "0";
  }
  
  public static void main(String[] args) {
    String xml = "<?xml version='1.0' encoding='UTF-8'?><data><field name='id' value='1564088'/><field name='name' value='张三'/><field name='sex' value='男'/><field name='age' value='24'/><field name='tel' value='159********'/><field name='applyPosition' value='高级软件开发工程师'/><field name='topEducation' value='本科'/><field name='topEducationSchool' value='XXXXX大学'/><field name='topDegree' value='学士'/><field name='topDegreeSchool' value='XXXX大学'/><field name='professionType' value='IT'/><field name='professionName' value='计算机科学与技术'/><field name='politicalStatus' value='党员'/><field name='bachelorSchool' value='XXXXX大学'/><field name='bachelorProfession' value='计算机科学与技术'/><field name='url' value='http://*******'/></data>";
    (new ResumeService()).addResume(xml);
  }
}
