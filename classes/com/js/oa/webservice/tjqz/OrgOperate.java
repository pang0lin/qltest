package com.js.oa.webservice.tjqz;

import com.js.util.util.DataSourceBase;
import com.js.util.util.IO2File;
import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

public class OrgOperate {
  public String orgAdd(String xml) {
    OrgPO org = getPOFromXml(xml);
    return insertOrg(org);
  }
  
  public String orgEdit(String xml) {
    OrgPO org = getPOFromXml(xml);
    return updateOrg(org);
  }
  
  public String orgDelete(String xml) {
    OrgPO org = getPOFromXml(xml);
    return deleteOrg(org);
  }
  
  public String orgDeleteLogic(String xml) {
    OrgPO org = getPOFromXml(xml);
    return deleteOrgLogic(org);
  }
  
  private String insertOrg(OrgPO org) {
    if (org == null)
      return "0-请提交正确格式的参数"; 
    if (org.getOrgGuid() == null || "".equals(org.getOrgGuid()))
      return "0-组织唯一标识不能为空"; 
    if (org.getOrgParentId() == null || "".equals(org.getOrgParentId()))
      return "0-上级组织唯一标识不能为空"; 
    if (org.getOrgName() == null || "".equals(org.getOrgName()))
      return "0-组织名称不能为空"; 
    if (org.getOrgDescripte() != null && org.getOrgDescripte().length() > 300)
      return "0-组织介绍不能超过300字"; 
    String flag = "";
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      base.begin();
      String sql = "select org_id from org_organization where orgstatus<>4 and guid='" + org.getOrgGuid() + "'";
      rs = base.executeQuery(sql);
      if (rs.next()) {
        flag = "0-组织唯一标识已存在";
      } else {
        boolean codeIsNotExit = true;
        if (org.getOrgCode() != null && !"".equals(org.getOrgCode())) {
          sql = "select org_id from org_organization where orgstatus<>4 and orgcode='" + org.getOrgCode() + "'";
          if (rs.next())
            codeIsNotExit = false; 
        } 
        if (codeIsNotExit) {
          String[] orgInfo = { "0", "", "_500000$-1$", "", "-1" };
          sql = "select org_id,orgname,orgIdString,orgNameString,orglevel from org_organization where orgstatus<>4 and guid='" + org.getOrgParentId() + "'";
          rs = base.executeQuery(sql);
          if (rs.next()) {
            orgInfo[0] = rs.getString(1);
            orgInfo[1] = rs.getString(2);
            if (!"0".equals(org.getOrgParentId())) {
              orgInfo[2] = rs.getString(3);
              orgInfo[3] = String.valueOf(rs.getString(4)) + ".";
              orgInfo[4] = rs.getString(5);
            } 
            sql = "select org_id from org_organization where orgstatus<>4 and orgname='" + org.getOrgName() + "' and orgparentorgid=" + orgInfo[0];
            rs = base.executeQuery(sql);
            if (rs.next()) {
              flag = "0-同级组织下已存在同名组织";
            } else {
              String leaderIds = "", leaderNames = "";
              if (org.getOrgLeader() != null && !"".equals(org.getOrgLeader())) {
                String[] leaders = org.getOrgLeader().split(",");
                if (leaders.length > 0) {
                  String whereSQL = "(1=2 ";
                  for (int i = 0; i < leaders.length; i++)
                    whereSQL = String.valueOf(whereSQL) + " or guid='" + leaders[i] + "' "; 
                  whereSQL = String.valueOf(whereSQL) + ") ";
                  sql = "select emp_id,empName from org_employee where " + whereSQL + " and userisactive=1 and userisdeleted=0";
                  rs = base.executeQuery(sql);
                  while (rs.next()) {
                    leaderIds = String.valueOf(leaderIds) + "$" + rs.getString(1) + "$";
                    leaderNames = String.valueOf(leaderNames) + rs.getString(2) + ",";
                  } 
                } 
              } 
              String nameString = String.valueOf(orgInfo[3]) + org.getOrgName();
              int orgLevel = Integer.parseInt(orgInfo[4]) + 1;
              long orgOrderCode = getOrderCode(orgInfo[0]);
              sql = "insert into org_organization(orgparentorgid,orgmanagerempid,orgmanagerempname,orgname,orgsimplename,orgcode,orgordercode,orgdescripte,orglevel,orghasjunior,orgstatus,orgnamestring,orghaschannel,orgserial,domain_id,guid,orgtype,orgchanneltype) values (" + 


                
                orgInfo[0] + ",'" + leaderIds + "','" + leaderNames + "','" + org.getOrgName() + "','" + org.getOrgName() + "'," + 
                "'" + org.getOrgCode() + "'," + orgOrderCode + ",'" + org.getOrgDescripte() + "'," + orgLevel + ",0,0," + 
                "'" + nameString + "',0,'" + org.getOrgCode() + "',0,'" + org.getOrgGuid() + "'," + 
                "1,0)";
              IO2File.printFile("新增组织：" + sql, "组织信息操作");
              int n = base.executeUpdate(sql);
              if (n > 0) {
                sql = "select org_id from org_organization where guid='" + org.getOrgGuid() + "'";
                rs = base.executeQuery(sql);
                if (rs.next()) {
                  String orgId = rs.getString(1);
                  String idString = String.valueOf(orgInfo[2]) + "_" + orgOrderCode + "$" + orgId + "$";
                  sql = "update org_organization set orgidstring='" + idString + "' where org_id=" + orgId;
                  base.executeUpdate(sql);
                  flag = "1-success";
                } 
              } else {
                flag = "0-组织信息添加失败";
              } 
            } 
          } else {
            flag = "0-上级组织不存在或已删除";
          } 
        } else {
          flag = "0-组织编码重复";
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      flag = "0-" + e.getMessage();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        base.end();
      } catch (SQLException e) {
        e.printStackTrace();
        flag = "0-" + e.getMessage();
      } 
    } 
    System.out.println("操作结果：" + flag);
    return flag;
  }
  
