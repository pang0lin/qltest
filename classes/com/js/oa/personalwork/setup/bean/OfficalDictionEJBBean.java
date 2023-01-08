package com.js.oa.personalwork.setup.bean;

import com.js.oa.personalwork.setup.po.OfficalDictionPO;
import com.js.util.hibernate.HibernateBase;
import java.util.List;
import java.util.Vector;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class OfficalDictionEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Vector list(Long curUserId, Integer offset) throws Exception {
    return null;
  }
  
  public String add(OfficalDictionPO po, Long curUserId, String curUserName) throws Exception {
    String retString = "save";
    begin();
    try {
      if (repeatRecord(curUserId, po.getDictionContent(), po.getDomainId())) {
        retString = "repeat";
      } else {
        po.setEmpId(curUserId.longValue());
        po.setEmpName(curUserName);
        this.session.save(po);
        this.session.flush();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      retString = "nosave";
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return retString;
  }
  
  private boolean repeatRecord(Long curUserId, String name, String domainId) throws Exception {
    try {
      String sql = " select po.id from com.js.oa.personalwork.setup.po.OfficalDictionPO po where po.dictionContent = '" + 
        name + "' and (po.empId=" + curUserId.longValue() + 
        " or po.dictionIsShare=1) and po.domainId=" + domainId;
      List list = this.session.createQuery(sql).list();
      if (list.size() <= 0)
        return false; 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } 
    return true;
  }
  
  private boolean repeatRecord(Long curUserId, String name, Long editId, Byte isShare, String domainId) throws Exception {
    try {
      String sql = "";
      sql = " select po.id from com.js.oa.personalwork.setup.po.OfficalDictionPO po where po.dictionContent = '" + 
        name + "' and (po.empId=" + curUserId.longValue() + 
        " or po.dictionIsShare=1) and po.id<>" + editId.longValue() + " and po.domainId=" + domainId;
      List list = this.session.createQuery(sql).list();
      if (list.size() <= 0)
        return false; 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } 
    return true;
  }
  
  public String update(String content, Byte isShare, Long editId, Long curUserId, String curUserName) throws Exception {
    String retString = "save";
    begin();
    try {
      OfficalDictionPO po = (OfficalDictionPO)this.session.load(OfficalDictionPO.class, editId);
      if (repeatRecord(curUserId, content, editId, isShare, po.getDomainId())) {
        retString = "repeat";
      } else {
        po.setDictionContent(content);
        po.setDictionIsShare(isShare.byteValue());
        this.session.update(po);
        this.session.flush();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      retString = "nosave";
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return retString;
  }
  
  public void delAll(Long curUserId) throws Exception {
    begin();
    try {
      this.session.delete(
          "from com.js.oa.personalwork.setup.po.OfficalDictionPO po  where po.empId =" + 
          curUserId);
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
  
  public void delBatch(String ids) throws Exception {
    begin();
    try {
      this.session.delete(
          "from com.js.oa.personalwork.setup.po.OfficalDictionPO po  where po.id in (" + 
          ids.substring(0, ids.length() - 1) + ")");
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
  
  public OfficalDictionPO load(Long editId) throws Exception {
    OfficalDictionPO po = new OfficalDictionPO();
    begin();
    try {
      po = (OfficalDictionPO)this.session.load(OfficalDictionPO.class, editId);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return po;
  }
}
