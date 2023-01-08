package com.js.oa.routine.resource.action;

import com.js.message.RealTimeUtil;
import com.js.oa.jsflow.service.WorkFlowCommonBD;
import com.js.oa.routine.resource.po.PtMasterPO;
import com.js.oa.routine.resource.service.IntoOutStockBD;
import com.js.oa.routine.resource.service.StockBD;
import com.js.oa.routine.resource.service.TypeDefineBD;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import java.util.Calendar;
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

public class IntoStockAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    IntoStockActionForm intoStockActionForm = 
      (IntoStockActionForm)actionForm;
    String flag = (httpServletRequest.getParameter("flag") == null) ? "view" : 
      httpServletRequest.getParameter("flag").toString();
    String domainId = "0";
    httpServletRequest.setAttribute("send", httpServletRequest.getParameter("send"));
    String stockShow = SystemCommon.getStockShowUse();
    if ("1".equals(stockShow)) {
      String IntoStock = httpServletRequest.getParameter("IntoStockfromMenu");
      if ("IntoStock".equals(IntoStock)) {
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
        intoStockActionForm.setPtMoney((new StringBuilder(String.valueOf(totalMoney))).toString());
        httpServletRequest.setAttribute("totalMoney", Double.valueOf(totalMoney));
        httpServletRequest.setAttribute("goodsIds", goodsIds);
        httpServletRequest.setAttribute("chbm", chbm);
        httpServletRequest.setAttribute("chmc", chmc);
        httpServletRequest.setAttribute("jldw", jldw);
        httpServletRequest.setAttribute("dj", dj);
        httpServletRequest.setAttribute("ggxh", ggxh);
        httpServletRequest.setAttribute("kc", kc);
      } 
    } 
    HttpSession httpSession = httpServletRequest.getSession(true);
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    String tag = "view";
    if (flag.equals("view") || flag.equals("search") || 
      flag.equals("delete") || flag.equals("tuih")) {
      tag = "view";
      if (flag.equals("delete"))
        httpServletRequest.setAttribute("deleteSuccess", 
            delete(httpServletRequest)); 
      if (flag.equals("tuih"))
        tuih(httpServletRequest); 
      if (flag.equals("search") || flag.equals("delete")) {
        search(httpServletRequest);
      } else {
        view(httpServletRequest);
      } 
      if (httpServletRequest.getParameter("mode") != null)
        if (httpServletRequest.getParameter("mode").equals(
            "salesreturn"))
          tag = "salesreturn";  
    } else if (flag.equals("add") || flag.equals("continue") || 
      flag.equals("close")) {
      tag = "add";
      TypeDefineBD tdbd = new TypeDefineBD();
      if (httpServletRequest.getParameter("mode") != null)
        if (httpServletRequest.getParameter("mode").equals(
            "salesreturn")) {
          httpServletRequest.setAttribute("typeDefineList", 
              tdbd.list("4", new Long(domainId)));
        } else {
          httpServletRequest.setAttribute("typeDefineList", 
              tdbd.list("1", new Long(domainId)));
        }  
      if (flag.equals("continue") || flag.equals("close"))
        if (save(intoStockActionForm, httpServletRequest)) {
          if (flag.equals("continue")) {
            httpServletRequest.setAttribute("close", "0");
          } else {
            httpServletRequest.setAttribute("close", "1");
          } 
        } else {
          httpServletRequest.setAttribute("close", "2");
        }  
    } else if (flag.equals("modify") || flag.equals("update") || 
      flag.equals("check") || flag.equals("modiMoney")) {
      tag = "modify";
      if (flag.equals("modiMoney"))
        tag = "modiMoney"; 
      TypeDefineBD tdbd = new TypeDefineBD();
      if (httpServletRequest.getParameter("mode") != null)
        if (httpServletRequest.getParameter("mode").equals(
            "salesreturn")) {
          httpServletRequest.setAttribute("typeDefineList", 
              tdbd.list("4", new Long(domainId)));
        } else {
          httpServletRequest.setAttribute("typeDefineList", 
              tdbd.list("1", new Long(domainId)));
        }  
      if (flag.equals("modify") || flag.equals("modiMoney")) {
        String modifyFlag = httpServletRequest.getParameter(
            "modifyFlag");
        if (modifyFlag != null)
          httpServletRequest.setAttribute("modifyFlag", modifyFlag); 
        getIntoStock(intoStockActionForm, httpServletRequest);
      } else if (update(intoStockActionForm, httpServletRequest)) {
        httpServletRequest.setAttribute("close", "1");
      } else {
        httpServletRequest.setAttribute("close", "2");
        getIntoStock(intoStockActionForm, httpServletRequest);
      } 
    } else if (flag.equals("unCheck")) {
      tag = "modify";
      if (unCheck(intoStockActionForm)) {
        httpServletRequest.setAttribute("close", "1");
      } else {
        httpServletRequest.setAttribute("close", "2");
      } 
    } else if (flag.equals("myPrint") || flag.equals("print")) {
      tag = flag;
      getIntoStock(intoStockActionForm, httpServletRequest);
      getPtDetail(httpServletRequest);
    } else if (flag.equals("updateMoney")) {
      tag = "modiMoney";
      TypeDefineBD tdbd = new TypeDefineBD();
      if (httpServletRequest.getParameter("mode") != null)
        if (httpServletRequest.getParameter("mode").equals(
            "salesreturn")) {
          httpServletRequest.setAttribute("typeDefineList", 
              tdbd.list("4", new Long(domainId)));
        } else {
          httpServletRequest.setAttribute("typeDefineList", 
              tdbd.list("1", new Long(domainId)));
        }  
      if (updateMoney(intoStockActionForm, httpServletRequest)) {
        httpServletRequest.setAttribute("close", "1");
      } else {
        httpServletRequest.setAttribute("close", "2");
        getIntoStock(intoStockActionForm, httpServletRequest);
      } 
    } 
    if ("listLoad".equals(flag)) {
      ActionForward forward = new ActionForward();
      forward.setPath(listLoad(httpServletRequest, httpServletResponse));
      return forward;
    } 
    return actionMapping.findForward(tag);
  }
  
  private boolean save(IntoStockActionForm intoStockActionForm, HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    PtMasterPO ptMasterPO = new PtMasterPO();
    ptMasterPO.setPtStock(new Long(intoStockActionForm.getStock()));
    ptMasterPO.setPtSupp(intoStockActionForm.getSupp());
    ptMasterPO.setPtMan(intoStockActionForm.getMan());
    ptMasterPO.setPtMoney(new Float(intoStockActionForm.getPtMoney()));
    ptMasterPO.setRemark(intoStockActionForm.getRemark());
    ptMasterPO.setMakeMan(new Long(httpSession.getAttribute("userId").toString()));
    ptMasterPO.setMakeDate(new Date());
    ptMasterPO.setCheckFlag("N");
    ptMasterPO.setPtDate(new Date(httpServletRequest.getParameter("ptDate")));
    String domainId = "";
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    if (!domainId.equals("") || domainId.length() > 0)
      ptMasterPO.setDomainid(Integer.parseInt(domainId)); 
    ptMasterPO.setPtOrg(intoStockActionForm.getPtOrg());
    ptMasterPO.setPtOrgName(intoStockActionForm.getPtOrgName());
    ptMasterPO.setPtMode("1");
    ptMasterPO.setPtTypeDefine(intoStockActionForm.getPtTypeDefine());
    ptMasterPO.setPtHandleName(intoStockActionForm.getPtHandleName());
    ptMasterPO.setInvoiceNO(intoStockActionForm.getInvoiceNO());
    ptMasterPO.setPtHaveWorkFlow("0");
    String[] goodsId = httpServletRequest.getParameterValues("goodsId");
    String[] amount = httpServletRequest.getParameterValues("amount");
    String[] price = httpServletRequest.getParameterValues("price");
    String[] money = httpServletRequest.getParameterValues("money");
    String[] goodsName = httpServletRequest.getParameterValues("goodsName");
    String[] goodsUnit = httpServletRequest.getParameterValues("goodsUnit");
    String[] goodsSpecs = httpServletRequest.getParameterValues("goodsSpecs");
    String[] returnReason = (httpServletRequest.getParameterValues("returnReason") == null) ? 
      new String[goodsId.length] : httpServletRequest.getParameterValues("returnReason");
    if (httpServletRequest.getParameter("mode").equals("salesreturn")) {
      ptMasterPO.setPtMode("4");
      ptMasterPO.setPtMoney(new Float("-" + 
            intoStockActionForm.getPtMoney()));
      for (int i = 0; i < amount.length; i++) {
        amount[i] = "-" + amount[i];
        money[i] = "-" + money[i];
      } 
    } 
    Object[] para = { goodsId, amount, price, money, goodsName, goodsUnit, goodsSpecs, returnReason };
    Map result = (new IntoOutStockBD()).save(ptMasterPO, para);
    boolean success = ((Boolean)result.get("success")).booleanValue();
    if (success) {
      Calendar now = Calendar.getInstance();
      if (result.get("stockPut") != null) {
        RealTimeUtil rtxApi = new RealTimeUtil();
        String stockPut = result.get("stockPut").toString();
        if (stockPut.indexOf(",") > 0) {
          String[] tmp = stockPut.split(",");
          for (int i = 0; i < tmp.length; i++)
            rtxApi.sendNotify(tmp[i], "物品采购进货提醒", 
                "[" + result.get("ptMan") + "] 编号为" + 
                result.get("serial") + 
                "的采购进货单等待您审核！(" + (
                now.get(2) + 1) + "-" + 
                now.get(5) + ")", "0", 
                "0"); 
        } else {
          rtxApi.sendNotify(stockPut, "物品采购进货提醒", 
              "[" + result.get("ptMan") + "] 编号为" + 
              result.get("serial") + "的采购进货单等待您审核！(" + (
              now.get(2) + 1) + "-" + 
              now.get(5) + ")", "0", "0");
        } 
      } 
    } 
    return success;
  }
  
  private void view(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = "";
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    String ptMode = "1";
    if (httpServletRequest.getParameter("mode") != null) {
      if (httpServletRequest.getParameter("mode").equals("cancelstock"))
        ptMode = "3"; 
      if (httpServletRequest.getParameter("mode").equals("salesreturn"))
        ptMode = "4"; 
    } 
    getUserManaStock(httpServletRequest);
    IntoOutStockBD bd = new IntoOutStockBD();
    String stockId = httpServletRequest.getParameter("stockId");
    String stockType = "0";
    if (httpServletRequest.getParameter("mode") != null) {
      if (httpServletRequest.getParameter("mode").equals("cancelstock"))
        stockType = "2"; 
      if (httpServletRequest.getParameter("mode").equals("salesreturn"))
        stockType = "3"; 
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
    String viewSql = "aaa.id,aaa.ptDate,aaa.ptMan,aaa.checkFlag,aaa.serial,aaa.ptOrgName";
    String fromSql = "com.js.oa.routine.resource.po.PtMasterPO aaa ";
    String whereSql = "where aaa.ptStock=" + 
      httpServletRequest.getParameter("stockId") + 
      " and aaa.domainid=" + domainId + " and aaa.ptMode='" + 
      ptMode + 
      "' order by aaa.id desc";
    list(httpServletRequest, viewSql, fromSql, whereSql);
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
    httpServletRequest.setAttribute("pageParameters", "flag,stockName,stockId,searchId,searchPtMan,searchStock,searchCheckFlag,searchStartDate,searchEndDate,searchDate,mode,goodsName,goods_specs,tableId,remindField,processId,processType,processName");
  }
  
  private void getUserManaStock(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = "";
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    httpServletRequest.setAttribute("stockList", (
        new StockBD())
        .getUserManaStock(httpSession
          .getAttribute("userId").toString(), domainId));
  }
  
  private void search(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = "";
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    String searchId = httpServletRequest.getParameter("searchId");
    String searchPtMan = httpServletRequest.getParameter("searchPtMan");
    String searchStock = httpServletRequest.getParameter("stockId");
    String searchCheckFlag = httpServletRequest.getParameter(
        "searchCheckFlag");
    String searchStartDate = httpServletRequest.getParameter(
        "searchStartDate");
    String goodsName = httpServletRequest.getParameter("goodsName");
    String goods_specs = httpServletRequest.getParameter("goods_specs");
    String searchEndDate = httpServletRequest.getParameter("searchEndDate");
    String viewSql = "distinct aaa.id,aaa.ptDate,aaa.ptMan,aaa.checkFlag,aaa.serial,aaa.ptOrgName";
    String fromSql = "com.js.oa.routine.resource.po.PtMasterPO aaa join aaa.ptDetail b";
    String tmpSql = " where 1=1 ";
    String databaseType = 
      SystemCommon.getDatabaseType();
    if (httpServletRequest.getParameter("searchDate") != null && 
      httpServletRequest.getParameter("searchDate").equals("1"))
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = String.valueOf(tmpSql) + " and aaa.ptDate between '" + 
          searchStartDate + 
          " 00:00:00' and '" + 
          searchEndDate + " 23:59:59' and aaa.domainid=" + 
          domainId;
      } else {
        tmpSql = String.valueOf(tmpSql) + " and (aaa.ptDate between JSDB.FN_STRTODATE('" + 
          searchStartDate + 
          " 00:00:00', 'L') and JSDB.FN_STRTODATE('" + 
          searchEndDate + 
          " 23:59:59', 'L')) and aaa.domainid=" + 
          domainId;
      }  
    if (searchPtMan != null)
      tmpSql = String.valueOf(tmpSql) + " and aaa.ptMan like '%" + searchPtMan.trim() + "%' "; 
    if (goodsName != null && !goodsName.trim().equals(""))
      tmpSql = String.valueOf(tmpSql) + " and b.goodsName like '%" + goodsName.trim() + "%' "; 
    if (goods_specs != null && !"null".equals(goods_specs) && !"".equals(goods_specs)) {
      tmpSql = String.valueOf(tmpSql) + " and b.goodsSpecs like'%" + goods_specs + "%'";
      httpServletRequest.setAttribute("goods_specs", goods_specs);
    } 
    StringBuffer whereSql = new StringBuffer(tmpSql);
    if (searchId != null && !searchId.equals(""))
      whereSql.append(" and aaa.serial like '%" + searchId + "%' "); 
    if (searchStock != null && !searchStock.equals("0") && 
      !searchStock.equals("")) {
      whereSql.append(" and aaa.ptStock=" + searchStock);
    } else {
      List<Object[]> stockList = (new StockBD()).getUserManaStock(httpSession
          .getAttribute("userId").toString(), domainId);
      Object[] obj = (Object[])null;
      String tmp = "";
      for (int i = 0; i < stockList.size(); i++) {
        obj = stockList.get(i);
        tmp = String.valueOf(tmp) + obj[0] + ",";
      } 
      if (!"".equals(tmp)) {
        if (tmp.endsWith(","))
          tmp = tmp.substring(0, tmp.length() - 1); 
        whereSql.append(" and aaa.ptStock in (" + tmp + ") ");
      } else {
        whereSql.append(" and aaa.ptStock=0");
      } 
    } 
    if (httpServletRequest.getParameter("mode").equals("stock")) {
      whereSql.append(" and aaa.ptMode='1' ");
    } else if (httpServletRequest.getParameter("mode").equals("salesreturn")) {
      whereSql.append(" and aaa.ptMode='4' ");
    } 
    if (searchCheckFlag != null && !searchCheckFlag.equals("0") && 
      !searchCheckFlag.equals(""))
      whereSql.append(" and aaa.checkFlag='" + searchCheckFlag + "' "); 
    whereSql.append(" order by aaa.id desc");
    list(httpServletRequest, viewSql, fromSql, whereSql.toString());
    getUserManaStock(httpServletRequest);
    IntoOutStockBD bd = new IntoOutStockBD();
    String stockId = httpServletRequest.getParameter("stockId");
    String stockType = "";
    if (httpServletRequest.getParameter("mode").equals("stock")) {
      stockType = "0";
    } else if (httpServletRequest.getParameter("mode").equals("salesreturn")) {
      stockType = "3";
    } 
    List list = null;
    if (stockId != null && !"null".equals(stockId) && !"".equals(stockId))
      list = bd.getWFProcessInfoByStockId(stockId, stockType); 
    if (list != null && !list.isEmpty()) {
      httpServletRequest.setAttribute("tableId", list.get(0));
      httpServletRequest.setAttribute("processId", list.get(1));
      httpServletRequest.setAttribute("processName", list.get(2));
      httpServletRequest.setAttribute("hasProcess", "yes");
    } else {
      httpServletRequest.setAttribute("hasProcess", "no");
    } 
  }
  
  private void getIntoStock(IntoStockActionForm intoStockActionForm, HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = "0";
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    String ptMasterId = httpServletRequest.getParameter("ptMasterId");
    if (httpServletRequest.getParameter("record") != null)
      ptMasterId = httpServletRequest.getParameter("record"); 
    Map intoStockMap = (new IntoOutStockBD()).getIntoStock(ptMasterId);
    PtMasterPO ptMasterPO = (PtMasterPO)intoStockMap.get("ptMasterPO");
    intoStockActionForm.setPtMasterId(ptMasterPO.getId().toString());
    intoStockActionForm.setStock(ptMasterPO.getPtStock().toString());
    httpServletRequest.setAttribute("stockId", 
        ptMasterPO.getPtStock());
    intoStockActionForm.setSupp(ptMasterPO.getPtSupp());
    intoStockActionForm.setMan(ptMasterPO.getPtMan());
    intoStockActionForm.setPtMoney(ptMasterPO.getPtMoney().toString());
    TypeDefineBD tdbd = new TypeDefineBD();
    if (ptMasterPO.getPtMode().equals("4")) {
      intoStockActionForm.setPtMoney(
          Math.abs(ptMasterPO.getPtMoney().floatValue()));
      httpServletRequest.setAttribute("mode", "salesreturn");
      httpServletRequest.setAttribute("typeDefineList", 
          tdbd.list("4", new Long(domainId)));
    } else {
      httpServletRequest.setAttribute("mode", "stock");
      httpServletRequest.setAttribute("typeDefineList", 
          tdbd.list("1", new Long(domainId)));
    } 
    intoStockActionForm.setRemark(ptMasterPO.getRemark());
    intoStockActionForm.setSerial(ptMasterPO.getSerial());
    intoStockActionForm.setPtOrg(ptMasterPO.getPtOrg());
    intoStockActionForm.setPtOrgName(ptMasterPO.getPtOrgName());
    intoStockActionForm.setPtTypeDefine(ptMasterPO.getPtTypeDefine());
    intoStockActionForm.setPtMode(ptMasterPO.getPtMode());
    httpServletRequest.setAttribute("ptDate", ptMasterPO.getPtDate());
    httpServletRequest.setAttribute("stockName", 
        intoStockMap.get("stockName"));
    httpServletRequest.setAttribute("makeManName", 
        intoStockMap.get("makeManName"));
    intoStockActionForm.setMakeMan(ptMasterPO.getMakeMan().toString());
    httpServletRequest.setAttribute("makeDate", ptMasterPO.getMakeDate());
    httpServletRequest.setAttribute("checkFlag", ptMasterPO.getCheckFlag());
    httpServletRequest.setAttribute("checkDate", ptMasterPO.getCheckDate());
    httpServletRequest.setAttribute("ptDetail", ptMasterPO.getPtDetail());
    httpServletRequest.setAttribute("ptTypeDefineSelected", 
        ptMasterPO.getPtTypeDefine());
    httpServletRequest.setAttribute("checkManName", 
        ptMasterPO.getCheckManName());
    intoStockActionForm.setPtHandleName(ptMasterPO.getPtHandleName());
    intoStockActionForm.setInvoiceNO(ptMasterPO.getInvoiceNO());
    httpServletRequest.setAttribute("haveWorkFlow", 
        ptMasterPO.getPtHaveWorkFlow());
  }
  
  private boolean update(IntoStockActionForm intoStockActionForm, HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    PtMasterPO ptMasterPO = new PtMasterPO();
    ptMasterPO.setId(new Long(intoStockActionForm.getPtMasterId()));
    ptMasterPO.setPtDate(new Date(httpServletRequest.getParameter("ptDate")));
    ptMasterPO.setPtSupp(intoStockActionForm.getSupp());
    ptMasterPO.setPtMan(intoStockActionForm.getMan());
    ptMasterPO.setPtMoney(new Float(intoStockActionForm.getPtMoney()));
    ptMasterPO.setRemark(intoStockActionForm.getRemark());
    if (httpServletRequest.getParameter("flag").equals("check")) {
      ptMasterPO.setCheckFlag("Y");
      ptMasterPO.setCheckMan(new Long(httpSession.getAttribute("userId")
            .toString()));
      ptMasterPO.setCheckManName(httpSession.getAttribute("userName")
          .toString());
      ptMasterPO.setCheckDate(new Date());
    } else {
      ptMasterPO.setCheckFlag("N");
    } 
    ptMasterPO.setPtOrg(intoStockActionForm.getPtOrg());
    ptMasterPO.setPtOrgName(intoStockActionForm.getPtOrgName());
    ptMasterPO.setPtMode("1");
    ptMasterPO.setPtTypeDefine(intoStockActionForm.getPtTypeDefine());
    ptMasterPO.setPtHandleName(intoStockActionForm.getPtHandleName());
    ptMasterPO.setInvoiceNO(intoStockActionForm.getInvoiceNO());
    String[] goodsId = httpServletRequest.getParameterValues("goodsId");
    String[] amount = httpServletRequest.getParameterValues("amount");
    String[] price = httpServletRequest.getParameterValues("price");
    String[] money = httpServletRequest.getParameterValues("money");
    String[] goodsName = httpServletRequest.getParameterValues("goodsName");
    String[] goodsUnit = httpServletRequest.getParameterValues("goodsUnit");
    String[] goodsSpecs = httpServletRequest.getParameterValues("goodsSpecs");
    String[] returnReason = (httpServletRequest.getParameterValues("returnReason") == null) ? 
      new String[goodsId.length] : httpServletRequest.getParameterValues("returnReason");
    if (httpServletRequest.getParameter("mode").equals("salesreturn")) {
      ptMasterPO.setPtMode("4");
      ptMasterPO.setPtMoney(new Float("-" + 
            intoStockActionForm.getPtMoney()));
      for (int i = 0; i < amount.length; i++) {
        amount[i] = "-" + amount[i];
        money[i] = "-" + money[i];
      } 
    } 
    Object[] para = { goodsId, amount, price, money, goodsName, goodsUnit, goodsSpecs, returnReason };
    return (new IntoOutStockBD()).updateIntoStock(ptMasterPO, para).booleanValue();
  }
  
  private void tuih(HttpServletRequest httpServletRequest) {
    IntoOutStockBD bd = new IntoOutStockBD();
    bd.unCheckIntoStock(httpServletRequest.getParameter("ptMasterId"));
    bd.deleteIntoStock(httpServletRequest.getParameter("ptMasterId"));
  }
  
  private boolean unCheck(IntoStockActionForm intoStockActionForm) {
    return (new IntoOutStockBD()).unCheckIntoStock(intoStockActionForm
        .getPtMasterId()).booleanValue();
  }
  
  private Boolean delete(HttpServletRequest httpServletRequest) {
    if (httpServletRequest
      .getParameter("ptMasterId") != null && !"".equals(httpServletRequest
        .getParameter("ptMasterId")))
      return (new IntoOutStockBD()).deleteIntoStock(httpServletRequest
          .getParameter("ptMasterId")); 
    return Boolean.TRUE;
  }
  
  private void loadProviderList(HttpServletRequest httpServletRequest) {
    String viewSql = "aaa.id,aaa.name";
    String fromSql = "com.js.oa.routine.resource.po.ProviderPO aaa";
    String whereSql = "order by aaa.id desc";
    Page page = new Page(viewSql, fromSql, whereSql);
    page.setPageSize(100000);
    page.setcurrentPage(1);
    List list = page.getResultList();
    httpServletRequest.setAttribute("ProviderList", list);
  }
  
  private void getPtDetail(HttpServletRequest httpServletRequest) {
    String ptMasterId = httpServletRequest.getParameter("ptMasterId");
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
    String fromSQL = "com.js.oa.routine.resource.po.PtDetailPO po";
    String whereSQL = "where po.ptMaster.id=" + ptMasterId;
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
    httpServletRequest.setAttribute("pageParameters", 
        "flag,ptMasterId,mode,stockId,pageSize");
  }
  
  private boolean updateMoney(IntoStockActionForm intoStockActionForm, HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    PtMasterPO ptMasterPO = new PtMasterPO();
    ptMasterPO.setId(new Long(intoStockActionForm.getPtMasterId()));
    ptMasterPO.setPtMoney(new Float(intoStockActionForm.getPtMoney()));
    String[] goodsId = httpServletRequest.getParameterValues("goodsId");
    String[] amount = httpServletRequest.getParameterValues("amount");
    String[] price = httpServletRequest.getParameterValues("price");
    String[] money = httpServletRequest.getParameterValues("money");
    String[] goodsName = httpServletRequest.getParameterValues("goodsName");
    String[] goodsUnit = httpServletRequest.getParameterValues("goodsUnit");
    String[] goodsSpecs = httpServletRequest.getParameterValues("goodsSpecs");
    String[] returnReason = (httpServletRequest.getParameterValues("returnReason") == null) ? 
      new String[goodsId.length] : httpServletRequest.getParameterValues("returnReason");
    if (httpServletRequest.getParameter("mode").equals("salesreturn")) {
      ptMasterPO.setPtMode("4");
      ptMasterPO.setPtMoney(new Float("-" + 
            intoStockActionForm.getPtMoney()));
      for (int i = 0; i < amount.length; i++) {
        amount[i] = "-" + amount[i];
        money[i] = "-" + money[i];
      } 
    } 
    Object[] para = { goodsId, amount, price, money, goodsName, goodsUnit, goodsSpecs, returnReason };
    return (new IntoOutStockBD()).updateIntoStockMoney(ptMasterPO, para)
      .booleanValue();
  }
  
  private String listLoad(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    WorkFlowCommonBD wfcBD = new WorkFlowCommonBD();
    Map workMap = wfcBD.getWorkInfo("13", 
        httpServletRequest.getParameter(
          "ptMasterId"), "物品领用申请");
    String sendFileLink = 
      "/IntoStockAction.do?flag=modify&activityName=" + 
      workMap.get("activityName") + 
      "&submitPersonId=" + workMap.get("submitPersonId") + 
      "&submitPerson=" + workMap.get("submitPerson") + 
      "&work=" + workMap.get("work") + "&workType=" + 
      workMap.get("workType") + "&activity=" + 
      workMap.get("activity") + "&table=" + workMap.get("tableId") + 
      "&record=" + 
      httpServletRequest.getParameter("ptMasterId") + "&processName=" + 
      workMap.get("processName") + 
      "&workStatus=1&submitTime=" + workMap.get("submitTime") + 
      "&processId=" + workMap.get("processId") + 
      "&stepCount=" + workMap.get("stepCount") + "&isStandForWork=" + 
      workMap.get("isStandForWork") + 
      "&standForUserId=" + workMap.get("standForUserId") + 
      "&standForUserName=" + workMap.get("standForUserName") + "&modifyFlag=" + 
      httpServletRequest.getParameter("modifyFlag") + "&stockId=" + httpServletRequest.getParameter("stockId");
    return sendFileLink;
  }
}
