package com.js.oa.crm.bean;

import com.js.oa.crm.util.JDBCManager;
import com.js.util.hibernate.HibernateBase;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;

public class CstEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public void create(Object object) throws Exception {
    begin();
    try {
      this.session.save(object);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
  }
  
  public void remove(String table, String id) throws Exception {
    try {
      JDBCManager.removeByTableAndId(table, id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } 
  }
  
  public Object getSingle(Class clazz, String id) throws Exception {
    begin();
    Object obj = null;
    try {
      Query query = this.session.createQuery("from " + clazz.getName() + " cst where cst.id=" + id);
      obj = query.uniqueResult();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return obj;
  }
  
  public void update(Object object) throws Exception {
    begin();
    try {
      this.session.update(object);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
  }
  
  public List getObjList(Class clazz, String flag) throws Exception {
    begin();
    List objList = null;
    try {
      String sql = " from " + clazz.getName() + " as po ";
      if (flag != null && !flag.equals(""))
        sql = String.valueOf(sql) + " where po.type=" + flag; 
      Query query = this.session.createQuery(sql);
      objList = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return objList;
  }
  
  public List getObjList(Class clazz, String where, String other) throws Exception {
    begin();
    List objList = null;
    try {
      String sql = " from " + clazz.getName() + " as po ";
      if (where != null && !where.equals(""))
        sql = String.valueOf(sql) + where; 
      Query query = this.session.createQuery(sql);
      objList = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return objList;
  }
  
  public List getCstListById(String id) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      String sql = "select po.id,po.CName,po.CType,po.CTele,po.CEmail,";
      sql = String.valueOf(sql) + " po.CMobile,po.cstState,po.chargeUser,po.cstStateName,";
      sql = String.valueOf(sql) + " po.acceptUser,po.CType from Cst po where po.cstState=" + id;
      list = this.session.createQuery(sql).list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List getQueryCstByName(String name) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      String sql = "select po.id,po.CName,po.CType,po.CTele,po.CEmail,";
      sql = String.valueOf(sql) + " po.CMobile,po.cstState,po.chargeUser,po.cstStateName,";
      sql = String.valueOf(sql) + " po.acceptUser,po.CType from Cst po where po.CName like '%" + name + "%'";
      list = this.session.createQuery(sql).list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List getCstSellListById(String id) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      String sql = "select po.id,po.proName,po.cstName,po.cstId,po.compactState,";
      sql = String.valueOf(sql) + " po.chargeUser,po.chargeUserId,po.chargeOrg,po.chargeOrgId,";
      sql = String.valueOf(sql) + " po.floowUser,po.floowUserId from CstSell po where po.productId=" + id;
      list = this.session.createQuery(sql).list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List getQueryCstSellByName(String name) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      String sql = "select po.id,po.proName,po.cstName,po.cstId,po.compactState,";
      sql = String.valueOf(sql) + " po.chargeUser,po.chargeUserId,po.chargeOrg,po.chargeOrgId,";
      sql = String.valueOf(sql) + " po.floowUser,po.floowUserId from CstSell po where po.proName like '%" + name + "%'";
      list = this.session.createQuery(sql).list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List exportXSl(String view, String from, String where) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      list = this.session.createQuery("select " + view + " from " + from + where).list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
}
