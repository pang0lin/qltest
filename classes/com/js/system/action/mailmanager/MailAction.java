package com.js.system.action.mailmanager;

import com.js.system.service.mailmanager.MailBD;
import com.js.system.vo.mailmanager.MailVO;
import com.js.util.page.Page;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class MailAction extends DispatchAction {
  public ActionForward goMailList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    list(request, "");
    return mapping.findForward("gomaillist");
  }
  
  public ActionForward goQueryMailList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    StringBuffer hql = new StringBuffer();
    String fromUser = request.getParameter("fromUser");
    if (fromUser != null && !fromUser.equals("")) {
      hql.append(" where po.fromUser like '%" + fromUser + "%'");
    } else {
      hql.append(" where 1=1 ");
    } 
    hql.append(" order by po.createTime asc");
    list(request, hql.toString());
    return mapping.findForward("gomaillist");
  }
  
  public ActionForward goProAddMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    return mapping.findForward("goproaddmail");
  }
  
  public ActionForward addMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    MailActionForm mailForm = (MailActionForm)form;
    MailBD mailDB = new MailBD();
    if (mailDB.getSingleMailByFromUser(mailForm) != null) {
      request.setAttribute("error", "该账号已存在！");
      return mapping.findForward("goproaddmail");
    } 
    MailVO mailVO = new MailVO();
    mailForm.setCreateTime(date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
    mailForm.setBakString("-1");
    BeanUtils.copyProperties(mailVO, mailForm);
    mailDB.add(mailVO);
    return goMailList(mapping, form, request, response);
  }
  
  public ActionForward goProModMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String mailId = request.getParameter("mailId");
    MailBD mailDB = new MailBD();
    MailActionForm mailForm = (MailActionForm)form;
    MailVO mailVO = mailDB.getSingleMail(mailId);
    BeanUtils.copyProperties(mailForm, mailVO);
    request.setAttribute("encryptionType", mailVO.getEncryptionType());
    return mapping.findForward("gopromodmail");
  }
  
  public ActionForward modMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    MailActionForm mailForm = (MailActionForm)form;
    MailBD mailDB = new MailBD();
    MailVO mailVOTemp = mailDB.getSingleMail(String.valueOf(mailForm.getMailId()));
    if (mailVOTemp.getFromUser().equals(mailForm.getFromUser())) {
      MailVO mailVO1 = new MailVO();
      mailForm.setCreateTime(date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
      BeanUtils.copyProperties(mailVO1, mailForm);
      mailDB.modMail(mailVO1);
      return goMailList(mapping, form, request, response);
    } 
    if (mailDB.getSingleMailByFromUser(mailForm) != null) {
      request.setAttribute("error", "该账号已存在！");
      return mapping.findForward("gopromodmail");
    } 
    MailVO mailVO = new MailVO();
    mailForm.setCreateTime(date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
    BeanUtils.copyProperties(mailVO, mailForm);
    mailDB.modMail(mailVO);
    return goMailList(mapping, form, request, response);
  }
  
  public ActionForward delMailById(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String mailId = request.getParameter("mailId");
    String[] mailIds = request.getParameterValues("batchDel");
    MailBD mailDB = new MailBD();
    mailDB.del(mailId, mailIds);
    return goMailList(mapping, form, request, response);
  }
  
  private void list(HttpServletRequest request, String wherePara) {
    Object object = (request.getSession(true).getAttribute("domainId") == null) ? "0" : request.getSession(true).getAttribute("domainId");
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    try {
      Page page = new Page("po.mailId,po.fromUser,po.bakString,po.bakString1,po.createTime,po.port,po.encryptionType", "com.js.system.vo.mailmanager.MailVO po ", wherePara);
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      request.setAttribute("mailList", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "method");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public static String date2String(Date date, String format) {
    if (date == null)
      return ""; 
    if (format == null || format.equals(""))
      format = "yyyy-MM-dd HH:mm:ss"; 
    String result = "";
    DateFormat dateFormat = new SimpleDateFormat(format);
    result = dateFormat.format(date);
    return result;
  }
}
