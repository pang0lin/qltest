package com.js.oa.module.bean;

import com.js.oa.module.po.SystemMenuPO;
import com.js.util.hibernate.HibernateBase;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;

public class ModuleMenuAllEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Long saveOriginalMenuSet(SystemMenuPO po) throws HibernateException, Exception {
    Long retNo = new Long(0L);
    begin();
    try {
      retNo = (Long)this.session.save(po);
      po.setLeftURL("/jsoa/ModuleMenuAction.do?menuid=" + 
          retNo);
      po.setMenuIdString(retNo.toString());
      this.session.flush();
    } catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return retNo;
  }
  
  public List getOriginalMenuSet(String domainId, String menuId) throws HibernateException, Exception {
    List list = null;
    begin();
    try {
      list = this.session.createQuery(
          "select po.menuId, po.menuName, po.menuLevel, po.inUse, po.isSystemInit, po.menuCode  from com.js.oa.module.po.SystemMenuPO po  where 1=1 and po.domainId = " + 
          
          domainId + ((
          menuId != null && menuId.length() > 0) ? (
          " and po.menuId = " + menuId) : " ") + 
          " order by po.menuId")
        .list();
    } catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return list;
  }
  
  public SystemMenuPO loadMneuSet(String menuId) throws HibernateException, Exception {
    SystemMenuPO po = null;
    begin();
    try {
      List<SystemMenuPO> list = 
        this.session.createQuery(" from com.js.oa.module.po.SystemMenuPO po where po.menuId = " + 
          menuId).list();
      if (list.size() > 0)
        po = list.get(0); 
    } catch (Exception ex) {
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return po;
  }
  
  public boolean updateOriginalMenuSet(SystemMenuPO po) throws HibernateException {
    boolean retFlag = false;
    begin();
    try {
      this.session.update(po);
      this.session.flush();
    } catch (HibernateException ex) {
      ex.printStackTrace();
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return retFlag;
  }
  
  public boolean delBatchMenuSet(String domainId, String menuId) throws HibernateException {
    boolean flag = false;
    SystemMenuPO po = null;
    begin();
    try {
      this.session.delete(" from com.js.oa.module.po.SystemMenuPO po where po.domainId =" + 
          domainId + 
          " and po.isSystemInit < 1 and po.menuId in (" + 
          menuId + ")");
      this.session.flush();
      flag = true;
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return flag;
  }
  
  public boolean delAllCustomizeMenuSet(String domainId) throws HibernateException {
    boolean flag = false;
    SystemMenuPO po = null;
    begin();
    try {
      this.session.delete(" from com.js.oa.module.po.SystemMenuPO po where po.domainId =" + 
          domainId + " and po.isSystemInit < 1 ");
      this.session.flush();
      flag = true;
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return flag;
  }
  
  public List getAllMenuSet(String domainId) throws HibernateException {
    List list = null;
    begin();
    try {
      list = this.session.createQuery(" select po.menuId, po.menuName from com.js.oa.module.po.SystemMenuPO po where po.domainId = " + 
          domainId).list();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return list;
  }
  
  public Integer checkSubMenuExists(String menuSetId) {
    return null;
  }
  
  public List loadMneuSetByCode(String menuCode, String domainId) throws HibernateException {
    List list = null;
    begin();
    try {
      list = this.session.createQuery(" select po.menuId, po.menuName from com.js.oa.module.po.SystemMenuPO po where po.domainId = " + 
          domainId + " and po.menuCode = '" + 
          menuCode + "'").list();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return list;
  }
}
