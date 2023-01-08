package com.js.oa.userdb.action;

import com.js.oa.userdb.po.BaseSetPO;
import com.js.oa.userdb.service.BaseSetBD;
import com.js.util.page.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class BaseSetAction extends Action {
  ActionForward forward = new ActionForward();
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) {
    String flag = req.getParameter("flag");
    HttpSession session = req.getSession();
    String userId = session.getAttribute("userId").toString();
    String orgId = session.getAttribute("orgId").toString();
    if ("list".equals(flag))
      return viewList(mapping, req, res); 
    if ("add".equals(flag)) {
      String baseTypeName = req.getParameter("baseTypeName");
      String Typecontent = req.getParameter("baseTypeContent");
      BaseSetPO po = new BaseSetPO();
      po.setBaseName(baseTypeName);
      po.setBaseContent(Typecontent);
      po.setBaseParent("0");
      po.setBaseOrder(0);
      po.setBaseUser(Long.valueOf(userId));
      po.setBaseOrg(Long.valueOf(orgId));
      BaseSetBD bd = new BaseSetBD();
      Long long_ = bd.addBaseType(po);
      String[] baseKey = req.getParameterValues("baseKey");
      String[] baseValue = req.getParameterValues("baseValue");
      String[] baseContent = req.getParameterValues("baseContent");
      String[] baseOrder = req.getParameterValues("baseOrder");
      String key = "";
      String value = "";
      int order = 0;
      String content = "";
      for (int i = 0; i < baseKey.length; i++) {
        key = (baseKey[i] == null || "null".equals(baseKey[i])) ? "" : baseKey[i];
        value = (baseValue[i] == null || "null".equals(baseValue[i])) ? "" : baseValue[i];
        content = (baseContent[i] == null || "null".equals(baseContent[i])) ? "" : baseContent[i];
        if (!"".equals(key) || !"".equals(value)) {
          if ("".equals(key))
            key = value; 
          if ("".equals(value))
            value = key; 
          order = (baseOrder[i] == null || "".equals(baseOrder[i]) || "null".equals(baseOrder[i])) ? 0 : Integer.valueOf(baseOrder[i]).intValue();
          BaseSetPO bpo = new BaseSetPO();
          bpo.setBaseName("");
          bpo.setBaseKey(key);
          bpo.setBaseValue(value);
          bpo.setBaseContent(content);
          bpo.setBaseParent((String)long_);
          bpo.setBaseOrder(order);
          bpo.setBaseUser(Long.valueOf(userId));
          bpo.setBaseOrg(Long.valueOf(orgId));
          bd.addBaseType(bpo);
        } 
      } 
      req.setAttribute("reload", "yes");
      this.forward = mapping.findForward("add");
      return this.forward;
    } 
    if ("load".equals(flag)) {
      String baseId = req.getParameter("baseId");
      BaseSetBD bd = new BaseSetBD();
      BaseSetPO po = bd.load(baseId);
      List list = bd.loadBaseSet(baseId);
      req.setAttribute("list", list);
      req.setAttribute("baseSetPo", po);
      this.forward = mapping.findForward("load");
      return this.forward;
    } 
    if ("modi".equals(flag)) {
      String baseTypeId = req.getParameter("baseTypeId");
      String baseTypeName = req.getParameter("baseTypeName");
      String baseTypeContent = req.getParameter("baseTypeContent");
      String baseDel = req.getParameter("baseDel");
      BaseSetBD bd = new BaseSetBD();
      BaseSetPO po = bd.load(baseTypeId);
      po.setBaseOrder(0);
      po.setBaseName(baseTypeName);
      po.setBaseContent(baseTypeContent);
      bd.modi(po);
      String[] baseId = req.getParameterValues("baseId");
      String[] baseKey = req.getParameterValues("baseKey");
      String[] baseValue = req.getParameterValues("baseValue");
      String[] baseContent = req.getParameterValues("baseContent");
      String[] baseOrder = req.getParameterValues("baseOrder");
      String key = "";
      String value = "";
      int order = 0;
      String content = "";
      for (int i = 0; i < baseId.length; i++) {
        if ("-1".equals(baseId[i])) {
          key = (baseKey[i] == null || "null".equals(baseKey[i])) ? "" : baseKey[i];
          value = (baseValue[i] == null || "null".equals(baseValue[i])) ? "" : baseValue[i];
          content = (baseContent[i] == null || "null".equals(baseContent[i])) ? "" : baseContent[i];
          if (!"".equals(key) || !"".equals(value)) {
            if ("".equals(key))
              key = value; 
            if ("".equals(value))
              value = key; 
            order = (baseOrder[i] == null || "".equals(baseOrder[i]) || "null".equals(baseOrder[i])) ? 0 : Integer.valueOf(baseOrder[i]).intValue();
            BaseSetPO bpo = new BaseSetPO();
            bpo.setBaseName("");
            bpo.setBaseKey(key);
            bpo.setBaseValue(value);
            bpo.setBaseContent(content);
            bpo.setBaseParent(baseTypeId);
            bpo.setBaseOrder(order);
            bpo.setBaseUser(Long.valueOf(userId));
            bpo.setBaseOrg(Long.valueOf(orgId));
            bd.addBaseType(bpo);
          } 
        } else {
          key = (baseKey[i] == null || "null".equals(baseKey[i])) ? "" : baseKey[i];
          value = (baseValue[i] == null || "null".equals(baseValue[i])) ? "" : baseValue[i];
          content = (baseContent[i] == null || "null".equals(baseContent[i])) ? "" : baseContent[i];
          if (!"".equals(key) || !"".equals(value)) {
            if ("".equals(key))
              key = value; 
            if ("".equals(value))
              value = key; 
            order = (baseOrder[i] == null || "".equals(baseOrder[i]) || "null".equals(baseOrder[i])) ? 0 : Integer.valueOf(baseOrder[i]).intValue();
            BaseSetPO bpo = bd.load(baseId[i]);
            bpo.setBaseName("");
            bpo.setBaseKey(key);
            bpo.setBaseValue(value);
            bpo.setBaseContent(content);
            bpo.setBaseOrder(order);
            bd.modi(bpo);
          } else {
            del(baseId[i]);
          } 
        } 
      } 
      if (!"".equals(baseDel))
        del(baseDel); 
      req.setAttribute("baseSetPo", po);
      req.setAttribute("list", null);
      req.setAttribute("reload", "yes");
      this.forward = mapping.findForward("load");
      return this.forward;
    } 
    if ("del".equals(flag)) {
      String baseId = req.getParameter("baseId");
      BaseSetBD bd = new BaseSetBD();
      bd.del(baseId);
      req.setAttribute("reload", "yes");
      return viewList(mapping, req, res);
    } 
    if ("dels".equals(flag)) {
      String baseIds = req.getParameter("baseIds");
      del(baseIds);
      req.setAttribute("reload", "yes");
      return viewList(mapping, req, res);
    } 
    return null;
  }
  
  public void del(String baseIds) {
    String[] baseId = baseIds.split(",");
    BaseSetBD bd = new BaseSetBD();
    for (int i = 0; i < baseId.length; i++)
      bd.del(baseId[i]); 
  }
  
  public ActionForward viewList(ActionMapping mapping, HttpServletRequest request, HttpServletResponse res) {
    String where = "where ";
    String para = "base";
    String from = "com.js.oa.userdb.po.BaseSetPO base";
    if (request.getParameter("baseId") != null) {
      String baseId = request.getParameter("baseId");
      where = String.valueOf(where) + "base.baseParent='" + baseId + "' ";
    } else {
      where = String.valueOf(where) + "base.baseParent='0' ";
    } 
    if (request.getParameter("searchName") != null) {
      request.setAttribute("searchName", request.getParameter("searchName"));
      where = String.valueOf(where) + "and base.baseName like '%" + request.getParameter("searchName") + "%'";
    } 
    where = String.valueOf(where) + " order by base.baseOrder , base.baseId desc";
    list(para, from, where, request);
    this.forward = mapping.findForward("list");
    return this.forward;
  }
  
  public void list(String para, String from, String where, HttpServletRequest request) {
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      if ("".equals(request.getParameter("pager.offset")) || "null".equals(request.getParameter("pager.offset"))) {
        offset = Integer.parseInt("0");
      } else {
        offset = Integer.parseInt(request.getParameter("pager.offset"));
      }  
    int currentPage = offset / pageSize + 1;
    Page page = new Page(para, from, where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    int recordCount = page.getRecordCount();
    if (offset > 0 && offset >= recordCount) {
      offset = (recordCount - pageSize) / pageSize;
      currentPage = offset + 1;
      offset *= pageSize;
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      recordCount = page.getRecordCount();
      request.setAttribute("pager.offset", String.valueOf(offset));
      request.setAttribute("pager.realCurrent", String.valueOf(currentPage));
    } 
    request.setAttribute("baseList", list);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("recordCount", String.valueOf(page.getRecordCount()));
    request.setAttribute("pageParameters", "flag,searchName");
  }
}
