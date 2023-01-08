package com.js.oa.hr.examination.action;

import com.js.oa.hr.examination.po.ExaminationManagePO;
import com.js.oa.hr.examination.service.ExaminationManageBD;
import com.js.system.manager.service.ManagerService;
import com.js.system.service.messages.RemindUtil;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.StringSplit;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExaminationManageAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    ExaminationManageActionForm form = 
      (ExaminationManageActionForm)actionForm;
    String tag = "list";
    String action = (httpServletRequest.getParameter("action") == null) ? 
      "list" : httpServletRequest.getParameter("action");
    String orgId = httpServletRequest.getSession(true).getAttribute("orgId")
      .toString();
    String curUserId = httpServletRequest.getSession(true).getAttribute(
        "userId").toString();
    String userName = httpServletRequest.getSession(true).getAttribute("userName").toString();
    Long domainId = (httpServletRequest.getSession(true).getAttribute(
        "domainId") == null) ? 
      Long.valueOf("0") : 
      Long.valueOf(httpServletRequest.getSession(true)
        .getAttribute("domainId").toString());
    ExaminationManageBD bd = new ExaminationManageBD();
    if (action.equals("list")) {
      list(httpServletRequest);
    } else if (action.equals("add")) {
      tag = "add";
    } else if (action.equals("save")) {
      tag = "modify";
      ExaminationManagePO po = setPO(form, httpServletRequest);
      Long id = bd.save(po);
      po = new ExaminationManagePO();
      po = bd.load(id);
      setForm(form, po);
      httpServletRequest.setAttribute("start_date", po.getStartDate());
      httpServletRequest.setAttribute("end_date", po.getEndDate());
      httpServletRequest.setAttribute("startTime", po.getStartTime());
      httpServletRequest.setAttribute("endTime", po.getEndTime());
      httpServletRequest.setAttribute("id", id);
    } else if ("modify".equals(action)) {
      tag = "modify";
      Long id = new Long(httpServletRequest.getParameter("id").toString());
      ExaminationManagePO po = bd.load(id);
      setForm(form, po);
      httpServletRequest.setAttribute("start_date", po.getStartDate());
      httpServletRequest.setAttribute("end_date", po.getEndDate());
      httpServletRequest.setAttribute("startTime", po.getStartTime());
      httpServletRequest.setAttribute("endTime", po.getEndTime());
      httpServletRequest.setAttribute("selectRadioCount", po.getRadioAmount());
      httpServletRequest.setAttribute("selectCheckCount", po.getCheckAmount());
      httpServletRequest.setAttribute("selectQuestionCount", po.getQuestionAmount());
    } else if ("update".equals(action)) {
      tag = "modify";
      ExaminationManagePO po = setPO(form, httpServletRequest);
      form.setSaveType("saveAndExit");
      bd.update(po, form.getId());
    } else if (action.equals("manage")) {
      tag = "manage";
      list(httpServletRequest);
    } else if (action.equals("read")) {
      tag = "read";
      read(httpServletRequest);
    } else if (action.equals("complete")) {
      Long id = new Long(httpServletRequest.getParameter("id"));
      Long answerID = new Long(httpServletRequest.getParameter("answerID"));
      ExaminationManagePO po = bd.load(id);
      String questionIds = po.getQuestionIds();
      Object[] para = (Object[])null;
      if (questionIds != null && questionIds.length() > 1) {
        questionIds = questionIds.substring(1, questionIds.length() - 1);
        String[] tmpArr = questionIds.split(",,");
        para = new Object[tmpArr.length];
        for (int i = 0; i < tmpArr.length; i++) {
          String[] item = new String[2];
          item[0] = tmpArr[i];
          item[1] = httpServletRequest.getParameter("score" + 
              tmpArr[i]);
          para[i] = item;
        } 
      } 
      bd.grade(answerID, para);
      String examName = httpServletRequest.getParameter("examName");
      String empId = httpServletRequest.getParameter("curUserID");
      String title = String.valueOf(userName) + "阅卷完毕：" + examName;
      String url = "/jsoa/examination/examinationMyPaperPreview.jsp?id=" + id;
      RemindUtil.sendMessageToUsers(title, url, empId, "Info", new Date(), new Date("2050/1/1"));
      tag = "complete";
      String close = httpServletRequest.getParameter("close");
      if (close != null && !close.equals("")) {
        httpServletRequest.setAttribute("close", "other");
      } else {
        httpServletRequest.setAttribute("close", "1");
      } 
      httpServletRequest.setAttribute("id", id);
      httpServletRequest.setAttribute("curUserID", httpServletRequest.getParameter("curUserID"));
    } else if (action.equals("delete")) {
      tag = "list";
      if (httpServletRequest.getParameter("id") != null && 
        !"".equals(httpServletRequest.getParameter("id")))
        bd.delete(new Long(httpServletRequest.getParameter("id"))); 
      list(httpServletRequest);
    } else if (action.equals("deleteBatch")) {
      tag = "list";
      if (httpServletRequest.getParameter("ids") != null && 
        !"".equals(httpServletRequest.getParameter("ids")))
        bd.deleteBatch(httpServletRequest.getParameter("ids")); 
      list(httpServletRequest);
    } 
    return actionMapping.findForward(tag);
  }
  
  private void list(HttpServletRequest httpServletRequest) {
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
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    ManagerService mbd = new ManagerService();
    String whereSQL = mbd.getRightFinalWhere(curUserId, orgId, "07*40*02", 
        "po.createdOrg", 
        "po.createdEmp");
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
    String whereSql = " where po.domainId=" + domainId + " and " + whereSQL + 
      searchSQL;
    whereSql = String.valueOf(whereSql) + " order by po.id desc";
    Page page = new Page(
        "po.id,po.scopeRange,po.startDate,po.startTime,po.endDate,po.endTime,po.examName", 
        "com.js.oa.hr.examination.po.ExaminationManagePO po ", 
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
  
  private ExaminationManagePO setPO(ExaminationManageActionForm form, HttpServletRequest httpServletRequest) {
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
    ExaminationManagePO po = new ExaminationManagePO();
    po.setDomainId(domainId);
    po.setCreatedEmp(new Long(curUserId));
    po.setCreatedOrg(new Long(orgId));
    po.setExamName(form.getExamName());
    po.setScopeRange(form.getScopeRange());
    String scopeCode = form.getScopeCode();
    po.setScopeEmpID(StringSplit.splitWith(scopeCode, "$", "*@"));
    po.setScopeOrgID(StringSplit.splitWith(scopeCode, "*", "$@"));
    po.setScopeGroupID(StringSplit.splitWith(scopeCode, "@", "*$"));
    po.setState(form.getState());
    po.setStartDate(new Date(httpServletRequest.getParameter("start_date")));
    po.setStartTime(form.getStartTime());
    po.setEndDate(new Date(httpServletRequest.getParameter("end_date")));
    po.setEndTime(form.getEndTime());
    po.setRadioAmount(form.getRadioAmount());
    po.setRadioMark(form.getRadioMark());
    po.setCheckAmount(form.getCheckAmount());
    po.setCheckMark(form.getCheckMark());
    po.setQuestionAmount(form.getQuestionAmount());
    po.setQuestionMark(form.getQuestionMark());
    po.setRadioIds(form.getRadioIds());
    po.setCheckIds(form.getCheckIds());
    po.setQuestionIds(form.getQuestionIds());
    return po;
  }
  
  private void setForm(ExaminationManageActionForm form, ExaminationManagePO po) {
    form.setId(po.getId());
    form.setExamName(po.getExamName());
    form.setScopeRange(po.getScopeRange());
    form.setScopeCode(String.valueOf(po.getScopeEmpID()) + po.getScopeOrgID() + 
        po.getScopeGroupID());
    form.setState(po.getState());
    form.setStartTime(po.getStartTime());
    form.setEndTime(po.getEndTime());
    form.setRadioAmount(po.getRadioAmount());
    form.setRadioMark(po.getRadioMark());
    form.setCheckAmount(po.getCheckAmount());
    form.setCheckMark(po.getCheckMark());
    form.setQuestionAmount(po.getQuestionAmount());
    form.setQuestionMark(po.getQuestionMark());
    form.setRadioIds(po.getRadioIds());
    form.setCheckIds(po.getCheckIds());
    form.setQuestionIds(po.getQuestionIds());
  }
  
  private void read(HttpServletRequest httpServletRequest) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String id = httpServletRequest.getParameter("id");
    String searchSQL = "";
    if (httpServletRequest.getParameter("searchExamName") != null && 
      !"".equals(httpServletRequest.getParameter("searchExamName")) && 
      !"null".equals(httpServletRequest.getParameter("searchExamName")))
      searchSQL = String.valueOf(searchSQL) + " and po.empName like '%" + 
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
        searchSQL = String.valueOf(searchSQL) + " and (po.examTime between '" + 
          searchStartDate + 
          " 00:00:00' and '" + 
          searchEndDate + " 23:59:59') ";
      } else {
        searchSQL = String.valueOf(searchSQL) + 
          " and (po.examTime between JSDB.FN_STRTODATE('" + 
          searchStartDate + 
          " 00:00:00', 'L') and JSDB.FN_STRTODATE('" + 
          searchEndDate + " 23:59:59', 'L')) ";
      } 
    } 
    String whereSql = " where po.examinationID=" + id + searchSQL;
    whereSql = String.valueOf(whereSql) + " order by po.answerID desc";
    Page page = new Page(
        "po.answerID,po.empName,po.orgName,po.score,po.examTime,po.examinationID,po.empID", 
        "com.js.oa.hr.examination.po.ExaminationAnswerPO po ", 
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
        "action,searchExamName,start_date,end_date,isTime,id");
  }
}
