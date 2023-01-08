package com.js.oa.datasync.service;

import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DataSynchronizeService {
  private String processId;
  
  private String activityId;
  
  private String recordId;
  
  private Map<String, String> mainTableValue;
  
  private String[] mainTableFieldArr;
  
  private String mainTableFields;
  
  private String mainTableName;
  
  public DataSynchronizeService(String processId, String activityId, String recordId) {
    this.processId = processId;
    this.activityId = activityId;
    this.recordId = recordId;
  }
  
  public int synchronizeData(HttpServletRequest request) {
    Map<String, Object> map = getSyncInfo();
    if (map != null) {
      List<String[]> syncSetList = (List<String[]>)map.get("setInfo");
      Map<String, List> syncFieldMap = (Map<String, List>)map.get("fieldInfo");
      for (int i = 0; i < syncSetList.size(); i++) {
        String[] syncSetInfo = syncSetList.get(i);
        Connection toConn = null;
        PreparedStatement toPstmt = null;
        try {
          String dataSourceType = syncSetInfo[2];
          String dataSourceName = syncSetInfo[3];
          if ("1".equals(dataSourceType)) {
            toConn = (new DataSourceBase()).getDataSource(this.mainTableValue.get(dataSourceName)).getConnection();
          } else if ("system".equals(dataSourceName)) {
            toConn = (new DataSourceBase()).getDataSource().getConnection();
          } else {
            toConn = (new DataSourceBase()).getDataSource(dataSourceName).getConnection();
          } 
          String operateTable = syncSetInfo[4];
          String operateType = syncSetInfo[5];
          String execondition = syncSetInfo[6];
          List<String[]> fieldInfoList = syncFieldMap.get(syncSetInfo[0]);
          StringBuffer sqlBuffer = new StringBuffer(256);
          if ("1".equals(syncSetInfo[1])) {
            if (!syncConditionOK(syncSetInfo[9], "", ""))
              continue; 
            if ("0".equals(operateType)) {
              sqlBuffer.append("insert into ").append(operateTable).append("(");
              int j;
              for (j = 0; j < fieldInfoList.size(); j++) {
                String[] fieldInfo = fieldInfoList.get(j);
                if (j == 0) {
                  sqlBuffer.append(fieldInfo[3]);
                } else {
                  sqlBuffer.append(",").append(fieldInfo[3]);
                } 
              } 
              sqlBuffer.append(") values (");
              for (j = 0; j < fieldInfoList.size(); j++) {
                if (j == 0) {
                  sqlBuffer.append("?");
                } else {
                  sqlBuffer.append(",?");
                } 
              } 
              sqlBuffer.append(")");
              toPstmt = toConn.prepareStatement(sqlBuffer.toString());
              for (j = 0; j < fieldInfoList.size(); j++) {
                String[] fieldInfo = fieldInfoList.get(j);
                if ("".equals(fieldInfo[0])) {
                  toPstmt.setString(j + 1, getSpecialColumnValue(syncSetInfo[7], fieldInfo[2]));
                } else {
                  toPstmt.setString(j + 1, this.mainTableValue.get(fieldInfo[0]));
                } 
              } 
              toPstmt.executeUpdate();
            } else if ("1".equals(operateType)) {
              sqlBuffer.append("update ").append(operateTable).append(" set ");
              for (int j = 0; j < fieldInfoList.size(); j++) {
                String[] fieldInfo = fieldInfoList.get(j);
                if (j == 0) {
                  sqlBuffer.append(String.valueOf(fieldInfo[3]) + " = ? ");
                } else {
                  sqlBuffer.append(",").append(String.valueOf(fieldInfo[3]) + " = ? ");
                } 
              } 
              HttpSession session = request.getSession();
              String userId = session.getAttribute("userId").toString();
              String orgId = session.getAttribute("orgId").toString();
              String userAccount = session.getAttribute("userAccount").toString();
              String userName = session.getAttribute("userName").toString();
              String[] split = execondition.split("=");
              String condition = split[1];
              String[] paras = (String[])null;
              if (condition.equals("@@userId@@")) {
                condition = userId;
              } else if (condition.equals("@@orgId@@")) {
                condition = orgId;
              } else if (condition.equals("@@userName@@")) {
                condition = userName;
              } else if (condition.equals("@@userAccount@@")) {
                condition = userAccount;
              } else if (condition.equals("@@recordId@@")) {
                condition = this.recordId;
              } else if (condition.equals("@@processId@@")) {
                condition = this.processId;
              } else {
                paras = condition.split("\\@\\@");
                condition = "?";
              } 
              sqlBuffer.append(" where " + split[0] + "=" + condition);
              toPstmt = toConn.prepareStatement(sqlBuffer.toString());
              for (int k = 0; k < fieldInfoList.size(); k++) {
                String[] fieldInfo = fieldInfoList.get(k);
                if ("".equals(fieldInfo[0])) {
                  toPstmt.setString(k + 1, getSpecialColumnValue(syncSetInfo[7], fieldInfo[2]));
                } else {
                  if (paras != null)
                    toPstmt.setString(fieldInfoList.size() + 1, getConditionValue(paras[1])); 
                  toPstmt.setString(k + 1, this.mainTableValue.get(fieldInfo[0]));
                } 
              } 
              toPstmt.executeUpdate();
            } else if ("2".equals(operateType)) {
              HttpSession session = request.getSession();
              String userId = session.getAttribute("userId").toString();
              String orgId = session.getAttribute("orgId").toString();
              String userAccount = session.getAttribute("userAccount").toString();
              String userName = session.getAttribute("userName").toString();
              String[] split = execondition.split("=");
              String condition = split[1];
              String[] paras = (String[])null;
              if (condition.equals("@@userId@@")) {
                condition = userId;
              } else if (condition.equals("@@orgId@@")) {
                condition = orgId;
              } else if (condition.equals("@@userName@@")) {
                condition = userName;
              } else if (condition.equals("@@userAccount@@")) {
                condition = userAccount;
              } else if (condition.equals("@@recordId@@")) {
                condition = this.recordId;
              } else if (condition.equals("@@processId@@")) {
                condition = this.processId;
              } else {
                paras = condition.split("\\@\\@");
                condition = "?";
              } 
              sqlBuffer.append("delete from ").append(operateTable).append(" where " + split[0] + "=" + condition);
              toPstmt = toConn.prepareStatement(sqlBuffer.toString());
              for (int j = 0; j < fieldInfoList.size(); j++) {
                String[] fieldInfo = fieldInfoList.get(j);
                if ("".equals(fieldInfo[0])) {
                  toPstmt.setString(j + 1, getSpecialColumnValue(syncSetInfo[7], fieldInfo[2]));
                } else if (paras != null) {
                  toPstmt.setString(1, getConditionValue(paras[1]));
                } 
              } 
              toPstmt.executeUpdate();
            } 
          } else if ("0".equals(operateType)) {
            sqlBuffer.append("insert into ").append(operateTable).append("(");
            int j;
            for (j = 0; j < fieldInfoList.size(); j++) {
              String[] fieldInfo = fieldInfoList.get(j);
              if (j == 0) {
                sqlBuffer.append(fieldInfo[3]);
              } else {
                sqlBuffer.append(",").append(fieldInfo[3]);
              } 
            } 
            sqlBuffer.append(") values (");
            for (j = 0; j < fieldInfoList.size(); j++) {
              if (j == 0) {
                sqlBuffer.append("?");
              } else {
                sqlBuffer.append(",?");
              } 
            } 
            sqlBuffer.append(")");
            toPstmt = toConn.prepareStatement(sqlBuffer.toString());
            List<Map> subTableDataList = getSubTableValues(syncSetInfo, fieldInfoList);
            for (int k = 0; k < subTableDataList.size(); k++) {
              Map<String, String> oneData = subTableDataList.get(k);
              if (syncConditionOK(syncSetInfo[9], syncSetInfo[8], oneData.get(String.valueOf(syncSetInfo[8]) + "_id"))) {
                for (int fi = 0; fi < fieldInfoList.size(); fi++) {
                  String[] fieldInfo = fieldInfoList.get(fi);
                  if ("".equals(fieldInfo[0])) {
                    toPstmt.setString(fi + 1, oneData.get(String.valueOf(syncSetInfo[8]) + "_randcolumn_" + fi));
                  } else if ("1".equals(fieldInfo[5])) {
                    toPstmt.setString(fi + 1, this.mainTableValue.get(fieldInfo[0]));
                  } else if ("302".equals(fieldInfo[1])) {
                    toPstmt.setString(fi + 1, String.valueOf(k + 1));
                  } else {
                    toPstmt.setString(fi + 1, oneData.get(fieldInfo[0]));
                  } 
                } 
                toPstmt.execute();
              } 
            } 
          } else if ("1".equals(operateType)) {
            sqlBuffer.append("update ").append(operateTable).append(" set ");
            for (int j = 0; j < fieldInfoList.size(); j++) {
              String[] fieldInfo = fieldInfoList.get(j);
              if (j == 0) {
                sqlBuffer.append(String.valueOf(fieldInfo[3]) + " = ? ");
              } else {
                sqlBuffer.append(",").append(String.valueOf(fieldInfo[3]) + " = ? ");
              } 
            } 
            HttpSession session = request.getSession();
            String userId = session.getAttribute("userId").toString();
            String orgId = session.getAttribute("orgId").toString();
            String userAccount = session.getAttribute("userAccount").toString();
            String userName = session.getAttribute("userName").toString();
            String[] split = execondition.split("=");
            String condition = split[1];
            String[] paras = (String[])null;
            if (condition.equals("@@userId@@")) {
              condition = userId;
            } else if (condition.equals("@@orgId@@")) {
              condition = orgId;
            } else if (condition.equals("@@userName@@")) {
              condition = userName;
            } else if (condition.equals("@@userAccount@@")) {
              condition = userAccount;
            } else if (condition.equals("@@recordId@@")) {
              condition = this.recordId;
            } else if (condition.equals("@@processId@@")) {
              condition = this.processId;
            } else {
              paras = condition.split("\\@\\@");
              for (int m = 0; m < fieldInfoList.size(); m++) {
                String[] fieldInfo = fieldInfoList.get(m);
                if (paras[1].equals(fieldInfo[0]))
                  condition = "?"; 
              } 
            } 
            sqlBuffer.append(" where " + split[0] + "=" + condition);
            toPstmt = toConn.prepareStatement(sqlBuffer.toString());
            List<Map> subTableDataList = getSubTableValues(syncSetInfo, fieldInfoList);
            for (int k = 0; k < subTableDataList.size(); k++) {
              Map<String, String> oneData = subTableDataList.get(k);
              if (syncConditionOK(syncSetInfo[9], syncSetInfo[8], oneData.get(String.valueOf(syncSetInfo[8]) + "_id"))) {
                for (int fi = 0; fi < fieldInfoList.size(); fi++) {
                  String[] fieldInfo = fieldInfoList.get(fi);
                  if ("".equals(fieldInfo[0])) {
                    toPstmt.setString(fi + 1, oneData.get(String.valueOf(syncSetInfo[8]) + "_randcolumn_" + fi));
                  } else if ("1".equals(fieldInfo[5])) {
                    toPstmt.setString(fi + 1, this.mainTableValue.get(fieldInfo[0]));
                    if (paras != null && paras[1].equals(fieldInfo[0]))
                      toPstmt.setString(fieldInfoList.size() + 1, this.mainTableValue.get(fieldInfo[0])); 
                  } else if ("302".equals(fieldInfo[1])) {
                    toPstmt.setString(fi + 1, String.valueOf(k + 1));
                  } else {
                    toPstmt.setString(fi + 1, oneData.get(fieldInfo[0]));
                    if (paras != null && paras[1].equals(fieldInfo[0]))
                      toPstmt.setString(fieldInfoList.size() + 1, oneData.get(fieldInfo[0])); 
                  } 
                } 
                toPstmt.execute();
              } 
            } 
          } else if ("2".equals(operateType)) {
            HttpSession session = request.getSession();
            String userId = session.getAttribute("userId").toString();
            String orgId = session.getAttribute("orgId").toString();
            String userAccount = session.getAttribute("userAccount").toString();
            String userName = session.getAttribute("userName").toString();
            String[] split = execondition.split("=");
            String condition = split[1];
            String[] paras = (String[])null;
            if (condition.equals("@@userId@@")) {
              condition = userId;
            } else if (condition.equals("@@orgId@@")) {
              condition = orgId;
            } else if (condition.equals("@@userName@@")) {
              condition = userName;
            } else if (condition.equals("@@userAccount@@")) {
              condition = userAccount;
            } else if (condition.equals("@@recordId@@")) {
              condition = this.recordId;
            } else if (condition.equals("@@processId@@")) {
              condition = this.processId;
            } else {
              paras = condition.split("\\@\\@");
              for (int k = 0; k < fieldInfoList.size(); k++) {
                String[] fieldInfo = fieldInfoList.get(k);
                if (paras[1].equals(fieldInfo[0]))
                  condition = "?"; 
              } 
            } 
            sqlBuffer.append("delete from ").append(operateTable).append(" where " + split[0] + "=" + condition);
            toPstmt = toConn.prepareStatement(sqlBuffer.toString());
            List<Map> subTableDataList = getSubTableValues(syncSetInfo, fieldInfoList);
            for (int j = 0; j < subTableDataList.size(); j++) {
              Map<String, String> oneData = subTableDataList.get(j);
              if (syncConditionOK(syncSetInfo[9], syncSetInfo[8], oneData.get(String.valueOf(syncSetInfo[8]) + "_id"))) {
                for (int fi = 0; fi < fieldInfoList.size(); fi++) {
                  String[] fieldInfo = fieldInfoList.get(fi);
                  if (!"".equals(fieldInfo[0]))
                    if ("1".equals(fieldInfo[5])) {
                      if (paras != null && paras[1].equals(fieldInfo[0]))
                        toPstmt.setString(fieldInfoList.size() + 1, this.mainTableValue.get(fieldInfo[0])); 
                    } else if (!"302".equals(fieldInfo[1])) {
                      if (paras != null && paras[1].equals(fieldInfo[0]))
                        toPstmt.setString(1, oneData.get(fieldInfo[0])); 
                    }  
                } 
                toPstmt.execute();
              } 
            } 
          } 
          if (toPstmt != null)
            toPstmt.close(); 
          toConn.close();
        } catch (Exception ex) {
          if (toConn != null)
            try {
              toConn.close();
            } catch (Exception err) {
              err.printStackTrace();
            }  
          ex.printStackTrace();
        } 
        continue;
      } 
    } 
    return 1;
  }
  
  private Map<String, Object> getSyncInfo() {
    Map<String, Object> map = null;
    Connection oaConn = null;
    try {
      String syncId = "";
      oaConn = (new DataSourceBase()).getDataSource().getConnection();
      String sql = "select id from data_sync where process_id=? and activity_id=?";
      PreparedStatement oaPstmt = oaConn.prepareStatement(sql);
      oaPstmt.setString(1, this.processId);
      oaPstmt.setString(2, this.activityId);
      ResultSet rs = oaPstmt.executeQuery();
      if (rs.next())
        syncId = rs.getString(1); 
      rs.close();
      oaPstmt.close();
      if (!"".equals(syncId)) {
        map = new ConcurrentHashMap<String, Object>();
        StringBuffer mainFieldBuffer = new StringBuffer(",");
        List<String[]> syncSetList = (List)new ArrayList<String>();
        Map<Object, Object> syncFieldMap = new HashMap<Object, Object>();
        sql = "select id,settype,datasourcetype,datasourcename,operatetable,operatetype,execondition,executeorder,maintablename,subtablename,synccondition from data_sync_set where sync_id=? order by executeorder";
        oaPstmt = oaConn.prepareStatement(sql);
        oaPstmt.setString(1, syncId);
        rs = oaPstmt.executeQuery();
        while (rs.next()) {
          String[] baseInfo = new String[10];
          baseInfo[0] = rs.getString("id");
          baseInfo[1] = rs.getString("settype");
          baseInfo[2] = rs.getString("datasourcetype");
          baseInfo[3] = rs.getString("datasourcename");
          baseInfo[4] = rs.getString("operatetable");
          baseInfo[5] = rs.getString("operatetype");
          baseInfo[6] = rs.getString("execondition");
          baseInfo[7] = rs.getString("maintablename");
          baseInfo[8] = rs.getString("subtablename");
          baseInfo[9] = rs.getString("synccondition");
          this.mainTableName = baseInfo[7];
          if ("1".equals(baseInfo[2]))
            mainFieldBuffer.append(baseInfo[3]).append(","); 
          syncSetList.add(baseInfo);
        } 
        rs.close();
        oaPstmt.close();
        int i;
        for (i = 0; i < syncSetList.size(); i++) {
          String[] baseInfo = syncSetList.get(i);
          sql = "select fromfield,fromtype,fromvalueset,tofield,totype,fieldismainform from data_sync_field where syncset_id=?";
          oaPstmt = oaConn.prepareStatement(sql);
          oaPstmt.setString(1, baseInfo[0]);
          rs = oaPstmt.executeQuery();
          List<String[]> syncFieldList = (List)new ArrayList<String>();
          while (rs.next()) {
            String[] fieldInfo = new String[6];
            fieldInfo[0] = rs.getString("fromfield");
            fieldInfo[1] = rs.getString("fromtype");
            fieldInfo[2] = rs.getString("fromvalueset");
            fieldInfo[3] = rs.getString("tofield");
            fieldInfo[4] = rs.getString("totype");
            fieldInfo[5] = rs.getString("fieldismainform");
            if ("1".equals(baseInfo[1])) {
              if (!"".equals(fieldInfo[0]) && 
                mainFieldBuffer.indexOf("," + fieldInfo[0] + ",") < 0)
                mainFieldBuffer.append(fieldInfo[0]).append(","); 
            } else if (!"".equals(fieldInfo[0]) && "1".equals(fieldInfo[5]) && 
              mainFieldBuffer.indexOf("," + fieldInfo[0] + ",") < 0) {
              mainFieldBuffer.append(fieldInfo[0]).append(",");
            } 
            syncFieldList.add(fieldInfo);
          } 
          syncFieldMap.put(baseInfo[0], syncFieldList);
        } 
        this.mainTableFields = mainFieldBuffer.toString();
        if (this.mainTableFields.length() > 2) {
          this.mainTableValue = new ConcurrentHashMap<String, String>();
          this.mainTableFields = this.mainTableFields.substring(1, this.mainTableFields.length() - 1);
          System.out.println("mainTableFields:" + this.mainTableFields);
          this.mainTableFieldArr = this.mainTableFields.split(",");
          sql = "select " + this.mainTableFields + " from " + this.mainTableName + " where " + this.mainTableName + "_id=?";
          oaPstmt = oaConn.prepareStatement(sql);
          oaPstmt.setString(1, this.recordId);
          rs = oaPstmt.executeQuery();
          if (rs.next())
            for (i = 0; i < this.mainTableFieldArr.length; i++)
              this.mainTableValue.put(this.mainTableFieldArr[i], rs.getString(this.mainTableFieldArr[i]));  
          rs.close();
          oaPstmt.close();
        } 
        map.put("setInfo", syncSetList);
        map.put("fieldInfo", syncFieldMap);
      } 
      oaConn.close();
    } catch (Exception ex) {
      if (oaConn != null)
        try {
          oaConn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return map;
  }
  
  private String getSpecialColumnValue(String tableName, String column) {
    String value = "";
    Connection oaConn = null;
    try {
      oaConn = (new DataSourceBase()).getDataSource().getConnection();
      PreparedStatement pstmt = oaConn.prepareStatement("select (" + column + ") from " + tableName + " where " + tableName + "_id=?");
      pstmt.setString(1, this.recordId);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next())
        value = rs.getString(1); 
      rs.close();
      pstmt.close();
      oaConn.close();
    } catch (Exception ex) {
      if (oaConn != null)
        try {
          oaConn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return value;
  }
  
  private List<Map> getSubTableValues(String[] syncSetInfo, List<String[]> fieldInfoList) {
    List<Map> subDataList = new ArrayList<Map>();
    Connection oaConn = null;
    try {
      StringBuffer sqlPara = new StringBuffer(256);
      StringBuffer selectPara = new StringBuffer(256);
      int tempi = 0;
      for (int i = 0; i < fieldInfoList.size(); i++) {
        String[] fieldInfo = fieldInfoList.get(i);
        if ("".equals(fieldInfo[0])) {
          if (tempi == 0) {
            sqlPara.append("(").append(fieldInfo[2]).append(") as ").append(syncSetInfo[8]).append("_randcolumn_").append(i);
            selectPara.append(syncSetInfo[8]).append("_randcolumn_").append(i);
          } else {
            sqlPara.append(",").append("(").append(fieldInfo[2]).append(") as ").append(syncSetInfo[8]).append("_randcolumn_").append(i);
            selectPara.append(",").append(syncSetInfo[8]).append("_randcolumn_").append(i);
          } 
          tempi++;
        } else if (!"1".equals(fieldInfo[5])) {
          if (tempi == 0) {
            sqlPara.append(fieldInfo[0]);
            selectPara.append(fieldInfo[0]);
          } else {
            sqlPara.append(",").append(fieldInfo[0]);
            selectPara.append(",").append(fieldInfo[0]);
          } 
          tempi++;
        } 
      } 
      String subParas = sqlPara.toString();
      String[] selectParaArray = selectPara.toString().split(",");
      StringBuffer sqlBuffer = (new StringBuffer("select ")).append(syncSetInfo[8]).append("_id,").append(subParas)
        .append(" from ").append(syncSetInfo[8])
        .append(" where ").append(syncSetInfo[8]).append("_foreignkey=?")
        .append(" order by ").append(syncSetInfo[8]).append("_id");
      oaConn = (new DataSourceBase()).getDataSource().getConnection();
      PreparedStatement pstmt = oaConn.prepareStatement(sqlBuffer.toString());
      pstmt.setString(1, this.recordId);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        Map<String, String> oneData = new ConcurrentHashMap<String, String>();
        oneData.put(String.valueOf(syncSetInfo[8]) + "_id", rs.getString(String.valueOf(syncSetInfo[8]) + "_id"));
        for (int j = 0; j < selectParaArray.length; j++)
          oneData.put(selectParaArray[j], rs.getString(selectParaArray[j])); 
        subDataList.add(oneData);
      } 
      rs.close();
      pstmt.close();
      oaConn.close();
    } catch (Exception ex) {
      if (oaConn != null)
        try {
          oaConn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return subDataList;
  }
  
  private boolean syncConditionOK(String condition, String subTableName, String subTableDataId) {
    boolean result = false;
    if ("".equals(condition)) {
      result = true;
    } else {
      String[] conditionArr = condition.split(";;;;");
      String conditionFieldType = conditionArr[0].split(";")[0];
      String conditionField = conditionArr[0].split(";")[1];
      String conditionOpr = conditionArr[1];
      String conditionValue = conditionArr[2];
      Connection oaConn = null;
      try {
        String conditionTable;
        oaConn = (new DataSourceBase()).getDataSource().getConnection();
        if ("1".equals(conditionFieldType)) {
          conditionTable = this.mainTableName;
        } else {
          conditionTable = subTableName;
        } 
        StringBuffer sql = new StringBuffer("select ");
        sql.append(conditionTable).append("_id from ").append(conditionTable)
          .append(" where ").append(conditionTable).append("_id=? and ").append(conditionField).append(" ").append(conditionOpr).append(" ?");
        PreparedStatement pstmt = oaConn.prepareStatement(sql.toString());
        if ("1".equals(conditionFieldType)) {
          pstmt.setString(1, this.recordId);
        } else {
          pstmt.setString(1, subTableDataId);
        } 
        pstmt.setString(2, conditionValue);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next())
          result = true; 
        rs.close();
        pstmt.close();
        oaConn.close();
      } catch (Exception ex) {
        if (oaConn != null)
          try {
            oaConn.close();
          } catch (Exception err) {
            err.printStackTrace();
          }  
        ex.printStackTrace();
      } 
    } 
    return result;
  }
  
  private String getConditionValue(String fieldName) {
    String result = "";
    Connection oaConn = null;
    try {
      oaConn = (new DataSourceBase()).getDataSource().getConnection();
      String sql = "select " + fieldName + " from " + this.mainTableName + " where " + this.mainTableName + "_id=?";
      PreparedStatement pstmt = oaConn.prepareStatement(sql);
      pstmt.setString(1, this.recordId);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next())
        result = rs.getString(1); 
      rs.close();
      pstmt.close();
      oaConn.close();
    } catch (Exception ex) {
      if (oaConn != null)
        try {
          oaConn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return result;
  }
}
