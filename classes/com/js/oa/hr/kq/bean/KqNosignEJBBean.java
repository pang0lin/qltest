package com.js.oa.hr.kq.bean;

import com.js.oa.hr.kq.po.KqNosignPO;
import com.js.oa.hr.kq.po.KqNosignUserPO;
import com.js.util.hibernate.HibernateBase;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class KqNosignEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Long add(KqNosignPO kqNosignPO) throws Exception {
    Long eveId = Long.valueOf("-1");
    begin();
    try {
      eveId = (Long)this.session.save(kqNosignPO);
      String userId = kqNosignPO.getUserIds();
      userId = userId.substring(1, userId.length() - 1);
      String[] userIds = userId.split("\\$\\$");
      for (int i = 0; i < userIds.length; i++) {
        String nosignType = kqNosignPO.getNosignType();
        for (int j = 0; j < 6; j++) {
          if ("1".equals(String.valueOf(nosignType.charAt(j)))) {
            KqNosignUserPO kqNosignUserPO = new KqNosignUserPO();
            kqNosignUserPO.setNsignId(eveId.longValue());
            kqNosignUserPO.setNsignSeq(j + 1);
            kqNosignUserPO.setUserId(Long.valueOf(userIds[i].toString()).longValue());
            this.session.save(kqNosignUserPO);
          } 
        } 
      } 
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return eveId;
  }
  
  public void update(KqNosignPO kqNosignPO) throws Exception {
    begin();
    try {
      this.session.delete("from com.js.oa.hr.kq.po.KqNosignUserPO po where po.nsignId=" + kqNosignPO.getId());
      this.session.update(kqNosignPO);
      String userId = kqNosignPO.getUserIds();
      userId = userId.substring(1, userId.length() - 1);
      String[] userIds = userId.split("\\$\\$");
      for (int i = 0; i < userIds.length; i++) {
        String nosignType = kqNosignPO.getNosignType();
        for (int j = 0; j < 6; j++) {
          if ("1".equals(String.valueOf(nosignType.charAt(j)))) {
            KqNosignUserPO kqNosignUserPO = new KqNosignUserPO();
            kqNosignUserPO.setNsignId(kqNosignPO.getId());
            kqNosignUserPO.setNsignSeq(j + 1);
            kqNosignUserPO.setUserId(Long.valueOf(userIds[i].toString()).longValue());
            this.session.save(kqNosignUserPO);
          } 
        } 
      } 
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void del(long id) throws Exception {
    begin();
    try {
      this.session.delete("from com.js.oa.hr.kq.po.KqNosignUserPO po where po.nsignId=" + id);
      this.session.delete(this.session.load(KqNosignPO.class, Long.valueOf(id)));
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public KqNosignPO searchById(long kqNosignId) throws Exception {
    KqNosignPO kqNosignPO = new KqNosignPO();
    begin();
    try {
      kqNosignPO = (KqNosignPO)this.session.load(KqNosignPO.class, Long.valueOf(kqNosignId));
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return kqNosignPO;
  }
  
  public boolean isNosignByUser(long userId, int seq, String beginDate, String endDate) throws Exception {
    List list = new ArrayList();
    boolean re = false;
    begin();
    try {
      list = this.session.createQuery("select po from com.js.oa.hr.kq.po.KqNosignUserPO po where po.userId =" + userId + " and po.nsignSeq =" + seq).list();
      if (!list.isEmpty())
        re = true; 
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
}
