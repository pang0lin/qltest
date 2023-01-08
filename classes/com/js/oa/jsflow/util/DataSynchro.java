package com.js.oa.jsflow.util;

import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class DataSynchro {
  protected DataSourceBase base = new DataSourceBase();
  
  protected ResultSet rs = null;
  
  protected Connection conn = null;
  
  protected Statement stat = null;
  
  public boolean ifSynchro(String processId) {
    return ifSynchro(processId, "-1");
  }
  
  public boolean ifSynchro(String processId, String nodeId) {
    boolean flag = false;
    String sql = "SELECT ifSynchro FROM jsf_dataExecute WHERE processId=" + processId + " AND nodeId=" + nodeId;
    try {
      this.base.begin();
      this.rs = this.base.executeQuery(sql);
      if (this.rs.next() && 
        1 == this.rs.getInt(1))
        flag = true; 
      this.rs.close();
      this.base.end();
    } catch (Exception e) {
      this.base.end();
      e.printStackTrace();
    } 
    return flag;
  }
  
  public List<String> getFieldByProcessId() {
    return null;
  }
  
  public List<String[]> getFieldByPageId(String tableId) {
    String sql = "SELECT f.field_id,f.field_desname,f.field_table,f.field_type,f.field_name,f.field_show,f.field_value,f.field_len,s.show_name,p.type_desname,p.type_name FROM tfield f,ttable t,tarea a,tshow s,ttype p WHERE t.table_name=a.area_table AND t.table_id=f.field_table AND s.show_id=f.field_show AND p.type_id=f.field_type AND a.area_name='form1' AND a.page_id=" + 

      
      tableId;
    List<String[]> fieldList = (List)new ArrayList<String>();
    try {
      this.base.begin();
      this.rs = this.base.executeQuery(sql);
      while (this.rs.next()) {
        String[] fieldInfo = new String[11];
        fieldInfo[0] = this.rs.getString(1);
        fieldInfo[1] = this.rs.getString(2);
        fieldInfo[2] = this.rs.getString(3);
        fieldInfo[3] = this.rs.getString(4);
        fieldInfo[4] = this.rs.getString(5);
        fieldInfo[5] = this.rs.getString(6);
        fieldInfo[6] = this.rs.getString(7);
        fieldInfo[7] = this.rs.getString(8);
        fieldInfo[8] = this.rs.getString(9);
        fieldInfo[9] = this.rs.getString(10);
        fieldInfo[10] = this.rs.getString(11);
        fieldList.add(fieldInfo);
      } 
      this.rs.close();
      this.base.end();
    } catch (Exception e) {
      this.base.end();
      e.printStackTrace();
    } 
    return fieldList;
  }
  
  public List<String[]> getFieldByTableId(String tableId) {
    String sql = "SELECT f.field_id,f.field_desname,f.field_table,f.field_type,f.field_name,f.field_show,f.field_value,f.field_len,s.show_name,p.type_desname,p.type_name FROM tfield f,tshow s,ttype p WHERE s.show_id=f.field_show AND p.type_id=f.field_type AND f.field_table=" + 
      
      tableId;
    List<String[]> fieldList = (List)new ArrayList<String>();
    try {
      this.base.begin();
      this.rs = this.base.executeQuery(sql);
      while (this.rs.next()) {
        String[] fieldInfo = new String[11];
        fieldInfo[0] = this.rs.getString(1);
        fieldInfo[1] = this.rs.getString(2);
        fieldInfo[2] = this.rs.getString(3);
        fieldInfo[3] = this.rs.getString(4);
        fieldInfo[4] = this.rs.getString(5);
        fieldInfo[5] = this.rs.getString(6);
        fieldInfo[6] = this.rs.getString(7);
        fieldInfo[7] = this.rs.getString(8);
        fieldInfo[8] = this.rs.getString(9);
        fieldInfo[9] = this.rs.getString(10);
        fieldInfo[10] = this.rs.getString(11);
        fieldList.add(fieldInfo);
      } 
      this.rs.close();
      this.base.end();
    } catch (Exception e) {
      this.base.end();
      e.printStackTrace();
    } 
    return fieldList;
  }
  
  public List<String[]> getTableInfo(String pageId) {
    String sql = "SELECT t.table_id,table_desname,table_name FROM ttable t,tarea a WHERE t.table_name=a.area_table AND a.area_name='form1' AND a.page_id=" + 
      pageId;
    String mainTableId = "";
    List<String[]> tableList = (List)new ArrayList<String>();
    try {
      this.base.begin();
      this.rs = this.base.executeQuery(sql);
      if (this.rs.next()) {
        String[] tableInfo = new String[3];
        tableInfo[0] = this.rs.getString(1);
        tableInfo[1] = this.rs.getString(2);
        tableInfo[2] = this.rs.getString(3);
        tableList.add(tableInfo);
        mainTableId = this.rs.getString(1);
      } 
      this.rs.close();
      if (!"".equals(mainTableId)) {
        sql = "SELECT t.table_id,table_desname,table_name FROM ttable t,tlimit l WHERE l.limit_prytable=t.table_id AND l.limit_table=" + mainTableId;
        this.rs = this.base.executeQuery(sql);
        while (this.rs.next()) {
          String[] tableInfo = new String[3];
          tableInfo[0] = this.rs.getString(1);
          tableInfo[1] = this.rs.getString(2);
          tableInfo[2] = this.rs.getString(3);
          tableList.add(tableInfo);
        } 
      } 
      this.rs.close();
      this.base.end();
    } catch (Exception e) {
      this.base.end();
      e.printStackTrace();
    } 
    return tableList;
  }
  
  public String getSelectHtml(String tableId, String selectName, String defaultValue) {
    String selectHtml = "<select name='" + selectName + "' id='" + selectName + "' style='width:100%'>";
    selectHtml = String.valueOf(selectHtml) + "<option value=''>---选择对应字段---</option>";
    List<String[]> fieldList = getFieldByTableId(tableId);
    for (int i = 0; i < fieldList.size(); i++) {
      String[] fieldInfo = fieldList.get(i);
      selectHtml = String.valueOf(selectHtml) + "<option value='" + fieldInfo[4] + "' " + (defaultValue.equals(fieldInfo[4]) ? "selected" : "") + 
        ">" + fieldInfo[1] + "[" + fieldInfo[8] + "(" + fieldInfo[10] + ")]</option>";
    } 
    selectHtml = String.valueOf(selectHtml) + "</select>";
    return selectHtml;
  }
  
  public void save(HttpServletRequest request) {
    String ifSynchro = request.getParameter("duiying");
    String executeContent = ((request.getParameter("executeContent") == null) ? "" : request.getParameter("executeContent")).replace("'", "&apos;");
    String processId = request.getParameter("processId");
    String dataNodeId = request.getParameter("dataNodeId");
    String executeId = (new StringBuilder(String.valueOf((new Date()).getTime()))).toString();
    String[] tableId = request.getParameterValues("tableSynchro");
    String mainTable = request.getParameter("mainTable");
    String tableIds = "";
    String[][] tableSynchro = new String[tableId.length][8];
    for (int i = 0; i < tableId.length; i++) {
      tableIds = String.valueOf(tableIds) + "," + tableId[i] + ",";
      tableSynchro[i][0] = request.getParameter("OAtableName_" + tableId[i]);
      tableSynchro[i][1] = request.getParameter("OAtableId_" + tableId[i]);
      tableSynchro[i][2] = request.getParameter("targetBase_" + tableId[i]);
      tableSynchro[i][3] = request.getParameter("targetTableName_" + tableId[i]);
      tableSynchro[i][4] = request.getParameter("insertOrUpdate_" + tableId[i]);
      tableSynchro[i][5] = ((request.getParameter("conditionContent_" + tableId[i]) == null) ? "" : 
        request.getParameter("conditionContent_" + tableId[i])).replace("'", "&apos;");
      tableSynchro[i][6] = String.valueOf((new Date()).getTime()) + i;
      tableSynchro[i][7] = request.getParameter("tableType_" + tableId[i]);
    } 
    try {
      this.base.begin();
      String sql = "insert into jsf_dataExecute (executeId,executeContent,ifSynchro,processId,nodeId,tableId,mainTable) values (" + 
        executeId + ",'" + executeContent + "'," + ifSynchro + "," + processId + "," + dataNodeId + ",'" + tableIds + "','" + mainTable + "')";
      this.base.executeUpdate(sql);
      sql = "";
      for (int j = 0; j < tableSynchro.length; j++) {
        sql = "insert into jsf_dataCondition (conditionId,targetBaseName,targetTableName,conditionType,conditionContent,sourceTableName,executeId,sourceTableId,tableType) values (" + 
          tableSynchro[j][6] + ",'" + tableSynchro[j][2] + "','" + tableSynchro[j][3] + "','" + 
          tableSynchro[j][4] + "','" + tableSynchro[j][5] + "','" + tableSynchro[j][0] + "'," + executeId + "," + tableSynchro[j][1] + "," + tableSynchro[j][7] + ")";
        this.base.executeUpdate(sql);
        String[] synchroId = request.getParameterValues("synchroId_" + tableId[j]);
        String[] targetFieldName = request.getParameterValues("targetFieldName_" + tableId[j]);
        String[] fieldInfo = request.getParameterValues("fieldInfo_" + tableId[j]);
        String[] dataDefaultValue = request.getParameterValues("dataDefaultValue_" + tableId[j]);
        for (int k = 0; k < synchroId.length; k++) {
          String isTran = "0";
          if (dataDefaultValue[k].indexOf("@tranValue@") >= 0)
            isTran = "1"; 
          sql = "insert into jsf_dataSynchro (dataId,conditionId,targetTableField,sourceFieldName,dataDefaultValue,isTran) values (" + (
            new Date()).getTime() + j + k + "," + tableSynchro[j][6] + ",'" + targetFieldName[k] + "','" + ((fieldInfo[k] == null || "null".equals(fieldInfo[k])) ? 
            "" : fieldInfo[k]) + "','" + ((dataDefaultValue[k] == null || "null".equals(dataDefaultValue[k])) ? "" : dataDefaultValue[k]).replace("'", "&apos;") + "'," + isTran + ")";
          this.base.executeUpdate(sql);
        } 
      } 
      this.base.end();
    } catch (Exception e) {
      this.base.end();
      e.printStackTrace();
    } 
  }
  
  public void update(HttpServletRequest request) {
    String sql = "";
    String executeContent = ((request.getParameter("executeContent") == null) ? "" : request.getParameter("executeContent")).replace("'", "&apos;");
    String processId = request.getParameter("processId");
    String dataNodeId = request.getParameter("dataNodeId");
    String executeId = request.getParameter("executeId");
    String[] tableId = request.getParameterValues("tableSynchro");
    String mainTable = request.getParameter("mainTable");
    String tableIds = "";
    String tableIdDelete = "-1";
    for (int i = 0; tableId != null && i < tableId.length; i++) {
      tableIds = String.valueOf(tableIds) + "," + tableId[i] + ",";
      tableIdDelete = String.valueOf(tableIdDelete) + "," + tableId[i];
    } 
    if (tableIds.equals("")) {
      deleteSynchro(processId, dataNodeId);
    } else {
      sql = "update jsf_dataexecute set executeContent='" + executeContent + "',ifSynchro=1,processId=" + processId + 
        ",nodeId=" + dataNodeId + ",tableId='" + tableIds + "',mainTable='" + mainTable + "' where executeId=" + executeId;
      try {
        this.base.begin();
        this.base.executeUpdate(sql);
        this.base.end();
      } catch (Exception e) {
        this.base.end();
        e.printStackTrace();
      } 
      String[][] tableSynchro = new String[tableId.length][8];
      int j;
      for (j = 0; j < tableId.length; j++) {
        tableSynchro[j][0] = request.getParameter("OAtableName_" + tableId[j]);
        tableSynchro[j][1] = request.getParameter("OAtableId_" + tableId[j]);
        tableSynchro[j][2] = request.getParameter("targetBase_" + tableId[j]);
        tableSynchro[j][3] = request.getParameter("targetTableName_" + tableId[j]);
        tableSynchro[j][4] = request.getParameter("insertOrUpdate_" + tableId[j]);
        tableSynchro[j][5] = ((request.getParameter("conditionContent_" + tableId[j]) == null) ? "" : 
          request.getParameter("conditionContent_" + tableId[j])).replaceAll("'", "&apos;");
        tableSynchro[j][6] = request.getParameter("condId_" + tableId[j]);
        tableSynchro[j][7] = request.getParameter("tableType_" + tableId[j]);
      } 
      for (j = 0; j < tableSynchro.length; j++) {
        if (tableSynchro[j][6].equals("-1") && !"".equals(tableSynchro[j][3])) {
          String[] synchroId = request.getParameterValues("synchroId_" + tableId[j]);
          String[] targetFieldName = request.getParameterValues("targetFieldName_" + tableId[j]);
          String[] fieldInfo = request.getParameterValues("fieldInfo_" + tableId[j]);
          String[] dataDefaultValue = request.getParameterValues("dataDefaultValue_" + tableId[j]);
          try {
            this.base.begin();
            String condId = String.valueOf((new Date()).getTime()) + j;
            sql = "insert into jsf_dataCondition (conditionId,targetBaseName,targetTableName,conditionType,conditionContent,sourceTableName,executeId,sourceTableId,tableType) values (" + 
              condId + ",'" + tableSynchro[j][2] + "','" + tableSynchro[j][3] + "','" + 
              tableSynchro[j][4] + "','" + tableSynchro[j][5] + "','" + tableSynchro[j][0] + "'," + executeId + "," + tableSynchro[j][1] + "," + tableSynchro[j][7] + ")";
            this.base.executeUpdate(sql);
            for (int k = 0; k < synchroId.length; k++) {
              String isTran = "0";
              if (dataDefaultValue[k].indexOf("@tranValue@") >= 0)
                isTran = "1"; 
              sql = "insert into jsf_dataSynchro (dataId,conditionId,targetTableField,sourceFieldName,dataDefaultValue,isTran) values (" + (
                new Date()).getTime() + j + k + "," + condId + ",'" + targetFieldName[k] + "','" + fieldInfo[k] + "','" + 
                dataDefaultValue[k].replaceAll("'", "&apos;") + "'," + isTran + ")";
              this.base.executeUpdate(sql);
            } 
            this.base.end();
          } catch (Exception e) {
            this.base.end();
            e.printStackTrace();
          } 
        } else if (!"".equals(tableSynchro[j][3])) {
          String[] synchroId = request.getParameterValues("synchroId_" + tableId[j]);
          String[] targetFieldName = request.getParameterValues("targetFieldName_" + tableId[j]);
          String[] fieldInfo = request.getParameterValues("fieldInfo_" + tableId[j]);
          String[] dataDefaultValue = request.getParameterValues("dataDefaultValue_" + tableId[j]);
          try {
            this.base.begin();
            sql = "update jsf_dataCondition set targetBaseName='" + tableSynchro[j][2] + "',targetTableName='" + tableSynchro[j][3] + "',conditionType=" + 
              "'" + tableSynchro[j][4] + "',conditionContent='" + tableSynchro[j][5] + "',sourceTableName='" + tableSynchro[j][0] + "',sourceTableId=" + 
              tableSynchro[j][1] + ",tableType=" + tableSynchro[j][7] + " where conditionId=" + tableSynchro[j][6];
            this.base.executeUpdate(sql);
            for (int k = 0; k < synchroId.length; k++) {
              String isTran = "0";
              if (dataDefaultValue[k].indexOf("@tranValue@") >= 0)
                isTran = "1"; 
              if (synchroId[k].equals("-1") && !"".equals(targetFieldName[k])) {
                String newDateId = String.valueOf((new Date()).getTime()) + j + k;
                synchroId[k] = newDateId;
                sql = "insert into jsf_dataSynchro (dataId,conditionId,targetTableField,sourceFieldName,dataDefaultValue,isTran) values (" + 
                  newDateId + "," + tableSynchro[j][6] + ",'" + targetFieldName[k] + "','" + fieldInfo[k] + "','" + ((
                  dataDefaultValue[k] == null || "null".equals(dataDefaultValue[k])) ? "" : dataDefaultValue[k]).replaceAll("'", "&apos;") + "'," + isTran + ")";
                this.base.executeUpdate(sql);
              } else {
                sql = "update jsf_dataSynchro set targetTableField='" + targetFieldName[k] + "',sourceFieldName='" + fieldInfo[k] + "'," + 
                  "dataDefaultValue='" + ((dataDefaultValue[k] == null || "null".equals(dataDefaultValue[k])) ? "" : dataDefaultValue[k])
                  .replaceAll("'", "&apos;") + "',isTran=" + isTran + " where dataId=" + synchroId[k];
                this.base.executeUpdate(sql);
              } 
            } 
            this.base.end();
          } catch (Exception e) {
            this.base.end();
            e.printStackTrace();
          } 
          delete(synchroId, tableSynchro[j][6]);
        } 
      } 
      delete(executeId, tableIdDelete);
    } 
  }
  
  public String[] getIds(String processId, String nodeId) {
    DataSourceBase baseNew = new DataSourceBase();
    ResultSet rsNew = null;
    String sql = "";
    String executeId = "-1";
    String condId = "-1";
    String fieldId = "-1";
    String[] Ids = new String[3];
    try {
      baseNew.begin();
      sql = "SELECT executeId FROM jsf_dataexecute WHERE processId=" + processId + " AND nodeId=" + nodeId;
      rsNew = baseNew.executeQuery(sql);
      while (rsNew.next())
        executeId = String.valueOf(executeId) + "," + rsNew.getString(1); 
      rsNew.close();
      sql = "SELECT conditionId FROM jsf_datacondition WHERE executeId in (" + executeId + ")";
      rsNew = baseNew.executeQuery(sql);
      while (rsNew.next())
        condId = String.valueOf(condId) + "," + rsNew.getString(1); 
      rsNew.close();
      sql = "SELECT dataId FROM jsf_datasynchro WHERE conditionId IN (" + condId + ")";
      rsNew = baseNew.executeQuery(sql);
      while (rsNew.next())
        fieldId = String.valueOf(fieldId) + "," + rsNew.getString(1); 
      rsNew.close();
      Ids[0] = executeId;
      Ids[1] = condId;
      Ids[2] = fieldId;
      baseNew.end();
    } catch (Exception e) {
      baseNew.end();
      e.printStackTrace();
    } 
    return Ids;
  }
  
  public void delete(String[] fieldSynchro, String conditionId) {
    DataSourceBase baseNew = new DataSourceBase();
    String fields = "-1";
    for (int i = 0; i < fieldSynchro.length; i++) {
      if (!fieldSynchro[i].equals("-1") && !fieldSynchro[i].equals(""))
        fields = String.valueOf(fields) + "," + fieldSynchro[i]; 
    } 
    try {
      baseNew.begin();
      String sql = "delete from jsf_dataSynchro where conditionId=" + conditionId + " and dataId not in (" + fields + ")";
      baseNew.executeUpdate(sql);
      baseNew.end();
    } catch (Exception e) {
      baseNew.end();
      e.printStackTrace();
    } 
  }
  
  public void deleteFieldSyn(String conditionId) {
    DataSourceBase baseNew = new DataSourceBase();
    try {
      baseNew.begin();
      String sql = "delete from jsf_dataSynchro where conditionId=" + conditionId;
      baseNew.executeUpdate(sql);
      baseNew.end();
    } catch (Exception e) {
      baseNew.end();
      e.printStackTrace();
    } 
  }
  
  public void deleteFieldSyn(String executeId, String notDeleteTableId) {
    DataSourceBase baseNew = new DataSourceBase();
    try {
      baseNew.begin();
      String sql = "delete from jsf_dataSynchro where conditionId in (select conditionId from jsf_dataCondition where executeId=" + 
        executeId + " and sourceTableId not in (" + notDeleteTableId + "))";
      baseNew.executeUpdate(sql);
      baseNew.end();
    } catch (Exception e) {
      baseNew.end();
      e.printStackTrace();
    } 
  }
  
  public void delete(String executeId, String notDeleteTableId) {
    DataSourceBase baseNew = new DataSourceBase();
    try {
      baseNew.begin();
      deleteFieldSyn(executeId, notDeleteTableId);
      String sql = "delete from jsf_dataCondition where executeId=" + executeId + " and sourceTableId not in (" + notDeleteTableId + ")";
      baseNew.executeUpdate(sql);
      baseNew.end();
    } catch (Exception e) {
      baseNew.end();
      e.printStackTrace();
    } 
  }
  
  public void deleteSynchro(String processId) {
    deleteSynchro(processId, "-1");
  }
  
  public void deleteSynchro(String processId, String nodeId) {
    String[] ids = getIds(processId, nodeId);
    DataSourceBase baseNew = new DataSourceBase();
    String sql = "";
    try {
      baseNew.begin();
      sql = "DELETE from jsf_dataexecute WHERE executeId IN (" + ids[0] + ")";
      baseNew.executeUpdate(sql);
      sql = "DELETE from jsf_datacondition WHERE conditionId IN (" + ids[1] + ")";
      baseNew.executeUpdate(sql);
      sql = "DELETE from jsf_datasynchro WHERE dataId IN (" + ids[2] + ")";
      baseNew.executeUpdate(sql);
      baseNew.end();
    } catch (Exception e) {
      baseNew.end();
      e.printStackTrace();
    } 
  }
  
  public Map<String, Object> getSynchroInfo(String processId, String nodeId) {
    Map<String, Object> infoMap = new HashMap<String, Object>();
    String sql = "SELECT executeId,executeContent,ifSynchro,processId,nodeId,tableId,mainTable FROM jsf_dataexecute WHERE processId=" + 
      processId + " AND nodeId=" + nodeId + " and ifSynchro=1";
    String[] executeInfo = { "", "", "", "", "", "", "" };
    try {
      this.base.begin();
      this.rs = this.base.executeQuery(sql);
      if (this.rs.next()) {
        executeInfo[0] = this.rs.getString(1);
        executeInfo[1] = ((this.rs.getString(2) == null) ? "" : this.rs.getString(2)).replaceAll("&apos;", "'");
        executeInfo[2] = this.rs.getString(3);
        executeInfo[3] = this.rs.getString(4);
        executeInfo[4] = this.rs.getString(5);
        executeInfo[5] = this.rs.getString(6);
        executeInfo[6] = this.rs.getString(7);
        infoMap.put("executeInfo", executeInfo);
      } 
      this.rs.close();
      if (!"".equals(executeInfo[0])) {
        List<String[]> condIds = (List)new ArrayList<String>();
        sql = "SELECT conditionId,targetBaseName,targetTableName,conditionType,conditionContent,sourceTableName,sourceTableId,tableType FROM jsf_datacondition WHERE executeId=" + 
          executeInfo[0];
        this.rs = this.base.executeQuery(sql);
        while (this.rs.next()) {
          String[] info = { "", "", "", "", "", "", "", "" };
          info[0] = this.rs.getString(1);
          info[1] = this.rs.getString(2);
          info[2] = this.rs.getString(3);
          info[3] = this.rs.getString(4);
          info[4] = ((this.rs.getString(5) == null) ? "" : this.rs.getString(5)).replaceAll("&apos;", "'");
          info[5] = this.rs.getString(6);
          info[6] = this.rs.getString(7);
          info[7] = this.rs.getString(8);
          infoMap.put("cond_" + info[6], info);
          String[] condStrings = { info[0], info[6] };
          condIds.add(condStrings);
        } 
        this.rs.close();
        for (int i = 0; i < condIds.size(); i++) {
          String[] condInfo = condIds.get(i);
          List<String[]> synchroList = (List)new ArrayList<String>();
          sql = "SELECT dataId,targetTableField,sourceFieldName,dataDefaultValue FROM jsf_datasynchro WHERE conditionId=" + condInfo[0];
          this.rs = this.base.executeQuery(sql);
          while (this.rs.next()) {
            String[] synchro = { "", "", "", "" };
            synchro[0] = this.rs.getString(1);
            synchro[1] = this.rs.getString(2);
            synchro[2] = (this.rs.getString(3) == null) ? "" : this.rs.getString(3);
            synchro[3] = ((this.rs.getString(4) == null) ? "" : this.rs.getString(4)).replace("&apos;", "'");
            synchroList.add(synchro);
          } 
          infoMap.put("synchro_" + condInfo[1], synchroList);
          this.rs.close();
        } 
      } 
      this.base.end();
    } catch (Exception e) {
      this.base.end();
      e.printStackTrace();
    } 
    return infoMap;
  }
}
