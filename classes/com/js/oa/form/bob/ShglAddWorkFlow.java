package com.js.oa.form.bob;

import com.js.oa.form.Workflow;
import com.js.util.util.DataSourceBase;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class ShglAddWorkFlow extends Workflow {
  public Map save(HttpServletRequest request) {
    Map map = super.save(request);
    String flag = "";
    if (request.getParameter("flag") != null)
      flag = request.getParameter("flag"); 
    if ("save".equals(flag) && map.get("id") != null) {
      String recordId = (String)map.get("id");
      String updateSql = "update bank_thsh set bank_lczt=1 where bank_thsh_id=" + recordId;
      (new DataSourceBase()).executeSqlUpdate(updateSql);
    } 
    return map;
  }
  
  public String complete(HttpServletRequest request) {
    String result = super.complete(request);
    if ("success".equals(result)) {
      String recordId = request.getParameter("recordId");
      String updateSql = "update bank_thsh set bank_lczt=2 where bank_thsh_id=" + recordId;
      (new DataSourceBase()).executeSqlUpdate(updateSql);
    } 
    return result;
  }
  
  public void back(HttpServletRequest request) {
    String recordId = request.getParameter("recordId");
    String type = request.getParameter("type");
    if (type.equals("0")) {
      String updateSql = "update bank_thsh set bank_lczt=-1 where bank_thsh_id=" + recordId;
      (new DataSourceBase()).executeSqlUpdate(updateSql);
    } 
  }
}
