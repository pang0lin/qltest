package com.js.oa.eform.action;

import com.js.oa.eform.service.CustomFormBD;
import com.js.oa.eform.service.FormBD;
import com.js.oa.form.Workflow;
import com.js.oa.jsflow.util.FormReflection;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EFormPageAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String action = request.getParameter("action");
    String pageOffset = request.getParameter("pager.offset");
    String isRefFlow = (request.getParameter("isRefFlow") == null) ? "" : request.getParameter("isRefFlow");
    if ("add".equals(action)) {
      String addFlag = request.getParameter("addFlag");
      if (addFlag != null && "batchAdd".equals(addFlag))
        return actionMapping.findForward("batchAdd"); 
      return actionMapping.findForward("add");
    } 
    if ("update".equals(action)) {
      if ("0".equals(request.getParameter("flag")))
        request.setAttribute("fromEFormFlag", "1"); 
      if (!"-1".equals(isRefFlow) && !"".equals(isRefFlow)) {
        String tableid = request.getParameter("formId");
        String recordId = request.getParameter("recordId");
        String workid = request.getParameter("workid");
        String url = getprocessData(workid, recordId);
        if (!"".equals(url)) {
          response.sendRedirect(url);
          return null;
        } 
      } 
      return actionMapping.findForward("update");
    } 
    String operate = request.getParameter("operate");
    String menuId = request.getParameter("menuId");
    String formId = request.getParameter("formId");
    String code = (new CustomFormBD()).getCode(request.getParameter("formId"));
    String reUrl = "";
    if (code != null && !code.toUpperCase().equals("NULL") && code.trim().length() > 0) {
      String pageId = request.getParameter("Page_Id");
      String formClassName = "WorkForm.class";
      String formClassMethod = "save";
      if (pageId != null && !"".equals(pageId)) {
        FormBD page = new FormBD();
        List<Object[]> forminfo = page.getSingleForm(pageId);
        if (forminfo != null && forminfo.size() > 0) {
          Object[] arrayOfObject = forminfo.get(0);
          formClassName = (arrayOfObject[10] == null || "".equals(arrayOfObject[10].toString())) ? formClassName : arrayOfObject[10].toString();
          if ("modify".equals(operate)) {
            formClassMethod = (arrayOfObject[12] == null || "".equals(arrayOfObject[12].toString())) ? formClassMethod : arrayOfObject[12].toString();
          } else {
            formClassMethod = (arrayOfObject[11] == null || "".equals(arrayOfObject[11].toString())) ? formClassMethod : arrayOfObject[11].toString();
          } 
        } 
      } 
      FormReflection formReflection = new FormReflection();
      Object obj = formReflection.execute("com.js.oa.form." + formClassName.substring(0, formClassName.indexOf(".class")), formClassMethod, request);
      if ("add".equals(operate)) {
        String infoId = (obj == null) ? "" : obj.toString();
        if (infoId != null && infoId.length() > 0) {
          reUrl = "/jsoa/eform/eform_page.jsp?close=1&stat=1&menuId=" + menuId + "&formId=" + formId + "&pager.offset=" + pageOffset;
        } else {
          reUrl = "/jsoa/eform/eform_page.jsp?stat=0&menuId=" + menuId + "&formId=" + formId + "&pager.offset=" + pageOffset;
        } 
      } else if ("saveShow".equals(operate)) {
        String infoId = (obj == null) ? "" : obj.toString();
        if (infoId != null && infoId.length() > 0) {
          String recordId = request.getParameter("recordId");
          try {
            Map map = (Map)obj;
            recordId = (String)map.get("id");
          } catch (Exception e) {
            recordId = request.getParameter("recordId");
          } 
          reUrl = "/jsoa/eform/eform_page.jsp?close=1&stat=1&saveShow=1&menuId=" + menuId + "&formId=" + formId + "&recordId=" + recordId + "&pager.offset=" + pageOffset;
        } else {
          reUrl = "/jsoa/eform/eform_page.jsp?stat=0&menuId=" + menuId + "&formId=" + formId + "&pager.offset=" + pageOffset;
        } 
      } else if ("continue".equals(operate)) {
        String infoId = (obj == null) ? "" : obj.toString();
        String collectId = "";
        if (request.getParameter("collectId") != null)
          collectId = "&collectId=" + request.getParameter("collectId"); 
        if (infoId != null && infoId.length() > 0) {
          reUrl = "/jsoa/EFormPageAction.do?action=add&stat=1&menuId=" + menuId + "&formId=" + formId + collectId + "&pager.offset=" + pageOffset;
        } else {
          reUrl = "/jsoa/EFormPageAction.do?action=add&stat=0&menuId=" + menuId + "&formId=" + formId + collectId + "&pager.offset=" + pageOffset;
        } 
      } else if ("modify".equals(operate)) {
        boolean reBoolean = (obj == null) ? false : Boolean.valueOf((String)obj).booleanValue();
        if (reBoolean) {
          reUrl = "/jsoa/eform/eform_page.jsp?close=1&stat=1&menuId=" + menuId + "&formId=" + formId + "&pager.offset=" + pageOffset;
        } else {
          reUrl = "/jsoa/eform/eform_page.jsp?stat=0&recordId=" + request.getParameter("recordId") + "&menuId=" + menuId + "&formId=" + formId + "&pager.offset=" + pageOffset;
        } 
      } 
    } else if ("add".equals(operate)) {
      (new Workflow()).save(request);
      reUrl = "/jsoa/eform/eform_page.jsp?close=1&stat=1&menuId=" + menuId + "&formId=" + formId + "&pager.offset=" + pageOffset;
    } else if ("continue".equals(operate)) {
      (new Workflow()).save(request);
      reUrl = "/jsoa/EFormPageAction.do?action=add&stat=1&menuId=" + menuId + "&formId=" + formId + "&pager.offset=" + pageOffset;
    } else if ("modify".equals(operate)) {
      (new Workflow()).update(request);
      reUrl = "/jsoa/eform/eform_page.jsp?close=1&stat=1&menuId=" + menuId + "&formId=" + formId + "&pager.offset=" + pageOffset;
    } 
    if (!reUrl.equals("")) {
      if (request.getParameter("t") != null)
        reUrl = String.valueOf(reUrl) + "&t=" + request.getParameter("t"); 
      response.sendRedirect(reUrl);
    } 
    return actionMapping.findForward("save");
  }
  
  public String getprocessData(String workid, String recordId) {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String processStatus = "", curStatus = "", queryType = "1", url = "";
    String sql = "select work_hangup,workstatus,workprocess_id,workcurstep from jsf_work where wf_work_id=" + workid;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      if (rs.next()) {
        processStatus = rs.getString(1);
        curStatus = rs.getString(2);
        if ("0".equals(rs.getString(2))) {
          queryType = "1";
        } else {
          queryType = "100";
        } 
        if ("-101".equals(rs.getString(2)))
          curStatus = "-1"; 
        url = "/jsoa/jsflow/workflow_listInfo.jsp?record=" + recordId + "&processStatus=" + processStatus + "&workStatus=" + queryType + "&curStatus=" + curStatus + "&fromDossierData=y";
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (SQLException e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return url;
  }
}
