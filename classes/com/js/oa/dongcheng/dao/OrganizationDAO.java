package com.js.oa.dongcheng.dao;

import com.js.oa.dongcheng.db.DBUtil;
import com.js.oa.dongcheng.pojo.OrganizationPojo;
import com.js.util.config.SystemCommon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrganizationDAO {
  private Connection conn = null;
  
  private PreparedStatement ps = null;
  
  private ResultSet rs = null;
  
  public String insertOrganization(OrganizationPojo org, String orderOrgGUID, String tag) {
    if (org == null)
      return "{result:'缁勭粐淇℃伅涓嶈兘涓虹┖銆�}"; 
    if (org.getOrgGUID() == null || "".equals(org.getOrgGUID()))
      return "{result:'缁勭粐鍞竴鏍囪瘑涓虹┖銆�}"; 
    if (org.getOrgName() == null || "".equals(org.getOrgName()))
      return "{result:'缁勭粐鍚嶇О涓虹┖銆�}"; 
    if (org.getOrgDescripte() == null)
      org.setOrgDescripte(""); 
    if (org.getOrgParentOrgGUID() == null || "".equals(org.getOrgParentOrgGUID())) {
      org.setOrgParentOrgId(0L);
      org.setOrgParentOrgGUID("");
    } 
    String result = "";
    String orgIdString = "";
    String orgNameString = "";
    int orgLevel = 0;
    try {
      if (!"".equals(org.getOrgParentOrgGUID())) {
        if (org.getOrgGUID().equals(org.getOrgParentOrgGUID()))
          return "{result:'涓婄骇缁勭粐涓嶈兘鏄嚜宸便�'}"; 
        this.conn = DBUtil.getConn();
        OrganizationPojo parentOrg = findOrgByOrgGUID(org.getOrgParentOrgGUID());
        if (parentOrg == null)
          return "{result:'涓婄骇缁勭粐涓嶅瓨鍦�}"; 
        org.setOrgParentOrgId(parentOrg.getOrgId());
        orgIdString = parentOrg.getOrgIdString();
        orgNameString = String.valueOf(parentOrg.getOrgName()) + "." + org.getOrgName();
        orgLevel = parentOrg.getOrgLevel() + 1;
      } else {
        orgIdString = "_500000$-1$";
        orgNameString = org.getOrgName();
      } 
      List<OrganizationPojo> orgs = findByNameAndLevel(org.getOrgName(), orgLevel);
      if (orgs == null || orgs.size() == 0) {
        int orgOrderCode = 0;
        if (orderOrgGUID != null && !"".equals(orderOrgGUID)) {
          orgOrderCode = getOrderCode(orderOrgGUID, tag);
          if (orgOrderCode == 0)
            return "{result:'鐩稿鎺掑簭缁勭粐涓嶅瓨鍦ㄣ�'}"; 
        } else {
          orgOrderCode = getOrderCode(orgLevel, org.getOrgParentOrgId());
        } 
        orgIdString = String.valueOf(orgIdString) + "_" + orgOrderCode + "$";
        String databaseType = SystemCommon.getDatabaseType();
        String sql = null;
        if (databaseType.indexOf("mysql") >= 0) {
          sql = "insert into org_organization(ORGNAME,ORGSIMPLENAME,ORGPARENTORGID,ORGORDERCODE,ORGDESCRIPTE,ORGLEVEL,ORGHASJUNIOR,ORGSTATUS,ORGNAMESTRING,GUID,ORGIDSTRING,ORGHASCHANNEL,DOMAIN_ID) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        } else if (databaseType.indexOf("oracle") >= 0) {
          sql = "insert into org_organization(ORGNAME,ORGSIMPLENAME,ORGPARENTORGID,ORGORDERCODE,ORGDESCRIPTE,ORGLEVEL,ORGHASJUNIOR,ORGSTATUS,ORGNAMESTRING,GUID,ORGIDSTRING,ORGHASCHANNEL,DOMAIN_ID,ORG_ID) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
          this.ps = this.conn.prepareStatement("select HIBERNATE_SEQUENCE.nextval from dual");
          this.rs = this.ps.executeQuery();
          if (this.rs.next())
            org.setOrgId(this.rs.getLong(1)); 
        } 
        this.ps = this.conn.prepareStatement(sql);
        this.ps.setString(1, org.getOrgName());
        this.ps.setString(2, (org.getOrgSimpleName() == null) ? "" : org.getOrgSimpleName());
        this.ps.setLong(3, org.getOrgParentOrgId());
        this.ps.setInt(4, orgOrderCode);
        this.ps.setString(5, org.getOrgDescripte());
        this.ps.setInt(6, orgLevel);
        this.ps.setInt(7, 0);
        this.ps.setInt(8, 0);
        this.ps.setString(9, orgNameString);
        this.ps.setString(10, org.getOrgGUID());
        this.ps.setString(11, String.valueOf(orgIdString) + org.getOrgId() + "$");
        this.ps.setInt(12, 0);
        this.ps.setInt(13, 0);
        if (org.getOrgId() > 0L)
          this.ps.setLong(14, org.getOrgId()); 
        int n = this.ps.executeUpdate();
        if (n > 0)
          return "{result:'1'}"; 
        return "{result:'鏂板缁勭粐澶辫触'}";
      } 
      return "{result:'鍚岀骇缁勭粐鍚嶇О閲嶅銆�}";
    } catch (Exception e) {
      e.printStackTrace();
      result = "{result:" + e.getMessage() + "}";
    } finally {
      DBUtil.closeAll(this.rs, this.ps, this.conn);
    } 
    return result;
  }
  
  public String updateOrganization(OrganizationPojo org, String orderOrgGUID, String tag) {
    if (org == null)
      return "{result:'缁勭粐淇℃伅涓嶈兘涓虹┖銆�}"; 
    if (org.getOrgGUID() == null || "".equals(org.getOrgGUID()))
      return "{result:'缁勭粐鍞竴鏍囪瘑涓虹┖銆�}"; 
    if (org.getOrgName() == null || "".equals(org.getOrgName()))
      return "{result:'缁勭粐鍚嶇О涓虹┖銆�}"; 
    if (org.getOrgParentOrgGUID() == null || "".equals(org.getOrgParentOrgGUID())) {
      org.setOrgParentOrgId(0L);
      org.setOrgParentOrgGUID("");
    } 
    String result = "";
    try {
      this.conn = DBUtil.getConn();
      OrganizationPojo oldOrg = findOrgByOrgGUID(org.getOrgGUID());
      List<Object> objs = new ArrayList();
      if (oldOrg == null)
        return "{result:'缁勭粐淇℃伅涓嶅瓨鍦ㄣ�'}"; 
      StringBuffer sql = new StringBuffer("update org_organization set orgname=?,orgsimplename=?");
      objs.add(org.getOrgName());
      objs.add(org.getOrgName());
      if (org.getOrgDescripte() != null) {
        sql.append(",orgdescripte=?");
        objs.add(org.getOrgDescripte());
      } 
      int orgLevel = 0;
      String orgIdString = "";
      String orgNameString = "";
      if (org.getOrgParentOrgGUID() != null) {
        sql.append(",orgparentorgid=?");
        if ("".equals(org.getOrgParentOrgGUID())) {
          objs.add(Integer.valueOf(0));
          orgIdString = "_500000$-1$";
          orgNameString = org.getOrgName();
        } else {
          OrganizationPojo parentOrg = findOrgByOrgGUID(org.getOrgParentOrgGUID());
          if (parentOrg == null)
            return "{result:'涓婄骇缁勭粐涓嶅瓨鍦ㄣ�'}"; 
          orgIdString = parentOrg.getOrgIdString();
          orgNameString = String.valueOf(parentOrg.getOrgNameString()) + "." + org.getOrgName();
          orgLevel = parentOrg.getOrgLevel() + 1;
          objs.add(Long.valueOf(parentOrg.getOrgId()));
        } 
      } 
      sql.append(",orglevel=?");
      objs.add(Integer.valueOf(orgLevel));
      int orgOrderCode = 0;
      if (orderOrgGUID != null) {
        sql.append(",orgordercode=?");
        if (orderOrgGUID != null && !"".equals(orderOrgGUID)) {
          orgOrderCode = getOrderCode(orderOrgGUID, tag);
          if (orgOrderCode == 0)
            return "{result:'鐩稿鎺掑簭缁勭粐涓嶅瓨鍦ㄣ�'}"; 
        } else {
          orgOrderCode = getOrderCode(orgLevel, org.getOrgParentOrgId());
        } 
        objs.add(Integer.valueOf(orgOrderCode));
      } 
      sql.append(",orgidstring=?,orgnamestring=?");
      orgIdString = String.valueOf(orgIdString) + "_" + orgOrderCode + "$";
      objs.add(orgIdString);
      objs.add(orgNameString);
      sql.append(" where guid=?");
      objs.add(org.getOrgGUID());
      if (objs.size() > 0) {
        this.ps = this.conn.prepareStatement(sql.toString());
        for (int i = 0; i < objs.size(); i++)
          this.ps.setObject(i + 1, objs.get(i)); 
        int n = this.ps.executeUpdate();
        if (n > 0)
          return "{result:'1'}"; 
        return "{result:'缁勭粐淇℃伅淇敼澶辫触銆�}";
      } 
    } catch (Exception e) {
      e.printStackTrace();
      result = "{result:" + e.getMessage() + "}";
    } finally {
      DBUtil.closeAll(this.rs, this.ps, this.conn);
    } 
    return result;
  }
  
  private OrganizationPojo findOrgByOrgGUID(String orgGUID) {
    OrganizationPojo org = null;
    String sql = "select ORG_ID,ORGPARENTORGID,ORGNAME,ORGDESCRIPTE,ORGSTATUS,ORGORDERCODE,ORGIDSTRING,ORGSIMPLENAME,GUID,ORGLEVEL,ORGNAMESTRING from org_organization where (ORGSTATUS=0 or ORGSTATUS=1) and guid=?";
    try {
      this.ps = this.conn.prepareStatement(sql);
      this.ps.setString(1, orgGUID);
      this.rs = this.ps.executeQuery();
      if (this.rs.next()) {
        org = new OrganizationPojo();
        org.setOrgId(this.rs.getLong(1));
        org.setOrgParentOrgId(this.rs.getLong(2));
        org.setOrgName(this.rs.getString(3));
        org.setOrgDescripte(this.rs.getString(4));
        org.setOrgStatus(this.rs.getInt(5));
        org.setOrgOrderCode(this.rs.getInt(6));
        org.setOrgIdString(this.rs.getString(7));
        org.setOrgSimpleName(this.rs.getString(8));
        org.setOrgGUID(this.rs.getString(9));
        org.setOrgLevel(this.rs.getInt(10));
        org.setOrgNameString(this.rs.getString(11));
      } 
    } catch (SQLException e) {
      e.printStackTrace();
    } 
    return org;
  }
  
  private int getOrderCode(String orderOrgGUID, String tag) {
    int orderCode = 0;
    int pCode = 0;
    int nearCode = 0;
    long orgParentOrgId = 0L;
    long orgId = 0L;
    try {
      String sql1 = "select org1.ORGORDERCODE,org1.ORG_ID,org1.ORGPARENTORGID from org_organization org1 where org1.ORGSTATUS in(0,1) and org1.guid=?";
      this.ps = this.conn.prepareStatement(sql1);
      this.ps.setString(1, orderOrgGUID);
      this.rs = this.ps.executeQuery();
      if (this.rs.next()) {
        pCode = this.rs.getInt(1);
        orgId = this.rs.getLong(2);
        orgParentOrgId = this.rs.getLong(3);
      } else {
        return 0;
      } 
      String sql = "select org1.ORGORDERCODE from org_organization org1 where org1.ORGSTATUS in(0,1) and org1.ORGPARENTORGID=? and org1.ORG_ID<>? and org1.ORGORDERCODE between ";
      if ("+".equals(tag)) {
        sql = String.valueOf(sql) + pCode + " and " + (pCode + 5000) + " order by org1.ORGORDERCODE ";
      } else if ("-".equals(tag)) {
        sql = String.valueOf(sql) + (pCode - 5000) + " and " + pCode + " order by org1.ORGORDERCODE desc";
      } else {
        return 0;
      } 
      this.ps = this.conn.prepareStatement(sql);
      this.ps.setLong(1, orgParentOrgId);
      this.ps.setLong(2, orgId);
      this.rs = this.ps.executeQuery();
      if (this.rs.next())
        nearCode = this.rs.getInt(1); 
    } catch (SQLException e) {
      e.printStackTrace();
    } 
    if (nearCode > 0) {
      orderCode = (pCode + nearCode) / 2;
    } else if ("+".equals(tag)) {
      orderCode = pCode + 5000;
    } else if ("-".equals(tag)) {
      orderCode = pCode - 5000;
    } else {
      return 0;
    } 
    return orderCode;
  }
  
  private int getOrderCode(int orgLevel, long pOrgId) {
    int orderCode = 500000;
    try {
      String sql = "select org1.ORGORDERCODE from org_organization org1 where org1.ORGSTATUS in(0,1) and org1.ORGLEVEL=" + orgLevel + " and org1.ORGPARENTORGID=" + pOrgId + " order by org1.ORGORDERCODE desc ";
      this.ps = this.conn.prepareStatement(sql);
      this.rs = this.ps.executeQuery(sql);
      if (this.rs.next())
        orderCode = this.rs.getInt(1); 
    } catch (SQLException e) {
      e.printStackTrace();
    } 
    return orderCode + 5000;
  }
  
  private List<OrganizationPojo> findByNameAndLevel(String orgName, int orgLevel) {
    List<OrganizationPojo> orgs = new ArrayList<OrganizationPojo>();
    OrganizationPojo org = null;
    String sql = "select ORG_ID,ORGPARENTORGID,ORGNAME,ORGDESCRIPTE,ORGSTATUS,ORGORDERCODE,ORGIDSTRING,ORGSIMPLENAME,GUID,ORGLEVEL,ORGNAMESTRING from org_organization where (ORGSTATUS=0 or ORGSTATUS=1) and ORGNAME=? and ORGLEVEL=?";
    try {
      this.ps = this.conn.prepareStatement(sql);
      this.ps.setString(1, orgName);
      this.ps.setInt(2, orgLevel);
      this.rs = this.ps.executeQuery();
      while (this.rs.next()) {
        org = new OrganizationPojo();
        org.setOrgId(this.rs.getLong(1));
        org.setOrgParentOrgId(this.rs.getLong(2));
        org.setOrgName(this.rs.getString(3));
        org.setOrgDescripte(this.rs.getString(4));
        org.setOrgStatus(this.rs.getInt(5));
        org.setOrgOrderCode(this.rs.getInt(6));
        org.setOrgIdString(this.rs.getString(7));
        org.setOrgSimpleName(this.rs.getString(8));
        org.setOrgGUID(this.rs.getString(9));
        org.setOrgLevel(this.rs.getInt(10));
        org.setOrgNameString(this.rs.getString(11));
        orgs.add(org);
      } 
    } catch (SQLException e) {
      e.printStackTrace();
    } 
    return orgs;
  }
}
