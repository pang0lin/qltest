package rtx;

import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

public class TransRTX {
  public void Sync() {
    System.out.println("**************Dept sync begin*****************");
    Connection conn = null;
    Connection conn2 = null;
    Statement stmt = null;
    Statement stmt2 = null;
    ResultSet allrs = null;
    ResultSet maxRtxDRs = null;
    try {
      DataSource ds = (new DataSourceBase()).getDataSource();
      conn = ds.getConnection();
      conn2 = ds.getConnection();
      stmt = conn.createStatement();
      stmt2 = conn2.createStatement();
      RTXSvrApi rtxSvr = new RTXSvrApi();
      if (rtxSvr.Init()) {
        int maxRtxDId = 0;
        Map<Long, Integer> rtxMap = new HashMap<Long, Integer>();
        rtxMap.put(Long.valueOf(0L), Integer.valueOf(0));
        String deptName = "";
        String deptInfo = "";
        long deptId = 0L;
        long parentId = 0L;
        int rtxDId = 0;
        int rtxPDId = -1;
        int temp = -1;
        int status = -1;
        maxRtxDRs = stmt.executeQuery("select max(RTXDEPTID) from org_organization");
        if (maxRtxDRs.next())
          maxRtxDId = maxRtxDRs.getInt(1); 
        maxRtxDRs.close();
        stmt.close();
        stmt = conn.createStatement();
        allrs = stmt.executeQuery("select ORG_ID,ORGPARENTORGID,RTXDEPTID,RTXDEPTPID,ORGNAME,ORGDESCRIPTE,ORGSTATUS from org_organization where ORGPARENTORGID<>-1 order by ORGLEVEL,ORGIDSTRING");
        while (allrs.next()) {
          RTXDeptVO rtxDept = new RTXDeptVO();
          deptId = allrs.getLong(1);
          parentId = allrs.getLong(2);
          rtxDId = allrs.getInt(3);
          rtxDId = (Integer.valueOf(rtxDId) == null) ? 0 : rtxDId;
          rtxPDId = allrs.getInt(4);
          rtxPDId = (Integer.valueOf(rtxPDId) == null) ? 0 : rtxPDId;
          deptName = allrs.getString(5);
          deptName = (deptName == null) ? "" : deptName;
          deptInfo = allrs.getString(6);
          deptInfo = (deptInfo == null) ? "" : deptInfo;
          rtxDept.setDeptName(deptName);
          rtxDept.setDeptInfo(deptInfo);
          status = allrs.getInt(7);
          if (status != 0) {
            if (rtxDId != 0 && rtxSvr.deptIsExist(String.valueOf(rtxDId)) != 0) {
              if (rtxSvr.deleteDept(String.valueOf(rtxDId), "1") == 0) {
                System.out.println("部门RTX同步(删除)成功:DEPTID=" + deptId + ",rtxDId=" + rtxDId);
              } else {
                System.out.println("部门RTX同步(删除)失败:DEPTID=" + deptId + ",rtxDId=" + rtxDId);
              } 
            } else {
              System.out.println("部门信息未创建:DEPTID=" + deptId + ",rtxDId=" + rtxDId);
            } 
          } else if (rtxDId != 0 && rtxSvr.deptIsExist(String.valueOf(rtxDId)) != 0) {
            rtxMap.put(Long.valueOf(deptId), Integer.valueOf(rtxDId));
            temp = ((Integer)rtxMap.get(Long.valueOf(parentId))).intValue();
            if (rtxPDId != temp) {
              rtxPDId = temp;
              temp = 0;
              stmt2.executeUpdate(getUpdateSql(rtxDId, rtxPDId, deptId));
              System.out.println("数据更新成功:DEPTID=" + deptId + ",rtxDId=" + rtxDId);
            } 
            rtxDept.setDeptId(String.valueOf(rtxDId));
            rtxDept.setDeptParentId(String.valueOf(rtxPDId));
            if (rtxSvr.saveDept(rtxDept) == 0) {
              System.out.println("部门RTX同步(修改)成功:DEPTID=" + deptId + ",rtxDId=" + rtxDId);
            } else {
              System.out.println("部门RTX同步失败:DEPTID=" + deptId + ",rtxDId=" + rtxDId);
            } 
          } else {
            rtxDId = maxRtxDId + 1;
            if (parentId == 0L) {
              rtxPDId = 0;
            } else {
              rtxPDId = ((Integer)rtxMap.get(Long.valueOf(parentId))).intValue();
            } 
            rtxDept.setDeptId(String.valueOf(rtxDId));
            rtxDept.setDeptParentId(String.valueOf(rtxPDId));
            if (rtxSvr.saveDept(rtxDept) == 0) {
              maxRtxDId = rtxDId;
              rtxMap.put(Long.valueOf(deptId), Integer.valueOf(rtxDId));
              stmt2.executeUpdate(getUpdateSql(rtxDId, rtxPDId, deptId));
              System.out.println("部门RTX同步(添加)成功,数据更新成功:DEPTID=" + deptId + ",rtxDId=" + rtxDId);
            } else {
              System.out.println("部门RTX同步(添加)失败,DEPTID=" + deptId + ",rtxDId=" + rtxDId);
            } 
          } 
          deptId = 0L;
          parentId = 0L;
          rtxDId = 0;
          rtxPDId = -1;
          deptName = null;
          deptInfo = null;
          rtxDept = null;
        } 
        stmt2.close();
        allrs.close();
        stmt.close();
      } 
      rtxSvr.UnInit();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (conn2 != null)
          conn2.close(); 
        if (conn != null)
          conn.close(); 
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    System.out.println("**************Dept syn end*****************");
  }
  
  private String getUpdateSql(int rtxDeptId, int rtxDeptPId, long orgId) {
    StringBuffer sql = new StringBuffer("update org_organization set rtxDeptId=");
    sql.append(rtxDeptId);
    sql.append(", rtxDeptPId=");
    sql.append(rtxDeptPId);
    sql.append(" where org_id=");
    sql.append(orgId);
    return sql.toString();
  }
  
  public void transUsers() {
    System.out.println("**************User sync begin*****************");
    Connection conn = null;
    Connection conn2 = null;
    Statement stmt = null;
    Statement stmt2 = null;
    ResultSet rs = null;
    Map<Long, Integer[]> deptMap = (Map)new HashMap<Long, Integer>();
    Integer[] ints = (Integer[])null;
    Long deptId = null;
    StringBuffer sqlBuffer = null;
    int sum = 0;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      conn2 = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      stmt2 = conn2.createStatement();
      rs = stmt.executeQuery("select ORG_ID,RTXDEPTID,ORGSTATUS from org_organization where ORGPARENTORGID<>-1 order by ORGLEVEL,ORGIDSTRING");
      RTXSvrApi rtxSvr = new RTXSvrApi();
      if (rtxSvr.Init()) {
        while (rs.next()) {
          deptId = Long.valueOf(rs.getLong(1));
          ints = new Integer[2];
          ints[0] = Integer.valueOf(rs.getInt(2));
          ints[1] = Integer.valueOf(rs.getInt(3));
          deptMap.put(deptId, ints);
          deptId = null;
          ints = (Integer[])null;
        } 
        rs.close();
        rs = null;
        stmt.close();
        stmt = null;
        sqlBuffer = new StringBuffer("select emp.emp_id,emp.userAccounts,emp.userpassword,org.org_id,emp.userisdeleted,emp.IMID,emp.empName,emp.empAddress,emp.empBloodType,emp.empCountry,emp.empCounty,emp.empEmail,emp.empGnome,emp.empDuty,emp.empZipCode,emp.empNativePlace,emp.empSex,emp.empBirth,emp.empMobilePhone,empbusinessphone from org_employee emp,org_organization_user org,org_organization org2 where org.emp_id=emp.emp_id and org.org_id=org2.org_id and emp.userAccounts is not null and (userisdeleted=0 or userisdeleted=1)");
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sqlBuffer.toString());
        while (rs.next()) {
          sum++;
          Long empId = Long.valueOf(rs.getLong(1));
          String account = rs.getString(2);
          if (account == null || "".equals(account))
            continue; 
          String pwd = rs.getString(3);
          deptId = Long.valueOf(rs.getLong(4));
          String isDeleted = rs.getString(5);
          String IMId = rs.getString(6);
          IMId = (IMId == null) ? "" : IMId;
          if ("1".equals(isDeleted) || ((Integer[])deptMap.get(deptId))[1].intValue() == 4) {
            if (IMId != null && !"".equals(IMId) && !"null".equals(IMId)) {
              if (rtxSvr.userIsExist(IMId) == 0) {
                if (rtxSvr.deleteUser(account) == 0) {
                  System.out.println("用户RTX同步(删除)成功,ACCOUNT:" + account + ",DEPTID:" + ((Integer[])deptMap.get(deptId))[0]);
                  stmt2.executeUpdate("update org_employee set IMID='' where emp_id=" + empId);
                  continue;
                } 
                System.out.println("用户RTX同步(删除)失败,ACCOUNT:" + account + ",DEPTID:" + ((Integer[])deptMap.get(deptId))[0]);
                continue;
              } 
              System.out.println("用户已被RTX端删除：ACCOUNT:" + account + ",DEPTID:" + ((Integer[])deptMap.get(deptId))[0]);
              stmt2.executeUpdate("update org_employee set IMID='' where emp_id=" + empId);
              continue;
            } 
            System.out.println("用户未初始化：ACCOUNT:" + account + ",DEPTID:" + ((Integer[])deptMap.get(deptId))[0]);
            continue;
          } 
          if ("0".equals(isDeleted)) {
            String empName = rs.getString("empName");
            String sex = rs.getString("empSex");
            String birthday = rs.getString("empBirth");
            String empAddress = rs.getString("empAddress");
            String bloodType = rs.getString("empBloodType");
            String country = rs.getString("empCountry");
            String city = rs.getString("empCounty");
            String email = rs.getString("empEmail");
            String memo = rs.getString("empGnome");
            String position = rs.getString("empDuty");
            String postCode = rs.getString("empZipCode");
            String province = rs.getString("empNativePlace");
            String empMobilePhone = rs.getString("empMobilePhone");
            String empPhone = rs.getString("empbusinessphone");
            RTXUserVO rtxUser = new RTXUserVO();
            rtxUser.setNick(account);
            rtxUser.setPassword(pwd);
            rtxUser.setSex(sex);
            if (birthday != null && !"".equals(birthday.trim()) && !"null".equals(birthday))
              rtxUser.setBirthday(birthday.substring(0, birthday.indexOf(" ")).replace("-", "")); 
            rtxUser.setDept(String.valueOf(((Integer[])deptMap.get(deptId))[0]));
            rtxUser.setName(empName);
            rtxUser.setAddress(empAddress);
            rtxUser.setBloodType(bloodType);
            rtxUser.setCity(city);
            rtxUser.setCountry(country);
            rtxUser.setEmail(email);
            rtxUser.setMemo(memo);
            rtxUser.setPosition(position);
            rtxUser.setPostcode(postCode);
            rtxUser.setProvince(province);
            rtxUser.setMobile(empMobilePhone);
            rtxUser.setPhone(empPhone);
            if (rtxSvr.userIsExist(IMId) == 0) {
              if (rtxSvr.updateUser(rtxUser) == 0) {
                System.out.println("用户RTX同步(基本信息修改)成功,ACCOUNT:" + account + ",DEPTID:" + ((Integer[])deptMap.get(deptId))[0]);
              } else {
                System.out.println("用户RTX同步(基本信息修改)失败,ACCOUNT:" + account + ",DEPTID:" + ((Integer[])deptMap.get(deptId))[0]);
              } 
            } else {
              stmt2.executeUpdate("update org_employee set IMID='" + account + "' where emp_id=" + empId);
              if (rtxSvr.addUser(rtxUser) == 0) {
                System.out.println("用户RTX同步(基本信息添加)成功,ACCOUNT:" + account + ",DEPTID:" + ((Integer[])deptMap.get(deptId))[0]);
              } else {
                System.out.println("用户RTX同步(基本信息添加)失败,ACCOUNT:" + account + ",DEPTID:" + ((Integer[])deptMap.get(deptId))[0]);
              } 
            } 
            if (rtxSvr.setUserDetailInfo(rtxUser) == 0) {
              System.out.println("用户RTX同步(详细信息修改)成功,ACCOUNT:" + account + ",DEPTID:" + ((Integer[])deptMap.get(deptId))[0]);
              continue;
            } 
            System.out.println("用户RTX同步(详细信息修改)失败,ACCOUNT:" + account + ",DEPTID:" + ((Integer[])deptMap.get(deptId))[0]);
          } 
        } 
      } 
      rtxSvr.UnInit();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        if (stmt != null)
          stmt.close(); 
        if (conn != null)
          conn.close(); 
        if (stmt != null)
          stmt2.close(); 
        if (conn2 != null)
          conn2.close(); 
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    System.out.println("sum:" + sum);
    System.out.println("**************User sync end*****************");
  }
}
