package com.js.oa.dcq.action;

import com.js.oa.dcq.bean.BusinessShowBean;
import com.js.util.page.Page;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class BusinessShowAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    try {
      String action = request.getParameter("action");
      BusinessShowBean bean = new BusinessShowBean();
      String personID = "1cd11d8875c9bd7d5a3dd37756cab950";
      String orgID = "C3800";
      String realPath = request.getSession().getServletContext().getRealPath("/");
      if ("documentShow".equals(action)) {
        documentList(request);
        return actionMapping.findForward(action);
      } 
      if ("informationShow".equals(action)) {
        informationList(request);
        return actionMapping.findForward(action);
      } 
      if ("inpsectorShow".equals(action)) {
        inpsectorList(request);
        return actionMapping.findForward(action);
      } 
      if ("meetingShow".equals(action)) {
        meetingList(request);
        return actionMapping.findForward(action);
      } 
      if ("meetingMaterials".equals(action)) {
        meetingMaterials(request);
        return actionMapping.findForward(action);
      } 
      if ("messageShow".equals(action)) {
        messageList(request);
        return actionMapping.findForward(action);
      } 
      if ("getMessage".equals(action)) {
        try {
          PrintWriter out = response.getWriter();
          boolean flag = false;
          if (personID != null && !"".equals(personID) && orgID != null && !"".equals(orgID)) {
            long sl = (new Date()).getTime();
            System.out.println("数据获取开始==" + new Date());
            flag = (new BusinessShowBean()).getBusinessMessage(personID, orgID, realPath);
            System.out.println("数据获取结束==" + flag + "，用时：" + ((new Date()).getTime() - sl));
          } else {
            System.out.println("人员或组织唯一标识为空。");
          } 
          if (flag) {
            out.println("success");
          } else {
            out.println("fail");
          } 
        } catch (IOException e) {
          e.printStackTrace();
        } 
        return null;
      } 
      if ("submitToOA".equals(action)) {
        String fileId = request.getParameter("fileId");
        String fileType = request.getParameter("fileType");
        boolean flag = false;
        if ("sendfile".equals(fileType)) {
          System.out.println("发文信息提交:" + fileId);
          flag = bean.sendDocument(realPath, personID, orgID, fileId);
        } else if ("notice".equals(fileType)) {
          System.out.println("通知公告提交：" + fileId);
          String orgId = request.getParameter("orgId");
          flag = bean.releaseInformation(realPath, fileId, orgID, personID, orgId);
        } 
        if (flag) {
          response.getWriter().print("success");
        } else {
          response.getWriter().print("fail");
        } 
        return null;
      } 
      if ("sendfilePress".equals(action)) {
        String fileId = request.getParameter("fileId");
        String title = request.getParameter("title");
        String orgId = request.getParameter("orgId");
        String content = request.getParameter("content");
        if (title != null && !"".equals(title))
          title = URLDecoder.decode(title, "utf-8"); 
        if (content != null && !"".equals(content))
          content = URLDecoder.decode(content, "utf-8"); 
        boolean flag = bean.documentPress(personID, fileId, title, orgId, content);
        if (flag) {
          response.getWriter().print("success");
        } else {
          response.getWriter().print("fail");
        } 
        return null;
      } 
      if ("copyFile".equals(action)) {
        String curId = request.getParameter("curId");
        String fileType = request.getParameter("fileType");
        String result = bean.copyFile(realPath, curId, fileType);
        response.setCharacterEncoding("gbk");
        response.getWriter().print(result);
        return null;
      } 
      if ("informationFeedback".equals(action)) {
        String oid = request.getParameter("oid");
        String browseDate = request.getParameter("browseDate");
        String feedbackContent = request.getParameter("feedbackContent");
        if (feedbackContent != null && !"".equals(feedbackContent))
          feedbackContent = URLDecoder.decode(feedbackContent, "utf-8"); 
        boolean flag = bean.feedbackInformation(personID, oid, orgID, feedbackContent, browseDate);
        if (flag) {
          response.getWriter().print("success");
        } else {
          response.getWriter().print("fail");
        } 
        return null;
      } 
      if ("meetingFeedback".equals(action)) {
        String oid = request.getParameter("oid");
        String fn = request.getParameter("fn");
        String fd = request.getParameter("fd");
        String ft = request.getParameter("ft");
        String fb = request.getParameter("fb");
        if (fn != null && !"".equals(fn))
          fn = URLDecoder.decode(fn, "utf-8"); 
        if (fd != null && !"".equals(fd))
          fd = URLDecoder.decode(fd, "utf-8"); 
        if (ft != null && !"".equals(ft))
          ft = URLDecoder.decode(ft, "utf-8"); 
        if (fb != null && !"".equals(fb))
          fb = URLDecoder.decode(fb, "utf-8"); 
        boolean flag = bean.feedbackMeeting(oid, personID, orgID, fn, fd, ft, fb);
        if (flag) {
          response.getWriter().print("success");
        } else {
          response.getWriter().print("fail");
        } 
      } else if ("inspectorFeedback".equals(action)) {
        String oid = request.getParameter("oid");
        String gzjh = request.getParameter("gzjh");
        String cbsj = request.getParameter("cbsj");
        String czwt = request.getParameter("czwt");
        if (gzjh != null && !"".equals(gzjh))
          gzjh = URLDecoder.decode(gzjh, "utf-8"); 
        if (czwt != null && !"".equals(czwt))
          czwt = URLDecoder.decode(czwt, "utf-8"); 
        boolean flag = bean.feedbackInspector(realPath, personID, oid, orgID, gzjh, czwt, cbsj);
        if (flag) {
          response.getWriter().print("success");
        } else {
          response.getWriter().print("fail");
        } 
      } else {
        if ("submitMessage".equals(action)) {
          String infoId = request.getParameter("infoId");
          String amendments = request.getParameter("amendments");
          String urgencyLevel = request.getParameter("urgencyLevel");
          String tel = request.getParameter("tel");
          String deptId = request.getParameter("deptId");
          if (amendments != null && !"".equals(amendments))
            amendments = URLDecoder.decode(amendments, "utf-8"); 
          if (tel != null && !"".equals(tel))
            tel = URLDecoder.decode(tel, "utf-8"); 
          boolean flag = false;
          System.out.println("信息报送主键：" + infoId);
          if (infoId != null && !"".equals(infoId)) {
            flag = bean.releaseMessage(realPath, infoId, personID, orgID, amendments, urgencyLevel, tel, deptId);
          } else {
            System.out.println("报送信息主键值为空。");
          } 
          if (flag) {
            response.getWriter().print("success");
          } else {
            response.getWriter().print("fail");
          } 
          return null;
        } 
        if ("messageStauts".equals(action)) {
          String oid = request.getParameter("oid");
          String result = bean.getMessageStatus(oid, personID);
          response.setCharacterEncoding("utf-8");
          response.getWriter().print(result);
          return null;
        } 
        return null;
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return null;
  }
  
  private void documentList(HttpServletRequest httpServletRequest) {
    String whereString = " where po.file_oid not in (select gov.field19 from com.js.doc.doc.po.GovReceiveFilePO gov where gov.field19 is not null) ";
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page("po.file_id,po.docTitle,po.releaseDate,po.deptName,po.releaseStatus,po.file_oid", 
        "com.js.oa.dcq.po.DcqRecivefilePO po", String.valueOf(whereString) + "  order by po.file_id desc ");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    httpServletRequest.setAttribute("myList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", 
        String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
  
  private void informationList(HttpServletRequest httpServletRequest) {
    String whereString = " where po.oid not in (select gov.field19 from com.js.doc.doc.po.GovReceiveFilePO gov where gov.field19 is not null) ";
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest
          .getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "po.informationId,po.announcementTitle,po.senderName,po.releaseDate,po.important,po.oid,po.feedbackDate", 
        "com.js.oa.dcq.po.DcqInformationPO po", String.valueOf(whereString) + 
        "  order by po.informationId desc ");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    httpServletRequest.setAttribute("myList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", 
        String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
  
  private void inpsectorList(HttpServletRequest httpServletRequest) {
    String whereString = " where po.oid not in (select gov.field19 from com.js.doc.doc.po.GovReceiveFilePO gov where gov.field19 is not null) ";
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest
          .getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "po.inspectorID,po.title,po.deliveryTime,po.finishTime,po.type,po.oid", 
        "com.js.oa.dcq.po.DcqInspectorPO po", String.valueOf(whereString) + 
        "  order by po.inspectorID desc ");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    httpServletRequest.setAttribute("myList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", 
        String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
  
  private void meetingList(HttpServletRequest httpServletRequest) {
    String whereString = " where po.endTime>now() and po.oid not in (select gov.field19 from com.js.doc.doc.po.GovReceiveFilePO gov where gov.field19 is not null) ";
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest
          .getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "po.meetingID,po.meetingName,po.beginTime,po.endTime,po.meetingSite,po.oid", 
        "com.js.oa.dcq.po.DcqMeetingPO po", String.valueOf(whereString) + 
        "  order by po.meetingID desc ");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    httpServletRequest.setAttribute("myList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", 
        String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
  
  private void meetingMaterials(HttpServletRequest httpServletRequest) {
    String whereString = " ";
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest
          .getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "po.MaterialsId,po.dataName,po.beginTime,po.meetingSubject,po.registerDate", 
        "com.js.oa.dcq.po.DcqMeetingMaterialsPO po", String.valueOf(whereString) + 
        "  order by po.MaterialsId desc ");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    httpServletRequest.setAttribute("myList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", 
        String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
  
  private void messageList(HttpServletRequest httpServletRequest) {
    String whereString = " ";
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest
          .getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "po.messageId,po.infoTitle,po.isReturn,po.urgencyLevel,po.releaseStatus", 
        "com.js.oa.dcq.po.DcqMessagesubmissionPO po", String.valueOf(whereString) + 
        "  order by po.messageId desc ");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    httpServletRequest.setAttribute("myList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", 
        String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
}
