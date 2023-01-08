package com.js.oa.personalwork.netaddress.bean;

import com.js.oa.personalwork.netaddress.po.AddressClassPO;
import com.js.util.hibernate.HibernateBase;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class AddressClassEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public void delBatch(String ids, String userId) throws Exception {
    begin();
    try {
      if (ids != null && !ids.equals(""))
        this.session.delete(
            " from com.js.oa.personalwork.netaddress.po.AddressClassPO po where po.id in (" + 
            ids.substring(0, ids.length() - 1) + ") or  po.id in (" + 
            ids.substring(0, ids.length() - 1) + ") and po.empId = " + userId); 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void delAll(String userId) throws Exception {
    begin();
    try {
      this.session.delete(
          "from com.js.oa.personalwork.netaddress.po.AddressClassPO po where po.empId = " + userId);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public Boolean add(String className, Integer classIsShare, String action, String editId, String userId, String domainId) throws Exception {
    AddressClassPO po = new AddressClassPO();
    begin();
    try {
      if (hasAddressClass(className, userId, action, editId, domainId))
        return Boolean.FALSE; 
      po.setClassName(className);
      po.setClassIsShare(classIsShare.intValue());
      po.setEmpId(Long.parseLong(userId));
      po.setDomainId(domainId);
      this.session.save(po);
      this.session.flush();
      return Boolean.TRUE;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  private boolean hasAddressClass(String name, String userId, String action, String editId, String domainId) {
    try {
      List list = new ArrayList();
      if ("add".equals(action))
        list = this.session.createQuery("select po.id from com.js.oa.personalwork.netaddress.po.AddressClassPO po where po.empId=" + userId + " and po.domainId=" + domainId + " and po.className = '" + name + "' or po.domainId=" + domainId + " and po.className = '" + name + "'").list(); 
      if ("update".equals(action))
        list = this.session.createQuery("select po.id from com.js.oa.personalwork.netaddress.po.AddressClassPO po where po.empId=" + userId + " and po.domainId=" + domainId + " and po.className = '" + name + "' and po.id<>" + editId).list(); 
      if (list.size() > 0)
        return true; 
    } catch (Exception e) {
      e.printStackTrace();
      return true;
    } 
    return false;
  }
  
  public Boolean update(String className, Integer classIsShare, String action, String editId, String userId) throws Exception {
    begin();
    try {
      AddressClassPO po = (AddressClassPO)this.session.load(AddressClassPO.class, new Long(editId));
      if (hasAddressClass(className, userId, action, editId, po.getDomainId()))
        return Boolean.FALSE; 
      if (classIsShare == null) {
        po.setClassIsShare(0);
      } else {
        po.setClassIsShare(classIsShare.intValue());
      } 
      po.setClassName(className);
      this.session.update(po);
      this.session.flush();
      return Boolean.TRUE;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public AddressClassPO load(String id) throws Exception {
    AddressClassPO addressClassPO = new AddressClassPO();
    String className = "";
    begin();
    try {
      AddressClassPO po = (AddressClassPO)this.session.load(AddressClassPO.class, new Long(id));
      className = po.getClassName();
      addressClassPO.setClassName(po.getClassName());
      addressClassPO.setClassIsShare(po.getClassIsShare());
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return addressClassPO;
  }
}
