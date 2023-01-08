package com.js.oa.form.kq;

import com.js.oa.form.Workflow;
import com.js.oa.hr.kq.po.KqWaiChuPO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;

public class WaiChuFlow extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  Workflow workFlow = new Workflow();
  
  public Map save(HttpServletRequest request) {
    Map saveMap = this.workFlow.save(request);
    return saveMap;
  }
  
  public String update(HttpServletRequest request) {
    String ret = this.workFlow.update(request);
    return ret;
  }
  
  public void back(HttpServletRequest request) {
    System.out.println("流程退回");
  }
  
  public void complete(HttpServletRequest request) {
    this.workFlow.complete(request);
    if (SystemCommon.getJsoaUse() == 0) {
      try {
        String hide_Field = request.getParameter("Hide_Field");
        String info_Id = request.getParameter("Info_Id");
        String waichu_userId = "";
        String waichu_date = "";
        String waichu_start = "";
        String waichu_end = "";
        String waichu_type = "0";
        float waichu_hour = 0.0F;
        String sqlView = "";
        if (hide_Field.indexOf("waichu_userId,") >= 0)
          sqlView = String.valueOf(sqlView) + "waichu_userId,"; 
        if (hide_Field.indexOf("waichu_date,") >= 0)
          sqlView = String.valueOf(sqlView) + "waichu_date,"; 
        if (hide_Field.indexOf("waichu_start,") >= 0)
          sqlView = String.valueOf(sqlView) + "waichu_start,"; 
        if (hide_Field.indexOf("waichu_end,") >= 0)
          sqlView = String.valueOf(sqlView) + "waichu_end,"; 
        if (hide_Field.indexOf("waichu_type,") >= 0)
          sqlView = String.valueOf(sqlView) + "waichu_type,"; 
        if (sqlView.length() > 1) {
          DataSourceBase base = new DataSourceBase();
          ResultSet rs = null;
          try {
            base.begin();
            String tableSql = "SELECT area_table FROM tarea WHERE page_id=" + request.getParameter("tableId");
            rs = base.executeQuery(tableSql);
            String tableName = "";
            if (rs.next())
              tableName = rs.getString(1); 
            rs.close();
            if (tableName == null || "".equals(tableName) || "null".equals(tableName))
              tableName = "jst_waichu"; 
            String infoSql = "select " + sqlView.substring(0, sqlView.length() - 1) + " from " + tableName + " where " + tableName + "_id=" + info_Id;
            rs = base.executeQuery(infoSql);
            if (rs.next()) {
              if (hide_Field.indexOf("waichu_userId,") < 0) {
                waichu_userId = request.getParameter("waichu_userId");
              } else {
                waichu_userId = rs.getString("waichu_userId");
              } 
              if (hide_Field.indexOf("waichu_date,") < 0) {
                waichu_date = request.getParameter("waichu_date");
              } else {
                waichu_date = rs.getString("waichu_date");
              } 
              if (hide_Field.indexOf("waichu_start,") < 0) {
                waichu_start = request.getParameter("waichu_start");
              } else {
                waichu_start = rs.getString("waichu_start");
              } 
              if (hide_Field.indexOf("waichu_end,") < 0) {
                waichu_end = request.getParameter("waichu_end");
              } else {
                waichu_end = rs.getString("waichu_end");
              } 
              if (hide_Field.indexOf("waichu_type,") < 0) {
                waichu_type = request.getParameter("waichu_type");
              } else {
                waichu_type = rs.getString("waichu_type");
              } 
            } 
            base.end();
          } catch (Exception e) {
            base.end();
            e.printStackTrace();
          } 
        } else {
          String waichu_starthours = "";
          String waichu_startminutes = "";
          String waichu_endhours = "";
          String waichu_endminutes = "";
          waichu_userId = request.getParameter("waichu_userId");
          waichu_date = request.getParameter("waichu_date");
          waichu_type = request.getParameter("waichu_type");
          waichu_starthours = request.getParameter("waichu_starthours");
          waichu_startminutes = request.getParameter("waichu_startminutes");
          waichu_endhours = request.getParameter("waichu_endhours");
          waichu_endminutes = request.getParameter("waichu_endminutes");
          waichu_start = String.valueOf(request.getParameter("waichu_start")) + " " + waichu_starthours + ":" + waichu_startminutes;
          waichu_end = String.valueOf(request.getParameter("waichu_end")) + " " + waichu_endhours + ":" + waichu_endminutes;
        } 
        if ("".equals(waichu_type))
          waichu_type = "0"; 
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        DecimalFormat dformat = new DecimalFormat("0.00");
        KqImportUtil util = new KqImportUtil();
        waichu_hour = util.getHour(waichu_start, waichu_end, "1", waichu_userId);
        begin();
        try {
          KqWaiChuPO po = new KqWaiChuPO();
          po.setWaiChuEmp(waichu_userId);
          po.setWaiChuDate(waichu_date);
          po.setWaiChuStart(df.parse(waichu_start));
          po.setWaiChuEnd(df.parse(waichu_end));
          po.setWaiChuHour(Float.valueOf(waichu_hour));
          po.setWaiChuType(waichu_type);
          this.session.save(po);
          this.session.flush();
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          this.session.close();
        } 
        System.out.println("流程结束");
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } else {
      try {
        String hide_Field = request.getParameter("Hide_Field");
        String info_Id = request.getParameter("Info_Id");
        String waichu_userId = "";
        String waichu_date = "";
        String waichu_type = "0";
        float waichu_hour = 0.0F;
        String[][] waichu_time = (String[][])null;
        String sqlView = "";
        if (hide_Field.indexOf("waichu_userId,") >= 0)
          sqlView = String.valueOf(sqlView) + "waichu_userId,"; 
        if (hide_Field.indexOf("waichu_date,") >= 0)
          sqlView = String.valueOf(sqlView) + "waichu_date,"; 
        if (hide_Field.indexOf("waichu_type,") >= 0)
          sqlView = String.valueOf(sqlView) + "waichu_type,"; 
        if (sqlView.length() > 1) {
          DataSourceBase base = new DataSourceBase();
          ResultSet rs = null;
          try {
            base.begin();
            String tableSql = "SELECT area_table FROM tarea WHERE page_id=" + request.getParameter("tableId") + " AND area_name='form1'";
            rs = base.executeQuery(tableSql);
            String tableName = "";
            if (rs.next())
              tableName = rs.getString(1); 
            rs.close();
            if (tableName == null || "".equals(tableName) || "null".equals(tableName))
              tableName = "jst_waichu"; 
            String infoSql = "select " + sqlView.substring(0, sqlView.length() - 1) + " from " + tableName + " where " + tableName + "_id=" + info_Id;
            rs = base.executeQuery(infoSql);
            if (rs.next()) {
              if (hide_Field.indexOf("waichu_userId,") < 0) {
                waichu_userId = request.getParameter("waichu_userId");
              } else {
                waichu_userId = rs.getString("waichu_userId");
              } 
              if (hide_Field.indexOf("waichu_date,") < 0) {
                waichu_date = request.getParameter("waichu_date");
              } else {
                waichu_date = rs.getString("waichu_date");
              } 
              if (hide_Field.indexOf("waichu_type,") < 0) {
                waichu_type = request.getParameter("waichu_type");
              } else {
                waichu_type = rs.getString("waichu_type");
              } 
            } 
            rs.close();
            String subSql = "select waichu_subStart,waichu_subEnd from jst_3251 where jst_3251_FOREIGNKEY=" + info_Id;
            rs = base.executeQuery(subSql);
            String[] waichu_sT = request.getParameterValues("waichu_subStart");
            waichu_time = new String[waichu_sT.length][2];
            int j = 0;
            while (rs.next()) {
              waichu_time[j][0] = rs.getString("waichu_subStart");
              waichu_time[j][1] = rs.getString("waichu_subEnd");
              j++;
            } 
            base.end();
          } catch (Exception e) {
            base.end();
            e.printStackTrace();
          } 
        } else {
          waichu_userId = request.getParameter("waichu_userId");
          waichu_date = request.getParameter("waichu_date");
          waichu_type = request.getParameter("waichu_type");
          String[] waichu_starthours = request.getParameterValues("waichu_subStarthours");
          String[] waichu_startminutes = request.getParameterValues("waichu_subStartminutes");
          String[] waichu_endhours = request.getParameterValues("waichu_subEndhours");
          String[] waichu_endminutes = request.getParameterValues("waichu_subEndminutes");
          String[] waichu_sDate = request.getParameterValues("waichu_subStart");
          String[] waichu_eDate = request.getParameterValues("waichu_subEnd");
          waichu_time = new String[waichu_sDate.length][2];
          for (int j = 0; j < waichu_sDate.length; j++) {
            waichu_time[j][0] = String.valueOf(waichu_sDate[j]) + " " + waichu_starthours[j] + ":" + waichu_startminutes[j];
            waichu_time[j][1] = String.valueOf(waichu_eDate[j]) + " " + waichu_endhours[j] + ":" + waichu_endminutes[j];
          } 
        } 
        if ("".equals(waichu_type))
          waichu_type = "0"; 
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        DecimalFormat dformat = new DecimalFormat("0.00");
        for (int i = 0; i < waichu_time.length; i++) {
          KqImportUtil util = new KqImportUtil();
          waichu_hour = util.getHour(waichu_time[i][0], waichu_time[i][1], "1", waichu_userId);
          begin();
          try {
            KqWaiChuPO po = new KqWaiChuPO();
            po.setWaiChuEmp(waichu_userId);
            po.setWaiChuDate(waichu_date);
            po.setWaiChuStart(df.parse(waichu_time[i][0]));
            po.setWaiChuEnd(df.parse(waichu_time[i][1]));
            po.setWaiChuHour(Float.valueOf(waichu_hour));
            po.setWaiChuType(waichu_type);
            this.session.save(po);
            this.session.flush();
          } catch (Exception e) {
            e.printStackTrace();
          } finally {
            this.session.close();
          } 
        } 
        System.out.println("流程结束");
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
  }
  
  public void delete(HttpServletRequest request) {}
}
