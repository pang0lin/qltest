package com.js.doc.doc.action;

import com.js.doc.doc.service.SendFileBD;
import com.js.util.page.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GovSendFileDraftBoxAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    String flag = (httpServletRequest.getParameter("flag") == null) ? "list" : httpServletRequest.getParameter("flag");
    String tag = "list";
    if ("list".equals(flag)) {
      list(httpServletRequest);
      tag = "list";
    } else if ("delSingle".equals(flag)) {
      delSingle(httpServletRequest);
      tag = "list";
    } 
    return actionMapping.findForward(tag);
  }
  
  private void list(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    Object object = httpSession.getAttribute("userId");
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page("sendfile.id, sendfile.documentSendFileByteNumber, sendfile.documentSendFileTitle, sendfile.documentSendFileWriteOrg, sendfile.processId, sendfile.processName, sendfile.processType, sendfile.tableId, sendfile.remindField,sendfile.createdTime ", 
        "com.js.doc.doc.po.GovDocumentSendFilePO sendfile", 
        "where sendfile.createdEmp=" + object + " and sendfile.isDraft=1 and sendfile.domainId=" + domainId + " order by sendfile.id desc");
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
      httpServletRequest.setAttribute("pager.realCurrent", (new StringBuilder(String.valueOf(currentPage))).toString());
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("draftList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "flag");
  }
  
  private void delSingle(HttpServletRequest httpServletRequest) {
    SendFileBD sendFileBD = new SendFileBD();
    sendFileBD.delBatch(String.valueOf(httpServletRequest.getParameter("sendFileId")) + ", ");
    list(httpServletRequest);
  }
}
