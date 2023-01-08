package com.js.oa.jsflow.util;

import com.js.util.util.DataSourceBase;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class InitWorkFlowData {
  public Map getClassMethod(String mark, HttpServletRequest request) {
    HttpSession session = request.getSession();
    String workIdString = (String)request.getAttribute("wfWorkId");
    String tableIdString = (String)request.getAttribute("tableId");
    String recordIdString = (String)request.getAttribute("recordId");
    String userIdString = session.getAttribute("userId").toString();
    String processIdString = (String)request.getAttribute("processId");
    String userAccount = session.getAttribute("userAccount").toString();
    String orgId = session.getAttribute("orgId").toString();
    String orgName = session.getAttribute("orgName").toString();
    Map<String, String> fieldTypeMap = getFieldType(tableIdString);
    Map<Object, Object> result = new HashMap<Object, Object>();
    if ("1".equals(mark)) {
      List<Object[]> list = getTpageList(tableIdString);
      Object[] obj = list.get(0);
      try {
        if (obj[2] != null && !"".equals(obj[2]) && "1".equals(obj[2]))
          if (obj[3] != null && !"".equals(obj[3]) && "1".equals(obj[3])) {
            String[] paraString = (String[])null;
            if (obj[8] != null && !"".equals(obj[8]))
              paraString = obj[8].toString().split(","); 
            Class<?> cls = Class.forName(obj[6].toString().replace(".class", "").replace(" ", ""));
            if (cls != null) {
              Constructor<?> ct = cls.getConstructor(null);
              Class[] arg = new Class[paraString.length];
              Object retobj = ct.newInstance(null);
              Object[] arglist = new Object[paraString.length];
              for (int z = 0; z < paraString.length; z++) {
                arg[z] = String.class;
                if (paraString[z].equals("@$@userId@$@")) {
                  arglist[z] = userIdString;
                } else if (paraString[z].equals("@$@tableId@$@")) {
                  arglist[z] = tableIdString;
                } else if (paraString[z].equals("@$@workId@$@")) {
                  arglist[z] = workIdString;
                } else if (paraString[z].equals("@$@recordId@$@")) {
                  arglist[z] = recordIdString;
                } else if (paraString[z].equals("@$@processId@$@")) {
                  arglist[z] = processIdString;
                } else if (paraString[z].equals("@$@userAccount@$@")) {
                  arglist[z] = userAccount;
                } else if (paraString[z].equals("@$@orgId@$@")) {
                  arglist[z] = orgId;
                } else if (paraString[z].equals("@$@orgName@$@")) {
                  arglist[z] = orgName;
                } else {
                  arglist[z] = getValueFromRequest(request, paraString[z]);
                } 
              } 
              String mappingFeild = (obj[9] == null) ? "" : obj[9].toString();
              String[] mappingFeildStrings = mappingFeild.split(";;;");
              Map<String, String> fieldMap = new HashMap<String, String>();
              for (int xx = 0; xx < mappingFeildStrings.length; xx++) {
                if (mappingFeildStrings[xx] != null) {
                  String[] mappingFeildDatasource = mappingFeildStrings[xx].split("=:=");
                  if (fieldMap.get(mappingFeildDatasource[1]) == null) {
                    if ("450".equals(fieldTypeMap.get(mappingFeildDatasource[0]))) {
                      fieldMap.put(mappingFeildDatasource[1], String.valueOf(mappingFeildDatasource[0]) + "," + mappingFeildDatasource[0] + "_temp");
                    } else {
                      fieldMap.put(mappingFeildDatasource[1], mappingFeildDatasource[0]);
                    } 
                  } else if ("450".equals(fieldTypeMap.get(mappingFeildDatasource[0]))) {
                    fieldMap.put(mappingFeildDatasource[1], String.valueOf(fieldMap.get(mappingFeildDatasource[1])) + 
                        "," + mappingFeildDatasource[0] + "," + mappingFeildDatasource[0] + "_temp");
                  } else {
                    fieldMap.put(mappingFeildDatasource[1], 
                        String.valueOf(fieldMap.get(mappingFeildDatasource[1])) + "," + mappingFeildDatasource[0]);
                  } 
                } 
              } 
              Map<Object, Object> resultValue = new HashMap<Object, Object>();
              Method meth = cls.getMethod((obj[7] == null) ? "" : obj[7].toString().replace(" ", ""), arg);
              if (meth != null) {
                resultValue = (Map<Object, Object>)meth.invoke(retobj, arglist);
                for (String key : resultValue.keySet()) {
                  if (fieldMap.get(key) != null) {
                    if (((String)fieldMap.get(key)).indexOf(",") > 0) {
                      String[] fields = ((String)fieldMap.get(key)).split(",");
                      for (int i = 0; i < fields.length; i++)
                        result.put(fields[i], resultValue.get(key)); 
                      continue;
                    } 
                    result.put(fieldMap.get(key), resultValue.get(key));
                    continue;
                  } 
                  result.put(key, resultValue.get(key));
                } 
              } 
            } 
          } else if (obj[3] != null && !"".equals(obj[3]) && "0".equals(obj[3])) {
            if (obj[4] != null && !"".equals(obj[4])) {
              Connection conn = null;
              DataSourceBase base = new DataSourceBase();
              ResultSet rSet = null;
              if ("system".equals(obj[4].toString())) {
                conn = base.getDataSource().getConnection();
              } else {
                conn = base.getDataSource(obj[4].toString()).getConnection();
              } 
              String sql = (obj[5] == null) ? "" : obj[5].toString();
              rSet = conn.createStatement().executeQuery(sql);
              Map<Object, Object> map = new HashMap<Object, Object>();
              String mappingFeild = (obj[9] == null) ? "" : obj[9].toString();
              String[] mappingFeildStrings = mappingFeild.split(";;;");
              List<Object[]> feildList = new ArrayList();
              for (int xx = 0; xx < mappingFeildStrings.length; xx++) {
                if (mappingFeildStrings[0] != null) {
                  String[] mappingFeildDatasource = mappingFeildStrings[xx].split("=:=");
                  Object[] objStriObjects = new Object[mappingFeildDatasource.length];
                  objStriObjects[0] = mappingFeildDatasource[0];
                  objStriObjects[1] = mappingFeildDatasource[1];
                  feildList.add(objStriObjects);
                } 
              } 
              Object[] objcStrings = (Object[])null;
              while (rSet.next()) {
                for (int mf = 0; mf < feildList.size(); mf++) {
                  objcStrings = feildList.get(mf);
                  map.put(objcStrings[0], rSet.getString(mf + 1));
                } 
              } 
              result = map;
            } 
          }  
      } catch (ClassNotFoundException e) {
        System.out.println("操作失败！！！检查路径wabapps/jsoa/WEB-INF/classes/是否有[" + obj[6] + "]类");
      } catch (NoSuchMethodException e) {
        System.out.println("操作失败！！！检查" + obj[6].toString() + "类中是否有【" + obj[7] + "】方法！！");
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return result;
  }
  
  public List getTpageList(String tableId) {
    List<Object[]> PageList = new ArrayList();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      String sqlString = "select PAGE_ID,PAGE_NAME,initdata,initdatatype,datasourcename,fetchsql,interfacename,interfacemethod,interfacemethodpara,mappingfields  from tpage where page_id=" + 








        
        tableId;
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sqlString);
      while (rs.next()) {
        Object[] obj = new Object[10];
        obj[0] = rs.getString(1);
        obj[1] = rs.getString(2);
        obj[2] = rs.getString(3);
        obj[3] = rs.getString(4);
        obj[4] = rs.getString(5);
        obj[5] = rs.getString(6);
        obj[6] = rs.getString(7);
        obj[7] = rs.getString(8);
        obj[8] = rs.getString(9);
        obj[9] = rs.getString(10);
        PageList.add(obj);
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (SQLException e1) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e3) {
          e3.printStackTrace();
        }  
      if (stmt != null)
        try {
          stmt.close();
        } catch (SQLException e2) {
          e2.printStackTrace();
        }  
      e1.printStackTrace();
    } 
    return PageList;
  }
  
  public static String getKeyValue(Long fieldId, Map<String, String> paraMap, HttpServletRequest request) {
    String sql = "SELECT field_value FROM tfield WHERE field_id=" + fieldId;
    DataSourceBase base = new DataSourceBase();
    String fieldValue = "";
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      if (rs.next())
        fieldValue = rs.getString(1); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return getKeyValue(fieldValue, paraMap, request);
  }
  
  public static String getKeyValue(String fieldValue, Map<String, String> paraMap, HttpServletRequest request) {
    String[] classInfo = fieldValue.substring(12, fieldValue.length() - 1).split(";;;");
    String[] params = (String[])null;
    if (classInfo[2].length() > 0 && !"".equals(classInfo[2]) && !"null".equals(classInfo[2]))
      params = classInfo[2].split(","); 
    if (params == null)
      params = new String[0]; 
    String returnValue = "";
    Map<String, String> result = null;
    String paramStr = "";
    try {
      Class<?> cls = Class.forName(classInfo[0]);
      Constructor<?> ct = cls.getConstructor(null);
      Class[] arg = new Class[params.length];
      Object[] arglist = new Object[params.length];
      for (int i = 0; i < params.length; i++) {
        arg[i] = String.class;
        if ("request".equals(params[i])) {
          arg[i] = HttpServletRequest.class;
          arglist[i] = request;
        } else if (params[i].equals("@$@tableId@$@")) {
          arglist[i] = paraMap.get("tableId");
        } else if (params[i].equals("@$@processId@$@")) {
          arglist[i] = paraMap.get("processId");
        } else if (params[i].equals("@$@userId@$@")) {
          arglist[i] = paraMap.get("userId");
        } else if (params[i].equals("@$@userName@$@")) {
          arglist[i] = paraMap.get("userName");
        } else if (params[i].equals("@$@orgId@$@")) {
          arglist[i] = paraMap.get("orgId");
        } else if (params[i].equals("@$@orgName@$@")) {
          arglist[i] = paraMap.get("orgName");
        } else if (params[i].equals("@$@corpId@$@")) {
          arglist[i] = paraMap.get("corpId");
        } else if (params[i].equals("@$@userAccount@$@")) {
          arglist[i] = paraMap.get("userAccount");
        } else {
          arglist[i] = getValueFromRequest(request, params[i]);
        } 
        paramStr = String.valueOf(paramStr) + arglist[i] + "#";
      } 
      Method meth = cls.getMethod(classInfo[1], arg);
      Object retobj = ct.newInstance(null);
      result = (Map<String, String>)meth.invoke(retobj, arglist);
    } catch (ClassNotFoundException e) {
      System.out.println("操作失败！！！检查路径wabapps/jsoa/WEB-INF/classes/是否有[" + classInfo[0] + "]类");
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      System.out.println("操作失败！！！检查[" + classInfo[0] + "]是否有[" + classInfo[1] + "]方法");
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    if (result != null)
      for (String key : result.keySet())
        returnValue = String.valueOf(returnValue) + key + "{@" + paramStr + "@}/" + (String)result.get(key) + ";";  
    if (returnValue.equals(""))
      returnValue = String.valueOf(returnValue) + "/暂无数据;"; 
    return returnValue;
  }
  
  public static String getShowContext(String fieldValue, String paramStr) {
    if (paramStr.indexOf(",") > 0) {
      String[] paramStrs = paramStr.split(",");
      paramStr = paramStrs[0].substring(paramStrs[0].indexOf("{@"));
      if (paramStr.startsWith("{@"))
        paramStr = paramStr.substring(2); 
      if (paramStr.endsWith("@}"))
        paramStr = paramStr.substring(0, paramStr.length() - 2); 
      String[] classInfo = fieldValue.substring(12, fieldValue.length() - 1).split(";;;");
      String[] params = { "" };
      if (classInfo[2].length() > 0)
        params = paramStr.split("#"); 
      String returnValue = "";
      Map<String, String> result = null;
      try {
        Class<?> cls = Class.forName(classInfo[0]);
        Constructor<?> ct = cls.getConstructor(null);
        Class[] arg = new Class[params.length];
        Object[] arglist = new Object[params.length];
        for (int i = 0; i < params.length; i++) {
          arg[i] = String.class;
          arglist[i] = params[i];
        } 
        Method meth = cls.getMethod(classInfo[1], arg);
        Object retobj = ct.newInstance(null);
        result = (Map<String, String>)meth.invoke(retobj, arglist);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      for (String key : result.keySet())
        returnValue = String.valueOf(returnValue) + key + "{@" + paramStr + "@}/" + (String)result.get(key) + ";"; 
      if (returnValue.equals(""))
        returnValue = String.valueOf(returnValue) + "/暂无数据;"; 
      return returnValue;
    } 
    return paramStr;
  }
  
  public String getOnchange(HttpServletRequest request) {
    HttpSession session = request.getSession();
    String curIndex = request.getParameter("curIndex");
    String fieldId = request.getParameter("fieldId");
    String transValue = request.getParameter("transValue");
    String userId = session.getAttribute("userId").toString();
    String orgId = session.getAttribute("orgId").toString();
    String userAccount = session.getAttribute("userAccount").toString();
    String userName = session.getAttribute("userName").toString();
    String recordId = "";
    String processId = "";
    String[] paraString = (String[])null;
    Map result = null;
    String sql = "SELECT field_value,field_toffield,field_interfacename,field_interfacemethod,field_interfacemethodpara,field_interfacetype FROM tfield WHERE field_id=" + 
      fieldId;
    String[][] fieldInfo = (new DataSourceBase()).queryArrayBySql(sql);
    String field_interfacename = fieldInfo[0][2];
    String field_interfacemethod = fieldInfo[0][3];
    String field_interfacemethodpara = fieldInfo[0][4];
    try {
      if (field_interfacemethodpara != null && !"".equals(field_interfacemethodpara) && !"null".equals(field_interfacemethodpara))
        paraString = field_interfacemethodpara.toString().split(","); 
      if (paraString == null)
        paraString = new String[0]; 
      Class<?> cls = Class.forName(field_interfacename.toString().replace(".class", "").replace(" ", ""));
      if (cls != null) {
        Constructor<?> ct = cls.getConstructor(null);
        Class[] arg = new Class[paraString.length + 1];
        Object retobj = ct.newInstance(null);
        Object[] arglist = new Object[paraString.length + 1];
        arg[0] = String.class;
        arglist[0] = transValue;
        for (int z = 1; z < paraString.length + 1; z++) {
          arg[z] = String.class;
          if (paraString[z - 1].equals("@$@userId@$@")) {
            arglist[z] = userId;
          } else if (paraString[z - 1].equals("@$@orgId@$@")) {
            arglist[z] = orgId;
          } else if (paraString[z - 1].equals("@$@userName@$@")) {
            arglist[z] = userName;
          } else if (paraString[z - 1].equals("@$@recordId@$@")) {
            arglist[z] = recordId;
          } else if (paraString[z - 1].equals("@$@processId@$@")) {
            arglist[z] = processId;
          } else if (paraString[z - 1].equals("@$@userAccount@$@")) {
            arglist[z] = userAccount;
          } else {
            arglist[z] = getValueFromRequest(request, paraString[z - 1]);
          } 
        } 
        Method meth = cls.getMethod((field_interfacemethod == null) ? "" : field_interfacemethod.toString().replace(" ", ""), arg);
        if (meth != null)
          result = (Map)meth.invoke(retobj, arglist); 
      } 
    } catch (ClassNotFoundException e) {
      System.out.println("操作失败！！！检查路径wabapps/jsoa/WEB-INF/classes/是否有[" + field_interfacename.toString().replace(".class", "").replace(" ", "") + "]类");
    } catch (NoSuchMethodException e) {
      System.out.println(
          ("操作失败！！！检查[" + field_interfacename.toString().replace(".class", "").replace(" ", "") + "]是否有[" + field_interfacemethod == null) ? "" : (String.valueOf(field_interfacemethod.toString().replace(" ", "")) + "]方法"));
    } catch (Exception e) {
      e.printStackTrace();
    } 
    StringBuffer buffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?><root>");
    buffer.append("<curIndex>").append(curIndex).append("</curIndex>");
    if (result != null) {
      String[] fieldToffield = fieldInfo[0][1].split(";;;");
      Map<String, String> fieldMap = new HashMap<String, String>();
      for (int i = 0; i < fieldToffield.length; i++) {
        String[] fieldKeyValue = fieldToffield[i].split("=:=");
        if (fieldMap.get(fieldKeyValue[1]) == null) {
          fieldMap.put(fieldKeyValue[1], fieldKeyValue[0]);
        } else {
          fieldMap.put(fieldKeyValue[1], String.valueOf(fieldMap.get(fieldKeyValue[1])) + "," + fieldKeyValue[0]);
        } 
      } 
      if (result.get("error") != null) {
        buffer.append("<data>");
        buffer.append("<formfield>").append("error").append("</formfield>");
        buffer.append("<fetchdata>").append(0).append("</fetchdata>");
        buffer.append("<content>").append(result.get("error")).append("</content>");
        buffer.append("</data>");
      } else {
        for (String key : result.keySet()) {
          if (fieldMap.get(key) != null) {
            if (((String)fieldMap.get(key)).indexOf(",") > 0) {
              String[] fields = ((String)fieldMap.get(key)).split(",");
              for (int j = 0; j < fields.length; j++) {
                buffer.append("<data>");
                buffer.append("<formfield>").append(fields[j]).append("</formfield>");
                buffer.append("<fetchdata>").append(0).append("</fetchdata>");
                buffer.append("<content>").append(result.get(key)).append("</content>");
                buffer.append("</data>");
              } 
              continue;
            } 
            buffer.append("<data>");
            buffer.append("<formfield>").append(fieldMap.get(key)).append("</formfield>");
            buffer.append("<fetchdata>").append(0).append("</fetchdata>");
            buffer.append("<content>").append(result.get(key)).append("</content>");
            buffer.append("</data>");
            continue;
          } 
          buffer.append("<data>");
          buffer.append("<formfield>").append(key).append("</formfield>");
          buffer.append("<fetchdata>").append(0).append("</fetchdata>");
          buffer.append("<content>").append(result.get(key)).append("</content>");
          buffer.append("</data>");
        } 
      } 
    } 
    buffer.append("</root>");
    return buffer.toString();
  }
  
  public static String getValueFromRequest(HttpServletRequest request, String param) {
    param = param.replaceAll("\\@\\$\\@userId\\@\\$\\@", request.getSession().getAttribute("userId").toString());
    param = param.replaceAll("\\@\\$\\@orgId\\@\\$\\@", request.getSession().getAttribute("orgId").toString());
    param = param.replaceAll("\\@\\$\\@userAccount\\@\\$\\@", request.getSession().getAttribute("userAccount").toString());
    param = param.replaceAll("\\@\\$\\@userName\\@\\$\\@", request.getSession().getAttribute("userName").toString());
    try {
      String[] paras = param.split("\\@\\@");
      for (int z = 1; z < paras.length; z += 2) {
        String pageValue = "";
        if (paras[z].startsWith("*")) {
          if ("*year".equalsIgnoreCase(paras[z])) {
            pageValue = (new StringBuilder(String.valueOf((new Date()).getYear() + 1900))).toString();
          } else if ("*month".equalsIgnoreCase(paras[z])) {
            pageValue = (new StringBuilder(String.valueOf((new Date()).getMonth() + 1))).toString();
          } else if ("*day".equalsIgnoreCase(paras[z])) {
            pageValue = (new StringBuilder(String.valueOf((new Date()).getDate()))).toString();
          } else if (paras[z].startsWith("*format")) {
            SimpleDateFormat format = new SimpleDateFormat(paras[z].substring(7));
            pageValue = format.format(new Date());
          } else {
            pageValue = paras[z].substring(1);
          } 
        } else if (request != null) {
          if (paras[z].startsWith("#")) {
            String key = paras[z].substring(1);
            pageValue = (request.getParameter(key) != null) ? request.getParameter(key) : key;
          } else if (paras[z].startsWith("$")) {
            String key = paras[z].substring(1);
            Object object = (request.getAttribute(key) != null) ? request.getAttribute(key) : key;
          } else if (paras[z].startsWith("%")) {
            String key = paras[z].substring(1);
            Object object = (request.getSession().getAttribute(key) != null) ? request.getSession().getAttribute(key) : key;
          } else if (request.getParameter(paras[z]) != null) {
            pageValue = request.getParameter(paras[z]);
          } else if (request.getAttribute(paras[z]) != null) {
            Object object = request.getAttribute(paras[z]);
          } else if (request.getSession().getAttribute(paras[z]) != null) {
            Object object = request.getSession().getAttribute(paras[z]);
          } else {
            pageValue = paras[z];
          } 
        } else {
          pageValue = paras[z];
        } 
        param = param.replace("@@" + paras[z] + "@@", pageValue);
      } 
    } catch (Exception e) {
      System.out.println("接口参数取值出现问题，请检查参数格式：" + param);
    } 
    return param;
  }
  
  public static String getValueFromRequest(Map request, String param) {
    try {
      String[] paras = param.split("\\@\\@");
      for (int z = 1; z < paras.length; z += 2) {
        String pageValue = "";
        if (paras[z].startsWith("*")) {
          if ("*year".equalsIgnoreCase(paras[z])) {
            pageValue = (new StringBuilder(String.valueOf((new Date()).getYear() + 1900))).toString();
          } else if ("*month".equalsIgnoreCase(paras[z])) {
            pageValue = (new StringBuilder(String.valueOf((new Date()).getMonth() + 1))).toString();
          } else if ("*day".equalsIgnoreCase(paras[z])) {
            pageValue = (new StringBuilder(String.valueOf((new Date()).getDate()))).toString();
          } else if (paras[z].startsWith("*format")) {
            SimpleDateFormat format = new SimpleDateFormat(paras[z].substring(7));
            pageValue = format.format(new Date());
          } else {
            pageValue = paras[z].substring(1);
          } 
        } else if (request != null) {
          String key = paras[z];
          if (paras[z].startsWith("#") || paras[z].startsWith("$") || paras[z].startsWith("%"))
            key = paras[z].substring(1); 
          pageValue = key;
          if (request.get(key) != null) {
            pageValue = (String)request.get(key);
          } else {
            List<String[]> list = (List)request.get("list");
            for (int t = 0; t < list.size(); t++) {
              String[] str = list.get(t);
              if (str[0].equals(key)) {
                pageValue = str[2];
                break;
              } 
            } 
          } 
        } else {
          pageValue = paras[z];
        } 
        param = param.replace("@@" + paras[z] + "@@", pageValue);
      } 
    } catch (Exception e) {
      System.out.println("接口参数取值出现问题，请检查参数格式：" + param);
    } 
    return param;
  }
  
  public Map<String, String> getFieldType(String tableId) {
    Map<String, String> map = new HashMap<String, String>();
    String sql = "SELECT f.field_name,f.field_show FROM tarea a JOIN ttable t ON a.area_table=t.table_name JOIN tfield f ON f.field_table=t.table_id WHERE a.PAGE_ID=" + 
      tableId;
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      while (rs.next())
        map.put(rs.getString(1), rs.getString(2)); 
      rs.close();
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return map;
  }
  
  public static String getNdKingking(String RecordId) {
    String nd = "";
    String sql = "select nd from king_yszj where king_yszj_id=" + RecordId;
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      while (rs.next())
        nd = rs.getString(1); 
      rs.close();
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return nd;
  }
  
  public static String getValueFromRequestKing(HttpServletRequest request, String param, String parentRecordId) {
    try {
      String nd = "";
      String[] paras = param.split("\\@\\@");
      for (int z = 1; z < paras.length; z += 2) {
        String pageValue = "";
        if (paras[z].startsWith("*")) {
          if ("*year".equalsIgnoreCase(paras[z])) {
            pageValue = (new StringBuilder(String.valueOf((new Date()).getYear() + 1900))).toString();
            nd = getNdKingking(parentRecordId);
            if (!"".equals(nd) && nd != null && !"null".equals(nd))
              pageValue = nd; 
          } else if ("*month".equalsIgnoreCase(paras[z])) {
            pageValue = (new StringBuilder(String.valueOf((new Date()).getMonth() + 1))).toString();
          } else if ("*day".equalsIgnoreCase(paras[z])) {
            pageValue = (new StringBuilder(String.valueOf((new Date()).getDate()))).toString();
          } else if (paras[z].startsWith("*format")) {
            SimpleDateFormat format = new SimpleDateFormat(paras[z].substring(7));
            pageValue = format.format(new Date());
          } else {
            pageValue = paras[z].substring(1);
          } 
        } else if (request != null) {
          if (paras[z].startsWith("#")) {
            String key = paras[z].substring(1);
            pageValue = (request.getParameter(key) != null) ? request.getParameter(key) : key;
          } else if (paras[z].startsWith("$")) {
            String key = paras[z].substring(1);
            Object object = (request.getAttribute(key) != null) ? request.getAttribute(key) : key;
          } else if (paras[z].startsWith("%")) {
            String key = paras[z].substring(1);
            Object object = (request.getSession().getAttribute(key) != null) ? request.getSession().getAttribute(key) : key;
          } else if (request.getParameter(paras[z]) != null) {
            pageValue = request.getParameter(paras[z]);
          } else if (request.getAttribute(paras[z]) != null) {
            Object object = request.getAttribute(paras[z]);
          } else if (request.getSession().getAttribute(paras[z]) != null) {
            Object object = request.getSession().getAttribute(paras[z]);
          } else {
            pageValue = paras[z];
          } 
        } else {
          pageValue = paras[z];
        } 
        param = param.replace("@@" + paras[z] + "@@", pageValue);
      } 
    } catch (Exception e) {
      System.out.println("接口参数取值出现问题，请检查参数格式：" + param);
    } 
    return param;
  }
}
