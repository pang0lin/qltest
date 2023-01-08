package com.js.oa.haier;

import com.js.oa.hr.officemanager.service.EmployeeBD;
import com.js.oa.hr.personnelmanager.service.NewEmployeeBD;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.service.organizationmanager.OrganizationBD;
import com.js.system.vo.organizationmanager.OrganizationVO;
import com.js.system.vo.usermanager.EmployeeOtherInfoVO;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;

public class OrgAndUserService extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbActivate() throws EJBException, RemoteException {}
  
  public void ejbPassivate() throws EJBException, RemoteException {}
  
  public void ejbRemove() throws EJBException, RemoteException {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public void orgAndEmpOperate() {
    orgSynchronize();
    orgSynchronize();
    empSynchronize();
    orgSynchronize();
    empSynchronize();
  }
  
  private void orgSynchronize() {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    DataSourceBase base = new DataSourceBase();
    String sql = "select departid,departNo,departNm,uppDepartId,departleader from vHOrgnization";
    Map<String, String> map = new HashMap<String, String>();
    try {
      conn = base.getDataSource("jdbc/haier").getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        map.put("departid", rs.getString(1));
        map.put("departNo", rs.getString(2));
        map.put("departNm", rs.getString(3));
        map.put("uppDepartId", rs.getString(4));
        map.put("departleader", rs.getString(5));
        if (!"1".equals(rs.getString(1))) {
          if (isOrgExists((new StringBuilder(String.valueOf(rs.getString(1)))).toString())) {
            updateDept(map);
            continue;
          } 
          addOrganization(map);
        } 
      } 
      System.out.println("组织同步完成！");
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
  }
  
  private void addOrganization(Map map) {
    OrganizationVO vo = new OrganizationVO();
    OrganizationBD bd = new OrganizationBD();
    String departid = (String)map.get("departid");
    String uppDepartId = (String)map.get("uppDepartId");
    String departNm = (String)map.get("departNm");
    String leaderId = (String)map.get("departleader");
    String empid = "", empname = "";
    long time = (new Date()).getTime();
    vo.setOrgName(departNm);
    vo.setOrgSerial((String)map.get("departNo"));
    vo.setOrgOrderCode(1);
    vo.setOrgType("1");
    vo.setGuid(departid);
    if (leaderId != null && !"null".equals(leaderId) && !"".equals(leaderId)) {
      Map map1 = getLeaderIdAndName(leaderId);
      if (map1 != null) {
        empid = "$" + map1.get("empId") + "$";
        empname = (new StringBuilder()).append(map1.get("empName")).append(",").toString();
      } 
    } 
    vo.setOrgManagerEmpId(empid);
    vo.setOrgManagerEmpName(empname);
    String parent = "0";
    if (uppDepartId != null && !"null".equals(uppDepartId) && !"".equals(uppDepartId))
      parent = getOrgIdByGUID(uppDepartId); 
    if (parent == null || "".equals(parent))
      parent = "0"; 
    vo.setOrgParentOrgId(Integer.valueOf(parent).intValue());
    vo.setDomainId("0");
    DateFormat dtm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String lastupdate = dtm.format(new Date());
    vo.setLastupdate(lastupdate);
    boolean flag = bd.add(vo, "1", parent, Integer.valueOf(1));
    if (flag) {
      System.out.println("部门" + departNm + "新增成功");
    } else {
      System.out.println("部门" + departNm + "新增失败");
    } 
  }
  
  private void updateDept(Map map) {
    OrganizationBD bd = new OrganizationBD();
    String guid = (String)map.get("departid");
    String uppDepartId = (String)map.get("uppDepartId");
    String departNm = (String)map.get("departNm");
    String leaderId = (String)map.get("departleader");
    String empid = "", empname = "";
    String id = getOrgIdByGUID(guid);
    long time = (new Date()).getTime();
    try {
      OrganizationVO vo = bd.getOrgByOrgId(id);
      vo.setOrgName((String)map.get("departNm"));
      vo.setOrgSerial((String)map.get("departNo"));
      vo.setOrgOrderCode(1);
      vo.setGuid(guid);
      vo.setOrgType("1");
      if (leaderId != null && !"null".equals(leaderId) && !"".equals(leaderId)) {
        Map map1 = getLeaderIdAndName(leaderId);
        if (map1 != null) {
          empid = "$" + map1.get("empId") + "$";
          empname = (new StringBuilder()).append(map1.get("empName")).append(",").toString();
        } 
      } 
      vo.setOrgManagerEmpId(empid);
      vo.setOrgManagerEmpName(empname);
      DateFormat dtm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String lastupdate = dtm.format(new Date());
      vo.setLastupdate(lastupdate);
      String parent = "0";
      if (uppDepartId != null && !"null".equals(uppDepartId) && !"".equals(uppDepartId))
        parent = getOrgIdByGUID(uppDepartId); 
      if (parent == null || "".equals(parent))
        parent = "0"; 
      vo.setOrgParentOrgId(Integer.valueOf(parent).intValue());
      boolean flag = bd.update(vo, "1", parent, Integer.valueOf(1), "1");
      if (flag) {
        System.out.println("部门" + departNm + "更新成功");
      } else {
        System.out.println("部门" + departNm + "更新失败");
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private void empSynchronize() {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    DataSourceBase base = new DataSourceBase();
    String sql = "select empid,empNm,empNo,nowDepartId,gender,postNm,innerEmail,phone,bornDt,empsort from vHEemployeeInfo where empsort is not null";
    String empNo = "";
    Map<String, String> map = new HashMap<String, String>();
    try {
      conn = base.getDataSource("jdbc/haier").getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        map.put("userId", rs.getString(1));
        map.put("empNm", rs.getString(2));
        map.put("empNo", rs.getString(3));
        map.put("DepartId", rs.getString(4));
        map.put("gender", rs.getString(5));
        map.put("postNm", rs.getString(6));
        map.put("innerEmail", rs.getString(7));
        map.put("phone", rs.getString(8));
        map.put("bornDt", rs.getString(9));
        map.put("empsort", rs.getString(10));
        if (!"1".equals(rs.getString(1))) {
          if (isEmployeeExists((new StringBuilder(String.valueOf(rs.getString(1)))).toString())) {
            updateEmp(map);
            continue;
          } 
          addEmp(map);
        } 
      } 
      System.out.println("用户同步完成！");
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
  }
  
  private void addEmp(Map map) {
    String userId = (String)map.get("userId");
    String empName = (String)map.get("empNm");
    EmployeeBD bd = new EmployeeBD();
    EmployeeVO vo = new EmployeeVO();
    vo.setUserAccounts((String)map.get("empNo"));
    vo.setEmpName((String)map.get("empNm"));
    vo.setGuid(userId);
    long Id = 0L;
    String postion = "";
    if (map.get("postNm") != null) {
      Id = empPositionInsert((String)map.get("postNm"));
      postion = (String)map.get("postNm");
    } 
    vo.setEmpPosition(postion);
    vo.setEmpPositionId(Long.valueOf(Id));
    Map<String, String> mapLeader = getLeaderByGuid(userId);
    String empLeaderId = (new StringBuilder(String.valueOf(mapLeader.get("empLeaderId")))).toString();
    String empLeaderName = (new StringBuilder(String.valueOf(mapLeader.get("empLeaderName")))).toString();
    if (empLeaderId != null && !"".equals(empLeaderId) && !"null".equals(empLeaderId)) {
      vo.setEmpLeaderId(empLeaderId);
      vo.setEmpLeaderName(empLeaderName);
    } 
    String sex = "0";
    if ("女".equals(map.get("gender")))
      sex = "1"; 
    vo.setEmpSex(Byte.valueOf(sex).byteValue());
    vo.setEmpNumber((String)map.get("empNo"));
    String innerEmail = "";
    if (map.get("innerEmail") != null)
      innerEmail = (String)map.get("innerEmail"); 
    vo.setEmpEmail(innerEmail);
    String phone = "";
    if (map.get("phone") != null)
      phone = (String)map.get("phone"); 
    vo.setEmpMobilePhone(phone);
    vo.setUserSimpleName((String)map.get("empNo"));
    String birthStr = (String)map.get("bornDt");
    DateFormat dtm = new SimpleDateFormat("yyyy-MM-dd");
    Date birth = null;
    try {
      if (!"".equals(birthStr) && birthStr != null && !"null".equals(birthStr)) {
        birth = dtm.parse(birthStr);
      } else {
        birthStr = dtm.format(new Date());
        birth = dtm.parse(birthStr);
      } 
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    vo.setEmpBirth(birth);
    vo.setBrowseRange("0");
    vo.setMailPost("0");
    vo.setSkin("blue");
    vo.setIsChangePwd("0");
    vo.setMailboxSize("100");
    vo.setNetDiskSize("150");
    vo.setImId((String)map.get("empNo"));
    vo.setGrantRange("0");
    vo.setEmpState("1");
    vo.setUserOrderCode("10000.0000");
    DateFormat dtmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String lastupdate = dtmt.format(new Date());
    vo.setLastupdate(lastupdate);
    String mwmm = "haier.123";
    vo.setUserPassword(mwmm);
    if (vo.getUserAccounts() != null && !"".equals(vo.getUserAccounts())) {
      byte userIsActive = 1;
      byte userIsDeleted = 0;
      Integer UserIsFormalUser = Integer.valueOf(1);
      if (map.get("empsort").toString().indexOf("离职") >= 0 || map.get("empsort").toString().indexOf("退休") >= 0)
        userIsActive = 0; 
      vo.setUserIsFormalUser(UserIsFormalUser);
      vo.setUserIsActive(userIsActive);
      vo.setUserIsDeleted(userIsDeleted);
    } else {
      byte userIsActive = 0;
      vo.setUserIsActive(userIsActive);
      vo.setUserIsFormalUser(Integer.valueOf("2"));
    } 
    byte isSuper = 1;
    vo.setUserIsSuper(isSuper);
    DateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
    try {
      vo.setUserSuperBegin(dt1.parse("2000-01-01"));
      vo.setUserSuperEnd(dt1.parse("9999-12-31"));
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    int flag = 0;
    vo.setDomainId("0");
    if (map.get("DepartId") != null && !"null".equals(map.get("DepartId")) && !"".equals(map.get("DepartId")))
      flag = bd.add(vo, getOrgIdByGUID((String)map.get("DepartId"))); 
    try {
      emailSet(userId);
    } catch (HibernateException e) {
      e.printStackTrace();
    } 
    if (flag == 0) {
      System.out.println("用户" + empName + "新增成功");
    } else if (flag == 1) {
      String empId = getEmpIdByGUID(userId, "0");
      bd.regain(empId);
      System.out.println("用户" + empName + "新增成功");
    } else {
      System.out.println("用户" + empName + "新增失败");
    } 
  }
  
  private void updateEmp(Map map) {
    int count = 0;
    String empNo = (String)map.get("empNo");
    String empName = (String)map.get("empNm");
    String userId = (String)map.get("userId");
    String id = getEmpIdByGUID(userId);
    NewEmployeeBD bd = new NewEmployeeBD();
    EmployeeVO vo = new EmployeeVO();
    List<Object[]> list = bd.selectSingle(Long.valueOf(id));
    if (list != null && list.size() >= 1) {
      Object[] object = list.get(0);
      vo = (EmployeeVO)object[0];
    } 
    vo.setUserAccounts(empNo);
    vo.setEmpName(empName);
    vo.setGuid(userId);
    long Id = 0L;
    String postion = "";
    if (map.get("postNm") != null) {
      Id = empPositionInsert((String)map.get("postNm"));
      postion = (String)map.get("postNm");
    } 
    vo.setEmpPosition(postion);
    vo.setEmpPositionId(Long.valueOf(Id));
    Map<String, String> mapLeader = getLeaderByGuid(userId);
    String empLeaderId = (new StringBuilder(String.valueOf(mapLeader.get("empLeaderId")))).toString();
    String empLeaderName = (new StringBuilder(String.valueOf(mapLeader.get("empLeaderName")))).toString();
    if (empLeaderId != null && !"".equals(empLeaderId) && !"null".equals(empLeaderId)) {
      vo.setEmpLeaderId(empLeaderId);
      vo.setEmpLeaderName(empLeaderName);
    } 
    String sex = "0";
    if ("女".equals(map.get("gender")))
      sex = "1"; 
    vo.setEmpSex(Byte.valueOf(sex).byteValue());
    vo.setEmpNumber(empNo);
    vo.setEmpEmail((String)map.get("innerEmail"));
    vo.setEmpMobilePhone((String)map.get("phone"));
    vo.setUserSimpleName((String)map.get("empNo"));
    String birthStr = (String)map.get("bornDt");
    vo.setEmpState("1");
    DateFormat dtm = new SimpleDateFormat("yyyy-MM-dd");
    Date birth = null;
    try {
      if (!"".equals(birthStr) && birthStr != null && !"null".equals(birthStr)) {
        birth = dtm.parse(birthStr);
      } else {
        birthStr = dtm.format(new Date());
        birth = dtm.parse(birthStr);
      } 
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    vo.setEmpBirth(birth);
    DateFormat dtmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String lastupdate = dtm.format(new Date());
    vo.setLastupdate(lastupdate);
    vo.setBrowseRange("0");
    vo.setMailPost("0");
    vo.setSkin("blue");
    vo.setIsChangePwd("0");
    vo.setMailboxSize("100");
    vo.setNetDiskSize("150");
    vo.setImId(empNo);
    vo.setGrantRange("0");
    vo.setUserOrderCode("10000");
    byte isSuper = 1;
    vo.setUserIsSuper(isSuper);
    DateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
    try {
      vo.setUserSuperBegin(dt1.parse("2000-01-01"));
      vo.setUserSuperEnd(dt1.parse("9999-12-31"));
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    if (vo.getUserAccounts() != null && !"".equals(vo.getUserAccounts())) {
      byte userIsActive = 1;
      Integer UserIsFormalUser = Integer.valueOf(1);
      byte userIsDeleted = 0;
      if (map.get("empsort").toString().indexOf("离职") >= 0 || map.get("empsort").toString().indexOf("退休") >= 0)
        userIsActive = 0; 
      vo.setUserIsFormalUser(UserIsFormalUser);
      vo.setUserIsActive(userIsActive);
      vo.setUserIsDeleted(userIsDeleted);
    } else {
      byte userIsActive = 0;
      vo.setUserIsActive(userIsActive);
      vo.setUserIsFormalUser(Integer.valueOf("2"));
    } 
    vo.setDomainId("0");
    vo.setEmpId(Long.valueOf(getEmpIdByGUID(userId)).longValue());
    if (map.get("DepartId") != null && !"null".equals(map.get("DepartId")) && !"".equals(map.get("DepartId")))
      count = bd.update(vo, new EmployeeOtherInfoVO(), getOrgIdByGUID((String)map.get("DepartId")), Long.valueOf(vo.getEmpId())); 
    try {
      emailSet(userId);
    } catch (HibernateException e) {
      e.printStackTrace();
    } 
    if (count == 0) {
      System.out.println("用户" + empName + "更新成功");
    } else {
      System.out.println("用户" + empName + "更新失败");
    } 
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
  
  private boolean isEmployeeExists(String uuid) {
    boolean result = false;
    DbOpt db = new DbOpt();
    try {
      String count = db
        .executeQueryToStrPS("select count(0) num  from org_Employee where guid = ?", new String[] { uuid });
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
  
  private String getEmpIdByGUID(String guid) {
    String result = "";
    DbOpt db = new DbOpt();
    try {
      result = db
        .executeQueryToStrPS("select max(emp_id) emp_id from org_employee where guid = ?", new String[] { guid });
      db.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  private Map getLeaderIdAndName(String guid) {
    Map<String, String> map = new HashMap<String, String>();
    String sql = "SELECT t.emp_id,t.EMPNAME FROM org_employee t WHERE t.guid=?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Connection conn = null;
    DataSourceBase base = new DataSourceBase();
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, guid);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        map.put("empId", rs.getString(1));
        map.put("empName", rs.getString(2));
      } 
      rs.close();
      pstmt.close();
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
    return map;
  }
  
  private long empPositionInsert(String position) {
    String Id = "";
    String sql = "SELECT ID FROM ST_STATION WHERE STATION_NAME=?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Connection conn = null;
    DataSourceBase base = new DataSourceBase();
    try {
      begin();
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
        long rightScopeSeq = getTableId().longValue();
        sql = "insert into ST_STATION(ID,STATION_NAME,DOMAIN_ID,description,corpId,NO) values(?,?,0,?,0,?)";
        pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, rightScopeSeq);
        pstmt.setString(2, position);
        pstmt.setString(3, "");
        pstmt.setString(4, "gw" + time);
        pstmt.executeUpdate();
        pstmt.close();
        sql = "SELECT ID FROM ST_STATION WHERE STATION_NAME=?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, position);
        rs = pstmt.executeQuery();
        if (rs.next())
          Id = rs.getString(1); 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        if (pstmt != null)
          pstmt.close(); 
        if (conn != null)
          conn.close(); 
        this.session.close();
        this.session = null;
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return Long.valueOf(Id).longValue();
  }
  
  private void emailSet(String guid) throws HibernateException {
    String sql1 = "select type from sys_remind_type";
    String sql2 = "insert into sys_remind_set(id,emp_id,status,type) values(?,?,?,?)";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Connection conn = null;
    DataSourceBase base = new DataSourceBase();
    String empid = getEmpIdByGUID(guid, "1");
    String type = "";
    if (!"".equals(empid) && !"null".equals(empid) && empid != null && 
      checkEmailSet(empid))
      try {
        begin();
        conn = base.getDataSource().getConnection();
        conn.setAutoCommit(false);
        pstmt = conn.prepareStatement(sql1);
        rs = pstmt.executeQuery();
        pstmt = conn.prepareStatement(sql2);
        while (rs.next()) {
          String status = "$01$$03$";
          type = rs.getString(1);
          if ("Email".equals(type) || "document".equals(type) || "KqOvertime".equals(type))
            status = "-1"; 
          long rightScopeSeq = getTableId().longValue();
          pstmt.setLong(1, rightScopeSeq);
          pstmt.setString(2, empid);
          pstmt.setString(3, status);
          pstmt.setString(4, type);
          pstmt.addBatch();
        } 
        int[] count = pstmt.executeBatch();
        conn.commit();
        pstmt.close();
        rs.close();
        conn.close();
        this.session.close();
        this.session = null;
      } catch (Exception e) {
        try {
          if (pstmt != null)
            pstmt.close(); 
          if (rs != null)
            rs.close(); 
          if (conn != null)
            conn.close(); 
        } catch (SQLException e1) {
          e1.printStackTrace();
        } 
        e.printStackTrace();
      }  
  }
  
  private boolean checkEmailSet(String empid) {
    boolean flag = true;
    String sql = "select count(*) from sys_remind_set  where emp_id=?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Connection conn = null;
    DataSourceBase base = new DataSourceBase();
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, empid);
      rs = pstmt.executeQuery();
      if (rs.next() && 
        rs.getInt(1) > 0)
        flag = false; 
      pstmt.close();
      rs.close();
      conn.close();
    } catch (SQLException e) {
      try {
        if (pstmt != null)
          pstmt.close(); 
        if (rs != null)
          rs.close(); 
        if (conn != null)
          conn.close(); 
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return flag;
  }
  
  private Map<String, String> getLeaderByGuid(String guid) {
    Map<String, String> map = new HashMap<String, String>();
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Connection conn = null;
    String sql = "SELECT a.emp_id,a.empLeaderId,a.empLeaderName,c.orgmanagerempid,c.orgmanagerempname,c.ORGIDSTRING,c.org_id FROM ORG_EMPLOYEE a LEFT JOIN org_organization_user b ON a.emp_id=b.emp_id LEFT JOIN org_organization c ON b.org_id=c.org_id  WHERE a.guid=?";
    String empLeaderId = "", emp_id = "", orgidstring = "", orgid = "";
    DataSourceBase base = new DataSourceBase();
    String orgStr = "";
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, guid);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        emp_id = rs.getString(1);
        empLeaderId = rs.getString(2);
        orgidstring = rs.getString(6);
        orgid = rs.getString(7);
      } 
      rs.close();
      pstmt.close();
      conn.close();
      if ("".equals(empLeaderId) || "null".equals(empLeaderId) || empLeaderId == null) {
        if (orgidstring != null && !"".equals(orgidstring) && !"null".equals(orgidstring)) {
          orgidstring = orgidstring.replace("$", ",");
          String[] orgArry = orgidstring.split(",");
          if (orgArry.length > 0)
            for (int i = orgArry.length - 1; i < orgArry.length && i >= 0; i--) {
              if (!"".equals(orgArry[i]) && orgArry[i].indexOf("_") < 0 && orgArry[i].indexOf("-") < 0)
                orgStr = String.valueOf(orgStr) + orgArry[i] + ","; 
            }  
        } 
        if (!"".equals(orgStr)) {
          String[] orgStrArr = orgStr.split(",");
          for (int i = 0; i < orgStrArr.length; i++) {
            Map<String, String> mapManage = getOrgManageleaderByOrgId(orgStrArr[i]);
            String orgMempid = mapManage.get("orgmanagerempid");
            String orgmanagereName = mapManage.get("orgmanagereName");
            if (!"".equals(orgMempid) && !"null".equals(orgMempid) && orgMempid != null)
              if (orgMempid.indexOf(emp_id) >= 0) {
                map.put("empLeaderId", orgMempid);
                map.put("empLeaderName", orgmanagereName);
              } else {
                map.put("empLeaderId", orgMempid);
                map.put("empLeaderName", orgmanagereName);
                break;
              }  
          } 
        } 
      } 
    } catch (SQLException e) {
      try {
        if (pstmt != null)
          pstmt.close(); 
        if (rs != null)
          rs.close(); 
        if (conn != null)
          conn.close(); 
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return map;
  }
  
  private Map<String, String> getOrgManageleaderByOrgId(String orgid) {
    Map<String, String> map = new HashMap<String, String>();
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Connection conn = null;
    String sql = "SELECT orgmanagerempid,orgmanagerempname FROM org_organization WHERE org_id=?";
    DataSourceBase base = new DataSourceBase();
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, orgid);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        map.put("orgmanagerempid", rs.getString(1));
        map.put("orgmanagereName", rs.getString(2));
      } 
      pstmt.close();
      rs.close();
      conn.close();
    } catch (SQLException e) {
      try {
        if (pstmt != null)
          pstmt.close(); 
        if (rs != null)
          rs.close(); 
        if (conn != null)
          conn.close(); 
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return map;
  }
}
