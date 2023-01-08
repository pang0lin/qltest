package com.js.system.bean.usermanager;

import com.active.e_uc.user.po.TblJilu;
import com.buguniao.TransBuguniao;
import com.js.ldap.OAToAD;
import com.js.oa.audit.bean.AuditMsRemindEJBBean;
import com.js.oa.audit.po.AuditEmployeePO;
import com.js.oa.audit.po.AuditLog;
import com.js.oa.chinaLife.tbUser.SynchronizeUsers;
import com.js.oa.hr.personnelmanager.po.EmployeeChangePO;
import com.js.oa.security.seamoon.po.SecSeaMoon;
import com.js.oa.security.seamoon.service.SeaMoonService;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.util.StaticParam;
import com.js.system.vo.organizationmanager.OrganizationVO;
import com.js.system.vo.organizationmanager.SyncRTXVO;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.FillBean;
import com.js.util.util.MD5;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class UserEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Integer add(EmployeeVO employeeVO, String[] orgId, String[] rightId, String[] rightScopeType, String[] rightScopeScope, String rightScopeDsp, String[] roleId) throws Exception {
    int result = 0, i = 0;
    begin();
    try {
      if ("0".equals(SystemCommon.getLicType())) {
        int regUserNum = 0, buyUserNum = 0;
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          tmpIter = this.session.iterate("select count(user.empId) from com.js.system.vo.usermanager.EmployeeVO user where user.userIsDeleted=0 and (user.userAccounts is not null and user.userAccounts<>'admin' and user.userAccounts<>'') and user.userIsFormalUser=1 and user.empId>0 and user.domainId=" + employeeVO.getDomainId());
        } else if (databaseType.indexOf("oracle") >= 0) {
          tmpIter = this.session.iterate("select count(user.empId) from com.js.system.vo.usermanager.EmployeeVO user where user.userIsDeleted=0 and (user.userAccounts is not null and user.userAccounts<>'admin') and user.userIsFormalUser=1 and user.empId>0 and user.domainId=" + employeeVO.getDomainId());
        } else {
          tmpIter = this.session.iterate("select count(user.empId) from com.js.system.vo.usermanager.EmployeeVO user where user.userIsDeleted=0 and (user.userAccounts is not null and user.userAccounts<>'admin' and user.userAccounts<>'') and user.userIsFormalUser=1 and user.empId>0 and user.domainId=" + employeeVO.getDomainId());
        } 
        if (tmpIter.hasNext()) {
          Object obj = tmpIter.next();
          regUserNum = (obj == null) ? 0 : Integer.parseInt(obj.toString());
        } 
        Iterator tmpIter = this.session.iterate("select a.userNum from com.js.system.vo.organizationmanager.DomainVO a where a.id=" + employeeVO.getDomainId() + " and a.inUse=1");
        if (tmpIter.hasNext()) {
          Object obj = tmpIter.next();
          buyUserNum = (obj == null) ? 0 : Integer.parseInt(obj.toString());
        } 
        if (regUserNum >= buyUserNum) {
          this.session.close();
          return Integer.valueOf("-100");
        } 
      } 
      if ("1".equals(employeeVO.getMailPost())) {
        result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where (emp.mailPost='1' and emp.userSimpleName is not null and emp.userAccounts is not null and emp.userIsDeleted=0) and emp.domainId=" + employeeVO.getDomainId()).next().toString());
        if (result >= Integer.parseInt(SystemCommon.getMobileLogonNum())) {
          this.session.close();
          result = 20;
          return new Integer(20);
        } 
        result = 0;
      } 
      if ("1".equals(employeeVO.getWeixinPost())) {
        result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where (emp.weixinPost='1' and emp.userSimpleName is not null and emp.userAccounts is not null and emp.userIsDeleted=0) and emp.domainId=" + employeeVO.getDomainId()).next().toString());
        if (result >= SystemCommon.getWeixinUserNum()) {
          this.session.close();
          result = 21;
          return new Integer(21);
        } 
        result = 0;
      } 
      result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where emp.userIsDeleted=0 and emp.domainId=" + employeeVO.getDomainId() + " and emp.userAccounts='" + employeeVO.getUserAccounts() + "'").next().toString());
      if (result > 0) {
        this.session.close();
        result = 1;
        return new Integer(1);
      } 
      if (employeeVO.getUserSimpleName() != null && !"".equals(employeeVO.getUserSimpleName())) {
        result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where emp.domainId=" + employeeVO.getDomainId() + " and emp.userSimpleName='" + employeeVO.getUserSimpleName() + "' and emp.userSimpleName is not null").next().toString());
        if (result > 0) {
          this.session.close();
          result = 4;
          return new Integer(4);
        } 
      } 
      if (employeeVO.getEmpEnglishName() != null && 
        !"".equals(employeeVO.getEmpEnglishName()) && 
        !"null".equals(employeeVO.getEmpEnglishName())) {
        result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where emp.domainId=" + 
              employeeVO.getDomainId() + " and emp.empEnglishName='" + 
              employeeVO.getEmpEnglishName() + 
              "' and emp.empEnglishName is not null").next().toString());
        if (result > 0) {
          this.session.close();
          result = 9;
          return new Integer(9);
        } 
      } 
      if (employeeVO.getEmpNumber() != null && 
        !"".equals(employeeVO.getEmpNumber()) && 
        !"null".equals(employeeVO.getEmpNumber())) {
        result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where emp.userIsDeleted=0 and  emp.domainId=" + 
              employeeVO.getDomainId() + " and emp.empNumber='" + 
              employeeVO.getEmpNumber() + 
              "' and emp.empNumber is not null").next().toString());
        if (result > 0) {
          this.session.close();
          result = 30;
          return new Integer(30);
        } 
      } 
      if ("1".equals(employeeVO.getKeyValidate())) {
        result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where emp.domainId=" + employeeVO.getDomainId() + " and emp.keySerial='" + employeeVO.getKeySerial() + "'").next().toString());
        if (result > 0) {
          this.session.close();
          result = 3;
          return new Integer(3);
        } 
      } 
      Set<Object> orgSet = new HashSet();
      for (i = 0; i < orgId.length; i++)
        orgSet.add(this.session.load(OrganizationVO.class, new Long(orgId[i]))); 
      employeeVO.setOrganizations(orgSet);
      String leaderName = employeeVO.getEmpLeaderName();
      if (leaderName == null || "".equals(leaderName) || "null".equals(leaderName)) {
        Iterator<Object[]> orgLeaderIt = this.session.createQuery("select po.orgManagerEmpId,po.orgManagerEmpName from com.js.system.vo.organizationmanager.OrganizationVO po where po.orgId=" + orgId[0]).iterate();
        if (orgLeaderIt != null && orgLeaderIt.hasNext()) {
          Object[] obj = orgLeaderIt.next();
          if (obj != null && obj[0] != null && obj[1] != null) {
            employeeVO.setEmpLeaderId(obj[0].toString());
            employeeVO.setEmpLeaderName(obj[1].toString());
          } 
        } 
      } 
      String duty = employeeVO.getEmpDuty();
      Iterator<E> it = this.session.createQuery("select po.dutyLevel from com.js.oa.hr.officemanager.po.DutyPO po where po.dutyName='" + duty + "' and po.domainId=" + employeeVO.getDomainId()).iterate();
      if (it.hasNext()) {
        duty = it.next().toString();
        employeeVO.setEmpDutyLevel(duty);
      } else {
        employeeVO.setEmpDutyLevel("1000");
      } 
      employeeVO.setSkin("blue");
      if ("0".equals(employeeVO.getEmpLeaderId()))
        employeeVO.setEmpLeaderId(""); 
      Long empId = Long.valueOf(0L);
      boolean op = true;
      if (SystemCommon.getCustomerName().equals("chinaLife"))
        op = SynchronizeUsers.synchronizeUsersFromEmployeeVO(employeeVO, "1"); 
    } catch (Exception e) {
      result = 2;
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      try {
        this.session.close();
      } catch (Exception exception) {}
    } 
    try {
      this.session.close();
    } catch (Exception exception) {}
    return new Integer(result);
  }
  
  public Integer add(EmployeeVO employeeVO, String[] orgId, String[] rightId, String[] rightScopeType, String[] rightScopeScope, String rightScopeDsp, String[] roleId, String[] log) throws Exception {
    int result = 0, i = 0;
    begin();
    try {
      if ("0".equals(SystemCommon.getLicType())) {
        int regUserNum = 0, buyUserNum = 0;
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          tmpIter = this.session.iterate("select count(user.empId) from com.js.system.vo.usermanager.EmployeeVO user where user.userIsDeleted=0 and (user.userAccounts is not null and user.userAccounts<>'admin' and user.userAccounts<>'') and user.userIsFormalUser=1 and user.domainId=" + employeeVO.getDomainId());
        } else if (databaseType.indexOf("oracle") >= 0) {
          tmpIter = this.session.iterate("select count(user.empId) from com.js.system.vo.usermanager.EmployeeVO user where user.userIsDeleted=0 and (user.userAccounts is not null and user.userAccounts<>'admin') and user.userIsFormalUser=1 and user.domainId=" + employeeVO.getDomainId());
        } else {
          tmpIter = this.session.iterate("select count(user.empId) from com.js.system.vo.usermanager.EmployeeVO user where user.userIsDeleted=0 and (user.userAccounts is not null and user.userAccounts<>'admin' and user.userAccounts<>'') and user.userIsFormalUser=1 and user.domainId=" + employeeVO.getDomainId());
        } 
        if (tmpIter.hasNext()) {
          Object obj = tmpIter.next();
          regUserNum = (obj == null) ? 0 : Integer.parseInt(obj.toString());
        } 
        Iterator tmpIter = this.session.iterate("select a.userNum from com.js.system.vo.organizationmanager.DomainVO a where a.id=" + employeeVO.getDomainId() + " and a.inUse=1");
        if (tmpIter.hasNext()) {
          Object obj = tmpIter.next();
          buyUserNum = (obj == null) ? 0 : Integer.parseInt(obj.toString());
        } 
        if (regUserNum >= buyUserNum) {
          this.session.close();
          return Integer.valueOf("-100");
        } 
      } 
      if ("1".equals(employeeVO.getMailPost())) {
        result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where (emp.mailPost='1' and emp.userSimpleName is not null and emp.userAccounts is not null and emp.userIsDeleted=0) and emp.domainId=" + employeeVO.getDomainId()).next().toString());
        if (result >= Integer.parseInt(SystemCommon.getMobileLogonNum())) {
          this.session.close();
          result = 20;
          return new Integer(20);
        } 
        result = 0;
      } 
      if ("1".equals(employeeVO.getWeixinPost())) {
        result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where (emp.weixinPost='1' and emp.userSimpleName is not null and emp.userAccounts is not null and emp.userIsDeleted=0) and emp.domainId=" + employeeVO.getDomainId()).next().toString());
        if (result >= SystemCommon.getWeixinUserNum()) {
          this.session.close();
          result = 21;
          return new Integer(21);
        } 
        result = 0;
      } 
      result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where emp.userIsDeleted=0 and emp.domainId=" + employeeVO.getDomainId() + " and emp.userAccounts='" + employeeVO.getUserAccounts() + "'").next().toString());
      if (result > 0) {
        this.session.close();
        result = 1;
        return new Integer(1);
      } 
      result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where emp.domainId=" + employeeVO.getDomainId() + " and emp.userSimpleName='" + employeeVO.getUserSimpleName() + "' and emp.userSimpleName is not null").next().toString());
      if (result > 0) {
        this.session.close();
        result = 4;
        return new Integer(4);
      } 
      if (employeeVO.getEmpEnglishName() != null && 
        !"".equals(employeeVO.getEmpEnglishName()) && 
        !"null".equals(employeeVO.getEmpEnglishName())) {
        result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where emp.domainId=" + 
              employeeVO.getDomainId() + " and emp.empEnglishName='" + 
              employeeVO.getEmpEnglishName() + 
              "' and emp.empEnglishName is not null").next().toString());
        if (result > 0) {
          this.session.close();
          result = 9;
          return new Integer(9);
        } 
      } 
      if (employeeVO.getEmpNumber() != null && 
        !"".equals(employeeVO.getEmpNumber()) && 
        !"null".equals(employeeVO.getEmpNumber())) {
        result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where emp.userIsDeleted=0 and emp.domainId=" + 
              employeeVO.getDomainId() + " and emp.empNumber='" + 
              employeeVO.getEmpNumber() + 
              "' and emp.empNumber is not null").next().toString());
        if (result > 0) {
          this.session.close();
          result = 30;
          return new Integer(30);
        } 
      } 
      if ("1".equals(employeeVO.getKeyValidate())) {
        result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where emp.domainId=" + employeeVO.getDomainId() + " and emp.keySerial='" + employeeVO.getKeySerial() + "'").next().toString());
        if (result > 0) {
          this.session.close();
          result = 3;
          return new Integer(3);
        } 
      } 
      String leaderName = employeeVO.getEmpLeaderName();
      if (leaderName == null || "".equals(leaderName) || "null".equals(leaderName)) {
        Iterator<Object[]> orgLeaderIt = this.session.createQuery("select po.orgManagerEmpId,po.orgManagerEmpName from com.js.system.vo.organizationmanager.OrganizationVO po where po.orgId=" + orgId[0]).iterate();
        if (orgLeaderIt != null && orgLeaderIt.hasNext()) {
          Object[] obj = orgLeaderIt.next();
          if (obj != null && obj[0] != null && obj[1] != null) {
            employeeVO.setEmpLeaderId(obj[0].toString());
            employeeVO.setEmpLeaderName(obj[1].toString());
          } 
        } 
      } 
      String duty = employeeVO.getEmpDuty();
      Iterator<E> it = this.session.createQuery("select po.dutyLevel from com.js.oa.hr.officemanager.po.DutyPO po where po.dutyName='" + duty + "' and po.domainId=" + employeeVO.getDomainId()).iterate();
      if (it.hasNext()) {
        duty = it.next().toString();
        employeeVO.setEmpDutyLevel(duty);
      } else {
        employeeVO.setEmpDutyLevel("1000");
      } 
      employeeVO.setSkin("blue");
      if ("0".equals(employeeVO.getEmpLeaderId()))
        employeeVO.setEmpLeaderId(""); 
      Long logId = Long.valueOf(saveLog(log, "增加用户管理“" + employeeVO.getEmpName() + "”"));
      AuditEmployeePO auditEmployeePO = (AuditEmployeePO)FillBean.transformOTO(employeeVO, AuditEmployeePO.class);
      auditEmployeePO.setOrgId(orgId[0]);
      auditEmployeePO.setLogId(logId.longValue());
      auditEmployeePO.setOperate("insert");
      auditEmployeePO.setEmpId(0L);
      this.session.save(auditEmployeePO);
      this.session.flush();
      SynchronizeUsers.synchronizeUsersFromAuditEmployeePO(auditEmployeePO, "1");
      Connection conn = null;
      Statement stmt = null;
      PreparedStatement pStmt = null;
    } catch (Exception e) {
      result = 2;
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      try {
        this.session.close();
      } catch (Exception exception) {}
    } 
    try {
      this.session.close();
    } catch (Exception exception) {}
    return new Integer(result);
  }
  
  public HashMap getUserRelativeInfo(Long userId, String type) throws Exception {
    if (userId == null)
      return null; 
    begin();
    HashMap<Object, Object> result = new HashMap<Object, Object>();
    EmployeeVO employeeVO = null;
    try {
      employeeVO = (EmployeeVO)this.session.load(EmployeeVO.class, userId);
      if (type.indexOf("employee") >= 0)
        result.put("employee", employeeVO); 
      if (type.indexOf("user") >= 0)
        result.put("user", employeeVO); 
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public String delete(String[] ids) throws Exception {
    Connection conn = null;
    Statement stmt = null;
    String result = "";
    Byte type = new Byte("1");
    Byte opr = new Byte("2");
    begin();
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      for (int i = 0; i < ids.length; i++) {
        EmployeeVO userVO = (EmployeeVO)this.session.load(EmployeeVO.class, new Long(ids[i]));
        boolean op = SynchronizeUsers.synchronizeUsersFromEmployeeVO(userVO, "3");
        if (op) {
          byte bt = 1;
          userVO.setUserIsDeleted(bt);
          bt = 0;
          userVO.setUserIsActive(bt);
          if (i == 0) {
            result = userVO.getEmpName();
          } else {
            result = String.valueOf(result) + "," + userVO.getEmpName();
          } 
          SynchronizeUsers.synchronizeUsersFromEmployeeVO(userVO, "3");
          SyncRTXVO rtxVO = new SyncRTXVO();
          rtxVO.setUserAccount(userVO.getUserAccounts());
          rtxVO.setDataType(type);
          rtxVO.setDataOpr(opr);
          this.session.save(rtxVO);
          stmt.executeUpdate("delete from sys_logon_record where useraccount='" + userVO.getUserAccounts() + "'");
        } 
      } 
      this.session.flush();
    } catch (Exception e) {
      result = "";
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      stmt.close();
      conn.close();
    } 
    return result;
  }
  
  public String delete(String[] ids, String[] log) throws Exception {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String sql = "";
    String result = "";
    begin();
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      for (int i = 0; i < ids.length; i++) {
        EmployeeVO userVO = (EmployeeVO)this.session.load(EmployeeVO.class, 
            new Long(ids[i]));
        Long logId = Long.valueOf(saveLog(log, "删除用户管理“" + userVO.getEmpName() + "”"));
        String orgId = StaticParam.getOrgIdByEmpId((new StringBuilder(String.valueOf(userVO.getEmpId()))).toString());
        AuditEmployeePO po = (AuditEmployeePO)FillBean.transformOTO(userVO, AuditEmployeePO.class);
        po.setOrgId(orgId);
        byte bt = 1;
        po.setUserIsDeleted(bt);
        bt = 0;
        po.setUserIsActive(bt);
        po.setOperate("delete");
        po.setLogId(logId.longValue());
        if (i == 0) {
          result = userVO.getEmpName();
        } else {
          result = String.valueOf(result) + "," + userVO.getEmpName();
        } 
        this.session.save(po);
        SynchronizeUsers.synchronizeUsersFromAuditEmployeePO(po, "3");
      } 
      this.session.flush();
    } catch (Exception e) {
      result = "";
      e.printStackTrace();
      throw e;
    } finally {
      stmt.close();
      conn.close();
      this.session.close();
    } 
    return result;
  }
  
  public boolean delete(String status) throws Exception {
    boolean result = false;
    begin();
    Byte type = new Byte("1");
    Byte opr = new Byte("2");
    try {
      String sql = "";
      if ("active".equals(status)) {
        sql = "SELECT employee.empId FROM com.js.system.vo.usermanager.EmployeeVO employee WHERE employee.userIsActive=1";
      } else if ("disabled".equals(status)) {
        sql = "SELECT employee.empId FROM com.js.system.vo.usermanager.EmployeeVO employee WHERE employee.userIsActive=0";
      } 
      List<Long> idList = this.session.createQuery(sql).list();
      int size = idList.size();
      byte bt = 1;
      for (int i = 0; i < size; i++) {
        EmployeeVO employeeVO = (EmployeeVO)this.session.load(EmployeeVO.class, idList.get(i));
        employeeVO.setUserIsDeleted(bt);
        bt = 0;
        employeeVO.setUserIsActive(bt);
        this.session.update(employeeVO);
        SyncRTXVO rtxVO = new SyncRTXVO();
        rtxVO.setUserAccount(employeeVO.getUserAccounts());
        rtxVO.setDataType(type);
        rtxVO.setDataOpr(opr);
        this.session.save(rtxVO);
      } 
      this.session.flush();
      result = true;
    } catch (Exception e) {
      result = false;
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public String disable(String[] ids) throws Exception {
    String result = "";
    begin();
    try {
      for (int i = 0; i < ids.length; i++) {
        EmployeeVO userVO = (EmployeeVO)this.session.load(EmployeeVO.class, new Long(ids[i]));
        boolean op = true;
        if ("chinaLife".equalsIgnoreCase(SystemCommon.getCustomerName()))
          op = SynchronizeUsers.synchronizeUsersFromEmployeeVO(userVO, "3"); 
        if (op) {
          byte userIsActive = 0;
          userVO.setUserIsActive(userIsActive);
          this.session.update(userVO);
          if (i == 0) {
            result = userVO.getEmpName();
          } else {
            result = String.valueOf(result) + "," + userVO.getEmpName();
          } 
          SyncRTXVO rtxVO = new SyncRTXVO();
          rtxVO.setUserAccount(userVO.getUserAccounts());
          rtxVO.setDataType(Byte.valueOf("1"));
          rtxVO.setDataOpr(Byte.valueOf("2"));
          this.session.save(rtxVO);
        } 
      } 
      this.session.flush();
    } catch (Exception e) {
      result = "";
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public String disable(String[] ids, String[] log) throws Exception {
    String result = "";
    String sql = "";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    begin();
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      for (int i = 0; i < ids.length; i++) {
        EmployeeVO userVO = (EmployeeVO)this.session.load(EmployeeVO.class, 
            new Long(ids[i]));
        Long logId = Long.valueOf(saveLog(log, "禁用用户管理“" + userVO.getEmpName() + "”"));
        String orgId = StaticParam.getOrgIdByEmpId((new StringBuilder(String.valueOf(userVO.getEmpId()))).toString());
        AuditEmployeePO po = (AuditEmployeePO)FillBean.transformOTO(userVO, AuditEmployeePO.class);
        po.setOrgId(orgId);
        byte userIsActive = 0;
        po.setUserIsActive(userIsActive);
        if (i == 0) {
          result = userVO.getEmpName();
        } else {
          result = String.valueOf(result) + "," + userVO.getEmpName();
        } 
        po.setLogId(logId.longValue());
        po.setOperate("disable");
        this.session.save(po);
      } 
      this.session.flush();
    } catch (Exception e) {
      result = "";
      throw e;
    } finally {
      stmt.close();
      conn.close();
      this.session.close();
    } 
    return result;
  }
  
  public String recover(String[] ids) throws Exception {
    String result = "";
    begin();
    try {
      for (int i = 0; i < ids.length; i++) {
        EmployeeVO userVO = (EmployeeVO)this.session.load(EmployeeVO.class, new Long(ids[i]));
        boolean op = SynchronizeUsers.synchronizeUsersFromEmployeeVO(userVO, "2");
        if (op) {
          byte userIsActive = 1;
          userVO.setUserIsActive(userIsActive);
          this.session.update(userVO);
          if (i == 0) {
            result = userVO.getEmpName();
          } else {
            result = String.valueOf(result) + "," + userVO.getEmpName();
          } 
          SyncRTXVO rtxVO = new SyncRTXVO();
          rtxVO.setUserAccount(userVO.getUserAccounts());
          rtxVO.setDataType(Byte.valueOf("1"));
          rtxVO.setDataOpr(Byte.valueOf("0"));
          this.session.save(rtxVO);
        } 
      } 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public String recover(String[] ids, String[] log) throws Exception {
    String result = "";
    String sql = "";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    begin();
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      for (int i = 0; i < ids.length; i++) {
        EmployeeVO userVO = (EmployeeVO)this.session.load(EmployeeVO.class, 
            new Long(ids[i]));
        Long logId = Long.valueOf(saveLog(log, "恢复用户管理“" + userVO.getEmpName() + "”"));
        String orgId = StaticParam.getOrgIdByEmpId((new StringBuilder(String.valueOf(userVO.getEmpId()))).toString());
        AuditEmployeePO po = (AuditEmployeePO)FillBean.transformOTO(userVO, AuditEmployeePO.class);
        po.setOrgId(orgId);
        byte userIsActive = 1;
        po.setUserIsActive(userIsActive);
        po.setLogId(logId.longValue());
        po.setOperate("recover");
        if (i == 0) {
          result = userVO.getEmpName();
        } else {
          result = String.valueOf(result) + "," + userVO.getEmpName();
        } 
        this.session.save(po);
      } 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      stmt.close();
      conn.close();
      this.session.close();
    } 
    return result;
  }
  
  public boolean open(String[] ids) throws Exception {
    boolean result = false;
    begin();
    try {
      for (int i = 0; i < ids.length; i++) {
        EmployeeVO userVO = (EmployeeVO)this.session.load(EmployeeVO.class, 
            new Long(ids[i]));
        byte userIsFormalUser = 1;
        userVO.setUserIsFormalUser(Integer.valueOf("1"));
        userVO.setUserIsActive(userIsFormalUser);
        userVO.setUserIsDeleted(Byte.parseByte("0"));
        this.session.update(userVO);
      } 
      this.session.flush();
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public String getUserAccountByIds(String idStr) throws Exception {
    StringBuffer buffer = new StringBuffer();
    begin();
    try {
      String tmpSql = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "select org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org where '" + idStr + "' like concat('%*', org.orgId, '*%')";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = "select org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('*', JSDB.FN_INTTOSTR(org.orgId)),'*'),'" + idStr + "')>0 ";
      } else {
        tmpSql = "select org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org where '" + idStr + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%*', JSDB.FN_INTTOSTR(org.orgId)),'*%')";
      } 
      Query query = this.session.createQuery(tmpSql);
      List<String> list = query.list();
      for (int i = 0; i < list.size(); i++) {
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "select org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org where org.orgIdString like concat('%$', '" + list.get(i) + "', '$%')";
        } else if (databaseType.indexOf("db2") >= 0) {
          tmpSql = "select org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(" + list.get(i) + ")), '$'),org.orgIdString)>0";
        } else {
          tmpSql = "select org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org where org.orgIdString like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(" + list.get(i) + ")), '$%')";
        } 
        Iterator iterator = this.session.createQuery(tmpSql).iterate();
        while (iterator.hasNext())
          buffer.append(iterator.next()).append(","); 
      } 
      buffer.append("-1");
      if (buffer.length() == 0)
        buffer.append("-100"); 
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "select emp.empId from com.js.system.vo.usermanager.EmployeeVO emp join emp.organizations org where org.orgId in (" + buffer.toString() + ") or '" + idStr + "' like concat('%$', emp.empId, '$%')";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = "select emp.empId from com.js.system.vo.usermanager.EmployeeVO emp join emp.organizations org where org.orgId in (" + buffer.toString() + ") or locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(emp.empId)), '$'),'" + idStr + "')>0 ";
      } else {
        tmpSql = "select emp.empId from com.js.system.vo.usermanager.EmployeeVO emp join emp.organizations org where org.orgId in (" + buffer.toString() + ") or '" + idStr + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(emp.empId)), '$%')";
      } 
      Iterator it = this.session.createQuery(tmpSql).iterate();
      buffer = new StringBuffer();
      while (it.hasNext())
        buffer.append(it.next()).append(","); 
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "select emp.empId from com.js.system.vo.usermanager.EmployeeVO emp join emp.groups g where '" + idStr + "' like concat('%@', g.groupId, '@%')";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = "select emp.empId from com.js.system.vo.usermanager.EmployeeVO emp join emp.groups g where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('@', JSDB.FN_INTTOSTR(g.groupId)), '@'),'" + idStr + "')>0 ";
      } else {
        tmpSql = "select emp.empId from com.js.system.vo.usermanager.EmployeeVO emp join emp.groups g where '" + idStr + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%@', JSDB.FN_INTTOSTR(g.groupId)), '@%')";
      } 
      it = this.session.createQuery(tmpSql).iterate();
      while (it.hasNext())
        buffer.append(it.next()).append(","); 
      buffer.append("-1");
      it = this.session.createQuery("select distinct emp.userAccounts from com.js.system.vo.usermanager.EmployeeVO emp where emp.empId in (" + buffer.toString() + ")").iterate();
      buffer = new StringBuffer();
      while (it.hasNext())
        buffer.append(it.next()).append(","); 
      if (buffer.length() > 0)
        buffer = buffer.deleteCharAt(buffer.length() - 1); 
    } catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    } finally {
      this.session.close();
    } 
    return buffer.toString();
  }
  
  public String getUserNameById(String userId) throws Exception {
    StringBuffer userName = new StringBuffer();
    if (userId.endsWith(","))
      userId = userId.substring(0, 
          userId.length() - 1); 
    begin();
    try {
      Query query = this.session.createQuery("SELECT employee.empName FROM com.js.system.vo.usermanager.EmployeeVO employee WHERE employee.empId IN (" + 

          
          userId + ")");
      List userList = query.list();
      for (int i = 0; i < userList.size(); i++) {
        userName.append(userList.get(i));
        userName.append(",");
      } 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return userName.toString();
  }
  
  public String getUserAccountByEnglistName(String englishName) throws Exception {
    StringBuffer userAccounts = new StringBuffer();
    begin();
    try {
      Query query = this.session.createQuery("SELECT employee.userAccounts FROM com.js.system.vo.usermanager.EmployeeVO employee WHERE employee.empEnglishName =:empEnglishName")
        
        .setString("empEnglishName", englishName);
      List userList = query.list();
      if (userList.size() > 0)
        userAccounts.append(userList.get(0)); 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return userAccounts.toString();
  }
  
  public Integer update(EmployeeVO modiEmployeeVO, String[] orgId, String[] rightId, String[] rightScopeType, String[] rightScopeScope, String rightScopeDsp, String[] roleId) throws Exception {
    begin();
    int result = 0, i = 0;
    long empId = modiEmployeeVO.getEmpId();
    String userAccounts = modiEmployeeVO.getUserAccounts();
    try {
      EmployeeVO employeeVO = (EmployeeVO)this.session.load(EmployeeVO.class, new Long(empId));
      result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where emp.userIsDeleted=0 and emp.userAccounts='" + userAccounts + "' and emp.domainId=" + employeeVO.getDomainId() + " and emp.empId<>" + empId).next().toString());
      if (result > 0) {
        this.session.close();
        result = 1;
        return new Integer(1);
      } 
      result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where (emp.userSimpleName='" + modiEmployeeVO.getUserSimpleName() + "' and emp.userSimpleName is not null) and emp.domainId=" + employeeVO.getDomainId() + " and emp.empId<>" + empId).next().toString());
      if (result > 0) {
        this.session.close();
        result = 4;
        return new Integer(4);
      } 
      if ("1".equals(modiEmployeeVO.getMailPost())) {
        result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where (emp.mailPost='1' and emp.mailPost is not null and emp.userIsDeleted=0) and emp.domainId=" + employeeVO.getDomainId() + " and emp.empId<>" + empId).next().toString());
        if (result >= Integer.parseInt(SystemCommon.getMobileLogonNum())) {
          this.session.close();
          result = 20;
          return new Integer(20);
        } 
        result = 0;
      } 
      if ("1".equals(modiEmployeeVO.getWeixinPost())) {
        result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where (emp.weixinPost='1' and emp.weixinPost is not null and emp.userIsDeleted=0) and emp.domainId=" + employeeVO.getDomainId() + " and emp.empId<>" + empId).next().toString());
        if (result >= SystemCommon.getWeixinUserNum()) {
          this.session.close();
          result = 21;
          return new Integer(21);
        } 
        result = 0;
      } 
      if (modiEmployeeVO.getEmpEnglishName() != null && 
        !"".equals(modiEmployeeVO.getEmpEnglishName()) && 
        !"null".equals(modiEmployeeVO.getEmpEnglishName())) {
        result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where emp.userIsDeleted=0 and emp.empEnglishName='" + modiEmployeeVO.getEmpEnglishName() + "' and emp.domainId=" + employeeVO.getDomainId() + " and emp.empId<>" + empId).next().toString());
        if (result > 0) {
          this.session.close();
          result = 9;
          return new Integer(9);
        } 
      } 
      if (modiEmployeeVO.getEmpNumber() != null && 
        !"".equals(modiEmployeeVO.getEmpNumber()) && 
        !"null".equals(modiEmployeeVO.getEmpNumber())) {
        result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where emp.userIsDeleted=0 and emp.empNumber='" + modiEmployeeVO.getEmpNumber() + "' and emp.domainId=" + employeeVO.getDomainId() + " and emp.empId<>" + empId).next().toString());
        if (result > 0) {
          this.session.close();
          result = 30;
          return new Integer(30);
        } 
      } 
      String keyValidate = modiEmployeeVO.getKeyValidate();
      String keySerial = modiEmployeeVO.getKeySerial();
      if ("1".equals(keyValidate)) {
        result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where emp.keySerial='" + keySerial + "' and emp.domainId=" + employeeVO.getDomainId() + " and emp.empId<>" + empId).next().toString());
        if (result > 0) {
          this.session.close();
          result = 3;
          return new Integer(3);
        } 
      } 
      String userPassword = modiEmployeeVO.getUserPassword();
      byte userIsSuper = modiEmployeeVO.getUserIsSuper();
      String oldAccounts = employeeVO.getUserAccounts();
      if (employeeVO.getUserAccounts() != null && employeeVO.getUserAccounts().equals(userAccounts)) {
        SyncRTXVO rtxVO = new SyncRTXVO();
        rtxVO.setUserAccount(userAccounts);
        rtxVO.setDataType(new Byte("1"));
        rtxVO.setDataOpr(new Byte("1"));
        this.session.save(rtxVO);
      } else {
        SyncRTXVO rtxVO = new SyncRTXVO();
        rtxVO.setUserAccount(userAccounts);
        rtxVO.setDataType(new Byte("1"));
        rtxVO.setDataOpr(new Byte("0"));
        this.session.save(rtxVO);
        SyncRTXVO rtxVO2 = new SyncRTXVO();
        rtxVO2.setUserAccount(employeeVO.getUserAccounts());
        rtxVO2.setDataType(new Byte("1"));
        rtxVO2.setDataOpr(new Byte("2"));
        this.session.save(rtxVO2);
      } 
      employeeVO.setEmpName(modiEmployeeVO.getEmpName());
      employeeVO.setEmpEnglishName(modiEmployeeVO.getEmpEnglishName());
      employeeVO.setEmpNumber(modiEmployeeVO.getEmpNumber());
      employeeVO.setEmpLeaderId(modiEmployeeVO.getEmpLeaderId());
      employeeVO.setEmpLeaderName(modiEmployeeVO.getEmpLeaderName());
      employeeVO.setBrowseRange(modiEmployeeVO.getBrowseRange());
      employeeVO.setBrowseRangeName(modiEmployeeVO.getBrowseRangeName());
      employeeVO.setUserAccounts(userAccounts);
      employeeVO.setUserSimpleName(modiEmployeeVO.getUserSimpleName());
      employeeVO.setUserOrderCode(modiEmployeeVO.getUserOrderCode());
      employeeVO.setEmpSex(modiEmployeeVO.getEmpSex());
      employeeVO.setEmpDuty(modiEmployeeVO.getEmpDuty());
      employeeVO.setKeyValidate(keyValidate);
      employeeVO.setKeySerial(keySerial);
      employeeVO.setDingdingPost(modiEmployeeVO.getDingdingPost());
      employeeVO.setMailboxSize(modiEmployeeVO.getMailboxSize());
      employeeVO.setNetDiskSize(modiEmployeeVO.getNetDiskSize());
      employeeVO.setSidelineOrg(modiEmployeeVO.getSidelineOrg());
      employeeVO.setSidelineOrgName(modiEmployeeVO.getSidelineOrgName());
      employeeVO.setSignatureImgName(modiEmployeeVO.getSignatureImgName());
      employeeVO.setSignatureImgSaveName(modiEmployeeVO.getSignatureImgSaveName());
      employeeVO.setDeptLeader(modiEmployeeVO.getDeptLeader());
      employeeVO.setEmpPosition(modiEmployeeVO.getEmpPosition());
      employeeVO.setEmpPositionId(modiEmployeeVO.getEmpPositionId());
      employeeVO.setEmpPositionOtherId(modiEmployeeVO.getEmpPositionOtherId());
      employeeVO.setGrantRange(modiEmployeeVO.getGrantRange());
      employeeVO.setGrantRangeName(modiEmployeeVO.getGrantRangeName());
      employeeVO.setMailPost(modiEmployeeVO.getMailPost());
      employeeVO.setWeixinPost(modiEmployeeVO.getWeixinPost());
      employeeVO.setWorkAddress(modiEmployeeVO.getWorkAddress());
      employeeVO.setOpinionRemind(modiEmployeeVO.getOpinionRemind());
      String duty = modiEmployeeVO.getEmpDuty();
      Iterator<E> it = this.session.createQuery("select po.dutyLevel from com.js.oa.hr.officemanager.po.DutyPO po where po.dutyName='" + duty + "' and po.domainId=" + employeeVO.getDomainId()).iterate();
      if (it.hasNext()) {
        duty = it.next().toString();
        employeeVO.setEmpDutyLevel(duty);
      } else {
        employeeVO.setEmpDutyLevel("-1");
      } 
      employeeVO.setUserIsFormalUser(modiEmployeeVO.getUserIsFormalUser());
      String userPassword_AD = "";
      if (userPassword != null && !userPassword.equals("")) {
        userPassword_AD = userPassword;
        MD5 md5 = new MD5();
        userPassword = md5.getMD5Code(userPassword);
        employeeVO.setUserPassword(userPassword);
      } 
      (new TransBuguniao()).updateUserPass(userPassword, userAccounts, oldAccounts);
      byte isSuper = 0;
      if (userIsSuper == 1) {
        isSuper = 1;
        employeeVO.setUserSuperBegin(modiEmployeeVO.getUserSuperBegin());
        employeeVO.setUserSuperEnd(modiEmployeeVO.getUserSuperEnd());
      } 
      employeeVO.setUserIsSuper(isSuper);
      Set<Object> orgSet = new HashSet();
      for (i = 0; i < orgId.length; i++)
        orgSet.add(this.session.load(OrganizationVO.class, new Long(orgId[i]))); 
      employeeVO.setOrganizations(orgSet);
      if ("2".equals(SystemCommon.getUKey())) {
        String sn = employeeVO.getKeySerial();
        SeaMoonService smService = new SeaMoonService();
        if ("".equals(sn)) {
          smService.deleteSeaMoonByUserId(String.valueOf(empId));
        } else {
          SecSeaMoon po = new SecSeaMoon();
          po.setSn(sn);
          po.setUserId(String.valueOf(empId));
          po.setUserName(employeeVO.getEmpName());
          smService.updateSeaMoonByUserManage(po);
        } 
      } 
      if (userPassword_AD != null && !userPassword_AD.equals("")) {
        String path = System.getProperty("user.dir");
        String filePath = String.valueOf(path) + "/jsconfig/sysconfig.xml";
        int useLDAP = 0;
        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        try {
          doc = builder.build(filePath);
        } catch (JDOMException e1) {
          e1.printStackTrace();
        } catch (IOException e1) {
          e1.printStackTrace();
        } 
        Element ldapConfig = doc.getRootElement().getChild("LdapConfig");
        if (ldapConfig != null) {
          useLDAP = Integer.parseInt(ldapConfig.getAttribute("use").getValue());
          if (useLDAP == 1) {
            OAToAD oaToAd = new OAToAD();
            int isAccuess = oaToAd.updatePassword(userPassword_AD, userAccounts);
            if (isAccuess == 1)
              return Integer.valueOf(11); 
          } 
        } 
      } 
      boolean op = false;
      if ("chinaLife".equals(SystemCommon.getCustomerName())) {
        op = SynchronizeUsers.synchronizeUsersFromEmployeeVO(modiEmployeeVO, "2");
        if (op)
          this.session.flush(); 
      } else {
        op = true;
        this.session.flush();
      } 
    } catch (Exception e) {
      System.out.println(e.getMessage());
      result = 2;
      e.printStackTrace();
      throw e;
    } finally {
      try {
        this.session.close();
      } catch (Exception exception) {}
    } 
    try {
      this.session.close();
    } catch (Exception exception) {}
    return new Integer(result);
  }
  
  public Integer update(EmployeeVO modiEmployeeVO, String[] orgId, String[] rightId, String[] rightScopeType, String[] rightScopeScope, String rightScopeDsp, String[] roleId, String[] log) throws Exception {
    begin();
    int result = 0, i = 0;
    long empId = modiEmployeeVO.getEmpId();
    String userAccounts = modiEmployeeVO.getUserAccounts();
    try {
      EmployeeVO employeeVO = (EmployeeVO)this.session.load(EmployeeVO.class, new Long(empId));
      AuditEmployeePO auditEmployeePO = (AuditEmployeePO)FillBean.transformOTO(employeeVO, AuditEmployeePO.class);
      result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where emp.userIsDeleted=0 and emp.userAccounts='" + userAccounts + "' and emp.domainId=" + employeeVO.getDomainId() + " and emp.empId<>" + empId).next().toString());
      if (result > 0) {
        this.session.close();
        result = 1;
        return new Integer(1);
      } 
      result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where (emp.userSimpleName='" + modiEmployeeVO.getUserSimpleName() + "' and emp.userSimpleName is not null) and emp.domainId=" + employeeVO.getDomainId() + " and emp.empId<>" + empId).next().toString());
      if (result > 0) {
        this.session.close();
        result = 4;
        return new Integer(4);
      } 
      if ("1".equals(modiEmployeeVO.getMailPost())) {
        result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where (emp.mailPost='1' and emp.mailPost is not null and emp.userIsDeleted=0) and emp.domainId=" + employeeVO.getDomainId() + " and emp.empId<>" + empId).next().toString());
        if (result >= Integer.parseInt(SystemCommon.getMobileLogonNum())) {
          this.session.close();
          result = 20;
          return new Integer(20);
        } 
        result = 0;
      } 
      if ("1".equals(modiEmployeeVO.getMailPost())) {
        result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where (emp.weixinPost='1' and emp.mailPost is not null and emp.userIsDeleted=0) and emp.domainId=" + employeeVO.getDomainId() + " and emp.empId<>" + empId).next().toString());
        if (result >= SystemCommon.getWeixinUserNum()) {
          this.session.close();
          result = 21;
          return new Integer(21);
        } 
        result = 0;
      } 
      if (modiEmployeeVO.getEmpEnglishName() != null && 
        !"".equals(modiEmployeeVO.getEmpEnglishName()) && 
        !"null".equals(modiEmployeeVO.getEmpEnglishName())) {
        result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where emp.userIsDeleted=0 and emp.empEnglishName='" + modiEmployeeVO.getEmpEnglishName() + "' and emp.domainId=" + employeeVO.getDomainId() + " and emp.empId<>" + empId).next().toString());
        if (result > 0) {
          this.session.close();
          result = 9;
          return new Integer(9);
        } 
      } 
      if (modiEmployeeVO.getEmpNumber() != null && 
        !"".equals(modiEmployeeVO.getEmpNumber()) && 
        !"null".equals(modiEmployeeVO.getEmpNumber())) {
        result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where emp.userIsDeleted=0 and emp.empNumber='" + modiEmployeeVO.getEmpNumber() + "' and emp.domainId=" + employeeVO.getDomainId() + " and emp.empId<>" + empId).next().toString());
        if (result > 0) {
          this.session.close();
          result = 30;
          return new Integer(30);
        } 
      } 
      String keyValidate = modiEmployeeVO.getKeyValidate();
      String keySerial = modiEmployeeVO.getKeySerial();
      if ("1".equals(keyValidate)) {
        result = Integer.parseInt(this.session.iterate("select count(emp.empId) from com.js.system.vo.usermanager.EmployeeVO emp where emp.keySerial='" + keySerial + "' and emp.domainId=" + employeeVO.getDomainId() + " and emp.empId<>" + empId).next().toString());
        if (result > 0) {
          this.session.close();
          result = 3;
          return new Integer(3);
        } 
      } 
      String userPassword = modiEmployeeVO.getUserPassword();
      byte userIsSuper = modiEmployeeVO.getUserIsSuper();
      auditEmployeePO.setEmpName(modiEmployeeVO.getEmpName());
      auditEmployeePO.setEmpEnglishName(modiEmployeeVO.getEmpEnglishName());
      auditEmployeePO.setEmpNumber(modiEmployeeVO.getEmpNumber());
      auditEmployeePO.setEmpLeaderId(modiEmployeeVO.getEmpLeaderId());
      auditEmployeePO.setEmpLeaderName(modiEmployeeVO.getEmpLeaderName());
      auditEmployeePO.setBrowseRange(modiEmployeeVO.getBrowseRange());
      auditEmployeePO.setBrowseRangeName(modiEmployeeVO.getBrowseRangeName());
      auditEmployeePO.setUserAccounts(userAccounts);
      auditEmployeePO.setUserSimpleName(modiEmployeeVO.getUserSimpleName());
      auditEmployeePO.setUserOrderCode(modiEmployeeVO.getUserOrderCode());
      auditEmployeePO.setEmpSex(modiEmployeeVO.getEmpSex());
      auditEmployeePO.setEmpDuty(modiEmployeeVO.getEmpDuty());
      auditEmployeePO.setKeyValidate(keyValidate);
      auditEmployeePO.setKeySerial(keySerial);
      auditEmployeePO.setMailboxSize(modiEmployeeVO.getMailboxSize());
      auditEmployeePO.setNetDiskSize(modiEmployeeVO.getNetDiskSize());
      auditEmployeePO.setSidelineOrg(modiEmployeeVO.getSidelineOrg());
      auditEmployeePO.setSidelineOrgName(modiEmployeeVO.getSidelineOrgName());
      auditEmployeePO.setSignatureImgName(modiEmployeeVO.getSignatureImgName());
      auditEmployeePO.setSignatureImgSaveName(modiEmployeeVO.getSignatureImgSaveName());
      auditEmployeePO.setDeptLeader(modiEmployeeVO.getDeptLeader());
      auditEmployeePO.setEmpPosition(modiEmployeeVO.getEmpPosition());
      auditEmployeePO.setEmpPositionId(modiEmployeeVO.getEmpPositionId());
      auditEmployeePO.setEmpPositionOtherId(modiEmployeeVO.getEmpPositionOtherId());
      auditEmployeePO.setGrantRange(modiEmployeeVO.getGrantRange());
      auditEmployeePO.setGrantRangeName(modiEmployeeVO.getGrantRangeName());
      auditEmployeePO.setMailPost(modiEmployeeVO.getMailPost());
      auditEmployeePO.setWeixinPost(modiEmployeeVO.getWeixinPost());
      auditEmployeePO.setWorkAddress(modiEmployeeVO.getWorkAddress());
      String duty = modiEmployeeVO.getEmpDuty();
      Iterator<E> it = this.session.createQuery("select po.dutyLevel from com.js.oa.hr.officemanager.po.DutyPO po where po.dutyName='" + duty + "' and po.domainId=" + employeeVO.getDomainId()).iterate();
      if (it.hasNext()) {
        duty = it.next().toString();
        auditEmployeePO.setEmpDutyLevel(duty);
      } else {
        auditEmployeePO.setEmpDutyLevel("-1");
      } 
      auditEmployeePO.setUserIsFormalUser(modiEmployeeVO.getUserIsFormalUser());
      if (!userPassword.equals("")) {
        auditEmployeePO.setUserPassword(userPassword);
      } else {
        auditEmployeePO.setUserPassword("");
      } 
      byte isSuper = 0;
      if (userIsSuper == 1) {
        isSuper = 1;
        auditEmployeePO.setUserSuperBegin(modiEmployeeVO.getUserSuperBegin());
        auditEmployeePO.setUserSuperEnd(modiEmployeeVO.getUserSuperEnd());
      } 
      auditEmployeePO.setUserIsSuper(isSuper);
      Long logId = Long.valueOf(saveLog(log, "更新用户管理“" + employeeVO.getEmpName() + "”"));
      auditEmployeePO.setOrgId(orgId[0]);
      auditEmployeePO.setOperate("update");
      auditEmployeePO.setLogId(logId.longValue());
      this.session.save(auditEmployeePO);
      this.session.flush();
      String[] rightScopeDspArray = new String[rightId.length];
      if (rightScopeDsp != null && rightScopeDsp.indexOf("|") > 0)
        rightScopeDspArray = rightScopeDsp.split("\\|"); 
      Connection conn = null;
      Statement stmt = null;
      PreparedStatement pStmt = null;
      ResultSet rs = null;
      String empIdStr = String.valueOf(empId);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      result = 2;
      e.printStackTrace();
      throw e;
    } finally {
      try {
        this.session.close();
      } catch (Exception exception) {}
    } 
    try {
      this.session.close();
    } catch (Exception exception) {}
    return new Integer(result);
  }
  
  public List getUserInfo(Long empId) throws Exception {
    List result = null;
    try {
      StringBuffer hql = new StringBuffer(" SELECT distinct emp.empName,emp.empEnglishName,emp.userAccounts,emp.empSex,emp.userPassword, ");
      hql.append(" emp.userIsSuper,emp.userSuperBegin,emp.userSuperEnd,org.orgId,org.orgName,emp.empLeaderId, ");
      hql.append(" emp.empLeaderName,emp.browseRange,emp.browseRangeName,emp.userOrderCode, ");
      hql.append(" emp.empDuty,emp.keyValidate,emp.keySerial,emp.userSimpleName,emp.mailboxSize, ");
      hql.append(" emp.netDiskSize,emp.signatureImgName,emp.signatureImgSaveName,emp.sidelineOrg,emp.sidelineOrgName,emp.deptLeader,emp.empPosition,emp.empPositionId,emp.empPositionOtherId,");
      hql.append(" emp.grantRange,emp.grantRangeName,emp.mailPost,emp.weixinPost,emp.dingdingPost,emp.empNumber,emp.workAddress,emp.opinionRemind");
      hql.append(" FROM com.js.system.vo.usermanager.EmployeeVO emp join emp.organizations org ");
      hql.append(" WHERE emp.empId=" + empId);
      begin();
      result = this.session.createQuery(hql.toString()).list();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public List getUserWorkClass(Long empId) throws Exception {
    List result = null;
    String databaseType = SystemCommon.getDatabaseType();
    try {
      StringBuffer hql = new StringBuffer();
      if ("mysql".equals(databaseType)) {
        hql.append(" SELECT bbb.wfWorkFlowProcessId ,bbb.workFlowProcessName,count(bbb.workFlowProcessName) ");
        hql.append(" From com.js.oa.jsflow.po.WFWorkPO aaa ,com.js.oa.jsflow.po.WFWorkFlowProcessPO bbb ");
        hql.append(" where aaa.workProcessId=bbb.wfWorkFlowProcessId  ");
        hql.append(" and  aaa.workStatus = 0 and aaa.wfCurEmployeeId = " + empId);
        hql.append(" and aaa.workListControl = 1 and (aaa.workDelete = 0 or aaa.workDelete is null) ");
        hql.append(" GROUP BY bbb.workFlowProcessName ");
        hql.append(" order by aaa.emergence desc, aaa.wfWorkId desc");
        begin();
        result = this.session.createQuery(hql.toString()).list();
      } else {
        hql.append(" SELECT bbb.WF_WORKFLOWPROCESS_ID ,bbb.WORKFLOWPROCESSNAME,count(bbb.WORKFLOWPROCESSNAME) ");
        hql.append(" From jsf_work aaa ,JSF_WORKFLOWPROCESS bbb ");
        hql.append(" where aaa.WORKPROCESS_ID=bbb.WF_WORKFLOWPROCESS_ID  ");
        hql.append(" and  aaa.workStatus = 0 and aaa.WF_CUREMPLOYEE_ID = " + empId);
        hql.append(" and aaa.WORKLISTCONTROL = 1 and (aaa.WORKDELETE = 0 or aaa.WORKDELETE is NULl) ");
        hql.append(" GROUP BY bbb.WF_WORKFLOWPROCESS_ID,bbb.WORKFLOWPROCESSNAME ");
        hql.append(" order by bbb.WORKFLOWPROCESSNAME desc");
        result = getResult(hql.toString());
      } 
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw e;
    } finally {
      if ("mysql".equals(databaseType))
        this.session.close(); 
    } 
    return result;
  }
  
  public List getUserCoordination(Long empId) throws Exception {
    List result = null;
    String databaseType = SystemCommon.getDatabaseType();
    try {
      StringBuffer hql = new StringBuffer();
      if ("mysql".equals(databaseType)) {
        begin();
        hql.append("SELECT aaa.workTableId,aaa.workFileType,count(aaa.workFileType)");
        hql.append(" From com.js.oa.jsflow.po.WFWorkPO aaa  ");
        hql.append(" where  ");
        hql.append("  aaa.workStatus = 0 and aaa.workTableId=-1  and aaa.wfCurEmployeeId = " + empId);
        hql.append(" and aaa.workListControl = 1 and (aaa.workDelete = 0 or aaa.workDelete is null) ");
        hql.append(" GROUP BY aaa.workTableId,aaa.workFileType ");
        hql.append(" order by aaa.workTableId , aaa.wfWorkId desc");
        result = this.session.createQuery(hql.toString()).list();
      } else if ("oracle".equals(databaseType)) {
        hql.append("select WORKTABLE_ID,WORKFILETYPE,count(WORKFILETYPE)");
        hql.append(" From jsf_work  ");
        hql.append(" where  ");
        hql.append("  WORKSTATUS = 0 and WORKTABLE_ID=-1  and WF_CUREMPLOYEE_ID = " + empId);
        hql.append(" and WORKLISTCONTROL = 1 and (WORKDELETE = 0 or WORKDELETE is NUll)");
        hql.append(" group BY WORKTABLE_ID,WORKFILETYPE ");
        hql.append(" order by WORKTABLE_ID , WORKFILETYPE ");
        result = getResult(hql.toString());
      } 
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw e;
    } finally {
      if ("mysql".equals(databaseType))
        this.session.close(); 
    } 
    return result;
  }
  
  public List getResult(String sql) throws Exception {
    List<Object[]> resulList = new ArrayList();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        Object[] objects = new Object[3];
        objects[0] = rs.getString(1);
        objects[1] = rs.getString(2);
        objects[2] = rs.getString(3);
        resulList.add(objects);
      } 
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (rs != null)
        rs.close(); 
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
    } 
    return resulList;
  }
  
  public boolean updateEmpTurnover(String sql) throws Exception {
    boolean result = true;
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      boolean status_db = conn.getAutoCommit();
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      stmt.executeUpdate(sql);
      stmt.close();
      conn.commit();
      conn.setAutoCommit(status_db);
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
      result = false;
    } 
    return result;
  }
  
  public boolean updateEmpTurnoverCoordination(String sql) throws Exception {
    boolean result = true;
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      boolean status_db = conn.getAutoCommit();
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      stmt.executeUpdate(sql);
      stmt.close();
      conn.commit();
      conn.setAutoCommit(status_db);
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
      result = false;
    } 
    return result;
  }
  
  public boolean updateSysmessage(String sql) throws Exception {
    boolean result = true;
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      stmt.executeUpdate(sql);
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
      result = false;
    } 
    return result;
  }
  
  public List getUserInfo(String logId) throws Exception {
    List result = null;
    try {
      StringBuffer hql = new StringBuffer(" SELECT distinct emp.empName,emp.empEnglishName,emp.userAccounts,emp.empSex,emp.userPassword, ");
      hql.append(" emp.userIsSuper,emp.userSuperBegin,emp.userSuperEnd,org.orgId,org.orgName,emp.empLeaderId, ");
      hql.append(" emp.empLeaderName,emp.browseRange,emp.browseRangeName,emp.userOrderCode, ");
      hql.append(" emp.empDuty,emp.keyValidate,emp.keySerial,emp.userSimpleName,emp.mailboxSize, ");
      hql.append(" emp.netDiskSize,emp.signatureImgName,emp.signatureImgSaveName,emp.sidelineOrg,emp.sidelineOrgName,emp.deptLeader,emp.empPosition,emp.empPositionId,emp.empPositionOtherId,");
      hql.append(" emp.grantRange,emp.grantRangeName,emp.mailPost,emp.empId,emp.operate");
      hql.append(" FROM com.js.oa.audit.po.AuditEmployeePO emp,com.js.system.vo.organizationmanager.OrganizationVO org ");
      hql.append(" WHERE emp.orgId=org.orgId and emp.logId=" + logId);
      begin();
      result = this.session.createQuery(hql.toString()).list();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public String getUserDefaultDomain(String empId) throws Exception {
    String domain = null;
    begin();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select mailDomain from oa_empMailDomain where emp_id=" + empId + " and defaultdomain=1");
      if (rs.next())
        domain = rs.getString(1); 
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (rs != null)
        rs.close(); 
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
      this.session.close();
    } 
    return domain;
  }
  
  public List getUserDomain(String empId) throws Exception {
    List<String> list = new ArrayList();
    begin();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select mailDomain from oa_empMailDomain where emp_id=" + empId + " order by defaultdomain desc");
      while (rs.next())
        list.add(rs.getString(1)); 
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (rs != null)
        rs.close(); 
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public Integer getUserNum() throws Exception {
    begin();
    Integer userNum = Integer.valueOf("0");
    try {
      Iterator<E> iter = this.session.createQuery("select count(user.empId) from com.js.system.vo.usermanager.EmployeeVO user where user.userIsDeleted=0 and (user.userAccounts is not null and user.userAccounts <> ' ') and user.userIsFormalUser=1").iterate();
      if (iter.hasNext())
        userNum = Integer.valueOf(iter.next().toString()); 
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return userNum;
  }
  
  public Boolean getRtxLogin(String userId) throws Exception {
    Boolean result = Boolean.FALSE;
    begin();
    try {
      Iterator it = this.session.createQuery("select vo.rtxIsLogin from com.js.system.vo.usermanager.EmployeeVO vo where vo.empId=" + userId).iterate();
      if (it.hasNext()) {
        Object obj = it.next();
        if (obj == null || "1".equals(obj.toString()))
          result = Boolean.TRUE; 
      } 
    } catch (Exception ex) {
      this.session.close();
      throw ex;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public Integer getIsChangePwd(String userId) throws Exception {
    Integer result = Integer.valueOf("0");
    begin();
    try {
      Iterator it = this.session.iterate("select vo.isChangePwd from com.js.system.vo.usermanager.EmployeeVO vo where vo.empId=" + userId);
      if (it.hasNext()) {
        Object obj = it.next();
        if (obj != null && !obj.toString().equals("") && !obj.toString().equalsIgnoreCase("null"))
          result = Integer.valueOf(obj.toString()); 
      } 
    } catch (Exception ex) {
      this.session.close();
      throw ex;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public Integer getMailBoxSize(String userId) throws Exception {
    Integer result = Integer.valueOf("0");
    begin();
    try {
      Iterator it = this.session.iterate("select vo.mailboxSize from com.js.system.vo.usermanager.EmployeeVO vo where vo.empId=" + userId);
      if (it.hasNext()) {
        Object obj = it.next();
        if (obj != null && !obj.toString().equals("") && !obj.toString().equalsIgnoreCase("null"))
          result = Integer.valueOf(obj.toString()); 
      } 
    } catch (Exception ex) {
      this.session.close();
      throw ex;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public Integer getNetDiskSize(String userId) throws Exception {
    Integer result = Integer.valueOf("0");
    begin();
    try {
      Iterator it = this.session.iterate("select vo.netDiskSize from com.js.system.vo.usermanager.EmployeeVO vo where vo.empId=" + userId);
      if (it.hasNext()) {
        Object obj = it.next();
        if (obj != null && !obj.toString().equals("") && !obj.toString().equalsIgnoreCase("null"))
          result = Integer.valueOf(obj.toString()); 
      } 
      this.session.close();
    } catch (Exception ex) {
      this.session.close();
      throw ex;
    } finally {}
    return result;
  }
  
  public String getSignature(String userId) throws Exception {
    String signature = "";
    begin();
    try {
      Iterator<String> it = this.session.createQuery("select emp.signatureImgSaveName from com.js.system.vo.usermanager.EmployeeVO emp where emp.empId=:empId").setString("empId", userId).iterate();
      if (it.hasNext()) {
        signature = it.next();
        if (signature == null)
          signature = ""; 
      } 
      this.session.close();
    } catch (Exception ex) {
      this.session.close();
      throw ex;
    } 
    return signature;
  }
  
  public String getUserName(Long empId) throws Exception {
    String userName = "";
    try {
      begin();
      Query qu = this.session.createQuery("SELECT emp.userAccounts FROM com.js.system.vo.usermanager.EmployeeVO emp WHERE emp.empId=" + empId);
      userName = qu.list().get(0);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw e;
    } finally {
      this.session.close();
    } 
    return userName;
  }
  
  public EmployeeVO getEmployeeVO(Long userId) throws Exception {
    begin();
    EmployeeVO employeeVO = null;
    try {
      employeeVO = (EmployeeVO)this.session.load(EmployeeVO.class, userId);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return employeeVO;
  }
  
  public void addTblJilu(TblJilu tblJilu) throws Exception {
    begin();
    try {
      this.session.save(tblJilu);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void delTblJilu(TblJilu tblJilu) throws Exception {
    begin();
    try {
      this.session.delete(tblJilu);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public TblJilu findTblJiluByUsername(String username) throws Exception {
    begin();
    TblJilu tblJilu = new TblJilu();
    try {
      Query query = this.session.createQuery("select jl from com.active.e_uc.user.po.TblJilu jl where jl.userName like '" + username + "'");
      if (query.list().size() > 0)
        tblJilu = query.list().get(0); 
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return tblJilu;
  }
  
  public long exselAdd(EmployeeVO employeeVO, Long orgId, List<Long> roleIdList) throws Exception {
    Long empId = null;
    Connection conn = null;
    PreparedStatement pStmt = null;
    Statement stmt = null;
    List<Long> roleRightList = new ArrayList();
    ResultSet rs = null;
    begin();
    try {
      String duty = employeeVO.getEmpDuty();
      Iterator<E> it = this.session.createQuery("select po.dutyLevel from com.js.oa.hr.officemanager.po.DutyPO po where po.dutyName='" + 
          duty + 
          "' and po.domainId=" + 
          employeeVO.getDomainId()).iterate();
      if (it.hasNext()) {
        duty = it.next().toString();
        employeeVO.setEmpDutyLevel(duty);
      } else {
        employeeVO.setEmpDutyLevel("1000");
      } 
      employeeVO.setSkin("blue");
      if ("0".equals(employeeVO.getEmpLeaderId()))
        employeeVO.setEmpLeaderId(""); 
      empId = (Long)this.session.save(employeeVO);
      this.session.flush();
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pStmt = conn.prepareStatement("insert into JSDB.org_organization_user (ORG_ID,EMP_ID) values(?,?)");
      pStmt.setLong(1, orgId.longValue());
      pStmt.setLong(2, empId.longValue());
      pStmt.executeUpdate();
      for (int i = 0; i < roleIdList.size(); i++) {
        pStmt = conn.prepareStatement("insert into JSDB.org_user_role (EMP_ID,ROLE_ID) values(?,?)");
        pStmt.setLong(1, empId.longValue());
        pStmt.setLong(2, ((Long)roleIdList.get(i)).longValue());
        pStmt.executeUpdate();
        stmt = conn.createStatement();
        rs = stmt.executeQuery("select RIGHT_ID from JSDB.org_role_right where ROLE_ID =" + 
            (Long)roleIdList.get(i));
        while (rs.next()) {
          if (!roleRightList.contains(Long.valueOf(rs.getLong(1))))
            roleRightList.add(Long.valueOf(rs.getLong(1))); 
        } 
        rs.close();
      } 
      pStmt = conn.prepareStatement("insert into JSDB.org_rightscope(rightscope_id,emp_id,right_id,rightScopeType,rightScopeScope,rightScopeUser,rightScopeGroup,rightScope,domain_id) values(?,?,?,?,?,?,?,?,?)");
      long rightScopeSeq = getTableId(roleRightList.size()).longValue();
      for (int k = 0; k < roleRightList.size(); k++, rightScopeSeq++) {
        pStmt.setLong(1, rightScopeSeq);
        pStmt.setLong(2, empId.longValue());
        pStmt.setLong(3, ((Long)roleRightList.get(k)).longValue());
        pStmt.setString(4, "2");
        pStmt.setString(5, "");
        pStmt.setString(6, "");
        pStmt.setString(7, "");
        pStmt.setString(8, "");
        pStmt.setString(9, "0");
        pStmt.executeUpdate();
      } 
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      if (stmt != null)
        stmt.close(); 
      if (pStmt != null)
        pStmt.close(); 
      if (conn != null)
        conn.close(); 
      this.session.close();
    } 
    return empId.longValue();
  }
  
  public long exselAdd(EmployeeVO employeeVO, Long orgId, List<Long> roleIdList, String[] log) throws Exception {
    Long logId = Long.valueOf(0L);
    Connection conn = null;
    PreparedStatement pStmt = null;
    Statement stmt = null;
    List<Long> roleRightList = new ArrayList();
    ResultSet rs = null;
    begin();
    try {
      logId = Long.valueOf(saveLog(log, "导入用户"));
      String duty = employeeVO.getEmpDuty();
      Iterator<E> it = this.session.createQuery("select po.dutyLevel from com.js.oa.hr.officemanager.po.DutyPO po where po.dutyName='" + 
          duty + 
          "' and po.domainId=" + 
          employeeVO.getDomainId()).iterate();
      if (it.hasNext()) {
        duty = it.next().toString();
        employeeVO.setEmpDutyLevel(duty);
      } else {
        employeeVO.setEmpDutyLevel("1000");
      } 
      employeeVO.setSkin("blue");
      if ("0".equals(employeeVO.getEmpLeaderId()))
        employeeVO.setEmpLeaderId(""); 
      AuditEmployeePO po = (AuditEmployeePO)FillBean.transformOTO(employeeVO, AuditEmployeePO.class);
      po.setOrgId(String.valueOf(orgId));
      po.setLogId(logId.longValue());
      po.setOperate("insert");
      po.setEmpId(0L);
      this.session.save(po);
      this.session.flush();
      conn = (new DataSourceBase()).getDataSource().getConnection();
      for (int i = 0; i < roleIdList.size(); i++) {
        pStmt = conn.prepareStatement("insert into JSDB.audit_user_role (EMP_ID,ROLE_ID,log_Id) values(?,?,?)");
        pStmt.setLong(1, 0L);
        pStmt.setLong(2, ((Long)roleIdList.get(i)).longValue());
        pStmt.setString(3, String.valueOf(logId));
        pStmt.executeUpdate();
        stmt = conn.createStatement();
        rs = stmt.executeQuery("select RIGHT_ID from JSDB.org_role_right where ROLE_ID =" + 
            (Long)roleIdList.get(i));
        while (rs.next()) {
          if (!roleRightList.contains(Long.valueOf(rs.getLong(1))))
            roleRightList.add(Long.valueOf(rs.getLong(1))); 
        } 
        rs.close();
      } 
      pStmt = conn.prepareStatement("insert into JSDB.audit_rightscope(rightscope_id,emp_id,right_id,rightScopeType,rightScopeScope,rightScopeUser,rightScopeGroup,rightScope,domain_id,log_id) values(?,?,?,?,?,?,?,?,?,?)");
      long rightScopeSeq = getTableId(roleRightList.size()).longValue();
      for (int k = 0; k < roleRightList.size(); k++, rightScopeSeq++) {
        pStmt.setLong(1, rightScopeSeq);
        pStmt.setLong(2, 0L);
        pStmt.setLong(3, ((Long)roleRightList.get(k)).longValue());
        pStmt.setString(4, "2");
        pStmt.setString(5, "");
        pStmt.setString(6, "");
        pStmt.setString(7, "");
        pStmt.setString(8, "");
        pStmt.setString(9, "0");
        pStmt.setString(10, String.valueOf(logId));
        pStmt.executeUpdate();
      } 
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      if (stmt != null)
        stmt.close(); 
      if (pStmt != null)
        pStmt.close(); 
      if (conn != null)
        conn.close(); 
      this.session.close();
    } 
    return logId.longValue();
  }
  
  public void delById(long userid) throws Exception {
    begin();
    try {
      this.session.delete(this.session.get(EmployeeVO.class, Long.valueOf(userid)));
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void delById(String logId) throws Exception {
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      String sql = "DELETE FROM audit_employee where log_Id=" + logId;
      base.addBatch(sql);
      sql = "DELETE FROM audit_log where log_Id=" + logId;
      base.addBatch(sql);
      sql = "DELETE FROM audit_user_role where log_Id=" + logId;
      base.addBatch(sql);
      sql = "DELETE FROM audit_rightscope where log_Id=" + logId;
      base.addBatch(sql);
      base.executeBatch();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
  }
  
  public EmployeeVO getEmployeeVOByNumber(String empnumber) throws Exception {
    begin();
    EmployeeVO employeeVO = null;
    try {
      List<EmployeeVO> list = this.session.createQuery("select emp FROM com.js.system.vo.usermanager.EmployeeVO emp where emp.empNumber like '" + empnumber + "'").list();
      if (!list.isEmpty())
        employeeVO = list.get(0); 
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return employeeVO;
  }
  
  public String validateUserAndPassword(String username, String password) throws Exception {
    String longStr = "N";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = (new DataSourceBase()).getDataSource()
        .getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select * from org_employee emp where emp.useraccounts= '" + username + "'  and emp.USERPASSWORD= '" + password + "' and emp.USERISDELETED=0 and emp.DOMAIN_ID=0");
      if (rs.next())
        longStr = "Y"; 
    } catch (Exception e) {
      throw e;
    } finally {
      if (rs != null)
        rs.close(); 
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
    } 
    return longStr;
  }
  
  public String findUserRTXloginById(Long userId) throws Exception {
    EmployeeVO employeeVO = null;
    String rtxLogin = "0";
    begin();
    try {
      employeeVO = (EmployeeVO)this.session.get(EmployeeVO.class, userId);
      if (employeeVO != null)
        rtxLogin = employeeVO.getRtxIsLogin(); 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return rtxLogin;
  }
  
  public long findUserIdByUserAccount(String userAccount) throws Exception {
    Long userId = null;
    begin();
    try {
      System.out.println("userAccount:" + userAccount);
      userId = (Long)this.session.createQuery("select emp.empId FROM com.js.system.vo.usermanager.EmployeeVO emp where emp.userAccounts = '" + userAccount + "' and userIsDeleted=0").uniqueResult();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return userId.longValue();
  }
  
  public void activeAdd(EmployeeVO employeeVO, Long orgId) throws Exception {
    begin();
    Connection conn = null;
    PreparedStatement pStmt = null;
    try {
      Long empId = (Long)this.session.save(employeeVO);
      conn = (new DataSourceBase()).getDataSource()
        .getConnection();
      pStmt = conn
        .prepareStatement("insert into JSDB.org_organization_user (ORG_ID,EMP_ID) values(?,?)");
      pStmt.setLong(1, orgId.longValue());
      pStmt.setLong(2, empId.longValue());
      pStmt.executeUpdate();
    } catch (Exception e) {
      throw e;
    } finally {
      if (pStmt != null)
        pStmt.close(); 
      if (conn != null)
        conn.close(); 
      this.session.close();
    } 
  }
  
  public List<EmployeeVO> selectAllEmployee() throws Exception {
    List<EmployeeVO> list = new ArrayList<EmployeeVO>();
    begin();
    try {
      list = this.session.createQuery("select emp FROM com.js.system.vo.usermanager.EmployeeVO emp where emp.userIsDeleted =0 and emp.userAccounts is not null and emp.userIsFormalUser =1 and emp.domainId like '0'").list();
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public Long getUserOrgId(Long empId) throws Exception {
    Long ORG_ID = null;
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select ORG_ID from org_organization_user where EMP_ID=" + empId);
      if (rs.next())
        ORG_ID = Long.valueOf(rs.getLong(1)); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        conn.close(); 
      throw ex;
    } 
    return ORG_ID;
  }
  
  public List<String> selectAllUserAccounts() throws Exception {
    List<String> list = new ArrayList<String>();
    begin();
    try {
      list = this.session.createQuery("select emp.userAccounts FROM com.js.system.vo.usermanager.EmployeeVO emp where emp.userIsDeleted =0 and emp.userAccounts is not null and emp.userIsFormalUser =1 and emp.domainId like '0' and userIsDeleted=0").list();
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List getUserIdByEmpName(String name) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      list = this.session.createQuery(" from com.js.system.vo.usermanager.EmployeeVO emp where emp.userIsDeleted =0  and emp.userIsFormalUser =1  and emp.empName like '%" + name + "%'").list();
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List<EmployeeVO> getUsersByUserName(String userName) throws Exception {
    begin();
    try {
      List<EmployeeVO> list = this.session.createQuery(" from com.js.system.vo.usermanager.EmployeeVO emp where emp.empName = '" + userName + "'").list();
      this.session.flush();
      return list;
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public EmployeeVO getEmployeeByUserName(String userName) throws Exception {
    begin();
    try {
    
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    this.session.close();
    this.session = null;
    this.transaction = null;
    return new EmployeeVO();
  }
  
  public EmployeeVO getEmpByid(Long empId) throws Exception {
    EmployeeVO employeeVO = new EmployeeVO();
    try {
      begin();
      employeeVO = (EmployeeVO)this.session.get(EmployeeVO.class, empId);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw e;
    } finally {
      this.session.close();
    } 
    return employeeVO;
  }
  
  public void updateUserByEmpChange(EmployeeChangePO employeeVO) throws Exception {
    begin();
    try {
      EmployeeVO changeVO = (EmployeeVO)this.session.get(EmployeeVO.class, employeeVO.getEmpChangeEmpId());
      changeVO.setEmpId(employeeVO.getEmpChangeEmpId().longValue());
      this.session.update(changeVO);
      this.session.flush();
    } catch (Exception exception) {
    
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void update(EmployeeVO employeeVO) throws Exception {
    begin();
    try {
      this.session.update(employeeVO);
      this.session.flush();
    } catch (Exception exception) {
    
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void updateRelationEmp(String empId) throws Exception {
    try {
      begin();
      if (empId != null && !empId.equals("") && !empId.equals("null") && !empId.equals("0")) {
        String empIdString = empId.substring(1, empId.length() - 1);
        String[] ids = empIdString.split("\\$\\$");
        for (int i = 0; i < ids.length; i++) {
          EmployeeVO employeeVO = (EmployeeVO)this.session.get(EmployeeVO.class, Long.valueOf(ids[i]));
          if (employeeVO.getDeptLeader().equals("0")) {
            employeeVO.setEmpId(Long.parseLong(ids[i]));
            employeeVO.setDeptLeader("1");
            this.session.update(employeeVO);
            this.session.flush();
          } 
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public List searchOrgIdByUserId(String userId) throws Exception {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    List<Long> list = new ArrayList();
    try {
      begin();
      String searchSQL = "";
      String[] userIds = userId.split("\\$\\$");
      searchSQL = String.valueOf(searchSQL) + " where oo.EMP_ID in (";
      for (int i = 0; i < userIds.length; i++)
        searchSQL = String.valueOf(searchSQL) + userIds[i] + ","; 
      searchSQL = String.valueOf(searchSQL.substring(0, searchSQL.length() - 1)) + 
        ")";
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select oo.ORG_ID  from  org_organization_user oo " + searchSQL);
      while (rs.next())
        list.add(Long.valueOf(rs.getLong(1))); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (rs != null)
        rs.close(); 
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List selectEmpIdByOrgIds(String orgIds) throws Exception {
    List list = new ArrayList();
    List<E> list1 = new ArrayList();
    try {
      begin();
      String[] orgs = orgIds.split(",");
      String seachOrg = "";
      String orgId = "";
      if (orgs.length > 0) {
        seachOrg = " and (";
        for (int i = 0; i < orgs.length; i++) {
          orgId = orgs[i].toString();
          seachOrg = String.valueOf(seachOrg) + "org.orgIdString like '%$" + orgId + "$%' or ";
        } 
        seachOrg = seachOrg.substring(0, seachOrg.lastIndexOf("or"));
        seachOrg = String.valueOf(seachOrg) + " )";
      } 
      String orgIdss = "";
      list1 = this.session.createQuery("select org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org  where org.orgStatus=0 " + seachOrg).list();
      if (!list1.isEmpty())
        for (int i = 0; i < list1.size(); i++)
          orgIdss = String.valueOf(orgIdss) + list1.get(i).toString() + ",";  
      orgIdss = orgIdss.substring(0, orgIdss.length() - 1);
      list = this.session.createQuery("select emp.empId FROM com.js.system.vo.usermanager.EmployeeVO emp join emp.organizations org where emp.userIsDeleted=0 and emp.userIsActive=1 and org.orgId in ( " + orgIdss + " ) ").list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List selectEmpIdsAndOrgIds(String userIds) throws Exception {
    List list = new ArrayList();
    try {
      begin();
      list = this.session.createQuery("select emp.empId,emp.empName,org.orgId FROM com.js.system.vo.usermanager.EmployeeVO emp join emp.organizations org where emp.userIsDeleted=0 and emp.userIsActive=1 and  emp.empId in ( " + userIds + " ) ").list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List selectEmpForRecord() throws Exception {
    List list = new ArrayList();
    try {
      begin();
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      Date nowDate = new Date();
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(nowDate);
      calendar.add(5, -1);
      calendar.set(11, 0);
      calendar.set(12, 1);
      String beginDate = format.format(calendar.getTime());
      calendar.set(11, 23);
      calendar.set(12, 59);
      String endDate = format.format(calendar.getTime());
      list = this.session.createQuery("select emp.empId,org.orgId FROM com.js.system.vo.usermanager.EmployeeVO emp join emp.organizations org where emp.userIsDeleted=0 and emp.userIsActive=1 and  emp.empId not in ( select po.userId from com.js.oa.hr.kq.po.KqRecordPO po where  po.dutyTime >= '" + beginDate + "' and  po.dutyTime <= '" + endDate + "' ) ").list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List selectMyUnderling(String userId) throws Exception {
    List list = new ArrayList();
    try {
      begin();
      list = this.session.createQuery("select po.empId,po.empName from com.js.system.vo.usermanager.EmployeeVO po where po.userIsDeleted=0 and po.userIsActive=1 and po.empLeaderId like '%$" + userId + "$%'").list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List getUserIdAndNameByEmpNumber(String empNumber) throws Exception {
    List list = new ArrayList();
    try {
      begin();
      list = this.session.createQuery("select po.empId ,po.empName from com.js.system.vo.usermanager.EmployeeVO po where po.empNumber = '" + empNumber + "'").list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public Integer canImportUserNum(String domainId) {
    int regUserNum = 0, buyUserNum = 0;
    try {
      begin();
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpIter = this.session.iterate("select count(user.empId) from com.js.system.vo.usermanager.EmployeeVO user where user.userIsDeleted=0 and (user.userAccounts is not null and user.userAccounts<>'admin' and user.userAccounts<>'') and user.userIsFormalUser=1 and user.empId>0  and user.domainId=" + domainId);
      } else if (databaseType.indexOf("oracle") >= 0) {
        tmpIter = this.session.iterate("select count(user.empId) from com.js.system.vo.usermanager.EmployeeVO user where user.userIsDeleted=0 and (user.userAccounts is not null and user.userAccounts<>'admin') and user.userIsFormalUser=1 and user.empId>0 and user.domainId=" + domainId);
      } else {
        tmpIter = this.session.iterate("select count(user.empId) from com.js.system.vo.usermanager.EmployeeVO user where user.userIsDeleted=0 and (user.userAccounts is not null and user.userAccounts<>'admin' and user.userAccounts<>'') and user.userIsFormalUser=1 and user.empId>0  and user.domainId=" + domainId);
      } 
      if (tmpIter.hasNext()) {
        Object obj = tmpIter.next();
        regUserNum = (obj == null) ? 0 : Integer.parseInt(obj.toString());
      } 
      Iterator tmpIter = this.session.iterate("select a.userNum from com.js.system.vo.organizationmanager.DomainVO a where a.id=" + domainId + " and a.inUse=1");
      if (tmpIter.hasNext()) {
        Object obj = tmpIter.next();
        buyUserNum = (obj == null) ? 0 : Integer.parseInt(obj.toString());
      } 
      this.session.close();
    } catch (Exception ex) {
      try {
        this.session.close();
      } catch (Exception e) {
        e.printStackTrace();
      } 
      ex.printStackTrace();
    } 
    return new Integer(buyUserNum - regUserNum);
  }
  
  public void auditEmp(String logId, String flag, String checkEmpid, String checkEmpName) throws Exception {
    try {
      begin();
      AuditLog auditLog = (AuditLog)this.session.load(AuditLog.class, Long.valueOf(logId));
      auditLog.setCheckEmpid(Long.valueOf(checkEmpid));
      auditLog.setCheckEmpname(checkEmpName);
      auditLog.setCheckTime(new Date());
      auditLog.setIschecked(Integer.valueOf(1));
      if ("yes".equals(flag)) {
        auditLog.setAuditStatus(Integer.valueOf(1));
      } else {
        auditLog.setAuditStatus(Integer.valueOf(0));
      } 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
  }
  
  public AuditLog auditLog(String logId) throws Exception {
    AuditLog auditLog = new AuditLog();
    try {
      begin();
      auditLog = (AuditLog)this.session.load(AuditLog.class, Long.valueOf(logId));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return auditLog;
  }
  
  public AuditEmployeePO getEmployeePO(String logId) throws Exception {
    AuditEmployeePO po = new AuditEmployeePO();
    try {
      begin();
      Query query = this.session.createQuery("select po from com.js.oa.audit.po.AuditEmployeePO po where po.logId=" + logId);
      List<AuditEmployeePO> list = query.list();
      if (list.size() > 0)
        po = list.get(0); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return po;
  }
  
  public long saveLog(String[] log, String caozuo) throws HibernateException {
    Long logId = Long.valueOf(0L);
    try {
      AuditLog auditLog = new AuditLog();
      auditLog.setSubmitEmpid(Long.valueOf(log[0]));
      auditLog.setSubmitEmpname(log[1]);
      auditLog.setSubmitOrgid(Long.valueOf(log[2]));
      auditLog.setSubmitTime(new Date());
      auditLog.setAuditModule(Long.valueOf(log[3]));
      auditLog.setAuditStatus(Integer.valueOf(0));
      auditLog.setIschecked(Integer.valueOf(0));
      if (log.length > 4 && "autoAudit".equals(log[4])) {
        Date ts = Timestamp.valueOf((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
        auditLog.setAuditStatus(Integer.valueOf(1));
        auditLog.setIschecked(Integer.valueOf(1));
        auditLog.setCheckEmpid(new Long(0L));
        auditLog.setCheckEmpname("系统自动审核");
        auditLog.setCheckTime(ts);
        logId = (Long)this.session.save(auditLog);
      } else {
        logId = (Long)this.session.save(auditLog);
        AuditMsRemindEJBBean msRemindBeann = new AuditMsRemindEJBBean();
        msRemindBeann.auditMsRemind(Long.valueOf(log[0]).longValue(), log[2], log[1], 
            1, 1, new Date(), "审计提醒:" + log[1] + caozuo, "audit", logId.longValue(), "/jsoa/AuditUserAddAction.do?status=active&action=update&logId=" + logId + "&disabled=disabled&comeflag=tixing");
      } 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return logId.longValue();
  }
  
  public String getWeiXinLoginCheckInfo(String useraccounts) throws NumberFormatException, HibernateException {
    String error = "0";
    int result = 0;
    try {
      DbOpt db = new DbOpt();
      String count = db.executeQueryToStr("select count(*) from org_employee emp where (emp.weixinPost='1' and emp.weixinPost is not null and emp.userIsDeleted=0)  and emp.userAccounts='" + useraccounts + "'");
      db.close();
      result = Integer.valueOf(count).intValue();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    if (result == 0) {
      result = 1;
      return "1";
    } 
    result = 0;
    try {
      DbOpt db = new DbOpt();
      String count = db.executeQueryToStr("select count(*) from org_employee emp where (emp.weixinPost='1' and emp.weixinPost is not null and emp.userIsDeleted=0)");
      db.close();
      result = Integer.valueOf(count).intValue();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    if (result > SystemCommon.getWeixinUserNum()) {
      result = 2;
      return "2";
    } 
    result = 0;
    return error;
  }
  
  public String getDingLoginCheckInfo(String useraccounts) {
    String result = "0";
    try {
      DbOpt db = new DbOpt();
      result = db.executeQueryToStr("select count(*) from org_employee where dingdingpost='1' and dingdingpost is not null and userIsDeleted=0 and userIsActive=1 and userAccounts='" + useraccounts + "'");
      db.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
}
