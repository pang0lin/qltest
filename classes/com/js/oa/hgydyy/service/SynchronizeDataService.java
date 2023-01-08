package com.js.oa.hgydyy.service;

import com.js.oa.hgydyy.pojo.DyyObject;
import com.js.oa.hgydyy.pojo.DyyOrganization;
import com.js.oa.hgydyy.pojo.DyyPerson;
import com.js.oa.hgydyy.pojo.DyyRole;
import com.js.oa.hgydyy.pojo.ResultData;
import com.js.oa.hgydyy.utils.XMLUtil;
import com.js.oa.hr.officemanager.service.EmployeeBD;
import com.js.oa.hr.personnelmanager.service.NewEmployeeBD;
import com.js.system.service.organizationmanager.OrganizationBD;
import com.js.system.vo.organizationmanager.OrganizationVO;
import com.js.system.vo.usermanager.EmployeeOtherInfoVO;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class SynchronizeDataService {
  public String synchronizeDatas(String appMark, String xml) {
    return synchronizeOneData(appMark, xml);
  }
  
  private synchronized String synchronizeOneData(String appMark, String xml) {
    System.out.println("appMark:" + appMark);
    System.out.println("接收到的参数为：");
    System.out.println(xml);
    String result = "";
    List<DyyObject> list = null;
    try {
      System.out.println("开始解析XML");
      list = XMLUtil.getObjectsFromXML(xml);
    } catch (Exception e) {
      System.out.println("XML解析错误！XML内容为：");
      System.out.println(xml);
      e.printStackTrace();
      return "";
    } 
    List<ResultData> results = new ArrayList<ResultData>();
    for (int i = 0; i < list.size(); i++) {
      DyyObject obj = list.get(i);
      if (obj != null)
        if (obj instanceof DyyPerson) {
          DyyPerson person = (DyyPerson)obj;
          if ("INSERT".equals(person.getOperation())) {
            result = addEmployee(person);
          } else if ("DELETE".equals(person
              .getOperation())) {
            result = deletEmloyee(person);
          } else if ("UPDATE".equals(person.getOperation()) || "MODIFY".equals(person.getOperation())) {
            result = updateEmployee(person);
          } 
        } else if (obj instanceof DyyOrganization) {
          DyyOrganization org = (DyyOrganization)obj;
          if ("INSERT".equals(org.getOperation())) {
            result = addDept(org);
          } else if ("DELETE".equals(org
              .getOperation())) {
            result = deletDept(org);
          } else if ("UPDATE".equals(org.getOperation()) || "MODIFY".equals(org.getOperation())) {
            result = updateDept(org);
          } 
        }  
    } 
    return result;
  }
  
  private String addDept(DyyOrganization org) {
    String result = "-1";
    try {
      if (isOrgExists(org.getGuid()))
        return updateDept(org); 
      OrganizationBD bd = new OrganizationBD();
      OrganizationVO vo = new OrganizationVO();
      vo.setOrgName(org.getOu());
      vo.setOrgSerial(org.getDeptCode());
      String orderCodeStr = org.getOrderCode();
      if (StringUtils.isEmpty(orderCodeStr)) {
        orderCodeStr = "-1";
        org.setOrderCode("-1");
      } 
      vo.setOrgOrderCode(Integer.valueOf(orderCodeStr).intValue());
      vo.setOrgType(org.getDeptType());
      vo.setGuid(org.getGuid());
      vo.setOrgManagerEmpId("");
      vo.setOrgManagerEmpName("");
      String parent = "0";
      if (org.getNewParentUuid() != null && !"null".equals(org.getNewParentUuid()) && !"".equals(org.getNewParentUuid())) {
        parent = getOrgIdByGUID(org.getNewParentUuid());
      } else {
        parent = getOrgIdByOrgNameString(org.getNewParentPath());
      } 
      if (parent == null || "".equals(parent))
        parent = "0"; 
      vo.setOrgParentOrgId(Integer.valueOf(parent).intValue());
      vo.setDomainId("0");
      DateFormat dtm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String lastupdate = dtm.format(new Date());
      String lastupdatestr = org.getLastmodifytime();
      if (!"".equals(lastupdatestr) && lastupdatestr != null && !"null".equals(lastupdatestr))
        lastupdate = lastupdatestr; 
      vo.setLastupdate(lastupdate);
      boolean flag = bd.add(vo, org.getOrderCode(), parent, Integer.valueOf(1));
      if (flag) {
        result = "0";
      } else {
        result = "-1";
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  private String deletDept(DyyOrganization org) {
    String result = "-1";
    try {
      if (!isOrgExists(org.getGuid()))
        return result; 
      OrganizationBD bd = new OrganizationBD();
      String guid = org.getGuid();
      String id = getOrgIdByGUID(guid);
      bd.delete(Long.valueOf(id).longValue(), "0");
      result = "0";
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  private String updateDept(DyyOrganization org) {
    String result = "-1";
    try {
      if (!isOrgExists(org.getGuid()))
        return addDept(org); 
      OrganizationBD bd = new OrganizationBD();
      String guid = org.getGuid();
      String id = getOrgIdByGUID(guid);
      OrganizationVO vo = bd.getOrgByOrgId(id);
      vo.setOrgName(org.getOu());
      vo.setOrgSerial(org.getDeptCode());
      String orderCodeStr = org.getOrderCode();
      if (StringUtils.isEmpty(orderCodeStr)) {
        orderCodeStr = "-1";
        org.setOrderCode("-1");
      } 
      vo.setOrgOrderCode(Integer.valueOf(orderCodeStr).intValue());
      vo.setGuid(org.getGuid());
      vo.setOrgType(org.getDeptType());
      DateFormat dtm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String lastupdate = dtm.format(new Date());
      String lastupdatestr = org.getLastmodifytime();
      if (!"".equals(lastupdatestr) && lastupdatestr != null && !"null".equals(lastupdatestr))
        lastupdate = lastupdatestr; 
      vo.setLastupdate(lastupdate);
      String parent = "0";
      if (org.getNewParentUuid() != null && !"null".equals(org.getNewParentUuid()) && !"".equals(org.getNewParentUuid())) {
        parent = getOrgIdByGUID(org.getNewParentUuid());
      } else {
        parent = getOrgIdByOrgNameString(org.getNewParentPath());
      } 
      if (parent == null || "".equals(parent))
        parent = "0"; 
      vo.setOrgParentOrgId(Integer.valueOf(parent).intValue());
      bd.update(vo, org.getOrderCode(), parent, Integer.valueOf(1), "1");
      result = "0";
    } catch (Exception exception) {}
    return result;
  }
  
  private String addEmployee(DyyPerson person) {
    String result = "-1";
    try {
      if (isEmployeeExists(person.getUuid(), "1"))
        return updateEmployee(person); 
      EmployeeBD bd = new EmployeeBD();
      EmployeeVO vo = new EmployeeVO();
      vo.setUserAccounts(person.getCn());
      vo.setEmpName(person.getSn());
      vo.setGuid(person.getUuid());
      vo.setEmpPosition(person.getEmployeePosition());
      String sex = "0";
      if ("女".equals(person.getEmployeeSex()))
        sex = "1"; 
      vo.setEmpSex(Byte.valueOf(sex).byteValue());
      vo.setEmpNumber(person.getEmployeeId());
      vo.setEmpEmail(person.getEmployeeMail());
      vo.setEmpMobilePhone(person.getEmployeeMobile());
      vo.setUserSimpleName(person.getCn());
      String birthStr = person.getEmployeeBirthday();
      DateFormat dtm = new SimpleDateFormat("yyyy-MM-dd");
      Date birth = null;
      if (!"".equals(birthStr) && birthStr != null && !"null".equals(birthStr)) {
        birth = dtm.parse(birthStr);
        vo.setEmpBirth(birth);
      } 
      vo.setBrowseRange("0");
      vo.setMailPost("0");
      vo.setSkin("blue");
      vo.setIsChangePwd("0");
      vo.setMailboxSize("100");
      vo.setNetDiskSize("150");
      vo.setImId(person.getCn());
      vo.setGrantRange("0");
      vo.setEmpState(person.getEmployeestate());
      vo.setUserOrderCode(person.getOrderCode());
      DateFormat dtmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date lastupdate = dtmt.parse(dtmt.format(new Date()));
      String lastupdatestr = person.getLastmodifytime();
      if (!"".equals(lastupdatestr) && lastupdatestr != null && !"null".equals(lastupdatestr))
        lastupdate = dtmt.parse(lastupdatestr); 
      vo.setLastupdate(String.valueOf(lastupdate.getTime()));
      vo.setUserPassword(person.getUserPassWord());
      vo.setEmpIdCard(person.getEmployeeCard());
      if (vo.getUserAccounts() != null && !"".equals(vo.getUserAccounts())) {
        vo.setUserIsFormalUser(Integer.valueOf("0"));
        byte userIsActive = 1;
        vo.setUserIsActive(userIsActive);
      } else {
        byte userIsActive = 0;
        vo.setUserIsActive(userIsActive);
        vo.setUserIsFormalUser(Integer.valueOf("2"));
      } 
      byte isSuper = 1;
      vo.setUserIsSuper(isSuper);
      DateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
      vo.setUserSuperBegin(dt1.parse("2000-01-01"));
      vo.setUserSuperEnd(dt1.parse("9999-12-31"));
      int flag = 0;
      vo.setDomainId("0");
      if (person.getNewParentUuid() != null && !"null".equals(person.getNewParentUuid()) && !"".equals(person.getNewParentUuid())) {
        flag = bd.add(vo, getOrgIdByGUID(person.getNewParentUuid()));
      } else {
        flag = bd.add(vo, getOrgIdByOrgNameString(person.getNewParentPath()));
      } 
      if (flag == 0) {
        result = "0";
      } else if (flag == 1) {
        String empId = getEmpIdByGUID(vo.getGuid(), "0");
        bd.regain(empId);
        result = "0";
      } else {
        result = "-1";
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  private String deletEmloyee(DyyPerson person) {
    String result = "-1";
    try {
      if (!isEmployeeExists(person.getUuid(), "1"))
        return result; 
      EmployeeBD bd = new EmployeeBD();
      String id = getEmpIdByGUID(person.getUuid(), "1");
      boolean flag = bd.forbid(id);
      if (flag)
        result = "0"; 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  private String updateEmployee(DyyPerson person) {
    String result = "-1";
    try {
      if (!isEmployeeExists(person.getUuid(), "1"))
        return addEmployee(person); 
      NewEmployeeBD bd = new NewEmployeeBD();
      String id = getEmpIdByGUID(person.getUuid(), "1");
      EmployeeVO vo = new EmployeeVO();
      List<Object[]> list = bd.selectSingle(Long.valueOf(id));
      if (list != null && list.size() >= 1) {
        Object[] object = list.get(0);
        vo = (EmployeeVO)object[0];
      } 
      vo.setUserAccounts(person.getCn());
      vo.setEmpName(person.getSn());
      vo.setGuid(person.getUuid());
      vo.setEmpPosition(person.getEmployeePosition());
      String sex = "0";
      if ("女".equals(person.getEmployeeSex()))
        sex = "1"; 
      vo.setEmpSex(Byte.valueOf(sex).byteValue());
      vo.setEmpNumber(person.getEmployeeId());
      vo.setEmpEmail(person.getEmployeeMail());
      vo.setEmpMobilePhone(person.getEmployeeMobile());
      vo.setUserOrderCode(person.getOrderCode());
      vo.setUserSimpleName(person.getCn());
      vo.setEmpState(person.getEmployeestate());
      String birthStr = person.getEmployeeBirthday();
      DateFormat dtm = new SimpleDateFormat("yyyy-MM-dd");
      Date birth = null;
      if (!"".equals(birthStr) && birthStr != null && !"null".equals(birthStr)) {
        birth = dtm.parse(birthStr);
        vo.setEmpBirth(birth);
      } 
      DateFormat dtmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date lastupdate = dtmt.parse(dtmt.format(new Date()));
      String lastupdatestr = person.getLastmodifytime();
      if (!"".equals(lastupdatestr) && lastupdatestr != null && !"null".equals(lastupdatestr))
        lastupdate = dtmt.parse(lastupdatestr); 
      vo.setLastupdate(String.valueOf(lastupdate.getTime()));
      vo.setBrowseRange("0");
      vo.setMailPost("0");
      vo.setSkin("blue");
      vo.setIsChangePwd("0");
      vo.setMailboxSize("100");
      vo.setNetDiskSize("150");
      vo.setImId(person.getCn());
      vo.setGrantRange("0");
      byte isSuper = 1;
      vo.setUserIsSuper(isSuper);
      DateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
      vo.setUserSuperBegin(dt1.parse("2000-01-01"));
      vo.setUserSuperEnd(dt1.parse("9999-12-31"));
      vo.setUserPassword(person.getUserPassWord());
      vo.setEmpIdCard(person.getEmployeeCard());
      if (vo.getUserAccounts() != null && 
        !"".equals(vo.getUserAccounts())) {
        vo.setUserIsFormalUser(Integer.valueOf("0"));
        byte userIsActive = 1;
        vo.setUserIsActive(userIsActive);
      } else {
        byte userIsActive = 0;
        vo.setUserIsActive(userIsActive);
        vo.setUserIsFormalUser(Integer.valueOf("2"));
      } 
      vo.setDomainId("0");
      vo.setEmpId(Long.valueOf(getEmpIdByGUID(person.getUuid(), "1")).longValue());
      if (person.getNewParentUuid() != null && !"null".equals(person.getNewParentUuid()) && !"".equals(person.getNewParentUuid())) {
        bd.update(vo, new EmployeeOtherInfoVO(), getOrgIdByGUID(person.getNewParentUuid()), Long.valueOf(vo.getEmpId()));
      } else {
        bd.update(vo, new EmployeeOtherInfoVO(), getOrgIdByOrgNameString(person.getNewParentPath()), Long.valueOf(vo.getEmpId()));
      } 
      result = "0";
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  private ResultData addRole(DyyRole role) {
    ResultData result = new ResultData();
    result.setId(role.getId());
    if (isRoleExists(role.getGuid())) {
      result = updateRole(role);
    } else {
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try {
        String sql = "insert into org_role(role_id,rolename,roledescription,createdorg,createdemp,domain_id,rolerangetype,guid) values(";
        sql = String.valueOf(sql) + "hibernate_sequence.nextval,?,?,0,0,0,0,?)";
        conn = (new DataSourceBase()).getDataSource().getConnection();
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, role.getRoleName());
        pstmt.setString(2, role.getRoleDescription());
        pstmt.setString(3, role.getUuid());
        int num = pstmt.executeUpdate();
        if (num > 0) {
          result.setCode("0");
          result.setMessage("操作成功");
        } else {
          result.setCode("1");
          result.setMessage("操作失败,系统异常");
        } 
        pstmt.close();
        conn.close();
      } catch (Exception e) {
        if (conn != null)
          try {
            conn.close();
          } catch (Exception e1) {
            e1.printStackTrace();
          }  
        e.printStackTrace();
        result.setCode("1");
        result.setMessage("操作失败,系统异常");
      } 
    } 
    return result;
  }
  
  private ResultData updateRole(DyyRole role) {
    ResultData result = new ResultData();
    result.setId(role.getId());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      String sql = "update org_role set rolename=?,roledescription=? where guid=?";
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, role.getRoleName());
      pstmt.setString(2, role.getRoleDescription());
      pstmt.setString(3, role.getUuid());
      int num = pstmt.executeUpdate();
      if (num > 0) {
        result.setCode("0");
        result.setMessage("操作成功");
      } else {
        result.setCode("1");
        result.setMessage("操作失败,系统异常");
      } 
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
      result.setCode("1");
      result.setMessage("操作失败,系统异常");
    } 
    return result;
  }
  
  private ResultData deleteRole(DyyRole role) {
    ResultData result = new ResultData();
    result.setId(role.getId());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String roleId = "0";
    try {
      String sql = "select role_id from org_role where guid=?";
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, role.getUuid());
      rs = pstmt.executeQuery();
      if (rs.next())
        roleId = rs.getString(1); 
      rs.close();
      sql = "delete from org_rightscope where right_id in(select r.right_id from org_role_right r where r.role_id=?) and emp_id in(select u.emp_id from org_user_role u where u.role_id=?)";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, roleId);
      pstmt.setString(2, roleId);
      pstmt.executeUpdate();
      sql = "delete from org_role_right where role_id=?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, roleId);
      pstmt.executeUpdate();
      sql = "delete from org_user_role where role_id=?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, roleId);
      pstmt.executeUpdate();
      sql = "delete from org_role where role_id=?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, roleId);
      int num = pstmt.executeUpdate();
      if (num > 0) {
        result.setCode("0");
        result.setMessage("操作成功");
      } else {
        result.setCode("1");
        result.setMessage("操作失败,系统异常");
      } 
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
      result.setCode("1");
      result.setMessage("操作失败,系统异常");
    } 
    return result;
  }
  
  private boolean isRoleExists(String guid) {
    boolean result = false;
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      String sql = "select count(0) num from org_role where guid =?";
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, guid);
      rs = pstmt.executeQuery();
      if (rs.next() && 
        rs.getInt(1) > 0)
        result = true; 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return result;
  }
  
  private boolean isOrgExists(String guid) {
    boolean result = false;
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      String sql = "select count(0) num from org_organization where orgstatus='0' and guid =?";
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, guid);
      rs = pstmt.executeQuery();
      if (rs.next() && 
        rs.getInt(1) != 0)
        result = true; 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return result;
  }
  
  private String getOrgIdByGUID(String guid) {
    String result = "";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      String sql = "select max(org_id) org_id from org_organization where orgstatus='0' and guid =?";
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, guid);
      rs = pstmt.executeQuery();
      if (rs.next())
        result = rs.getString(1); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return result;
  }
  
  private String getOrgIdByOrgNameString(String ParentPath) {
    String result = "";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      String sql = "select max(org_id) org_id from org_organization where orgstatus='0' and orgnamestring = ?";
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, ParentPath);
      rs = pstmt.executeQuery();
      if (rs.next())
        result = rs.getString(1); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return result;
  }
  
  private boolean isEmployeeExists(String uuid, String userisactiveFlag) {
    boolean result = false;
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      String sql = "select count(0) num  from org_Employee where userisactive=? and guid = ?";
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, userisactiveFlag);
      pstmt.setString(2, uuid);
      rs = pstmt.executeQuery();
      if (rs.next() && 
        rs.getInt(1) != 0)
        result = true; 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return result;
  }
  
  private String getEmpIdByGUID(String guid, String userisactiveFlag) {
    String result = "";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      String sql = "select max(emp_id) emp_id from org_employee where userisactive=? and guid = ?";
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, userisactiveFlag);
      pstmt.setString(2, guid);
      rs = pstmt.executeQuery();
      if (rs.next())
        result = rs.getString(1); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return result;
  }
  
  private String syncRole(String empId, String userAccount, String oprType) {
    String result = "";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      String orgSerial = "";
      String sql = "select orgserial from org_organization oo,org_organization_user oou where oo.org_id=oou.org_id and oou.emp_id=?";
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, empId);
      rs = pstmt.executeQuery();
      if (rs.next())
        orgSerial = rs.getString(1); 
      rs.close();
      System.out.println("orgSerial:" + orgSerial);
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return result;
  }
  
  public static void main(String[] args) {}
}
