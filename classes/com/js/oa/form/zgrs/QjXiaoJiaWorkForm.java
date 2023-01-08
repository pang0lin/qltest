package com.js.oa.form.zgrs;

import com.js.oa.form.Workflow;
import javax.servlet.http.HttpServletRequest;

public class QjXiaoJiaWorkForm extends Workflow {
  public String complete(HttpServletRequest request) {
    String result = super.complete(request);
    if ("success".equals(result)) {
      String recordId = request.getParameter("recordId");
      (new QjUtil()).xiaoJia0(recordId, "-1");
    } 
    return result;
  }
}
