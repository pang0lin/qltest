package com.js.oa.hr.officemanager.bean;

import com.js.oa.hr.officemanager.po.PostTitlePO;
import com.js.util.hibernate.HibernateBase;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class PostTitleEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Integer add(PostTitlePO postTitlePO) throws Exception {
    int result = 2;
    begin();
    String series = postTitlePO.getPostTitleSeries();
    try {
      result = Integer.parseInt(this.session.createQuery("select count(*) from com.js.oa.hr.officemanager.po.PostTitlePO po where po.postTitleSeries='" + postTitlePO.getPostTitleSeries() + "' and po.postTitle='" + postTitlePO.getPostTitle() + "' and po.domainId=" + postTitlePO.getDomainId()).iterate().next().toString());
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      System.out.println(result);
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    System.out.println(result);
    this.session.close();
    this.session = null;
    this.transaction = null;
    return new Integer(result);
  }
  
  public Boolean del(String[] id) throws Exception {
    Boolean result = Boolean.FALSE;
    String idbuf = "";
    for (int i = 0; i < id.length; i++)
      idbuf = String.valueOf(idbuf) + id[i] + ","; 
    begin();
    try {
      this.session.delete("from com.js.oa.hr.officemanager.po.PostTitlePO po where  po.id in (" + idbuf.substring(0, idbuf.length() - 1) + ")");
      this.session.flush();
      result = Boolean.TRUE;
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public List getPostTitle(String postTitleSeries) throws Exception {
    begin();
    List list = null;
    try {
      list = this.session.createQuery("select a.postTitle from com.js.oa.hr.officemanager.po.PostTitlePO a where a.postTitleSeries='" + postTitleSeries + "'").list();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
}
