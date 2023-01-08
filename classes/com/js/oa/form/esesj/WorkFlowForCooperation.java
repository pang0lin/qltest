package com.js.oa.form.esesj;

import com.js.oa.esesj.bean.EsesjBean;
import com.js.oa.form.Workflow;
import com.js.system.vo.usermanager.EmployeeVO;
import javax.servlet.http.HttpServletRequest;

public class WorkFlowForCooperation extends Workflow {
  public String complete(HttpServletRequest request) {
    String recorded = request.getParameter("recordId");
    EsesjBean es = new EsesjBean();
    String[] cooperationProcessInfo = es.getCooperationProcessInfo(recorded);
    if (cooperationProcessInfo != null && 
      "是".equals(cooperationProcessInfo[1])) {
      String name = "";
      String id = cooperationProcessInfo[2].split("_")[0];
      String type = cooperationProcessInfo[2].split("_")[1];
      String[] Collaborator = es.getInfoById(id, type);
      EmployeeVO employee = es.getEmployeeVOById(cooperationProcessInfo[0]);
      if (Collaborator[2] == null || "".equals(Collaborator[2])) {
        name = String.valueOf(employee.getEmpName()) + ";$" + cooperationProcessInfo[0] + "$";
      } else {
        name = String.valueOf(Collaborator[2].split(";")[0]) + "," + employee.getEmpName() + ";" + Collaborator[2].split(";")[1] + "$" + cooperationProcessInfo[0] + "$";
      } 
      es.updateCooperationr(id, name, type);
    } 
    return super.complete(request);
  }
}
