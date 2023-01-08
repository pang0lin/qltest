package com.js.oa.oacollect.action;

import com.js.oa.eform.service.CustomFormBD;
import com.js.oa.eform.service.FormBD;
import com.js.oa.form.Workflow;
import com.js.oa.jsflow.util.FormReflection;
import com.js.oa.oacollect.bean.OACollectEJBBean;
import com.js.util.config.SystemCommon;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CollectSkipAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String action = request.getParameter("action");
    String userId = request.getSession(true).getAttribute("userId").toString();
    OACollectEJBBean ejbBean = new OACollectEJBBean();
    String fromFlag = (request.getParameter("fromFlag") == null) ? ((request.getAttribute("fromFlag") == null) ? "" : request.getAttribute("fromFlag").toString()) : request.getParameter("fromFlag");
    request.setAttribute("fromFlag", fromFlag);
    String fromTabFlag = (request.getParameter("fromTabFlag") == null) ? ((request.getAttribute("fromTabFlag") == null) ? "" : request.getAttribute("fromTabFlag").toString()) : request.getParameter("fromTabFlag");
    request.setAttribute("fromTabFlag", fromTabFlag);
    if ("add".equals(action)) {
      request.setAttribute("fromFlagFlow", request.getParameter("fromFlagFlow"));
      request.setAttribute("wfWorkId", request.getParameter("wfWorkId"));
      request.setAttribute("collectId", request.getParameter("collectId"));
      String addFlag = request.getParameter("addFlag");
      if (addFlag != null && "batchAdd".equals(addFlag))
        return actionMapping.findForward("batchAdd"); 
      return actionMapping.findForward("add");
    } 
    if ("update".equals(action)) {
      if ("0".equals(request.getParameter("flag")))
        request.setAttribute("fromEFormFlag", "1"); 
      return actionMapping.findForward("update");
    } 
    String operate = request.getParameter("operate");
    String collectId = request.getParameter("collectId");
    String formId = request.getParameter("formId");
    String code = (new CustomFormBD()).getCode(request.getParameter("formId"));
    if (code != null && !code.toUpperCase().equals("NULL") && code.trim().length() > 0) {
      String infoId = "";
      if ("add".equals(operate)) {
        String pageId = request.getParameter("Page_Id");
        String formClassName = "WorkForm.class";
        String formClassMethod = "save";
        if (pageId != null && !"".equals(pageId)) {
          FormBD page = new FormBD();
          List<Object[]> forminfo = page.getSingleForm(pageId);
          if (forminfo != null && forminfo.size() > 0) {
            Object[] arrayOfObject = forminfo.get(0);
            formClassName = (arrayOfObject[10] == null || "".equals(arrayOfObject[10].toString())) ? formClassName : arrayOfObject[10].toString();
            formClassMethod = (arrayOfObject[11] == null || "".equals(arrayOfObject[11].toString())) ? formClassMethod : arrayOfObject[11].toString();
          } 
        } 
        FormReflection formReflection = new FormReflection();
        Object obj = formReflection.execute("com.js.oa.form." + formClassName.substring(0, formClassName.indexOf(".class")), formClassMethod, request);
        infoId = obj.toString();
        if (infoId.length() > 0) {
          String sql = "update oa_collect_emp set emp_status=1 where collect_id=" + collectId + " and emp_id=" + userId;
          ejbBean.updateBySql(sql);
          response.sendRedirect("/jsoa/oacollect/template/eform_page.jsp?close=1&stat=1&collectId=" + collectId + "&formId=" + formId + "&fromFlag=" + fromFlag + "&fromTabFlag=" + fromTabFlag);
        } else {
          response.sendRedirect("/jsoa/oacollect/template/eform_page.jsp?stat=0&collectId=" + collectId + "&formId=" + formId + "&fromFlag=" + fromFlag + "&fromTabFlag=" + fromTabFlag);
        } 
      } else if ("continue".equals(operate)) {
        String sql = "update oa_collect_emp set emp_status=1 where collect_id=" + collectId + " and emp_id=" + userId;
        ejbBean.updateBySql(sql);
        String pageId = request.getParameter("Page_Id");
        String formClassName = "WorkForm.class";
        String formClassMethod = "save";
        if (pageId != null && !"".equals(pageId)) {
          FormBD page = new FormBD();
          List<Object[]> forminfo = page.getSingleForm(pageId);
          if (forminfo != null && forminfo.size() > 0) {
            Object[] arrayOfObject = forminfo.get(0);
            formClassName = (arrayOfObject[10] == null || "".equals(arrayOfObject[10].toString())) ? formClassName : arrayOfObject[10].toString();
            formClassMethod = (arrayOfObject[11] == null || "".equals(arrayOfObject[11].toString())) ? formClassMethod : arrayOfObject[11].toString();
          } 
        } 
        FormReflection formReflection = new FormReflection();
        Object obj = formReflection.execute("com.js.oa.form." + formClassName.substring(0, formClassName.indexOf(".class")), formClassMethod, request);
        infoId = obj.toString();
        if (infoId.length() > 0) {
          response.sendRedirect("/jsoa/EFormPageAction.do?action=add&stat=1&collectId=" + collectId + "&formId=" + formId + "&fromFlag=" + fromFlag + "&fromTabFlag=" + fromTabFlag);
        } else {
          response.sendRedirect("/jsoa/EFormPageAction.do?action=add&stat=0&collectId=" + collectId + "&formId=" + formId + "&fromFlag=" + fromFlag + "&fromTabFlag=" + fromTabFlag);
        } 
      } else if ("modify".equals(operate)) {
        boolean reBoolean = false;
        String pageId = request.getParameter("Page_Id");
        String formClassName = "WorkForm";
        String formClassMethod = "update";
        if (pageId != null && !"".equals(pageId)) {
          FormBD page = new FormBD();
          List<Object[]> forminfo = page.getSingleForm(pageId);
          if (forminfo != null && forminfo.size() > 0) {
            Object[] arrayOfObject = forminfo.get(0);
            formClassName = (arrayOfObject[10] == null || "".equals(arrayOfObject[10].toString())) ? formClassName : arrayOfObject[10].toString();
            formClassMethod = (arrayOfObject[12] == null || "".equals(arrayOfObject[12].toString())) ? formClassMethod : arrayOfObject[12].toString();
          } 
        } 
        FormReflection formReflection = new FormReflection();
        Object obj = formReflection.execute("com.js.oa.form." + formClassName.substring(0, formClassName.indexOf(".class")), formClassMethod, request);
        reBoolean = ((Boolean)obj).booleanValue();
        if (reBoolean) {
          response.sendRedirect("/jsoa/oacollect/template/eform_page.jsp?close=1&stat=1&collectId=" + collectId + "&formId=" + formId + "&fromFlag=" + fromFlag + "&fromTabFlag=" + fromTabFlag);
        } else {
          response.sendRedirect("/jsoa/oacollect/template/eform_page.jsp?stat=0&recordId=" + request.getParameter("recordId") + "&collectId=" + collectId + "&formId=" + formId + "&fromFlag=" + fromFlag + "&fromTabFlag=" + fromTabFlag);
        } 
      } 
      if (infoId != null && !"".equals(infoId)) {
        String fromFlagFlow = request.getParameter("fromFlagFlow");
        if ("fromDaiBan".equals(fromFlagFlow) || "fromDuBan".equals(fromFlagFlow)) {
          collectId = request.getParameter("collectId");
          String wfWorkId = request.getParameter("wfWorkId");
          OACollectEJBBean collectBean = new OACollectEJBBean();
          String url = collectBean.getUrlForZaiBan(collectId, infoId);
          String dateString = "now()";
          String databaseType = SystemCommon.getDatabaseType();
          if (databaseType.indexOf("oracle") >= 0)
            dateString = "sysdate"; 
          String sql = "update JSF_WORK set WORKSTATUS=101,DEALWITHTIME=" + dateString + ",WORKMAINLINKFILE='" + url + "' where WF_WORK_ID=" + wfWorkId;
          try {
            collectBean.updateByYourYuanShengSql(sql);
          } catch (Exception e) {
            e.printStackTrace();
          } 
        } 
      } 
    } else if ("add".equals(operate)) {
      (new Workflow()).save(request);
      response.sendRedirect("/jsoa/oacollect/template/eform_page.jsp?close=1&stat=1&collectId=" + collectId + "&formId=" + formId + "&fromFlag=" + fromFlag + "&fromTabFlag=" + fromTabFlag);
    } else if ("continue".equals(operate)) {
      (new Workflow()).save(request);
      response.sendRedirect("/jsoa/EFormPageAction.do?action=add&stat=1&collectId=" + collectId + "&formId=" + formId + "&fromFlag=" + fromFlag + "&fromTabFlag=" + fromTabFlag);
    } else if ("modify".equals(operate)) {
      (new Workflow()).update(request);
      response.sendRedirect("/jsoa/oacollect/template/eform_page.jsp?close=1&stat=1&collectId=" + collectId + "&formId=" + formId + "&fromFlag=" + fromFlag + "&fromTabFlag=" + fromTabFlag);
    } 
    return actionMapping.findForward("save");
  }
}
