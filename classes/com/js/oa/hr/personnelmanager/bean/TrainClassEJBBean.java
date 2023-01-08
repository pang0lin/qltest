package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.TrainClassPO;
import com.js.util.hibernate.HibernateBase;
import java.util.Iterator;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class TrainClassEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Boolean save(TrainClassPO po) throws Exception {
    Boolean ret = new Boolean(false);
    try {
      begin();
      Iterator iter = this.session.createQuery(
          "select po.id from com.js.oa.hr.personnelmanager.po.TrainClassPO po  where po.trainName='" + 
          po.getTrainName() + "' and po.domain=" + po.getDomain()).iterate();
      if (!iter.hasNext()) {
        this.session.save(po);
        this.session.flush();
        ret = new Boolean(true);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public TrainClassPO load(Long id) throws Exception {
    TrainClassPO po = null;
    try {
      begin();
      po = (TrainClassPO)this.session.load(TrainClassPO.class, id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return po;
  }
  
  public Boolean update(TrainClassPO po, Long id) throws Exception {
    Boolean ret = Boolean.FALSE;
    try {
      begin();
      Iterator iter = this.session.createQuery(
          "select po.id from com.js.oa.hr.personnelmanager.po.TrainClassPO po  where po.trainName='" + 
          po.getTrainName() + "' and po.domain=" + po.getDomain() + " and po.id<>" + id).iterate();
      if (!iter.hasNext()) {
        TrainClassPO needModifyPO = (TrainClassPO)this.session.load(
            TrainClassPO.class, id);
        needModifyPO.setDomain(po.getDomain());
        needModifyPO.setTrainAim(po.getTrainAim());
        needModifyPO.setTrainContent(po.getTrainContent());
        needModifyPO.setTrainDescribe(po.getTrainDescribe());
        needModifyPO.setTrainName(po.getTrainName());
        this.session.update(needModifyPO);
        this.session.flush();
        ret = Boolean.TRUE;
      } 
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
      this.session.delete("from com.js.oa.hr.personnelmanager.po.TrainClassPO po where po.id in(" + ids + ")");
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
  
  public List list(Long domainID) throws Exception {
    List list = null;
    try {
      begin();
      list = this.session.createQuery("select po.id,po.trainName from com.js.oa.hr.personnelmanager.po.TrainClassPO po where po.domain=" + 
          
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
