package com.js.oa.form.zgrs;

import com.js.oa.form.Workflow;
import com.js.util.util.DataSourceBase;
import javax.servlet.http.HttpServletRequest;

public class OfficeStationWorkFlow extends Workflow {
  public String complete(HttpServletRequest request) {
    String result = super.complete(request);
    if ("success".equals(result)) {
      DataSourceBase base = new DataSourceBase();
      try {
        base.begin();
        boolean isFree = false;
        base.addBatch("update jst_3009 set jst_3009_f3077='空',jst_3009_f3073='',jst_3009_f3072='' where jst_3009_f3076='" + request.getParameter("jst_3008_f3065") + "' and jst_3009_f3072='" + request.getParameter("submitPerson") + "'");
        String submitOrg = request.getParameter("jst_3008_f3064");
        if (submitOrg.indexOf(".") > 0) {
          submitOrg = submitOrg.substring(submitOrg.indexOf(".") + 1);
          if (submitOrg.indexOf(".") > 0)
            submitOrg = submitOrg.substring(submitOrg.indexOf(".") + 1); 
        } 
        base.addBatch("update jst_3009 set jst_3009_f3077='已占用',jst_3009_f3073='" + submitOrg + "',jst_3009_f3072='" + request.getParameter("submitPerson") + "' where jst_3009_f3076='" + request.getParameter("jst_3008_f3069") + "'");
        base.executeBatch();
        base.clearBatch();
        base.end();
      } catch (Exception e) {
        base.end();
        e.printStackTrace();
      } 
    } 
    return result;
  }
}
