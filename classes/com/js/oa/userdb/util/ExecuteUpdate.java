package com.js.oa.userdb.util;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class ExecuteUpdate {
  private Statement m_statement = null;
  
  private Map m_addFieldValues = new HashMap<Object, Object>();
  
  private String m_tableName = null;
  
  private String m_updateFieldsWithType = null;
  
  private HttpServletRequest request = null;
  
  public ExecuteUpdate() {}
  
  public ExecuteUpdate(Statement statement) {
    setStatement(statement);
  }
  
  public void setStatement(Statement statement) {
    this.m_statement = statement;
  }
  
  public Statement getStatement() throws NullPointerException {
    if (this.m_statement == null)
      throw new NullPointerException(
          "未设置Statement对象！请通过构造函数或setStatement(Statement)方法设置！"); 
    return this.m_statement;
  }
  
  public void setRequest(HttpServletRequest request) throws UnsupportedEncodingException {
    this.request = request;
  }
  
  public void putFieldValue(String field, String value) {
    this.m_addFieldValues.put(field, value);
  }
  
  public String getFieldValue(String strField) {
    return getFieldValues(strField)[0];
  }
  
  public String[] getFieldValues(String strFields) {
    String[] aStrField = strFields.split(",");
    String[] aStrValue = new String[aStrField.length];
    for (int i = 0; i < aStrValue.length; i++) {
      if (this.m_addFieldValues.containsKey(aStrField[i])) {
        aStrValue[i] = (this.m_addFieldValues.get(aStrField[i]) == null) ? "" : 
          String.valueOf(this.m_addFieldValues.get(aStrField[i]));
      } else {
        aStrValue[i] = getParameter(aStrField[i]);
      } 
      if (aStrValue[i] == null) {
        aStrValue[i] = "";
      } else {
        aStrValue[i] = aStrValue[i].trim();
      } 
    } 
    return aStrValue;
  }
  
  private String[][] getFieldNameAndValuesForSql(String strFieldsWithType) {
    String[] aFieldWithType = strFieldsWithType.split(",");
    String[] aFieldName = new String[aFieldWithType.length / 2];
    String[] aFieldType = new String[aFieldWithType.length / 2];
    int j = 0;
    for (int i = 0; i < aFieldWithType.length; i += 2) {
      aFieldName[j] = aFieldWithType[i].trim();
      aFieldType[j] = aFieldWithType[i + 1].trim();
      j++;
    } 
    String strFields = aFieldName[0];
    for (int k = 1; k < aFieldName.length; k++)
      strFields = String.valueOf(strFields) + "," + aFieldName[k]; 
    String[] aFieldValue = getFieldValues(strFields);
    List<String> listName = new ArrayList();
    List<String> listValue = new ArrayList();
    for (int m = 0; m < aFieldName.length; m++) {
      if (aFieldType[m].equals("1")) {
        aFieldValue[m] = aFieldValue[m].replaceAll("'", "''");
        aFieldValue[m] = "'" + aFieldValue[m] + "'";
      } 
      if (!aFieldType[m].equals("0") || !aFieldValue[m].equals("")) {
        listName.add(aFieldName[m]);
        listValue.add(aFieldValue[m]);
      } 
    } 
    String[][] aNameValue = new String[2][listName.size()];
    listName.toArray(aNameValue[0]);
    listValue.toArray(aNameValue[1]);
    return aNameValue;
  }
  
  public void setTableName(String tableName) {
    this.m_tableName = tableName;
  }
  
  public String getTableName() throws NullPointerException {
    if (this.m_tableName == null)
      throw new NullPointerException("未设置操作的表名！"); 
    return this.m_tableName;
  }
  
  public void setUpdateFieldsWithType(String updateFieldsWithType) {
    this.m_updateFieldsWithType = updateFieldsWithType;
  }
  
  public String getUpdateFieldsWithType() throws NullPointerException {
    if (this.m_updateFieldsWithType == null)
      throw new NullPointerException("未设置更新的字段名称及类型！"); 
    return this.m_updateFieldsWithType;
  }
  
  public int executeInsert() throws SQLException, NullPointerException {
    return executeInsert(getTableName(), getUpdateFieldsWithType());
  }
  
  public int executeInsert(String strTableName, String strFieldsWithType) throws SQLException, NullPointerException {
    String[][] aNameValue = getFieldNameAndValuesForSql(strFieldsWithType);
    String[] aFieldName = aNameValue[0];
    String[] aFieldValue = aNameValue[1];
    String strFieldNames = aFieldName[0];
    for (int i = 1; i < aFieldName.length; i++)
      strFieldNames = String.valueOf(strFieldNames) + "," + aFieldName[i]; 
    StringBuffer sbSql = new StringBuffer();
    sbSql.append(" insert into " + strTableName + " ( ");
    sbSql.append(String.valueOf(strFieldNames) + ") ");
    sbSql.append(" VALUES ( ");
    for (int j = 0; j < aFieldValue.length - 1; j++)
      sbSql.append(String.valueOf(aFieldValue[j]) + ","); 
    sbSql.append(String.valueOf(aFieldValue[aFieldValue.length - 1]) + ")");
    return getStatement().executeUpdate(sbSql.toString());
  }
  
  public int executeUpdate(String strKeyFieldsWithType) throws SQLException, NullPointerException {
    return executeUpdate(getTableName(), getUpdateFieldsWithType(), 
        strKeyFieldsWithType);
  }
  
  public int executeUpdate(String strTableName, String strUpdateFieldsWithType, String strKeyFieldsWithType) throws SQLException, NullPointerException {
    String[][] aTemp = getFieldNameAndValuesForSql(strUpdateFieldsWithType);
    String[] aUFieldName = aTemp[0];
    String[] aUFieldValue = aTemp[1];
    aTemp = getFieldNameAndValuesForSql(strKeyFieldsWithType);
    String[] aKFieldName = aTemp[0];
    String[] aKFieldValue = aTemp[1];
    StringBuffer sbSql = new StringBuffer();
    sbSql.append(" update " + strTableName + " set ");
    for (int i = 0; i < aUFieldName.length - 1; i++)
      sbSql.append(String.valueOf(aUFieldName[i].trim()) + " = " + aUFieldValue[i] + ","); 
    int iLast = aUFieldName.length - 1;
    sbSql.append(String.valueOf(aUFieldName[iLast]) + " = " + aUFieldValue[iLast] + " ");
    for (int j = 0; j < aKFieldName.length; j++) {
      if (j == 0) {
        sbSql.append(" where " + aKFieldName[j].trim() + " = " + aKFieldValue[j] + 
            " ");
      } else {
        sbSql.append(" AND " + aKFieldName[j].trim() + " = " + aKFieldValue[j] + 
            " ");
      } 
    } 
    return getStatement().executeUpdate(sbSql.toString());
  }
  
  private String getParameter(String field) {
    String value = this.request.getParameter(field);
    value = (value == null) ? "" : value.trim();
    return value;
  }
}
