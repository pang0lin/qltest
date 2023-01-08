package com.js.oa.rws.service;

import com.itrus.utils.Decrypt;
import com.js.oa.hr.officemanager.service.EmployeeBD;
import com.js.oa.hr.personnelmanager.service.NewEmployeeBD;
import com.js.oa.rws.pojo.ResultData;
import com.js.oa.rws.pojo.RwsObject;
import com.js.oa.rws.pojo.RwsOrganization;
import com.js.oa.rws.pojo.RwsPerson;
import com.js.oa.rws.util.XMLUtil;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.service.organizationmanager.OrganizationBD;
import com.js.system.vo.organizationmanager.OrganizationVO;
import com.js.system.vo.usermanager.EmployeeOtherInfoVO;
import com.js.system.vo.usermanager.EmployeeVO;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RwsService {
  private static String AES_PASSWORD = "123456781qaz@wsX3";
  
  public String synchronizeDatas(String appMark, String xml) {
    System.out.println("接收到的参数为：");
    System.out.println(xml);
    String result = "";
    List<RwsObject> list = null;
    try {
      System.out.println("开始解析XML");
      list = XMLUtil.getObjectsFromXML(xml);
    } catch (Exception e) {
      System.out.println("XML解析错误！XML内容为：");
      System.out.println(xml);
      return "";
    } 
    List<ResultData> results = new ArrayList<ResultData>();
    for (int i = 0; i < list.size(); i++) {
      RwsObject obj = list.get(i);
      ResultData resultData = null;
      if (obj != null) {
        if (obj instanceof RwsPerson) {
          RwsPerson person = (RwsPerson)obj;
          if ("INSERT".equals(person.getOperation())) {
            resultData = addEmployee(person);
          } else if ("DELETE".equals(person
              .getOperation())) {
            resultData = deletEmloyee(person);
          } else if ("MODIFY".equals(person
              .getOperation())) {
            resultData = updateEmployee(person);
          } else if ("MOVE".equals(person
              .getOperation())) {
            resultData = moveEmployee(person);
          } else if ("PERMIT".equals(person
              .getOperation())) {
            resultData = updateEmployee(person);
          } else if ("FORBID".equals(person
              .getOperation())) {
            resultData = deletEmloyee(person);
          } else if ("RENAME".equals(person
              .getOperation())) {
            resultData = updateEmployee(person);
          } else {
            resultData = new ResultData();
            resultData.setId(person.getId());
            resultData.setCode("5");
            resultData.setMessage("操作类型错误");
          } 
        } else {
          RwsOrganization org = (RwsOrganization)obj;
          if ("INSERT".equals(org.getOperation())) {
            resultData = addDept(org);
          } else if ("DELETE".equals(org
              .getOperation())) {
            resultData = deletDept(org);
          } else if ("MODIFY".equals(org
              .getOperation())) {
            resultData = updateDept(org);
          } else if ("MOVE".equals(org
              .getOperation())) {
            resultData = moveDept(org);
          } else if ("RENAME".equals(org
              .getOperation())) {
            resultData = updateDept(org);
          } else {
            resultData = new ResultData();
            resultData.setId(org.getId());
            resultData.setCode("5");
            resultData.setMessage("操作类型错误");
          } 
        } 
      } else {
        resultData = new ResultData();
        resultData.setId("");
        resultData.setCode("1");
        resultData.setMessage("操作失败,系统异常");
      } 
      results.add(resultData);
    } 
    result = XMLUtil.getResultStr(results);
    System.out.println("返回的结果为：");
    System.out.println(result);
    return result;
  }
  
  private ResultData addDept(RwsOrganization org) {
    ResultData result = new ResultData();
    result.setId(org.getId());
    try {
      if (isOrgExists(org.getGuid())) {
        result.setCode("6");
        result.setMessage("添加人员/部门已存在");
        return updateDept(org);
      } 
      OrganizationBD bd = new OrganizationBD();
      OrganizationVO vo = new OrganizationVO();
      vo.setOrgName(org.getOu());
      vo.setOrgSerial(org.getDeptCode());
      vo.setOrgOrderCode(Integer.valueOf(org.getOrderCode()).intValue());
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
        result.setCode("0");
        result.setMessage("操作成功");
      } else {
        result.setCode("1");
        result.setMessage("操作失败,系统异常");
      } 
    } catch (Exception e) {
      e.printStackTrace();
      result.setCode("7");
      result.setMessage("属性错误(属性不存在或属性值错误)");
    } 
    return result;
  }
  
  private ResultData deletDept(RwsOrganization org) {
    ResultData result = new ResultData();
    result.setId(org.getId());
    try {
      if (!isOrgExists(org.getGuid())) {
        result.setCode("2");
        result.setMessage("操作的人员/部门不存在");
        return result;
      } 
      OrganizationBD bd = new OrganizationBD();
      String guid = org.getGuid();
      String id = getOrgIdByGUID(guid);
      bd.delete(Long.valueOf(id).longValue(), "0");
      result.setCode("0");
      result.setMessage("操作成功");
    } catch (Exception e) {
      e.printStackTrace();
      result.setCode("1");
      result.setMessage("操作失败,系统异常");
    } 
    return result;
  }
  
  private ResultData updateDept(RwsOrganization org) {
    ResultData result = new ResultData();
    result.setId(org.getId());
    try {
      if (!isOrgExists(org.getGuid())) {
        result.setCode("2");
        result.setMessage("操作的人员/部门不存在");
        return addDept(org);
      } 
      OrganizationBD bd = new OrganizationBD();
      String guid = org.getGuid();
      String id = getOrgIdByGUID(guid);
      OrganizationVO vo = bd.getOrgByOrgId(id);
      vo.setOrgName(org.getOu());
      vo.setOrgSerial(org.getDeptCode());
      vo.setOrgOrderCode(Integer.valueOf(org.getOrderCode()).intValue());
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
      result.setCode("0");
      result.setMessage("操作成功");
    } catch (Exception e) {
      e.printStackTrace();
      result.setCode("7");
      result.setMessage("属性错误(属性不存在或属性值错误)");
    } 
    return result;
  }
  
  private ResultData moveDept(RwsOrganization org) {
    return updateDept(org);
  }
  
  private ResultData addEmployee(RwsPerson person) {
    ResultData result = new ResultData();
    result.setId(person.getId());
    try {
      if (isEmployeeExists(person.getUuid(), "1")) {
        result.setCode("6");
        result.setMessage("添加人员/部门已存在");
        return updateEmployee(person);
      } 
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
      String mwmm = Decrypt.decryptPasswd(person.getUserPassWordCipher());
      vo.setUserPassword(mwmm);
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
        result.setCode("0");
      } else if (flag == 1) {
        String empid = getEmpIdByGUID(vo.getGuid(), "0");
        bd.regain(empid);
        result.setCode("0");
        result.setMessage("操作成功");
      } else {
        result.setCode("1");
        result.setMessage("操作失败,系统异常");
      } 
    } catch (Exception e) {
      e.printStackTrace();
      result.setCode("7");
      result.setMessage("属性错误(属性不存在或属性值错误)");
    } 
    return result;
  }
  
  private ResultData deletEmloyee(RwsPerson person) {
    ResultData result = new ResultData();
    result.setId(person.getId());
    try {
      if (!isEmployeeExists(person.getUuid(), "1")) {
        result.setCode("2");
        result.setMessage("操作的人员/部门不存在");
        return result;
      } 
      EmployeeBD bd = new EmployeeBD();
      String id = getEmpIdByGUID(person.getUuid(), "1");
      boolean flag = bd.forbid(id);
      if (flag) {
        result.setCode("0");
        result.setMessage("操作成功");
      } else {
        result.setCode("1");
        result.setMessage("操作失败,系统异常");
      } 
    } catch (Exception e) {
      e.printStackTrace();
      result.setCode("1");
      result.setMessage("操作失败,系统异常");
    } 
    return result;
  }
  
  private ResultData updateEmployee(RwsPerson person) {
    ResultData result = new ResultData();
    result.setId(person.getId());
    try {
      if (!isEmployeeExists(person.getUuid(), "1")) {
        result.setCode("2");
        result.setMessage("操作的人员/部门不存在");
        return addEmployee(person);
      } 
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
      String mwmm = Decrypt.decryptPasswd(person.getUserPassWordCipher());
      vo.setUserPassword(mwmm);
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
      result.setCode("0");
      result.setMessage("操作成功");
    } catch (Exception e) {
      e.printStackTrace();
      result.setCode("1");
      result.setMessage("操作失败,系统异常");
    } 
    return result;
  }
  
  private ResultData moveEmployee(RwsPerson person) {
    return updateEmployee(person);
  }
  
  private boolean isOrgExists(String guid) {
    boolean result = false;
    DbOpt db = new DbOpt();
    try {
      String count = db
        .executeQueryToStrPS("select count(0) num from org_organization where orgstatus='0' and guid = ?", new String[] { guid });
      if (!"0".equals(count))
        result = true; 
      db.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  private String getOrgIdByGUID(String guid) {
    String result = "";
    DbOpt db = new DbOpt();
    try {
      result = db
        .executeQueryToStrPS("select max(org_id) org_id from org_organization where orgstatus='0' and guid = ?", new String[] { guid });
      db.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  private String getOrgIdByOrgNameString(String ParentPath) {
    String result = "";
    DbOpt db = new DbOpt();
    try {
      result = db
        .executeQueryToStrPS("select max(org_id) org_id from org_organization where orgstatus='0' and orgnamestring = ?", new String[] { ParentPath });
      db.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  private boolean isEmployeeExists(String uuid, String userisactiveFlag) {
    boolean result = false;
    DbOpt db = new DbOpt();
    try {
      String count = db
        .executeQueryToStrPS("select count(0) num  from org_Employee where userisactive=? and guid = ?", new String[] { userisactiveFlag, uuid });
      if (!"0".equals(count))
        result = true; 
      db.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  private String getEmpIdByGUID(String guid, String userisactiveFlag) {
    String result = "";
    DbOpt db = new DbOpt();
    try {
      result = db
        .executeQueryToStrPS("select max(emp_id) emp_id from org_employee where userisactive=? and guid = ?", new String[] { userisactiveFlag, guid });
      db.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
}
