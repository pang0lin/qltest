package com.js.oa.form;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class ZkyWorkForm extends Workflow {
  public Map saveFlow(HttpServletRequest request) {
    Map saveMap = save(request);
    return saveMap;
  }
  
  public String updateZky(HttpServletRequest request) {
    return update(request);
  }
  
  public String completeZky(HttpServletRequest request) {
    return complete(request);
  }
}
