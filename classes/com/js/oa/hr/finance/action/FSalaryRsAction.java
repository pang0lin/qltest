package com.js.oa.hr.finance.action;

import com.js.oa.hr.finance.util.SAPConn;
import com.js.oa.hr.personnelmanager.service.EmployeeOtherInfoBD;
import com.js.system.vo.usermanager.EmployeeOtherInfoVO;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FSalaryRsAction extends Action {
  String[] keys = null;
  
  String[] values = null;
  
  String message = "";
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    FSalaryRsActionForm form = (FSalaryRsActionForm)actionForm;
    String year = form.getYear();
    String month = form.getMonth();
    if (year == null || month == null || "".equals(year) || "".equals(month)) {
      year = (new StringBuilder(String.valueOf((new Date()).getYear() + 1900))).toString();
      month = (new StringBuilder(String.valueOf((new Date()).getMonth() + 1))).toString();
      month = "0" + month;
      month = month.substring(month.length() - 2, month.length());
      form.setYear(year);
      form.setMonth(month);
    } 
    String userId = session.getAttribute("userId").toString();
    EmployeeOtherInfoBD bd = new EmployeeOtherInfoBD();
    EmployeeOtherInfoVO otherInfo = bd.load(Long.valueOf(userId));
    if (otherInfo == null || otherInfo.getSap_ID() == null || "".equals(otherInfo.getSap_ID())) {
      this.message = "您的SAP编号未被设置，请设置！";
    } else {
      String sapID = otherInfo.getSap_ID();
      querySalary(sapID, year, month);
    } 
    request.setAttribute("message", this.message);
    request.setAttribute("keys", this.keys);
    request.setAttribute("values", this.values);
    return actionMapping.findForward("list");
  }
  
  public void querySalary(String userNum, String year, String month) {
    JCoFunction function = null;
    JCoDestination destination = SAPConn.connect();
    try {
      function = destination.getRepository().getFunction("ZRFC_PYXX_READ_PAYROLL_RESULT");
      function.getImportParameterList().setValue("PERNR", userNum);
      function.getImportParameterList().setValue("GJAHR", year);
      function.getImportParameterList().setValue("MONAT", month);
      function.execute(destination);
      String result_log = function.getExportParameterList().getString("RESULT_LOG");
      this.message = result_log;
      if ("".equals(this.message)) {
        JCoTable salaryItem = function.getTableParameterList().getTable("ZT512T");
        JCoTable salaryValue = function.getTableParameterList().getTable("PAYROLL_RESULT_RT");
        if (salaryValue.getNumRows() == 0)
          this.message = "没有查询到工资信息！"; 
        this.keys = new String[salaryValue.getNumRows()];
        this.values = new String[salaryValue.getNumRows()];
        for (int i = 0; i < this.keys.length; i++) {
          salaryValue.setRow(i);
          String key = salaryValue.getString("LGART");
          this.values[i] = (new StringBuilder(String.valueOf(salaryValue.getFloat("BETRG")))).toString();
          for (int j = 0; j < salaryItem.getNumRows(); j++) {
            salaryItem.setRow(j);
            if (key.equals(salaryItem.getString("LGART"))) {
              this.keys[i] = salaryItem.getString("LGTXT");
              break;
            } 
          } 
        } 
      } 
    } catch (JCoException e) {
      e.printStackTrace();
    } 
  }
}
