package com.js.oa.form.bry;

import com.js.oa.form.Workflow;
import com.js.oa.hr.kq.bry.BryJq;
import javax.servlet.http.HttpServletRequest;

public class BryJqWorkFlow extends Workflow {
  public String complete(HttpServletRequest request) {
    String result = super.complete(request);
    if ("success".equals(result)) {
      String processId = request.getParameter("processId");
      String recordId = request.getParameter("recordId");
      BryJqUtil util = new BryJqUtil();
      BryJq jq = util.getBryJq(processId, recordId, "");
      util.insertJq(jq);
      util.updateJq(jq);
    } 
    return result;
  }
}
