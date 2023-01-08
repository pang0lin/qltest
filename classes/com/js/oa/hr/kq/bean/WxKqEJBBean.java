package com.js.oa.hr.kq.bean;

import com.js.util.util.DataSourceBase;
import com.js.util.util.DataSourceUtil;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WxKqEJBBean {
  public List<String[]> getDataList(String sql) {
    return (new DataSourceUtil()).getListQuery(sql, "");
  }
  
  public Map<String, String> getKqMap(String empNum, long flag) {
    Map<String, String> map = new HashMap<String, String>();
    String sql = "select record_time,record_equno from wx_atdrecord where record_empNumber='" + empNum + "' and record_day=" + flag + 
      " order by record_empnumber,record_flag,record_time,record_equno";
    List<String[]> list = (new DataSourceUtil()).getListQuery(sql, "");
    for (int i = 0; i < list.size(); i++) {
      String[] single = list.get(i);
      if (map.get(single[1]) == null) {
        map.put(single[1], single[0]);
      } else {
        map.put(single[1], String.valueOf(map.get(single[1])) + "," + single[0]);
      } 
    } 
    return map;
  }
  
  public String[] getEmpInfo(String empId) {
    String[] empInfo = new String[4];
    String sql = "select e.empName,e.Empnumber,o.OrgNamestring,e.emp_id from org_employee e join org_organization_user u on e.emp_id=u.emp_id join org_organization o on u.org_id=o.org_id where u.emp_id=" + 
      empId;
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      if (rs.next()) {
        empInfo[0] = (rs.getString(1) == null) ? "" : rs.getString(1);
        empInfo[1] = (rs.getString(2) == null) ? "" : rs.getString(2);
        empInfo[2] = (rs.getString(3) == null) ? "" : rs.getString(3);
        empInfo[3] = (rs.getString(4) == null) ? "" : rs.getString(4);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return empInfo;
  }
  
  public List<String[]> getHolidayList() {
    String sql = "select id,holiday_name,begin_date,end_date,memo,type from kq_holiday";
    return (new DataSourceUtil()).getListQuery(sql, "");
  }
  
  public int addHoliday(String[] holiday) {
    String sql = "insert into kq_holiday (id,domain_id,holiday_name,begin_date,end_date,memo,type,paibanset) values (hibernate_sequence.nextval,0,'" + 
      holiday[0] + "',to_date('" + holiday[1] + "','yyyy-mm-dd hh24:mi:ss')," + 
      "to_date('" + holiday[2] + "','yyyy-mm-dd hh24:mi:ss'),'" + holiday[4] + "'," + holiday[3] + ",'') ";
    int i = (new DataSourceBase()).executeSqlUpdate(sql);
    return i;
  }
  
  public int updateHoliday(String[] holiday) {
    String sql = "update kq_holiday set holiday_name='" + holiday[0] + "',begin_date=to_date('" + holiday[1] + "','yyyy-mm-dd hh24:mi:ss')" + 
      ",end_date=to_date('" + holiday[2] + "','yyyy-mm-dd hh24:mi:ss'),memo='" + holiday[4] + "',type=" + holiday[3] + " where id=" + holiday[5];
    int i = (new DataSourceBase()).executeSqlUpdate(sql);
    return i;
  }
  
  public String[] getHoliday(String holidayId) {
    String sql = "select id,holiday_name,begin_date,end_date,memo,type from kq_holiday where id=" + holidayId;
    List<String[]> list = (new DataSourceUtil()).getListQuery(sql, "");
    if (list.size() > 0)
      return (new DataSourceUtil()).getListQuery(sql, "").get(0); 
    return null;
  }
  
  public int deleteHoliday(String holidayId) {
    String sql = "delete from kq_holiday where id=" + holidayId;
    int i = (new DataSourceBase()).executeSqlUpdate(sql);
    return i;
  }
}
