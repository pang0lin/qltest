package com.js.oa.hr.kq.bean;

import com.js.oa.hr.kq.po.KqRecordPO;
import com.js.oa.hr.kq.po.KqSigntimePO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class KqRecordEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public void add(KqRecordPO kqRecordPO) throws Exception {
    begin();
    try {
      this.session.save(kqRecordPO);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
  }
  
  public boolean seachByDate(long userId, String beginDate, String endDate) throws Exception {
    boolean re = false;
    List list = new ArrayList();
    begin();
    try {
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0)
        list = this.session.createQuery(" select po from com.js.oa.hr.kq.po.KqRecordPO po where po.userId =" + userId + 
            " and po.dutyTime >= '" + beginDate + "' and  po.dutyTime <= '" + endDate + "' ").list(); 
      if (databaseType.indexOf("oracle") >= 0)
        list = this.session.createQuery(" select po from com.js.oa.hr.kq.po.KqRecordPO po where po.userId =" + userId + " " + 
            "and po.dutyTime >= to_date('" + beginDate + "','yyyy-MM-dd hh24:mi:ss') and  po.dutyTime <= to_date('" + 
            endDate + "','yyyy-MM-dd hh24:mi:ss') ").list(); 
      if (list.isEmpty()) {
        re = true;
      } else {
        re = false;
      } 
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return re;
  }
  
  public KqRecordPO seachKqRecordByDate(long userId, String beginDate, String endDate) throws Exception {
    KqRecordPO kqRecordPO = new KqRecordPO();
    List<KqRecordPO> list = new ArrayList();
    begin();
    try {
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0)
        list = this.session.createQuery(" select po from com.js.oa.hr.kq.po.KqRecordPO po where po.recordStatus = -1 and po.userId =" + userId + " " + 
            "and po.dutyTime >= '" + beginDate + "' and  po.dutyTime <= '" + endDate + "' order by po.id ").list(); 
      if (databaseType.indexOf("oracle") >= 0)
        list = this.session.createQuery(" select po from com.js.oa.hr.kq.po.KqRecordPO po where po.recordStatus = -1 and po.userId =" + userId + " " + 
            "and po.dutyTime >= to_date('" + beginDate + "','yyyy-MM-dd HH24:MI:SS') and  po.dutyTime <= to_date('" + endDate + "','yyyy-MM-dd HH24:MI:SS') order by po.id ").list(); 
      if (list.isEmpty()) {
        kqRecordPO = null;
      } else {
        kqRecordPO = list.get(0);
      } 
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return kqRecordPO;
  }
  
  public KqRecordPO getKqRecordById(long kqRecordId) throws Exception {
    KqRecordPO kqRecordPO = new KqRecordPO();
    begin();
    try {
      kqRecordPO = (KqRecordPO)this.session.get(KqRecordPO.class, Long.valueOf(kqRecordId));
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return kqRecordPO;
  }
  
  public void update(KqRecordPO kqRecordPO) throws Exception {
    begin();
    try {
      this.session.update(kqRecordPO);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public int searchStat(String begindate, String enddate, long userId, int type, long domain_id) throws Exception {
    Integer sta = Integer.valueOf(0);
    KqSigntimePO kqSigntimePO = new KqSigntimePO();
    begin();
    try {
      List<KqSigntimePO> list = this.session.createQuery("select po from com.js.oa.hr.kq.po.KqSigntimePO po where po.domainId =" + domain_id).list();
      if (list.isEmpty()) {
        kqSigntimePO = null;
      } else {
        kqSigntimePO = list.get(0);
      } 
      int offset = 30;
      if (kqSigntimePO != null)
        offset = kqSigntimePO.getOffset(); 
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date());
      calendar.add(12, -offset);
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      String whereSQL = " where  po.userId =" + userId + " and po.recordStatus=" + type + " and po.dutyTime < '" + format.format(calendar.getTime()) + "'";
      if (!"".equals(begindate))
        whereSQL = String.valueOf(whereSQL) + " and po.dutyTime >='" + begindate + "'"; 
      if (!"".equals(enddate))
        whereSQL = String.valueOf(whereSQL) + " and po.dutyTime <='" + enddate + "'"; 
      sta = (Integer)this.session.createQuery("select count(po) from com.js.oa.hr.kq.po.KqRecordPO po" + whereSQL).uniqueResult();
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
  
  public int searchStatOrg(String begindate, String enddate, long orgId, int type, long domain_id) throws Exception {
    Integer sta = Integer.valueOf(0);
    KqSigntimePO kqSigntimePO = new KqSigntimePO();
    List<E> list = new ArrayList();
    begin();
    String orgIds = "";
    try {
      list = this.session.createQuery("select org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org  where org.orgStatus=0  and org.orgIdString like '%$" + orgId + "$%'").list();
      if (!list.isEmpty())
        for (int i = 0; i < list.size(); i++)
          orgIds = String.valueOf(orgIds) + list.get(i).toString() + ",";  
      List<KqSigntimePO> list1 = this.session.createQuery("select po from com.js.oa.hr.kq.po.KqSigntimePO po where po.domainId =" + domain_id).list();
      if (list1.isEmpty()) {
        kqSigntimePO = null;
      } else {
        kqSigntimePO = list1.get(0);
      } 
      int offset = 30;
      if (kqSigntimePO != null)
        offset = kqSigntimePO.getOffset(); 
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date());
      calendar.add(12, -offset);
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      orgIds = orgIds.substring(0, orgIds.length() - 1);
      String whereSQL = " where  po.orgId in ( " + orgIds + " ) and po.recordStatus=" + type + " and po.dutyTime <'" + format.format(calendar.getTime()) + "'";
      if (!"".equals(begindate))
        whereSQL = String.valueOf(whereSQL) + " and po.dutyTime >='" + begindate + "'"; 
      if (!"".equals(enddate))
        whereSQL = String.valueOf(whereSQL) + " and po.dutyTime <='" + enddate + "'"; 
      sta = (Integer)this.session.createQuery("select count(po) from com.js.oa.hr.kq.po.KqRecordPO po" + whereSQL).uniqueResult();
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
