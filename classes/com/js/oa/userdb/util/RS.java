package com.js.oa.userdb.util;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RS {
  public static Map toMap(ResultSet rs) throws SQLException {
    if (rs == null)
      return null; 
    Map<Object, Object> map = null;
    try {
      ResultSetMetaData rsmd = rs.getMetaData();
      int intColCount = rsmd.getColumnCount();
      String[] aStrColName = new String[intColCount];
      int i = 0;
      for (i = 0; i < intColCount; i++)
        aStrColName[i] = rsmd.getColumnName(i + 1).toLowerCase(); 
      Object objValue = null;
      if (rs.next()) {
        map = new HashMap<Object, Object>(intColCount);
        for (i = 0; i < intColCount; i++) {
          objValue = rs.getString(aStrColName[i]);
          if (objValue == null)
            objValue = new String(""); 
          map.put(aStrColName[i], objValue);
        } 
      } 
    } finally {
      if (rs != null)
        rs.close(); 
    } 
    return map;
  }
  
  public static StringMap toStrMap(ResultSet rs) throws SQLException {
    if (rs == null)
      return null; 
    StringMap objStrMap = null;
    try {
      ResultSetMetaData rsmd = rs.getMetaData();
      int intColCount = rsmd.getColumnCount();
      String[] aStrColName = new String[intColCount];
      int i = 0;
      for (i = 0; i < intColCount; i++)
        aStrColName[i] = rsmd.getColumnName(i + 1).toLowerCase(); 
      objStrMap = new StringMap(intColCount);
      String strValue = null;
      if (rs.next())
        for (i = 0; i < intColCount; i++) {
          strValue = rs.getString(aStrColName[i]);
          if (strValue == null)
            strValue = new String(""); 
          objStrMap.put(aStrColName[i], strValue.trim());
        }  
    } finally {
      if (rs != null)
        rs.close(); 
    } 
    return objStrMap;
  }
  
  public static Map[] toMaps(ResultSet rs) throws SQLException {
    List<Map> list = toList(rs);
    if (list == null)
      return null; 
    Map[] aMap = new Map[list.size()];
    for (int i = 0; i < aMap.length; i++)
      aMap[i] = list.get(i); 
    return aMap;
  }
  
  public static String toStr(ResultSet rs) throws SQLException {
    if (rs == null)
      return null; 
    String strRet = "";
    try {
      if (rs.next()) {
        ResultSetMetaData rsmd = rs.getMetaData();
        if (rsmd.getColumnTypeName(1).toUpperCase().indexOf("CLOB") >= 0) {
          strRet = clobToString(rs.getClob(1));
        } else {
          strRet = rs.getString(1);
        } 
        strRet = (strRet == null) ? "" : strRet.trim();
      } 
    } finally {
      if (rs != null)
        rs.close(); 
    } 
    return strRet;
  }
  
  public static String[] toStrArr1(ResultSet rs) throws SQLException {
    if (rs == null)
      return null; 
    List<String> list = new ArrayList();
    String[] aRet = (String[])null;
    try {
      while (rs.next())
        list.add(rs.getString(1)); 
      aRet = new String[list.size()];
      for (int i = 0; i < aRet.length; i++) {
        aRet[i] = (list.get(i) == null) ? "" : String.valueOf(list.get(i));
        aRet[i] = aRet[i].trim();
      } 
    } finally {
      if (rs != null)
        rs.close(); 
    } 
    return aRet;
  }
  
  public static String[][] toStrArr2(ResultSet rs) throws SQLException {
    return toStrArr2(rs, 2);
  }
  
  public static String[][] toStrArr2(ResultSet rs, int columnCount) throws SQLException {
    List<String> colTypeList = new ArrayList();
    ArrayList[] arrayOfArrayList = new ArrayList[columnCount];
    for (int i = 0; i < columnCount; i++)
      arrayOfArrayList[i] = new ArrayList(); 
    String[][] aRet = (String[][])null;
    try {
      if (rs != null) {
        ResultSetMetaData rsmd = rs.getMetaData();
        for (int k = 0; k < rsmd.getColumnCount(); k++) {
          if (rsmd.getColumnTypeName(k + 1).toUpperCase().indexOf("CLOB") >= 0)
            colTypeList.add(String.valueOf(k)); 
        } 
      } 
      while (rs.next()) {
        for (int k = 0; k < columnCount; k++) {
          if (colTypeList.contains(String.valueOf(k))) {
            arrayOfArrayList[k].add(clobToString(rs.getClob(k + 1)));
          } else {
            arrayOfArrayList[k].add(rs.getString(k + 1));
          } 
        } 
      } 
      aRet = new String[arrayOfArrayList[0].size()][columnCount];
      for (int j = 0; j < aRet.length; j++) {
        for (int k = 0; k < columnCount; k++)
          aRet[j][k] = (arrayOfArrayList[k].get(j) == null) ? "" : 
            String.valueOf(arrayOfArrayList[k].get(j)).trim(); 
      } 
    } finally {
      if (rs != null)
        rs.close(); 
    } 
    return aRet;
  }
  
  public static List toList(ResultSet rs) throws SQLException {
    if (rs == null)
      return null; 
    List<Map<Object, Object>> list = new ArrayList();
    try {
      ResultSetMetaData rsmd = rs.getMetaData();
      int intColCount = rsmd.getColumnCount();
      String[] aStrColName = new String[intColCount];
      int i = 0;
      for (i = 0; i < intColCount; i++)
        aStrColName[i] = rsmd.getColumnName(i + 1).toLowerCase(); 
      Map<Object, Object> mapTemp = null;
      Object objValue = null;
      while (rs.next()) {
        mapTemp = new HashMap<Object, Object>(intColCount);
        for (i = 0; i < intColCount; i++) {
          objValue = rs.getString(aStrColName[i]);
          if (objValue == null)
            objValue = new String(""); 
          mapTemp.put(aStrColName[i], objValue);
        } 
        list.add(mapTemp);
      } 
      rs.close();
    } finally {
      if (rs != null)
        rs.close(); 
    } 
    return list;
  }
  
  private static String clobToString(Clob clobObject) throws NumberFormatException, SQLException {
    if (clobObject == null)
      return ""; 
    return clobObject.getSubString(1L, (int)clobObject.length());
  }
}
