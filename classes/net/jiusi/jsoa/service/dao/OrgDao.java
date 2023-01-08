package net.jiusi.jsoa.service.dao;

import com.js.util.config.SystemCommon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import net.jiusi.jsoa.service.IDBConnection;
import net.jiusi.jsoa.service.impl.DBConnectionImpl;
import net.jiusi.jsoa.service.pojo.OrganizationPojo;

public class OrgDao {
  private IDBConnection idb = new DBConnectionImpl();
  
  public String modifyOrgStatus(String orgSerial, int status) {
    String reValue = "-1";
    long pId = 0L;
    OrganizationPojo op = findOrgByOrgSerial(orgSerial);
    if (op == null)
      return "1"; 
    pId = op.getOrgParentOrgId();
    Connection conn = this.idb.getConnection();
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      String orgIds = findSubOrgsByOrgSerial(orgSerial, "0");
      stmt.execute("update org_organization set ORGSTATUS=" + status + " where ORG_ID in(" + orgIds + ")");
      String updateEmpSql = "update org_employee emp set ";
      if (status == 0) {
        updateEmpSql = String.valueOf(updateEmpSql) + "emp.USERISACTIVE=1";
      } else if (status == 1) {
        updateEmpSql = String.valueOf(updateEmpSql) + "emp.USERISACTIVE=0";
      } else if (status == 4) {
        stmt.execute("delete from org_organization_user where org_id in (" + orgIds + ")");
        stmt.executeUpdate(" UPDATE ORG_ORGANIZATION SET orgserial='' where org_id in (" + orgIds + ")");
        conn.commit();
        updateEmpSql = String.valueOf(updateEmpSql) + "emp.USERISDELETED=1";
      } else {
        System.out.println("ERROR,....................");
      } 
      updateEmpSql = String.valueOf(updateEmpSql) + " where emp.EMP_ID in(select oou.EMP_ID from org_organization_user oou where oou.ORG_ID in(" + orgIds + ") )";
      stmt.executeUpdate(updateEmpSql);
      conn.commit();
      rs = stmt.executeQuery("select org1.ORGSERIAL from org_organization org1 where org1.ORGSTATUS in(0,1) and org1.ORG_ID=" + pId);
      String parentOrgSerial = null;
      if (rs.next())
        parentOrgSerial = rs.getString(1); 
      String orgListIds = findSubOrgsByOrgSerial(parentOrgSerial, "1");
      if ("".equals(orgListIds))
        stmt.execute("update org_organization set ORGHASJUNIOR=0 where ORGSERIAL='" + parentOrgSerial + "'"); 
      conn.commit();
      reValue = "0";
    } catch (SQLException e) {
      e.printStackTrace();
      try {
        conn.rollback();
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
    } finally {
      this.idb.close(rs, null, stmt, conn);
    } 
    return reValue;
  }
  
