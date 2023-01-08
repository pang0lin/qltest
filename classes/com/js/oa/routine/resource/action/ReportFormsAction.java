package com.js.oa.routine.resource.action;

import com.js.oa.routine.resource.service.GoodsTypeBD;
import com.js.oa.routine.resource.service.ReportFormsBD;
import com.js.oa.routine.resource.service.StockBD;
import com.js.system.manager.service.ManagerService;
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

public class ReportFormsAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    ReportFormsActionForm reportFormsActionForm = 
      (ReportFormsActionForm)actionForm;
    String flag = (httpServletRequest.getParameter("flag") == null) ? "view" : 
      httpServletRequest.getParameter("flag");
    String tag = "view";
    if (flag.equals("view")) {
      tag = "view";
      getUserManaStock(httpServletRequest);
    } else if (flag.equals("search")) {
      tag = "search";
      if ("6".equals(httpServletRequest.getParameter("searchType"))) {
        search2(httpServletRequest, reportFormsActionForm);
        tag = "search2";
        if (httpServletRequest.getParameter("export") != null)
          tag = "search2_export"; 
      } else if ("8".equals(httpServletRequest.getParameter("searchType"))) {
        search4(httpServletRequest, reportFormsActionForm);
        tag = "search4";
        if (httpServletRequest.getParameter("export") != null)
          tag = "search4_export"; 
      } else if ("7".equals(httpServletRequest.getParameter("searchType"))) {
        search3(httpServletRequest, reportFormsActionForm);
        tag = "intoStockReport";
        if (httpServletRequest.getParameter("export") != null)
          tag = "intoStock_export"; 
      } else if ("9".equals(httpServletRequest.getParameter("searchType"))) {
        search6(httpServletRequest, reportFormsActionForm);
        tag = "tkdReport";
        if (httpServletRequest.getParameter("export") != null)
          tag = "tkd_export"; 
      } else if ("10".equals(httpServletRequest.getParameter("searchType"))) {
        search5(httpServletRequest, reportFormsActionForm);
        tag = "thdReport";
        if (httpServletRequest.getParameter("export") != null)
          tag = "thd_export"; 
      } else {
        search(httpServletRequest, reportFormsActionForm);
        if ("1".equals(httpServletRequest.getParameter("searchType")) || 
          "11".equals(httpServletRequest.getParameter("searchType"))) {
          tag = "search3";
          if (httpServletRequest.getParameter("export") != null)
            tag = "export"; 
        } else if (httpServletRequest.getParameter("export") != null && (
          "2".equals(httpServletRequest.getParameter("searchType")) || 
          "3".equals(httpServletRequest.getParameter("searchType")) || 
          "4".equals(httpServletRequest.getParameter("searchType")) || 
          "5".equals(httpServletRequest.getParameter("searchType")))) {
          tag = "export2345";
        } 
      } 
    } else if ("addChooseGoodsType".equals(flag)) {
      tag = "view";
      String searchStock = reportFormsActionForm.getSearchStock();
      String searchStockId = searchStock.substring(0, 
          searchStock.indexOf(","));
      String viewSql = "aaa.id,aaa.name,aaa.parentname";
      String fromSql = 
        "com.js.oa.routine.resource.po.GoodsTypePO aaa join aaa.stock bbb ";
      String whereSql = "where bbb.id =" + 
        searchStockId;
      Page page = new Page(viewSql, fromSql, whereSql);
      page.setPageSize(10000);
      page.setcurrentPage(1);
      List list = page.getResultList();
      httpServletRequest.setAttribute("goodsTypeList", list);
      getUserManaStock(httpServletRequest);
      httpServletRequest.setAttribute("searchType", 
          reportFormsActionForm.getSearchType());
      httpServletRequest.setAttribute("searchStock", 
          reportFormsActionForm
          .getSearchStock());
      httpServletRequest.setAttribute("searchCountType", 
          reportFormsActionForm
          .getSearchCountType());
      httpServletRequest.setAttribute("searchGoodsType", 
          reportFormsActionForm
          .getSearchGoodsType());
    } 
    return actionMapping.findForward(tag);
  }
  
  private void getUserManaStock(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = "";
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    ManagerService bookmanagerBD = new ManagerService();
    String stockWhere = bookmanagerBD.getScopeFinalWhere(httpSession.getAttribute("userId").toString(), httpSession.getAttribute("orgId").toString(), httpSession.getAttribute("orgIdString").toString(), "aaa.stockApplyExtensionId", "aaa.stockApplyExtensionId", "aaa.stockApplyExtensionId");
    stockWhere = String.valueOf(stockWhere) + " or aaa.stockPut like '%$" + httpSession.getAttribute("userId").toString() + "$%'";
    httpServletRequest.setAttribute("stockList", (
        new StockBD())
        .getUserManaStockSong(stockWhere, domainId));
  }
  
  private void search(HttpServletRequest httpServletRequest, ReportFormsActionForm reportFormsActionForm) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = "";
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    String searchStock = reportFormsActionForm.getSearchStock();
    String searchStockId = searchStock.substring(0, searchStock.indexOf(","));
    String searchStockName = searchStock.substring(searchStock.indexOf(",") + 
        1);
    String searchGoodsname = httpServletRequest.getParameter(
        "searchGoodsname");
    reportFormsActionForm.setSearchStockId(searchStockId);
    reportFormsActionForm.setSearchStockName(searchStockName);
    String searchType = reportFormsActionForm.getSearchType();
    String searchGoodsType = reportFormsActionForm.getSearchGoodsType();
    if (!"-1".equals(searchGoodsType)) {
      String[] obj = (new GoodsTypeBD()).getSingleGoodsType(searchGoodsType);
      httpServletRequest.setAttribute("goodsType", "物品类别：" + obj[0]);
    } 
    String checkFlag = httpServletRequest.getParameter("searchCheckFlag");
    String[] para = { searchType, searchStockId, 
        httpServletRequest.getParameter("searchStartDate"), 
        httpServletRequest.getParameter("searchEndDate"), 
        searchGoodsname, searchGoodsType };
    httpServletRequest.setAttribute("reportList", (
        new ReportFormsBD())
        .getReportForms(para, domainId));
  }
  
  private void search2(HttpServletRequest httpServletRequest, ReportFormsActionForm reportFormsActionForm) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = "";
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    String searchStock = reportFormsActionForm.getSearchStock();
    String searchStockId = searchStock.substring(0, searchStock.indexOf(","));
    String searchStockName = searchStock.substring(searchStock.indexOf(",") + 
        1);
    String deptId = httpServletRequest.getParameter("applyOrg");
    String deptName = httpServletRequest.getParameter("applyOrgName");
    String startDate = httpServletRequest.getParameter("searchStartDate");
    String endDate = httpServletRequest.getParameter("searchEndDate");
    String searchGoodsType = reportFormsActionForm.getSearchGoodsType();
    String tmpWhere = "";
    if (!searchGoodsType.equals("-1")) {
      String[] obj = (new GoodsTypeBD()).getSingleGoodsType(searchGoodsType);
      httpServletRequest.setAttribute("goodsType", "物品类别：" + obj[0]);
      tmpWhere = " and goodsPO.goodsType.id=" + searchGoodsType;
    } 
    String view = "ssDetailPO.goodsId,ssDetailPO.goodsName,ssDetailPO.amount,ssDetailPO.goodsUnit,ssDetailPO.factPrice,ssDetailPO.factMoney,ssMasterPO.ssMan,ssMasterPO.serial,ssMasterPO.checkDate";
    String from = "com.js.oa.routine.resource.po.SsDetailPO ssDetailPO join ssDetailPO.ssMaster ssMasterPO,com.js.oa.routine.resource.po.GoodsPO goodsPO";
    String checkFlag = httpServletRequest.getParameter("searchCheckFlag");
    String checkWhere = "";
    if (checkFlag.equals("0")) {
      checkWhere = 
        " and ssMasterPO.checkFlag='Y' and ssMasterPO.ssOutFlag='0' ";
    } else if (checkFlag.equals("1")) {
      checkWhere = " and  ssMasterPO.ssOutFlag='1' ";
    } else {
      checkWhere = 
        " and ssMasterPO.checkFlag='" + checkFlag + "' ";
    } 
    String lyPerson = httpServletRequest.getParameter("ly_person");
    if (lyPerson != null && !"null".equals(lyPerson))
      checkWhere = String.valueOf(checkWhere) + " and ssMasterPO.ssMan like '%" + lyPerson + "%' "; 
    String where = "";
    String databaseType = 
      SystemCommon.getDatabaseType();
    if (databaseType.indexOf("mysql") >= 0) {
      where = 
        " where ssDetailPO.goodsId=goodsPO.id and  ssMasterPO.domainid=" + 
        domainId + 
        " and ssMasterPO.ssStock ='" + searchStockId + 
        "' and ssMasterPO.ssDept = '" + deptId + 
        "' and ssMasterPO.checkDate between '" + startDate + 
        " 00:00:00' and '" + endDate + 
        " 23:59:59' " + tmpWhere + checkWhere + " order by ssDetailPO.goodsId";
    } else {
      where = 
        " where ssDetailPO.goodsId=goodsPO.id and ssMasterPO.domainid=" + 
        domainId + 
        " and ssMasterPO.ssStock ='" + searchStockId + 
        "' and ssMasterPO.ssDept in ( select org.orgId from  com.js.system.vo.organizationmanager.OrganizationVO org where org.orgIdString like '%$" + 
        deptId + 
        "$%') and ssMasterPO.checkDate between JSDB.FN_STRTODATE('" + 
        startDate + " 00:00:00','L') and JSDB.FN_STRTODATE('" + 
        endDate + " 23:59:59','L') " + tmpWhere + checkWhere + 
        " order by ssDetailPO.goodsId";
    } 
    Page page = new Page(view, from, where);
    page.setPageSize(10000);
    page.setcurrentPage(1);
    List list = page.getResultList();
    httpServletRequest.setAttribute("stockName", searchStockName);
    httpServletRequest.setAttribute("deptName", deptName);
    httpServletRequest.setAttribute("deptId", deptId);
    httpServletRequest.setAttribute("startDate", startDate);
    httpServletRequest.setAttribute("endDate", endDate);
    httpServletRequest.setAttribute("drawGoodsList", list);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(10000));
  }
  
  private void search3(HttpServletRequest httpServletRequest, ReportFormsActionForm reportFormsActionForm) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = "";
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    String searchStock = reportFormsActionForm.getSearchStock();
    String searchStockId = searchStock.substring(0, searchStock.indexOf(","));
    String searchStockName = searchStock.substring(searchStock.indexOf(",") + 
        1);
    String startDate = httpServletRequest.getParameter("searchStartDate");
    String endDate = httpServletRequest.getParameter("searchEndDate");
    String searchGoodsType = reportFormsActionForm.getSearchGoodsType();
    String supp = reportFormsActionForm.getSupp();
    String invoiceNO = reportFormsActionForm.getInvoiceNO();
    String tmpWhere = "";
    if (!searchGoodsType.equals("-1")) {
      String[] obj = (new GoodsTypeBD()).getSingleGoodsType(searchGoodsType);
      httpServletRequest.setAttribute("goodsType", "物品类别：" + obj[0]);
      tmpWhere = String.valueOf(tmpWhere) + " and goodsPO.goodsType.id=" + searchGoodsType;
    } 
    if (supp != null && supp.trim().length() > 0)
      tmpWhere = String.valueOf(tmpWhere) + " and   ptMasterPO.ptSupp like '%" + supp + "%'"; 
    if (invoiceNO != null && invoiceNO.trim().length() > 0)
      tmpWhere = String.valueOf(tmpWhere) + " and   ptMasterPO.serial like '%" + invoiceNO + "%'"; 
    String view = "ptMasterPO.serial,ptMasterPO.ptSupp,ptDetailPO.goodsName,ptDetailPO.goodsUnit,ptDetailPO.amount,ptDetailPO.mcost,ptDetailPO.goodsMoney,ptDetailPO.goodsId,ptMasterPO.ptDate";
    String from = "com.js.oa.routine.resource.po.PtDetailPO ptDetailPO join ptDetailPO.ptMaster ptMasterPO,com.js.oa.routine.resource.po.GoodsPO goodsPO";
    String where = "";
    String databaseType = 
      SystemCommon.getDatabaseType();
    if (databaseType.indexOf("mysql") >= 0) {
      where = 
        " where ptDetailPO.goodsId=goodsPO.id and  ptMasterPO.domainid=" + 
        domainId + 
        " and ptMasterPO.checkFlag='Y' and ptMasterPO.ptStock ='" + searchStockId + 
        "' and ptMasterPO.ptDate between '" + startDate + 
        " 00:00:00' and '" + endDate + 
        " 23:59:59' " + tmpWhere + " order by ptDetailPO.goodsId";
    } else {
      where = 
        " where ptDetailPO.goodsId=goodsPO.id and ptMasterPO.domainid=" + 
        domainId + 
        "  and ptMasterPO.checkFlag='Y' and ptMasterPO.ptStock ='" + searchStockId + 
        "' and ptMasterPO.ptDate between JSDB.FN_STRTODATE('" + 
        startDate + " 00:00:00','L') and JSDB.FN_STRTODATE('" + 
        endDate + " 23:59:59','L') " + tmpWhere + 
        " order by ptDetailPO.goodsId";
    } 
    Page page = new Page(view, from, where);
    page.setPageSize(999999);
    page.setcurrentPage(1);
    List list = page.getResultList();
    httpServletRequest.setAttribute("stockName", searchStockName);
    httpServletRequest.setAttribute("startDate", startDate);
    httpServletRequest.setAttribute("endDate", endDate);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(999999));
    httpServletRequest.setAttribute("intoStockList", list);
  }
  
  private void search4(HttpServletRequest httpServletRequest, ReportFormsActionForm reportFormsActionForm) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = "";
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    String searchStock = reportFormsActionForm.getSearchStock();
    String searchStockId = searchStock.substring(0, searchStock.indexOf(","));
    String searchStockName = searchStock.substring(searchStock.indexOf(",") + 
        1);
    String deptId = httpServletRequest.getParameter("applyOrg");
    String deptName = httpServletRequest.getParameter("applyOrgName");
    String startDate = httpServletRequest.getParameter("searchStartDate");
    String endDate = httpServletRequest.getParameter("searchEndDate");
    String searchGoodsType = reportFormsActionForm.getSearchGoodsType();
    String tmpWhere = "";
    if (!searchGoodsType.equals("-1")) {
      String[] obj = (new GoodsTypeBD()).getSingleGoodsType(searchGoodsType);
      httpServletRequest.setAttribute("goodsType", "物品类别：" + obj[0]);
      tmpWhere = " and goodsPO.goodsType.id=" + searchGoodsType;
    } 
    String view = "ssDetailPO.goodsId,ssDetailPO.goodsName,ssDetailPO.amount,ssDetailPO.goodsUnit,ssDetailPO.factPrice,ssDetailPO.factMoney,ssMasterPO.serial,ssMasterPO.ssUseMan,ssMasterPO.ssDate";
    String from = "com.js.oa.routine.resource.po.SsDetailPO ssDetailPO join ssDetailPO.ssMaster ssMasterPO,com.js.oa.routine.resource.po.GoodsPO goodsPO";
    String checkFlag = httpServletRequest.getParameter("searchCheckFlag");
    String checkWhere = "";
    checkWhere = " and ssMasterPO.checkFlag='Y' and ssMasterPO.ssOutFlag='0' ";
    String where = "";
    String databaseType = 
      SystemCommon.getDatabaseType();
    if (databaseType.indexOf("mysql") >= 0) {
      where = 
        " where ssDetailPO.goodsId=goodsPO.id and  ssMasterPO.domainid=" + 
        domainId + 
        " and ssMasterPO.ssStock ='" + searchStockId + 
        "' and ssMasterPO.ssDept = '" + deptId + 
        "' and ssMasterPO.ssDate between '" + startDate + 
        " 00:00:00' and '" + endDate + 
        " 23:59:59' " + tmpWhere + checkWhere + " order by ssDetailPO.goodsId";
    } else {
      where = 
        " where ssDetailPO.goodsId=goodsPO.id and ssMasterPO.domainid=" + 
        domainId + 
        " and ssMasterPO.ssStock ='" + searchStockId + 
        "' and ssMasterPO.ssDept in ( select org.orgId from  com.js.system.vo.organizationmanager.OrganizationVO org where org.orgIdString like '%$" + 
        deptId + 
        "$%') and ssMasterPO.ssDate between JSDB.FN_STRTODATE('" + 
        startDate + " 00:00:00','L') and JSDB.FN_STRTODATE('" + 
        endDate + " 23:59:59','L') " + tmpWhere + checkWhere + 
        " order by ssDetailPO.goodsId";
    } 
    Page page = new Page(view, from, where);
    page.setPageSize(10000);
    page.setcurrentPage(1);
    List list = page.getResultList();
    httpServletRequest.setAttribute("stockName", searchStockName);
    httpServletRequest.setAttribute("deptName", deptName);
    httpServletRequest.setAttribute("deptId", deptId);
    httpServletRequest.setAttribute("startDate", startDate);
    httpServletRequest.setAttribute("endDate", endDate);
    httpServletRequest.setAttribute("drawGoodsList", list);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(10000));
  }
  
  private void search5(HttpServletRequest httpServletRequest, ReportFormsActionForm reportFormsActionForm) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = "";
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    String searchStock = reportFormsActionForm.getSearchStock();
    String searchStockId = searchStock.substring(0, searchStock.indexOf(","));
    String searchStockName = searchStock.substring(searchStock.indexOf(",") + 
        1);
    String startDate = httpServletRequest.getParameter("searchStartDate");
    String endDate = httpServletRequest.getParameter("searchEndDate");
    String searchGoodsType = reportFormsActionForm.getSearchGoodsType();
    String supp = reportFormsActionForm.getSupp();
    String invoiceNO = reportFormsActionForm.getInvoiceNO();
    String tmpWhere = "";
    if (!searchGoodsType.equals("-1")) {
      String[] obj = (new GoodsTypeBD()).getSingleGoodsType(searchGoodsType);
      httpServletRequest.setAttribute("goodsType", "物品类别：" + obj[0]);
      tmpWhere = String.valueOf(tmpWhere) + " and goodsPO.goodsType.id=" + searchGoodsType;
    } 
    if (supp != null && supp.trim().length() > 0)
      tmpWhere = String.valueOf(tmpWhere) + " and   ptMasterPO.ptSupp like '%" + supp + "%'"; 
    if (invoiceNO != null && invoiceNO.trim().length() > 0)
      tmpWhere = String.valueOf(tmpWhere) + " and   ptMasterPO.serial like '%" + invoiceNO + "%'"; 
    tmpWhere = String.valueOf(tmpWhere) + " and ptMasterPO.serial like '%THD%'";
    String view = "ptMasterPO.serial,ptMasterPO.ptSupp,ptDetailPO.goodsName,ptDetailPO.goodsUnit,ptDetailPO.amount,ptDetailPO.mcost,ptDetailPO.goodsMoney,ptDetailPO.goodsId,ptMasterPO.ptMan,ptMasterPO.checkDate";
    String from = "com.js.oa.routine.resource.po.PtDetailPO ptDetailPO join ptDetailPO.ptMaster ptMasterPO,com.js.oa.routine.resource.po.GoodsPO goodsPO";
    String where = "";
    String databaseType = 
      SystemCommon.getDatabaseType();
    if (databaseType.indexOf("mysql") >= 0) {
      where = 
        " where ptDetailPO.goodsId=goodsPO.id and  ptMasterPO.domainid=" + 
        domainId + 
        " and ptMasterPO.checkFlag='Y' and ptMasterPO.ptStock ='" + 
        searchStockId + 
        "' and ptMasterPO.checkDate between '" + startDate + 
        " 00:00:00' and '" + endDate + 
        " 23:59:59' " + tmpWhere + " order by ptDetailPO.goodsId";
    } else {
      where = 
        " where ptDetailPO.goodsId=goodsPO.id and ptMasterPO.domainid=" + 
        domainId + 
        "  and ptMasterPO.checkFlag='Y' and ptMasterPO.ptStock ='" + 
        searchStockId + 
        "' and ptMasterPO.checkDate between JSDB.FN_STRTODATE('" + 
        startDate + " 00:00:00','L') and JSDB.FN_STRTODATE('" + 
        endDate + " 23:59:59','L') " + tmpWhere + 
        " order by ptDetailPO.goodsId";
    } 
    Page page = new Page(view, from, where);
    page.setPageSize(999999);
    page.setcurrentPage(1);
    List list = page.getResultList();
    httpServletRequest.setAttribute("stockName", searchStockName);
    httpServletRequest.setAttribute("startDate", startDate);
    httpServletRequest.setAttribute("endDate", endDate);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(999999));
    httpServletRequest.setAttribute("intoStockList", list);
  }
  
  private void search6(HttpServletRequest httpServletRequest, ReportFormsActionForm reportFormsActionForm) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = "";
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    String searchStock = reportFormsActionForm.getSearchStock();
    String searchStockId = searchStock.substring(0, searchStock.indexOf(","));
    String searchStockName = searchStock.substring(searchStock.indexOf(",") + 
        1);
    String deptId = httpServletRequest.getParameter("applyOrg");
    String deptName = httpServletRequest.getParameter("applyOrgName");
    String startDate = httpServletRequest.getParameter("searchStartDate");
    String endDate = httpServletRequest.getParameter("searchEndDate");
    String searchGoodsType = reportFormsActionForm.getSearchGoodsType();
    String tmpWhere = "";
    if (!"-1".equals(searchGoodsType)) {
      String[] obj = (new GoodsTypeBD()).getSingleGoodsType(searchGoodsType);
      httpServletRequest.setAttribute("goodsType", "物品类别：" + obj[0]);
      tmpWhere = " and goodsPO.goodsType.id=" + searchGoodsType;
    } 
    tmpWhere = String.valueOf(tmpWhere) + " and ssMasterPO.serial like '%TKD%' and ssMasterPO.ssOutFlag='1' ";
    String view = "ssDetailPO.goodsId,ssDetailPO.goodsName,ssDetailPO.amount,ssDetailPO.goodsUnit,ssDetailPO.price,ssDetailPO.goodsMoney,ssMasterPO.serial,ssMasterPO.ssOrgName,ssMasterPO.ssUseMan,ssMasterPO.checkDate";
    String from = "com.js.oa.routine.resource.po.SsDetailPO ssDetailPO join ssDetailPO.ssMaster ssMasterPO,com.js.oa.routine.resource.po.GoodsPO goodsPO";
    String checkFlag = httpServletRequest.getParameter("searchCheckFlag");
    String checkWhere = "";
    checkWhere = " and ssMasterPO.checkFlag='Y' ";
    String where = "";
    String databaseType = 
      SystemCommon.getDatabaseType();
    if (databaseType.indexOf("mysql") >= 0) {
      where = 
        " where ssDetailPO.goodsId=goodsPO.id and  ssMasterPO.domainid=" + 
        domainId + 
        " and ssMasterPO.ssStock ='" + searchStockId + 
        
        "' and ssMasterPO.checkDate between '" + startDate + 
        " 00:00:00' and '" + endDate + 
        " 23:59:59' " + tmpWhere + checkWhere + 
        " order by ssDetailPO.goodsId";
    } else {
      where = 
        " where ssDetailPO.goodsId=goodsPO.id and ssMasterPO.domainid=" + 
        domainId + 
        " and ssMasterPO.ssStock ='" + searchStockId + 


        
        "' and ssMasterPO.checkDate between JSDB.FN_STRTODATE('" + 
        startDate + " 00:00:00','L') and JSDB.FN_STRTODATE('" + 
        endDate + " 23:59:59','L') " + tmpWhere + checkWhere + 
        " order by ssDetailPO.goodsId";
    } 
    Page page = new Page(view, from, where);
    page.setPageSize(999999);
    page.setcurrentPage(1);
    List list = page.getResultList();
    httpServletRequest.setAttribute("stockName", searchStockName);
    httpServletRequest.setAttribute("deptName", deptName);
    httpServletRequest.setAttribute("startDate", startDate);
    httpServletRequest.setAttribute("endDate", endDate);
    httpServletRequest.setAttribute("drawGoodsList", list);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(999999));
  }
}
