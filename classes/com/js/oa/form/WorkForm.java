package com.js.oa.form;

import com.js.oa.eform.service.CustomFormBD;
import javax.servlet.http.HttpServletRequest;

public class WorkForm {
  public String save(HttpServletRequest request) {
    String infoId = null;
    infoId = (new CustomFormBD()).save(request);
    return infoId;
  }
  
  public boolean update(HttpServletRequest request) {
    boolean reBoolean = false;
    reBoolean = (new CustomFormBD()).update(request).booleanValue();
    return reBoolean;
  }
}
