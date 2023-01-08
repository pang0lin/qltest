package com.js.oa.scheme.workreport.action;

import com.js.oa.scheme.workreport.po.WorkReportTransmitPO;
import com.js.oa.scheme.workreport.service.WorkReportBD;
import com.js.oa.scheme.workreport.service.WorkReportLeaderBD;
import com.js.system.manager.service.ManagerService;
import com.js.system.service.organizationmanager.OrganizationBD;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.config.SysConfig;
import com.js.util.page.simple.Page;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.hibernate.HibernateException;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WorkReportLeaderAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, HibernateException {
    HttpSession session = request.getSession(true);
    Long domainId = (session.getAttribute("domainId") == null) ? 
      Long.valueOf("0") : 
      Long.valueOf(session.getAttribute("domainId").toString());
    Long curUserId = new Long(request.getSession(true).getAttribute(
          "userId").toString());
    Long curOrgId = new Long(request.getSession(true).getAttribute("orgId")
        .toString());
    String curUserName = request.getSession(true).getAttribute("userName")
      .toString();
    String curOrgName = request.getSession(true).getAttribute("orgName")
      .toString();
    WorkReportLeaderActionForm workReportLeaderActionForm = 
      (WorkReportLeaderActionForm)actionForm;
    WorkReportLeaderBD bd = new WorkReportLeaderBD();
    String action = request.getParameter("action");
    if ("add".equals(action)) {
      bd.add(curUserName, 
          request.getParameter("receiveRecordId"), 
          (String)curUserId, 
          workReportLeaderActionForm.getPostilContent(), 
          workReportLeaderActionForm.getUsersId(), 
          workReportLeaderActionForm.getUsersName(), 
          workReportLeaderActionForm.getNextWorkClew(), 
          domainId);
      if (workReportLeaderActionForm.getReportReaderId() != null && 
        !"".equals(workReportLeaderActionForm.getReportReaderId())) {
        WorkReportTransmitPO wrtpo = new WorkReportTransmitPO();
        wrtpo.setDomain(domainId.longValue());
        wrtpo.setTransFromEMP(curUserId.longValue());
        wrtpo.setTransFromEMPName(curUserName);
        wrtpo.setTransToEMP(workReportLeaderActionForm
            .getReportReaderId());
        wrtpo.setTransToEMPName(workReportLeaderActionForm
            .getReportReader());
        wrtpo.setTransReason(workReportLeaderActionForm.getTransReason());
        wrtpo.setTransTime(new Date());
        wrtpo.setWorkreportID((new Long(request.getParameter(
                "reportID"))).longValue());
        (new WorkReportBD()).deliverTO(request.getParameter(
              "receiveRecordId"), workReportLeaderActionForm.getReportReaderId(), wrtpo);
      } 
      workReportLeaderActionForm.reset(actionMapping, request);
      action = "load";
    } 
    if ("monthadd".equals(action)) {
      bd.add(curUserName, 
          request.getParameter("receiveRecordId"), 
          (String)curUserId, 
          workReportLeaderActionForm.getPostilContent(), 
          workReportLeaderActionForm.getUsersId(), 
          workReportLeaderActionForm.getUsersName(), 
          workReportLeaderActionForm.getNextWorkClew(), 
          workReportLeaderActionForm.getGrade(), 
          workReportLeaderActionForm.getPostilResult(), 
          workReportLeaderActionForm.getPostilGrade(), 
          domainId);
      if (workReportLeaderActionForm.getReportReaderId() != null && 
        !"".equals(workReportLeaderActionForm.getReportReaderId())) {
        WorkReportTransmitPO wrtpo = new WorkReportTransmitPO();
        wrtpo.setDomain(domainId.longValue());
        wrtpo.setTransFromEMP(curUserId.longValue());
        wrtpo.setTransFromEMPName(curUserName);
        wrtpo.setTransToEMP(workReportLeaderActionForm
            .getReportReaderId());
        wrtpo.setTransToEMPName(workReportLeaderActionForm
            .getReportReader());
        wrtpo.setTransReason(workReportLeaderActionForm.getTransReason());
        wrtpo.setTransTime(new Date());
        wrtpo.setWorkreportID((new Long(request.getParameter(
                "reportID"))).longValue());
        (new WorkReportBD()).deliverTO(request.getParameter(
              "receiveRecordId"), 
            workReportLeaderActionForm
            .getReportReaderId(), wrtpo);
      } 
      workReportLeaderActionForm.reset(actionMapping, request);
      action = "load";
    } 
    if ("delBatch".equals(action)) {
      bd.delBatch(request.getParameter("ids"));
      action = "list";
    } 
    if ("list".equals(action)) {
      list(request, domainId);
      if (request.getParameter("view") != null && 
        "1".equals(request.getParameter("view")))
        return actionMapping.findForward("viewlist"); 
    } 
    if ("back".equals(action)) {
      String reportType = request.getParameter("reportType");
      (new WorkReportLeaderBD()).back(new Long(request.getParameter("reportId")), curUserName, reportType);
      return actionMapping.findForward("close");
    } 
    if ("load".equals(action)) {
      String re = bd.getByIds(request.getParameter("sendRecordId"), 
          request.getParameter("receiveRecordId"), 
          request.getParameter("status"), domainId);
      if ("Y".equals(re)) {
        Vector<ArrayList> vec = bd.load(request.getParameter("sendRecordId"), 
            request.getParameter("receiveRecordId"), 
            request.getParameter("status"), domainId);
        request.setAttribute("listReport", vec.get(0));
        request.setAttribute("listPostil", vec.get(1));
        request.setAttribute("transmitLog", vec.get(2));
        List<Object[]> listReport = vec.get(0);
        Object[] obj = listReport.get(0);
        String empId = (obj[12] == null) ? "" : obj[12].toString();
        WorkReportBD wbd = new WorkReportBD();
        EmployeeVO vo = wbd.getEmployeeByID(new Long(empId));
        Long long_ = curUserId;
        String str1 = "$" + long_ + "$";
        request.setAttribute("re", re);
        if (request.getParameter("reportType").equals("1"))
          return actionMapping.findForward("modi"); 
        return actionMapping.findForward("monthmodi");
      } 
      return actionMapping.findForward("error");
    } 
    return actionMapping.findForward("list");
  }
  
  public void list(HttpServletRequest request, Long domainId) {
    try {
      int pageSize = 15;
      int offset = 0;
      if (request.getParameter("pager.offset") != null)
        offset = Integer.parseInt(request.getParameter("pager.offset")); 
      int currentPage = offset / pageSize + 1;
      long userId = Long.parseLong(String.valueOf(request.getSession(true)
            .getAttribute("userId")));
      String orgId = request.getSession(true).getAttribute("orgId")
        .toString();
      ManagerService bd = new ManagerService();
      List<Object[]> rightlist = bd.getRightScope(userId, "04*02*01");
      String rang = "*AAAA*";
      if (rightlist != null && rightlist.size() > 0) {
        Object[] obj = rightlist.get(0);
        if (obj[0] != null && obj[0].toString().equals("0")) {
          rang = "*0*";
        } else if (obj[0] != null && (obj[0].toString().equals("2") || obj[0].toString().equals("3"))) {
          rang = "*" + orgId + "*";
        } else if (obj[0] != null && obj[0].toString().equals("4")) {
          rang = obj[1].toString();
        } 
      } 
      request.getSession(true).setAttribute("rang", rang);
      String where = "1=1 and poo.reportType=" + request.getParameter("reportType") + " ";
      String queryItem = request.getParameter("queryItem");
      String queryText = request.getParameter("queryText");
      String queryName = request.getParameter("queryName");
      String reportJob = request.getParameter("reportJob");
      String selYear = request.getParameter("selYear");
      String reportCourse = request.getParameter("reportCourse");
      String dataType = SysConfig.getDatabaseType();
      if (queryText != null && !"".equals(queryText))
        if ("oracle".equals(dataType)) {
          where = String.valueOf(where) + " and ( dbms_lob.instr(poo.previousReport,'" + 
            queryText + "',1,1)>0 ";
          where = String.valueOf(where) + " or dbms_lob.instr(poo.nextReport,'" + queryText + 
            "',1,1)>0 )";
        } else {
          where = String.valueOf(where) + " and poo.previousReport like '%" + queryText + 
            "%'";
          where = String.valueOf(where) + " or poo.nextReport like '%" + queryText + "%'";
        }  
      if (queryItem != null && queryItem.equals("1"))
        if ("mysql".equals(dataType)) {
          where = String.valueOf(where) + " and ( poo.reportTime between '" + 
            request.getParameter("startDate") + " 00:00:00" + 
            "' and '" + 
            request.getParameter("endDate") + " 23:59:59" + 
            "'";
          where = String.valueOf(where) + " or  poo.reportTime between '" + 
            request.getParameter("endDate") + " 00:00:00" + 
            "' and '" + 
            request.getParameter("startDate") + " 23:59:59" + 
            "' )";
        } else {
          where = String.valueOf(where) + 
            " and ( poo.reportTime between JSDB.FN_STRTODATE('" + 
            request.getParameter("startDate") + " 00:00:00" + 
            "','L') and JSDB.FN_STRTODATE('" + 
            request.getParameter("endDate") + " 23:59:59" + 
            "','L')";
          where = String.valueOf(where) + 
            " or  poo.reportTime between JSDB.FN_STRTODATE('" + 
            request.getParameter("endDate") + " 00:00:00" + 
            "','L') and JSDB.FN_STRTODATE('" + 
            request.getParameter("startDate") + " 23:59:59" + 
            "','L') )";
        }  
      if (reportJob != null && !"".equals(reportJob))
        where = String.valueOf(where) + " and poo.reportJob like '%" + reportJob + "%' "; 
      if (reportCourse != null && !"".equals(reportCourse))
        where = String.valueOf(where) + " and poo.reportCourse like '%" + reportCourse + "%' "; 
      String s0 = "";
      if (request.getParameter("view") != null && 
        "1".equals(request.getParameter("view"))) {
        s0 = " com.js.oa.scheme.workreport.po.WorkReportPO poo   ";
      } else {
        s0 = " com.js.oa.scheme.workreport.po.WorkReportLeaderPO po join po.report poo  ";
      } 
      String s1 = " ,com.js.system.vo.usermanager.EmployeeVO p1 ";
      String s2 = " ,com.js.system.vo.organizationmanager.OrganizationVO p2 ";
      if (request.getParameter("reportEmpName") != null && !"".equals(request.getParameter("reportEmpName")) && (
        queryName == null || "".equals(queryName)))
        queryName = request.getParameter("reportEmpName"); 
      if (queryName != null && !"".equals(queryName)) {
        String[] tmp = queryName.split(",");
        if (tmp.length > 0) {
          s0 = String.valueOf(s0) + s1;
          where = String.valueOf(where) + " and poo.empId = p1.empId and ( ";
          for (int i = 0; i < tmp.length; i++)
            where = String.valueOf(where) + "p1.empName like '%" + tmp[i] + "%' or "; 
          where = String.valueOf(where.substring(0, where.trim().length() - 2)) + 
            " ) ";
        } 
      } 
      if (selYear != null && !selYear.equals("-1")) {
        where = String.valueOf(where) + " and poo.reportCourse like '" + selYear + "%' ";
        request.setAttribute("selYear", selYear);
      } else {
        request.setAttribute("selYear", "-1");
      } 
      String reportDepart = request.getParameter("reportDepart");
      if (reportDepart != null && !"".equals(reportDepart)) {
        String[] tmp = reportDepart.split(",");
        if (tmp.length > 0) {
          s0 = String.valueOf(s0) + s2;
          String names = "";
          for (int i = 0; i < tmp.length; i++) {
            names = String.valueOf(names) + "'" + tmp[i] + "',";
            WorkReportBD wrb = new WorkReportBD();
            List<Object[]> list1 = wrb.getSonsByName(tmp[i]);
            for (int j = 0; j < list1.size(); j++) {
              Object[] obj = list1.get(j);
              names = String.valueOf(names) + "'" + obj[1].toString() + "',";
            } 
          } 
          names = names.substring(0, names.trim().length() - 1);
          where = String.valueOf(where) + " and poo.reportOrgID = p2.orgId and (" + convertStr(names, "p2.orgName", true) + ")";
        } 
      } 
      if (request.getParameter("view") != null && 
        "1".equals(request.getParameter("view"))) {
        bd = new ManagerService();
        rightlist = bd.getRightScope(userId, "04*02*01");
        if (rightlist != null && rightlist.size() > 0) {
          Object[] obj = rightlist.get(0);
          if (obj[0] != null && obj[0].toString().equals("3")) {
            where = String.valueOf(where) + " and poo.reportOrgID=" + orgId;
          } else if (obj[0] != null && obj[0].toString().equals("2")) {
            String ids = "";
            OrganizationBD obd = new OrganizationBD();
            List<E> list1 = obd.getSons(orgId);
            if (list1 != null && list1.size() != 0) {
              for (int j = 0; j < list1.size(); j++)
                ids = String.valueOf(ids) + list1.get(j).toString() + ","; 
              ids = String.valueOf(ids) + orgId + ",";
              ids = ids.substring(0, ids.trim().length() - 1);
              where = String.valueOf(where) + " and (" + 
                convertStr(ids, "poo.reportOrgID", false) + 
                ")";
            } else {
              where = String.valueOf(where) + " and poo.reportOrgID=" + orgId;
            } 
          } else if (obj[0] != null && obj[0].toString().equals("4")) {
            rang = obj[1].toString();
            rang = rang.substring(1, rang.length() - 1);
            String[] tmp = rang.split("\\*\\*");
            if (tmp.length > 0) {
              String ids = "";
              for (int i = 0; i < tmp.length; i++) {
                ids = String.valueOf(ids) + tmp[i] + ",";
                OrganizationBD obd = new OrganizationBD();
                List<E> list1 = obd.getSons(tmp[i]);
                for (int j = 0; j < list1.size(); j++)
                  ids = String.valueOf(ids) + list1.get(j).toString() + ","; 
              } 
              ids = ids.substring(0, 
                  ids.trim().length() - 1);
              where = String.valueOf(where) + " and (" + convertStr(ids, "poo.reportOrgID", false) + ")";
            } 
          } 
        } else {
          where = String.valueOf(where) + " and poo.reportOrgID=9999999 ";
        } 
      } 
      String reportType = request.getParameter("reportType");
      String orderby = 
        " order by poo.reportCourse desc,poo.reportTime desc ";
      if (request.getParameter("sortFirst") != null && 
        !"none".equals(request.getParameter("sortFirst")))
        if ("hbqj".equals(request.getParameter("sortFirst"))) {
          if ("1".equals(request.getParameter("hbqjDesc"))) {
            orderby = " order by poo.reportCourse ";
          } else if ("2".equals(request.getParameter("hbqjDesc"))) {
            orderby = " order by poo.reportCourse desc ";
          } 
        } else if ("txsj".equals(request.getParameter("sortFirst"))) {
          if ("1".equals(request.getParameter("txsjDesc"))) {
            orderby = " order by poo.reportTime ";
          } else if ("2".equals(request.getParameter("txsjDesc"))) {
            orderby = " order by poo.reportTime desc ";
          } 
        } else if ("xingming".equals(request.getParameter("sortFirst"))) {
          if (dataType.indexOf("mysql") >= 0) {
            if ("1".equals(request.getParameter("xingming"))) {
              orderby = " order by convert(poo.reportEmpName using gbk ) ";
            } else if ("2".equals(request.getParameter("xingming"))) {
              orderby = " order by convert(poo.reportEmpName using gbk ) desc ";
            } 
          } else if ("1".equals(request.getParameter("xingming"))) {
            orderby = " order by poo.reportEmpName ";
          } else if ("2".equals(request.getParameter("xingming"))) {
            orderby = " order by poo.reportEmpName desc ";
          } 
        } else if ("bumen".equals(request.getParameter("sortFirst"))) {
          if ("1".equals(request.getParameter("bumen"))) {
            orderby = " order by poo.reportOrgID ";
          } else if ("2".equals(request.getParameter("bumen"))) {
            orderby = " order by poo.reportOrgID desc ";
          } 
        }  
      Page page = null;
      if (request.getParameter("view") != null && 
        "1".equals(request.getParameter("view"))) {
        page = new Page(
            "poo.id,poo.reportTime,poo.reportEmpName,poo.reportType,poo.reportReader,poo.empId,poo.reportCourse,poo.reportDepart", 
            s0, 
            " where " + (
            (domainId != null) ? (" poo.reportDomainId = " + domainId) : 
            "") + 
            " and " + where + orderby);
      } else {
        page = new Page(
            " po.id,po.hadRead,poo.reportTime,poo.reportEmpName,poo.reportType,poo.reportReader,poo.empId,poo.reportCourse,poo.reportDepart", 
            s0, 
            " where po.empId=" + userId + (
            (domainId != null) ? (" and po.rlDomainId = " + domainId) : 
            "") + 
            " and " + where + orderby);
      } 
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      request.setAttribute("mylist", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", 
          "action,queryText,queryItem,sortFirst,hbqjDesc,txsjDesc,xingming,bumen,startDate,endDate,queryName,reportJob,reportEmpId,reportDepartId,reportDepart,selYear,reportType,view,reportCourse");
      request.setAttribute("reportType", reportType);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private String convertStr(String strIds, String fields, boolean isString) {
    StringBuffer where = new StringBuffer();
    String[] tmp = strIds.split(",");
    int max = 500;
    if (tmp.length > max) {
      int t = (tmp.length % max == 0) ? (
        tmp.length / max) : (
        tmp.length / max + 1);
      for (int i = 0; i < t; i++) {
        if (i == 0) {
          if (isString) {
            where.append(String.valueOf(fields) + " in ('-1'");
          } else {
            where.append(String.valueOf(fields) + " in (-1");
          } 
          for (int j = 0; j < max; j++) {
            if (i * max + j < 
              tmp.length)
              where.append(",")
                .append(
                  tmp[i * max + j]); 
          } 
          where.append(")");
        } else {
          if (isString) {
            where.append(" or " + fields + " in ('-1'");
          } else {
            where.append(" or " + fields + " in (-1");
          } 
          for (int j = 0; j < max; j++) {
            if (i * max + j < 
              tmp.length)
              where.append(",")
                .append(
                  tmp[i * max + j]); 
          } 
          where.append(")");
        } 
      } 
    } else {
      where.append(String.valueOf(fields) + " in (");
      where.append(strIds);
      where.append(")");
    } 
    return where.toString();
  }
}
