package com.js.oa.routine.resource.bean;

import com.js.oa.routine.resource.po.TypeDefinePO;
import com.js.util.hibernate.HibernateBase;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;

public class TypeDefineEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public String save(TypeDefinePO po) throws Exception {
    String result = "true";
    try {
      begin();
      String sql = "select t.id from com.js.oa.routine.resource.po.TypeDefinePO t where t.typeDefineName= '" + po.getTypeDefineName() + "' and t.typeDefineMode='" + po.getTypeDefineMode() + "'";
      Query query1 = this.session.createQuery(sql);
      if (query1.iterate().hasNext()) {
        result = "false";
      } else {
        this.session.save(po);
        this.session.flush();
      } 
    } catch (Exception e) {
      result = "false";
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public TypeDefinePO load(Long id) throws Exception {
    TypeDefinePO po = new TypeDefinePO();
    try {
      begin();
      po = (TypeDefinePO)this.session.load(TypeDefinePO.class, id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return po;
  }
  
  public Boolean update(TypeDefinePO po, Long id) throws Exception {
    Boolean ret = Boolean.TRUE;
    try {
      begin();
      String sql = "select t.id from com.js.oa.routine.resource.po.TypeDefinePO t where t.typeDefineName= '" + po.getTypeDefineName() + "' and t.typeDefineMode='" + po.getTypeDefineMode() + "' and t.id <> " + id;
      Query query1 = this.session.createQuery(sql);
      if (query1.iterate().hasNext()) {
        ret = Boolean.FALSE;
      } else {
        TypeDefinePO updatePO = (TypeDefinePO)this.session.load(TypeDefinePO.class, id);
        updatePO.setTypeDefineMode(po.getTypeDefineMode());
        updatePO.setTypeDefineName(po.getTypeDefineName());
        this.session.update(updatePO);
        this.session.flush();
      } 
    } catch (Exception e) {
      ret = Boolean.FALSE;
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public List list(String mode, Long domainID) throws Exception {
    List list = null;
    try {
      begin();
      list = this.session.createQuery("select po.id,po.typeDefineName from  com.js.oa.routine.resource.po.TypeDefinePO po where po.typeDefineMode='" + 
          
          mode + "' and po.domainID=" + domainID).list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public Boolean delete(Long id) throws Exception {
    Boolean ret = Boolean.TRUE;
    try {
      begin();
      TypeDefinePO po = (TypeDefinePO)this.session.load(TypeDefinePO.class, id);
      this.session.delete(po);
      this.session.flush();
    } catch (Exception e) {
      ret = Boolean.FALSE;
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
}
