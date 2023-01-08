package com.js.oa.module.util;

import com.js.oa.userdb.util.DbOpt;
import com.js.util.config.SystemCommon;
import com.js.util.util.CharacterTool;
import com.js.util.util.DataSourceBase;
import com.js.util.util.ExcelOperate;
import com.js.util.util.IO2File;
import com.js.util.util.InfoUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.jdom.JDOMException;

public class CustomizeDataImport {
  private Sheet sheet = null;
  
  private String filePath;
  
  public int importDataProcessor(String path, String userId, String userOrg, String collectId) throws BiffException, IOException, Exception {
    int count = 0;
    this.filePath = path;
    String[] outStr = (String[])null;
    FileInputStream file = new FileInputStream(new File(path));
    Workbook workbook = null;
    try {
      workbook = Workbook.getWorkbook(file);
    } catch (Exception e) {
      return 0;
    } finally {
      file.close();
    } 
    if (workbook != null) {
      this.sheet = workbook.getSheet(0);
    } else {
      throw new Exception("####### 查看excel中是否存在sheet(工作表) #######");
    } 
    String tableName = this.sheet.getName();
    DbOpt opt = null;
    try {
      Map<String, Object> map = pickDataToSqlString(tableName, userId, userOrg, collectId);
      outStr = (map.get("sqlArr") == null) ? null : (String[])map.get("sqlArr");
      if (outStr != null && outStr.length > 0) {
        opt = new DbOpt();
        Statement state = opt.getStatement();
        for (int i = 0; i < outStr.length; i++) {
          if (outStr[i] != null && outStr[i].length() > 0) {
            IO2File.printFile("扩展菜单导入sql语句   " + i + ": " + outStr[i], "扩展菜单导入");
            state.addBatch(outStr[i]);
            count++;
          } 
        } 
        state.executeBatch();
        opt.close();
        List<String[]> clobList = (map.get("clobList") == null) ? null : (List<String[]>)map.get("clobList");
        if (clobList != null && clobList.size() > 0) {
          String databaseType = SystemCommon.getDatabaseType();
          for (String[] arr : clobList) {
            if ("oracle".equalsIgnoreCase(databaseType)) {
              InfoUtil.insert_oracle_clob(arr[0], arr[1], arr[2], Long.valueOf(Long.parseLong(arr[3])), arr[4]);
              continue;
            } 
            if ("mysql".equalsIgnoreCase(databaseType))
              InfoUtil.insert_mysql_longtext(arr[0], arr[1], arr[2], Long.valueOf(Long.parseLong(arr[3])), arr[4]); 
          } 
        } 
      } 
      workbook.close();
      file.close();
    } catch (Exception ex) {
      if (opt != null)
        opt.close(); 
      ex.printStackTrace();
    } 
    return count;
  }
  
