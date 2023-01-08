package com.js.oa.form.esesj;

import com.js.oa.esesj.bean.EsesjBean;
import com.js.oa.form.Workflow;
import javax.servlet.http.HttpServletRequest;

public class WorkFlowForCompete extends Workflow {
  public String complete(HttpServletRequest request) {
    String recorded = request.getParameter("recordId");
    String tableId = request.getParameter("tableId");
    EsesjBean es = new EsesjBean();
    String tableName = es.getProcessTableName(tableId);
    System.out.println("tableName:" + tableName);
    String[] competeProcessInfo = es.getCompeteProcessInfo(recorded, tableName);
    if (competeProcessInfo != null) {
      String competerName = es.getEmployeeVOById(competeProcessInfo[0]).getEmpName();
      String competerOrgId = es.getEmployeeVOById(competeProcessInfo[0]).getEmpOrgIdString();
      String originId = competeProcessInfo[2].split("_")[0];
      String type = competeProcessInfo[2].split("_")[1];
      try {
        if (competeProcessInfo[1].equals(competerName)) {
          int res = es.insertNewRecordForCompete(originId, type, competeProcessInfo[0], competerOrgId, competeProcessInfo[1]);
          if (res > 0) {
            es.updateBankRecordToInvalid(originId, type, "invalid");
          } else {
            es.updateBankRecordToInvalid(originId, type, "");
          } 
        } else {
          es.updateBankRecordToInvalid(originId, type, "");
        } 
      } catch (Exception e) {
        System.out.println("竞争插入数据失败！");
        es.updateBankRecordToInvalid(originId, type, "");
      } 
    } 
    return super.complete(request);
  }
}
