package com.js.oa.hr.subsidiarywork.action;

import com.js.oa.hr.subsidiarywork.po.BirthdayWishPO;
import com.js.oa.hr.subsidiarywork.service.BirthCardBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.page.Page;
import com.js.util.util.FillBean;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class BirthCardAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    String curUserId = request.getSession(true).getAttribute("userId").toString();
    String curOrgId = request.getSession(true).getAttribute("orgId").toString();
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    BirthCardActionForm birthCardActionForm = (BirthCardActionForm)actionForm;
    BirthCardBD bd = new BirthCardBD();
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
      if (mbd.hasRight(curUserId, "07*10*02"))
        request.setAttribute("modiRight", "1"); 
    } 
    if ("add".equals(action)) {
      Calendar cal = Calendar.getInstance();
      String[] backgroundSaveName = request.getParameterValues(
          "backgroundSaveName");
      String[] backgroundName = request.getParameterValues(
          "backgroundName");
      BirthdayWishPO po = (BirthdayWishPO)FillBean.transformOneToOne(birthCardActionForm, BirthdayWishPO.class);
      if (backgroundSaveName != null)
        po.setPath(String.valueOf(cal.get(1)) + "/" + "birthdayWish" + "/" + backgroundSaveName[0] + "," + backgroundName[0]); 
      po.setDomainId(domainId);
      bd.add(po, curUserId, curOrgId);
      birthCardActionForm.setEditId("");
      birthCardActionForm.setWishCard("");
      birthCardActionForm.setWishContent("");
      birthCardActionForm.setWishEmployees("");
      birthCardActionForm.setWishEmployeesName("");
      birthCardActionForm.setWishSign("");
      birthCardActionForm.setWishCardName("");
      birthCardActionForm.setDone("done");
      String[] fileName = request.getParameterValues(
          "infoPicName");
      String[] saveName = request.getParameterValues(
          "infoPicSaveName");
      return actionMapping.findForward("add");
    } 
    if ("load".equals(action)) {
      BirthCardActionForm form = (BirthCardActionForm)FillBean.transformOTO(bd.load(request.getParameter("editId")), BirthCardActionForm.class);
      form.setEditId(request.getParameter("editId"));
      birthCardActionForm.setEditId(form.getEditId());
      birthCardActionForm.setWishContent(form.getWishContent());
      birthCardActionForm.setWishCard(form.getWishCard());
      birthCardActionForm.setWishEmployees(form.getWishEmployees());
      birthCardActionForm.setWishEmployeesName(form.getWishEmployeesName());
      birthCardActionForm.setWishSign(form.getWishSign());
      birthCardActionForm.setWishCardName(form.getWishCardName());
      birthCardActionForm.setWishCard1(form.getWishCard());
      request.setAttribute("fileName", form.getWishCard());
      BirthdayWishPO load = bd.load(request.getParameter("editId"));
      String path = load.getPath();
      if (path != null && !"".equals(path)) {
        String backgroundName = load.getPath().substring(load.getPath().lastIndexOf(",") + 1, load.getPath().length());
        String backgroundSaveName = load.getPath().substring(load.getPath().lastIndexOf("/") + 1, load.getPath().lastIndexOf(","));
        request.setAttribute("backgroundName", backgroundName);
        request.setAttribute("backgroundSaveName", backgroundSaveName);
      } 
      return actionMapping.findForward("modi");
    } 
    if ("update".equals(action)) {
      Calendar cal = Calendar.getInstance();
      String[] backgroundSaveName = request.getParameterValues(
          "backgroundSaveName");
      String[] backgroundName = request.getParameterValues(
          "backgroundName");
      String path = "";
      if ("1".equals(request.getParameter("isApp"))) {
        BirthdayWishPO load = bd.load(birthCardActionForm.getEditId());
        path = load.getPath();
      } 
      if (backgroundSaveName != null)
        path = String.valueOf(cal.get(1)) + "/" + "birthdayWish" + "/" + backgroundSaveName[0] + "," + backgroundName[0]; 
      bd.update(birthCardActionForm.getEditId(), 
          birthCardActionForm.getWishCard(), 
          birthCardActionForm.getWishCardName(), 
          birthCardActionForm.getWishEmployees(), 
          birthCardActionForm.getWishEmployeesName(), 
          birthCardActionForm.getWishSign(), 
          birthCardActionForm.getWishContent(), path);
      if (backgroundName != null) {
        request.setAttribute("backName", backgroundName[0]);
      } else {
        request.setAttribute("backName", "");
      } 
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
    Page page = new Page(
        " po.id,po.wishContent,po.wishEmployeesName ", 
        " com.js.oa.hr.subsidiarywork.po.BirthdayWishPO po ", 
        " where " + wherePara + " and po.domainId=" + request.getSession(true).getAttribute("domainId") + " order by po.id desc");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    request.setAttribute("mylist", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action");
  }
  
  public void selectCard(HttpServletRequest request, String wherePara) throws Exception {
    int pageSize = 40;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String cardName = request.getParameter("cardName");
    cardName = (cardName == null) ? "" : cardName;
    String cardClass = request.getParameter("myCardClass");
    cardClass = (cardClass == null) ? "" : cardClass;
    StringBuffer where = new StringBuffer(" where 1=1 and (");
    where.append(wherePara);
    where.append(")");
    where.append(" and (").append(" po.cardName like '%").append(
        cardName).append("%'");
    if (!"".equals(cardClass) && !"none".equals(cardClass))
      where.append(" and poo.id=").append(cardClass); 
    where.append(")");
    where.append(" and po.domainId=" + request.getSession(true).getAttribute("domainId") + " order by po.id desc ");
    Page page = new Page(
        " po.id,po.cardAuthor,po.cardName,po.cardFileName,po.cardSaveName,poo.id,poo.cardClassName  ", 
        " com.js.oa.hr.subsidiarywork.po.WishCardPO po left join po.cardClass poo ", 
        where.toString());
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    request.setAttribute("mylist", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action,cardName,myCardClass");
  }
}