  public OrganizationPojo findOrgByOrgSerial(String orgSerial) {
    OrganizationPojo org = null;
    Connection conn = this.idb.getConnection();
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = "select ORG_ID,ORGPARENTORGID,ORGNAME,ORGDESCRIPTE,ORGSTATUS,ORGORDERCODE,ORGIDSTRING,ORGSIMPLENAME,ORGSERIAL,ORGLEVEL,ORGNAMESTRING from org_organization where ORGSTATUS in(0,1) and ORGSERIAL=?";
    try {
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, orgSerial);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        org = new OrganizationPojo();
        org.setOrgId(rs.getLong(1));
        org.setOrgParentOrgId(rs.getLong(2));
        org.setOrgName(rs.getString(3));
        org.setOrgDescripte(rs.getString(4));
        org.setOrgStatus(rs.getInt(5));
        org.setOrgOrderCode(rs.getInt(6));
        org.setOrgIdString(rs.getString(7));
        org.setOrgSimpleName(rs.getString(8));
        org.setOrgSerial(rs.getString(9));
        org.setOrgLevel(rs.getInt(10));
        org.setOrgNameString(rs.getString(11));
      } 
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      this.idb.close(rs, pstmt, null, conn);
    } 
    return org;
  }
  
  public String findSubOrgsByOrgSerial(String orgSerial, String tag) {
    StringBuffer orgIds = new StringBuffer("");
    Connection conn = this.idb.getConnection();
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = "select org1.ORG_ID from org_organization org1 where ";
    if ("1".equals(tag))
      sql = String.valueOf(sql) + " org1.ORGSERIAL<>? and "; 
    sql = String.valueOf(sql) + " org1.orgstatus in(0,1)" + " and org1.ORGIDSTRING like (select CONCAT(CONCAT('%',org2.ORGIDSTRING),'%') " + 
      "from org_organization org2 where org2.ORGSTATUS in(0,1) and org2.ORGSERIAL=?)";
    try {
      pstmt = conn.prepareStatement(sql);
      if ("1".equals(tag)) {
        pstmt.setString(1, orgSerial);
        pstmt.setString(2, orgSerial);
      } else {
        pstmt.setString(1, orgSerial);
      } 
      rs = pstmt.executeQuery();
      while (rs.next())
        orgIds.append("," + rs.getLong(1)); 
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      this.idb.close(rs, pstmt, null, conn);
    } 
    if (orgIds.length() < 1)
      return ""; 
    return orgIds.substring(1);
  }
  
  public String modifyOrg(OrganizationPojo org, String orderOrgSerial, String tag) {
    List<Object> list = new ArrayList();
    String reValue = "-1";
    String orgSerial = org.getOrgSerial();
    int levelTag = 0;
    boolean parentTag = false;
    boolean nameTag = false;
    String orgListIds = findSubOrgsByOrgSerial(orgSerial, "1");
    Connection conn = this.idb.getConnection();
    PreparedStatement pstmt = null;
    Statement stmt = null;
    try {
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      if (orgSerial == null || "".equals(orgSerial))
        return "1"; 
      OrganizationPojo beforeUpdateOrg = findOrgByOrgSerial(orgSerial);
      if (beforeUpdateOrg == null)
        return "2"; 
      StringBuffer sqlsb = new StringBuffer("update org_organization set ORG_ID=ORG_ID ");
      String orgSimpleName = org.getOrgSimpleName();
      if (orgSimpleName != null && !"".equals(orgSimpleName)) {
        sqlsb.append(",ORGSIMPLENAME=?");
        list.add(orgSimpleName);
      } 
      String orgDescripte = org.getOrgDescripte();
      if (orgDescripte != null && !"".equals(orgDescripte)) {
        sqlsb.append(",ORGDESCRIPTE=?");
        list.add(orgDescripte);
      } 
      String orgName = org.getOrgName();
      String orgNameStr = "";
      String oldNameStr = beforeUpdateOrg.getOrgNameString();
      int posi = oldNameStr.lastIndexOf(".");
      if (orgName != null && !"".equals(orgName)) {
        sqlsb.append(",ORGNAME=?");
        list.add(orgName);
        if (posi > 0) {
          orgNameStr = String.valueOf(oldNameStr.substring(0, posi)) + "." + orgName;
        } else {
          orgNameStr = orgName;
        } 
        nameTag = true;
      } 
      boolean orderTag = false;
      int orgOrderCode = 0;
      if (orderOrgSerial != null && !"".equals(orderOrgSerial)) {
        orgOrderCode = getOrderCode(orderOrgSerial, tag);
        if (orgOrderCode == 0)
          return "4"; 
        orderTag = true;
      } 
      String parentOrgSerial = org.getOrgParentOrgSerial();
      if (parentOrgSerial != null) {
        long parentOrgId = 0L;
        int orgLevel = 0;
        String orgIdStr = "";
        if ("".equals(parentOrgSerial)) {
          orgIdStr = "_500000$-1";
        } else {
          OrganizationPojo parentOrg = findOrgByOrgSerial(parentOrgSerial);
          if (parentOrg == null)
            return "3"; 
          parentOrgId = parentOrg.getOrgId();
          orgLevel = parentOrg.getOrgLevel() + 1;
          orgIdStr = parentOrg.getOrgIdString();
          if (orgName != null && !"".equals(orgName)) {
            orgNameStr = String.valueOf(parentOrg.getOrgNameString()) + "." + orgName;
          } else if (posi > 0) {
            orgNameStr = String.valueOf(parentOrg.getOrgNameString()) + oldNameStr.substring(posi);
          } else {
            orgNameStr = String.valueOf(parentOrg.getOrgNameString()) + "." + beforeUpdateOrg.getOrgName();
          } 
        } 
        if (orderOrgSerial != null && !"".equals(orderOrgSerial)) {
          orgOrderCode = getOrderCode(orderOrgSerial, tag);
          if (orgOrderCode == 0)
            return "4"; 
          sqlsb.append(",ORGORDERCODE=?");
          list.add(Integer.valueOf(orgOrderCode));
        } 
        orgIdStr = String.valueOf(orgIdStr) + "_" + orgOrderCode + "$" + beforeUpdateOrg.getOrgId() + "$";
        sqlsb.append(",ORGPARENTORGID=?");
        list.add(Long.valueOf(parentOrgId));
        sqlsb.append(",ORGLEVEL=?");
        list.add(Integer.valueOf(orgLevel));
        sqlsb.append(",ORGNAMESTRING=?");
        list.add(orgNameStr);
        sqlsb.append(",ORGIDSTRING=?");
        list.add(orgIdStr);
        stmt.execute("update org_organization set ORGHASJUNIOR=1 where ORGSTATUS in(0,1) and ORG_ID=" + parentOrgId);
        parentTag = true;
      } 
      if (nameTag && !parentTag) {
        sqlsb.append(",ORGNAMESTRING=?");
        list.add(orgNameStr);
      } 
      if (orderTag && !parentTag) {
        sqlsb.append(",ORGORDERCODE=?");
        list.add(Integer.valueOf(orgOrderCode));
        String str = beforeUpdateOrg.getOrgIdString();
        str = str.replace("_" + beforeUpdateOrg.getOrgOrderCode() + "$" + beforeUpdateOrg.getOrgId() + "$", "_" + orgOrderCode + "$" + beforeUpdateOrg.getOrgId() + "$");
        sqlsb.append(",ORGIDSTRING=?");
        list.add(str);
      } 
      sqlsb.append(" where ORGSERIAL=?");
      list.add(orgSerial);
      pstmt = conn.prepareStatement(sqlsb.toString());
      if (!list.isEmpty())
        for (int i = 0; i < list.size(); i++)
          pstmt.setObject(i + 1, list.get(i));  
      pstmt.execute();
      conn.commit();
      if (!"".equals(orgListIds)) {
        OrganizationPojo afterUpdateOrg = findOrgByOrgSerial(orgSerial);
        if (nameTag && !parentTag)
          stmt.execute("update org_organization set ORGNAMESTRING=replace(ORGNAMESTRING,'" + 
              beforeUpdateOrg.getOrgNameString() + "." + "','" + afterUpdateOrg.getOrgNameString() + 
              "." + "')where ORG_ID in(" + orgListIds + ")"); 
        if (parentTag) {
          levelTag = afterUpdateOrg.getOrgLevel() - beforeUpdateOrg.getOrgLevel();
          String tempStr = null;
          if (levelTag >= 0) {
            tempStr = "+" + levelTag;
          } else {
            tempStr = String.valueOf(levelTag);
          } 
          stmt.execute("update org_organization set ORGLEVEL=ORGLEVEL" + tempStr + ", " + 
              "ORGIDSTRING=replace(ORGIDSTRING,'" + beforeUpdateOrg.getOrgIdString() + "','" + 
              afterUpdateOrg.getOrgIdString() + "')," + "ORGNAMESTRING=replace(ORGNAMESTRING,'" + 
              beforeUpdateOrg.getOrgNameString() + "." + "','" + afterUpdateOrg.getOrgNameString() + 
              "." + "')" + " where ORG_ID in(" + orgListIds + ")");
        } 
      } 
      conn.commit();
      reValue = "0";
    } catch (SQLException e) {
      e.printStackTrace();
      try {
        conn.rollback();
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
    } finally {
      this.idb.close(null, pstmt, null, conn);
    } 
    return reValue;
  }
  
  public int getOrderCode(int orgLevel, long pOrgId) {
    int orderCode = 500000;
    Connection conn = this.idb.getConnection();
    Statement stmt = null;
    ResultSet rs = null;
    try {
      stmt = conn.createStatement();
      String sql = "select org1.ORGORDERCODE from org_organization org1 where org1.ORGSTATUS in(0,1) and org1.ORGLEVEL=" + orgLevel + " and org1.ORGPARENTORGID=" + pOrgId + " order by org1.ORGORDERCODE desc ";
      rs = stmt.executeQuery(sql);
      if (rs.next())
        orderCode = rs.getInt(1); 
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      this.idb.close(rs, null, stmt, conn);
    } 
    return orderCode + 5000;
  }
  
  public int getOrderCode(String orderOrgSerial, String tag) {
    int orderCode = 0;
    int pCode = 0;
    int nearCode = 0;
    long orgParentOrgId = 0L;
    long orgId = 0L;
    Connection conn = this.idb.getConnection();
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      String sql1 = "select org1.ORGORDERCODE,org1.ORG_ID,org1.ORGPARENTORGID from org_organization org1 where org1.ORGSTATUS in(0,1) and org1.ORGSERIAL=?";
      pstmt = conn.prepareStatement(sql1);
      pstmt.setString(1, orderOrgSerial);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        pCode = rs.getInt(1);
        orgId = rs.getLong(2);
        orgParentOrgId = rs.getLong(3);
      } else {
        return 0;
      } 
      rs.close();
      String sql = "select org1.ORGORDERCODE from org_organization org1 where org1.ORGSTATUS in(0,1) and org1.ORGPARENTORGID=" + 
        orgParentOrgId + " and org1.ORG_ID<>" + orgId + " and org1.ORGORDERCODE between ";
      if ("+".equals(tag)) {
        sql = String.valueOf(sql) + pCode + " and " + (pCode + 5000) + " order by org1.ORGORDERCODE ";
      } else if ("-".equals(tag)) {
        sql = String.valueOf(sql) + (pCode - 5000) + " and " + pCode + " order by org1.ORGORDERCODE desc ";
      } else {
        return 0;
      } 
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      if (rs.next())
        nearCode = rs.getInt(1); 
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      this.idb.close(rs, pstmt, stmt, conn);
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
  
  public String addOrg(OrganizationPojo org, String orderOrgSerial, String tag) {
    String reValue = "-1";
    Connection conn = this.idb.getConnection();
    PreparedStatement pstmt = null;
    Statement stmt = null;
    ResultSet rs = null;
    String orgSerial = org.getOrgSerial();
    if (orgSerial == null && "".equals(orgSerial))
      return "1"; 
    if (findOrgByOrgSerial(orgSerial) != null)
      return "2"; 
    String orgName = org.getOrgName();
    String orgNameString = orgName;
    String orgIdString = "";
    int orgLevel = 0;
    String pOrgSerial = org.getOrgParentOrgSerial();
    long parentOrgId = 0L;
    if (pOrgSerial != null && !"".equals(pOrgSerial)) {
      OrganizationPojo parentOrg = findOrgByOrgSerial(pOrgSerial);
      if (parentOrg == null)
        return "3"; 
      parentOrgId = parentOrg.getOrgId();
      orgIdString = parentOrg.getOrgIdString();
      orgLevel = parentOrg.getOrgLevel() + 1;
      orgNameString = String.valueOf(parentOrg.getOrgNameString()) + "." + orgNameString;
    } else {
      orgIdString = "_500000$-1$";
    } 
    int orgOrderCode = 0;
    String sql = null;
    try {
      if (orderOrgSerial != null && !"".equals(orderOrgSerial)) {
        orgOrderCode = getOrderCode(orderOrgSerial, tag);
        if (orgOrderCode == 0)
          return "4"; 
      } else {
        orgOrderCode = getOrderCode(orgLevel, parentOrgId);
      } 
      orgIdString = String.valueOf(orgIdString) + "_" + orgOrderCode + "$";
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      long org_id = 0L;
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        System.out.println("enter getdb113............................");
        sql = "insert into org_organization(ORGNAME,ORGSIMPLENAME,ORGPARENTORGID,ORGORDERCODE,ORGDESCRIPTE,ORGLEVEL,ORGHASJUNIOR,ORGSTATUS,ORGNAMESTRING,ORGSERIAL,ORGIDSTRING,ORGHASCHANNEL,DOMAIN_ID) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
      } else if (databaseType.indexOf("oracle") >= 0) {
        sql = "insert into org_organization(ORGNAME,ORGSIMPLENAME,ORGPARENTORGID,ORGORDERCODE,ORGDESCRIPTE,ORGLEVEL,ORGHASJUNIOR,ORGSTATUS,ORGNAMESTRING,ORGSERIAL,ORGIDSTRING,ORGHASCHANNEL,DOMAIN_ID,ORG_ID) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        rs = stmt.executeQuery("select HIBERNATE_SEQUENCE.nextval from dual");
        if (rs.next())
          org_id = rs.getLong(1); 
        rs.close();
        rs = null;
      } 
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, orgName);
      pstmt.setString(2, (org.getOrgSimpleName() == null) ? "" : org.getOrgSimpleName());
      pstmt.setLong(3, parentOrgId);
      pstmt.setInt(4, orgOrderCode);
      pstmt.setString(5, (org.getOrgDescripte() == null) ? "" : org.getOrgDescripte());
      pstmt.setInt(6, orgLevel);
      pstmt.setInt(7, 0);
      pstmt.setInt(8, 0);
      pstmt.setString(9, orgNameString);
      pstmt.setString(10, orgSerial);
      pstmt.setString(11, String.valueOf(orgIdString) + org_id + "$");
      pstmt.setInt(12, 0);
      pstmt.setInt(13, 0);
      if (org_id > 0L)
        pstmt.setLong(14, org_id); 
      pstmt.execute();
      conn.commit();
      stmt.execute("update org_organization set ORGHASJUNIOR=1 where ORG_ID=" + parentOrgId);
      if (org_id <= 0L) {
        rs = stmt.executeQuery("select max(ORG_ID) from org_organization where ORGSTATUS in(0,1)");
        long tempId = 0L;
        if (rs.next())
          tempId = rs.getLong(1); 
        System.out.println("sql:update org_organization org1 set org1.ORGIDSTRING=replace(org1.ORGIDSTRING,'$0$','$" + tempId + "$') where org2.ORGSTATUS in(0,1)");
        stmt.execute("update org_organization org1 set org1.ORGIDSTRING=replace(org1.ORGIDSTRING,'$0$','$" + tempId + "$') where org1.ORGSTATUS in(0,1)");
      } 
      conn.commit();
      reValue = "0";
    } catch (SQLException e) {
      e.printStackTrace();
      try {
        conn.rollback();
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.idb.close(null, pstmt, null, conn);
    } 
    return reValue;
  }
}
