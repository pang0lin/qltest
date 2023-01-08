package com.js.oa.zky.bean;

import com.js.util.util.DataSourceBase;
import com.js.util.util.DataSourceUtil;
import java.util.List;

public class ZkyCountBean {
  public List<String[]> getListData(String sql) {
    return (new DataSourceUtil()).getListQuery(sql, "");
  }
  
  public String queryStrBySql(String sql) {
    return (new DataSourceBase()).queryStrBySql(sql);
  }
}
