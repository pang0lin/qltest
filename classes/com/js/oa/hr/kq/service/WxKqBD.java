package com.js.oa.hr.kq.service;

import com.js.oa.hr.kq.bean.WxKqEJBBean;
import java.util.List;
import java.util.Map;

public class WxKqBD {
  public List<String[]> getDataList(String sql) {
    WxKqEJBBean bean = new WxKqEJBBean();
    return bean.getDataList(sql);
  }
  
  public Map<String, String> getKqMap(String empNum, long flag) {
    WxKqEJBBean bean = new WxKqEJBBean();
    return bean.getKqMap(empNum, flag);
  }
  
  public String[] getEmpInfo(String empId) {
    WxKqEJBBean bean = new WxKqEJBBean();
    return bean.getEmpInfo(empId);
  }
  
  public List<String[]> getHolidayList() {
    WxKqEJBBean bean = new WxKqEJBBean();
    return bean.getHolidayList();
  }
  
  public int addHoliday(String[] holiday) {
    WxKqEJBBean bean = new WxKqEJBBean();
    return bean.addHoliday(holiday);
  }
  
  public int upadteHoliday(String[] holiday) {
    WxKqEJBBean bean = new WxKqEJBBean();
    return bean.updateHoliday(holiday);
  }
  
  public String[] getHoliday(String holidayId) {
    WxKqEJBBean bean = new WxKqEJBBean();
    return bean.getHoliday(holidayId);
  }
  
  public int deleteHoliday(String holidayId) {
    WxKqEJBBean bean = new WxKqEJBBean();
    return bean.deleteHoliday(holidayId);
  }
}
