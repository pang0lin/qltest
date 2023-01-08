package com.js.oa.workplan.service;

import com.js.oa.workplan.bean.WorkplanSetBean;
import com.js.oa.workplan.po.WorkplanGroupPO;
import com.js.oa.workplan.po.WorkplanProxyPO;
import com.js.oa.workplan.po.WorkplanStatusPO;
import java.util.List;
import org.apache.log4j.Logger;

public class WorkplanSetService {
  private static Logger logger = Logger.getLogger(WorkplanSetService.class.getName());
  
  public boolean addGroup(WorkplanGroupPO po) {
    boolean result = false;
    WorkplanSetBean bean = new WorkplanSetBean();
    try {
      result = bean.addGroup(po);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return result;
  }
  
  public WorkplanGroupPO loadGroup(String groupId) {
    WorkplanGroupPO po = null;
    WorkplanSetBean bean = new WorkplanSetBean();
    try {
      po = bean.loadGroup(groupId);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return po;
  }
  
  public boolean modifyGroup(WorkplanGroupPO po) {
    boolean result = false;
    WorkplanSetBean bean = new WorkplanSetBean();
    try {
      result = bean.updateGroup(po);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return result;
  }
  
  public boolean delGroup(String groupId) {
    WorkplanGroupPO po = null;
    WorkplanSetBean bean = new WorkplanSetBean();
    try {
      return bean.delGroup(groupId);
    } catch (Exception ex) {
      ex.printStackTrace();
      return false;
    } 
  }
  
  public boolean addStatus(WorkplanStatusPO po) {
    boolean result = false;
    WorkplanSetBean bean = new WorkplanSetBean();
    try {
      result = bean.addStatus(po);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return result;
  }
  
  public WorkplanStatusPO loadStatus(String statusId) {
    WorkplanStatusPO po = null;
    WorkplanSetBean bean = new WorkplanSetBean();
    try {
      po = bean.loadStatus(statusId);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return po;
  }
  
  public boolean modifyStatus(WorkplanStatusPO po) {
    boolean result = false;
    WorkplanSetBean bean = new WorkplanSetBean();
    try {
      result = bean.updateStatus(po);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return result;
  }
  
  public boolean delStatus(String statusId) {
    WorkplanStatusPO po = null;
    WorkplanSetBean bean = new WorkplanSetBean();
    try {
      return bean.delStatus(statusId);
    } catch (Exception ex) {
      ex.printStackTrace();
      return false;
    } 
  }
  
  public boolean addProxy(WorkplanProxyPO po) {
    boolean result = false;
    WorkplanSetBean bean = new WorkplanSetBean();
    try {
      result = bean.addProxy(po);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return result;
  }
  
  public WorkplanProxyPO loadproxy(String proxysetId) {
    WorkplanProxyPO po = null;
    WorkplanSetBean bean = new WorkplanSetBean();
    try {
      po = bean.loadProxy(proxysetId);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return po;
  }
  
  public boolean modifyProxy(WorkplanProxyPO po) {
    boolean result = false;
    WorkplanSetBean bean = new WorkplanSetBean();
    try {
      result = bean.updateProxy(po);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return result;
  }
  
  public boolean delProxy(String proxysetId) {
    WorkplanStatusPO po = null;
    WorkplanSetBean bean = new WorkplanSetBean();
    try {
      return bean.delProxy(proxysetId);
    } catch (Exception ex) {
      ex.printStackTrace();
      return false;
    } 
  }
  
  public List getLeaderList(String proxyId) {
    List list = null;
    try {
      list = (new WorkplanSetBean()).getLeaderList(proxyId);
    } catch (Exception err) {
      err.printStackTrace();
    } 
    return list;
  }
  
  public boolean userIsLeader(String userId) {
    boolean result = false;
    WorkplanSetBean bean = new WorkplanSetBean();
    try {
      result = bean.userIsLeader(userId);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return result;
  }
  
  public boolean userIsProxy(String userId) {
    boolean result = false;
    WorkplanSetBean bean = new WorkplanSetBean();
    try {
      result = bean.userIsProxy(userId);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return result;
  }
  
  public List searchStatusList() {
    List list = null;
    try {
      list = (new WorkplanSetBean()).searchStatusList();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return list;
  }
}
