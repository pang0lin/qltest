package com.js.oa.hr.finance.util;

import com.js.oa.userdb.util.DbOpt;

public class CodeUtil {
  public static String getSAPCode(String codeType, String oa_Code, String defaultValue) {
    String result = "";
    DbOpt db = new DbOpt();
    try {
      String sql = "select sap_code from TEMP_SAP_CODEMAP where codetype='" + codeType + "' and oa_code='" + oa_Code + "'";
      result = db.executeQueryToStr(sql);
      db.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    if ("".equals(result))
      result = defaultValue; 
    return result;
  }
}
