package com.js.oa.hr.kq.bean;

import com.js.oa.hr.kq.po.KqEvectionPO;
import com.js.util.hibernate.HibernateBase;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class KqEvectionEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Long add(KqEvectionPO kqEvectionPO) throws Exception {
    Long eveId = Long.valueOf("-1");
    begin();
    try {
      eveId = (Long)this.session.save(kqEvectionPO);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return eveId;
  }
  
  public void update(KqEvectionPO kqEvectionPO) throws Exception {
    begin();
    try {
      this.session.update(kqEvectionPO);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void del(long kqEvectionId) throws Exception {
    begin();
    try {
      this.session.delete(this.session.load(KqEvectionPO.class, Long.valueOf(kqEvectionId)));
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public KqEvectionPO searchById(long kqEvectionId) throws Exception {
    KqEvectionPO kqEvectionPO = new KqEvectionPO();
    begin();
    try {
      kqEvectionPO = (KqEvectionPO)this.session.load(KqEvectionPO.class, Long.valueOf(kqEvectionId));
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return kqEvectionPO;
  }
  
  public void isreturn(long kqEvectionId) throws Exception {
    KqEvectionPO kqEvectionPO = new KqEvectionPO();
    begin();
    try {
      kqEvectionPO = (KqEvectionPO)this.session.load(KqEvectionPO.class, Long.valueOf(kqEvectionId));
      kqEvectionPO.setIsreturn(1);
      kqEvectionPO.setEndTime(new Date());
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
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
      sta = (Integer)this.session.createQuery("select count(po) from com.js.oa.hr.kq.po.KqEvectionPO po" + whereSQL).uniqueResult();
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
      sta = (Integer)this.session.createQuery("select count(po) from com.js.oa.hr.kq.po.KqEvectionPO po" + whereSQL).uniqueResult();
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
