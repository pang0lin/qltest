package com.js.oa.relproject.action;

import com.js.oa.message.service.MsManageBD;
import com.js.oa.relproject.bean.RelProjectBean;
import com.js.oa.relproject.po.RelProjectPO;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.DataSourceBase;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import net.sf.hibernate.ObjectNotFoundException;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class RelProjectAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String userName = session.getAttribute("userName").toString();
    String orgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    String promanagerIds = request.getParameter("promanagerIds");
    request.setAttribute("promanagerIds", promanagerIds);
    String projectId_ = request.getParameter("id");
    String operate = request.getParameter("flag");
    String tag = "add";
    if (!"add".equals(operate))
      if ("save".equals(operate) || "modify".equals(operate)) {
        tag = "add";
        RelProjectActionForm proForm = (RelProjectActionForm)form;
        RelProjectPO po = new RelProjectPO();
        BeanUtils.copyProperties(po, proForm);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(request.getParameter("startTime")));
        calendar.set(11, Integer.valueOf(request.getParameter("startHour")).intValue());
        calendar.set(12, Integer.valueOf(request.getParameter("startMinutes")).intValue());
        po.setStartTime(calendar.getTime());
        calendar.setTime(new Date(request.getParameter("endTime")));
        calendar.set(11, Integer.valueOf(request.getParameter("endHour")).intValue());
        calendar.set(12, Integer.valueOf(request.getParameter("endMinutes")).intValue());
        po.setEndTime(calendar.getTime());
        po.setClassId(Long.valueOf(request.getParameter("classId")).longValue());
        String[] actor = new String[8];
        actor[0] = request.getParameter("fzrId");
        actor[1] = request.getParameter("fzrName");
        actor[2] = request.getParameter("ldId");
        actor[3] = request.getParameter("ldName");
        actor[4] = request.getParameter("cyrId");
        actor[5] = request.getParameter("cyrName");
        actor[6] = request.getParameter("xgrId");
        actor[7] = request.getParameter("xgrName");
        String[] itemTitle = request.getParameterValues("itemTitle");
        String[] itemStartTime = request.getParameterValues("itemStartTime");
        String[] itemEndTime = request.getParameterValues("itemEndTime");
        String[] itemRemind = request.getParameterValues("itemRemind");
        RelProjectBean bean = new RelProjectBean();
        if ("modify".equals(operate)) {
          po.setId(Long.valueOf(request.getParameter("projectId")));
          bean.save(po, itemTitle, itemStartTime, itemEndTime, itemRemind, actor, userId, userName, orgId, "1");
        } else {
          bean.save(po, itemTitle, itemStartTime, itemEndTime, itemRemind, actor, userId, userName, orgId, "0");
        } 
        request.setAttribute("operate", "saveClose");
        String openType = (request.getParameter("openType") == null) ? "" : request.getParameter("openType");
        request.setAttribute("openType", openType);
        tag = "close";
      } else if ("list".equals(operate)) {
        tag = "list";
        RelProjectBean bean = new RelProjectBean();
        String para = " distinct po.id,po.title,po.startTime,po.endTime,po.rate";
        String from = "com.js.oa.relproject.po.RelProjectPO po join po.projectActor act";
        String where = bean.getCurScopeWhere(userId, orgId, orgIdString, "act.actorId", "act.actorType");
        String status = request.getParameter("status");
        String title = request.getParameter("title");
        String searchDate = request.getParameter("searchDate");
        String fzrId = request.getParameter("fzrId");
        if (title != null && !"".equals(title) && !"null".equals(title))
          where = String.valueOf(where) + " and po.title like '%" + title + "%'"; 
        if (status != null && !"".equals(status) && !"null".equals(status))
          where = String.valueOf(where) + " and po.status= " + status; 
        String orderBy = " order by po.startTime desc, po.endTime desc ";
        where = "where  " + where;
        if ("1".equals(searchDate)) {
          String startDate = request.getParameter("searchBeginDate");
          String endDate = request.getParameter("searchEndDate");
          String databaseType = SystemCommon.getDatabaseType();
          if (databaseType.indexOf("mysql") >= 0) {
            where = String.valueOf(where) + " and ('" + startDate + "' between po.startTime and po.endTime or '" + endDate + "' between po.startTime and po.endTime)";
          } else {
            startDate = startDate.replaceAll("/", "-");
            endDate = endDate.replaceAll("/", "-");
            where = String.valueOf(where) + " and (JSDB.FN_STRTODATE('" + startDate + "','S') between po.startTime and po.endTime or JSDB.FN_STRTODATE('" + endDate + "','S') between po.startTime and po.endTime)";
          } 
        } 
        if (fzrId != null && !"".equals(fzrId))
          where = String.valueOf(where) + " and po.id in (select act2.projectId from com.js.oa.relproject.po.RelProActorSinglePO act2 where act2.actorId=" + fzrId + " and act2.actorType=10 and act2.actorRole=10)"; 
        where = String.valueOf(where) + orderBy;
        request.setAttribute("status", status);
        request.setAttribute("title", title);
        list(request, para, from, where);
      } else if ("detail".equals(operate)) {
        tag = "detail";
        request.setAttribute("proId", projectId_);
        RelProjectBean bean = new RelProjectBean();
        Map map = bean.getRelationDetail(projectId_, userId, orgId, orgIdString);
        RelProjectPO projectPO = (RelProjectPO)map.get("projectInfo");
        List list = bean.getActorByProId(Long.parseLong(projectId_));
        String userRoll = "0";
        if (list.contains(Long.valueOf(Long.parseLong(userId))))
          userRoll = "1"; 
        request.setAttribute("userRoll", userRoll);
        request.setAttribute("projectId_", projectId_);
        request.setAttribute("detail", map);
      } else if ("load".equals(operate)) {
        tag = "load";
        String openType = (request.getParameter("openType") == null) ? "" : request.getParameter("openType");
        RelProjectBean bean = new RelProjectBean();
        Map map = null;
        try {
          map = bean.getProjectInfo(projectId_);
        } catch (ObjectNotFoundException e) {
          e.printStackTrace();
          return mapping.findForward("error");
        } 
        request.setAttribute("map", map);
        request.setAttribute("openType", openType);
      } else if ("delete".equals(operate)) {
        String proIds = request.getParameter("ids");
        String status = request.getParameter("status");
        RelProjectBean bean = new RelProjectBean();
        if (proIds != null && proIds.length() > 0 && !"null".equals(proIds))
          bean.deleteProject(proIds); 
        tag = "list";
        String para = "distinct po.id,po.title,po.startTime,po.endTime,po.rate";
        String from = "com.js.oa.relproject.po.RelProjectPO po join po.projectActor act";
        String where = bean.getCurScopeWhere(userId, orgId, orgIdString, "act.actorId", "act.actorType");
        where = "where po.status=" + status + " and " + where;
        list(request, para, from, where);
      } else if ("flowlist".equals(operate)) {
        tag = "flowlist";
        RelProjectBean bean = new RelProjectBean();
        request.setAttribute("relproject", bean.getProjectInfo(projectId_));
        StringBuffer para = new StringBuffer();
        request.setAttribute("projectId_", projectId_);
        para.append("aaa.workTitle,aaa.workSubmitPerson, aaa.workSubmitTime,")
          .append("aaa.workProcessId,aaa.workTableId, aaa.workRecordId ");
        String from = "com.js.oa.jsflow.po.WFWorkPO aaa";
        boolean isPrincipal = bean.isPrincipal(projectId_, userId);
        String where = " where aaa.relProjectId=" + projectId_;
        if (!isPrincipal) {
          where = String.valueOf(where) + " and aaa.wfCurEmployeeId=" + userId;
        } else {
          where = String.valueOf(where) + " and aaa.workStartFlag=1";
        } 
        where = String.valueOf(where) + " order by aaa.wfWorkId desc";
        list(request, para.toString(), from, where);
      } else if (operate.equals("get2Column")) {
        StringBuffer xml = new StringBuffer(1024);
        response.setContentType("text/xml;charset=GBK");
        PrintWriter out = response.getWriter();
        String repId = request.getParameter("repId");
        String a = get2Column(userId, repId);
        xml.append("<?xml version=\"1.0\" encoding=\"GBK\" ?>\n");
        xml.append("<result>\n");
        xml.append("  <readerId>" + a.split(";")[0] + "</readerId>\n");
        xml.append("  <hadRead>" + a.split(";")[1] + "</hadRead>\n");
        xml.append("</result>\n");
        out.print(xml.toString());
        out.close();
        return null;
      }  
    return mapping.findForward(tag);
  }
  
  public void list(HttpServletRequest request, String viewSql, String fromSql, String whereSql) {
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSql, fromSql, whereSql);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List<Object[]> list = page.getResultList();
    MsManageBD msBD = new MsManageBD();
    String sql = "select p.alarmId,p.alarmDays,p.alarmColor from com.js.oa.relproject.po.ProAlarmSet p where p.alarmEnable='1' order by p.alarmDays desc";
    List<Object[]> msList = null;
    try {
      msList = msBD.getListByYourSQL(sql);
      if (msList != null && msList.size() != 0 && list != null && list.size() > 0)
        for (int k = 0; k < list.size(); k++) {
          Object[] oj = list.get(k);
          if (oj[3] != null && !"".equals(oj[3])) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date endDate = sdf.parse(oj[3].toString());
            Date nowDate = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(endDate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(nowDate);
            long time2 = cal.getTimeInMillis();
            long between_days = (time1 - time2) / 86400000L;
            String alarmColor = "";
            String alarmDays = "";
            String zhengchang = "";
            for (int i = 0; i < msList.size(); i++) {
              Object[] obj = msList.get(i);
              if (between_days < Long.valueOf(obj[1].toString()).longValue()) {
                alarmColor = obj[2].toString();
                alarmDays = obj[1].toString();
              } else {
                if (i == 0)
                  zhengchang = obj[1].toString(); 
                break;
              } 
            } 
            if (!"".equals(alarmColor))
              oj[1] = "<font color='" + alarmColor + "' title='距结束时间小于" + alarmDays + "天'>" + oj[1] + "</font>"; 
            if (!"".equals(zhengchang))
              oj[1] = "<div title='距结束时间大于" + zhengchang + "天'>" + oj[1] + "</div>"; 
          } 
        }  
    } catch (Exception e) {
      e.printStackTrace();
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    request.setAttribute("list", list);
    request.setAttribute("proLeaders", getProjectLeader(list));
    request.setAttribute("proClassName", getProjectClass(list));
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "flag,status,title,fzrName,fzrId,searchDate,searchBeginDate,searchEndDate,id");
  }
  
  private Map getProjectLeader(List<Object[]> list) {
    if (list != null) {
      StringBuffer ids = new StringBuffer("-1");
      for (int i = 0; i < list.size(); i++) {
        Object[] obj = list.get(i);
        ids.append(",").append(obj[0]);
      } 
      return (new RelProjectBean()).getProjectLeaders(ids.toString());
    } 
    return new HashMap<Object, Object>();
  }
  
  private Map getProjectClass(List<Object[]> list) {
    if (list != null) {
      StringBuffer ids = new StringBuffer("-1");
      for (int i = 0; i < list.size(); i++) {
        Object[] obj = list.get(i);
        ids.append(",").append(obj[0]);
      } 
      return (new RelProjectBean()).getProjectClass(ids.toString());
    } 
    return new HashMap<Object, Object>();
  }
  
  private String get2Column(String userId, String repId) {
    String name = "";
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT REPORTLEADER_ID,HADREAD FROM rep_leader where EMP_ID=" + userId + " and REPORT_ID=" + repId);
      if (rs.next())
        name = String.valueOf(rs.getString(1)) + ";" + rs.getString(2); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return name;
  }
  
  public String getUserIdString(String relproject_id) {
    String idStr = "";
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT CLASSUSERID FROM OA_FORUMCLASS where relproject_id=" + relproject_id);
      if (rs.next())
        idStr = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return idStr;
  }
}
