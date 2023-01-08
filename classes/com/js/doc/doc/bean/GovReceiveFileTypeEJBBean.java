package com.js.doc.doc.bean;

import com.js.doc.doc.po.GovReceiveFileTypePO;
import com.js.util.hibernate.HibernateBase;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class GovReceiveFileTypeEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public String govReceiveFileTypeAdd(GovReceiveFileTypePO po) throws Exception {
    begin();
    String info = null;
    try {
      Iterator iter = this.session.createQuery(
          "select po.id from com.js.doc.doc.po.GovReceiveFileTypePO po where po.domainId=" + 
          po.getDomainId() + " and po.receiveFileTypeName='" + po.getReceiveFileTypeName().trim() + "'").iterate();
      if (iter.hasNext()) {
        info = "same";
      } else {
        info = "successful";
        this.session.save(po);
        this.session.flush();
      } 
    } catch (Exception e) {
      System.out.println("---------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return info;
  }
  
  public String govReceiveFileTypeDelBatch(String ids) throws Exception {
    begin();
    String info = "no";
    try {
      if (ids != null && !ids.equals("")) {
        this.session.delete(
            " from com.js.doc.doc.po.GovReceiveFileTypePO po where po.id in (" + 
            ids.substring(0, ids.length() - 1) + ")");
        this.session.flush();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return info;
  }
  
  public String govReceiveFileTypeDel(String id) throws Exception {
    begin();
    String info = "no";
    try {
      this.session.delete(
          " from com.js.doc.doc.po.GovReceiveFileTypePO po where po.id='" + 
          id + "'");
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return info;
  }
  
  public String govReceiveFileTypeUpdate(GovReceiveFileTypePO paraPO) throws Exception {
    begin();
    String update_info = null;
    try {
      Iterator iter = this.session.createQuery(
          "select  po.id from com.js.doc.doc.po.GovReceiveFileTypePO po where po.domainId=" + 
          paraPO.getDomainId() + " and po.receiveFileTypeName='" + paraPO.getReceiveFileTypeName().trim() + "' and  po.id <>" + 
          paraPO.getId()).iterate();
      if (iter.hasNext()) {
        update_info = "same";
      } else {
        update_info = "successful";
        GovReceiveFileTypePO po = (GovReceiveFileTypePO)this.session.load(GovReceiveFileTypePO.class, paraPO.getId());
        po.setReceiveFileTypeName(paraPO.getReceiveFileTypeName());
        po.setUserName(paraPO.getUserName());
        po.setUserId(paraPO.getUserId());
        po.setOrgId(paraPO.getOrgId());
        po.setGroupId(paraPO.getGroupId());
        this.session.update(po);
        this.session.flush();
      } 
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return update_info;
  }
  
  public List govReceiveFileTypeModifylist(String id) throws Exception {
    begin();
    List list = new ArrayList();
    try {
      String where = " where po.id=" + id;
      list = this.session.createQuery(
          "select po from com.js.doc.doc.po.GovReceiveFileTypePO po" + 
          where)
        .list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List govReceiveFileTypeList() throws Exception {
    begin();
    List list = new ArrayList();
    try {
      list = this.session.createQuery(
          "select po.id,po.receiveFileTypeName from com.js.doc.doc.po.GovReceiveFileTypePO po")
        
        .list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List govReceiveFileTypeList(String scopeWhere) throws Exception {
    begin();
    List list = new ArrayList();
    try {
      list = this.session.createQuery(
          "select po.id,po.receiveFileTypeName from com.js.doc.doc.po.GovReceiveFileTypePO po where (" + 
          scopeWhere + ")").list();
    } catch (Exception e) {
      System.out.println("---------------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
}
