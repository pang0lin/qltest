package com.js.oa.zky.service;

import com.js.oa.zky.bean.ZkyAjaxBean;
import com.js.system.util.StaticParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ZkyAjaxBD {
  public String recordIsExist(Map<String, String> map) {
    if (map.get("fields") != null && !"".equals(map.get("fields")) && !"null".equalsIgnoreCase(map.get("fields"))) {
      ZkyAjaxBean bean = new ZkyAjaxBean();
      List<String[]> fieldInfo = bean.getFieldInfo(map.get("fields"), map.get("pageId"));
      String tableName = ((String[])fieldInfo.get(0))[0];
      String sql = "select " + tableName + "_id from " + tableName + " where 1=1 ";
      String whereSql = "";
      List<String> preParam = new ArrayList<String>();
      for (int i = 0; i < fieldInfo.size(); i++) {
        String[] fieldSingle = fieldInfo.get(i);
        if ("1000000".equals(fieldSingle[5]) || "1000001".equals(fieldSingle[5])) {
          if (map.get(fieldSingle[3]) != null && !"".equals(map.get(fieldSingle[3])) && !"null".equalsIgnoreCase(map.get(fieldSingle[3])))
            whereSql = String.valueOf(whereSql) + " and " + fieldSingle[3] + "=" + (String)map.get(fieldSingle[3]); 
        } else {
          whereSql = String.valueOf(whereSql) + " and " + fieldSingle[3] + "=?";
          preParam.add(((String)map.get(fieldSingle[3])).replace("'", "＇"));
        } 
      } 
      if (map.get("recordId") == null || "".equals(map.get("recordId")) || "null".equalsIgnoreCase(map.get("recordId"))) {
        sql = String.valueOf(sql) + whereSql;
      } else {
        sql = String.valueOf(sql) + " and " + tableName + "_id<>" + (String)map.get("recordId") + whereSql;
      } 
      return bean.recordIsExist(sql, preParam);
    } 
    return "no field";
  }
  
  public String getNumber(String empId) {
    return StaticParam.getEmpNumberByEmpId(empId);
  }
  
  public String getHidden(String empId, String tableName) {
    String fieldRx = tableName.contains("_") ? tableName.split("_")[1] : tableName;
    String sql = "SELECT " + tableName + "_id FROM " + tableName + " WHERE (" + fieldRx + "_mqzt=1 or " + fieldRx + "_mqzt=2) AND " + fieldRx + "_gh='" + 
      StaticParam.getEmpNumberByEmpId(empId) + "'";
    return (new ZkyAjaxBean()).getHidden(sql);
  }
}
