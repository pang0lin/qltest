package com.js.oa.hr.finance.bean;

import com.js.oa.userdb.util.DbOpt;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;

public class FTableEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public String[][] getSimpleTableInfo(String tableName) throws Exception {
    String[][] list = (String[][])null;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      String sql = "select table_id,table_name,table_desname,table_page from f_table where table_name='" + 
        tableName + "'";
      list = dbopt.executeQueryToStrArr2(sql, 4);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return list;
  }
  
  public String[][] getAllFieldInfo(String tableName) throws Exception {
    String[][] list = (String[][])null;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      String sql = "select field_id,table_name,field_name,field_desname,field_type,field_Len,field_Default,field_list_show,field_Is_Total,field_Is_Null,field_Is_enable,created_emp,created_org,field_order  from f_field where table_name='" + 

        
        tableName + "' and field_Is_Sys!=1 order by field_id asc";
      list = dbopt.executeQueryToStrArr2(sql, 14);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      try {
        dbopt.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      } 
    } 
    return list;
  }
  
  public boolean updateByYourYuanShengSql(String sql) throws Exception {
    boolean result = true;
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      boolean status_db = conn.getAutoCommit();
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      stmt.executeUpdate(sql);
      stmt.close();
      conn.commit();
      conn.setAutoCommit(status_db);
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
      result = false;
    } 
    return result;
  }
  
  public boolean deleteOATaskCollect(String ids) throws Exception {
    begin();
    boolean re = true;
    try {
      if (ids.endsWith(","))
        ids = ids.substring(0, ids.length() - 1); 
      this.session.delete("from com.js.oa.oacollect.po.OaCollect po where po.collectId in (" + ids + ")");
      this.session.delete("from com.js.oa.oacollect.po.OaCollectEmp po where po.collectId in (" + ids + ")");
      this.session.flush();
      String dateString = "now()";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("oracle") >= 0)
        dateString = "sysdate"; 
      String sql = "update JSF_WORK set WORKDONEWITHDATE=" + dateString + ",WORKSTATUS=101 where WORKRECORD_ID  in(" + ids + ")" + 
        " and WORKFILETYPE='数据采集' and INITACTIVITY  in(" + ids + ")";
      updateBySql(sql);
    } catch (Exception e) {
      e.printStackTrace();
      re = false;
    } finally {
      this.session.close();
    } 
    return re;
  }
  
  public String updateBySql(String sql) throws Exception {
    StringBuffer res = new StringBuffer(",");
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.executeUpdate(sql);
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return res.toString();
  }
  
  public String setFieldName(String fieldId, String fieldName) {
    String sql = "SELECT f.field_id,f.field_Len,f.field_type,f.field_name,f.table_name FROM f_field  f WHERE f.field_id=" + 
      fieldId;
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String up = "alter table ";
    String tableName = "";
    String returnString = "&nbsp;";
    try {
      base.begin();
      rs = base.executeQuery(sql);
      if (rs.next()) {
        returnString = rs.getString(4);
        tableName = rs.getString(5);
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0)
          if ("varchar".equals(rs.getString(3))) {
            up = String.valueOf(up) + tableName + " change " + returnString + " " + fieldName + " " + rs.getString(3) + "(" + rs.getString(2) + ")";
          } else {
            up = String.valueOf(up) + tableName + " change " + returnString + " " + fieldName + " " + rs.getString(3);
          }  
        if (databaseType.indexOf("oracle") >= 0)
          up = "alter table " + tableName + " rename column " + returnString + " to " + fieldName; 
      } 
      base.executeUpdate(up);
      String update = "update f_field set field_name='" + fieldName + "' where field_id=" + fieldId;
      base.executeUpdate(update);
      returnString = fieldName;
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return returnString;
  }
  
  public String getShouldDelId(String tableName, String[] fieldId) throws Exception {
    StringBuffer buffer = new StringBuffer("-1");
    StringBuffer result = new StringBuffer();
    if (fieldId != null)
      for (int i = 0; i < fieldId.length; i++)
        buffer.append(",").append(fieldId[i]);  
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      String sql = "select field_name from f_field where table_name='" + tableName + "' and field_id not in(" + buffer.toString() + ") and field_Is_Sys!=1";
      ResultSet rs = dbopt.executeQuery(sql);
      int i = 0;
      while (rs.next()) {
        if (i == 0) {
          result.append(rs.getString(1));
        } else {
          result.append(",").append(rs.getString(1));
        } 
        i++;
      } 
      rs.close();
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
      e.printStackTrace();
      throw e;
    } 
    return result.toString();
  }
  
  public void batchDeleteField(String tablename, String[] fieldname) {
    int success = 0;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      Statement stat = dbopt.getStatement();
      for (int i = 0; i < fieldname.length; i++) {
        String fieldid = dbopt.executeQueryToStr(
            "select field_id from f_field where  field_name='" + 
            fieldname[i] + "' and table_name='" + tablename + "'");
        stat.addBatch("Alter Table " + tablename + " Drop Column " + 
            fieldname[i]);
        stat.addBatch("delete from f_field where field_id=" + fieldid);
        stat.executeBatch();
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
  }
  
  public int addField(String tableName, String fieldDesName, String fieldName, String fieldType, String fieldLen, String fieldIsNull, String fieldDefault, String fieldListShow, String fieldIsTotal, String fieldIsEnable, String fieldIsSys, String createdEmp, String createdOrg, String fieldOrder) {
    int success = 1;
    DbOpt dbopt = null;
    Statement stat = null;
    try {
      dbopt = new DbOpt();
      stat = dbopt.getStatement();
      String sql1 = "alter table " + tableName + " add " + fieldName;
      if (fieldType.equals("varchar") && 
        DbOpt.dbtype.indexOf("oracle") >= 0)
        fieldType = "varchar2"; 
      if (fieldType.equals("double") && 
        DbOpt.dbtype.indexOf("oracle") >= 0)
        fieldType = "float"; 
      sql1 = String.valueOf(sql1) + " " + fieldType;
      if (fieldType != null && fieldType.toLowerCase().indexOf("varchar") >= 0) {
        sql1 = String.valueOf(sql1) + "(" + fieldLen + ")";
      } else if ("".equals(fieldLen)) {
        fieldLen = "0";
      } 
      String sql2 = "INSERT INTO f_field ( table_name, field_name, field_desname, field_type, field_Len, field_Default, field_list_show, field_Is_Total, field_Is_Null, field_Is_enable,field_Is_Sys, created_emp, created_org,field_order)  VALUES('" + 


        
        tableName + "','" + fieldName + "','" + fieldDesName + "','" + fieldType + "'," + fieldLen + ",'" + 
        fieldDefault + "'," + fieldListShow + "," + fieldIsTotal + "," + fieldIsNull + "," + fieldIsEnable + "," + 
        fieldIsSys + "," + createdEmp + "," + createdOrg + "," + fieldOrder + ")";
      String databaseType = SystemCommon.getDatabaseType();
      if ("oracle".equals(databaseType))
        sql2 = "INSERT INTO f_field (field_id, table_name, field_name, field_desname, field_type, field_Len, field_Default, field_list_show, field_Is_Total, field_Is_Null, field_Is_enable,field_Is_Sys, created_emp, created_org,field_order)  VALUES(hibernate_sequence.nextval,'" + 


          
          tableName + "','" + fieldName + "','" + fieldDesName + "','" + fieldType + "'," + fieldLen + ",'" + 
          fieldDefault + "'," + fieldListShow + "," + fieldIsTotal + "," + fieldIsNull + "," + fieldIsEnable + "," + 
          fieldIsSys + "," + createdEmp + "," + createdOrg + "," + fieldOrder + ")"; 
      stat.addBatch(sql1);
      stat.addBatch(sql2);
      stat.executeBatch();
      dbopt.close();
    } catch (Exception e) {
      success = 0;
      try {
        dbopt.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return success;
  }
  
  public int updateSimpleField(String fieldId, String tableName, String fieldDesName, String fieldName, String fieldType, String fieldLen, String fieldIsNull, String fieldDefault, String fieldListShow, String fieldIsTotal, String fieldIsEnable, String fieldIsSys, String createdEmp, String createdOrg, String fieldOrder) {
    int success = 1;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      if (fieldLen == null || "".equals(fieldLen))
        fieldLen = "0"; 
      String pSql = "update f_field set field_name=?,field_desname=?,field_type=?,field_Len=?,field_Order=?,field_Default=?,field_list_show=? ,field_Is_Total=?,field_Is_Null=?,field_Is_enable=?,field_Is_Sys=? where field_id=?";
      String[] pSqlPara = { 
          fieldName, fieldDesName, fieldType, fieldLen, fieldOrder, fieldDefault, fieldListShow, 
          fieldIsTotal, fieldIsNull, fieldIsEnable, 
          fieldIsSys, fieldId };
      dbopt.executePSUpdate(pSql, pSqlPara);
      if (fieldType.equals("varchar")) {
        String sql = "";
        if (DbOpt.dbtype.indexOf("oracle") >= 0) {
          sql = " alter table " + tableName + " modify " + 
            fieldName + " varchar2(" + fieldLen + ")";
        } else if (DbOpt.dbtype.indexOf("sqlserver") >= 0) {
          sql = " alter table " + tableName + " alter column " + 
            fieldName + " varchar(" + fieldLen + ")";
        } else if (DbOpt.dbtype.indexOf("mysql") >= 0) {
          sql = " alter table " + tableName + " change " + 
            fieldName + " " + fieldName + "  varchar(" + fieldLen + ")";
        } else if (DbOpt.dbtype.indexOf("db2") >= 0) {
          sql = " alter table " + tableName + " alter column " + 
            fieldName + " set data type varchar(" + fieldLen + 
            ")";
        } 
        dbopt.executeUpdate(sql);
      } 
    } catch (Exception e) {
      success = 0;
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return success;
  }
  
  public String getRightWhere(String userId, String orgId, String rightCode, String fieldOrg, String fieldEmp) throws Exception {
    String where = "";
    List<Object[]> list = getRightScope(userId, rightCode);
    if (list != null && list.size() > 0) {
      Object[] obj = list.get(0);
      String scopeType = obj[0].toString();
      if ("0".equals(scopeType)) {
        where = " 1=1 ";
      } else if ("1".equals(scopeType)) {
        where = "".equals(fieldEmp) ? "1>1" : (String.valueOf(fieldEmp) + "=" + userId);
      } else if ("2".equals(scopeType)) {
        String orgRange = getAllJuniorOrgIdByRange("*" + orgId + "*");
        if (orgRange.indexOf("a") > 0) {
          String[] tmp = orgRange.split("a");
          for (int k = 0; k < tmp.length; k++)
            where = String.valueOf(where) + "(" + fieldOrg + " in(" + tmp[k] + ")) or "; 
          if (where.endsWith("or "))
            where = where.substring(0, where.length() - 3); 
        } else {
          where = String.valueOf(fieldOrg) + " in(" + orgRange + ") ";
        } 
      } else if ("3".equals(scopeType)) {
        where = String.valueOf(fieldOrg) + "=" + orgId;
      } else {
        if (obj[1] != null && !"".equals(obj[1].toString())) {
          String orgRange = getAllJuniorOrgIdByRange((String)obj[1]);
          if ("".equals(orgRange)) {
            where = "1>2";
          } else if (orgRange.indexOf("a") > 0) {
            String[] tmp = orgRange.split("a");
            for (int k = 0; k < tmp.length; k++)
              where = String.valueOf(where) + "(" + fieldOrg + " in(" + tmp[k] + ")) or "; 
            if (where.endsWith("or "))
              where = where.substring(0, where.length() - 3); 
          } else {
            where = String.valueOf(fieldOrg) + " in(" + orgRange + ") ";
          } 
        } 
        String dbType = SystemCommon.getDatabaseType();
        if (obj[3] != null && !"".equals(obj[3].toString())) {
          if (!"".equals(where))
            where = String.valueOf(where) + " or "; 
          if ("mysql".equals(dbType)) {
            where = String.valueOf(where) + "'" + obj[3].toString() + "' like concat('%$'," + fieldEmp + ",'$%')";
          } else if ("oracle".equals(dbType)) {
            where = String.valueOf(where) + "'" + obj[3].toString() + "' like '%$'||" + fieldEmp + "||'$%'";
          } else if ("mssqlserver".equals(dbType)) {
            where = String.valueOf(where) + "'" + obj[3].toString() + "' like '%$'+" + fieldEmp + "+'$%'";
          } else if ("db2".equals(fieldEmp)) {
            where = String.valueOf(where) + "'" + obj[3].toString() + "' like '%$'+" + fieldEmp + "+'$%'";
          } 
        } 
      } 
    } else {
      where = "1>2";
    } 
    return "(" + where + ")";
  }
  
  public String getAllJuniorOrgIdByRange(String range) throws Exception {
    if (range == null || "".equals(range))
      return "-1"; 
    String result = "-1,";
    try {
      StringBuffer where = new StringBuffer(" WHERE ");
      range = "*" + range + "*";
      String[] rangeArray = range.split("\\*\\*");
      int i = 0;
      for (i = 1; i < rangeArray.length; i++) {
        if (i > 1)
          where.append(" or "); 
        where.append(" org.orgIdString like '%$");
        where.append(rangeArray[i]);
        where.append("$%' ");
      } 
      begin();
      List list = this.session.createQuery("SELECT org.orgId FROM com.js.system.vo.organizationmanager.OrganizationVO org" + where).list();
      int j = 900;
      StringBuffer tmp = new StringBuffer();
      for (i = 0; i < list.size(); i++) {
        tmp.append(list.get(i));
        if (i > j) {
          tmp.append("a");
          j += 900;
        } else {
          tmp.append(",");
        } 
      } 
      result = tmp.toString();
      if (result.length() > 0)
        result = result.substring(0, result.length() - 1); 
    } catch (Exception e) {
      System.out.println("error!" + e.getMessage());
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public List getRightScope(String userId, String rightCode) throws Exception {
    List list = null;
    begin();
    try {
      String domainId = getUserDomainId(userId);
      Query query = this.session
        .createQuery("select aaa.rightScopeType,aaa.rightScopeScope,aaa.rightScope,aaa.rightScopeUser,aaa.rightScopeGroup from com.js.system.vo.rolemanager.RightScopeVO aaa join aaa.employee bbb join aaa.right ccc where bbb.empId = " + 
          userId + " and ccc.rightCode = '" + rightCode + "' and ccc.domainId=" + domainId);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  private String getUserDomainId(String userId) {
    return "0";
  }
  
  public List getListByYourSQL(String sql) throws Exception {
    begin();
    List list = new ArrayList();
    try {
      Query query1 = this.session.createQuery(sql);
      list = query1.list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return list;
  }
}
