package com.js.oa.hr.examination.action;

import com.js.oa.hr.examination.po.ExaminationAnswerPO;
import com.js.oa.hr.examination.po.ExaminationManagePO;
import com.js.oa.hr.examination.po.ExaminationStockPO;
import com.js.oa.hr.examination.service.ExaminationManageBD;
import com.js.oa.hr.examination.service.ExaminationStockBD;
import com.js.system.service.messages.RemindUtil;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExaminationMyPaperAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    ExaminationMyPaperActionForm examinationMyPaperActionForm = 
      (ExaminationMyPaperActionForm)actionForm;
    String orgId = httpServletRequest.getSession(true).getAttribute("orgId")
      .toString();
    String curUserId = httpServletRequest.getSession(true).getAttribute(
        "userId")
      .toString();
    Long domainId = (httpServletRequest.getSession(true).getAttribute(
        "domainId") == null) ? 
      Long.valueOf("0") : 
      Long.valueOf(httpServletRequest.getSession(true)
        .getAttribute("domainId").toString());
    String userName = httpServletRequest.getSession(true).getAttribute(
        "userName").toString();
    String orgName = httpServletRequest.getSession(true).getAttribute(
        "orgName").toString();
    String tag = "list";
    String action = (httpServletRequest.getParameter("action") == null) ? 
      "list" : httpServletRequest.getParameter("action");
    if (action.equals("list")) {
      list(httpServletRequest);
    } else if (action.equals("putin")) {
      Long id = new Long(httpServletRequest.getParameter("id"));
      ExaminationAnswerPO eapo = new ExaminationAnswerPO();
      eapo.setExaminationID(id);
      eapo.setEmpID(new Long(curUserId));
      eapo.setEmpName(userName);
      eapo.setOrgID(new Long(orgId));
      eapo.setOrgName(orgName);
      eapo.setScore(new Long(0L));
      eapo.setExamTime(new Date());
      ExaminationManagePO empo = (new ExaminationManageBD()).load(id);
      String radioIds = (empo.getRadioIds() != null) ? empo.getRadioIds().trim() : "";
      String checkIds = (empo.getCheckIds() != null) ? empo.getCheckIds().trim() : "";
      String questionIds = (empo.getQuestionIds() != null) ? empo.getQuestionIds().trim() : "";
      String tmp = String.valueOf(radioIds) + checkIds + questionIds;
      tmp = tmp.substring(1, tmp.length() - 1);
      Object[] para = new Object[(tmp.split(",,")).length];
      Long score = Long.valueOf(0L);
      int paraSuffix = 0;
      if (radioIds != null && radioIds.trim().length() > 1) {
        String[] tmpArr = radioIds.substring(1, radioIds.length() - 1).split(",,");
        for (int i = 0; i < tmpArr.length; i++) {
          ExaminationStockPO esPO = (new ExaminationStockBD()).load(new Long(tmpArr[i]));
          String[] answerArr = new String[6];
          answerArr[0] = tmpArr[i];
          answerArr[1] = esPO.getResult();
          answerArr[3] = empo.getRadioMark().toString();
          if (httpServletRequest.getParameter("rd" + tmpArr[i]) != null) {
            answerArr[2] = "," + httpServletRequest.getParameter("rd" + tmpArr[i]) + ",";
          } else {
            answerArr[2] = ",-1,";
          } 
          if (answerArr[1].equals(answerArr[2])) {
            answerArr[4] = "1";
            answerArr[5] = empo.getRadioMark().toString();
            score = Long.valueOf(score.longValue() + empo.getRadioMark().longValue());
          } else {
            answerArr[4] = "0";
            answerArr[5] = "0";
          } 
          para[paraSuffix] = answerArr;
          paraSuffix++;
        } 
      } 
      if (checkIds != null && checkIds.trim().length() > 1) {
        String[] tmpArr = checkIds.substring(1, checkIds.length() - 1).split(",,");
        for (int i = 0; i < tmpArr.length; i++) {
          ExaminationStockPO esPO = (new ExaminationStockBD()).load(new Long(tmpArr[i]));
          String[] answerArr = new String[6];
          answerArr[0] = tmpArr[i];
          answerArr[1] = esPO.getResult();
          answerArr[3] = empo.getCheckMark().toString();
          String[] tmpArr2 = httpServletRequest.getParameterValues("cbx" + tmpArr[i]);
          answerArr[2] = ",";
          if (tmpArr2 != null && tmpArr2.length > 0) {
            for (int j = 0; j < tmpArr2.length; j++)
              answerArr[2] = String.valueOf(answerArr[2]) + tmpArr2[j] + ","; 
          } else {
            answerArr[2] = ",-1,";
          } 
          if (answerArr[1].equals(answerArr[2])) {
            answerArr[4] = "1";
            answerArr[5] = empo.getCheckMark().toString();
            score = Long.valueOf(score.longValue() + empo.getCheckMark().longValue());
          } else {
            answerArr[4] = "0";
            answerArr[5] = "0";
          } 
          para[paraSuffix] = answerArr;
          paraSuffix++;
        } 
      } 
      eapo.setScore(Long.valueOf(Long.parseLong(String.valueOf(score))));
      if (questionIds != null && questionIds.trim().length() > 1) {
        String[] tmpArr = questionIds.substring(1, 
            questionIds.length() - 1)
          .split(",,");
        for (int i = 0; i < tmpArr.length; i++) {
          ExaminationStockPO esPO = (new ExaminationStockBD()).load(
              new Long(tmpArr[i]));
          String[] answerArr = new String[6];
          answerArr[0] = tmpArr[i];
          answerArr[1] = esPO.getResult();
          answerArr[3] = empo.getRadioMark().toString();
          if (httpServletRequest.getParameter("ask" + tmpArr[i]) != null) {
            answerArr[2] = httpServletRequest.getParameter("ask" + 
                tmpArr[i]);
          } else {
            answerArr[2] = "";
          } 
          answerArr[4] = "0";
          answerArr[5] = "0";
          para[paraSuffix] = answerArr;
          paraSuffix++;
        } 
      } 
      (new ExaminationManageBD()).savePaper(eapo, para);
      String examinerId = httpServletRequest.getParameter("examinerId");
      if (examinerId != null && !examinerId.equals("null") && !examinerId.equals("")) {
        String examName = httpServletRequest.getParameter("examName");
        String title = String.valueOf(userName) + "答卷完毕：" + examName;
        String url = "/jsoa/examination/examinationAnswerPreview.jsp?close=2&id=" + id + "&empID=" + curUserId + "&answerID=" + eapo.getAnswerID();
        RemindUtil.sendMessageToUsers(title, url, examinerId, "Info", new Date(), new Date("2050/1/1"));
      } 
      tag = "putin";
      httpServletRequest.setAttribute("close", "1");
    } 
    return actionMapping.findForward(tag);
  }
  
  private void list(HttpServletRequest httpServletRequest) {
    String curUserId = httpServletRequest.getSession(true).getAttribute(
        "userId")
      .toString();
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String searchSQL = "";
    if (httpServletRequest.getParameter("searchExamName") != null && 
      !"".equals(httpServletRequest.getParameter("searchExamName")) && 
      !"null".equals(httpServletRequest.getParameter("searchExamName")))
      searchSQL = String.valueOf(searchSQL) + " and po.examName like '%" + 
        httpServletRequest.getParameter("searchExamName") + "%'"; 
    String searchStartDate = httpServletRequest.getParameter(
        "start_date");
    String searchEndDate = httpServletRequest.getParameter("end_date");
    String databaseType = 
      SystemCommon.getDatabaseType();
    if (httpServletRequest.getParameter("isTime") != null && 
      "1".equals(httpServletRequest.getParameter("isTime"))) {
      httpServletRequest.setAttribute("isTime", "1");
      if (databaseType.indexOf("mysql") >= 0) {
        searchSQL = String.valueOf(searchSQL) + " and (po.startDate between '" + 
          searchStartDate + 
          " 00:00:00' and '" + 
          searchEndDate + " 23:59:59') ";
      } else {
        searchSQL = String.valueOf(searchSQL) + 
          " and (po.startDate between JSDB.FN_STRTODATE('" + 
          searchStartDate + 
          " 00:00:00', 'L') and JSDB.FN_STRTODATE('" + 
          searchEndDate + " 23:59:59', 'L')) ";
      } 
    } 
    String whereSql = " where  ppo.examManagerID = po.id and ppo.empID=" + 
      curUserId + " and po.state='1' " + searchSQL;
    if (httpServletRequest.getParameter("orderbyisanswer") != null) {
      if (httpServletRequest.getParameter("orderbyisanswer").equals("0")) {
        whereSql = String.valueOf(whereSql) + " order by ppo.isAnswer,po.startDate,po.startTime  ";
      } else {
        whereSql = String.valueOf(whereSql) + " order by ppo.isAnswer desc,po.startDate desc,po.startTime desc";
      } 
    } else {
      whereSql = String.valueOf(whereSql) + " order by ppo.isAnswer,po.startDate desc,po.startTime desc";
    } 
    Page page = new Page(
        "po.id,po.scopeRange,po.startDate,po.startTime,po.endDate,po.endTime,po.examName,ppo.isAnswer,po.createdEmp", 
        "com.js.oa.hr.examination.po.ExaminationManagePO po ,com.js.oa.hr.examination.po.ExaminationPersonnelPO ppo", 
        whereSql);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("list", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", 
        "action,searchExamName,start_date,end_date,isTime");
  }
}
