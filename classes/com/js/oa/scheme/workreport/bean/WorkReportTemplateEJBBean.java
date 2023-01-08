package com.js.oa.scheme.workreport.bean;

import com.js.oa.scheme.workreport.po.WorkReportTemplatePO;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.StringSplit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class WorkReportTemplateEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public void delBatch(String ids) throws Exception {
    try {
      begin();
      if (ids != null && !ids.equals(""))
        this.session.delete(" from com.js.oa.scheme.workreport.po.WorkReportTemplatePO po where  po.id in (" + 
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
  
  public void add(String userId, String orgId, String templateName, String action, String userName, String templateDescript, String userOrgGroup, String templateUseRange, String templateContent, Long domainId) throws Exception {
    try {
      begin();
      WorkReportTemplatePO po = new WorkReportTemplatePO();
      po.setTemplateContent(templateContent);
      po.setTemplateDescript(templateDescript);
      po.setTemplateName(templateName);
      po.setTemplateUseRange(templateUseRange);
      po.setCreatedEmp(Long.parseLong(userId));
      po.setCreatedOrg(Long.parseLong(orgId));
      po.setCreatedEmpName(userName);
      po.setTemplateUseEmp(StringSplit.splitWith(userOrgGroup, "$", "*@"));
      po.setTemplateUseOrg(StringSplit.splitWith(userOrgGroup, "*", "$@"));
      po.setTemplateUseGroup(StringSplit.splitWith(userOrgGroup, "@", "*$"));
      po.setTemplateDomainId(domainId.longValue());
      this.session.save(po);
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
  
  public Boolean hasWorkReportTemplate(String id, String templateName, String action, Long domainId) throws Exception {
    boolean result = false;
    begin();
    try {
      List list = new ArrayList();
      if ("update".equals(action))
        list = this.session.createQuery("select po.id from com.js.oa.scheme.workreport.po.WorkReportTemplatePO po where  po.templateName = '" + 
            templateName + "' and po.id<>" + id + (
            (domainId != null) ? (" and po.templateDomainId = " + domainId) : "")).list(); 
      if ("add".equals(action))
        list = this.session.createQuery("select po.id from com.js.oa.scheme.workreport.po.WorkReportTemplatePO po where  po.templateName = '" + 
            templateName + "'" + (
            (domainId != null) ? (" and po.templateDomainId = " + domainId) : "")).list(); 
      if (list.size() > 0)
        result = true; 
    } catch (Exception e) {
      result = true;
      throw e;
    } finally {
      this.session.close();
    } 
    return new Boolean(result);
  }
  
  public void update(String id, String templateName, String action, String templateDescript, String userOrgGroup, String templateUseRange, String templateContent, Long domainId) throws Exception {
    try {
      begin();
      WorkReportTemplatePO po = (WorkReportTemplatePO)this.session.load(
          WorkReportTemplatePO.class, new Long(id));
      po.setTemplateDescript(templateDescript);
      po.setTemplateName(templateName);
      po.setTemplateUseRange(templateUseRange);
      po.setTemplateUseEmp(StringSplit.splitWith(userOrgGroup, "$", 
            "*@"));
      po.setTemplateUseOrg(StringSplit.splitWith(userOrgGroup, "*", 
            "$@"));
      po.setTemplateUseGroup(StringSplit.splitWith(userOrgGroup, "@", 
            "*$"));
      po.setTemplateContent(templateContent);
      this.session.update(po);
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
  
  public Map load(String id, Long domainId) throws Exception {
    Map<Object, Object> result = new HashMap<Object, Object>();
    try {
      begin();
      WorkReportTemplatePO po = (WorkReportTemplatePO)this.session.load(WorkReportTemplatePO.class, new Long(id));
      result.put("templateDescript", po.getTemplateDescript());
      result.put("templateName", po.getTemplateName());
      result.put("templateUseRange", po.getTemplateUseRange());
      result.put("userOrgGroup", String.valueOf(po.getTemplateUseEmp()) + po.getTemplateUseGroup() + po.getTemplateUseOrg());
      result.put("templateContent", po.getTemplateContent());
    } catch (Exception e) {
      e.printStackTrace();
      this.transaction.rollback();
      throw e;
    } finally {}
    this.session.close();
    this.session = null;
    this.transaction = null;
    return result;
  }
}
