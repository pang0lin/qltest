package com.js.oa.hr.subsidiarywork.action;

import com.js.oa.hr.subsidiarywork.po.NetSurveyPO;
import com.js.oa.hr.subsidiarywork.service.LookIntoBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.page.Page;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LookIntoAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) {
    HttpSession session = request.getSession(true);
    Long curUserId = new Long(session.getAttribute("userId").toString());
    Long curOrgId = new Long(session.getAttribute("orgId").toString());
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    LookIntoActionForm lookIntoActionForm = (LookIntoActionForm)actionForm;
    LookIntoBD bd = new LookIntoBD();
    String action = request.getParameter("action");
    if ("delBatch".equals(action)) {
      bd.delBatch(request.getParameter("ids"));
      action = "list";
    } 
    if ("delAll".equals(action)) {
      String orgIdString = request.getSession(true).getAttribute(
          "orgIdString").toString();
      ManagerService mdb = new ManagerService();
      String wherePara = mdb.getRightFinalWhere((String)curUserId, 
          (String)curOrgId, "07*08*02", "po.createdOrg", "po.createdEmp");
      bd.delAll(" po.createdEmp=" + curUserId + " or (" + wherePara + ")");
      if (mdb.hasRight((String)curUserId, "07*08*02"))
        request.setAttribute("addRight", "1"); 
      if (mdb.hasRight((String)curUserId, "07*08*02"))
        request.setAttribute("modiRight", "1"); 
      action = "list";
    } 
    if ("add".equals(action)) {
      Object[] obj = new Object[9];
      obj[0] = new Date(lookIntoActionForm.getSurveyBeginTime());
      obj[1] = new Date(lookIntoActionForm.getSurveyEndTime());
      obj[2] = lookIntoActionForm.getSurveyContent();
      obj[3] = lookIntoActionForm.getSurveyType();
      obj[4] = lookIntoActionForm.getSurveyRange();
      obj[5] = lookIntoActionForm.getSurveyStatus();
      obj[6] = lookIntoActionForm.getSurveyRangeName();
      obj[7] = lookIntoActionForm.getNewItems();
      obj[8] = domainId;
      bd.add(obj, (String)curUserId, (String)curOrgId);
      lookIntoActionForm.setNewItems(null);
      lookIntoActionForm.setSurveyContent("");
      lookIntoActionForm.setSurveyRange("");
      lookIntoActionForm.setSurveyType("");
      lookIntoActionForm.setSurveyStatus("");
      lookIntoActionForm.setSurveyRangeName("");
      return actionMapping.findForward("add");
    } 
    if ("load".equals(action)) {
      LookIntoActionForm form = new LookIntoActionForm();
      Map result = bd.load(request.getParameter("editId"));
      NetSurveyPO po = (NetSurveyPO)result.get("netSurveyPO");
      form.setSurveyBeginTime(String.valueOf(po.getSurveyBeginTime()));
      form.setSurveyEndTime(String.valueOf(po.getSurveyEndTime()));
      form.setSurveyContent(po.getSurveyContent());
      form.setSurveyRange(po.getSurveyRange());
      form.setSurveyRangeName(po.getSurveyRangeName());
      form.setSurveyStatus(String.valueOf(po.getSurveyStatus()));
      form.setSurveyType(String.valueOf(po.getSurveyType()));
      form.setItems((String[][])result.get("items"));
      form.setEditId(request.getParameter("editId"));
      lookIntoActionForm.setEditId(form.getEditId());
      lookIntoActionForm.setItems(form.getItems());
      request.setAttribute("mylist", form.getItems());
      request.setAttribute("surveyBeginTime", form.getSurveyBeginTime());
      request.setAttribute("surveyEndTime", form.getSurveyEndTime());
      lookIntoActionForm.setSurveyContent(form.getSurveyContent());
      lookIntoActionForm.setSurveyRange(form.getSurveyRange());
      lookIntoActionForm.setSurveyRangeName(form.getSurveyRangeName());
      lookIntoActionForm.setSurveyStatus(form.getSurveyStatus());
      lookIntoActionForm.setSurveyType(form.getSurveyType());
      return actionMapping.findForward("modi");
    } 
    if ("update".equals(action)) {
      bd.update(lookIntoActionForm.getSurveyBeginTime(), 
          lookIntoActionForm.getSurveyEndTime(), 
          lookIntoActionForm.getSurveyContent(), 
          lookIntoActionForm.getSurveyRange(), 
          lookIntoActionForm.getSurveyRangeName(), 
          lookIntoActionForm.getSurveyStatus(), 
          lookIntoActionForm.getSurveyType(), 
          lookIntoActionForm.getUpdateItems(), 
          lookIntoActionForm.getUpdateItemsIds(), 
          lookIntoActionForm.getNewItems(), 
          lookIntoActionForm.getDelItems(), 
          request.getParameter("editId"));
      return actionMapping.findForward("modi");
    } 
    if ("list".equals(action)) {
      ManagerService mdb = new ManagerService();
      String wherePara = mdb.getRightFinalWhere((String)curUserId, 
          (String)curOrgId, "07*08*02", "po.createdOrg", "po.createdEmp");
      list(request, 
          " po.createdEmp=" + curUserId + " or (" + wherePara + 
          ") or po.surveyRange like '%*" + 
          curOrgId + "*%'");
      if (mdb.hasRight((String)curUserId, "07*08*02"))
        request.setAttribute("addRight", "1"); 
      if (mdb.hasRight((String)curUserId, "07*08*02"))
        request.setAttribute("modiRight", "1"); 
    } 
    if ("voteAdd".equals(action)) {
      String surveyId = request.getParameter("surveyId");
      String itemIds = request.getParameter("itemsIds");
      String retString = bd.voteAdd(surveyId, itemIds, (String)curUserId, 
          domainId);
      request.setAttribute("voteAdd", retString);
      return actionMapping.findForward("ifmvote");
    } 
    if ("voteList".equals(action))
      action = "toVoteList"; 
    if ("toVoteList".equals(action)) {
      String surveyId = request.getParameter("surveyId");
      Map result = bd.load(surveyId);
      String itemIds = "";
      String[][] item = (String[][])result.get("items");
      request.setAttribute("item", item);
      for (int i = 0; i < item.length; i++)
        itemIds = String.valueOf(itemIds) + item[i][1] + ","; 
      itemIds = String.valueOf(itemIds);
      List list = bd.voteList(surveyId, itemIds);
      request.setAttribute("voteList", list);
      return actionMapping.findForward("ifmlist");
    } 
    if ("viewbrowserUser".equals(action)) {
      int pageSize = 15;
      int offset = 0;
      if (request.getParameter("pager.offset") != null)
        offset = Integer.parseInt(request.getParameter(
              "pager.offset")); 
      int currentPage = offset / pageSize + 1;
      String surveyId = request.getParameter("surveyId");
      String searchName = (request.getParameter("searchName") == null) ? "" : 
        request.getParameter("searchName");
      String read = (request.getParameter("read") == null) ? "1" : 
        request.getParameter("read");
      List list = null;
      try {
        list = bd.getBrowser(surveyId, searchName, read, pageSize, 
            currentPage, domainId);
      } catch (Exception ex1) {
        ex1.printStackTrace();
      } 
      String recordCount = "0";
      try {
        recordCount = String.valueOf(bd
            .getBrowserCount(surveyId, 
              searchName, read, domainId));
      } catch (Exception ex2) {
        ex2.printStackTrace();
      } 
      request.setAttribute("read", read);
      request.setAttribute("browserList", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", 
          String.valueOf(pageSize));
      request.setAttribute("pageParameters", 
          "action,surveyId,read,searchName");
      return actionMapping.findForward("viewbrowserUser");
    } 
    if ("viewItemBrowserUser".equals(action)) {
      int pageSize = 15;
      int offset = 0;
      if (request.getParameter("pager.offset") != null)
        offset = Integer.parseInt(request.getParameter(
              "pager.offset")); 
      int currentPage = offset / pageSize + 1;
      String items = request.getParameter("items");
      String searchName = (request.getParameter("searchName") == null) ? "" : 
        request.getParameter("searchName");
      List list = null;
      try {
        list = bd.getBrowser(items, searchName, pageSize, 
            currentPage);
      } catch (Exception ex1) {
        ex1.printStackTrace();
      } 
      String recordCount = "0";
      try {
        recordCount = String.valueOf(bd
            .getBrowserCount(items, 
              searchName));
      } catch (Exception ex2) {
        ex2.printStackTrace();
      } 
      request.setAttribute("browserList", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", 
          String.valueOf(pageSize));
      request.setAttribute("pageParameters", 
          "action,items,read,searchName");
      return actionMapping.findForward("viewItemBowserUser");
    } 
    return actionMapping.findForward("list");
  }
  
  public void list(HttpServletRequest request, String wherePara) {
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    StringBuffer where = new StringBuffer("where 1=1 and (");
    HttpSession session = request.getSession(true);
    Long curUserId = new Long(session.getAttribute("userId").toString());
    Long curOrgId = new Long(session.getAttribute("orgId").toString());
    ManagerService mdb = new ManagerService();
    String modiWherePara = mdb.getRightFinalWhere((String)curUserId, 
        (String)curOrgId, "07*08*02", "po.createdOrg", "po.createdEmp");
    StringBuffer modiWhere = new StringBuffer(
        "where 1=1 and ( po.createdEmp=" + curUserId + 
        " or (" + modiWherePara + ")");
    where.append(wherePara).append(") and po.domainId=" + 
        request
        .getSession(true).getAttribute("domainId") + 
        " ");
    modiWhere.append(") and po.domainId=" + 
        request
        .getSession(true).getAttribute("domainId") + 
        " ");
    String surveyContent = request.getParameter("surveyContent");
    if (surveyContent != null && !"".equals(surveyContent)) {
      where.append(" and po.surveyContent like '%").append(surveyContent)
        .append("%'");
      modiWhere.append(" and po.surveyContent like '%").append(
          surveyContent)
        .append("%'");
    } 
    where.append(" order by po.id desc ");
    Page page = new Page(
        " po.id,po.surveyContent,po.surveyRangeName,po.surveyStatus ", 
        " com.js.oa.hr.subsidiarywork.po.NetSurveyPO po ", 
        where.toString());
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    request.setAttribute("mylist", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action,surveyContent");
    page = new Page(" po.id ", 
        " com.js.oa.hr.subsidiarywork.po.NetSurveyPO po ", 
        modiWhere.toString());
    page.setPageSize(999999);
    page.setcurrentPage(1);
    list = page.getResultList();
    String canModiID = ",";
    if (list.size() > 0)
      for (int i = 0; i < list.size(); i++)
        canModiID = String.valueOf(canModiID) + String.valueOf(list.get(i)) + ",";  
    request.setAttribute("canModiID", canModiID);
  }
}
