package com.js.oa.hr.personnelmanager.action;

import com.js.oa.hr.personnelmanager.po.HprecordPO;
import com.js.oa.hr.personnelmanager.service.HprecordBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class HprecordAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    String action = httpServletRequest.getParameter("action");
    HprecordActionForm hprecordActionForm = (HprecordActionForm)actionForm;
    HprecordBD hprecordBD = new HprecordBD();
    String saveType = httpServletRequest.getParameter("saveType");
    HttpSession session = httpServletRequest.getSession(true);
    String domain = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    if ("hprecordView".equals(action)) {
      hprecordList(httpServletRequest);
      List selectHpName = hprecordBD.selectHpName(domain);
      httpServletRequest.setAttribute("hortationPunishList", selectHpName);
      return actionMapping.findForward("gotoHprecordList");
    } 
    if ("hprecordSearch".equals(action)) {
      hprecordSearchList(httpServletRequest);
      List selectHpName = hprecordBD.selectHpName(domain);
      httpServletRequest.setAttribute("hortationPunishList", selectHpName);
      return actionMapping.findForward("gotoHprecordList");
    } 
    if ("addHprecordView".equals(action)) {
      List selectHpName = hprecordBD.selectHpName(domain);
      httpServletRequest.setAttribute("hortationPunishList", selectHpName);
      return actionMapping.findForward("gotoHprecordAdd");
    } 
    if ("addHprecord".equals(action)) {
      String hpDate = httpServletRequest.getParameter("hpDate");
      String hpTitle = httpServletRequest.getParameter("hpTitle");
      String hpPersonnelName = httpServletRequest.getParameter(
          "hpPersonnelName");
      Long hpType = new Long(httpServletRequest.getParameter("hpType"));
      String hpExplain = httpServletRequest.getParameter("hpExplain");
      HprecordPO hprecordPO = new HprecordPO();
      hprecordPO.setDomain(new Long(domain));
      hprecordPO.setHpDate(new Date(hpDate));
      hprecordPO.setHpTitle(hpTitle);
      hprecordPO.setHpType(hpType);
      hprecordPO.setHpPersonnel(hprecordActionForm.getHpPersonnel());
      hprecordPO.setHpPersonnelName(hpPersonnelName);
      hprecordPO.setHpExplain(hpExplain);
      hprecordPO.setCreatedEmp(new Long(session.getAttribute("userId")
            .toString()));
      hprecordPO.setCreatedOrg(new Long(session.getAttribute("orgId")
            .toString()));
      hprecordPO.setDomain(new Long(domain));
      List selectHpName = hprecordBD.selectHpName(domain);
      boolean result = hprecordBD.addHprecord(hprecordPO);
      if (result) {
        if ("saveAndContinue".equals(saveType)) {
          hprecordActionForm.reset(actionMapping, httpServletRequest);
          hprecordActionForm.setSaveType("saveAndContinue");
          httpServletRequest.setAttribute("hortationPunishList", 
              selectHpName);
          return actionMapping.findForward("hprecord_saveAndContinue");
        } 
        if ("saveAndExit".equals(saveType)) {
          hprecordActionForm.reset(actionMapping, httpServletRequest);
          hprecordActionForm.setSaveType("saveAndExit");
          httpServletRequest.setAttribute("hortationPunishList", 
              selectHpName);
          return actionMapping.findForward("hprecord_saveAndExit");
        } 
      } 
    } 
    if ("deleteHprecord".equals(action)) {
      Long hprecordId = Long.valueOf("0");
      if (httpServletRequest.getParameter("hprecordId") != null)
        hprecordId = Long.valueOf(httpServletRequest.getParameter(
              "hprecordId")); 
      if (hprecordId.longValue() != 0L)
        hprecordBD.deleteHprecord(hprecordId); 
      List selectHpName = hprecordBD.selectHpName(domain);
      httpServletRequest.setAttribute("hortationPunishList", selectHpName);
      hprecordSearchList(httpServletRequest);
      hprecordActionForm.reset(actionMapping, httpServletRequest);
      return actionMapping.findForward("gotoHprecordList");
    } 
    if ("deleteBatchHprecord".equals(action)) {
      String hprecordIds = httpServletRequest.getParameter("ids");
      boolean result = hprecordBD.deleteBatchHprecord(hprecordIds);
      List selectHpName = hprecordBD.selectHpName(domain);
      httpServletRequest.setAttribute("hortationPunishList", selectHpName);
      if (result)
        hprecordSearchList(httpServletRequest); 
      hprecordActionForm.reset(actionMapping, httpServletRequest);
      return actionMapping.findForward("gotoHprecordList");
    } 
    if ("toHprecordView".equals(action)) {
      Long hprecordId = Long.valueOf(httpServletRequest.getParameter(
            "hprecordId"));
      HprecordPO hprecordPO = hprecordBD.selectHprecordView(hprecordId);
      Date hpDate = hprecordPO.getHpDate();
      hprecordActionForm.setHpExplain(hprecordPO.getHpExplain());
      hprecordActionForm.setHpTitle(hprecordPO.getHpTitle());
      Long hpType = hprecordPO.getHpType();
      hprecordActionForm.setHpPersonnel(hprecordPO.getHpPersonnel());
      hprecordActionForm.setHpPersonnelName(hprecordPO
          .getHpPersonnelName());
      hprecordActionForm.setHpExplain(hprecordPO.getHpExplain());
      List selectHpName = hprecordBD.selectHpName(domain);
      httpServletRequest.setAttribute("hpType", hpType);
      httpServletRequest.setAttribute("hpDate", hpDate);
      httpServletRequest.setAttribute("hortationPunishList", selectHpName);
      httpServletRequest.setAttribute("hprecordId", hprecordPO.getId());
      return actionMapping.findForward("gotoViewHprecord");
    } 
    if ("selectHprecordView".equals(action)) {
      Long hprecordId = Long.valueOf(httpServletRequest.getParameter(
            "hprecordId"));
      HprecordPO hprecordPO = hprecordBD.selectHprecordView(hprecordId);
      Date hpDate = hprecordPO.getHpDate();
      hprecordActionForm.setHpExplain(hprecordPO.getHpExplain());
      hprecordActionForm.setHpTitle(hprecordPO.getHpTitle());
      Long hpType = hprecordPO.getHpType();
      hprecordActionForm.setHpPersonnel(hprecordPO.getHpPersonnel());
      hprecordActionForm.setHpPersonnelName(hprecordPO.getHpPersonnelName());
      hprecordActionForm.setHpExplain(hprecordPO.getHpExplain());
      List selectHpName = hprecordBD.selectHpName(domain);
      httpServletRequest.setAttribute("hpType", hpType);
      httpServletRequest.setAttribute("hpDate", hpDate);
      httpServletRequest.setAttribute("hortationPunishList", selectHpName);
      httpServletRequest.setAttribute("hprecordId", hprecordPO.getId());
      return actionMapping.findForward("gotoModiHprecord");
    } 
    if ("updateHprecord".equals(action)) {
      Long hprecordId = Long.valueOf(httpServletRequest.getParameter(
            "hprecordId"));
      Date hpDate = new Date(httpServletRequest.getParameter("hpDate"));
      HprecordPO hprecordPO = new HprecordPO();
      hprecordPO.setId(hprecordId);
      hprecordPO.setHpDate(hpDate);
      hprecordPO.setHpTitle(hprecordActionForm.getHpTitle());
      hprecordPO.setHpType(hprecordActionForm.getHpType());
      hprecordPO.setHpPersonnel(hprecordActionForm.getHpPersonnel());
      hprecordPO.setHpPersonnelName(hprecordActionForm.getHpPersonnelName());
      hprecordPO.setHpExplain(hprecordActionForm.getHpExplain());
      hprecordPO.setDomain(new Long(domain));
      boolean result = hprecordBD.updateHprecord(hprecordPO);
      List selectHpName = hprecordBD.selectHpName(domain);
      if (result && 
        "updateAndExit".equals(saveType)) {
        hprecordActionForm.reset(actionMapping, httpServletRequest);
        hprecordActionForm.setSaveType("updateAndExit");
        httpServletRequest.setAttribute("hortationPunishList", 
            selectHpName);
        return actionMapping.findForward("hprecord_updateAndExit");
      } 
    } 
    if ("personCard".equals(action)) {
      String whereSql = "where hprecordPO.domain=" + domain + 
        " and hprecordPO.hpType = hortationPunishPO.id  and hprecordPO.hpPersonnel like '%$" + 
        httpServletRequest.getParameter("empID") + 
        "$%' order by hprecordPO.hpDate desc";
      String viewSql = 
        "hprecordPO.id,hprecordPO.hpDate,hprecordPO.hpTitle,hortationPunishPO.hp_name,hprecordPO.hpPersonnelName,hortationPunishPO.hp_dealwith,hprecordPO.hpType";
      String fromSql = 
        "com.js.oa.hr.personnelmanager.po.HprecordPO hprecordPO,com.js.oa.hr.personnelmanager.po.HortationPunishPO hortationPunishPO";
      list(httpServletRequest, viewSql, fromSql, whereSql);
      return actionMapping.findForward("personCard");
    } 
    if ("export".equals(action)) {
      export(httpServletRequest);
      return actionMapping.findForward("export");
    } 
    throw new UnsupportedOperationException(
        "Method perform() not yet implemented.");
  }
  
  private void hprecordList(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domain = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String whereSql = "";
    String curUserId = httpServletRequest.getSession(true).getAttribute(
        "userId")
      .toString();
    String orgID = httpServletRequest.getSession(true).getAttribute("orgId")
      .toString();
    ManagerService mbd = new ManagerService();
    String rightSQL = mbd.getRightFinalWhere(curUserId, orgID, "07*33*02", 
        "hprecordPO.createdOrg", 
        "hprecordPO.createdEmp");
    rightSQL = "( (" + rightSQL + ") or hprecordPO.createdEmp=" + curUserId + 
      ")";
    String databaseType = 
      SystemCommon.getDatabaseType();
    whereSql = "where hprecordPO.domain=" + domain + 
      " and hprecordPO.hpType = hortationPunishPO.id  and " + 
      rightSQL + " order by hprecordPO.hpDate desc";
    String viewSql = 
      "hprecordPO.id,hprecordPO.hpDate,hprecordPO.hpTitle,hortationPunishPO.hp_name,hprecordPO.hpPersonnelName,hortationPunishPO.hp_dealwith,hprecordPO.hpType";
    String fromSql = 
      "com.js.oa.hr.personnelmanager.po.HprecordPO hprecordPO,com.js.oa.hr.personnelmanager.po.HortationPunishPO hortationPunishPO";
    list(httpServletRequest, viewSql, fromSql, whereSql);
  }
  
  private void list(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    if (list.size() == 0 && offset >= 15) {
      offset -= 15;
      currentPage = offset / pageSize + 1;
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      httpServletRequest.setAttribute("new.offset", (new StringBuilder(String.valueOf(offset))).toString());
      httpServletRequest.setAttribute("pager.realCurrent", (
          new StringBuilder(String.valueOf(currentPage))).toString());
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("personnelList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,empID");
  }
  
  public void hprecordSearchList(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domain = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String beginDate = String.valueOf(httpServletRequest.getParameter("beginDate")) + 
      " 00:00:00";
    String endDate = String.valueOf(httpServletRequest.getParameter("endDate")) + 
      " 23:59:59";
    String searchHpTitle = (httpServletRequest.getParameter(
        "searchHpTitle") == null) ? "" : httpServletRequest.getParameter(
        "searchHpTitle");
    String searchHpType = httpServletRequest.getParameter("searchHpType");
    String searchHpPersonnelName = (httpServletRequest.getParameter(
        "searchHpPersonnelName") == null) ? "" : 
      httpServletRequest.getParameter(
        "searchHpPersonnelName");
    String htrq = httpServletRequest.getParameter("htrq");
    String where = " where hprecordPO.domain=" + domain + 
      " and hprecordPO.hpType = hortationPunishPO.id ";
    String curUserId = httpServletRequest.getSession(true).getAttribute(
        "userId")
      .toString();
    String orgID = httpServletRequest.getSession(true).getAttribute("orgId")
      .toString();
    ManagerService mbd = new ManagerService();
    String rightSQL = mbd.getRightFinalWhere(curUserId, orgID, "07*33*02", 
        "hprecordPO.createdOrg", 
        "hprecordPO.createdEmp");
    rightSQL = "( (" + rightSQL + ") or hprecordPO.createdEmp=" + curUserId + 
      ")";
    where = String.valueOf(where) + " and " + rightSQL;
    String viewSql = "";
    String fromSql = "";
    String databaseType = 
      SystemCommon.getDatabaseType();
    if (!"".equals(searchHpTitle))
      where = String.valueOf(where) + " and  hprecordPO.hpTitle like '%" + 
        searchHpTitle + "%' "; 
    if (!"".equals(searchHpPersonnelName))
      where = String.valueOf(where) + " and hprecordPO.hpPersonnelName like '%" + 
        searchHpPersonnelName + "%'"; 
    if (htrq != null && !htrq.equals("null") && !htrq.equals("")) {
      if (databaseType.indexOf("mysql") >= 0) {
        where = String.valueOf(where) + " and hprecordPO.hpDate between '" + beginDate + 
          "' and '" + endDate + "'";
      } else {
        where = String.valueOf(where) + 
          " and hprecordPO.hpDate between JSDB.FN_STRTODATE('" + 
          beginDate + "','L') and JSDB.FN_STRTODATE('" + 
          endDate + "','L')";
      } 
      httpServletRequest.setAttribute("htrqchecked", "1");
      httpServletRequest.setAttribute("beginDate", beginDate);
      httpServletRequest.setAttribute("endDate", endDate);
    } 
    if (searchHpType != null && !"0".equals(searchHpType))
      where = String.valueOf(where) + " and hprecordPO.hpType='" + searchHpType + "'"; 
    where = String.valueOf(where) + " order by hprecordPO.hpDate desc";
    viewSql = 
      "hprecordPO.id,hprecordPO.hpDate,hprecordPO.hpTitle,hortationPunishPO.hp_name,hprecordPO.hpPersonnelName,hortationPunishPO.hp_dealwith,hprecordPO.hpType";
    fromSql = 
      "com.js.oa.hr.personnelmanager.po.HprecordPO hprecordPO,com.js.oa.hr.personnelmanager.po.HortationPunishPO hortationPunishPO";
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSql, fromSql, where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    if (list.size() == 0 && offset >= 15) {
      offset -= 15;
      currentPage = offset / pageSize + 1;
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      httpServletRequest.setAttribute("new.offset", (new StringBuilder(String.valueOf(offset))).toString());
      httpServletRequest.setAttribute("pager.realCurrent", (
          new StringBuilder(String.valueOf(currentPage))).toString());
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("searchHpTitle", searchHpTitle);
    httpServletRequest.setAttribute("searchHpType", searchHpType);
    httpServletRequest.setAttribute("searchHpPersonnelName", 
        searchHpPersonnelName);
    httpServletRequest.setAttribute("personnelList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", 
        "action,beginDate,endDate,searchHpTitle,searchHpType,searchHpPersonnelName,htrq");
  }
  
  public void export(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domain = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String beginDate = String.valueOf(httpServletRequest.getParameter("beginDate")) + 
      " 00:00:00";
    String endDate = String.valueOf(httpServletRequest.getParameter("endDate")) + 
      " 23:59:59";
    String searchHpTitle = (httpServletRequest.getParameter(
        "searchHpTitle") == null) ? "" : httpServletRequest.getParameter(
        "searchHpTitle");
    String searchHpType = httpServletRequest.getParameter("searchHpType");
    String searchHpPersonnelName = (httpServletRequest.getParameter(
        "searchHpPersonnelName") == null) ? "" : 
      httpServletRequest.getParameter(
        "searchHpPersonnelName");
    String htrq = httpServletRequest.getParameter("htrq");
    String where = " where hprecordPO.domain=" + domain + 
      " and hprecordPO.hpType = hortationPunishPO.id ";
    String curUserId = httpServletRequest.getSession(true).getAttribute(
        "userId")
      .toString();
    String orgID = httpServletRequest.getSession(true).getAttribute("orgId")
      .toString();
    ManagerService mbd = new ManagerService();
    String rightSQL = mbd.getRightFinalWhere(curUserId, orgID, "07*33*02", 
        "hprecordPO.createdOrg", 
        "hprecordPO.createdEmp");
    rightSQL = "( (" + rightSQL + ") or hprecordPO.createdEmp=" + curUserId + 
      ")";
    where = String.valueOf(where) + " and " + rightSQL;
    String viewSql = "";
    String fromSql = "";
    String databaseType = 
      SystemCommon.getDatabaseType();
    if (!"".equals(searchHpTitle))
      where = String.valueOf(where) + " and  hprecordPO.hpTitle like '%" + 
        searchHpTitle + "%' "; 
    if (!"".equals(searchHpPersonnelName))
      where = String.valueOf(where) + " and hprecordPO.hpPersonnelName like '%" + 
        searchHpPersonnelName + "%'"; 
    if (htrq != null && !htrq.equals("null") && !htrq.equals("")) {
      if (databaseType.indexOf("mysql") >= 0) {
        where = String.valueOf(where) + " and hprecordPO.hpDate between '" + beginDate + 
          "' and '" + endDate + "'";
      } else {
        where = String.valueOf(where) + 
          " and hprecordPO.hpDate between JSDB.FN_STRTODATE('" + 
          beginDate + "','L') and JSDB.FN_STRTODATE('" + 
          endDate + "','L')";
      } 
      httpServletRequest.setAttribute("htrqchecked", "1");
      httpServletRequest.setAttribute("beginDate", beginDate);
      httpServletRequest.setAttribute("endDate", endDate);
    } 
    if (searchHpType != null && !"0".equals(searchHpType))
      where = String.valueOf(where) + " and hprecordPO.hpType='" + searchHpType + "'"; 
    where = String.valueOf(where) + " order by hprecordPO.hpDate desc";
    viewSql = 
      "hprecordPO.id,hprecordPO.hpDate,hprecordPO.hpTitle,hortationPunishPO.hp_name,hprecordPO.hpPersonnelName,hortationPunishPO.hp_dealwith,hprecordPO.hpType";
    fromSql = 
      "com.js.oa.hr.personnelmanager.po.HprecordPO hprecordPO,com.js.oa.hr.personnelmanager.po.HortationPunishPO hortationPunishPO";
    int pageSize = 999999;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSql, fromSql, where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    httpServletRequest.setAttribute("personnelList", list);
  }
}
