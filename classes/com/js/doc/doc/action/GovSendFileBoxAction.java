package com.js.doc.doc.action;

import com.js.doc.doc.po.GovDocumentSendFilePO;
import com.js.doc.doc.service.SendFileBD;
import com.js.doc.doc.util.DateToStringCN;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GovSendFileBoxAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) {
    String action = request.getParameter("action");
    GovSendFileActionForm govSendFileActionForm = 
      (GovSendFileActionForm)actionForm;
    if ("list".equals(action)) {
      list(request);
      return actionMapping.findForward("list");
    } 
    if ("load".equals(action))
      return actionMapping.findForward(load(request, govSendFileActionForm)); 
    if ("userinfo".equals(action)) {
      userinfo(request);
      return actionMapping.findForward("userinfo");
    } 
    if ("delBatch".equals(action)) {
      delete(request);
      return actionMapping.findForward("list");
    } 
    return actionMapping.findForward("list");
  }
  
  private void list(HttpServletRequest request) {
    HttpSession httpSession = request.getSession(true);
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    StringBuffer sb = new StringBuffer("");
    String queryNumber = request.getParameter("queryNumber");
    String queryTitle = request.getParameter("queryTitle");
    String documentSendFileTopicWord = request.getParameter(
        "documentSendFileTopicWord");
    String queryOrg = request.getParameter("queryOrg");
    String queryItem = request.getParameter("queryItem");
    String queryBeginDate = request.getParameter("queryBeginDate");
    String queryEndDate = request.getParameter("queryEndDate");
    String joinwhere = "1=1";
    String fromwhere = 
      "com.js.doc.doc.po.GovDocumentSendFilePO po ";
    if (queryNumber != null && !"".equals(queryNumber))
      sb.append(" and po.documentSendFileByteNumber like '%").append(
          queryNumber.trim()).append("%'"); 
    if (queryTitle != null && !"".equals(queryTitle))
      sb.append(" and po.documentSendFileTitle like '%").append(
          queryTitle.trim()).append("%'"); 
    if (documentSendFileTopicWord != null && 
      !"".equals(documentSendFileTopicWord))
      sb.append(" and po.documentSendFileTopicWord = '%").append(
          documentSendFileTopicWord.trim()).append("%'"); 
    if (queryOrg != null && !"".equals(queryOrg))
      sb.append(" and po.documentSendFileWriteOrg like '%").append(
          queryOrg.trim()).append("%'"); 
    if ("1".equals(queryItem)) {
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        sb.append(" and ( po.createdTime between '").append(
            queryBeginDate).append(" 00:00:00")
          .append("' and '").append(
            queryEndDate).append(" 23:59:59")
          .append("'").append(
            " or  po.createdTime between '")
          .append(queryEndDate).append(" 00:00:00").append(
            "' and '")
          .append(queryBeginDate).append(" 23:59:59").append(
            "' )");
      } else {
        sb.append(" and ( po.createdTime between JSDB.FN_STRTODATE('").append(
            queryBeginDate).append(" 00:00:00")
          .append("','L') and JSDB.FN_STRTODATE('").append(
            queryEndDate).append(" 23:59:59")
          .append("','L')").append(
            " or  po.createdTime between JSDB.FN_STRTODATE('")
          .append(queryEndDate).append(" 00:00:00").append(
            "','L') and JSDB.FN_STRTODATE('")
          .append(queryBeginDate).append(" 23:59:59").append(
            "','L') )");
      } 
    } 
    sb.append(" and (").append("po.createdEmp=" + 
        request.getSession(true).getAttribute("userId"))
      .append(")");
    try {
      Page page = new Page(" po.id,po.documentSendFileByteNumber,po.documentSendFileTitle,po.documentSendFileWriteOrg,po.createdTime ,po.createdEmp,po.createdOrg,po.sendFileLink", 
          fromwhere, 
          " where " + joinwhere + sb + " and po.field5='1' and po.domainId=" + domainId + 
          " order by po.id desc ");
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      request.setAttribute("mylist", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "action,queryItem,queryBeginDate,queryEndDate,queryNumber,querySecret,queryOrg,queryTitle");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private String load(HttpServletRequest httpServletRequest, GovSendFileActionForm govSendFileActionForm) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    String recordId = httpServletRequest.getParameter("editId");
    SendFileBD bd = new SendFileBD();
    GovDocumentSendFilePO po = bd.load(recordId);
    govSendFileActionForm.setDocumentSendFileSecurityGrade(po
        .getDocumentSendFileSecurityGrade());
    govSendFileActionForm.setSendFileGrade(po.getSendFileGrade());
    govSendFileActionForm.setSendFileRedHeadPic(po.getSendFileRedHeadPic());
    govSendFileActionForm.setDocumentSendFileByteNumber(po
        .getDocumentSendFileByteNumber());
    govSendFileActionForm.setSendFileType(po.getSendFileType());
    govSendFileActionForm.setDocumentSendFileTitle(po.getDocumentSendFileTitle());
    govSendFileActionForm.setSendFileText(po.getSendFileText());
    if (po.getHtmlContent() != null && !po.getHtmlContent().equals(""))
      httpServletRequest.setAttribute("htmlContent", po.getHtmlContent()); 
    govSendFileActionForm.setSendFileSealPic(po.getSendFileSealPic());
    DateToStringCN dateToStringCN = new DateToStringCN();
    String dateString = DateToStringCN.convertObjectToYearMonthDateCN(po
        .getDocumentSendFileSendDate());
    httpServletRequest.setAttribute("dateString", dateString);
    httpServletRequest.setAttribute("accessoryName", po.getAccessoryName());
    httpServletRequest.setAttribute("accessorySaveName", 
        po.getAccessorySaveName());
    govSendFileActionForm.setDocumentSendFileTopicWord(po
        .getDocumentSendFileTopicWord());
    String sendToType = po.getSendToType();
    if (sendToType != null) {
      govSendFileActionForm.setSendToType(sendToType);
      if ("0".equals(sendToType)) {
        govSendFileActionForm.setToPerson1(po.getMainToName());
        govSendFileActionForm.setToPerson2(po.getCopyToName());
      } else if ("1".equals(sendToType)) {
        govSendFileActionForm.setToPerson3(po.getMainToName());
        govSendFileActionForm.setToPerson4(po.getSendRoundName());
        govSendFileActionForm.setToPerson5(po.getSunderToName());
      } else if ("2".equals(sendToType)) {
        govSendFileActionForm.setToPerson6(po.getSunderToName());
      } 
    } else {
      govSendFileActionForm.setSendToType("0");
    } 
    govSendFileActionForm.setDocumentSendFileWriteOrg(po
        .getDocumentSendFileWriteOrg());
    govSendFileActionForm.setDocumentSendFileAssumePeople(po
        .getDocumentSendFileAssumePeople());
    govSendFileActionForm.setDocumentSendFileAssumeUnit(po
        .getDocumentSendFileAssumeUnit());
    govSendFileActionForm.setDocumentSendFileCheckCommit(po
        .getDocumentSendFileCheckCommit());
    govSendFileActionForm.setDocumentSendFileCheckDate(po
        .getDocumentSendFileCheckDate());
    govSendFileActionForm.setDocumentSendFileCounterSign(po
        .getDocumentSendFileCounterSign());
    govSendFileActionForm.setDocumentSendFileHead(po.getDocumentSendFileHead());
    govSendFileActionForm.setDocumentSendFilePrintNumber((new StringBuilder(
          String.valueOf(po.getDocumentSendFilePrintNumber()))).toString());
    govSendFileActionForm.setDocumentSendFileSendFile(po
        .getDocumentSendFileSendFile());
    httpServletRequest.setAttribute("sendFileDate", po.getDocumentSendFileDate());
    httpServletRequest.setAttribute("sendFileSendDate", 
        po.getDocumentSendFileSendDate());
    govSendFileActionForm.setDocumentSendFileWriteOrg(po
        .getDocumentSendFileWriteOrg());
    govSendFileActionForm.setSendToId(String.valueOf(po.getSendToOrg()) + po.getSendToGroup() + 
        po.getSendToEmp());
    govSendFileActionForm.setSendToName(po.getSendToName());
    govSendFileActionForm.setSendFileAccessoryDesc(po.getSendFileAccessoryDesc());
    govSendFileActionForm.setSendFileAgentDraft(po.getSendFileAgentDraft());
    govSendFileActionForm.setSendFileDraft(po.getSendFileDraft());
    govSendFileActionForm.setSendFileMassDraft(po.getSendFileMassDraft());
    govSendFileActionForm.setSendFilePrinter(po.getSendFilePrinter());
    govSendFileActionForm.setSendFileProof(po.getSendFileProof());
    govSendFileActionForm.setSendFileProveDraft(po.getSendFileProveDraft());
    govSendFileActionForm.setSendFileReadComment(po.getSendFileReadComment());
    govSendFileActionForm.setReceiveFileIsFlowMode(po.getReceiveFileIsFlowMode());
    govSendFileActionForm.setSendFileRedHeadId(po.getSendFileRedHeadId());
    govSendFileActionForm.setSendFileSealId(po.getSendFileSealId());
    govSendFileActionForm.setDocumentSendFileSecurityGrade(po
        .getDocumentSendFileSecurityGrade());
    httpServletRequest.setAttribute("sendToType", sendToType);
    httpServletRequest.setAttribute("myform", govSendFileActionForm);
    bd.setSendFileBrower(recordId, 
        httpSession.getAttribute("userId").toString(), 
        httpSession.getAttribute("userName").toString(), 
        httpSession.getAttribute("orgName").toString(), 
        domainId);
    String sendFileType = (po.getSendFileType() == null) ? "0" : 
      po.getSendFileType();
    httpServletRequest.setAttribute("queryStr", httpServletRequest.getQueryString());
    return "modi";
  }
  
  private void userinfo(HttpServletRequest httpServletRequest) {
    String recordId = httpServletRequest.getParameter("editId");
    String showType = httpServletRequest.getParameter("type");
    Integer type = Integer.valueOf("0");
    if (showType.equals("showNotRead"))
      type = Integer.valueOf("1"); 
    httpServletRequest.setAttribute("showType", type);
    httpServletRequest.setAttribute("uList", (
        new SendFileBD()).getBrowerUser(recordId, 
          type));
  }
  
  public void delete(HttpServletRequest httpServletRequest) {
    String recordId = httpServletRequest.getParameter("editId");
    SendFileBD bd = new SendFileBD();
    if (recordId != null && !"".equals(recordId))
      bd.sendFileBoxDel(recordId); 
    String sendFileUserId = httpServletRequest.getParameter("sendFileUserId");
    bd.removeSendUser(sendFileUserId);
    list(httpServletRequest);
  }
}
