package com.js.oa.report.action;

import com.js.oa.report.po.ReportPO;
import com.js.oa.report.po.ReportReplacePO;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ReportAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    String action = request.getParameter("action");
    ReportActionForm reportForm = (ReportActionForm)actionForm;
    String orgId = session.getAttribute("orgId").toString();
    String userId = session.getAttribute("userId").toString();
    String corpId = session.getAttribute("corpId").toString();
    String userName = session.getAttribute("userName").toString();
    String domainId = session.getAttribute("domainId").toString();
    String tag = action;
    if ("list".equals(action)) {
      try {
        reportList(request);
        String typeId = (request.getParameter("id") == null) ? "" : request.getParameter("id");
        if (!"".equals(typeId) && !"0".equals(typeId)) {
          String hql = "select po.typeId from com.js.oa.report.po.ReportTypePO po where po.typeId=" + typeId + 
            " and po.oprEmpId like '%$" + userId + "$%'";
          List list = (new ReportBD()).getType(hql);
          if (list.size() > 0) {
            request.setAttribute("opr", "y");
          } else {
            request.setAttribute("opr", "n");
          } 
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } else if ("add".equals(action)) {
      tag = "save";
      request.setAttribute("reportType", reportType(request));
      String id = (request.getParameter("id") == null) ? "0" : request.getParameter("id");
      reportForm.setReportType(id);
    } else if ("save".equals(action) || "continue".equals(action)) {
      ReportPO po = new ReportPO();
      String id = request.getParameter("id");
      po.setReportName(reportForm.getReportName());
      po.setReportDescribe(reportForm.getReportDescribe());
      po.setViewScope(reportForm.getViewScope());
      po.setViewScopeId(reportForm.getViewScopeId());
      po.setGrfName(reportForm.getGrfName());
      po.setReportSql(reportForm.getReportSql());
      po.setSourceBase(reportForm.getSourceBase());
      po.setReportClass(reportForm.getReportClass());
      po.setChartSql(reportForm.getChartSql());
      if ("0".equals(reportForm.getReportClass())) {
        String url = "/jsoa/gridreport/General/DisplayReport.jsp?data=data/xmlData.jsp&report=" + reportForm.getGrfName();
        po.setReportUrl(url);
      } else if ("1".equals(reportForm.getReportClass())) {
        String url = "/jsoa/gridreport/General/DisplayChart.jsp?data=data/xmlChart.jsp&report=" + reportForm.getGrfName();
        po.setReportUrl(url);
      } else {
        po.setReportUrl(reportForm.getReportUrl());
        po.setPhoneUrl(reportForm.getPhoneUrl());
      } 
      po.setReportType(id);
      po.setCorpId(corpId);
      po.setCreateEmp(userName);
      po.setCreateEmpId(Long.valueOf(userId));
      po.setCreateOrgId(Long.valueOf(orgId));
      po.setCreateDate(new Date());
      po.setDomainId(domainId);
      String[] beReplaceName = request.getParameterValues("beReplaceName");
      String[] beReplaceString = request.getParameterValues("beReplaceString");
      String[] replaceString = request.getParameterValues("replaceString");
      String[] sqlType = request.getParameterValues("sqlType");
      Set<ReportReplacePO> set = new HashSet<ReportReplacePO>();
      if (beReplaceName != null && beReplaceString != null && replaceString != null && sqlType != null)
        for (int i = 0; i < beReplaceName.length; i++) {
          ReportReplacePO replacePO = new ReportReplacePO();
          replacePO.setBeReplaceName(beReplaceName[i]);
          replacePO.setBeReplaceString(beReplaceString[i]);
          replacePO.setReplaceString(replaceString[i]);
          replacePO.setSqlType(sqlType[i]);
          set.add(replacePO);
        }  
      po.setReplaceSet(set);
      ReportBD bd = new ReportBD();
      long reportId = bd.saveReport(po).longValue();
      String grfPath = session.getServletContext().getRealPath("/gridreport/grf/");
      File file = new File(String.valueOf(grfPath) + "\\" + po.getGrfName());
      if (!file.exists()) {
        File sourceFile = new File(String.valueOf(grfPath) + "\\emptyGrf.grf");
        try {
          copyFile(sourceFile, file);
        } catch (Exception e) {
          e.printStackTrace();
        } 
      } 
      if ("save".equals(action)) {
        request.setAttribute("result", "close");
        request.setAttribute("reportType", new ArrayList());
      } else {
        reportForm.setViewScope("");
        reportForm.setViewScopeId("");
        reportForm.setReportName("");
        reportForm.setReportDescribe("");
        request.setAttribute("result", "continue");
        request.setAttribute("reportType", reportType(request));
      } 
      tag = "save";
    } else if ("modi".equals(action)) {
      Long reportId = Long.valueOf(request.getParameter("reportId"));
      ReportBD bd = new ReportBD();
      ReportPO po = bd.loadReport(reportId);
      request.setAttribute("reportId", po.getReportId());
      reportForm.setGrfName(po.getGrfName());
      reportForm.setReportName(po.getReportName());
      reportForm.setReportDescribe(po.getReportDescribe());
      reportForm.setViewScope(po.getViewScope());
      reportForm.setViewScopeId(po.getViewScopeId());
      reportForm.setReportSql(po.getReportSql());
      reportForm.setSourceBase(po.getSourceBase());
      reportForm.setReportType(po.getReportType());
      reportForm.setReportClass(po.getReportClass());
      reportForm.setChartSql(po.getChartSql());
      reportForm.setReportUrl(po.getReportUrl());
      reportForm.setPhoneUrl(po.getPhoneUrl());
      request.setAttribute("replaceSet", po.getReplaceSet());
      request.setAttribute("reportType", reportType(request));
    } else if ("update".equals(action)) {
      Long reportId = Long.valueOf(request.getParameter("reportId"));
      String id = request.getParameter("id");
      ReportBD bd = new ReportBD();
      ReportPO po = bd.loadReport(reportId);
      po.setReportDescribe(reportForm.getReportDescribe());
      po.setReportName(reportForm.getReportName());
      po.setViewScope(reportForm.getViewScope());
      po.setViewScopeId(reportForm.getViewScopeId());
      po.setReportSql(reportForm.getReportSql());
      po.setSourceBase(reportForm.getSourceBase());
      po.setReportClass(reportForm.getReportClass());
      po.setChartSql(reportForm.getChartSql());
      if ("0".equals(reportForm.getReportClass())) {
        String url = "/jsoa/gridreport/General/DisplayReport.jsp?data=data/xmlData.jsp&report=" + reportForm.getGrfName();
        po.setReportUrl(url);
      } else if ("1".equals(reportForm.getReportClass())) {
        String url = "/jsoa/gridreport/General/DisplayChart.jsp?data=data/xmlChart.jsp&report=" + reportForm.getGrfName();
        po.setReportUrl(url);
      } else {
        po.setReportUrl(reportForm.getReportUrl());
      } 
      po.setReportType(id);
      String[] beReplaceName = request.getParameterValues("beReplaceName");
      String[] beReplaceString = request.getParameterValues("beReplaceString");
      String[] replaceString = request.getParameterValues("replaceString");
      String[] sqlType = request.getParameterValues("sqlType");
      Set<ReportReplacePO> set = new HashSet<ReportReplacePO>();
      if (beReplaceName != null && beReplaceString != null && replaceString != null && sqlType != null)
        for (int i = 0; i < beReplaceName.length; i++) {
          ReportReplacePO replacePO = new ReportReplacePO();
          replacePO.setBeReplaceName(beReplaceName[i]);
          replacePO.setBeReplaceString(beReplaceString[i]);
          replacePO.setReplaceString(replaceString[i]);
          replacePO.setSqlType(sqlType[i]);
          set.add(replacePO);
        }  
      po.setReplaceSet(set);
      boolean flag = bd.updateReport(po);
      request.setAttribute("result", "close");
      request.setAttribute("reportId", reportId);
      request.setAttribute("reportType", new ArrayList());
      tag = "modi";
    } else if ("delete".equals(action)) {
      String[] ids = request.getParameter("reportId").split(",");
      ReportBD bd = new ReportBD();
      for (int i = 0; i < ids.length; i++) {
        if (!"".equals(ids[i])) {
          Long reportId = Long.valueOf(ids[i]);
          bd.deteleReport(reportId);
        } 
      } 
    } 
    return actionMapping.findForward(tag);
  }
  
  public void reportList(HttpServletRequest request) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    String orgId = session.getAttribute("orgId").toString();
    int offset_page = 0;
    if (request.getParameter("pager.offset") != null && !"".equals(request.getParameter("pager.offset")))
      offset_page = Integer.parseInt(request.getParameter("pager.offset")); 
    int pageSize = 15;
    int currentPage = offset_page / pageSize + 1;
    String para = " po.reportId,po.reportName,po.reportDescribe,po.grfName,po.createEmp,po.viewScope,po.reportClass,po.reportUrl";
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
    String opr = (new ManagerService()).getRightFinalWhere(userId, orgId, "09*09*02", "po.createOrgId", "po.createEmpId");
    whereTmp = String.valueOf(whereTmp) + "  or (" + opr + ")) ";
    typeWhere = String.valueOf(typeWhere) + " ) ";
    String searchReportName = (request.getParameter("searchReportName") == null) ? "" : request.getParameter("searchReportName");
    if (!"".equals(searchReportName))
      whereTmp = String.valueOf(whereTmp) + " and po.reportName like '%" + searchReportName + "%' "; 
    String typeId = (request.getParameter("id") == null) ? "" : request.getParameter("id");
    if (!"".equals(typeId) && !"0".equals(typeId))
      whereTmp = String.valueOf(whereTmp) + " and tpo.typeId like '%" + typeId + "%' "; 
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
        Object obj = temp[7];
        String url = obj.toString();
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
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
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
  
  public List reportType(HttpServletRequest request) {
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
}
