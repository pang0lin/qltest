package com.js.oa.info.templatemanager.bean;

import com.js.oa.info.templatemanager.po.InformationTemplatePO;
import com.js.util.hibernate.HibernateBase;
import java.util.Iterator;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;

public class TemplateEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Boolean add(InformationTemplatePO templatePO) throws Exception {
    begin();
    Boolean success = new Boolean(false);
    try {
      Query query = this.session.createQuery("select a.id from com.js.oa.info.templatemanager.po.InformationTemplatePO a where a.templateTitle='" + 
          templatePO.getTemplateTitle() + "' and a.domainId=" + templatePO.getDomainId());
      if (query.iterate().hasNext()) {
        success = Boolean.FALSE;
      } else {
        this.session.save(templatePO);
        this.session.flush();
        success = Boolean.TRUE;
      } 
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return success;
  }
  
  public Boolean delBatch(String ids) throws Exception {
    begin();
    try {
      if (ids != null && !ids.equals("")) {
        this.session.delete("from  com.js.oa.info.templatemanager.po.InformationTemplatePO po where po.id in (" + 
            ids.substring(0, ids.length() - 1) + ")");
        this.session.flush();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return Boolean.TRUE;
  }
  
  public void delAll(String wherePara) throws Exception {
    begin();
    try {
      this.session.delete("from com.js.oa.info.templatemanager.po.InformationTemplatePO po where " + wherePara);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
  }
  
  public InformationTemplatePO load(String id) throws Exception {
    InformationTemplatePO templatePO = new InformationTemplatePO();
    begin();
    try {
      templatePO = (InformationTemplatePO)this.session.load(InformationTemplatePO.class, new Long(id));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return templatePO;
  }
  
  public Boolean update(InformationTemplatePO templatePO) throws Exception {
    begin();
    Boolean success = new Boolean(false);
    try {
      Query query = this.session.createQuery("select a.id from com.js.oa.info.templatemanager.po.InformationTemplatePO a where a.templateTitle='" + 
          templatePO.getTemplateTitle() + "' and a.domainId=" + templatePO.getDomainId() + 
          " and a.id<>" + templatePO.getId());
      if (!query.iterate().hasNext()) {
        query = this.session.createQuery("select a.createdEmp,a.createdOrg from com.js.oa.info.templatemanager.po.InformationTemplatePO a where a.id=" + 
            templatePO.getId());
        Iterator<Object[]> iter = query.iterate();
        long createdEmp = 0L, createdOrg = 0L;
        if (iter.hasNext()) {
          Object[] obj = iter.next();
          createdEmp = Long.parseLong(obj[0].toString());
          createdOrg = Long.parseLong(obj[1].toString());
        } 
        templatePO.setCreatedEmp(createdEmp);
        templatePO.setCreatedOrg(createdOrg);
        this.session.update(templatePO);
        this.session.flush();
        success = Boolean.TRUE;
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return success;
  }
  
  public List getTemplate() throws Exception {
    begin();
    List list = null;
    try {
      Query query = this.session.createQuery("select aaa.id,aaa.templateTitle from com.js.oa.info.templatemanager.po.InformationTemplatePO aaa");
      list = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public String getTemplateContent(String templateId) throws Exception {
    begin();
    String str = "";
    try {
      Query query = this.session.createQuery("select aaa.templateContent from com.js.oa.info.templatemanager.po.InformationTemplatePO aaa where aaa.id = " + templateId);
      List<String> list = query.list();
      str = list.get(0);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return str;
  }
  
  public List getAvailableTemplateByUser(String wherePara, String domainId, String module) throws Exception {
    begin();
    List list = null;
    try {
      Query query = this.session.createQuery("select aaa.id,aaa.templateTitle from com.js.oa.info.templatemanager.po.InformationTemplatePO aaa where (" + wherePara + ") and aaa.module=" + module + " and aaa.domainId=" + domainId);
      list = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public String judgeName(String domainId, String templateName, String templateId) throws Exception {
    String rs = "0";
    try {
      begin();
      StringBuffer sql = new StringBuffer("select po.id from com.js.oa.info.templatemanager.po.InformationTemplatePO po where po.domainId=");
      sql.append(domainId).append(" and po.templateTitle='").append(templateName).append("'");
      if (!"".equals(templateId))
        sql.append(" and po.id<>").append(templateId); 
      Iterator it = this.session.createQuery(sql.toString()).iterate();
      if (it.hasNext())
        rs = "1"; 
      this.session.close();
    } catch (Exception ex) {
      this.session.close();
      ex.printStackTrace();
      throw ex;
    } 
    return rs;
  }
}
