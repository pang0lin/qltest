package com.js.system.manager.bean;

import com.js.system.util.ConvertIdAndName;
import com.js.system.util.EndowVO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;

public class ManagerEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public List getUserList(String para, String vo, String where) throws Exception {
    List userList = null;
    try {
      begin();
      StringBuffer sqlBuffer = new StringBuffer();
      sqlBuffer.append("SELECT ");
      sqlBuffer.append(para);
      sqlBuffer.append(" FROM ");
      sqlBuffer.append(vo);
      sqlBuffer.append(where);
      Query query = this.session.createQuery(sqlBuffer.toString());
      userList = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return userList;
  }
  
  public String getNameById(String idString) throws Exception {
    String nameString = "";
    ConvertIdAndName cIdAndName = new ConvertIdAndName();
    EndowVO endowVO = cIdAndName.splitId(idString);
    String orgId = endowVO.getOrgIdArray();
    String empId = endowVO.getEmpIdArray();
    String groupId = endowVO.getGroupIdArray();
    StringBuffer names = new StringBuffer();
    if (orgId != null && !orgId.equals(""))
      try {
        begin();
        Query query = this.session.createQuery("SELECT org.orgName FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE org.orgId IN (" + orgId + ")");
        List orgList = query.list();
        for (int i = 0; i < orgList.size(); i++) {
          names.append(orgList.get(i));
          names.append(",");
        } 
      } catch (Exception e) {
        throw e;
      } finally {
        this.session.close();
      }  
    if (groupId != null && !"".equals(groupId))
      try {
        begin();
        Query query = this.session.createQuery("SELECT g.groupName FROM com.js.system.vo.groupmanager.GroupVO g WHERE g.groupId IN (" + groupId + ")");
        List groupList = query.list();
        for (int i = 0; i < groupList.size(); i++) {
          names.append(groupList.get(i));
          names.append(",");
        } 
      } catch (Exception e) {
        throw e;
      } finally {
        this.session.close();
      }  
    if (empId != null && !"".equals(empId))
      try {
        begin();
        Query query = this.session.createQuery("SELECT emp.empName FROM com.js.system.vo.usermanager.EmployeeVO emp WHERE emp.empId IN (" + empId + ")");
        List empList = query.list();
        for (int i = 0; i < empList.size(); i++) {
          names.append(empList.get(i));
          names.append(",");
        } 
      } catch (Exception e) {
        throw e;
      } finally {
        this.session.close();
      }  
    return names.toString();
  }
  
