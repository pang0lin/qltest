package com.js.oa.hr.kq.bean;

import com.js.oa.hr.kq.po.KqLeavePO;
import com.js.util.hibernate.HibernateBase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class KqLeaveEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Long add(KqLeavePO kqLeavePO) throws Exception {
    Long leaveId = new Long(-1L);
    try {
      begin();
      leaveId = (Long)this.session.save(kqLeavePO);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return leaveId;
  }
  
  public void update(KqLeavePO kqLeavePO) throws Exception {
    begin();
    try {
      this.session.update(kqLeavePO);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void del(long kqLeaveId) throws Exception {
    begin();
    try {
      this.session.delete(this.session.load(KqLeavePO.class, Long.valueOf(kqLeaveId)));
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public KqLeavePO searchById(long kqLeaveId) throws Exception {
    KqLeavePO kqLeavePO = new KqLeavePO();
    begin();
    try {
      kqLeavePO = (KqLeavePO)this.session.load(KqLeavePO.class, Long.valueOf(kqLeaveId));
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return kqLeavePO;
  }
  
  public List searchLeaveType(long domainId) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      list = this.session.createQuery("select po.id,po.name from com.js.oa.hr.kq.po.KqLeaveTypePO po where po.domainId=" + domainId).list();
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public Map<Integer, String> searchLeaveTypeMap() throws Exception {
    Map<Integer, String> leaveTypeMap = new HashMap<Integer, String>();
    List<Object[]> list = new ArrayList();
    begin();
    try {
      list = this.session.createQuery("select po.id,po.name from com.js.oa.hr.kq.po.KqLeaveTypePO po").list();
      if (!list.isEmpty())
        for (int i = 0; i < list.size(); i++) {
          Object[] obj = list.get(i);
          leaveTypeMap.put(new Integer(obj[0].toString()), obj[1].toString());
        }  
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return leaveTypeMap;
  }
  
  public int searchStat(String begindate, String enddate, long userId) throws Exception {
    Integer sta = Integer.valueOf(0);
    begin();
    try {
      String whereSQL = " where po.status=100 and po.userId =" + userId;
      if (!"".equals(begindate))
        whereSQL = String.valueOf(whereSQL) + " and po.startTime >='" + begindate + "'"; 
      if (!"".equals(enddate))
        whereSQL = String.valueOf(whereSQL) + " and po.endTime <='" + enddate + "'"; 
      sta = (Integer)this.session.createQuery("select count(po) from com.js.oa.hr.kq.po.KqLeavePO po" + whereSQL).uniqueResult();
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return sta.intValue();
  }
  
  public int searchStatOrg(String begindate, String enddate, long orgId) throws Exception {
    Integer sta = Integer.valueOf(0);
    List<E> list = new ArrayList();
    begin();
    String orgIds = "";
    try {
      list = this.session.createQuery("select org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org  where org.orgStatus=0  and org.orgIdString like '%$" + orgId + "$%'").list();
      if (!list.isEmpty())
        for (int i = 0; i < list.size(); i++)
          orgIds = String.valueOf(orgIds) + list.get(i).toString() + ",";  
      orgIds = orgIds.substring(0, orgIds.length() - 1);
      String whereSQL = " where po.status=100 and po.orgId in ( " + orgIds + " )";
      if (!"".equals(begindate))
        whereSQL = String.valueOf(whereSQL) + " and po.startTime >='" + begindate + "'"; 
      if (!"".equals(enddate))
        whereSQL = String.valueOf(whereSQL) + " and po.endTime <='" + enddate + "'"; 
      sta = (Integer)this.session.createQuery("select count(po) from com.js.oa.hr.kq.po.KqLeavePO po" + whereSQL).uniqueResult();
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return sta.intValue();
  }
}
