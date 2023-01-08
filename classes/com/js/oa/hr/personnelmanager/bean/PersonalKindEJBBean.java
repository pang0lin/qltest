package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.PersonalKindPO;
import com.js.util.hibernate.HibernateBase;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class PersonalKindEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Boolean save(PersonalKindPO po) throws Exception {
    Boolean ret = Boolean.FALSE;
    try {
      begin();
      this.session.save(po);
      this.session.flush();
      ret = Boolean.TRUE;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public PersonalKindPO load(Long id) throws Exception {
    PersonalKindPO po = null;
    try {
      begin();
      po = (PersonalKindPO)this.session.get(PersonalKindPO.class, id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return po;
  }
  
  public PersonalKindPO loadByName(String name) throws Exception {
    PersonalKindPO po = null;
    List<PersonalKindPO> list = null;
    try {
      begin();
      list = this.session.createQuery("from com.js.oa.hr.personnelmanager.po.PersonalKindPO po where  po.kindName ='" + 
          name + "'").list();
      if (list.size() == 1)
        return list.get(0); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return po;
  }
  
  public Boolean update(PersonalKindPO po, Long id) throws Exception {
    Boolean ret = Boolean.FALSE;
    try {
      begin();
      PersonalKindPO needModifyPO = (PersonalKindPO)this.session.load(PersonalKindPO.class, id);
      needModifyPO.setKindName(po.getKindName());
      needModifyPO.setKindDescription(po.getKindDescription());
      needModifyPO.setKindSort(po.getKindSort());
      this.session.update(needModifyPO);
      this.session.flush();
      ret = Boolean.TRUE;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public Boolean delete(String ids) throws Exception {
    Boolean ret = Boolean.FALSE;
    try {
      begin();
      this.session.delete("from com.js.oa.hr.personnelmanager.po.PersonalKindPO po  where po.kindId in(" + 
          ids + ")");
      this.session.flush();
      ret = Boolean.TRUE;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public List list() throws Exception {
    List list = null;
    try {
      begin();
      list = this.session.createQuery("select po.kindId,po.kindName from com.js.oa.hr.personnelmanager.po.PersonalKindPO po order by po.kindSort")
        .list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public Boolean checkExistKind(Long kindId, String kindName) throws Exception {
    Boolean ret = Boolean.FALSE;
    try {
      begin();
      List list = this.session.createQuery("select po from com.js.oa.hr.personnelmanager.po.PersonalKindPO po where " + ((kindId != null && !"".equals(kindId)) ? (" po.kindId != " + kindId + " and ") : "") + " po.kindName ='" + 
          kindName + "'")
        .list();
      if (list != null && list.size() > 0)
        ret = Boolean.TRUE; 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
}
