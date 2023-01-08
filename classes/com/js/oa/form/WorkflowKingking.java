package com.js.oa.form;

import com.js.oa.kingking.KingkingService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class WorkflowKingking extends Workflow {
  public String complete(HttpServletRequest request) {
    String result = super.complete(request);
    String recordId = request.getParameter("recordId");
    String tableId = request.getParameter("tableId");
    (new KingkingService()).updateYSYE(recordId, tableId);
    return result;
  }
  
  public String completeYSZJ(HttpServletRequest request) {
    String result = super.complete(request);
    String recordId = request.getParameter("recordId");
    String tableId = request.getParameter("tableId");
    (new KingkingService()).updateYSJEZJ(tableId, recordId);
    return result;
  }
  
  public Map save(HttpServletRequest request) {
    Map result = super.save(request);
    String recordId = result.get("id").toString();
    (new KingkingService()).updateField(recordId);
    return result;
  }
}
