package com.js.oa.weixin.report;

import com.js.oa.report.action.ReportActionForm;
import com.js.oa.report.service.ReportBD;
import com.js.system.manager.service.ManagerService;
import com.js.system.util.StaticParam;
import com.js.util.page.Page;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WeiXinReportAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    String action = request.getParameter("action");
    ReportActionForm reportForm = (ReportActionForm)actionForm;
    String orgId = session.getAttribute("orgId").toString();
    String userId = session.getAttribute("userId").toString();
    String corpId = session.getAttribute("corpId").toString();
    String userName = session.getAttribute("userName").toString();
    String domainId = session.getAttribute("domainId").toString();
    request.setAttribute("back", "1");
    String tag = action;
    if ("list".equals(action)) {
      try {
        reportList(request, "list");
        String typeId = (request.getParameter("id") == null) ? "" : request.getParameter("id");
        if (!"".equals(typeId) && !"0".equals(typeId)) {
          String hql = "select po.typeId from com.js.oa.report.po.ReportTypePO po where po.typeId=" + typeId + 
            " and po.oprEmpId like '%$" + userId + "$%'";
          List<?> list = (new ReportBD()).getType(hql);
          if (list.size() > 0) {
            request.setAttribute("opr", "y");
          } else {
            request.setAttribute("opr", "n");
          } 
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } else if ("class".equals(action)) {
      try {
        getReportType(request);
        reportList(request, "class");
      } catch (Exception e) {
        e.printStackTrace();
      } 
      tag = "class";
    } 
    return actionMapping.findForward(tag);
  }
  
  public void reportList(HttpServletRequest request, String type) throws Exception {
    HttpSession session = request.getSession(true);
    String keyword = (request.getParameter("keyword") != null) ? request.getParameter("keyword") : "";
    if (keyword != null && !"".equals(keyword))
      try {
        keyword = URLDecoder.decode(keyword, "utf-8");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }  
    String userId = session.getAttribute("userId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    String orgId = session.getAttribute("orgId").toString();
    int offset_page = 0;
    if (request.getParameter("beginIndex") != null)
      offset_page = Integer.parseInt(request.getParameter("beginIndex")); 
    int pageSize = 15;
    int currentPage = offset_page / pageSize + 1;
    String para = " po.reportId,po.reportName,po.reportDescribe,po.grfName,po.createEmp,po.viewScope,po.reportClass,po.reportUrl,po.phoneUrl ";
    String from = " com.js.oa.report.po.ReportPO po, com.js.oa.report.po.ReportTypePO tpo ";
    String where = " where po.reportType = tpo.typeId  ";
    String whereTmp = "";
    String typeWhere = "";
    String[] orgIds = orgIdString.split("\\$");
    whereTmp = String.valueOf(whereTmp) + " and (po.viewScopeId like '%$" + userId + "$%' ";
    typeWhere = String.valueOf(typeWhere) + " and (tpo.viewScopeId like '%$" + userId + "$%' ";
    for (int i = 1; i < orgIds.length; i += 2) {
      whereTmp = String.valueOf(whereTmp) + " or po.viewScopeId like '%*" + orgIds[i] + "*%' ";
      typeWhere = String.valueOf(typeWhere) + " or tpo.viewScopeId like '%*" + orgIds[i] + "*%' ";
    } 
    String group = StaticParam.getGroupIdByEmpId(userId);
    String[] groups = group.split(",");
    for (int j = 1; j < groups.length; j++) {
      whereTmp = String.valueOf(whereTmp) + " or po.viewScopeId like '%@" + groups[j] + "@%' ";
      typeWhere = String.valueOf(typeWhere) + " or tpo.viewScopeId like '%@" + groups[j] + "@%' ";
    } 
    String opr = (new ManagerService()).getRightFinalWhere(userId, orgId, "09*09*02", "po.createOrgId", 
        "po.createEmpId");
    whereTmp = String.valueOf(whereTmp) + "  or (" + opr + ")) ";
    typeWhere = String.valueOf(typeWhere) + " ) ";
    String searchReportName = (request.getParameter("keyword") == null) ? "" : request.getParameter("keyword");
    if (!"".equals(searchReportName))
      whereTmp = String.valueOf(whereTmp) + " and po.reportName like '%" + searchReportName + "%' "; 
    String typeId = (request.getParameter("id") == null) ? "" : request.getParameter("id");
    if (!"".equals(typeId) && !"0".equals(typeId))
      whereTmp = String.valueOf(whereTmp) + " and tpo.typeId like '%" + typeId + "%' "; 
    String parentId = request.getParameter("parentId");
    if (parentId == null || parentId.isEmpty())
      parentId = "0"; 
    if ("class".equals(type))
      whereTmp = String.valueOf(whereTmp) + "and po.reportType=" + parentId; 
    if (typeWhere != null && !typeWhere.equals(""))
      where = String.valueOf(where) + typeWhere; 
    if (whereTmp != null && !whereTmp.equals(""))
      where = String.valueOf(where) + whereTmp; 
    String orderBy = " order by po.reportId";
    Page page = new Page(para, from, String.valueOf(where) + orderBy);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List<Object> list = page.getResultList();
    if (list != null)
      for (int k = 1; k < list.size(); k++) {
        Object selectList = list.get(k);
        Object[] temp = (Object[])selectList;
        if (temp[8] != null && !temp[8].toString().isEmpty())
          temp[7] = temp[8]; 
        Object pcUrl = temp[7];
        String url = pcUrl.toString();
        String userAccount = session.getAttribute("userAccount").toString();
        String userName = session.getAttribute("userName").toString();
        if (!url.isEmpty()) {
          url = url.replaceAll("\\@\\$\\@userId\\@\\$\\@", userId);
          url = url.replaceAll("\\@\\$\\@orgId\\@\\$\\@", orgId);
          url = url.replaceAll("\\@\\$\\@userAccount\\@\\$\\@", userAccount);
          url = url.replaceAll("\\@\\$\\@userName\\@\\$\\@", userName);
        } 
        temp[7] = url;
        selectList = temp;
        list.set(k, selectList);
      }  
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    String operation = (new ReportBD()).getOperation(opr);
    request.setAttribute("operation", operation);
    request.setAttribute("list", list);
    request.setAttribute("currentPage", Integer.valueOf(currentPage));
    request.setAttribute("size", recordCount);
    request.setAttribute("keyword", keyword);
    request.setAttribute("pageParameters", "action,searchReportName,id");
  }
  
  public void copyFile(File sourceFile, File targetFile) throws IOException {
    BufferedInputStream inBuff = null;
    BufferedOutputStream outBuff = null;
    try {
      inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
      outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
      byte[] b = new byte[5120];
      int len;
      while ((len = inBuff.read(b)) != -1)
        outBuff.write(b, 0, len); 
      outBuff.flush();
    } finally {
      if (inBuff != null)
        inBuff.close(); 
      if (outBuff != null)
        outBuff.close(); 
    } 
  }
  
  public List<?> reportType(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    String para = "select po.typeId,po.typeName,po.orderCode from com.js.oa.report.po.ReportTypePO po";
    String where = " where 1=1 ";
    String whereTmp = "";
    String[] orgIds = orgIdString.split("\\$");
    whereTmp = String.valueOf(whereTmp) + " and (po.viewScopeId like '%$" + userId + "$%' ";
    for (int i = 1; i < orgIds.length; i += 2)
      whereTmp = String.valueOf(whereTmp) + " or po.viewScopeId like '%*" + orgIds[i] + "*%' "; 
    String group = StaticParam.getGroupIdByEmpId(userId);
    String[] groups = group.split(",");
    for (int j = 1; j < groups.length; j++)
      whereTmp = String.valueOf(whereTmp) + " or po.viewScopeId like '%@" + groups[j] + "@%' "; 
    whereTmp = String.valueOf(whereTmp) + " ) order by po.orderCode";
    if (whereTmp != null && !whereTmp.equals(""))
      where = String.valueOf(where) + whereTmp; 
    return (new ReportBD()).getType(String.valueOf(para) + where);
  }
  
  private void getReportType(HttpServletRequest request) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    String parentId = request.getParameter("parentId");
    if (parentId == null || parentId.isEmpty())
      parentId = "0"; 
    String para = " po.typeId,po.typeName,po.typeDescribe,po.createEmp,po.viewScope,po.orderCode,po.parentId";
    String from = " com.js.oa.report.po.ReportTypePO po";
    String where = " where 1=1 ";
    String whereTmp = " and " + getViewSql(orgIdString, userId) + "and po.parentId=" + parentId;
    Page page = new Page(para, from, String.valueOf(where) + whereTmp);
    List<?> list = page.getResultList();
    request.setAttribute("parentId", parentId);
    request.setAttribute("classList", list);
  }
  
  public String getViewSql(String orgIdString, String userId) {
    String[] orgIds = orgIdString.split("\\$");
    String whereTmp = "(po.viewScopeId like '%$" + userId + "$%' ";
    for (int i = 1; i < orgIds.length; i += 2)
      whereTmp = String.valueOf(whereTmp) + " or po.viewScopeId like '%*" + orgIds[i] + "*%' "; 
    String group = StaticParam.getGroupIdByEmpId(userId);
    String[] groups = group.split(",");
    for (int j = 1; j < groups.length; j++)
      whereTmp = String.valueOf(whereTmp) + " or po.viewScopeId like '%@" + groups[j] + "@%' "; 
    whereTmp = String.valueOf(whereTmp) + " ) ";
    return whereTmp;
  }
}
