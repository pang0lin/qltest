package com.js.ldap;

import com.js.oa.userdb.util.DbOpt;
import com.js.system.util.OperateUserFromBase;
import com.js.util.util.MD5;
import com.wiscom.ldapvalidate.ldapCheck;
import java.util.Map;

public class LdapInterface {
  public String validateUser(String userName, String password) {
    String sql = "SELECT empnumber,useraccounts FROM org_employee WHERE empnumber=? OR empemail=? OR useraccounts=?";
    String[] userInfo = (String[])null;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      String[][] info = dbopt.executeQueryToStrArr2PS(sql, new String[] { userName, userName, userName });
      if (info != null && info.length > 0)
        userInfo = info[0]; 
      dbopt.close();
    } catch (Exception ex) {
      if (dbopt != null)
        try {
          dbopt.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    String validate = "-1";
    Map<String, String> map = null;
    if (userInfo != null) {
      password = "{WMD5}" + (new MD5()).toMD5(password).toLowerCase();
      try {
        String path = System.getProperty("user.dir");
        String filePath = String.valueOf(path) + "/jsconfig/config.properties";
        ldapCheck test = ldapCheck.getInstance(filePath);
        if (test.checkPassword(userInfo[0], password)) {
          validate = userInfo[1];
          System.out.println("用户" + userName + "验证通过！");
        } else {
          map = (new OpenLDAP()).getLdapInfo();
          validate = "0";
          System.out.println("用户" + userName + "密码错误！");
        } 
      } catch (Exception e) {
        validate = "-1";
        System.err.println("用户" + userName + "在Ldap中不存在！");
      } 
    } else {
      System.err.println("用户" + userName + "在OA中不存在！");
      try {
        map = (new OpenLDAP()).getLdapInfo();
        String userSql = ((String)map.get("oneUseSql")).replaceAll("@value@", userName);
        (new OperateUserFromBase()).getUserInfoFromOtherBase(map.get("dataSource"), userSql);
      } catch (Exception err) {
        System.err.println("同步OA中不存在的用户" + userName + "异常！");
        err.printStackTrace();
      } 
    } 
    return validate;
  }
}
