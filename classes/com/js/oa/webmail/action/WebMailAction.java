package com.js.oa.webmail.action;

import com.js.oa.webmail.po.Attach;
import com.js.oa.webmail.po.WebMail;
import com.js.oa.webmail.service.AffixBD;
import com.js.oa.webmail.service.WebMailBD;
import com.js.oa.webmail.util.AffixManager;
import com.js.oa.webmail.util.IOManager;
import com.js.oa.webmail.util.MailUtil;
import com.js.oa.webmail.util.WebMailAccManager;
import com.js.oa.webmail.util.WebMailShowImg;
import com.js.oa.webmail.util.str;
import com.js.system.util.StaticParam;
import com.js.util.mail.Mail;
import com.js.util.mail.MailSender;
import com.js.util.page.Page;
import com.js.util.util.BASE64;
import com.js.util.util.UploadFile;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class WebMailAction extends DispatchAction {
  public ActionForward searchWebMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    Long userId = Long.valueOf(Long.parseLong(session.getAttribute("userId").toString()));
    String subject = request.getParameter("subject");
    String startTime = request.getParameter("start_date");
    String endTime = request.getParameter("end_date");
    if (startTime != null && !startTime.equals("null")) {
      String var1 = startTime.substring(startTime.indexOf("/") + 1, startTime.lastIndexOf("/"));
      String var2 = startTime.substring(startTime.lastIndexOf("/") + 1, startTime.length());
      if (var1.length() == 1)
        var1 = "0" + var1; 
      if (var2.length() == 1)
        var2 = "0" + var2; 
      startTime = String.valueOf(startTime.substring(0, 4)) + "-" + var1 + "-" + var2;
    } 
    if (endTime != null && !endTime.equals("null")) {
      String var1 = endTime.substring(endTime.indexOf("/") + 1, endTime.lastIndexOf("/"));
      String var2 = endTime.substring(endTime.lastIndexOf("/") + 1, endTime.length());
      if (var1.length() == 1)
        var1 = "0" + var1; 
      if (var2.length() == 1)
        var2 = "0" + var2; 
      endTime = String.valueOf(endTime.substring(0, 4)) + "-" + var1 + "-" + var2;
    } 
    String boxType = request.getParameter("boxId");
    String checkbox = request.getParameter("checkbox");
    StringBuffer hql = new StringBuffer();
    hql.append(" where po.userId =" + userId);
    if (boxType != null && !boxType.equals("")) {
      hql.append(" and  po.mailBox ='" + boxType + "'");
    } else {
      hql.append(" and  po.mailBox ='null'");
    } 
    if (subject != null && !subject.equals("null") && !subject.equals(""))
      hql.append(" and  po.subject like '%" + subject + "%'"); 
    if (checkbox != null && checkbox.equals("1")) {
      if (startTime != null && !startTime.equals(""))
        hql.append(" and po.sendDate between '" + startTime + " 00:00:00' "); 
      if (endTime != null && !endTime.equals(""))
        hql.append("  and  '" + endTime + " 23:59:59'"); 
    } 
    hql.append(" order by po.sendDate desc");
    request.setAttribute("boxType", (boxType == null || boxType.equals("")) ? "" : boxType);
    request.setAttribute("subject", (subject == null || subject.equals("")) ? "" : subject);
    request.setAttribute("checkbox", (checkbox == null) ? "" : checkbox);
    request.setAttribute("startTime", (startTime == null || startTime.equals("")) ? "" : startTime);
    request.setAttribute("endTime", (endTime == null || endTime.equals("")) ? "" : endTime);
    list(request, hql.toString());
    return mapping.findForward("searchWebMail");
  }
  
  public ActionForward goSearchWebMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    request.setAttribute("webMailListInDB", Integer.valueOf(0));
    request.setAttribute("recordCount", "0");
    request.setAttribute("maxPageItems", "0");
    request.setAttribute("pageParameters", "method");
    return mapping.findForward("searchWebMail");
  }
  
  public ActionForward zipMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    ServletContext application = request.getSession().getServletContext();
    Long userId = Long.valueOf(Long.parseLong(session.getAttribute("userId").toString()));
    String idss = request.getParameter("ids");
    WebMailBD web = new WebMailBD();
    List list = web.getWebMailList(idss.substring(0, idss.lastIndexOf(",")).split(","));
    Calendar cal = Calendar.getInstance();
    String year = String.valueOf(cal.get(1));
    String tempPath = String.valueOf(application.getRealPath("/upload")) + "/" + year + "/webmail/~attachment/atttemp/";
    String attachPath = String.valueOf(application.getRealPath("/upload")) + "/" + year + "/webmail/";
    String zipFileURL = MailUtil.zipMail(list, tempPath.replaceAll("\\\\", "/"), userId, attachPath);
    request.setAttribute("zipFileURL", zipFileURL);
    request.setAttribute("zipFileFlag", "1");
    String filename = zipFileURL.substring(zipFileURL.lastIndexOf("/") + 1);
    String moduleCode = request.getParameter("moduleCode");
    StringBuffer xml = new StringBuffer(1024);
    response.setContentType("text/xml;charset=GBK");
    PrintWriter out = response.getWriter();
    xml.append("<?xml version=\"1.0\" encoding=\"GBK\" ?>\n");
    xml.append("<result>\n");
    xml.append("  <info>" + BASE64.BASE64EncoderNoBR("FileName=" + filename + "&name=" + filename + "&path=webmail/~attachment/atttemp&moduleCode=" + moduleCode) + "</info>\n");
    xml.append("</result>\n");
    out.print(xml.toString());
    out.close();
    return null;
  }
  
  public ActionForward goWriteMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    Long userId = Long.valueOf(session.getAttribute("userId").toString());
    request.setAttribute("folderList", WebMailAccManager.getInstance().getMyAccList(userId));
    String mailUrl = (request.getParameter("mailUrlw") == null) ? "" : request.getParameter("mailUrlw");
    request.setAttribute("mailUrl", mailUrl);
    return mapping.findForward("goWriteMail");
  }
  
  public ActionForward save2Bak(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setAttribute("isSubmit", "true");
    HttpSession session = request.getSession(true);
    Long userId = Long.valueOf(Long.parseLong(session.getAttribute("userId").toString()));
    String infoId = request.getParameter("mailInfoId");
    String to = request.getParameter("to");
    String subject = request.getParameter("subject");
    String cc = request.getParameter("cc");
    String bc = request.getParameter("bc");
    String con = request.getParameter("content1");
    String[] saveName = request.getParameterValues("webmailSaveName");
    String[] fileName = request.getParameterValues("webmailFileName");
    int mailSize = getAffixSize(saveName);
    save2Bak(userId, saveName, fileName, infoId, to, subject, cc, bc, con, mailSize, null, null);
    return getPopMailBakList(mapping, form, request, response);
  }
  
  public ActionForward showWaiting(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String mailAccId = request.getParameter("mailAccId");
    String idss = request.getParameter("ids");
    if (mailAccId != null && !mailAccId.equals("")) {
      request.setAttribute("mailAccId", mailAccId);
      request.setAttribute("flag", "0");
    } 
    if (idss != null && !idss.equals("")) {
      request.setAttribute("idss", idss);
      request.setAttribute("zipFileFlag", "0");
    } 
    return mapping.findForward("showWaiting");
  }
  
  public ActionForward crawlEmailFromPOP(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    Long userId = Long.valueOf(Long.parseLong(session.getAttribute("userId").toString()));
    String mailAccId = request.getParameter("mailAccId");
    Calendar cal = Calendar.getInstance();
    String year = String.valueOf(cal.get(1));
    String tempPath = String.valueOf(request.getRealPath("/upload/")) + "/" + year + "/webmail/";
    tempPath = tempPath.replaceAll("\\\\", "/");
    if (mailAccId != null && !mailAccId.equals("")) {
      Map popMailMap = MailUtil.CrawlEmailFromPOP(Long.valueOf(Long.parseLong(mailAccId)), userId, tempPath);
      if (popMailMap.get("ERORMESSAGE") == null || popMailMap.get("ERORMESSAGE").equals("")) {
        if (popMailMap != null && popMailMap.size() > 2) {
          List mailList = (List)popMailMap.get("MailList");
          List AffixList = (List)popMailMap.get("AffixList");
          List uuidList = (List)popMailMap.get("uuidList");
          WebMailBD web = new WebMailBD();
          web.createAllPOPMail(mailList, AffixList);
          web.createAllUUID(uuidList);
          request.setAttribute("mailList", Integer.valueOf((mailList == null) ? 0 : mailList.size()));
          request.setAttribute("mailNoneList", popMailMap.get("jumpList"));
          request.setAttribute("flag", "1");
          request.setAttribute("countList", popMailMap.get("countList"));
          request.setAttribute("allMailList", popMailMap.get("allMailList"));
          return mapping.findForward("showWaiting");
        } 
        request.setAttribute("flag", "1");
        request.setAttribute("mailList", Integer.valueOf(0));
        request.setAttribute("mailNoneList", Integer.valueOf(0));
        request.setAttribute("countList", Integer.valueOf(0));
        request.setAttribute("allMailList", Integer.valueOf(0));
        return mapping.findForward("showWaiting");
      } 
      request.setAttribute("errorMeg", popMailMap.get("ERORMESSAGE"));
      request.setAttribute("flag", "flag");
      return mapping.findForward("showWaiting");
    } 
    return null;
  }
  
  public ActionForward getPopMailDBList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    Long userId = Long.valueOf(Long.parseLong(session.getAttribute("userId").toString()));
    String order = request.getParameter("order");
    String desc = request.getParameter("desc");
    StringBuffer hql = new StringBuffer();
    hql.append(" where po.userId =" + userId);
    hql.append(" and  po.mailBox ='0'");
    if ((order == null || order.equals("")) && (desc == null || desc.equals("")))
      hql.append(" order by po.sendDate desc"); 
    if (order != null && order.equals("1") && desc != null && desc.equals("1"))
      hql.append(" order by po.mailState desc"); 
    if (order != null && order.equals("1") && desc != null && desc.equals("2"))
      hql.append(" order by po.mailState asc"); 
    if (order != null && order.equals("2") && desc != null && desc.equals("1"))
      hql.append(" order by po.sendDate desc"); 
    if (order != null && order.equals("2") && desc != null && desc.equals("2"))
      hql.append(" order by po.sendDate asc"); 
    request.setAttribute("folderList", WebMailAccManager.getInstance().getMyAccList(userId));
    request.setAttribute("order", (order == null || order.equals("")) ? "" : order);
    request.setAttribute("desc", (desc == null || desc.equals("")) ? "" : desc);
    list(request, hql.toString());
    return mapping.findForward("gopoplistindb");
  }
  
  public ActionForward getPopMailSendList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    Long userId = Long.valueOf(Long.parseLong(session.getAttribute("userId").toString()));
    StringBuffer hql = new StringBuffer();
    hql.append(" where po.userId =" + userId);
    hql.append(" and  po.mailBox ='1'");
    hql.append(" order by po.sendDate desc");
    list(request, hql.toString());
    return mapping.findForward("getPopMailSendList");
  }
  
  public ActionForward getPopMailDropList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    Long userId = Long.valueOf(Long.parseLong(session.getAttribute("userId").toString()));
    StringBuffer hql = new StringBuffer();
    hql.append(" where po.userId =" + userId);
    hql.append(" and  po.mailBox ='2'");
    hql.append(" order by po.sendDate desc");
    list(request, hql.toString());
    return mapping.findForward("getPopMailDropList");
  }
  
  public ActionForward getPopMailBakList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    Long userId = Long.valueOf(Long.parseLong(session.getAttribute("userId").toString()));
    StringBuffer hql = new StringBuffer();
    hql.append(" where po.userId =" + userId);
    hql.append(" and  po.mailBox ='3'");
    hql.append(" order by po.sendDate desc");
    list(request, hql.toString());
    return mapping.findForward("getPopMailBakList");
  }
  
  public ActionForward getPopMailInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    Long userId = Long.valueOf(Long.parseLong(session.getAttribute("userId").toString()));
    String mailId = request.getParameter("mailId");
    String type = request.getParameter("type");
    WebMailBD web = new WebMailBD();
    Map map = web.getSingleWebMailInfo(mailId);
    WebMail wm = (WebMail)map.get("WebMail");
    if (wm != null && "0".equals(wm.getMailState())) {
      wm.setMailState("1");
      web.modMailState(wm);
    } 
    String conString = wm.getContent().toString();
    if (conString.contains("/upload/webmail"))
      conString = conString.replace("/upload/webmail", "/upload/0000/webmail"); 
    String path = String.valueOf(request.getRealPath("/")) + wm.getContent();
    path = path.replaceAll("\\\\", "/");
    if (IOManager.isFile(path))
      wm.setContent(wm.getContentByIOStr(path)); 
    request.setAttribute("WebMail", wm);
    request.setAttribute("folderList", WebMailAccManager.getInstance().getMyAccList(userId));
    request.setAttribute("affixList", IOManager.getAffixString((List)map.get("AffixList"), AffixManager.getInstance().getBakAttahListById(Long.valueOf(Long.parseLong(mailId)))));
    if (type != null && type.equals("bak")) {
      request.setAttribute("bakFlag", type);
      return mapping.findForward("getPopMailInfoWrite");
    } 
    return mapping.findForward("getPopMailInfo");
  }
  
  public ActionForward moveMail2_(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String idss = request.getParameter("ids");
    String boxId = request.getParameter("boxId");
    String mailId = request.getParameter("mailInfoId");
    WebMailBD web = new WebMailBD();
    List<WebMail> list = null;
    if (idss != null && !idss.equals(""))
      list = web.getWebMailList(idss.substring(0, idss.lastIndexOf(",")).split(",")); 
    if (mailId != null && !mailId.equals(""))
      list = web.getWebMailList(mailId.split(",")); 
    List<WebMail> mailList = null;
    if (list != null && list.size() > 0) {
      mailList = new ArrayList();
      for (int i = 0; i < list.size(); i++) {
        WebMail wm = list.get(i);
        wm.setMailBox(boxId);
        mailList.add(wm);
      } 
    } 
    if (mailList != null && mailList.size() > 0)
      web.moveMail2_(mailList); 
    mailList = null;
    if (boxId != null && boxId.equals("0"))
      return getPopMailDBList(mapping, form, request, response); 
    if (boxId != null && boxId.equals("1"))
      return getPopMailSendList(mapping, form, request, response); 
    if (boxId != null && boxId.equals("2"))
      return getPopMailDropList(mapping, form, request, response); 
    return getPopMailBakList(mapping, form, request, response);
  }
  
  public ActionForward moveMail2F(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String idss = request.getParameter("ids");
    String mailId = request.getParameter("mailInfoId");
    WebMailBD web = new WebMailBD();
    List<WebMail> list = null;
    if (idss != null && !idss.equals(""))
      list = web.getWebMailList(idss.substring(0, idss.lastIndexOf(",")).split(",")); 
    if (mailId != null && !mailId.equals(""))
      list = web.getWebMailList(mailId.split(",")); 
    List<WebMail> mailList = null;
    if (list != null && list.size() > 0) {
      mailList = new ArrayList();
      for (int i = 0; i < list.size(); i++) {
        WebMail wm = list.get(i);
        wm.setMailBox("2");
        mailList.add(wm);
      } 
    } 
    if (mailList != null && mailList.size() > 0)
      web.moveMail2_(mailList); 
    mailList = null;
    return getPopMailDropList(mapping, form, request, response);
  }
  
  public ActionForward delAll(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    WebMailBD web = new WebMailBD();
    defFJ(web, "2", null, request);
    web.delAll();
    return getPopMailDropList(mapping, form, request, response);
  }
  
  public void defFJ(WebMailBD web, String boxId, String maiIds, HttpServletRequest request) {
    List<WebMail> list = web.getWebMailListByBoxId(boxId, maiIds);
    if (list != null && list.size() > 0)
      for (int i = 0; i < list.size(); i++) {
        WebMail wm = list.get(i);
        if (wm.getMailId() != null && !wm.equals("")) {
          List affLst = AffixManager.getInstance().getAffixArray(wm.getMailId());
          IOManager.delFile(affLst);
        } 
        IOManager.delFile(AffixManager.getInstance().getAttachArray(wm.getMailInfoId(), request));
        if (wm.getContent() != null)
          IOManager.delFile(wm.getContent().split(",")); 
      }  
  }
  
  public ActionForward delMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String idss = request.getParameter("ids");
    String boxId = request.getParameter("boxId");
    if (idss != null && !idss.equals("")) {
      WebMailBD web = new WebMailBD();
      defFJ(web, boxId, idss.substring(0, idss.lastIndexOf(",")), request);
      web.delMail(idss.substring(0, idss.lastIndexOf(",")).split(","));
    } 
    if (boxId != null && boxId.equals("0"))
      return getPopMailDBList(mapping, form, request, response); 
    if (boxId != null && boxId.equals("1"))
      return getPopMailSendList(mapping, form, request, response); 
    if (boxId != null && boxId.equals("2"))
      return getPopMailDropList(mapping, form, request, response); 
    return getPopMailBakList(mapping, form, request, response);
  }
  
  public ActionForward rePopMailInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    Long userId = Long.valueOf(Long.parseLong(session.getAttribute("userId").toString()));
    String frm = request.getParameter("frm");
    String subject = request.getParameter("subject");
    String mailId = request.getParameter("mailInfoId");
    WebMailForm wm = new WebMailForm();
    WebMailBD web = new WebMailBD();
    Map map = web.getSingleWebMailInfo(mailId);
    WebMail wmTemp = (WebMail)map.get("WebMail");
    String content = wmTemp.getContent();
    String path = String.valueOf(request.getRealPath("/")) + content;
    path = path.replaceAll("\\\\", "/");
    if (IOManager.isFile(path)) {
      wm.setContent(wmTemp.getContentByIOStr(path));
    } else {
      wm.setContent(content);
    } 
    if (frm.equals("") || frm == null) {
      wm.setFrm("");
    } else {
      wm.setFrm(String.valueOf(frm) + ",");
    } 
    wm.setSubject("Re:" + subject);
    request.setAttribute("WebMailForm", wm);
    request.setAttribute("folderList", WebMailAccManager.getInstance().getMyAccList(userId));
    return mapping.findForward("rePopMailInfo");
  }
  
  public ActionForward rePopMailInfoAll(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    Long userId = Long.valueOf(Long.parseLong(session.getAttribute("userId").toString()));
    String mailId = request.getParameter("mailInfoId");
    String frm = request.getParameter("frm");
    String subject = request.getParameter("subject");
    String cc = request.getParameter("cc");
    String too = request.getParameter("too");
    WebMailForm wm = new WebMailForm();
    if (frm.equals("") || frm == null) {
      wm.setFrm("");
    } else {
      wm.setFrm(String.valueOf(frm) + ",");
    } 
    wm.setSubject("Re:" + subject);
    if (cc != null && !cc.equals("")) {
      String a = str.containMailAddre(String.valueOf(cc) + "," + too, userId);
      if (!a.equals("")) {
        wm.setCc(a);
      } else {
        wm.setCc("");
      } 
    } else {
      String a = str.containMailAddre(String.valueOf(cc) + "," + too, userId);
      if (!a.equals("")) {
        wm.setCc(a);
      } else {
        wm.setCc("");
      } 
    } 
    WebMailBD web = new WebMailBD();
    Map map = web.getSingleWebMailInfo(mailId);
    WebMail wmTemp = (WebMail)map.get("WebMail");
    String content = wmTemp.getContent();
    String path = String.valueOf(request.getRealPath("/")) + content;
    path = path.replaceAll("\\\\", "/");
    if (IOManager.isFile(path)) {
      wm.setContent(wmTemp.getContentByIOStr(path));
    } else {
      wm.setContent(content);
    } 
    request.setAttribute("WebMailForm", wm);
    request.setAttribute("folderList", WebMailAccManager.getInstance().getMyAccList(userId));
    return mapping.findForward("rePopMailInfo");
  }
  
  public ActionForward fwPopMailInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    Long userId = Long.valueOf(Long.parseLong(session.getAttribute("userId").toString()));
    String subject = URLDecoder.decode(request.getParameter("subject"), "UTF-8");
    String con = request.getParameter("conText");
    String[] affixPath = request.getParameterValues("afficPath");
    String[] afficName = request.getParameterValues("afficName");
    WebMailForm wm = new WebMailForm();
    wm.setSubject("Fw:" + subject);
    String mailInfoId = request.getParameter("mailInfoId");
    WebMailBD web = new WebMailBD();
    Map map = web.getSingleWebMailInfo(mailInfoId);
    WebMail wmTemp = (WebMail)map.get("WebMail");
    String content = wmTemp.getContent();
    String path = String.valueOf(request.getRealPath("/")) + content;
    path = path.replaceAll("\\\\", "/");
    if (IOManager.isFile(path)) {
      wm.setContent(wmTemp.getContentByIOStr(path));
    } else {
      wm.setContent(content);
    } 
    request.setAttribute("WebMailForm", wm);
    request.setAttribute("affixPath", affixPath);
    request.setAttribute("afficName", afficName);
    request.setAttribute("folderList", WebMailAccManager.getInstance().getMyAccList(userId));
    request.setAttribute("affixList", IOManager.getAffixString((List)map.get("AffixList"), AffixManager.getInstance().getBakAttahListById(Long.valueOf(Long.parseLong(mailInfoId)))));
    return mapping.findForward("rePopMailInfo");
  }
  
  public ActionForward sendPopMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    Long userId = Long.valueOf(Long.parseLong(session.getAttribute("userId").toString()));
    String mailAccId = request.getParameter("mailAccId");
    String to = DecodePerName(request.getParameter("to"));
    String subject = URLDecoder.decode(request.getParameter("subject"), "UTF-8");
    String cc = DecodePerName(request.getParameter("cc"));
    String bc = DecodePerName(request.getParameter("bc"));
    String mailInfoId = request.getParameter("mailInfoId");
    String content = request.getParameter("content1");
    Calendar cal = Calendar.getInstance();
    String year = String.valueOf(cal.get(1));
    String tempPath = session.getServletContext().getRealPath("/");
    tempPath = tempPath.replaceAll("\\\\", "/");
    tempPath = tempPath.substring(0, tempPath.length() - 1);
    Mail wm = new Mail();
    WebMailBD web = new WebMailBD();
    WebMail wmTemp = null;
    if (mailInfoId != null && !mailInfoId.equals("null") && !mailInfoId.equals("")) {
      Map map = web.getSingleWebMailInfo(mailInfoId);
      wmTemp = (WebMail)map.get("WebMail");
      content = web.setFileByContent(content, tempPath);
      wmTemp.setContent(content);
    } else {
      wmTemp = new WebMail();
      content = web.setFileByContent(content, tempPath);
      wmTemp.setContent(content);
    } 
    String[] saveName = request.getParameterValues("webmailSaveName");
    String[] fileName = request.getParameterValues("webmailFileName");
    String[] afficPath = request.getParameterValues("afficPath");
    String[] afficName = request.getParameterValues("afficName");
    String[] afficSize = request.getParameterValues("afficSize");
    String[] affic = (String[])null;
    wm.setHtml(true);
    if (to != null && !to.equals(""))
      wm.setSendTo(str.formatMailAddre(to)); 
    if (subject != null && !subject.equals(""))
      wm.setSubjectTitle(subject); 
    if (cc != null && !cc.equals(""))
      wm.setCopyTo(str.formatMailAddre(cc)); 
    if (bc != null && !bc.equals(""))
      wm.setSecretTo(str.formatMailAddre(bc)); 
    if (IOManager.isFile(String.valueOf(tempPath) + wmTemp.getContent())) {
      wm.setBoby(wmTemp.getContentByIOStr(String.valueOf(tempPath) + wmTemp.getContent()));
    } else {
      wm.setBoby(wmTemp.getContent());
    } 
    if ((saveName != null && saveName.length > 0) || (afficPath != null && afficPath.length > 0)) {
      if (fileName != null && !fileName.equals(""))
        fileName = DecodeFileName(fileName); 
      if (afficName != null && !afficName.equals(""))
        afficName = DecodeAffixName(afficName); 
      saveFJ(fileName, saveName, null, afficPath, afficName);
      affic = IOManager.transAffixName(afficPath, saveName, request);
      wm.setFileAffix(affic);
    } 
    wm = (new WebMailShowImg()).ImgDuiying(wm, request.getSession().getServletContext().getRealPath("/upload/html/"));
    String smtp = WebMailAccManager.getInstance().getSmtp(Long.valueOf(Long.parseLong(mailAccId)));
    String pwd = WebMailAccManager.getInstance().getPWD(Long.valueOf(Long.parseLong(mailAccId)));
    String from = WebMailAccManager.getInstance().getMyChooseAcc(Long.valueOf(Long.parseLong(mailAccId)));
    int smtpPort = WebMailAccManager.getInstance().getSmtpPort(Long.valueOf(Long.parseLong(mailAccId)));
    String encryptionType = WebMailAccManager.getInstance().getSmtpJMFS(Long.valueOf(Long.parseLong(mailAccId)));
    String a = MailSender.send(wm, smtp, from, pwd, smtpPort, encryptionType);
    int size = 0;
    int mailSize = getAffixSize(saveName);
    if (afficSize != null && afficSize.length > 0)
      for (int i = 0; i < afficSize.length; i++)
        size += Integer.parseInt(afficSize[i]);  
    if (a.equals("6000")) {
      IOManager.delFile(affic);
      if (mailInfoId != null && !mailInfoId.equals("null")) {
        List<WebMail> list = web.getWebMailList(mailInfoId.split(","));
        WebMail mm = list.get(0);
        mm.setMailBox("1");
        mm.setMailSize(size + mailSize);
        mm.setSendDate(date2String(new Date(), null));
        mm.setFrm(from);
        mm.setIsReply("0");
        mm.setIsHtml("1");
        mm.setToo((to == null) ? "" : to);
        mm.setCc((cc == null) ? "" : cc);
        mm.setBc((bc == null) ? "" : bc);
        if (IOManager.isFile(String.valueOf(tempPath) + wmTemp.getContent())) {
          mm.setContent(wmTemp.getContent());
        } else {
          mm.setContent(wmTemp.getContent());
        } 
        mm.setSubject((subject == null) ? "" : subject);
        if ((fileName != null && !fileName.equals("")) || (afficPath != null && !afficPath.equals(""))) {
          mm.setHasAttach("1");
        } else {
          mm.setHasAttach("0");
        } 
        if (mm.getHasAttach().equals("1"))
          saveFJ(fileName, saveName, mm.getMailInfoId(), afficPath, afficName); 
        List<WebMail> list1 = new ArrayList();
        list1.add(mm);
        web.moveMail2_(list1);
        list1 = null;
      } else {
        WebMail webMail = new WebMail();
        webMail.setUserId(userId);
        webMail.setMailSize(size + mailSize);
        webMail.setMailBox("1");
        webMail.setIsReply("0");
        webMail.setIsHtml("1");
        webMail.setSendDate(date2String(new Date(), null));
        webMail.setFrm(from);
        webMail.setMailState("1");
        webMail.setToo((to == null) ? "" : to);
        webMail.setCc((cc == null) ? "" : cc);
        webMail.setBc((bc == null) ? "" : bc);
        if (IOManager.isFile(String.valueOf(tempPath) + wmTemp.getContent())) {
          webMail.setContent(wmTemp.getContent());
        } else {
          webMail.setContent(wmTemp.getContent());
        } 
        webMail.setSubject((subject == null) ? "" : subject);
        if ((fileName != null && !fileName.equals("")) || (afficPath != null && !afficPath.equals(""))) {
          webMail.setHasAttach("1");
        } else {
          webMail.setHasAttach("0");
        } 
        Long id = web.createMail(webMail);
        if (webMail.getHasAttach().equals("1"))
          saveFJ(fileName, saveName, id, afficPath, afficName); 
      } 
    } else {
      save2Bak(userId, saveName, fileName, mailInfoId, to, subject, cc, bc, wm.getBoby(), size + mailSize, afficPath, afficName);
    } 
    request.setAttribute("sendResult", a);
    return mapping.findForward("sendResult");
  }
  
  public void saveFJ(String[] fileName, String[] saveName, Long infoId, String[] afficPath, String[] afficName) {
    Attach att = null;
    List<Attach> attachList = new ArrayList();
    AffixBD ab = new AffixBD();
    if (fileName != null && fileName.length > 0)
      for (int i = 0; i < fileName.length; i++) {
        att = new Attach();
        att.setAttachName(fileName[i]);
        att.setAttachDisName(saveName[i]);
        if (infoId != null && !infoId.equals("")) {
          att.setMailInfoId(infoId);
        } else {
          att.setMailInfoId(Long.valueOf(Long.parseLong("0")));
        } 
        attachList.add(att);
      }  
    if (afficPath != null && afficPath.length > 0)
      for (int i = 0; i < afficPath.length; i++) {
        att = new Attach();
        att.setAttachName(afficName[i]);
        att.setAttachDisName(afficPath[i].substring(afficPath[i].lastIndexOf("/") + 1, afficPath[i].length()));
        if (infoId != null && !infoId.equals("")) {
          att.setMailInfoId(infoId);
        } else {
          att.setMailInfoId(Long.valueOf(Long.parseLong("0")));
        } 
        attachList.add(att);
      }  
    if (attachList.size() > 0)
      ab.createAttach(attachList); 
  }
  
  public void save2Bak(Long userId, String[] saveName, String[] fileName, String infoId, String to, String subject, String cc, String bc, String con, int mailSize, String[] afficPath, String[] afficName) {
    WebMail wm = new WebMail();
    wm.setUserId(userId);
    wm.setMailBox("3");
    wm.setSendDate(date2String(new Date(), null));
    wm.setFrm("");
    wm.setMailState("");
    wm.setIsHtml("1");
    wm.setIsReply("0");
    if (to != null && !to.equals("")) {
      wm.setToo(to);
    } else {
      wm.setToo("");
    } 
    if (subject != null && !subject.equals("")) {
      wm.setSubject(subject);
    } else {
      wm.setSubject("");
    } 
    if (cc != null && !cc.equals("")) {
      wm.setCc(cc);
    } else {
      wm.setCc("");
    } 
    if (bc != null && !bc.equals("")) {
      wm.setBc(bc);
    } else {
      wm.setBc("");
    } 
    if (con != null && !con.equals("")) {
      wm.setContent(con);
      wm.setMailSize(mailSize + con.length() * 2);
    } else {
      wm.setContent("");
      wm.setMailSize(mailSize);
    } 
    if (saveName != null || afficName != null) {
      wm.setHasAttach("1");
    } else {
      wm.setHasAttach("0");
    } 
    WebMailBD wmb = new WebMailBD();
    Long mailInfoId = null;
    if (infoId == null || infoId.equals("null")) {
      mailInfoId = wmb.createMail(wm);
    } else {
      mailInfoId = Long.valueOf(Long.parseLong(infoId));
      wm.setMailInfoId(mailInfoId);
      wmb.updateMail(wm);
      if (wm.getHasAttach().equals("0")) {
        StaticParam.delById(mailInfoId.toString(), "oa_web_mail_attach", "mail_info_id");
        AffixManager.getInstance().init();
      } 
    } 
    if (saveName != null && !saveName.equals("")) {
      StaticParam.delById(mailInfoId.toString(), "oa_web_mail_attach", "mail_info_id");
      AffixManager.getInstance().init();
      saveFJ(fileName, saveName, mailInfoId, null, null);
    } 
    if (afficName != null && !afficName.equals(""))
      saveFJ(null, null, mailInfoId, afficPath, afficName); 
  }
  
  private void list(HttpServletRequest request, String wherePara) {
    Object object = (request.getSession(true).getAttribute("domainId") == null) ? "0" : request.getSession(true).getAttribute("domainId");
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    try {
      Page page = new Page("po.mailId,po.frm,po.subject,po.sendDate,po.hasAttach,po.too,po.mailInfoId,po.mailState,po.mailSize", "com.js.oa.webmail.po.WebMail po ", wherePara);
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      request.setAttribute("webMailListInDB", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "method,boxId,subject,start_date,end_date,checkbox");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public String DecodePerName(String names) {
    String c = "";
    try {
      if (!names.equals("") && names != null)
        c = URLDecoder.decode(names, "utf-8"); 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return c;
  }
  
  public String[] DecodeFileName(String[] fileName) {
    String[] a = (String[])null;
    try {
      if (fileName != null && fileName.length > 0) {
        a = new String[fileName.length];
        for (int i = 0; i < fileName.length; i++)
          a[i] = URLDecoder.decode(fileName[i], "utf-8"); 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return a;
  }
  
  public String[] DecodeAffixName(String[] affixName) {
    String[] b = (String[])null;
    try {
      if (affixName != null && affixName.length > 0) {
        b = new String[affixName.length];
        for (int i = 0; i < affixName.length; i++)
          b[i] = URLDecoder.decode(affixName[i], "utf-8"); 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return b;
  }
  
  public int getAffixSize(String[] saveName) {
    int mailSize = 0;
    try {
      if (saveName != null && saveName.length > 0) {
        UploadFile upFile = new UploadFile();
        for (int i = 0; i < saveName.length; i++)
          mailSize += upFile.getFileSizeStr(saveName[i]); 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return mailSize;
  }
  
  public String date2String(Date date, String format) {
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
