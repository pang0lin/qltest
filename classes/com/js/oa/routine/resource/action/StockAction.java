package com.js.oa.routine.resource.action;

import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.vo.ModuleVO;
import com.js.oa.routine.resource.po.StockPO;
import com.js.oa.routine.resource.service.StockBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.page.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class StockAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    StockActionForm stockActionForm = (StockActionForm)actionForm;
    String flag = (httpServletRequest.getParameter("flag") == null) ? "view" : 
      httpServletRequest.getParameter("flag");
    String tag = "view";
    if (flag.equals("view") || flag.equals("search") || 
      flag.equals("delete")) {
      tag = "view";
      HttpSession httpSession = httpServletRequest.getSession(true);
      String domainId = "";
      if (httpSession.getAttribute("domainId") != null && 
        !httpSession.getAttribute("domainId").equals("null"))
        domainId = httpSession.getAttribute("domainId").toString(); 
      httpServletRequest.setAttribute("searchStock", (
          new StockBD()).getStockIDName(
            domainId));
      httpServletRequest.setAttribute("vindicate", 
          getVindicate(httpServletRequest));
      httpServletRequest.setAttribute("addRight", 
          new Boolean((new ManagerService())
            .hasRightTypeName(httpSession.getAttribute("userId")
              .toString(), "物品管理-仓库管理", "维护")));
      if (flag.equals("view") || flag.equals("delete")) {
        if (flag.equals("delete")) {
          delete(httpServletRequest);
          search(httpServletRequest);
        } else {
          view(httpServletRequest);
        } 
      } else if (flag.equals("search")) {
        search(httpServletRequest);
      } 
    } else if (flag.equals("continue") || flag.equals("close")) {
      tag = "add";
      if (save(stockActionForm, httpServletRequest).booleanValue()) {
        if (flag.equals("continue")) {
          httpServletRequest.setAttribute("close", "0");
        } else {
          httpServletRequest.setAttribute("close", "1");
        } 
      } else {
        httpServletRequest.setAttribute("close", "2");
      } 
    } else if (flag.equals("modify") || flag.equals("update")) {
      tag = "modify";
      if (flag.equals("modify")) {
        getSingleStock(stockActionForm, httpServletRequest);
        httpServletRequest.setAttribute("pager.offset", httpServletRequest.getParameter("pager.offset"));
        httpServletRequest.setAttribute("searchStock_old", httpServletRequest.getParameter("searchStock_old"));
      } else {
        httpServletRequest.setAttribute("pager.offset", httpServletRequest.getParameter("pager.offset"));
        httpServletRequest.setAttribute("searchStock_old", httpServletRequest.getParameter("searchStock_old"));
        if (update(stockActionForm, httpServletRequest).booleanValue()) {
          httpServletRequest.setAttribute("close", "1");
        } else {
          httpServletRequest.setAttribute("close", "2");
          getSingleStock(stockActionForm, httpServletRequest);
        } 
      } 
    } 
    ModuleVO moduleVO = new ModuleVO();
    moduleVO.setId(13);
    moduleVO.setFormType(0);
    httpServletRequest.setAttribute("tableVO", (
        new WorkFlowBD())
        .getAccessTable(moduleVO).get(0));
    return actionMapping.findForward(tag);
  }
  
  private Boolean save(StockActionForm stockActionForm, HttpServletRequest httpServletRequest) {
    StockPO stockPO = new StockPO();
    stockPO.setStockName(stockActionForm.getStockName());
    stockPO.setRemark(stockActionForm.getRemark());
    stockPO.setStockDesci(stockActionForm.getStockDesci());
    stockPO.setStockPut(stockActionForm.getStockPut());
    stockPO.setStockPutName(stockActionForm.getStockPutName());
    stockPO.setStockApplyExtension(stockActionForm.getStockApplyExtension());
    stockPO.setStockApplyExtensionId(stockActionForm
        .getStockApplyExtensionId());
    HttpSession httpSession = httpServletRequest.getSession(true);
    stockPO.setCreatedEmp(new Long(httpSession.getAttribute("userId")
          .toString()));
    stockPO.setCreatedOrg(new Long(httpSession.getAttribute("orgId")
          .toString()));
    if (stockActionForm.getChukuShenhe().intValue() == 1) {
      String shenhe = "";
      if ("1".equals(stockActionForm.getShenhe1())) {
        shenhe = String.valueOf(shenhe) + "1";
      } else {
        shenhe = String.valueOf(shenhe) + "0";
      } 
      if ("1".equals(stockActionForm.getShenhe2())) {
        shenhe = String.valueOf(shenhe) + "1";
      } else {
        shenhe = String.valueOf(shenhe) + "0";
      } 
      if ("1".equals(stockActionForm.getShenhe3())) {
        shenhe = String.valueOf(shenhe) + "1";
      } else {
        shenhe = String.valueOf(shenhe) + "0";
      } 
      if ("1".equals(stockActionForm.getShenhe4())) {
        shenhe = String.valueOf(shenhe) + "1";
      } else {
        shenhe = String.valueOf(shenhe) + "0";
      } 
      stockPO.setChukuShenhe(shenhe);
    } else {
      stockPO.setChukuShenhe("0000");
    } 
    stockPO.setIsKucun(stockActionForm.getIsKucun());
    stockPO.setIsKucunYj(stockActionForm.getIsKucunYj());
    String domainId = "";
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    if (!domainId.equals("") || domainId.length() > 0)
      stockPO.setDomainid(Integer.parseInt(domainId)); 
    StockBD stockBD = new StockBD();
    return stockBD.save(stockPO);
  }
  
  private void view(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = "";
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    String viewSql = 
      "distinct aaa.id,aaa.stockName,aaa.stockPutName,aaa.stockApplyExtension,aaa.chukuShenhe";
    String fromSql = 
      "com.js.oa.routine.resource.po.StockPO aaa ";
    String curUserId = httpServletRequest.getSession(true).getAttribute("userId").toString();
    String orgID = httpServletRequest.getSession(true).getAttribute("orgId").toString();
    ManagerService mbd = new ManagerService();
    String whereSQL = mbd.getRightFinalWhere(curUserId, orgID, "07*03*02", 
        "aaa.createdOrg", 
        "aaa.createdEmp");
    whereSQL = "( (" + whereSQL + ") or aaa.createdEmp=" + curUserId + ")";
    String whereSql = " where aaa.domainid=" + domainId + " and " + 
      whereSQL + " order by aaa.id desc";
    list(httpServletRequest, viewSql, fromSql, whereSql);
  }
  
  private void list(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    if (list != null && 
      list.size() == 0 && offset >= 15) {
      offset -= 15;
      currentPage = offset / pageSize + 1;
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      httpServletRequest.setAttribute("new.offset", (new StringBuilder(String.valueOf(offset))).toString());
      httpServletRequest.setAttribute("pager.realCurrent", (
          new StringBuilder(String.valueOf(currentPage))).toString());
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("stockList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "flag,searchStock");
  }
  
  private void search(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = "";
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    String searchStock = httpServletRequest.getParameter("searchStock");
    httpServletRequest.setAttribute("searchStockId", searchStock);
    String viewSql = 
      "aaa.id,aaa.stockName,aaa.stockPutName,aaa.stockApplyExtension,aaa.chukuShenhe";
    String fromSql = 
      "com.js.oa.routine.resource.po.StockPO aaa";
    String curUserId = httpServletRequest.getSession(true).getAttribute(
        "userId")
      .toString();
    String orgID = httpServletRequest.getSession(true).getAttribute("orgId")
      .toString();
    ManagerService mbd = new ManagerService();
    String whereSQL = mbd.getRightFinalWhere(curUserId, orgID, "07*03*02", 
        "aaa.createdOrg", 
        "aaa.createdEmp");
    whereSQL = "( (" + whereSQL + ") or aaa.createdEmp=" + curUserId + ")";
    String whereSql = "where aaa.stockName like '%" + searchStock + "%'" + 
      " and aaa.domainid=" + domainId + " and " + whereSQL + 
      " order by aaa.id desc";
    list(httpServletRequest, viewSql, fromSql, whereSql);
  }
  
  private void delete(HttpServletRequest httpServletRequest) {
    String stockIds = httpServletRequest.getParameter("stockId");
    httpServletRequest.setAttribute("deleteSuccess", (
        new StockBD()).delete(stockIds));
  }
  
  private void getSingleStock(StockActionForm stockActionForm, HttpServletRequest httpServletRequest) {
    String stockId = httpServletRequest.getParameter("stockId");
    String vindicate = (httpServletRequest.getParameter("vindicate") == null) ? 
      "" : httpServletRequest.getParameter("vindicate");
    String[] stock = (new StockBD()).getSingleStock(stockId);
    stockActionForm.setStockName(stock[0]);
    stockActionForm.setStockPut(stock[1]);
    stockActionForm.setStockPutName(stock[2]);
    stockActionForm.setStockDesci(stock[3]);
    stockActionForm.setRemark(stock[4]);
    stockActionForm.setStockApplyExtension(stock[5]);
    stockActionForm.setStockApplyExtensionId(stock[6]);
    String shenhe = stock[7];
    if (shenhe.indexOf("1") != -1) {
      stockActionForm.setChukuShenhe(new Integer(1));
      String shenhe1 = shenhe.substring(0, 1);
      String shenhe2 = shenhe.substring(1, 2);
      String shenhe3 = shenhe.substring(2, 3);
      String shenhe4 = shenhe.substring(3, 4);
      if ("1".equals(shenhe1)) {
        stockActionForm.setShenhe1("1");
      } else {
        stockActionForm.setShenhe1("0");
      } 
      if ("1".equals(shenhe2)) {
        stockActionForm.setShenhe2("1");
      } else {
        stockActionForm.setShenhe2("0");
      } 
      if ("1".equals(shenhe3)) {
        stockActionForm.setShenhe3("1");
      } else {
        stockActionForm.setShenhe3("0");
      } 
      if ("1".equals(shenhe4)) {
        stockActionForm.setShenhe4("1");
      } else {
        stockActionForm.setShenhe4("0");
      } 
    } else {
      stockActionForm.setChukuShenhe(new Integer(0));
    } 
    stockActionForm.setIsKucun(new Integer(stock[8]));
    stockActionForm.setIsKucunYj(new Integer(stock[9]));
    httpServletRequest.setAttribute("stockId", stockId);
    if (vindicate.indexOf("$" + stockId + "$") >= 0) {
      httpServletRequest.setAttribute("canModify", new Boolean(true));
    } else {
      httpServletRequest.setAttribute("canModify", new Boolean(false));
    } 
  }
  
  private Boolean update(StockActionForm stockActionForm, HttpServletRequest httpServletRequest) {
    StockPO stockPO = new StockPO();
    stockPO.setId(new Long(httpServletRequest.getParameter("stockId")));
    stockPO.setStockName(stockActionForm.getStockName());
    stockPO.setStockPut(stockActionForm.getStockPut());
    stockPO.setStockPutName(stockActionForm.getStockPutName());
    stockPO.setStockDesci(stockActionForm.getStockDesci());
    stockPO.setRemark(stockActionForm.getRemark());
    stockPO.setStockApplyExtension(stockActionForm.getStockApplyExtension());
    stockPO.setStockApplyExtensionId(stockActionForm
        .getStockApplyExtensionId());
    if (stockActionForm.getChukuShenhe().intValue() == 1) {
      String shenhe = "";
      if ("1".equals(stockActionForm.getShenhe1())) {
        shenhe = String.valueOf(shenhe) + "1";
      } else {
        shenhe = String.valueOf(shenhe) + "0";
      } 
      if ("1".equals(stockActionForm.getShenhe2())) {
        shenhe = String.valueOf(shenhe) + "1";
      } else {
        shenhe = String.valueOf(shenhe) + "0";
      } 
      if ("1".equals(stockActionForm.getShenhe3())) {
        shenhe = String.valueOf(shenhe) + "1";
      } else {
        shenhe = String.valueOf(shenhe) + "0";
      } 
      if ("1".equals(stockActionForm.getShenhe4())) {
        shenhe = String.valueOf(shenhe) + "1";
      } else {
        shenhe = String.valueOf(shenhe) + "0";
      } 
      stockPO.setChukuShenhe(shenhe);
    } else {
      stockPO.setChukuShenhe("0000");
    } 
    stockPO.setIsKucun(stockActionForm.getIsKucun());
    stockPO.setIsKucunYj(stockActionForm.getIsKucunYj());
    String domainId = "";
    HttpSession httpSession = httpServletRequest.getSession(true);
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    stockPO.setDomainid(Integer.parseInt(domainId));
    return (new StockBD()).update(stockPO);
  }
  
  private String getVindicate(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String where = (new ManagerService()).getRightFinalWhere(httpSession
        .getAttribute("userId").toString(), 
        httpSession.getAttribute("orgId").toString(), 
        httpSession.getAttribute("orgIdString").toString(), 
        "物品管理-仓库管理", "维护", "aaa.createdOrg", "aaa.createdEmp");
    return (new StockBD()).getVindicate(where);
  }
}