  public Map<String, String> importDataProcessorForHaier(String path, String userId, String userOrg, String collectId) throws BiffException, IOException, Exception {
    Map<String, String> resultMap = new HashMap<String, String>();
    int count = 0;
    this.filePath = path;
    String[] outStr = (String[])null;
    FileInputStream file = new FileInputStream(new File(path));
    Workbook workbook = null;
    try {
      workbook = Workbook.getWorkbook(file);
    } catch (Exception e) {
      resultMap.put("cnt", "0");
      resultMap.put("reason", "读取模版异常");
      return resultMap;
    } finally {
      file.close();
    } 
    if (workbook != null) {
      this.sheet = workbook.getSheet(0);
    } else {
      throw new Exception("####### 查看excel中是否存在sheet(工作表) #######");
    } 
    String tableName = this.sheet.getName();
    DbOpt opt = null;
    try {
      Map<String, Object> map = pickDataToSqlString(tableName, userId, userOrg, collectId);
      outStr = (map.get("sqlArr") == null) ? null : (String[])map.get("sqlArr");
      String[] checkStr = (map.get("checkStr") == null) ? null : (String[])map.get("checkStr");
      int checkCnt = 0;
      StringBuffer reasons = new StringBuffer();
      if (checkStr != null) {
        opt = new DbOpt();
        Statement state = opt.getStatement();
        for (int k = 0; k < checkStr.length; k++) {
          if (checkStr[k] != null && !"null".equalsIgnoreCase(checkStr[k]))
            if (checkStr[k].contains(";;")) {
              String[] tvls = checkStr[k].split(";;");
              if (tvls.length > 1 && tvls[1] != null) {
                ResultSet rsTmp = state.executeQuery("select count(*) as cnt from jst_3004 where jst_3004_f3053='" + tvls[1] + "' and jst_3004_f3054='" + tvls[0] + "'");
                if (rsTmp.next()) {
                  int tt = rsTmp.getInt("cnt");
                  checkCnt += tt;
                  if (tt > 0)
                    reasons.append("第" + (k + 1) + "行图号或者EO号存在重复;<br/>"); 
                } 
                rsTmp.close();
              } 
            }  
        } 
        opt.close();
      } 
      if (checkCnt > 0) {
        resultMap.put("cnt", "0");
        resultMap.put("reason", reasons.toString());
        return resultMap;
      } 
      if (outStr != null && outStr.length > 0) {
        opt = new DbOpt();
        Statement state = opt.getStatement();
        for (int i = 0; i < outStr.length; i++) {
          if (outStr[i] != null && outStr[i].length() > 0) {
            IO2File.printFile("扩展菜单导入sql语句   " + i + ": " + outStr[i], "扩展菜单导入");
            state.addBatch(outStr[i]);
            count++;
          } 
        } 
        state.executeBatch();
        opt.close();
        List<String[]> clobList = (map.get("clobList") == null) ? null : (List<String[]>)map.get("clobList");
        if (clobList != null && clobList.size() > 0) {
          String databaseType = SystemCommon.getDatabaseType();
          for (String[] arr : clobList) {
            if ("oracle".equalsIgnoreCase(databaseType)) {
              InfoUtil.insert_oracle_clob(arr[0], arr[1], arr[2], Long.valueOf(Long.parseLong(arr[3])), arr[4]);
              continue;
            } 
            if ("mysql".equalsIgnoreCase(databaseType))
              InfoUtil.insert_mysql_longtext(arr[0], arr[1], arr[2], Long.valueOf(Long.parseLong(arr[3])), arr[4]); 
          } 
        } 
      } 
      workbook.close();
      file.close();
    } catch (Exception ex) {
      if (opt != null)
        opt.close(); 
      ex.printStackTrace();
    } 
    resultMap.put("cnt", (new StringBuilder(String.valueOf(count))).toString());
    return resultMap;
  }
  
  private Map<String, Object> pickDataToSqlString(String tableName, String userId, String userOrg, String collectId) throws JDOMException, IOException, BiffException, IOException {
    if (this.sheet != null) {
      int rows = this.sheet.getRows();
      int cols = this.sheet.getColumns();
      Cell cell = null;
      Connection conn = null;
      try {
        String[][] fields = new String[cols][2];
        conn = (new DataSourceBase()).getDataSource().getConnection();
        Statement stmt = conn.createStatement();
        String tableId = "-1";
        ResultSet rs = stmt.executeQuery("select table_id from ttable where table_name='" + tableName.replace(" ", "") + "'");
        if (rs.next())
          tableId = rs.getString(1); 
        rs.close();
        for (int i = 0; i < cols; i++) {
          String fieldName = this.sheet.getCell(i, 0).getContents();
          rs = stmt.executeQuery("select field_name,field_type from tfield where field_desname='" + fieldName + "' and field_table=" + tableId);
          if (rs.next()) {
            fields[i][0] = rs.getString(1);
            fields[i][1] = rs.getString(2);
          } 
          rs.close();
        } 
        stmt.close();
        conn.close();
        String[][] datas = ExcelOperate.getData(new File(this.filePath), 0);
        return createInsert(fields, datas, rows, cols, tableName, userId, 
            userOrg, collectId);
      } catch (Exception ex) {
        if (conn != null)
          try {
            conn.close();
          } catch (Exception err) {
            err.printStackTrace();
          }  
        ex.printStackTrace();
      } 
    } 
    return null;
  }
  
