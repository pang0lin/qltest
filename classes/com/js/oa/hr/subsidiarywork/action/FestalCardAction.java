package com.js.oa.hr.subsidiarywork.action;

import com.js.oa.hr.subsidiarywork.po.FestivalSetPO;
import com.js.oa.hr.subsidiarywork.service.FestalCardBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.page.Page;
import com.js.util.util.FillBean;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FestalCardAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    String curUserId = session.getAttribute("userId").toString();
    String curOrgId = session.getAttribute("orgId").toString();
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    FestalCardActionForm festalCardActionForm = (FestalCardActionForm)actionForm;
    FestalCardBD bd = new FestalCardBD();
    String action = request.getParameter("action");
    if ("delBatch".equals(action)) {
      bd.delBatch(request.getParameter("ids"));
      action = "list";
    } 
    if ("delAll".equals(action)) {
      ManagerService mbd = new ManagerService();
      String wherePara = mbd.getRightFinalWhere(curUserId, curOrgId, "07*10*02", "po.createdOrg", "po.createdEmp");
      bd.delAll(" po.createdEmp =" + curUserId + " or (" + wherePara + ")");
      action = "list";
      if (mbd.hasRight(curUserId, "07*10*02"))
        request.setAttribute("addRight", "1"); 
      if (mbd.hasRight((new StringBuilder(String.valueOf(curUserId))).toString(), "07*10*02"))
        request.setAttribute("modiRight", "1"); 
    } 
    if ("add".equals(action)) {
      int appointYear = Integer.parseInt(request.getParameter("appointYear"));
      if (appointYear == 0)
        festalCardActionForm.setFestiveYear(0); 
      FestivalSetPO po = (FestivalSetPO)FillBean.transformOneToOne(festalCardActionForm, FestivalSetPO.class);
      po.setDomainId(domainId);
      bd.add(po, curUserId, curOrgId);
      festalCardActionForm.setFestivalName("");
      festalCardActionForm.setFestivalWish("");
      festalCardActionForm.setWishCardName("");
      festalCardActionForm.setFestivalWishCard("");
      festalCardActionForm.setDone("done");
      return actionMapping.findForward("add");
    } 
    if ("load".equals(action)) {
      FestalCardActionForm form = (FestalCardActionForm)FillBean.transformOTO(bd.load(request.getParameter("editId")), FestalCardActionForm.class);
      form.setEditId(request.getParameter("editId"));
      festalCardActionForm.setAppointYear(form.getAppointYear());
      festalCardActionForm.setCalendarType(form.getCalendarType());
      festalCardActionForm.setEditId(form.getEditId());
      festalCardActionForm.setFestivalName(form.getFestivalName());
      festalCardActionForm.setFestivalRemind(form.getFestivalRemind());
      festalCardActionForm.setFestivalWish(form.getFestivalWish());
      festalCardActionForm.setFestivalWishCard(form.getFestivalWishCard());
      request.setAttribute("fileName", form.getFestivalWishCard());
      festalCardActionForm.setFestivalWishCard1(form.getFestivalWishCard());
      festalCardActionForm.setFestiveDay(form.getFestiveDay());
      festalCardActionForm.setFestiveMonth(form.getFestiveMonth());
      festalCardActionForm.setFestiveYear(form.getFestiveYear());
      festalCardActionForm.setWishCardName(form.getWishCardName());
      festalCardActionForm.setUseRange(form.getUseRange());
      festalCardActionForm.setUserRangeName(form.getUserRangeName());
      return actionMapping.findForward("modi");
    } 
    if ("update".equals(action)) {
      FestivalSetPO paraPO = new FestivalSetPO();
      paraPO.setAppointYear(festalCardActionForm.getAppointYear());
      paraPO.setCalendarType(festalCardActionForm.getCalendarType());
      paraPO.setFestivalName(festalCardActionForm.getFestivalName());
      paraPO.setFestivalRemind(festalCardActionForm.getFestivalRemind());
      paraPO.setFestivalWish(festalCardActionForm.getFestivalWish());
      paraPO.setFestivalWishCard(festalCardActionForm.getFestivalWishCard());
      paraPO.setFestiveDay(festalCardActionForm.getFestiveDay());
      paraPO.setFestiveMonth(festalCardActionForm.getFestiveMonth());
      paraPO.setFestiveYear(festalCardActionForm.getFestiveYear());
      paraPO.setUseRange(festalCardActionForm.getUseRange());
      paraPO.setUserRangeName(festalCardActionForm.getUserRangeName());
      paraPO.setWishCardName(festalCardActionForm.getWishCardName());
      bd.update(paraPO, festalCardActionForm.getEditId(), (
          new StringBuilder(String.valueOf(festalCardActionForm.getAppointYear()))).toString());
      return actionMapping.findForward("modi");
    } 
    if ("list".equals(action)) {
      ManagerService mbd = new ManagerService();
      String wherePara = mbd.getRightFinalWhere(curUserId, curOrgId, "07*10*02", "po.createdOrg", "po.createdEmp");
      list(request, " po.createdEmp =" + curUserId + " or (" + wherePara + ")");
    } 
    return actionMapping.findForward("list");
  }
  
  public void list(HttpServletRequest request, String wherePara) {
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String queryText = request.getParameter("queryText");
    queryText = (queryText == null) ? "" : queryText;
    StringBuffer where = new StringBuffer(" where ");
    if (!"".equals(queryText)) {
      where.append("po.festivalName like '%").append(queryText).append("%' and (").append(wherePara).append(")");
    } else {
      where.append(wherePara);
    } 
    Page page = new Page(
        " po.id,po.festivalName,po.festiveDay,po.festiveMonth, po.festiveYear,po.festivalRemind,po.festivalWish,po.festivalWishCard,po.calendarType,po.appointYear ", 
        
        " com.js.oa.hr.subsidiarywork.po.FestivalSetPO po ", 
        String.valueOf(where.toString()) + " and po.domainId=" + request.getSession(true).getAttribute("domainId") + " order by po.id desc");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    request.setAttribute("mylist", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action,queryText");
  }
}
