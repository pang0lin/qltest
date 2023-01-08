package com.js.oa.datasync.bean;

import com.js.oa.datasync.po.DataSyncFieldPO;
import com.js.oa.datasync.po.DataSyncPO;
import com.js.oa.datasync.po.DataSyncSetPO;
import com.js.oa.jsflow.po.WFWorkFlowProcessPO;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;

public class DataSyncEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbActivate() throws EJBException, RemoteException {}
  
  public void ejbPassivate() throws EJBException, RemoteException {}
  
  public void ejbRemove() throws EJBException, RemoteException {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public void saveDataSync(DataSyncPO dataSyncPO) throws HibernateException {
    try {
      begin();
      this.session.save(dataSyncPO);
      this.session.flush();
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
  }
  
  public void updateDataSync(DataSyncPO dataSyncPO) throws HibernateException {
    begin();
    try {
      this.session.update(dataSyncPO);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
  }
  
  public DataSyncPO getDataSyncPOById(Long id) throws HibernateException {
    begin();
    DataSyncPO dataSyncPO = null;
    try {
      dataSyncPO = (DataSyncPO)this.session.load(DataSyncPO.class, id);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
    return dataSyncPO;
  }
  
  public List getAllDataSync() throws HibernateException {
    List list = new ArrayList();
    try {
      begin();
      String hql = "select aaa.id,aaa.syncTitle,aaa.memo,aaa.processId,aaa.activityId from com.js.oa.datasync.po.DataSyncPO aaa";
      Query query = this.session.createQuery(hql);
      list = query.list();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public boolean deleleDataSync(String ids) throws HibernateException {
    begin();
    boolean bl = false;
    try {
      if (ids != null && !ids.equals("")) {
        this.session.delete("from com.js.oa.datasync.po.DataSyncFieldPO fpo where fpo.syncSetId in(select spo.id from com.js.oa.datasync.po.DataSyncSetPO spo where spo.syncId in(" + 
            
            ids.substring(0, ids.length() - 1) + "))");
        this.session.delete("from com.js.oa.datasync.po.DataSyncSetPO po where po.syncId in(" + 
            ids.substring(0, ids.length() - 1) + ")");
        this.session.delete(
            " from com.js.oa.datasync.po.DataSyncPO po where po.id in (" + 
            ids.substring(0, ids.length() - 1) + ")");
      } 
      this.session.flush();
      bl = true;
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return bl;
  }
  
  public Long saveDataSyncSetPO(DataSyncSetPO dataSyncSetPO) throws HibernateException {
    Long id = Long.valueOf(0L);
    try {
      begin();
      id = (Long)this.session.save(dataSyncSetPO);
      this.session.flush();
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
    return id;
  }
  
  public List getAllDataSyncSetPO(Long syncId) throws HibernateException {
    List list = new ArrayList();
    try {
      begin();
      String hql = "select aaa.id,aaa.syncId,aaa.setTitle,aaa.description,aaa.setType,aaa.dataSourceType,aaa.dataSourceName,aaa.operateTable,aaa.operateType,aaa.exeCondition,aaa.executeOrder from com.js.oa.datasync.po.DataSyncSetPO aaa";
      String where = " where aaa.syncId = " + syncId;
      Query query = this.session.createQuery(String.valueOf(hql) + where);
      list = query.list();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public DataSyncSetPO getDataSyncSetPOById(Long id) throws HibernateException {
    begin();
    DataSyncSetPO dataSyncSetPO = null;
    try {
      dataSyncSetPO = (DataSyncSetPO)this.session.load(DataSyncSetPO.class, id);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
    return dataSyncSetPO;
  }
  
  public void updateDataSyncSetPO(DataSyncSetPO dataSyncSetPO) throws HibernateException {
    begin();
    try {
      this.session.update(dataSyncSetPO);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
  }
  
  public boolean deleleDataSyncSetPO(String ids) throws HibernateException {
    begin();
    boolean bl = false;
    try {
      if (ids != null && !ids.equals("")) {
        this.session.delete(
            " from com.js.oa.datasync.po.DataSyncFieldPO po where po.syncSetId in (" + 
            ids.substring(0, ids.length() - 1) + ")");
        this.session.delete(
            " from com.js.oa.datasync.po.DataSyncSetPO po where po.id in (" + 
            ids.substring(0, ids.length() - 1) + ")");
      } 
      this.session.flush();
      bl = true;
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return bl;
  }
  
  public void saveDataSyncFieldPO(DataSyncFieldPO dataSyncFieldPO) throws HibernateException {
    try {
      begin();
      this.session.save(dataSyncFieldPO);
      this.session.flush();
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
  }
  
  public List getAllProcess() throws HibernateException {
    List list = new ArrayList();
    try {
      begin();
      Query query = this.session.createQuery("select aaa.wfWorkFlowProcessId,aaa.workFlowProcessName from com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa join aaa.wfPackage bbb where bbb.moduleId=1 order by  bbb.orderCode asc , aaa.wfWorkFlowProcessId desc");
      list = query.list();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public WFWorkFlowProcessPO getWFWorkFlowProcessPOById(Long id) throws HibernateException {
    begin();
    WFWorkFlowProcessPO wFWorkFlowProcessPO = null;
    try {
      wFWorkFlowProcessPO = (WFWorkFlowProcessPO)this.session.load(
          WFWorkFlowProcessPO.class, id);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
    return wFWorkFlowProcessPO;
  }
  
  public List getTareaByAccessDatabaseId(Long id) throws HibernateException {
    List list = new ArrayList();
    try {
      begin();
      Query query = this.session.createQuery("select aaa.areaTable from com.js.oa.eform.po.TAreaPO aaa where aaa.tpage.id = " + id);
      list = query.list();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List getTTablePOByTableName(String name) throws HibernateException {
    List list = new ArrayList();
    try {
      begin();
      Query query = this.session.createQuery("select aaa.tableId from com.js.oa.jsflow.po.TTablePO aaa where aaa.tableName = '" + name + "'");
      list = query.list();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List getTfieldById(Long[] id) throws HibernateException {
    List list = new ArrayList();
    String ids = "";
    for (int i = 0; i < id.length; i++)
      ids = String.valueOf(ids) + String.valueOf(id[i]) + ","; 
    try {
      begin();
      Query query = this.session.createQuery("select aaa.fieldId,aaa.fieldName,aaa.fieldDesName from com.js.oa.jsflow.po.TFieldPO aaa where aaa.table.tableId in (" + ids.substring(0, ids.length() - 1) + ")");
      list = query.list();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List<String[]> getTableList(Long processId) {
    List<String[]> list = (List)new ArrayList<String>();
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("select area_name,area_table,table_desname from tarea,ttable where area_table=table_name and  exists (select accessdatabaseid from jsf_workflowprocess where wf_workflowprocess_id=? and page_id=accessdatabaseid) order by areatype_id ");
      pstmt.setLong(1, processId.longValue());
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        list.add(new String[] { rs.getString(1), rs.getString(2), rs.getString(3) });
      } 
      rs.close();
      pstmt.close();
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
    return list;
  }
  
  public List<String[]> getTableFieldList(String tableName) {
    List<String[]> list = (List)new ArrayList<String>();
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("select field_id,field_desname,field_name,field_type,field_show from tfield where exists (select table_id from ttable where table_id=field_table and table_name=?) order by field_id");
      pstmt.setString(1, tableName);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        list.add(new String[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5) });
      } 
      rs.close();
      pstmt.close();
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
    return list;
  }
  
  public List<String[]> getSyncSetFieldList(String syncSetId) {
    List<String[]> list = (List)new ArrayList<String>();
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("select id,tofield,totype,fromfield,fromtype,fromvalueset,fieldismainform from data_sync_field where syncset_id=? order by id");
      pstmt.setString(1, syncSetId);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        list.add(new String[] { rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7) });
      } 
      rs.close();
      pstmt.close();
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
    return list;
  }
  
  public String deleteDataSyncSetFields(String syncSetId) {
    String res = "";
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("delete from data_sync_field where syncset_id=?");
      pstmt.setString(1, syncSetId);
      pstmt.executeUpdate();
      pstmt.close();
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
    return res;
  }
  
  public List<String[]> getProcessActivity(String processId) {
    List<String[]> list = (List)new ArrayList<String>();
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("SELECT wf_activity_id,activityname,activitybeginend FROM jsf_activity WHERE wf_workflowprocess_id=? AND activitybeginend<>1 ORDER BY WF_ACTIVITY_ID");
      pstmt.setString(1, processId);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        String[] tmp = { rs.getString(1), rs.getString(2), rs.getString(3) };
        list.add(tmp);
      } 
      rs.close();
      pstmt.close();
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
    return list;
  }
}
