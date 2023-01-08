package com.js.oa.form.kq;

import com.ibm.icu.util.Calendar;
import com.js.oa.form.Workflow;
import com.js.oa.hr.kq.po.KqDutyOutPO;
import com.js.oa.hr.kq.po.KqJiaBanPO;
import com.js.oa.hr.kq.service.KqDutyOutBD;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;

public class JiaBanFlow extends HibernateBase implements SessionBean {
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
      String jiaban_userId = "";
      String jiaban_date = "";
      String jiaban_start = "";
      String jiaban_end = "";
      String jiaban_type = "0";
      float jiaban_hour = 0.0F;
      String sqlView = "";
      if (hide_Field.indexOf("jiaban_userId,") >= 0)
        sqlView = String.valueOf(sqlView) + "jiaban_userId,"; 
      if (hide_Field.indexOf("jiaban_date,") >= 0)
        sqlView = String.valueOf(sqlView) + "jiaban_date,"; 
      if (hide_Field.indexOf("jiaban_start,") >= 0)
        sqlView = String.valueOf(sqlView) + "jiaban_start,"; 
      if (hide_Field.indexOf("jiaban_end,") >= 0)
        sqlView = String.valueOf(sqlView) + "jiaban_end,"; 
      if (hide_Field.indexOf("jiaban_type,") >= 0)
        sqlView = String.valueOf(sqlView) + "jiaban_type,"; 
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
            tableName = "jst_jiaban"; 
          String infoSql = "select " + sqlView.substring(0, sqlView.length() - 1) + " from " + tableName + " where " + tableName + "_id=" + info_Id;
          rs = base.executeQuery(infoSql);
          if (rs.next()) {
            if (hide_Field.indexOf("jiaban_userId,") < 0) {
              jiaban_userId = request.getParameter("jiaban_userId");
            } else {
              jiaban_userId = rs.getString("jiaban_userId");
            } 
            if (hide_Field.indexOf("jiaban_date,") < 0) {
              jiaban_date = request.getParameter("jiaban_date");
            } else {
              jiaban_date = rs.getString("jiaban_date");
            } 
            if (hide_Field.indexOf("jiaban_start,") < 0) {
              jiaban_start = request.getParameter("jiaban_start");
            } else {
              jiaban_start = rs.getString("jiaban_start");
            } 
            if (hide_Field.indexOf("jiaban_end,") < 0) {
              jiaban_end = request.getParameter("jiaban_end");
            } else {
              jiaban_end = rs.getString("jiaban_end");
            } 
            if (hide_Field.indexOf("jiaban_type,") < 0) {
              jiaban_type = request.getParameter("jiaban_type");
            } else {
              jiaban_type = rs.getString("jiaban_type");
            } 
          } 
          base.end();
        } catch (Exception e) {
          base.end();
          e.printStackTrace();
        } 
      } else {
        String jiaban_starthours = "";
        String jiaban_startminutes = "";
        String jiaban_endhours = "";
        String jiaban_endminutes = "";
        jiaban_userId = request.getParameter("jiaban_userId");
        jiaban_date = request.getParameter("jiaban_date");
        jiaban_type = request.getParameter("jiaban_type");
        jiaban_starthours = request.getParameter("jiaban_starthours");
        jiaban_startminutes = request.getParameter("jiaban_startminutes");
        jiaban_endhours = request.getParameter("jiaban_endhours");
        jiaban_endminutes = request.getParameter("jiaban_endminutes");
        jiaban_start = String.valueOf(request.getParameter("jiaban_start")) + " " + jiaban_starthours + ":" + jiaban_startminutes;
        jiaban_end = String.valueOf(request.getParameter("jiaban_end")) + " " + jiaban_endhours + ":" + jiaban_endminutes;
      } 
      if ("".equals(jiaban_type))
        jiaban_type = "0"; 
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      jiaban_hour = getDutySet(jiaban_userId, jiaban_start, jiaban_end);
      begin();
      try {
        KqJiaBanPO po = new KqJiaBanPO();
        po.setJiaBanEmp(jiaban_userId);
        po.setJiaBanDate(jiaban_date);
        po.setJiaBanStart(df.parse(jiaban_start));
        po.setJiaBanEnd(df.parse(jiaban_end));
        po.setJiaBanHour(Float.valueOf(jiaban_hour));
        po.setJiaBanType(jiaban_type);
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
  
  public float getDutySet(String userId, String beginDate, String endDate) {
    float duty = 0.0F;
    try {
      KqImportUtil util = new KqImportUtil();
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      String[][] dutySetAll = util.getDutySet(userId);
      Calendar begin = Calendar.getInstance();
      Calendar end = Calendar.getInstance();
      begin.setTime(df.parse(beginDate));
      end.setTime(df.parse(endDate));
      for (int z = 0; z < dutySetAll.length; z++) {
        String[] dutySet = dutySetAll[z];
        int[] dutyTime = new int[6];
        int[] timeJiange = new int[3];
        int minute = 0;
        for (int i = 0; i < dutySet.length - 2; i += 2) {
          int beginTime = 0;
          int endTime = 0;
          if (dutySet[i] != null && !"".equals(dutySet[i]) && !"null".equals(dutySet[i]) && dutySet[i].indexOf(":") > 0) {
            String[] timeS = dutySet[i].split(":");
            beginTime += 60 * Integer.valueOf(timeS[0]).intValue();
            beginTime += Integer.valueOf(timeS[1]).intValue();
            dutyTime[i] = beginTime;
          } else {
            dutyTime[i] = dutyTime[i - 1];
          } 
          if (dutySet[i + 1] != null && !"".equals(dutySet[i + 1]) && !"null".equals(dutySet[i + 1]) && dutySet[i].indexOf(":") > 0) {
            String[] timeS = dutySet[i + 1].split(":");
            endTime += 60 * Integer.valueOf(timeS[0]).intValue();
            endTime += Integer.valueOf(timeS[1]).intValue();
            dutyTime[i + 1] = endTime;
          } else {
            dutyTime[i + 1] = dutyTime[i];
          } 
          if (endTime > beginTime) {
            minute += endTime - beginTime;
            timeJiange[i / 2] = endTime - beginTime;
          } 
        } 
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (begin.get(1) == end.get(1) && begin.get(6) == end.get(6)) {
          duty = (float)((end.getTimeInMillis() - begin.getTimeInMillis()) / 3600000L);
          KqDutyOutPO po = new KqDutyOutPO();
          po.setOutEmp(userId);
          po.setOutDate(begin.getTime());
          po.setOutHour(duty);
          po.setOutType("2");
          po.setOutInputDate(new Date());
          KqDutyOutBD outBD = new KqDutyOutBD();
          outBD.add(po);
        } else {
          duty = (float)((end.getTimeInMillis() - begin.getTimeInMillis()) / 3600000L);
          if (begin.get(1) == end.get(1)) {
            Calendar c = (Calendar)begin.clone();
            for (int j = begin.get(6); j <= end.get(6); j++) {
              c.set(6, j);
              long timel = 0L;
              if (j == begin.get(6)) {
                String timeDate = String.valueOf(begin.get(1)) + "-" + (begin.get(2) + 1) + "-" + begin.get(5) + 
                  " 24:00";
                timel = df.parse(timeDate).getTime() - begin.getTimeInMillis();
              } else if (j == end.get(6)) {
                String timeDate = String.valueOf(end.get(1)) + "-" + (end.get(2) + 1) + "-" + end.get(5) + 
                  " 00:00";
                timel = end.getTimeInMillis() - df.parse(timeDate).getTime();
              } else {
                timel = df.parse(String.valueOf(c.get(1)) + "-" + (c.get(2) + 1) + "-" + c.get(5) + 
                    " 24:00").getTime() - df.parse(String.valueOf(c.get(1)) + "-" + (c.get(2) + 1) + "-" + c.get(5) + 
                    " 00:00").getTime();
              } 
              KqDutyOutPO po = new KqDutyOutPO();
              po.setOutEmp(userId);
              po.setOutDate(c.getTime());
              po.setOutHour((float)timel / 3600000.0F);
              po.setOutType("2");
              po.setOutInputDate(new Date());
              KqDutyOutBD outBD = new KqDutyOutBD();
              outBD.add(po);
            } 
          } else {
            Calendar c = (Calendar)begin.clone();
            for (int j = begin.get(6); j <= begin.getActualMaximum(6) + end.get(6); j++) {
              if (j > begin.getActualMaximum(6) + 1) {
                c.set(6, j - begin.getActualMaximum(6));
              } else {
                c.set(6, j);
              } 
              float timel = 0.0F;
              if (j == begin.get(6)) {
                String timeDate = String.valueOf(begin.get(1)) + "-" + (begin.get(2) + 1) + "-" + begin.get(5) + 
                  " 24:00";
                timel = (float)(df.parse(timeDate).getTime() - begin.getTimeInMillis());
              } else if (j == end.get(6)) {
                String timeDate = String.valueOf(end.get(1)) + "-" + (end.get(2) + 1) + "-" + end.get(5) + 
                  " 00:00";
                timel = (float)(end.getTimeInMillis() - df.parse(timeDate).getTime());
              } else {
                timel = (float)(df.parse(String.valueOf(c.get(1)) + "-" + (c.get(2) + 1) + "-" + c.get(5) + 
                    " 24:00").getTime() - df.parse(String.valueOf(c.get(1)) + "-" + (c.get(2) + 1) + "-" + c.get(5) + 
                    " 00:00").getTime());
              } 
              KqDutyOutPO po = new KqDutyOutPO();
              po.setOutEmp(userId);
              po.setOutDate(c.getTime());
              po.setOutHour(timel / 3600000.0F);
              po.setOutType("2");
              po.setOutInputDate(new Date());
              KqDutyOutBD outBD = new KqDutyOutBD();
              outBD.add(po);
            } 
          } 
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return duty;
  }
}
