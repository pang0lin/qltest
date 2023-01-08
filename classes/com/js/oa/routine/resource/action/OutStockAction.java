package com.js.oa.routine.resource.action;

import com.js.oa.jsflow.service.WorkFlowButtonBD;
import com.js.oa.jsflow.service.WorkFlowCommonBD;
import com.js.oa.jsflow.util.ProcessSubmit;
import com.js.oa.jsflow.util.WorkflowCommon;
import com.js.oa.jsflow.vo.WorkVO;
import com.js.oa.routine.resource.po.SsMasterPO;
import com.js.oa.routine.resource.service.DrawDeptBD;
import com.js.oa.routine.resource.service.IntoOutStockBD;
import com.js.oa.routine.resource.service.StockBD;
import com.js.oa.routine.resource.service.TypeDefineBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.BrowserJudge;
import java.util.ArrayList;
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

public class OutStockAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    OutStockActionForm outStockActionForm = (OutStockActionForm)actionForm;
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = "0";
    String flag = (httpServletRequest.getParameter("flag") == null) ? "view" : 
      httpServletRequest.getParameter("flag").toString();
    String personnalFlag = httpServletRequest.getParameter("personnalFlag");
    if ("1".equals(personnalFlag))
      flag = "personnalUseStock"; 
    String stockShow = SystemCommon.getStockShowUse();
    if ("1".equals(stockShow)) {
      String OutStock_Add = httpServletRequest.getParameter("OutStockfromMenu");
      String OutStockMarkStr = httpServletRequest.getParameter("OutStockfromMenu");
      if ("OutStock_Add".equals(OutStock_Add) || "OutStockMarkStr".equals(OutStockMarkStr)) {
        flag = "add";
        httpServletRequest.setAttribute("stockName", httpServletRequest.getParameter("stockName"));
        httpServletRequest.setAttribute("stockId", httpServletRequest.getParameter("stockId"));
        httpServletRequest.setAttribute("processId", httpServletRequest.getParameter("processId"));
        httpServletRequest.setAttribute("tableId", httpServletRequest.getParameter("tableId"));
        httpServletRequest.setAttribute("processName", httpServletRequest.getParameter("processName"));
        httpServletRequest.setAttribute("hasProcess", httpServletRequest.getParameter("hasProcess"));
        httpServletRequest.setAttribute("processType", httpServletRequest.getParameter("processType"));
        httpServletRequest.setAttribute("moduleId", httpServletRequest.getParameter("moduleId"));
        httpServletRequest.setAttribute("mode", httpServletRequest.getParameter("mode"));
        String goodsIds = httpServletRequest.getParameter("goodsIds_hidden");
        String chbm = httpServletRequest.getParameter("chbm_hidden");
        String chmc = httpServletRequest.getParameter("chmc_hidden");
        String jldw = httpServletRequest.getParameter("jldw_hidden");
        String dj = httpServletRequest.getParameter("dj_hidden");
        String ggxh = httpServletRequest.getParameter("ggxh_hidden");
        String kc = httpServletRequest.getParameter("kc_hidden");
        String[] goodsIdss = (String[])null;
        String[] djID = (String[])null;
        double totalMoney = 0.0D;
        if (goodsIds != null && !"".equals(goodsIds)) {
          goodsIdss = goodsIds.split(",");
          if (dj != null && !"".equals(dj))
            djID = dj.split(","); 
          for (int sq = 0; sq < goodsIdss.length; sq++)
            totalMoney += Double.parseDouble(djID[sq]); 
        } 
        outStockActionForm.setPtMoney((new StringBuilder(String.valueOf(totalMoney))).toString());
        httpServletRequest.setAttribute("goodsIds", goodsIds);
        httpServletRequest.setAttribute("chbm", chbm);
        httpServletRequest.setAttribute("chmc", chmc);
        httpServletRequest.setAttribute("jldw", jldw);
        httpServletRequest.setAttribute("dj", dj);
        httpServletRequest.setAttribute("ggxh", ggxh);
        httpServletRequest.setAttribute("kc", kc);
      } 
    } 
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    Object object = httpSession.getAttribute("userId");
    String tag = "view";
    if (flag.equals("view") || flag.equals("search") || 
      flag.equals("delete") || flag.equals("tuih") || 
      flag.equals("outFlag")) {
      if (flag.equals("delete"))
        httpServletRequest.setAttribute("deleteSuccess", 
            delete(httpServletRequest)); 
      if (flag.equals("tuih"))
        tuih(httpServletRequest); 
      if (flag.equals("outFlag")) {
        Map result = updateOutFlag(httpServletRequest);
        httpServletRequest.setAttribute("result", result);
        flag = "search";
      } 
      if (flag.equals("search")) {
        search(httpServletRequest, (String)object);
      } else {
        view(httpServletRequest, (String)object);
      } 
      if ("returnStock".equals(httpServletRequest.getParameter("mode")))
        tag = "returnview"; 
    } else if (flag.equals("add") || flag.equals("continue") || 
      flag.equals("close")) {
      tag = "add";
      TypeDefineBD tdbd = new TypeDefineBD();
      if (httpServletRequest.getParameter("mode") != null)
        if (httpServletRequest.getParameter("mode").equals(
            "outStock")) {
          httpServletRequest.setAttribute("typeDefineList", 
              tdbd.list("2", new Long(domainId)));
        } else {
          httpServletRequest.setAttribute("typeDefineList", 
              tdbd.list("3", new Long(domainId)));
        }  
      if (flag.equals("add"))
        getDeptInStock(httpServletRequest); 
      if (flag.equals("continue") || flag.equals("close"))
        if (save(outStockActionForm, httpServletRequest)) {
          if (flag.equals("continue")) {
            httpServletRequest.setAttribute("close", "0");
            getDeptInStock(httpServletRequest);
          } else {
            httpServletRequest.setAttribute("close", "1");
          } 
        } else {
          httpServletRequest.setAttribute("close", "2");
        }  
    } else if (flag.equals("showdetail")) {
      tag = "showdetail";
      getDeptInStock(httpServletRequest);
      getOutStock(outStockActionForm, httpServletRequest);
    } else if (flag.equals("modify") || flag.equals("update") || 
      flag.equals("check")) {
      tag = "modify";
      httpServletRequest.setAttribute("infoId", "0");
      if (flag.equals("modify")) {
        httpServletRequest.setAttribute("curWriteFields", (
            new WorkflowCommon())
            .getCurActivityWriteField(
              httpServletRequest));
        httpServletRequest.setAttribute("myform", outStockActionForm);
        getDeptInStock(httpServletRequest);
        getOutStock(outStockActionForm, httpServletRequest);
      } else {
        httpServletRequest.setAttribute("deptList", new ArrayList());
        if (update(outStockActionForm, httpServletRequest)) {
          httpServletRequest.setAttribute("close", "1");
        } else {
          httpServletRequest.setAttribute("close", "2");
          getOutStock(outStockActionForm, httpServletRequest);
        } 
      } 
    } else if (flag.equals("unCheck")) {
      tag = "modify";
      if (unCheck(outStockActionForm)) {
        httpServletRequest.setAttribute("close", "1");
      } else {
        httpServletRequest.setAttribute("close", "2");
        getDeptInStock(httpServletRequest);
        getOutStock(outStockActionForm, httpServletRequest);
      } 
    } else if (flag.equals("next")) {
      tag = "next";
      getNextStep(httpServletRequest);
    } else {
      if (flag.equals("oper")) {
        oper(httpServletRequest);
        return actionMapping.findForward("next");
      } 
      if ("comp".equals(flag)) {
        comp(httpServletRequest);
        return actionMapping.findForward("next");
      } 
      if ("reSub".equals(flag)) {
        reSub(httpServletRequest, outStockActionForm);
        tag = "add";
      } else if ("trans".equals(flag)) {
        trans(httpServletRequest, outStockActionForm);
        tag = "add";
      } 
    } 
    if ("listLoad".equals(flag)) {
      ActionForward forward = new ActionForward();
      forward.setPath(listLoad(httpServletRequest, httpServletResponse));
      return forward;
    } 
    if (flag.equals("myPrint") || flag.equals("print")) {
      tag = flag;
      getDeptInStock(httpServletRequest);
      getOutStock(outStockActionForm, httpServletRequest);
      getSsDetail(httpServletRequest);
    } 
    if ("personnalUseStock".equals(flag)) {
      personalApplayView(httpServletRequest, (String)object);
      tag = flag;
    } 
    return actionMapping.findForward(tag);
  }
  
  private void personalApplayView(HttpServletRequest httpServletRequest, String userId) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = "";
    ManagerService bookmanagerBD = new ManagerService();
    String stockWhere = bookmanagerBD.getScopeFinalWhere(httpSession.getAttribute("userId").toString(), httpSession.getAttribute("orgId").toString(), httpSession.getAttribute("orgIdString").toString(), "aaa.stockApplyExtensionId", "aaa.stockApplyExtensionId", "aaa.stockApplyExtensionId");
    stockWhere = String.valueOf(stockWhere) + " or aaa.stockPut like '%$" + httpSession.getAttribute("userId").toString() + "$%'";
    httpServletRequest.setAttribute("stockList", (
        new StockBD())
        .getUserManaStockSong(stockWhere, "0"));
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    String searchId = httpServletRequest.getParameter("searchId");
    String searchSsMan = httpServletRequest.getParameter("searchSsMan");
    String searchCheckFlag = httpServletRequest.getParameter("searchCheckFlag");
    String goodsName = httpServletRequest.getParameter("goodsName");
    String searchStartDate = httpServletRequest.getParameter("searchStartDate");
    String searchEndDate = httpServletRequest.getParameter("searchEndDate");
    String chickdate = httpServletRequest.getParameter("chickdate");
    String searchStock = httpServletRequest.getParameter("searchStock");
    String personnalFlag = httpServletRequest.getParameter("personnalFlag");
    httpServletRequest.setAttribute("personnalFlag", personnalFlag);
    String whereSql = "where  aaa.domainid=" + domainId;
    if (searchId != null && !"".equals(searchId))
      whereSql = String.valueOf(whereSql) + " and aaa.serial=" + searchId; 
    if (searchSsMan != null && !"".equals(searchSsMan))
      whereSql = String.valueOf(whereSql) + " and aaa.ssMan like '%" + searchSsMan + "%'"; 
    if (searchCheckFlag != null && !"".equals(searchCheckFlag))
      whereSql = String.valueOf(whereSql) + " and aaa.checkFlag='" + searchCheckFlag + "'"; 
    if (goodsName != null && !"".equals(goodsName))
      whereSql = String.valueOf(whereSql) + " and bbb.goodsName like '%" + goodsName + "%'"; 
    if (goodsName != null && !"".equals(goodsName))
      whereSql = String.valueOf(whereSql) + " and bbb.goodsName like '%" + goodsName + "%'"; 
    if (searchStock != null && !"".equals(searchStock))
      whereSql = String.valueOf(whereSql) + " and aaa.ssStock =" + searchStock + " "; 
    if ("true".equals(chickdate)) {
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        whereSql = String.valueOf(whereSql) + " and (aaa.checkDate>='" + searchStartDate + " 00:00:00' and aaa.checkDate<='" + searchEndDate + " 23:59:59')";
      } else {
        whereSql = String.valueOf(whereSql) + " and (aaa.checkDate>=JSDB.FN_STRTODATE('" + searchStartDate + " 00:00:00','') and aaa.checkDate<=JSDB.FN_STRTODATE('" + searchEndDate + " 23:59:59',''))";
      } 
    } 
    whereSql = String.valueOf(whereSql) + " and aaa.ssUseManID=" + userId;
    String viewSql = 
      "bbb.id,bbb.goodsName,bbb.amount,bbb.price,aaa.ssDate,aaa.ssMan,aaa.makeDate,aaa.ssUseMan,aaa.ssStock,aaa.checkFlag";
    String fromSql = "com.js.oa.routine.resource.po.SsMasterPO aaa join aaa.ssDetail bbb ";
    whereSql = String.valueOf(whereSql) + " order by aaa.id desc";
    personnalList(httpServletRequest, viewSql, fromSql, whereSql);
  }
  
  private void personnalList(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
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
      httpServletRequest.setAttribute("pager.realCurrent", (
          new StringBuilder(String.valueOf(currentPage))).toString());
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("personnalList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "flag,searchId,searchSsMan,searchCheckFlag,goodsName,searchStartDate,searchEndDate,chickdate,personnalFlag");
  }
  
  private void view(HttpServletRequest httpServletRequest, String userId) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = "";
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    if (isStockCharger(httpServletRequest.getParameter("stockId"), 
        userId)) {
      httpServletRequest.setAttribute("ischarge", "0");
    } else {
      httpServletRequest.setAttribute("ischarge", "1");
    } 
    getUserManaStock(httpServletRequest);
    getUserManaDept(httpServletRequest);
    IntoOutStockBD bd = new IntoOutStockBD();
    String stockId = httpServletRequest.getParameter("stockId");
    String stockType = "";
    String mode = "";
    mode = httpServletRequest.getParameter("mode");
    if ("outStock".equals(mode)) {
      stockType = "1";
    } else {
      stockType = "2";
    } 
    List list = bd.getWFProcessInfoByStockId(stockId, stockType);
    if (list != null && !list.isEmpty()) {
      httpServletRequest.setAttribute("tableId", list.get(0));
      httpServletRequest.setAttribute("processId", list.get(1));
      httpServletRequest.setAttribute("processName", list.get(2));
      httpServletRequest.setAttribute("hasProcess", "yes");
    } else {
      httpServletRequest.setAttribute("hasProcess", "no");
    } 
    String whereSql = "where aaa.ssStock=" + 
      httpServletRequest.getParameter("stockId") + 
      " and aaa.domainid=" + domainId;
    if ("outStock".equals(mode)) {
      whereSql = String.valueOf(whereSql) + " and aaa.ssMode='2' ";
    } else {
      whereSql = String.valueOf(whereSql) + " and aaa.ssMode='3' ";
    } 
    String[] stock = (new StockBD()).getSingleStock(httpServletRequest
        .getParameter("stockId"));
    Object object = httpSession.getAttribute("userId");
    if (stock[1].indexOf("$" + object + "$") > -1) {
      httpServletRequest.setAttribute("isStockPut", "1");
    } else {
      httpServletRequest.setAttribute("isStockPut", "0");
      whereSql = String.valueOf(whereSql) + " and aaa.makeMan=" + object;
    } 
    String viewSql = 
      "aaa.id,aaa.ssDate,aaa.ssMan,aaa.checkFlag,aaa.serial,aaa.ssOutFlag,aaa.ssOrgName,aaa.ssUseMan";
    String fromSql = "com.js.oa.routine.resource.po.SsMasterPO aaa ";
    whereSql = String.valueOf(whereSql) + " order by aaa.id desc";
    list(httpServletRequest, viewSql, fromSql, whereSql);
    httpServletRequest.setAttribute("searchDeptList", new ArrayList());
  }
  
  private void list(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
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
      httpServletRequest.setAttribute("pager.realCurrent", (
          new StringBuilder(String.valueOf(currentPage))).toString());
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("intoStockList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "flag,stockName,stockId,searchId,searchSsMan,searchDept,searchStock,searchCheckFlag,searchStartDate,searchEndDate,processId,tableId,processName,hasProcess,chickdate,mode,goodsName,processType,goods_specs");
  }
  
  private void getUserManaStock(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = "";
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    ManagerService bd = new ManagerService();
    String stockWhere = bd.getScopeFinalWhere(httpSession.getAttribute(
          "userId").toString(), 
        httpSession.getAttribute(
          "orgId").toString(), 
        httpSession.getAttribute(
          "orgIdString")
        .toString(), 
        "aaa.stockApplyExtensionId", 
        "aaa.stockApplyExtensionId", 
        "aaa.stockApplyExtensionId");
    stockWhere = String.valueOf(stockWhere) + " or aaa.stockPut like '%$" + 
      httpSession.getAttribute("userId").toString() + "$%'";
    httpServletRequest.setAttribute("stockList", (
        new StockBD())
        .getStockIDName(domainId, stockWhere));
  }
  
  private boolean save(OutStockActionForm outStockActionForm, HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    SsMasterPO ssMasterPO = new SsMasterPO();
    ssMasterPO.setSsStock(new Long(outStockActionForm.getStock()));
    ssMasterPO.setSsMan(outStockActionForm.getSsMan());
    ssMasterPO.setSsMoney(new Float(outStockActionForm.getPtMoney()));
    ssMasterPO.setRemark(outStockActionForm.getRemark());
    ssMasterPO.setMakeMan(new Long(httpSession.getAttribute("userId")
          .toString()));
    ssMasterPO.setMakeDate(new Date());
    ssMasterPO.setCheckFlag("Y");
    ssMasterPO.setSsDate(new Date(httpServletRequest.getParameter("ssDate")));
    ssMasterPO.setSsDept(new Long(outStockActionForm.getSsDept()));
    ssMasterPO.setCheckMan(new Long(httpSession.getAttribute("userId")
          .toString()));
    ssMasterPO.setCheckManName(httpSession.getAttribute("userName")
        .toString());
    ssMasterPO.setCheckDate(new Date());
    ssMasterPO.setSsHaveWorkFlow(new Long(0L));
    ssMasterPO.setSsOutFlag("0");
    ssMasterPO.setSsUseManID(outStockActionForm.getSsUseManID());
    ssMasterPO.setSsUseMan(outStockActionForm.getSsUseMan());
    String domainId = "";
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    if (!domainId.equals("") || domainId.length() > 0)
      ssMasterPO.setDomainid(Integer.parseInt(domainId)); 
    ssMasterPO.setSsOrg(outStockActionForm.getSsOrg());
    ssMasterPO.setSsOrgName(outStockActionForm.getSsOrgName());
    ssMasterPO.setSsTypeDefine(outStockActionForm.getSsTypeDefine());
    ssMasterPO.setSsMode("2");
    String[] goodsId = httpServletRequest.getParameterValues("goodsId");
    String[] amount = httpServletRequest.getParameterValues("amount");
    String[] price = httpServletRequest.getParameterValues("price");
    String[] money = httpServletRequest.getParameterValues("money");
    String[] goodsName = httpServletRequest.getParameterValues("goodsName");
    String[] goodsUnit = httpServletRequest.getParameterValues("goodsUnit");
    String[] goodsSpecs = httpServletRequest.getParameterValues("goodsSpecs");
    String[] returnReason = (httpServletRequest.getParameterValues("returnReason") == null) ? 
      new String[goodsId.length] : httpServletRequest.getParameterValues("returnReason");
    if (httpServletRequest.getParameter("mode").equals("returnStock")) {
      ssMasterPO.setSsMode("3");
      ssMasterPO.setSsMoney(new Float("-" + 
            outStockActionForm.getPtMoney()));
      for (int i = 0; i < amount.length; i++) {
        amount[i] = "-" + amount[i];
        money[i] = "-" + money[i];
      } 
    } 
    if (BrowserJudge.isOtherBrowser(httpServletRequest))
      for (int i = 0; i < goodsId.length; i++)
        goodsId[i] = String.valueOf(domainId) + "_" + goodsId[i];  
    Object[] para = { goodsId, amount, price, money, goodsName, goodsUnit, goodsSpecs, returnReason };
    Long recordId = (new IntoOutStockBD()).saveDirectOutStock(ssMasterPO, 
        para);
    return true;
  }
  
  private void getDeptInStock(HttpServletRequest httpServletRequest) {
    httpServletRequest.setAttribute("deptList", (
        new DrawDeptBD())
        .getDeptInStock(httpServletRequest
          .getParameter("stockId")));
  }
  
  private void getOutStock(OutStockActionForm outStockActionForm, HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = "0";
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    String ssMasterId = httpServletRequest.getParameter("record");
    Map outStockMap = (new IntoOutStockBD()).getOutStock(ssMasterId);
    SsMasterPO ssMasterPO = (SsMasterPO)outStockMap.get("ssMasterPO");
    outStockActionForm.setSerial(ssMasterPO.getSerial());
    outStockActionForm.setSsMasterId(ssMasterPO.getId().toString());
    outStockActionForm.setStock(ssMasterPO.getSsStock().toString());
    httpServletRequest.setAttribute("stockId", 
        ssMasterPO.getSsStock().toString());
    httpServletRequest.setAttribute("ssDept", ssMasterPO.getSsDept());
    httpServletRequest.setAttribute("SsUseMan", ssMasterPO.getSsUseMan());
    httpServletRequest.setAttribute("SsUseManID", ssMasterPO.getSsUseManID());
    outStockActionForm.setSsMan(ssMasterPO.getSsMan());
    outStockActionForm.setPtMoney(
        Math.abs(ssMasterPO.getSsMoney()
          .floatValue()));
    outStockActionForm.setRemark(ssMasterPO.getRemark());
    httpServletRequest.setAttribute("ssDate", ssMasterPO.getSsDate());
    httpServletRequest.setAttribute("stockName", 
        outStockMap.get("stockName"));
    httpServletRequest.setAttribute("makeManName", 
        outStockMap.get("makeManName"));
    httpServletRequest.setAttribute("ssDeptName", 
        outStockMap.get("ssDeptName"));
    outStockActionForm.setMakeMan(ssMasterPO.getMakeMan().toString());
    outStockActionForm.setSsOrg(ssMasterPO.getSsOrg());
    outStockActionForm.setSsOrgName(ssMasterPO.getSsOrgName());
    outStockActionForm.setSsMode(ssMasterPO.getSsMode());
    outStockActionForm.setSsTypeDefine(ssMasterPO.getSsTypeDefine());
    outStockActionForm.setSsUseMan(ssMasterPO.getSsUseMan());
    outStockActionForm.setSsUseManID(ssMasterPO.getSsUseManID());
    TypeDefineBD tdbd = new TypeDefineBD();
    httpServletRequest.setAttribute("typeDefineList", 
        tdbd.list(ssMasterPO.getSsMode(), 
          new Long(domainId)));
    httpServletRequest.setAttribute("ssTypeDefineSelected", 
        ssMasterPO.getSsTypeDefine());
    httpServletRequest.setAttribute("mode", ssMasterPO.getSsMode());
    httpServletRequest.setAttribute("makeDate", ssMasterPO.getMakeDate());
    httpServletRequest.setAttribute("checkFlag", ssMasterPO.getCheckFlag());
    httpServletRequest.setAttribute("checkDate", ssMasterPO.getCheckDate());
    httpServletRequest.setAttribute("ssDetail", ssMasterPO.getSsDetail());
    httpServletRequest.setAttribute("checkManName", 
        ssMasterPO.getCheckManName());
    httpServletRequest.setAttribute("checkManName", 
        ssMasterPO.getCheckManName());
    httpServletRequest.setAttribute("haveWorkFlow", 
        ssMasterPO.getSsHaveWorkFlow());
  }
  
  private boolean update(OutStockActionForm outStockActionForm, HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    SsMasterPO ssMasterPO = new SsMasterPO();
    ssMasterPO.setId(new Long(outStockActionForm.getSsMasterId()));
    ssMasterPO.setSsDate(new Date(httpServletRequest.getParameter("ssDate")));
    ssMasterPO.setSsDept(new Long(outStockActionForm.getSsDept()));
    ssMasterPO.setSsMan(outStockActionForm.getSsMan());
    ssMasterPO.setSsMoney(new Float(outStockActionForm.getPtMoney()));
    ssMasterPO.setRemark(outStockActionForm.getRemark());
    ssMasterPO.setSsOrg(outStockActionForm.getSsOrg());
    ssMasterPO.setSsOrgName(outStockActionForm.getSsOrgName());
    if (outStockActionForm.getSsTypeDefine() != null && 
      !"".equals(outStockActionForm.getSsTypeDefine()) && 
      !"null".equals(outStockActionForm.getSsTypeDefine()))
      ssMasterPO.setSsTypeDefine(outStockActionForm.getSsTypeDefine()); 
    if (httpServletRequest.getParameter("flag").equals("check")) {
      ssMasterPO.setCheckFlag("Y");
      ssMasterPO.setCheckMan(new Long(httpSession.getAttribute("userId")
            .toString()));
      ssMasterPO.setCheckManName(httpSession.getAttribute("userName")
          .toString());
      ssMasterPO.setCheckDate(new Date());
    } else {
      ssMasterPO.setCheckFlag("N");
    } 
    String[] goodsId = httpServletRequest.getParameterValues("goodsId");
    String[] amount = httpServletRequest.getParameterValues("amount");
    String[] price = httpServletRequest.getParameterValues("price");
    String[] money = httpServletRequest.getParameterValues("money");
    String[] goodsName = httpServletRequest.getParameterValues("goodsName");
    String[] goodsUnit = httpServletRequest.getParameterValues("goodsUnit");
    String[] goodsSpecs = httpServletRequest.getParameterValues("goodsSpecs");
    String[] returnReason = (httpServletRequest.getParameterValues("returnReason") == null) ? 
      new String[goodsId.length] : httpServletRequest.getParameterValues("returnReason");
    String stockId = httpServletRequest.getParameter("stock");
    String[] stockIdS = { stockId };
    Object[] para = { goodsId, amount, price, money, goodsName, goodsUnit, goodsSpecs, returnReason };
    return (new IntoOutStockBD()).updateOutStock(ssMasterPO, para)
      .booleanValue();
  }
  
  private boolean unCheck(OutStockActionForm outStockActionForm) {
    return (new IntoOutStockBD()).uncheckOutStock(outStockActionForm
        .getSsMasterId()).booleanValue();
  }
  
  private Boolean delete(HttpServletRequest httpServletRequest) {
    String tableId = httpServletRequest.getParameter("tableId");
    (new IntoOutStockBD()).deleteOutStock(httpServletRequest
        .getParameter("ssMasterId"), 
        tableId);
    WorkVO workVO = new WorkVO();
    workVO.setTableId(new Long(tableId));
    workVO.setProcessId(new Long(httpServletRequest.getParameter("processId")));
    workVO.setRecordId(new Long(httpServletRequest.getParameter("ssMasterId")));
    (new WorkFlowButtonBD()).deleteWork(workVO);
    return Boolean.TRUE;
  }
  
  private void tuih(HttpServletRequest httpServletRequest) {
    IntoOutStockBD bd = new IntoOutStockBD();
    bd.uncheckOutStock(httpServletRequest.getParameter("ssMasterId"));
    bd.deleteOutStock(httpServletRequest.getParameter("ssMasterId"), 
        httpServletRequest.getParameter("tableId"));
  }
  
  private void getUserManaDept(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    httpServletRequest.setAttribute("deptList", (
        new DrawDeptBD()).getUserManaDept(
          httpSession.getAttribute("userId").toString()));
  }
  
  private void search(HttpServletRequest httpServletRequest, String userId) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = "";
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    String searchId = httpServletRequest.getParameter("searchId");
    String searchSsMan = httpServletRequest.getParameter("searchSsMan");
    String searchSsOrgName = httpServletRequest.getParameter(
        "searchSsOrgName");
    String searchCheckFlag = httpServletRequest.getParameter(
        "searchCheckFlag");
    String searchStartDate = httpServletRequest.getParameter(
        "searchStartDate");
    String searchEndDate = httpServletRequest.getParameter("searchEndDate");
    String searchDept = httpServletRequest.getParameter("searchDept");
    String goodsName = httpServletRequest.getParameter("goodsName");
    String goods_specs = httpServletRequest.getParameter("goods_specs");
    String useMan = httpServletRequest.getParameter("useMan");
    String viewSql = 
      "distinct aaa.id,aaa.ssDate,aaa.ssMan,aaa.checkFlag,aaa.serial,aaa.ssOutFlag,aaa.ssOrgName,aaa.ssUseMan";
    String fromSql = "com.js.oa.routine.resource.po.SsMasterPO aaa join aaa.ssDetail b";
    String tmpDate = "";
    String databaseType = 
      SystemCommon.getDatabaseType();
    if (httpServletRequest.getParameter("chickdate") != null && 
      httpServletRequest.getParameter("chickdate").equals("1"))
      if (databaseType.indexOf("mysql") >= 0) {
        tmpDate = "(aaa.ssDate between '" + 
          searchStartDate + 
          " 00:00:00' and '" + 
          searchEndDate + " 23:59:59') and";
      } else {
        tmpDate = "(aaa.ssDate between JSDB.FN_STRTODATE('" + 
          searchStartDate + 
          " 00:00:00', 'L') and JSDB.FN_STRTODATE('" + 
          searchEndDate + " 23:59:59', 'L')) and";
      }  
    String sSman = "";
    if (searchSsMan != null && !"".equals(searchSsMan) && !"null".equals(searchSsMan)) {
      sSman = " aaa.ssMan like '%" + searchSsMan.trim() + "%'  and ";
    } else {
      sSman = " aaa.ssMan like '% %'  and ";
    } 
    StringBuffer whereSql = new StringBuffer(
        "where " + (
        "1".equals(httpServletRequest.getParameter("chickdate")) ? 
        tmpDate : "") + ((
        searchSsMan != null && searchSsMan.length() > 0) ? sSman : " ") + 
        " aaa.domainid=" + domainId);
    if (searchId != null && !"".equals(searchId) && !"null".equals(searchId))
      whereSql.append(" and aaa.serial like '%" + searchId + "%' "); 
    whereSql.append(" and aaa.ssStock=" + 
        httpServletRequest.getParameter("stockId"));
    if (searchCheckFlag != null && !"".equals(searchCheckFlag) && !"null".equals(searchCheckFlag)) {
      if (!"0".equals(searchCheckFlag) && !"1".equals(searchCheckFlag))
        whereSql.append(" and aaa.checkFlag='" + searchCheckFlag + 
            "' and aaa.ssOutFlag='0' "); 
      if (searchCheckFlag.equals("1"))
        whereSql.append(" and aaa.ssOutFlag='" + searchCheckFlag + "' "); 
    } 
    if (searchSsOrgName != null && !searchSsOrgName.trim().equals(""))
      whereSql.append(" and aaa.ssOrgName like '%" + searchSsOrgName + 
          "%'"); 
    if (searchDept != null && !searchDept.equals("0"))
      whereSql.append(" and aaa.ssDept='" + searchDept + "' "); 
    if (goodsName != null && !goodsName.trim().equals(""))
      whereSql.append(" and b.goodsName like '%" + goodsName.trim() + "%' "); 
    if (goods_specs != null && !"null".equals(goods_specs) && !"".equals(goods_specs)) {
      whereSql.append(" and b.goodsSpecs like'%" + goods_specs + "%'");
      httpServletRequest.setAttribute("goods_specs", goods_specs);
    } 
    if (useMan != null && !"null".equals(useMan) && !"".equals(useMan)) {
      whereSql.append(" and aaa.ssUseMan like'%" + useMan + "%'");
      httpServletRequest.setAttribute("useMan", useMan);
    } 
    if (httpServletRequest.getParameter("mode").equals("outStock")) {
      whereSql.append(" and aaa.ssMode='2' ");
    } else {
      whereSql.append(" and aaa.ssMode='3' ");
    } 
    String[] stock = (new StockBD()).getSingleStock(httpServletRequest
        .getParameter("stockId"));
    Object object = httpSession.getAttribute("userId");
    if (stock[1].indexOf("$" + object + "$") > -1) {
      httpServletRequest.setAttribute("isStockPut", "1");
    } else {
      httpServletRequest.setAttribute("isStockPut", "0");
      whereSql.append(" and aaa.makeMan=" + object);
    } 
    whereSql.append(" order by aaa.id desc");
    list(httpServletRequest, viewSql, fromSql, whereSql.toString());
    getUserManaStock(httpServletRequest);
    getUserManaDept(httpServletRequest);
    if (!isStockCharger(httpServletRequest.getParameter("stockId"), userId)) {
      httpServletRequest.setAttribute("processId", 
          httpServletRequest
          .getParameter("processId"));
      httpServletRequest.setAttribute("tableId", 
          httpServletRequest
          .getParameter("tableId"));
      httpServletRequest.setAttribute("processName", 
          httpServletRequest
          .getParameter("processName"));
      httpServletRequest.setAttribute("hasProcess", 
          httpServletRequest
          .getParameter("hasProcess"));
      httpServletRequest.setAttribute("ischarge", "1");
    } else {
      httpServletRequest.setAttribute("ischarge", "0");
    } 
    if (httpServletRequest.getParameter("searchStock") != null) {
      httpServletRequest.setAttribute("searchDeptList", (
          new DrawDeptBD())
          .getDeptInStock(httpServletRequest
            .getParameter("searchStock")));
    } else {
      httpServletRequest.setAttribute("searchDeptList", new ArrayList());
    } 
  }
  
  private boolean isStockCharger(String stockId, String userId) {
    if (stockId != null) {
      String chargers = (new IntoOutStockBD()).getStockCharger(stockId);
      if (chargers.indexOf(userId) > 0)
        return true; 
    } 
    return false;
  }
  
  private void saveProcess(HttpServletRequest httpServletRequest, String recordId) {
    int moduleId = 10, formType = 1;
    String mainLinkFile = 
      "/jsoa/OutStockAction.do?flag=modify&stockId=" + 
      httpServletRequest.getParameter("stock") + 
      "&checkFlag=N&ssMasterId=" + recordId;
    String cancelFile = "window.open('/jsoa/jsflow/workflow_cancelReason.jsp?workStatus=1&workId=workIdValue&tableId=tableIdValue&processName=" + 
      httpServletRequest.getParameter("fileType") + 
      "&recordId=" + recordId + "&processId=" + 
      httpServletRequest.getParameter("processId") + 
      "&remindValue=&" + 
      "','','TOP=0,LEFT=0,scrollbars=no,resizable=no,width=480,height=250')";
    try {
      (new ProcessSubmit()).saveProcess(httpServletRequest, 
          recordId, 
          moduleId, 
          formType, 
          mainLinkFile, 
          cancelFile);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private void getNextStep(HttpServletRequest httpServletRequest) {
    (new ProcessSubmit()).getNextStep(httpServletRequest);
  }
  
  private void oper(HttpServletRequest httpServletRequest) {
    SsMasterPO ssMasterPO = new SsMasterPO();
    ssMasterPO.setId(new Long(httpServletRequest.getParameter(
            "ssMasterId")));
    ssMasterPO.setSsDate(new Date(httpServletRequest.getParameter(
            "ssDate")));
    ssMasterPO.setSsDept(new Long(httpServletRequest.getParameter(
            "ssDept")));
    ssMasterPO.setSsMan(httpServletRequest.getParameter("ssMan"));
    ssMasterPO.setSsMoney(new Float(httpServletRequest.getParameter(
            "ptMoney")));
    ssMasterPO.setRemark(httpServletRequest.getParameter("remark"));
    String[] goodsId = httpServletRequest.getParameterValues("goodsId");
    String[] amount = httpServletRequest.getParameterValues("amount");
    String[] price = httpServletRequest.getParameterValues("price");
    String[] money = httpServletRequest.getParameterValues("money");
    String[] goodsName = httpServletRequest.getParameterValues(
        "goodsName");
    String[] goodsUnit = httpServletRequest.getParameterValues(
        "goodsUnit");
    Object[] para = { goodsId, amount, price, money, goodsName, 
        goodsUnit };
    (new IntoOutStockBD()).updateOutStock(ssMasterPO, para);
    String toMainFile = "OutStockAction.do?flag=modify&stockId=" + 
      httpServletRequest.getParameter("stock") + 
      "&checkFlag=N&ssMasterId=" + 
      httpServletRequest.getParameter("ssMasterId");
    (new ProcessSubmit()).operate(httpServletRequest, toMainFile);
    httpServletRequest.setAttribute("close", "1");
  }
  
  private void comp(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    SsMasterPO ssMasterPO = new SsMasterPO();
    ssMasterPO.setId(new Long(httpServletRequest.getParameter(
            "ssMasterId")));
    ssMasterPO.setSsDate(new Date(httpServletRequest.getParameter(
            "ssDate")));
    ssMasterPO.setSsDept(new Long(httpServletRequest.getParameter(
            "ssDept")));
    ssMasterPO.setSsMan(httpServletRequest.getParameter("ssMan"));
    ssMasterPO.setSsMoney(new Float(httpServletRequest.getParameter(
            "ptMoney")));
    ssMasterPO.setRemark(httpServletRequest.getParameter("remark"));
    String[] goodsId = httpServletRequest.getParameterValues("goodsId");
    String[] amount = httpServletRequest.getParameterValues("amount");
    String[] price = httpServletRequest.getParameterValues("price");
    String[] money = httpServletRequest.getParameterValues("money");
    String[] goodsName = httpServletRequest.getParameterValues(
        "goodsName");
    String[] goodsUnit = httpServletRequest.getParameterValues(
        "goodsUnit");
    ssMasterPO.setCheckFlag("Y");
    ssMasterPO.setCheckMan(new Long(httpSession.getAttribute("userId")
          .toString()));
    ssMasterPO.setCheckManName(httpSession.getAttribute("userName")
        .toString());
    ssMasterPO.setCheckDate(new Date());
    Object[] para = { goodsId, amount, price, money, goodsName, 
        goodsUnit };
    (new IntoOutStockBD()).updateOutStock(ssMasterPO, para).booleanValue();
    httpServletRequest.setAttribute("close", "1");
    (new ProcessSubmit()).comp(httpServletRequest);
  }
  
  private void reSub(HttpServletRequest httpServletRequest, OutStockActionForm outStockActionForm) {
    delete(httpServletRequest);
    HttpSession httpSession = httpServletRequest.getSession(true);
    SsMasterPO ssMasterPO = new SsMasterPO();
    ssMasterPO.setSsStock(new Long(outStockActionForm.getStock()));
    ssMasterPO.setSsMan(outStockActionForm.getSsMan());
    ssMasterPO.setSsMoney(new Float(outStockActionForm.getPtMoney()));
    ssMasterPO.setRemark(outStockActionForm.getRemark());
    ssMasterPO.setMakeMan(new Long(httpSession.getAttribute("userId")
          .toString()));
    ssMasterPO.setMakeDate(new Date());
    ssMasterPO.setCheckFlag("N");
    ssMasterPO.setSsDate(new Date(httpServletRequest.getParameter(
            "ssDate")));
    ssMasterPO.setSsDept(new Long(outStockActionForm.getSsDept()));
    String[] goodsId = httpServletRequest.getParameterValues("goodsId");
    String[] amount = httpServletRequest.getParameterValues("amount");
    String[] price = httpServletRequest.getParameterValues("price");
    String[] money = httpServletRequest.getParameterValues("money");
    String[] goodsName = httpServletRequest.getParameterValues(
        "goodsName");
    String[] goodsUnit = httpServletRequest.getParameterValues(
        "goodsUnit");
    Object[] para = { goodsId, amount, price, money, goodsName, 
        goodsUnit };
    Long recordId = (new IntoOutStockBD()).saveOutStock(ssMasterPO, para);
    saveProcess(httpServletRequest, recordId.toString());
    httpServletRequest.setAttribute("close", "1");
  }
  
  private void trans(HttpServletRequest httpServletRequest, OutStockActionForm outStockActionForm) {
    update(outStockActionForm, httpServletRequest);
    (new ProcessSubmit()).transition(httpServletRequest, "");
    httpServletRequest.setAttribute("close", "1");
  }
  
  private String listLoad(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    WorkFlowCommonBD wfcBD = new WorkFlowCommonBD();
    Map workMap = wfcBD.getWorkInfo("13", 
        httpServletRequest.getParameter(
          "record"), "物品领用申请");
    String sendFileLink = 
      "/OutStockAction.do?flag=modify&activityName=" + 
      workMap.get("activityName") + 
      "&submitPersonId=" + workMap.get("submitPersonId") + 
      "&submitPerson=" + workMap.get("submitPerson") + 
      "&work=" + workMap.get("work") + "&workType=" + 
      workMap.get("workType") + "&activity=" + 
      workMap.get("activity") + "&table=" + workMap.get("tableId") + 
      "&record=" + 
      httpServletRequest.getParameter("record") + "&processName=" + 
      workMap.get("processName") + 
      "&workStatus=1&submitTime=" + workMap.get("submitTime") + 
      "&processId=" + workMap.get("processId") + 
      "&stepCount=" + workMap.get("stepCount") + "&isStandForWork=" + 
      workMap.get("isStandForWork") + 
      "&standForUserId=" + workMap.get("standForUserId") + 
      "&standForUserName=" + workMap.get("standForUserName");
    return sendFileLink;
  }
  
  private Map updateOutFlag(HttpServletRequest httpServletRequest) {
    return (new IntoOutStockBD()).updateOutFlag(httpServletRequest
        .getParameter("ssMasterId"));
  }
  
  private void getSsDetail(HttpServletRequest httpServletRequest) {
    String ssMasterId = httpServletRequest.getParameter("record");
    int pageSize = 15;
    if (httpServletRequest.getParameter("pageSize") != null)
      pageSize = Integer.parseInt(httpServletRequest.getParameter(
            "pageSize")); 
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String viewSQL = "po";
    String fromSQL = "com.js.oa.routine.resource.po.SsDetailPO po";
    String whereSQL = "where po.ssMaster.id=" + ssMasterId;
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
      httpServletRequest.setAttribute("pager.realCurrent", (
          new StringBuilder(String.valueOf(currentPage))).toString());
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("pageCount", pageCount);
    httpServletRequest.setAttribute("intoStockList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "flag,record,mode,stockId,pageSize");
  }
}
