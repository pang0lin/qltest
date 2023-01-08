package com.js.oa.routine.resource.bean;

import com.js.oa.routine.resource.po.ProviderPO;
import com.js.util.hibernate.HibernateBase;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;

public class ProviderEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Boolean save(ProviderPO providerPO) throws Exception {
    Boolean flag = new Boolean(false);
    begin();
    try {
      Query query = this.session.createQuery(
          "select a.id from ProviderPO a where a.id ='" + 
          providerPO.getId() + "'");
      if (query.list().size() == 0) {
        this.session.save(providerPO);
        flag = Boolean.TRUE;
      } 
      this.session.flush();
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return flag;
  }
  
  public ProviderPO getModifyProvider(String providerId) throws Exception {
    begin();
    ProviderPO po = new ProviderPO();
    try {
      po = (ProviderPO)this.session.load(ProviderPO.class, 
          providerId.trim());
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return po;
  }
  
  public Boolean update(ProviderPO providerActionForm, String oldProviderId) throws Exception {
    Boolean flag = new Boolean(false);
    begin();
    try {
      if (oldProviderId.equals(providerActionForm.getId()) || 
        this.session.load(ProviderPO.class, providerActionForm.getId()) == null) {
        ProviderPO providerPO = (ProviderPO)this.session.load(ProviderPO.class, 
            oldProviderId);
        providerPO.setAccounts(providerActionForm.getAccounts());
        providerPO.setConsignmentAddress(providerActionForm
            .getConsignmentAddress());
        providerPO.setContacter(providerActionForm.getContacter());
        providerPO.setContactMethod(providerActionForm.getContactMethod());
        providerPO.setId(providerActionForm.getId());
        providerPO.setMakeOutInvoiceAddress(providerActionForm.getMakeOutInvoiceAddress());
        providerPO.setName(providerActionForm.getName());
        flag = Boolean.TRUE;
      } 
      this.session.flush();
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return flag;
  }
  
  public Boolean delete(String providerIdString) throws Exception {
    Boolean flag = new Boolean(false);
    begin();
    try {
      String[] tempId = providerIdString.split(",");
      StringBuffer hqlWhere = new StringBuffer();
      StringBuffer testWhere = new StringBuffer();
      testWhere.append(
          "select a.id from com.js.oa.routine.resource.po.PtMasterPO a where ");
      hqlWhere.append(
          "from com.js.oa.routine.resource.po.ProviderPO po where");
      if (tempId.length == 1) {
        testWhere.append(" a.ptSupp ='");
        testWhere.append(providerIdString);
        testWhere.append("'");
        Query query = this.session.createQuery(testWhere.toString());
        if (query.list().size() == 0) {
          hqlWhere.append(" po.id ='");
          hqlWhere.append(providerIdString);
          hqlWhere.append("'");
          this.session.delete(hqlWhere.toString());
          flag = Boolean.TRUE;
        } 
      } else {
        testWhere.append(" a.ptSupp in(");
        for (int i = 0; i < tempId.length; i++) {
          testWhere.append("'");
          testWhere.append(tempId[i]);
          testWhere.append("',");
        } 
        testWhere.append("'')");
        Query query = this.session.createQuery(testWhere.toString());
        if (query.list().size() == 0) {
          hqlWhere.append(" po.id in(");
          for (int j = 0; j < tempId.length; j++) {
            hqlWhere.append("'");
            hqlWhere.append(tempId[j]);
            hqlWhere.append("',");
          } 
          hqlWhere.append("'')");
          this.session.delete(hqlWhere.toString());
          flag = Boolean.TRUE;
        } 
      } 
      this.session.flush();
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return flag;
  }
}
