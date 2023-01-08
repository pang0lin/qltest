package com.js.oa.hr.kq.action;

import com.js.oa.hr.kq.service.BryKqBD;
import com.js.system.manager.service.ManagerService;
import com.js.system.util.StaticParam;
import com.js.util.page.util.PageSqlUtil;
import com.js.util.page.util.PageUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class BryKqAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws ParseException {
    String action = request.getParameter("action");
    HttpSession session = request.getSession();
    if (action.equals("list") || action.equals("export")) {
      ManagerService managerBD = new ManagerService();
      String yue = (request.getParameter("yue") == null) ? "" : request.getParameter("yue");
      if (yue.equals("")) {
        yue = (new SimpleDateFormat("yyyyMM")).format(new Date());
        String[] dates = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date()).split("-");
        if (Integer.valueOf(dates[2]).intValue() > 25)
          if (dates[1].equals("12")) {
            yue = String.valueOf(Integer.valueOf(dates[0]).intValue() + 1) + "01";
          } else {
            yue = (new StringBuilder(String.valueOf(Integer.valueOf(yue).intValue() + 1))).toString();
          }  
      } 
      request.setAttribute("yue", yue);
      Object object1 = session.getAttribute("userId");
      Object object2 = "";
      List<Object[]> rlist = managerBD.getRightScope((String)object1, "07*01*02");
      if (rlist.size() > 0) {
        Object[] ranks = rlist.get(0);
        if ("3".equals(ranks[0])) {
          object2 = "*" + session.getAttribute("orgId") + "*";
        } else if ("2".equals(ranks[0])) {
          object2 = "*" + StaticParam.getOrgIdsByOrgId((String)session.getAttribute("orgId")).replace(",", "**") + "*";
        } else if ("0".equals(ranks[0])) {
          object2 = "*-1*";
        } else if ("4".equals(ranks[0])) {
          object2 = ranks[1];
        } 
      } 
      String viewSql = "kqtj_orgname,kqtj_username,kqtj_kqts,kqtj_cd,kqtj_bj,kqtj_sj,kqtj_cj,kqtj_chj,kqtj_brj,kqtj_nj,kqtj_hj,kqtj_qt,kqtj_cctx,kqtj_cczts,kqtj_htxts,kqtj_jbpt,kqtj_jbsx,kqtj_jbfdjr,kqtj_wcwcts,kqtj_bcqts,kqtj_bcqwcf,kqtj_bndyxnj,kqtj_nczsysynj,kqtj_xsynj,kqtj_zsysytx,kqtj_dyxcx,kqtj_xsycx,kqtj_hjsxxj,kqtj_sjcqts,kqtj_rjtf,kqtj_sjjtf";
      String fromSql = "bry_kqtj";
      String whereSql = "kqtj_num=" + yue;
      if ("".equals(object2)) {
        whereSql = String.valueOf(whereSql) + " and kqtj_userid=" + object1;
      } else if (object2.contains("*") && !object2.equals("*-1*")) {
        object2 = object2.replaceAll("\\*\\*", ",").replaceAll("\\*", "");
        whereSql = String.valueOf(whereSql) + " and kqtj_orgid in (" + object2 + ")";
      } else if (object2.contains("$")) {
        object2 = object2.replaceAll("\\$\\$", ",").replaceAll("\\$", "");
        whereSql = String.valueOf(whereSql) + " and kqtj_userid in (" + object2 + ")";
      } else {
        "*-1*".equals(object2);
      } 
      String orderBy = "kqtj_id";
      PageUtil page = new PageSqlUtil();
      if (action.equals("export")) {
        request.setAttribute("title", "宝日医考勤统计导出");
        String[] head = { 
            "部门", "姓名", "出勤天数", "迟到", "病假", "事假", "产检", "产假", "哺乳假", "年假", 
            "婚假", "其他", "出差调休", "出差总天数", "含调休天数", 
            "加班普通", "加班双休", "加班法定假日", "外出午餐天数", "报出勤天数", 
            "报出勤误餐费", "本年度应享年假", "年初至上月剩余年假", "现剩余年假", "至上月剩余调休", "当月新存休", "现剩余村修", 
            "合计剩余休假", "实际出勤天数", "日交通费", 
            "实际交通费" };
        request.setAttribute("head", head);
        page = new PageSqlUtil(9999999);
      } 
      List<Object> list = page.list(request, viewSql, fromSql, whereSql, orderBy);
      request.setAttribute("list", list);
      List<String[]> yueList = (new BryKqBD()).getYueList();
      request.setAttribute("yueList", yueList);
    } else if ("tiaoxiuList".equals(action)) {
      Object object = (new StringBuilder(String.valueOf(request.getParameter("userId")))).toString();
      if (object.equalsIgnoreCase("null") || "".equals(object))
        object = session.getAttribute("userId"); 
      request.setAttribute("tiaoxiu", (new BryKqBD()).getTiaoxiu((String)object).get(0));
    } else if ("nianjia".equals(action)) {
      Object object = (new StringBuilder(String.valueOf(request.getParameter("userId")))).toString();
      if (object.equalsIgnoreCase("null") || "".equals(object))
        object = session.getAttribute("userId"); 
      request.setAttribute("nianjia", (new BryKqBD()).getNianjia((String)object).get(0));
    } 
    return actionMapping.findForward(action);
  }
}
