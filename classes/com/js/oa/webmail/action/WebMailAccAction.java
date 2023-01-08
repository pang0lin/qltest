package com.js.oa.webmail.action;

import com.js.oa.webmail.po.WebMailAcc;
import com.js.oa.webmail.service.WebMailAccBD;
import com.js.oa.webmail.util.WebMailAccManager;
import com.js.util.page.Page;
import com.js.util.util.EncryptSelf;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class WebMailAccAction extends DispatchAction {
  public ActionForward mailAccList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    Long userId = Long.valueOf(session.getAttribute("userId").toString());
    list(request, " where po.userId=" + userId);
    return mapping.findForward("mailAccList");
  }
  
  public ActionForward goCreateMailAcc(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    return mapping.findForward("createMailAcc");
  }
  
  public ActionForward setDefaultFlag(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    Long userId = Long.valueOf(session.getAttribute("userId").toString());
    String id = request.getParameter("id");
    WebMailAccBD wmab = new WebMailAccBD();
    List<WebMailAcc> listAcc = wmab.getMailAccListByUserId(userId, null);
    for (int i = 0; i < listAcc.size(); i++) {
      WebMailAcc web = listAcc.get(i);
      web.setDefaultFlag("0");
      wmab.ModMailAccInfo(web);
    } 
    WebMailAcc temp = wmab.getMailAccInfo(Long.valueOf(Long.parseLong(id)));
    temp.setDefaultFlag("1");
    wmab.ModMailAccInfo(temp);
    return mailAccList(mapping, form, request, response);
  }
  
  public ActionForward createMailAcc(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    Long userId = Long.valueOf(session.getAttribute("userId").toString());
    WebMailAccForm wmaf = (WebMailAccForm)form;
    wmaf.setMailAccPwd(EncryptSelf.selfEncoder(wmaf.getMailAccPwd()));
    WebMailAccBD wmab = new WebMailAccBD();
    List list = wmab.getMailAccList(userId, wmaf.getMailAccUser());
    String bakFlag = request.getParameter("bakFlag");
    String defaultFlag = request.getParameter("defaultFlag");
    wmaf.setBakFlag(bakFlag);
    wmaf.setDefaultFlag(defaultFlag);
    if (list != null && list.size() > 0) {
      request.setAttribute("error", "该账号已存在！");
      return mapping.findForward("createMailAcc");
    } 
    WebMailAcc wma = new WebMailAcc();
    if (wmaf.getDefaultFlag().equals("1")) {
      List<WebMailAcc> listAcc = wmab.getMailAccListByUserId(userId, null);
      for (int i = 0; i < listAcc.size(); i++) {
        WebMailAcc web = listAcc.get(i);
        web.setDefaultFlag("0");
        wmab.ModMailAccInfo(web);
      } 
    } 
    wmaf.setUserId(userId);
    wmaf.setDisName(wmaf.getMailAccUser());
    BeanUtils.copyProperties(wma, wmaf);
    wmab.saveMailAcc(wma);
    return mailAccList(mapping, form, request, response);
  }
  
  public ActionForward delMailAcc(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String ids = request.getParameter("ids");
    String id = request.getParameter("mailAccId");
    WebMailAccBD wmab = new WebMailAccBD();
    wmab.removeMailAcc(id, ids.split(","));
    return mailAccList(mapping, form, request, response);
  }
  
  public ActionForward goProModMailAcc(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String id = request.getParameter("id");
    WebMailAccBD wmab = new WebMailAccBD();
    WebMailAcc wma = wmab.getMailAccInfo(Long.valueOf(Long.parseLong(id)));
    WebMailAccForm wmaf = (WebMailAccForm)form;
    BeanUtils.copyProperties(wmaf, wma);
    request.setAttribute("smtpJMFS", wmaf.getSmtpJMFS());
    request.setAttribute("popJMFS", wmaf.getPopJMFS());
    return mapping.findForward("goProModMailAcc");
  }
  
  public ActionForward ModMailAcc(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    Long userId = Long.valueOf(session.getAttribute("userId").toString());
    WebMailAccForm wmaf = (WebMailAccForm)form;
    wmaf.setMailAccPwd(EncryptSelf.selfEncoder(wmaf.getMailAccPwd()));
    WebMailAccBD wmab = new WebMailAccBD();
    WebMailAcc temp = wmab.getMailAccInfo(wmaf.getMailAccId());
    List list = wmab.getMailAccList(userId, wmaf.getMailAccUser());
    if (wmaf.getMailAccUser().equals(temp.getMailAccUser())) {
      WebMailAcc wma = new WebMailAcc();
      if (wmaf.getDefaultFlag().equals("1")) {
        List<WebMailAcc> listAcc = wmab.getMailAccListByUserId(userId, null);
        for (int i = 0; i < listAcc.size(); i++) {
          WebMailAcc web = listAcc.get(i);
          web.setDefaultFlag("0");
          wmab.ModMailAccInfo(web);
        } 
      } 
      wmaf.setUserId(userId);
      wmaf.setDisName(wmaf.getMailAccUser());
      BeanUtils.copyProperties(wma, wmaf);
      wmab.ModMailAccInfo(wma);
    } else {
      if (list != null && list.size() > 0) {
        request.setAttribute("error", "该账号已存在！");
        return mapping.findForward("createMailAcc");
      } 
      WebMailAcc wma = new WebMailAcc();
      if (wmaf.getDefaultFlag().equals("1")) {
        List<WebMailAcc> listAcc = wmab.getMailAccListByUserId(userId, null);
        for (int i = 0; i < listAcc.size(); i++) {
          WebMailAcc web = listAcc.get(i);
          web.setDefaultFlag("0");
          wmab.ModMailAccInfo(web);
        } 
      } 
      wmaf.setUserId(userId);
      wmaf.setDisName(wmaf.getMailAccUser());
      BeanUtils.copyProperties(wma, wmaf);
      wmab.ModMailAccInfo(wma);
    } 
    return mailAccList(mapping, form, request, response);
  }
  
  private void list(HttpServletRequest request, String wherePara) {
    Object object = (request.getSession(true).getAttribute("domainId") == null) ? "0" : request.getSession(true).getAttribute("domainId");
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    try {
      Page page = new Page("po.mailAccId,po.defaultFlag,po.mailAccUser,po.pop,po.smtp,po.bakFlag,po.smtpPort,po.popPort,po.smtpJMFS,po.popJMFS ", "com.js.oa.webmail.po.WebMailAcc po ", wherePara);
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      request.setAttribute("mailAccList", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "method");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public ActionForward getMailAcc(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    Long userId = Long.valueOf(session.getAttribute("userId").toString());
    response.setContentType("text/xml;charset=GBK");
    PrintWriter out = response.getWriter();
    List list = WebMailAccManager.getInstance().getMyAccList(userId);
    boolean flag = false;
    if (list != null && list.size() > 0)
      flag = true; 
    StringBuffer sb = new StringBuffer(1024);
    sb.append("<?xml version=\"1.0\" encoding=\"GBK\" ?>\n");
    sb.append("<result>\n");
    sb.append("  <info>" + flag + "</info>\n");
    sb.append("</result>\n");
    out.print(sb.toString());
    out.close();
    return null;
  }
  
  public ActionForward goTestXSL(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    response.setContentType("text/xml;charset=GBK");
    PrintWriter out = response.getWriter();
    StringBuffer sb = new StringBuffer(1024);
    sb.append("<?xml version=\"1.0\" encoding=\"GBK\" ?>\n");
    sb.append("<result>\n");
    out.close();
    return null;
  }
  
  public static String getListResult() {
    StringBuffer sb = new StringBuffer(1024);
    sb.append("<?xml version=\"1.0\" encoding=\"GBK\" ?>\n");
    sb.append("<result>\n");
    sb.append("  <list>\n");
    for (int i = 0; i < 3; i++) {
      sb.append("    <row>\n");
      sb.append("      <link>\n");
      sb.append("       http://www.g.cn\n");
      sb.append("      </link>\n");
      sb.append("      <icon>\n");
      sb.append("       a.gif\n");
      sb.append("      </icon>\n");
      sb.append("    </row>\n");
    } 
    sb.append("  </list>\n");
    sb.append("</result>\n");
    return sb.toString();
  }
  
  public static String getTreeResult() {
    StringBuffer xml = new StringBuffer(1024);
    xml.append("<?xml version=\"1.0\" encoding=\"GBK\" ?>\n");
    xml.append("<result>\n");
    xml.append("  <sub>\n");
    for (int i = 0; i < 3; i++) {
      xml.append("    <leaf>\n");
      xml.append("      <key>");
      xml.append(i);
      xml.append("</key>\n");
      xml.append("      <name>");
      xml.append("菜单" + i);
      xml.append("</name>\n");
      xml.append("      <child>");
      xml.append(1);
      xml.append("</child>\n");
      xml.append("      <parent>");
      xml.append("45");
      xml.append("</parent>\n");
      xml.append("      <link>");
      xml.append("");
      xml.append("</link>\n");
      xml.append("      <image>");
      xml.append("");
      xml.append("</image>\n");
      xml.append("      <icon>");
      xml.append("");
      xml.append("</icon>\n");
      xml.append("    </leaf>\n");
    } 
    xml.append("  </sub>\n");
    xml.append("</result>\n");
    return xml.toString();
  }
}
