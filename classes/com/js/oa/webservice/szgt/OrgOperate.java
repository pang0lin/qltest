package com.js.oa.webservice.szgt;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import com.js.util.util.IO2File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrgOperate {
  private String databaseType = SystemCommon.getDatabaseType();
  
  public String saveOrg(List<OrgPO> orgs) {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String flag = "success";
    try {
      base.begin();
      for (OrgPO org : orgs) {
        if (org == null) {
          IO2File.printFile("错误信息：请提交正确格式的参数", "组织信息操作");
          continue;
        } 
        if (org.getOrgId() == null || "".equals(org.getOrgId())) {
          IO2File.printFile("错误信息：组织唯一标识不能为空", "组织信息操作");
          continue;
        } 
        if (!"success".equals(flag))
          break; 
        String sql = "";
        if ("del".equals(org.getOperateType())) {
          if ("oracle".equals(this.databaseType)) {
            sql = "select 1 from org_organization_user where org_id=(select nvl(org_id,0) from org_organization where guid='" + org.getOrgId() + "')";
          } else {
            sql = "select 1 from org_organization_user where org_id=(select ifnull(org_id,0) from org_organization where guid='" + org.getOrgId() + "')";
          } 
          rs = base.executeQuery(sql);
          if (rs.next()) {
            IO2File.printFile("组织下存在人员，不能删除", "组织信息操作");
            continue;
          } 
          sql = "update org_organization set orgstatus=4 where guid='" + org.getOrgId() + "'";
          base.executeUpdate(sql);
          continue;
        } 
        if (org.getOrgParentId() == null || "".equals(org.getOrgParentId())) {
          IO2File.printFile("错误信息：上级组织唯一标识不能为空", "组织信息操作");
          continue;
        } 
        if (org.getOrgName() == null || "".equals(org.getOrgName())) {
          IO2File.printFile("错误信息：组织名称不能为空", "组织信息操作");
          continue;
        } 
        sql = "select org_id,orgIdString,orgNameString,orglevel from org_organization where orgstatus<>4 and guid='" + org.getOrgParentId() + "'";
        rs = base.executeQuery(sql);
        IO2File.printFile("查询上级部门：" + sql, "组织信息操作");
        if (rs.next()) {
          String parentId = rs.getString(1);
          String orgIdString = rs.getString(2);
          String orgNameString = String.valueOf(rs.getString(3)) + ".";
          IO2File.printFile("上级部门idstring：" + orgIdString + ",nameString：" + orgNameString, "组织信息操作");
          if ("0".equals(orgIdString)) {
            orgIdString = "_500000$-1$";
            orgNameString = "";
          } 
          int orgLevel = rs.getInt(4) + 1;
          sql = "select org_id,orgordercode from org_organization where orgstatus<>4 and guid<>'" + org.getOrgId() + "' and orgname='" + org.getOrgName() + "' and orgparentorgid=" + parentId;
          rs = base.executeQuery(sql);
          IO2File.printFile("查询同名部门：" + sql, "组织信息操作");
          if (rs.next()) {
            IO2File.printFile("错误信息：上级组织下已存在相同名称部门：" + org.getOrgName(), "组织信息操作");
            continue;
          } 
          sql = "select 1 from org_organization where orgstatus<>4 and guid<>'" + org.getOrgId() + "' and orgcode='" + org.getOrgCode() + "'";
          IO2File.printFile("查询组织编码是否重复：" + sql, "组织信息操作");
          rs = base.executeQuery(sql);
          if (rs.next()) {
            IO2File.printFile("错误信息：" + org.getOrgName() + "：部门编码重复：" + org.getOrgCode(), "组织信息操作");
            continue;
          } 
          long orgId = 0L;
          long orgOrderCode = getOrderCode(parentId);
          sql = "select org_id,orgordercode from org_organization where orgstatus<>4 and guid='" + org.getOrgId() + "'";
          rs = base.executeQuery(sql);
          IO2File.printFile("查询是否存在：" + sql, "组织信息操作");
          orgNameString = String.valueOf(orgNameString) + org.getOrgName();
          if (rs.next()) {
            orgId = rs.getLong(1);
            orgOrderCode = rs.getLong(2);
            orgIdString = String.valueOf(orgIdString) + "_" + orgOrderCode + "$" + orgId + "$";
            String orgManagerId = "";
            String orgManagerName = "";
            if (org.getManager() != null && !"".equals(org.getManager()) && !"null".equalsIgnoreCase(org.getManager())) {
              sql = "select emp_id,empname from org_employee where userisdeleted=0 and userisactive=1 and guid='" + org.getManager() + "'";
              IO2File.printFile("查询组织领导是否存在：" + sql, "组织信息操作");
              rs = base.executeQuery(sql);
              if (rs.next()) {
                orgManagerId = "$" + rs.getString(1) + "$";
                orgManagerName = String.valueOf(rs.getString(2)) + ",";
              } else {
                IO2File.printFile("组织领导不存在：" + org.getManager(), "组织信息操作");
                continue;
              } 
            } 
            sql = "update org_organization set orgname='" + org.getOrgName() + "',orgparentorgid=" + parentId + 
              ",orgsimplename='" + org.getOrgName() + "',orgordercode=" + orgOrderCode + ",orglevel=" + 
              orgLevel + ",orgidstring='" + orgIdString + "',orgnamestring='" + orgNameString + 
              "',orgmanagerempid='" + orgManagerId + "',orgmanagerempname='" + orgManagerName + 
              "',orgcode='" + org.getOrgCode() + "',orgSerial='" + org.getOrgCode() + "' where org_id=" + orgId;
            base.executeUpdate(sql);
            IO2File.printFile("更新组织信息：" + sql, "组织信息操作");
          } else {
            orgOrderCode = getOrderCode(parentId);
            if ("oracle".equals(this.databaseType)) {
              sql = "insert into org_organization(org_id,orgparentorgid,orgname,orgsimplename,orgcode,orgordercode,orgdescripte,orglevel,orghasjunior,orgstatus,orgnamestring,orghaschannel,orgserial,domain_id,guid,orgtype,orgchanneltype) values (hibernate_sequence.nextval," + 


                
                parentId + ",'" + org.getOrgName() + "','" + org.getOrgName() + "','" + org.getOrgCode() + "'," + orgOrderCode + 
                ",''," + orgLevel + ",0,0,'" + orgNameString + 
                "',0,'" + org.getOrgCode() + "',0,'" + org.getOrgId() + "',1,0)";
            } else {
              sql = "insert into org_organization(orgparentorgid,orgname,orgsimplename,orgcode,orgordercode,orgdescripte,orglevel,orghasjunior,orgstatus,orgnamestring,orghaschannel,orgserial,domain_id,guid,orgtype,orgchanneltype) values (" + 


                
                parentId + ",'" + org.getOrgName() + "','" + org.getOrgName() + "','" + org.getOrgCode() + "'," + orgOrderCode + 
                ",''," + orgLevel + ",0,0,'" + orgNameString + 
                "',0,'" + org.getOrgCode() + "',0,'" + org.getOrgId() + "',1,0)";
            } 
            base.executeUpdate(sql);
            IO2File.printFile("新增组织信息：" + sql, "组织信息操作");
            sql = "select org_id from org_organization where guid='" + org.getOrgId() + "'";
            rs = base.executeQuery(sql);
            IO2File.printFile("查询新增ID：" + sql, "组织信息操作");
            if (rs.next())
              orgId = rs.getLong(1); 
            orgIdString = String.valueOf(orgIdString) + "_" + orgOrderCode + "$" + orgId + "$";
            sql = "update org_organization set orgidstring='" + orgIdString + "' where org_id=" + orgId;
            base.executeUpdate(sql);
            IO2File.printFile("--" + org.getCancle() + "更新idString：" + sql, "组织信息操作");
          } 
          IO2File.printFile("当前部门idstring：" + orgIdString + ",nameString：" + orgNameString, "组织信息操作");
          if ("Y".equalsIgnoreCase(org.getCancle())) {
            if ("oracle".equals(this.databaseType)) {
              sql = "select 1 from org_organization_user oou where oou.org_id=(select nvl(org_id,0) from org_organization where orgstatus<>4 and guid='" + org.getOrgId() + "') and exists (select oe.emp_id from org_employee oe where oe.emp_id=oou.emp_id and oe.userisactive=1)";
            } else {
              sql = "select 1 from org_organization_user oou where oou.org_id=(select ifnull(org_id,0) from org_organization where orgstatus<>4 and guid='" + org.getOrgId() + "') and exists (select oe.emp_id from org_employee oe where oe.emp_id=oou.emp_id and oe.userisactive=1)";
            } 
            IO2File.printFile("查询组织下人员：" + sql, "组织信息操作");
            rs = base.executeQuery(sql);
            if (rs.next()) {
              IO2File.printFile("组织下存在人员，不能禁用", "组织信息操作");
              continue;
            } 
            if ("oracle".equals(this.databaseType)) {
              sql = "select orgname from org_organization where orgstatus<>4 and orgstatus<>1 and orgparentorgid=(select nvl(max(org_id),-2) from org_organization where guid='" + org.getOrgId() + "')";
            } else {
              sql = "select orgname from org_organization where orgstatus<>4 and orgstatus<>1 and orgparentorgid=(select ifnull(max(org_id),-2) from org_organization where guid='" + org.getOrgId() + "')";
            } 
            IO2File.printFile("查询组织下级：" + sql, "组织信息操作");
            rs = base.executeQuery(sql);
            if (rs.next()) {
              IO2File.printFile("存在下级部门" + rs.getString(1) + "，不能禁用", "组织信息操作");
              continue;
            } 
            sql = "update org_organization set orgstatus=1 where guid='" + org.getOrgId() + "'";
            IO2File.printFile("禁用组织：" + sql, "组织信息操作");
            base.executeUpdate(sql);
            continue;
          } 
          if ("N".equalsIgnoreCase(org.getCancle())) {
            sql = "update org_organization set orgstatus=0 where guid='" + org.getOrgId() + "'";
            IO2File.printFile("启用组织：" + sql, "组织信息操作");
            base.executeUpdate(sql);
          } 
          continue;
        } 
        IO2File.printFile("错误信息：组织上级部门不存在", "组织信息操作");
      } 
    } catch (Exception e) {
      e.printStackTrace();
      IO2File.printFile("错误信息：" + e.getMessage(), "组织信息操作");
      flag = e.getMessage();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        base.end();
      } catch (SQLException e) {
        e.printStackTrace();
        IO2File.printFile("错误信息：" + e.getMessage(), "组织信息操作");
        flag = e.getMessage();
      } 
    } 
    return flag;
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
}
