package com.js.oa.hr.kq.bean;

import com.js.util.util.DataSourceUtil;
import java.util.List;

public class BryKqEJBBean {
  public List<String[]> getDataList(String sql) {
    return (new DataSourceUtil()).getListQuery(sql, "");
  }
  
  public List<String[]> getYueList(String sql) {
    List<String[]> yueList = (new DataSourceUtil()).getListQuery(sql, "");
    return yueList;
  }
  
  public List<String[]> getStrings(String sql) {
    return (new DataSourceUtil()).getListQuery(sql, "0");
  }
}
