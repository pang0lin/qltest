package com.js.oa.hr.kq.bean;

import com.js.oa.hr.kq.po.KqDutyRangePO;
import com.js.oa.hr.kq.po.KqDutySetPO;
import com.js.system.service.usermanager.UserBD;
import com.js.util.hibernate.HibernateBase;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class KqDutySetEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public void add(KqDutySetPO kqDutySetPO) throws Exception {
    Long id = Long.valueOf("-1");
    begin();
    try {
      id = (Long)this.session.save(kqDutySetPO);
      List<String> listUserids = new ArrayList();
      String userIdString = kqDutySetPO.getUserIds();
      if (userIdString != null && !"".equals(userIdString)) {
        userIdString = userIdString.substring(1, 
            userIdString.length() - 1);
        String[] userIds = userIdString.split("\\$\\$");
        for (int i = 0; i < userIds.length; i++) {
          if (!listUserids.contains(userIds[i].toString())) {
            KqDutyRangePO kqDutyRangePO = new KqDutyRangePO();
            kqDutyRangePO.setDutyId(id.longValue());
            kqDutyRangePO.setDutyName(kqDutySetPO.getDutyName());
            kqDutyRangePO.setUserId(Long.valueOf(userIds[i].toString()).longValue());
            this.session.save(kqDutyRangePO);
            listUserids.add(userIds[i].toString());
          } 
        } 
      } 
      String orgIdString = kqDutySetPO.getOrgIds();
      if (orgIdString != null && !"".equals(orgIdString)) {
        orgIdString = orgIdString.substring(1, orgIdString.length() - 1);
        String orgIds = orgIdString.replace("**", ",");
        UserBD userBD = new UserBD();
        List<E> list = new ArrayList();
        list = userBD.selectEmpIdByOrgIds(orgIds);
        if (!list.isEmpty() && list.size() > 0)
          for (int i = 0; i < list.size(); i++) {
            if (!listUserids.contains(list.get(i).toString())) {
              KqDutyRangePO kqDutyRangePO = new KqDutyRangePO();
              kqDutyRangePO.setDutyId(id.longValue());
              kqDutyRangePO.setDutyName(kqDutySetPO.getDutyName());
              kqDutyRangePO.setUserId(Long.valueOf(list.get(i).toString()).longValue());
              this.session.save(kqDutyRangePO);
              listUserids.add(list.get(i).toString());
            } 
          }  
      } 
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
  }
  
  public void update(KqDutySetPO kqDutySetPO) throws Exception {
    begin();
    try {
      this.session.update(kqDutySetPO);
      this.session.delete("from com.js.oa.hr.kq.po.KqDutyRangePO po where po.dutyId =" + kqDutySetPO.getId());
      List<String> listUserids = new ArrayList();
      String userIdString = kqDutySetPO.getUserIds();
      if (userIdString != null && !"".equals(userIdString)) {
        userIdString = userIdString.substring(1, 
            userIdString.length() - 1);
        String[] userIds = userIdString.split("\\$\\$");
        for (int i = 0; i < userIds.length; i++) {
          if (!listUserids.contains(userIds[i].toString())) {
            KqDutyRangePO kqDutyRangePO = new KqDutyRangePO();
            kqDutyRangePO.setDutyId(kqDutySetPO.getId());
            kqDutyRangePO.setDutyName(kqDutySetPO.getDutyName());
            kqDutyRangePO.setUserId(Long.valueOf(userIds[i].toString()).longValue());
            this.session.save(kqDutyRangePO);
            listUserids.add(userIds[i].toString());
          } 
        } 
      } 
      String orgIdString = kqDutySetPO.getOrgIds();
      if (orgIdString != null && !"".equals(orgIdString)) {
        orgIdString = orgIdString.substring(1, orgIdString.length() - 1);
        String orgIds = orgIdString.replace("**", ",");
        UserBD userBD = new UserBD();
        List<E> list = new ArrayList();
        list = userBD.selectEmpIdByOrgIds(orgIds);
        if (!list.isEmpty() && list.size() > 0)
          for (int i = 0; i < list.size(); i++) {
            if (!listUserids.contains(list.get(i).toString())) {
              KqDutyRangePO kqDutyRangePO = new KqDutyRangePO();
              kqDutyRangePO.setDutyId(kqDutySetPO.getId());
              kqDutyRangePO.setDutyName(kqDutySetPO.getDutyName());
              kqDutyRangePO.setUserId(Long.valueOf(list.get(i).toString()).longValue());
              this.session.save(kqDutyRangePO);
              listUserids.add(list.get(i).toString());
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
      this.session.delete(this.session.load(KqDutySetPO.class, Long.valueOf(id)));
      this.session.delete("from com.js.oa.hr.kq.po.KqDutyRangePO po where po.dutyId =" + id);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public KqDutySetPO searchById(long kqDutySetId) throws Exception {
    KqDutySetPO kqDutySetPO = new KqDutySetPO();
    begin();
    try {
      kqDutySetPO = (KqDutySetPO)this.session.load(KqDutySetPO.class, Long.valueOf(kqDutySetId));
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return kqDutySetPO;
  }
  
  public KqDutySetPO searchByUserId(long userId) throws Exception {
    KqDutySetPO kqDutySetPO = new KqDutySetPO();
    List<E> list = new ArrayList();
    long id = 1L;
    begin();
    try {
      list = this.session.createQuery("select po.dutyId  from com.js.oa.hr.kq.po.KqDutyRangePO po where po.userId =" + userId).list();
      if (!list.isEmpty())
        id = Long.valueOf(list.get(list.size() - 1).toString()).longValue(); 
      kqDutySetPO = (KqDutySetPO)this.session.load(KqDutySetPO.class, Long.valueOf(id));
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return kqDutySetPO;
  }
}
