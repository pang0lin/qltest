package com.js.oa.form.zgrs;

import com.js.oa.form.Workflow;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class ChinaLifeQjWorkForm extends Workflow {
  public Map save(HttpServletRequest request) {
    Map map = super.save(request);
    String flag = "";
    if (request.getParameter("flag") != null)
      flag = request.getParameter("flag"); 
    if ("save".equals(flag) && map.get("id") != null) {
      String recordId = (String)map.get("id");
      String rs_qjspd_xgqjdbh = request.getParameter("rs_qjspd_xgqjdbh");
      (new QjUtil()).qjOp(recordId, rs_qjspd_xgqjdbh);
    } 
    return map;
  }
  
  public String complete(HttpServletRequest request) {
    String result = super.complete(request);
    if ("success".equals(result)) {
      String recordId = request.getParameter("recordId");
      (new QjUtil()).setQj(recordId, "1");
    } 
    return result;
  }
  
  public void back(HttpServletRequest request) {
    String recordId = request.getParameter("recordId");
    String type = request.getParameter("type");
    if (type.equals("0"))
      (new QjUtil()).setQj(recordId, "-2"); 
  }
}