  private String updateOrg(OrgPO org) {
    if (org == null)
      return "0-请提交正确格式的参数"; 
    if (org.getOrgGuid() == null || "".equals(org.getOrgGuid()))
      return "0-组织唯一标识不能为空"; 
    if (org.getOrgParentId() == null || "".equals(org.getOrgParentId()))
      return "0-上级组织唯一标识不能为空"; 
    if (org.getOrgName() == null || "".equals(org.getOrgName()))
      return "0-组织名称不能为空"; 
    if (org.getOrgDescripte() != null && org.getOrgDescripte().length() > 300)
      return "0-组织介绍不能超过300字"; 
    String flag = "";
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      base.begin();
      String sql = "select org_id from org_organization where orgstatus<>4 and guid='" + org.getOrgGuid() + "'";
      rs = base.executeQuery(sql);
      if (rs.next()) {
        String orgId = rs.getString(1);
        boolean codeIsNotExit = true;
        if (org.getOrgCode() != null && !"".equals(org.getOrgCode())) {
          sql = "select org_id from org_organization where orgstatus<>4 and orgcode='" + org.getOrgCode() + "' and org_id<>" + orgId;
          if (rs.next())
            codeIsNotExit = false; 
        } 
        if (codeIsNotExit) {
          String[] orgInfo = { "0", "", "_500000$-1$", "", "-1" };
          sql = "select org_id,orgname,orgIdString,orgNameString,orglevel from org_organization where orgstatus<>4 and guid='" + org.getOrgParentId() + "'";
          rs = base.executeQuery(sql);
          if (rs.next()) {
            orgInfo[0] = rs.getString(1);
            orgInfo[1] = rs.getString(2);
            if (!"0".equals(org.getOrgParentId())) {
              orgInfo[2] = rs.getString(3);
              orgInfo[3] = String.valueOf(rs.getString(4)) + ".";
              orgInfo[4] = rs.getString(5);
            } 
            sql = "select org_id from org_organization where orgstatus<>4 and orgname='" + org.getOrgName() + "' and org_id<>" + orgId + " and orgparentorgid=" + orgInfo[0];
            rs = base.executeQuery(sql);
            if (rs.next()) {
              flag = "0-同级组织下已存在同名组织";
            } else {
              String leaderIds = "", leaderNames = "";
              if (org.getOrgLeader() != null && !"".equals(org.getOrgLeader())) {
                String[] leaders = org.getOrgLeader().split(",");
                if (leaders.length > 0) {
                  String whereSQL = "(1=2 ";
                  for (int i = 0; i < leaders.length; i++)
                    whereSQL = String.valueOf(whereSQL) + " or guid='" + leaders[i] + "' "; 
                  whereSQL = String.valueOf(whereSQL) + ") ";
                  sql = "select emp_id,empName from org_employee where " + whereSQL + " and userisactive=1 and userisdeleted=0";
                  rs = base.executeQuery(sql);
                  while (rs.next()) {
                    leaderIds = String.valueOf(leaderIds) + "$" + rs.getString(1) + "$";
                    leaderNames = String.valueOf(leaderNames) + rs.getString(2) + ",";
                  } 
                } 
              } 
              String nameString = String.valueOf(orgInfo[3]) + org.getOrgName();
              int orgLevel = Integer.parseInt(orgInfo[4]) + 1;
              long orgOrderCode = getOrderCode(orgInfo[0]);
              String idString = String.valueOf(orgInfo[2]) + "_" + orgOrderCode + "$" + orgId + "$";
              sql = "update org_organization set orgparentorgid=" + orgInfo[0] + ",orgmanagerempid='" + leaderIds + 
                "',orgmanagerempname='" + leaderNames + "',orgname='" + org.getOrgName() + "',orgsimplename='" + 
                org.getOrgName() + "',orgcode='" + org.getOrgCode() + "',orgordercode=" + orgOrderCode + 
                ",orgdescripte='" + org.getOrgDescripte() + "',orglevel=" + orgLevel + "," + 
                "orgidstring='" + idString + "',orgnamestring='" + nameString + "',orgserial='" + org.getOrgCode() + "' where org_id=" + orgId;
              IO2File.printFile("更新组织：" + sql, "组织信息操作");
              int n = base.executeUpdate(sql);
              if (n > 0) {
                flag = "1-success";
              } else {
                flag = "0-组织信息更新失败";
              } 
            } 
          } else {
            flag = "0-上级组织不存在或已删除";
          } 
        } else {
          flag = "0-组织编码重复";
        } 
      } else {
        flag = "0-组织信息不存在";
      } 
    } catch (Exception e) {
      e.printStackTrace();
      flag = "0-" + e.getMessage();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        base.end();
      } catch (SQLException e) {
        e.printStackTrace();
        flag = "0-" + e.getMessage();
      } 
    } 
    System.out.println("操作结果：" + flag);
    return flag;
  }
  
  private String deleteOrg(OrgPO org) {
    if (org == null)
      return "0-请提交正确格式的参数"; 
    String flag = "0";
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      if (org.getOrgGuid() != null && !"".equals(org.getOrgGuid())) {
        base.begin();
        String sql = "select org_id from org_organization where guid='" + org.getOrgGuid() + "'";
        rs = base.executeQuery(sql);
        if (rs.next()) {
          String userIds = orgUser(org.getOrgGuid());
          base.executeUpdate("delete from org_rightscope where emp_id in (" + userIds + ")");
          base.executeUpdate("delete from org_user_role where emp_id in (" + userIds + ")");
          base.executeUpdate("delete from org_user_group where emp_id in (" + userIds + ")");
          base.executeUpdate("delete from org_organization_user where emp_id in (" + userIds + ")");
          base.executeUpdate("delete from org_employee where emp_id in (" + userIds + ")");
          int n = base.executeUpdate("delete from org_organization where guid='" + org.getOrgGuid() + "'");
          if (n > 0) {
            flag = "1-success";
          } else {
            flag = "0-error";
          } 
        } else {
          flag = "0-部门信息不存在。";
        } 
      } else {
        flag = "0-部门ID不能为空。";
      } 
    } catch (Exception e) {
      e.printStackTrace();
      flag = "0-" + e.getMessage();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        base.end();
      } catch (SQLException e) {
        e.printStackTrace();
        flag = "0-" + e.getMessage();
      } 
    } 
    System.out.println("操作结果：" + flag);
    return flag.split("-")[0];
  }
  
  private String deleteOrgLogic(OrgPO org) {
    if (org == null)
      return "0-请提交正确格式的参数"; 
    String flag = "0";
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      if (org.getOrgGuid() != null && !"".equals(org.getOrgGuid())) {
        base.begin();
        String sql = "select org_id from org_organization where guid='" + org.getOrgGuid() + "'";
        rs = base.executeQuery(sql);
        if (rs.next()) {
          String orgId = rs.getString(1);
          sql = "update org_employee set USERISACTIVE=0,USERISDELETED=1 where emp_id in (select emp_id from org_organization_user where org_id IN (select org_id from org_organization where orgIdString like '%$" + orgId + "$%'))";
          base.executeUpdate(sql);
          sql = "update org_organization set orgstatus=4 where orgIdString like '%$" + orgId + "$%'";
          int n = base.executeUpdate(sql);
          if (n > 0) {
            flag = "1-success";
          } else {
            flag = "0-删除组织信息失败";
          } 
        } else {
          flag = "0-组织信息不存在";
        } 
      } else {
        flag = "0-组织唯一标识不能为空";
      } 
    } catch (Exception e) {
      e.printStackTrace();
      flag = "0-" + e.getMessage();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        base.end();
      } catch (SQLException e) {
        e.printStackTrace();
        flag = "0-" + e.getMessage();
      } 
    } 
    System.out.println("操作结果：" + flag);
    return flag;
  }
  
  private String orgUser(String orgId) {
    String userId = "-1";
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery("select emp_id from org_organization_user where org_id=(select org_id from org_organization where guid='" + 
          orgId + "')");
      while (rs.next())
        userId = String.valueOf(userId) + "," + rs.getString(1); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return userId;
  }
  
  private long getOrderCode(String orgParentOrgId) {
    long orgOrderCode = 500000L;
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      base.begin();
      rs = base.executeQuery("SELECT orgordercode FROM org_organization WHERE orgparentorgid=" + Long.parseLong(orgParentOrgId) + " order by orgordercode desc");
      if (rs.next())
        orgOrderCode = rs.getLong(1) + 5000L; 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        base.end();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return orgOrderCode;
  }
  
  private OrgPO getPOFromXml(String xml) {
    System.out.println("部门信息xml：" + xml);
    OrgPO org = new OrgPO();
    SAXBuilder builder = new SAXBuilder();
    builder.setExpandEntities(false);
    Document doc = null;
    try {
      doc = builder.build(new InputSource(new StringReader(xml)));
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
    Element organization = doc.getRootElement();
    List<Element> fields = organization.getChildren("Field");
    for (int i = 0; i < fields.size(); i++) {
      Element field = fields.get(i);
      String colName = field.getAttributeValue("ColName");
      if ("guid".equalsIgnoreCase(colName)) {
        org.setOrgGuid(field.getAttributeValue("Value"));
      } else if ("parentOrgId".equalsIgnoreCase(colName)) {
        org.setOrgParentId(field.getAttributeValue("Value"));
      } else if ("orgName".equalsIgnoreCase(colName)) {
        org.setOrgName(field.getAttributeValue("Value"));
      } else if ("orgCode".equalsIgnoreCase(colName)) {
        org.setOrgCode(field.getAttributeValue("Value"));
      } else if ("orgManagerEmpId".equalsIgnoreCase(colName)) {
        org.setOrgLeader(field.getAttributeValue("Value"));
      } else if ("orgDescripte".equalsIgnoreCase(colName)) {
        org.setOrgDescripte(field.getAttributeValue("Value"));
      } 
    } 
    return org;
  }
}
