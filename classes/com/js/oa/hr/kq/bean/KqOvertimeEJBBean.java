package com.js.oa.hr.kq.bean;

import com.js.oa.hr.kq.po.KqOvertimePO;
import com.js.oa.hr.kq.po.KqOvertimeUserPO;
import com.js.util.hibernate.HibernateBase;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class KqOvertimeEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Long add(KqOvertimePO kqOvertimePO) throws Exception {
    Long eveId = Long.valueOf("-1");
    begin();
    try {
      eveId = (Long)this.session.save(kqOvertimePO);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return eveId;
  }
  
  public void update(KqOvertimePO kqOvertimePO) throws Exception {
    begin();
    try {
      this.session.update(kqOvertimePO);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void del(long kqOvertimeId) throws Exception {
    begin();
    try {
      this.session.delete(this.session.load(KqOvertimePO.class, Long.valueOf(kqOvertimeId)));
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public KqOvertimePO searchById(long kqOvertimeId) throws Exception {
    KqOvertimePO kqOvertimePO = new KqOvertimePO();
    begin();
    try {
      kqOvertimePO = (KqOvertimePO)this.session.load(KqOvertimePO.class, Long.valueOf(kqOvertimeId));
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return kqOvertimePO;
  }
  
  public int searchStat(String begindate, String enddate, long userId) throws Exception {
    Integer sta = Integer.valueOf(0);
    begin();
    try {
      String whereSQL = " where po.status=100 and po.id=poUser.overtimeId and poUser.userId =" + userId;
      if (!"".equals(begindate))
        whereSQL = String.valueOf(whereSQL) + " and po.startTime >='" + begindate + "'"; 
      if (!"".equals(enddate))
        whereSQL = String.valueOf(whereSQL) + " and po.endTime <='" + enddate + "'"; 
      sta = (Integer)this.session.createQuery("select count(po) from com.js.oa.hr.kq.po.KqOvertimePO po ,com.js.oa.hr.kq.po.KqOvertimeUserPO poUser " + whereSQL).uniqueResult();
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
      list = this.session.createQuery("select org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org  where org.orgStatus=0 and org.orgIdString like '%$" + orgId + "$%'").list();
      if (!list.isEmpty())
        for (int i = 0; i < list.size(); i++)
          orgIds = String.valueOf(orgIds) + list.get(i).toString() + ",";  
      orgIds = orgIds.substring(0, orgIds.length() - 1);
      String whereSQL = " where po.status=100 and po.id=poUser.overtimeId  and poUser.orgId in ( " + orgIds + " )";
      if (!"".equals(begindate))
        whereSQL = String.valueOf(whereSQL) + " and po.startTime >='" + begindate + "'"; 
      if (!"".equals(enddate))
        whereSQL = String.valueOf(whereSQL) + " and po.endTime <='" + enddate + "'"; 
      sta = (Integer)this.session.createQuery("select count(po) from com.js.oa.hr.kq.po.KqOvertimePO po ,com.js.oa.hr.kq.po.KqOvertimeUserPO poUser " + whereSQL).uniqueResult();
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
  
  public Long addKqOvertimeUser(KqOvertimeUserPO kqOvertimeUserPO) throws Exception {
    Long eveId = Long.valueOf("-1");
    begin();
    try {
      eveId = (Long)this.session.save(kqOvertimeUserPO);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return eveId;
  }
}
