package com.js.oa.webservice.langxin;

import com.js.util.config.SystemCommon;
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
  String dataBaseType = SystemCommon.getDatabaseType();
  
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
  
  public String orgMerge(String fromXml, String toXml) {
    OrgPO fromOrg = getPOFromXml(fromXml);
    OrgPO toOrg = getPOFromXml(toXml);
    return orgMerge(fromOrg, toOrg);
  }
  
  public String insertOrg(OrgPO org) {
    String flag = "";
    String sql = "";
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      if (org.getOrgName() != null && !"".equals(org.getOrgName())) {
        if (org.getOrgParentId() != null && !"".equals(org.getOrgParentId())) {
          base.begin();
          String[] orgInfo = { "0", "", "_500000$-1$", "", "0" };
          sql = "select org_id,orgname,orgIdString,orgNameString,ORGPARENTORGID from org_organization where orgparentorgid=-1 OR guid='" + org.getOrgParentId() + "' ORDER BY org_id DESC";
          rs = base.executeQuery(sql);
          if (rs.next()) {
            orgInfo[0] = rs.getString(1);
            orgInfo[1] = rs.getString(2);
            orgInfo[2] = rs.getString(3);
            orgInfo[3] = rs.getString(4);
            orgInfo[4] = rs.getString(5);
            sql = "select org_id from org_organization where orgname='" + org.getOrgName() + "' and orgparentorgid=" + orgInfo[0];
            rs = base.executeQuery(sql);
            if (rs.next()) {
              flag = "0-同级父部门下存在同名部门。";
            } else {
              sql = "select org_id from org_organization where guid='" + org.getOrgGuid() + "' or orgserial='" + org.getOrgCode() + "'";
              rs = base.executeQuery(sql);
              if (rs.next()) {
                flag = "0-部门ID或编号重复。";
              } else {
                int n = insertOrg(orgInfo[0], org.getOrgName(), org.getOrgCode(), orgInfo[2], orgInfo[3], org.getOrgGuid(), base);
                if (n > 0) {
                  flag = "1-success";
                  if (org.getOrgLeader() != null && !"".equals(org.getOrgLeader())) {
                    sql = "select emp_id from org_employee where wm_emp_id in ('" + org.getOrgLeader().replace(",", "','") + "')";
                    rs = base.executeQuery(sql);
                    if (rs.next()) {
                      updateOrgLeader(org, base);
                    } else {
                      flag = String.valueOf(flag) + ",部门领导信息不存在。";
                      System.out.println("部门领导信息不存在。");
                    } 
                  } 
                } else {
                  flag = "0-error";
                } 
              } 
            } 
          } else {
            flag = "0-父部门信息不存在。";
          } 
        } else {
          flag = "0-父部门ID不能为空。";
        } 
      } else {
        flag = "0-部门名称不能为空。";
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
  
  private int insertOrg(String parentId, String orgName, String orgCode, String orgIdString, String orgNameString, String orgGuid, DataSourceBase base) throws SQLException {
    String orgId = "0";
    orgNameString = ("0".equals(orgIdString) || orgNameString.equals("")) ? orgName : (String.valueOf(orgNameString) + "." + orgName);
    System.out.println("新增部门【" + orgNameString + "】");
    String sql = "";
    ResultSet rs = null;
    int n = 0;
    long orgOrderCode = getOrderCode(parentId);
    if ("0".equals(orgIdString)) {
      orgIdString = "_500000$-1$_500000$ORGID$";
    } else {
      orgIdString = String.valueOf(orgIdString) + "_" + orgOrderCode + "$ORGID$";
    } 
    if (this.dataBaseType.indexOf("mysql") >= 0) {
      sql = "INSERT INTO org_organization (ORGPARENTORGID,ORGMANAGEREMPID,ORGMANAGEREMPNAME,ORGNAME,ORGSIMPLENAME,ORGCODE,ORGORDERCODE,ORGFOUNDDATE,ORGDESCRIPTE,ORGLEVEL,ORGHASJUNIOR,ORGSTATUS,ORGIDSTRING,ORGNAMESTRING,orghaschannel,RTXDEPTID,RTXDEPTPID,ORGBANNER,ORGSERIAL,USERORDERCODE,DOMAIN_ID,lastupdate,orgtype,orgChannelType,orgChannelUrl,guid) VALUES(" + 


        
        parentId + ",'','','" + orgName + "'," + "'','" + orgCode + "'," + orgOrderCode + ",NULL,'','0','0'," + 
        "'0','_500000$-1$_500000$123$','" + orgNameString + "','0',NULL,NULL,NULL,'" + orgCode + "',NULL," + 
        "'0',NULL,'1','0',NULL,'" + orgGuid + "')";
      IO2File.printFile("插入部门：" + sql, "朗新部门操作");
      n = base.executeUpdate(sql);
      rs = base.executeQuery("select max(ORG_ID) from org_organization");
      if (rs.next())
        orgId = rs.getString(1); 
      base.executeUpdate("update org_organization set ORGIDSTRING ='" + orgIdString.replace("ORGID", orgId) + "' where org_id=" + orgId);
    } else {
      rs = base.executeQuery("select hibernate_sequence.nextval from dual");
      if (rs.next())
        orgId = rs.getString(1); 
      sql = "INSERT INTO org_organization (org_id,ORGPARENTORGID,ORGMANAGEREMPID,ORGMANAGEREMPNAME,ORGNAME,ORGSIMPLENAME,ORGCODE,ORGORDERCODE,ORGFOUNDDATE,ORGDESCRIPTE,ORGLEVEL,ORGHASJUNIOR,ORGSTATUS,ORGIDSTRING,ORGNAMESTRING,orghaschannel,RTXDEPTID,RTXDEPTPID,ORGBANNER,ORGSERIAL,USERORDERCODE,DOMAIN_ID,lastupdate,orgtype,orgChannelType,orgChannelUrl,guid) VALUES(" + 


        
        orgId + "," + parentId + ",'','','" + orgName + "'," + 
        "'','" + orgCode + "'," + orgOrderCode + ",NULL,'','0','0'," + 
        "'0','" + orgIdString.replace("ORGID", orgId) + "','" + orgNameString + "','0',NULL,NULL,NULL,'" + orgCode + "',NULL," + 
        "'0',NULL,'1','0',NULL,'" + orgGuid + "')";
      IO2File.printFile("插入部门：" + sql, "朗新部门操作");
      n = base.executeUpdate(sql);
    } 
    if (rs != null)
      rs.close(); 
    return n;
  }
  
  public String updateOrg(OrgPO org) {
    String flag = "";
    String sql = "";
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      if (org.getOrgName() != null && !"".equals(org.getOrgName())) {
        if (org.getOrgParentId() != null && !"".equals(org.getOrgParentId())) {
          base.begin();
          String[] orgInfo = { "0", "", "_500000$-1$", "", "0" };
          sql = "select org_id,orgname,orgIdString,orgNameString,ORGPARENTORGID from org_organization where orgparentorgid=-1 OR guid='" + org.getOrgParentId() + "' ORDER BY org_id DESC";
          rs = base.executeQuery(sql);
          if (rs.next()) {
            orgInfo[0] = rs.getString(1);
            orgInfo[1] = rs.getString(2);
            orgInfo[2] = rs.getString(3);
            orgInfo[3] = rs.getString(4);
            orgInfo[4] = rs.getString(5);
            sql = "select org_id from org_organization where orgname='" + org.getOrgName() + "' and guid<>'" + org.getOrgGuid() + "' and orgparentorgid=" + orgInfo[0];
            rs = base.executeQuery(sql);
            if (rs.next()) {
              flag = "0-同级父部门中存在同名部门。";
            } else {
              sql = "select org_id from org_organization where guid<>'" + org.getOrgGuid() + "' and orgserial='" + org.getOrgCode() + "'";
              rs = base.executeQuery(sql);
              if (rs.next()) {
                flag = "0-部门编号重复。";
              } else {
                int n = updateOrg(orgInfo[0], org.getOrgName(), org.getOrgCode(), orgInfo[2], orgInfo[3], org.getOrgGuid(), base);
                if (n > 0) {
                  flag = "1-success";
                  if (org.getOrgLeader() != null && !"".equals(org.getOrgLeader())) {
                    sql = "select emp_id from org_employee where wm_emp_id in ('" + org.getOrgLeader().replace(",", "','") + "')";
                    rs = base.executeQuery(sql);
                    if (rs.next()) {
                      updateOrgLeader(org, base);
                    } else {
                      flag = String.valueOf(flag) + "，部门领导信息不存在。";
                      System.out.println("部门领导信息不存在。");
                    } 
                  } 
                } else {
                  flag = "0-error";
                } 
              } 
            } 
          } else {
            flag = "0-父部门信息不存在。";
          } 
        } else {
          flag = "0-父部门ID不能为空。";
        } 
      } else {
        flag = "0-部门名称不能为空。";
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
  
  private int updateOrg(String parentId, String orgName, String orgCode, String orgIdString, String orgNameString, String orgGuid, DataSourceBase base) {
    orgNameString = ("0".equals(orgIdString) || orgNameString.equals("")) ? orgName : (String.valueOf(orgNameString) + "." + orgName);
    System.out.println("修改部门【" + orgNameString + "】");
    long orgOrderCode = getOrderCode(parentId);
    if ("0".equals(orgIdString))
      orgIdString = "_500000$-1$"; 
    String sql = "";
    if (this.dataBaseType.indexOf("mysql") >= 0) {
      sql = "update org_organization set ORGPARENTORGID=" + parentId + ",ORGNAME='" + orgName + "'," + 
        "ORGCODE='" + orgCode + "',ORGIDSTRING=CONCAT('" + orgIdString + "','_" + orgOrderCode + "$',org_id,'$')," + 
        "ORGNAMESTRING='" + orgNameString + "', " + "ORGSERIAL='" + orgCode + "' where guid='" + orgGuid + "'";
    } else {
      sql = "update org_organization set ORGPARENTORGID=" + parentId + ",ORGNAME='" + orgName + "'," + 
        "ORGCODE='" + orgCode + "',ORGIDSTRING='" + orgIdString + "'||'_" + orgOrderCode + "$'||org_id||'$'," + 
        "ORGNAMESTRING='" + orgNameString + "', " + "ORGSERIAL='" + orgCode + "' " + 
        "where guid='" + orgGuid + "'";
    } 
    IO2File.printFile("修改部门：" + sql, "朗新部门操作");
    return base.executeUpdate(sql);
  }
  
  private void updateOrgLeader(OrgPO org, DataSourceBase base) throws SQLException {
    String leader = org.getOrgLeader();
    String userIds = "";
    String userNames = "";
    ResultSet rs = base.executeQuery("select emp_id,empName from org_employee where wm_emp_id in ('" + leader.replace(",", "','") + "')");
    while (rs.next()) {
      userIds = String.valueOf(userIds) + "$" + rs.getString(1) + "$";
      userNames = String.valueOf(userNames) + rs.getString(2) + ",";
    } 
    String sql = "update org_organization set orgmanagerempid='" + userIds + "',orgmanagerempname='" + userNames + 
      "' where guid='" + org.getOrgGuid() + "'";
    base.executeUpdate(sql);
    sql = "update org_employee set deptleader='1' where wm_emp_id in ('" + leader.replace(",", "','") + "')";
    base.executeUpdate(sql);
    sql = "update org_employee set empleaderid='" + userIds + "',empleadername='" + userNames + "' where emp_id in (" + 
      "SELECT emp_id FROM org_organization_user WHERE org_id=(SELECT org_id FROM org_organization WHERE guid='" + org.getOrgGuid() + "'))" + 
      " and wm_emp_id not in ('" + leader.replace(",", "','") + "')";
    base.executeUpdate(sql);
  }
  
  public String deleteOrg(OrgPO org) {
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
  
  public String deleteOrgLogic(OrgPO org) {
    String flag = "0";
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      if (org.getOrgGuid() != null && !"".equals(org.getOrgGuid())) {
        base.begin();
        String sql = "select org_id from org_organization where guid='" + org.getOrgGuid() + "'";
        rs = base.executeQuery(sql);
        if (rs.next()) {
          sql = "update org_employee set USERISACTIVE=0,USERISDELETED=1 where emp_id in (" + orgUser(org.getOrgGuid()) + ")";
          base.executeUpdate(sql);
          sql = "update org_organization set orgstatus=4 where guid='" + org.getOrgGuid() + "'";
          int n = base.executeUpdate(sql);
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
  
  public String orgUser(String orgId) {
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
  
  public String orgMerge(OrgPO fromOrg, OrgPO toOrg) {
    System.out.println("暂未实现");
    return "0";
  }
  
  private long getOrderCode(String orgParentOrgId) {
    long orgOrderCode = 500000L;
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      base.begin();
      rs = base.executeQuery("SELECT orgordercode FROM org_organization WHERE orgparentorgid=" + orgParentOrgId + " order by orgordercode desc");
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
  
  public OrgPO getPOFromXml(String xml) {
    System.out.println("部门信息xml：" + xml);
    OrgPO org = new OrgPO();
    SAXBuilder builder = new SAXBuilder();
    builder.setExpandEntities(false);
    Document doc = null;
    try {
      doc = builder.build(new InputSource(new StringReader(xml)));
    } catch (Exception e) {
      e.printStackTrace();
    } 
    Element person = doc.getRootElement();
    List<Element> fields = person.getChildren("Field");
    for (int i = 0; i < fields.size(); i++) {
      Element field = fields.get(i);
      String colName = field.getAttributeValue("ColName");
      if ("CONTENT".equalsIgnoreCase(colName)) {
        org.setOrgName(field.getAttributeValue("Value"));
      } else if ("Dept_code".equalsIgnoreCase(colName)) {
        org.setOrgCode(field.getAttributeValue("Value"));
      } else if ("Dept_id".equalsIgnoreCase(colName)) {
        org.setOrgGuid(field.getAttributeValue("Value"));
      } else if ("Parent_id".equalsIgnoreCase(colName)) {
        org.setOrgParentId("".equals(field.getAttributeValue("Value")) ? "0" : field.getAttributeValue("Value"));
      } else if ("B0180".equalsIgnoreCase(colName)) {
        org.setOrgLeader(field.getAttributeValue("Value"));
      } else if ("GRADE".equalsIgnoreCase(colName)) {
        org.setOrgNameString(field.getAttributeValue("Value"));
      } 
    } 
    return org;
  }
}
