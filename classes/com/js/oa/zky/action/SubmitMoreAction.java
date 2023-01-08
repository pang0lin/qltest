package com.js.oa.zky.action;

import com.js.oa.zky.service.SubmitBD;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SubmitMoreAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0L);
    String submitFlag = request.getParameter("submitFlag");
    String mqzt = request.getParameter("mqzt");
    String nd = request.getParameter("nd");
    if (request.getParameter("flag") == null) {
      if (!"all".equals(submitFlag)) {
        String id = (request.getParameter("id") == null) ? "" : request.getParameter("id");
        for (; id.endsWith(","); id = id.substring(0, id.length() - 1));
        SubmitBD bd = new SubmitBD();
        String[] tableName = bd.getTableName(submitFlag).split("_");
        bd.updateMqzt(tableName, mqzt, nd, id);
      } else {
        SubmitBD bd = new SubmitBD();
        List<String> tableNameList = bd.getTableNameList();
        for (int i = 0; i < tableNameList.size(); i++) {
          if (!"".equals(tableNameList.get(i))) {
            String[] tableName = ((String)tableNameList.get(i)).split("_");
            bd.updateMqzt(tableName, mqzt, nd);
          } 
        } 
      } 
    } else {
      SubmitBD bd = new SubmitBD();
      String[] tableName = bd.getTableName(submitFlag).split("_");
      String id = (request.getParameter("id") == null) ? "" : request.getParameter("id");
      bd.backMqzt(tableName, mqzt, nd, id);
      request.setAttribute("flag", "back");
    } 
    return actionMapping.findForward("close");
  }
}
