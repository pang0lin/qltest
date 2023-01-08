package com.js.oa.form.zgrs;

import com.js.oa.form.Workflow;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;

public class EmployeeTgtzWorkFlow extends Workflow {
  public String complete(HttpServletRequest request) {
    String result = super.complete(request);
    if ("success".equals(result)) {
      DataSourceBase base = new DataSourceBase();
      try {
        base.begin();
        String rzBm = request.getParameter("rs_dgtzd_xgw_Id");
        ResultSet rs = base.executeQuery("select orgnamestring from org_organization where org_id=" + rzBm);
        if (rs.next())
          rzBm = rs.getString(1); 
        rs.close();
        if (rzBm.indexOf(".") > 0) {
          rzBm = rzBm.substring(rzBm.indexOf(".") + 1);
          if (rzBm.indexOf(".") > 0)
            rzBm = rzBm.substring(0, rzBm.indexOf(".") + 1); 
        } 
        String newEmployee = request.getParameter("rs_dgtzd_xm_Name");
        boolean isFree = false;
        rs = base.executeQuery("select jst_3009_f3077 from jst_3009 where jst_3009_f3076='" + request.getParameter("rs_dgtzd_xggw") + "'");
        if (rs.next()) {
          String temp = rs.getString(1);
          if ("空".equals(temp))
            isFree = true; 
        } 
        rs.close();
        if (isFree) {
          base.addBatch("update jst_3009 set jst_3009_f3077='已占用',jst_3009_f3073='" + rzBm + "',jst_3009_f3072='" + newEmployee + "' where jst_3009_f3076='" + request.getParameter("rs_dgtzd_xggw") + "'");
          base.addBatch("update jst_3009 set jst_3009_f3077='空',jst_3009_f3073='',jst_3009_f3072='' where jst_3009_f3076='" + request.getParameter("rs_dgtzd_yggw") + "'");
          base.executeBatch();
          base.clearBatch();
        } else {
          result = "error";
          request.setAttribute("ErrorMessage", "所选工位已被占用，请选择其他工位！");
        } 
        base.end();
      } catch (Exception e) {
        base.end();
        e.printStackTrace();
      } 
    } 
    return result;
  }
}
