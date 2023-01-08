package com.js.oa.form.zgrs;

import com.js.oa.form.Workflow;
import com.js.util.util.IO2File;
import javax.servlet.http.HttpServletRequest;

public class KqBuQianWorkFlow extends Workflow {
  public String complete(HttpServletRequest request) {
    String result = super.complete(request);
    if ("success".equals(result)) {
      String recordId = request.getParameter("recordId");
      IO2File.printFile("补签：数据库表名：rs_bqd   数据id:" + recordId, "人寿补签", 3);
      (new KqUtil()).kqOp(recordId);
      IO2File.printFile("补签结束", "人寿补签", 3);
    } 
    return result;
  }
}
