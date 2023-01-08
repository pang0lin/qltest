package com.js.oa.message.bean;

import com.js.oa.message.po.MsManageInfoPO;
import com.js.oa.message.po.MsManagePO;
import com.js.system.manager.service.ManagerService;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.StringSplit;
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

public class MsManageEJBBean extends HibernateBase implements SessionBean {
  private static final long serialVersionUID = 1L;
  
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public List getMsManageList(String userId, String rightCode) throws Exception {
    begin();
    List<Object[]> list = new ArrayList();
    try {
      Query query1 = this.session.createQuery("select ms.msId,ms.msTitle,ms.msRemark,ms.domainId  from com.js.oa.message.po.MsManagePO ms ");
      list = query1.list();
      Object[] obj = (Object[])null;
      if (list != null)
        for (int i = 0; i < list.size(); i++)
          obj = list.get(i);  
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public String getUserNameByMsId(String msId) throws Exception {
    begin();
    String users = "";
    try {
      Query query1 = this.session.createQuery("select msinfo.grantName,msinfo.msInfoId from com.js.oa.message.po.MsManageInfoPO msinfo where msinfo.msManage.msId=" + 
          msId);
      List<Object[]> list = query1.list();
      if (list != null)
        for (int i = 0; i < list.size(); i++) {
          Object[] obj = list.get(i);
          if (i == 0) {
            users = (String)obj[0];
          } else {
            users = String.valueOf(users) + "," + obj[0];
          } 
        }  
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return users;
  }
  
  public MsManagePO loadMs(String msId) throws Exception {
    MsManagePO po = new MsManagePO();
    begin();
    try {
      po = (MsManagePO)this.session.load(MsManagePO.class, new Long(msId));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return po;
  }
  
  public List getMsManageInfoByMsId(String msId) throws Exception {
    begin();
    List list = new ArrayList();
    try {
      Query query1 = this.session.createQuery("select msinfo.msInfoId,msinfo.msManage.msId,msinfo.grantId,msinfo.grantName,msinfo.grantType from com.js.oa.message.po.MsManageInfoPO msinfo where msinfo.msManage.msId=" + 

          
          msId + " order by msinfo.grantType DESC,msinfo.msInfoId");
      list = query1.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public Boolean updateMsMangeGrant(MsManagePO po) throws Exception {
    begin();
    Boolean falg = new Boolean(false);
    try {
      ManagerService mangeService = new ManagerService();
      this.session.delete("from com.js.oa.message.po.MsManageInfoPO po where po.msManage.msId=" + po.getMsId());
      this.session.flush();
      if (po.getUserRange() != null && !"".equals(po.getUserRange())) {
        String useEmps = StringSplit.splitWith(po.getUserRange(), "$", "*@");
        String useOrgs = StringSplit.splitWith(po.getUserRange(), "*", "@$");
        String useGroups = StringSplit.splitWith(po.getUserRange(), "@", "$*");
        if (useEmps != null && !"".equals(useEmps) && useEmps.length() > 2) {
          useEmps = useEmps.substring(1, useEmps.length() - 1);
          String[] useEmpsArr = useEmps.split("\\$\\$");
          for (int i = 0; i < useEmpsArr.length; i++) {
            MsManageInfoPO infoPo = new MsManageInfoPO();
            infoPo.setMsManage(po);
            infoPo.setGrantId(useEmpsArr[i]);
            infoPo.setGrantName(mangeService.getEmpNameByEmpId(useEmpsArr[i]));
            infoPo.setGrantType("1");
            this.session.save(infoPo);
          } 
        } 
        if (useOrgs != null && !"".equals(useOrgs) && useOrgs.length() > 2) {
          useOrgs = useOrgs.substring(1, useOrgs.length() - 1);
          String[] useOrgsArr = useOrgs.split("\\*\\*");
          for (int i = 0; i < useOrgsArr.length; i++) {
            MsManageInfoPO infoPo = new MsManageInfoPO();
            infoPo.setMsManage(po);
            infoPo.setGrantId(useOrgsArr[i]);
            infoPo.setGrantName(mangeService.getOrgNameByOrgId(useOrgsArr[i]));
            infoPo.setGrantType("2");
            this.session.save(infoPo);
          } 
        } 
        if (useGroups != null && !"".equals(useGroups) && useGroups.length() > 2) {
          useGroups = useGroups.substring(1, useGroups.length() - 1);
          String[] useGroupsArr = useGroups.split("\\@\\@");
          for (int i = 0; i < useGroupsArr.length; i++) {
            MsManageInfoPO infoPo = new MsManageInfoPO();
            infoPo.setMsManage(po);
            infoPo.setGrantId(useGroupsArr[i]);
            infoPo.setGrantName(mangeService.getGroupNameByGroupId(useGroupsArr[i]));
            infoPo.setGrantType("3");
            this.session.save(infoPo);
          } 
        } 
        this.session.flush();
      } 
      falg = Boolean.TRUE;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return falg;
  }
  
  public List getListByYourSQL(String sql) throws Exception {
    begin();
    List list = new ArrayList();
    try {
      Query query1 = this.session.createQuery(sql);
      list = query1.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public String getListOwner(String tableNameString) throws Exception {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String userString = "";
    List list = new ArrayList();
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      String sqlString = "select distinct " + tableNameString + "_owner," + tableNameString + "_owner from " + tableNameString;
      rs = stmt.executeQuery(sqlString);
      System.out.println(rs);
      while (rs.next())
        userString = String.valueOf(userString) + rs.getString(1) + ","; 
      rs.close();
      stmt.close();
      conn.close();
    } catch (SQLException e1) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e1.printStackTrace();
    } 
    return userString;
  }
  
  public List getListType(String tableNameString) throws Exception {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    List<Object[]> list = new ArrayList();
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      String sqlString = "select field_id,field_code,field_name,field_desname from tfield where field_show in(201,210,211) and field_table=" + tableNameString;
      rs = stmt.executeQuery(sqlString);
      while (rs.next()) {
        Object[] obj = new Object[4];
        obj[0] = rs.getString(1);
        obj[1] = rs.getString(2);
        obj[2] = rs.getString(3);
        obj[3] = rs.getString(4);
        list.add(obj);
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (SQLException e1) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e1.printStackTrace();
    } 
    return list;
  }
  
  public String getListUsers(String tableNameString, String tableCode) throws Exception {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String userString = "";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      String tabCoun = "";
      String sqlString = "select " + tableNameString + " from " + tableCode;
      rs = stmt.executeQuery(sqlString);
      while (rs.next()) {
        if (tableNameString.contains(",")) {
          String[] tableNameStrings = tableNameString.split(",");
          for (int z = 0; z < tableNameStrings.length; z++) {
            String str = rs.getString(tableNameStrings[z]);
            if (str != null && (str.split(";")).length > 1)
              userString = String.valueOf(userString) + str.split(";")[1] + ","; 
          } 
          userString = userString.replace("$$", ",").replace("$", "");
          continue;
        } 
        String user = rs.getString(1);
        if (user != null && (user.split(";")).length > 1)
          userString = String.valueOf(userString) + user.split(";")[1] + ","; 
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (SQLException e1) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e1.printStackTrace();
    } 
    return userString;
  }
  
  public String getListTtable(String tableNameString) throws Exception {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String tabString = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      String sqlString = "select table_name from ttable where  table_id=" + tableNameString;
      rs = stmt.executeQuery(sqlString);
      while (rs.next())
        tabString = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (SQLException e1) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e1.printStackTrace();
    } 
    return tabString;
  }
  
  public List getWorkActivity(String sql) throws Exception {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    List<Object[]> list = new ArrayList();
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        Object[] obj = new Object[3];
        obj[0] = rs.getString(1);
        obj[1] = rs.getString(2);
        obj[2] = rs.getString(3);
        list.add(obj);
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (SQLException e1) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e1.printStackTrace();
    } 
    return list;
  }
}
