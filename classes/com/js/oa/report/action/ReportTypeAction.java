package com.js.oa.report.action;

import com.js.oa.message.service.MsManageBD;
import com.js.oa.oasysremind.bean.JsonData;
import com.js.oa.report.po.ReportTypePO;
import com.js.oa.report.service.ReportTypeBD;
import com.js.system.manager.service.ManagerService;
import com.js.system.util.StaticParam;
import com.js.util.page.Page;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONArray;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ReportTypeAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    String action = request.getParameter("action");
    ReportTypeActionForm form = (ReportTypeActionForm)actionForm;
    String orgId = session.getAttribute("orgId").toString();
    String userId = session.getAttribute("userId").toString();
    String corpId = session.getAttribute("corpId").toString();
    String userName = session.getAttribute("userName").toString();
    String domainId = session.getAttribute("domainId").toString();
    String tag = action;
    if ("list".equals(action)) {
      try {
        reportTypeList(request);
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } else if ("add".equals(action)) {
      request.setAttribute("typeSort", getTypeBySort(request));
      request.setAttribute("viewSql", getViewSql(session.getAttribute("orgIdString").toString(), userId));
      tag = "save";
    } else if ("save".equals(action) || "continue".equals(action)) {
      request.setAttribute("viewSql", "1=1");
      ReportTypeBD bd = new ReportTypeBD();
      ReportTypePO po = new ReportTypePO();
      po.setTypeName(form.getTypeName());
      po.setTypeDescribe(form.getTypeDescribe());
      po.setViewScope(form.getViewScope());
      po.setViewScopeId(form.getViewScopeId());
      po.setParentId(form.getParentId());
      po.setSortCode(form.getSortCode());
      po.setInsertSite(form.getInsertSite());
      po.setOprEmpId(form.getOprEmpId());
      po.setOprEmpName(form.getOprEmpName());
      po.setCorpId(corpId);
      po.setCreateEmp(userName);
      po.setCreateEmpId(Long.valueOf(userId));
      po.setCreateOrgId(Long.valueOf(orgId));
      po.setCreateDate(new Date());
      po.setDomainId(domainId);
      Long typeId = bd.saveReport(po, (String)form.getInsertSite());
      if ("save".equals(action)) {
        request.setAttribute("result", "close");
      } else {
        form.setViewScope("");
        form.setViewScopeId("");
        form.setTypeName("");
        form.setTypeDescribe("");
        request.setAttribute("result", "continue");
      } 
      tag = "save";
    } else if ("modi".equals(action)) {
      request.setAttribute("viewSql", getViewSql(session.getAttribute("orgIdString").toString(), userId));
      Long typeId = Long.valueOf(request.getParameter("typeId"));
      request.setAttribute("typeSort", getTypeBySort(request));
      ReportTypeBD bd = new ReportTypeBD();
      ReportTypePO po = bd.loadReportType(typeId);
      request.setAttribute("typeId", po.getTypeId());
      request.setAttribute("parentId", po.getParentId());
      form.setTypeName(po.getTypeName());
      form.setTypeDescribe(po.getTypeDescribe());
      form.setViewScope(po.getViewScope());
      form.setViewScopeId(po.getViewScopeId());
      form.setParentId(po.getParentId());
      form.setInsertSite(po.getInsertSite());
      form.setOprEmpId(po.getOprEmpId());
      form.setOprEmpName(po.getOprEmpName());
      Object[] obj = bd.searchByParentId(form.getParentId(), po.getSortCode(), po.getTypeId());
      if (obj != null && obj.length >= 2)
        if ("0".equals(obj[0].toString())) {
          form.setSortCode(Integer.valueOf(-1));
        } else if ("1".equals(obj[0].toString())) {
          form.setSortCode(Integer.valueOf(obj[1].toString()));
          form.setInsertSite(Integer.valueOf(0));
        } else if ("2".equals(obj[0].toString())) {
          form.setSortCode(Integer.valueOf(obj[1].toString()));
          form.setInsertSite(Integer.valueOf(1));
        } else if ("3".equals(obj[0].toString())) {
          form.setSortCode(Integer.valueOf(obj[1].toString()));
          form.setInsertSite(Integer.valueOf(0));
        }  
    } else if ("update".equals(action)) {
      request.setAttribute("viewSql", "1=1");
      Long typeId = Long.valueOf(request.getParameter("typeId"));
      ReportTypeBD bd = new ReportTypeBD();
      ReportTypePO po = bd.loadReportType(typeId);
      request.setAttribute("parentId", Integer.valueOf(0));
      po.setTypeDescribe(form.getTypeDescribe());
      po.setTypeName(form.getTypeName());
      po.setViewScope(form.getViewScope());
      po.setViewScopeId(form.getViewScopeId());
      po.setParentId(form.getParentId());
      po.setInsertSite(form.getInsertSite());
      po.setOprEmpId(form.getOprEmpId());
      po.setOprEmpName(form.getOprEmpName());
      bd.updateReportType(po, (String)form.getInsertSite());
      request.setAttribute("result", "close");
      request.setAttribute("typeId", typeId);
      tag = "modi";
    } else if ("delete".equals(action)) {
      String typeId = request.getParameter("typeId");
      ReportTypeBD bd = new ReportTypeBD();
      bd.deteleReportType(typeId);
    } else if ("getReportClass".equals(action)) {
      PrintWriter out = null;
      try {
        long parentId = Long.parseLong(request.getParameter("parentId"));
        String viewWhere = getViewSql(session.getAttribute("orgIdString").toString(), userId);
        String sql = "select po.sortCode,po.typeName from com.js.oa.report.po.ReportTypePO po where 1=1 and " + viewWhere + 
          " and po.parentId=" + parentId + " order by po.orderCode";
        response.setContentType("text/xml; charset=GBK");
        out = response.getWriter();
        JsonData jsonDate = null;
        List<JsonData> listJson = new ArrayList<JsonData>();
        if (sql != null && !"".equals(sql)) {
          MsManageBD msBD = new MsManageBD();
          List<Object[]> ttableList = msBD.getListByYourSQL(sql);
          if (ttableList != null && ttableList.size() != 0)
            for (int i = 0; i < ttableList.size(); i++) {
              jsonDate = new JsonData();
              Object[] obj = ttableList.get(i);
              jsonDate.setId(obj[0].toString());
              jsonDate.setName(obj[1].toString());
              if (obj.length > 2)
                jsonDate.setTableName(obj[2].toString()); 
              listJson.add(jsonDate);
            }  
          JSONArray jsonArray = JSONArray.fromObject(listJson);
          out.print(jsonArray.toString());
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        out.flush();
        out.close();
      } 
      return null;
    } 
    return actionMapping.findForward(tag);
  }
  
  public void reportTypeList(HttpServletRequest request) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String orgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    int offset_page = 0;
    if (request.getParameter("pager.offset") != null && !"".equals(request.getParameter("pager.offset")))
      offset_page = Integer.parseInt(request.getParameter("pager.offset")); 
    int pageSize = 15;
    int currentPage = offset_page / pageSize + 1;
    String para = " po.typeId,po.typeName,po.typeDescribe,po.createEmp,po.viewScope,po.orderCode ";
    String from = " com.js.oa.report.po.ReportTypePO po";
    String where = " where 1=1 ";
    String whereTmp = " and " + getViewSql(orgIdString, userId);
    String opr = (new ManagerService()).getRightFinalWhere(userId, orgId, "09*09*01", "po.createOrgId", 
        "po.createEmpId");
    whereTmp = String.valueOf(whereTmp) + " or (" + opr + ")";
    String searchTypeName = (request.getParameter("searchTypeName") == null) ? "" : request.getParameter("searchTypeName");
    if (!"".equals(searchTypeName))
      whereTmp = String.valueOf(whereTmp) + " and po.typeName like '%" + searchTypeName + "%' "; 
    if (whereTmp != null && !whereTmp.equals(""))
      where = String.valueOf(where) + whereTmp; 
    String orderBy = " order by po.orderCode";
    Page page = new Page(para, from, String.valueOf(where) + orderBy);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    String operation = (new ReportTypeBD()).getOpr(opr);
    request.setAttribute("operation", operation);
    request.setAttribute("list", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action,searchTypeName");
  }
  
  public List getTypeBySort(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    String orgId = session.getAttribute("orgId").toString();
    String searchTypeName = (request.getParameter("searchTypeName") == null) ? "" : request.getParameter("searchTypeName");
    String whereTmp = "";
    if (!"".equals(searchTypeName))
      whereTmp = String.valueOf(whereTmp) + " and po.typeName like '%" + searchTypeName + "%' "; 
    whereTmp = String.valueOf(whereTmp) + " or (" + (new ManagerService()).getRightFinalWhere(userId, orgId, "09*09*01", "po.createOrgId", 
        "po.createEmpId") + ")";
    return (new ReportTypeBD()).getQueryByHql(userId, orgIdString, whereTmp);
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
