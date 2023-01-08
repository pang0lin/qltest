package com.js.oa.form.zgrs;

import com.js.oa.form.Workflow;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class QjBuDengWorkFlow extends Workflow {
  public Map save(HttpServletRequest httpServletRequest) {
    Map rMap = super.save(httpServletRequest);
    if (rMap.get("id") != null) {
      String recordId = (String)rMap.get("id");
      (new QjUtil()).qjOp(recordId, "");
    } 
    return rMap;
  }
}
