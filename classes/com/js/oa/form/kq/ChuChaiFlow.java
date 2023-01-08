package com.js.oa.form.kq;

import com.js.oa.form.Workflow;
import com.js.oa.hr.kq.po.KqChuChaiPO;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;

public class ChuChaiFlow extends HibernateBase implements SessionBean {
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
      String hide_Field = request.getParameter("Hide_Field");
      String info_Id = request.getParameter("Info_Id");
      String chuchai_userId = "";
      String chuchai_date = "";
      String chuchai_start = "";
      String chuchai_end = "";
      String chuchai_type = "0";
      float chuchai_hour = 0.0F;
      String sqlView = "";
      if (hide_Field.indexOf("chuchai_userId,") >= 0)
        sqlView = String.valueOf(sqlView) + "chuchai_userId,"; 
      if (hide_Field.indexOf("chuchai_date,") >= 0)
        sqlView = String.valueOf(sqlView) + "chuchai_date,"; 
      if (hide_Field.indexOf("chuchai_start,") >= 0)
        sqlView = String.valueOf(sqlView) + "chuchai_start,"; 
      if (hide_Field.indexOf("chuchai_end,") >= 0)
        sqlView = String.valueOf(sqlView) + "chuchai_end,"; 
      if (hide_Field.indexOf("chuchai_type,") >= 0)
        sqlView = String.valueOf(sqlView) + "chuchai_type,"; 
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
            tableName = "jst_chuchai"; 
          String infoSql = "select " + sqlView.substring(0, sqlView.length() - 1) + " from " + tableName + " where " + tableName + "_id=" + info_Id;
          rs = base.executeQuery(infoSql);
          if (rs.next()) {
            if (hide_Field.indexOf("chuchai_userId,") < 0) {
              chuchai_userId = request.getParameter("chuchai_userId");
            } else {
              chuchai_userId = rs.getString("chuchai_userId");
            } 
            if (hide_Field.indexOf("chuchai_date,") < 0) {
              chuchai_date = request.getParameter("chuchai_date");
            } else {
              chuchai_date = rs.getString("chuchai_date");
            } 
            if (hide_Field.indexOf("chuchai_start,") < 0) {
              chuchai_start = request.getParameter("chuchai_start");
            } else {
              chuchai_start = rs.getString("chuchai_start");
            } 
            if (hide_Field.indexOf("chuchai_end,") < 0) {
              chuchai_end = request.getParameter("chuchai_end");
            } else {
              chuchai_end = rs.getString("chuchai_end");
            } 
            if (hide_Field.indexOf("chuchai_type,") < 0) {
              chuchai_type = request.getParameter("chuchai_type");
            } else {
              chuchai_type = rs.getString("chuchai_type");
            } 
          } 
          base.end();
        } catch (Exception e) {
          base.end();
          e.printStackTrace();
        } 
      } else {
        String chuchai_starthours = "";
        String chuchai_startminutes = "";
        String chuchai_endhours = "";
        String chuchai_endminutes = "";
        chuchai_userId = request.getParameter("chuchai_userId");
        chuchai_date = request.getParameter("chuchai_date");
        chuchai_type = request.getParameter("chuchai_type");
        chuchai_starthours = request.getParameter("chuchai_starthours");
        chuchai_startminutes = request.getParameter("chuchai_startminutes");
        chuchai_endhours = request.getParameter("chuchai_endhours");
        chuchai_endminutes = request.getParameter("chuchai_endminutes");
        chuchai_start = String.valueOf(request.getParameter("chuchai_start")) + " " + chuchai_starthours + ":" + chuchai_startminutes;
        chuchai_end = String.valueOf(request.getParameter("chuchai_end")) + " " + chuchai_endhours + ":" + chuchai_endminutes;
      } 
      if ("".equals(chuchai_type))
        chuchai_type = "0"; 
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      KqImportUtil util = new KqImportUtil();
      chuchai_hour = util.getHour(chuchai_start, chuchai_end, "4", chuchai_userId);
      begin();
      try {
        KqChuChaiPO po = new KqChuChaiPO();
        po.setChuChaiEmp(chuchai_userId);
        po.setChuChaiDate(chuchai_date);
        po.setChuChaiStart(df.parse(chuchai_start));
        po.setChuChaiEnd(df.parse(chuchai_end));
        po.setChuChaiHour(Float.valueOf(chuchai_hour));
        po.setChuChaiType(chuchai_type);
        this.session.save(po);
        this.session.flush();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        this.session.close();
      } 
      System.out.println("流程结束--");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void delete(HttpServletRequest request) {}
}
