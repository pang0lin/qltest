package com.js.oa.form.kq;

import com.js.oa.form.Workflow;
import com.js.oa.hr.kq.po.KqQingJiaPO;
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

public class QingJiaFlow extends HibernateBase implements SessionBean {
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
    try {
      String hide_Field = (request.getParameter("Hide_Field") == null) ? "" : request.getParameter("Hide_Field");
      String info_Id = (request.getParameter("Info_Id") == null) ? "0" : request.getParameter("Info_Id");
      String qingjia_userId = "";
      String qingjia_date = "";
      String qingjia_start = "";
      String qingjia_end = "";
      String qingjia_type = "0";
      float qingjia_hour = 0.0F;
      String sqlView = "";
      if (hide_Field.indexOf("qingjia_userId,") >= 0)
        sqlView = String.valueOf(sqlView) + "qingjia_userId,"; 
      if (hide_Field.indexOf("qingjia_date,") >= 0)
        sqlView = String.valueOf(sqlView) + "qingjia_date,"; 
      if (hide_Field.indexOf("qingjia_start,") >= 0)
        sqlView = String.valueOf(sqlView) + "qingjia_start,"; 
      if (hide_Field.indexOf("qingjia_end,") >= 0)
        sqlView = String.valueOf(sqlView) + "qingjia_end,"; 
      if (hide_Field.indexOf("qingjia_type,") >= 0)
        sqlView = String.valueOf(sqlView) + "qingjia_type,"; 
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
            tableName = "jst_qingjia"; 
          String infoSql = "select " + sqlView.substring(0, sqlView.length() - 1) + " from " + tableName + " where " + tableName + "_id=" + info_Id;
          rs = base.executeQuery(infoSql);
          if (rs.next()) {
            if (hide_Field.indexOf("qingjia_userId,") < 0) {
              qingjia_userId = request.getParameter("qingjia_userId");
            } else {
              qingjia_userId = rs.getString("qingjia_userId");
            } 
            if (hide_Field.indexOf("qingjia_date,") < 0) {
              qingjia_date = request.getParameter("qingjia_date");
            } else {
              qingjia_date = rs.getString("qingjia_date");
            } 
            if (hide_Field.indexOf("qingjia_start,") < 0) {
              qingjia_start = request.getParameter("qingjia_start");
            } else {
              qingjia_start = rs.getString("qingjia_start");
            } 
            if (hide_Field.indexOf("qingjia_end,") < 0) {
              qingjia_end = request.getParameter("qingjia_end");
            } else {
              qingjia_end = rs.getString("qingjia_end");
            } 
            if (hide_Field.indexOf("qingjia_type,") < 0) {
              qingjia_type = request.getParameter("qingjia_type");
            } else {
              qingjia_type = rs.getString("qingjia_type");
            } 
          } 
          base.end();
        } catch (Exception e) {
          base.end();
          e.printStackTrace();
        } 
      } else {
        String qingjia_starthours = "";
        String qingjia_startminutes = "";
        String qingjia_endhours = "";
        String qingjia_endminutes = "";
        qingjia_userId = request.getParameter("qingjia_userId");
        qingjia_date = request.getParameter("qingjia_date");
        qingjia_type = request.getParameter("qingjia_type");
        qingjia_starthours = request.getParameter("qingjia_starthours");
        qingjia_startminutes = request.getParameter("qingjia_startminutes");
        qingjia_endhours = request.getParameter("qingjia_endhours");
        qingjia_endminutes = request.getParameter("qingjia_endminutes");
        qingjia_start = String.valueOf(request.getParameter("qingjia_start")) + " " + qingjia_starthours + ":" + qingjia_startminutes;
        qingjia_end = String.valueOf(request.getParameter("qingjia_end")) + " " + qingjia_endhours + ":" + qingjia_endminutes;
      } 
      if ("".equals(qingjia_type))
        qingjia_type = "0"; 
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      DecimalFormat dformat = new DecimalFormat("0.00");
      KqImportUtil util = new KqImportUtil();
      qingjia_hour = util.getHour(qingjia_start, qingjia_end, "3", qingjia_userId);
      begin();
      try {
        KqQingJiaPO po = new KqQingJiaPO();
        po.setQingJiaEmp(qingjia_userId);
        po.setQingJiaDate(qingjia_date);
        po.setQingJiaStart(df.parse(qingjia_start));
        po.setQingJiaEnd(df.parse(qingjia_end));
        po.setQingJiaHour(Float.valueOf(qingjia_hour));
        po.setQingJiaType(qingjia_type);
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
  }
  
  public void delete(HttpServletRequest request) {}
  
  public void saveInfo(HttpServletRequest request) {}
}
