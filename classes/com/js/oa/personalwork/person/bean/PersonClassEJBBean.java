package com.js.oa.personalwork.person.bean;

import com.js.oa.personalwork.person.po.PersonClassPO;
import com.js.util.hibernate.HibernateBase;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;

public class PersonClassEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public void delBatch(String ids, String userId, String classType) throws Exception {
    begin();
    try {
      if (ids != null && !"".equals(ids)) {
        if ("0".equals(classType))
          this.session.delete(" from com.js.oa.personalwork.person.po.PersonClassPO po where po.classType=0 and  po.id in (" + 
              ids.substring(0, ids.length() - 1) + ")"); 
        if ("1".equals(classType))
          this.session.delete(" from com.js.oa.personalwork.person.po.PersonClassPO po where po.classType=1 and  po.id in (" + 
              ids.substring(0, ids.length() - 1) + ")"); 
      } 
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
  
  public void delAll(String userType, String paraWhere) throws Exception {
    begin();
    try {
      if ("0".equals(userType)) {
        this.session.delete("from com.js.oa.personalwork.person.po.PersonClassPO po where po.classType=0 and po.empId = " + 
            paraWhere);
      } else {
        this.session.delete("from com.js.oa.personalwork.person.po.PersonClassPO po where po.classType=1 and  " + paraWhere);
      } 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception(e.getMessage());
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void add(String className, String classDescribe, String userId, String userType, String id, String name, String action, String classType, String orgId, String domainId) throws Exception {
    PersonClassPO po = new PersonClassPO();
    begin();
    try {
      if (!hasPersonClass(userId, userType, id, name, action, domainId)) {
        po.setClassName(className);
        po.setClassDescribe(classDescribe);
        po.setEmpId(Long.parseLong(userId));
        po.setClassType(Byte.parseByte(classType));
        po.setCreatedOrg(Long.parseLong(orgId));
        po.setDomainId(domainId);
        this.session.save(po);
        this.session.flush();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception(e.getMessage());
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  private boolean hasPersonClass(String userId, String userType, String id, String name, String action, String domainId) {
    try {
      List list = new ArrayList();
      if (userType.equals("0")) {
        if ("update".equals(action))
          list = this.session.createQuery("select po from com.js.oa.personalwork.person.po.PersonClassPO po where po.className='" + name + "' and po.classType=0 and po.domainId=(select a.domainId from com.js.oa.personalwork.person.po.PersonClassPO a where a.id=" + id + ") and po.empId=" + userId + " and po.id<>" + id).list(); 
        if ("add".equals(action))
          list = this.session.createQuery("select po from com.js.oa.personalwork.person.po.PersonClassPO po where po.className='" + name + "' and po.classType=0 and po.domainId=" + domainId + " and po.empId=" + userId).list(); 
      } 
      if (userType.equals("1")) {
        if ("update".equals(action))
          list = this.session.createQuery("select po from com.js.oa.personalwork.person.po.PersonClassPO po where po.className = '" + name + "' and po.domainId=(select a.domainId from com.js.oa.personalwork.person.po.PersonClassPO a where a.id=" + id + ") and po.classType=1 and po.id<>" + id).list(); 
        if ("add".equals(action))
          list = this.session.createQuery("select po from com.js.oa.personalwork.person.po.PersonClassPO po where po.className = '" + name + "' and po.domainId=" + domainId + " and po.classType=1 ").list(); 
      } 
      if (list.size() > 0)
        return true; 
    } catch (Exception e) {
      e.printStackTrace();
      return true;
    } 
    return false;
  }
  
  public Integer update(String userId, String userType, String id, String name, String action, String className, String classDescribe) throws Exception {
    Integer result = Integer.valueOf("0");
    try {
      begin();
      if (hasPersonClass(userId, userType, id, name, action, "")) {
        result = Integer.valueOf("-1");
      } else {
        PersonClassPO po = (PersonClassPO)this.session.load(PersonClassPO.class, new Long(id));
        po.setClassName(className);
        po.setClassDescribe(classDescribe);
        this.session.update(po);
        this.session.flush();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception(e.getMessage());
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public PersonClassPO load(String id) throws Exception {
    PersonClassPO po = null;
    begin();
    try {
      po = (PersonClassPO)this.session.load(PersonClassPO.class, new Long(id));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return po;
  }
  
  public Boolean hasSameClassName(Long userId, String className, String domainId, String classType) throws HibernateException {
    Boolean retFlg = Boolean.FALSE;
    begin();
    String where = "";
    if ("0".equals(classType))
      where = " and po.empId=" + userId; 
    try {
      List list = this.session.createQuery("from com.js.oa.personalwork.person.po.PersonClassPO po where po.classType='" + classType + "' and po.className = '" + className + "' and po.domainId = " + domainId + where).list();
      if (list != null && list.size() > 0)
        retFlg = Boolean.TRUE; 
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return retFlg;
  }
  
  public PersonClassPO findPersonClassPO(Long userId, String className, byte classType, String orgId) throws HibernateException {
    PersonClassPO personClassPO = null;
    begin();
    try {
      List<PersonClassPO> list = this.session.createQuery("from com.js.oa.personalwork.person.po.PersonClassPO po where po.classType=" + 
          classType + " and po.className = '" + className + "' and po.empId=" + userId.toString() + 
          " and po.domainId = '0'").list();
      if (!list.isEmpty()) {
        personClassPO = list.get(0);
      } else {
        list = this.session.createQuery("from com.js.oa.personalwork.person.po.PersonClassPO po where po.classType=" + classType + " and po.className = '默认' and po.domainId = '0'").list();
        if (!list.isEmpty()) {
          personClassPO = list.get(0);
        } else {
          PersonClassPO po = new PersonClassPO();
          po.setClassName("默认");
          po.setClassDescribe("默认分类");
          po.setClassType(classType);
          po.setCreatedOrg(Long.parseLong(orgId));
          po.setDomainId("0");
          Serializable key = this.session.save(po);
          this.session.flush();
          personClassPO = (PersonClassPO)this.session.load(PersonClassPO.class, key);
        } 
      } 
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return personClassPO;
  }
  
  public long addper(PersonClassPO personClassPO) throws HibernateException {
    long personClassPOid;
    begin();
    try {
      personClassPOid = ((Long)this.session.save(personClassPO)).longValue();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return personClassPOid;
  }
}
