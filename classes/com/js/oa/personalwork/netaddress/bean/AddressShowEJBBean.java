package com.js.oa.personalwork.netaddress.bean;

import com.js.oa.personalwork.netaddress.po.AddressPO;
import com.js.util.hibernate.HibernateBase;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class AddressShowEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public List getAllAddressShow(String empId) throws Exception {
    List list = null;
    try {
      begin();
      list = this.session.createQuery("from com.js.oa.personalwork.netaddress.po.AddressShowPO as addressShowPO where addressShowPO.empId='" + 
          empId + "'").list();
      this.session.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public AddressPO getAddressPO(String addressID) throws Exception {
    AddressPO addressPO = null;
    List<AddressPO> list = null;
    try {
      begin();
      list = this.session
        .createQuery(
          "from com.js.oa.personalwork.netaddress.po.AddressPO as addressPO where addressPO.id='" + 
          addressID + "'").list();
      if (list.size() > 0)
        addressPO = list.get(0); 
      this.session.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return addressPO;
  }
}
