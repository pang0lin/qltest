package com.js.oa.form;

import com.js.system.util.StaticParam;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;

public class ZkyFormula {
  public double getScore(HttpServletRequest request, String recordId) {
    double df = -1.0D;
    String sql = "select area_table from tarea where page_id=" + request.getParameter("tableId");
    String tableName = "";
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      if (rs.next())
        tableName = rs.getString(1); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    if ("t_grxx".equals(tableName)) {
      df = getGrxxScore(request);
    } else if ("t_lwzz".equals(tableName)) {
      df = getLwzzScore(request);
    } else if ("t_yhdjx".equals(tableName)) {
      df = getYhdjxScore(request);
    } else if ("t_zzsbjx".equals(tableName)) {
      df = getZzsbjxScore(request);
    } else if ("t_sbzl".equals(tableName)) {
      df = getSbzlScore(request);
    } else if ("t_xshy".equals(tableName)) {
      df = getXshyScore(request);
    } else if ("t_kyxm".equals(tableName)) {
      df = getKyxmScore(request);
    } else if ("t_rcpy".equals(tableName)) {
      df = getRcpyScore(request);
    } else if ("t_gjzzrz".equals(tableName)) {
      df = getGjzzrzScore(request);
    } else if ("t_gjqkrz".equals(tableName)) {
      df = getGjqkrzScore(request);
    } else if ("t_gjhyrz".equals(tableName)) {
      df = getGjhyrzScore(request);
    } else if ("t_cf".equals(tableName)) {
      df = getCfScore(request);
    } else if ("t_lf".equals(tableName)) {
      df = getLfScore(request);
    } else if ("t_gjhz".equals(tableName)) {
      df = getGjhzScore(request);
    } else if ("t_ydhz".equals(tableName)) {
      df = getYdhzScore(request);
    } else if ("t_zxbg".equals(tableName)) {
      df = getZxbgScore(request);
    } 
    updateTable(tableName, recordId, df);
    return df;
  }
  
  public double getGrxxScore(HttpServletRequest request) {
    double number = 0.0D;
    String[] markValue = getScoreMark(request, new String[] { "grxx_sxyrzqk" });
    String mark = markValue[0];
    if (!mark.equals("1")) {
      if (mark.equals("4")) {
        number = 8.0D;
      } else {
        number = 15.0D;
      } 
    } else {
      number = 0.0D;
    } 
    return myRound(number, 2);
  }
  
  public double getLwzzScore(HttpServletRequest request) {
    double number = 0.0D;
    String[] markValue = getScoreMark(request, new String[] { "lwzz_zl", "lwzz_zzlx", "lwzz_lwlx", "lwzz_zzrs" });
    if ("1".equals(markValue[0])) {
      if ("1".equals(markValue[2])) {
        number = 25.0D;
      } else if ("2".equals(markValue[2]) || "3".equals(markValue[2])) {
        number = 15.0D;
      } else if ("4".equals(markValue[2]) || "5".equals(markValue[2])) {
        number = 8.0D;
      } 
    } else if ("2".equals(markValue[0])) {
      if ("1".equals(markValue[1])) {
        number = 100.0D;
      } else if ("2".equals(markValue[1])) {
        number = 60.0D;
      } else if ("3".equals(markValue[1]) || "4".equals(markValue[1])) {
        number = 30.0D;
      } 
    } 
    number /= Double.valueOf(markValue[3].equals("") ? "1" : markValue[3]).doubleValue();
    return myRound(number, 2);
  }
  
  public double getYhdjxScore(HttpServletRequest request) {
    double number = 500.0D;
    String[] markValue = getScoreMark(request, new String[] { "yhdjx_jllb", "yhdjx_jldj", "yhdjx_fs", "yhdjx_wcrs" });
    String mark = String.valueOf(markValue[0]) + "_" + markValue[1];
    if ("1_1".equals(mark)) {
      number = 500.0D;
    } else if ("1_2".equals(mark)) {
      number = 300.0D;
    } else if ("1_3".equals(mark)) {
      number = 200.0D;
    } else if ("2_2".equals(mark)) {
      number = 100.0D;
    } else if ("2_3".equals(mark)) {
      number = 50.0D;
    } else if ("2_4".equals(mark)) {
      number = 25.0D;
    } 
    number /= Double.valueOf(markValue[3].equals("") ? "1" : markValue[3]).doubleValue();
    return myRound(number, 2);
  }
  
