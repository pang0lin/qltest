package com.js.oa.eform.action;

import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ZSFHFormAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    String recordId = request.getParameter("recordId");
    String classLevel = request.getParameter("level");
    String firstClass = request.getParameter("firstClass");
    String secondClass = request.getParameter("secondClass");
    String thirdClass = request.getParameter("thirdClass");
    String fourthClass = request.getParameter("fourthClass");
    String fifthClass = request.getParameter("fifthClass");
    String parentClassId = new String();
    String whereSql = new String();
    String where = new String();
    if (classLevel != null) {
      if (classLevel.equals("firstClass")) {
        secondClass = "none";
        thirdClass = "none";
        fourthClass = "none";
        fifthClass = "none";
      } 
      if (classLevel.equals("secondClass")) {
        thirdClass = "none";
        fourthClass = "none";
        fifthClass = "none";
      } 
      if (classLevel.equals("thirdClass")) {
        fourthClass = "none";
        fifthClass = "none";
      } 
      if (classLevel.equals("fourthClass"))
        fifthClass = "none"; 
    } 
    if (firstClass == null || firstClass.equals("")) {
      parentClassId = "10406";
    } else if (!firstClass.equals("none")) {
      parentClassId = firstClass;
      whereSql = "where zsfh_3052_f3014='" + firstClass + "'";
    } 
    if (secondClass != null && !secondClass.equals("none")) {
      parentClassId = secondClass;
      whereSql = "where zsfh_3052_f3015='" + secondClass + "'";
    } 
    if (thirdClass != null && !thirdClass.equals("none")) {
      parentClassId = thirdClass;
      whereSql = "where zsfh_3052_f3016='" + thirdClass + "'";
    } 
    if (fourthClass != null && !fourthClass.equals("none")) {
      parentClassId = fourthClass;
      whereSql = "where zsfh_3052_f3017='" + fourthClass + "'";
    } 
    if (fifthClass != null && !fifthClass.equals("none")) {
      parentClassId = fifthClass;
      whereSql = "where zsfh_3052_f3018='" + fifthClass + "'";
    } 
    if (recordId != null && !recordId.equals(""))
      where = " and zsfh_3021_id <> '" + recordId + "'"; 
    String tag = "list";
    String startTime = request.getParameter("startTime");
    String endTime = request.getParameter("endTime");
    String sql = "select zsfh_3021_f3361, zsfh_3021_f3021, zsfh_3021_f3022 from zsfh_3021 where not ((zsfh_3021_f3022 < '" + 
      startTime + "') OR (zsfh_3021_f3021 > '" + endTime + "' ))" + where;
    String sql1 = "select zsfh_3051_id, zsfh_3051_f3006, zsfh_3051_f3007 from zsfh_3051 where zsfh_3051_f3006='10406'";
    String sql2 = "SELECT zsfh_3052_f3014,(select zsfh_3051_f3007 from zsfh_3051 where zsfh_3051_id = zsfh_3052_f3014) AS a1,zsfh_3052_f3015,(select zsfh_3051_f3007 from zsfh_3051 where zsfh_3051_id = zsfh_3052_f3015) AS a2,zsfh_3052_f3016,(select zsfh_3051_f3007 from zsfh_3051 where zsfh_3051_id = zsfh_3052_f3016) AS a3,zsfh_3052_f3017,(select zsfh_3051_f3007 from zsfh_3051 where zsfh_3051_id = zsfh_3052_f3017) AS a4,zsfh_3052_f3018,(select zsfh_3051_f3007 from zsfh_3051 where zsfh_3051_id = zsfh_3052_f3018) AS a5,zsfh_3052_f3019 FROM zsfh_3052 " + 








      
      whereSql;
    List<Object[]> lendLicenses = new ArrayList();
    List<Object[]> licenseClass = new ArrayList();
    List<Object[]> licenseList = new ArrayList();
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      ResultSetMetaData rsmd = rs.getMetaData();
      int columnCount = rsmd.getColumnCount();
      while (rs.next()) {
        String[] arrayOfString = new String[columnCount];
        for (int i = 0; i < columnCount; i++)
          arrayOfString[i] = (rs.getString(i + 1) == null) ? "" : rs.getString(i + 1); 
        lendLicenses.add(arrayOfString);
      } 
      rs.close();
      ResultSet rs1 = base.executeQuery(sql1);
      ResultSetMetaData rsmd1 = rs1.getMetaData();
      int columnCount1 = rsmd1.getColumnCount();
      while (rs1.next()) {
        String[] arrayOfString = new String[columnCount1];
        for (int i = 0; i < columnCount1; i++)
          arrayOfString[i] = (rs1.getString(i + 1) == null) ? "" : rs1.getString(i + 1); 
        licenseClass.add(arrayOfString);
      } 
      rs1.close();
      ResultSet rs2 = base.executeQuery(sql2);
      ResultSetMetaData rsmd2 = rs2.getMetaData();
      int columnCount2 = rsmd2.getColumnCount();
      while (rs2.next()) {
        String[] arrayOfString = new String[columnCount2 + 3];
        for (int i = 0; i < columnCount2; i++)
          arrayOfString[i] = (rs2.getString(i + 1) == null) ? "" : rs2.getString(i + 1); 
        StringBuffer s = new StringBuffer();
        for (int j = 0; j < 5 && 
          !arrayOfString[j * 2 + 1].equals("") && arrayOfString[j * 2 + 1] != null; j++) {
          s.append(arrayOfString[j * 2 + 1]);
          s.append(",");
        } 
        s.append(arrayOfString[10]);
        String licenseName = s.toString();
        for (int k = 0; k < lendLicenses.size(); k++) {
          if (((Object[])lendLicenses.get(k))[0].toString().indexOf(licenseName) >= 0) {
            arrayOfString[columnCount2] = "false";
            arrayOfString[columnCount2 + 1] = (String)((Object[])lendLicenses.get(k))[1];
            arrayOfString[columnCount2 + 2] = (String)((Object[])lendLicenses.get(k))[2];
            break;
          } 
        } 
        licenseList.add(arrayOfString);
      } 
      rs2.close();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    List<Object[]> licenseClassSec = new ArrayList();
    List<Object[]> licenseClassThi = new ArrayList();
    List<Object[]> licenseClassFour = new ArrayList();
    List<Object[]> licenseClassFif = new ArrayList();
    if (classLevel != null) {
      if (classLevel.equals("firstClass") && !firstClass.equals("none"))
        licenseClassSec = queryLicenseClass(parentClassId); 
      if (classLevel.equals("secondClass")) {
        licenseClassSec = queryLicenseClass(firstClass);
        licenseClassThi = queryLicenseClass(parentClassId);
      } 
      if (classLevel.equals("thirdClass")) {
        licenseClassSec = queryLicenseClass(firstClass);
        licenseClassThi = queryLicenseClass(secondClass);
        licenseClassFour = queryLicenseClass(parentClassId);
      } 
      if (classLevel.equals("fourthClass")) {
        licenseClassSec = queryLicenseClass(firstClass);
        licenseClassThi = queryLicenseClass(secondClass);
        licenseClassFour = queryLicenseClass(thirdClass);
        licenseClassFif = queryLicenseClass(parentClassId);
      } 
    } 
    request.setAttribute("licenseClassSec", licenseClassSec);
    request.setAttribute("licenseClassThi", licenseClassThi);
    request.setAttribute("licenseClassFour", licenseClassFour);
    request.setAttribute("licenseClassFif", licenseClassFif);
    request.setAttribute("lendLicenses", lendLicenses);
    request.setAttribute("licenseClass", licenseClass);
    request.setAttribute("licenseList", licenseList);
    request.setAttribute("startTime", startTime);
    request.setAttribute("endTime", endTime);
    return actionMapping.findForward(tag);
  }
  
  public List<Object[]> queryLicenseClass(String classId) {
    String sql = "select zsfh_3051_id, zsfh_3051_f3006, zsfh_3051_f3007 from zsfh_3051 where zsfh_3051_f3006='" + classId + "'";
    List<Object[]> licenseClass = new ArrayList();
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      ResultSetMetaData rsmd = rs.getMetaData();
      int columnCount = rsmd.getColumnCount();
      while (rs.next()) {
        String[] arrayOfString = new String[columnCount];
        for (int i = 0; i < columnCount; i++)
          arrayOfString[i] = (rs.getString(i + 1) == null) ? "" : rs.getString(i + 1); 
        licenseClass.add(arrayOfString);
      } 
      rs.close();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return licenseClass;
  }
}
