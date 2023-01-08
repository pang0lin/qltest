package com.js.oa.weixin.common.bean;

import com.js.util.hibernate.HibernateBase;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class WeiXinCommonBean extends HibernateBase implements SessionBean {
  public void ejbActivate() throws EJBException, RemoteException {}
  
  public void ejbPassivate() throws EJBException, RemoteException {}
  
  public void ejbRemove() throws EJBException, RemoteException {}
  
  public void setSessionContext(SessionContext arg0) throws EJBException, RemoteException {}
  
  public List getOrgList(String domainId, String range, String sele, String pId) throws Exception {
    List orgList = new ArrayList();
    String sql = "";
    if (range != null && !range.equals("") && range.indexOf("*") >= 0) {
      range = range.replaceAll("\\*", ",");
      if (range.indexOf(",,") >= 0) {
        range = range.replaceAll(",,", ",");
      } else {
        range = range.replaceAll(",", "");
      } 
    } 
    try {
      begin();
      if (range == null || "".equals(range) || "0".equals(range)) {
        if (!sele.equals("1")) {
          sql = "from OrganizationVO org where org.orgStatus<>1 and org.orgStatus<>4 and org.orgParentOrgId<>-1 and org.domainId=" + 
            domainId + " and orgParentOrgId=" + pId + " order by org.orgIdString";
        } else {
          sql = "from OrganizationVO org where org.orgParentOrgId<>-1 and org.orgStatus<>4 and org.domainId=" + 
            domainId + " and orgParentOrgId=" + pId + " order by org.orgIdString";
        } 
      } else if (!sele.equals("1")) {
        sql = "from OrganizationVO org where (org.orgId in (" + range + ") ";
        for (int i = 0; i < (range.split(",")).length; i++)
          sql = String.valueOf(sql) + " or org.orgIdString like '%$" + range.split(",")[i] + "$%' "; 
        sql = String.valueOf(sql) + ") and org.orgStatus<>1 and org.orgStatus<>4 and org.orgParentOrgId<>-1 and org.domainId=" + 
          domainId + " and orgParentOrgId=" + pId + " order by org.orgIdString";
      } else {
        sql = "from OrganizationVO org where (org.orgId in(" + range + ") ";
        for (int i = 0; i < (range.split(",")).length; i++)
          sql = String.valueOf(sql) + " or org.orgIdString like '%$" + range.split(",")[i] + "$%' "; 
        sql = String.valueOf(sql) + ") and org.orgParentOrgId<>-1 and org.orgStatus<>4 and org.domainId=" + 
          domainId + " and orgParentOrgId=" + pId + " order by org.orgIdString";
      } 
      orgList = this.session.createQuery(sql).list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return orgList;
  }
  
  public List getPrivateList(String userId, String domainId) throws Exception {
    List privateList = new ArrayList();
    try {
      begin();
      String sql = "from PersonClassPO pcp where pcp.classType=0 and pcp.domainId=" + domainId + " and pcp.empId=" + userId;
      privateList = this.session.createQuery(sql).list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return privateList;
  }
  
  public List getPublicList(String userId, String domainId) throws Exception {
    List orgList = new ArrayList();
    try {
      begin();
      String sql = "from PersonClassPO pcp where pcp.classType=1 and pcp.domainId=" + domainId;
      orgList = this.session.createQuery(sql).list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return orgList;
  }
}
