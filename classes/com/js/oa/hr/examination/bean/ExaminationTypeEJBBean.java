package com.js.oa.hr.examination.bean;

import com.js.oa.hr.examination.po.ExaminationTypePO;
import com.js.util.hibernate.HibernateBase;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class ExaminationTypeEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Boolean save(ExaminationTypePO po) throws Exception {
    Boolean ret = Boolean.FALSE;
    begin();
    try {
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
  
  public ExaminationTypePO load(Long id) throws Exception {
    ExaminationTypePO po = null;
    try {
      begin();
      po = (ExaminationTypePO)this.session.load(ExaminationTypePO.class, id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return po;
  }
  
  public Boolean update(ExaminationTypePO po, Long id) throws Exception {
    Boolean ret = Boolean.FALSE;
    try {
      begin();
      ExaminationTypePO oldpo = (ExaminationTypePO)this.session.load(
          ExaminationTypePO.class, id);
      oldpo.setTypeName(po.getTypeName());
      this.session.update(oldpo);
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
  
  public Boolean delete(Long id) throws Exception {
    Boolean ret = Boolean.FALSE;
    try {
      begin();
      ExaminationTypePO po = (ExaminationTypePO)this.session.get(
          ExaminationTypePO.class, id);
      if (po != null) {
        this.session.delete(po);
        this.session.flush();
      } 
      ret = Boolean.TRUE;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public Boolean delBatch(String ids) throws Exception {
    Boolean ret = Boolean.FALSE;
    try {
      begin();
      String[] idsArr = ids.split(",");
      for (int i = 0; i < idsArr.length; i++) {
        ExaminationTypePO po = (ExaminationTypePO)this.session.get(
            ExaminationTypePO.class, new Long(idsArr[i]));
        if (po != null) {
          this.session.delete(po);
          this.session.flush();
        } 
      } 
      ret = Boolean.TRUE;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public List getTypeList(Long domainID) throws Exception {
    List list = null;
    try {
      begin();
      list = this.session.createQuery("select po.id,po.typeName from  com.js.oa.hr.examination.po.ExaminationTypePO po  where po.domainID=" + 
          
          domainID).list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
}
