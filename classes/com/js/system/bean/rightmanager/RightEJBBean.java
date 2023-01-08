package com.js.system.bean.rightmanager;

import com.js.system.vo.rightmanager.RightVO;
import com.js.system.vo.rolemanager.RightScopeVO;
import com.js.system.vo.rolemanager.RoleVO;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;

public class RightEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public List getRightType() throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery("select aaa.rightType from com.js.system.vo.rightmanager.RightVO aaa group by aaa.rightType");
      list = query.list();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      System.out.println(e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List getRightIdAndName(String rightType) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery("select aaa.rightId,aaa.rightName from com.js.system.vo.rightmanager.RightVO aaa where aaa.rightType = '" + rightType + "' order by aaa.rightId desc");
      list = query.list();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      System.out.println(e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List getIdTypeName(String manager, String domainId) throws Exception {
    List list = null;
    begin();
    try {
      if ("1".equals(manager)) {
        list = this.session.createQuery("select aaa.rightId,aaa.rightType,aaa.rightName,aaa.rightClass,aaa.rightCode from com.js.system.vo.rightmanager.RightVO aaa where aaa.domainId=" + 
            domainId + " and aaa.rightStatus=1 and (aaa.rightCustomer='' or aaa.rightCustomer is null " + ((
            SystemCommon.getCustomerName().equals("") || SystemCommon.getCustomerName().equals("九思")) ? "" : ("or aaa.rightCustomer like '%" + SystemCommon.getCustomerName() + "%' ")) + ")" + 
            "order by aaa.rightClass,aaa.rightType,aaa.rightId").list();
      } else {
        list = this.session.createQuery("select aaa.rightId,aaa.rightType,aaa.rightName,aaa.rightClass,aaa.rightCode from com.js.system.vo.rightmanager.RightVO aaa where aaa.domainId=" + 
            domainId + " and aaa.rightStatus=1 and (aaa.rightCustomer='' or aaa.rightCustomer is null " + ((
            SystemCommon.getCustomerName().equals("") || SystemCommon.getCustomerName().equals("九思")) ? "" : ("or aaa.rightCustomer like '%" + SystemCommon.getCustomerName() + "%' ")) + ")" + 
            "order by aaa.rightClass,aaa.rightType,aaa.rightId").list();
      } 
    } catch (Exception e) {
      System.out.println(e.getMessage());
      System.out.println(e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List getRightInfo(String rightId) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery("select aaa.rightName,aaa.rightType,aaa.rightDescription from com.js.system.vo.rightmanager.RightVO aaa where aaa.rightId = " + rightId);
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
  
  public List getRoleId(String rightId) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery("select aaa.roleId from com.js.system.vo.rightmanager.RightVO bbb join bbb.role aaa where bbb.rightId = " + rightId);
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
  
  public void updateRole(String rightId, String[] roleId) throws Exception {
    begin();
    try {
      if (roleId == null)
        roleId = new String[0]; 
      RightVO rightVO = (RightVO)this.session.load(RightVO.class, new Long(rightId));
      String selectRange = rightVO.getRightSelectRange();
      Set<RoleVO> roleSet = rightVO.getRole();
      if (roleSet == null)
        roleSet = new HashSet(); 
      StringBuffer buffer = new StringBuffer();
      byte defaultScope = 0;
      int i;
      for (i = 1; i < 5; i++) {
        if (selectRange.charAt(i) == '1') {
          defaultScope = Byte.valueOf(String.valueOf(i)).byteValue();
          break;
        } 
      } 
      List<E> list = this.session.createQuery("select roleVO.roleId from com.js.system.vo.rightmanager.RightVO rightVO join rightVO.role roleVO where rightVO.rightId=" + rightId).list();
      if (list != null && list.size() > 0)
        for (i = 0; i < list.size(); i++)
          buffer.append(list.get(i)).append(",");  
      String oldRole = buffer.append("-1").toString();
      System.out.println("权限修改前对应的角色:" + oldRole);
      buffer = new StringBuffer();
      for (i = 0; i < roleId.length; i++) {
        if (!roleId[i].equals(""))
          buffer.append(roleId[i]).append(","); 
      } 
      String nowRole = buffer.append("-1").toString();
      System.out.println("权限修改后对应的角色:" + nowRole);
      buffer = new StringBuffer();
      list = this.session.createQuery("select roleVO.roleId from com.js.system.vo.rolemanager.RoleVO roleVO  where roleVO.roleId in(" + oldRole + ") and roleVO.roleId not in(" + nowRole + ")").list();
      if (list != null && list.size() > 0)
        for (i = 0; i < list.size(); i++) {
          RoleVO roleVO = (RoleVO)this.session.load(RoleVO.class, new Long(list.get(i).toString()));
          Set rightSet = roleVO.getRights();
          if (rightSet == null)
            rightSet = new HashSet(); 
          rightSet.remove(rightVO);
          roleVO.setRights(rightSet);
          roleSet.remove(roleVO);
          buffer.append(list.get(i)).append(",");
        }  
      String shouldDelRole = buffer.append("-1").toString();
      System.out.println("应删除原来的角色:" + shouldDelRole);
      buffer = new StringBuffer();
      list = this.session.createQuery("select roleVO.roleId from com.js.system.vo.rolemanager.RoleVO roleVO where roleVO.roleId not in(" + oldRole + ") and roleVO.roleId in(" + nowRole + ")").list();
      if (list != null && list.size() > 0)
        for (i = 0; i < list.size(); i++) {
          RoleVO roleVO = (RoleVO)this.session.load(RoleVO.class, new Long(list.get(i).toString()));
          Set<RightVO> rightSet = roleVO.getRights();
          if (rightSet == null)
            rightSet = new HashSet(); 
          rightSet.add(rightVO);
          roleVO.setRights(rightSet);
          roleSet.add(roleVO);
          buffer.append(list.get(i)).append(",");
        }  
      String shouldAddRole = buffer.append("-1").toString();
      System.out.println("应增加的新的角色:" + shouldDelRole);
      buffer = new StringBuffer();
      list = this.session.createQuery("select emp.empId from com.js.system.vo.usermanager.EmployeeVO emp join emp.roles roleVO where roleVO.roleId in  (" + shouldDelRole + ") and roleVO.roleId not in (" + nowRole + ")").list();
      if (list != null && list.size() > 0)
        for (i = 0; i < list.size(); i++)
          buffer.append(list.get(i)).append(",");  
      String shouldDelEmp = buffer.append("-1").toString();
      System.out.println("应删除的角色对应的用户:" + shouldDelEmp);
      buffer = new StringBuffer();
      list = this.session.createQuery("select scope from com.js.system.vo.rolemanager.RightScopeVO scope join scope.employee emp join scope.right r where emp.empId in (" + shouldDelEmp + ") and r.rightId=" + rightId).list();
      if (list != null && list.size() > 0)
        for (i = 0; i < list.size(); i++) {
          RightScopeVO rightScopeVO = (RightScopeVO)list.get(i);
          this.session.delete(rightScopeVO);
        }  
      list = this.session.createQuery("select emp.empId from com.js.system.vo.usermanager.EmployeeVO emp join emp.roles roleVO where roleVO.roleId in (" + shouldAddRole + ")").list();
      if (list != null && list.size() > 0)
        for (i = 0; i < list.size(); i++) {
          String temp = list.get(i).toString();
          EmployeeVO employeeVO = (EmployeeVO)this.session.load(EmployeeVO.class, new Long(temp));
          Set<RightScopeVO> rightScopeVOSet = employeeVO.getRightScopes();
          if (rightScopeVOSet == null)
            rightScopeVOSet = new HashSet(); 
          RightScopeVO rightScopeVO = new RightScopeVO();
          rightScopeVO.setRightScopeId(getTableId().longValue());
          rightScopeVO.setEmployee(employeeVO);
          rightScopeVO.setRight(rightVO);
          rightScopeVO.setRightScopeScope("");
          rightScopeVO.setRightScopeType(defaultScope);
          rightScopeVO.setDomainId(employeeVO.getDomainId());
          this.session.save(rightScopeVO);
          rightScopeVOSet.add(rightScopeVO);
          rightVO.addRightScopes(rightScopeVO);
          employeeVO.setRightScopes(rightScopeVOSet);
        }  
      rightVO.setRole(roleSet);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public List getUserRightScope(String userId) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery("select ccc.rightId,aaa.rightScopeType,aaa.rightScopeScope,aaa.rightScopeUser,aaa.rightScopeGroup,aaa.rightScope from com.js.system.vo.rolemanager.RightScopeVO aaa join aaa.employee bbb join aaa.right ccc where bbb.empId = " + 

          
          userId);
      list = query.list();
    } catch (Exception e) {
      System.out.println("e.getMessage() : " + e.getMessage());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List getAuditRightScope(String logId) throws Exception {
    List<String[]> list = new ArrayList();
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      String sql = "select RIGHT_ID,RIGHTSCOPETYPE,RIGHTSCOPESCOPE,RIGHTSCOPEUSER,RIGHTSCOPEGROUP,RIGHTSCOPE from audit_RIGHTSCOPE where log_id=" + 
        logId;
      base.begin();
      rs = base.executeQuery(sql);
      while (rs.next()) {
        String[] right = new String[6];
        right[0] = (rs.getString(1) == null) ? "" : rs.getString(1);
        right[1] = (rs.getString(2) == null) ? "" : rs.getString(2);
        right[2] = (rs.getString(3) == null) ? "" : rs.getString(3);
        right[3] = (rs.getString(4) == null) ? "" : rs.getString(4);
        right[4] = (rs.getString(5) == null) ? "" : rs.getString(5);
        right[5] = (rs.getString(6) == null) ? "" : rs.getString(6);
        list.add(right);
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      System.out.println("e.getMessage() : " + e.getMessage());
      throw e;
    } 
    return list;
  }
}
