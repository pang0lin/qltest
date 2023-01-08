package com.js.message.lava;

import com.js.util.util.DataSourceBase;
import com.js.util.util.MD5;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class GKSync {
  private String root = "全部组织";
  
  private String domainId = "0";
  
  public boolean Sync() {
    boolean flag = false;
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      DataSource ds = (new DataSourceBase()).getDataSource();
      conn = ds.getConnection();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    try {
      User user = new User();
      Organization organization = new Organization();
      stmt = conn.createStatement();
      GKOrganization gkOrganization = new GKOrganization();
      rs = stmt.executeQuery("select org_id,orgParentOrgId,orgname,orgdescripte,orgordercode from org_organization where orgstatus=0 and domain_id=" + this.domainId + " order by orgLevel");
      while (rs.next()) {
        organization.setCode(rs.getString(1));
        organization.setParentcode(rs.getString(2));
        organization.setName(rs.getString("orgname"));
        String desc = rs.getString("orgdescripte");
        organization.setSign("1");
        organization.setEmail("");
        organization.setLocation(rs.getString(5));
        organization.setRemark(desc);
        flag = gkOrganization.saveOrganization(organization);
      } 
      rs.close();
      gkOrganization.close();
      System.out.println("同步组织完成");
      List<String> accountList = new ArrayList();
      GKUserManager gkUserManager = new GKUserManager();
      rs = stmt.executeQuery("select userAccounts,empName,empSex,empBirth,empAddress,empWebAddress,empEmail,empMobilePhone,empBusinessphone,empBusinessFax,empDuty,empZipCode,userIsActive,userPassword   from org_employee emp where emp_id>0 and userIsDeleted=0 and useraccounts is not null  and userisactive=1 and useraccounts<>' ' and domain_id=" + 

          
          this.domainId);
      while (rs.next()) {
        String account = rs.getString(1);
        String name = rs.getString(2);
        String sex = rs.getString(3);
        String birth = rs.getString(4);
        String address = rs.getString(5);
        String webAddress = rs.getString(6);
        String email = rs.getString(7);
        String mobile = rs.getString(8);
        String phone = rs.getString(9);
        String fax = rs.getString(10);
        String position = rs.getString(11);
        String zip = rs.getString(12);
        String active = rs.getString(13);
        String password = rs.getString(14);
        password = (new MD5()).getOldMD5Code(password);
        user.setAccount(account);
        user.setUgName(this.root);
        user.setName(name);
        user.setSex(sex);
        user.setBirthday((birth == null || "null".equals(birth)) ? "" : birth);
        user.setEmail((email == null || "null".equals(email)) ? "" : email);
        user.setMobile((mobile == null || "null".equals(mobile)) ? "" : mobile);
        user.setOfficeTel((phone == null || "null".equals(phone)) ? "" : phone);
        user.setFax((fax == null || "null".equals(fax)) ? "" : fax);
        user.setWebaddress((webAddress == null || "null".equals(webAddress)) ? "" : webAddress);
        user.setPostcode((zip == null || "null".equals(zip)) ? "" : zip);
        user.setAddress((address == null || "null".equals(address)) ? "" : address);
        user.setPosition((position == null || "null".equals(position)) ? "" : position);
        user.setPwd("");
        user.setDisplayName(name);
        user.setRemark("");
        user.setState(active);
        user.setUg_code("");
        user.setMd5_pwd(password);
        if (gkUserManager.userIsExist(account)) {
          flag = gkUserManager.modUser(user);
        } else {
          flag = gkUserManager.saveUser(user);
        } 
        accountList.add(account);
      } 
      rs.close();
      PreparedStatement pstmt = conn.prepareStatement("update org_employee set imid=? where useraccounts=?");
      for (int i = 0; i < accountList.size(); i++) {
        String account = accountList.get(i).toString();
        String gid = gkUserManager.getUserGID(account);
        pstmt.setString(1, gid);
        pstmt.setString(2, account);
        pstmt.executeUpdate();
      } 
      pstmt.close();
      accountList.clear();
      gkUserManager.close();
      System.out.println("同步用户完成");
      stmt = conn.createStatement();
      gkOrganization = new GKOrganization();
      rs = stmt.executeQuery("select oo.org_id,userAccounts from org_organization oo,org_organization_user ou,org_employee emp where oo.org_id=ou.org_id and ou.emp_id=emp.emp_id and emp.userIsDeleted=0 and emp.useraccounts<>'admin' and emp.useraccounts is not null");
      while (rs.next())
        gkOrganization.orgSignUser(rs.getString(1), rs.getString(2)); 
      rs.close();
      System.out.println("同步用户所属组织机构完成");
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      try {
        if (conn != null)
          conn.close(); 
      } catch (Exception e) {
        e.printStackTrace();
      } 
      ex.printStackTrace();
    } 
    return flag;
  }
  
  public boolean SyncPart() {
    boolean flag = false;
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      DataSource ds = (new DataSourceBase()).getDataSource();
      conn = ds.getConnection();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    try {
      User user = new User();
      Organization organization = new Organization();
      stmt = conn.createStatement();
      GKOrganization gkOrganization = new GKOrganization();
      int dataopr = -1;
      rs = stmt.executeQuery("select tt.org_id,tt.orgParentOrgId,tt.orgname,tt.orgdescripte,t.dataopr  from org_syncrtx t,org_organization tt  where t.org_id=tt.org_id and t.datatype=0 and tt.orgstatus=0 and tt.domain_id=" + 
          
          this.domainId + " order by tt.orgLevel");
      while (rs.next()) {
        dataopr = rs.getInt(5);
        if (dataopr == 0) {
          organization.setCode(rs.getString(1));
          organization.setParentcode(rs.getString(2));
          organization.setName(rs.getString("orgname"));
          String desc = rs.getString("orgdescripte");
          organization.setSign("1");
          organization.setEmail("");
          organization.setLocation((desc == null || "null".equals(desc)) ? "" : desc);
          organization.setRemark(desc);
          flag = gkOrganization.saveOrganization(organization);
          continue;
        } 
        if (dataopr == 1) {
          organization.setCode(rs.getString(1));
          organization.setParentcode(rs.getString(2));
          organization.setName(rs.getString("orgname"));
          String desc = rs.getString("orgdescripte");
          organization.setSign("1");
          organization.setEmail("");
          organization.setLocation((desc == null || "null".equals(desc)) ? "" : desc);
          organization.setRemark(desc);
          flag = gkOrganization.modOrganization(organization);
          continue;
        } 
        if (dataopr == 2)
          gkOrganization.delOrganization(rs.getString(1)); 
      } 
      rs.close();
      gkOrganization.close();
      GKUserManager gkUserManager = new GKUserManager();
      rs = stmt.executeQuery("select emp.userAccounts,emp.empName,emp.empSex,emp.empBirth,emp.empAddress,emp.empWebAddress,emp.empEmail,emp.empMobilePhone,emp.empBusinessphone,emp.empBusinessFax,emp.empDuty,emp.empZipCode,emp.userIsActive,emp.userPassword,sync.dataopr from org_employee emp ,org_syncrtx sync   where userIsDeleted=0 and useraccounts is not null and useraccounts<>'admin'  and userisactive=1 and useraccounts<>' '  and sync.useraccount = emp.useraccounts  and sync.datatype=1 and domain_id=" + 



          
          this.domainId);
      while (rs.next()) {
        String account = rs.getString(1);
        String name = rs.getString(2);
        String sex = rs.getString(3);
        String birth = rs.getString(4);
        String address = rs.getString(5);
        String webAddress = rs.getString(6);
        String email = rs.getString(7);
        String mobile = rs.getString(8);
        String phone = rs.getString(9);
        String fax = rs.getString(10);
        String position = rs.getString(11);
        String zip = rs.getString(12);
        String active = rs.getString(13);
        String password = rs.getString(14);
        dataopr = rs.getInt(15);
        if (dataopr == 0) {
          user.setAccount(account);
          user.setUgName(this.root);
          user.setName(name);
          user.setSex(sex);
          user.setBirthday((birth == null || "null".equals(birth)) ? "" : birth);
          user.setEmail((email == null || "null".equals(email)) ? "" : email);
          user.setMobile((mobile == null || "null".equals(mobile)) ? "" : mobile);
          user.setOfficeTel((phone == null || "null".equals(phone)) ? "" : phone);
          user.setFax((fax == null || "null".equals(fax)) ? "" : fax);
          user.setWebaddress((webAddress == null || "null".equals(webAddress)) ? "" : webAddress);
          user.setPostcode((zip == null || "null".equals(zip)) ? "" : zip);
          user.setAddress((address == null || "null".equals(address)) ? "" : address);
          user.setPosition((position == null || "null".equals(position)) ? "" : position);
          user.setPwd("");
          user.setDisplayName(name);
          user.setRemark("");
          user.setState(active);
          user.setUg_code("");
          user.setMd5_pwd(password);
          flag = gkUserManager.saveUser(user);
          PreparedStatement pstmt = conn.prepareStatement(
              "update org_employee set imid=? where useraccounts=?");
          String gid = gkUserManager.getUserGID(account);
          pstmt.setString(1, gid);
          pstmt.setString(2, account);
          pstmt.executeUpdate();
          pstmt.close();
          continue;
        } 
        if (dataopr == 1) {
          user.setAccount(account);
          user.setUgName(this.root);
          user.setName(name);
          user.setSex(sex);
          user.setBirthday((birth == null || "null".equals(birth)) ? "" : birth);
          user.setEmail((email == null || "null".equals(email)) ? "" : email);
          user.setMobile((mobile == null || "null".equals(mobile)) ? "" : mobile);
          user.setOfficeTel((phone == null || "null".equals(phone)) ? "" : phone);
          user.setFax((fax == null || "null".equals(fax)) ? "" : fax);
          user.setWebaddress((webAddress == null || "null".equals(webAddress)) ? "" : webAddress);
          user.setPostcode((zip == null || "null".equals(zip)) ? "" : zip);
          user.setAddress((address == null || "null".equals(address)) ? "" : address);
          user.setPosition((position == null || "null".equals(position)) ? "" : position);
          user.setPwd("");
          user.setDisplayName(name);
          user.setRemark("");
          user.setState(active);
          user.setUg_code("");
          user.setMd5_pwd(password);
          flag = gkUserManager.modUser(user);
          continue;
        } 
        if (dataopr == 2)
          flag = gkUserManager.delUser(account); 
      } 
      rs.close();
      gkUserManager.close();
      System.out.println("同步用户完成");
      stmt.executeUpdate("delete from org_syncrtx");
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      try {
        if (conn != null)
          conn.close(); 
      } catch (Exception e) {
        e.printStackTrace();
      } 
      ex.printStackTrace();
    } 
    return flag;
  }
  
  public void delAccount() {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      GKUserManager gkUserManager = new GKUserManager();
      DataSource ds = (new DataSourceBase()).getDataSource();
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select userAccounts from org_employee emp where userIsDeleted=0 and useraccounts is not null and useraccounts<>'admin' and domain_id=" + this.domainId);
      while (rs.next()) {
        String account = rs.getString(1);
        if (!gkUserManager.delUser(account))
          System.out.println("----------------Del-Error--userAccounts=" + account); 
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    try {
      GKUserManager gkUserManager = new GKUserManager();
      GKOrganization gkOrg = new GKOrganization();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select useraccounts from org_employee");
      while (rs.next())
        gkUserManager.delUser(rs.getString(1)); 
      rs.close();
      rs = stmt.executeQuery("select org_id from org_organization");
      while (rs.next())
        gkOrg.delOrganization(rs.getString(1)); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
}
