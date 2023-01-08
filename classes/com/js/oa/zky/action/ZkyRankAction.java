package com.js.oa.zky.action;

import com.js.oa.zky.service.ZkyRankBD;
import com.js.util.page.util.PageSqlUtil;
import com.js.util.page.util.PageUtil;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ZkyRankAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    String action = request.getParameter("action");
    if (action == null) {
      String nd = request.getParameter("nd");
      String orgId = request.getParameter("orgId");
      String tiao = (request.getParameter("tiao") == null) ? "" : request.getParameter("tiao");
      request.setAttribute("rank", (new ZkyRankBD()).getScoreList(nd, orgId, tiao));
      String export = request.getParameter("export");
      if (export == null)
        return actionMapping.findForward("rank"); 
      request.setAttribute("title", "中科院" + nd + "年度绩效排名");
      return actionMapping.findForward("export");
    } 
    if ("change".equals(action)) {
      String level = request.getParameter("level");
      String id = request.getParameter("id");
      (new ZkyRankBD()).changeLevel(level, id);
      return null;
    } 
    if ("word".equals(action)) {
      String jobNum = (request.getParameter("jobNum") == null) ? "" : request.getParameter("jobNum");
      String nd = (request.getParameter("nd") == null) ? "" : request.getParameter("nd");
      String select = "g.grxx_nd,e.Empname,e.Empnumber,o.OrgnameString,s.score_number,e.emp_id";
      String from = "t_grxx g left join zky_score s on g.grxx_gh=s.score_job join org_employee e on g.grxx_gh=e.Empnumber join org_organization_user ou on e.emp_id=ou.emp_id join org_organization o on ou.org_id=o.org_id";
      String where = "g.grxx_mqzt='2'";
      if (!"".equals(jobNum)) {
        where = String.valueOf(where) + " and e.Empnumber='" + jobNum + "' ";
      } else {
        where = String.valueOf(where) + " and e.Empnumber is not null ";
      } 
      if (!"".equals(nd))
        where = String.valueOf(where) + " and g.grxx_nd='" + nd + "'"; 
      String orderBy = "o.orgIdString";
      PageUtil util = new PageSqlUtil(30);
      List<Object> data = util.list(request, select, from, where, orderBy);
      request.setAttribute("data", data);
    } 
    return actionMapping.findForward(action);
  }
}
