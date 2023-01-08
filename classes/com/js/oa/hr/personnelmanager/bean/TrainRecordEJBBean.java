package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.TrainRecordPO;
import com.js.util.hibernate.HibernateBase;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class TrainRecordEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Boolean save(TrainRecordPO po) throws Exception {
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
  
  public TrainRecordPO load(Long trainRecordID) throws Exception {
    TrainRecordPO po = null;
    try {
      begin();
      po = (TrainRecordPO)this.session.load(TrainRecordPO.class, 
          trainRecordID);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return po;
  }
  
  public Boolean update(TrainRecordPO po, Long trainRecordID) throws Exception {
    Boolean ret = Boolean.FALSE;
    try {
      begin();
      TrainRecordPO needModifyPO = (TrainRecordPO)this.session.load(
          TrainRecordPO.class, trainRecordID);
      needModifyPO.setTrainName(po.getTrainName());
      needModifyPO.setTrainClass(po.getTrainClass());
      needModifyPO.setTrainOrganizer(po.getTrainOrganizer());
      needModifyPO.setTrainOrganizerID(po.getTrainOrganizerID());
      needModifyPO.setTrainContent(po.getTrainContent());
      needModifyPO.setTrainAim(po.getTrainAim());
      needModifyPO.setTrainAddress(po.getTrainAddress());
      needModifyPO.setTrainActor(po.getTrainActor());
      needModifyPO.setTrainEmpID(po.getTrainEmpID());
      needModifyPO.setTrainEffect(po.getTrainEffect());
      needModifyPO.setBeginDate(po.getBeginDate());
      needModifyPO.setEndDate(po.getEndDate());
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
      this.session.delete("from com.js.oa.hr.personnelmanager.po.TrainRecordPO po  where po.id in(" + 
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
}
