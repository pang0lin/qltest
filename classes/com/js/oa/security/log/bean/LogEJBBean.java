package com.js.oa.security.log.bean;

import com.js.oa.security.log.po.LogPO;
import com.js.util.hibernate.HibernateBase;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class LogEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Boolean log(String userId, String userName, String userOrgName, String moduleSerial, String subModule, Date oprStartTime, Date oprEndTime, String oprType, String oprContent, String logIP, String domainId) throws Exception {
    Boolean result = new Boolean(false);
    begin();
    String canLog = "";
    try {
      Iterator<Object[]> it = this.session.createQuery("select po.moduleLog,po.moduleName,po.parentSerial from com.js.oa.security.log.po.LogModulePO po where po.moduleSerial='" + moduleSerial + "' and po.domainId=" + domainId).iterate();
      if (it.hasNext()) {
        Object[] obj = it.next();
        canLog = obj[0].toString();
        subModule = obj[1].toString();
        moduleSerial = obj[2].toString();
      } 
      if ("1".equals(canLog)) {
        LogPO po = new LogPO();
        po.setEmpId(userId);
        po.setEmpName(userName);
        po.setEmpOrgName(userOrgName);
        po.setModuleSerial(moduleSerial);
        po.setOprSubModule(subModule);
        po.setOprType(oprType);
        po.setOprStartTime(oprStartTime);
        po.setOprEndTime(oprEndTime);
        po.setLogIP(logIP);
        po.setDomainId(domainId);
        if (oprContent.length() > 249)
          oprContent = String.valueOf(oprContent.substring(0, 245)) + "..."; 
        po.setOprContent(oprContent);
        this.session.save(po);
        this.session.flush();
        result = Boolean.TRUE;
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public List export(String where) throws Exception {
    List list = new ArrayList();
    String[] tableName = where.split("&");
    begin();
    try {
      list = this.session.createQuery("select po.logId,po.oprStartTime,po.empName,po.empOrgName,po.oprSubModule,po.oprContent,po.oprType,po.logIP,eo.userAccounts from com.js.oa.security.log.po." + 
          
          tableName[0].toString() + " po, com.js.system.vo.usermanager.EmployeeVO eo " + 
          "where po.empId=eo.empId and " + tableName[1].toString().replaceAll("where", " ")).list();
    } catch (Exception ex) {
      throw ex;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List moduleList(String domainId) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      list = this.session.createQuery("select po.moduleSerial,po.moduleName from com.js.oa.security.log.po.LogModulePO po where po.moduleLog=1 and po.moduleLevel=0 and po.domainId=" + domainId).list();
    } catch (Exception ex) {
      throw ex;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public void deleteLog(String where, String tabelNameString) throws Exception {
    begin();
    try {
      this.session.delete("from com.js.oa.security.log.po." + tabelNameString + " po " + where);
      this.session.flush();
    } catch (Exception ex) {
      throw ex;
    } finally {
      this.session.close();
    } 
  }
}
