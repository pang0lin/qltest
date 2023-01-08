package com.js.oa.esesj.bean;

import com.js.oa.userdb.util.DbOpt;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class EsesjBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbActivate() throws EJBException, RemoteException {}
  
  public void ejbPassivate() throws EJBException, RemoteException {}
  
  public void ejbRemove() throws EJBException, RemoteException {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public boolean checkBankNameRepeat(String name, String type) {
    boolean result = true;
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "";
    String tableName = "jst_bank";
    String whereScope = "jst_BankName";
    if ("3".equals(type)) {
      tableName = "jst_Government";
      whereScope = "OrganizationName";
    } else if ("4".equals(type)) {
      tableName = "BigBusiness";
      whereScope = "EnterpriseName";
    } 
    sql = "select " + tableName + "_id from " + tableName + " where jst_State='有效' and " + whereScope + "=?";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      ps = conn.prepareStatement(sql);
      ps.setString(1, name);
      rs = ps.executeQuery();
      if (rs.next())
        result = false; 
      rs.close();
      ps.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e2) {
          e2.printStackTrace();
        }  
      if (ps != null)
        try {
          ps.close();
        } catch (SQLException e3) {
          e3.printStackTrace();
        }  
    } 
    return result;
  }
  
  public int updateBankRecordToInvalid(String id, String business, String status) {
    int index = -1;
    Connection conn = null;
    PreparedStatement ps = null;
    String sql = "";
    String tableName = "";
    if ("1".equals(business)) {
      tableName = "jst_Bank";
    } else if ("2".equals(business)) {
      tableName = "jst_Collectioneva";
    } else if ("3".equals(business)) {
      tableName = "jst_Government";
    } else if ("4".equals(business)) {
      tableName = "BigBusiness";
    } 
    if ("invalid".equals(status)) {
      sql = "update " + tableName + " set jst_State = '无效' where " + tableName + "_id =?";
    } else if ("cancel".equals(status)) {
      sql = "update " + tableName + " set jst_State = '取消' where " + tableName + "_id =?";
    } else if ("overdue".equals(status)) {
      sql = "update " + tableName + " set jst_State='过期' where jst_ValidityPeriod<curdate() and " + tableName + "_id =?";
    } else if ("compete".equals(status)) {
      sql = "update " + tableName + " set jst_State = '竞争' where " + tableName + "_id =?";
    } else {
      sql = "update " + tableName + " set jst_State = '有效' where " + tableName + "_id =?";
    } 
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      ps = conn.prepareStatement(sql);
      ps.setString(1, id);
      index = ps.executeUpdate();
      ps.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e2) {
          e2.printStackTrace();
        }  
      if (ps != null)
        try {
          ps.close();
        } catch (SQLException e3) {
          e3.printStackTrace();
        }  
    } 
    return index;
  }
  
  public String[] getInfoById(String id, String type) {
    String[] arr = new String[6];
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "";
    String tableName = "";
    String selectScope = "";
    if ("5".equals(type)) {
      sql = "select AptitudeReport_owner from AptitudeReport where AptitudeReport_id =?";
    } else {
      if ("1".equals(type)) {
        tableName = "jst_bank";
        selectScope = "jst_BankName";
      } else if ("2".equals(type)) {
        tableName = "jst_Collectioneva";
        selectScope = "EntrNname";
      } else if ("3".equals(type)) {
        tableName = "jst_Government";
        selectScope = "OrganizationName";
      } else if ("4".equals(type)) {
        tableName = "BigBusiness";
        selectScope = "EnterpriseName";
      } 
      sql = "select jst_ValidityPeriod," + tableName + "_owner,jst_Collaborator," + selectScope + ",jst_state,jst_name from " + tableName + " where " + tableName + "_id =?";
    } 
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      ps = conn.prepareStatement(sql);
      ps.setString(1, id);
      rs = ps.executeQuery();
      if (rs.next()) {
        arr[0] = rs.getString(1);
        if (!"5".equals(type)) {
          arr[1] = rs.getString(2);
          arr[2] = rs.getString(3);
          arr[3] = rs.getString(4);
          arr[4] = rs.getString(5);
          arr[5] = rs.getString(6);
        } 
      } 
      rs.close();
      ps.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e2) {
          e2.printStackTrace();
        }  
      if (ps != null)
        try {
          ps.close();
        } catch (SQLException e3) {
          e3.printStackTrace();
        }  
    } 
    return arr;
  }
  
  public boolean updateCooperationr(String id, String name, String type) {
    boolean rest = false;
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "";
    String tableName = "";
    if ("1".equals(type)) {
      tableName = "jst_bank";
    } else if ("2".equals(type)) {
      tableName = "jst_Collectioneva";
    } else if ("3".equals(type)) {
      tableName = "jst_Government";
    } else if ("4".equals(type)) {
      tableName = "BigBusiness";
    } 
    sql = "update " + tableName + " set jst_Collaborator=? where " + tableName + "_id=?";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      ps = conn.prepareStatement(sql);
      ps.setString(1, name);
      ps.setString(2, id);
      int res = ps.executeUpdate();
      if (res > 0)
        rest = true; 
      ps.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e2) {
          e2.printStackTrace();
        }  
      if (ps != null)
        try {
          ps.close();
        } catch (SQLException e3) {
          e3.printStackTrace();
        }  
    } 
    return rest;
  }
  
  public String[] getCooperationProcessInfo(String id) {
    String[] arr = new String[3];
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "select Cooperation_owner,RecordOpinion,CooperationID from Cooperation where Cooperation_id=?";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      ps = conn.prepareStatement(sql);
      ps.setString(1, id);
      rs = ps.executeQuery();
      if (rs.next()) {
        arr[0] = rs.getString(1);
        arr[1] = rs.getString(2);
        arr[2] = rs.getString(3);
      } 
      rs.close();
      ps.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e2) {
          e2.printStackTrace();
        }  
      if (ps != null)
        try {
          ps.close();
        } catch (SQLException e3) {
          e3.printStackTrace();
        }  
    } 
    return arr;
  }
  
  public EmployeeVO getEmployeeVOById(String id) {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    EmployeeVO ee = new EmployeeVO();
    String sql = "select a.useraccounts,a.empname,b.ORG_ID from org_employee a,org_organization_user b where a.emp_id = b.EMP_ID and a.emp_id =?";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      ps = conn.prepareStatement(sql);
      ps.setString(1, id);
      rs = ps.executeQuery();
      if (rs.next()) {
        ee.setUserAccounts(rs.getString(1));
        ee.setEmpName(rs.getString(2));
        ee.setEmpOrgIdString(rs.getString(3));
      } 
      rs.close();
      ps.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e2) {
          e2.printStackTrace();
        }  
      if (ps != null)
        try {
          ps.close();
        } catch (SQLException e3) {
          e3.printStackTrace();
        }  
    } 
    return ee;
  }
  
  public String getProcessTableName(String tableId) {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "select area_table from tarea where page_id=?";
    String tableName = "";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      ps = conn.prepareStatement(sql);
      ps.setString(1, tableId);
      rs = ps.executeQuery();
      if (rs.next())
        tableName = rs.getString(1); 
      rs.close();
      ps.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e2) {
          e2.printStackTrace();
        }  
      if (ps != null)
        try {
          ps.close();
        } catch (SQLException e3) {
          e3.printStackTrace();
        }  
    } 
    return tableName;
  }
  
  public String[] getCompeteProcessInfo(String recorded, String tableName) {
    String[] arr = new String[3];
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "select " + tableName + "_owner,CandidateName,bs_id from " + tableName + " where " + tableName + "_id=?";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      ps = conn.prepareStatement(sql);
      ps.setString(1, recorded);
      rs = ps.executeQuery();
      if (rs.next()) {
        arr[0] = rs.getString(1);
        arr[1] = rs.getString(2);
        arr[2] = rs.getString(3);
      } 
      rs.close();
      ps.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e2) {
          e2.printStackTrace();
        }  
      if (ps != null)
        try {
          ps.close();
        } catch (SQLException e3) {
          e3.printStackTrace();
        }  
    } 
    return arr;
  }
  
  public int insertNewRecordForCompete(String id, String type, String userId, String userOrg, String name) {
    Connection conn = null;
    PreparedStatement ps = null;
    DbOpt dbopt = new DbOpt();
    String sql = "";
    int res = 0;
    if ("1".equals(type)) {
      sql = "insert into jst_bank (jst_bank_id,jst_bank_owner,jst_bank_date,jst_bank_org,jst_bank_BH,jst_name,jst_StartTime,jst_recordType,jst_bankName,jst_recordName,jst_serviceType,jst_explain,jst_bank_past,jst_bank_estimate,jst_bank_f3001,jst_collaborator,jst_ValidityPeriod,jst_state) select * from ((select SEQ_SEQ from JSDB.OA_SEQ) t,(select ?,sysdate(),?,jst_bank_BH,?,jst_StartTime,jst_recordType,jst_bankName,jst_recordName,jst_serviceType,jst_explain,jst_bank_past,jst_bank_estimate,jst_bank_f3001,jst_collaborator,jst_ValidityPeriod,'有效' from jst_bank where jst_bank_id=?) m);";
    } else if ("2".equals(type)) {
      sql = "insert into jst_Collectioneva (jst_Collectioneva_id,jst_Collectioneva_owner,jst_Collectioneva_date,jst_Collectioneva_org,jst_BH,jst_name,jst_StartTime,jst_level,jst_RecordType,EntrNname,ProjectAddress,jst_ServiceType,BusinessSources,ServiceNode,RecordDescription,jst_Collaborator,jst_ValidityPeriod,jst_State) select * from ((select SEQ_SEQ from JSDB.OA_SEQ) t,(select ?,sysdate(),?,jst_BH,?,jst_StartTime,jst_level,jst_RecordType,EntrNname,ProjectAddress,jst_ServiceType,BusinessSources,ServiceNode,RecordDescription,jst_Collaborator,jst_ValidityPeriod,'有效' from jst_Collectioneva where jst_Collectioneva_id=?) m)";
    } else if ("3".equals(type)) {
      sql = "insert into jst_Government (jst_Government_id,jst_Government_owner,jst_Government_date,jst_Government_org,jst_Government_BH,jst_name,jst_StartTime,RecordLevel,RecordType,OrganizationName,QualificationName,ServiceType,RecordDescription,PastPerformance,ExpectedPer,jst_Collaborator,jst_ValidityPeriod,jst_State) select * from ((select SEQ_SEQ from JSDB.OA_SEQ) t,(select ?,sysdate(),?,jst_Government_BH,?,jst_StartTime,RecordLevel,RecordType,OrganizationName,QualificationName,ServiceType,RecordDescription,PastPerformance,ExpectedPer,jst_Collaborator,jst_ValidityPeriod,'有效' from jst_Government where jst_Government_id=?) m)";
    } else if ("4".equals(type)) {
      sql = "insert into BigBusiness (BigBusiness_id,BigBusiness_owner,BigBusiness_date,BigBusiness_org,BigNumber,jst_name,jst_StartTime,biglevel,jst_RecordType,EnterpriseName,bigEntryName,bigProjectAddress,jst_ServiceType,BusinessProof,PastPerformance,bigExpectedPer,jst_Collaborator,jst_ValidityPeriod,jst_State) select * from ((select SEQ_SEQ from JSDB.OA_SEQ) t,(select ?,sysdate(),?,BigNumber,?,jst_StartTime,biglevel,jst_RecordType,EnterpriseName,bigEntryName,bigProjectAddress,jst_ServiceType,BusinessProof,PastPerformance,bigExpectedPer,jst_Collaborator,jst_ValidityPeriod,'有效' from BigBusiness where BigBusiness_id=?) m)";
    } 
    try {
      dbopt.executeUpdate("update JSDB.OA_SEQ set SEQ_SEQ = SEQ_SEQ+1");
      conn = (new DataSourceBase()).getDataSource().getConnection();
      ps = conn.prepareStatement(sql);
      ps.setString(1, userId);
      ps.setString(2, userOrg);
      ps.setString(3, name);
      ps.setString(4, id);
      res = ps.executeUpdate();
      System.out.println("res" + res);
      ps.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      res = -1;
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e2) {
          e2.printStackTrace();
        }  
      if (ps != null)
        try {
          ps.close();
        } catch (SQLException e3) {
          e3.printStackTrace();
        }  
    } 
    return res;
  }
  
  public boolean getCurUserHasRole(String userId) {
    boolean res = false;
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "select * from org_role where roleuserid like '%" + userId + "%' and role_id = '564'";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      ps = conn.prepareStatement(sql);
      rs = ps.executeQuery();
      if (rs.next())
        res = true; 
      rs.close();
      ps.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e2) {
          e2.printStackTrace();
        }  
      if (ps != null)
        try {
          ps.close();
        } catch (SQLException e3) {
          e3.printStackTrace();
        }  
    } 
    return res;
  }
}
