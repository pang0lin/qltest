package com.js.oa.hr.kq.bean;

import com.js.oa.hr.kq.po.KqDutyOutPO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;

public class KqDutyOutEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  private String databaseType = SystemCommon.getDatabaseType();
  
  public void add(KqDutyOutPO kqDutyOutPO) throws Exception {
    Long id = Long.valueOf("-1");
    begin();
    try {
      id = (Long)this.session.save(kqDutyOutPO);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
  }
  
  public float[] getHour(String beginDate, String endDate, String userId) throws Exception {
    begin();
    float[] value = { 0.0F, 0.0F, 0.0F, 0.0F };
    List<Object[]> list = null;
    try {
      if (this.databaseType.indexOf("mysql") >= 0)
        list = this.session.createQuery("select po.outHour,po.outType from com.js.oa.hr.kq.po.KqDutyOutPO po where po.outDate BETWEEN '" + 
            beginDate + "' and '" + endDate + "' and po.outEmp='" + userId + "'").list(); 
      if (this.databaseType.indexOf("oracle") >= 0)
        list = this.session.createQuery("select po.outHour,po.outType from com.js.oa.hr.kq.po.KqDutyOutPO po where (po.outDate BETWEEN  to_date('" + 
            beginDate + "','yyyy-MM-dd hh24:mi:ss') and to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')) and po.outEmp='" + userId + 
            "'").list(); 
      if (list != null && list.size() > 0) {
        for (int i = 0; i < list.size(); i++) {
          Object[] obj = list.get(i);
          if (Integer.valueOf(obj[1].toString()).intValue() == 4) {
            value[0] = value[0] + ((Float)obj[0]).floatValue();
          } else if (Integer.valueOf(obj[1].toString()).intValue() == 3) {
            value[1] = value[1] + ((Float)obj[0]).floatValue();
          } else if (Integer.valueOf(obj[1].toString()).intValue() == 1) {
            value[2] = value[2] + ((Float)obj[0]).floatValue();
          } else if (Integer.valueOf(obj[1].toString()).intValue() == 2) {
            value[3] = value[3] + ((Float)obj[0]).floatValue();
          } 
        } 
        value[0] = Math.round(value[0] * 100.0F) / 100.0F;
        value[1] = Math.round(value[1] * 100.0F) / 100.0F;
        value[2] = Math.round(value[2] * 100.0F) / 100.0F;
        value[3] = Math.round(value[3] * 100.0F) / 100.0F;
      } 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return value;
  }
  
  public float getHourByOrg(String beginDate, String endDate, String orgId, String flag) throws Exception {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    float value = 0.0F;
    String sql = "";
    try {
      base.begin();
      if (this.databaseType.indexOf("mysql") >= 0) {
        sql = "SELECT SUM(a.outHour) FROM kq_duty_out a,org_organization b,org_organization_user c WHERE a.outEmp=c.emp_id AND c.org_id=b.org_id AND b.org_id=" + 
          orgId + " and (a.outDate between '" + beginDate + "' and '" + endDate + "') and a.outType=" + flag;
      } else {
        sql = "SELECT SUM(a.outHour) FROM kq_duty_out a,org_organization b,org_organization_user c WHERE a.outEmp=c.emp_id AND c.org_id=b.org_id AND b.org_id=" + 
          orgId + " and (a.outDate between to_date('" + beginDate + "','yyyy-MM-dd') and " + 
          "to_date('" + endDate + "','yyyy-MM-dd')) and a.outType=" + flag;
      } 
      rs = base.executeQuery(sql);
      if (rs.next() && 
        rs.getString(1) != null)
        value = rs.getFloat(1); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return value;
  }
  
  public int getDutyByOrg(String beginDate, String endDate, String orgId, String flag) throws Exception {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    int value = 0;
    String sql = "";
    try {
      base.begin();
      if (this.databaseType.indexOf("mysql") >= 0) {
        sql = "SELECT count(a.kq_infoId) FROM kq_info a,org_organization b,org_organization_user c WHERE a.kq_infoEmpId=c.emp_id AND c.org_id=b.org_id AND b.org_id=" + 
          orgId + " and (a.kq_infoDate between '" + beginDate + "' and '" + endDate + "') and " + 
          "a.kq_infoType='" + flag + "'";
      } else {
        sql = "SELECT count(a.kq_infoId) FROM kq_info a,org_organization b,org_organization_user c WHERE a.kq_infoEmpId=c.emp_id AND c.org_id=b.org_id AND b.org_id=" + 
          orgId + " and (a.kq_infoDate between '" + beginDate + "' and " + 
          "'" + endDate + "') and a.kq_infoType='" + flag + "'";
      } 
      rs = base.executeQuery(sql);
      if (rs.next())
        value = rs.getInt(1); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return value;
  }
  
  public List searchByEmpId(String beginDate, String endDate, String userId) {
    List list = null;
    try {
      begin();
      if (this.databaseType.indexOf("mysql") >= 0) {
        list = this.session.createQuery("select po.outDate,po.outHour,po.outType from com.js.oa.hr.kq.po.KqDutyOutPO po where po.outDate BETWEEN '" + 
            beginDate + "' and '" + endDate + "' and po.outEmp='" + userId + "' " + 
            "order by po.outId desc").list();
      } else {
        list = this.session.createQuery("select po.outDate,po.outHour,po.outType from com.js.oa.hr.kq.po.KqDutyOutPO po where po.outDate BETWEEN to_date('" + 
            beginDate + "','yyyy-MM-dd') and to_date('" + endDate + "','yyyy-MM-dd')" + 
            " and po.outEmp='" + userId + "' order by po.outId desc").list();
      } 
      if (list == null || list.size() <= 0)
        list = new ArrayList(); 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        this.session.close();
      } catch (HibernateException e) {
        e.printStackTrace();
      } 
    } 
    list = getHoliday(String.valueOf(beginDate) + " 00:00:00", String.valueOf(endDate) + " 23:59:59", list);
    return list;
  }
  
  public KqDutyOutPO searchById(long outId) throws Exception {
    KqDutyOutPO kqDutyOutPO = new KqDutyOutPO();
    begin();
    try {
      kqDutyOutPO = (KqDutyOutPO)this.session.load(KqDutyOutPO.class, Long.valueOf(outId));
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return kqDutyOutPO;
  }
  
  public int[] getDutyInfo(String searchBegindate, String searchEnddate, String empId) {
    String sql = "";
    sql = "SELECT DISTINCT kq_infoType,kq_infoDate FROM kq_info where (kq_infoDate BETWEEN '" + searchBegindate + "' " + 
      "AND '" + searchEnddate + "') AND kq_infoEmpId='" + empId + "' and kq_infoIf=1";
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    int[] value = new int[5];
    try {
      base.begin();
      rs = base.executeQuery(sql);
      while (rs.next()) {
        if (rs.getString(1).equals("2")) {
          value[0] = value[0] + 1;
          continue;
        } 
        if (rs.getString(1).equals("3")) {
          value[1] = value[1] + 1;
          continue;
        } 
        if (rs.getString(1).equals("4")) {
          value[2] = value[2] + 1;
          continue;
        } 
        if (rs.getString(1).equals("5")) {
          value[3] = value[3] + 1;
          continue;
        } 
        if (rs.getString(1).equals("8"))
          value[4] = value[4] + 1; 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return value;
  }
  
  public List getHoliday(String begin, String end, List<Object[]> list) {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String holidaySql = "";
    if (this.databaseType.indexOf("mysql") >= 0)
      holidaySql = "SELECT begin_date,end_date,TYPE FROM kq_holiday WHERE (begin_date BETWEEN '" + begin + "' " + 
        "AND '" + end + "') OR (end_date BETWEEN '" + begin + "' AND '" + end + "') order by begin_date"; 
    if (this.databaseType.indexOf("oracle") >= 0)
      holidaySql = "SELECT begin_date,end_date,TYPE FROM kq_holiday WHERE (begin_date BETWEEN to_date('" + begin + "','yyyy-MM-dd HH24:MI:SS') " + 
        "AND to_date('" + end + "','yyyy-MM-dd HH24:MI:SS')) OR (end_date BETWEEN to_date('" + begin + "','yyyy-MM-dd HH24:MI:SS')" + 
        " AND to_date('" + end + "','yyyy-MM-dd HH24:MI:SS')) order by begin_date"; 
    try {
      base.begin();
      rs = base.executeQuery(holidaySql);
      while (rs.next()) {
        long beginDate = rs.getTimestamp(1).getTime();
        long endDate = rs.getTimestamp(2).getTime();
        int type = rs.getInt(3);
        for (long i = beginDate; i <= endDate; i += 86400000L) {
          Object[] obj = new Object[3];
          obj[0] = new Date(i);
          obj[1] = Integer.valueOf(0);
          if (type == 0) {
            obj[2] = Integer.valueOf(6);
          } else {
            obj[2] = Integer.valueOf(5);
          } 
          list.add(obj);
        } 
      } 
      rs.close();
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return list;
  }
  
  public Map getDutyByEmpId(String beginDate, String endDate, String userId) {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    Map<String, String> map = new HashMap<String, String>();
    String sql = "SELECT kq_infoDate,kq_infoType FROM kq_info WHERE (kq_infoDate BETWEEN '" + beginDate + 
      "' AND '" + endDate + "') AND kq_infoEmpId=" + userId + " order by kq_infoDate";
    try {
      base.begin();
      rs = base.executeQuery(sql);
      while (rs.next()) {
        if (map.get(rs.getString(1)) == null)
          map.put(rs.getString(1), ""); 
        if (rs.getInt(2) == 2) {
          map.put(rs.getString(1), String.valueOf(map.get(rs.getString(1).toString())) + ",2");
          continue;
        } 
        if (rs.getInt(2) == 3) {
          map.put(rs.getString(1), String.valueOf(map.get(rs.getString(1).toString())) + ",3");
          continue;
        } 
        if (rs.getInt(2) == 4)
          map.put(rs.getString(1), String.valueOf(map.get(rs.getString(1).toString())) + ",4"); 
      } 
      rs.close();
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return null;
  }
  
  public Map<String, String> getDutyMap(String beginDate, String endDate, String userId) {
    String sql = "SELECT DISTINCT kq_infoDate,kq_infoType FROM kq_info WHERE (kq_infoDate BETWEEN '" + beginDate + 
      "' AND '" + endDate + "') AND kq_infoType<>7 and kq_infoEmpId=" + userId + " order by kq_infoDate";
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    Map<String, String> map = new HashMap<String, String>();
    String[] dutyShow = { "迟到", "早退", "下班未打卡", "上班未打卡" };
    try {
      base.begin();
      rs = base.executeQuery(sql);
      while (rs.next()) {
        if (map.get(rs.getString(1)) == null) {
          if (rs.getInt(2) != 0 && rs.getInt(2) != 1 && rs.getInt(2) != 8) {
            map.put(rs.getString(1), dutyShow[rs.getInt(2) - 2]);
            continue;
          } 
          if (rs.getInt(2) == 8) {
            map.put(rs.getString(1), "旷工");
            continue;
          } 
          map.put(rs.getString(1), "正常打卡");
          continue;
        } 
        if (rs.getInt(2) != 0 && rs.getInt(2) != 1 && rs.getInt(2) != 8) {
          String showContent = map.get(rs.getString(1));
          if (showContent.indexOf("正常打卡") >= 0)
            showContent = showContent.replace("正常打卡/", "").replace("正常打卡", ""); 
          if (!showContent.contains(dutyShow[rs.getInt(2) - 2])) {
            showContent = String.valueOf(showContent) + ("".equals(showContent) ? dutyShow[rs.getInt(2) - 2] : ("/" + dutyShow[rs.getInt(2) - 2]));
            map.put(rs.getString(1), showContent);
          } 
        } 
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return map;
  }
  
  public Map<String, String> getDutyShow(String beginDate, String endDate, String userId) {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    Map<String, String> dutyShow = new HashMap<String, String>();
    String sql = "SELECT punch_date,punch_time FROM kq_punch WHERE punch_oaId=" + userId + " AND (punch_date BETWEEN " + 
      "'" + beginDate + "' and '" + endDate + "') order by punch_date,punch_time";
    try {
      base.begin();
      rs = base.executeQuery(sql);
      while (rs.next()) {
        if (dutyShow.get(rs.getString(1)) == null) {
          String str = "<table width='120' border='0' cellpadding='0' cellspacing='1' bgcolor='#C1F0FF'>";
          str = String.valueOf(str) + "<tr><td width='80'>打卡时间：</td><td align='left'>" + rs.getString(2) + "</td></tr>";
          dutyShow.put(rs.getString(1), str);
          continue;
        } 
        String html = ((String)dutyShow.get(rs.getString(1))).toString();
        html = String.valueOf(html) + "<tr><td width='80'>打卡时间：</td><td align='left'>" + rs.getString(2) + "</td></tr>";
        dutyShow.put(rs.getString(1), html);
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    for (String key : dutyShow.keySet())
      dutyShow.put(key, String.valueOf(dutyShow.get(key)) + "</table>"); 
    return dutyShow;
  }
  
  public String dateShow(String sql) {
    DataSourceBase base = new DataSourceBase();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    String returnValue = "";
    try {
      base.begin();
      ResultSet rs = null;
      rs = base.executeQuery(sql);
      while (rs.next())
        returnValue = String.valueOf(returnValue) + "<div>" + format.format(new Date(rs.getTimestamp(1).getTime())) + "&nbsp;到&nbsp;" + 
          format.format(new Date(rs.getTimestamp(2).getTime())) + "</div>"; 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return returnValue;
  }
}
