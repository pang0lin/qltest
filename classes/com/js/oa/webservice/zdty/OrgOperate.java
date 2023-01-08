package com.js.oa.webservice.zdty;

import com.js.util.util.DataSourceBase;
import com.js.util.util.IO2File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrgOperate {
  public boolean saveOrgs() {
    boolean result = true;
    List<OrgPO> orgs = queryOrgs();
    IO2File.printFile("共接收组织机构信息" + orgs.size(), "组织同步");
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    int n = 0;
    try {
      if (orgs.size() > 0) {
        base.begin();
        String sql = "";
        for (OrgPO o : orgs) {
          IO2File.printFile("\n\r*****************************************\n\r", "组织同步");
          if (o.getOrgGuid() == null || "".equals(o.getOrgGuid())) {
            IO2File.printFile("部门唯一标识为空。", "组织同步");
            result = false;
            break;
          } 
          if (o.getOrgParentId() == null || "".equals(o.getOrgParentId())) {
            IO2File.printFile("部门上级标识为空。", "组织同步");
            result = false;
            break;
          } 
          if (o.getOrgName() == null || "".equals(o.getOrgName())) {
            IO2File.printFile("部门名称为空。", "组织同步");
            result = false;
            break;
          } 
          if (result) {
            sql = "select org_id,orgname,orgIdString,orgNameString,ORGPARENTORGID,orglevel+1 orglevel from org_organization where orgserial='" + o.getOrgParentId() + "' ORDER BY org_id DESC";
            IO2File.printFile("获取上级部门信息（" + o.getOrgName() + "）：" + sql, "组织同步");
            rs = base.executeQuery(sql);
            if (rs.next()) {
              o.setOrgParentId(rs.getString("org_id"));
              long orgOrderCode = getOrderCode(o.getOrgParentId());
              o.setOrgOrderCode((new StringBuilder(String.valueOf(orgOrderCode))).toString());
              String orgIdString = rs.getString("orgIdString");
              if ("0".equals(orgIdString)) {
                orgIdString = "_500000$-1$_500000$ORGID$";
              } else {
                orgIdString = String.valueOf(orgIdString) + "_" + orgOrderCode + "$ORGID$";
              } 
              o.setOrgIdString(orgIdString);
              String orgNameString = rs.getString("orgNameString");
              if (orgNameString != null && "null".equalsIgnoreCase(orgNameString) && !"".equals(orgNameString)) {
                orgNameString = String.valueOf(orgNameString) + "." + o.getOrgName();
              } else {
                orgNameString = o.getOrgName();
              } 
              o.setOrgNameString(orgNameString);
              o.setOrgLevel(rs.getInt("orglevel"));
              sql = "select org_id,orgname,orgIdString,orgNameString,ORGPARENTORGID from org_organization where orgserial='" + o.getOrgGuid() + "' ORDER BY org_id DESC";
              IO2File.printFile("查询" + o.getOrgName() + "是否存在：" + sql, "组织同步");
              rs = base.executeQuery(sql);
              if (rs.next()) {
                o.setOrgId(rs.getString("org_id"));
                o.setOrgIdString(orgIdString.replace("ORGID", o.getOrgId()));
                sql = "select 1 from org_organization where orgname='" + o.getOrgName() + "' and org_id<>" + o.getOrgId() + " and orgparentorgid=" + o.getOrgParentId();
                IO2File.printFile("查询同级组织中是否存在" + o.getOrgName() + ":" + sql, "组织同步");
                rs = base.executeQuery(sql);
                if (rs.next()) {
                  IO2File.printFile(String.valueOf(o.getOrgName()) + "在同级组织已经存在，更新失败。", "组织同步");
                  result = false;
                  break;
                } 
                n = updateOrg(o, base);
              } else {
                sql = "select 1 from org_organization where orgname='" + o.getOrgName() + "' and orgparentorgid=" + o.getOrgParentId();
                IO2File.printFile("查询同级组织中是否存在" + o.getOrgName() + ":" + sql, "组织同步");
                rs = base.executeQuery(sql);
                if (rs.next()) {
                  IO2File.printFile(String.valueOf(o.getOrgName()) + "在同级组织已经存在，新增失败。", "组织同步");
                  result = false;
                  break;
                } 
                n = insertOrg(o, base);
              } 
              if (n == 0) {
                IO2File.printFile("数据保存失败。", "组织同步");
                result = false;
                break;
              } 
              continue;
            } 
            IO2File.printFile(String.valueOf(o.getOrgName()) + "上级部门不存在。" + o.getOrgGuid(), "组织同步");
            result = false;
          } 
          break;
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      result = false;
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        base.end();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
      IO2File.printFile("同步组织完成：" + result, "组织同步");
    } 
    return result;
  }
  
  private int updateOrg(OrgPO org, DataSourceBase base) {
    String sql = "update org_organization set ORGPARENTORGID=" + org.getOrgParentId() + ",ORGNAME='" + org.getOrgName() + "'," + 
      "ORGIDSTRING='" + org.getOrgIdString() + "',orglevel=" + org.getOrgLevel() + "," + 
      "ORGNAMESTRING='" + org.getOrgNameString() + "',lastupdate=" + System.currentTimeMillis() + " where org_id=" + org.getOrgId();
    IO2File.printFile("更新组织" + org.getOrgName() + ":" + sql, "组织同步");
    return base.executeUpdate(sql);
  }
  
  private int insertOrg(OrgPO org, DataSourceBase base) {
    String orgId = "0";
    ResultSet rs = null;
    int n = 0;
    try {
      rs = base.executeQuery("select hibernate_sequence.nextval from dual");
      if (rs.next())
        orgId = rs.getString(1); 
      if (rs != null)
        rs.close(); 
      String sql = "INSERT INTO org_organization (org_id,ORGPARENTORGID,ORGMANAGEREMPID,ORGMANAGEREMPNAME,ORGNAME,ORGSIMPLENAME,ORGCODE,ORGORDERCODE,ORGDESCRIPTE,ORGLEVEL,ORGHASJUNIOR,ORGSTATUS,ORGIDSTRING,ORGNAMESTRING,orghaschannel,ORGSERIAL,DOMAIN_ID,lastupdate,orgtype,orgChannelType) VALUES(" + 


        
        orgId + "," + org.getOrgParentId() + ",'','','" + org.getOrgName() + "',''," + 
        "''," + org.getOrgOrderCode() + ",''," + org.getOrgLevel() + ",'0'," + 
        "'0','" + org.getOrgIdString().replace("ORGID", orgId) + "','" + org.getOrgNameString() + "','0'," + 
        "'" + org.getOrgGuid() + "','0'," + System.currentTimeMillis() + ",'1','0')";
      n = base.executeUpdate(sql);
      IO2File.printFile("新增组织" + org.getOrgName() + ":" + n + ":" + sql, "组织同步");
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return n;
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
  
  private List<OrgPO> queryOrgs() {
    String databaseName = "zdty";
    String getOrganizationSQL = "select * from V_BRW_Institute";
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<OrgPO> orgs = new ArrayList<OrgPO>();
    OrgPO org = null;
    try {
      DataSourceBase base = new DataSourceBase();
      conn = base.getDataSource(databaseName).getConnection();
      ps = conn.prepareStatement(getOrganizationSQL);
      rs = ps.executeQuery();
      while (rs.next()) {
        org = new OrgPO();
        org.setOrgGuid(rs.getString("USER_DM"));
        org.setOrgName(rs.getString("ZWMC"));
        org.setOrgSimpleName((rs.getString("YWMC") == null) ? "" : rs.getString("YWMC"));
        org.setOrgParentId("1");
        org.setOrgGuid(org.getOrgGuid().trim());
        orgs.add(org);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        if (ps != null)
          ps.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception e1) {
        e1.getStackTrace();
      } 
    } 
    return orgs;
  }
}
