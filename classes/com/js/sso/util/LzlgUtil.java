package com.js.sso.util;

import com.js.oa.userdb.util.DbOpt;
import java.sql.SQLException;

public class LzlgUtil {
  public static String getUserAccountByEnglistName(String englishName) {
    String useraccounts = "";
    DbOpt db = new DbOpt();
    try {
      useraccounts = db.executeQueryToStr("select max(useraccounts) useraccounts from org_employee where empenglishname='" + englishName + "'");
      db.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return useraccounts;
  }
}
