package com.js.oa.workplan.bean;

import com.js.oa.workplan.po.WorkplanGroupPO;
import com.js.oa.workplan.po.WorkplanProxyPO;
import com.js.oa.workplan.po.WorkplanStatusPO;
import com.js.util.hibernate.HibernateBase;
import java.util.ArrayList;
import java.util.List;
import net.sf.hibernate.Query;

public class WorkplanSetBean extends HibernateBase {
  public List searchGroupList() throws Exception {
    List list = new ArrayList();
    begin();
    try {
      String hql = "select po.id,po.groupName,po.groupLeaderName,po.groupLeaderId from com.js.oa.workplan.po.WorkplanGroupPO po  order by po.id desc";
      Query query = this.session.createQuery(hql);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public boolean addGroup(WorkplanGroupPO po) throws Exception {
    boolean result = false;
    try {
      begin();
      this.session.save(po);
      this.session.flush();
      this.session.close();
      result = true;
    } catch (Exception err) {
      this.session.close();
      throw err;
    } 
    return result;
  }
  
  public WorkplanGroupPO loadGroup(String groupId) throws Exception {
    WorkplanGroupPO po = null;
    try {
      begin();
      po = (WorkplanGroupPO)this.session.load(WorkplanGroupPO.class, Long.valueOf(groupId));
      this.session.close();
    } catch (Exception ex) {
      this.session.close();
      throw ex;
    } 
    return po;
  }
  
  public boolean updateGroup(WorkplanGroupPO po) throws Exception {
    boolean result = false;
    try {
      begin();
      this.session.update(po);
      this.session.flush();
      this.session.close();
      result = true;
    } catch (Exception err) {
      this.session.close();
      throw err;
    } 
    return result;
  }
  
  public boolean delGroup(String groupId) throws Exception {
    boolean result = false;
    try {
      begin();
      this.session.delete("from com.js.oa.workplan.po.WorkplanGroupPO po where po.id in (" + groupId + ")");
      this.session.flush();
      this.session.close();
      result = true;
    } catch (Exception err) {
      this.session.close();
      throw err;
    } 
    return result;
  }
  
  public List searchStatusList() throws Exception {
    List list = new ArrayList();
    begin();
    try {
      Query query = this.session.createQuery("select po.id,po.statusName from com.js.oa.workplan.po.WorkplanStatusPO po order by po.id desc");
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public boolean addStatus(WorkplanStatusPO po) throws Exception {
    boolean result = false;
    try {
      begin();
      this.session.save(po);
      this.session.flush();
      this.session.close();
      result = true;
    } catch (Exception err) {
      this.session.close();
      throw err;
    } 
    return result;
  }
  
  public WorkplanStatusPO loadStatus(String statusId) throws Exception {
    WorkplanStatusPO po = null;
    try {
      begin();
      po = (WorkplanStatusPO)this.session.load(WorkplanStatusPO.class, Long.valueOf(statusId));
      this.session.close();
    } catch (Exception ex) {
      this.session.close();
      throw ex;
    } 
    return po;
  }
  
  public boolean updateStatus(WorkplanStatusPO po) throws Exception {
    boolean result = false;
    try {
      begin();
      this.session.update(po);
      this.session.flush();
      this.session.close();
      result = true;
    } catch (Exception err) {
      this.session.close();
      throw err;
    } 
    return result;
  }
  
  public boolean delStatus(String statusId) throws Exception {
    boolean result = false;
    try {
      begin();
      this.session.delete("from com.js.oa.workplan.po.WorkplanStatusPO po where po.id in (" + statusId + ")");
      this.session.flush();
      this.session.close();
      result = true;
    } catch (Exception err) {
      this.session.close();
      throw err;
    } 
    return result;
  }
  
  public List searchProxyList(String leaderId) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      Query query = this.session.createQuery("select po.id,po.leaderName,po.proxyName,po.proxystatus,po.proxybegin,po.proxyend,po.proxyId from com.js.oa.workplan.po.WorkplanProxyPO po where po.leaderId=" + leaderId);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public boolean addProxy(WorkplanProxyPO po) throws Exception {
    boolean result = false;
    try {
      begin();
      this.session.save(po);
      this.session.flush();
      this.session.close();
      result = true;
    } catch (Exception err) {
      this.session.close();
      throw err;
    } 
    return result;
  }
  
  public WorkplanProxyPO loadProxy(String proxysetId) throws Exception {
    WorkplanProxyPO po = null;
    try {
      begin();
      po = (WorkplanProxyPO)this.session.load(WorkplanProxyPO.class, Long.valueOf(proxysetId));
      this.session.close();
    } catch (Exception ex) {
      this.session.close();
      throw ex;
    } 
    return po;
  }
  
  public boolean updateProxy(WorkplanProxyPO po) throws Exception {
    boolean result = false;
    try {
      begin();
      this.session.update(po);
      this.session.flush();
      this.session.close();
      result = true;
    } catch (Exception err) {
      this.session.close();
      throw err;
    } 
    return result;
  }
  
  public boolean delProxy(String proxysetId) throws Exception {
    boolean result = false;
    try {
      begin();
      this.session.delete("from com.js.oa.workplan.po.WorkplanProxyPO po where po.id in (" + proxysetId + ")");
      this.session.flush();
      this.session.close();
      result = true;
    } catch (Exception err) {
      this.session.close();
      throw err;
    } 
    return result;
  }
  
  public List getLeaderList(String proxyId) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      Query query = this.session.createQuery("select po.leaderId,po.leaderName from com.js.oa.workplan.po.WorkplanProxyPO po where po.proxyId=" + proxyId);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public boolean userIsLeader(String userId) throws Exception {
    begin();
    try {
      String hql = "select po.groupLeaderName from com.js.oa.workplan.po.WorkplanGroupPO po where groupLeaderId like '$%" + userId + "%$'";
      Query query = this.session.createQuery(hql);
      List list = query.list();
      if (list != null && list.size() > 0)
        return true; 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return false;
  }
  
  public boolean userIsProxy(String userId) throws Exception {
    begin();
    try {
      String hql = "select po.proxyName from com.js.oa.workplan.po.WorkplanProxyPO po where proxyId=" + userId;
      Query query = this.session.createQuery(hql);
      List list = query.list();
      if (list != null && list.size() > 0)
        return true; 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return false;
  }
}
