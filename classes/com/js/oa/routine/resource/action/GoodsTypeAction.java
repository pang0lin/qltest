package com.js.oa.routine.resource.action;

import com.js.oa.routine.resource.po.GoodsTypePO;
import com.js.oa.routine.resource.service.GoodsTypeBD;
import com.js.oa.routine.resource.service.StockBD;
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

public class GoodsTypeAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    GoodsTypeActionForm goodsTypeActionForm = 
      (GoodsTypeActionForm)actionForm;
    String flag = (httpServletRequest.getParameter("flag") == null) ? "view" : 
      httpServletRequest.getParameter("flag");
    String tag = "view";
    if (flag.equals("view") || flag.equals("delete") || 
      flag.equals("search")) {
      httpServletRequest.setAttribute("vindicate", 
          getVindicate(httpServletRequest));
      HttpSession httpSession = httpServletRequest.getSession(true);
      httpServletRequest.setAttribute("addRight", 
          getVindicate(httpServletRequest));
      tag = "view";
      if (flag.equals("delete"))
        httpServletRequest.setAttribute("deleteSuccess", 
            delete(httpServletRequest
              .getParameter("goodsTypeId"), httpServletRequest
              .getParameter("stockId"))); 
      if (flag.equals("search")) {
        search(httpServletRequest);
      } else {
        view(httpServletRequest);
      } 
    } else if (flag.equals("add") || flag.equals("continue") || 
      flag.equals("close")) {
      tag = "add";
      if (flag.equals("continue") || flag.equals("add"))
        getUserManaStock(httpServletRequest); 
      if (flag.equals("continue") || flag.equals("close"))
        if (save(goodsTypeActionForm, httpServletRequest)) {
          if (flag.equals("continue")) {
            getUserManaStock(httpServletRequest);
            httpServletRequest.setAttribute("close", "0");
          } else {
            httpServletRequest.setAttribute("close", "1");
          } 
        } else {
          httpServletRequest.setAttribute("close", "2");
          getUserManaStock(httpServletRequest);
        }  
    } else if (flag.equals("modify") || flag.equals("update")) {
      tag = "modify";
      if (flag.equals("modify")) {
        getUserManaStock(httpServletRequest);
        getSingleGoodsType(goodsTypeActionForm, httpServletRequest);
        getGoodsTypewithStock(httpServletRequest);
        httpServletRequest.setAttribute("stockId", httpServletRequest
            .getParameter("stockId"));
      } else if (update(goodsTypeActionForm, httpServletRequest)) {
        getUserManaStock(httpServletRequest);
        getSingleGoodsType(goodsTypeActionForm, httpServletRequest);
        httpServletRequest.setAttribute("close", "1");
      } else {
        getUserManaStock(httpServletRequest);
        getSingleGoodsType(goodsTypeActionForm, httpServletRequest);
        getGoodsTypewithStock(httpServletRequest);
        httpServletRequest.setAttribute("stockId", 
            httpServletRequest
            .getParameter("stockId"));
        httpServletRequest.setAttribute("close", "2");
      } 
    } else if ("loadGoodsType".equals(flag)) {
      tag = "add";
      httpServletRequest.setAttribute("stockIdValue", 
          httpServletRequest
          .getParameter("stockId"));
      getUserManaStock(httpServletRequest);
      getGoodsTypewithStock(httpServletRequest);
    } 
    return actionMapping.findForward(tag);
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
    httpServletRequest.setAttribute("parentTypeList", (
        new GoodsTypeBD())
        .getUserManaGoodsTypeParent(httpSession
          .getAttribute("userId").toString()));
  }
  
  private boolean save(GoodsTypeActionForm goodsTypeActionForm, HttpServletRequest httpServletRequest) {
    GoodsTypePO goodsTypePO = new GoodsTypePO();
    goodsTypePO.setName(goodsTypeActionForm.getGoodsTypeName());
    goodsTypePO.setRemark(goodsTypeActionForm.getRemark());
    goodsTypePO.setParentid(new Long(goodsTypeActionForm.getParentTypeId()));
    HttpSession httpSession = httpServletRequest.getSession(true);
    goodsTypePO.setCreatedEmp(new Long(httpSession.getAttribute("userId")
          .toString()));
    goodsTypePO.setCreatedOrg(new Long(httpSession.getAttribute("orgId")
          .toString()));
    goodsTypePO.setGoodsTypePrefix(goodsTypeActionForm.getGoodsTypePrefix());
    String domainId = "";
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    if (!domainId.equals("") || domainId.length() > 0)
      goodsTypePO.setDomainid(Integer.parseInt(domainId)); 
    return (new GoodsTypeBD()).save(goodsTypePO, 
        goodsTypeActionForm.getStockId())
      .booleanValue();
  }
  
  private void view(HttpServletRequest httpServletRequest) {
    String domainId = "";
    HttpSession httpSession = httpServletRequest.getSession(true);
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    String viewSql = "aaa.id,aaa.name,bbb.stockName,aaa.remark,aaa.parentid,bbb.id,aaa.parentname,aaa.goodsTypePrefix";
    String fromSql = 
      "com.js.oa.routine.resource.po.GoodsTypePO aaa , com.js.oa.routine.resource.po.StockPO bbb";
    String whereSql = "";
    String curUserId = httpServletRequest.getSession(true).getAttribute(
        "userId")
      .toString();
    String databaseType = 
      SystemCommon.getDatabaseType();
    if (databaseType.indexOf("mysql") >= 0) {
      whereSql = " where aaa.stockId=bbb.id and aaa.domainid=" + domainId + 
        " and bbb.stockPut like '%$" + curUserId + 
        "$%' order by bbb.id,concat(aaa.parentid, '-', aaa.id)";
    } else {
      whereSql = " where aaa.stockId=bbb.id and aaa.domainid=" + domainId + 
        " and bbb.stockPut like '%$" + curUserId + "$%' order by bbb.id,JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(JSDB.FN_INTTOSTR(JSDB.FN_GETONEVALUE(aaa.parentid,aaa.id)),'-'),JSDB.FN_INTTOSTR(aaa.id))";
    } 
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
    if (list != null && 
      list.size() == 0 && offset >= 15) {
      offset -= 15;
      currentPage = offset / pageSize + 15;
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      httpServletRequest.setAttribute("new.offset", (new StringBuilder(String.valueOf(offset))).toString());
      httpServletRequest.setAttribute("pager.realCurrent", (
          new StringBuilder(String.valueOf(currentPage))).toString());
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("goodsTypeList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", 
        "searchGoodsTypeName,flag");
  }
  
  private void getSingleGoodsType(GoodsTypeActionForm goodsTypeActionForm, HttpServletRequest httpServletRequest) {
    String goodsTypeId = httpServletRequest.getParameter("goodsTypeId");
    String[] goodsType = (new GoodsTypeBD()).getSingleGoodsType(goodsTypeId);
    goodsTypeActionForm.setGoodsTypeName(goodsType[0]);
    goodsTypeActionForm.setGoodsTypePrefix(goodsType[5]);
    goodsTypeActionForm.setRemark(goodsType[1]);
    httpServletRequest.setAttribute("stockId", goodsType[2]);
    httpServletRequest.setAttribute("goodsTypeId", goodsTypeId);
    httpServletRequest.setAttribute("parentTypeId", goodsType[3]);
    httpServletRequest.setAttribute("hideparentid", goodsType[3]);
    httpServletRequest.setAttribute("subcount", goodsType[4]);
  }
  
  private boolean update(GoodsTypeActionForm goodsTypeActionForm, HttpServletRequest httpServletRequest) {
    GoodsTypePO goodsTypePO = new GoodsTypePO();
    goodsTypePO.setName(goodsTypeActionForm.getGoodsTypeName());
    goodsTypePO.setId(new Long(httpServletRequest.getParameter(
            "goodsTypeId")));
    goodsTypePO.setParentid(new Long(goodsTypeActionForm.getParentTypeId()));
    goodsTypePO.setRemark(goodsTypeActionForm.getRemark());
    goodsTypePO.setGoodsTypePrefix(goodsTypeActionForm.getGoodsTypePrefix());
    String domainId = "";
    HttpSession httpSession = httpServletRequest.getSession(true);
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    if (!domainId.equals("") || domainId.length() > 0)
      goodsTypePO.setDomainid(Integer.parseInt(domainId)); 
    StringBuffer newStockIdAndOldStockId = new StringBuffer();
    newStockIdAndOldStockId.append(goodsTypeActionForm.getStockId());
    newStockIdAndOldStockId.append(",");
    newStockIdAndOldStockId.append(httpServletRequest.getParameter(
          "oldstockId"));
    return (new GoodsTypeBD()).update(goodsTypePO, 
        newStockIdAndOldStockId.toString())
      .booleanValue();
  }
  
  private String delete(String goodsTypeId, String stockId) {
    if (goodsTypeId != null && stockId != null) {
      StringBuffer t = new StringBuffer();
      t.append(goodsTypeId);
      t.append("#");
      t.append(stockId);
      return (new GoodsTypeBD()).delete(t.toString());
    } 
    return "";
  }
  
  private void search(HttpServletRequest httpServletRequest) {
    String domainId = "";
    HttpSession httpSession = httpServletRequest.getSession(true);
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    String searchGoodsTypeName = httpServletRequest.getParameter(
        "searchGoodsTypeName");
    httpServletRequest.setAttribute("searchGoodsTypeName", 
        searchGoodsTypeName);
    String viewSql = 
      "aaa.id,aaa.name,bbb.stockName,aaa.remark,aaa.parentid,bbb.id,aaa.parentname,aaa.goodsTypePrefix";
    String fromSql = 
      "com.js.oa.routine.resource.po.GoodsTypePO aaa join aaa.stock bbb";
    String whereSql = "";
    String curUserId = httpServletRequest.getSession(true).getAttribute(
        "userId")
      .toString();
    String databaseType = 
      SystemCommon.getDatabaseType();
    if (databaseType.indexOf("mysql") >= 0) {
      whereSql = "where aaa.domainid=" + domainId + 
        " and bbb.stockPut like '%$" + curUserId + 
        "$%' and aaa.name like '%" + searchGoodsTypeName + 
        "%' order by bbb.id,concat(aaa.parentid,'-',aaa.id)";
    } else {
      whereSql = "where aaa.domainid=" + domainId + 
        " and bbb.stockPut like '%$" + curUserId + 
        "$%' and aaa.name like '%" + searchGoodsTypeName + "%' order by bbb.id,JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(JSDB.FN_INTTOSTR(JSDB.FN_GETONEVALUE(aaa.parentid,aaa.id)),'-'),JSDB.FN_INTTOSTR(aaa.id))";
    } 
    list(httpServletRequest, viewSql, fromSql, whereSql);
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
  
  private void getGoodsTypewithStock(HttpServletRequest httpServletRequest) {
    httpServletRequest.setAttribute("parentTypeList", (
        new GoodsTypeBD())
        .getUserManaGoodsTypeParent(
          httpServletRequest.getParameter("stockId").toString()));
  }
}
