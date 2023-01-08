package com.js.system.util;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class OperateUserFromBase {
  private static Map<String, String[]> fieldUserMap = null;
  
  private static Map<String, String[]> fieldOrgMap = null;
  
  private Map<String, String> dutyMap = null;
  
  private static String syncStatus = "0";
  
  public String userAndOrg() {
    return userAndOrg("1");
  }
  
  public String userAndOrg(String dataType) {
    if ("1".equals(syncStatus))
      return "2"; 
    return getUserInfoFromOtherBase(Integer.parseInt(dataType));
  }
  
  public String getUserInfoFromOtherBase() {
    return getUserInfoFromOtherBase(1);
  }
  
  public String getUserInfoFromOtherBase(int syncType) {
    syncDutyInfo();
    String sql = "select xykh,xb,xm,yxh,dw,zwmc from (select max(xykh) xykh,max(xm) xm,max(xb) xb,yxh,max(dw) dw,max(zwmc) as zwmc from usr_gxsj.V_JS_RYXX_NEW1 group by yxh having count(yxh)=1) where (xb='男' or xb='女') and yxh is not null and xykh is not null and (dw<>'研究生.' and  dw<>'本科生.' and dw<>'兰州大学.' ";
    if (syncType == 1) {
      sql = String.valueOf(sql) + "and zwmc<>'本科生' and zwmc<>'研究生'";
    } else {
      sql = String.valueOf(sql) + "and (zwmc='本科生' or zwmc='研究生')";
    } 
    sql = String.valueOf(sql) + ") order by xykh";
    fieldUserMap = readFormXML("userField");
    String[] dataSource = fieldUserMap.get("dataSource");
    return getUserInfoFromOtherBase(dataSource[0], sql, Integer.valueOf(dataSource[3]).intValue(), dataSource[2], dataSource[4], dataSource[5]);
  }
  
  public String getUserInfoFromOtherBase(String baseName, String sql) {
    fieldUserMap = readFormXML("userField");
    String[] dataSource = fieldUserMap.get("dataSource");
    return getUserInfoFromOtherBase(baseName, sql, 100, dataSource[2], dataSource[4], dataSource[5]);
  }
  
  public String getUserInfoFromOtherBase(String baseName, String sql, int onceNum, String updateOrg, String notDel, String defaultOrg) {
    if ("1".equals(syncStatus))
      return "2"; 
    syncStatus = "1";
    boolean flag = false;
    DataSourceBase base = new DataSourceBase();
    Connection connLocal = null;
    PreparedStatement psLocal = null;
    Statement statLocal = null;
    Connection conn = null;
    Statement stat = null;
    ResultSet rs = null;
    String dataType = SystemCommon.getDatabaseType();
    String empNumber = "", empName = "", userAccounts = "", orgName = "", orgId = "-1", empStatus = "1", empDuty = "", hrEmpDuty = "", empDutyLevel = "1000";
    int empSex = 0;
    Map<String, Map> orgMaps = OperateUser.getOrgSimpleInfo();
    Map<String, String> twoLevelMap = orgMaps.get("twoLevelMap");
    Map<String, String> twoLevelIdAndNameMap = orgMaps.get("twoLevelIdAndNameMap");
    Map<String, String> thirdLevelMap = orgMaps.get("thirdLevelMap");
    Map<String, String> orgStatusMap = orgMaps.get("orgStatusMap");
    Iterator<Map.Entry> it = twoLevelMap.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry entry = it.next();
      Object key = entry.getKey();
      System.out.println("two:orgName:" + key.toString() + "   val:" + (String)twoLevelMap.get(key));
    } 
    it = thirdLevelMap.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry entry = it.next();
      Object key = entry.getKey();
      System.out.println("third:orgName:" + key.toString() + "   val:" + (String)thirdLevelMap.get(key));
    } 
    it = twoLevelIdAndNameMap.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry entry = it.next();
      Object key = entry.getKey();
      System.out.println("two:orgId:" + key.toString() + "   val:" + (String)twoLevelIdAndNameMap.get(key));
    } 
    int num = 0, accountsIsNull = 0, exitsNum = 0;
    long beginTime = System.currentTimeMillis();
    try {
      Map<String, Integer> map = new HashMap<String, Integer>();
      boolean exits = false;
      conn = base.getDataSource(baseName).getConnection();
      stat = conn.createStatement();
      connLocal = base.getDataSource().getConnection();
      connLocal.setAutoCommit(false);
      System.out.println("人员同步执行sql：" + sql);
      rs = stat.executeQuery(sql);
      if (dataType.indexOf("oracle") >= 0) {
        psLocal = connLocal.prepareStatement("insert into temp_emp(empName,userAccounts,userSimpleName,empstatus,userPassword,empSex,empHeight,empIsMarriage,empState,empWeight,userIsActive,userIsDeleted,userIsFormalUser,createdOrg,domain_id,userordercode,keyvalidate,userIsSuper,usersuperBegin,usersuperEnd,empnumber,empduty,org_id,empdutylevel) values(?,?,?,0,'5EB72F96E795C92A549DD5A330112621896O',?,0,0,0,0,?,0,1,0,0,10000,0,1,to_date('2014-01-01 00:00:00','yyyy-mm-dd hh24:mi:ss'),to_date('2020-01-01 00:00:00','yyyy-mm-dd hh24:mi:ss'),?,?,?,?)");
      } else {
        psLocal = connLocal.prepareStatement("insert into temp_emp(empName,userAccounts,userSimpleName,empstatus,userPassword,empSex,empHeight,empIsMarriage,empState,empWeight,userIsActive,userIsDeleted,userIsFormalUser,createdOrg,domain_id,userordercode,keyvalidate,userIsSuper,usersuperBegin,usersuperEnd,empnumber,empduty,org_id,empdutylevel) values(?,?,?,0,'5EB72F96E795C92A549DD5A330112621896O',?,0,0,0,0,?,0,1,0,0,10000,0,1,'2014-01-01 00:00:00','2020-01-01 00:00:00',?,?,?,?)");
      } 
      while (rs.next()) {
        empNumber = rs.getString(1);
        empSex = (rs.getString(2) == null || "".equals(rs.getString(2))) ? 0 : ("男".equals(rs.getString(2)) ? 0 : 1);
        empName = rs.getString(3);
        userAccounts = rs.getString(4);
        orgName = rs.getString(5);
        empDuty = rs.getString(6);
        System.out.println("1userAccounts:" + userAccounts);
        System.out.println("orgName:" + orgName);
        System.out.println("orgId:" + orgId);
        System.out.println("empName:" + empName);
        System.out.println("empNumber:" + empNumber);
        if (userAccounts == null || "".equals(userAccounts)) {
          if (empNumber == null || "".equals(empNumber)) {
            accountsIsNull++;
            System.out.println("用户" + empName + "账户信息不存在...");
            continue;
          } 
          userAccounts = empNumber;
        } 
        exits = (map.get(userAccounts) == null || ((Integer)map.get(userAccounts)).intValue() == 0);
        if (exits) {
          num++;
          map.put(userAccounts, Integer.valueOf(num));
        } else {
          exitsNum++;
          System.out.println("用户" + empName + "用户名重复" + userAccounts);
          continue;
        } 
        if (!"".equals(orgName)) {
          if (twoLevelMap.get(orgName) == null) {
            if (orgName.startsWith("本科生.")) {
              orgName = "兰州大学.本科生.其他部门";
            } else if (orgName.startsWith("研究生.")) {
              orgName = "兰州大学.研究生.其他部门";
            } else {
              orgName = defaultOrg;
            } 
            orgId = twoLevelMap.get(orgName);
          } else {
            orgId = twoLevelMap.get(orgName);
            if (!userOrgChanged(userAccounts, orgName, orgId, thirdLevelMap, twoLevelIdAndNameMap, connLocal))
              orgId = "-1"; 
          } 
          if ("1".equals(orgStatusMap.get(orgId))) {
            empStatus = "0";
          } else {
            empStatus = "1";
          } 
        } 
        if (orgId == null || "null".equals(orgId) || "".equals(orgId)) {
          System.out.println("orgName:" + orgName + " 在OA中不存在！");
          continue;
        } 
        if (orgName.contains(".本科生.") || orgName.contains("本科生.")) {
          empDuty = "本科生";
          empDutyLevel = "1000";
        } else if (orgName.contains(".研究生.") || orgName.contains("研究生.")) {
          empDuty = "研究生";
          empDutyLevel = "1000";
        } else if (empDuty == null || "".equals(empDuty)) {
          empDuty = "";
          empDutyLevel = "1000";
        } else {
          empDutyLevel = this.dutyMap.get(empDuty);
        } 
        if (empDutyLevel == null || "".equals(empDutyLevel))
          empDutyLevel = "1000"; 
        System.out.println("OA ORGID:" + orgId);
        psLocal.setString(1, empName);
        psLocal.setString(2, userAccounts);
        psLocal.setString(3, empName);
        psLocal.setInt(4, empSex);
        psLocal.setString(5, empStatus);
        psLocal.setString(6, empNumber);
        psLocal.setString(7, empDuty);
        psLocal.setLong(8, Long.parseLong(orgId));
        psLocal.setString(9, empDutyLevel);
        psLocal.addBatch();
        if (num % 1000 == 0) {
          psLocal.executeBatch();
          connLocal.commit();
        } 
      } 
      if (num % 1000 > 0) {
        psLocal.executeBatch();
        connLocal.commit();
      } 
      System.out.println(String.valueOf((System.currentTimeMillis() - beginTime) / 1000L) + "s，获取人员信息完成。");
      String updateSQL = "UPDATE org_employee emp SET (emp.empName,emp.userAccounts,emp.userSimpleName,emp.empSex,emp.empState,emp.empnumber,emp.mailPost,empduty,empdutylevel)=(SELECT distinct a.empName,a.userAccounts,a.userSimpleName,a.empSex,a.empState,a.empnumber,1,empduty,empdutylevel FROM temp_emp a WHERE a.useraccounts=emp.useraccounts) where exists (select 1 from temp_emp tb where tb.useraccounts=emp.useraccounts and emp.userisdeleted=0)";
      System.out.println("更新已经存在的信息:" + updateSQL);
      psLocal = connLocal.prepareStatement(updateSQL);
      int updateNum = psLocal.executeUpdate();
      connLocal.commit();
      System.out.println(String.valueOf((System.currentTimeMillis() - beginTime) / 1000L) + "s，" + updateNum + "条人员基本信息更新完成。");
      String insertSQL = "";
      if (dataType.indexOf("oracle") >= 0) {
        insertSQL = "insert into org_employee(emp_id,empName,userAccounts,userSimpleName,empstatus,userPassword,empSex,empHeight,empIsMarriage,empState,empWeight,userIsActive,userIsDeleted,userIsFormalUser,createdOrg,domain_id,userordercode,keyvalidate,userIsSuper,usersuperBegin,usersuperEnd,empnumber,empduty,mailPost,empdutylevel) select hibernate_sequence.nextVal,empName,userAccounts,userSimpleName,empstatus,userPassword,empSex,empHeight,empIsMarriage,empState,empWeight,userIsActive,userIsDeleted,userIsFormalUser,createdOrg,domain_id,userordercode,keyvalidate,userIsSuper,usersuperBegin,usersuperEnd,empnumber,empduty,1,empdutylevel from temp_emp temp where not exists (select 1 from org_employee emp where (emp.useraccounts=temp.useraccounts or emp.useraccounts=temp.empnumber or  emp.empnumber=temp.useraccounts) and emp.userisdeleted=0)";
      } else {
        insertSQL = "insert into org_employee(empName,userAccounts,userSimpleName,empstatus,userPassword,empSex,empHeight,empIsMarriage,empState,empWeight,userIsActive,userIsDeleted,userIsFormalUser,createdOrg,domain_id,userordercode,keyvalidate,userIsSuper,usersuperBegin,usersuperEnd,empnumber,empduty,mailPost,empdutylevel) select empName,userAccounts,userSimpleName,empstatus,userPassword,empSex,empHeight,empIsMarriage,empState,empWeight,userIsActive,userIsDeleted,userIsFormalUser,createdOrg,domain_id,userordercode,keyvalidate,userIsSuper,usersuperBegin,usersuperEnd,empnumber,empduty,1,empdutylevel from temp_emp temp where not exists (select 1 from org_employee emp where (emp.useraccounts=temp.useraccounts or emp.useraccounts=temp.empnumber or emp.empnumber=temp.useraccounts) and emp.userisdeleted=0)";
      } 
      System.out.println("添加不存在的信息:" + insertSQL);
      psLocal = connLocal.prepareStatement(insertSQL);
      int insetNum = psLocal.executeUpdate();
      connLocal.commit();
      System.out.println(String.valueOf((System.currentTimeMillis() - beginTime) / 1000L) + "s，" + insetNum + "条人员信息添加完成。");
      String insertOrgUserSQL = "insert into org_organization_user(emp_id,org_id) select distinct emp_id,a.org_id from org_employee emp,temp_emp a where a.useraccounts=emp.useraccounts and emp_id not in(select emp_id from org_organization_user)";
      System.out.println("添加新增人员的组织对应关系:" + insertOrgUserSQL);
      psLocal = connLocal.prepareStatement(insertOrgUserSQL);
      int insertOrgNum = psLocal.executeUpdate();
      connLocal.commit();
      System.out.println(String.valueOf((System.currentTimeMillis() - beginTime) / 1000L) + "s，" + insertOrgNum + "个人员组织关系添加完成。");
      String updateOrgUserSQL = "update org_organization_user orguser set org_id=(select distinct org_id from temp_emp a,org_employee emp where a.useraccounts=emp.useraccounts and emp.emp_id=orguser.emp_id) where orguser.emp_id in (select emp.emp_id from org_employee emp,temp_emp temp where temp.org_id<>-1 and temp.useraccounts=emp.useraccounts and emp.userisactive=1 and emp.userisdeleted=0)";
      System.out.println(" 更新人员组织对应关系:" + updateOrgUserSQL);
      psLocal = connLocal.prepareStatement(updateOrgUserSQL);
      int updateOrgNum = psLocal.executeUpdate();
      connLocal.commit();
      System.out.println(String.valueOf((System.currentTimeMillis() - beginTime) / 1000L) + "s，" + updateOrgNum + "个人员组织关系更新完成。");
      flag = true;
    } catch (Exception e) {
      e.printStackTrace();
      try {
        connLocal.rollback();
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
      flag = false;
    } finally {
      try {
        String deleteSQL = "truncate table temp_emp";
        statLocal = connLocal.createStatement();
        statLocal.executeUpdate(deleteSQL);
        connLocal.commit();
        System.out.println(String.valueOf((System.currentTimeMillis() - beginTime) / 1000L) + "s，临时表数据已删除。");
        if (rs != null)
          rs.close(); 
        if (stat != null)
          stat.close(); 
        if (conn != null)
          conn.close(); 
        if (statLocal != null)
          statLocal.close(); 
        if (psLocal != null)
          psLocal.close(); 
        if (connLocal != null)
          connLocal.close(); 
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    System.out.println("本次成功同步" + num + "人，失败：登录名为空" + accountsIsNull + 
        "人，重复人数" + exitsNum + "，耗时：" + ((System.currentTimeMillis() - beginTime) / 1000L) + "s");
    syncStatus = "0";
    return "1";
  }
  
  public boolean getUserInfoFromOtherBase2(String baseName, String sql, int onceNum, String updateOrg, String notDel, String defaultOrg) {
    if (fieldUserMap == null)
      fieldUserMap = readFormXML("userField"); 
    if (baseName.equals(""))
      baseName = ((String[])fieldUserMap.get("dataSource"))[0]; 
    if (sql.contains("@fields@")) {
      String selectSql = "";
      for (String key : fieldUserMap.keySet()) {
        if (!",dataSource,tableName,orderBy,".contains("," + key + ",")) {
          String[] field = fieldUserMap.get(key);
          selectSql = String.valueOf(selectSql) + field[0] + ",";
        } 
      } 
      sql = sql.replace("@fields@", selectSql.substring(0, selectSql.length() - 1));
    } 
    Map<String, String> orgStrMap = null;
    if ("map".equals(((String[])fieldUserMap.get("orgnamestring"))[1]))
      orgStrMap = getOrgString(((String[])fieldUserMap.get("orgnamestring"))[3], baseName); 
    boolean returnValue = true;
    OperateUser oUser = new OperateUser();
    long beginTime = System.currentTimeMillis();
    DataSourceBase base = new DataSourceBase();
    Connection conn = null;
    Statement stat = null;
    boolean flag = true;
    int num = 1;
    String executeSql = sql.replace("@begin@", "1").replace("@end@", (new StringBuilder(String.valueOf(onceNum))).toString());
    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    while (flag) {
      flag = false;
      try {
        conn = base.getDataSource(baseName).getConnection();
        stat = conn.createStatement();
        System.out.println("人员同步执行sql：" + executeSql);
        ResultSet rs = stat.executeQuery(executeSql);
        while (rs.next()) {
          Map<String, String> map = new HashMap<String, String>();
          for (String key : fieldUserMap.keySet()) {
            if (!",dataSource,tableName,orderBy,".contains("," + key + ",")) {
              String[] field = fieldUserMap.get(key);
              String column = field[2].equals("") ? field[0] : field[2];
              if (",useraccounts,empname,empnumber,".contains("," + key + ",")) {
                map.put(key, (rs.getString(column) == null) ? "" : rs.getString(column));
                continue;
              } 
              if ("map".equals(field[1])) {
                String value = (rs.getString(column) == null) ? "" : rs.getString(column);
                map.put(key, (orgStrMap.get(value) == null) ? "" : orgStrMap.get(value));
                continue;
              } 
              if (field[1].contains("varchar")) {
                map.put(key, (rs.getString(column) == null) ? "''" : ("'" + rs.getString(column) + "'"));
                continue;
              } 
              if (field[1].contains("date")) {
                String databaseType = SystemCommon.getDatabaseType();
                if (databaseType.indexOf("mysql") >= 0)
                  map.put(key, (rs.getString(column) == null) ? "null" : ("'" + rs.getString(column) + "'")); 
                if (databaseType.indexOf("oracle") >= 0) {
                  String dateStr = (rs.getString(column) == null) ? "null" : ("fn_strtodate('" + ((rs.getString(column).indexOf(".") > 0) ? 
                    rs.getString(column).substring(0, rs.getString(column).indexOf(".")) : 
                    rs.getString(column)) + "','')");
                  map.put(key, dateStr);
                } 
                continue;
              } 
              map.put(key, (rs.getString(column) == null) ? "null" : rs.getString(column));
            } 
          } 
          list.add(map);
          flag = true;
        } 
        rs.close();
      } catch (Exception e) {
        System.out.println("用户同步出现异常");
        returnValue = false;
        flag = false;
        e.printStackTrace();
        break;
      } finally {
        try {
          if (stat != null)
            stat.close(); 
          if (conn != null)
            conn.close(); 
        } catch (SQLException e) {
          e.printStackTrace();
        } 
      } 
      oUser.userOperate(list, Long.valueOf(beginTime), updateOrg, notDel, defaultOrg);
      list.clear();
      if (sql.contains("@begin@") && sql.contains("@end@")) {
        num += onceNum;
        executeSql = sql.replace("@begin@", (new StringBuilder(String.valueOf(num))).toString()).replace("@end@", (new StringBuilder(String.valueOf(num + onceNum - 1))).toString());
        continue;
      } 
      flag = false;
    } 
    System.out.println("人员同步耗时：" + ((System.currentTimeMillis() - beginTime) / 1000L) + "s");
    fieldUserMap = null;
    return returnValue;
  }
  
  public boolean operateOrgFromBase() {
    fieldOrgMap = readFormXML("orgField");
    if (fieldOrgMap != null) {
      String[] dataSource = fieldOrgMap.get("dataSource");
      String[] tableName = fieldOrgMap.get("tableName");
      String sql = "select @fields@ from " + tableName[0] + " " + ((String[])fieldOrgMap.get("orderBy"))[1] + " " + ((String[])fieldOrgMap.get("orderBy"))[0];
      String selectSql = "";
      for (String key : fieldOrgMap.keySet()) {
        if (!",dataSource,tableName,orderBy,".contains("," + key + ",")) {
          String[] field = fieldOrgMap.get(key);
          selectSql = String.valueOf(selectSql) + field[0] + ",";
        } 
      } 
      sql = sql.replace("@fields@", selectSql.substring(0, selectSql.length() - 1));
      return getOrgInfoFromOtherBase(dataSource[0], sql);
    } 
    return true;
  }
  
  public boolean getOrgInfoFromOtherBase(String baseName, String sql) {
    if (fieldOrgMap == null)
      fieldOrgMap = readFormXML("orgField"); 
    if (sql.contains("@fields@")) {
      String selectSql = "";
      for (String key : fieldOrgMap.keySet()) {
        if (!",dataSource,tableName,orderBy,".contains("," + key + ",")) {
          String[] field = fieldOrgMap.get(key);
          selectSql = String.valueOf(selectSql) + field[0] + ",";
        } 
      } 
      sql = sql.replace("@fields@", selectSql.substring(0, selectSql.length() - 1));
    } 
    Map<String, String> orgStrMap = null;
    if ("map".equals(((String[])fieldOrgMap.get("orgnamestring"))[1]))
      orgStrMap = getOrgString(((String[])fieldOrgMap.get("orgnamestring"))[3], baseName); 
    boolean returnValue = true;
    OperateOrg oOrg = new OperateOrg();
    long beginTime = System.currentTimeMillis();
    DataSourceBase base = new DataSourceBase();
    Connection conn = null;
    Statement stat = null;
    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    try {
      conn = base.getDataSource(baseName).getConnection();
      stat = conn.createStatement();
      System.out.println("部门同步执行sql：" + sql);
      ResultSet rs = stat.executeQuery(sql);
      while (rs.next()) {
        Map<String, String> map = new HashMap<String, String>();
        for (String key : fieldOrgMap.keySet()) {
          if (!",dataSource,tableName,orderBy,".contains("," + key + ",")) {
            String[] field = fieldOrgMap.get(key);
            String column = field[2].equals("") ? field[0] : field[2];
            if ("orgnamestring".equals(key)) {
              String value = (rs.getString(column) == null) ? "" : rs.getString(column);
              if ("map".equals(field[1])) {
                map.put(key, (orgStrMap.get(value) == null) ? "" : orgStrMap.get(value));
                continue;
              } 
              map.put(key, value);
              continue;
            } 
            if (field[1].contains("varchar")) {
              map.put(key, (rs.getString(column) == null) ? "''" : ("'" + rs.getString(column) + "'"));
              continue;
            } 
            if (field[1].contains("date")) {
              String databaseType = SystemCommon.getDatabaseType();
              if (databaseType.indexOf("mysql") >= 0)
                map.put(key, (rs.getString(column) == null) ? "null" : ("'" + rs.getString(column) + "'")); 
              if (databaseType.indexOf("oracle") >= 0) {
                String dateStr = (rs.getString(column) == null) ? "null" : ("fn_strtodate('" + ((rs.getString(column).indexOf(".") > 0) ? 
                  rs.getString(column).substring(0, rs.getString(column).indexOf(".")) : 
                  rs.getString(column)) + "','')");
                map.put(key, dateStr);
              } 
              continue;
            } 
            map.put(key, (rs.getString(column) == null) ? "null" : rs.getString(column));
          } 
        } 
        list.add(map);
      } 
      rs.close();
    } catch (Exception e) {
      System.out.println("用户部门出现异常");
      returnValue = false;
      e.printStackTrace();
    } finally {
      try {
        if (stat != null)
          stat.close(); 
        if (conn != null)
          conn.close(); 
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    oOrg.updateOrg(list);
    System.out.println("部门同步耗时：" + ((System.currentTimeMillis() - beginTime) / 1000L) + "s");
    return returnValue;
  }
  
  public Map<String, String[]> readFormXML(String flag) {
    Map<String, String[]> map = null;
    try {
      String path = System.getProperty("user.dir");
      String filePath = String.valueOf(path) + "/jsconfig/userOrgField.xml";
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(filePath);
      Element dataField = doc.getRootElement().getChild(flag);
      if (dataField != null) {
        map = (Map)new HashMap<String, String>();
        Element dataSource = dataField.getChild("dataSource");
        map.put("dataSource", new String[] { dataSource.getAttributeValue("name"), dataSource.getAttributeValue("type"), 
              (dataField.getAttribute("updateOrg") == null) ? "0" : dataField.getAttributeValue("updateOrg"), 
              (dataField.getAttribute("onceNum") == null) ? "100" : dataField.getAttributeValue("onceNum"), 
              (dataField.getAttribute("notDel") == null) ? "" : dataField.getAttributeValue("notDel"), 
              (dataField.getAttribute("defalutOrg") == null) ? "" : dataField.getAttributeValue("defalutOrg") });
        Element fields = dataField.getChild("fields");
        map.put("tableName", new String[] { fields.getAttributeValue("table"), "" });
        List<Element> fieldList = fields.getChildren("field");
        for (int i = 0; i < fieldList.size(); i++) {
          Element field = fieldList.get(i);
          map.put(field.getAttributeValue("column").toLowerCase(), 
              new String[] { field.getText(), field.getAttributeValue("type").toLowerCase(), 
                (field.getAttribute("as") == null) ? "" : field.getAttributeValue("as"), 
                (field.getAttribute("sql") == null) ? "" : field.getAttributeValue("sql") });
        } 
        map.put("orderBy", new String[] { (dataField.getChildText("orderBy") == null) ? "" : dataField.getChildText("orderBy"), 
              (dataField.getChildText("where") == null) ? "" : dataField.getChildText("where") });
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return map;
  }
  
  public Map<String, String> getOrgString(String sql, String baseName) {
    Map<String, String[]> orgMap = (Map)new HashMap<String, String>();
    List<String[]> orgList = (List)new ArrayList<String>();
    Map<String, String> orgStrMap = new HashMap<String, String>();
    DataSourceBase base = new DataSourceBase();
    Connection conn = null;
    Statement stat = null;
    try {
      conn = base.getDataSource(baseName).getConnection();
      stat = conn.createStatement();
      ResultSet rs = stat.executeQuery(sql);
      while (rs.next()) {
        orgMap.put(rs.getString(1), new String[] { rs.getString(2), (rs.getString(3) == null) ? "0" : rs.getString(3) });
        orgList.add(new String[] { rs.getString(1), (rs.getString(3) == null) ? "0" : rs.getString(3), rs.getString(2) });
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (stat != null)
          stat.close(); 
        if (conn != null)
          conn.close(); 
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    for (int i = 0; i < orgList.size(); i++) {
      String[] orgInfo = orgList.get(i);
      if (!"0".equals(orgInfo[1]) && orgMap.get(orgInfo[1]) != null) {
        String[] getOrg = orgMap.get(orgInfo[1]);
        if ("0".equals(getOrg[1])) {
          orgStrMap.put(orgInfo[0], String.valueOf(getOrg[0]) + "." + orgInfo[2]);
        } else {
          orgList.add(new String[] { orgInfo[0], getOrg[1], String.valueOf(getOrg[0]) + "." + orgInfo[2] });
        } 
      } else if ("0".equals(orgInfo[1])) {
        orgStrMap.put(orgInfo[0], orgInfo[2]);
      } 
    } 
    return orgStrMap;
  }
  
  public boolean syncDutyInfo() {
    boolean result = false;
    this.dutyMap = new ConcurrentHashMap<String, String>();
    fieldUserMap = readFormXML("userField");
    String[] dataSource = fieldUserMap.get("dataSource");
    Connection hrConn = null;
    Connection oaConn = null;
    PreparedStatement hrPstmt = null;
    PreparedStatement oaPstmt = null;
    boolean autoCommit = true;
    try {
      String sql = "delete from oa_duty";
      oaConn = (new DataSourceBase()).getDataSource().getConnection();
      autoCommit = oaConn.getAutoCommit();
      oaConn.setAutoCommit(false);
      oaPstmt = oaConn.prepareStatement(sql);
      oaPstmt.executeUpdate();
      sql = "insert into oa_duty(duty_id,dutyname,domain_id,dutylevel,duty_describe,corpid,dutyno) values (hibernate_sequence.nextval,?,0,?,?,0,'')";
      oaPstmt = oaConn.prepareStatement(sql);
      sql = "select zwmc,zjmc,zjdm from usr_gxsj.v_js_zwdmb";
      hrConn = (new DataSourceBase()).getDataSource(dataSource[0]).getConnection();
      hrPstmt = hrConn.prepareStatement(sql);
      ResultSet rs = hrPstmt.executeQuery();
      while (rs.next()) {
        String dutyName = rs.getString(1);
        String dutyDescribe = rs.getString(2);
        String dutyLevel = rs.getString(3);
        this.dutyMap.put(dutyName, dutyLevel);
        oaPstmt.setString(1, dutyName);
        oaPstmt.setString(2, dutyLevel);
        oaPstmt.setString(3, dutyDescribe);
        oaPstmt.executeUpdate();
      } 
      rs.close();
      oaConn.commit();
      oaConn.setAutoCommit(autoCommit);
      oaPstmt.close();
      oaConn.close();
      hrPstmt.close();
      hrConn.close();
    } catch (Exception ex) {
      if (oaConn != null)
        try {
          oaConn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      if (hrConn != null)
        try {
          hrConn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return result;
  }
  
  public boolean userOrgChanged(String userAccount, String orgName, String orgId, Map<String, String> thirdLevelMap, Map<String, String> twoLevelIdAndNameMap, Connection conn) {
    boolean changed = true;
    try {
      String curOrgId = "";
      String sql = "select org_id from org_organization_user oou join org_employee oe on oou.emp_id=oe.emp_id and oe.userAccounts=? and userisdeleted=0";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, userAccount);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next())
        curOrgId = rs.getString(1); 
      rs.close();
      pstmt.close();
      if (!curOrgId.equals("-1")) {
        if ((thirdLevelMap.get(curOrgId) == null && !"兰州大学.其他部门".equals(twoLevelIdAndNameMap.get(curOrgId))) || orgId.equals(thirdLevelMap.get(curOrgId)))
          changed = false; 
        if (twoLevelIdAndNameMap.get(curOrgId) != null && !orgId.equals(thirdLevelMap.get(curOrgId)))
          changed = true; 
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return changed;
  }
  
  public void deleteDuplicateUser() {
    Connection conn = null;
    Statement stmt = null;
    try {
      List<String> numList = new ArrayList<String>();
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      String sql = "select distinct empnumber from (select count(empnumber) as unum, empnumber from org_employee group by empnumber) where unum>1";
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next())
        numList.add(rs.getString(1)); 
      rs.close();
      for (int i = 0; i < numList.size(); i++) {
        String empId1 = "";
        String empId2 = "";
        String empId3 = "";
        String empName1 = "";
        String empName2 = "";
        String empName3 = "";
        String account1 = "";
        String account2 = "";
        String account3 = "";
        int accIsNum1 = 0;
        int accIsNum2 = 0;
        int accIsNum3 = 0;
        String isActive1 = "";
        String isActive2 = "";
        String isActive3 = "";
        String isDeleted1 = "";
        String isDeleted2 = "";
        String isDeleted3 = "";
        sql = "select emp_id,useraccounts,empname,userisactive,userisdeleted from org_employee where empnumber='" + (String)numList.get(i) + "'";
        rs = stmt.executeQuery(sql);
        if (rs.next()) {
          empId1 = rs.getString(1);
          account1 = rs.getString(2);
          empName1 = rs.getString(3);
          isActive1 = rs.getString(4);
          isDeleted1 = rs.getString(5);
          accIsNum1 = accountIsNumber(account1);
        } 
        if (rs.next()) {
          empId2 = rs.getString(1);
          account2 = rs.getString(2);
          empName2 = rs.getString(3);
          isActive2 = rs.getString(4);
          isDeleted2 = rs.getString(5);
          accIsNum2 = accountIsNumber(account2);
        } 
        if (rs.next()) {
          empId3 = rs.getString(1);
          account3 = rs.getString(2);
          empName3 = rs.getString(3);
          isActive3 = rs.getString(4);
          isDeleted3 = rs.getString(5);
          accIsNum3 = accountIsNumber(account3);
        } 
        rs.close();
        if (accIsNum1 == 1)
          if (!"".equals(empId2) && accIsNum2 == 0 && "0".equals(isDeleted2))
            updateDataAndDelUser(stmt, empId1, empId2);  
        if (accIsNum2 == 1)
          if (!"".equals(empId1) && accIsNum1 == 0 && "0".equals(isDeleted1))
            updateDataAndDelUser(stmt, empId2, empId1);  
      } 
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      ex.printStackTrace();
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
    } 
  }
  
  private void updateDataAndDelUser(Statement stmt, String delId, String empId) {
    try {
      String sql = "";
      stmt.executeUpdate("delete from org_rightscope where emp_id=" + delId);
      stmt.executeUpdate("delete from org_user_role  where emp_id=" + delId);
      stmt.executeUpdate("delete from org_organization_user  where emp_id=" + delId);
      stmt.executeUpdate("delete from org_user_group  where emp_id=" + delId);
      stmt.executeUpdate("delete from org_employee  where emp_id=" + delId);
      stmt.executeUpdate("update jsf_work set wf_curemployee_id=" + empId + " where wf_curemployee_id=" + delId);
      stmt.executeUpdate("update co_nodemember set emp_id=" + empId + " where emp_id=" + delId);
      stmt.executeUpdate("update co_opinion set emp_id=" + empId + " where emp_id=" + delId);
      stmt.executeUpdate("update co_node set nickname=replace(nickname,'P" + delId + "','P" + empId + "')");
      stmt.executeUpdate("update org_employee set empleaderid=replace(empleaderid,'$" + delId + "$','$" + empId + "$') where empleaderid like '%$" + delId + "$%'");
      stmt.executeUpdate("update jsf_dealwithcomment set dealwithemployee_id=" + empId + " where dealwithemployee_id=" + delId);
      stmt.executeUpdate("update jsf_dealwithlog set senduserid=" + empId + " where senduserid=" + delId);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  private int accountIsNumber(String str) {
    boolean result = str.matches("[0-9]+");
    if (result)
      return 1; 
    return 0;
  }
}