  private boolean checkUnique(String compName, String[] comps) {
    if (comps == null)
      return false; 
    for (int i = 0; i < comps.length; i++) {
      if (compName.equals(comps[i]))
        return true; 
    } 
    return false;
  }
  
  private Map<String, Object> createInsert(String[][] fields, String[][] datas, int rows, int cols, String tableName, String userId, String userOrg, String collectId) throws JDOMException, IOException {
    String[] sqlStr = (String[])null;
    String[] checkStr = (String[])null;
    Map<String, Object> map = new HashMap<String, Object>();
    List<String[]> clobList = (List)new ArrayList<String>();
    if (datas != null && datas.length > 0) {
      String valueTemp = "";
      String id = "";
      DbOpt opt = new DbOpt();
      try {
        String columnList = String.valueOf(tableName) + "_ID," + tableName + "_owner," + tableName + "_date," + tableName + "_org," + tableName + "_relaByInde";
        for (int i = 0; i < cols; i++)
          columnList = String.valueOf(columnList) + "," + fields[i][0]; 
        String databaseType = SystemCommon.getDatabaseType();
        sqlStr = new String[rows];
        if (SystemCommon.getCustomerName().equals("haier") && "jst_3004".equalsIgnoreCase(tableName))
          checkStr = new String[rows]; 
        for (int j = 1; j < rows; j++) {
          if (databaseType.indexOf("oracle") >= 0) {
            id = opt.executeQueryToStr("Select HIBERNATE_SEQUENCE.Nextval From dual");
          } else {
            opt.executeUpdate("update oa_seq set seq_seq = seq_seq + 1");
            id = opt.executeQueryToStr("select seq_seq from oa_seq");
          } 
          if (datas[j][1] != null) {
            String clobContent = "";
            for (int k = 0; k < cols; k++) {
              if ("1000002".equals(fields[k][1])) {
                if ("null".equals(datas[j][k])) {
                  valueTemp = String.valueOf(valueTemp) + "null,";
                } else {
                  valueTemp = String.valueOf(valueTemp) + "'" + CharacterTool.escapeHTMLQuotOther(datas[j][k]) + "',";
                } 
              } else if ("1000003".equals(fields[k][1])) {
                clobContent = CharacterTool.escapeHTMLTagsGW(datas[j][k]);
                if (databaseType.indexOf("oracle") >= 0) {
                  valueTemp = String.valueOf(valueTemp) + "empty_clob(),";
                } else {
                  valueTemp = String.valueOf(valueTemp) + "null,";
                } 
                clobList.add(new String[] { tableName, fields[k][0], String.valueOf(tableName) + "_id", id, clobContent });
              } else if ("null".equals(datas[j][k]) || "".equals(datas[j][k])) {
                valueTemp = String.valueOf(valueTemp) + "null,";
              } else {
                valueTemp = String.valueOf(valueTemp) + datas[j][k] + ",";
              } 
            } 
            String sql = "insert into " + tableName + 
              "(" + columnList + ") values (" + 
              id + ",'" + userId;
            if (databaseType.indexOf("mysql") >= 0)
              sql = String.valueOf(sql) + "', now(), '"; 
            if (databaseType.indexOf("oracle") >= 0)
              sql = String.valueOf(sql) + "', sysdate, '"; 
            sql = String.valueOf(sql) + userOrg + "','" + collectId + "'," + 
              valueTemp.substring(0, valueTemp.lastIndexOf(",")) + 
              ")";
            sqlStr[j] = sql;
            if (checkStr != null) {
              String[] valueTempArr = valueTemp.split(",");
              checkStr[j] = String.valueOf(valueTempArr[3].replaceAll("'", "")) + ";;" + valueTempArr[2].replaceAll("'", "");
            } 
            valueTemp = "";
          } 
        } 
        opt.close();
      } catch (Exception ex) {
        try {
          opt.close();
        } catch (Exception err) {
          err.printStackTrace();
        } 
        ex.printStackTrace();
      } 
    } 
    map.put("clobList", clobList);
    map.put("sqlArr", sqlStr);
    map.put("checkStr", checkStr);
    return map;
  }
}
