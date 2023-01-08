package com.js.system.bean.organizationmanager;

import com.js.oa.audit.bean.AuditMsRemindEJBBean;
import com.js.oa.audit.po.AuditLog;
import com.js.oa.audit.po.OrganizationPO;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.vo.organizationmanager.DomainVO;
import com.js.system.vo.organizationmanager.OrganizationVO;
import com.js.system.vo.organizationmanager.SyncRTXVO;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.FillBean;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;

public class OrganizationEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public void add(OrganizationVO organizationVO, String currentOrderCode, String parentIdString, Integer sort) throws Exception {
    long parentId = organizationVO.getOrgParentOrgId();
    try {
      begin();
      if (organizationVO.getOrgManagerEmpName() == null) {
        organizationVO.setOrgManagerEmpName("");
        organizationVO.setOrgManagerEmpId("");
      } 
      Long orgId = (Long)this.session.save(organizationVO);
      int orderCode = 100000;
      String orgIdString = "";
      List<Integer> list = null;
      if ("-1".equals(currentOrderCode)) {
        orderCode = 500000;
      } else if (sort.intValue() == 0) {
        Query query = this.session.createQuery("SELECT MAX(org.orgOrderCode) FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE org.orgParentOrgId=" + parentId + " AND org.orgOrderCode<" + currentOrderCode);
        list = query.list();
        if (list == null || (list.size() == 1 && list.get(0) == null)) {
          orderCode = Integer.parseInt(currentOrderCode) - 5000;
        } else {
          int intValue = ((Integer)list.get(0)).intValue();
          if (intValue == 0)
            intValue = 100000; 
          orderCode = (intValue + Integer.parseInt(currentOrderCode)) / 2;
        } 
      } else {
        Query query = this.session.createQuery("SELECT MIN(org.orgOrderCode) FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE org.orgParentOrgId=" + parentId + " AND org.orgOrderCode>" + currentOrderCode);
        list = query.list();
        if (list == null || (list.size() == 1 && list.get(0) == null)) {
          orderCode = Integer.parseInt(currentOrderCode) + 5000;
        } else {
          orderCode = (((Integer)list.get(0)).intValue() + Integer.parseInt(currentOrderCode)) / 2;
        } 
      } 
      organizationVO.setOrgOrderCode(orderCode);
      String orgNameString = "";
      int orgLevel = -1;
      if (parentId != 0L) {
        OrganizationVO org = (OrganizationVO)this.session.load(
            OrganizationVO.class, new Long(parentId));
        org.setOrgHasJunior(1);
        orgLevel = org.getOrgLevel();
        orgIdString = String.valueOf(org.getOrgIdString()) + "_" + orderCode + "$" + orgId.toString() + "$";
        orgNameString = String.valueOf(org.getOrgNameString()) + "." + organizationVO.getOrgName();
      } else {
        orgIdString = "_500000$-1$_" + orderCode + "$" + orgId.toString() + "$";
        orgNameString = organizationVO.getOrgName();
      } 
      organizationVO.setOrgIdString(orgIdString);
      organizationVO.setOrgNameString(orgNameString);
      organizationVO.setOrgLevel(++orgLevel);
      SyncRTXVO rtxVO = new SyncRTXVO();
      rtxVO.setOrgId(orgId);
      rtxVO.setDataType(new Byte("0"));
      rtxVO.setDataOpr(new Byte("0"));
      this.session.save(rtxVO);
      this.session.flush();
    } catch (HibernateException e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void add(OrganizationVO organizationVO, String currentOrderCode, String parentIdString, Integer sort, String[] log) throws Exception {
    long parentId = organizationVO.getOrgParentOrgId();
    try {
      begin();
      Long logId = Long.valueOf(saveLog(log, "新增组织机构“" + organizationVO.getOrgName() + "”"));
      if (organizationVO.getOrgManagerEmpName() == null) {
        organizationVO.setOrgManagerEmpName("");
        organizationVO.setOrgManagerEmpId("");
      } 
      int orderCode = 100000;
      String orgIdString = "";
      List<Integer> list = null;
      if ("-1".equals(currentOrderCode)) {
        orderCode = 500000;
      } else if (sort.intValue() == 0) {
        Query query = this.session.createQuery("SELECT MAX(org.orgOrderCode) FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE org.orgParentOrgId=" + parentId + " AND org.orgOrderCode<" + currentOrderCode);
        list = query.list();
        if (list == null || (list.size() == 1 && list.get(0) == null)) {
          orderCode = Integer.parseInt(currentOrderCode) - 5000;
        } else {
          int intValue = ((Integer)list.get(0)).intValue();
          if (intValue == 0)
            intValue = 100000; 
          orderCode = (intValue + Integer.parseInt(currentOrderCode)) / 2;
        } 
      } else {
        Query query = this.session.createQuery("SELECT MIN(org.orgOrderCode) FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE org.orgParentOrgId=" + parentId + " AND org.orgOrderCode>" + currentOrderCode);
        list = query.list();
        if (list == null || (list.size() == 1 && list.get(0) == null)) {
          orderCode = Integer.parseInt(currentOrderCode) + 5000;
        } else {
          orderCode = (((Integer)list.get(0)).intValue() + Integer.parseInt(currentOrderCode)) / 2;
        } 
      } 
      orgIdString = String.valueOf(parentIdString) + "_" + orderCode + "$" + "orgId" + "$";
      if (parentId == 0L)
        orgIdString = "_500000$-1$" + orgIdString; 
      organizationVO.setOrgOrderCode(orderCode);
      organizationVO.setOrgIdString(orgIdString);
      String orgNameString = "";
      int orgLevel = -1;
      if (parentId != 0L) {
        OrganizationVO org = (OrganizationVO)this.session.load(
            OrganizationVO.class, new Long(parentId));
        org.setOrgHasJunior(1);
        orgLevel = org.getOrgLevel();
        orgNameString = String.valueOf(org.getOrgNameString()) + "." + organizationVO.getOrgName();
      } else {
        orgNameString = organizationVO.getOrgName();
      } 
      organizationVO.setOrgNameString(orgNameString);
      organizationVO.setOrgLevel(++orgLevel);
      OrganizationPO organizationPO = (OrganizationPO)FillBean.transformOTO(organizationVO, OrganizationPO.class);
      organizationPO.setOrgId(Long.valueOf(-100L));
      organizationPO.setLogId((String)logId);
      organizationPO.setOperate("insert");
      this.session.save(organizationPO);
      this.session.flush();
    } catch (HibernateException e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public boolean isDeptContainEmp(String empId, String orgId, EmployeeVO employee) {
    boolean flag = false;
    String in = "";
    String[] empIds = (String[])null;
    StringBuffer sb = new StringBuffer("");
    try {
      if (empId.indexOf("$") >= 0) {
        empId = empId.substring(1, empId.length() - 1);
        empIds = empId.split("\\$\\$");
        if (empIds != null && empIds.length > 0)
          for (int i = 0; i < empIds.length; i++)
            sb.append(String.valueOf(empIds[i]) + ",");  
        in = sb.toString().substring(0, sb.toString().length() - 1);
        String sql = "FROM EmployeeOrgVO  e where e.empId in(" + in + ") and e.orgId='" + orgId + "'";
        List list = this.session.createQuery(sql).list();
        if (empIds != null && empIds.length > 0)
          for (int i = 0; i < empIds.length; i++) {
            if (list != null && list.size() > 0 && empIds[i].equals(String.valueOf(employee.getEmpId())))
              flag = true; 
          }  
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return flag;
  }
  
  public void update(OrganizationVO organizationVO, String currentOrderCode, String modifiedParentIdString, Integer sort, String hasChanged) throws Exception {
    long modifiedParentId = organizationVO.getOrgParentOrgId();
    String modifiedName = organizationVO.getOrgName();
    long originalParentId = 0L;
    int originalOrgLevel = 0;
    String originalIdString = "";
    String originalNameString = "";
    try {
      int modifiedLevel;
      begin();
      Long orgId = organizationVO.getOrgId();
      SyncRTXVO rtxVO = new SyncRTXVO();
      rtxVO.setOrgId(orgId);
      rtxVO.setDataType(new Byte("0"));
      rtxVO.setDataOpr(new Byte("1"));
      this.session.save(rtxVO);
      OrganizationVO modifiedOrgVO = (OrganizationVO)this.session.load(OrganizationVO.class, orgId);
      originalParentId = modifiedOrgVO.getOrgParentOrgId();
      originalOrgLevel = modifiedOrgVO.getOrgLevel();
      originalIdString = modifiedOrgVO.getOrgIdString();
      originalNameString = modifiedOrgVO.getOrgNameString();
      String oriManagerName = modifiedOrgVO.getOrgManagerEmpName();
      String oriManagerId = modifiedOrgVO.getOrgManagerEmpId();
      if (oriManagerName == null)
        oriManagerName = ""; 
      modifiedOrgVO.setOrgName(organizationVO.getOrgName());
      modifiedOrgVO.setOrgDescripte(organizationVO.getOrgDescripte());
      modifiedOrgVO.setOrgParentOrgId(modifiedParentId);
      modifiedOrgVO.setOrgChannelType(organizationVO.getOrgChannelType());
      modifiedOrgVO.setOrgChannelUrl(organizationVO.getOrgChannelUrl());
      modifiedOrgVO.setLastupdate(organizationVO.getLastupdate());
      if (!oriManagerName.equals(organizationVO.getOrgManagerEmpName())) {
        modifiedOrgVO.setOrgManagerEmpId(organizationVO
            .getOrgManagerEmpId());
        modifiedOrgVO.setOrgManagerEmpName(organizationVO
            .getOrgManagerEmpName());
        Iterator<EmployeeVO> userIt = modifiedOrgVO.getEmployees().iterator();
        while (userIt.hasNext()) {
          EmployeeVO employee = userIt.next();
          if (!isDeptContainEmp(organizationVO.getOrgManagerEmpId(), String.valueOf(orgId), employee)) {
            employee.setEmpLeaderId(organizationVO.getOrgManagerEmpId());
            employee.setEmpLeaderName(organizationVO.getOrgManagerEmpName());
            continue;
          } 
          employee.setEmpLeaderId("");
          employee.setEmpLeaderName("");
        } 
      } 
      modifiedOrgVO.setOrgHasChannel(organizationVO.getOrgHasChannel());
      modifiedOrgVO.setOrgSimpleName(organizationVO.getOrgSimpleName());
      modifiedOrgVO.setOrgSerial(organizationVO.getOrgSerial());
      modifiedOrgVO.setOrgType(organizationVO.getOrgType());
      List<Integer> list = null;
      int orderCode = modifiedOrgVO.getOrgOrderCode();
      if ("1".equals(hasChanged))
        if ("-1".equals(currentOrderCode)) {
          orderCode = 500000;
        } else if (sort.intValue() == 0) {
          Query query = this.session.createQuery("SELECT MAX(org.orgOrderCode) FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE org.orgParentOrgId=" + 
              modifiedParentId + 
              " AND org.orgOrderCode<" + 
              currentOrderCode);
          list = query.list();
          if (list == null || (list.size() == 1 && list.get(0) == null)) {
            orderCode = Integer.parseInt(currentOrderCode) - 5000;
          } else {
            int intValue = ((Integer)list.get(0)).intValue();
            if (intValue == 0)
              intValue = 100000; 
            orderCode = (intValue + 
              Integer.parseInt(currentOrderCode)) / 2;
          } 
        } else {
          Query query = this.session.createQuery("SELECT MIN(org.orgOrderCode) FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE org.orgParentOrgId=" + 
              modifiedParentId + 
              " AND org.orgOrderCode>" + 
              currentOrderCode);
          list = query.list();
          if (list == null || (list.size() == 1 && list.get(0) == null)) {
            orderCode = Integer.parseInt(currentOrderCode) + 5000;
          } else {
            orderCode = (((Integer)list.get(0)).intValue() + 
              Integer.parseInt(currentOrderCode)) / 2;
          } 
        }  
      modifiedOrgVO.setOrgOrderCode(orderCode);
      OrganizationVO org = null;
      String modifiedIdString = "", modifiedNameString = "", parentNameString = "";
      modifiedParentIdString = "";
      if (modifiedParentId != 0L) {
        org = (OrganizationVO)this.session.load(OrganizationVO.class, 
            new Long(modifiedParentId));
        modifiedParentIdString = org.getOrgIdString();
        parentNameString = org.getOrgNameString();
        modifiedLevel = org.getOrgLevel() + 1;
        org.setOrgHasJunior(1);
      } else {
        modifiedLevel = 0;
      } 
      modifiedOrgVO.setOrgLevel(modifiedLevel);
      int levelDiff = modifiedLevel - originalOrgLevel;
      if (parentNameString.equals("")) {
        modifiedNameString = modifiedName;
      } else {
        modifiedNameString = String.valueOf(parentNameString) + "." + modifiedName;
      } 
      modifiedIdString = String.valueOf(modifiedParentIdString) + "_" + orderCode + "$" + orgId + "$";
      if (modifiedParentId == 0L)
        modifiedIdString = "_500000$-1$" + modifiedIdString; 
      modifiedOrgVO.setOrgIdString(modifiedIdString);
      modifiedOrgVO.setOrgNameString(modifiedNameString);
      list = this.session.createQuery(
          "SELECT org FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE org.orgIdString LIKE '%$" + 
          orgId + 
          "$%' AND org.orgId<>" + orgId).list();
      int parentIdStringlen = originalIdString.length();
      modifiedParentIdString = modifiedIdString;
      int parentNameStringlen = originalNameString.length();
      parentNameString = modifiedNameString;
      for (int i = 0; i < list.size(); i++) {
        org = (OrganizationVO)list.get(i);
        modifiedIdString = org.getOrgIdString();
        modifiedNameString = org.getOrgNameString();
        modifiedIdString = String.valueOf(modifiedParentIdString) + modifiedIdString.substring(parentIdStringlen);
        modifiedNameString = String.valueOf(parentNameString) + modifiedNameString.substring(parentNameStringlen);
        org.setOrgIdString(modifiedIdString);
        org.setOrgNameString(modifiedNameString);
        org.setOrgLevel(org.getOrgLevel() + levelDiff);
        this.session.update(org);
      } 
      this.session.update(modifiedOrgVO);
      if (modifiedParentId != originalParentId) {
        int has = ((Integer)this.session.iterate("SELECT COUNT(org.orgId) FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE org.orgParentOrgId=" + 
            originalParentId).next())
          .intValue();
        if (has == 0) {
          org = (OrganizationVO)this.session.load(OrganizationVO.class, 
              new Long(originalParentId));
          org.setOrgHasJunior(0);
          this.session.update(org);
        } 
      } 
      this.session.flush();
    } catch (Exception e) {
      System.out.println("error to update organizaitn info at OrganizationEJB(update())");
      throw e;
    } finally {
      this.session.close();
    } 
  }
  
  public void update(OrganizationVO organizationVO, String currentOrderCode, String modifiedParentIdString, Integer sort, String hasChanged, String[] log) throws Exception {
    long modifiedParentId = organizationVO.getOrgParentOrgId();
    String modifiedName = organizationVO.getOrgName();
    long originalParentId = 0L;
    int originalOrgLevel = 0;
    String originalIdString = "";
    String originalNameString = "";
    try {
      int modifiedLevel;
      begin();
      Long orgId = organizationVO.getOrgId();
      OrganizationVO modifiedOrgVO = (OrganizationVO)this.session.load(OrganizationVO.class, orgId);
      Long logId = Long.valueOf(saveLog(log, "修改组织机构“" + modifiedOrgVO.getOrgName() + "”"));
      OrganizationPO organizationPO = (OrganizationPO)FillBean.transformOTO(modifiedOrgVO, OrganizationPO.class);
      originalParentId = modifiedOrgVO.getOrgParentOrgId();
      originalOrgLevel = modifiedOrgVO.getOrgLevel();
      originalIdString = modifiedOrgVO.getOrgIdString();
      originalNameString = modifiedOrgVO.getOrgNameString();
      String oriManagerName = modifiedOrgVO.getOrgManagerEmpName();
      String oriManagerId = modifiedOrgVO.getOrgManagerEmpId();
      if (oriManagerName == null)
        oriManagerName = ""; 
      organizationPO.setOrgName(organizationVO.getOrgName());
      organizationPO.setOrgDescripte(organizationVO.getOrgDescripte());
      organizationPO.setOrgParentOrgId(modifiedParentId);
      organizationPO.setOrgChannelType(organizationVO.getOrgChannelType());
      organizationPO.setOrgChannelUrl(organizationVO.getOrgChannelUrl());
      if (!oriManagerName.equals(organizationVO.getOrgManagerEmpName())) {
        organizationPO.setOrgManagerEmpId(organizationVO
            .getOrgManagerEmpId());
        organizationPO.setOrgManagerEmpName(organizationVO
            .getOrgManagerEmpName());
        Iterator<EmployeeVO> userIt = modifiedOrgVO.getEmployees().iterator();
        while (userIt.hasNext()) {
          EmployeeVO employee = userIt.next();
          if (!isDeptContainEmp(organizationVO.getOrgManagerEmpId(), String.valueOf(orgId), employee)) {
            employee.setEmpLeaderId(organizationVO.getOrgManagerEmpId());
            employee.setEmpLeaderName(organizationVO.getOrgManagerEmpName());
            continue;
          } 
          employee.setEmpLeaderId("");
          employee.setEmpLeaderName("");
        } 
      } 
      organizationPO.setOrgHasChannel(organizationVO.getOrgHasChannel());
      organizationPO.setOrgSimpleName(organizationVO.getOrgSimpleName());
      organizationPO.setOrgSerial(organizationVO.getOrgSerial());
      organizationPO.setOrgType(organizationVO.getOrgType());
      List<Integer> list = null;
      int orderCode = modifiedOrgVO.getOrgOrderCode();
      if ("1".equals(hasChanged))
        if ("-1".equals(currentOrderCode)) {
          orderCode = 500000;
        } else if (sort.intValue() == 0) {
          Query query = this.session.createQuery("SELECT MAX(org.orgOrderCode) FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE org.orgParentOrgId=" + 
              modifiedParentId + 
              " AND org.orgOrderCode<" + 
              currentOrderCode);
          list = query.list();
          if (list == null || (list.size() == 1 && list.get(0) == null)) {
            orderCode = Integer.parseInt(currentOrderCode) - 5000;
          } else {
            int intValue = ((Integer)list.get(0)).intValue();
            if (intValue == 0)
              intValue = 100000; 
            orderCode = (intValue + 
              Integer.parseInt(currentOrderCode)) / 2;
          } 
        } else {
          Query query = this.session.createQuery("SELECT MIN(org.orgOrderCode) FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE org.orgParentOrgId=" + 
              modifiedParentId + 
              " AND org.orgOrderCode>" + 
              currentOrderCode);
          list = query.list();
          if (list == null || (list.size() == 1 && list.get(0) == null)) {
            orderCode = Integer.parseInt(currentOrderCode) + 5000;
          } else {
            orderCode = (((Integer)list.get(0)).intValue() + 
              Integer.parseInt(currentOrderCode)) / 2;
          } 
        }  
      organizationPO.setOrgOrderCode(orderCode);
      OrganizationVO org = null;
      String modifiedIdString = "", modifiedNameString = "", parentNameString = "";
      modifiedParentIdString = "";
      if (modifiedParentId != 0L) {
        org = (OrganizationVO)this.session.load(OrganizationVO.class, 
            new Long(modifiedParentId));
        modifiedParentIdString = org.getOrgIdString();
        parentNameString = org.getOrgNameString();
        modifiedLevel = org.getOrgLevel() + 1;
        org.setOrgHasJunior(1);
      } else {
        modifiedLevel = 0;
      } 
      organizationPO.setOrgLevel(modifiedLevel);
      int levelDiff = modifiedLevel - originalOrgLevel;
      if (parentNameString.equals("")) {
        modifiedNameString = modifiedName;
      } else {
        modifiedNameString = String.valueOf(parentNameString) + "." + modifiedName;
      } 
      modifiedIdString = String.valueOf(modifiedParentIdString) + "_" + orderCode + "$" + orgId + "$";
      if (modifiedParentId == 0L)
        modifiedIdString = "_500000$-1$" + modifiedIdString; 
      organizationPO.setOrgIdString(modifiedIdString);
      organizationPO.setOrgNameString(modifiedNameString);
      organizationPO.setLogId((String)logId);
      organizationPO.setOperate("update");
      this.session.save(organizationPO);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("error to update organizaitn info at OrganizationEJB(update())");
      throw e;
    } finally {
      this.session.close();
    } 
  }
  
  public void updateRelationDept(String orgId, String empId, String empName, String type) throws Exception {
    try {
      begin();
      if (type.equals("1")) {
        Long orgIdTemp = Long.valueOf(orgId);
        OrganizationVO modifiedOrgVO = (OrganizationVO)this.session.load(OrganizationVO.class, orgIdTemp);
        if (modifiedOrgVO.getOrgManagerEmpId().indexOf("$" + empId + "$") < 0) {
          modifiedOrgVO.setOrgId(orgIdTemp);
          modifiedOrgVO.setOrgManagerEmpId("$" + empId + "$" + modifiedOrgVO.getOrgManagerEmpId());
          modifiedOrgVO.setOrgManagerEmpName(String.valueOf(empName) + "," + modifiedOrgVO.getOrgManagerEmpName());
          this.session.update(modifiedOrgVO);
          this.session.flush();
        } 
      } else {
        Long orgIdTemp = Long.valueOf(orgId);
        OrganizationVO modifiedOrgVO = (OrganizationVO)this.session.load(OrganizationVO.class, orgIdTemp);
        modifiedOrgVO.setOrgId(orgIdTemp);
        if (modifiedOrgVO.getOrgManagerEmpId() != null && !"".equals(modifiedOrgVO.getOrgManagerEmpId()) && modifiedOrgVO.getOrgManagerEmpId().indexOf("$" + empId + "$") >= 0) {
          String empIdString = modifiedOrgVO.getOrgManagerEmpId();
          empIdString = String.valueOf(empIdString.substring(0, empIdString.indexOf("$" + empId + "$"))) + empIdString.substring(empIdString.indexOf("$" + empId + "$") + empId.length() + 2, empIdString.length());
          modifiedOrgVO.setOrgManagerEmpId(empIdString);
          String empNameString = modifiedOrgVO.getOrgManagerEmpName();
          StringBuffer sb = new StringBuffer("");
          for (int i = 0; i < (empNameString.substring(0, empNameString.length() - 1).split(",")).length; i++) {
            if (!empNameString.substring(0, empNameString.length() - 1).split(",")[i].equals(empName))
              sb.append(String.valueOf(empNameString.substring(0, empNameString.length() - 1).split(",")[i]) + ","); 
          } 
          modifiedOrgVO.setOrgManagerEmpName(sb.toString());
          this.session.update(modifiedOrgVO);
          this.session.flush();
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
  }
  
  public void updateRootDept(OrganizationVO organizationVO, String[] log) throws Exception {
    try {
      begin();
      Long orgId = organizationVO.getOrgId();
      OrganizationVO modifiedOrgVO = (OrganizationVO)this.session.load(OrganizationVO.class, orgId);
      Long logId = Long.valueOf(saveLog(log, "修改组织机构“" + modifiedOrgVO.getOrgName() + "”"));
      OrganizationPO organizationPO = new OrganizationPO();
      organizationPO = (OrganizationPO)FillBean.transformOTO(modifiedOrgVO, OrganizationPO.class);
      organizationPO.setOrgId(orgId);
      organizationPO.setOrgName(organizationVO.getOrgName());
      organizationPO.setOrgSerial(organizationVO.getOrgSerial());
      organizationPO.setOrgSimpleName(organizationVO.getOrgSimpleName());
      organizationPO.setOrgDescripte(organizationVO.getOrgDescripte());
      organizationPO.setOrgChannelUrl(organizationVO.getOrgChannelUrl());
      organizationPO.setGuid(organizationVO.getGuid());
      Set booksBuy = new HashSet();
      organizationPO.setBooksBuy(booksBuy);
      Set booksDetail = new HashSet();
      organizationPO.setBooksDetail(booksDetail);
      organizationPO.setLogId((String)logId);
      organizationPO.setOperate("updateRoot");
      this.session.save(organizationPO);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
  }
  
  public void updateRootDept(OrganizationVO organizationVO) throws Exception {
    try {
      begin();
      Long orgId = organizationVO.getOrgId();
      OrganizationVO modifiedOrgVO = (OrganizationVO)this.session.load(OrganizationVO.class, orgId);
      modifiedOrgVO.setOrgId(orgId);
      modifiedOrgVO.setOrgName(organizationVO.getOrgName());
      modifiedOrgVO.setOrgSerial(organizationVO.getOrgSerial());
      modifiedOrgVO.setOrgSimpleName(organizationVO.getOrgSimpleName());
      modifiedOrgVO.setOrgDescripte(organizationVO.getOrgDescripte());
      modifiedOrgVO.setOrgChannelUrl(organizationVO.getOrgChannelUrl());
      modifiedOrgVO.setGuid(organizationVO.getGuid());
      this.session.update(modifiedOrgVO);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
  }
  
  public String delete(Long key, String operType) throws Exception {
    String name = "";
    Byte type = new Byte("0");
    Byte opr = new Byte("2");
    try {
      begin();
      List<OrganizationVO> list = this.session.createQuery("FROM OrganizationVO org WHERE org.orgIdString like '%$" + key + "$%'  order by org.orgIdString desc").list();
      if (operType != null && operType.equals("0")) {
        for (int i = 0; i < list.size(); i++) {
          OrganizationVO organizationVO = list.get(i);
          key = organizationVO.getOrgId();
          organizationVO.setOrgStatus(1);
          if (i == 0)
            name = organizationVO.getOrgName(); 
          if (!deleteEmployee(key))
            throw new Exception("error delete employee!"); 
          SyncRTXVO rtxVO = new SyncRTXVO();
          rtxVO.setOrgId(key);
          rtxVO.setDataType(type);
          rtxVO.setDataOpr(opr);
          this.session.save(rtxVO);
        } 
      } else {
        for (int i = 0; i < list.size(); i++) {
          OrganizationVO organizationVO = list.get(i);
          key = organizationVO.getOrgId();
          organizationVO.setOrgStatus(4);
          organizationVO.setOrgSerial("");
        } 
      } 
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {}
    this.session.close();
    return name;
  }
  
  public String delete(Long key, String operType, String[] log) throws Exception {
    String name = "";
    Long logId = null;
    try {
      begin();
      OrganizationVO modifiedOrgVO = (OrganizationVO)this.session.load(OrganizationVO.class, key);
      if ("0".equals(operType)) {
        logId = Long.valueOf(saveLog(log, "禁用组织机构“" + modifiedOrgVO.getOrgName() + "”"));
      } else {
        logId = Long.valueOf(saveLog(log, "删除组织机构“" + modifiedOrgVO.getOrgName() + "”"));
      } 
      OrganizationPO organizationPO = (OrganizationPO)FillBean.transformOTO(modifiedOrgVO, OrganizationPO.class);
      organizationPO.setOrgId(key);
      Map<Object, Object> map = new HashMap<Object, Object>();
      map.put("0", "jinyong-0");
      map.put("1", "shanchu-1");
      organizationPO.setOperate(map.get(operType).toString());
      organizationPO.setLogId((String)logId);
      this.session.save(organizationPO);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {}
    this.session.close();
    return name;
  }
  
  public String reDept(Long key) throws Exception {
    String name = "";
    try {
      begin();
      List<OrganizationVO> list = this.session.createQuery("FROM OrganizationVO org WHERE org.orgIdString like '%$" + key + "$%'  order by org.orgIdString desc").list();
      for (int i = 0; i < list.size(); i++) {
        OrganizationVO organizationVO = list.get(i);
        key = organizationVO.getOrgId();
        organizationVO.setOrgStatus(0);
        if (i == 0)
          name = organizationVO.getOrgName(); 
      } 
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {}
    this.session.close();
    return name;
  }
  
  public String reDept(Long key, String[] log) throws Exception {
    String name = "";
    try {
      begin();
      OrganizationVO modifiedOrgVO = (OrganizationVO)this.session.load(OrganizationVO.class, key);
      Long logId = Long.valueOf(saveLog(log, "恢复组织机构“" + modifiedOrgVO.getOrgName() + "”"));
      OrganizationPO organizationPO = (OrganizationPO)FillBean.transformOTO(modifiedOrgVO, OrganizationPO.class);
      organizationPO.setOrgId(key);
      organizationPO.setOperate("huifu");
      organizationPO.setLogId((String)logId);
      this.session.save(organizationPO);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {}
    this.session.close();
    return name;
  }
  
  public List getSubOrgs(Long parentId, String domainId) throws Exception {
    List orgArray = new ArrayList();
    try {
      begin();
      String sql = 
        "SELECT organization  FROM com.js.system.vo.organizationmanager.OrganizationVO organization WHERE organization.orgParentOrgId=" + 
        
        parentId.longValue() + 
        " AND organization.domainId=" + domainId + " and organization.orgStatus=0 ORDER BY organization.orgOrderCode";
      orgArray = this.session.createQuery(sql).list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return orgArray;
  }
  
  public List getPrivateList(String userId, String domainId) throws Exception {
    List privateList = new ArrayList();
    try {
      begin();
      String sql = "from PersonClassPO pcp where pcp.classType=0 and pcp.domainId=" + domainId + " and pcp.empId in(" + userId + "," + Character.MIN_VALUE + ")";
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
  
  public List getZWList(String domainId) throws Exception {
    return getZWList(domainId, "");
  }
  
  public List getZWList(String domainId, String corpId) throws Exception {
    List gwList = new ArrayList();
    try {
      begin();
      String hql = "";
      if (SystemCommon.getMultiDepart() == 1 && !"".equals(corpId))
        hql = "(d.corpId=0 or d.corpId=" + corpId + ") and "; 
      String sql = "from DutyPO d where " + hql + " d.domainId='" + domainId + "' order by d.dutyLevel";
      gwList = this.session.createQuery(sql).list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return gwList;
  }
  
  public List getGWList(String domainId) throws Exception {
    return getGWList(domainId, "");
  }
  
  public List getGWList(String domainId, String corpId) throws Exception {
    List gwList = new ArrayList();
    try {
      begin();
      String hqlString = "";
      if (SystemCommon.getMultiDepart() == 1 && !"".equals(corpId))
        hqlString = "(d.corpId=0 or d.corpId=" + corpId + ") and"; 
      String sql = "from StationPO d where " + hqlString + " d.domainId='" + domainId + "' order by d.name";
      gwList = this.session.createQuery(sql).list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return gwList;
  }
  
  public List getRootDept(String domainId) throws Exception {
    List gwList = new ArrayList();
    try {
      begin();
      String sql = "from OrganizationVO org where org.orgStatus<>1 and org.domainId=" + domainId + " and org.orgParentOrgId=-1";
      gwList = this.session.createQuery(sql).list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return gwList;
  }
  
  public List getGroupListByRange(String userId, String domainId, String range, String orgId) throws Exception {
    List orgList = new ArrayList();
    try {
      begin();
      String where = "";
      if (orgId.equals("") || orgId == null || orgId.equals("null")) {
        where = " or 1=1 ";
      } else {
        orgId = orgId.substring(1, orgId.length() - 1);
        String[] orgArr = orgId.split("\\$\\$");
        for (int i = 0; i < orgArr.length; i++)
          where = String.valueOf(where) + " or gv.rangeOrg like '%*" + orgArr[i] + "*%' "; 
      } 
      String sql = "from GroupVO gv where ((gv.rangeEmp is null and gv.rangeOrg is null and gv.rangeGroup is null) or (gv.rangeEmp='' and gv.rangeOrg='' and gv.rangeGroup='') or gv.rangeEmp like '%$" + userId + "$%' " + where + ") and gv.groupId in(" + range + ")  and gv.domainId=" + domainId + " and gv.groupType=0";
      sql = String.valueOf(sql) + " order by gv.groupOrder";
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
  
  public List getGroupList(String userId, String domainId, String orgId, String groupRange) throws Exception {
    List orgList = new ArrayList();
    try {
      begin();
      String where = "";
      if (orgId.equals("") || orgId == null || orgId.equals("null")) {
        where = " or 1=1 ";
      } else {
        orgId = orgId.substring(1, orgId.length() - 1);
        String[] orgArr = orgId.split("\\$\\$");
        for (int i = 0; i < orgArr.length; i++)
          where = String.valueOf(where) + " or gv.rangeOrg like '%*" + orgArr[i] + "*%' "; 
      } 
      String sql = "from GroupVO gv where ((gv.rangeEmp is null and gv.rangeOrg is null and gv.rangeGroup is null) or (gv.rangeEmp='' and gv.rangeOrg='' and gv.rangeGroup='') or gv.rangeEmp like '%$" + userId + "$%' " + where + ") and gv.domainId=" + domainId + " ";
      if (groupRange != null && !groupRange.equals("") && !"-4".equals(groupRange))
        sql = String.valueOf(sql) + " and gv.groupId in(" + groupRange + ")"; 
      sql = String.valueOf(sql) + " and  gv.groupType=0 order by gv.groupOrder";
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
  
  public List getPrivateGroupList(String userId, String domainId, String orgId, String groupRange) throws Exception {
    List orgList = new ArrayList();
    orgId = orgId.substring(1, orgId.length() - 1).replace("$$", ",").replace("$", "");
    String[] orgIdtemp = orgId.split(",");
    try {
      begin();
      String sql = "from GroupVO gv where  gv.domainId=" + domainId + " and gv.createdEmp=" + userId + " ";
      if (groupRange != null && !groupRange.equals(""))
        sql = String.valueOf(sql) + " and orgIdString like '%" + orgIdtemp[1] + "%'"; 
      sql = String.valueOf(sql) + " and gv.groupType=1 order by gv.groupOrder";
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
  
  public List getOrgList(String domainId, String range, String sele) throws Exception {
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
      if (range.equals("") || range == null || range.equals("0")) {
        if (!sele.equals("1")) {
          sql = "from OrganizationVO org where org.orgStatus<>1 and org.orgStatus<>4 and org.orgParentOrgId<>-1  and org.domainId=" + domainId + " order by  org.orgIdString";
        } else {
          sql = "from OrganizationVO org where org.domainId=" + domainId + " and org.orgParentOrgId<>-1 and org.orgStatus<>4 order by  org.orgIdString";
        } 
      } else if (!sele.equals("1")) {
        sql = "from OrganizationVO org where (org.orgId in(" + range + ") ";
        for (int i = 0; i < (range.split(",")).length; i++)
          sql = String.valueOf(sql) + " or org.orgIdString like '%$" + range.split(",")[i] + "$%' "; 
        sql = String.valueOf(sql) + ") and org.orgStatus<>1 and org.orgStatus<>4 and org.orgParentOrgId<>-1 and org.domainId=" + domainId + " order by org.orgIdString";
      } else {
        sql = "from OrganizationVO org where (org.orgId in(" + range + ") ";
        for (int i = 0; i < (range.split(",")).length; i++)
          sql = String.valueOf(sql) + " or org.orgIdString like '%$" + range.split(",")[i] + "$%' "; 
        sql = String.valueOf(sql) + ") and  org.orgParentOrgId<>-1 and org.orgStatus<>4 and org.domainId=" + domainId + " order by org.orgIdString";
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
  
  public List getOrgCurrentList(String domainId, String orgID) throws Exception {
    List orgList = new ArrayList();
    try {
      begin();
      String sql = "from OrganizationVO org where org.orgStatus<>1 and org.orgId in(" + orgID + ") and org.orgParentOrgId<>-1  and org.domainId=" + domainId + " order by  org.orgIdString";
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
  
  public List getChildOrgList(String domainId, String range, String parentId) throws Exception {
    List orgList = new ArrayList();
    String sql = "";
    try {
      begin();
      sql = "from OrganizationVO org where org.domainId=" + domainId + " and org.orgStatus<>1 and org.orgParentOrgId<>0 and org.orgStatus<>4 and org.orgIdString like '%$" + parentId + "$%' and org.orgId<>" + parentId + "  order by  org.orgIdString";
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
  
  public String getOrgIdString(String domainId, String parentOrgId, String orgId) throws Exception {
    String sql = "", returnStr = "null";
    try {
      begin();
      sql = "from OrganizationVO org where org.domainId=" + domainId + " and org.orgStatus<>1 and org.orgStatus<>4 and org.orgIdString like '%$" + orgId + "$%'  order by  org.orgIdString";
      List<OrganizationVO> orgList = this.session.createQuery(sql).list();
      if (orgList != null && orgList.size() > 0) {
        OrganizationVO vo = orgList.get(0);
        returnStr = vo.getOrgIdString();
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return returnStr;
  }
  
  public List getUserInRange(String range, String condition) throws Exception {
    List userList = new ArrayList();
    try {
      begin();
      String databaseType = SystemCommon.getDatabaseType();
      StringBuffer sql = new StringBuffer();
      sql.append("SELECT emp.empId,emp.empName,emp.empEmail,emp.empMobilePhone,emp.curStatus,emp.imId,org.orgIdString,emp.userAccounts,emp.userOnline,org.orgId,org.orgName FROM EmployeeVO emp join emp.organizations org ");
      sql.append(" WHERE emp.empId in(" + range + ") AND emp.userIsActive=1 and emp.userIsDeleted=0 and emp.userAccounts is not null ");
      if (!condition.equals("") && condition.equals("email"))
        if (databaseType.equals("mysql")) {
          sql.append(" and emp.empEmail <>'' and emp.empEmail is not null ");
        } else if (databaseType.equals("oracle")) {
          sql.append(" and emp.empEmail <>'null' and emp.empEmail is not null ");
        }  
      if (!condition.equals("") && condition.equals("mobile"))
        if (databaseType.equals("mysql")) {
          sql.append(" and emp.empMobilePhone <>'' and emp.empMobilePhone is not null ");
        } else if (databaseType.equals("oracle")) {
          sql.append(" and emp.empEmail <>'null' and emp.empEmail is not null ");
        }  
      sql.append("ORDER BY emp.empDutyLevel,emp.userOrderCode,emp.empName");
      userList = this.session.createQuery(sql.toString()).list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return userList;
  }
  
  public List getUserInOrgList(String orgId, String condition, String text, String typeTemp) throws Exception {
    List userList = new ArrayList();
    try {
      begin();
      String databaseType = SystemCommon.getDatabaseType();
      if (typeTemp.equals("true")) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT emp.empId,emp.empName,emp.empEmail,emp.empMobilePhone,emp.curStatus,emp.imId,org.orgIdString,emp.userAccounts,emp.userOnline,org.orgId,org.orgName FROM EmployeeVO emp join emp.organizations org ");
        sql.append(" WHERE (org.orgId=" + orgId + " or emp.sidelineOrg like '%*" + orgId + "*%') AND emp.userIsActive=1 and emp.userIsDeleted=0 and emp.userAccounts like '%_%' ");
        if (!condition.equals("") && condition.equals("email"))
          if (databaseType.equals("mysql")) {
            sql.append(" and emp.empEmail <>'' and emp.empEmail is not null ");
          } else if (databaseType.equals("oracle")) {
            sql.append(" and emp.empEmail <>'null' and emp.empEmail is not null ");
          }  
        if (!condition.equals("") && condition.equals("mobile"))
          if (databaseType.equals("mysql")) {
            sql.append(" and emp.empMobilePhone <>'' and emp.empMobilePhone is not null ");
          } else if (databaseType.equals("oracle")) {
            sql.append(" and emp.empMobilePhone <>'null' and emp.empMobilePhone is not null ");
          }  
        sql.append("ORDER BY emp.empDutyLevel,emp.userOrderCode,emp.empName");
        userList = this.session.createQuery(sql.toString()).list();
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return userList;
  }
  
  public List getUserInPPList(String classId, String condition, String text, String type, String userId, String orgId) throws Exception {
    List userList = new ArrayList();
    try {
      begin();
      String databaseType = SystemCommon.getDatabaseType();
      StringBuffer sql = new StringBuffer();
      sql.append("SELECT pp.id,pp.linkManName,pp.linkManEmail,pp.mobilePhone,pp.linkManDepart,pp.linkManProfession,pp.linkManWebUrl,pp.bussinessPhone,pp.linkManState,link.id,link.className FROM PersonPO pp join pp.linkManClass link");
      sql.append(" where link.id=" + classId + " ");
      if (!condition.equals("") && condition.equals("email"))
        if (databaseType.equals("mysql")) {
          sql.append(" and pp.linkManEmail <>'' and pp.linkManEmail is not null ");
        } else if (databaseType.equals("oracle")) {
          sql.append(" and pp.linkManEmail <>'null' and pp.linkManEmail is not null ");
        }  
      if (!condition.equals("") && condition.equals("mobile"))
        if (databaseType.equals("mysql")) {
          sql.append(" and pp.mobilePhone <>'' and pp.mobilePhone is not null ");
        } else if (databaseType.equals("oracle")) {
          sql.append(" and pp.mobilePhone <>'null' and pp.mobilePhone is not null ");
        }  
      if (type != null && type.equals("1")) {
        sql.append(" and ((pp.viewScope like '%$" + userId + "$%' or pp.viewScope like '%*" + orgId + "*%') ");
        sql.append(" or  (pp.viewScope='0' or pp.createdEmpId=" + userId + " or pp.viewScope like '%*-1*%')) ");
      } 
      if (type != null && type.equals("0"))
        sql.append(" and pp.createdEmpId=" + userId); 
      sql.append(" and pp.linkManType=" + type + "  ORDER BY pp.id,pp.linkManName");
      userList = this.session.createQuery(sql.toString()).list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return userList;
  }
  
  public List getUserInGroupList(String groupId, String condition, String text) throws Exception {
    List userList = new ArrayList();
    try {
      begin();
      String databaseType = SystemCommon.getDatabaseType();
      StringBuffer sql = new StringBuffer();
      sql.append("SELECT emp.empId,emp.empName,emp.empEmail,emp.empMobilePhone,emp.curStatus,emp.imId,org.orgIdString,emp.userAccounts,emp.userOnline,groupVO.groupId,groupVO.groupName FROM GroupVO groupVO join groupVO.employees emp join emp.organizations org ");
      sql.append(" WHERE (groupVO.groupId=" + groupId + ") AND emp.userIsActive=1 and emp.userIsDeleted=0 and emp.userAccounts is not null ");
      if (!condition.equals("") && condition.equals("email"))
        if (databaseType.equals("mysql")) {
          sql.append(" and emp.empEmail <>'' and emp.empEmail is not null ");
        } else if (databaseType.equals("oracle")) {
          sql.append(" and emp.empEmail <>'null' and emp.empEmail is not null ");
        }  
      if (!condition.equals("") && condition.equals("mobile"))
        if (databaseType.equals("mysql")) {
          sql.append(" and emp.empMobilePhone <>'' and emp.empMobilePhone is not null ");
        } else if (databaseType.equals("oracle")) {
          sql.append(" and emp.empMobilePhone <>'null' and emp.empMobilePhone is not null ");
        }  
      sql.append("ORDER BY emp.empDutyLevel,emp.userOrderCode,emp.empName");
      userList = this.session.createQuery(sql.toString()).list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return userList;
  }
  
  public List getUserInGZWList(String id, String condition, String text, String type) throws Exception {
    List userList = new ArrayList();
    try {
      begin();
      String databaseType = SystemCommon.getDatabaseType();
      StringBuffer sql = new StringBuffer();
      sql.append("SELECT emp.empId,emp.empName,emp.empEmail,emp.empMobilePhone,emp.curStatus,emp.imId,org.orgIdString,emp.userAccounts,emp.userOnline,org.orgId,org.orgName FROM EmployeeVO emp join emp.organizations org ");
      sql.append(" WHERE 1=1 ");
      if (id != null && !id.equals("") && !id.equals("0"))
        sql.append(" AND org.orgId in(" + id + ") "); 
      sql.append(" AND emp.userIsActive=1 and emp.userIsDeleted=0 and emp.userAccounts is not null ");
      if (type.equals("0")) {
        if (databaseType.equals("mysql")) {
          sql.append(" AND emp.empDuty<>'' ");
        } else if (databaseType.equals("oracle")) {
          sql.append(" AND emp.empDuty<>'null' ");
        } 
        sql.append(" and emp.empDuty is not null and emp.empDuty='" + text + "'");
      } 
      if (type.equals("1")) {
        if (databaseType.equals("mysql")) {
          sql.append(" AND emp.empPositionId<>'' ");
        } else if (databaseType.equals("oracle")) {
          sql.append(" AND emp.empPositionId>0 ");
        } 
        sql.append(" and emp.empPositionId is not null and emp.empPositionId=" + text + " " + "or emp.empPositionOtherId=" + text + " ");
      } 
      if (!condition.equals("") && condition.equals("email"))
        if (databaseType.equals("mysql")) {
          sql.append(" and emp.empEmail <>'' and emp.empEmail is not null ");
        } else if (databaseType.equals("oracle")) {
          sql.append(" and emp.empEmail <>'null' and emp.empEmail is not null ");
        }  
      if (!condition.equals("") && condition.equals("mobile"))
        if (databaseType.equals("mysql")) {
          sql.append(" and emp.empMobilePhone <>'' and emp.empMobilePhone is not null ");
        } else if (databaseType.equals("oracle")) {
          sql.append(" and emp.empMobilePhone <>'null' and emp.empMobilePhone is not null ");
        }  
      sql.append(" ORDER BY emp.empDutyLevel,emp.userOrderCode,emp.empName");
      userList = this.session.createQuery(sql.toString()).list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return userList;
  }
  
  public List getQueryDownDownEmp(List<Object[]> list, String text) throws Exception {
    List userList = new ArrayList();
    try {
      begin();
      if (list != null && list.size() > 0) {
        StringBuffer hql = new StringBuffer("select emp.empId,emp.empName,emp.empEmail,emp.empMobilePhone,emp.curStatus,emp.imId,org.orgIdString,emp.userAccounts,emp.userOnline,org.orgId,org.orgName from EmployeeVO emp join emp.organizations org ");
        hql.append(" where emp.empName like '%" + text + "%' and (");
        for (int i = 0; i < list.size() - 1; i++) {
          Object[] pcp = list.get(i);
          hql.append(" emp.empLeaderId like '%$" + pcp[0] + "$%' or ");
        } 
        hql.append(" emp.empLeaderId like '%$" + ((Object[])list.get(list.size() - 1))[0] + "$%' ");
        hql.append(") and emp.userIsActive=1 and emp.userIsDeleted=0 and emp.userAccounts != ' ' order by emp.empId desc ");
        userList = this.session.createQuery(hql.toString()).list();
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return userList;
  }
  
  public List getQueryUserList(String condition, String text, String type, String groupIdString, String range, String option) throws Exception {
    List userList = new ArrayList();
    try {
      begin();
      String hql = queryUserByType(condition, text, type, groupIdString, range, option);
      if (hql != null && !hql.equals(""))
        userList = this.session.createQuery(hql).list(); 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return userList;
  }
  
  private String queryUserByType(String condition, String text, String type, String groupIdString, String range, String option) throws Exception {
    StringBuffer sql = new StringBuffer();
    String databaseType = SystemCommon.getDatabaseType();
    if (type == "" || type.equals("0") || type.equals("4") || type.equals("5")) {
      sql.append("SELECT emp.empId,emp.empName,emp.empEmail,emp.empMobilePhone,emp.curStatus,emp.imId,org.orgIdString,emp.userAccounts,emp.userOnline,org.orgId,org.orgName FROM com.js.system.vo.usermanager.EmployeeVO emp join emp.organizations org ");
      sql.append(" WHERE  emp.userIsActive=1 and emp.userIsDeleted=0 and emp.userAccounts like '%_%' ");
      if (!condition.equals("") && condition.equals("email"))
        if (databaseType.equals("mysql")) {
          sql.append(" and emp.empEmail <>'' and emp.empEmail is not null ");
        } else if (databaseType.equals("oracle")) {
          sql.append(" and emp.empEmail <>'null' and emp.empEmail is not null ");
        }  
      if (!condition.equals("") && condition.equals("mobile"))
        if (databaseType.equals("mysql")) {
          sql.append(" and emp.empMobilePhone <>'' and emp.empMobilePhone is not null ");
        } else if (databaseType.equals("oracle")) {
          sql.append(" and emp.empMobilePhone <>'null' and emp.empMobilePhone is not null ");
        }  
      if (text != null && !text.equals("") && !text.equals("null"))
        sql.append(" and emp.empName like '%" + text + "%'"); 
      if (range != null && !range.equals("") && !range.equals("null") && !"*0*".equals(range)) {
        String rangeTemp = range;
        if (range.indexOf("*") >= 0) {
          rangeTemp = range.replaceAll("\\*", ",").replaceAll(",,", ",");
          rangeTemp = rangeTemp.substring(rangeTemp.indexOf(",") + 1, rangeTemp.lastIndexOf(","));
          String orgSql = "select po.orgId from com.js.system.vo.organizationmanager.OrganizationVO po ";
          String[] orgIdArr = rangeTemp.split(",");
          for (int i = 0; i < orgIdArr.length; i++) {
            if (i == 0) {
              orgSql = String.valueOf(orgSql) + " where po.orgIdString like '%$" + orgIdArr[i] + "$%'";
            } else {
              orgSql = String.valueOf(orgSql) + " or po.orgIdString like '%$" + orgIdArr[i] + "$%'";
            } 
          } 
          sql.append(" and org.orgId in(" + orgSql + ")");
        } else if (!rangeTemp.equals("") && !rangeTemp.equals("0")) {
          sql.append(" and (org.orgId in (" + rangeTemp + ") or org.orgParentOrgId in(" + rangeTemp + "))");
        } 
      } 
      if (type.equals("4"))
        sql.append(" and emp.empDuty='" + option + "' "); 
      if (type.equals("5"))
        sql.append(" and emp.empPosition='" + option + "' "); 
      sql.append(" ORDER BY emp.empDutyLevel,emp.userOrderCode,emp.empName");
    } 
    if (type.equals("1")) {
      sql.append("SELECT emp.empId,emp.empName,emp.empEmail,emp.empMobilePhone,emp.curStatus,emp.imId,org.orgIdString,emp.userAccounts,emp.userOnline,groupVO.groupId,groupVO.groupName,org.orgName FROM GroupVO groupVO join groupVO.employees emp join emp.organizations org ");
      sql.append(" WHERE (groupVO.groupId in(" + groupIdString.substring(0, groupIdString.lastIndexOf(",")) + ")) AND emp.userIsActive=1 and emp.userIsDeleted=0 and emp.userAccounts like '%_%' ");
      if (!condition.equals("") && condition.equals("email"))
        if (databaseType.equals("mysql")) {
          sql.append(" and emp.empEmail <>'' and emp.empEmail is not null ");
        } else if (databaseType.equals("oracle")) {
          sql.append(" and emp.empEmail <>'null' and emp.empEmail is not null ");
        }  
      if (!condition.equals("") && condition.equals("mobile"))
        if (databaseType.equals("mysql")) {
          sql.append(" and emp.empMobilePhone <>'' and emp.empMobilePhone is not null ");
        } else if (databaseType.equals("oracle")) {
          sql.append(" and emp.empMobilePhone <>'null' and emp.empMobilePhone is not null ");
        }  
      if (text != null && !text.equals("") && !text.equals("null"))
        sql.append(" and emp.empName like '%" + text + "%'"); 
      sql.append("ORDER BY emp.empDutyLevel,emp.userOrderCode,emp.empName");
    } 
    if (type.equals("2") || type.equals("3")) {
      sql.append("SELECT pp.id,pp.linkManName,pp.linkManEmail,pp.mobilePhone,pp.linkManDepart,pp.linkManProfession,pp.linkManWebUrl,pp.bussinessPhone,pp.linkManState,link.id,link.className FROM PersonPO pp join pp.linkManClass link");
      if (type.equals("2"))
        sql.append(" where link.classType=0 "); 
      if (type.equals("3"))
        sql.append(" where link.classType=1 "); 
      if (!condition.equals("") && condition.equals("email"))
        if (databaseType.equals("mysql")) {
          sql.append(" and pp.linkManEmail <>'' and pp.linkManEmail is not null ");
        } else if (databaseType.equals("oracle")) {
          sql.append(" and pp.linkManEmail <>'null' and pp.linkManEmail is not null ");
        }  
      if (!condition.equals("") && condition.equals("mobile"))
        if (databaseType.equals("mysql")) {
          sql.append(" and pp.mobilePhone <>'' and pp.mobilePhone is not null ");
        } else if (databaseType.equals("oracle")) {
          sql.append(" and pp.mobilePhone <>'null' and pp.mobilePhone is not null ");
        }  
      if (text != null && !text.equals("") && !text.equals("null"))
        sql.append(" and pp.linkManName like '%" + text + "%'"); 
      sql.append(" ORDER BY pp.id,pp.linkManName");
    } 
    return sql.toString();
  }
  
  public List getParentOrgList(String domainId) throws Exception {
    List orgList = new ArrayList();
    String sql = "";
    try {
      begin();
      sql = "select distinct org.orgParentOrgId from OrganizationVO org where org.orgStatus<>1 and org.domainId=" + domainId + " group by org.orgParentOrgId";
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
  
  public List getRootOrgList(String domainId) throws Exception {
    List orgList = new ArrayList();
    String sql = "";
    try {
      begin();
      sql = "from OrganizationVO org where org.orgStatus<>1 and org.domainId=" + domainId + " and org.orgParentOrgId=-1";
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
  
  public List getRootDeptList(String domainId, String range) throws Exception {
    List orgList = new ArrayList();
    String sql = "";
    if (range != null && range.indexOf("*") >= 0) {
      range = range.substring(1, range.length() - 1);
      range = range.replaceAll("\\*", ",").replaceAll(",,", ",");
    } 
    try {
      begin();
      if (range != null && !range.equals("") && !range.equals("0")) {
        sql = "from OrganizationVO org where org.orgStatus<>1 and org.domainId=" + domainId + " and org.orgParentOrgId=0 and org.orgId in(" + range + ") order by org.orgIdString";
      } else {
        sql = "from OrganizationVO org where org.orgStatus<>1 and org.domainId=" + domainId + " and org.orgParentOrgId=0 order by org.orgIdString";
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
  
  public List getEmpNumInOrg(long orgId) throws Exception {
    List orgList = new ArrayList();
    String sql = "";
    try {
      begin();
      sql = "select distinct org.orgParentOrgId from OrganizationVO org where org.domainId=" + orgId;
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
  
  public List getAllOrgs() throws HibernateException {
    List allOrgArray = new ArrayList();
    begin();
    try {
      String sql = "SELECT org.orgId,org.orgName,org.orgParentOrgId,org.orgManagerEmpId,org.orgManagerEmpName,org.orgLevel,org.orgFoundDate,org.orgOrderCode,org.orgIdString,org.orgDescripte,org.orgHasJunior,org.orgStatus,org.orgNameString FROM com.js.system.vo.organizationmanager.OrganizationVO org ORDER BY org.orgIdString";
      allOrgArray = this.session.createQuery(sql).list();
    } catch (HibernateException e) {
      System.out.println(e.toString());
      throw e;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return allOrgArray;
  }
  
  public List getValidOrgs() throws HibernateException {
    List validOrgs = new ArrayList();
    begin();
    try {
      String sql = "SELECT org.orgId,org.orgName,org.orgParentOrgId,org.orgManagerEmpId,org.orgManagerEmpName,org.orgLevel,org.orgFoundDate,org.orgOrderCode,org.orgIdString,org.orgDescripte,org.orgHasJunior,org.orgStatus,org.orgHasChannel,org.orgNameString FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE org.orgStatus=0 ORDER BY org.orgIdString";
      validOrgs = this.session.createQuery(sql).list();
    } catch (HibernateException e) {
      throw e;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return validOrgs;
  }
  
  public Set getSubUsers(Long orgId) throws Exception {
    Set userList = null;
    try {
      begin();
      OrganizationVO organizationVO = (OrganizationVO)this.session.load(OrganizationVO.class, orgId);
      userList = organizationVO.getEmployees();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return userList;
  }
  
  public List getHasChannel() throws Exception {
    List<Object[]> list = null;
    begin();
    try {
      Query query = this.session.createQuery("select aaa.orgId,aaa.orgName,aaa.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO aaa where aaa.orgHasChannel = 1");
      list = query.list();
      String tmpSql = "";
      String databaseType = SystemCommon.getDatabaseType();
      for (int i = 0; i < list.size(); i++) {
        Object[] obj = list.get(i);
        String orgIdString = (String)obj[2];
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "select aaa.orgName from com.js.system.vo.organizationmanager.OrganizationVO aaa where '" + orgIdString + "' like concat('%$', aaa.orgId, '$%') order by aaa.orgLevel";
        } else {
          tmpSql = "select aaa.orgName from com.js.system.vo.organizationmanager.OrganizationVO aaa where '" + orgIdString + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.orgId)),'$%') order by aaa.orgLevel";
        } 
        query = this.session.createQuery(tmpSql);
        List list2 = query.list();
        StringBuffer sb = new StringBuffer();
        for (int j = 0; j < list2.size(); j++) {
          Object obj2 = list2.get(j);
          sb.append(obj2 + "·");
        } 
        String tmp = sb.toString();
        if (tmp.endsWith("·")) {
          obj[1] = tmp.substring(0, tmp.length() - "·".length());
        } else {
          obj[1] = tmp;
        } 
        list.set(i, obj);
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public String getOrgName(String orgId) throws Exception {
    begin();
    String orgFullName = "";
    try {
      Query query = this.session.createQuery("select aaa.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO aaa where aaa.orgId = " + orgId);
      List<String> list = query.list();
      String orgIdString = list.get(0);
      String tmpSql = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "select aaa.orgName from com.js.system.vo.organizationmanager.OrganizationVO aaa where '" + orgIdString + "' like concat('%$', aaa.orgId, '$%') order by aaa.orgLevel";
      } else {
        tmpSql = "select aaa.orgName from com.js.system.vo.organizationmanager.OrganizationVO aaa where '" + orgIdString + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.orgId)),'$%') order by aaa.orgLevel";
      } 
      query = this.session.createQuery(tmpSql);
      StringBuffer sb = new StringBuffer();
      list = query.list();
      for (int j = 0; j < list.size(); j++)
        sb.append(String.valueOf(list.get(j)) + "·"); 
      orgFullName = sb.toString();
      if (orgFullName.endsWith("·"))
        orgFullName = orgFullName.substring(0, orgFullName.length() - "·".length()); 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return orgFullName;
  }
  
  public String findOrgSerial(long key) throws Exception {
    begin();
    String orgSerial = "";
    try {
      Query query = this.session.createQuery("select aaa.orgSerial from com.js.system.vo.organizationmanager.OrganizationVO aaa where aaa.orgId = " + key);
      List<String> list = query.list();
      orgSerial = list.get(0);
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return orgSerial;
  }
  
  public String findStationName(String id) throws Exception {
    begin();
    String stationName = "";
    try {
      Query query = this.session.createQuery("select aaa.name from com.js.oa.hr.personnelmanager.po.StationPO aaa where aaa.id = " + id);
      List<String> list = query.list();
      stationName = list.get(0);
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return stationName;
  }
  
  public boolean deleteEmployee(Long orgId) throws Exception {
    boolean result = false;
    boolean newSession = false;
    Byte type = new Byte("1");
    Byte opr = new Byte("2");
    if (!this.session.isConnected()) {
      begin();
      newSession = true;
    } 
    try {
      EmployeeVO employeeVO = null;
      List<EmployeeVO> list = this.session.createQuery("SELECT employee FROM com.js.system.vo.usermanager.EmployeeVO employee join employee.organizations org WHERE employee.userIsDeleted=0 and org.orgId=" + orgId).list();
      for (int i = 0; i < list.size(); i++) {
        employeeVO = list.get(i);
        employeeVO.setUserIsDeleted((new Byte("0")).byteValue());
        employeeVO.setUserIsActive((new Byte("0")).byteValue());
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
      throw e;
    } finally {
      if (newSession)
        this.session.close(); 
    } 
    return result;
  }
  
  public List getSons(String orgId) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery("select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa where aaa.orgIdString like '%$" + orgId + "$%' and aaa.orgId <> " + orgId);
      list = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List getSuperior(String orgId) throws Exception {
    List list = null;
    begin();
    try {
      String tmpSql = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + orgId + ") like concat('%$', aaa.orgId,'$%') and aaa.orgId <> " + orgId;
      } else {
        tmpSql = "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + orgId + ") like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.orgId)),'$%') and aaa.orgId <> " + orgId;
      } 
      Query query = this.session.createQuery(tmpSql);
      list = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List getNameAndId(String orgIds) throws Exception {
    List list = null;
    try {
      begin();
      String tmpSql = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "SELECT org.orgId,org.orgName FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE '" + orgIds + "' LIKE concat('%*', org.orgId, '*%') ";
      } else {
        tmpSql = "SELECT org.orgId,org.orgName FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE '" + orgIds + "' LIKE JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%*', JSDB.FN_INTTOSTR(org.orgId)),'*%') ";
      } 
      list = this.session.createQuery(tmpSql).list();
    } catch (Exception e) {
      System.out.println("error getNameAndId in EJB :" + e.getMessage());
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List getValidOrgsByRange(String orgIds, String domainId) throws Exception {
    List list = null;
    try {
      String sqlWhere;
      if (orgIds.equals("*0*") || orgIds.equals("0")) {
        sqlWhere = "";
      } else {
        sqlWhere = "";
        orgIds = "*" + orgIds + "*";
        String[] orgIdArray = orgIds.split("\\*\\*");
        int i = 0, j = 0;
        for (i = 0; i < orgIdArray.length; i++) {
          if (!orgIdArray[i].equals("")) {
            if (j == 0) {
              sqlWhere = " AND (org.orgIdString like '%$" + 
                orgIdArray[i] + "$%'";
            } else {
              sqlWhere = String.valueOf(sqlWhere) + " OR org.orgIdString like '%$" + 
                orgIdArray[i] + "$%'";
            } 
            j++;
          } 
        } 
        if (j > 0)
          sqlWhere = String.valueOf(sqlWhere) + ")"; 
      } 
      begin();
      String sql = "SELECT org.orgId,org.orgName,org.orgParentOrgId,org.orgManagerEmpId,org.orgManagerEmpName,org.orgLevel,org.orgFoundDate,org.orgOrderCode,org.orgIdString,org.orgDescripte,org.orgHasJunior,org.orgStatus,org.orgHasChannel,org.orgNameString,org.orgSimpleName,org.orgSerial,org.orgType,org.orgChannelType,org.orgChannelUrl FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE org.orgStatus=0 " + sqlWhere + " and org.domainId=" + domainId + " ORDER BY org.orgIdString";
      list = this.session.createQuery(sql).list();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List getSimpleOrg(String userId, String domainId) throws Exception {
    List list = null;
    begin();
    try {
      Query query = null;
      String orgIds = selectShowOrg(userId);
      if (orgIds != null && !"".equals(orgIds)) {
        orgIds = " select aaa.orgId,aaa.orgName,aaa.orgHasChannel,aaa.orgLevel,aaa.orgParentOrgId,aaa.orgChannelType,aaa.orgChannelUrl from  com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgStatus=0 and  aaa.orgId not in (" + 

          
          orgIds + ") and aaa.domainId=" + domainId + " order by aaa.orgIdString ";
      } else {
        orgIds = " select aaa.orgId,aaa.orgName,aaa.orgHasChannel,aaa.orgLevel,aaa.orgParentOrgId,aaa.orgChannelType,aaa.orgChannelUrl from  com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgStatus=0 and aaa.domainId=" + 
          
          domainId + " order by aaa.orgIdString ";
      } 
      query = this.session.createQuery(orgIds);
      list = query.list();
    } catch (Exception e) {
      System.out.println(e);
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public String selectShowOrg(String userId) throws Exception {
    StringBuffer buffer = new StringBuffer();
    boolean newSession = false;
    if (!this.session.isConnected()) {
      begin();
      newSession = true;
    } 
    if (newSession)
      this.session.close(); 
    if (buffer.length() > 0)
      return buffer.substring(0, buffer.length() - 1); 
    return "";
  }
  
  public List getAllChannelList(String userId) throws Exception {
    List list = null;
    begin();
    try {
      String orgIds = selectShowOrg(userId);
      if (orgIds != null && !"".equals(orgIds)) {
        orgIds = " select aaa.orgId,aaa.orgName,aaa.orgHasChannel,aaa.orgLevel,aaa.orgParentOrgId from  com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgStatus=0 and  aaa.orgId in (" + 

          
          orgIds + ") and aaa.orgHasChannel>0 order by aaa.orgIdString ";
      } else {
        orgIds = " select aaa.orgId,aaa.orgName,aaa.orgHasChannel,aaa.orgLevel,aaa.orgParentOrgId from  com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgStatus=0 and aaa.orgHasChannel>0 order by aaa.orgIdString ";
      } 
      list = this.session.createQuery(orgIds).list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public Integer checkOrganizationSerial(String orgId, String orgSerial, String flag) throws Exception {
    int result = 0;
    begin();
    try {
      String sql = "";
      if (orgId == null || "null".equals(orgId) || "".equals(orgId)) {
        sql = "select count(org.orgSerial) from com.js.system.vo.organizationmanager.OrganizationVO org where org.orgSerial='" + orgSerial + "'";
      } else if ("true".equals(flag)) {
        sql = "select count(org.orgSerial) from com.js.system.vo.organizationmanager.OrganizationVO org where org.orgSerial='" + orgSerial + "'";
      } else {
        sql = "select count(org.orgSerial) from com.js.system.vo.organizationmanager.OrganizationVO org where org.orgSerial='" + orgSerial + "' and org.orgId<>" + orgId;
      } 
      int num = ((Integer)this.session.createQuery(sql).iterate().next()).intValue();
      if (num > 0)
        result = 1; 
    } catch (Exception ex) {
      System.out.println("checkOrgSerial error:" + ex);
      throw ex;
    } finally {
      this.session.close();
    } 
    return new Integer(result);
  }
  
  public Integer checkOrgSerialAndOrgParentorgid(String orgParentorgid, String orgSerial) throws Exception {
    int result = 0;
    begin();
    try {
      String sql = "select count(org.orgSerial) from com.js.system.vo.organizationmanager.OrganizationVO org where org.orgSerial='" + orgSerial + "'" + 
        " and org.orgParentOrgId='" + orgParentorgid + "'";
      int num = ((Integer)this.session.createQuery(sql).iterate().next()).intValue();
      if (num > 0)
        result = 1; 
    } catch (Exception ex) {
      System.out.println("checkOrgSerial error:" + ex);
      throw ex;
    } finally {
      this.session.close();
    } 
    return new Integer(result);
  }
  
  public Integer checkOrganizationName(String orgId, String orgName) throws Exception {
    int result = 0;
    begin();
    try {
      String sql;
      if (orgId == null || "null".equals(orgId) || "".equals(orgId)) {
        sql = "select count(org.orgName) from OrganizationVO org where org.orgName='" + orgName + "' and org.orgStatus<>4 and org.orgParentOrgId=0 ";
      } else {
        if (orgId.equals("-1"))
          orgId = "-44"; 
        sql = "select count(org.orgName) from OrganizationVO org where org.orgName='" + orgName + "' and org.orgStatus<>4 and org.orgParentOrgId=" + orgId;
      } 
      int num = ((Integer)this.session.createQuery(sql).iterate().next()).intValue();
      if (num > 0)
        result = 1; 
    } catch (Exception ex) {
      System.out.println("checkOrgSerial error:" + ex);
      throw ex;
    } finally {
      this.session.close();
    } 
    return new Integer(result);
  }
  
  public DomainVO loadDomain(String domainId) throws Exception {
    begin();
    DomainVO result = null;
    try {
      result = (DomainVO)this.session.load(DomainVO.class, Long.valueOf(domainId));
    } catch (Exception exception) {
    
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public long activeAdd(OrganizationVO organizationVO, String currentOrderCode, String parentIdString, Integer sort) throws HibernateException {
    Long orgId = null;
    long parentId = organizationVO.getOrgParentOrgId();
    try {
      begin();
      if (organizationVO.getOrgManagerEmpName() == null) {
        organizationVO.setOrgManagerEmpName("");
        organizationVO.setOrgManagerEmpId("");
      } 
      orgId = (Long)this.session.save(organizationVO);
      int orderCode = 100000;
      String orgIdString = "";
      List<Integer> list = null;
      if ("-1".equals(currentOrderCode)) {
        orderCode = 500000;
      } else if (sort.intValue() == 0) {
        Query query = this.session.createQuery("SELECT MAX(org.orgOrderCode) FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE org.orgParentOrgId=" + parentId + " AND org.orgOrderCode<" + currentOrderCode);
        list = query.list();
        if (list == null || (list.size() == 1 && list.get(0) == null)) {
          orderCode = Integer.parseInt(currentOrderCode) - 5000;
        } else {
          int intValue = ((Integer)list.get(0)).intValue();
          if (intValue == 0)
            intValue = 100000; 
          orderCode = (intValue + Integer.parseInt(currentOrderCode)) / 2;
        } 
      } else {
        Query query = this.session.createQuery("SELECT MIN(org.orgOrderCode) FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE org.orgParentOrgId=" + parentId + " AND org.orgOrderCode>" + currentOrderCode);
        list = query.list();
        if (list == null || (list.size() == 1 && list.get(0) == null)) {
          orderCode = Integer.parseInt(currentOrderCode) + 5000;
        } else {
          orderCode = (((Integer)list.get(0)).intValue() + Integer.parseInt(currentOrderCode)) / 2;
        } 
      } 
      if ("".equals(parentIdString)) {
        orgIdString = "_500000$-1$_" + orderCode + "$" + orgId.toString() + "$";
      } else {
        orgIdString = String.valueOf(parentIdString) + "_" + orderCode + "$" + orgId.toString() + "$";
      } 
      organizationVO.setOrgOrderCode(orderCode);
      organizationVO.setOrgIdString(orgIdString);
      String orgNameString = "";
      int orgLevel = -1;
      if (parentId != 0L) {
        OrganizationVO org = (OrganizationVO)this.session.load(
            OrganizationVO.class, new Long(parentId));
        org.setOrgHasJunior(1);
        orgLevel = org.getOrgLevel();
        orgNameString = String.valueOf(org.getOrgNameString()) + "." + organizationVO.getOrgName();
      } else {
        orgNameString = organizationVO.getOrgName();
      } 
      organizationVO.setOrgNameString(orgNameString);
      organizationVO.setOrgLevel(++orgLevel);
      this.session.flush();
    } catch (HibernateException e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return orgId.longValue();
  }
  
  public OrganizationVO getOrgBySerial(String serial) throws Exception {
    OrganizationVO organizationVO = null;
    begin();
    try {
      Query query = this.session.createQuery("select aaa from com.js.system.vo.organizationmanager.OrganizationVO aaa where aaa.orgSerial like '" + serial + "'");
      organizationVO = (OrganizationVO)query.uniqueResult();
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return organizationVO;
  }
  
  public OrganizationVO getOrgByOrgId(String orgId) throws Exception {
    OrganizationVO organizationVO = null;
    begin();
    try {
      Query query = this.session.createQuery("select aaa from com.js.system.vo.organizationmanager.OrganizationVO aaa where aaa.orgId='" + orgId + "'");
      organizationVO = (OrganizationVO)query.uniqueResult();
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return organizationVO;
  }
  
  public List getParentOrgListTemp(String orgId, String domainId, String range) throws Exception {
    List list = null;
    begin();
    String sql = "";
    try {
      if (orgId != null && !"null".equals(orgId) && !"".equals(orgId)) {
        sql = "from OrganizationVO org where  org.orgParentOrgId=" + orgId + " and org.orgStatus<>4 and org.domainId=" + domainId + " ";
      } else {
        sql = "from OrganizationVO org where  org.orgParentOrgId=0 and org.orgStatus<>4 and org.domainId=" + domainId + " ";
      } 
      if (range != null && !range.equals("") && !range.equals("0"))
        range.equals("*0*"); 
      sql = String.valueOf(sql) + " order by org.orgIdString";
      Query query = this.session.createQuery(sql);
      list = query.list();
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public String getMaxOrgOrderCode(long orgParentOrgId) throws Exception {
    String OrgOrderCode = "";
    Integer code = Integer.valueOf(-1);
    List<Integer> list = new ArrayList();
    begin();
    try {
      Query query = this.session.createQuery("SELECT MAX(org.orgOrderCode) FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE org.orgParentOrgId=" + orgParentOrgId);
      list = query.list();
      if (list == null || (list.size() == 1 && list.get(0) == null)) {
        OrgOrderCode = code.toString();
      } else {
        code = list.get(0);
        OrgOrderCode = code.toString();
      } 
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return OrgOrderCode;
  }
  
  public void delAllOrganization() throws Exception {
    Connection conn = null;
    Statement stmt = null;
    begin();
    try {
      conn = this.session.connection();
      stmt = conn.createStatement();
      stmt.execute("DELETE from JSDB.org_organization");
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void delById(long orgid) throws Exception {
    begin();
    try {
      this.session.delete(this.session.get(OrganizationVO.class, Long.valueOf(orgid)));
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public List<OrganizationVO> selectAllOrganization() throws Exception {
    List<OrganizationVO> list = new ArrayList<OrganizationVO>();
    begin();
    try {
      list = this.session.createQuery("select org from com.js.system.vo.organizationmanager.OrganizationVO org where org.orgStatus=0 and org.domainId like '0' order by org.orgId").list();
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
  
  public List<String> selectAllOrgSerial() throws Exception {
    List<String> list = new ArrayList<String>();
    begin();
    try {
      list = this.session.createQuery("select org.orgSerial from com.js.system.vo.organizationmanager.OrganizationVO org where org.domainId like '0' ").list();
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
  
  public String findOrgIdString(long key) throws Exception {
    begin();
    String orgIdString = "";
    try {
      Query query = this.session.createQuery("select aaa.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO aaa where aaa.orgId = " + key);
      List<String> list = query.list();
      orgIdString = list.get(0);
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return orgIdString;
  }
  
  public List<OrganizationVO> selectAllParentOrganization() throws Exception {
    List<OrganizationVO> list = new ArrayList<OrganizationVO>();
    begin();
    try {
      list = this.session.createQuery("select org from com.js.system.vo.organizationmanager.OrganizationVO org where org.orgStatus=0 and org.domainId like '0' and org.orgParentOrgId = 0").list();
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
  
  public String getOrgIdByUserID(String userId) throws Exception {
    String orgId = "";
    begin();
    try {
      Query query = this.session.createQuery(
          " select aaa.orgId from  com.js.system.vo.usermanager.EmployeeOrgVO aaa  where aaa.empId = " + 
          
          userId);
      Iterator iter = query.iterate();
      if (iter.hasNext()) {
        Object tmp = iter.next();
        orgId = (tmp == null) ? "" : tmp.toString();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return orgId;
  }
  
  public String getAllSubOrgIdByOrgId(String orgId) throws Exception {
    String allSubOrgId = "";
    begin();
    try {
      Query query = this.session.createQuery(
          " select aaa.orgId from  com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like '%$" + 
          
          orgId + "$%'");
      List list = query.list();
      if (list != null && list.size() != 0)
        for (int i = 0; i < list.size(); i++) {
          Object obj = list.get(i);
          if (i == 0) {
            allSubOrgId = (obj == null) ? "" : obj.toString();
          } else {
            allSubOrgId = String.valueOf(allSubOrgId) + "," + ((obj == null) ? "" : obj.toString());
          } 
        }  
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return allSubOrgId;
  }
  
  public void setOrder(String orgIdString, String orgOrderCode, String ordId) throws Exception {
    begin();
    try {
      OrganizationVO organizationVO = (OrganizationVO)this.session.load(OrganizationVO.class, Long.valueOf(ordId));
      organizationVO.setOrgIdString(orgIdString);
      organizationVO.setOrgOrderCode(Integer.valueOf(orgOrderCode).intValue());
      this.session.update(organizationVO);
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
  
  public Long activeAdd(OrganizationVO organizationVO, String currentOrderCode, String parentIdString, Integer sort, String[] log) throws HibernateException {
    String orgId = "orgId";
    Long logId = null;
    long parentId = organizationVO.getOrgParentOrgId();
    try {
      begin();
      logId = Long.valueOf(saveLog(log, "导入组织机构"));
      if (organizationVO.getOrgManagerEmpName() == null) {
        organizationVO.setOrgManagerEmpName("");
        organizationVO.setOrgManagerEmpId("");
      } 
      int orderCode = 100000;
      String orgIdString = "";
      List<Integer> list = null;
      if ("-1".equals(currentOrderCode)) {
        orderCode = 500000;
      } else if (sort.intValue() == 0) {
        Query query = this.session.createQuery("SELECT MAX(org.orgOrderCode) FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE org.orgParentOrgId=" + parentId + " AND org.orgOrderCode<" + currentOrderCode);
        list = query.list();
        if (list == null || (list.size() == 1 && list.get(0) == null)) {
          orderCode = Integer.parseInt(currentOrderCode) - 5000;
        } else {
          int intValue = ((Integer)list.get(0)).intValue();
          if (intValue == 0)
            intValue = 100000; 
          orderCode = (intValue + Integer.parseInt(currentOrderCode)) / 2;
        } 
      } else {
        Query query = this.session.createQuery("SELECT MIN(org.orgOrderCode) FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE org.orgParentOrgId=" + parentId + " AND org.orgOrderCode>" + currentOrderCode);
        list = query.list();
        if (list == null || (list.size() == 1 && list.get(0) == null)) {
          orderCode = Integer.parseInt(currentOrderCode) + 5000;
        } else {
          orderCode = (((Integer)list.get(0)).intValue() + Integer.parseInt(currentOrderCode)) / 2;
        } 
      } 
      if ("".equals(parentIdString)) {
        orgIdString = "_500000$-1$_" + orderCode + "$" + orgId.toString() + "$";
      } else {
        orgIdString = String.valueOf(parentIdString) + "_" + orderCode + "$" + orgId.toString() + "$";
      } 
      organizationVO.setOrgOrderCode(orderCode);
      organizationVO.setOrgIdString(orgIdString);
      String orgNameString = "";
      int orgLevel = -1;
      if (parentId != 0L) {
        OrganizationVO org = (OrganizationVO)this.session.load(
            OrganizationVO.class, new Long(parentId));
        org.setOrgHasJunior(1);
        orgLevel = org.getOrgLevel();
        orgNameString = String.valueOf(org.getOrgNameString()) + "." + organizationVO.getOrgName();
      } else {
        orgNameString = organizationVO.getOrgName();
      } 
      organizationVO.setOrgNameString(orgNameString);
      organizationVO.setOrgLevel(++orgLevel);
      organizationVO.setOrgNameString(orgNameString);
      organizationVO.setOrgLevel(++orgLevel);
      OrganizationPO organizationPO = (OrganizationPO)FillBean.transformOTO(organizationVO, OrganizationPO.class);
      organizationPO.setOrgId(Long.valueOf(0L));
      organizationPO.setLogId((String)logId);
      organizationPO.setOperate("insert");
      this.session.save(organizationPO);
      this.session.flush();
    } catch (HibernateException e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return logId;
  }
  
  public void delByLogId(long logId) throws Exception {
    DataSourceBase dataSource = new DataSourceBase();
    try {
      dataSource.begin();
      String sql = "delete from audit_log where log_id=" + logId;
      dataSource.executeUpdate(sql);
      sql = "delete from audit_organization where log_id=" + logId;
      dataSource.executeUpdate(sql);
    } catch (Exception e) {
      throw e;
    } finally {
      dataSource.end();
    } 
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
            1, 1, new Date(), "审计提醒:" + log[1] + caozuo, "audit", logId.longValue(), "/jsoa/AuditOrgAction.do?action=getOrgDetail&type=1&range=*0*&logId=" + logId + "&disabled=disabled&comeflag=tixing");
      } 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return logId.longValue();
  }
  
  public void updateLeader(String empIds) {
    DataSourceBase base = new DataSourceBase();
    String sql = "update org_employee set deptleader=1 where emp_id in (" + empIds + ")";
    try {
      base.begin();
      base.executeUpdate(sql);
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
  }
  
  public boolean isSubOrg(String orgId, String parentOrgId) {
    DataSourceBase base = new DataSourceBase();
    boolean b = false;
    String sql = "SELECT org_id FROM org_organization WHERE orgIdString LIKE '%$" + parentOrgId + "$%' AND org_id=" + orgId;
    System.out.println(sql);
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      if (rs.next())
        b = true; 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return b;
  }
  
  public List getUsersByRange(String range) {
    List<String[]> userList = new ArrayList();
    if (range == null || "".equals(range))
      return userList; 
    String orgRange = "";
    String groupRange = "";
    String sql = "";
    if (range.indexOf("*@") > 0) {
      String[] tempArr = range.split("\\*\\@");
      orgRange = tempArr[0];
      groupRange = tempArr[1];
    } else if (range.indexOf("*") > -1) {
      orgRange = range;
    } else if (range.indexOf("@") > -1) {
      groupRange = range;
    } 
    DbOpt db = new DbOpt();
    try {
      String[][] result = (String[][])null;
      if (!"".equals(orgRange)) {
        orgRange = orgRange.replace("**", "*");
        if (orgRange.startsWith("*"))
          orgRange = orgRange.substring(1); 
        if (orgRange.endsWith("*"))
          orgRange = orgRange.substring(0, orgRange.length() - 1); 
        String[] orgArr = orgRange.split("\\*");
        for (int i = 0; i < orgArr.length; i++) {
          sql = "SELECT emp_id,empname,useraccounts FROM org_employee WHERE emp_id IN (SELECT emp_id FROM org_organization_user WHERE org_id IN (SELECT org_id FROM org_organization WHERE orgidstring LIKE '%$" + 
            
            orgArr[i] + "$%'))";
          result = db.executeQueryToStrArr2(sql, 3);
          if (result != null && result.length > 0)
            for (int j = 0; j < result.length; j++) {
              userList.add(new String[] { result[j][0], result[j][1], result[j][2] });
            }  
        } 
      } 
      if (!"".equals(groupRange)) {
        groupRange = groupRange.replace("@@", "@");
        if (groupRange.startsWith("@"))
          groupRange = groupRange.substring(1); 
        if (groupRange.endsWith("@"))
          groupRange = groupRange.substring(0, groupRange.length() - 1); 
        String[] groupArr = groupRange.split("\\@");
        for (int i = 0; i < groupArr.length; i++) {
          sql = "SELECT emp_id,empname,useraccounts FROM org_employee WHERE emp_id IN (SELECT emp_id FROM org_user_group WHERE group_id=" + 
            groupArr[i] + ")";
          result = db.executeQueryToStrArr2(sql, 3);
          if (result != null && result.length > 0)
            for (int j = 0; j < result.length; j++) {
              userList.add(new String[] { result[j][0], result[j][1], result[j][2] });
            }  
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        db.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return userList;
  }
}
