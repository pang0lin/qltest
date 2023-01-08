package com.js.oa.datasync.service;

import com.js.oa.datasync.bean.DataSyncEJBBean;
import com.js.oa.datasync.po.DataSyncFieldPO;
import com.js.oa.datasync.po.DataSyncPO;
import com.js.oa.datasync.po.DataSyncSetPO;
import com.js.oa.portal.service.CustomDesktopBD;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class DataSyncBD {
  private DataSyncEJBBean dataSyncEJBBean = new DataSyncEJBBean();
  
  private static Logger logger = Logger.getLogger(CustomDesktopBD.class
      .getName());
  
  public void saveDataSync(DataSyncPO dataSyncPO) {
    try {
      this.dataSyncEJBBean.saveDataSync(dataSyncPO);
    } catch (Exception ex) {
      logger.error("error to save DataSyncPO :" + ex.getMessage());
      ex.printStackTrace();
    } 
  }
  
  public DataSyncPO getDataSyncPOById(Long id) {
    DataSyncPO dataSyncPO = null;
    try {
      dataSyncPO = this.dataSyncEJBBean.getDataSyncPOById(id);
    } catch (Exception ex) {
      logger.error("error to get DataSyncPO :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return dataSyncPO;
  }
  
  public List getAllDataSync() {
    List list = null;
    try {
      list = this.dataSyncEJBBean.getAllDataSync();
    } catch (Exception ex) {
      logger.error("error to getAllDataSync :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return list;
  }
  
  public void updateDataSync(DataSyncPO dataSyncPO) {
    try {
      this.dataSyncEJBBean.updateDataSync(dataSyncPO);
    } catch (Exception ex) {
      logger.error("error to getAllDataSync :" + ex.getMessage());
      ex.printStackTrace();
    } 
  }
  
  public void deleteDataSync(String ids) {
    try {
      this.dataSyncEJBBean.deleleDataSync(ids);
    } catch (Exception ex) {
      logger.error("error to deleteDataSync :" + ex.getMessage());
      ex.printStackTrace();
    } 
  }
  
  public Long saveDataSyncSetPO(DataSyncSetPO dataSyncSetPO) {
    Long id = Long.valueOf(0L);
    try {
      id = this.dataSyncEJBBean.saveDataSyncSetPO(dataSyncSetPO);
    } catch (Exception ex) {
      logger.error("error to save DataSyncSetPO :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return id;
  }
  
  public List getAllDataSyncSetPO(Long syncId) {
    List list = null;
    try {
      list = this.dataSyncEJBBean.getAllDataSyncSetPO(syncId);
    } catch (Exception ex) {
      logger.error("error to getAllDataSync :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return list;
  }
  
  public DataSyncSetPO getDataSyncSetPOById(Long id) {
    DataSyncSetPO dataSyncSetPO = null;
    try {
      dataSyncSetPO = this.dataSyncEJBBean.getDataSyncSetPOById(id);
    } catch (Exception ex) {
      logger.error("error to get DataSyncSetPO :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return dataSyncSetPO;
  }
  
  public void updateDataSyncSetPO(DataSyncSetPO dataSyncSetPO) {
    try {
      this.dataSyncEJBBean.updateDataSyncSetPO(dataSyncSetPO);
    } catch (Exception ex) {
      logger.error("error to updateDataSyncSetPO :" + ex.getMessage());
      ex.printStackTrace();
    } 
  }
  
  public void deleteDataSyncSetPO(String ids) {
    try {
      this.dataSyncEJBBean.deleleDataSyncSetPO(ids);
    } catch (Exception ex) {
      logger.error("error to deleteDataSync :" + ex.getMessage());
      ex.printStackTrace();
    } 
  }
  
  public void saveDataSyncFieldPO(DataSyncFieldPO dataSyncFieldPO) {
    try {
      this.dataSyncEJBBean.saveDataSyncFieldPO(dataSyncFieldPO);
    } catch (Exception ex) {
      logger.error("error to save DataSyncSetPO :" + ex.getMessage());
      ex.printStackTrace();
    } 
  }
  
  public List getAllProcess() {
    List list = null;
    try {
      list = this.dataSyncEJBBean.getAllProcess();
    } catch (Exception ex) {
      logger.error("error to deleteDataSync :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return list;
  }
  
  public List<String[]> getTableList(Long processId) {
    List<String[]> list = (List)new ArrayList<String>();
    try {
      list = this.dataSyncEJBBean.getTableList(processId);
    } catch (Exception ex) {
      logger.error("error to getTableList :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return list;
  }
  
  public List<String[]> getTableFieldList(String tableName) {
    List<String[]> list = (List)new ArrayList<String>();
    try {
      list = this.dataSyncEJBBean.getTableFieldList(tableName);
    } catch (Exception ex) {
      logger.error("error to getTableFieldList :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return list;
  }
  
  public List<String[]> getSyncSetFieldList(String syncSetId) {
    List<String[]> list = (List)new ArrayList<String>();
    try {
      list = this.dataSyncEJBBean.getSyncSetFieldList(syncSetId);
    } catch (Exception ex) {
      logger.error("error to getSyncSetFieldList :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return list;
  }
  
  public String deleteDataSyncSetFields(String syncSetId) {
    String res = "";
    try {
      this.dataSyncEJBBean.deleteDataSyncSetFields(syncSetId);
    } catch (Exception ex) {
      logger.error("error to getSyncSetFieldList :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return res;
  }
  
  public List<String[]> getProcessActivity(String processId) {
    List<String[]> list = (List)new ArrayList<String>();
    try {
      list = this.dataSyncEJBBean.getProcessActivity(processId);
    } catch (Exception ex) {
      logger.error("error to getProcessActivity :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return list;
  }
}
