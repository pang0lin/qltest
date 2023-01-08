package com.js.oa.routine.resource.action;

import com.js.oa.routine.resource.service.StockBD;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ApplicationReportFormsAction extends Action {
  private static Logger logger = Logger.getLogger(
      ApplicationReportFormsAction.class.getName());
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    ApplicationReportFormsActionForm appRepFormsActionForm = 
      (ApplicationReportFormsActionForm)actionForm;
    String action = httpServletRequest.getParameter("action");
    String flag = "view";
    if ("view".equals(action)) {
      getUserManaStock(httpServletRequest);
      getDrawDept(httpServletRequest);
    } else if ("search".equals(action)) {
      flag = "report";
      String countType = appRepFormsActionForm.getCountType();
      httpServletRequest.setAttribute("countType", countType);
      String stockId = "";
      String stockName = "";
      if (appRepFormsActionForm.getSearchStock() != null) {
        String[] searchStock = appRepFormsActionForm.getSearchStock()
          .split(",");
        stockId = searchStock[0];
        stockName = searchStock[1];
      } 
      httpServletRequest.setAttribute("stockName", stockName);
      String deptId = "";
      String deptName = "";
      if (appRepFormsActionForm.getSearchDept() != null) {
        String[] searchDept = appRepFormsActionForm.getSearchDept()
          .split(",");
        deptId = searchDept[0];
        deptName = searchDept[1];
      } 
      httpServletRequest.setAttribute("deptName", deptName);
      String startDate = httpServletRequest.getParameter(
          "searchStartDate");
      String endDate = httpServletRequest.getParameter("searchEndDate");
      String view = "ssDetailPO.goodsId,ssDetailPO.goodsName,ssDetailPO.amount,ssDetailPO.goodsUnit,ssDetailPO.price,ssDetailPO.goodsMoney";
      String from = 
        "com.js.oa.routine.resource.po.SsDetailPO ssDetailPO join ssDetailPO.ssMaster ssMasterPO";
      StringBuffer whereBuffer = new StringBuffer("where ");
      if (countType != null)
        if ("1".equals(countType)) {
          whereBuffer.append("ssMasterPO.checkFlag ='");
          whereBuffer.append("Y' and ");
        } else if ("2".equals(countType)) {
          whereBuffer.append("ssMasterPO.checkFlag ='");
          whereBuffer.append("N' and ");
        }  
      if (!"".equals(stockId)) {
        whereBuffer.append("ssMasterPO.ssStock ='");
        whereBuffer.append(stockId);
        whereBuffer.append("' and ");
      } 
      if (!"".equals(deptId)) {
        whereBuffer.append("ssMasterPO.ssDept ='");
        whereBuffer.append(deptId);
        whereBuffer.append("' and ");
      } 
      if (startDate != null && endDate != null) {
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          whereBuffer.append("(ssMasterPO.ssDate between ");
          whereBuffer.append("'");
          whereBuffer.append(startDate);
          whereBuffer.append(" 00:00:00' and ");
          whereBuffer.append("'");
          whereBuffer.append(endDate);
          whereBuffer.append(" 23:59:59') and");
        } else {
          whereBuffer.append("(ssMasterPO.ssDate between ");
          whereBuffer.append("JSDB.FN_STRTODATE('");
          whereBuffer.append(startDate);
          whereBuffer.append(" 00:00:00', 'L') and ");
          whereBuffer.append("JSDB.FN_STRTODATE('");
          whereBuffer.append(endDate);
          whereBuffer.append(" 23:59:59', 'L')) and");
        } 
      } 
      String where = whereBuffer.substring(0, 
          whereBuffer.lastIndexOf("and"));
      where = String.valueOf(where) + " order by ssDetailPO.goodsId";
      logger.info("where =" + where);
      drawDeptList(httpServletRequest, view, from, where);
    } 
    return actionMapping.findForward(flag);
  }
  
  private void getUserManaStock(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = "";
    if (httpSession.getAttribute("domainId").toString() != null && !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    httpServletRequest.setAttribute("stockList", (
        new StockBD())
        .getUserManaStock(httpSession
          .getAttribute("userId").toString(), domainId));
  }
  
  private void getDrawDept(HttpServletRequest httpServletRequest) {
    String view = "organizationVO.orgId,organizationVO.orgName";
    String from = "com.js.system.vo.organizationmanager.OrganizationVO organizationVO";
    String where = "order by organizationVO.orgId desc";
    Page page = new Page(view, from, where);
    page.setPageSize(10000);
    page.setcurrentPage(1);
    List list = page.getResultList();
    httpServletRequest.setAttribute("drawDeptList", list);
  }
  
  private void drawDeptList(HttpServletRequest httpServletRequest, String view, String from, String where) {
    Page page = new Page(view, from, where);
    page.setPageSize(10000);
    page.setcurrentPage(1);
    List list = page.getResultList();
    httpServletRequest.setAttribute("drawGoodsList", list);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(10000));
  }
}