  public double getZzsbjxScore(HttpServletRequest request) {
    double number = 0.0D;
    String[] markValue = getScoreMark(request, new String[] { "zzsbjx_lx" });
    if ("1".equals(markValue[0])) {
      number = 20.0D;
    } else if ("2".equals(markValue[0])) {
      number = 8.0D;
    } else if ("3".equals(markValue[0])) {
      number = 5.0D;
    } 
    return myRound(number, 2);
  }
  
  public double getSbzlScore(HttpServletRequest request) {
    double number = 5.0D;
    return myRound(number, 2);
  }
  
  public double getXshyScore(HttpServletRequest request) {
    double number = 0.0D;
    String[] markValue = getScoreMark(request, new String[] { "xshy_hylx", "xshy_bglx" });
    String mark = String.valueOf(markValue[0]) + "_" + markValue[1];
    if ("1_1".equals(mark)) {
      number = 5.0D;
    } else if ("2_1".equals(mark)) {
      number = 3.0D;
    } 
    return myRound(number, 2);
  }
  
  public double getKyxmScore(HttpServletRequest request) {
    double number = 0.0D;
    String[] markValue = getScoreMark(request, new String[] { "kyxm_xmlx", "kyxm_cjfs" });
    String mark = String.valueOf(markValue[0]) + "_" + markValue[1];
    if ("1_1".equals(mark) || "2_1".equals(mark)) {
      number = 20.0D;
    } else if ("1_2".equals(mark) || "2_2".equals(mark) || "3_1".equals(mark) || "4_1".equals(mark)) {
      number = 8.0D;
    } 
    return myRound(number, 2);
  }
  
  public double getRcpyScore(HttpServletRequest request) {
    double number = 0.0D;
    String[] markValue = getScoreMark(request, new String[] { "rcpy_zdssrs", "rcpy_zdbsrs", "rcpy_skxs" });
    int zdssrs = (Integer.valueOf(markValue[0]).intValue() > 4) ? 4 : Integer.valueOf(markValue[0]).intValue();
    int zdbsrs = (Integer.valueOf(markValue[1]).intValue() > 3) ? 3 : Integer.valueOf(markValue[1]).intValue();
    int skxs = Integer.valueOf(markValue[2]).intValue();
    number += skxs * 0.3D;
    number += (zdssrs * 8);
    number += (zdbsrs * 15);
    return myRound(number, 2);
  }
  
  public double getGjzzrzScore(HttpServletRequest request) {
    double number = 0.0D;
    String[] markValue = getScoreMark(request, new String[] { "gjzzrz_drzw" });
    if ("6".equals(markValue[0])) {
      number = 3.0D;
    } else {
      number = 5.0D;
    } 
    return myRound(number, 2);
  }
  
  public double getGjhyrzScore(HttpServletRequest request) {
    double number = 0.0D;
    String[] markValue = getScoreMark(request, new String[] { "gjhyrz_drzw" });
    if ("6".equals(markValue[0])) {
      number = 3.0D;
    } else {
      number = 5.0D;
    } 
    return myRound(number, 2);
  }
  
  public double getGjqkrzScore(HttpServletRequest request) {
    double number = 0.0D;
    String[] markValue = getScoreMark(request, new String[] { "gjqkrz_drzw" });
    if ("3".equals(markValue[0])) {
      number = 3.0D;
    } else {
      number = 5.0D;
    } 
    return myRound(number, 2);
  }
  
  public double getCfScore(HttpServletRequest request) {
    double number = 3.0D;
    return myRound(number, 2);
  }
  
  public double getLfScore(HttpServletRequest request) {
    double number = 0.0D;
    String[] markValue = getScoreMark(request, new String[] { "lf_lx" });
    if ("1".equals(markValue[0])) {
      number = 3.0D;
    } else {
      number = 1.0D;
    } 
    return myRound(number, 2);
  }
  
