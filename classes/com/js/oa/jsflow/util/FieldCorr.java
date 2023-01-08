package com.js.oa.jsflow.util;

import com.js.oa.userdb.bean.BaseSetEJBBean;
import com.js.oa.userdb.util.RS;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import com.js.util.util.eval.ExpressionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class FieldCorr {
  public void saveField(HttpServletRequest request, String flowId, int num, String fieldType) {
    ArrayList<String[]> list = new ArrayList();
    String delete = "";
    String operate = "";
    String condition = "";
    String zhuanyi = "";
    String isrun = "";
    String[][] condStrings = new String[num][7];
    for (int i = 0; i < num; i++) {
      if (request.getParameterValues("field" + i) != null) {
        String[] fields = request.getParameterValues("field" + i);
        for (int j = 0; j < fields.length; j++) {
          String[] fieldstr = fields[j].split(",");
          list.add(fieldstr);
        } 
      } 
      delete = String.valueOf(delete) + request.getParameter("deleteId" + i);
      operate = request.getParameter("operate" + i);
      condition = request.getParameter("condition" + i);
      zhuanyi = request.getParameter("zhuanyi" + i);
      condStrings[i][0] = request.getParameter("condid" + i);
      condStrings[i][1] = flowId;
      condStrings[i][2] = (new StringBuilder(String.valueOf(i))).toString();
      condStrings[i][3] = operate;
      condStrings[i][4] = condition;
      condStrings[i][5] = fieldType;
      condStrings[i][6] = zhuanyi;
    } 
    isrun = (request.getParameter("isrun") == null) ? "" : request.getParameter("isrun");
    if (!delete.equals(""))
      delete = delete.substring(0, delete.length() - 1); 
    conditionUpdate(condStrings, isrun);
    save(list, flowId, delete, fieldType);
  }
  
  public void save(List<String[]> fieldList, String flowId, String delete, String fieldType) {
    DataSourceBase base = new DataSourceBase();
    PreparedStatement pstmt = null;
    PreparedStatement pstmt2 = null;
    Connection conn = null;
    Statement stmt = null;
    String sql = "", sql1 = "";
    try {
      conn = base.getDataSource().getConnection();
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("oracle") >= 0) {
        sql = "INSERT INTO jsf_corrfield (fieldid,oatable,oafield,basedata,basetable,basefield,fieldtype,showtype,processId,tfield_id,fieldshowtype) VALUES (hibernate_sequence.nextval,?,?,?,?,?,?,?,?,?,?)";
        pstmt = conn.prepareStatement(sql);
        sql1 = "update jsf_corrfield set oatable=?,oafield=?,Basedata=?,basetable=?,basefield=?,fieldtype=?,showtype=?,processid=?,tfield_id=?,fieldshowType=? where fieldid=?";
        pstmt2 = conn.prepareStatement(sql1);
        for (int x = 0; x < fieldList.size(); x++) {
          String[] charu = fieldList.get(x);
          if (charu[0].equals("-1")) {
            pstmt.setString(1, charu[1]);
            pstmt.setString(2, charu[2]);
            pstmt.setString(3, charu[3]);
            pstmt.setString(4, charu[4]);
            pstmt.setString(5, charu[5]);
            pstmt.setString(6, charu[6]);
            pstmt.setString(7, charu[7]);
            pstmt.setString(8, flowId);
            pstmt.setString(9, charu[8]);
            pstmt.setString(10, fieldType);
            pstmt.executeUpdate();
          } else {
            pstmt2.setString(1, charu[1]);
            pstmt2.setString(2, charu[2]);
            pstmt2.setString(3, charu[3]);
            pstmt2.setString(4, charu[4]);
            pstmt2.setString(5, charu[5]);
            pstmt2.setString(6, charu[6]);
            pstmt2.setString(7, charu[7]);
            pstmt2.setString(8, flowId);
            pstmt2.setString(9, charu[8]);
            pstmt2.setString(10, fieldType);
            pstmt2.setString(11, charu[0]);
            pstmt2.executeUpdate();
          } 
        } 
        pstmt.close();
        pstmt2.close();
      } else {
        sql = "INSERT INTO jsf_corrfield (oatable,oafield,basedata,basetable,basefield,fieldtype,showtype,processId,tfield_id,fieldshowtype) VALUES (?,?,?,?,?,?,?,?,?,?)";
        pstmt = conn.prepareStatement(sql);
        sql1 = "update jsf_corrfield set oatable=?,oafield=?,Basedata=?,basetable=?,basefield=?,fieldtype=?,showtype=?,processid=?,tfield_id=?,fieldshowType=? where fieldid=?";
        pstmt2 = conn.prepareStatement(sql1);
        for (int x = 0; x < fieldList.size(); x++) {
          String[] charu = fieldList.get(x);
          if (charu[0].equals("-1")) {
            pstmt.setString(1, charu[1]);
            pstmt.setString(2, charu[2]);
            pstmt.setString(3, charu[3]);
            pstmt.setString(4, charu[4]);
            pstmt.setString(5, charu[5]);
            pstmt.setString(6, charu[6]);
            pstmt.setString(7, charu[7]);
            pstmt.setString(8, flowId);
            pstmt.setString(9, charu[8]);
            pstmt.setString(10, fieldType);
            pstmt.executeUpdate();
          } else {
            pstmt2.setString(1, charu[1]);
            pstmt2.setString(2, charu[2]);
            pstmt2.setString(3, charu[3]);
            pstmt2.setString(4, charu[4]);
            pstmt2.setString(5, charu[5]);
            pstmt2.setString(6, charu[6]);
            pstmt2.setString(7, charu[7]);
            pstmt2.setString(8, flowId);
            pstmt2.setString(9, charu[8]);
            pstmt2.setString(10, fieldType);
            pstmt2.setString(11, charu[0]);
            pstmt2.executeUpdate();
          } 
        } 
        pstmt.close();
        pstmt2.close();
      } 
      stmt = conn.createStatement();
      if (!"".equals(delete)) {
        sql = "DELETE FROM jsf_corrfield WHERE fieldId IN (" + delete + ")";
        stmt.executeUpdate(sql);
      } 
      stmt.close();
      conn.close();
    } catch (Exception e) {
      try {
        if (pstmt != null)
          pstmt.close(); 
        if (pstmt2 != null)
          pstmt2.close(); 
        if (stmt != null)
          stmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
      e.printStackTrace();
    } 
  }
  
  public void conditionUpdate(String[][] condStrings, String isrun) {
    DataSourceBase base = new DataSourceBase();
    PreparedStatement pstmt = null;
    PreparedStatement pstmt2 = null;
    Statement stmt = null;
    Connection conn = null;
    try {
      conn = base.getDataSource().getConnection();
      String sql = "", sql1 = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("oracle") >= 0) {
        sql = "insert into jsf_fieldcondition (condition_id,showtype,ProcessId,operate,conditions,fieldshowtype,zhuanyi) values (hibernate_sequence.nextval,?,?,?,?,?,?)";
        pstmt = conn.prepareStatement(sql);
        sql1 = "update jsf_fieldcondition set ProcessId=?,showtype=?,operate=?,conditions=?,fieldshowType=?,zhuanyi=? where condition_id=?";
        pstmt2 = conn.prepareStatement(sql1);
        for (int i = 0; i < condStrings.length; i++) {
          if ("-1".equals(condStrings[i][0])) {
            pstmt.setString(1, condStrings[i][2]);
            pstmt.setString(2, condStrings[i][1]);
            pstmt.setString(3, condStrings[i][3]);
            pstmt.setString(4, condStrings[i][4]);
            pstmt.setString(5, condStrings[i][5]);
            pstmt.setString(6, condStrings[i][6]);
            pstmt.executeUpdate();
          } else {
            pstmt2.setString(1, condStrings[i][1]);
            pstmt2.setString(2, condStrings[i][2]);
            pstmt2.setString(3, condStrings[i][3]);
            pstmt2.setString(4, condStrings[i][4]);
            pstmt2.setString(5, condStrings[i][5]);
            pstmt2.setString(6, condStrings[i][6]);
            pstmt2.setString(7, condStrings[i][0]);
            pstmt2.executeUpdate();
          } 
        } 
        pstmt.close();
        pstmt2.close();
      } else {
        sql = "insert into jsf_fieldcondition (showtype,ProcessId,operate,conditions,fieldshowtype,zhuanyi) values (?,?,?,?,?,?)";
        pstmt = conn.prepareStatement(sql);
        sql1 = "update jsf_fieldcondition set ProcessId=?,showtype=?,operate=?,conditions=?,fieldshowType=?,zhuanyi=? where condition_id=?";
        pstmt2 = conn.prepareStatement(sql1);
        for (int i = 0; i < condStrings.length; i++) {
          if ("-1".equals(condStrings[i][0])) {
            pstmt.setString(1, condStrings[i][2]);
            pstmt.setString(2, condStrings[i][1]);
            pstmt.setString(3, condStrings[i][3]);
            pstmt.setString(4, condStrings[i][4]);
            pstmt.setString(5, condStrings[i][5]);
            pstmt.setString(6, condStrings[i][6]);
            pstmt.executeUpdate();
          } else {
            pstmt2.setString(1, condStrings[i][1]);
            pstmt2.setString(2, condStrings[i][2]);
            pstmt2.setString(3, condStrings[i][3]);
            pstmt2.setString(4, condStrings[i][4]);
            pstmt2.setString(5, condStrings[i][5]);
            pstmt2.setString(6, condStrings[i][6]);
            pstmt2.setString(7, condStrings[i][0]);
            pstmt2.executeUpdate();
          } 
        } 
        pstmt.close();
        pstmt2.close();
      } 
      String updateSql = "update jsf_fieldcondition set isrun='" + isrun + "' where processId='" + condStrings[0][1] + "' and " + 
        "showtype='0' and fieldshowType='" + condStrings[0][5] + "'";
      stmt = conn.createStatement();
      stmt.executeUpdate(updateSql);
      conn.close();
    } catch (Exception e) {
      try {
        if (stmt != null)
          stmt.close(); 
        if (pstmt != null)
          pstmt.close(); 
        if (pstmt2 != null)
          pstmt2.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
      e.printStackTrace();
    } 
  }
  
  public boolean isRele(String flowId, String type) {
    DataSourceBase dataSourceBase = new DataSourceBase();
    ResultSet rs = null;
    boolean flag = false;
    try {
      dataSourceBase.begin();
      String sql = "select fieldid from jsf_corrfield where ProcessId=" + flowId + " and fieldshowType='" + type + "'";
      rs = dataSourceBase.executeQuery(sql);
      if (rs.next())
        flag = true; 
      rs.close();
      dataSourceBase.end();
    } catch (Exception e) {
      dataSourceBase.end();
      e.printStackTrace();
    } 
    return flag;
  }
  
  public void selectField(String flowId, String record, String type) {
    DataSourceBase dataSourceBase = new DataSourceBase();
    ResultSet rs = null;
    int num = 0;
    int tableNum = 0;
    int fieldNum = 0;
    String[] selects = new String[2];
    String[] dataTable = new String[3];
    String insertValue = "";
    String cond = "";
    try {
      dataSourceBase.begin();
      String sql = "";
      boolean isRun = false;
      sql = "SELECT DISTINCT c.oatable,f.isrun FROM jsf_corrField c,jsf_fieldcondition f WHERE c.ProcessId=f.ProcessId AND c.showtype = f.showtype AND c.fieldShowType=f.fieldshowType and f.showtype=0 and f.processId='" + 
        flowId + "'" + 
        " and f.fieldshowType='" + type + "'";
      rs = dataSourceBase.executeQuery(sql);
      if (rs.next())
        if (rs.getString(2) == null || "null".equalsIgnoreCase(rs.getString(2)) || "".equals(rs.getString(2))) {
          isRun = true;
        } else {
          isRun = isRun(rs.getString(1), rs.getString(2), record);
        }  
      rs.close();
      boolean flag = false;
      if (isRun) {
        sql = "select fieldid from jsf_corrfield where processId=" + flowId + " and fieldshowType='" + type + "'";
        rs = dataSourceBase.executeQuery(sql);
        if (rs.next())
          flag = true; 
        rs.close();
      } 
      if (flag) {
        sql = "select max(showtype) from jsf_corrfield where processId=" + flowId + " and fieldshowType='" + type + "'";
        rs = dataSourceBase.executeQuery(sql);
        if (rs.next())
          num = rs.getInt(1); 
        rs.close();
        for (int i = 0; i < num + 1; i++) {
          tableNum++;
          String select = "";
          String update = "";
          String[] fieldcond = getCond(flowId, i, type);
          cond = fieldcond[0];
          sql = "select oatable,oafield,basedata,basetable,basefield from jsf_corrfield where ProcessId=" + 
            flowId + " and showtype='" + i + "' and fieldshowType='" + type + "'";
          rs = dataSourceBase.executeQuery(sql);
          while (rs.next()) {
            fieldNum++;
            dataTable[0] = rs.getString(1);
            dataTable[1] = rs.getString(3);
            dataTable[2] = rs.getString(4);
            select = String.valueOf(select) + "," + rs.getString(2);
            if ("insert".equals(cond.substring(0, cond.indexOf("@||@")))) {
              update = String.valueOf(update) + "," + rs.getString(5);
              continue;
            } 
            update = String.valueOf(update) + "," + rs.getString(5) + "=?";
          } 
          rs.close();
          if (!"".equals(select)) {
            if ("insert".equals(cond.substring(0, cond.indexOf("@||@")))) {
              for (int j = 0; j < fieldNum; j++)
                insertValue = String.valueOf(insertValue) + ",?"; 
              insertValue = insertValue.substring(1);
            } 
            selects[0] = select.substring(1);
            selects[1] = update.substring(1);
            OA2BaseDate(dataTable, selects, insertValue, i, fieldNum, record, cond, fieldcond[1]);
            fieldNum = 0;
            insertValue = "";
          } 
        } 
      } else if (type.indexOf("table") >= 0) {
        System.out.println("此流程无法进行数据库同步！");
      } else {
        System.out.println("此节点无法进行数据库同步！");
      } 
      dataSourceBase.end();
    } catch (Exception e) {
      dataSourceBase.end();
      System.out.println("OA数据导入其他数据库时出错！");
      e.printStackTrace();
    } 
  }
  
  public void OA2BaseDate(String[] dataTable, String[] selects, String insertValue, int tableNum, int fieldNum, String record, String cond, String zhuanyi) {
    DataSourceBase base = new DataSourceBase();
    PreparedStatement pstmt = null;
    Connection conn = null;
    try {
      Connection conn1 = base.getDataSource().getConnection();
      Statement stmt = conn1.createStatement();
      String sql = "";
      if (cond.indexOf("@$@") >= 0)
        cond = setCond(cond); 
      String[] conds = { "", "", "0" };
      if (cond.indexOf("]$[") >= 0) {
        conds = cond.split("\\]\\$\\[");
        cond = conds[0];
      } 
      if (tableNum == 0) {
        sql = "select " + selects[0] + conds[1] + "," + dataTable[0] + "_id from " + dataTable[0] + " where " + dataTable[0] + "_id=" + record;
      } else {
        sql = "select " + selects[0] + conds[1] + "," + dataTable[0] + "_id from " + dataTable[0] + " where " + dataTable[0] + "_FOREIGNKEY=" + record;
      } 
      ResultSet rs = stmt.executeQuery(sql);
      String[][] selectStrings = RS.toStrArr2(rs, fieldNum + 1 + Integer.parseInt(conds[2]));
      rs.close();
      stmt.close();
      conn1.close();
      if ("system".equals(dataTable[1])) {
        conn = base.getDataSource().getConnection();
      } else {
        conn = base.getDataSource(dataTable[1]).getConnection();
      } 
      if ("update".equals(cond.substring(0, cond.indexOf("@||@")))) {
        String where = "1=1";
        if (!"".equals(cond.substring(cond.indexOf("@||@") + 4)))
          where = cond.substring(cond.indexOf("@||@") + 4); 
        sql = "update " + dataTable[2] + " set " + selects[1] + " where " + where;
      } else {
        String conditionString = cond.substring(cond.indexOf("@||@") + 4);
        String intoFields = "";
        String valueField = "";
        if (!"".equals(conditionString)) {
          String[] valueStrings = conditionString.split(";");
          for (int i = 0; i < valueStrings.length; i++) {
            if (valueStrings[i].indexOf("=") >= 0) {
              String[] showValue = valueStrings[i].split("=");
              intoFields = String.valueOf(intoFields) + "," + showValue[0];
              valueField = String.valueOf(valueField) + "," + showValue[1];
            } 
          } 
        } 
        sql = "insert into " + dataTable[2] + " (" + selects[1] + intoFields + ") values (" + insertValue + valueField + ")";
      } 
      pstmt = conn.prepareStatement(sql);
      insertValue = "";
      String[] fieldShow = (String.valueOf(selects[0]) + conds[1]).split(",");
      Map map = specialField(dataTable[0], fieldShow);
      for (int x = 0; x < selectStrings.length; x++) {
        for (int i = 0; i < fieldNum + Integer.parseInt(conds[2]); i++) {
          if (map.get(fieldShow[i]) != null && zhuanyi.indexOf(fieldShow[i]) >= 0) {
            if (";".equals(map.get(fieldShow[i]).toString())) {
              pstmt.setString(i + 1, selectStrings[x][i].split(";")[0]);
            } else {
              Map fieldMap = (Map)map.get(fieldShow[i]);
              if (fieldMap.get(selectStrings[x][i]) != null) {
                pstmt.setString(i + 1, fieldMap.get(selectStrings[x][i]).toString());
              } else {
                String field = "";
                if (selectStrings[x][i].indexOf(",") >= 0) {
                  String[] splic = selectStrings[x][i].split(",");
                  for (int t = 0; t < splic.length; t++) {
                    if (fieldMap.get(splic[t]) != null)
                      field = String.valueOf(field) + fieldMap.get(splic[t]).toString() + ","; 
                  } 
                } 
                pstmt.setString(i + 1, field);
              } 
            } 
          } else {
            pstmt.setString(i + 1, selectStrings[x][i]);
          } 
        } 
        pstmt.executeUpdate();
      } 
      pstmt.close();
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
  }
  
  public String[] getCond(String flowId, String showtype, String type) {
    DataSourceBase dataSourceBase = new DataSourceBase();
    ResultSet rs = null;
    String[] fieldcond = new String[2];
    String flag = "@||@";
    try {
      dataSourceBase.begin();
      String sql = "SELECT operate,conditions,zhuanyi FROM jsf_fieldcondition WHERE processId='" + flowId + "' AND showType='" + showtype + 
        "' and fieldshowtype='" + type + "'";
      rs = dataSourceBase.executeQuery(sql);
      if (rs.next()) {
        String tiaojian = "insert";
        if (rs.getString(1) != null || !"".equals(rs.getString(1)))
          tiaojian = rs.getString(1); 
        if (rs.getString(2) == null) {
          flag = String.valueOf(tiaojian) + flag;
        } else {
          flag = String.valueOf(tiaojian) + flag + rs.getString(2);
        } 
        fieldcond[0] = flag;
        fieldcond[1] = (rs.getString(3) == null) ? "" : rs.getString(3);
      } else {
        flag = "insert" + flag;
        fieldcond[0] = flag;
        fieldcond[1] = "";
      } 
      rs.close();
      dataSourceBase.end();
    } catch (Exception e) {
      dataSourceBase.end();
      e.printStackTrace();
    } 
    return fieldcond;
  }
  
  public String setCond(String cond) {
    String test = cond;
    if (test.endsWith("@$@"))
      test = String.valueOf(test) + "xuxueyun"; 
    String[] teStrings = test.split("\\@\\$\\@");
    int y = teStrings.length;
    if (y % 2 == 0)
      y -= 2; 
    int i = 0;
    String select = "";
    for (int x = 1; x < y; x += 2) {
      i++;
      select = String.valueOf(select) + "," + teStrings[x];
      cond = cond.replace("@$@" + teStrings[x] + "@$@", "? ");
    } 
    if (!"".equals(select)) {
      String flag = String.valueOf(cond) + "]$[" + select + "]$[" + i;
      return flag;
    } 
    return cond;
  }
  
  public Map specialField(String tableName, String[] fields) throws Exception {
    Map<Object, Object> map = new HashMap<Object, Object>();
    String field = "";
    for (int i = 0; i < fields.length; i++)
      field = String.valueOf(field) + ",'" + fields[i] + "'"; 
    field = field.substring(1);
    DataSourceBase base = new DataSourceBase();
    Connection conn = null;
    Statement stam = null;
    ResultSet rs = null;
    try {
      base.begin();
      String sql = "SELECT field_name,field_show,field_value FROM tfield WHERE field_table=(SELECT table_id FROM ttable WHERE table_name='" + 
        tableName + "') " + 
        "AND field_name IN (" + field + ")";
      rs = base.executeQuery(sql);
      String[][] rsArray = RS.toStrArr2(rs, 3);
      rs.close();
      base.end();
      for (int x = 0; x < rsArray.length; x++) {
        if ("105".equals(rsArray[x][1]) || "103".equals(rsArray[x][1]) || "104".equals(rsArray[x][1])) {
          if (rsArray[x][2].startsWith("$") || rsArray[x][2].startsWith("@")) {
            Map<Object, Object> itemMap = new HashMap<Object, Object>();
            if (rsArray[x][2].indexOf("].$[") >= 0) {
              String database = rsArray[x][2].substring(rsArray[x][2].indexOf("$[") + 
                  2, rsArray[x][2].indexOf("].$["));
              if ("system".equals(database)) {
                conn = base.getDataSource().getConnection();
              } else {
                conn = base.getDataSource(database).getConnection();
              } 
              sql = rsArray[x][2].substring(rsArray[x][2].indexOf("].$[") + 4, rsArray[x][2].length() - 1);
            } else {
              conn = base.getDataSource().getConnection();
              if (rsArray[x][2].startsWith("@")) {
                sql = rsArray[x][2].substring(rsArray[x][2].indexOf("][") + 2, rsArray[x][2].length() - 1);
                String[] sqls = sql.split("\\.");
                sql = "select " + sqls[0] + "_id," + sqls[1] + " from " + sqls[0];
              } else {
                sql = rsArray[x][2].substring(rsArray[x][2].indexOf("$[") + 2, rsArray[x][2].length() - 1);
              } 
            } 
            stam = conn.createStatement();
            rs = stam.executeQuery(sql);
            while (rs.next())
              itemMap.put(rs.getString(1), rs.getString(2)); 
            map.put(rsArray[x][0], itemMap);
            rs.close();
            stam.close();
            conn.close();
          } else {
            String[] xiala = (String[])null;
            if (rsArray[x][2].startsWith("*")) {
              String parentId = rsArray[x][2].substring(rsArray[x][2].indexOf(".*[") + 3, rsArray[x][2].length() - 1);
              xiala = (new BaseSetEJBBean()).getValue(parentId).split(";");
            } else {
              xiala = rsArray[x][2].split(";");
            } 
            Map<Object, Object> itemMap = new HashMap<Object, Object>();
            for (int j = 0; j < xiala.length; j++) {
              String[] item = xiala[j].split("/");
              itemMap.put(item[0], item[1]);
            } 
            map.put(rsArray[x][0], itemMap);
          } 
        } else if (",210,211,212,214,".indexOf("," + rsArray[x][1] + ",") >= 0) {
          map.put(rsArray[x][0], ";");
        } 
      } 
    } catch (Exception e) {
      base.end();
      if (stam != null)
        stam.close(); 
      if (conn != null)
        conn.close(); 
      e.printStackTrace();
    } 
    return map;
  }
  
  public void deleteAll(String flowId, String type) {
    DataSourceBase dataSourceBase = new DataSourceBase();
    try {
      dataSourceBase.begin();
      String sql = "delete from jsf_corrfield where ProcessId='" + flowId + "' and fieldshowType='" + type + "'";
      dataSourceBase.executeUpdate(sql);
      sql = "delete from jsf_fieldcondition where ProcessId='" + flowId + "' and fieldshowType='" + type + "'";
      dataSourceBase.executeUpdate(sql);
      dataSourceBase.end();
    } catch (Exception e) {
      dataSourceBase.end();
      e.printStackTrace();
    } 
  }
  
  public List getNameList(String tableId, DataSourceBase base) {
    List<String[]> areaList = new ArrayList();
    ResultSet rs = null;
    try {
      String sql = "SELECT table_desname,table_name FROM ttable,tarea WHERE table_name=area_table AND page_id=" + tableId + 
        " ORDER BY area_name";
      rs = base.executeQuery(sql);
      while (rs.next()) {
        String[] name = new String[2];
        name[0] = (rs.getString(1) == null) ? "" : rs.getString(1);
        name[1] = (rs.getString(2) == null) ? "" : rs.getString(2);
        areaList.add(name);
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return areaList;
  }
  
  public List getFieldList(String flowId, int showtype, String type, DataSourceBase base) {
    List<String> list = new ArrayList();
    Map<String, String[]> map = (Map)new HashMap<String, String>();
    String dataBase = "";
    String baseTable = "";
    ResultSet rs = null;
    try {
      String selectSql = "select fieldid,oatable,oafield,Basedata,basetable,basefield,fieldtype from jsf_corrfield where showtype='" + 
        showtype + "' and processId='" + flowId + "' and fieldshowtype='" + type + "'";
      ResultSet rSet = base.executeQuery(selectSql);
      while (rSet.next()) {
        dataBase = rSet.getString(4);
        baseTable = rSet.getString(5);
        String[] showStrings = new String[2];
        showStrings[0] = rSet.getString(1);
        showStrings[1] = rSet.getString(6);
        map.put(rSet.getString(3), showStrings);
      } 
      list.add(dataBase);
      list.add(baseTable);
      list.add(map);
      rSet.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return list;
  }
  
  public String[] getCondition(String flowId, int showtype, String type, DataSourceBase base) {
    String[] cond = { "-1", "", "", "", "" };
    ResultSet rs = null;
    try {
      String selectSql = "SELECT condition_id,operate,conditions,isrun,zhuanyi FROM jsf_fieldcondition WHERE processId='" + 
        flowId + "' AND showType='" + showtype + "' and fieldshowtype='" + type + "'";
      rs = base.executeQuery(selectSql);
      if (rs.next()) {
        cond[0] = rs.getString(1);
        cond[1] = (rs.getString(2) == null) ? "" : rs.getString(2);
        cond[2] = (rs.getString(3) == null) ? "" : rs.getString(3);
        cond[3] = (rs.getString(4) == null) ? "" : rs.getString(4);
        cond[4] = (rs.getString(5) == null) ? "" : rs.getString(5);
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return cond;
  }
  
  public boolean isRun(String tableName, String cond, String record) {
    Map map = null;
    String test = cond;
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    boolean flag = false;
    if (test.endsWith("@$@"))
      test = String.valueOf(test) + "xuxueyun"; 
    String[] teStrings = test.split("\\@\\$\\@");
    String searchField = "";
    for (int i = 1; i < teStrings.length; i += 2)
      searchField = String.valueOf(searchField) + teStrings[i] + ","; 
    String[] search = searchField.split(",");
    String[][] searchValue = new String[search.length][2];
    try {
      base.begin();
      String sql = "select " + searchField.substring(0, searchField.length() - 1) + " from " + tableName + " where " + tableName + "_id=" + record;
      rs = base.executeQuery(sql);
      if (rs.next())
        for (int x = 0; x < search.length; x++) {
          searchValue[x][0] = search[x];
          searchValue[x][1] = rs.getString(x + 1);
        }  
      map = specialField(tableName, search);
      for (int j = 0; j < search.length; j++) {
        String trueValue = "";
        if (map.get(search[j]) != null) {
          if (";".equals(map.get(search[j]).toString())) {
            trueValue = searchValue[j][1].split(";")[0];
          } else {
            Map fieldMap = (Map)map.get(search[j]);
            if (fieldMap.get(searchValue[j][1]) != null) {
              trueValue = fieldMap.get(searchValue[j][1]).toString();
            } else {
              String field = "";
              if (searchValue[j][1].indexOf(",") >= 0) {
                String[] splic = searchValue[j][1].split(",");
                for (int t = 0; t < splic.length; t++) {
                  if (fieldMap.get(splic[t]) != null)
                    field = String.valueOf(field) + fieldMap.get(splic[t]).toString() + ","; 
                } 
              } 
              trueValue = field;
            } 
          } 
        } else {
          trueValue = searchValue[j][1];
        } 
        cond = cond.replace("@$@" + search[j] + "@$@", trueValue);
      } 
      ExpressionUtil eu = ExpressionUtil.getInstance();
      flag = Boolean.valueOf(eu.eval(cond)).booleanValue();
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return flag;
  }
}
