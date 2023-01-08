package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.officemanager.po.DutyPO;
import com.js.oa.hr.personnelmanager.po.EmployeeChangePO;
import com.js.oa.hr.personnelmanager.po.EmployeeChangeTypePO;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;

public class EmpChangeEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public boolean addEmpChange(EmployeeChangePO empChangePO) throws Exception {
    boolean result = false;
    begin();
    try {
      this.session.save(empChangePO);
      this.session.flush();
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.executeUpdate("update org_organization_user set org_id=" + 
          empChangePO.getEmpChangeNewOrg() + " where emp_id=" + 
          empChangePO.getEmpChangeEmpId());
      Query selectEmpNameQuery = this.session.createQuery("SELECT po.orgManagerEmpId, po.orgManagerEmpName FROM com.js.system.vo.organizationmanager.OrganizationVO po WHERE po.orgId = " + 
          empChangePO.getEmpChangeNewOrg());
      List<Object[]> list = selectEmpNameQuery.list();
      Object[] orgManager = list.get(0);
      String orgManagerId = (orgManager[0] == null) ? "" : orgManager[0].toString();
      String orgManagerName = (orgManager[1] == null) ? "" : orgManager[1].toString();
      stmt.executeUpdate("UPDATE org_employee SET EMPLEADERID='" + orgManagerId + "', EMPLEADERNAME = '" + orgManagerName + "' WHERE emp_id = " + empChangePO.getEmpChangeEmpId());
      DutyPO dutyPO = (DutyPO)this.session.get(DutyPO.class, empChangePO.getEmpChangeNewDuty());
      if (dutyPO != null)
        stmt.executeUpdate("update org_employee set empduty='" + 
            dutyPO.getDutyName() + "' where emp_id=" + 
            empChangePO.getEmpChangeEmpId()); 
      result = true;
      stmt.close();
      conn.close();
    } catch (Exception e) {
      System.out.println("addEmpChangeEJB Exception:" + e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public boolean deleteEmpChange(Long empChangeId) throws Exception {
    boolean result = false;
    begin();
    try {
      EmployeeChangePO empChangePO = (EmployeeChangePO)this.session.load(
          EmployeeChangePO.class, 
          empChangeId);
      this.session.delete(empChangePO);
      this.session.flush();
      result = true;
    } catch (Exception e) {
      System.out.println("deleteEmpChangeEJB Exception:" + e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public boolean deleteBatchEmpChange(String empChangeIds) throws Exception {
    boolean result = false;
    begin();
    try {
      String[] idsArr = empChangeIds.split(",");
      for (int i = 0; i < idsArr.length; i++) {
        EmployeeChangePO empChangePO = (EmployeeChangePO)this.session.load(
            EmployeeChangePO.class, 
            Long.valueOf(idsArr[i]));
        this.session.delete(empChangePO);
      } 
      this.session.flush();
      result = true;
    } catch (Exception e) {
      System.out.println("deleteBatchEmpChangeEJB Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public EmployeeChangePO selectEmpChangeView(Long empChangeId) throws HibernateException {
    EmployeeChangePO empChangePO = null;
    try {
      begin();
      empChangePO = (EmployeeChangePO)this.session.load(EmployeeChangePO.class, 
          empChangeId);
    } catch (HibernateException e) {
      System.out.println("selectEmpChangeViewEJB Exception:" + e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return empChangePO;
  }
  
  public boolean updateEmpChange(EmployeeChangePO empChangePO) throws Exception {
    boolean result = false;
    begin();
    try {
      EmployeeChangePO employeeChange = (EmployeeChangePO)this.session.load(EmployeeChangePO.class, 
          empChangePO.getEmpChangeId());
      employeeChange.setEmpChangeId(empChangePO.getEmpChangeId());
      employeeChange.setEmpChangeDate(empChangePO.getEmpChangeDate());
      employeeChange.setEmpChangeEmpId(empChangePO.getEmpChangeEmpId());
      employeeChange.setEmpChangeOldOrg(empChangePO.getEmpChangeOldOrg());
      employeeChange.setEmpChangeNewOrg(empChangePO.getEmpChangeNewOrg());
      employeeChange.setEmpChangeOldDuty(empChangePO.getEmpChangeOldDuty());
      employeeChange.setEmpChangeNewDuty(empChangePO.getEmpChangeNewDuty());
      employeeChange.setEmpChangeChangeType(empChangePO
          .getEmpChangeChangeType());
      this.session.update(employeeChange);
      this.session.flush();
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.executeUpdate(
          "update org_organization_user set org_id=" + 
          empChangePO.getEmpChangeNewOrg() + " where emp_id=" + 
          empChangePO.getEmpChangeEmpId());
      Query selectEmpNameQuery = this.session.createQuery("SELECT po.orgManagerEmpId, po.orgManagerEmpName FROM com.js.system.vo.organizationmanager.OrganizationVO po WHERE po.orgId = " + 
          empChangePO.getEmpChangeNewOrg());
      List<Object[]> list = selectEmpNameQuery.list();
      Object[] orgManager = list.get(0);
      String orgManagerId = (orgManager[0] == null) ? "" : orgManager[0].toString();
      String orgManagerName = (orgManager[1] == null) ? "" : orgManager[1].toString();
      stmt.executeUpdate("UPDATE org_employee SET EMPLEADERID='" + orgManagerId + "', EMPLEADERNAME = '" + orgManagerName + "' WHERE emp_id = " + empChangePO.getEmpChangeEmpId());
      DutyPO dutyPO = (DutyPO)this.session.get(DutyPO.class, 
          empChangePO
          .getEmpChangeNewDuty());
      if (dutyPO != null)
        stmt.executeUpdate("update org_employee set empduty='" + 
            dutyPO.getDutyName() + "' where emp_id=" + 
            empChangePO.getEmpChangeEmpId()); 
      result = true;
      stmt.close();
      conn.close();
    } catch (Exception e) {
      System.out.println("updateEmpChangeEJB Exception:" + e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public String selectEmpName(Long empChangeEmpId) throws HibernateException {
    String empName = "";
    try {
      begin();
      Query selectEmpNameQuery = this.session.createQuery("select empPO.empName from com.js.system.vo.usermanager.EmployeeVO empPO where empPO.empId=" + 
          empChangeEmpId);
      List list = selectEmpNameQuery.list();
      Object obj = list.get(0);
      empName = obj.toString();
    } catch (HibernateException e) {
      System.out.println("selectEmpNameEJB Exception:" + e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return empName;
  }
  
  public String selectOrgName(Long empChangeOrgId) throws HibernateException {
    String orgName = "";
    try {
      begin();
      Query selectEmpNameQuery = this.session.createQuery("select orgPO.orgName from com.js.system.vo.organizationmanager.OrganizationVO orgPO where orgPO.orgId=" + 
          empChangeOrgId);
      List list = selectEmpNameQuery.list();
      Object obj = list.get(0);
      orgName = obj.toString();
    } catch (HibernateException e) {
      System.out.println("selectOrgNameEJB Exception:" + e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return orgName;
  }
  
  public List selectEmpDuty(String domainId) throws HibernateException {
    List list = new ArrayList();
    try {
      begin();
      Query selectEmpDutyQuery = this.session.createQuery("select dutyPO.id,dutyPO.dutyName from com.js.oa.hr.officemanager.po.DutyPO dutyPO where dutyPO.domainId = " + 
          domainId + " order by dutyPO.dutyName");
      list = selectEmpDutyQuery.list();
    } catch (HibernateException e) {
      System.out.println("selectEmpDutyEJB Exception:" + e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List selectEmpType(String domainId) throws HibernateException {
    List list = new ArrayList();
    try {
      begin();
      Query selectEmpDutyQuery = this.session.createQuery("select empChangeTypePO.id,empChangeTypePO.empChangeType from com.js.oa.hr.personnelmanager.po.EmployeeChangeTypePO empChangeTypePO where empChangeTypePO.domainId = " + 
          domainId + " order by empChangeTypePO.empChangeType");
      list = selectEmpDutyQuery.list();
    } catch (HibernateException e) {
      System.out.println("selectEmpTypeEJB Exception:" + e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public Integer addEmpChangeType(EmployeeChangeTypePO empChangeTypePO) throws Exception {
    int result = 2;
    begin();
    try {
      Iterator iter = this.session.createQuery("select a.id from com.js.oa.hr.personnelmanager.po.EmployeeChangeTypePO a where a.empChangeType='" + 
          
          empChangeTypePO
          .getEmpChangeType() + 
          "' and a.domainId=" + 
          empChangeTypePO.getDomainId())
        .iterate();
      if (iter.hasNext()) {
        result = 1;
      } else {
        this.session.save(empChangeTypePO);
        this.session.flush();
        result = 0;
      } 
    } catch (Exception e) {
      System.out.println("addEmpChangeTypeEJB Exception:" + e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return new Integer(result);
  }
  
  public boolean deleteEmpChangeType(Long empChangeTypeId) throws Exception {
    boolean result = false;
    begin();
    try {
      Query query = this.session.createQuery("select po.id from com.js.oa.hr.personnelmanager.po.EmployeeChangePO po  where po.empChangeChangeType=" + 
          empChangeTypeId);
      Iterator iter = query.iterate();
      if (iter.hasNext()) {
        result = false;
      } else {
        this.session.delete(
            "from com.js.oa.hr.personnelmanager.po.EmployeeChangeTypePO po  where po.id=" + 
            empChangeTypeId);
        this.session.flush();
        result = true;
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public boolean deleteBatchEmpChangeType(String empChangeTypeIds) throws Exception {
    boolean result = false;
    begin();
    try {
      Query query = this.session.createQuery("select po.id from com.js.oa.hr.personnelmanager.po.EmployeeChangePO po  where po.empChangeChangeType in (" + 
          empChangeTypeIds + "-1)");
      Iterator iter = query.iterate();
      if (!iter.hasNext()) {
        this.session.delete(
            "from com.js.oa.hr.personnelmanager.po.EmployeeChangeTypePO po  where po.id in (" + 
            empChangeTypeIds + "-1)");
        this.session.flush();
        result = true;
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public EmployeeChangeTypePO selectEmpChangeTypeView(Long empChangeTypeId) throws HibernateException {
    EmployeeChangeTypePO empChangeTypePO = null;
    try {
      begin();
      empChangeTypePO = (EmployeeChangeTypePO)this.session.load(
          EmployeeChangeTypePO.class, empChangeTypeId);
    } catch (HibernateException e) {
      System.out.println("selectEmpChangeTypeViewEJB Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return empChangeTypePO;
  }
  
  public Integer updateEmpChangeType(EmployeeChangeTypePO empChangeTypePO) throws Exception {
    int result = 2;
    begin();
    try {
      Iterator iter = this.session.createQuery("select a.id from com.js.oa.hr.personnelmanager.po.EmployeeChangeTypePO a where a.empChangeType='" + 
          
          empChangeTypePO
          .getEmpChangeType() + 
          "' and a.id<>" + 
          empChangeTypePO.getId() + 
          " and a.domainId=" + 
          empChangeTypePO.getDomainId())
        .iterate();
      if (iter.hasNext()) {
        result = 1;
      } else {
        EmployeeChangeTypePO employeeChangeType = (EmployeeChangeTypePO)this.session.load(
            EmployeeChangeTypePO.class, 
            empChangeTypePO.getId());
        employeeChangeType.setId(empChangeTypePO.getId());
        employeeChangeType.setEmpChangeType(empChangeTypePO
            .getEmpChangeType());
        this.session.update(employeeChangeType);
        this.session.flush();
        result = 0;
      } 
    } catch (Exception e) {
      System.out.println("updateEmpChangeTypeEJB Exception:" + e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return new Integer(result);
  }
  
  public Boolean hasSameNameExists(String typeName, String domainId) throws HibernateException {
    Boolean retFlg = Boolean.FALSE;
    try {
      begin();
      List list = this.session.createQuery(" from com.js.oa.hr.personnelmanager.po.EmployeeChangeTypePO po where po.domainId=" + 
          domainId + 
          " and po.empChangeType = '" + 
          typeName + "'").list();
      if (list != null && list.size() > 0)
        retFlg = Boolean.TRUE; 
    } catch (HibernateException e) {
      System.out.println("hasSameNameExists Exception:" + e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return retFlg;
  }
  
  public Boolean hasSameNameExists(String typeName, String domainId, Long id) throws HibernateException {
    Boolean retFlg = Boolean.FALSE;
    try {
      begin();
      List list = this.session.createQuery(" from com.js.oa.hr.personnelmanager.po.EmployeeChangeTypePO po where po.domainId=" + 
          domainId + 
          " and po.empChangeType = '" + 
          typeName + "' and po.id<>" + id)
        .list();
      if (list != null && list.size() > 0)
        retFlg = Boolean.TRUE; 
    } catch (HibernateException e) {
      System.out.println("hasSameNameExists Exception:" + e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return retFlg;
  }
}
