package com.js.system.action.rssmanager;

import com.js.system.service.rssmanager.RssCategoryBD;
import com.js.system.vo.rssmanager.CategoryVO;
import com.js.util.page.Page;
import com.js.util.util.DateHelper;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class RssCategoryAction extends DispatchAction {
  public ActionForward goRssCategoryList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    categoryList(request, " order by po.createTime desc ");
    return mapping.findForward("goRssCategoryList");
  }
  
  public ActionForward addOrUpdateRssCategory(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String categoryId = request.getParameter("categoryId");
    String userId = session.getAttribute("userId").toString();
    String categoryName = request.getParameter("rssChannelTypeName");
    if (categoryName != null && !categoryName.equals("")) {
      CategoryVO rssVO = new CategoryVO();
      rssVO.setCategoryName(categoryName);
      rssVO.setCreateTime(DateHelper.date2String(new Date(), null));
      rssVO.setCreateUserId(Long.valueOf(Long.parseLong(userId)));
      if (categoryId != null && !categoryId.equals(""))
        rssVO.setCategoryId(Long.valueOf(categoryId)); 
      RssCategoryBD rssDB = new RssCategoryBD();
      rssDB.saveOrUpdateRssCategory(rssVO);
    } 
    return mapping.findForward("addOrUpdateRssCategory");
  }
  
  public ActionForward proModRssCategory(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String categoryId = request.getParameter("categoryId");
    RssCategoryBD rssDB = new RssCategoryBD();
    CategoryVO rssVO = rssDB.getSingleRssCategory(categoryId);
    request.setAttribute("rssVO", rssVO);
    return mapping.findForward("goProModRssCategory");
  }
  
  public ActionForward checkCate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String name = request.getParameter("typeName");
    RssCategoryBD rssDB = new RssCategoryBD();
    List list = rssDB.getSingleRssCategoryByName(name.trim());
    StringBuffer xml = new StringBuffer(1024);
    response.setContentType("text/xml;charset=GBK");
    PrintWriter out = response.getWriter();
    xml.append("<?xml version=\"1.0\" encoding=\"GBK\" ?>\n");
    xml.append("<result>\n");
    if (list == null || list.size() == 0) {
      xml.append("  <name>0</name>\n");
    } else {
      xml.append("  <name>1</name>\n");
    } 
    xml.append("</result>\n");
    out.print(xml.toString());
    out.close();
    return null;
  }
  
  public ActionForward delRssCategory(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String delType = request.getParameter("delType");
    String categoryId = request.getParameter("categoryId");
    String categoryIds = request.getParameter("categoryIds");
    if (delType != null && !delType.equals("")) {
      RssCategoryBD rssDB = new RssCategoryBD();
      if (delType.equals("single")) {
        String[] ids = (String[])null;
        if (categoryIds != null && !categoryIds.equals(""))
          ids = categoryIds.split(","); 
        rssDB.delCategory(categoryId, ids);
      } else {
        rssDB.delAllCategory();
      } 
    } 
    return goRssCategoryList(mapping, form, request, response);
  }
  
  private void categoryList(HttpServletRequest request, String wherePara) {
    Object object = (request.getSession(true).getAttribute("domainId") == null) ? "0" : request.getSession(true).getAttribute("domainId");
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    try {
      Page page = new Page("po.categoryId,po.categoryName,po.createUserId,po.createTime,po.rangeUser,po.rangeOrg,po.rangeGroup", "CategoryVO po ", wherePara);
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      request.setAttribute("rssCategoryList", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "method");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
