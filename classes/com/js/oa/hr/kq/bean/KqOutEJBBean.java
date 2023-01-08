package com.js.oa.hr.kq.bean;

import com.js.oa.hr.kq.po.KqOutPO;
import com.js.util.hibernate.HibernateBase;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class KqOutEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Long add(KqOutPO kqOutPO) throws Exception {
    Long eveId = Long.valueOf("-1");
    begin();
    try {
      eveId = (Long)this.session.save(kqOutPO);
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
  
  public void update(KqOutPO kqOutPO) throws Exception {
    begin();
    try {
      this.session.update(kqOutPO);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void del(long kqOutId) throws Exception {
    begin();
    try {
      this.session.delete(this.session.load(KqOutPO.class, Long.valueOf(kqOutId)));
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public KqOutPO searchById(long kqOutId) throws Exception {
    KqOutPO kqOutPO = new KqOutPO();
    begin();
    try {
      kqOutPO = (KqOutPO)this.session.load(KqOutPO.class, Long.valueOf(kqOutId));
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return kqOutPO;
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
      sta = (Integer)this.session.createQuery("select count(po) from com.js.oa.hr.kq.po.KqOutPO po" + whereSQL).uniqueResult();
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
      sta = (Integer)this.session.createQuery("select count(po) from com.js.oa.hr.kq.po.KqOutPO po" + whereSQL).uniqueResult();
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
