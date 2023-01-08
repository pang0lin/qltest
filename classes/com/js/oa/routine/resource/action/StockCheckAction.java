package com.js.oa.routine.resource.action;

import com.js.oa.routine.resource.po.CsMasterPO;
import com.js.oa.routine.resource.service.IntoOutStockBD;
import com.js.oa.routine.resource.service.StockBD;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class StockCheckAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    StockCheckActionForm stockCheckActionForm = (StockCheckActionForm)actionForm;
    String flag = (httpServletRequest.getParameter("flag") == null) ? "view" : httpServletRequest.getParameter("flag").toString();
    String tag = "view";
    if (flag.equals("view") || flag.equals("search") || flag.equals("delete")) {
      tag = "view";
      if (flag.equals("delete"))
        httpServletRequest.setAttribute("deleteSuccess", delete(httpServletRequest)); 
      if (flag.equals("search")) {
        search(httpServletRequest);
      } else {
        view(httpServletRequest);
      } 
    } else if (flag.equals("add") || flag.equals("continue") || flag.equals("close")) {
      tag = "add";
      getStockGoods(httpServletRequest);
      if (flag.equals("continue") || flag.equals("close"))
        if (save(stockCheckActionForm, httpServletRequest)) {
          if (flag.equals("continue")) {
            httpServletRequest.setAttribute("close", "0");
          } else {
            httpServletRequest.setAttribute("close", "1");
          } 
        } else {
          httpServletRequest.setAttribute("close", "2");
        }  
    } else if (flag.equals("modify") || flag.equals("update") || flag.equals("check")) {
      tag = "modify";
      if (flag.equals("modify")) {
        getStockCheck(stockCheckActionForm, httpServletRequest);
      } else if (update(stockCheckActionForm, httpServletRequest)) {
        httpServletRequest.setAttribute("close", "1");
      } else {
        httpServletRequest.setAttribute("close", "2");
        getStockCheck(stockCheckActionForm, httpServletRequest);
      } 
    } else if (flag.equals("unCheck")) {
      tag = "modify";
      if (unCheck(stockCheckActionForm)) {
        httpServletRequest.setAttribute("close", "1");
      } else {
        httpServletRequest.setAttribute("close", "2");
      } 
    } 
    return actionMapping.findForward(tag);
  }
  
  private void view(HttpServletRequest httpServletRequest) {
    getUserManaStock(httpServletRequest);
    String viewSql = "aaa.id,aaa.csDate,aaa.csMan,aaa.checkFlag";
    String fromSql = "com.js.oa.routine.resource.po.CsMasterPO aaa ";
    String whereSql = "where aaa.csStock=" + httpServletRequest.getParameter("stockId") + 
      " order by aaa.id desc";
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
    httpServletRequest.setAttribute("intoStockList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "flag,stockName,stockId,searchId,searchCsMan,searchStock,searchCheckFlag,searchStartDate,searchEndDate");
  }
  
  private void search(HttpServletRequest httpServletRequest) {
    String searchId = httpServletRequest.getParameter("searchId");
    String searchCsMan = httpServletRequest.getParameter("searchCsMan");
    String searchStock = httpServletRequest.getParameter("searchStock");
    String searchCheckFlag = httpServletRequest.getParameter("searchCheckFlag");
    String searchStartDate = httpServletRequest.getParameter("searchStartDate");
    String searchEndDate = httpServletRequest.getParameter("searchEndDate");
    String viewSql = "aaa.id,aaa.csDate,aaa.csMan,aaa.checkFlag";
    String fromSql = "com.js.oa.routine.resource.po.CsMasterPO aaa ";
    String tmpSql = "";
    String databaseType = SystemCommon.getDatabaseType();
    if (databaseType.indexOf("mysql") >= 0) {
      tmpSql = "where (aaa.csDate between '" + 
        searchStartDate + " 00:00:00' and '" + 
        searchEndDate + " 23:59:59') and aaa.csMan like '%" + 
        searchCsMan.trim() + "%' ";
    } else {
      tmpSql = "where (aaa.csDate between JSDB.FN_STRTODATE('" + 
        searchStartDate + " 00:00:00', 'L') and JSDB.FN_STRTODATE('" + 
        searchEndDate + " 23:59:59', 'L')) and aaa.csMan like '%" + 
        searchCsMan.trim() + "%' ";
    } 
    StringBuffer whereSql = new StringBuffer(tmpSql);
    if (!searchId.equals(""))
      whereSql.append(" and aaa.id=" + searchId + " "); 
    if (!searchStock.equals("0"))
      whereSql.append(" and aaa.csStock=" + searchStock + " "); 
    if (!searchCheckFlag.equals("0"))
      whereSql.append(" and aaa.checkFlag='" + searchCheckFlag + "' "); 
    whereSql.append(" order by aaa.id desc");
    list(httpServletRequest, viewSql, fromSql, whereSql.toString());
    getUserManaStock(httpServletRequest);
  }
  
  private void getUserManaStock(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = "";
    if (httpSession.getAttribute("domainId") != null && !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    httpServletRequest.setAttribute("stockList", (new StockBD()).getUserManaStock(httpSession.getAttribute("userId").toString(), domainId));
  }
  
  private void getStockGoods(HttpServletRequest httpServletRequest) {
    httpServletRequest.setAttribute("goodsList", (
        new IntoOutStockBD()).getStockGoods(httpServletRequest.getParameter("stockId")));
  }
  
  private boolean save(StockCheckActionForm stockCheckActionForm, HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    CsMasterPO csMasterPO = new CsMasterPO();
    csMasterPO.setCsStock(new Long(stockCheckActionForm.getStock()));
    csMasterPO.setCsMan(stockCheckActionForm.getCsMan());
    csMasterPO.setRemark(stockCheckActionForm.getRemark());
    csMasterPO.setMakeMan(new Long(httpSession.getAttribute("userId").toString()));
    csMasterPO.setMakeDate(new Date());
    csMasterPO.setCheckFlag("N");
    csMasterPO.setCsDate(new Date(httpServletRequest.getParameter("csDate")));
    String[] goodsId = httpServletRequest.getParameterValues("goodsId");
    String[] goodsName = httpServletRequest.getParameterValues("goodsName");
    String[] goodsUnit = httpServletRequest.getParameterValues("goodsUnit");
    String[] accAmount = httpServletRequest.getParameterValues("accAmount");
    String[] factAmount = httpServletRequest.getParameterValues("factAmount");
    String[] plAmount = httpServletRequest.getParameterValues("plAmount");
    Object[] para = { goodsId, goodsName, goodsUnit, accAmount, factAmount, plAmount };
    return (new IntoOutStockBD()).saveStockCheck(csMasterPO, para).booleanValue();
  }
  
  private void getStockCheck(StockCheckActionForm stockCheckActionForm, HttpServletRequest httpServletRequest) {
    String csMasterId = httpServletRequest.getParameter("csMasterId");
    Map stockCheckMap = (new IntoOutStockBD()).getStockCheck(csMasterId);
    CsMasterPO csMasterPO = (CsMasterPO)stockCheckMap.get("csMasterPO");
    stockCheckActionForm.setCsMasterId(csMasterPO.getId().toString());
    stockCheckActionForm.setStock(csMasterPO.getCsStock().toString());
    stockCheckActionForm.setCsMan(csMasterPO.getCsMan());
    stockCheckActionForm.setRemark(csMasterPO.getRemark());
    httpServletRequest.setAttribute("csDate", csMasterPO.getCsDate());
    httpServletRequest.setAttribute("stockName", stockCheckMap.get("stockName"));
    httpServletRequest.setAttribute("makeManName", stockCheckMap.get("makeManName"));
    stockCheckActionForm.setMakeMan(csMasterPO.getMakeMan().toString());
    httpServletRequest.setAttribute("makeDate", csMasterPO.getMakeDate());
    httpServletRequest.setAttribute("checkFlag", csMasterPO.getCheckFlag());
    httpServletRequest.setAttribute("checkDate", csMasterPO.getCheckDate());
    httpServletRequest.setAttribute("csDetail", csMasterPO.getCsDetail());
    httpServletRequest.setAttribute("checkManName", csMasterPO.getCheckManName());
  }
  
  private boolean update(StockCheckActionForm stockCheckActionForm, HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    CsMasterPO csMasterPO = new CsMasterPO();
    csMasterPO.setId(new Long(stockCheckActionForm.getCsMasterId()));
    csMasterPO.setCsDate(new Date(httpServletRequest.getParameter("csDate")));
    csMasterPO.setCsMan(stockCheckActionForm.getCsMan());
    csMasterPO.setRemark(stockCheckActionForm.getRemark());
    if (httpServletRequest.getParameter("flag").equals("check")) {
      csMasterPO.setCheckFlag("Y");
      csMasterPO.setCheckMan(new Long(httpSession.getAttribute("userId").toString()));
      csMasterPO.setCheckManName(httpSession.getAttribute("userName").toString());
      csMasterPO.setCheckDate(new Date());
    } else {
      csMasterPO.setCheckFlag("N");
    } 
    String[] goodsId = httpServletRequest.getParameterValues("goodsId");
    String[] accAmount = httpServletRequest.getParameterValues("accAmount");
    String[] factAmount = httpServletRequest.getParameterValues("factAmount");
    String[] plAmount = httpServletRequest.getParameterValues("plAmount");
    String[] goodsName = httpServletRequest.getParameterValues("goodsName");
    String[] goodsUnit = httpServletRequest.getParameterValues("goodsUnit");
    Object[] para = { goodsId, goodsName, goodsUnit, accAmount, factAmount, plAmount };
    return (new IntoOutStockBD()).updateStockCheck(csMasterPO, para).booleanValue();
  }
  
  private boolean unCheck(StockCheckActionForm stockCheckActionForm) {
    return (new IntoOutStockBD()).uncheckStockCheck(stockCheckActionForm.getCsMasterId()).booleanValue();
  }
  
  private Boolean delete(HttpServletRequest httpServletRequest) {
    return (new IntoOutStockBD()).deleteStockCheck(httpServletRequest.getParameter("csMasterId"));
  }
}
