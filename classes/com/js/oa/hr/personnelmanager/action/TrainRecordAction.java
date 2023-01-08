package com.js.oa.hr.personnelmanager.action;

import com.js.oa.hr.personnelmanager.po.TrainRecordPO;
import com.js.oa.hr.personnelmanager.service.TrainClassBD;
import com.js.oa.hr.personnelmanager.service.TrainRecordBD;
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

public class TrainRecordAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    TrainRecordActionForm trainRecordActionForm = 
      (TrainRecordActionForm)actionForm;
    String flag = (httpServletRequest.getParameter("flag") == null) ? "list" : 
      httpServletRequest.getParameter("flag");
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String tag = "list";
    if (flag.equals("list")) {
      tag = "list";
      httpServletRequest.setAttribute("trainClassList", (
          new TrainClassBD())
          .list(new Long(domainId)));
      view(httpServletRequest);
    } else if (flag.equals("add")) {
      tag = "add";
      httpServletRequest.setAttribute("trainClassList", (
          new TrainClassBD())
          .list(new Long(domainId)));
    } else if (flag.equals("close") || flag.equals("continue")) {
      tag = "add";
      httpServletRequest.setAttribute("trainClassList", (
          new TrainClassBD())
          .list(new Long(domainId)));
      if (save(trainRecordActionForm, httpServletRequest)) {
        if (flag.equals("continue")) {
          httpServletRequest.setAttribute("close", "0");
        } else {
          httpServletRequest.setAttribute("close", "1");
        } 
      } else {
        httpServletRequest.setAttribute("close", "2");
      } 
    } else if (flag.equals("modify")) {
      tag = "modify";
      load(trainRecordActionForm, httpServletRequest);
    } else if (flag.equals("update")) {
      tag = "modify";
      if (update(trainRecordActionForm, httpServletRequest)) {
        httpServletRequest.setAttribute("close", "1");
      } else {
        httpServletRequest.setAttribute("close", "2");
      } 
    } else if (flag.equals("delete")) {
      tag = "list";
      httpServletRequest.setAttribute("trainClassList", (
          new TrainClassBD())
          .list(new Long(domainId)));
      delete(httpServletRequest);
    } else if (flag.equals("view")) {
      tag = "view";
      load(trainRecordActionForm, httpServletRequest);
    } else if (flag.equals("search")) {
      tag = "list";
      httpServletRequest.setAttribute("trainClassList", (
          new TrainClassBD())
          .list(new Long(domainId)));
      search(httpServletRequest);
    } else if (flag.equals("personCard")) {
      tag = "personCard";
      String viewSql = 
        "po.id,po.trainName,po.trainAddress,po.beginDate,po.endDate,ppo.trainName,po.trainActor,po.trainClass";
      String fromSql = 
        " com.js.oa.hr.personnelmanager.po.TrainRecordPO po,com.js.oa.hr.personnelmanager.po.TrainClassPO ppo ";
      String whereSql = " where po.trainClass=ppo.id and po.domain=" + 
        domainId + " and po.trainEmpID like '%$" + httpServletRequest.getParameter("empId") + 
        "$%' order by po.id desc ";
      list(httpServletRequest, viewSql, fromSql, whereSql);
    } else if (flag.equals("export")) {
      tag = "export";
      export(httpServletRequest);
    } 
    return actionMapping.findForward(tag);
  }
  
  private void view(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String curUserId = httpServletRequest.getSession(true).getAttribute(
        "userId")
      .toString();
    String orgID = httpServletRequest.getSession(true).getAttribute("orgId")
      .toString();
    ManagerService mbd = new ManagerService();
    String rightSQL = mbd.getRightFinalWhere(curUserId, orgID, "07*32*02", 
        "po.createdOrg", 
        "po.createdEmpID");
    rightSQL = "( (" + rightSQL + ") or po.createdEmpID=" + curUserId + ")";
    String viewSql = 
      "po.id,po.trainName,po.trainAddress,po.beginDate,po.endDate,ppo.trainName,po.trainActor";
    String fromSql = 
      " com.js.oa.hr.personnelmanager.po.TrainRecordPO po,com.js.oa.hr.personnelmanager.po.TrainClassPO ppo ";
    String whereSql = " where po.trainClass=ppo.id and po.domain=" + 
      domainId + " and " + rightSQL + 
      " order by po.id desc ";
    list(httpServletRequest, viewSql, fromSql, whereSql);
  }
  
  private void search(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String where = "";
    String beginDate = String.valueOf(httpServletRequest.getParameter("beginDate")) + 
      " 00:00:00";
    String endDate = String.valueOf(httpServletRequest.getParameter("endDate")) + 
      " 23:59:59";
    String trainName = (httpServletRequest.getParameter("trainName") == null) ? 
      "" : httpServletRequest.getParameter("trainName");
    String trainActor = (httpServletRequest.getParameter("trainActor") == null) ? 
      "" : httpServletRequest.getParameter("trainActor");
    if (trainName != null && !"".equals(trainName))
      where = String.valueOf(where) + " and po.trainName like '%" + trainName + "%' "; 
    if (trainActor != null && !"".equals(trainActor))
      where = String.valueOf(where) + " and po.trainActor like '%" + trainActor + "%' "; 
    if (httpServletRequest.getParameter("trainClass") != null && 
      Integer.parseInt(httpServletRequest.getParameter("trainClass")) != 0)
      where = String.valueOf(where) + " and po.trainClass=" + 
        httpServletRequest.getParameter("trainClass"); 
    String databaseType = 
      SystemCommon.getDatabaseType();
    if (httpServletRequest.getParameter("pxrq") != null && 
      !"".equals(httpServletRequest.getParameter("pxrq"))) {
      if (databaseType.indexOf("mysql") >= 0) {
        where = String.valueOf(where) + " and po.beginDate between '" + beginDate + 
          "' and '" + endDate + "'";
      } else {
        where = String.valueOf(where) + 
          " and po.beginDate between JSDB.FN_STRTODATE('" + 
          beginDate + "','L') and JSDB.FN_STRTODATE('" + 
          endDate + "','L')";
      } 
      httpServletRequest.setAttribute("pxrqchecked", "1");
      httpServletRequest.setAttribute("beginDate", beginDate);
      httpServletRequest.setAttribute("endDate", endDate);
    } 
    String curUserId = httpServletRequest.getSession(true).getAttribute(
        "userId")
      .toString();
    String orgID = httpServletRequest.getSession(true).getAttribute("orgId")
      .toString();
    ManagerService mbd = new ManagerService();
    String rightSQL = mbd.getRightFinalWhere(curUserId, orgID, "07*32*02", 
        "po.createdOrg", 
        "po.createdEmpID");
    rightSQL = "( (" + rightSQL + ") or po.createdEmpID=" + curUserId + ")";
    String viewSql = 
      "po.id,po.trainName,po.trainAddress,po.beginDate,po.endDate,ppo.trainName,po.trainActor";
    String fromSql = 
      " com.js.oa.hr.personnelmanager.po.TrainRecordPO po,com.js.oa.hr.personnelmanager.po.TrainClassPO ppo ";
    String whereSql = " where po.trainClass=ppo.id and po.domain=" + 
      domainId + where + " and " + rightSQL + 
      " order by po.id desc ";
    httpServletRequest.setAttribute("trainClass", 
        httpServletRequest
        .getParameter("trainClass"));
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
    httpServletRequest.setAttribute("trainRecordList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", 
        "flag,trainName,trainActor,trainClass,beginDate,endDate,pxrq,empId");
  }
  
  private boolean save(TrainRecordActionForm trainRecordActionForm, HttpServletRequest httpServletRequest) {
    boolean ret = false;
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String curUserId = session.getAttribute("userId").toString();
    String orgId = session.getAttribute("orgId").toString();
    TrainRecordPO po = new TrainRecordPO();
    po.setTrainName(trainRecordActionForm.getTrainName());
    po.setTrainClass(trainRecordActionForm.getTrainClass());
    po.setTrainOrganizer(trainRecordActionForm.getTrainOrganizer());
    po.setTrainOrganizerID(trainRecordActionForm.getTrainOrganizerID());
    po.setTrainContent(trainRecordActionForm.getTrainContent());
    po.setTrainAim(trainRecordActionForm.getTrainAim());
    po.setTrainAddress(trainRecordActionForm.getTrainAddress());
    po.setTrainActor(trainRecordActionForm.getTrainActor());
    po.setTrainEmpID(trainRecordActionForm.getTrainEmpID());
    po.setTrainEffect(trainRecordActionForm.getTrainEffect());
    po.setDomain(new Long(domainId));
    po.setCreatedEmpID(new Long(curUserId));
    po.setCreatedOrg(new Long(orgId));
    Date beginDate = new Date(httpServletRequest.getParameter("beginDate"));
    Date endDate = new Date(httpServletRequest.getParameter("endDate"));
    po.setBeginDate(beginDate);
    po.setEndDate(endDate);
    ret = (new TrainRecordBD()).save(po);
    return ret;
  }
  
  private void load(TrainRecordActionForm trainRecordActionForm, HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    TrainRecordPO po = (new TrainRecordBD()).load(new Long(httpServletRequest
          .getParameter("TrainRecordId")));
    trainRecordActionForm.setTrainActor(po.getTrainActor());
    trainRecordActionForm.setTrainAim(po.getTrainAim());
    trainRecordActionForm.setTrainClass(po.getTrainClass());
    trainRecordActionForm.setTrainAddress(po.getTrainAddress());
    trainRecordActionForm.setTrainContent(po.getTrainContent());
    trainRecordActionForm.setTrainEffect(po.getTrainEffect());
    trainRecordActionForm.setTrainEmpID(po.getTrainEmpID());
    trainRecordActionForm.setTrainName(po.getTrainName());
    trainRecordActionForm.setTrainOrganizer(po.getTrainOrganizer());
    trainRecordActionForm.setTrainOrganizerID(po.getTrainOrganizerID());
    trainRecordActionForm.setTrainResource(po.getTrainResource());
    httpServletRequest.setAttribute("trainClassList", (
        new TrainClassBD())
        .list(new Long(domainId)));
    httpServletRequest.setAttribute("beginDate", po.getBeginDate());
    httpServletRequest.setAttribute("endDate", po.getEndDate());
    httpServletRequest.setAttribute("trainClass", po.getTrainClass());
  }
  
  private boolean update(TrainRecordActionForm trainRecordActionForm, HttpServletRequest httpServletRequest) {
    TrainRecordPO po = new TrainRecordPO();
    po.setTrainName(trainRecordActionForm.getTrainName());
    po.setTrainClass(trainRecordActionForm.getTrainClass());
    po.setTrainOrganizer(trainRecordActionForm.getTrainOrganizer());
    po.setTrainOrganizerID(trainRecordActionForm.getTrainOrganizerID());
    po.setTrainContent(trainRecordActionForm.getTrainContent());
    po.setTrainAim(trainRecordActionForm.getTrainAim());
    po.setTrainAddress(trainRecordActionForm.getTrainAddress());
    po.setTrainActor(trainRecordActionForm.getTrainActor());
    po.setTrainEmpID(trainRecordActionForm.getTrainEmpID());
    po.setTrainEffect(trainRecordActionForm.getTrainEffect());
    Date beginDate = new Date(httpServletRequest.getParameter("beginDate"));
    Date endDate = new Date(httpServletRequest.getParameter("endDate"));
    po.setBeginDate(beginDate);
    po.setEndDate(endDate);
    return (new TrainRecordBD()).update(po, new Long(httpServletRequest
          .getParameter("TrainRecordId")));
  }
  
  private void delete(HttpServletRequest httpServletRequest) {
    if (httpServletRequest.getParameter("TrainRecordId") != null) {
      String ids = httpServletRequest.getParameter("TrainRecordId");
      httpServletRequest.setAttribute("deleteSuccess", (
          new TrainRecordBD()).delete(ids));
    } 
    search(httpServletRequest);
  }
  
  private void export(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String where = "";
    String beginDate = String.valueOf(httpServletRequest.getParameter("beginDate")) + 
      " 00:00:00";
    String endDate = String.valueOf(httpServletRequest.getParameter("endDate")) + 
      " 23:59:59";
    String trainName = (httpServletRequest.getParameter("trainName") == null) ? 
      "" : httpServletRequest.getParameter("trainName");
    String trainActor = (httpServletRequest.getParameter("trainActor") == null) ? 
      "" : httpServletRequest.getParameter("trainActor");
    if (trainName != null && !"".equals(trainName))
      where = String.valueOf(where) + " and po.trainName like '%" + trainName + "%' "; 
    if (trainActor != null && !"".equals(trainActor))
      where = String.valueOf(where) + " and po.trainActor like '%" + trainActor + "%' "; 
    if (httpServletRequest.getParameter("trainClass") != null && 
      Integer.parseInt(httpServletRequest.getParameter("trainClass")) != 0)
      where = String.valueOf(where) + " and po.trainClass=" + 
        httpServletRequest.getParameter("trainClass"); 
    String databaseType = 
      SystemCommon.getDatabaseType();
    if (httpServletRequest.getParameter("pxrq") != null && 
      !"".equals(httpServletRequest.getParameter("pxrq"))) {
      if (databaseType.indexOf("mysql") >= 0) {
        where = String.valueOf(where) + " and po.beginDate between '" + beginDate + 
          "' and '" + endDate + "'";
      } else {
        where = String.valueOf(where) + 
          " and po.beginDate between JSDB.FN_STRTODATE('" + 
          beginDate + "','L') and JSDB.FN_STRTODATE('" + 
          endDate + "','L')";
      } 
      httpServletRequest.setAttribute("pxrqchecked", "1");
      httpServletRequest.setAttribute("beginDate", beginDate);
      httpServletRequest.setAttribute("endDate", endDate);
    } 
    String curUserId = httpServletRequest.getSession(true).getAttribute(
        "userId")
      .toString();
    String orgID = httpServletRequest.getSession(true).getAttribute("orgId")
      .toString();
    ManagerService mbd = new ManagerService();
    String rightSQL = mbd.getRightFinalWhere(curUserId, orgID, "07*32*02", 
        "po.createdOrg", 
        "po.createdEmpID");
    rightSQL = "( (" + rightSQL + ") or po.createdEmpID=" + curUserId + ")";
    String viewSql = 
      "po.id,po.trainName,po.trainAddress,po.beginDate,po.endDate,ppo.trainName,po.trainActor";
    String fromSql = 
      " com.js.oa.hr.personnelmanager.po.TrainRecordPO po,com.js.oa.hr.personnelmanager.po.TrainClassPO ppo ";
    String whereSql = " where po.trainClass=ppo.id and po.domain=" + 
      domainId + where + " and " + rightSQL + 
      " order by po.id desc ";
    httpServletRequest.setAttribute("trainClass", 
        httpServletRequest
        .getParameter("trainClass"));
    exportList(httpServletRequest, viewSql, fromSql, whereSql);
  }
  
  private void exportList(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL) {
    int pageSize = 999999;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    httpServletRequest.setAttribute("trainRecordList", list);
  }
}
