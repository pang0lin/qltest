package com.js.oa.hr.officemanager.action;

import com.js.oa.userdb.util.DbOpt;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LdhtAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    LdhtActionForm ldhtActionForm = (LdhtActionForm)actionForm;
    String action = httpServletRequest.getParameter("action");
    String tag = "";
    if (action.equals("init")) {
      Date date = new Date();
      String ksyear = (new StringBuilder(String.valueOf(date.getYear() + 1900))).toString();
      String ksmonth = (new StringBuilder(String.valueOf(date.getMonth() + 1))).toString();
      String ksday = (new StringBuilder(String.valueOf(date.getDate()))).toString();
      httpServletRequest.setAttribute("ksyear", ksyear);
      httpServletRequest.setAttribute("ksmonth", ksmonth);
      httpServletRequest.setAttribute("ksday", ksday);
      httpServletRequest.setAttribute("jsyear", ksyear);
      httpServletRequest.setAttribute("jsmonth", ksmonth);
      httpServletRequest.setAttribute("jsday", ksday);
      httpServletRequest.setAttribute("result", null);
      tag = "init";
    } else if ("query".equals(action)) {
      String kssj = ldhtActionForm.getKssj();
      String jssj = ldhtActionForm.getJssj();
      httpServletRequest.setAttribute("ksyear", kssj.split("/")[0]);
      httpServletRequest.setAttribute("ksmonth", kssj.split("/")[1]);
      httpServletRequest.setAttribute("ksday", kssj.split("/")[2]);
      httpServletRequest.setAttribute("jsyear", jssj.split("/")[0]);
      httpServletRequest.setAttribute("jsmonth", jssj.split("/")[1]);
      httpServletRequest.setAttribute("jsday", jssj.split("/")[2]);
      tag = "init";
      DbOpt db = new DbOpt();
      String sql = "select bm, name, zw, hth, qdrq, htdqrq  from (select emp.empname name,               (select max(orgname)                  from org_organization_user gx, org_organization org                  where gx.emp_id = emp.emp_id                   and gx.org_id = org.org_id) bm,               emp.empduty zw,               contract.contractNO hth,               to_char(contract.givendate, 'yyyy/mm/dd') qdrq,               to_char(contract.enddate, 'yyyy/mm/dd') htdqrq          from org_employee emp, org_employee_contract contract         where emp.userisdeleted = 0           and emp.emp_id = contract.emp_id           and enddate is not null           and enddate between to_date('" + 












        
        kssj + "', 'yyyy/mm/dd') and" + 
        "               to_date('" + jssj + "', 'yyyy/mm/dd'))" + 
        " order by bm";
      String[][] result = (String[][])null;
      try {
        result = db.executeQueryToStrArr2(sql, 6);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      httpServletRequest.setAttribute("result", result);
    } 
    return actionMapping.findForward(tag);
  }
}
