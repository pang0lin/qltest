package com.js.ldap;

import com.js.util.util.DataSourceBase;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class OAToAD extends LDAP {
  public OAToAD() {
    try {
      getContext();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public void insertGroup() throws Exception {
    Statement stmt = null;
    ResultSet rs = null;
    Connection conn = (new DataSourceBase()).getDataSource().getConnection();
    String sql = " select org_id, orgname, orgparentorgid, orgordercode ,orgnamestring, orgstatus from org_organization  where orgstatus!=2 and orgidstring!='0' order by orgidstring";
    try {
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      System.out.println("update starting!!!");
      while (rs.next())
        createGroup(rs.getString(1), 
            rs.getString(2), 
            rs.getString(3), 
            rs.getString(4), 
            rs.getString(5), 
            rs.getString(6)); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      rs.close();
      stmt.close();
      try {
        conn.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
    } 
  }
  
  private boolean createGroup(String orgId, String orgName, String orgParentorGid, String orgOrderCode, String orgNameString, String orgStatus) {
    Attributes attrs = new BasicAttributes(true);
    Attribute objclass = new BasicAttribute("objectclass");
    objclass.add("top");
    objclass.add("organizationalUnit");
    attrs.put(objclass);
    attrs.put("ou", orgName);
    attrs.put("description", orgId);
    attrs.put("l", orgParentorGid);
    attrs.put("street", orgOrderCode);
    if (orgStatus.equals("1")) {
      attrs.put("postalCode", "此组织已被禁用");
    } else if (orgStatus.equals("4")) {
      attrs.put("postalCode", "此组织已被删除");
    } 
    try {
      String ouStr = getGroupOU(orgNameString);
      if (isGroupExist(orgId) == 0 && !orgStatus.equals("4")) {
        this.cnt.createSubcontext(String.valueOf(ouStr) + base, attrs);
      } else if (orgStatus.equals("1")) {
        ModificationItem[] mods = new ModificationItem[3];
        mods[0] = new ModificationItem(
            2, new BasicAttribute(
              "l", orgParentorGid));
        mods[1] = new ModificationItem(
            2, new BasicAttribute(
              "street", orgOrderCode));
        mods[2] = new ModificationItem(
            2, new BasicAttribute(
              "postalCode", "此组织已被禁用"));
        this.cnt
          .modifyAttributes(search_DN_group(orgId), mods);
        this.cnt.rename(search_DN_group(orgId), String.valueOf(ouStr) + 
            base);
      } else if (orgStatus.equals("4")) {
        ModificationItem[] mods = new ModificationItem[3];
        mods[0] = new ModificationItem(
            2, new BasicAttribute(
              "l", orgParentorGid));
        mods[1] = new ModificationItem(
            2, new BasicAttribute(
              "street", orgOrderCode));
        mods[2] = new ModificationItem(
            2, new BasicAttribute(
              "postalCode", "此组织已被删除"));
        if (!search_DN_group(orgId).equals("none")) {
          this.cnt.modifyAttributes(search_DN_group(orgId), 
              mods);
          this.cnt.rename(search_DN_group(orgId), String.valueOf(ouStr) + 
              base);
        } 
      } else {
        ModificationItem[] mods = new ModificationItem[3];
        mods[0] = new ModificationItem(
            2, new BasicAttribute(
              "l", orgParentorGid));
        mods[1] = new ModificationItem(
            2, new BasicAttribute(
              "street", orgOrderCode));
        mods[2] = new ModificationItem(
            2, new BasicAttribute(
              "postalCode", " "));
        this.cnt.modifyAttributes(search_DN_group(orgId), mods);
        this.cnt.rename(search_DN_group(orgId), String.valueOf(ouStr) + 
            base);
      } 
      return true;
    } catch (NamingException e1) {
      e1.printStackTrace();
      System.out.println("组织" + orgName + "同步未成功！");
      return false;
    } 
  }
  
  private int isGroupExist(String orgId) {
    String filter = "(&(objectClass=organizationalUnit)(description=" + orgId + "))";
    NamingEnumeration<SearchResult> namingEnum = null;
    String[] atts = { "description" };
    try {
      SearchControls searchCons = new SearchControls();
      searchCons.setSearchScope(2);
      searchCons.setCountLimit(0L);
      searchCons.setTimeLimit(0);
      searchCons.setReturningAttributes(atts);
      namingEnum = this.cnt.search(base, filter, searchCons);
      if (namingEnum.hasMore())
        return 1; 
      return 0;
    } catch (Exception e) {
      e.printStackTrace();
      return 1;
    } 
  }
  
  private String getGroupOU(String orgNameString) {
    String ouStr = "";
    if (orgNameString.indexOf(".") > 0) {
      String[] ouArray = orgNameString.split("\\.");
      for (int i = ouArray.length - 1; i >= 0; i--)
        ouStr = String.valueOf(ouStr) + "ou=" + ouArray[i] + ","; 
    } else {
      ouStr = "ou=" + orgNameString + ",";
    } 
    return ouStr;
  }
  
  public int updatePassword(String newPassword, String userName) {
    if (getUseCert() != 0) {
      ModificationItem[] mods = new ModificationItem[1];
      String newQuotedPassword = "\"" + newPassword + "\"";
      byte[] newUnicodePassword = (byte[])null;
      try {
        newUnicodePassword = newQuotedPassword.getBytes("UTF-16LE");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
        return 1;
      } 
      mods[0] = new ModificationItem(2, 
          new BasicAttribute("unicodePwd", newUnicodePassword));
      try {
        String userDN = search_distinguishedName(userName);
        if (!userDN.equals("none"))
          this.cnt.modifyAttributes(search_distinguishedName(userName), mods); 
      } catch (NamingException e) {
        e.printStackTrace();
        return 1;
      } 
    } 
    return 0;
  }
  
  private String search_distinguishedName(String userName) {
    String searchFilter = "(&(objectClass=user)(sAMAccountName=" + userName + 
      "))";
    String[] returnedAtts = { "distinguishedName" };
    try {
      SearchControls searchCtls = new SearchControls();
      searchCtls.setSearchScope(2);
      searchCtls.setReturningAttributes(returnedAtts);
      NamingEnumeration<SearchResult> answer = this.cnt.search(base, searchFilter, 
          searchCtls);
      if (answer.hasMore()) {
        SearchResult sr = answer.next();
        Attributes attrs = sr.getAttributes();
        if (attrs != null) {
          NamingEnumeration<? extends Attribute> ae = attrs.getAll();
          Attribute attr = ae.next();
          NamingEnumeration<?> e = attr.getAll();
          return (String)e.next();
        } 
      } 
    } catch (NamingException e) {
      System.out
        .println("用户" + userName + "已被删除或不存在！");
      return "none";
    } 
    return "none";
  }
  
  private String search_DN_group(String orgId) {
    String searchFilter = "(&(objectClass=organizationalUnit)(description=" + orgId + "))";
    String[] returnedAtts = { "distinguishedName" };
    try {
      SearchControls searchCtls = new SearchControls();
      searchCtls.setSearchScope(2);
      searchCtls.setReturningAttributes(returnedAtts);
      NamingEnumeration<SearchResult> answer = this.cnt.search(base, searchFilter, 
          searchCtls);
      if (answer.hasMore()) {
        SearchResult sr = answer.next();
        Attributes attrs = sr.getAttributes();
        if (attrs != null) {
          NamingEnumeration<? extends Attribute> ae = attrs.getAll();
          Attribute attr = ae.next();
          NamingEnumeration<?> e = attr.getAll();
          return (String)e.next();
        } 
      } 
    } catch (NamingException e) {
      System.out
        .println("组织id: " + orgId + " 已被删除或不存在！");
      return "none";
    } 
    return "none";
  }
  
  private int deleteGroup(String orgId) {
    String group_dn = search_DN_group(orgId);
    try {
      if (!group_dn.equals("none")) {
        updateUserStatus();
        hasJunior(group_dn);
      } 
      return 0;
    } catch (Exception e) {
      e.printStackTrace();
      return 1;
    } 
  }
  
  public void hasJunior(String base) {
    NamingEnumeration<SearchResult> namingEnum = null;
    String[] attrs = { "distinguishedName" };
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
        SearchResult sr = null;
        if (i == 1)
          sr = namingEnum.next(); 
        if (namingEnum.hasMore()) {
          i++;
          sr = namingEnum.next();
        } 
        Attributes attrs_ = sr.getAttributes();
        if (attrs_ != null && i != 1) {
          NamingEnumeration<? extends Attribute> ae = attrs_.getAll();
          Attribute attr = ae.next();
          NamingEnumeration<?> e = attr.getAll();
          hasJunior((String)e.next());
        } 
      } 
      this.cnt.unbind(base);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  private void updateUserStatus() {
    Statement stmt = null;
    ResultSet rs = null;
    Connection conn = null;
    String sql = " select emp_id,useraccounts from org_employee  where userisdeleted=1 or userisactive=0 ";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        int isAccuess = deleteUser(rs.getString(2));
        if (isAccuess == 1)
          System.out.println("用户" + rs.getString(2) + "删除失败！"); 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        rs.close();
        stmt.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
      try {
        conn.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
    } 
  }
  
  private int deleteUser(String userAccount) {
    String userDN = search_distinguishedName(userAccount);
    try {
      if (!userDN.equals("none"))
        this.cnt.unbind(userDN); 
    } catch (NamingException e) {
      e.printStackTrace();
      return 1;
    } 
    return 0;
  }
  
  public void createUsers() {
    Statement stmt = null;
    ResultSet rs = null;
    Connection conn = null;
    String sql = " select e.emp_id,e.useraccounts,e.empname,e.empnumber,e.empsex, e.empbirth,e.empphone,e.empmobilephone,e.empbusinessfax,e.empemail, e.empidcard,e.empduty,e.empstatus,e.userpassword,e.userordercode, e.jobstatus,e.personalkind,e.sidelineorg,u.org_id,e.empbusinessphone, e.userisdeleted,e.userisactive,oo.orgnamestring  from org_employee e,org_organization_user u,org_organization oo where u.emp_id=e.emp_id and u.org_id=oo.org_id";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("emp_id", (rs.getString(1) == null) ? "" : rs.getString(1));
        map.put("useraccounts", (rs.getString(2) == null) ? "" : rs.getString(2));
        map.put("empname", (rs.getString(3) == null) ? "" : rs.getString(3));
        map.put("empnumber", (rs.getString(4) == null) ? "" : rs.getString(4));
        map.put("empsex", (rs.getString(5) == null) ? "" : rs.getString(5));
        map.put("empbirth", (rs.getString(6) == null) ? "" : rs.getString(6));
        map.put("empphone", (rs.getString(7) == null) ? "" : rs.getString(7));
        map.put("empmobilephone", (rs.getString(8) == null) ? "" : rs.getString(8));
        map.put("empbusinessfax", (rs.getString(9) == null) ? "" : rs.getString(9));
        map.put("empmail", (rs.getString(10) == null) ? "" : rs.getString(10));
        map.put("empidcard", (rs.getString(11) == null) ? "" : rs.getString(11));
        map.put("empduty", (rs.getString(12) == null) ? "" : rs.getString(12));
        map.put("empstatus", (rs.getString(13) == null) ? "" : rs.getString(13));
        map.put("userpassword", (rs.getString(14) == null) ? "" : rs.getString(14));
        map.put("userordercode", (rs.getString(15) == null) ? "" : rs.getString(15));
        map.put("jobstatus", (rs.getString(16) == null) ? "" : rs.getString(16));
        String personalKindId = rs.getString(17);
        if (personalKindId != null && !personalKindId.equals("")) {
          map.put("personalkind", getPersonalName(personalKindId));
        } else {
          map.put("personalkind", "");
        } 
        map.put("sidelineorg", (rs.getString(18) == null) ? "" : rs.getString(18));
        map.put("org_id", (rs.getString(19) == null) ? "" : rs.getString(19));
        map.put("empbusinessphone", (rs.getString(20) == null) ? "" : rs.getString(20));
        map.put("userisdeleted", (rs.getString(21) == null) ? "" : rs.getString(21));
        map.put("userisactive", (rs.getString(22) == null) ? "" : rs.getString(22));
        map.put("orgNameString", rs.getString(23));
        if (isUserExist(rs.getString(2)) == 1) {
          modifyUser(map);
          continue;
        } 
        if (!((String)map.get("userisdeleted")).equals("1"))
          createUser(map); 
      } 
      System.out.println("update ended!!!");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        rs.close();
        stmt.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
      try {
        conn.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
    } 
  }
  
  public int createUser(Map map) throws Exception {
    Attributes attrs = new BasicAttributes(true);
    attrs.put("objectClass", "user");
    attrs.put("givenName", map.get("emp_id").toString());
    attrs.put("sAMAccountName", map.get("useraccounts").toString());
    attrs.put("sn", 
        map.get("empname").toString().equals("") ? " " : map.get("empname").toString());
    attrs.put("description", 
        map.get("empnumber").toString().equals("") ? " " : map.get("empnumber").toString());
    attrs.put("initials", 
        map.get("empsex").toString().equals("") ? " " : map.get("empsex").toString());
    attrs.put("ipPhone", 
        map.get("empbirth").toString().equals("") ? " " : map.get("empbirth").toString());
    attrs.put("homePhone", 
        map.get("empphone").toString().equals("") ? " " : map.get("empphone").toString());
    attrs.put("mobile", 
        map.get("empmobilephone").toString().equals("") ? " " : map.get("empmobilephone").toString());
    attrs.put("facsimileTelephoneNumber", 
        map.get("empbusinessfax").toString().equals("") ? " " : map.get("empbusinessfax").toString());
    attrs.put("mail", 
        map.get("empmail").toString().equals("") ? " " : map.get("empmail").toString());
    attrs.put("streetAddress", 
        map.get("empidcard").toString().equals("") ? " " : map.get("empidcard").toString());
    attrs.put("title", 
        map.get("empduty").toString().equals("") ? " " : map.get("empduty").toString());
    String newQuotedPassword = "\"111111\"";
    byte[] newUnicodePassword = (byte[])null;
    try {
      newUnicodePassword = newQuotedPassword.getBytes("UTF-16LE");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      return 1;
    } 
    attrs.put("unicodePwd", newUnicodePassword);
    attrs.put("wwwHomePage", 
        map.get("userordercode").toString().equals("") ? " " : map.get("userordercode").toString());
    attrs.put("info", 
        map.get("jobstatus").toString().equals("") ? " " : map.get("jobstatus").toString());
    attrs.put("pager", 
        map.get("personalkind").toString().equals("") ? " " : map.get("personalkind").toString());
    attrs.put("department", 
        map.get("sidelineorg").toString().equals("") ? " " : map.get("sidelineorg").toString());
    attrs.put("postalCode", 
        map.get("org_id").toString().equals("") ? " " : map.get("org_id").toString());
    attrs.put("telephoneNumber", 
        map.get("empbusinessphone").toString().equals("") ? " " : map.get("empbusinessphone").toString());
    if (((String)map.get("userisactive")).equals("0")) {
      attrs.put("postOfficeBox", "此用户已被禁用");
    } else {
      attrs.put("postOfficeBox", " ");
    } 
    if (((String)map.get("userisdeleted")).equals("1")) {
      attrs.put("postalCode", "此用户已被删除");
    } else {
      attrs.put("postalCode", " ");
    } 
    int UF_ACCOUNTDISABLE = 512;
    int UF_PASSWD_NOTREQD = 32;
    int UF_NORMAL_ACCOUNT = 512;
    int UF_PASSWORD_EXPIRED = 66048;
    attrs.put("userAccountControl", Integer.toString(UF_NORMAL_ACCOUNT + 
          UF_PASSWD_NOTREQD + UF_PASSWORD_EXPIRED + UF_ACCOUNTDISABLE));
    try {
      String userDN = search_DN_group(map.get("org_id").toString());
      String userCN = "cn=" + map.get("useraccounts").toString() + ",";
      System.out.println(userCN);
      this.cnt.createSubcontext(String.valueOf(userCN) + userDN, attrs);
    } catch (NamingException e1) {
      e1.printStackTrace();
      System.out.println("用户" + map.get("useraccounts").toString() + "同步未成功！");
      return 1;
    } 
    return 0;
  }
  
  public int modifyUser(Map map) {
    ModificationItem[] mods = new ModificationItem[19];
    mods[0] = new ModificationItem(2, 
        new BasicAttribute("givenName", map.get("emp_id").toString()));
    mods[1] = new ModificationItem(2, 
        new BasicAttribute("sn", 
          map.get("empname").toString().equals("") ? " " : map.get("empname").toString()));
    mods[2] = new ModificationItem(2, 
        new BasicAttribute("description", 
          map.get("empnumber").toString().equals("") ? " " : map.get("empnumber").toString()));
    mods[3] = new ModificationItem(2, 
        new BasicAttribute("initials", 
          map.get("empsex").toString().equals("") ? " " : map.get("empsex").toString()));
    mods[4] = new ModificationItem(2, 
        new BasicAttribute("ipPhone", 
          map.get("empbirth").toString().equals("") ? " " : map.get("empbirth").toString()));
    mods[5] = new ModificationItem(2, 
        new BasicAttribute("homePhone", 
          map.get("empphone").toString().equals("") ? " " : map.get("empphone").toString()));
    mods[6] = new ModificationItem(2, 
        new BasicAttribute("mobile", 
          map.get("empmobilephone").toString().equals("") ? " " : map.get("empmobilephone").toString()));
    mods[7] = new ModificationItem(2, 
        new BasicAttribute("facsimileTelephoneNumber", 
          map.get("empbusinessfax").toString().equals("") ? " " : map.get("empbusinessfax").toString()));
    mods[8] = new ModificationItem(2, 
        new BasicAttribute("mail", 
          map.get("empmail").toString().equals("") ? " " : map.get("empmail").toString()));
    mods[9] = new ModificationItem(2, 
        new BasicAttribute("streetAddress", 
          map.get("empidcard").toString().equals("") ? " " : map.get("empidcard").toString()));
    mods[10] = new ModificationItem(2, 
        new BasicAttribute("title", 
          map.get("empduty").toString().equals("") ? " " : map.get("empduty").toString()));
    mods[11] = new ModificationItem(2, 
        new BasicAttribute("wwwHomePage", 
          map.get("userordercode").toString().equals("") ? " " : map.get("userordercode").toString()));
    mods[12] = new ModificationItem(2, 
        new BasicAttribute("info", 
          map.get("jobstatus").toString().equals("") ? " " : map.get("jobstatus").toString()));
    mods[13] = new ModificationItem(2, 
        new BasicAttribute("pager", 
          map.get("personalkind").toString().equals("") ? " " : map.get("personalkind").toString()));
    mods[14] = new ModificationItem(2, 
        new BasicAttribute("department", 
          map.get("sidelineorg").toString().equals("") ? " " : map.get("sidelineorg").toString()));
    mods[15] = new ModificationItem(2, 
        new BasicAttribute("postalCode", 
          map.get("org_id").toString().equals("") ? " " : map.get("org_id").toString()));
    mods[16] = new ModificationItem(2, 
        new BasicAttribute("telephoneNumber", 
          map.get("empbusinessphone").toString().equals("") ? " " : map.get("empbusinessphone").toString()));
    if (((String)map.get("userisactive")).equals("0")) {
      mods[17] = new ModificationItem(2, 
          new BasicAttribute("postOfficeBox", "此用户已被禁用"));
    } else {
      mods[17] = new ModificationItem(2, 
          new BasicAttribute("postOfficeBox", " "));
    } 
    if (((String)map.get("userisdeleted")).equals("1")) {
      mods[18] = new ModificationItem(2, 
          new BasicAttribute("postalCode", "此用户已被删除"));
    } else {
      mods[18] = new ModificationItem(2, 
          new BasicAttribute("postalCode", " "));
    } 
    try {
      String dn = search_distinguishedName(map.get("useraccounts").toString());
      this.cnt.modifyAttributes(dn, mods);
      String newDN = "CN=" + map.get("useraccounts").toString() + "," + getGroupOU(map.get("orgNameString").toString()) + base;
      this.cnt.rename(dn, newDN);
    } catch (NamingException e) {
      e.printStackTrace();
    } 
    return 0;
  }
  
  private int isUserExist(String userAccount) {
    String filter = "(&(objectClass=user)(sAMAccountName=" + userAccount + "))";
    NamingEnumeration<SearchResult> namingEnum = null;
    String[] atts = { "sAMAccountName" };
    try {
      SearchControls searchCons = new SearchControls();
      searchCons.setSearchScope(2);
      searchCons.setCountLimit(0L);
      searchCons.setTimeLimit(0);
      searchCons.setReturningAttributes(atts);
      namingEnum = this.cnt.search(base, filter, searchCons);
      if (namingEnum.hasMore())
        return 1; 
      return 0;
    } catch (Exception e) {
      e.printStackTrace();
      return 1;
    } 
  }
  
  private String getUserGroupId(String userId) {
    Statement stmt = null;
    ResultSet rs = null;
    Connection conn = null;
    String groupId = "";
    String sql = " select org_id  from org_organization_user  where emp_id=" + 
      
      userId;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      if (rs.next())
        groupId = rs.getString(1); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        rs.close();
        stmt.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
      try {
        conn.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
    } 
    return groupId;
  }
  
  private String getPersonalName(String personalId) {
    Statement stmt = null;
    ResultSet rs = null;
    Connection conn = null;
    String personalKindName = "";
    String sql = " select kind_name  from oa_personal_kind  where kind_id=" + 
      
      personalId;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      if (rs.next())
        personalKindName = rs.getString(1); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        rs.close();
        stmt.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
      try {
        conn.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
    } 
    return personalKindName;
  }
  
  public String canUseAD() {
    String path = System.getProperty("user.dir");
    String filePath = String.valueOf(path) + "/jsconfig/sysconfig.xml";
    String useLDAP = "";
    SAXBuilder builder = new SAXBuilder();
    Document doc = null;
    try {
      doc = builder.build(filePath);
    } catch (JDOMException e1) {
      e1.printStackTrace();
    } catch (IOException e1) {
      e1.printStackTrace();
    } 
    Element ldapConfig = doc.getRootElement().getChild("LdapConfig");
    if (ldapConfig != null)
      useLDAP = ldapConfig.getAttribute("use").getValue(); 
    return useLDAP;
  }
}
