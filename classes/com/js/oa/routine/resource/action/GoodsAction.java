package com.js.oa.routine.resource.action;

import com.js.oa.routine.resource.po.GoodsPO;
import com.js.oa.routine.resource.service.GoodsBD;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.DataSourceBase;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GoodsAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    GoodsActionForm goodsActionForm = (GoodsActionForm)actionForm;
    String flag = (httpServletRequest.getParameter("flag") == null || 
      httpServletRequest.getParameter("flag").equals("null")) ? 
      "view" : httpServletRequest.getParameter("flag");
    String tag = "view";
    httpServletRequest.setAttribute("queryString", httpServletRequest.getQueryString());
    if (flag.equals("view") || flag.equals("delete") || 
      flag.equals("search") || flag.equals("noused")) {
      httpServletRequest.setAttribute("vindicate", 
          getVindicate(httpServletRequest));
      HttpSession httpSession = httpServletRequest.getSession(true);
      httpServletRequest.setAttribute("addRight", 
          getVindicate(httpServletRequest));
      tag = "view";
      if (flag.equals("delete")) {
        delete(httpServletRequest);
      } else if (flag.equals("noused")) {
        noused(httpServletRequest);
      } 
      getUserManaGoodsType(httpServletRequest);
      if (flag.equals("search")) {
        search(httpServletRequest);
      } else if ("1".equals(httpServletRequest.getParameter("addGoods"))) {
        userGoodsView(httpServletRequest);
      } else if ("2".equals(httpServletRequest.getParameter("addGoods"))) {
        userGoodsView2(httpServletRequest);
        tag = "outStockGoodsList";
      } else {
        view(httpServletRequest);
      } 
    } else if (flag.equals("add") || flag.equals("close") || 
      flag.equals("continue")) {
      tag = "add";
      if (flag.equals("add") || flag.equals("continue"))
        getUserManaGoodsType(httpServletRequest); 
      if (flag.equals("continue") || flag.equals("close"))
        if (save(goodsActionForm, httpServletRequest)) {
          if (flag.equals("close")) {
            httpServletRequest.setAttribute("close", "1");
            httpServletRequest.setAttribute("goodsTypeList", 
                new ArrayList());
          } else {
            httpServletRequest.setAttribute("close", "0");
            goodsActionForm.reset(actionMapping, httpServletRequest);
          } 
        } else {
          getUserManaGoodsType(httpServletRequest);
          httpServletRequest.setAttribute("close", "2");
        }  
    } else if (flag.equals("modify") || flag.equals("update")) {
      tag = "modify";
      String goodsId = (httpServletRequest.getParameter("goodsId") == null) ? 
        "" : httpServletRequest.getParameter("goodsId");
      String viewSql = "bbb.id,aaa.stockId";
      String fromSql = 
        "com.js.oa.routine.resource.po.GoodsPO aaa join aaa.goodsType bbb join bbb.stock ccc ";
      String whereSql = "where aaa.id = '" + goodsId + "'";
      Page page = new Page(viewSql, fromSql, whereSql);
      page.setPageSize(10000);
      page.setcurrentPage(1);
      List<Object[]> list = page.getResultList();
      if (list != null && list.size() > 0) {
        Object[] id = list.get(0);
        httpServletRequest.setAttribute("goodsTypeId", id[0]);
        httpServletRequest.setAttribute("stockIdValue", id[1]);
        viewSql = "aaa.id,aaa.name,aaa.parentname";
        fromSql = 
          "com.js.oa.routine.resource.po.GoodsTypePO aaa join aaa.stock bbb ";
        whereSql = "where bbb.id = '" + id[1].toString() + "'";
        Page page1 = new Page(viewSql, fromSql, whereSql);
        page1.setPageSize(10000);
        page1.setcurrentPage(1);
        List list1 = page1.getResultList();
        httpServletRequest.setAttribute("goodsTypeList", list1);
      } 
      if (flag.equals("modify")) {
        getUserManaGoodsType(httpServletRequest);
        getSingleGoods(goodsActionForm, httpServletRequest);
      } else if (update(goodsActionForm, httpServletRequest)) {
        httpServletRequest.setAttribute("close", "1");
        getUserManaGoodsType(httpServletRequest);
      } else {
        httpServletRequest.setAttribute("close", "2");
        getUserManaGoodsType(httpServletRequest);
        getSingleGoods(goodsActionForm, httpServletRequest);
      } 
    } else if ("addChooseGoodsType".equals(flag)) {
      tag = "add";
      if (goodsActionForm.getStockId() != null) {
        String viewSql = "aaa.id,aaa.name,aaa.parentname";
        String fromSql = 
          "com.js.oa.routine.resource.po.GoodsTypePO aaa , com.js.oa.routine.resource.po.StockPO bbb ";
        String whereSql = "where aaa.stockId=bbb.id and bbb.id =" + goodsActionForm.getStockId();
        Page page = new Page(viewSql, fromSql, whereSql);
        page.setPageSize(10000);
        page.setcurrentPage(1);
        List list = page.getResultList();
        httpServletRequest.setAttribute("goodsTypeList", list);
      } 
      getUserManaGoodsType(httpServletRequest);
      httpServletRequest.setAttribute("stockIdValue", 
          goodsActionForm.getStockId());
    } else if ("modifyChooseGoodsType".equals(flag)) {
      tag = "modify";
      if (goodsActionForm.getStockId() != null) {
        String viewSql = "aaa.id,aaa.name,aaa.parentname";
        String fromSql = 
          "com.js.oa.routine.resource.po.GoodsTypePO aaa join aaa.stock bbb ";
        String whereSql = "where bbb.id =" + goodsActionForm.getStockId();
        Page page = new Page(viewSql, fromSql, whereSql);
        page.setPageSize(10000);
        page.setcurrentPage(1);
        List list = page.getResultList();
        httpServletRequest.setAttribute("goodsTypeList", list);
      } 
      getUserManaGoodsType(httpServletRequest);
      httpServletRequest.setAttribute("stockIdValue", 
          goodsActionForm.getStockId());
    } else if (flag.equals("queryLibraryRangeMax")) {
      queryLibraryRangeMax(httpServletRequest, httpServletResponse);
      return null;
    } 
    String mark = httpServletRequest.getParameter("mark");
    if ("intoStock".equals(mark)) {
      httpServletRequest.setAttribute("stockName", httpServletRequest.getParameter("stockName"));
      httpServletRequest.setAttribute("stockId", httpServletRequest.getParameter("stockId"));
      httpServletRequest.setAttribute("processId", httpServletRequest.getParameter("processId"));
      httpServletRequest.setAttribute("tableId", httpServletRequest.getParameter("tableId"));
      httpServletRequest.setAttribute("processName", httpServletRequest.getParameter("processName"));
      httpServletRequest.setAttribute("hasProcess", httpServletRequest.getParameter("hasProcess"));
      httpServletRequest.setAttribute("processType", httpServletRequest.getParameter("processType"));
      httpServletRequest.setAttribute("moduleId", httpServletRequest.getParameter("moduleId"));
      httpServletRequest.setAttribute("mode", httpServletRequest.getParameter("mode"));
      tag = "intoStockMark";
    } 
    if ("outStock".equals(mark)) {
      httpServletRequest.setAttribute("stockName", httpServletRequest.getParameter("stockName"));
      httpServletRequest.setAttribute("stockId", httpServletRequest.getParameter("stockId"));
      httpServletRequest.setAttribute("processId", httpServletRequest.getParameter("processId"));
      httpServletRequest.setAttribute("tableId", httpServletRequest.getParameter("tableId"));
      httpServletRequest.setAttribute("processName", httpServletRequest.getParameter("processName"));
      httpServletRequest.setAttribute("hasProcess", httpServletRequest.getParameter("hasProcess"));
      httpServletRequest.setAttribute("processType", httpServletRequest.getParameter("processType"));
      httpServletRequest.setAttribute("moduleId", httpServletRequest.getParameter("moduleId"));
      httpServletRequest.setAttribute("mode", httpServletRequest.getParameter("mode"));
      tag = "outStockMark";
    } 
    if ("outStockReturn".equals(mark)) {
      httpServletRequest.setAttribute("stockName", httpServletRequest.getParameter("stockName"));
      httpServletRequest.setAttribute("stockId", httpServletRequest.getParameter("stockId"));
      httpServletRequest.setAttribute("processId", httpServletRequest.getParameter("processId"));
      httpServletRequest.setAttribute("tableId", httpServletRequest.getParameter("tableId"));
      httpServletRequest.setAttribute("processName", httpServletRequest.getParameter("processName"));
      httpServletRequest.setAttribute("hasProcess", httpServletRequest.getParameter("hasProcess"));
      httpServletRequest.setAttribute("processType", httpServletRequest.getParameter("processType"));
      httpServletRequest.setAttribute("moduleId", httpServletRequest.getParameter("moduleId"));
      httpServletRequest.setAttribute("mode", httpServletRequest.getParameter("mode"));
      tag = "outStockReturn";
    } 
    if ("outStockReturnMark".equals(mark)) {
      httpServletRequest.setAttribute("stockName", httpServletRequest.getParameter("stockName"));
      httpServletRequest.setAttribute("stockId", httpServletRequest.getParameter("stockId"));
      httpServletRequest.setAttribute("processId", httpServletRequest.getParameter("processId"));
      httpServletRequest.setAttribute("tableId", httpServletRequest.getParameter("tableId"));
      httpServletRequest.setAttribute("processName", httpServletRequest.getParameter("processName"));
      httpServletRequest.setAttribute("hasProcess", httpServletRequest.getParameter("hasProcess"));
      httpServletRequest.setAttribute("processType", httpServletRequest.getParameter("processType"));
      httpServletRequest.setAttribute("moduleId", httpServletRequest.getParameter("moduleId"));
      httpServletRequest.setAttribute("mode", httpServletRequest.getParameter("mode"));
      tag = "outStockGoodsList";
    } 
    String modeName = httpServletRequest.getParameter("mode");
    String nameValue = httpServletRequest.getParameter("nameValue");
    if ("salesreturn".equals(modeName) && "tuihuo".equals(nameValue))
      tag = "intoStockGoodsList"; 
    return actionMapping.findForward(tag);
  }
  
  private void getUserManaGoodsType(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : 
      httpSession.getAttribute("domainId").toString();
    String curUserId = httpServletRequest.getSession(true).getAttribute(
        "userId")
      .toString();
    String viewSql = "aaa.id,aaa.stockName";
    String fromSql = "com.js.oa.routine.resource.po.StockPO aaa";
    String whereSql = " where aaa.domainid=" + domainId + " and aaa.stockPut like '%$" + curUserId + 
      "$%' order by aaa.id desc";
    Page page = new Page(viewSql, fromSql, whereSql);
    page.setPageSize(10000);
    page.setcurrentPage(1);
    List list = page.getResultList();
    httpServletRequest.setAttribute("stockList", list);
    String tempFlag = httpServletRequest.getParameter("flag");
    if (!"add".equals(tempFlag) && 
      !"continue".equals(tempFlag) && !"modify".equals(tempFlag) && 
      !"modifyChooseGoodsType".equals(tempFlag) && 
      !"addChooseGoodsType".equals(tempFlag) && !"update".equals(tempFlag) && 
      !"close".equals(tempFlag)) {
      viewSql = "aaa.id,bbb.name,aaa.name,aaa.unit,aaa.price,bbb.parentname,aaa.maxamount,aaa.minamount,ccc.id,ccc.stockName,bbb.id";
      fromSql = "com.js.oa.routine.resource.po.GoodsPO aaa join aaa.goodsType bbb join bbb.stock ccc";
      whereSql = "where aaa.stockId = ccc.id and bbb.domainid=" + 
        domainId;
      whereSql = String.valueOf(whereSql) + " and ccc.stockPut like '%$" + curUserId + 
        "$%'";
      if (httpServletRequest.getParameter("stockId") != null)
        whereSql = String.valueOf(whereSql) + " and ccc.id = " + 
          httpServletRequest.getParameter("stockId"); 
      whereSql = String.valueOf(whereSql) + " order by aaa.id";
      Page page1 = new Page(viewSql, fromSql, whereSql);
      page1.setPageSize(10000);
      page1.setcurrentPage(1);
      httpServletRequest.setAttribute("goodsTypeList", 
          page1.getResultList());
      viewSql = "aaa.id,aaa.name,aaa.parentname,bbb.stockName";
      fromSql = 
        "com.js.oa.routine.resource.po.GoodsTypePO aaa , com.js.oa.routine.resource.po.StockPO bbb ";
      whereSql = " where 1=1 ";
      if (httpServletRequest.getParameter("stockId") != null)
        whereSql = String.valueOf(whereSql) + " and aaa.stockId = " + 
          httpServletRequest.getParameter("stockId"); 
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        whereSql = String.valueOf(whereSql) + " and aaa.stockId=bbb.id and aaa.domainid=" + domainId + 
          " and bbb.stockPut like '%$" + curUserId + 
          "$%' order by bbb.id,concat(aaa.parentid, '-', aaa.id)";
      } else {
        whereSql = String.valueOf(whereSql) + " and aaa.stockId=bbb.id and aaa.domainid=" + domainId + 
          " and bbb.stockPut like '%$" + curUserId + "$%' order by bbb.id,JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(JSDB.FN_INTTOSTR(JSDB.FN_GETONEVALUE(aaa.parentid,aaa.id)),'-'),JSDB.FN_INTTOSTR(aaa.id))";
      } 
      Page page2 = new Page(viewSql, fromSql, whereSql);
      page2.setPageSize(10000);
      page2.setcurrentPage(1);
      List lists = page2.getResultList();
      httpServletRequest.setAttribute("searchGoodsTypeList", lists);
    } 
  }
  
  private boolean save(GoodsActionForm goodsActionForm, HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = "";
    if (session.getAttribute("domainId") != null && 
      !session.getAttribute("domainId").equals("null"))
      domainId = session.getAttribute("domainId").toString(); 
    GoodsPO goodsPO = new GoodsPO();
    goodsPO.setId(goodsActionForm.getId());
    goodsPO.setName(goodsActionForm.getName());
    goodsPO.setUnit(goodsActionForm.getUnit());
    goodsPO.setPrice(goodsActionForm.getPrice());
    goodsPO.setMaxamount(goodsActionForm.getMaxamount());
    goodsPO.setMinamount(goodsActionForm.getMinamount());
    goodsPO.setRemark(goodsActionForm.getRemark());
    goodsPO.setCreatedEmp(session.getAttribute("userId").toString());
    goodsPO.setCreatedOrg(session.getAttribute("orgId").toString());
    goodsPO.setSpecs(goodsActionForm.getSpecs());
    goodsPO.setModel(goodsActionForm.getModel());
    if (httpServletRequest.getParameter("goodsPicName") != null)
      goodsPO.setPic(httpServletRequest.getParameter("goodsPicName")); 
    if (!domainId.equals("") || domainId.length() > 0)
      goodsPO.setDomainid(Integer.parseInt(domainId)); 
    StringBuffer goodsTypeIdandStcokId = new StringBuffer();
    goodsTypeIdandStcokId.append(goodsActionForm.getGoodsType());
    goodsTypeIdandStcokId.append("#");
    goodsTypeIdandStcokId.append(goodsActionForm.getStockId());
    return (new GoodsBD()).save(goodsPO, goodsTypeIdandStcokId.toString())
      .booleanValue();
  }
  
  private void userGoodsView(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = "0";
    if (session.getAttribute("domainId") != null && 
      !session.getAttribute("domainId").equals("null"))
      domainId = session.getAttribute("domainId").toString(); 
    String viewSql = "distinct aaa.id,bbb.name,aaa.name,aaa.unit,aaa.price,bbb.parentname,aaa.maxamount,aaa.minamount,ccc.id,ccc.stockName,aaa.remark,aaa.remark,aaa.specs,aaa.price,aaa.status,aaa.status,aaa.pic";
    String fromSql = "com.js.oa.routine.resource.po.GoodsPO aaa join aaa.goodsType bbb , com.js.oa.routine.resource.po.StockPO ccc";
    String whereSql = "where aaa.domainid=" + domainId + " and aaa.stockId=ccc.id ";
    if (httpServletRequest.getParameter("stockId") != null)
      whereSql = String.valueOf(whereSql) + " and ccc.id=" + 
        httpServletRequest.getParameter("stockId") + " "; 
    if (httpServletRequest.getParameter("chuku") != null && 
      "1".equals(httpServletRequest.getParameter("chuku"))) {
      fromSql = "com.js.oa.routine.resource.po.GoodsPO aaa join aaa.goodsType bbb , com.js.oa.routine.resource.po.StockPO ccc,com.js.oa.routine.resource.po.GoodsStockPO ddd";
      whereSql = String.valueOf(whereSql) + " and ddd.goodsId = aaa.id and ddd.amount>0 ";
    } 
    whereSql = String.valueOf(whereSql) + "  order by aaa.id";
    list(httpServletRequest, viewSql, fromSql, whereSql);
  }
  
  private void userGoodsView2(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = "0";
    if (session.getAttribute("domainId") != null && 
      !session.getAttribute("domainId").equals("null"))
      domainId = session.getAttribute("domainId").toString(); 
    String searchGoodsType = httpServletRequest.getParameter(
        "searchGoodsType");
    httpServletRequest.setAttribute("searchGoodsType", searchGoodsType);
    String searchGoodsId = httpServletRequest.getParameter("searchGoodsId");
    httpServletRequest.setAttribute("searchGoodsId", searchGoodsId);
    String searchGoodsName = httpServletRequest.getParameter("searchGoodsName");
    String goodsSpecs = httpServletRequest.getParameter("goodsSpecs");
    httpServletRequest.setAttribute("searchGoodsName", searchGoodsName);
    String viewSql = "distinct aaa.id,bbb.name,aaa.name,aaa.unit,aaa.price,bbb.parentname,aaa.maxamount,aaa.minamount,ccc.id,ccc.stockName,aaa.remark,aaa.remark,aaa.specs,aaa.price,aaa.status,aaa.status,aaa.pic";
    String fromSql = "com.js.oa.routine.resource.po.GoodsPO aaa join aaa.goodsType bbb , com.js.oa.routine.resource.po.StockPO ccc ";
    String _ids = "'-1'";
    String userId = session.getAttribute("userId").toString();
    Page page = new Page("a.id,sum(b.changeAmount),sum(b.factMoney)", "com.js.oa.routine.resource.po.GoodsPO a, com.js.oa.routine.resource.po.StockChangePO b, com.js.oa.routine.resource.po.SsMasterPO c", "where a.id=b.goodsId and c.ssStock = " + httpServletRequest.getParameter("stockId") + " and b.scNo = c.id and c.ssOutFlag = '1' and c.makeMan=" + userId + " and b.changeType like 'D%' group by a.id");
    page.setPageSize(999999);
    page.setcurrentPage(1);
    List ll = page.getResultList();
    if (ll != null && ll.size() > 0) {
      for (Iterator<Object[]> it = ll.iterator(); it.hasNext(); ) {
        Object[] obj = it.next();
        if (obj[1] != null && Float.parseFloat(obj[1].toString()) > 0.0F)
          _ids = String.valueOf(_ids) + ",'" + obj[0].toString() + "'"; 
      } 
      httpServletRequest.setAttribute("outStockList", ll);
    } 
    String whereSql = "where aaa.domainid=" + domainId + 
      " and aaa.stockId=ccc.id ";
    whereSql = String.valueOf(whereSql) + " and aaa.id in (" + _ids + ")";
    if (httpServletRequest.getParameter("stockId") != null)
      whereSql = String.valueOf(whereSql) + " and ccc.id=" + 
        httpServletRequest.getParameter("stockId") + " "; 
    if (searchGoodsName != null && !"".equals(searchGoodsName) && 
      !"null".equals(searchGoodsName))
      whereSql = String.valueOf(whereSql) + " and aaa.name like '%" + searchGoodsName + "%'"; 
    if (searchGoodsType != null && !"".equals(searchGoodsType) && 
      !"null".equals(searchGoodsType))
      whereSql = String.valueOf(whereSql) + " and aaa.goodsType.id = " + searchGoodsType + " "; 
    if (searchGoodsId != null && !"".equals(searchGoodsId) && 
      !"null".equals(searchGoodsId))
      whereSql = String.valueOf(whereSql) + " and aaa.id like '%" + searchGoodsId + "%'"; 
    if (goodsSpecs != null && !"".equals(goodsSpecs) && 
      !"null".equals(goodsSpecs))
      whereSql = String.valueOf(whereSql) + " and aaa.specs like '%" + goodsSpecs + "%'"; 
    if (httpServletRequest.getParameter("chuku") != null && 
      "1".equals(httpServletRequest.getParameter("chuku"))) {
      fromSql = "com.js.oa.routine.resource.po.GoodsPO aaa join aaa.goodsType bbb , com.js.oa.routine.resource.po.StockPO ccc,com.js.oa.routine.resource.po.GoodsStockPO ddd";
      whereSql = String.valueOf(whereSql) + " and ddd.goodsId = aaa.id and ddd.amount>0 ";
    } 
    whereSql = String.valueOf(whereSql) + "  order by aaa.id";
    list(httpServletRequest, viewSql, fromSql, whereSql);
  }
  
  private void view(HttpServletRequest httpServletRequest) {
    String domainId = "";
    HttpSession httpSession = httpServletRequest.getSession(true);
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    String viewSql = "distinct aaa.id,bbb.name,aaa.name,aaa.unit,aaa.price,bbb.parentname,aaa.maxamount,aaa.minamount,ccc.id,ccc.stockName,bbb.id,aaa.remark,aaa.specs,aaa.specs,aaa.price,aaa.status,aaa.pic";
    String fromSql = "com.js.oa.routine.resource.po.GoodsPO aaa join aaa.goodsType bbb , com.js.oa.routine.resource.po.StockPO ccc";
    String curUserId = httpServletRequest.getSession(true).getAttribute(
        "userId")
      .toString();
    String whereSql = "where aaa.domainid=" + domainId + 
      " and aaa.stockId=ccc.id and ccc.stockPut like '%$" + curUserId + 
      "$%' and aaa.stockId = ccc.id order by aaa.id";
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
    httpServletRequest.setAttribute("goodsList", list);
    httpServletRequest.setAttribute("addGoods", httpServletRequest.getParameter("addGoods"));
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", 
        "flag,searchGoodsType,searchGoodsId,searchGoodsName,addGoods,stockId,searchKc,chuku,addcolumn,mode,fromMenu,nameValue");
  }
  
  private void getSingleGoods(GoodsActionForm goodsActionForm, HttpServletRequest httpServletRequest) {
    String goodsId = httpServletRequest.getParameter("goodsId");
    String[] goods = (new GoodsBD()).getSingleGoods(goodsId);
    DecimalFormat format = new DecimalFormat("0.00");
    goodsActionForm.setId(goodsId);
    httpServletRequest.setAttribute("goodsTypeId", goods[0]);
    if (goods[3] == null || "".equals((new StringBuilder(String.valueOf(goods[3]))).toString()))
      goods[3] = "0"; 
    if (goods[5] == null || "".equals((new StringBuilder(String.valueOf(goods[5]))).toString()))
      goods[5] = "0"; 
    if (goods[6] == null || "".equals((new StringBuilder(String.valueOf(goods[6]))).toString()))
      goods[6] = "0"; 
    goodsActionForm.setName(goods[1]);
    goodsActionForm.setUnit(goods[2]);
    goodsActionForm.setPrice(format.format(Double.parseDouble(goods[3])));
    goodsActionForm.setRemark(goods[4]);
    format = new DecimalFormat("0.00");
    goodsActionForm.setMaxamount(format.format(Double.parseDouble(
            goods[5])));
    goodsActionForm.setMinamount(format.format(Double.parseDouble(
            goods[6])));
    goodsActionForm.setSpecs(goods[7]);
    goodsActionForm.setModel(goods[8]);
    httpServletRequest.setAttribute("pic", goods[9]);
  }
  
  public boolean update(GoodsActionForm goodsActionForm, HttpServletRequest httpServletRequest) {
    GoodsPO goodsPO = new GoodsPO();
    goodsPO.setId(goodsActionForm.getId());
    goodsPO.setName(goodsActionForm.getName());
    goodsPO.setUnit(goodsActionForm.getUnit());
    goodsPO.setPrice(goodsActionForm.getPrice());
    goodsPO.setMaxamount(goodsActionForm.getMaxamount());
    goodsPO.setMinamount(goodsActionForm.getMinamount());
    goodsPO.setRemark(goodsActionForm.getRemark());
    goodsPO.setStockId(goodsActionForm.getStockId());
    goodsPO.setSpecs(goodsActionForm.getSpecs());
    goodsPO.setModel(goodsActionForm.getModel());
    if (httpServletRequest.getParameter("goodsPicName") != null)
      goodsPO.setPic(httpServletRequest.getParameter("goodsPicName")); 
    String domainId = "";
    HttpSession session = httpServletRequest.getSession(true);
    if (session.getAttribute("domainId") != null && 
      !session.getAttribute("domainId").equals("null"))
      domainId = session.getAttribute("domainId").toString(); 
    if (!domainId.equals("") || domainId.length() > 0)
      goodsPO.setDomainid(Integer.parseInt(domainId)); 
    return (new GoodsBD()).update(goodsPO, goodsActionForm.getGoodsType())
      .booleanValue();
  }
  
  public void delete(HttpServletRequest httpServletRequest) {
    httpServletRequest.setAttribute("deleteSuccess", (
        new GoodsBD()).delete(httpServletRequest
          .getParameter("goodsId")));
  }
  
  public void noused(HttpServletRequest httpServletRequest) {
    httpServletRequest.setAttribute("nousedSuccess", (
        new GoodsBD()).noused(httpServletRequest
          .getParameter("goodsId"), httpServletRequest.getParameter("opt")));
  }
  
  private void search(HttpServletRequest httpServletRequest) {
    String domainId = "";
    HttpSession httpSession = httpServletRequest.getSession(true);
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    String goodsSpecs = httpServletRequest.getParameter("goodsSpecs");
    String searchGoodsType = httpServletRequest.getParameter(
        "searchGoodsType");
    String searchKc = httpServletRequest.getParameter("searchKc");
    httpServletRequest.setAttribute("searchKc", searchKc);
    httpServletRequest.setAttribute("searchGoodsType", searchGoodsType);
    String searchGoodsId = httpServletRequest.getParameter("searchGoodsId");
    httpServletRequest.setAttribute("searchGoodsId", searchGoodsId);
    String searchGoodsName = httpServletRequest.getParameter(
        "searchGoodsName");
    httpServletRequest.setAttribute("searchGoodsName", searchGoodsName);
    String viewSql = "distinct aaa.id,bbb.name,aaa.name,aaa.unit,aaa.price,bbb.parentname,aaa.maxamount,aaa.minamount,ccc.id,ccc.stockName,bbb.id,aaa.remark,aaa.specs,aaa.specs,aaa.price,aaa.status,aaa.pic";
    String fromSql = "com.js.oa.routine.resource.po.GoodsPO aaa join aaa.goodsType bbb , com.js.oa.routine.resource.po.StockPO ccc";
    StringBuffer whereSql = new StringBuffer("where aaa.domainid=" + 
        domainId + 
        " and  aaa.stockId = ccc.id ");
    if (httpServletRequest.getParameter("addGoods") != null && 
      !httpServletRequest.getParameter("addGoods").equals("0")) {
      fromSql = "com.js.oa.routine.resource.po.GoodsPO aaa join aaa.goodsType bbb , com.js.oa.routine.resource.po.StockPO ccc";
      whereSql.append(" and ccc.id=" + 
          httpServletRequest.getParameter("stockId"));
    } 
    if (searchGoodsType != null && !"0".equals(searchGoodsType))
      whereSql.append(" and bbb.id='" + searchGoodsType + "' "); 
    if ("1".equals(searchKc)) {
      String dataType = 
        SystemCommon.getDatabaseType();
      if (dataType.indexOf("mysql") >= 0) {
        whereSql.append(" and aaa.maxamount < (select goodstock.amount from com.js.oa.routine.resource.po.GoodsStockPO goodstock where goodstock.goodsId=aaa.id and goodstock.stockId=ccc.id) ");
      } else {
        whereSql.append(
            " and aaa.maxamount < JSDB.FN_GETGOODSKC(aaa.id,ccc.id) ");
      } 
    } 
    if ("2".equals(searchKc)) {
      String dataType = 
        SystemCommon.getDatabaseType();
      if (dataType.indexOf("mysql") >= 0) {
        whereSql.append(" and aaa.minamount > (select goodstock.amount from com.js.oa.routine.resource.po.GoodsStockPO goodstock where goodstock.goodsId=aaa.id and goodstock.stockId=ccc.id)");
      } else {
        whereSql.append(
            " and aaa.minamount > JSDB.FN_GETGOODSKC(aaa.id,ccc.id) ");
      } 
    } 
    if (searchGoodsId != null && !"".equals(searchGoodsId) && !"null".equals(searchGoodsId))
      whereSql.append(" and aaa.id like '%" + searchGoodsId + "%'"); 
    if (searchGoodsName != null && !"".equals(searchGoodsName) && !"null".equals(searchGoodsName))
      whereSql.append(" and aaa.name like '%" + searchGoodsName + "%'"); 
    if (goodsSpecs != null && !"".equals(goodsSpecs) && !"null".equals(goodsSpecs))
      whereSql.append(" and aaa.specs like '%" + goodsSpecs + "%'"); 
    httpServletRequest.setAttribute("goodsSpecs", goodsSpecs);
    if (httpServletRequest.getParameter("addGoods") != null && 
      httpServletRequest.getParameter("addGoods").equals("0")) {
      String curUserId = httpServletRequest.getSession(true).getAttribute(
          "userId")
        .toString();
      whereSql.append(" and ccc.stockPut like '%$" + curUserId + "$%' ");
    } 
    if (httpServletRequest.getParameter("chuku") != null && 
      "1".equals(httpServletRequest.getParameter("chuku"))) {
      fromSql = "com.js.oa.routine.resource.po.GoodsPO aaa join aaa.goodsType bbb , com.js.oa.routine.resource.po.StockPO ccc,com.js.oa.routine.resource.po.GoodsStockPO ddd";
      whereSql.append(" and ddd.goodsId = aaa.id and ddd.amount>0 ");
    } 
    whereSql.append(" order by aaa.id ");
    list(httpServletRequest, viewSql, fromSql, whereSql.toString());
  }
  
  private String getVindicate(HttpServletRequest httpServletRequest) {
    String viewSql = 
      "distinct aaa.id ";
    String fromSql = 
      "com.js.oa.routine.resource.po.StockPO aaa ";
    String curUserId = httpServletRequest.getSession(true).getAttribute(
        "userId")
      .toString();
    String whereSql = " where aaa.stockPut like '%$" + curUserId + "$%' ";
    Page page = new Page(viewSql, fromSql, whereSql);
    page.setPageSize(9999);
    page.setcurrentPage(1);
    List list = page.getResultList();
    if (list != null && list.size() > 0)
      return "true"; 
    return "";
  }
  
  public void queryLibraryRangeMax(HttpServletRequest request, HttpServletResponse response) {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      String sql = "SELECT DISTINCT g.MAXAMOUNT, gs.STOCK_AMOUNT FROM ST_GOODSSTOCK gs RIGHT JOIN ST_GOODS g ON gs.GOODS_ID=g.GOODS_ID WHERE g.GOODS_ID='G_GOODS_ID'";
      sql = sql.replace("G_GOODS_ID", request.getParameter("GOODS_ID").trim());
      rs = stmt.executeQuery(sql);
      int rangeMaxValue = 0;
      if (rs.next()) {
        int maxAmount = rs.getInt(1);
        int stockAmount = rs.getInt(2);
        rangeMaxValue = maxAmount - stockAmount;
      } 
      response.setContentType("text/plain;charset=GBK");
      response.setHeader("Cache-Control", "no-cache");
      PrintWriter out = response.getWriter();
      out.print(rangeMaxValue);
      out.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (rs != null)
        try {
          rs.close();
        } catch (SQLException sQLException) {} 
      if (stmt != null)
        try {
          stmt.close();
        } catch (SQLException sQLException) {} 
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException sQLException) {} 
    } 
  }
}
