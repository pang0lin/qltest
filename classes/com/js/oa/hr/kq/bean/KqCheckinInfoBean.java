package com.js.oa.hr.kq.bean;

import com.js.oa.hr.kq.po.KqCheckinInfoPO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import net.sf.hibernate.HibernateException;

public class KqCheckinInfoBean extends HibernateBase {
  private String databaseType = SystemCommon.getDatabaseType();
  
  public List<KqCheckinInfoPO> showInfoByQuery(String sql, int pageSize, int firstRow) {
    List<KqCheckinInfoPO> kis = null;
    try {
      kis = this.session.createQuery(sql).setMaxResults(pageSize).setFirstResult(firstRow).list();
    } catch (HibernateException e) {
      e.printStackTrace();
    } 
    return kis;
  }
  
  public List getWeixinKq(String startdate, String endDate, String userid) {
    List<Object[]> list = new ArrayList();
    StringBuffer sb = new StringBuffer();
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    float value = 0.0F;
    try {
      base.begin();
      if (this.databaseType.indexOf("mysql") >= 0) {
        sb.append("SELECT id,userid,checkintime,position FROM kq_checkininfo k WHERE 1=1 and userid='" + userid + "' ");
        sb.append(" and k.checkinTime between '").append(startdate)
          .append(" 00:00:00").append("' and '").append(endDate)
          .append(" 23:59:59'");
        sb.append(" order by k.checkinTime asc");
      } else {
        sb.append("SELECT id,userid,checkintime,position FROM kq_checkininfo k WHERE 1=1 and userid='" + userid + "' ");
        sb.append(" and k.checkinTime between to_date('").append(startdate).append(" 00:00:00")
          .append("','yyyy-mm-dd hh24:mi:ss')").append(" and ").append(" to_date('")
          .append(endDate).append(" 23:59:59").append("','yyyy-mm-dd hh24:mi:ss')");
        sb.append(" order by k.checkinTime asc");
      } 
      rs = base.executeQuery(sb.toString());
      while (rs.next()) {
        Object[] obj = new Object[4];
        obj[0] = rs.getString(1);
        obj[1] = rs.getString(2);
        obj[2] = rs.getString(3);
        obj[3] = rs.getString(4);
        list.add(obj);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return list;
  }
  
  public List getWeixinKqByDate(String Date, String userid) {
    List<Object[]> list = new ArrayList();
    StringBuffer sb = new StringBuffer();
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    float value = 0.0F;
    try {
      base.begin();
      if (this.databaseType.indexOf("mysql") >= 0) {
        sb.append("SELECT id,userid,checkintime,position FROM kq_checkininfo k WHERE 1=1 and userid='" + userid + "' ");
        sb.append(" and k.checkinTime between '").append(Date)
          .append(" 00:00:00").append("' and '").append(Date)
          .append(" 23:59:59'");
        sb.append(" order by k.checkinTime asc");
      } else {
        sb.append("SELECT id,userid,checkintime,position FROM kq_checkininfo k WHERE 1=1 and userid='" + userid + "' ");
        sb.append(" and k.checkinTime between to_date('").append(Date).append(" 00:00:00")
          .append("','yyyy-mm-dd hh24:mi:ss')").append(" and ").append(" to_date('")
          .append(Date).append(" 23:59:59").append("','yyyy-mm-dd hh24:mi:ss')");
        sb.append(" order by k.checkinTime asc");
      } 
      rs = base.executeQuery(sb.toString());
      while (rs.next()) {
        Object[] obj = new Object[4];
        obj[0] = rs.getString(1);
        obj[1] = rs.getString(2);
        obj[2] = rs.getString(3);
        obj[3] = rs.getString(4);
        list.add(obj);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return list;
  }
}
