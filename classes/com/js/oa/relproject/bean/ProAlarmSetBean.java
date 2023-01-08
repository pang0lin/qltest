package com.js.oa.relproject.bean;

import com.js.oa.relproject.po.ProAlarmSet;
import com.js.util.hibernate.HibernateBase;
import net.sf.hibernate.HibernateException;

public class ProAlarmSetBean extends HibernateBase {
  public Long saveAlarmSet(ProAlarmSet po) throws HibernateException {
    Long auditOrgGroupId = null;
    begin();
    try {
      auditOrgGroupId = (Long)this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return auditOrgGroupId;
  }
  
  public ProAlarmSet loadAlarmSet(Long noteId) {
    ProAlarmSet po = null;
    try {
      begin();
      po = (ProAlarmSet)this.session.load(ProAlarmSet.class, noteId);
      this.session.close();
    } catch (Exception ex) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return po;
  }
  
  public boolean updateAlarmSet(ProAlarmSet po) throws Exception {
    begin();
    boolean re = true;
    try {
      this.session.update(po);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      re = false;
    } finally {
      this.session.close();
    } 
    return re;
  }
  
  public boolean deleteAlarmSet(String ids) throws HibernateException {
    begin();
    boolean re = true;
    try {
      if (ids.endsWith(","))
        ids = ids.substring(0, ids.length() - 1); 
      this.session.delete("from com.js.oa.relproject.po.ProAlarmSet po where po.alarmId in (" + ids + ")");
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      re = false;
    } finally {
      this.session.close();
    } 
    return re;
  }
}
