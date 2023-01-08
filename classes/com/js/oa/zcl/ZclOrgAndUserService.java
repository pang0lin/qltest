package com.js.oa.zcl;

import com.js.oa.hr.officemanager.service.EmployeeBD;
import com.js.oa.hr.personnelmanager.service.NewEmployeeBD;
import com.js.oa.rws.pojo.ResultData;
import com.js.oa.rws.pojo.RwsObject;
import com.js.oa.rws.pojo.RwsOrganization;
import com.js.oa.rws.pojo.RwsPerson;
import com.js.oa.rws.util.XMLUtil;
import com.js.oa.userdb.util.DbOpt;
import com.js.oa.zcl.util.AESUtils;
import com.js.oa.zcl.util.WebserviceUtil;
import com.js.system.service.organizationmanager.OrganizationBD;
import com.js.system.vo.organizationmanager.OrganizationVO;
import com.js.system.vo.usermanager.EmployeeOtherInfoVO;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import net.sf.hibernate.HibernateException;
import org.jdom.JDOMException;

public class ZclOrgAndUserService extends HibernateBase {
  private static String AES_key = "InspurCB&JiusiOA=NB502";
  
  public String synchronizeDatas(String xml) {
    System.out.println("接收到的参数为---：" + xml);
    String decrypt = "";
    AESUtils aes = new AESUtils();
    try {
      decrypt = AESUtils.aesDecrypt(xml, AES_key);
    } catch (Exception e1) {
      e1.printStackTrace();
    } 
    System.out.println("接收到的参数为：");
    System.out.println(decrypt);
    String result = "";
    List<RwsObject> list = null;
    try {
      System.out.println("开始解析XML");
      list = XMLUtil.getObjectsFromXML(decrypt);
    } catch (Exception e) {
      System.out.println("XML解析错误！XML内容为：");
      System.out.println(decrypt);
      return "";
    } 
    List<ResultData> results = new ArrayList<ResultData>();
    String optype = "";
    for (int i = 0; i < list.size(); i++) {
      RwsObject obj = list.get(i);
      ResultData resultData = null;
      if (obj != null) {
        if (obj instanceof RwsPerson) {
          optype = "person";
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
          optype = "organization";
          RwsOrganization org = (RwsOrganization)obj;
          if ("2".equals(org.getOrgantype()) || "1".equals(org.getOrgantype()))
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
      if (resultData != null)
        results.add(resultData); 
    } 
    if ("person".equals(optype))
      setPersonRight(); 
    result = XMLUtil.getResultStr(results);
    System.out.println("未加密返回的结果为：" + result);
    try {
      result = AESUtils.aesEncrypt(result, AES_key);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    System.out.println("加密返回的结果为：");
    System.out.println(result);
    return result;
  }
  
  private ResultData addDept(RwsOrganization org) {
    ResultData result = new ResultData();
    result.setId(org.getId());
    try {
      if (isOrgExists(org.getUuid())) {
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
      vo.setGuid(org.getUuid());
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
      if (!isOrgExists(org.getUuid())) {
        result.setCode("2");
        result.setMessage("操作的人员/部门不存在");
        return result;
      } 
      OrganizationBD bd = new OrganizationBD();
      String guid = org.getUuid();
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
      if (!isOrgExists(org.getUuid())) {
        result.setCode("2");
        result.setMessage("操作的人员/部门不存在");
        return addDept(org);
      } 
      OrganizationBD bd = new OrganizationBD();
      String guid = org.getUuid();
      String id = getOrgIdByGUID(guid);
      OrganizationVO vo = bd.getOrgByOrgId(id);
      vo.setOrgName(org.getOu());
      vo.setOrgSerial(org.getDeptCode());
      vo.setOrgOrderCode(Integer.valueOf(org.getOrderCode()).intValue());
      vo.setGuid(org.getUuid());
      vo.setOrgType(org.getDeptType());
      DateFormat dtm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String lastupdate = dtm.format(new Date());
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
      long Id = empPositionInsert(person.getEmployeePosition());
      vo.setEmpPosition(person.getEmployeePosition());
      vo.setEmpPositionId(Long.valueOf(Id));
      String sex = "0";
      if ("女".equals(person.getEmployeeSex()))
        sex = "1"; 
      vo.setEmpSex(Byte.valueOf(sex).byteValue());
      vo.setEmpNumber(person.getEmployeeId());
      vo.setEmpEmail(person.getEmployeeMail());
      vo.setEmpMobilePhone(person.getEmployeeMobile());
      vo.setUserSimpleName(person.getCn());
      vo.setWm_code(person.getRights());
      String birthStr = person.getEmployeeBirthday();
      DateFormat dtm = new SimpleDateFormat("yyyy-MM-dd");
      Date birth = null;
      if (!"".equals(birthStr) && birthStr != null && !"null".equals(birthStr)) {
        birth = dtm.parse(birthStr);
      } else {
        birthStr = dtm.format(new Date());
        birth = dtm.parse(birthStr);
      } 
      vo.setEmpBirth(birth);
      vo.setBrowseRange("0");
      vo.setMailPost("0");
      vo.setSkin("blue");
      vo.setIsChangePwd("0");
      vo.setMailboxSize("100");
      vo.setNetDiskSize("150");
      vo.setImId(person.getCn());
      vo.setGrantRange("0");
      vo.setEmpState(person.getEmployeestate());
      vo.setUserOrderCode("10000.0000");
      DateFormat dtmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String lastupdate = dtmt.format(new Date());
      vo.setLastupdate(lastupdate);
      String mwmm = "111111";
      vo.setUserPassword(mwmm);
      if (vo.getUserAccounts() != null && !"".equals(vo.getUserAccounts())) {
        byte userIsActive = 1;
        Integer UserIsFormalUser = Integer.valueOf(0);
        if ("0".equals(person.getEmployeestate())) {
          userIsActive = 0;
          UserIsFormalUser = Integer.valueOf(2);
        } 
        vo.setUserIsFormalUser(UserIsFormalUser);
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
      System.out.println("----------部门id:" + getOrgIdByGUID(person.getNewParentUuid()));
      if (person.getNewParentUuid() != null && !"null".equals(person.getNewParentUuid()) && !"".equals(person.getNewParentUuid())) {
        flag = bd.add(vo, getOrgIdByGUID(person.getNewParentUuid()));
      } else {
        flag = bd.add(vo, getOrgIdByOrgNameString(person.getNewParentPath()));
      } 
      if (flag == 0) {
        result.setCode("0");
        result.setMessage("操作成功");
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
      long Id = empPositionInsert(person.getEmployeePosition());
      vo.setEmpPosition(person.getEmployeePosition());
      vo.setEmpPositionId(Long.valueOf(Id));
      String sex = "0";
      if ("女".equals(person.getEmployeeSex()))
        sex = "1"; 
      vo.setEmpSex(Byte.valueOf(sex).byteValue());
      vo.setEmpNumber(person.getEmployeeId());
      vo.setEmpEmail(person.getEmployeeMail());
      vo.setEmpMobilePhone(person.getEmployeeMobile());
      vo.setUserSimpleName(person.getCn());
      vo.setWm_code(person.getRights());
      vo.setEmpState(person.getEmployeestate());
      String birthStr = person.getEmployeeBirthday();
      DateFormat dtm = new SimpleDateFormat("yyyy-MM-dd");
      Date birth = null;
      if (!"".equals(birthStr) && birthStr != null && !"null".equals(birthStr)) {
        birth = dtm.parse(birthStr);
      } else {
        birthStr = dtm.format(new Date());
        birth = dtm.parse(birthStr);
      } 
      vo.setEmpBirth(birth);
      DateFormat dtmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String lastupdate = dtmt.format(new Date());
      vo.setLastupdate(lastupdate);
      vo.setBrowseRange("0");
      vo.setMailPost("0");
      vo.setSkin("blue");
      vo.setIsChangePwd("0");
      vo.setMailboxSize("100");
      vo.setNetDiskSize("150");
      vo.setImId(person.getCn());
      vo.setGrantRange("0");
      vo.setUserOrderCode("10000");
      byte isSuper = 1;
      vo.setUserIsSuper(isSuper);
      DateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
      vo.setUserSuperBegin(dt1.parse("2000-01-01"));
      vo.setUserSuperEnd(dt1.parse("9999-12-31"));
      String mwmm = "111111";
      vo.setUserPassword(mwmm);
      if (vo.getUserAccounts() != null && !"".equals(vo.getUserAccounts())) {
        byte userIsActive = 1;
        Integer UserIsFormalUser = Integer.valueOf(0);
        if ("0".equals(person.getEmployeestate())) {
          userIsActive = 0;
          UserIsFormalUser = Integer.valueOf(2);
        } 
        vo.setUserIsFormalUser(UserIsFormalUser);
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
  
  private void setPersonRight() {
    String sql = "SELECT c.emp_id,c.empname FROM org_employee c WHERE  c.wm_code='2' AND c.USERISACTIVE=1 AND c.USERISDELETED=0 AND c.USERISFORMALUSER=1 AND c.DOMAIN_ID=0 AND USERACCOUNTS IS NOT NULL AND c.guid IS NOT NULL";
    String sql2 = "SELECT a.roleuserid FROM ORG_ROLE a WHERE a.role_id=564";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Connection conn = null;
    DataSourceBase base = new DataSourceBase();
    String ids = "", ids2 = "", id3 = "";
    String nameStr = "";
    String rightStr = "";
    int count = 0;
    try {
      try {
        begin();
      } catch (HibernateException e1) {
        e1.printStackTrace();
      } 
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next())
        ids = String.valueOf(ids) + rs.getString(1) + ","; 
      rs.close();
      pstmt.close();
      pstmt = conn.prepareStatement(sql2);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        ids2 = rs.getString(1);
        ids2 = ids2.replace("$$", ",").replace("$", ",");
      } 
      rs.close();
      pstmt.close();
      ids = String.valueOf(ids) + ids2;
      ids = ids.replace(",,", ",");
      if (ids.indexOf(",") == 0)
        ids = "-1" + ids; 
      int dd = ids.lastIndexOf(",");
      int ff = ids.length();
      if (ids.lastIndexOf(",") + 1 == ids.length())
        ids = String.valueOf(ids) + "-1"; 
      sql = "SELECT distinct c.emp_id,c.empname FROM org_employee c WHERE  c.emp_id in(" + ids + ") AND c.USERISACTIVE=1" + 
        " AND c.USERISDELETED=0 AND c.USERISFORMALUSER=1 AND c.DOMAIN_ID=0 AND USERACCOUNTS IS NOT NULL and (c.wm_code='2' or c.guid is null)";
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        id3 = String.valueOf(id3) + "$" + rs.getString(1) + "$";
        nameStr = String.valueOf(nameStr) + rs.getString(2) + ",";
      } 
      rs.close();
      pstmt.close();
      if (!"".equals(id3)) {
        sql = "update ORG_ROLE set roleuserid=? , roleusername=? where role_id=564";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id3);
        pstmt.setString(2, nameStr);
        count = pstmt.executeUpdate();
        pstmt.close();
        if (count > 0) {
          sql = "delete from ORG_USER_ROLE where role_id=564";
          pstmt = conn.prepareStatement(sql);
          pstmt.executeUpdate();
          pstmt.close();
          sql = "insert ORG_USER_ROLE(emp_id,role_id) values(?,564)";
          pstmt = conn.prepareStatement(sql);
          String[] idStr = id3.replace("$$", ",").replace("$", "").split(",");
          for (int i = 0; i < idStr.length; i++) {
            pstmt.setInt(1, Integer.valueOf(idStr[i]).intValue());
            pstmt.addBatch();
          } 
          pstmt.executeBatch();
          pstmt.close();
          sql = "SELECT DISTINCT b.right_id FROM ORG_ROLE_RIGHT a LEFT JOIN org_right b ON a.right_id=b.right_id WHERE a.role_id=564";
          pstmt = conn.prepareStatement(sql);
          rs = pstmt.executeQuery();
          while (rs.next())
            rightStr = String.valueOf(rightStr) + rs.getString(1) + ","; 
          rs.close();
          pstmt.close();
          sql = "delete from ORG_RIGHTSCOPE where right_id=?";
          String[] rights = rightStr.substring(0, rightStr.lastIndexOf(",")).split(",");
          pstmt = conn.prepareStatement(sql);
          for (int j = 0; j < rights.length; j++) {
            pstmt.setString(1, rights[j]);
            pstmt.addBatch();
          } 
          pstmt.executeBatch();
          pstmt.close();
          sql = "insert into ORG_RIGHTSCOPE(emp_id,right_id,rightscopescope,rightscopeuser,rightscopegroup,rightscope,rightscopetype,domain_id,rightscope_id) values(?,?,?,?,?,?,0,0,?)";
          pstmt = conn.prepareStatement(sql);
          try {
            for (int k = 0; k < rights.length; k++) {
              for (int g = 0; g < idStr.length; g++) {
                long rightScopeSeq = getTableId().longValue();
                pstmt.setString(1, idStr[g]);
                pstmt.setString(2, rights[k]);
                pstmt.setString(3, "");
                pstmt.setString(4, "");
                pstmt.setString(5, "");
                pstmt.setString(6, "");
                pstmt.setLong(7, rightScopeSeq);
                pstmt.addBatch();
              } 
            } 
          } catch (HibernateException e) {
            e.printStackTrace();
          } 
          pstmt.executeBatch();
          pstmt.close();
        } 
      } 
    } catch (SQLException e) {
      try {
        if (rs != null)
          rs.close(); 
        if (pstmt != null)
          pstmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } finally {
      try {
        this.session.close();
        if (rs != null)
          rs.close(); 
        if (pstmt != null)
          pstmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
  }
  
  public void rwsTest() {
    String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><datas><data><id>SA0000000001002.ZLD</id><operation>INSERT</operation><type>person</type><uuid>SA0000000001002.ZLD</uuid><newParentUuid>SA0000000001003.S00000000000524</newParentUuid><newParentPath>兴良直属库.仓储科</newParentPath><utsNode><cn>SA0000000001002.ZLD</cn><sn>张连頔</sn><rights>1</rights><employeesex>1</employeesex><employeemail></employeemail><employeemobile></employeemobile><employeebirthday></employeebirthday><employeestate>1</employeestate><employeeposition>保管员</employeeposition><lastmodifytime></lastmodifytime></utsNode></data></datas>";
    AESUtils aes = new AESUtils();
    try {
      xml = AESUtils.aesEncrypt(xml, AES_key);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    System.out.println("加密后的xml:" + xml);
    String result = "";
    result = (new ZclOrgAndUserService()).synchronizeDatas(xml);
    System.out.println("-----result:" + result);
  }
  
  public String perService(String xml) throws Exception {
    String useraccount = "";
    String empid = "";
    String taskXml = "";
    String decrypt = "";
    String loginSuccess = "";
    String userid = "";
    String jsonStr = "";
    String type = "";
    String pageSize = "0";
    String currentPage = "0";
    WebserviceUtil wu = new WebserviceUtil();
    AESUtils aes = new AESUtils();
    System.out.println("接收到的xml----：" + xml);
    try {
      try {
        decrypt = AESUtils.aesDecrypt(xml, AES_key);
        System.out.println("获取用户信息解密后的数据：");
        System.out.println(decrypt);
      } catch (Exception e1) {
        e1.printStackTrace();
      } 
      Map map = wu.getLogInfo(decrypt);
      try {
        useraccount = (String)map.get("uuid");
        type = (String)map.get("type");
        pageSize = (String)map.get("pageSize");
        currentPage = (String)map.get("currentPage");
        empid = getEmpId(useraccount);
        if ("gwcy".equals(type)) {
          taskXml = docCheck(empid, useraccount, type, pageSize, currentPage);
        } else {
          taskXml = dealTask(empid, useraccount, type, pageSize, currentPage);
        } 
        taskXml = AESUtils.aesEncrypt(taskXml, AES_key);
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } catch (JDOMException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } 
    return taskXml;
  }
  
  private String dealTask(String empid, String useraccount, String type, String pageSize, String currentPage) {
    int ps = Integer.valueOf(pageSize).intValue();
    int cp = Integer.valueOf(currentPage).intValue();
    int size = (cp - 1) * ps;
    String sql = "SELECT b.workrecord_id,b.worktitle,b.wf_submitemployee_id,b.worksubmitperson,b.worksubmittime,b.workmainlinkfile,b.wf_work_id FROM jsf_workflowprocess a LEFT JOIN jsf_work b ON a.wf_workflowprocess_id = b.workprocess_id LEFT JOIN jsf_package c ON c.wf_package_id = a.wf_package_id WHERE b.wf_curemployee_id=? AND b.WORKSTATUS = ? AND c.wf_module_id IN(2,3) AND b.WORKLISTCONTROL = 1 AND b.WORKDELETE = 0 ORDER BY  b.emergence DESC, b.WF_WORK_ID DESC limit ?,?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Connection conn = null;
    String count = "0";
    String time = "";
    String workid = "";
    String status = "0";
    String result = "1";
    if ("ybsx".equals(type))
      status = "101"; 
    StringBuffer sb = new StringBuffer("");
    StringBuffer sb1 = new StringBuffer("");
    DataSourceBase base = new DataSourceBase();
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, empid);
      pstmt.setString(2, status);
      pstmt.setInt(3, size);
      pstmt.setInt(4, ps);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        time = rs.getString(5);
        workid = rs.getString(7);
        if (time.lastIndexOf(".0") > 0)
          time = time.substring(0, time.lastIndexOf(".0")); 
        sb.append("<data>");
        sb.append("<uuid>" + rs.getString(1) + "</uuid>");
        sb.append("<title>" + rs.getString(2) + "</title>");
        sb.append("<sender>" + rs.getString(4) + "</sender>");
        sb.append("<time>" + time + "</time>");
        sb.append("<href>/jsoa/zcl/goPageUrl.jsp?userName=" + useraccount + "&amp;workid=" + workid + "&amp;type=" + type + "</href>");
        sb.append("</data>");
      } 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      result = "0";
      try {
        if (rs != null)
          rs.close(); 
        if (pstmt != null)
          pstmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    count = dealTaskCount(empid, type);
    long timelong = (new Date()).getTime();
    sb1.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    sb1.append("<datas>");
    sb1.append("<result>" + result + "</result>");
    sb1.append("<user_id>" + empid + "</user_id>");
    sb1.append("<count>" + count + "</count>");
    sb1.append("<timestamp>" + timelong + "</timestamp>");
    sb1.append(sb);
    sb1.append("</datas>");
    return sb1.toString();
  }
  
  private String dealTaskCount(String empid, String type) {
    String sql = "SELECT count(*) FROM jsf_workflowprocess a LEFT JOIN jsf_work b ON a.wf_workflowprocess_id = b.workprocess_id LEFT JOIN jsf_package c ON c.wf_package_id = a.wf_package_id WHERE b.wf_curemployee_id=? AND b.WORKSTATUS = ? AND c.wf_module_id IN(2,3) AND b.WORKLISTCONTROL = 1 AND b.WORKDELETE = 0 ORDER BY  b.emergence DESC, b.WF_WORK_ID DESC";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Connection conn = null;
    String count = "0";
    String status = "0";
    if ("ybsx".equals(type))
      status = "101"; 
    DataSourceBase base = new DataSourceBase();
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, empid);
      pstmt.setString(2, status);
      rs = pstmt.executeQuery();
      if (rs.next())
        count = rs.getString(1); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      try {
        if (rs != null)
          rs.close(); 
        if (pstmt != null)
          pstmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return count;
  }
  
  private String docCheck(String empid, String useraccount, String type, String pageSize, String currentPage) {
    int ps = Integer.valueOf(pageSize).intValue();
    int cp = Integer.valueOf(currentPage).intValue();
    int size = (cp - 1) * ps;
    String sql = "SELECT DISTINCT a.DOCUMENTSENDFILE_ID,a.DOCUMENTSENDFILE_TITLE,a.createdTime,a.CREATEDEMP,b.SENDFILE_USER_ID FROM DOC_DOCUMENTSENDFILE a INNER JOIN DOC_SENDFILE_USER b ON a.DOCUMENTSENDFILE_ID = b.SENDFILE_ID  WHERE ((b.outSeeType IS NULL) OR (b.outSeeType <> '1')) AND ((b.EMP_ID = ?) OR ((1 <> 1)AND (b.orgId IS NOT NULL) ))  AND (a.SendFile_OverSee <> 2) AND (a.DOMAIN_ID = 0) ORDER BY a.DOCUMENTSENDFILE_ID DESC limit ?,?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Connection conn = null;
    String count = "0";
    String time = "";
    String result = "1";
    StringBuffer sb = new StringBuffer("");
    StringBuffer sb1 = new StringBuffer("");
    DataSourceBase base = new DataSourceBase();
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, empid);
      pstmt.setInt(2, size);
      pstmt.setInt(3, ps);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        time = rs.getString(3);
        if (time.lastIndexOf(".0") > 0)
          time = time.substring(0, time.lastIndexOf(".0")); 
        sb.append("<data>");
        sb.append("<uuid>" + rs.getString(1) + "</uuid>");
        sb.append("<title>" + rs.getString(2) + "</title>");
        sb.append("<sender>" + rs.getString(4) + "</sender>");
        sb.append("<time>" + time + "</time>");
        sb.append("<href>/jsoa/zcl/goPageUrl.jsp?userName=" + useraccount + "&amp;sendfileId=" + rs.getString(1) + "&amp;type=" + type + "</href>");
        sb.append("</data>");
      } 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      result = "0";
      try {
        if (rs != null)
          rs.close(); 
        if (pstmt != null)
          pstmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    long timelong = (new Date()).getTime();
    count = docCheckCount(empid, type);
    sb1.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    sb1.append("<datas>");
    sb1.append("<result>" + result + "</result>");
    sb1.append("<user_id>" + empid + "</user_id>");
    sb1.append("<count>" + count + "</count>");
    sb1.append("<timestamp>" + timelong + "</timestamp>");
    sb1.append(sb);
    sb1.append("</datas>");
    return sb1.toString();
  }
  
  private String docCheckCount(String empid, String type) {
    String sql = "SELECT DISTINCT a.DOCUMENTSENDFILE_ID,a.DOCUMENTSENDFILE_TITLE,a.createdTime,a.CREATEDEMP,b.SENDFILE_USER_ID FROM DOC_DOCUMENTSENDFILE a INNER JOIN DOC_SENDFILE_USER b ON a.DOCUMENTSENDFILE_ID = b.SENDFILE_ID  WHERE ((b.outSeeType IS NULL) OR (b.outSeeType <> '1')) AND ((b.EMP_ID = ?) OR ((1 <> 1)AND (b.orgId IS NOT NULL) ))  AND (a.SendFile_OverSee <> 2) AND (a.DOMAIN_ID = 0) ORDER BY a.DOCUMENTSENDFILE_ID DESC";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Connection conn = null;
    String count = "0";
    DataSourceBase base = new DataSourceBase();
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, empid);
      rs = pstmt.executeQuery();
      if (rs.next())
        count = rs.getString(1); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      try {
        if (rs != null)
          rs.close(); 
        if (pstmt != null)
          pstmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return count;
  }
  
  private String getEmpId(String useraccount) {
    String empId = "";
    String sql = "select emp_id from org_employee where useraccounts=? and USERISFORMALUSER = 1 and DOMAIN_ID = 0 and USERISACTIVE = 1 and USERISDELETED = 0";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Connection conn = null;
    DataSourceBase base = new DataSourceBase();
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, useraccount);
      rs = pstmt.executeQuery();
      while (rs.next())
        empId = rs.getString(1); 
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        if (pstmt != null)
          pstmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return empId;
  }
  
  private long empPositionInsert(String position) {
    String Id = "";
    String sql = "SELECT ID FROM ST_STATION WHERE STATION_NAME=?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Connection conn = null;
    DataSourceBase base = new DataSourceBase();
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, position);
      rs = pstmt.executeQuery();
      if (rs.next())
        Id = rs.getString(1); 
      rs.close();
      pstmt.close();
      if ("".equals(Id)) {
        long time = (new Date()).getTime();
        sql = "insert into ST_STATION(STATION_NAME,DOMAIN_ID,description,corpId,NO) values(?,0,?,0,?)";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, position);
        pstmt.setString(2, "");
        pstmt.setString(3, "gw" + time);
        pstmt.executeUpdate();
        pstmt.close();
        sql = "SELECT ID FROM ST_STATION WHERE STATION_NAME=?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, position);
        rs = pstmt.executeQuery();
        if (rs.next())
          Id = rs.getString(1); 
      } 
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        if (pstmt != null)
          pstmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return Long.valueOf(Id).longValue();
  }
}