  public Boolean hasRightType(String userId, String rightType) throws Exception {
    Boolean result = new Boolean(false);
    begin();
    try {
      String domainId = getUserDomainId(userId);
      Query query = this.session.createQuery("select count(aaa.rightScopeId) from com.js.system.vo.rolemanager.RightScopeVO aaa join aaa.employee bbb join aaa.right ccc where bbb.empId = " + userId + 
          " and ccc.rightType = '" + rightType + "' and ccc.domainId=" + domainId);
      int count = ((Integer)query.iterate().next()).intValue();
      if (count > 0)
        result = Boolean.TRUE; 
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public Boolean hasRightTypeName(String userId, String rightType, String rightName) throws Exception {
    Boolean result = new Boolean(false);
    begin();
    try {
      String domainId = getUserDomainId(userId);
      Query query = this.session.createQuery("select count(aaa.rightScopeId) from com.js.system.vo.rolemanager.RightScopeVO aaa join aaa.employee bbb join aaa.right ccc where bbb.empId = " + userId + 
          " and ccc.rightType = '" + rightType + "' and ccc.rightName = '" + rightName + "' and ccc.domainId=" + domainId);
      int count = ((Integer)query.iterate().next()).intValue();
      if (count > 0)
        result = Boolean.TRUE; 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public List getRightScope(String userId, String rightType, String rightName) throws Exception {
    List list = null;
    begin();
    try {
      String domainId = getUserDomainId(userId);
      Query query = this.session
        .createQuery("select aaa.rightScopeType,aaa.rightScopeScope,aaa.rightScope,aaa.rightScopeUser,aaa.rightScopeGroup from com.js.system.vo.rolemanager.RightScopeVO aaa join aaa.employee bbb join aaa.right ccc where bbb.empId = " + 
          userId + " and ccc.rightType = '" + rightType + "' and ccc.rightName = '" + rightName + "' and ccc.domainId=" + domainId);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public Boolean hasRightTypeScope(String userId, String orgId, String rightType, String rightName, String channelType) throws Exception {
    Boolean result = new Boolean(false);
    begin();
    try {
      String domainId = getUserDomainId(userId);
      Query query = this.session.createQuery(" select aaa.rightScopeType,aaa.rightScopeScope from  com.js.system.vo.rolemanager.RightScopeVO aaa  join aaa.employee bbb join aaa.right ccc where  bbb.empId = " + 
          userId + " and ccc.rightType = '" + rightType + "' " + " and ccc.rightName = '" + rightName + 
          "' and ccc.domainId=" + domainId);
      List<Object[]> list = query.list();
      if (list != null && list.size() > 0 && list.get(0) != null) {
        Object[] obj = list.get(0);
        String rightScopeType = obj[0].toString();
        String rightScopeScope = "";
        if (obj[1] != null) {
          rightScopeScope = obj[1].toString();
          rightScopeScope = rightScopeScope.substring(1, rightScopeScope.length() - 1);
        } 
        if (rightScopeType.equals("0")) {
          result = Boolean.TRUE;
        } else if (rightScopeType.equals("2")) {
          query = this.session.createQuery(" select aaa.orgId from  com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like '%$'" + orgId + "'$%'");
          list = query.list();
          for (int i = 0; i < list.size(); i++) {
            if (list.get(i).toString().equals(channelType)) {
              result = Boolean.TRUE;
              break;
            } 
          } 
        } else if (rightScopeType.equals("3")) {
          if (orgId.equals(channelType))
            result = Boolean.TRUE; 
        } else if (rightScopeType.equals("4")) {
          query = this.session
            .createQuery(" select aaa.orgId from  com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like '%$'" + rightScopeScope + "'$%'");
          list = query.list();
          for (int i = 0; i < list.size(); i++) {
            if (list.get(i).toString().equals(channelType)) {
              result = Boolean.TRUE;
              break;
            } 
          } 
        } 
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public String getAllJuniorOrgIdByRange(String range) throws Exception {
    if (range == null || "".equals(range))
      return "-1"; 
    String result = "-1,";
    try {
      StringBuffer where = new StringBuffer(" WHERE ");
      range = "*" + range + "*";
      String[] rangeArray = range.split("\\*\\*");
      int i = 0;
      for (i = 1; i < rangeArray.length; i++) {
        if (i > 1)
          where.append(" or "); 
        where.append(" org.orgIdString like '%$");
        where.append(rangeArray[i]);
        where.append("$%' ");
      } 
      begin();
      List list = this.session.createQuery("SELECT org.orgId FROM com.js.system.vo.organizationmanager.OrganizationVO org" + where).list();
      int j = 900;
      StringBuffer tmp = new StringBuffer();
      for (i = 0; i < list.size(); i++) {
        tmp.append(list.get(i));
        if (i > j) {
          tmp.append("a");
          j += 900;
        } else {
          tmp.append(",");
        } 
      } 
      result = tmp.toString();
      if (result.length() > 0)
        result = result.substring(0, result.length() - 1); 
    } catch (Exception e) {
      System.out.println("error!" + e.getMessage());
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public String getAllorg(String org) {
    DataSourceBase dataSourceBase = new DataSourceBase();
    String orgString = "";
    String sql = "";
    org = org.replaceAll("\\*", "");
    orgString = String.valueOf(org) + ",";
    ResultSet rs = null;
    try {
      dataSourceBase.begin();
      sql = "select org_id from org_organization where orgidstring like '%$" + org + "$%'";
      rs = dataSourceBase.executeQuery(sql);
      while (rs.next())
        orgString = String.valueOf(orgString) + rs.getString(1) + ","; 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        dataSourceBase.end();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return orgString;
  }
  
  public Map getOrgAndGroupByRange(String range, String group, String orgIdString, String empId, String currentOrg, String domainId) throws Exception {
    StringBuffer sqlWhere = new StringBuffer();
    HashMap<Object, Object> map = new HashMap<Object, Object>(2);
    if (!"".equals(currentOrg))
      sqlWhere.append(" and (org.orgIdString not like '%$").append(currentOrg).append("$%')"); 
    begin();
    try {
      StringBuffer tmp = new StringBuffer();
      if (range.equals("*0*") || range.equals("0")) {
        tmp.append(" 1=1 ");
      } else {
        String tmpSql = "";
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "select org.orgIdString,org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org where '" + range + 
            "' like concat('%*', org.orgId, '*%')) and org.domainId=" + domainId + " ";
        } else if (databaseType.indexOf("db2") >= 0) {
          tmpSql = "select org.orgIdString,org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org where locate(JSDB.FN_LINKCHAR('*', JSDB.FN_LINKCHAR(JSDB.FN_INTTOSTR(org.orgId),'*')),'" + 
            range + "')>0  and org.domainId=" + domainId + " ";
        } else {
          tmpSql = "select org.orgIdString,org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org where '" + range + 
            "' like JSDB.FN_LINKCHAR('%*', JSDB.FN_LINKCHAR(JSDB.FN_INTTOSTR(org.orgId),'*%')) and org.domainId=" + domainId + " ";
        } 
        Iterator<Object[]> fIter = this.session.iterate(tmpSql);
        Object[] obj = (Object[])null;
        while (fIter.hasNext()) {
          obj = fIter.next();
          if (databaseType.indexOf("mysql") >= 0) {
            tmp.append(" '" + obj[0] + "' like concat('%$', org.orgId, '$%')) or orgIdString like '%$" + obj[1] + "$%' or ");
            continue;
          } 
          if (databaseType.indexOf("db2") >= 0) {
            tmp.append(" locate( JSDB.FN_LINKCHAR('$', JSDB.FN_LINKCHAR(JSDB.FN_INTTOSTR(org.orgId),'$')),'" + obj[0] + "')>0 or orgIdString like '%$" + obj[1] + "$%' or ");
            continue;
          } 
          tmp.append(" '" + obj[0] + "' like JSDB.FN_LINKCHAR('%$', JSDB.FN_LINKCHAR(JSDB.FN_INTTOSTR(org.orgId),'$%')) or orgIdString like '%$" + obj[1] + "$%' or ");
        } 
        tmp.append(" 1>1");
      } 
      String sql = "SELECT org.orgId,org.orgName,org.orgParentOrgId,org.orgLevel,org.orgHasJunior,org.orgIdString FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE org.orgStatus=0 " + 
        sqlWhere.toString() + " and (" + tmp.toString() + ") and org.domainId=" + domainId + " ORDER BY org.orgIdString";
      map.put("org", this.session.createQuery(sql).list());
      if (group.indexOf("group") >= 0) {
        String idStringArray = "";
        int index = orgIdString.indexOf("$");
        int i = 0;
        while (index > 0) {
          i++;
          String idStringTemp = orgIdString.substring(index + 1);
          index = idStringTemp.indexOf("$");
          if (i == 1) {
            idStringArray = idStringTemp.substring(0, index);
          } else {
            idStringArray = String.valueOf(idStringArray) + "," + idStringTemp.substring(0, index);
          } 
          if (index < idStringTemp.length()) {
            orgIdString = idStringTemp.substring(index + 1);
            index = orgIdString.indexOf("$");
          } 
        } 
        if (i == 0)
          idStringArray = "0"; 
        sql = "SELECT g.groupId,g.groupName FROM com.js.system.vo.groupmanager.GroupVO g";
        map.put("group", this.session.createQuery(sql).list());
      } 
      if (group.indexOf("publicPerson") >= 0) {
        String idStringArray = "";
        int index = orgIdString.indexOf("$");
        int i = 0;
        while (index > 0) {
          i++;
          String idStringTemp = orgIdString.substring(index + 1);
          index = idStringTemp.indexOf("$");
          if (i == 1) {
            idStringArray = idStringTemp.substring(0, index);
          } else {
            idStringArray = String.valueOf(idStringArray) + "," + idStringTemp.substring(0, index);
          } 
          if (index < idStringTemp.length()) {
            orgIdString = idStringTemp.substring(index + 1);
            index = orgIdString.indexOf("$");
          } 
        } 
        if (i == 0)
          idStringArray = "0"; 
        sql = "SELECT personClassPO.id,personClassPO.className FROM com.js.oa.personalwork.person.po.PersonClassPO personClassPO where personClassPO.classType=1 and personClassPO.domainId=" + 
          domainId;
        map.put("publicPerson", this.session.createQuery(sql).list());
      } 
      if (group.indexOf("privatePerson") >= 0) {
        String idStringArray = "";
        int index = orgIdString.indexOf("$");
        int i = 0;
        while (index > 0) {
          i++;
          String idStringTemp = orgIdString.substring(index + 1);
          index = idStringTemp.indexOf("$");
          if (i == 1) {
            idStringArray = idStringTemp.substring(0, index);
          } else {
            idStringArray = String.valueOf(idStringArray) + "," + idStringTemp.substring(0, index);
          } 
          if (index < idStringTemp.length()) {
            orgIdString = idStringTemp.substring(index + 1);
            index = orgIdString.indexOf("$");
          } 
        } 
        if (i == 0)
          idStringArray = "0"; 
        sql = "SELECT personClassPO.id,personClassPO.className FROM com.js.oa.personalwork.person.po.PersonClassPO personClassPO where personClassPO.classType=0 and personClassPO.empId=" + 
          empId + " and personClassPO.domainId=" + domainId;
        map.put("privatePerson", this.session.createQuery(sql).list());
      } 
    } catch (Exception e) {
      System.out.println("The error at MangerEJBBean:" + e.getMessage());
      throw e;
    } finally {
      this.session.close();
    } 
    return map;
  }
  
  public Map getSubOrgAndUsers(String orgId, String currentOrg, String domainId, String rootCorpId, String corpId, String departId, String otherDepart) throws Exception {
    HashMap<Object, Object> map = new HashMap<Object, Object>(2);
    String databaseType = SystemCommon.getDatabaseType();
    begin();
    try {
      String sql = "SELECT org.orgId,org.orgName FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE org.orgParentOrgId=" + orgId + " AND org.orgStatus=0 and org.domainId=" + 
        domainId;
      if ("0".equals(orgId)) {
        if (!"".equals(currentOrg)) {
          if (!"-1".equals(otherDepart))
            if ("1".equals(otherDepart)) {
              if (databaseType.indexOf("mysql") >= 0) {
                sql = String.valueOf(sql) + " and org.orgIdString not like '%$" + rootCorpId + "$%'";
              } else if (databaseType.indexOf("db2") >= 0) {
                sql = String.valueOf(sql) + " and org.orgIdString not like '%$" + rootCorpId + "$%'";
              } else {
                sql = String.valueOf(sql) + " and org.orgIdString not like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$'," + rootCorpId + "),'$%')";
              } 
            } else if (databaseType.indexOf("mysql") >= 0) {
              sql = String.valueOf(sql) + " and org.orgIdString like '%$" + rootCorpId + "$%'";
            } else if (databaseType.indexOf("db2") >= 0) {
              sql = String.valueOf(sql) + " and org.orgIdString like '%$" + rootCorpId + "$%'";
            } else {
              sql = String.valueOf(sql) + " and org.orgIdString like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$'," + rootCorpId + "),'$%')";
            }  
          if (databaseType.indexOf("mysql") >= 0) {
            sql = String.valueOf(sql) + " and org.orgIdString like '%$" + rootCorpId + "$%'";
          } else if (databaseType.indexOf("db2") >= 0) {
            sql = String.valueOf(sql) + " and org.orgIdString like '%$" + rootCorpId + "$%'";
          } else {
            sql = String.valueOf(sql) + " and org.orgIdString like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$'," + rootCorpId + "),'$%')";
          } 
        } else if (!"-1".equals(otherDepart)) {
          if ("1".equals(otherDepart)) {
            if (databaseType.indexOf("mysql") >= 0) {
              sql = String.valueOf(sql) + " and org.orgIdString not like '%$" + rootCorpId + "$%'";
            } else if (databaseType.indexOf("db2") >= 0) {
              sql = String.valueOf(sql) + " and org.orgIdString not like '%$" + rootCorpId + "$%'";
            } else {
              sql = String.valueOf(sql) + " and org.orgIdString not like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$'," + rootCorpId + "),'$%')";
            } 
          } else if (databaseType.indexOf("mysql") >= 0) {
            sql = String.valueOf(sql) + " and org.orgIdString like '%$" + rootCorpId + "$%'";
          } else if (databaseType.indexOf("db2") >= 0) {
            sql = String.valueOf(sql) + " and org.orgIdString like '%$" + rootCorpId + "$%'";
          } else {
            sql = String.valueOf(sql) + " and org.orgIdString like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$'," + rootCorpId + "),'$%')";
          } 
        } 
      } else if (!"".equals(currentOrg)) {
        sql = String.valueOf(sql) + " and org.orgId<>" + currentOrg;
      } 
      sql = String.valueOf(sql) + " ORDER BY org.orgOrderCode";
      map.put("org", this.session.createQuery(sql).list());
      map.put("user", this.session.createQuery(
            "SELECT emp.empId,emp.empName,emp.userAccounts,emp.curStatus,emp.imId FROM com.js.system.vo.usermanager.EmployeeVO emp join emp.organizations org WHERE (org.orgId=" + orgId + 
            " or emp.sidelineOrg like '%*" + orgId + 
            "*%') AND emp.userIsActive=1 and emp.userIsDeleted=0 and emp.userAccounts is not null ORDER BY emp.empDutyLevel,emp.userOrderCode,emp.empName").list());
    } catch (Exception e) {
      System.out.println("error ManagerEJB :" + e.getMessage());
      throw e;
    } finally {
      this.session.close();
    } 
    return map;
  }
  
  public Map getSubOrgAndUsersEmail(String orgId, String currentOrg, String domainId, String rootCorpId, String corpId, String departId, String otherDepart) throws Exception {
    HashMap<Object, Object> map = new HashMap<Object, Object>(2);
    String databaseType = SystemCommon.getDatabaseType();
    begin();
    try {
      String sql = "SELECT org.orgId,org.orgName FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE org.orgParentOrgId=" + orgId + " AND org.orgStatus=0 and org.domainId=" + 
        domainId;
      if ("0".equals(orgId)) {
        if (!"".equals(currentOrg)) {
          if (!"-1".equals(otherDepart))
            if ("1".equals(otherDepart)) {
              if (databaseType.indexOf("mysql") >= 0) {
                sql = String.valueOf(sql) + " and org.orgIdString not like '%$" + rootCorpId + "$%'";
              } else if (databaseType.indexOf("db2") >= 0) {
                sql = String.valueOf(sql) + " and org.orgIdString not like '%$" + rootCorpId + "$%'";
              } else {
                sql = String.valueOf(sql) + " and org.orgIdString not like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$'," + rootCorpId + "),'$%')";
              } 
            } else if (databaseType.indexOf("mysql") >= 0) {
              sql = String.valueOf(sql) + " and org.orgIdString like '%$" + rootCorpId + "$%'";
            } else if (databaseType.indexOf("db2") >= 0) {
              sql = String.valueOf(sql) + " and org.orgIdString like '%$" + rootCorpId + "$%'";
            } else {
              sql = String.valueOf(sql) + " and org.orgIdString like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$'," + rootCorpId + "),'$%')";
            }  
          if (databaseType.indexOf("mysql") >= 0) {
            sql = String.valueOf(sql) + " and org.orgIdString like '%$" + rootCorpId + "$%'";
          } else if (databaseType.indexOf("db2") >= 0) {
            sql = String.valueOf(sql) + " and org.orgIdString like '%$" + rootCorpId + "$%'";
          } else {
            sql = String.valueOf(sql) + " and org.orgIdString like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$'," + rootCorpId + "),'$%')";
          } 
        } else if (!"-1".equals(otherDepart)) {
          if ("1".equals(otherDepart)) {
            if (databaseType.indexOf("mysql") >= 0) {
              sql = String.valueOf(sql) + " and org.orgIdString not like '%$" + rootCorpId + "$%'";
            } else if (databaseType.indexOf("db2") >= 0) {
              sql = String.valueOf(sql) + " and org.orgIdString not like '%$" + rootCorpId + "$%'";
            } else {
              sql = String.valueOf(sql) + " and org.orgIdString not like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$'," + rootCorpId + "),'$%')";
            } 
          } else if (databaseType.indexOf("mysql") >= 0) {
            sql = String.valueOf(sql) + " and org.orgIdString like '%$" + rootCorpId + "$%'";
          } else if (databaseType.indexOf("db2") >= 0) {
            sql = String.valueOf(sql) + " and org.orgIdString like '%$" + rootCorpId + "$%'";
          } else {
            sql = String.valueOf(sql) + " and org.orgIdString like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$'," + rootCorpId + "),'$%')";
          } 
        } 
      } else if (!"".equals(currentOrg)) {
        sql = String.valueOf(sql) + " and org.orgId<>" + currentOrg;
      } 
      sql = String.valueOf(sql) + " ORDER BY org.orgOrderCode";
      map.put("org", this.session.createQuery(sql).list());
      map.put("user", this.session.createQuery("SELECT emp.empId,emp.empName,emp.empEmail,emp.curStatus,emp.imId FROM com.js.system.vo.usermanager.EmployeeVO emp join emp.organizations org WHERE (org.orgId=" + 
            
            orgId + " or emp.sidelineOrg like '%*" + orgId + "*%') AND emp.userIsActive=1 " + 
            "and emp.userIsDeleted=0 and emp.userAccounts is not null and emp.empEmail <>'' " + 
            "and emp.empEmail is not null ORDER BY emp.empDutyLevel,emp.userOrderCode,emp.empName").list());
    } catch (Exception e) {
      System.out.println("error ManagerEJB :" + e.getMessage());
      throw e;
    } finally {
      this.session.close();
    } 
    return map;
  }
  
  public Map getSelectedGroupAndOrgAndUsers(String selected) throws Exception {
    HashMap<Object, Object> map = new HashMap<Object, Object>(3);
    String databaseType = SystemCommon.getDatabaseType();
    begin();
    try {
      String sql;
      if (databaseType.indexOf("mysql") >= 0) {
        sql = "SELECT org.orgId,org.orgName FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE '" + selected + "' like concat('%*',org.orgId,'*%') ORDER BY org.orgIdString";
      } else if (databaseType.indexOf("db2") >= 0) {
        sql = "SELECT org.orgId,org.orgName FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%*',org.orgId),'*%'),'" + selected + 
          "')>0  ORDER BY org.orgIdString";
      } else {
        sql = "SELECT org.orgId,org.orgName FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE '" + selected + 
          "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%*',org.orgId),'*%') ORDER BY org.orgIdString";
      } 
      map.put("org", this.session.createQuery(sql).list());
      if (databaseType.indexOf("mysql") >= 0) {
        sql = "SELECT emp.empId,emp.empName,emp.userAccounts,emp.curStatus,emp.imId FROM com.js.system.vo.usermanager.EmployeeVO emp join emp.organizations org WHERE emp.userIsActive=1 and emp.userIsDeleted=0 and emp.userAccounts is not null and '" + 
          selected + "' like concat('%$',emp.empId,'$%') ORDER BY emp.empDutyLevel,emp.userOrderCode,emp.empName";
      } else if (databaseType.indexOf("db2") >= 0) {
        sql = "SELECT emp.empId,emp.empName,emp.userAccounts,emp.curStatus,emp.imId FROM com.js.system.vo.usermanager.EmployeeVO emp join emp.organizations org WHERE emp.userIsActive=1 and emp.userIsDeleted=0 and emp.userAccounts is not null and locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$',emp.empId),'$%'),'" + 
          selected + "')>0  ORDER BY emp.empDutyLevel,emp.userOrderCode,emp.empName";
      } else {
        sql = "SELECT emp.empId,emp.empName,emp.userAccounts,emp.curStatus,emp.imId FROM com.js.system.vo.usermanager.EmployeeVO emp join emp.organizations org WHERE emp.userIsActive=1 and emp.userIsDeleted=0 and emp.userAccounts is not null and '" + 
          selected + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$',emp.empId),'$%') ORDER BY emp.empDutyLevel,emp.userOrderCode,emp.empName";
      } 
      map.put("user", this.session.createQuery(sql).list());
      if (databaseType.indexOf("mysql") >= 0) {
        sql = "SELECT po.groupId,po.groupName FROM com.js.system.vo.groupmanager.GroupVO po WHERE '" + selected + "' like concat('%@',po.groupId,'@%') order by po.groupOrder";
      } else if (databaseType.indexOf("db2") >= 0) {
        sql = "SELECT po.groupId,po.groupName FROM com.js.system.vo.groupmanager.GroupVO po WHERE locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%@',po.groupId),'@%'),'" + selected + 
          "')>0  order by po.groupOrder";
      } else {
        sql = "SELECT po.groupId,po.groupName FROM com.js.system.vo.groupmanager.GroupVO po WHERE '" + selected + 
          "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%@',po.groupId),'@%') order by po.groupOrder";
      } 
      map.put("group", this.session.createQuery(sql).list());
    } catch (Exception e) {
      System.out.println("error ManagerEJB :" + e.getMessage());
      throw e;
    } finally {
      this.session.close();
    } 
    return map;
  }
  
  public String getRightWhere(String userId, String orgId, String orgIdString, String rightType, String rightName, String fieldOrg, String fieldEmp) throws Exception {
    String where = "";
    List<Object[]> list = getRightScope(userId, rightType, rightName);
    Object[] obj = list.get(0);
    String scopeType = obj[0].toString();
    if ("0".equals(scopeType)) {
      where = " 1=1 ";
    } else if ("1".equals(scopeType)) {
      where = "".equals(fieldEmp) ? " 1=1 " : (String.valueOf(fieldEmp) + "=" + userId);
    } else if ("2".equals(scopeType)) {
      String orgRange = getAllJuniorOrgIdByRange("*" + orgId + "*");
      if (orgRange.indexOf("a") > 0) {
        String[] tmp = orgRange.split("a");
        for (int k = 0; k < tmp.length; k++)
          where = String.valueOf(where) + "(" + fieldOrg + " in(" + tmp[k] + ")) or "; 
        if (where.endsWith("or "))
          where = where.substring(0, where.length() - 3); 
      } else {
        where = String.valueOf(fieldOrg) + " in(" + orgRange + ") ";
      } 
    } else if ("3".equals(scopeType)) {
      where = String.valueOf(fieldOrg) + "=" + orgId;
    } else {
      if (obj[1] != null && !"".equals(obj[1].toString())) {
        String orgRange = getAllJuniorOrgIdByRange((String)obj[1]);
        if ("".equals(orgRange)) {
          where = "1>2";
        } else if (orgRange.indexOf("a") > 0) {
          String[] tmp = orgRange.split("a");
          for (int k = 0; k < tmp.length; k++)
            where = String.valueOf(where) + "(" + fieldOrg + " in(" + tmp[k] + ")) or "; 
          if (where.endsWith("or "))
            where = where.substring(0, where.length() - 3); 
        } else {
          where = String.valueOf(fieldOrg) + " in(" + orgRange + ") ";
        } 
      } 
      String dbType = SystemCommon.getDatabaseType();
      if (obj.length > 2 && obj[3] != null && !"".equals(obj[3].toString())) {
        if (!"".equals(where))
          where = String.valueOf(where) + " or "; 
        if ("oracle".equals(dbType) || "mysql".equals(fieldEmp)) {
          where = String.valueOf(where) + obj[3].toString() + "' like '%$'||" + fieldEmp + "||'$%'";
        } else if ("mssqlserver".equals(dbType)) {
          where = String.valueOf(where) + obj[3].toString() + "' like '%$'+" + fieldEmp + "+'$%'";
        } else if ("db2".equals(fieldEmp)) {
          where = String.valueOf(where) + obj[3].toString() + "' like '%$'+" + fieldEmp + "+'$%'";
        } 
      } 
    } 
    return where;
  }
  
  public String getRightFinalWhere(String userId, String orgId, String orgIdString, String rightType, String rightName, String fieldOrg, String fieldEmp) throws Exception {
    String where;
    boolean bln = hasRightTypeName(userId, rightType, rightName).booleanValue();
    if (!bln) {
      where = " 1<>1 ";
    } else {
      where = getRightWhere(userId, orgId, orgIdString, rightType, rightName, fieldOrg, fieldEmp);
    } 
    return where;
  }
  
  public String getScopeWhere(String userId, String orgId, String orgIdString, String fieldUser, String fieldOrg, String fieldGroup, String fieldCreatedEmp) throws Exception {
    StringBuffer where = new StringBuffer();
    where.append(fieldUser).append(" like '%$").append(userId).append("$%'").append(" or ");
    where.append("(").append(whereOrg(fieldOrg, orgIdString)).append(") or ");
    where.append("(").append(whereGroup(fieldGroup, userId)).append(") or ");
    where.append(fieldCreatedEmp).append("=").append(userId);
    return where.toString();
  }
  
  public String getScopeFinalWhere(String userId, String orgId, String orgIdString, String fieldUser, String fieldOrg, String fieldGroup, String fieldName) throws Exception {
    StringBuffer where = new StringBuffer();
    where.append(fieldUser).append(" like '%$").append(userId).append("$%'").append(" or ");
    where.append("(").append(whereOrg(fieldOrg, orgIdString)).append(" or ").append(fieldName).append("='') or ");
    where.append("(").append(whereGroup(fieldGroup, userId)).append(") ");
    return where.toString();
  }
  
  public String getScopeFinalWhere(String userId, String orgId, String orgIdString, String fieldUser, String fieldOrg, String fieldGroup) throws Exception {
    StringBuffer where = new StringBuffer();
    where.append(fieldUser).append(" like '%$").append(userId).append("$%'").append(" or ");
    where.append("(").append(whereOrg(fieldOrg, orgIdString)).append(") or ");
    where.append("(").append(whereGroup(fieldGroup, userId)).append(") ");
    return where.toString();
  }
  
  private String whereOrg(String fieldOrg, String orgIdString) throws Exception {
    StringBuffer retString = new StringBuffer(" 1<>1 ");
    try {
      orgIdString = "$" + orgIdString + "$";
      String[] arr = orgIdString.split("\\$\\$");
      for (int i = 0; i < arr.length; i++) {
        if (arr[i] != null && !arr[i].equals("") && !arr[i].equals(" "))
          retString.append(" or ").append(fieldOrg).append(" like '%*").append(arr[i]).append("*%'"); 
      } 
    } catch (Exception e) {
      retString = new StringBuffer(" 1<>1 ");
      e.printStackTrace();
    } 
    return retString.toString();
  }
  
  public String whereGroup(String fieldGroup, String userId) throws Exception {
    StringBuffer retString = new StringBuffer(" 1<>1 ");
    try {
      begin();
      List list = this.session.createQuery("select groups.groupId from com.js.system.vo.usermanager.EmployeeVO user join user.groups groups where user.empId = " + userId).list();
      for (int i = 0; i < list.size(); i++)
        retString.append(" or ").append(fieldGroup).append(" like '%@").append(list.get(i)).append("@%'"); 
    } catch (Exception e) {
      retString = new StringBuffer(" 1<>1 ");
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return retString.toString();
  }
  
  public String getEmployeesAccounts(String empIds) {
    StringBuffer result = new StringBuffer();
    try {
      Query query = null;
      begin();
      if (empIds.indexOf("$") >= 0) {
        String tmpSql = "";
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "select distinct emp.userAccounts from com.js.system.vo.usermanager.EmployeeVO emp where '" + empIds + "' like concat('%$', emp.empId, '$%')";
        } else if (databaseType.indexOf("db2") >= 0) {
          tmpSql = "select distinct emp.userAccounts from com.js.system.vo.usermanager.EmployeeVO emp where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(emp.empId)), '$'),'" + 
            empIds + "')>0 ";
        } else {
          tmpSql = "select distinct emp.userAccounts from com.js.system.vo.usermanager.EmployeeVO emp where '" + empIds + 
            "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(emp.empId)), '$%')";
        } 
        query = this.session.createQuery(tmpSql);
      } else {
        query = this.session.createQuery("select distinct emp.userAccounts from com.js.system.vo.usermanager.EmployeeVO emp where emp.empId in (" + empIds + ")");
      } 
      Iterator iterator = query.iterate();
      if (iterator != null)
        while (iterator.hasNext())
          result.append(iterator.next()).append(",");  
    } catch (HibernateException ex) {
      throw new EJBException("Hibernate Exception:", ex);
    } finally {
      try {
        this.session.close();
      } catch (HibernateException ex1) {
        throw new EJBException("Close session Exception:", ex1);
      } 
    } 
    if (result.length() > 0)
      return result.substring(0, result.length() - 1); 
    return "";
  }
  
  public Boolean hasRight(String userId, String rightCode) throws Exception {
    Boolean result = new Boolean(false);
    begin();
    try {
      Long uId = Long.valueOf(userId);
      String domainId = getUserDomainId(userId);
      String tmpSql = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "select count(aaa.rightScopeId) from com.js.system.vo.rolemanager.RightScopeVO aaa join aaa.employee bbb join aaa.right ccc where bbb.empId = " + uId.toString() + 
          " and ccc.rightCode like concat('" + rightCode + "', '%') and ccc.domainId=" + domainId;
      } else {
        tmpSql = "select count(aaa.rightScopeId) from com.js.system.vo.rolemanager.RightScopeVO aaa join aaa.employee bbb join aaa.right ccc where bbb.empId = " + uId.toString() + 
          " and ccc.rightCode like '" + rightCode + "%' and ccc.domainId=" + domainId;
      } 
      Query query = this.session.createQuery(tmpSql);
      int count = ((Integer)query.iterate().next()).intValue();
      if (count > 0)
        result = Boolean.TRUE; 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public Boolean hasRights(String userId, String rightCodes) throws Exception {
    Boolean result = new Boolean(false);
    begin();
    try {
      String domainId = getUserDomainId(userId);
      String tmpSql = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "select count(aaa.rightScopeId) from com.js.system.vo.rolemanager.RightScopeVO aaa join aaa.employee bbb join aaa.right ccc where bbb.empId = " + userId + 
          " and '" + rightCodes + "' like concat('%',ccc.rightCode,'%') and ccc.domainId=" + domainId;
      } else {
        tmpSql = "select count(aaa.rightScopeId) from com.js.system.vo.rolemanager.RightScopeVO aaa join aaa.employee bbb join aaa.right ccc where bbb.empId = " + userId + 
          " and '" + rightCodes + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%',ccc.rightCode),'%') and ccc.domainId=" + domainId;
      } 
      Query query = this.session.createQuery(tmpSql);
      int count = ((Integer)query.iterate().next()).intValue();
      if (count > 0)
        result = Boolean.TRUE; 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public Boolean hasRightTypeScope(String userId, String orgId, String rightCode, String channelType) throws Exception {
    Boolean result = new Boolean(false);
    begin();
    try {
      String domainId = getUserDomainId(userId);
      Query query = this.session.createQuery(" select aaa.rightScopeType,aaa.rightScopeScope from  com.js.system.vo.rolemanager.RightScopeVO aaa  join aaa.employee bbb join aaa.right ccc where  bbb.empId = " + 
          userId + " and ccc.rightCode = '" + rightCode + "' and ccc.domainId=" + domainId);
      List<Object[]> list = query.list();
      if (list != null && list.size() > 0 && list.get(0) != null) {
        Object[] obj = list.get(0);
        String rightScopeType = obj[0].toString();
        String rightScopeScope = "";
        if (obj[1] != null) {
          rightScopeScope = obj[1].toString();
          rightScopeScope = rightScopeScope.substring(1, rightScopeScope.length() - 1);
        } 
        if (rightScopeType.equals("0")) {
          result = Boolean.TRUE;
        } else if (rightScopeType.equals("2")) {
          query = this.session.createQuery(" select aaa.orgId from  com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like '%$'" + orgId + "'$%'");
          list = query.list();
          for (int i = 0; i < list.size(); i++) {
            if (list.get(i).toString().equals(channelType)) {
              result = Boolean.TRUE;
              break;
            } 
          } 
        } else if (rightScopeType.equals("3")) {
          if (orgId.equals(channelType))
            result = Boolean.TRUE; 
        } else if (rightScopeType.equals("4")) {
          query = this.session.createQuery(" select aaa.orgId from  com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like '%$" + rightScopeScope + "$%'");
          list = query.list();
          for (int i = 0; i < list.size(); i++) {
            if (list.get(i).toString().equals(channelType)) {
              result = Boolean.TRUE;
              break;
            } 
          } 
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public List getRightScope(String userId, String rightCode) throws Exception {
    List list = null;
    begin();
    try {
      Long uId = Long.valueOf(userId);
      String domainId = getUserDomainId(userId);
      Query query = this.session
        .createQuery("select aaa.rightScopeType,aaa.rightScopeScope,aaa.rightScope,aaa.rightScopeUser,aaa.rightScopeGroup from com.js.system.vo.rolemanager.RightScopeVO aaa join aaa.employee bbb join aaa.right ccc where bbb.empId = " + 
          uId.toString() + " and ccc.rightCode = '" + rightCode + "' and ccc.domainId=" + domainId);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public String getRightWhere(String userId, String orgId, String rightCode, String fieldOrg, String fieldEmp) throws Exception {
    String where = "";
    List<Object[]> list = getRightScope(userId, rightCode);
    if (list != null && list.size() > 0) {
      Object[] obj = list.get(0);
      String scopeType = obj[0].toString();
      if ("0".equals(scopeType)) {
        where = " 1=1 ";
      } else if ("1".equals(scopeType)) {
        where = "".equals(fieldEmp) ? "1>1" : (String.valueOf(fieldEmp) + "=" + userId);
      } else if ("2".equals(scopeType)) {
        String orgRange = getAllJuniorOrgIdByRange("*" + orgId + "*");
        if (orgRange.indexOf("a") > 0) {
          String[] tmp = orgRange.split("a");
          for (int k = 0; k < tmp.length; k++)
            where = String.valueOf(where) + "(" + fieldOrg + " in(" + tmp[k] + ")) or "; 
          if (where.endsWith("or "))
            where = where.substring(0, where.length() - 3); 
        } else {
          where = String.valueOf(fieldOrg) + " in(" + orgRange + ") ";
        } 
      } else if ("3".equals(scopeType)) {
        where = String.valueOf(fieldOrg) + "=" + orgId;
      } else {
        if (obj[1] != null && !"".equals(obj[1].toString())) {
          String orgRange = getAllJuniorOrgIdByRange((String)obj[1]);
          if ("".equals(orgRange)) {
            where = "1>2";
          } else if (orgRange.indexOf("a") > 0) {
            String[] tmp = orgRange.split("a");
            for (int k = 0; k < tmp.length; k++)
              where = String.valueOf(where) + "(" + fieldOrg + " in(" + tmp[k] + ")) or "; 
            if (where.endsWith("or "))
              where = where.substring(0, where.length() - 3); 
          } else {
            where = String.valueOf(fieldOrg) + " in(" + orgRange + ") ";
          } 
        } 
        String dbType = SystemCommon.getDatabaseType();
        if (obj[3] != null && !"".equals(obj[3].toString())) {
          if (!"".equals(where))
            where = String.valueOf(where) + " or "; 
          if ("mysql".equals(dbType)) {
            where = String.valueOf(where) + "'" + obj[3].toString() + "' like concat('%$'," + fieldEmp + ",'$%')";
          } else if ("oracle".equals(dbType)) {
            where = String.valueOf(where) + "'" + obj[3].toString() + "' like '%$'||" + fieldEmp + "||'$%'";
          } else if ("mssqlserver".equals(dbType)) {
            where = String.valueOf(where) + "'" + obj[3].toString() + "' like '%$'+" + fieldEmp + "+'$%'";
          } else if ("db2".equals(dbType)) {
            where = String.valueOf(where) + "'" + obj[3].toString() + "' like '%$'+" + fieldEmp + "+'$%'";
          } 
        } 
      } 
    } else {
      where = "1>2";
    } 
    return "(" + where + ")";
  }
  
  public String getRightFinalWhere(String userId, String orgId, String rightCode, String fieldOrg, String fieldEmp) throws Exception {
    String where;
    boolean bln = hasRight(userId, rightCode).booleanValue();
    if (!bln) {
      where = " 1<>1 ";
    } else {
      where = getRightWhere(userId, orgId, rightCode, fieldOrg, fieldEmp);
    } 
    return where;
  }
  
  private String getUserDomainId(String userId) {
    return "0";
  }
  
  public List getAllDuty(String domainId) throws Exception {
    List list = new ArrayList();
    try {
      begin();
      list = this.session.createQuery("select po.id,po.dutyName,po.dutyLevel from com.js.oa.hr.officemanager.po.DutyPO po where po.domainId=" + domainId + " order by po.dutyLevel").list();
      this.session.close();
    } catch (Exception ex) {
      try {
        this.session.close();
      } catch (Exception err) {
        throw err;
      } 
      throw ex;
    } finally {}
    return list;
  }
  
  public String getEmpNameByEmpId(String empId) throws Exception {
    String content = "";
    begin();
    try {
      Query query = this.session.createQuery(
          " select aaa.empName from  com.js.system.vo.usermanager.EmployeeVO aaa  where aaa.empId = " + 
          
          empId);
      Iterator iter = query.iterate();
      if (iter.hasNext()) {
        Object tmp = iter.next();
        content = (tmp == null) ? "" : tmp.toString();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return content;
  }
  
  public String getOrgNameByOrgId(String orgId) throws Exception {
    String content = "";
    begin();
    try {
      Query query = this.session.createQuery(
          " select aaa.orgName from  com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgId = " + (
          
          orgId.equals("-1") ? "0" : orgId));
      Iterator iter = query.iterate();
      if (iter.hasNext()) {
        Object tmp = iter.next();
        content = (tmp == null) ? "" : tmp.toString();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return content;
  }
  
  public String getGroupNameByGroupId(String groupId) throws Exception {
    String content = "";
    begin();
    try {
      Query query = this.session.createQuery(
          " select aaa.groupName from  com.js.system.vo.groupmanager.GroupVO aaa  where aaa.groupId = " + 
          
          groupId);
      Iterator iter = query.iterate();
      if (iter.hasNext()) {
        Object tmp = iter.next();
        content = (tmp == null) ? "" : tmp.toString();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return content;
  }
}