  public double getGjhzScore(HttpServletRequest request) {
    double number = 0.0D;
    String[] markValue = getScoreMark(request, new String[] { "gjhz_lb" });
    if ("1".equals(markValue[0])) {
      number = 10.0D;
    } else {
      number = 15.0D;
    } 
    return myRound(number, 2);
  }
  
  public double getYdhzScore(HttpServletRequest request) {
    double number = 0.0D;
    String[] markValue = getScoreMark(request, new String[] { "ydhz_xmjf" });
    float xmjf = Float.valueOf(markValue[0]).floatValue();
    if (xmjf < 10.0F) {
      number = 5.0D;
    } else if (xmjf >= 20.0F) {
      number = 15.0D;
    } else {
      number = 10.0D;
    } 
    return myRound(number, 2);
  }
  
  public double getZxbgScore(HttpServletRequest request) {
    double number = 0.0D;
    String[] markValue = getScoreMark(request, new String[] { "zxbg_jb" });
    if ("1".equals(markValue[0])) {
      number = 15.0D;
    } else if ("6".equals(markValue[0])) {
      number = 5.0D;
    } else {
      number = 10.0D;
    } 
    return myRound(number, 2);
  }
  
  public String[] getScoreMark(HttpServletRequest request, String[] marks) {
    String[] markValues = new String[marks.length];
    for (int i = 0; i < marks.length; i++) {
      if (request.getParameter(marks[i]) == null) {
        markValues[i] = getInfoFromBase(marks[i], request.getParameter("tableId"), request.getParameter("recordId"))[0];
      } else {
        markValues[i] = request.getParameter(marks[i]);
      } 
    } 
    return markValues;
  }
  
  public String[] getInfoFromBase(String field, String pageId, String recordId) {
    int l = (field.split(",")).length;
    String[] infos = new String[l];
    String sql = "select area_table from tarea where page_id=" + pageId + " and area_name='form1'";
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      String tableName = "";
      if (rs.next())
        tableName = (rs.getString(1) == null) ? "" : rs.getString(1); 
      rs.close();
      if (!"".equals(tableName)) {
        sql = "select " + field + " from " + tableName + " where " + tableName + "_id=" + recordId;
        rs = base.executeQuery(sql);
        if (rs.next())
          for (int i = 0; i < l; i++)
            infos[i] = (rs.getString(i + 1) == null) ? "" : rs.getString(i + 1);  
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return infos;
  }
  
  public void updateTable(String tableName, String recordId, double df) {
    if (df != 0.0D) {
      DataSourceBase base = new DataSourceBase();
      String sql = "update " + tableName + " set " + tableName.split("_")[1] + "_fs=" + df + "," + tableName.split("_")[1] + "_jxzt='已提交' where " + tableName + "_id=" + recordId;
      try {
        base.begin();
        System.out.println("更新分数：" + sql);
        base.executeSQL(sql);
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        base.end();
      } 
    } 
  }
  
  private double myRound(double number, int index) {
    double temp = Math.pow(10.0D, index);
    return Math.round(number * temp) / temp;
  }
  
  public double getFs(String userId, String nd, String mqzt, String tableName, String all) {
    float fs = 0.0F;
    String numberId = StaticParam.getEmpNumberByEmpId(userId);
    String[] tableNames = tableName.split("_");
    DataSourceBase base = new DataSourceBase();
    String sql = "select sum(" + tableNames[1] + "_fs) from " + tableName;
    if ("0".equals(mqzt)) {
      sql = String.valueOf(sql) + " where " + tableNames[1] + "_gh='" + numberId + "' and " + tableNames[1] + "_nd='" + nd + "'";
    } else if ("all".equals(all)) {
      sql = String.valueOf(sql) + " where " + tableNames[1] + "_nd='" + nd + "' and " + tableNames[1] + "_mqzt='" + mqzt + "'";
    } else {
      sql = String.valueOf(sql) + " where " + tableNames[1] + "_gh='" + numberId + "' and " + tableNames[1] + "_nd='" + nd + "' and " + tableNames[1] + "_mqzt='" + mqzt + "'";
    } 
    try {
      base.begin();
      System.out.println("列表分数计算：" + sql);
      ResultSet rs = base.executeQuery(sql);
      if (rs.next())
        fs = rs.getFloat(1); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return myRound(fs, 2);
  }
}
