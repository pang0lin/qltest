package com.js.oa.chinaLife.action;

import com.js.oa.chinaLife.bean.KqShowBean;
import com.js.oa.chinaLife.bean.QingJiaBean;
import com.js.system.util.StaticParam;
import com.js.util.page.util.PageSqlUtil;
import com.js.util.page.util.PageUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class KqShowAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    String action = request.getParameter("action");
    String export = request.getParameter("export");
    SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
    try {
      if ("kqShow".equals(action)) {
        String startDate = (request.getParameter("startDate") == null) ? "" : request.getParameter("startDate").replace("/", "-");
        String endDate = (request.getParameter("endDate") == null) ? "" : request.getParameter("endDate").replace("/", "-");
        String userId = (request.getParameter("userId") == null) ? "" : request.getParameter("userId");
        String orgId = (request.getParameter("orgId") == null) ? "" : request.getParameter("orgId");
        String userName = (request.getParameter("userName") == null) ? "" : request.getParameter("userName");
        String orgName = (request.getParameter("orgName") == null) ? "" : request.getParameter("orgName");
        String type = (request.getParameter("type") == null) ? "" : request.getParameter("type");
        String[] kqType = request.getParameterValues("kqType");
        if (kqType != null) {
          type = "";
          for (int i = 0; i < kqType.length; i++)
            type = String.valueOf(type) + kqType[i] + ","; 
          type = type.replace(" ", "").equals(",") ? "" : type.replace(" ", "");
        } 
        if (startDate == null || "".equals(startDate) || "null".equalsIgnoreCase(startDate))
          startDate = String.valueOf(ym.format(new Date())) + "-01"; 
        if (endDate == null || "".equals(endDate) || "null".equalsIgnoreCase(endDate))
          endDate = ymd.format(new Date()); 
        startDate = ymd.format(ymd.parse(startDate));
        endDate = ymd.format(ymd.parse(endDate));
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        request.setAttribute("userId", userId);
        request.setAttribute("orgId", orgId);
        request.setAttribute("userName", userName);
        request.setAttribute("orgName", orgName);
        request.setAttribute("kqType", type);
        int num = 30;
        if (export != null && export.equals("1")) {
          request.setAttribute("fieldTitle", new String[] { 
                "部门", "人员编号", "姓名", "考勤日期", "班次", "上班", "下班", "迟到", "早退", 
                "缺勤", 
                "请假", "补签", "补签备注" });
          num = 100000;
          request.setAttribute("title", "人员考勤统计表");
        } 
        request.setAttribute("dataList", (new KqShowBean()).getKqShowData(request, userId.replace("$$", ",").replace("$", ""), 
              startDate, endDate, orgId.replace("**", ",").replace("*", ""), type, num));
      } else if ("kqMeal".equals(action)) {
        String startDate = (request.getParameter("startDate") == null) ? "" : request.getParameter("startDate").replace("/", "-");
        String endDate = (request.getParameter("endDate") == null) ? "" : request.getParameter("endDate").replace("/", "-");
        String userId = (request.getParameter("userId") == null) ? "" : request.getParameter("userId");
        String orgId = (request.getParameter("orgId") == null) ? "" : request.getParameter("orgId");
        String userName = (request.getParameter("userName") == null) ? "" : request.getParameter("userName");
        String orgName = (request.getParameter("orgName") == null) ? "" : request.getParameter("orgName");
        if (startDate == null || "".equals(startDate) || "null".equalsIgnoreCase(startDate))
          startDate = String.valueOf(ym.format(new Date())) + "-01"; 
        if (endDate == null || "".equals(endDate) || "null".equalsIgnoreCase(endDate))
          endDate = ymd.format(new Date()); 
        startDate = ymd.format(ymd.parse(startDate));
        endDate = ymd.format(ymd.parse(endDate));
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        request.setAttribute("userId", userId);
        request.setAttribute("orgId", orgId);
        request.setAttribute("userName", userName);
        request.setAttribute("orgName", orgName);
        request.setAttribute("dataList", (new KqShowBean()).getKqMealData(request, userId.replace("$$", ",").replace("$", ""), 
              startDate, endDate, orgId.replace("**", ",").replace("*", ""), 20));
      } else if ("kqQing".equals(action)) {
        String year = (request.getParameter("year") == null) ? "" : request.getParameter("year");
        String month = (request.getParameter("month") == null) ? "" : request.getParameter("month");
        String userId = (request.getParameter("userId") == null) ? "" : request.getParameter("userId");
        String orgId = (request.getParameter("orgId") == null) ? "" : request.getParameter("orgId");
        String userName = (request.getParameter("userName") == null) ? "" : request.getParameter("userName");
        String orgName = (request.getParameter("orgName") == null) ? "" : request.getParameter("orgName");
        String type = (request.getParameter("type") == null) ? "" : request.getParameter("type");
        request.setAttribute("year", year);
        request.setAttribute("month", month);
        request.setAttribute("userId", userId);
        request.setAttribute("orgId", orgId);
        request.setAttribute("userName", userName);
        request.setAttribute("orgName", orgName);
        request.setAttribute("type", type);
        request.setAttribute("qjType", (new KqShowBean()).qjType());
        int num = 20;
        if (export != null && export.equals("1")) {
          request.setAttribute("fieldTitle", new String[] { 
                "年份", "部门", "处室", "姓名", "委托人", "职位", "职级", "年假天数", "剩余年假天数", "请假类别", 
                "请假天数", "开始时间", "结束时间", "是否出京", "是否出境", "请假原因" });
          num = 100000;
          request.setAttribute("title", "人员请假统计表");
        } 
        request.setAttribute("dataList", (new KqShowBean()).getKqQingData(request, userId.replace("$$", ",").replace("$", ""), 
              year, month, orgId.replace("**", ",").replace("*", ""), type, num));
      } else if ("bumen".equals(action)) {
        String year = (request.getParameter("year") == null) ? "" : request.getParameter("year");
        String month = (request.getParameter("month") == null) ? "" : request.getParameter("month");
        request.setAttribute("year", year);
        request.setAttribute("month", month);
        request.setAttribute("dataList", (new KqShowBean()).getBumenDate(year, month));
      } else if ("lingdao".equals(action)) {
        String year = (request.getParameter("year") == null) ? "" : request.getParameter("year");
        String month = (request.getParameter("month") == null) ? "" : request.getParameter("month");
        request.setAttribute("year", year);
        request.setAttribute("month", month);
        request.setAttribute("dataList", (new KqShowBean()).getLingdaoDate(year, month));
      } else if ("ldqj".equals(action)) {
        KqShowBean bean = new KqShowBean();
        String year = (request.getParameter("year") == null) ? "" : request.getParameter("year");
        String month = (request.getParameter("month") == null) ? "" : request.getParameter("month");
        String userId = (request.getParameter("userId") == null) ? "" : request.getParameter("userId");
        String orgId = (request.getParameter("orgId") == null) ? "" : request.getParameter("orgId");
        String userName = (request.getParameter("userName") == null) ? "" : request.getParameter("userName");
        String orgName = (request.getParameter("orgName") == null) ? "" : request.getParameter("orgName");
        String type = (request.getParameter("type") == null) ? "" : request.getParameter("type");
        if (userId.equals("")) {
          String[] ld = bean.ldString();
          userId = ld[0];
          userName = ld[1];
        } 
        request.setAttribute("year", year);
        request.setAttribute("month", month);
        request.setAttribute("userId", userId);
        request.setAttribute("orgId", orgId);
        request.setAttribute("userName", userName);
        request.setAttribute("orgName", orgName);
        request.setAttribute("type", type);
        request.setAttribute("qjType", bean.qjType());
        int num = 20;
        if (export != null && export.equals("1")) {
          request.setAttribute("fieldTitle", new String[] { 
                "年份", "部门", "处室", "姓名", "委托人", "职位", "职级", "年假天数", "剩余年假天数", "请假类别", 
                "请假天数", "开始时间", "结束时间", "是否出京", "是否出境", "请假原因" });
          num = 100000;
          request.setAttribute("title", "人员请假统计表");
        } 
        request.setAttribute("dataList", bean.getKqQingData(request, userId.replace("$$", ",").replace("$", ""), 
              year, month, orgId.replace("**", ",").replace("*", ""), type, num));
      } else if ("bmMore".equals(action)) {
        String year = (request.getParameter("year") == null) ? "" : request.getParameter("year");
        String month = (request.getParameter("month") == null) ? "" : request.getParameter("month");
        String orgId = (request.getParameter("orgId") == null) ? "" : request.getParameter("orgId");
        String type = (request.getParameter("type") == null) ? "" : request.getParameter("type");
        String userId = (request.getParameter("userId") == null) ? "" : request.getParameter("userId");
        String userName = (request.getParameter("userName") == null) ? "" : request.getParameter("userName");
        String orgName = (request.getParameter("orgName") == null) ? "" : request.getParameter("orgName");
        if (!"迟到".equals(type) && !"早退".equals(type) && !"缺勤".equals(type)) {
          request.setAttribute("type", type);
          request.setAttribute("qjType", (new KqShowBean()).qjType());
          request.setAttribute("dataList", (new KqShowBean()).getKqQingData(request, userId, year, month, orgId, type, 15));
          action = "kqQing";
        } else {
          String startDate = String.valueOf(year) + "-" + ((month.length() > 1) ? month : ("0" + month)) + "-01";
          int nextMonth = Integer.valueOf(month).intValue() + 1;
          Long nextLong = Long.valueOf(0L);
          try {
            nextLong = Long.valueOf(ymd.parse(String.valueOf(year) + "-" + ((nextMonth >= 10) ? (String)Integer.valueOf(nextMonth) : ("0" + nextMonth)) + "-01").getTime());
          } catch (ParseException e) {
            e.printStackTrace();
          } 
          String endDate = ymd.format(new Date(nextLong.longValue() - 86400000L));
          request.setAttribute("startDate", startDate);
          request.setAttribute("endDate", endDate);
          int num = 30;
          request.setAttribute("dataList", (new KqShowBean()).getKqShowData(request, userId, startDate, endDate, orgId, type, num));
          action = "kqShow";
        } 
        request.setAttribute("year", year);
        request.setAttribute("month", month);
        request.setAttribute("userId", userId);
        request.setAttribute("orgId", orgId);
        request.setAttribute("userName", userName);
        request.setAttribute("orgName", orgName);
      } else if ("buqian".equals(action)) {
        String startDate = (request.getParameter("startDate") == null) ? "" : request.getParameter("startDate").replace("/", "-");
        String endDate = (request.getParameter("endDate") == null) ? "" : request.getParameter("endDate").replace("/", "-");
        String orgId = (request.getParameter("orgId") == null) ? "" : request.getParameter("orgId");
        String orgName = (request.getParameter("orgName") == null) ? "" : request.getParameter("orgName");
        String type = (request.getParameter("type") == null) ? "" : request.getParameter("type");
        String userId = (request.getParameter("userId") == null) ? "" : request.getParameter("userId");
        request.setAttribute("dataList", (new KqShowBean()).getBuqianData(request, userId, orgId, type, startDate, endDate, 20));
        request.setAttribute("userId", userId);
        request.setAttribute("orgId", orgId);
        request.setAttribute("orgName", orgName);
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
      } else if ("kqNian".equals(action)) {
        String year = (request.getParameter("year") == null) ? "" : request.getParameter("year");
        String viewSql = " o.Orgnamestring,'' chu,e.empName,r.rs_njjh_ts,'0' qing,'0' sheng,e.emp_id";
        String fromSql = "org_employee e JOIN org_organization_user ou ON e.emp_id=ou.emp_id JOIN org_organization o ON ou.org_id=o.org_id LEFT JOIN rs_njjh r ON r.rs_njjh_yggh=e.empNumber AND r.rs_njjh_nf='" + 
          year + "年'";
        String whereSql = " o.orgidstring like '%$20461$%' and e.Userisactive=1 AND e.Userisdeleted=0 AND e.emp_id>0 AND e.Empnumber IS NOT NULL ";
        String orderBy = "o.Orgidstring,e.userOrderCode,e.empdutylevel,e.empName";
        int num = 20;
        if (export != null && export.equals("1")) {
          request.setAttribute("fieldTitle", new String[] { "部门", "处室", "姓名", "应休年假天数", "已休年假天数", "剩余天数" });
          num = 100000;
          request.setAttribute("title", "员工年假统计表");
        } 
        PageUtil page = new PageSqlUtil(num);
        List<Object> list = page.list(request, viewSql, fromSql, whereSql, orderBy);
        QingJiaBean qjb = new QingJiaBean(1);
        for (int i = 0; i < list.size(); i++) {
          String[] data = (String[])list.get(i);
          String[] orgs = (new KqShowBean()).getDept(data[0]);
          data[0] = orgs[1];
          data[1] = "";
          if (orgs.length > 2)
            data[1] = orgs[2]; 
          String[] dataNian = qjb.getNianjiaData(data[6], year);
          data[3] = dataNian[0];
          data[4] = dataNian[7];
          data[5] = dataNian[1];
          list.set(i, data);
        } 
        request.setAttribute("dataList", list);
        request.setAttribute("year", year);
      } else if ("kouxin".equals(action)) {
        String year = (request.getParameter("year") == null) ? ((new Date()).getYear() + 1900) : request.getParameter("year");
        String month = (request.getParameter("month") == null) ? ((new Date()).getMonth() + 1) : request.getParameter("month");
        String userId = (request.getParameter("userId") == null) ? "" : request.getParameter("userId");
        request.setAttribute("userId", userId);
        Object object = (request.getParameter("userName") == null) ? request.getSession().getAttribute("userName") : request.getParameter("userName");
        request.setAttribute("userName", object);
        String orgId = (request.getParameter("orgId") == null) ? "" : request.getParameter("orgId");
        String orgName = (request.getParameter("orgName") == null) ? "" : request.getParameter("orgName");
        String export1 = (request.getParameter("export") == null) ? "0" : request.getParameter("export");
        KqShowBean bean = new KqShowBean();
        String[] userIds = bean.getUserArray(userId, orgId);
        if (request.getParameter("type") == null) {
          Map<String, List<String[]>> reMap = new HashMap<String, List<String[]>>();
          for (int i = 0; i < userIds.length; i++) {
            Map<String, List<String[]>> rMap = bean.getKouxinMap(userIds[i], year, month);
            for (String key : rMap.keySet()) {
              if (reMap.get(key) == null) {
                reMap.put(key, rMap.get(key));
                continue;
              } 
              List<String[]> rList = reMap.get(key);
              rList.addAll(rMap.get(key));
              reMap.put(key, rList);
            } 
          } 
          request.setAttribute("dataMap", reMap);
          action = "kouxinAll";
        } else {
          for (int i = 0; i < userIds.length; i++) {
            String userName = StaticParam.getEmpNameByEmpId(userIds[i]);
            request.setAttribute("dataMap" + i, bean.getKouxinMap(userIds[i], year, month));
            request.setAttribute("userId" + i, userIds[i]);
            request.setAttribute("userName" + i, userName);
          } 
          request.setAttribute("userNum", Integer.valueOf(userIds.length));
        } 
        request.setAttribute("year", year);
        request.setAttribute("month", month);
        request.setAttribute("orgId", orgId);
        request.setAttribute("orgName", orgName);
        if (export1.equals("1")) {
          export = "";
          request.setAttribute("title", "中国人寿扣减薪酬假单汇总表");
          request.setAttribute("head", new String[] { "请假类型", "部门", "处室", "姓名", "请假天数", "开始时间", "结束时间" });
          action = String.valueOf(action) + "Export";
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    if (export != null && export.equals("1")) {
      request.setAttribute("type", action);
      action = "export";
    } 
    return actionMapping.findForward(action);
  }
}
