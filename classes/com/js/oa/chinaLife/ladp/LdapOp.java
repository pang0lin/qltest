package com.js.oa.chinaLife.ladp;

import com.js.oa.chinaLife.tbUser.UserData;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class LdapOp {
  public boolean opLdap(UserData user, String flag) {
    String uid = user.getUserCode();
    boolean reslut = false;
    OperateLdap ol = new OperateLdap();
    if ("1".equals(flag)) {
      Map<String, String> attMap = getLdapMap(user);
      reslut = ol.addUser(uid, attMap);
    } else if ("2".equals(flag)) {
      Map<String, String> attMap = getLdapMap(user);
      reslut = ol.updateUser(uid, attMap);
    } else {
      reslut = ol.deleteUser(uid);
    } 
    return reslut;
  }
  
  public boolean updatePassword(String uid, String password) {
    boolean reslut = false;
    Map<String, String> attMap = new HashMap<String, String>();
    attMap.put("userPassword", password);
    reslut = (new OperateLdap()).updateUser(uid, attMap);
    return reslut;
  }
  
  private Map<String, String> getLdapMap(UserData user) {
    Map<String, String> map = new HashMap<String, String>();
    String password = user.getPassword();
    if (password != null && !"".equals(password) && !"".equals(password))
      map.put("userPassword", user.getPassword()); 
    map.put("sn", user.getUserName());
    String sapuid = (user.getUserCodeSAP() == null) ? getSapuid(user.getUserCode()) : user.getUserCodeSAP();
    if (!sapuid.equals(""))
      map.put("sapuid", sapuid); 
    map.put("email", (user.geteMail() == null) ? "" : user.geteMail());
    return map;
  }
  
  private String getSapuid(String accounts) {
    String sql = "SELECT o.sap_id FROM ORG_EMP_OTHERINFO o JOIN org_employee e ON o.emp_id=e.emp_id WHERE useraccounts='" + accounts + "'";
    DataSourceBase base = new DataSourceBase();
    String sapuid = "";
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      if (rs.next())
        sapuid = (rs.getString(1) == null) ? "" : rs.getString(1); 
      base.end();
    } catch (Exception e) {
      base.end();
    } 
    return sapuid;
  }
}
