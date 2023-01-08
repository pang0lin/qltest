package com.js.ldap;

import com.js.oa.userdb.util.DbOpt;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class MSADNoCert extends LDAP {
  private Map orgStringMap = new HashMap<Object, Object>(100);
  
  private long currentDate = (new Date()).getTime();
  
  private boolean hasToDB = true;
  
  private Connection conn = null;
  
  public MSADNoCert() {
    try {
      getContext();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public void queryItem(String filter) throws Exception {
    queryItemByFilter("(&(objectClass=organizationalUnit)(!(objectClass=computer))(!(objectClass=inetOrgPerson)))", "org");
    queryItemByFilter("(&(objectClass=user)(!(objectClass=computer))(!(objectClass=inetOrgPerson)))", "emp");
  }
  
  public void queryItemByFilter(String filter, String updateName) throws Exception {
    queryItemByFilter(filter, updateName, "1");
  }
  
  public void queryItemByFilter(String filter, String updateName, String closeConn) throws Exception {
    DbOpt dbopt = new DbOpt();
    this.conn = (new DataSourceBase()).getDataSource().getConnection();
    boolean hasSynced = false;
    NamingEnumeration<SearchResult> namingEnum = null;
    String[] atts = { "initials", "ou", "cn", "sAMAccountName", "DISPLAYName", "objectSID", "objectGUID", "whenChanged", "distinguishedName" };
    try {
      SearchControls searchCons = new SearchControls();
      searchCons.setSearchScope(2);
      searchCons.setCountLimit(0L);
      searchCons.setTimeLimit(0);
      searchCons.setReturningAttributes(atts);
      namingEnum = this.cnt.search(base, filter, searchCons);
      if (namingEnum == null) {
        hasSynced = false;
      } else {
        hasSynced = true;
      } 
      while (namingEnum != null && namingEnum.hasMore()) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        SearchResult result = namingEnum.next();
        Attributes attrs = result.getAttributes();
        if (attrs.size() != 0) {
          Object attValue = null;
          NamingEnumeration<? extends Attribute> namingEnum_1 = attrs.getAll();
          while (namingEnum_1.hasMoreElements()) {
            Attribute attribute = namingEnum_1.next();
            String attID = attribute.getID();
            NamingEnumeration<?> namingEnum_2 = attribute.getAll();
            while (namingEnum_2.hasMoreElements()) {
              attValue = namingEnum_2.nextElement();
              if (attID.equals("objectGUID")) {
                StringBuffer buffer = new StringBuffer();
                byte[] b = attValue.toString().getBytes();
                for (int i = 0; i < b.length; i++)
                  buffer.append(b[i]); 
                map.put(attID, buffer.toString());
                continue;
              } 
              map.put(attID, attValue);
            } 
          } 
          ldapToDB(map);
        } 
        hasSynced = true;
      } 
    } catch (Exception ex) {
      hasSynced = false;
      try {
        this.conn.close();
      } catch (Exception e) {
        e.printStackTrace();
      } 
      throw ex;
    } 
    if ("1".equals(closeConn)) {
      Statement stmt = this.conn.createStatement();
      int time = interval * 90000;
      if (time < 0)
        time = 300000; 
      if ("org".equals(updateName)) {
        stmt.executeUpdate("update org_organization set orgStatus=4 where lastupdate+" + time + "<" + this.currentDate);
      } else {
        stmt.executeUpdate("update org_employee set userisdeleted=1,useraccounts='',usersimplename='' where lastupdate+" + time + "<" + this.currentDate);
      } 
      stmt.close();
      this.conn.close();
    } 
  }
  
  public boolean ldapToDB(Map map) throws Exception {
    DbOpt dbopt = new DbOpt();
    Statement stmt = null;
    ResultSet rs = null;
    if (map == null)
      return false; 
    try {
      StringBuffer buffer = new StringBuffer();
      String guid = map.get("objectGUID").toString();
      String dn = map.get("distinguishedName").toString();
      if (dn.indexOf(",OU=") < 0)
        return false; 
      String parentDN = dn.substring(dn.indexOf(",OU=") + 1);
      stmt = this.conn.createStatement();
      if (map.get("ou") != null) {
        String orgName = map.get("ou").toString();
        String orgNameString = orgName;
        String orgIdString = "";
        String parentOrgNameString = "";
        long orgId = 0L;
        int orgLevel = 0;
        int hasJunior = hasJunior(dn);
        boolean newOrg = false;
        dn = "," + dn.substring(0, dn.lastIndexOf(",OU="));
        String[] ous = dn.split(",OU=");
        orgLevel = ous.length - 2;
        try {
          String parentOrgId, sql = "select org_Id from org_organization where guid='" + 
            guid + "'";
          rs = stmt.executeQuery(sql);
          if (rs.next())
            orgId = rs.getLong(1); 
          rs.close();
          if (orgId == 0L)
            if (DbOpt.dbtype.indexOf("oracle") >= 0) {
              rs = stmt.executeQuery(
                  "select hibernate_sequence.nextval from dual");
              if (rs.next()) {
                orgId = rs.getLong(1);
                newOrg = true;
              } 
              rs.close();
              buffer.append("insert into org_organization(org_id,orgName,orgSimpleName,orgSerial,orgLevel,guid,orgHasChannel) values(")
                .append(orgId).append(",'")
                .append(orgName).append("','")
                .append(orgName).append("','")
                .append(orgName).append("',")
                .append(orgLevel).append(",'")
                .append(guid).append("',0)");
              stmt.executeUpdate(buffer.toString());
            } else {
              buffer.append("insert into org_organization(orgName,orgSimpleName,orgSerial,orgLevel,guid,orgHasChannel) values('")
                .append(orgName).append("','")
                .append(orgName).append("','")
                .append(orgName).append("',")
                .append(orgLevel).append(",'")
                .append(guid).append("',0)");
              stmt.executeUpdate(buffer.toString());
              rs = stmt.executeQuery(
                  "select max(org_id) from org_organization");
              if (rs.next()) {
                orgId = rs.getLong(1);
                newOrg = true;
              } 
              rs.close();
            }  
          for (int i = ous.length - 1; i > 1; i--) {
            if (i == ous.length - 1) {
              parentOrgNameString = String.valueOf(parentOrgNameString) + ous[i];
            } else {
              parentOrgNameString = String.valueOf(parentOrgNameString) + "." + ous[i];
            } 
          } 
          if (orgLevel == 0) {
            orgNameString = orgName;
            orgIdString = "_500000$-1$";
            parentOrgId = "0";
          } else {
            orgNameString = String.valueOf(parentOrgNameString) + "." + orgName;
            if (this.orgStringMap.get(parentOrgNameString) == null)
              queryItemByFilter("(&(objectClass=organizationalUnit)(!(objectClass=computer))(!(objectClass=inetOrgPerson))(distinguishedName=" + parentDN + "))", "org", "0"); 
            orgIdString = this.orgStringMap.get(parentOrgNameString).toString();
            orgIdString = orgIdString.substring(0, 
                orgIdString.length() - 1);
            parentOrgId = orgIdString.substring(orgIdString
                .lastIndexOf("$") + 1);
            orgIdString = String.valueOf(orgIdString) + "$";
          } 
          int orderCode = 1000000;
          if (newOrg) {
            rs = stmt.executeQuery(
                "select orgOrderCode from org_organization where orgParentOrgId=" + 
                parentOrgId + " order by orgOrderCode desc");
            if (rs.next()) {
              orderCode = rs.getInt(1) + 10000;
              rs.close();
            } else {
              orderCode = 1000000;
            } 
          } else {
            rs = stmt.executeQuery(
                "select orgOrderCode from org_organization where org_id=" + 
                orgId);
            if (rs.next()) {
              orderCode = rs.getInt(1);
              rs.close();
            } else {
              orderCode = 1000000;
            } 
          } 
          orgIdString = String.valueOf(orgIdString) + "_" + orderCode + "$" + orgId + "$";
          this.orgStringMap.put(orgNameString, orgIdString);
          buffer = new StringBuffer();
          buffer.append("update org_organization set orgIdString='")
            .append(orgIdString)
            .append("',orgNameString='").append(orgNameString)
            .append("',orgName='").append(orgName)
            .append("',orgparentOrgId=").append(parentOrgId)
            .append(",orgHasJunior=").append(hasJunior);
          if (newOrg)
            buffer.append(",orgOrderCode=").append(orderCode); 
          buffer.append(",orgStatus=0,domain_id=0")
            .append(",lastUpdate='").append(this.currentDate)
            .append("' where org_id=").append(orgId);
          stmt.addBatch(buffer.toString());
          stmt.executeBatch();
        } catch (Exception err) {
          stmt.executeUpdate("update org_organization set lastUpdate='" + this.currentDate + "' where org_id=" + orgId);
          err.printStackTrace();
        } 
      } else {
        String account = "";
        account = map.get("sAMAccountName").toString();
        if (account == null || "".equals(account) || "null".equals(account))
          return false; 
        String empName = map.get("cn").toString();
        String empEname = (map.get("initials") == null) ? "" : 
          map.get("initials").toString();
        String empId = "-1";
        dn = dn.substring(dn.indexOf(",OU="), dn.lastIndexOf(",OU="));
        String[] ous = dn.split(",OU=");
        try {
          String parentOrgNameString = "";
          for (int i = ous.length - 1; i > 0; i--) {
            if (i == 1) {
              parentOrgNameString = String.valueOf(parentOrgNameString) + ous[i];
            } else {
              parentOrgNameString = String.valueOf(parentOrgNameString) + ous[i] + ".";
            } 
          } 
          String orgIdString = (String)this.orgStringMap.get(parentOrgNameString);
          orgIdString = orgIdString.substring(0, 
              orgIdString.length() - 1);
          String orgId = orgIdString.substring(orgIdString.lastIndexOf("$") + 1);
          String sql = "select emp_id from org_employee where guid='" + guid + "'";
          rs = stmt.executeQuery(sql);
          if (rs.next())
            empId = rs.getString(1); 
          rs.close();
          if ("-1".equals(empId))
            if (DbOpt.dbtype.indexOf("oracle") >= 0) {
              rs = stmt.executeQuery(
                  "select hibernate_sequence.nextval from dual");
              if (rs.next())
                empId = rs.getString(1); 
              rs.close();
              buffer.append("insert into org_employee (emp_id,empName,userAccounts,userSimpleName,empstatus,userPassword,empSex,empHeight,empIsMarriage,empState,empWeight,userIsActive,userIsDeleted,userIsFormalUser,userIsSuper,createdOrg,domain_id,userordercode,keyvalidate,guid) values(")
                .append(empId).append(",'")
                .append(empName).append("','")
                .append(account).append("','")
                .append(empName).append(
                  "','0','5EB72F96E795C92A549DD5A330112621896O',0,0,0,0,0,1,0,1,0,0,0,10000,0,'")
                .append(guid).append("')");
              stmt.executeUpdate(buffer.toString());
              stmt.executeUpdate(
                  "insert into org_organization_user(org_id,emp_id) values(" + 
                  orgId + "," + empId + ")");
            } else {
              buffer.append("insert into org_employee (empName,userAccounts,userSimpleName,empstatus,userPassword,empSex,empHeight,empIsMarriage,empState,empWeight,userIsActive,userIsDeleted,userIsFormalUser,userIsSuper,createdOrg,domain_id,userordercode,keyvalidate,guid) values('")
                .append(empName).append("','")
                .append(account).append("','")
                .append(empName).append(
                  "','0','5EB72F96E795C92A549DD5A330112621896O',0,0,0,0,0,1,0,1,0,0,0,10000,0,'")
                .append(guid).append("')");
              stmt.executeUpdate(buffer.toString());
              rs = stmt.executeQuery(
                  "select max(emp_id) from org_employee");
              if (rs.next())
                empId = rs.getString(1); 
              rs.close();
              stmt.executeUpdate(
                  "insert into org_organization_user(org_id,emp_id) values(" + 
                  orgId + "," + empId + ")");
            }  
          buffer = new StringBuffer();
          buffer.append("update org_employee set ")
            .append("empName='").append(empName)
            .append("',userAccounts='").append(account)
            .append("',empEnglishName='").append(empEname)
            .append("',lastUpdate='").append(this.currentDate)
            .append("' where guid='").append(guid).append("'");
          stmt.executeUpdate(buffer.toString());
          rs = stmt.executeQuery("select * from org_organization_user where emp_id=" + empId);
          if (rs.next()) {
            rs.close();
            stmt.executeUpdate("update org_organization_user set org_id=" + 
                orgId + " where emp_id=" + empId);
          } else {
            rs.close();
            stmt.executeUpdate(
                "insert into org_organization_user(org_id,emp_id) values(" + 
                orgId + "," + empId + ")");
          } 
        } catch (Exception err) {
          stmt.executeUpdate("update org_employee set lastUpdate='" + this.currentDate + "' where guid='" + guid + "'");
          err.printStackTrace();
        } 
      } 
      stmt.close();
    } catch (Exception e) {
      this.hasToDB = false;
      e.printStackTrace();
      throw e;
    } 
    return false;
  }
  
  public boolean ldapToDBForUpdate(Map map) throws Exception {
    DbOpt dbopt = new DbOpt();
    Statement stmt = null;
    ResultSet rs = null;
    if (map == null)
      return false; 
    try {
      StringBuffer buffer = new StringBuffer();
      String guid = map.get("objectGUID").toString();
      String dn = map.get("distinguishedName").toString();
      if (dn.indexOf(",OU=") < 0)
        return false; 
      stmt = this.conn.createStatement();
      if (map.get("ou") != null) {
        String orgName = map.get("ou").toString();
        String orgNameString = orgName;
        String orgIdString = "";
        String parentOrgNameString = "";
        long orgId = 0L;
        int orgLevel = 0;
        int hasJunior = hasJunior(dn);
        boolean newOrg = false;
        dn = "," + dn.substring(0, dn.lastIndexOf(",OU="));
        String[] ous = dn.split(",OU=");
        orgLevel = ous.length - 2;
        String orgNameStr = "";
        int i;
        for (i = ous.length - 1; i > 0; i--) {
          if (i == ous.length - 1) {
            orgNameStr = String.valueOf(orgNameStr) + ous[i];
          } else {
            orgNameStr = String.valueOf(orgNameStr) + "." + ous[i];
          } 
        } 
        try {
          String parentOrgId, sql = "select org_Id from org_organization where ORGNAMESTRING='" + 
            orgNameStr + "'";
          rs = stmt.executeQuery(sql);
          if (rs.next())
            orgId = rs.getLong(1); 
          rs.close();
          for (i = ous.length - 1; i > 1; i--) {
            if (i == ous.length - 1) {
              parentOrgNameString = String.valueOf(parentOrgNameString) + ous[i];
            } else {
              parentOrgNameString = String.valueOf(parentOrgNameString) + "." + ous[i];
            } 
          } 
          if (orgLevel == 0) {
            orgNameString = orgName;
            orgIdString = "";
            parentOrgId = "0";
          } else {
            orgNameString = String.valueOf(parentOrgNameString) + "." + orgName;
            System.out.println("orgNameString:" + orgNameString);
            orgIdString = this.orgStringMap.get(parentOrgNameString)
              .toString();
            orgIdString = orgIdString.substring(0, 
                orgIdString.length() - 1);
            parentOrgId = orgIdString.substring(orgIdString
                .lastIndexOf("$") + 1);
            orgIdString = String.valueOf(orgIdString) + "$";
          } 
          int orderCode = 1000000;
          if (newOrg) {
            rs = stmt.executeQuery(
                "select orgOrderCode from org_organization where orgParentOrgId=" + 
                parentOrgId + " order by orgOrderCode desc");
            if (rs.next()) {
              orderCode = rs.getInt(1) + 10000;
              rs.close();
            } else {
              orderCode = 1000000;
            } 
          } else {
            rs = stmt.executeQuery(
                "select orgOrderCode from org_organization where org_id=" + 
                orgId);
            if (rs.next()) {
              orderCode = rs.getInt(1);
              rs.close();
            } else {
              orderCode = 1000000;
            } 
          } 
          orgIdString = String.valueOf(orgIdString) + "_" + orderCode + "$" + orgId + "$";
          this.orgStringMap.put(orgNameString, orgIdString);
          buffer = new StringBuffer();
          buffer.append("update org_organization set ");
          buffer.append("guid='").append(guid).append("'")
            .append(",lastUpdate='").append(this.currentDate)
            .append("' where org_id=").append(orgId);
          stmt.addBatch(buffer.toString());
          stmt.executeBatch();
        } catch (Exception err) {
          stmt.executeUpdate("update org_organization set lastUpdate='" + this.currentDate + "' where org_id=" + orgId);
        } 
      } else {
        String account = "";
        account = map.get("sAMAccountName").toString();
        if (account == null || "".equals(account) || "null".equals(account))
          return false; 
        String empName = map.get("cn").toString();
        String empEname = (map.get("initials") == null) ? "" : 
          map.get("initials").toString();
        String empId = "-1";
        dn = dn.substring(dn.indexOf(",OU="), dn.lastIndexOf(",OU="));
        String[] ous = dn.split(",OU=");
        try {
          String parentOrgNameString = "";
          for (int i = ous.length - 1; i > 0; i--) {
            if (i == ous.length - 1) {
              parentOrgNameString = String.valueOf(parentOrgNameString) + ous[i];
            } else {
              parentOrgNameString = String.valueOf(parentOrgNameString) + "." + ous[i];
            } 
          } 
          String orgIdString = this.orgStringMap.get(parentOrgNameString)
            .toString();
          orgIdString = orgIdString.substring(0, 
              orgIdString.length() - 1);
          String orgId = orgIdString.substring(orgIdString.lastIndexOf("$") + 1);
          String sql = "select emp_id from org_employee where useraccounts='" + account + "'";
          rs = stmt.executeQuery(sql);
          if (rs.next())
            empId = rs.getString(1); 
          rs.close();
          System.out.println("同步用户的 guId:" + guid + "    empId:" + empId);
          System.out.println("empName:" + empName + "       dn:" + dn);
          buffer = new StringBuffer();
          buffer.append("update org_employee set ")
            .append("empName='").append(empName)
            .append("',empEnglishName='").append(empEname)
            .append("',lastUpdate='").append(this.currentDate)
            .append("',guid='").append(guid)
            .append("' where emp_id='").append(empId).append("'");
          stmt.addBatch(buffer.toString());
          stmt.addBatch("update org_organization_user set org_id=" + 
              orgId + " where emp_id=" + empId);
          stmt.executeBatch();
        } catch (Exception err) {
          stmt.executeUpdate("update org_employee set lastUpdate='" + this.currentDate + "' where emp_id='" + empId + "'");
          err.printStackTrace();
        } 
      } 
      stmt.close();
    } catch (Exception e) {
      this.hasToDB = false;
      e.printStackTrace();
      throw e;
    } 
    return false;
  }
  
  public boolean updatePass(String userName, String newPassword) {
    boolean result = false;
    userName = getUserDN(userName);
    try {
      ModificationItem[] mods = new ModificationItem[1];
      String newQuotedPassword = "\"" + newPassword + "\"";
      byte[] newUnicodePassword = newQuotedPassword.getBytes("UTF-16LE");
      mods[0] = new ModificationItem(2, 
          
          new BasicAttribute("unicodePwd", 
            newUnicodePassword));
      this.cnt.modifyAttributes(userName, mods);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return result;
  }
  
  public String getUserDN(String userName) {
    String[] atts = new String[0];
    String filter = "cn=" + userName;
    NamingEnumeration<SearchResult> namingEnum = null;
    try {
      getContext();
      SearchControls searchCons = new SearchControls();
      searchCons.setSearchScope(2);
      searchCons.setCountLimit(0L);
      searchCons.setTimeLimit(0);
      namingEnum = this.cnt.search(base, filter, searchCons);
      while (namingEnum != null && namingEnum.hasMore()) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        SearchResult result = namingEnum.next();
        StringBuffer sb = new StringBuffer(result.getName());
        if (result.getName().length() > 0)
          sb.append(","); 
        sb.append(base);
        userName = sb.toString();
      } 
    } catch (Exception exception) {}
    return userName;
  }
  
  public String getAccountDN(String userName) {
    String[] atts = new String[0];
    String filter = "(&(objectClass=user)(sAMAccountName=" + userName + "))";
    userName = "";
    NamingEnumeration<SearchResult> namingEnum = null;
    try {
      getContext();
      SearchControls searchCons = new SearchControls();
      searchCons.setSearchScope(2);
      searchCons.setCountLimit(0L);
      searchCons.setTimeLimit(0);
      namingEnum = this.cnt.search(base, filter, searchCons);
      while (namingEnum != null && namingEnum.hasMore()) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        SearchResult result = namingEnum.next();
        StringBuffer sb = new StringBuffer(result.getName());
        if (result.getName().length() > 0)
          sb.append(","); 
        sb.append(base);
        userName = sb.toString();
      } 
    } catch (Exception exception) {}
    return userName;
  }
  
  public boolean setOrgLeader() {
    return false;
  }
  
  public boolean setUserLeader() {
    return false;
  }
  
  public int hasJunior(String base) {
    int result = 0;
    NamingEnumeration<SearchResult> namingEnum = null;
    String[] attrs = { "distinguishedName", "ou" };
    try {
      SearchControls searchCons = new SearchControls();
      searchCons.setSearchScope(2);
      searchCons.setCountLimit(0L);
      searchCons.setTimeLimit(0);
      searchCons.setReturningAttributes(attrs);
      namingEnum = this.cnt.search(base, "objectClass=organizationalUnit", searchCons);
      int i = 0;
      while (namingEnum != null && namingEnum.hasMore()) {
        i++;
        namingEnum.next();
        if (i > 1) {
          result = 1;
          break;
        } 
      } 
    } catch (Exception exception) {}
    return result;
  }
  
  public String Authenticate(String account, String password) {
    try {
      account = getAccountDN(account);
      if ("".equals(account))
        return "-1"; 
      this.cnt.addToEnvironment("java.naming.security.principal", account);
      this.cnt.addToEnvironment("java.naming.security.credentials", password);
      this.cnt.reconnect(this.connCtls);
      return "0";
    } catch (Exception e) {
      return "1";
    } 
  }
  
  public static void main(String[] args) {
    MSADNoCert ad = new MSADNoCert();
    try {
      SearchControls searchCtls = new SearchControls();
      searchCtls.setSearchScope(2);
      String searchFilter = "(&(objectCategory=person)(objectClass=user)(name=*))";
      searchFilter = "(&(objectClass=organizationalUnit)(!(objectClass=computer))(!(objectClass=inetOrgPerson)))";
      String searchBase = "OU=测试组织,DC=jiusi,DC=com";
      String[] returnedAtts = { "memberOf" };
      searchCtls.setReturningAttributes(returnedAtts);
      NamingEnumeration<SearchResult> answer = ad.cnt.search(searchBase, searchFilter, searchCtls);
      while (answer.hasMoreElements()) {
        SearchResult sr = answer.next();
        System.out.println("<<<::[" + sr.getName() + "]::>>>>");
      } 
      ad.cnt.close();
    } catch (NamingException e) {
      e.printStackTrace();
      System.err.println("Problem searching directory: " + e);
    } 
  }
}
