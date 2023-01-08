package com.js.oa.jsflow.action;

import com.js.oa.form.ClientInfoFromWeb;
import com.js.util.util.DataSourceUtil;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WorkFlowSubmitMoreAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0L);
    String submitFlag = request.getParameter("submitFlag");
    if (!"all".equals(submitFlag)) {
      String[] flow = getFlowStr(submitFlag);
      ClientInfoFromWeb client = new ClientInfoFromWeb();
      List<String[]> recordList = getRecordList(flow[0], flow[1], flow[2]);
      for (int j = 0; j < recordList.size(); j++) {
        String[] record = recordList.get(j);
        client.save(record[0], "", flow[1], flow[0], record[1]);
      } 
    } else {
      List<String[]> flowList = getFlowList();
      ClientInfoFromWeb client = new ClientInfoFromWeb();
      for (int i = 0; i < flowList.size(); i++) {
        String[] flow = flowList.get(i);
        List<String[]> recordList = getRecordList(flow[0], flow[1], flow[2]);
        for (int j = 0; j < recordList.size(); j++) {
          String[] record = recordList.get(j);
          client.save(record[0], "", flow[1], flow[0], record[1]);
        } 
      } 
    } 
    return actionMapping.findForward("close");
  }
  
  private String[] getFlowStr(String processId) {
    String sql = "select w.wf_workflowprocess_id,w.accessdatabaseid,a.area_table from jsf_workflowprocess w join tarea a on w.accessdatabaseid=a.page_id where w.wf_workflowprocess_id=" + 
      processId;
    return (new DataSourceUtil()).getListQuery(sql, "").get(0);
  }
  
  private List<String[]> getFlowList() {
    String sql = "select w.wf_workflowprocess_id,w.accessdatabaseid,a.area_table from jsf_workflowprocess w join tarea a on w.accessdatabaseid=a.page_id where w.isJx=1";
    return (new DataSourceUtil()).getListQuery(sql, "");
  }
  
  private List<String[]> getRecordList(String processId, String tableId, String tableName) {
    String sql = "select userAccounts," + tableName + "_id from " + tableName + " join org_employee on " + tableName + "_owner=emp_id where " + tableName + "_id not in " + 
      "(select distinct workrecord_id from jsf_work where worktable_id=" + tableId + " and workprocess_id=" + processId + ")";
    return (new DataSourceUtil()).getListQuery(sql, "");
  }
}
