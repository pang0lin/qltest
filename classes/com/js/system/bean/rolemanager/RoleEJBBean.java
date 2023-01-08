package com.js.system.bean.rolemanager;

import com.js.oa.audit.bean.AuditMsRemindEJBBean;
import com.js.oa.audit.po.AuditLog;
import com.js.oa.audit.po.RolePO;
import com.js.system.util.ConvertIdAndName;
import com.js.system.util.EndowVO;
import com.js.system.vo.rightmanager.RightVO;
import com.js.system.vo.rightmanager.SimpleRightVO;
import com.js.system.vo.rolemanager.HandRoleVO;
import com.js.system.vo.rolemanager.RightScopeVO;
import com.js.system.vo.rolemanager.RoleVO;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.FillBean;
import java.sql.Connection;
import java.sql.ResultSet;
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

public class RoleEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public List getRoles() throws Exception {
    List<RoleVO> rolesList = new ArrayList();
    begin();
    try {
      String sql = "SELECT role.roleId,role.roleName,role.roleDescription FROM com.js.system.vo.rolemanager.RoleVO role ORDER BY role.roleName";
      Query query = this.session.createQuery(sql);
      List<Object[]> result = query.list();
      for (int i = 0; i < result.size(); i++) {
        Object[] resultArray = result.get(i);
        RoleVO roleVO = new RoleVO();
        roleVO.setRoleId(((Long)resultArray[0]).longValue());
        roleVO.setRoleName((String)resultArray[1]);
        roleVO.setRoleDescription((String)resultArray[2]);
        rolesList.add(roleVO);
        resultArray = (Object[])null;
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return rolesList;
  }
  
  public List getOwnerRoles(String userId, String orgId, String browseRange, String managerType, String domainId) throws Exception {
    List list = null;
    begin();
    try {
      String sql = "";
      String whereSql = "";
      int type = Integer.parseInt(managerType);
      if (type != 1) {
        Iterator<Object[]> iter = this.session.iterate("select aaa.rightScopeType,aaa.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO aaa join aaa.employee bbb join aaa.right ccc where bbb.empId = " + userId + " and ccc.rightCode = '00*01*02'");
        Object[] obj = iter.next();
        String scopeType = obj[0].toString();
        String scopScope = (String)obj[1];
        if (!"0".equals(scopeType)) {
          if ("1".equals(scopeType)) {
            whereSql = " WHERE (role.createdEmp=" + userId;
          } else if ("2".equals(scopeType)) {
            whereSql = " WHERE (role.createdOrg=" + orgId + 
              " OR role.createdEmp=" + userId;
          } else if ("3".equals(scopeType)) {
            String orgRange = getAllJuniorOrgIdByRange("*" + orgId + "*");
            whereSql = " WHERE ((role.createdOrg in(" + orgRange + ") AND role.roleId>=2) OR role.createdEmp=" + userId;
          } else if ("4".equals(scopeType)) {
            String orgRange = getAllJuniorOrgIdByRange(scopScope);
            whereSql = " WHERE ((role.createdOrg in(" + orgRange + ") AND role.roleId>=2) OR role.createdEmp=" + userId;
          } 
          whereSql = String.valueOf(whereSql) + " OR role.roleId=2) ";
        } 
      } 
      if (whereSql.equals("")) {
        whereSql = String.valueOf(whereSql) + " where role.domainId=" + domainId;
      } else {
        whereSql = String.valueOf(whereSql) + " and role.domainId=" + domainId;
        whereSql = String.valueOf(whereSql) + " or role.roleUseRange like '%$" + userId + "$%'";
      } 
      sql = "SELECT role.roleId,role.roleName,role.roleDescription FROM com.js.system.vo.rolemanager.RoleVO role " + whereSql + " ORDER BY role.roleName";
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
  
  public String getCanUpdateIds(String sql) throws Exception {
    String res = ",";
    List<E> list = null;
    begin();
    try {
      list = this.session.createQuery("select role.roleId from com.js.system.vo.rolemanager.RoleVO role " + sql).list();
      if (list != null)
        for (int i = 0; i < list.size(); i++)
          res = String.valueOf(res) + list.get(i).toString() + ",";  
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
    return res;
  }
  
  public List getRights(String[] roleIds) throws Exception {
    List rightsList = new ArrayList();
    StringBuffer buffer = new StringBuffer();
    begin();
    try {
      for (int i = 0; i < roleIds.length; i++) {
        if (!"".equals(roleIds[i]))
          buffer.append(roleIds[i]).append(","); 
      } 
      buffer.append("-1");
      rightsList = this.session.createQuery("select po from com.js.system.vo.rightmanager.RightVO po join po.role rolePO where rolePO.roleId in(" + buffer.toString() + ") order by po.rightId").list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return rightsList;
  }
  
  public Integer add(RoleVO roleVO, String[] ids) throws Exception {
    int result = 0;
    ConvertIdAndName cIdAndName = new ConvertIdAndName();
    EndowVO endowVO = cIdAndName.splitId(roleVO.getRoleUserId());
    String[] empIds = endowVO.getEmpIdArray().split(",");
    begin();
    try {
      String name = roleVO.getRoleName();
      result = ((Integer)this.session.iterate("select count(*) from com.js.system.vo.rolemanager.RoleVO vo where vo.roleName='" + name.trim() + "'").next()).intValue();
      if (result > 0)
        return new Integer(1); 
      Set<Object> rightSet = new HashSet();
      this.session.save(roleVO);
      int i;
      for (i = 0; i < ids.length; i++) {
        if (!ids[i].equals(""))
          rightSet.add(this.session.load(RightVO.class, new Long(ids[i]))); 
      } 
      roleVO.setRights(rightSet);
      Set<EmployeeVO> employees = new HashSet();
      Set<RightScopeVO> rightScopes = new HashSet();
      int num = 0;
    } catch (Exception e) {
      result = 2;
      System.out.println(e);
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    this.session.close();
    this.session = null;
    this.transaction = null;
    return new Integer(result);
  }
  
  public void deleteSingle(String id) throws Exception {
    StringBuffer buffer = new StringBuffer();
    begin();
    try {
      List list = this.session.createQuery("select rightVO.rightId from com.js.system.vo.rolemanager.RoleVO roleVO join roleVO.rights rightVO where roleVO.roleId =" + 
          id).list();
      if (list != null && list.size() > 0)
        for (int i = 0; i < list.size(); i++)
          buffer.append(list.get(i)).append(",");  
      String shouldDel = buffer.append("-1").toString();
      buffer = new StringBuffer();
      list = this.session.createQuery("select rightVO.rightId from com.js.system.vo.rolemanager.RoleVO roleVO join roleVO.rights rightVO where roleVO.roleId <>" + 
          id + " and rightVO.rightId in (" + 
          shouldDel + ")").list();
      if (list != null && list.size() > 0)
        for (int i = 0; i < list.size(); i++)
          buffer.append(list.get(i)).append(",");  
      String shouldnotDel = buffer.append("-1").toString();
      this.session.delete("from com.js.system.vo.rolemanager.RightScopeVO sc  where sc.right.rightId in (" + 
          shouldDel + ") and sc.right.rightId not in (" + 
          shouldnotDel + ")");
      this.session.delete("from com.js.system.vo.rolemanager.RoleVO aaa where aaa.roleId = " + id);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void batchDelete(String[] batchDeleteId) throws Exception {
    StringBuffer buffer = new StringBuffer();
    String id = "";
    for (int i = 0; i < batchDeleteId.length; i++)
      id = String.valueOf(id) + batchDeleteId[i] + ","; 
    id = id.substring(0, id.length() - 1);
    begin();
    try {
      List list = this.session.createQuery("select rightVO.rightId from com.js.system.vo.rolemanager.RoleVO roleVO join roleVO.rights rightVO where roleVO.roleId in(" + 
          id + ")").list();
      if (list != null && list.size() > 0)
        for (int j = 0; j < list.size(); j++)
          buffer.append(list.get(j)).append(",");  
      String shouldDel = buffer.append("-1").toString();
      buffer = new StringBuffer();
      list = this.session.createQuery("select rightVO.rightId from com.js.system.vo.rolemanager.RoleVO roleVO join roleVO.rights rightVO where roleVO.roleId not in (" + 
          id + ") and rightVO.rightId in (" + 
          shouldDel + ")").list();
      if (list != null && list.size() > 0)
        for (int j = 0; j < list.size(); j++)
          buffer.append(list.get(j)).append(",");  
      String shouldnotDel = buffer.append("-1").toString();
      this.session.delete("from com.js.system.vo.rolemanager.RightScopeVO sc where sc.right.rightId in (" + 
          shouldDel + ") and sc.right.rightId not in (" + 
          shouldnotDel + ")");
      this.session.delete("from com.js.system.vo.rolemanager.RoleVO aaa where aaa.roleId in (" + id + ")");
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void deleteAll() throws Exception {
    begin();
    try {
      this.session.delete("form com.js.system.rolemanager.RightScopeVO");
      this.session.delete("from com.js.system.vo.rolemanager.RoleVO");
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public Integer update(String modifyId, String[] rightId, String empIds, String roleUserId, String roleUserName, String roleName, String roleDescription, String createdOrg) throws Exception {
    int result = 2;
    begin();
    result = ((Integer)this.session.iterate("select count(*) from com.js.system.vo.rolemanager.RoleVO vo where vo.roleName='" + roleName.trim() + "' and vo.roleId<>" + modifyId).next()).intValue();
    if (result > 0) {
      result = 1;
      return new Integer(1);
    } 
    if ("".equals(empIds))
      empIds = "-1"; 
    StringBuffer buffer = new StringBuffer();
    int i;
    for (i = 0; i < rightId.length; i++) {
      if (!rightId[i].equals(""))
        buffer.append(rightId[i]).append(","); 
    } 
    buffer.append("-1");
    String nowRight = buffer.toString();
    buffer = new StringBuffer();
    try {
      List<E> list = this.session.createQuery("select rightVO.rightId from com.js.system.vo.rolemanager.RoleVO roleVO join roleVO.rights rightVO where roleVO.roleId=" + modifyId).list();
      if (list != null && list.size() > 0)
        for (i = 0; i < list.size(); i++)
          buffer.append(list.get(i)).append(",");  
      String oldRight = buffer.append("-1").toString();
      buffer = new StringBuffer();
      list = this.session.createQuery("select emp.empId from com.js.system.vo.rolemanager.RoleVO roleVO join roleVO.employees emp where roleVO.roleId=" + modifyId).list();
      if (list != null && list.size() > 0)
        for (i = 0; i < list.size(); i++)
          buffer.append(list.get(i)).append(",");  
      String user = buffer.append("-1").toString();
      buffer = new StringBuffer();
      list = this.session.createQuery("select rightVO.rightId from com.js.system.vo.rightmanager.RightVO rightVO where rightVO.rightId in(" + oldRight + ") and rightVO.rightId not in (" + nowRight + ")").list();
      if (list != null && list.size() > 0)
        for (i = 0; i < list.size(); i++)
          buffer.append(list.get(i)).append(",");  
      String cutRight = buffer.append("-1").toString();
      buffer = new StringBuffer();
      list = this.session.createQuery("select rightVO.rightId from com.js.system.vo.rightmanager.RightVO rightVO where rightVO.rightId not in(" + oldRight + ") and rightVO.rightId in (" + nowRight + ")").list();
      if (list != null && list.size() > 0)
        for (i = 0; i < list.size(); i++)
          buffer.append(list.get(i)).append(",");  
      String addRight = buffer.append("-1").toString();
      buffer = new StringBuffer();
      RoleVO roleVO = (RoleVO)this.session.load(RoleVO.class, new Long(modifyId));
      Set<Object> rightSet = new HashSet();
      for (i = 0; i < rightId.length; i++) {
        if (!rightId[i].equals("")) {
          buffer.append(rightId[i]).append(",");
          rightSet.add(this.session.load(RightVO.class, new Long(rightId[i])));
        } 
      } 
      String endRight = buffer.append("-1").toString();
      buffer = new StringBuffer();
      roleVO.setRoleName(roleName);
      roleVO.setRoleDescription(roleDescription);
      roleVO.setCreatedOrg(Long.parseLong(createdOrg));
      roleVO.setRights(rightSet);
      roleVO.setRoleUserId(roleUserId);
      roleVO.setRoleUserName(roleUserName);
      list = this.session.createQuery("select rightVO.rightId from com.js.system.vo.rightmanager.RightVO rightVO join rightVO.role roleVO join roleVO.employees empVO where empVO.empId in (" + user + ") and roleVO.roleId <> " + modifyId).list();
      StringBuffer otherRightId = new StringBuffer();
      if (list != null && list.size() > 0)
        for (int ii = 0; ii < list.size(); ii++)
          otherRightId.append((new StringBuilder()).append(list.get(ii)).append(",").toString());  
      list = this.session.createQuery("select sc from com.js.system.vo.rolemanager.RightScopeVO sc join sc.right r join sc.employee emp where emp.empId in (" + user + ") and r.rightId in(" + cutRight + ") and r.rightId not in (" + otherRightId.toString() + "-1)").list();
      if (list != null && list.size() > 0)
        for (i = 0; i < list.size(); i++)
          this.session.delete(list.get(i));  
      String[] rightArray = addRight.split(",");
      for (i = 0; i < rightArray.length; i++) {
        list = this.session.createQuery("select distinct emp.empId from com.js.system.vo.rolemanager.RightScopeVO sc join sc.employee emp where emp.empId in (" + user + ") and " + rightArray[i] + " not in (select scr.rightId  from com.js.system.vo.rolemanager.RightScopeVO scVO join scVO.right scr join  scVO.employee employee where employee.empId=emp.empId)").list();
        if (list != null && list.size() > 0) {
          long rightScopeSeq = getTableId(list.size()).longValue();
          for (int j = 0; j < list.size(); j++, rightScopeSeq++) {
            if (!rightArray[i].equals("-1")) {
              RightScopeVO rightScope = new RightScopeVO();
              RightVO rightVO = (RightVO)this.session.load(RightVO.class, new Long(rightArray[i]));
              EmployeeVO employeeVO = (EmployeeVO)this.session.load(EmployeeVO.class, new Long(String.valueOf(list.get(j))));
              Set<RightScopeVO> rightScopeSet = employeeVO.getRightScopes();
              rightScope.setRightScopeId(rightScopeSeq);
              rightScope.setEmployee(employeeVO);
              rightScope.setRight(rightVO);
              rightScope.setRightScopeType(Short.parseShort("2"));
              rightScope.setRightScopeScope("");
              this.session.save(rightScope);
              rightVO.addRightScopes(rightScope);
              rightScopeSet.add(rightScope);
              employeeVO.setRightScopes(rightScopeSet);
            } 
          } 
        } 
      } 
      Set<EmployeeVO> empSet = roleVO.getEmployees();
      if (empSet == null)
        empSet = new HashSet(); 
      list = this.session.createQuery("select emp.empId from com.js.system.vo.usermanager.EmployeeVO emp where emp.empId in(" + user + ") and emp.empId not in(" + empIds + ")").list();
      if (list != null && list.size() > 0)
        for (i = 0; i < list.size(); i++) {
          String empId = list.get(i).toString();
          EmployeeVO employeeVO = (EmployeeVO)this.session.load(EmployeeVO.class, new Long(empId));
          empSet.remove(employeeVO);
          buffer.append(list.get(i)).append(",");
        }  
      buffer.append("-1");
      String shouldDelUser = buffer.toString();
      list = this.session.createQuery("select sc from com.js.system.vo.rolemanager.RightScopeVO sc join sc.right r join sc.employee emp where emp.empId in (" + shouldDelUser + ") and r.rightId in(" + endRight + ")").list();
      if (list != null && list.size() > 0)
        for (i = 0; i < list.size(); i++)
          this.session.delete(list.get(i));  
      list = this.session.createQuery("select emp.empId from com.js.system.vo.usermanager.EmployeeVO emp where emp.empId not in(" + user + ") and emp.empId in(" + empIds + ")").list();
      if (list != null && list.size() > 0)
        for (i = 0; i < list.size(); i++) {
          String empId = list.get(i).toString();
          EmployeeVO employeeVO = (EmployeeVO)this.session.load(EmployeeVO.class, new Long(empId));
          empSet.add(employeeVO);
          long rightScopeSeq = getTableId(rightSet.size()).longValue();
          Iterator<RightVO> it = rightSet.iterator();
          Set<RightScopeVO> rightScopeSet = employeeVO.getRightScopes();
          while (it.hasNext()) {
            RightVO rightVO = it.next();
            RightScopeVO rightScope = new RightScopeVO();
            rightScope.setRightScopeId(rightScopeSeq);
            rightScope.setEmployee(employeeVO);
            rightScope.setRight(rightVO);
            rightScope.setRightScopeType(Byte.parseByte("2"));
            rightScope.setRightScopeScope("");
            this.session.save(rightScope);
            rightScopeSet.add(rightScope);
            rightVO.addRightScopes(rightScope);
            rightScopeSeq++;
          } 
          employeeVO.setRightScopes(rightScopeSet);
        }  
      roleVO.setEmployees(empSet);
      this.session.flush();
      result = 0;
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return new Integer(result);
  }
  
  public List getSingleRoleInfo(String roleId) throws Exception {
    begin();
    List<Object[]> list = null;
    try {
      Query query = this.session.createQuery("select aaa.roleName,aaa.roleDescription,aaa.createdOrg,aaa.roleUseRange,aaa.roleUseName,aaa.roleRangeType,aaa.roleRange,aaa.roleRangeName from com.js.system.vo.rolemanager.RoleVO aaa where aaa.roleId = " + roleId);
      list = query.list();
      Object[] obj = (Object[])null;
      StringBuffer empIds = new StringBuffer(100);
      StringBuffer empNames = new StringBuffer(100);
      if (list != null) {
        Iterator<Object[]> it = this.session.createQuery("select distinct emp.empId,emp.empName from com.js.system.vo.rolemanager.RoleVO roleVO join roleVO.employees emp where roleVO.roleId=" + roleId).iterate();
        if (it != null)
          while (it.hasNext()) {
            obj = it.next();
            empIds.append("$").append(obj[0]).append("$");
            empNames.append(obj[1]).append(",");
          }  
        Object[] objRole = list.get(0);
        obj = new Object[10];
        obj[0] = objRole[0];
        obj[1] = objRole[1];
        obj[2] = objRole[2];
        obj[3] = empIds.toString();
        obj[4] = empNames.toString();
        obj[5] = objRole[3];
        obj[6] = objRole[4];
        obj[7] = objRole[5];
        obj[8] = objRole[6];
        obj[9] = objRole[7];
        list = new ArrayList();
        list.add(obj);
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
  
  public List getSingleRoleInfo(Long logId) throws Exception {
    begin();
    List<Object[]> list = null;
    Connection conn = (new DataSourceBase()).getDataSource().getConnection();
    Statement stmt = conn.createStatement();
    ResultSet rs = null;
    try {
      Query query = this.session.createQuery("select aaa.roleName,aaa.roleDescription,aaa.createdOrg,aaa.roleUseRange,aaa.roleUseName,aaa.roleRangeType,aaa.roleRange,aaa.roleRangeName,aaa.roleId,aaa.operate from com.js.oa.audit.po.RolePO aaa where aaa.logId = " + 
          logId);
      list = query.list();
      Object[] obj = (Object[])null;
      StringBuffer empIds = new StringBuffer(100);
      StringBuffer empNames = new StringBuffer(100);
      if (list != null) {
        Object[] objRole = list.get(0);
        String sql = "select emp_id,empname from org_employee where emp_id in (select emp_Id from audit_user_role where log_id=" + logId + ")";
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
          empIds.append("$").append(rs.getString(1)).append("$");
          empNames.append(rs.getString(2)).append(",");
        } 
        rs.close();
        if (empIds.toString().length() <= 0) {
          sql = "select emp_id,empname from org_employee where emp_id in (select emp_Id from org_user_role where role_id=" + objRole[8] + ")";
          rs = stmt.executeQuery(sql);
          while (rs.next()) {
            empIds.append("$").append(rs.getString(1)).append("$");
            empNames.append(rs.getString(2)).append(",");
          } 
        } 
        obj = new Object[12];
        obj[0] = objRole[0];
        obj[1] = objRole[1];
        obj[2] = objRole[2];
        obj[3] = empIds.toString();
        obj[4] = empNames.toString();
        obj[5] = objRole[3];
        obj[6] = objRole[4];
        obj[7] = objRole[5];
        obj[8] = objRole[6];
        obj[9] = objRole[7];
        obj[10] = objRole[8];
        obj[11] = objRole[9];
        list = new ArrayList();
        list.add(obj);
      } 
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
    return list;
  }
  
  public List getSingleRoleRightId(String roleId) throws Exception {
    begin();
    List list = null;
    try {
      Query query = this.session.createQuery("select bbb.rightId from com.js.system.vo.rolemanager.RoleVO aaa join aaa.rights bbb where aaa.roleId = " + roleId);
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
  
  public List getSingleRoleRightId(Long logId) throws Exception {
    Connection conn = (new DataSourceBase()).getDataSource().getConnection();
    Statement stmt = conn.createStatement();
    ResultSet rs = null;
    List<String> list = new ArrayList();
    try {
      rs = stmt.executeQuery("select distinct right_id from audit_role_right where log_Id=" + logId);
      while (rs.next())
        list.add(rs.getString(1)); 
    } catch (Exception e) {
      throw e;
    } finally {
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
    } 
    return list;
  }
  
  public List getAllIdAndName(String domainId) throws Exception {
    begin();
    List list = null;
    try {
      Query query = this.session.createQuery("select aaa.roleId,aaa.roleName from com.js.system.vo.rolemanager.RoleVO aaa where aaa.domainId=" + domainId);
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
  
  public Map getDistinctRights(String roleId) throws Exception {
    Map<Object, Object> map = new HashMap<Object, Object>();
    Connection conn = null;
    try {
      List<SimpleRightVO> rightList = new ArrayList();
      List<String> classList = new ArrayList();
      String rightClass = "", oldRightClass = "";
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select distinct oright.right_id,oright.rightname,oright.righthasscope,oright.rightselectrange,oright.righttype,oright.rightclass from org_right oright left join org_role_right orr on oright.right_id=orr.right_id where orr.role_id in(" + roleId + ") order by oright.rightclass");
      while (rs.next()) {
        SimpleRightVO svo = new SimpleRightVO();
        svo.setRightId(Long.valueOf(rs.getLong(1)));
        svo.setRightName(rs.getString(2));
        svo.setRightHasScope(rs.getInt(3));
        svo.setRightSelectRange(rs.getString(4));
        svo.setRightType(rs.getString(5));
        rightClass = rs.getString(6);
        svo.setRightClass(rightClass);
        rightList.add(svo);
        if (!oldRightClass.equals(rightClass)) {
          oldRightClass = rightClass;
          classList.add(rightClass);
        } 
      } 
      map.put("rightClass", classList);
      map.put("rightList", rightList);
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        conn.close(); 
      throw e;
    } 
    return map;
  }
  
  public List getUserRole(String userId) throws Exception {
    begin();
    List list = null;
    try {
      Query query = this.session.createQuery("select aaa.roleId from com.js.system.vo.rolemanager.RoleVO aaa join aaa.employees bbb where bbb.empId = " + userId);
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
  
  public void addHandRole(HandRoleVO handRoleVO) throws Exception {
    begin();
    try {
      long roleRecieveId = handRoleVO.getRoleRecieveId();
      Set<Object> employeeSet = new HashSet();
      EmployeeVO employeeVO = (EmployeeVO)this.session.load(EmployeeVO.class, new Long(roleRecieveId));
      employeeSet.add(this.session.load(EmployeeVO.class, new Long(roleRecieveId)));
      long roleDeliverId = handRoleVO.getRoleDeliverId();
      Query query = this.session.createQuery("select aaa from com.js.system.vo.rolemanager.RoleVO aaa join aaa.employees bbb where bbb.empId = " + roleDeliverId);
      List<RoleVO> roleDeliverRoleList = query.list();
      RoleVO roleVO = null;
      for (int i = 0; i < roleDeliverRoleList.size(); i++) {
        roleVO = roleDeliverRoleList.get(i);
        roleVO.setEmployees(employeeSet);
      } 
      query = this.session.createQuery("select aaa from com.js.system.vo.rolemanager.RightScopeVO aaa join aaa.employee bbb where bbb.empId = " + roleDeliverId);
      List<RightScopeVO> roleDeliverRightScopeList = query.list();
      RightScopeVO rightScopeVO = null;
      for (int j = 0; j < roleDeliverRightScopeList.size(); j++) {
        rightScopeVO = roleDeliverRightScopeList.get(j);
        rightScopeVO.setEmployee(employeeVO);
      } 
      this.session.save(handRoleVO);
      this.session.flush();
    } catch (Exception e) {
      this.session.close();
      throw e;
    } finally {
      this.session = null;
      this.transaction = null;
    } 
  }
  
  private boolean deleteRightScope(String roleIds) {
    boolean result = false;
    StringBuffer buffer = new StringBuffer();
    try {
      List list = this.session.createQuery("select rightVO.rightId from com.js.system.vo.rolemanager.RoleVO roleVO join roleVO.rights rightVO where roleVO.roleId in(" + 
          roleIds + ")").list();
      if (list != null && list.size() > 0)
        for (int i = 0; i < list.size(); i++)
          buffer.append(list.get(i)).append(",");  
      String shouldDel = buffer.append("-1").toString();
      buffer = new StringBuffer();
      list = this.session.createQuery("select rightVO.rightId from com.js.system.vo.rolemanager.RoleVO roleVO join roleVO.rights rightVO where roleVO.roleId not in (" + 
          roleIds + ") and rightVO.rightId in (" + 
          shouldDel + ")").list();
      if (list != null && list.size() > 0)
        for (int i = 0; i < list.size(); i++)
          buffer.append(list.get(i)).append(",");  
      String shouldnotDel = buffer.append("-1").toString();
      this.session.delete("from com.js.system.vo.rolemanager.RightScopeVO sc join sc.right r where r.rightId in (" + 
          shouldDel + ") and r.rightId not in (" + 
          shouldnotDel + ")");
      result = true;
      this.session.flush();
    } catch (Exception e) {
      result = false;
      System.out.println(e.getMessage());
    } 
    return result;
  }
  
  public List getOwnerUserRoles(String userId, String orgId, String browseRange, String managerType, String modiUserId, String domainId) throws Exception {
    List list = null;
    try {
      begin();
      String sql = "";
      String whereSql = "";
      int type = Integer.parseInt(managerType);
      if (type != 1) {
        Iterator<Object[]> iter = this.session.iterate("select aaa.rightScopeType,aaa.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO aaa join aaa.employee bbb join aaa.right ccc where bbb.empId = " + userId + " and ccc.rightCode = '00*01*02'");
        Object[] obj = iter.next();
        String scopeType = obj[0].toString();
        String scopScope = (String)obj[1];
        if (!"0".equals(scopeType)) {
          if ("1".equals(scopeType)) {
            whereSql = " WHERE (role.createdEmp=" + userId;
          } else if ("2".equals(scopeType)) {
            whereSql = " WHERE (role.createdOrg=" + orgId + 
              " OR role.createdEmp=" + userId;
          } else if ("3".equals(scopeType)) {
            String orgRange = getAllJuniorOrgIdByRange(
                "*" + orgId + "*");
            whereSql = " WHERE ((role.createdOrg in(" + orgRange + 
              ") AND role.roleId>=2) OR role.createdEmp=" + 
              userId;
          } else if ("4".equals(scopeType)) {
            String orgRange = getAllJuniorOrgIdByRange(
                scopScope);
            whereSql = " WHERE ((role.createdOrg in(" + orgRange + 
              ") AND role.roleId>=2) OR role.createdEmp=" + 
              userId;
          } 
          whereSql = String.valueOf(whereSql) + " or role.roleId=2";
        } 
      } 
      if ("".equals(whereSql)) {
        whereSql = " Where (1=1 or emp.empId=" + modiUserId + ") and role.domainId=" + domainId;
      } else {
        whereSql = String.valueOf(whereSql) + " OR emp.empId=" + modiUserId + ") and role.domainId=" + domainId;
      } 
      sql = "SELECT distinct role.roleId,role.roleName,role.roleDescription FROM com.js.system.vo.rolemanager.RoleVO role left join role.employees emp " + whereSql + " ORDER BY role.roleName";
      list = this.session.createQuery(sql).list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List getUserRoleHistory(String userId) throws Exception {
    begin();
    List list = null;
    try {
      Query query = this.session.createQuery("select distinct aaa.roleId,aaa.roleName,aaa.roleDescription from com.js.system.vo.rolemanager.RoleVO aaa join aaa.employees bbb where bbb.empId = " + userId);
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
  
  public Integer newUpdate(String modifyId, String[] rightId, String empIds, RoleVO roleVO) throws Exception {
    String roleUserId = roleVO.getRoleUserId();
    String roleUserName = roleVO.getRoleUserName();
    String roleName = roleVO.getRoleName();
    String roleDescription = roleVO.getRoleDescription();
    String roleUseRange = roleVO.getRoleUseRange();
    String roleUseName = roleVO.getRoleUseName();
    String roleRangeType = roleVO.getRoleRangeType();
    String roleRange = roleVO.getRoleRange();
    String roleRangeName = roleVO.getRoleRangeName();
    begin();
    int result = 2;
    try {
      Iterator<E> iter = this.session.iterate("select a.domainId from com.js.system.vo.rolemanager.RoleVO a where a.roleId=" + modifyId);
      String domainId = "0";
      if (iter.hasNext())
        domainId = iter.next().toString(); 
      result = ((Integer)this.session.iterate("select count(*) from com.js.system.vo.rolemanager.RoleVO vo where vo.roleName='" + roleName.trim() + "' and vo.domainId=" + domainId + " and vo.roleId<>" + modifyId).next()).intValue();
      if (result > 0) {
        result = 1;
        return new Integer(1);
      } 
      if ("".equals(empIds))
        empIds = "-1"; 
      StringBuffer buffer = new StringBuffer();
      String user = "";
      iter = this.session.iterate("select emp.empId from com.js.system.vo.rolemanager.RoleVO roleVO join roleVO.employees emp where roleVO.roleId=" + modifyId);
      while (iter.hasNext())
        buffer.append(iter.next()).append(","); 
      user = buffer.append("-1").toString();
      buffer.delete(0, buffer.length());
      String levelUser = "-1";
      List<String> levelUserList = null;
      if (!empIds.equals("-1")) {
        levelUserList = this.session.createQuery("select empVO.empId from com.js.system.vo.rolemanager.RoleVO roleVO join roleVO.employees empVO where roleVO.roleId=" + modifyId + " and empVO.empId in (" + user + ") and empVO.empId in (" + empIds + ")").list();
        for (int j = 0; j < levelUserList.size(); j++)
          buffer.append(levelUserList.get(j)).append(","); 
        levelUser = buffer.append("-1").toString();
        buffer.delete(0, buffer.length());
      } 
      String cutUser = user;
      if (!empIds.equals("-1")) {
        iter = this.session.iterate("select empVO.empId from com.js.system.vo.usermanager.EmployeeVO empVO where empVO.empId in (" + user + ") and empVO.empId not in (" + empIds + ")");
        while (iter.hasNext())
          buffer.append(iter.next()).append(","); 
        cutUser = buffer.append("-1").toString();
        buffer.delete(0, buffer.length());
      } 
      List<String> addUserList = null;
      if (!empIds.equals("-1"))
        addUserList = this.session.createQuery("select empVO.empId from com.js.system.vo.usermanager.EmployeeVO empVO where empVO.empId in (" + empIds + ")").list(); 
      String oldRight = "";
      iter = this.session.iterate("select rightVO.rightId from com.js.system.vo.rolemanager.RoleVO roleVO join roleVO.rights rightVO where roleVO.roleId=" + modifyId);
      while (iter.hasNext())
        buffer.append(iter.next()).append(","); 
      oldRight = buffer.append("-1").toString();
      buffer.delete(0, buffer.length());
      String nowRight = "";
      for (int i = 0; i < rightId.length; i++) {
        if (!rightId[i].equals(""))
          buffer.append(rightId[i]).append(","); 
      } 
      buffer.append("-1");
      nowRight = buffer.toString();
      buffer.delete(0, buffer.length());
      String cutRight = oldRight;
      if (!nowRight.equals("-1")) {
        iter = this.session.iterate("select rightVO.rightId from com.js.system.vo.rightmanager.RightVO rightVO where rightVO.rightId in(" + oldRight + ") and rightVO.rightId not in (" + nowRight + ")");
        while (iter.hasNext())
          buffer.append(iter.next()).append(","); 
        cutRight = buffer.append("-1").toString();
        buffer.delete(0, buffer.length());
      } 
      List<String> addRightList = null;
      if (!nowRight.equals("-1"))
        addRightList = this.session.createQuery("select rightVO.rightId from com.js.system.vo.rightmanager.RightVO rightVO where rightVO.rightId not in (" + oldRight + ") and rightVO.rightId in (" + nowRight + ")").list(); 
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = null;
      if (!cutUser.equals("-1")) {
        String[] tmp = (cutUser.indexOf(",") > 0) ? cutUser.split(",") : null;
        if (tmp != null)
          for (int j = 0; j < tmp.length; j++)
            stmt.execute("DELETE FROM JSDB.ORG_RIGHTSCOPE WHERE EMP_ID IN (" + tmp[j] + ") AND EMP_ID<>0 AND RIGHT_ID IN (" + oldRight + ") AND RIGHT_ID NOT IN (SELECT A.RIGHT_ID FROM JSDB.ORG_ROLE_RIGHT A, JSDB.ORG_USER_ROLE B WHERE A.ROLE_ID = B.ROLE_ID AND B.EMP_ID IN (" + tmp[j] + ") AND A.ROLE_ID <>" + modifyId + ")");  
      } 
      stmt.execute("DELETE FROM JSDB.ORG_USER_ROLE WHERE ROLE_ID=" + modifyId + "  AND EMP_ID<>0 AND EMP_ID IN (" + cutUser + ")");
      if (!levelUser.equals("-1") && !cutRight.equals("-1")) {
        String[] tmp = (levelUser.indexOf(",") > 0) ? levelUser.split(",") : null;
        if (tmp != null)
          for (int j = 0; j < tmp.length; j++)
            stmt.execute("DELETE FROM JSDB.ORG_RIGHTSCOPE WHERE EMP_ID IN (" + tmp[j] + ")  AND EMP_ID<>0 AND RIGHT_ID IN (" + cutRight + ") AND RIGHT_ID NOT IN (SELECT A.RIGHT_ID FROM JSDB.ORG_ROLE_RIGHT A, JSDB.ORG_USER_ROLE B WHERE A.ROLE_ID = B.ROLE_ID AND B.EMP_ID IN (" + tmp[j] + ") AND A.RIGHT_ID IN (" + cutRight + ") AND A.ROLE_ID <>" + modifyId + ")");  
      } 
      if (levelUserList != null)
        for (int j = 0; j < levelUserList.size() && 
          addRightList != null; j++) {
          for (int k = 0; k < addRightList.size(); k++) {
            rs = stmt.executeQuery("SELECT RIGHTSCOPE_ID FROM JSDB.ORG_RIGHTSCOPE WHERE EMP_ID=" + levelUserList.get(j) + " AND RIGHT_ID=" + addRightList.get(k));
            if (rs.next()) {
              String rightScopeId = rs.getString(1);
              rs.close();
              stmt.executeUpdate("UPDATE JSDB.ORG_RIGHTSCOPE SET RIGHTSCOPETYPE=" + roleRangeType + ",RIGHTSCOPESCOPE='" + roleRange + "',RIGHTSCOPE='" + roleRangeName + "',RIGHTSCOPEUSER='',RIGHTSCOPEGROUP='' WHERE RIGHTSCOPE_ID=" + rightScopeId);
            } else {
              rs.close();
              stmt.execute("INSERT INTO JSDB.ORG_RIGHTSCOPE (RIGHTSCOPE_ID, EMP_ID, RIGHT_ID, RIGHTSCOPETYPE, RIGHTSCOPESCOPE, RIGHTSCOPEUSER, RIGHTSCOPEGROUP, RIGHTSCOPE, DOMAIN_ID) VALUES (" + getTableId() + ", " + levelUserList.get(j) + ", " + addRightList.get(k) + ", " + roleRangeType + ", '" + roleRange + "', '', '', '" + roleRangeName + "', " + domainId + ")");
            } 
          } 
        }  
      if (addUserList != null)
        for (int j = 0; j < addUserList.size(); j++) {
          if (rightId.length == 0)
            break; 
          for (int k = 0; k < rightId.length; k++) {
            if (!"".equals(rightId[k])) {
              rs = stmt.executeQuery("SELECT RIGHTSCOPE_ID FROM JSDB.ORG_RIGHTSCOPE WHERE EMP_ID=" + addUserList.get(j) + " AND RIGHT_ID=" + rightId[k]);
              if (rs.next()) {
                String rightScopeId = rs.getString(1);
                rs.close();
                stmt.executeUpdate("UPDATE JSDB.ORG_RIGHTSCOPE SET RIGHTSCOPETYPE=" + roleRangeType + ",RIGHTSCOPESCOPE='" + roleRange + "',RIGHTSCOPE='" + roleRangeName + "',RIGHTSCOPEUSER='',RIGHTSCOPEGROUP='' WHERE RIGHTSCOPE_ID=" + rightScopeId);
              } else {
                rs.close();
                stmt.execute("INSERT INTO JSDB.ORG_RIGHTSCOPE (RIGHTSCOPE_ID, EMP_ID, RIGHT_ID, RIGHTSCOPETYPE, RIGHTSCOPESCOPE, RIGHTSCOPEUSER, RIGHTSCOPEGROUP, RIGHTSCOPE, DOMAIN_ID) VALUES (" + getTableId() + ", " + addUserList.get(j) + ", " + rightId[k] + ",  " + roleRangeType + ", '" + roleRange + "', '', '', '" + roleRangeName + "', " + domainId + ")");
              } 
            } 
          } 
          rs = stmt.executeQuery("select * from JSDB.ORG_USER_ROLE where ROLE_ID=" + modifyId + " and EMP_ID=" + addUserList.get(j));
          if (rs.next()) {
            rs.close();
          } else {
            rs.close();
            stmt.execute("INSERT INTO JSDB.ORG_USER_ROLE (ROLE_ID, EMP_ID) VALUES (" + modifyId + ", " + addUserList.get(j) + ")");
          } 
        }  
      if (!cutRight.equals("-1"))
        stmt.execute("DELETE FROM JSDB.ORG_ROLE_RIGHT WHERE ROLE_ID=" + modifyId + " AND RIGHT_ID IN (" + cutRight + ")"); 
      if (addRightList != null)
        for (int j = 0; j < addRightList.size(); j++)
          stmt.execute("INSERT INTO JSDB.ORG_ROLE_RIGHT (ROLE_ID, RIGHT_ID) VALUES (" + modifyId + ", " + addRightList.get(j) + ")");  
      stmt.execute("UPDATE JSDB.ORG_ROLE SET ROLENAME='" + roleName + "', ROLEDESCRIPTION='" + roleDescription + "', ROLEUSERID='" + roleUserId + "', ROLEUSERNAME='" + roleUserName + "',ROLEUSERANGE='" + roleUseRange + "',ROLEUSENAME='" + roleUseName + "',ROLERANGETYPE='" + roleRangeType + "',ROLERANGE='" + roleRange + "',ROLERANGENAME='" + roleRangeName + "' WHERE ROLE_ID=" + modifyId);
      this.session.flush();
      stmt.close();
    } catch (Exception e) {
      System.out.println("-----------------------------------------------");
      e.printStackTrace();
      System.out.println("-----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
    this.session.close();
    return Integer.valueOf((new StringBuilder(String.valueOf(result))).toString());
  }
  
  public Integer newUpdate(String modifyId, String[] rightId, String empIds, RoleVO roleVO, String[] log) throws Exception {
    String roleUserId = roleVO.getRoleUserId();
    String roleUserName = roleVO.getRoleUserName();
    String roleName = roleVO.getRoleName();
    String roleDescription = roleVO.getRoleDescription();
    String roleUseRange = roleVO.getRoleUseRange();
    String roleUseName = roleVO.getRoleUseName();
    String roleRangeType = roleVO.getRoleRangeType();
    String roleRange = roleVO.getRoleRange();
    String roleRangeName = roleVO.getRoleRangeName();
    begin();
    int result = 2;
    try {
      Iterator<E> iter = this.session.iterate("select a.domainId from com.js.system.vo.rolemanager.RoleVO a where a.roleId=" + modifyId);
      String domainId = "0";
      if (iter.hasNext())
        domainId = iter.next().toString(); 
      result = ((Integer)this.session.iterate("select count(*) from com.js.system.vo.rolemanager.RoleVO vo where vo.roleName='" + 
          roleName.trim() + "' and vo.domainId=" + domainId + " and vo.roleId<>" + modifyId).next()).intValue();
      if (result > 0) {
        result = 1;
        return new Integer(1);
      } 
      if ("".equals(empIds))
        empIds = "-1"; 
      RoleVO vo = (RoleVO)this.session.load(RoleVO.class, Long.valueOf(modifyId));
      Long logId = Long.valueOf(saveLog(log, "修改角色管理“" + vo.getRoleName() + "”"));
      RolePO rolePO = (RolePO)FillBean.transformOTO(vo, RolePO.class);
      rolePO.setRoleName(roleName);
      rolePO.setRoleDescription(roleDescription);
      rolePO.setRoleUserId(roleUserId);
      rolePO.setRoleUserName(roleUserName);
      rolePO.setRoleUseRange(roleUseRange);
      rolePO.setRoleUseName(roleUseName);
      rolePO.setRoleRangeType(roleRangeType);
      rolePO.setRoleRange(roleRange);
      rolePO.setRoleRangeName(roleRangeName);
      rolePO.setRoleId(Long.valueOf(modifyId).longValue());
      rolePO.setOperate("update");
      rolePO.setLogId(logId.longValue());
      this.session.save(rolePO);
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = null;
      for (int i = 0; i < rightId.length; i++) {
        if (!rightId[i].equals(""))
          stmt.executeUpdate("INSERT INTO JSDB.audit_ROLE_RIGHT (ROLE_ID, RIGHT_ID,log_Id) VALUES (" + modifyId + ", " + rightId[i] + "," + logId + ")"); 
      } 
      String[] empId = empIds.split(",");
      int j;
      for (j = 0; j < empId.length; j++) {
        if (!empId[j].equals(""))
          stmt.executeUpdate("INSERT INTO JSDB.audit_USER_ROLE (ROLE_ID, EMP_ID,log_id) VALUES (0, " + empId[j] + "," + logId + ")"); 
      } 
      if (roleRangeType == null || "null".equals(roleRangeType)) {
        roleRangeType = "2";
        roleRange = "";
        roleRangeName = "";
      } else if (roleRange == null || "null".equals(roleRange) || "".equals(roleRange)) {
        roleRange = "";
        roleRangeName = "";
      } 
      for (j = 0; j < empId.length; j++) {
        if (!empId[j].equals("")) {
          if (rightId.length == 0)
            break; 
          for (int k = 0; k < rightId.length; k++) {
            if (!rightId[k].equals(""))
              stmt.executeUpdate("INSERT INTO JSDB.audit_RIGHTSCOPE (log_id,RIGHTSCOPE_ID, EMP_ID, RIGHT_ID, RIGHTSCOPETYPE,RIGHTSCOPESCOPE,RIGHTSCOPE,RIGHTSCOPEUSER,RIGHTSCOPEGROUP,DOMAIN_ID) VALUES (" + 
                  logId + ",0, " + empId[j] + ", " + rightId[k] + "," + roleRangeType + 
                  ",'" + roleRange + "','" + roleRangeName + "','','', " + roleVO.getDomainId() + ")"); 
          } 
        } 
      } 
      this.session.flush();
      stmt.close();
    } catch (Exception e) {
      System.out.println("-----------------------------------------------");
      e.printStackTrace();
      System.out.println("-----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
    this.session.close();
    return Integer.valueOf((new StringBuilder(String.valueOf(result))).toString());
  }
  
  public Integer save(RoleVO roleVO, String[] ids) throws Exception {
    int result = 0;
    ConvertIdAndName cIdAndName = new ConvertIdAndName();
    EndowVO endowVO = cIdAndName.splitId(roleVO.getRoleUserId());
    String[] empIds = endowVO.getEmpIdArray().split(",");
    begin();
    try {
      String name = roleVO.getRoleName();
      result = ((Integer)this.session.iterate("select count(*) from com.js.system.vo.rolemanager.RoleVO vo where vo.roleName='" + name.trim() + "' and vo.domainId=" + roleVO.getDomainId()).next()).intValue();
      if (result > 0)
        return new Integer(1); 
      Long roleId = (Long)this.session.save(roleVO);
      this.session.flush();
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = null;
      String roleRangeType = roleVO.getRoleRangeType();
      String roleRange = roleVO.getRoleRange();
      String roleRangeName = roleVO.getRoleRangeName();
      if (roleRangeType == null || "null".equals(roleRangeType)) {
        roleRangeType = "2";
        roleRange = "";
        roleRangeName = "";
      } else if (roleRange == null || "null".equals(roleRange) || "".equals(roleRange)) {
        roleRange = "";
        roleRangeName = "";
      } 
      int i;
      for (i = 0; i < ids.length; i++) {
        if (!ids[i].equals(""))
          stmt.executeUpdate("INSERT INTO JSDB.ORG_ROLE_RIGHT (ROLE_ID, RIGHT_ID) VALUES (" + roleId + ", " + ids[i] + ")"); 
      } 
      for (i = 0; i < empIds.length; i++) {
        if (!empIds[i].equals(""))
          stmt.executeUpdate("INSERT INTO JSDB.ORG_USER_ROLE (ROLE_ID, EMP_ID) VALUES (" + roleId + ", " + empIds[i] + ")"); 
      } 
      for (int j = 0; j < empIds.length; j++) {
        if (!empIds[j].equals("")) {
          if (ids.length == 0)
            break; 
          for (int k = 0; k < ids.length; k++) {
            if (!ids[k].equals("")) {
              rs = stmt.executeQuery("SELECT RIGHTSCOPE_ID FROM JSDB.ORG_RIGHTSCOPE WHERE RIGHT_ID=" + ids[k] + " AND EMP_ID=" + empIds[j]);
              if (rs.next()) {
                String rightScopeId = rs.getString(1);
                rs.close();
                stmt.executeUpdate("UPDATE JSDB.ORG_RIGHTSCOPE SET RIGHTSCOPETYPE=" + roleRangeType + ",RIGHTSCOPESCOPE='" + roleRange + "',RIGHTSCOPE='" + roleRangeName + "',RIGHTSCOPEUSER='',RIGHTSCOPEGROUP='' WHERE RIGHTSCOPE_ID=" + rightScopeId);
              } else {
                rs.close();
                stmt.executeUpdate("INSERT INTO JSDB.ORG_RIGHTSCOPE (RIGHTSCOPE_ID, EMP_ID, RIGHT_ID, RIGHTSCOPETYPE,RIGHTSCOPESCOPE,RIGHTSCOPE,RIGHTSCOPEUSER,RIGHTSCOPEGROUP,DOMAIN_ID) VALUES (" + getTableId() + ", " + empIds[j] + ", " + ids[k] + "," + roleRangeType + ",'" + roleRange + "','" + roleRangeName + "','','', " + roleVO.getDomainId() + ")");
              } 
            } 
          } 
        } 
      } 
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      result = 2;
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    this.session.close();
    this.session = null;
    this.transaction = null;
    return new Integer(result);
  }
  
  public Integer save(RoleVO roleVO, String[] ids, String[] log) throws Exception {
    int result = 0;
    ConvertIdAndName cIdAndName = new ConvertIdAndName();
    EndowVO endowVO = cIdAndName.splitId(roleVO.getRoleUserId());
    String[] empIds = endowVO.getEmpIdArray().split(",");
    begin();
    try {
      String name = roleVO.getRoleName();
      Long logId = Long.valueOf(saveLog(log, "新增角色管理“" + name + "”"));
      result = ((Integer)this.session.iterate("select count(*) from com.js.system.vo.rolemanager.RoleVO vo where vo.roleName='" + name.trim() + "' and vo.domainId=" + roleVO.getDomainId()).next()).intValue();
      if (result > 0)
        return new Integer(1); 
      RolePO rolePO = (RolePO)FillBean.transformOTO(roleVO, RolePO.class);
      rolePO.setRoleId(0L);
      rolePO.setOperate("add");
      rolePO.setLogId(logId.longValue());
      this.session.save(rolePO);
      this.session.flush();
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = null;
      String roleRangeType = roleVO.getRoleRangeType();
      String roleRange = roleVO.getRoleRange();
      String roleRangeName = roleVO.getRoleRangeName();
      if (roleRangeType == null || "null".equals(roleRangeType)) {
        roleRangeType = "2";
        roleRange = "";
        roleRangeName = "";
      } else if (roleRange == null || "null".equals(roleRange) || "".equals(roleRange)) {
        roleRange = "";
        roleRangeName = "";
      } 
      int i;
      for (i = 0; i < ids.length; i++) {
        if (!ids[i].equals(""))
          stmt.executeUpdate("INSERT INTO JSDB.audit_ROLE_RIGHT (ROLE_ID, RIGHT_ID,log_Id) VALUES (0, " + ids[i] + "," + logId + ")"); 
      } 
      for (i = 0; i < empIds.length; i++) {
        if (!empIds[i].equals(""))
          stmt.executeUpdate("INSERT INTO JSDB.audit_USER_ROLE (ROLE_ID, EMP_ID,log_id) VALUES (0, " + empIds[i] + "," + logId + ")"); 
      } 
      for (int j = 0; j < empIds.length; j++) {
        if (!empIds[j].equals("")) {
          if (ids.length == 0)
            break; 
          for (int k = 0; k < ids.length; k++) {
            if (!ids[k].equals(""))
              stmt.executeUpdate("INSERT INTO JSDB.audit_RIGHTSCOPE (log_id,RIGHTSCOPE_ID, EMP_ID, RIGHT_ID, RIGHTSCOPETYPE,RIGHTSCOPESCOPE,RIGHTSCOPE,RIGHTSCOPEUSER,RIGHTSCOPEGROUP,DOMAIN_ID) VALUES (" + 
                  logId + ",0, " + empIds[j] + ", " + ids[k] + "," + roleRangeType + 
                  ",'" + roleRange + "','" + roleRangeName + "','','', " + roleVO.getDomainId() + ")"); 
          } 
        } 
      } 
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      result = 2;
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    this.session.close();
    this.session = null;
    this.transaction = null;
    return new Integer(result);
  }
  
  public String newSingleDelete(String id) throws Exception {
    begin();
    String result = "";
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        ResultSet resultSet = stmt.executeQuery("SELECT RIGHTSCOPE_ID FROM JSDB.ORG_RIGHTSCOPE WHERE EMP_ID IN (SELECT EMP_ID FROM JSDB.ORG_USER_ROLE WHERE ROLE_ID=" + id + ") AND RIGHT_ID IN (SELECT RIGHT_ID FROM JSDB.ORG_ROLE_RIGHT WHERE ROLE_ID=" + id + ") AND RIGHT_ID NOT IN (SELECT RIGHT_ID FROM JSDB.ORG_RIGHTSCOPE WHERE EMP_ID IN (SELECT EMP_ID FROM JSDB.ORG_USER_ROLE WHERE ROLE_ID=" + id + ") AND RIGHT_ID IN (SELECT RIGHT_ID FROM JSDB.ORG_ROLE_RIGHT A, JSDB.ORG_USER_ROLE B WHERE A.ROLE_ID=B.ROLE_ID AND B.EMP_ID IN (SELECT EMP_ID FROM ORG_USER_ROLE WHERE ROLE_ID=" + id + ") AND A.ROLE_ID <> " + id + "))");
        StringBuffer sb = new StringBuffer();
        while (resultSet.next())
          sb.append(String.valueOf(resultSet.getString(1)) + ","); 
        resultSet.close();
        sb.append("-1");
        stmt.execute("DELETE FROM JSDB.ORG_RIGHTSCOPE WHERE RIGHTSCOPE_ID IN (" + sb.toString() + ")");
      } else {
        stmt.execute("DELETE FROM JSDB.ORG_RIGHTSCOPE WHERE EMP_ID IN (SELECT EMP_ID FROM JSDB.ORG_USER_ROLE WHERE ROLE_ID=" + id + ") AND RIGHT_ID IN (SELECT RIGHT_ID FROM JSDB.ORG_ROLE_RIGHT WHERE ROLE_ID=" + id + ") AND RIGHT_ID NOT IN (SELECT RIGHT_ID FROM JSDB.ORG_RIGHTSCOPE WHERE EMP_ID IN (SELECT EMP_ID FROM JSDB.ORG_USER_ROLE WHERE ROLE_ID=" + id + ") AND RIGHT_ID IN (SELECT RIGHT_ID FROM JSDB.ORG_ROLE_RIGHT A, JSDB.ORG_USER_ROLE B WHERE A.ROLE_ID=B.ROLE_ID AND B.EMP_ID IN (SELECT EMP_ID FROM ORG_USER_ROLE WHERE ROLE_ID=" + id + ") AND A.ROLE_ID <> " + id + "))");
      } 
      stmt.execute("DELETE FROM JSDB.ORG_ROLE_RIGHT WHERE ROLE_ID=" + id);
      stmt.execute("DELETE FROM JSDB.ORG_USER_ROLE WHERE ROLE_ID=" + id);
      ResultSet rs = stmt.executeQuery("select rolename from JSDB.org_role where role_id=" + id);
      if (rs.next())
        result = rs.getString(1); 
      rs.close();
      stmt.execute("DELETE FROM JSDB.ORG_ROLE WHERE ROLE_ID=" + id);
      stmt.close();
      conn.close();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public String newSingleDelete(String id, String[] log) throws Exception {
    begin();
    String result = "";
    try {
      RoleVO vo = (RoleVO)this.session.load(RoleVO.class, Long.valueOf(id));
      Long logId = Long.valueOf(saveLog(log, "删除角色管理“" + vo.getRoleName() + "”"));
      RolePO po = (RolePO)FillBean.transformOTO(vo, RolePO.class);
      po.setLogId(logId.longValue());
      po.setOperate("delete");
      this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public String newBatchDelete(String[] batchDeleteId) throws Exception {
    String id = "";
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < batchDeleteId.length; i++)
      sb.append(batchDeleteId[i]).append(","); 
    id = sb.toString();
    id = id.substring(0, id.length() - 1);
    String result = "";
    begin();
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        ResultSet resultSet = stmt.executeQuery("SELECT RIGHTSCOPE_ID FROM JSDB.ORG_RIGHTSCOPE WHERE EMP_ID IN (SELECT EMP_ID FROM JSDB.ORG_USER_ROLE WHERE ROLE_ID IN (" + id + ")) AND RIGHT_ID IN (SELECT RIGHT_ID FROM JSDB.ORG_ROLE_RIGHT WHERE ROLE_ID IN (" + id + ")) AND RIGHT_ID NOT IN (SELECT RIGHT_ID FROM JSDB.ORG_RIGHTSCOPE WHERE EMP_ID IN (SELECT EMP_ID FROM JSDB.ORG_USER_ROLE WHERE ROLE_ID IN (" + id + ")) AND RIGHT_ID IN (SELECT RIGHT_ID FROM JSDB.ORG_ROLE_RIGHT WHERE ROLE_ID NOT IN (" + id + ")))");
        StringBuffer sb2 = new StringBuffer();
        while (resultSet.next())
          sb2.append(String.valueOf(resultSet.getString(1)) + ","); 
        resultSet.close();
        sb2.append("0");
        stmt.execute("DELETE FROM JSDB.ORG_RIGHTSCOPE WHERE RIGHTSCOPE_ID IN (" + sb2.toString() + ")");
      } else {
        stmt.execute("DELETE FROM JSDB.ORG_RIGHTSCOPE WHERE EMP_ID IN (SELECT EMP_ID FROM JSDB.ORG_USER_ROLE WHERE ROLE_ID IN (" + id + ")) AND RIGHT_ID IN (SELECT RIGHT_ID FROM JSDB.ORG_ROLE_RIGHT WHERE ROLE_ID IN (" + id + ")) AND RIGHT_ID NOT IN (SELECT RIGHT_ID FROM JSDB.ORG_RIGHTSCOPE WHERE EMP_ID IN (SELECT EMP_ID FROM JSDB.ORG_USER_ROLE WHERE ROLE_ID IN (" + id + ")) AND RIGHT_ID IN (SELECT RIGHT_ID FROM JSDB.ORG_ROLE_RIGHT WHERE ROLE_ID NOT IN (" + id + ")))");
      } 
      stmt.execute("DELETE FROM JSDB.ORG_ROLE_RIGHT WHERE ROLE_ID IN (" + id + ")");
      stmt.execute("DELETE FROM JSDB.ORG_USER_ROLE WHERE ROLE_ID IN (" + id + ")");
      int j = 0;
      ResultSet rs = stmt.executeQuery("select rolename from JSDB.ORG_ROLE WHERE ROLE_ID IN (" + id + ")");
      while (rs.next()) {
        if (j == 0) {
          result = rs.getString(1);
        } else {
          result = String.valueOf(result) + "," + rs.getString(1);
        } 
        j++;
      } 
      rs.close();
      stmt.execute("DELETE FROM JSDB.ORG_ROLE WHERE ROLE_ID IN (" + id + ")");
      stmt.close();
      conn.close();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public String newBatchDelete(String[] batchDeleteId, String[] log) throws Exception {
    String result = "";
    try {
      for (int i = 0; i < batchDeleteId.length; i++) {
        if (!"".equals(batchDeleteId[i]))
          newSingleDelete(batchDeleteId[i], log); 
      } 
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } 
    return result;
  }
  
  private String getAllJuniorOrgIdByRange(String range) throws Exception {
    StringBuffer result = new StringBuffer();
    if (range != null && range.indexOf("*") >= 0) {
      StringBuffer where = new StringBuffer(" WHERE ");
      range = range.substring(1, range.length() - 1);
      String[] rangeArray = { "" };
      if (range.indexOf("*") >= 0) {
        rangeArray = range.split("\\*\\*");
      } else {
        rangeArray[0] = range;
      } 
      for (int i = 0; i < rangeArray.length; i++)
        where.append(" org.orgIdString like '%$").append(rangeArray[i]).append("$%' or "); 
      where.append(" 1<>1 ");
      Iterator iter = this.session.iterate("SELECT org.orgId FROM com.js.system.vo.organizationmanager.OrganizationVO org " + where.toString());
      while (iter.hasNext())
        result.append((new StringBuilder()).append(iter.next()).append(",").toString()); 
      result.append("-1");
    } else {
      result.append("-1");
    } 
    return result.toString();
  }
  
  public List searchIdByName(String roleName) throws Exception {
    List<String> reList = new ArrayList();
    reList.add(roleName);
    begin();
    try {
      List<String> list = this.session.createQuery("select role.roleId from com.js.system.vo.rolemanager.RoleVO role where role.roleName like '" + roleName + "'").list();
      if (!list.isEmpty())
        reList.add(list.get(0)); 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return reList;
  }
  
  public Long searchEmpPositionIdByName(String name) throws Exception {
    Long id = null;
    try {
      List<E> list = getListByYourSQL("select aaa.id from com.js.oa.hr.personnelmanager.po.StationPO aaa where aaa.name ='" + name + "'");
      if (!list.isEmpty())
        id = Long.valueOf(list.get(0).toString()); 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return id;
  }
  
  public List getListByYourSQL(String sql) throws Exception {
    begin();
    List list = new ArrayList();
    try {
      Query query1 = this.session.createQuery(sql);
      list = query1.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List getAuditUserRole(String logId) {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    List<String> list = new ArrayList();
    try {
      base.begin();
      String sql = "select role_id from audit_user_role where log_id=" + logId;
      rs = base.executeQuery(sql);
      while (rs.next())
        list.add(rs.getString(1)); 
      base.end();
    } catch (Exception e) {
      e.printStackTrace();
      base.end();
    } 
    return list;
  }
  
  public void auditRole(String logId, String flag, String checkEmpid, String checkEmpName) throws Exception {
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
  
  public RolePO getRolePO(String logId) throws Exception {
    begin();
    RolePO po = new RolePO();
    try {
      List<RolePO> list = this.session.createQuery("select po from com.js.oa.audit.po.RolePO po where po.logId=" + logId).list();
      if (list.size() > 0)
        po = list.get(0); 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return po;
  }
  
  public String getRightId(String logId) throws Exception {
    String rightId = "";
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      base.begin();
      rs = base.executeQuery("select distinct right_id from audit_role_right where log_id=" + logId);
      while (rs.next())
        rightId = String.valueOf(rightId) + "," + rs.getString(1); 
      if (rightId.length() > 0)
        rightId = rightId.substring(1); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return rightId;
  }
  
  public String getEmpId(String logId) throws Exception {
    String empId = "";
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      base.begin();
      rs = base.executeQuery("select distinct emp_id from audit_user_role where log_id=" + logId);
      while (rs.next())
        empId = String.valueOf(empId) + "," + rs.getString(1); 
      if (empId.length() > 0)
        empId = empId.substring(1); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return empId;
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
      } 
      AuditMsRemindEJBBean msRemindBeann = new AuditMsRemindEJBBean();
      msRemindBeann.auditMsRemind(Long.valueOf(log[0]).longValue(), log[2], log[1], 
          1, 1, new Date(), "审计提醒:" + log[1] + caozuo, "audit", logId.longValue(), "/jsoa/AuditRoleAction.do?action=modify&sysRole=0&logId=" + logId + "&disabled=disabled&comeflag=tixing");
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return logId.longValue();
  }
}
