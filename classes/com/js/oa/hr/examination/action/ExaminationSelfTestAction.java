package com.js.oa.hr.examination.action;

import com.js.oa.hr.examination.po.ExaminationSelfTestPO;
import com.js.oa.hr.examination.service.ExaminationSelfTestBD;
import com.js.oa.hr.examination.service.ExaminationStockBD;
import com.js.oa.hr.examination.service.ExaminationTypeBD;
import com.js.system.manager.service.ManagerService;
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

public class ExaminationSelfTestAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    ExaminationSelfTestActionForm examinationSelfTestActionForm = 
      (ExaminationSelfTestActionForm)actionForm;
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
    String action = httpServletRequest.getParameter("action");
    String tag = "list";
    ExaminationSelfTestBD bd = new ExaminationSelfTestBD();
    if (action.equals("list")) {
      tag = "list";
      httpServletRequest.setAttribute("typeList", (
          new ExaminationTypeBD())
          .getTypeList(domainId));
      list(httpServletRequest);
    } else if (action.equals("add")) {
      tag = "add";
      httpServletRequest.setAttribute("typeList", (
          new ExaminationTypeBD())
          .getTypeList(domainId));
    } else if (action.equals("createPaper")) {
      tag = "createPaper";
      String[] examinationType = httpServletRequest.getParameterValues(
          "examinationType");
      String[] examinationStyle = httpServletRequest.getParameterValues(
          "examinationStyle");
      String[] amount = httpServletRequest.getParameterValues(
          "amount");
      httpServletRequest.setAttribute("subjectCount", 
          String.valueOf(amount.length - 1));
      for (int i = 1; i < amount.length; i++) {
        String[] stockArr = (new ExaminationStockBD()).getStockArr(
            examinationStyle[i], examinationType[i], "0", domainId);
        httpServletRequest.setAttribute("examinationStyle" + i, 
            examinationStyle[i]);
        if (stockArr == null || stockArr.length == 0) {
          httpServletRequest.setAttribute("hasStock" + i, "0");
        } else {
          String[] stockSel = getRandomStock(stockArr, 
              Integer.parseInt(amount[i]));
          httpServletRequest.setAttribute("hasStock" + i, "1");
          httpServletRequest.setAttribute("stockSel" + i, stockSel);
        } 
      } 
    } else if (action.equals("putin")) {
      tag = "selfTestResult";
      ExaminationSelfTestPO po = new ExaminationSelfTestPO();
      po.setDomainId(domainId);
      po.setTestEmpID(new Long(curUserId));
      po.setTestEmpName(userName);
      po.setTestOrgID(new Long(orgId));
      po.setTestOrgName(orgName);
      po.setTestTime(new Date());
      String examinationID = httpServletRequest.getParameter(
          "examinationID");
      examinationID = examinationID.substring(0, 
          examinationID.length() - 1);
      String[] examArr = examinationID.split(",");
      Object[] para = new Object[examArr.length];
      for (int i = 0; i < examArr.length; i++) {
        String tmpStr = examArr[i];
        String[] itemPara = new String[5];
        if (tmpStr.startsWith("rd")) {
          String[] tmpArr = tmpStr.split("_");
          itemPara[0] = tmpArr[0].substring(2, tmpArr[0].length());
          itemPara[1] = "1";
          itemPara[2] = tmpArr[1];
          itemPara[3] = tmpArr[2];
          if (httpServletRequest.getParameter(String.valueOf(tmpArr[0]) + "_" + tmpArr[1]) != null) {
            itemPara[4] = "," + 
              httpServletRequest.getParameter(String.valueOf(tmpArr[0]) + "_" + tmpArr[1]) + 
              ",";
          } else {
            itemPara[4] = ",-1,";
          } 
        } else if (tmpStr.startsWith("cbx")) {
          String[] tmpArr = tmpStr.split("_");
          itemPara[0] = tmpArr[0].substring(3, tmpArr[0].length());
          itemPara[1] = "2";
          itemPara[2] = tmpArr[1];
          itemPara[3] = tmpArr[2];
          String[] tmpArr2 = httpServletRequest.getParameterValues(
              String.valueOf(tmpArr[0]) + "_" + tmpArr[1]);
          itemPara[4] = ",";
          if (tmpArr2 != null && tmpArr2.length > 0) {
            for (int j = 0; j < tmpArr2.length; j++)
              itemPara[4] = String.valueOf(itemPara[4]) + tmpArr2[j] + ","; 
          } else {
            itemPara[4] = ",-1,";
          } 
        } else if (tmpStr.startsWith("ask")) {
          String[] tmpArr = tmpStr.split("_");
          itemPara[0] = tmpArr[0].substring(3, tmpArr[0].length());
          itemPara[1] = "3";
          itemPara[2] = tmpArr[1];
          itemPara[3] = tmpArr[2];
          itemPara[4] = (httpServletRequest.getParameter(String.valueOf(tmpArr[0]) + "_" + tmpArr[1]) == null) ? 
            "" : httpServletRequest.getParameter(String.valueOf(tmpArr[0]) + "_" + tmpArr[1]);
        } 
        para[i] = itemPara;
      } 
      Long id = bd.save(po, para);
      String[] result = bd.result(id);
      httpServletRequest.setAttribute("result", result);
      httpServletRequest.setAttribute("id", id);
    } else if (action.equals("particularResult")) {
      tag = "particularResult";
      Long id = new Long(httpServletRequest.getParameter("id"));
      httpServletRequest.setAttribute("selfTestPO", bd.load(id));
    } else if (action.equals("search")) {
      tag = "list";
      search(httpServletRequest);
    } else if (action.equals("delete")) {
      if (httpServletRequest.getParameter("id") != null && 
        !"".equals(httpServletRequest.getParameter("id")))
        bd.delete(new Long(httpServletRequest.getParameter("id"))); 
      tag = "list";
      search(httpServletRequest);
    } else if (action.equals("deleteBatch")) {
      if (httpServletRequest.getParameter("ids") != null && 
        !"".equals(httpServletRequest.getParameter("ids")))
        bd.deleteBatch(httpServletRequest.getParameter("ids")); 
      tag = "list";
      search(httpServletRequest);
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
    String whereSql = " where  po.domainId=" + 
      domainId + " and po.testEmpID=" + curUserId;
    Page page = new Page(
        "po.selfTestID,po.testTime,po.testEmpName,po.testOrgName", 
        "com.js.oa.hr.examination.po.ExaminationSelfTestPO po ", 
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
        "action");
  }
  
  private String[] getRandomStock(String[] para, int sel) {
    String[] result = new String[sel];
    int count = para.length;
    if (count <= sel)
      return para; 
    for (int j = 0; j < result.length; j++) {
      int r = (int)(Math.random() * count);
      result[j] = para[r];
      para[r] = para[count - 1];
      count--;
    } 
    return result;
  }
  
  private void search(HttpServletRequest httpServletRequest) {
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
    String whereSQL = mbd.getRightFinalWhere(curUserId, orgId, "07*40*01", 
        "po.testOrgID", 
        "po.testEmpID");
    String searchSQL = "";
    if (httpServletRequest.getParameter("searchTestEmpID") != null && 
      !"".equals(httpServletRequest.getParameter("searchTestEmpID")) && 
      !"null".equals(httpServletRequest.getParameter("searchTestEmpID"))) {
      searchSQL = String.valueOf(searchSQL) + " and po.testEmpID=" + 
        httpServletRequest.getParameter("searchTestEmpID");
    } else if (httpServletRequest.getParameter("searchTestEmpName") != null && 
      !"".equals(httpServletRequest.getParameter(
          "searchTestEmpName"))) {
      if (!"null".equals(httpServletRequest.getParameter(
            "searchTestEmpName")))
        searchSQL = String.valueOf(searchSQL) + " and po.testEmpName like '%" + 
          httpServletRequest.getParameter("searchTestEmpName") + 
          "%'"; 
    } 
    if (httpServletRequest.getParameter("searchTestOrgID") != null && 
      !"".equals(httpServletRequest.getParameter("searchTestOrgID")) && 
      !"null".equals(httpServletRequest.getParameter("searchTestOrgID"))) {
      searchSQL = String.valueOf(searchSQL) + " and po.testOrgID=" + 
        httpServletRequest.getParameter("searchTestOrgID");
    } else if (httpServletRequest.getParameter("searchTestOrgName") != null && 
      !"".equals(httpServletRequest.getParameter(
          "searchTestOrgName"))) {
      if (!"null".equals(httpServletRequest.getParameter(
            "searchTestOrgName")))
        searchSQL = String.valueOf(searchSQL) + " and po.testOrgName like '%" + 
          httpServletRequest.getParameter("searchTestOrgName") + 
          "%'"; 
    } 
    String searchStartDate = httpServletRequest.getParameter(
        "start_date");
    String searchEndDate = httpServletRequest.getParameter("end_date");
    String databaseType = 
      SystemCommon.getDatabaseType();
    if (httpServletRequest.getParameter("isTime") != null && 
      "1".equals(httpServletRequest.getParameter("isTime"))) {
      httpServletRequest.setAttribute("isTime", "1");
      if (databaseType.indexOf("mysql") >= 0) {
        searchSQL = String.valueOf(searchSQL) + " and (po.testTime between '" + 
          searchStartDate + 
          " 00:00:00' and '" + 
          searchEndDate + " 23:59:59') ";
      } else {
        searchSQL = String.valueOf(searchSQL) + 
          " and (po.testTime between JSDB.FN_STRTODATE('" + 
          searchStartDate + 
          " 00:00:00', 'L') and JSDB.FN_STRTODATE('" + 
          searchEndDate + " 23:59:59', 'L')) ";
      } 
    } 
    String whereSql = " where  po.domainId=" + 
      domainId + " and " + whereSQL + searchSQL;
    Page page = new Page(
        "po.selfTestID,po.testTime,po.testEmpName,po.testOrgName", 
        "com.js.oa.hr.examination.po.ExaminationSelfTestPO po ", 
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
        "action,searchTestEmpName,searchTestEmpID,searchTestOrgName,searchTestOrgID,start_date,end_date,isTime");
  }
}
