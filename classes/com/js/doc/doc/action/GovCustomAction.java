package com.js.doc.doc.action;

import com.js.doc.doc.po.GovCustomCheckedFieldPO;
import com.js.doc.doc.po.GovCustomFieldPO;
import com.js.doc.doc.po.GovCustomPO;
import com.js.doc.doc.service.CovCustomBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.BrowserJudge;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GovCustomAction extends Action {
  private String fontSizeHtmlPre = "<div style='text-align:left;font-size:<%=docFormFontSize%>;'>";
  
  private String fontSizeHtmlSuf = "</div>";
  
  private String fontsize = SystemCommon.getDocFormFontSize();
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    String action = httpServletRequest.getParameter("action");
    GovCustomActionForm govCustomActionForm = (GovCustomActionForm)actionForm;
    HttpSession session = httpServletRequest.getSession();
    Long domainId = new Long(session.getAttribute("domainId").toString());
    if ("list".equals(action)) {
      view(httpServletRequest);
    } else if ("continue".equals(action)) {
      action = "add";
      Long formId = setCustomFormPO(httpServletRequest);
      if (formId.intValue() < 0) {
        if (formId.intValue() == -2) {
          httpServletRequest.setAttribute("stat", "1");
        } else {
          httpServletRequest.setAttribute("stat", "0");
        } 
      } else {
        String govFormType = "0";
        if (httpServletRequest.getParameter("govFormType") != null && !httpServletRequest.getParameter("govFormType").toString().equals("") && !httpServletRequest.getParameter("govFormType").toString().equals("null"))
          govFormType = httpServletRequest.getParameter("govFormType"); 
        ActionForward forward = new ActionForward();
        forward.setPath("/GovCustomAction.do?action=add&govFormType=" + govFormType);
        forward.setRedirect(true);
        return forward;
      } 
    } 
    if ("add".equals(action)) {
      String govFormType = "0";
      if (httpServletRequest.getParameter("govFormType") != null && !httpServletRequest.getParameter("govFormType").toString().equals("") && !httpServletRequest.getParameter("govFormType").toString().equals("null"))
        govFormType = httpServletRequest.getParameter("govFormType"); 
      CovCustomBD bd = new CovCustomBD();
      List list = bd.loadFieldList(govFormType);
      httpServletRequest.setAttribute("fieldList", list);
      return actionMapping.findForward("add");
    } 
    if ("loadForm".equals(action)) {
      String gffType = (httpServletRequest.getParameter("gffType") != null) ? httpServletRequest.getParameter("gffType").toString() : "0";
      String formId = httpServletRequest.getParameter("formId");
      String id = httpServletRequest.getParameter("id");
      String govFormType = "0";
      if (httpServletRequest.getParameter("govFormType") != null && !httpServletRequest.getParameter("govFormType").toString().equals("") && !httpServletRequest.getParameter("govFormType").toString().equals("null"))
        govFormType = httpServletRequest.getParameter("govFormType"); 
      CovCustomBD bd = new CovCustomBD();
      GovCustomPO po = bd.loadGovCustomPO(id);
      if (gffType.equals("1")) {
        httpServletRequest.setAttribute("customContent", po.getGovPrintFormContent());
      } else {
        httpServletRequest.setAttribute("customContent", po.getGovFormContent());
      } 
      String name = po.getGovFormName();
      httpServletRequest.setAttribute("govFormName", name);
      List allFieldList = bd.loadFieldList(govFormType);
      httpServletRequest.setAttribute("fieldList", allFieldList);
      List checkFieldList = bd.loadCheckFieldList(formId, gffType);
      httpServletRequest.setAttribute("checkFieldList", checkFieldList);
      httpServletRequest.setAttribute("pager.offset", httpServletRequest.getParameter("pager.offset"));
      httpServletRequest.setAttribute("govFormName_old", httpServletRequest.getParameter("govFormName_old"));
      String mustword = "";
      String mustfield = "";
      if (!"".equals(po.getMustword()) && !"null".equals(po.getMustword()) && po.getMustword() != null) {
        mustword = po.getMustword().substring(po.getMustword().indexOf("$") + 1);
        mustfield = po.getMustword().substring(0, po.getMustword().indexOf("$"));
      } 
      httpServletRequest.setAttribute("mustfield", mustfield);
      httpServletRequest.setAttribute("mustword", mustword);
      return actionMapping.findForward("edit");
    } 
    if ("addquit".equals(action)) {
      Long formId = setCustomFormPO(httpServletRequest);
      if (formId.intValue() < 0) {
        if (formId.intValue() == -2) {
          httpServletRequest.setAttribute("stat", "1");
        } else {
          httpServletRequest.setAttribute("stat", "0");
        } 
      } else {
        httpServletRequest.setAttribute("close", "1");
      } 
      return actionMapping.findForward("add");
    } 
    if ("modify".equals(action)) {
      httpServletRequest.setAttribute("pager.offset", httpServletRequest.getParameter("pager.offset"));
      httpServletRequest.setAttribute("govFormName_old", httpServletRequest.getParameter("govFormName_old"));
      String result = setCustomFormPO_modi(httpServletRequest);
      if (result.equals("-1")) {
        httpServletRequest.setAttribute("stat", "0");
      } else if (result.equals("-2")) {
        httpServletRequest.setAttribute("stat", "1");
      } else {
        httpServletRequest.setAttribute("close", "1");
      } 
      return actionMapping.findForward("edit");
    } 
    if ("del".equals(action)) {
      CovCustomBD bd = new CovCustomBD();
      String id = httpServletRequest.getParameter("ids");
      String formId = httpServletRequest.getParameter("formId");
      bd.deleteGovCustomPO(id, formId);
      view(httpServletRequest);
    } 
    return actionMapping.findForward("list");
  }
  
  private void view(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = (httpSession.getAttribute("domainId") != null) ? httpSession.getAttribute("domainId").toString() : "0";
    ManagerService managerBD = new ManagerService();
    String scopeSQl = managerBD.getRightFinalWhere(httpSession.getAttribute("userId").toString(), httpSession.getAttribute("orgId").toString(), "03*06*01", "po.createOrg", "po.createEmp");
    String govFormType = "0";
    if (httpServletRequest.getParameter("govFormType") != null && !httpServletRequest.getParameter("govFormType").toString().equals("") && !httpServletRequest.getParameter("govFormType").toString().equals("null"))
      govFormType = httpServletRequest.getParameter("govFormType"); 
    String viewSql = " po.id,po.govFormName,po.govFormId,po.govFormType,po.govCheckField ";
    String fromSql = " com.js.doc.doc.po.GovCustomPO po";
    String whereSql = " where po.domainId=" + domainId + " and ( po.createEmp=" + httpSession.getAttribute("userId").toString() + " or (" + scopeSQl + ") or  po.createEmp is null )  ";
    whereSql = String.valueOf(whereSql) + " and po.govFormType=" + govFormType;
    if (httpServletRequest.getParameter("govFormName") != null && !httpServletRequest.getParameter("govFormName").toString().equals("null"))
      try {
        String govFormName = URLDecoder.decode(httpServletRequest.getParameter("govFormName"), "UTF-8");
        whereSql = String.valueOf(whereSql) + " and po.govFormName like '%" + govFormName + "%' ";
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }  
    whereSql = String.valueOf(whereSql) + " order by po.id desc ";
    list(httpServletRequest, viewSql, fromSql, whereSql);
  }
  
  private void list(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
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
      httpServletRequest.setAttribute("pager.realCurrent", (new StringBuilder(String.valueOf(currentPage))).toString());
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("mylist", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,govFormType,govFormName");
  }
  
  public Long setCustomFormPO(HttpServletRequest request) {
    HttpSession session = request.getSession();
    Long domainId = new Long(session.getAttribute("domainId").toString());
    GovCustomPO fPO = new GovCustomPO();
    CovCustomBD bd = new CovCustomBD();
    List fieldPoList = new ArrayList();
    List fieldPoList_modi = new ArrayList();
    List fieldPoList_add = new ArrayList();
    List fieldPoList_print = new ArrayList();
    List fieldControlList = new ArrayList();
    String realPath = request.getRealPath("");
    String code = (request.getParameter("code") != null) ? request.getParameter("code").toString() : "";
    String fieldelt = (request.getParameter("fieldelt") != null) ? request.getParameter("fieldelt").toString() : "";
    String govFormName = (request.getParameter("govFormName") != null) ? request.getParameter("govFormName").toString() : "";
    String formType = (request.getParameter("govFormType") != null) ? request.getParameter("govFormType").toString() : "0";
    String gffType = (request.getParameter("gffType") != null) ? request.getParameter("gffType").toString() : "0";
    String mustfield = (request.getParameter("mustfield") != null) ? request.getParameter("mustfield") : "";
    String mustword = (request.getParameter("mustword") != null) ? request.getParameter("mustword") : "";
    mustfield = String.valueOf(mustfield) + "$" + mustword;
    Long formId = new Long(-1L);
    String mb_fileName_add = "sendfile_add_temp.jsp";
    String mb_fileName_modi = "sendfile_modi_temp.jsp";
    String mb_fileName_print = "sendfile_print_temp.jsp";
    if (formType.equals("1")) {
      mb_fileName_add = "receivefile_add_temp.jsp";
      mb_fileName_modi = "receivefile_modi_temp.jsp";
      mb_fileName_print = "receivefile_print_temp.jsp";
    } else if (formType.equals("2")) {
      mb_fileName_add = "filesendcheckwithworkflow_add_temp.jsp";
      mb_fileName_modi = "filesendcheckwithworkflow_modi_temp.jsp";
      mb_fileName_print = "filesendcheckwithworkflow_print_temp.jsp";
    } 
    String displayValues = (request.getParameter("displayValues") != null) ? request.getParameter("displayValues").toString() : "";
    String[] displayValuesArr = displayValues.split(";");
    String addjsp = code;
    String modijsp = code;
    fPO.setDomainId(domainId);
    fPO.setGovFormContent(code);
    fPO.setGovPrintFormContent(code);
    fPO.setGovCheckField(fieldelt);
    fPO.setGovFormType(Integer.parseInt(formType));
    fPO.setGovFormName(govFormName);
    fPO.setCreateEmp(new Long(session.getAttribute("userId").toString()));
    fPO.setCreateOrg(new Long(session.getAttribute("orgId").toString()));
    fPO.setMustword(mustfield);
    fieldPoList = bd.loadFieldList(formType);
    fieldPoList_modi.addAll(fieldPoList);
    fieldPoList_add.addAll(fieldPoList);
    fieldPoList_print.addAll(fieldPoList);
    formId = bd.SaveGovCustomPO(fPO);
    if (formId.intValue() < 0)
      return formId; 
    String[] checkFieldArr = fieldelt.split(";");
    List<GovCustomCheckedFieldPO> checkFieldlist = new ArrayList();
    String tempCode_f = "";
    String tempCode_e = "";
    for (int i = 0; i < checkFieldArr.length; i++) {
      String fieldName = checkFieldArr[i].toString();
      String fieldDisplayName = getDisplayName(fieldName, displayValuesArr);
      GovCustomCheckedFieldPO po = new GovCustomCheckedFieldPO();
      po.setDomainId(domainId);
      po.setGffName(fieldName);
      po.setGffType(Integer.parseInt(gffType));
      po.setGovFormId(formId);
      po.setGovFormType(Integer.parseInt(formType));
      po.setGffDisplayName(fieldDisplayName);
      checkFieldlist.add(po);
      getControlList(fieldPoList, fieldName, fieldControlList, fieldDisplayName);
      String ff = "";
      int first = 0;
      int l = 0;
      if (BrowserJudge.isMSIEx(request)) {
        ff = "<div style=\"WIDTH: 99%\" id=\"";
        ff = String.valueOf(ff) + fieldName;
        first = addjsp.indexOf(ff);
        if (first < 0) {
          ff = "<div id=";
          ff = String.valueOf(ff) + fieldName;
          first = addjsp.indexOf(ff);
        } 
        l = addjsp.indexOf("</div>", first) + 6;
      } else {
        ff = "<div id=";
        ff = String.valueOf(ff) + "\"" + fieldName + "\"";
        first = addjsp.indexOf(ff);
        if (first < 0) {
          ff = "<div style=\"WIDTH: 99%\" id=\"";
          ff = String.valueOf(ff) + fieldName;
          first = addjsp.indexOf(ff);
        } 
        l = addjsp.indexOf("</div>", first) + 6;
      } 
      if (first != -1 && l != -1) {
        tempCode_f = addjsp.substring(0, first);
        tempCode_e = addjsp.substring(l);
        addjsp = String.valueOf(tempCode_f) + getRealYu(fieldPoList_add, fieldName, "0", formType) + tempCode_e;
      } 
      tempCode_f = null;
      tempCode_e = null;
      first = modijsp.indexOf(ff);
      l = modijsp.indexOf("</div>", first) + 6;
      if (first != -1 && l != -1) {
        tempCode_f = modijsp.substring(0, first);
        tempCode_e = modijsp.substring(l);
        modijsp = String.valueOf(tempCode_f) + getRealYu(fieldPoList_modi, fieldName, "1", formType) + tempCode_e;
      } 
      tempCode_f = null;
      tempCode_e = null;
      ff = null;
    } 
    bd.addWorkFlowControl(fieldControlList, (String)formId);
    bd.saveCheckFieldBatch(checkFieldlist, "0");
    bd.saveCheckFieldBatch(checkFieldlist, "1");
    if (formType.equals("0"))
      bd.saveBookMarks(fieldControlList, (String)domainId); 
    String filePath = request.getRealPath("");
    String addfileName = String.valueOf(filePath) + "/doc/doc/" + formId + "_" + formType + "_add.jsp";
    String modifileName = String.valueOf(filePath) + "/doc/doc/" + formId + "_" + formType + "_modi.jsp";
    String printfileName = String.valueOf(filePath) + "/doc/doc/" + formId + "_" + formType + "_print.jsp";
    String hiddenField = getHiddenField(fieldPoList_add);
    String setGovBookMarks = getSetBookMarks(fieldControlList);
    String govBookMarksHidden = getBookMarksHidden(fieldControlList);
    String govCheckLength = getCheckLengFunction(fieldControlList);
    String mb_add = getTempFile(realPath, mb_fileName_add);
    mb_add = mb_add.replaceFirst("<govHiddenField>", hiddenField);
    mb_add = mb_add.replaceFirst("<govCheckLength>", govCheckLength);
    int fb = mb_add.indexOf("<govContent>");
    addjsp = String.valueOf(mb_add.substring(0, fb)) + addjsp + mb_add.substring(fb + 12);
    String mb_print = getTempFile(realPath, mb_fileName_print);
    int fpb = mb_print.indexOf("<govContent>");
    String printjsp = String.valueOf(mb_print.substring(0, fpb)) + modijsp + mb_print.substring(fpb + 12);
    String mb_modi = getTempFile(realPath, mb_fileName_modi);
    mb_modi = mb_modi.replaceFirst("<govHiddenField>", hiddenField);
    if (formType.equals("0")) {
      mb_modi = mb_modi.replaceFirst("<setGovBookMarks>", setGovBookMarks);
      mb_modi = mb_modi.replaceFirst("<govBookMarksHidden>", govBookMarksHidden);
    } 
    mb_modi = mb_modi.replaceFirst("<govCheckLength>", govCheckLength);
    int fmb = mb_modi.indexOf("<govContent>");
    modijsp = String.valueOf(mb_modi.substring(0, fmb)) + modijsp + mb_modi.substring(fmb + 12);
    createFile(addjsp, addfileName);
    createFile(modijsp, modifileName);
    createFile(printjsp, printfileName);
    return formId;
  }
  
  public String setCustomFormPO_modi(HttpServletRequest request) {
    HttpSession session = request.getSession();
    Long domainId = new Long(session.getAttribute("domainId").toString());
    GovCustomPO fPO = new GovCustomPO();
    CovCustomBD bd = new CovCustomBD();
    List fieldPoList = new ArrayList();
    List fieldPoList_modi = new ArrayList();
    List fieldPoList_add = new ArrayList();
    List fieldPoList_print = new ArrayList();
    List fieldControlList = new ArrayList();
    String realPath = request.getRealPath("");
    String code = (request.getParameter("code") != null) ? request.getParameter("code").toString() : "";
    String fieldelt = (request.getParameter("fieldelt") != null) ? request.getParameter("fieldelt").toString() : "";
    String govFormName = (request.getParameter("govFormName") != null) ? request.getParameter("govFormName").toString() : "";
    String formType = (request.getParameter("govFormType") != null) ? request.getParameter("govFormType").toString() : "0";
    String gffType = (request.getParameter("gffType") != null) ? request.getParameter("gffType").toString() : "0";
    String mustfield = (request.getParameter("mustfield") != null) ? request.getParameter("mustfield") : "";
    String mustword = (request.getParameter("mustword") != null) ? request.getParameter("mustword") : "";
    mustfield = String.valueOf(mustfield) + "$" + mustword;
    Long formId = new Long(request.getParameter("formId"));
    String id = request.getParameter("id");
    String result = "";
    String mb_fileName_add = "sendfile_add_temp.jsp";
    String mb_fileName_modi = "sendfile_modi_temp.jsp";
    String mb_fileName_print = "sendfile_print_temp.jsp";
    if (formType.equals("1")) {
      mb_fileName_add = "receivefile_add_temp.jsp";
      mb_fileName_modi = "receivefile_modi_temp.jsp";
      mb_fileName_print = "receivefile_print_temp.jsp";
    } else if (formType.equals("2")) {
      mb_fileName_add = "filesendcheckwithworkflow_add_temp.jsp";
      mb_fileName_modi = "filesendcheckwithworkflow_modi_temp.jsp";
      mb_fileName_print = "filesendcheckwithworkflow_print_temp.jsp";
    } 
    String displayValues = (request.getParameter("displayValues") != null) ? request.getParameter("displayValues").toString() : "";
    String[] displayValuesArr = displayValues.split(";");
    String addjsp = code;
    String modijsp = code;
    String isUpdatePrint = (request.getParameter("isUpdatePrint") == null) ? "0" : request.getParameter("isUpdatePrint");
    fPO.setDomainId(domainId);
    if (gffType.equals("1")) {
      fPO.setGovPrintFormContent(code);
    } else {
      fPO.setGovFormContent(code);
      if (isUpdatePrint != null && "1".equals(isUpdatePrint))
        fPO.setGovPrintFormContent(code); 
    } 
    fPO.setGovCheckField(fieldelt);
    fPO.setGovFormType(Integer.parseInt(formType));
    fPO.setGovFormName(govFormName);
    fPO.setGovFormId(formId);
    fPO.setId(new Long(id));
    fPO.setMustword(mustfield);
    fieldPoList = bd.loadFieldList(formType);
    fieldPoList_modi.addAll(fieldPoList);
    fieldPoList_add.addAll(fieldPoList);
    fieldPoList_print.addAll(fieldPoList);
    result = bd.updateGovCustomPO(fPO, gffType);
    if ("1".equals(result) && "1".equals(isUpdatePrint))
      bd.updateGovCustomPO(fPO, "1"); 
    if (result.equals("-1") || result.equals("-2"))
      return result; 
    String[] checkFieldArr = fieldelt.split(";");
    List<GovCustomCheckedFieldPO> checkFieldlist = new ArrayList();
    String tempCode_f = "";
    String tempCode_e = "";
    for (int i = 0; i < checkFieldArr.length; i++) {
      String fieldName = checkFieldArr[i].toString();
      String fieldDisplayName = getDisplayName(fieldName, displayValuesArr);
      GovCustomCheckedFieldPO po = new GovCustomCheckedFieldPO();
      po.setDomainId(domainId);
      po.setGffName(fieldName);
      po.setGffType(Integer.parseInt(gffType));
      po.setGovFormId(formId);
      po.setGovFormType(Integer.parseInt(formType));
      po.setGffDisplayName(fieldDisplayName);
      checkFieldlist.add(po);
      getControlList(fieldPoList, fieldName, fieldControlList, fieldDisplayName);
      String ff = "";
      int first = 0;
      int l = 0;
      String browserVersion = request.getHeader("User-Agent");
      if (BrowserJudge.isMSIEx(request) && browserVersion.indexOf("MSIE 7.") < 0) {
        ff = "<div id=\"";
        ff = String.valueOf(ff) + fieldName;
        first = addjsp.indexOf(ff);
        if (first < 0) {
          ff = "<div style=\"WIDTH: 99%\" id=\"";
          ff = String.valueOf(ff) + fieldName;
          first = addjsp.indexOf(ff);
        } 
        l = addjsp.indexOf("</div>", first) + 6;
      } else if (browserVersion.indexOf("MSIE 7.0") > 0 && browserVersion.indexOf("InfoPath.3") < 0) {
        ff = "<div id=\"";
        ff = String.valueOf(ff) + fieldName;
        first = addjsp.indexOf(ff);
        if (first < 0) {
          ff = "<div style=\"WIDTH: 99%\" id=\"";
          ff = String.valueOf(ff) + fieldName;
          first = addjsp.indexOf(ff);
        } 
        l = addjsp.indexOf("</div>", first) + 6;
      } else {
        ff = "<div id=";
        ff = String.valueOf(ff) + "\"" + fieldName + "\"";
        first = addjsp.indexOf(ff);
        if (first < 0) {
          ff = "<div style=\"WIDTH: 99%\" id=\"";
          ff = String.valueOf(ff) + fieldName;
          first = addjsp.indexOf(ff);
        } 
        l = addjsp.indexOf("</div>", first) + 6;
      } 
      if (first != -1 && l != -1) {
        tempCode_f = addjsp.substring(0, first);
        tempCode_e = addjsp.substring(l);
        addjsp = String.valueOf(tempCode_f) + getRealYu(fieldPoList_add, fieldName, "0", formType) + tempCode_e;
      } 
      tempCode_f = null;
      tempCode_e = null;
      first = modijsp.indexOf(ff);
      l = modijsp.indexOf("</div>", first) + 6;
      if (first != -1 && l != -1) {
        tempCode_f = modijsp.substring(0, first);
        tempCode_e = modijsp.substring(l);
        modijsp = String.valueOf(tempCode_f) + getRealYu(fieldPoList_modi, fieldName, "1", formType) + tempCode_e;
      } 
      tempCode_f = null;
      tempCode_e = null;
      ff = null;
    } 
    if (gffType.equals("0")) {
      bd.updateWorkFlowControl(fieldControlList, (String)formId);
      bd.saveCheckFieldBatch(checkFieldlist, "0");
      if (isUpdatePrint != null && "1".equals(isUpdatePrint))
        bd.saveCheckFieldBatch(checkFieldlist, "1"); 
      if (formType.equals("0"))
        bd.saveBookMarks(fieldControlList, (String)domainId); 
    } else {
      bd.saveCheckFieldBatch(checkFieldlist, "1");
    } 
    String filePath = request.getRealPath("");
    String addfileName = String.valueOf(filePath) + "/doc/doc/" + formId + "_" + formType + "_add.jsp";
    String modifileName = String.valueOf(filePath) + "/doc/doc/" + formId + "_" + formType + "_modi.jsp";
    String printfileName = String.valueOf(filePath) + "/doc/doc/" + formId + "_" + formType + "_print.jsp";
    String hiddenField = getHiddenField(fieldPoList_add);
    String setGovBookMarks = getSetBookMarks(fieldControlList);
    String govBookMarksHidden = getBookMarksHidden(fieldControlList);
    String govCheckLength = getCheckLengFunction(fieldControlList);
    String mb_add = getTempFile(realPath, mb_fileName_add);
    mb_add = mb_add.replaceFirst("<govHiddenField>", hiddenField);
    mb_add = mb_add.replaceFirst("<govCheckLength>", govCheckLength);
    int fb = mb_add.indexOf("<govContent>");
    addjsp = String.valueOf(mb_add.substring(0, fb)) + addjsp + mb_add.substring(fb + 12);
    String mb_print = getTempFile(realPath, mb_fileName_print);
    int fpb = mb_print.indexOf("<govContent>");
    String printjsp = String.valueOf(mb_print.substring(0, fpb)) + modijsp + mb_print.substring(fpb + 12);
    String mb_modi = getTempFile(realPath, mb_fileName_modi);
    setGovBookMarks = String.valueOf(setGovBookMarks) + "document.all.\\$underwritePerson.value = \"[签发人]\"+underwritePerson; \r\n";
    govBookMarksHidden = String.valueOf(govBookMarksHidden) + "<input type=\"hidden\" name=\"\\$underwritePerson\" value=\"-1\"> \r\n";
    if (setGovBookMarks.indexOf("$signsendTime") < 0) {
      setGovBookMarks = String.valueOf(setGovBookMarks) + "document.all.\\$underwriteTime.value = \"[签发日期]\"+underwriteTime; \r\n";
      govBookMarksHidden = String.valueOf(govBookMarksHidden) + "<input type=\"hidden\" name=\"\\$underwriteTime\" value=\"-1\"> \r\n";
    } 
    setGovBookMarks = String.valueOf(setGovBookMarks) + "document.all.\\$documentSendFileSendFile.value = \"[签发]\"+underwriteCont; \r\n";
    govBookMarksHidden = String.valueOf(govBookMarksHidden) + " <input type=\"hidden\" name=\"\\$documentSendFileSendFile\" value=\"-1\"> \r\n";
    mb_modi = mb_modi.replaceFirst("<govHiddenField>", hiddenField);
    mb_modi = mb_modi.replaceFirst("<setGovBookMarks>", setGovBookMarks);
    mb_modi = mb_modi.replaceFirst("<govBookMarksHidden>", govBookMarksHidden);
    mb_modi = mb_modi.replaceFirst("<govCheckLength>", govCheckLength);
    int fmb = mb_modi.indexOf("<govContent>");
    modijsp = String.valueOf(mb_modi.substring(0, fmb)) + modijsp + mb_modi.substring(fmb + 12);
    if (gffType.equals("0")) {
      createFile(addjsp, addfileName);
      createFile(modijsp, modifileName);
    } else {
      createFile(printjsp, printfileName);
    } 
    return result;
  }
  
  public void createFile(String fileContent, String fileName) {
    FileWriter fw = null;
    try {
      fw = new FileWriter(fileName);
      long begin3 = System.currentTimeMillis();
      fw.write(fileContent);
      fw.close();
      long end3 = System.currentTimeMillis();
      fw.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public String getRealYu(List<GovCustomFieldPO> allList, String checkField) {
    String resultValue = "";
    for (int i = 0; i < allList.size(); i++) {
      GovCustomFieldPO po = allList.get(i);
      if (checkField.equals(po.getGffName())) {
        allList.remove(i);
        int type = po.getGffDisplayType();
        switch (type) {
          case 0:
            resultValue = getRealYu0_add(checkField);
            break;
          default:
            resultValue = getRealYu0_add(checkField);
            break;
          case 1:
          case 2:
          case 3:
            break;
        } 
      } 
    } 
    return resultValue;
  }
  
  private String getRealYu(List<GovCustomFieldPO> allList, String checkField, String dealType, String formType) {
    String resultValue = "";
    for (int i = 0; i < allList.size(); i++) {
      GovCustomFieldPO po = allList.get(i);
      if (checkField.equals(po.getGffName())) {
        allList.remove(i);
        int type = po.getGffDisplayType();
        switch (type) {
          case 0:
            if (dealType.equals("1")) {
              resultValue = getRealYu0_modi(checkField);
              break;
            } 
            resultValue = getRealYu0_add(checkField);
            break;
          case 1:
            if (dealType.equals("1")) {
              resultValue = getRealYu1_modi(checkField);
              break;
            } 
            resultValue = getRealYu1_add(checkField);
            break;
          case 2:
            if (dealType.equals("1")) {
              resultValue = getRealYu2_modi(checkField);
              break;
            } 
            resultValue = getRealYu2_add(checkField);
            break;
          case 3:
            if (dealType.equals("1")) {
              resultValue = getRealYu3_modi(checkField);
              break;
            } 
            resultValue = getRealYu3_add(checkField);
            break;
          case 4:
            if (dealType.equals("1")) {
              resultValue = getRealYu4_modi(checkField);
              break;
            } 
            resultValue = getRealYu4_add(checkField);
            break;
          case 5:
            if (dealType.equals("1")) {
              resultValue = getRealYu5_modi(checkField, formType);
              break;
            } 
            resultValue = getRealYu5_add(checkField, formType);
            break;
          case 6:
            if (dealType.equals("1")) {
              resultValue = getRealYu6_modi(checkField);
              break;
            } 
            resultValue = getRealYu6_add(checkField);
            break;
          case 7:
            if (dealType.equals("1")) {
              resultValue = getRealYu78_modi(checkField);
              break;
            } 
            resultValue = getRealYu78_add(checkField);
            break;
          case 8:
            if (dealType.equals("1")) {
              resultValue = getRealYu78_modi(checkField);
              break;
            } 
            resultValue = getRealYu78_add(checkField);
            break;
          case 9:
            if (dealType.equals("1")) {
              resultValue = getRealYu9_modi(checkField);
              break;
            } 
            resultValue = getRealYu9_add(checkField);
            break;
          case 10:
            if (dealType.equals("1")) {
              resultValue = getRealYu10_modi(checkField);
              break;
            } 
            resultValue = getRealYu10_add(checkField);
            break;
          case 11:
            if (dealType.equals("1")) {
              resultValue = getRealYu11_modi(checkField);
              break;
            } 
            resultValue = getRealYu11_add(checkField);
            break;
          case 12:
            if (dealType.equals("1")) {
              resultValue = getRealYu12_modi(checkField);
              break;
            } 
            resultValue = getRealYu12_add(checkField);
            break;
          case 13:
            if (dealType.equals("1")) {
              resultValue = getRealYu13_modi(checkField);
              break;
            } 
            resultValue = getRealYu13_add(checkField);
            break;
          default:
            resultValue = getRealYu0_add(checkField);
            break;
        } 
      } 
    } 
    if (!checkField.equals("receiveFileTitle") && !checkField.equals("documentSendFileTitle"))
      resultValue = "<%if(noDisplayFields.indexOf(\"$" + checkField + "$\")<0){ %>" + resultValue + "<%}%>"; 
    return resultValue;
  }
  
  public String getRealYu0_modi(String checkField) {
    String resultValue = " <%String  " + checkField + "_value=(request.getAttribute(\"" + checkField + 
      "\")==null || \"null\".equals(request.getAttribute(\"" + checkField + "\")))?\"\":request.getAttribute(\"" + 
      checkField + "\").toString();" + "\r\n" + " if(\"1\".equals(isEdit)){%>" + "\r\n" + "    <input type=\"text\" name=\"" + 
      checkField + "\" id=\"" + checkField + "\" class=\"sw_text\" style=\"width:98%\" value=\"<%=" + checkField + "_value%>\">" + "\r\n" + 
      "<% }else{" + "\r\n" + "     if(curModifyField.indexOf(\"$" + checkField + 
      "$\") < 0&& !\"1\".equals(request.getParameter(\"resubmit\"))){%>" + this.fontSizeHtmlPre + "<%=" + 
      checkField + "_value==null?\"\":" + checkField + "_value%>" + this.fontSizeHtmlSuf + "<input type=\"hidden\" id=\"" + checkField + "\" name=\"" + 
      checkField + "\" value=\"<%=" + checkField + "_value%>\"/>" + "\r\n" + " <%}else{%>" + "\r\n" + "     <input type=\"text\"  name=\"" + 
      checkField + "\" id=\"" + checkField + "\" class=\"sw_text\"  style=\"width:98%\" value=\"<%=" + checkField + "_value%>\">" + "\r\n" + " <%}}%>" + "\r\n";
    return resultValue;
  }
  
  public String getRealYu0_add(String checkField) {
    String resultValue = "";
    if ("sendFileDraft".equals(checkField)) {
      resultValue = "<%String  " + checkField + "_value=request.getAttribute(\"" + checkField + "\")==null?session.getAttribute(\"userName\").toString():request.getAttribute(\"" + checkField + "\").toString();" + "\r\n" + " if(" + checkField + "_value==null||" + checkField + "_value.equals(\"null\")||" + checkField + "_value.equals(\"\")){" + "\r\n" + checkField + "_value=request.getParameter(\"" + checkField + "\")==null?\"\":request.getParameter(\"" + checkField + "\").toString();" + "\r\n" + "}" + "\r\n" + "  if(noWriteField.indexOf(\"$" + checkField + "$\")<0){%>" + "\r\n" + "      <input type=\"text\" name=\"" + checkField + "\" id=\"" + checkField + "\" class=\"sw_text\" style=\"width:98%\" value=\"<%=" + checkField + "_value%>\">" + "\r\n" + "<% }else{%>" + "\r\n" + "      <%=" + checkField + "_value==null?\"\":" + checkField + "_value%>" + "\r\n" + "      <input type=\"hidden\" id=\"" + checkField + "\" name=\"" + checkField + "\" value=\"<%=" + checkField + "_value%>\"/>" + "\r\n" + "<%}%>" + "\r\n";
    } else if ("documentSendFileWriteOrg".equals(checkField)) {
      resultValue = "<%String org=session.getAttribute(\"orgName\").toString() ;\r\nString curUserId = session.getAttribute(\"userId\").toString();\r\nList<String[]> orgList = com.js.system.util.StaticParam.haveSidelineOrg(curUserId);\r\nString orgName=\"\";\r\nif(orgList==null){\r\nif(org.indexOf(\".\")>=0){\r\nString o[]=org.split(\"\\\\.\");\r\norgName=o[o.length-2]+\".\"+o[o.length-1];\r\n}else{orgName=org;}\r\nString  " + 








        
        checkField + "_value=request.getAttribute(\"" + checkField + "\")==null?orgName:request.getAttribute(\"" + checkField + "\").toString();" + 
        "\r\n" + " if(" + checkField + "_value==null||" + checkField + "_value.equals(\"null\")||" + checkField + "_value.equals(\"\")){" + "\r\n" + checkField + "_value=request.getParameter(\"" + checkField + "\")==null?\"\":request.getParameter(\"" + checkField + "\").toString();" + "\r\n" + "}" + "\r\n" + 
        checkField + "_value = com.js.system.util.StaticParam.getOrgByNum(" + checkField + "_value,com.js.util.config.SystemCommon.getInnerShow());\r\n" + 
        "if(noWriteField.indexOf(\"$" + checkField + "$\")<0){%>" + "\r\n" + " <input type=\"text\" name=\"" + checkField + "\" id=\"" + checkField + "\" class=\"sw_text\" style=\"width:98%\" value=\"<%=" + checkField + "_value%>\">" + "\r\n" + "<% }else{%>" + "\r\n" + "      <%=" + checkField + "_value==null?\"\":" + checkField + "_value%>" + "\r\n" + "      <input type=\"hidden\" id=\"" + checkField + "\" name=\"" + checkField + "\" value=\"<%=" + checkField + "_value%>\"/>" + "\r\n" + "<%}%>" + "\r\n" + 
        "<%}else{%>" + "\r\n" + 
        "<select style=\"font-size:1em;\" id=\"" + checkField + "\" name=\"" + checkField + "\">" + "\r\n" + 
        "<%for(int s = 0;s<orgList.size();s++){" + "\r\n" + 
        "String[] orgInfo = orgList.get(s);%>" + "\r\n" + 
        "<option value=\"<%=com.js.system.util.StaticParam.getOrgByNum(orgInfo[1])%>\" <%=(s==0? \"selected\":\"\")%>><%=com.js.system.util.StaticParam.getOrgByNum(orgInfo[1])%></option>" + "\r\n" + 
        "<%}%>" + "\r\n" + 
        "</select>" + "\r\n" + 
        "<%}%>";
    } else {
      resultValue = "<%String  " + checkField + "_value=request.getAttribute(\"" + checkField + "\")==null?\"\":request.getAttribute(\"" + checkField + "\").toString();" + "\r\n" + " if(" + checkField + "_value==null||" + checkField + "_value.equals(\"null\")||" + checkField + "_value.equals(\"\")){" + "\r\n" + checkField + "_value=request.getParameter(\"" + checkField + "\")==null?\"\":request.getParameter(\"" + checkField + "\").toString();" + "\r\n" + "}" + "\r\n" + "  if(noWriteField.indexOf(\"$" + checkField + "$\")<0){%>" + "\r\n" + "      <input type=\"text\" name=\"" + checkField + "\" id=\"" + checkField + "\" class=\"sw_text\" style=\"width:98%\" value=\"<%=" + checkField + "_value%>\">" + "\r\n" + "<% }else{%>" + "\r\n" + "      <%=" + checkField + "_value==null?\"\":" + checkField + "_value%>" + "\r\n" + "      <input type=\"hidden\" id=\"" + checkField + "\" name=\"" + checkField + "\" value=\"<%=" + checkField + "_value%>\"/>" + "\r\n" + "<%}%>" + "\r\n";
    } 
    return resultValue;
  }
  
  public String getRealYu1_add(String checkField) {
    String resultValue = "<%   java.util.Date " + checkField + " = new java.util.Date() ;" + "\r\n" + "\t if(request.getAttribute(\"" + checkField + "\")!=null)" + "\r\n" + 
      "\t " + checkField + " =  (java.util.Date)request.getAttribute(\"" + checkField + "\") ;" + "\r\n" + "\t int " + checkField + "_year = " + checkField + ".getYear()+1900 ;" + "\r\n" + 
      "\t int " + checkField + "_month = " + checkField + ".getMonth()+1 ;" + "\r\n" + 
      "\t int " + checkField + "_date = " + checkField + ".getDate();" + "\r\n" + "\t if(noWriteField.indexOf(\"$" + 
      checkField + "$\")!=-1){%>" + "\r\n" + "\t     <%=" + checkField + "_year%>&nbsp;年&nbsp;<%=" + checkField + 
      "_month%>&nbsp;月&nbsp;<%=" + checkField + "_date%>&nbsp;日" + "\r\n" + "        " + 
      "<input type=\"hidden\" id=\"" + checkField + "\" name=\"" + checkField + "\" value=\"<%=" + checkField + "_year+\"/\"+" + 
      checkField + "_month+\"/\"+" + checkField + "_date%>\"/>" + "\r\n" + "    <%}else{%>" + "\r\n" + "        <script language=javascript>" + "\r\n" + "          var dtpDate = createDatePicker(\"" + checkField + "\",'<%=" + checkField + "_year%>','<%=" + checkField + "_month%>','<%=" + checkField + "_date%>');" + "\r\n" + "        </script>" + "\r\n" + "<%}%>" + "\r\n";
    return resultValue;
  }
  
  public String getRealYu1_modi(String checkField) {
    String resultValue = "";
    if (checkField.equals("documentSendFileCheckDate")) {
      resultValue = "<%if(curModifyField.indexOf(\"$documentSendFileCheckDate$\") < 0 && !\"1\".equals(request.getParameter(\"resubmit\"))){%>\r\n\t<%String dddd=myform.getDocumentSendFileCheckDate()==null?\"&nbsp;\":com.js.util.util.CharacterTool.escapeHTMLTagsSimple(myform.getDocumentSendFileCheckDate());%>\r\n<%if(dddd!=null&&!dddd.equals(\"null\")){String dddArr[]=dddd.split(\"/\");if(dddArr!=null&&dddArr.length>0){dddd=dddArr[0]+\"年\"+dddArr[1]+\"月\"+dddArr[2]+\"日\";}}%>" + this.fontSizeHtmlPre + "<%=dddd%>" + this.fontSizeHtmlSuf + "<html:hidden property=\"documentSendFileCheckDate\"/>\r\n<%}else{\r\n\tString tmp = myform.getDocumentSendFileCheckDate();\r\n\tjava.util.Calendar nowTime = java.util.Calendar.getInstance();\r\n\tString[] tmpArray = {nowTime.get(java.util.Calendar.YEAR)+\"\", (nowTime.get(java.util.Calendar.MONTH)+1)+\"\", nowTime.get(java.util.Calendar.DATE)+\"\"};\r\n\tif(tmp != null && !tmp.equals(\"\") && tmp.indexOf(\"/\") >= 0){\r\n\t   tmpArray = tmp.split(\"/\");}%>\r\n\t<script language=\"javascript\">\r\n\tvar dtpDate = createDatePicker(\"documentSendFileCheckDate\", <%=tmpArray[0]%>, <%=tmpArray[1]%>, <%=tmpArray[2]%>);\r\n\t</script>\r\n<%}%>&nbsp;\r\n";
      return resultValue;
    } 
    resultValue = "<%   java.util.Date " + checkField + " = new java.util.Date() ;" + "\r\n" + "\t if(request.getAttribute(\"" + checkField + "\")!=null)" + "\r\n" + "\t " + checkField + " =  (java.util.Date)request.getAttribute(\"" + checkField + "\") ;" + "\r\n" + "\t int " + checkField + "_year = " + checkField + ".getYear()+1900 ;" + "\r\n" + "\t int " + checkField + "_month = " + checkField + ".getMonth()+1 ;" + "\r\n" + "\t int " + checkField + "_date = " + checkField + ".getDate();" + "\r\n" + "\t if(curModifyField.indexOf(\"$" + checkField + "$\") < 0 && !\"1\".equals(request.getParameter(\"resubmit\"))){%>" + this.fontSizeHtmlPre + "<%=" + checkField + "_year%>&nbsp;年&nbsp;<%=" + checkField + "_month%>&nbsp;月&nbsp;<%=" + checkField + "_date%>&nbsp;日" + this.fontSizeHtmlSuf + "\r\n" + "        <input type=\"hidden\" id=\"" + checkField + "\" name=\"" + checkField + "\" value=\"<%=" + checkField + "_year+\"/\"+" + checkField + "_month+\"/\"+" + checkField + "_date%>\"/>" + "\r\n" + "    <%}else{%>" + "\r\n" + "        <script language=javascript>" + "\r\n" + "          var dtpDate = createDatePicker(\"" + checkField + "\",'<%=" + checkField + "_year%>','<%=" + checkField + "_month%>','<%=" + checkField + "_date%>');" + "\r\n" + "        </script>" + "\r\n" + "<%}%>" + "\r\n";
    return resultValue;
  }
  
  public String getRealYu2_modi(String checkField) {
    String resultValue = "<% String " + checkField + "_value=(request.getAttribute(\"" + checkField + "\")==null || \"null\".equals(request.getAttribute(\"" + checkField + "\")))?\"\":request.getAttribute(\"" + checkField + "\").toString();" + "\r\n" + "\tif(\"1\".equals(isEdit)||curModifyField.indexOf(\"$" + checkField + "$\")>= 0||\"1\".equals(request.getParameter(\"resubmit\"))){%>" + "\r\n" + "    <html:select property=\"" + checkField + "\" styleId=\"" + checkField + "\">" + "\r\n" + "      <html:option value=\"\"/>" + "\r\n" + "         <%if(" + checkField + "_Arr!=null&&" + checkField + "_Arr.length>0){" + "\r\n" + "              for(int i=0;i<" + checkField + "_Arr.length;i++){" + "\r\n" + "              String " + checkField + "_ArrObj=" + checkField + "_Arr[i].toString(); %>" + "\r\n" + "            <html:option value=\"<%=" + checkField + "_ArrObj%>\"><%=" + checkField + "_ArrObj%></html:option>" + "\r\n" + "     \t<%}}%>" + "\r\n" + "\t</html:select>" + "\r\n" + "<%}else{%>" + this.fontSizeHtmlPre + "<%=" + checkField + "_value==null?\"\":" + checkField + "_value%>" + this.fontSizeHtmlSuf + "<html:hidden property=\"" + checkField + "\"/>" + "\r\n" + "<%}%>" + "\r\n";
    return resultValue;
  }
  
  public String getRealYu3_add(String checkField) {
    String resultValue = "";
    resultValue = "  <%\r\n   String  " + checkField + "_value=request.getAttribute(\"" + checkField + "\")==null?\"\":request.getAttribute(\"" + 
      checkField + "\").toString();" + "\r\n" + "  %>" + "\r\n" + 
      "<%if(noWriteField.indexOf(\"$" + checkField + "$\")!=-1){%>" + "\r\n" + 
      "\t<input type=\"hidden\" id=\"" + checkField + "\"  name=\"" + checkField + "\"  value=\"<%=" + checkField + "_value%>\">" + "\r\n" + "<%}else{%>" + "\r\n" + 
      "    <input type=\"text\" class=\"sw_text\" name=\"" + checkField + "\" id=\"" + checkField + "\" value=\"<%=" + checkField + "_value%>\">" + 
      "<input type=\"button\" class=\"btnButton2Font\" onClick=\"javascript:choosePerson('mainToPerson','" + checkField + "','" + checkField + 
      "Id','选择单位');\" value=\"选择\" />" + "\r\n" + "<%}%>" + "\r\n";
    return resultValue;
  }
  
  public String getRealYu3_modi(String checkField) {
    String resultValue = "";
    resultValue = "  <%\r\n   String  " + checkField + "_value=(request.getAttribute(\"" + checkField + "\")==null || \"null\".equals(request.getAttribute(\"" + checkField + "\")))?\"\":request.getAttribute(\"" + 
      checkField + "\").toString();" + "\r\n" + "  %>" + "\r\n" + "<%if(curModifyField.indexOf(\"$" + checkField + "$\") < 0 && !\"1\".equals(request.getParameter(\"resubmit\"))&&!\"1\".equals(isEdit)){%>" + 
      this.fontSizeHtmlPre + "<%=" + checkField + "_value.equals(\"\")?\"&nbsp;\":" + checkField + "_value%>" + this.fontSizeHtmlSuf + "<input type=\"hidden\"  name=\"" + 
      checkField + "\" id=\"" + checkField + "\" value=\"<%=" + checkField + "_value%>\">" + "\r\n" + "<%}else{%>" + "\r\n" + "    <input type=\"text\" class=\"sw_text\" id=\"" + checkField + "\" name=\"" + 
      checkField + "\" value=\"<%=" + checkField + "_value%>\" style=\"width:98%\">" + 
      "<input type=\"button\" style=\"display: none;\" class=\"btnButton2Font\" onClick=\"javascript:choosePerson('mainToPerson','" + checkField + "','" + checkField + 
      "Id','选择单位');\" value=\"选择\" />" + "\r\n" + "<%}%>" + "\r\n";
    return resultValue;
  }
  
  public String getRealYu2_add(String checkField) {
    String resultValue = "<% String " + checkField + "_value=request.getAttribute(\"" + checkField + "\")==null?\"\":request.getAttribute(\"" + checkField + "\").toString();" + "\r\n" + "\tif(noWriteField.indexOf(\"$" + checkField + "$\")<0){%>" + "\r\n" + "    <html:select property=\"" + checkField + "\" styleId=\"" + checkField + "\">" + "\r\n" + "      <html:option value=\"\"/>" + "\r\n" + "         <%if(" + checkField + "_Arr!=null&&" + checkField + "_Arr.length>0){" + "\r\n" + "              for(int i=0;i<" + checkField + "_Arr.length;i++){" + "\r\n" + "              String " + checkField + "_ArrObj=" + checkField + "_Arr[i].toString(); %>" + "\r\n" + "            <html:option value=\"<%=" + checkField + "_ArrObj%>\"><%=" + checkField + "_ArrObj%></html:option>" + "\r\n" + "     \t<%}}%>" + "\r\n" + "\t</html:select>" + "\r\n" + "<%}else{%>" + "\r\n" + "       <%=" + checkField + "_value==null?\"\":" + checkField + "_value%>" + "\r\n" + "       <html:hidden property=\"" + checkField + "\"/>" + "\r\n" + "<%}%>" + "\r\n";
    return resultValue;
  }
  
  public String getRealYu4_modi(String checkField) {
    String resultValue = "<%  String  " + checkField + "_value=(request.getAttribute(\"" + checkField + "\")==null || \"null\".equals(request.getAttribute(\"" + 
      checkField + "\")))?\"\":request.getAttribute(\"" + checkField + "\").toString();" + "\r\n" + "\tif(curModifyField.indexOf(\"$" + 
      checkField + "$\") < 0 && !\"1\".equals(request.getParameter(\"resubmit\"))&&!\"1\".equals(isEdit)){%>" + this.fontSizeHtmlPre + "<%=" + 
      checkField + "_value.equals(\"\")?\"&nbsp;\":" + checkField + "_value%>" + this.fontSizeHtmlSuf + "<input type=\"hidden\" name=\"" + 
      checkField + "\"  id=\"" + checkField + "\"  value=\"<%=" + checkField + "_value%>\">" + "\r\n" + "<%}else{%>" + "\r\n" + "\t\t<input type=\"text\" id=\"" + checkField + "\" name=\"" + 
      checkField + "\" value=\"<%=" + checkField + "_value%>\"  class=\"sw_text\" style=\"width:90%\" >" + 
      "<input type=\"button\" class=\"btnButton2Font\" onClick=\"openEndowSend('" + checkField + "');\" value=\"选择\" />" + 
      "\r\n" + "<%}%>" + "\r\n";
    return resultValue;
  }
  
  public String getRealYu4_add(String checkField) {
    String resultValue = "<%  String  " + checkField + "_value=(request.getAttribute(\"" + checkField + "\")==null||\"null\".equals(request.getAttribute(\"" + checkField + "\")))?\"\":request.getAttribute(\"" + 
      checkField + "\").toString();" + "\r\n" + "\tif(noWriteField.indexOf(\"$" + checkField + "$\") >=0 ){%>" + "\r\n" + "\t\t<%=" + 
      checkField + "_value.equals(\"\")?\"&nbsp;\":" + checkField + "_value%>" + "\r\n" + "\t\t<input type=\"hidden\" id=\"" + 
      checkField + "\" name=\"" + checkField + "\"  value=\"<%=" + checkField + "_value%>\">" + "\r\n" + "<%}else{%>" + "\r\n" + "\t\t<input type=\"text\" id=\"" + checkField + "\" name=\"" + 
      checkField + "\" value=\"<%=" + checkField + "_value%>\"  class=\"sw_text\"    style=\"width:90%\" >" + 
      "<input type=\"button\" class=\"btnButton2Font\" onClick=\"openEndowSend('" + checkField + "');\" value=\"选择\" />" + 
      "\r\n" + "<%}%>" + "\r\n";
    return resultValue;
  }
  
  public String getRealYu5_add(String checkField, String formType) {
    String resultValue, variable_name = "";
    String variable_saveName = "";
    String variable_tablename = "";
    String variable_display_fileName = "";
    String variable_display_saveName = "";
    String scanInfo = "";
    if ("accessoryName1".equals(checkField)) {
      variable_name = "accessoryName1";
      variable_saveName = "accessorySaveName1";
      variable_tablename = "govdocumenttable1";
      variable_display_fileName = "accessoryName1";
      variable_display_saveName = "accessorySaveName1";
    } 
    if ("accessoryName2".equals(checkField)) {
      variable_name = "accessoryName2";
      variable_saveName = "accessorySaveName2";
      variable_tablename = "govdocumenttable2";
      variable_display_fileName = "accessoryName2";
      variable_display_saveName = "accessorySaveName2";
    } 
    if ("accessoryName".equals(checkField)) {
      variable_name = "accessoryName";
      variable_saveName = "accessorySaveName";
      variable_tablename = "govdocumenttable";
      variable_display_fileName = "accessoryName";
      variable_display_saveName = "accessorySaveName";
    } 
    if ("saveName".equals(checkField)) {
      variable_name = "accessoryName";
      variable_saveName = "accessorySaveName";
      variable_tablename = "govdocumenttablework";
      variable_display_fileName = "saveName";
      variable_display_saveName = "displayName";
    } 
    if ("1".equals(formType)) {
      resultValue = "<%canModify = true;\r\nif(noWriteField.indexOf(\"$" + checkField + "$\")>=0){" + "\r\n" + " canModify = false;" + "\r\n" + " }" + "\r\n" + "accName= " + variable_name + " ;" + "\r\n" + "accSName =" + variable_saveName + ";" + "\r\n" + "if(null!=accName&&!\"\".equals(accName) &&null!=accSName&&!\"\".equals(accSName)) {" + "\r\n" + "    realFileArray = (accName+\"\").split(\"\\\\|\");" + "\r\n" + "\t   saveFileArray = (accSName+\"\").split(\"\\\\|\") ;" + "\r\n" + "}else{" + "\r\n" + "    realFileArray = new String[0] ;" + "\r\n" + "    saveFileArray = new String[0] ;" + "\r\n" + "}" + "\r\n" + "tableName=\"" + variable_tablename + "\";" + "\r\n" + "fileName = \"" + variable_display_fileName + "\" ;" + "\r\n" + "saveName = \"" + variable_display_saveName + "\";" + "\r\n" + "%>" + "\r\n" + "<table width=\"100%\">" + "\r\n" + "<tr>" + "\r\n" + "<td width=\"90%\">" + "\r\n" + "  <%@ include file = \"/public/jsp/modifyupload.jsp\" %>" + "\r\n" + "</td>" + "\r\n" + scanInfo + "</tr>" + "\r\n" + "</table>" + "\r\n";
    } else if ("1".equals(SystemCommon.getSendfileAccessoryEdit())) {
      resultValue = "<%canModify = true;\r\nif(noWriteField.indexOf(\"$" + checkField + "$\")>=0){" + "\r\n" + " canModify = false;" + "\r\n" + " }" + "\r\n" + "accName= " + variable_name + " ;" + "\r\n" + "accSName =" + variable_saveName + ";" + "\r\n" + "if(null!=accName&&!\"\".equals(accName) &&null!=accSName&&!\"\".equals(accSName)) {" + "\r\n" + "    realFileArray = (accName+\"\").split(\"\\\\|\");" + "\r\n" + "\t   saveFileArray = (accSName+\"\").split(\"\\\\|\") ;" + "\r\n" + "}else{" + "\r\n" + "    realFileArray = new String[0] ;" + "\r\n" + "    saveFileArray = new String[0] ;" + "\r\n" + "}" + "\r\n" + "tableName=\"" + variable_tablename + "\";" + "\r\n" + "fileName = \"" + variable_display_fileName + "\" ;" + "\r\n" + "saveName = \"" + variable_display_saveName + "\";" + "\r\n" + "%>" + "\r\n" + "<table width=\"100%\">" + "\r\n" + "<tr>" + "\r\n" + "<td width=\"90%\">" + "\r\n" + "  <%@ include file = \"/public/jsp/doc_modifyupload.jsp\" %>" + "\r\n" + "</td>" + "\r\n" + scanInfo + "</tr>" + "\r\n" + "</table>" + "\r\n";
    } else {
      resultValue = "<%canModify = true;\r\nif(noWriteField.indexOf(\"$" + checkField + "$\")>=0){" + "\r\n" + " canModify = false;" + "\r\n" + " }" + "\r\n" + "accName= " + variable_name + " ;" + "\r\n" + "accSName =" + variable_saveName + ";" + "\r\n" + "if(null!=accName&&!\"\".equals(accName) &&null!=accSName&&!\"\".equals(accSName)) {" + "\r\n" + "    realFileArray = (accName+\"\").split(\"\\\\|\");" + "\r\n" + "\t   saveFileArray = (accSName+\"\").split(\"\\\\|\") ;" + "\r\n" + "}else{" + "\r\n" + "    realFileArray = new String[0] ;" + "\r\n" + "    saveFileArray = new String[0] ;" + "\r\n" + "}" + "\r\n" + "tableName=\"" + variable_tablename + "\";" + "\r\n" + "fileName = \"" + variable_display_fileName + "\" ;" + "\r\n" + "saveName = \"" + variable_display_saveName + "\";" + "\r\n" + "%>" + "\r\n" + "<table width=\"100%\">" + "\r\n" + "<tr>" + "\r\n" + "<td width=\"90%\">" + "\r\n" + "  <%@ include file = \"/public/jsp/modifyupload.jsp\" %>" + "\r\n" + "</td>" + "\r\n" + scanInfo + "</tr>" + "\r\n" + "</table>" + "\r\n";
    } 
    return resultValue;
  }
  
  public String getRealYu5_modi(String checkField, String formType) {
    String resultValue, variable_name = "";
    String variable_saveName = "";
    String variable_tablename = "";
    String variable_display_fileName = "";
    String variable_display_saveName = "";
    if ("accessoryName1".equals(checkField)) {
      variable_name = "accessoryName1";
      variable_saveName = "accessorySaveName1";
      variable_tablename = "govdocumenttable1";
      variable_display_fileName = "accessoryName1";
      variable_display_saveName = "accessorySaveName1";
    } 
    if ("accessoryName2".equals(checkField)) {
      variable_name = "accessoryName2";
      variable_saveName = "accessorySaveName2";
      variable_tablename = "govdocumenttable2";
      variable_display_fileName = "accessoryName2";
      variable_display_saveName = "accessorySaveName2";
    } 
    if ("accessoryName".equals(checkField)) {
      variable_name = "accessoryName";
      variable_saveName = "accessorySaveName";
      variable_tablename = "govdocumenttable";
      variable_display_fileName = "accessoryName";
      variable_display_saveName = "accessorySaveName";
    } 
    if ("saveName".equals(checkField)) {
      variable_name = "accessoryName";
      variable_saveName = "accessorySaveName";
      variable_tablename = "govdocumenttablework";
      variable_display_fileName = "saveName";
      variable_display_saveName = "displayName";
    } 
    if ("1".equals(formType)) {
      resultValue = " <%\r\n canModify = true;\r\nif (curModifyField.indexOf(\"$" + checkField + "$\") < 0) {" + "\r\n" + "       canModify = false;" + "\r\n" + " }" + "\r\n" + " if (\"1\".equals(isEdit)) {" + "\r\n" + "     canModify = true;" + "\r\n" + " }%>" + "\r\n" + "<%" + "\r\n" + "accName= " + variable_name + " ;" + "\r\n" + "accSName =" + variable_saveName + ";" + "\r\n" + "if(null!=accName&&!\"\".equals(accName) &&null!=accSName&&!\"\".equals(accSName)) {" + "\r\n" + "    realFileArray = (accName+\"\").split(\"\\\\|\");" + "\r\n" + "\t   saveFileArray = (accSName+\"\").split(\"\\\\|\") ;" + "\r\n" + "}else{" + "\r\n" + "    realFileArray = new String[0] ;" + "\r\n" + "    saveFileArray = new String[0] ;" + "\r\n" + "}" + "\r\n" + "tableName=\"" + variable_tablename + "\";" + "\r\n" + "fileName = \"" + variable_display_fileName + "\" ;" + "\r\n" + "saveName = \"" + variable_display_saveName + "\";" + "\r\n" + "%>" + "\r\n" + "<table width=\"100%\">" + "\r\n" + "<tr>" + "\r\n" + "<td width=\"90%\">" + "\r\n" + "  <%@ include file = \"/public/jsp/modifyupload.jsp\" %>" + "\r\n" + "</td>" + "\r\n" + "</tr>" + "\r\n" + "</table>" + "\r\n";
    } else if ("1".equals(SystemCommon.getSendfileAccessoryEdit())) {
      resultValue = " <%\r\n canModify = true;\r\nif (curModifyField.indexOf(\"$" + checkField + "$\") < 0) {" + "\r\n" + "       canModify = false;" + "\r\n" + " }" + "\r\n" + " if (\"1\".equals(isEdit)) {" + "\r\n" + "     canModify = true;" + "\r\n" + " }%>" + "\r\n" + "<%" + "\r\n" + "accName= " + variable_name + " ;" + "\r\n" + "accSName =" + variable_saveName + ";" + "\r\n" + "if(null!=accName&&!\"\".equals(accName) &&null!=accSName&&!\"\".equals(accSName)) {" + "\r\n" + "    realFileArray = (accName+\"\").split(\"\\\\|\");" + "\r\n" + "\t   saveFileArray = (accSName+\"\").split(\"\\\\|\") ;" + "\r\n" + "}else{" + "\r\n" + "    realFileArray = new String[0] ;" + "\r\n" + "    saveFileArray = new String[0] ;" + "\r\n" + "}" + "\r\n" + "tableName=\"" + variable_tablename + "\";" + "\r\n" + "fileName = \"" + variable_display_fileName + "\" ;" + "\r\n" + "saveName = \"" + variable_display_saveName + "\";" + "\r\n" + "%>" + "\r\n" + "<table width=\"100%\">" + "\r\n" + "<tr>" + "\r\n" + "<td width=\"90%\">" + "\r\n" + "  <%@ include file = \"/public/jsp/doc_modifyupload.jsp\" %>" + "\r\n" + "</td>" + "\r\n" + "</tr>" + "\r\n" + "</table>" + "\r\n";
    } else {
      resultValue = " <%\r\n canModify = true;\r\nif (curModifyField.indexOf(\"$" + checkField + "$\") < 0) {" + "\r\n" + "       canModify = false;" + "\r\n" + " }" + "\r\n" + " if (\"1\".equals(isEdit)) {" + "\r\n" + "     canModify = true;" + "\r\n" + " }%>" + "\r\n" + "<%" + "\r\n" + "accName= " + variable_name + " ;" + "\r\n" + "accSName =" + variable_saveName + ";" + "\r\n" + "if(null!=accName&&!\"\".equals(accName) &&null!=accSName&&!\"\".equals(accSName)) {" + "\r\n" + "    realFileArray = (accName+\"\").split(\"\\\\|\");" + "\r\n" + "\t   saveFileArray = (accSName+\"\").split(\"\\\\|\") ;" + "\r\n" + "}else{" + "\r\n" + "    realFileArray = new String[0] ;" + "\r\n" + "    saveFileArray = new String[0] ;" + "\r\n" + "}" + "\r\n" + "tableName=\"" + variable_tablename + "\";" + "\r\n" + "fileName = \"" + variable_display_fileName + "\" ;" + "\r\n" + "saveName = \"" + variable_display_saveName + "\";" + "\r\n" + "%>" + "\r\n" + "<table width=\"100%\">" + "\r\n" + "<tr>" + "\r\n" + "<td width=\"90%\">" + "\r\n" + "  <%@ include file = \"/public/jsp/modifyupload.jsp\" %>" + "\r\n" + "</td>" + "\r\n" + "</tr>" + "\r\n" + "</table>" + "\r\n";
    } 
    return resultValue;
  }
  
  public String getRealYu6_add(String checkField) {
    String resultValue = "<%if(noWriteField.indexOf(\"$sendFileDepartWord$\")!=-1){%>\r\n   <html:hidden property=\"sendFileDepartWord\" value=\"\"/>\r\n   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\r\n<%}else{%>\r\n    <html:select property=\"sendFileDepartWord\" styleId=\"sendFileDepartWord\" onchange=\"changeSenddocumentWord()\">\r\n <html:option value=\"\">-请选择-</html:option>\r\n      <%if(senddocuementList!=null&&senddocuementList.size()>0){\r\n\t\t\tfor(int i=0;i<senddocuementList.size();i++){\r\n          Object [] wordObj=(Object[])senddocuementList.get(i);\r\n          String  wordValue=wordObj[0]+\";\"+wordObj[1]+\";\"+wordObj[2];\r\n          String  wordText=wordObj[1]+\"\";%>\r\n <html:option value=\"<%=wordValue%>\"><%=wordText%></html:option>\r\n <%}\t}%>\r\n   </html:select>\r\n<%}%>\r\n";
    return resultValue;
  }
  
  public String getRealYu6_modi(String checkField) {
    String resultValue = "<%String zjkyWordId=(request.getAttribute(\"zjkyWordId\")==null || \"null\".equals(request.getAttribute(\"zjkyWordId\")))?\"\":request.getAttribute(\"zjkyWordId\").toString();%>\r\n<% if(\"1\".equals(isEdit)||curModifyField.indexOf(\"$sendFileDepartWord$\") >=0 ){%>\r\n\t<select name=\"sendFileDepartWord\" id=\"sendFileDepartWord\" onchange=\"changeSenddocumentWord()\">\r\n\t    <option value=\"\">-请选择-</option>\r\n\t    <%if(senddocuementList!=null&&senddocuementList.size()>0){\r\n\t    for(int i=0;i<senddocuementList.size();i++){\r\n\t      Object  wordObj[]=(Object [])senddocuementList.get(i);\r\n           String  wordValue=\"\"+wordObj[0]+\";\"+wordObj[1]+\";\"+wordObj[2];\r\n           String  wordText=wordObj[1]+\"\";%>\r\n      <option value=\"<%=wordValue%>\" <%if(zjkyWordId.equals(\"\"+wordObj[0])){out.print(\"selected\");}%> ><%=wordText%></option>\r\n      <%}\t}%>\r\n\t</select>\r\n<%}else{%>" + this.fontSizeHtmlPre + "<%=sendWord%>" + this.fontSizeHtmlSuf + "<html:hidden property=\"sendFileDepartWord\"/>\r\n<% }%>\r\n";
    return resultValue;
  }
  
  public String getRealYu78_modi(String checkField) {
    String resultValue = "<%String  " + checkField + "_value=(request.getAttribute(\"" + checkField + "\")==null || \"null\".equals(request.getAttribute(\"" + 
      checkField + "\")))?\"\":request.getAttribute(\"" + checkField + "\").toString();%>" + "\r\n" + 
      "<%if(\"1\".equals(request.getParameter(\"viewOnly\")) && \"0\".equals(request.getParameter(\"canEdit\")) || curModifyField.indexOf(\"$" + checkField + "$\")<0){%>" + 
      "<span style='text-align:left;font-size:<%=docFormFontSize%>;'><%=" + checkField + "_value%></span><input  type=\"hidden\" id=\"" + checkField + "\" name=\"" + checkField + "\" value=\"<%=" + checkField + "_value%>\" >\r\n" + 
      " <%}else{ %><input  type=\"text\" class=\"sw_text\" id=\"" + checkField + "\" name=\"" + 
      checkField + "\" value=\"<%=" + checkField + "_value%>\" style=\"width:98%\" readonly=\"true\" >\r\n<%} %>";
    return resultValue;
  }
  
  public String getRealYu78_add(String checkField) {
    String resultValue = "<%String  " + checkField + "_value=(request.getAttribute(\"" + checkField + "\")==null || \"null\".equals(request.getAttribute(\"" + 
      checkField + "\")))?\"\":request.getAttribute(\"" + checkField + "\").toString();%>" + "\r\n<input  type=\"text\" class=\"sw_text\"  name=\"" + 
      checkField + "\" id=\"" + checkField + "\" value=\"<%=" + checkField + "_value%>\" style=\"width:98%\" readonly=\"true\" >\r\n";
    return resultValue;
  }
  
  public String getRealYu9_add(String checkField) {
    String resultValue = "<%String documentSendFileTopicWord_value=request.getAttribute(\"documentSendFileTopicWord\")==null?\"\":request.getAttribute(\"documentSendFileTopicWord\").toString();\r\n\tif(noWriteField.indexOf(\"$documentSendFileTopicWord$\")!=-1){%>\r\n<%=" + 
      
      checkField + "_value==null?\"\":" + checkField + "_value%>" + "\r\n" + 
      "\t <input type=\"hidden\" id=\"documentSendFileTopicWord\" name=\"documentSendFileTopicWord\"  value=\"<%=documentSendFileTopicWord_value%>\"  >" + "\r\n" + "<%}else{%>" + "\r\n" + 
      "<input  type=\"text\"  class=\"sw_text\" id=\"documentSendFileTopicWord\" name=\"documentSendFileTopicWord\"  style=\"width:90%\" value=\"<%=documentSendFileTopicWord_value%>\">" + 
      "\r\n" + "\t  <input type=\"button\" class=\"btnButton2Font\" onClick=\"selecttw();\" value=\"选择\" />" + "\r\n" + 
      "<input type=\"hidden\" id=\"topicWordMustWrite\" name=\"topicWordMustWrite\" value=\"1\">" + "\r\n" + "<%}%>" + "\r\n";
    return resultValue;
  }
  
  public String getRealYu9_modi(String checkField) {
    String resultValue = "<%String documentSendFileTopicWord_value=(request.getAttribute(\"documentSendFileTopicWord\")==null || \"null\".equals(request.getAttribute(\"documentSendFileTopicWord\")))?\"\":request.getAttribute(\"documentSendFileTopicWord\").toString();\r\n\tif(!\"1\".equals(isEdit)&&curModifyField.indexOf(\"$documentSendFileTopicWord$\") <0&&!\"1\".equals(request.getParameter(\"resubmit\"))){%>\r\n\t <input type=\"hidden\"  name=\"documentSendFileTopicWord\" id=\"documentSendFileTopicWord\" value=\"<%=documentSendFileTopicWord_value%>\" >" + this.fontSizeHtmlPre + "<%=documentSendFileTopicWord_value%>" + this.fontSizeHtmlSuf + "<%}else{%>\r\n      <input  type=\"text\"  class=\"sw_text\" name=\"documentSendFileTopicWord\" id=\"documentSendFileTopicWord\" style=\"width:90%\" value=\"<%=documentSendFileTopicWord_value%>\">\r\n\t   <input type=\"button\" class=\"btnButton2Font\" onClick=\"selecttw();\" value=\"选择\"/>\r\n      <input type=\"hidden\" name=\"topicWordMustWrite\" id=\"topicWordMustWrite\" value=\"1\">\r\n<%}%>\r\n";
    return resultValue;
  }
  
  public String getRealYu10_add(String checkField) {
    String resultValue = "<input type=\"hidden\" name=\"" + checkField + "\" id=\"" + checkField + "\" >";
    return resultValue;
  }
  
  public String getRealYu10_modi(String checkField) {
    String resultValue = " <%\r\n comment_variableName = \"" + checkField + "\";" + "\r\n" + " comment_variableModifyContent = \"\";" + "\r\n" + " %>" + "\r\n" + " <%@ include file = \"/doc/doc/doc_includeComment.jsp\" %>" + "\r\n";
    return resultValue;
  }
  
  public String getRealYu11_add(String checkField) {
    String resultValue = "";
    resultValue = "<%if(noWriteField.indexOf(\"$" + checkField + "$\")!=-1){%>" + "\r\n" + 
      "\t<html:hidden   property=\"zjkySeq\" />" + "\r\n" + "<%}else{%>" + "\r\n" + 
      "    <html:text styleClass=\"sw_text\"   property=\"zjkySeq\" /><input type=\"button\" class=\"btnButton2Font\" onClick=\"changeSeqNum();\" value=\"选择\" /> " + 
      "\r\n" + "<%}%>" + "\r\n";
    return resultValue;
  }
  
  public String getRealYu11_modi(String checkField) {
    String resultValue = "";
    resultValue = " <%--<html:text styleClass=\"sw_text\"  property=\"zjkySeq\" />\r\n--%> <%if(curModifyField.indexOf(\"$" + 
      checkField + "$\") < 0 && !\"1\".equals(request.getParameter(\"resubmit\"))&&!\"1\".equals(isEdit)){%>" + 
      "\r\n" + "<html:text property=\"zjkySeq\" readonly=\"true\" style='border-left:0px;border-top:0px;border-right:0px;border-bottom:0px;font-size:" + this.fontsize + ";'/><%}else{%>" + "\r\n" + "<html:text styleClass=\"sw_text\"  property=\"zjkySeq\" style='font-size:" + this.fontsize + ";' /> <input type=\"button\" class=\"btnButton2Font\" onClick=\"changeSeqNum();\" value=\"选择\" />" + 
      "\r\n" + " <%}%>" + "\r\n";
    return resultValue;
  }
  
  public String getRealYu12_add(String checkField) {
    String resultValue = "<%String  " + checkField + "_value=request.getAttribute(\"" + checkField + "\")==null?\"\":request.getAttribute(\"" + checkField + "\").toString();" + "\r\n" + 
      " if(" + checkField + "_value==null||" + checkField + "_value.equals(\"null\")||" + checkField + "_value.equals(\"\")){" + "\r\n" + 
      checkField + "_value=request.getParameter(\"" + checkField + "\")==null?\"\":request.getParameter(\"" + checkField + "\").toString();" + "\r\n" + 
      "}" + "\r\n" + 
      "  if(noWriteField.indexOf(\"$" + checkField + "$\")<0){%>" + "\r\n" + 

      
      "<div style=\"text-align:right;\">" + 
      "<input type=\"button\"  value=\"常用语\"" + 
      "\t\t    \tonclick=\"window.open('/jsoa/tjgzw/common_words.jsp?p=" + checkField + "','','height=100,width=400,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no')\">" + 
      "\t</input>" + 
      "</div>" + 
      "   <textarea  rows=\"7\" name=\"" + checkField + "\" id=\"" + checkField + "\" class=\"sw_text\" style=\"width:98%;\"><%=" + checkField + "_value%></textarea>" + "\r\n" + 
      
      "<% }else{%>" + "\r\n" + 
      "      <%=com.js.util.util.CharacterTool.escapeHTMLTagsSimple(" + checkField + "_value==null?\"\":" + checkField + "_value)%>" + "\r\n" + 
      "      <input type=\"hidden\" id=\"" + checkField + "\" name=\"" + checkField + "\" value=\"<%=" + checkField + "_value%>\"/>" + "\r\n" + 
      "<%}%>" + "\r\n";
    return resultValue;
  }
  
  public String getRealYu12_modi(String checkField) {
    String resultValue = " <%String  " + checkField + "_value=(request.getAttribute(\"" + checkField + "\")==null || \"null\".equals(request.getAttribute(\"" + checkField + "\")))?\"\":request.getAttribute(\"" + checkField + "\").toString();" + "\r\n" + 
      " if(\"1\".equals(isEdit)){%>" + "\r\n" + 

      
      "<div style=\"text-align:right;\">" + 
      "<input type=\"button\" value=\"常用语\"" + 
      "\t\t    \tonclick=\"window.open('/jsoa/tjgzw/common_words.jsp?p=" + checkField + "','','height=100,width=400,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no')\">" + 
      "\t</input>" + 
      "</div>" + 
      "   <textarea  rows=\"7\" name=\"" + checkField + "\" id=\"" + checkField + "\" class=\"sw_text\" style=\"width:98%;height:99%;\"><%=" + checkField + "_value%></textarea>" + "\r\n" + 
      
      "<% }else{" + "\r\n" + 
      "     if(curModifyField.indexOf(\"$" + checkField + "$\") < 0&& !\"1\".equals(request.getParameter(\"resubmit\"))){%>" + "\r\n";
    if ("1".equals(SystemCommon.getIsSsdz()))
      this.fontSizeHtmlPre = "<div style='text-align:left;font-size:<%=docFormFontSize%>;line-height:1.4;'>"; 
    resultValue = String.valueOf(resultValue) + this.fontSizeHtmlPre + "     <%=com.js.util.util.CharacterTool.escapeHTMLTagsSimple(" + checkField + "_value==null?\"\":" + checkField + "_value)%>" + this.fontSizeHtmlSuf + "\r\n" + 
      "     <input type=\"hidden\" name=\"" + checkField + "\" id=\"" + checkField + "\" value=\"<%=" + checkField + "_value%>\"/>" + "\r\n" + 
      " <%}else{%>" + "\r\n" + 


      
      "     <textarea name=\"" + checkField + "\" id=\"" + checkField + "\" class=\"sw_text\" style=\"width:98%;height:99%;\"><%=" + checkField + "_value%></textarea>" + "\r\n" + 
      
      " <%}}%>" + "\r\n";
    if ("1".equals(SystemCommon.getIsSsdz()))
      this.fontSizeHtmlPre = "<div style='text-align:left;font-size:<%=docFormFontSize%>;'>"; 
    return resultValue;
  }
  
  public String getRealYu13_add(String checkField) {
    String resultValue = "<%String  " + checkField + "_value=request.getAttribute(\"" + checkField + "\")==null?" + 
      "\"\":request.getAttribute(\"" + checkField + "\").toString();" + "\r\n" + 
      "if(" + checkField + "_value==null||" + checkField + "_value.equals(\"null\")||" + checkField + "_value.equals(\"\")){" + 
      "\r\n" + checkField + "_value=request.getParameter(\"" + checkField + "\")==null?" + 
      "\"\":request.getParameter(\"" + checkField + "\").toString();" + "\r\n }" + "\r\n" + 
      "if(noWriteField.indexOf(\"$" + checkField + "$\")<0){%>" + "\r\n" + 
      "<input type=\"text\" name=\"" + checkField + "_name\" id=\"" + checkField + "_name\" value=\"\" onclick=\"" + 
      "openEndow('" + checkField + "','" + checkField + "_name',document.getElementById('" + checkField + "').value,document.getElementById('" + checkField + "_name').value,'user','no','user','*0*')" + 
      "\" readonly=\"readonly\" style=\"width:99%\" class=\"inputText\" title=\"请点击选择\">" + 
      "<input type=\"hidden\" name=\"" + checkField + "\" id=\"" + checkField + "\" >" + "\r\n" + 
      "<% }else{%>" + "\r\n" + 
      "<span><%=com.js.system.util.StaticParam.getNamesByIds(" + checkField + "_value) %></span>" + "\r\n" + 
      "<input type=\"hidden\" name=\"" + checkField + "\" id=\"" + checkField + "\" value=\"<%=" + checkField + "_value%>\"/>" + "\r\n" + 
      "<%}%>" + "\r\n";
    return resultValue;
  }
  
  public String getRealYu13_modi(String checkField) {
    String resultValue = " <%String " + checkField + "_value=(request.getAttribute(\"" + checkField + "\")==null || \"null\".equals(request.getAttribute(\"" + checkField + "\")+\"\"))?" + 
      "\"\":request.getAttribute(\"" + checkField + "\").toString();" + "\r\n" + 






      
      "if(curModifyField.indexOf(\"$" + checkField + "$\") < 0&& !\"1\".equals(request.getParameter(\"resubmit\"))){%>" + "\r\n" + 
      this.fontSizeHtmlPre + "<%=com.js.system.util.StaticParam.getNamesByIds(" + checkField + "_value) %>" + this.fontSizeHtmlSuf + "\r\n" + 
      "<input type=\"hidden\" name=\"" + checkField + "\" id=\"" + checkField + "\" value=\"<%=" + checkField + "_value%>\"/>" + "\r\n" + 
      "<%}else{%>" + "\r\n" + 
      "<input type=\"text\" name=\"" + checkField + "_name\" id=\"" + checkField + "_name\" value=\"<%=com.js.system.util.StaticParam.getNamesByIds(" + checkField + "_value) %>\" onclick=\"" + 
      "openEndow('" + checkField + "','" + checkField + "_name',document.getElementById('" + checkField + "').value,document.getElementById('" + checkField + "_name').value,'user','no','user','*0*')" + 
      "\" readonly=\"readonly\" style=\"width:99%\" class=\"inputText\" title=\"请点击选择\">" + 
      "<input type=\"hidden\" name=\"" + checkField + "\" id=\"" + checkField + "\" value=\"<%=" + checkField + "_value %>\" >" + "\r\n" + 
      "<%}%>" + "\r\n";
    return resultValue;
  }
  
  public String getTempFile(String path, String fileName) {
    String myreadline = "";
    try {
      FileReader fr = new FileReader(String.valueOf(path) + "/doc/doc/" + fileName);
      BufferedReader br = null;
      for (br = new BufferedReader(fr); br.ready();)
        myreadline = String.valueOf(myreadline) + br.readLine() + "\r\n"; 
      br.close();
      fr.close();
    } catch (IOException e) {
      e.printStackTrace();
    } 
    return myreadline;
  }
  
  public String getDisplayType_IsComment(List<GovCustomFieldPO> fieldList, String fieldName) {
    String isComment = "0";
    for (int i = 0; i < fieldList.size(); i++) {
      GovCustomFieldPO po = fieldList.get(i);
      if (fieldName.equals(po.getGffName())) {
        int displayType = po.getGffDisplayType();
        if (displayType == 10)
          isComment = "1"; 
      } 
    } 
    return isComment;
  }
  
  public void getControlList(List<GovCustomFieldPO> fieldList, String fieldName, List<String[]> controList, String fieldDisplayName) {
    String isComment = "0";
    for (int i = 0; i < fieldList.size(); i++) {
      GovCustomFieldPO po = fieldList.get(i);
      if (fieldName.equals(po.getGffName())) {
        int displayType = po.getGffDisplayType();
        if (displayType == 10)
          isComment = "1"; 
        String[] controlField = { fieldName, fieldDisplayName, isComment, po.getGffFieldType(), po.getGffLength(), po.getGffDisplayType() };
        controList.add(controlField);
      } 
    } 
  }
  
  public String getDisplayName(String checkField, String[] disAllArr) {
    String resultVlaue = "";
    for (int i = 0; i < disAllArr.length; i++) {
      if (disAllArr[i] != null && !disAllArr[i].toString().equals("") && !disAllArr[i].toString().equals("null")) {
        String[] totalNameArr = disAllArr[i].toString().split("_");
        if (checkField.equals(totalNameArr[0].toString()))
          resultVlaue = totalNameArr[1].toString(); 
      } 
    } 
    return resultVlaue;
  }
  
  public String getCheckLengFunction(List<String[]> checkFieldList) {
    String result = " function  checkFieldLength(){\r\n  if(true";
    for (int i = 0; i < checkFieldList.size(); i++) {
      String[] controlField = checkFieldList.get(i);
      int type = Integer.parseInt(controlField[3]);
      if (!"accessoryName".equals(controlField[0]) && !"saveName".equals(controlField[0]) && !"accessoryName1".equals(controlField[0]) && !"accessoryName2".equals(controlField[0])) {
        int length;
        int realLength;
        int ii;
        switch (type) {
          case 0:
            result = String.valueOf(result) + "&checkTextLengthOnly(document.all." + controlField[0] + "," + controlField[4] + ",\"" + controlField[1] + "\")";
            break;
          case 1:
            length = Integer.parseInt(controlField[4]);
            realLength = 0;
            for (ii = length - 1; ii > 0; ii--)
              realLength += (int)Math.pow(10.0D, (ii - 1)) * 9; 
            result = String.valueOf(result) + "&checkNumber(document.all." + controlField[0] + ",\"" + controlField[1] + "\"," + realLength + ")";
            break;
          case 2:
            result = String.valueOf(result) + "&checkTextLengthOnly(document.all." + controlField[0] + "," + controlField[4] + ",\"" + controlField[1] + "\")";
            break;
          case 3:
            result = String.valueOf(result) + "&isPhone(document.all." + controlField[0] + ")";
            break;
        } 
      } 
    } 
    result = String.valueOf(result) + "){\r\n  return true;\r\n}else{\r\n return false;\r\n }}";
    return result;
  }
  
  public String getSetBookMarks(List<String[]> checkFieldList) {
    String resultValue = "";
    for (int i = 0; i < checkFieldList.size(); i++) {
      String[] controlField = checkFieldList.get(i);
      if (!controlField[2].toString().equals("1"))
        if (controlField[0].toString().equals("accessoryName")) {
          resultValue = String.valueOf(resultValue) + "var doc_accessoryValue=\"\";";
          resultValue = String.valueOf(resultValue) + "var doc_accessoryName=document.getElementsByName(\"accessoryName\");";
          resultValue = String.valueOf(resultValue) + "for(var i=0;i<doc_accessoryName.length;i++){doc_accessoryValue+=doc_accessoryName[i].value+\"\\\\n\\\\r\";}";
          resultValue = String.valueOf(resultValue) + "document.all.\\$" + controlField[0] + ".value=\"[" + controlField[1] + "]\"+doc_accessoryValue;" + "\r\n";
        } else if (controlField[0].toString().equals("sendFileDepartWord")) {
          resultValue = String.valueOf(resultValue) + "document.all.\\$sendFileDepartWord.value = \"[" + controlField[1] + "]\"+departWord;" + "\r\n";
        } else if (controlField[3].toString().equals("2")) {
          resultValue = String.valueOf(resultValue) + "var js_" + controlField[0] + "=document.all." + controlField[0] + ".value;" + "\r\n" + "js_" + controlField[0] + "=js_" + controlField[0] + ".replace(\"/\",\"年\");" + "\r\n" + "js_" + controlField[0] + "=js_" + controlField[0] + ".replace(\"/\",\"月\");" + "\r\n" + "js_" + controlField[0] + "=js_" + controlField[0] + "+\"日\";" + "\r\n";
          resultValue = String.valueOf(resultValue) + "document.all.\\$" + controlField[0] + ".value = \"[" + controlField[1] + "]\"+js_" + controlField[0] + ";" + "\r\n";
        } else {
          resultValue = String.valueOf(resultValue) + "document.all.\\$" + controlField[0] + ".value=\"[" + controlField[1] + "]\"+document.all." + controlField[0] + ".value;" + "\r\n";
        }  
    } 
    return resultValue;
  }
  
  public String getBookMarksHidden(List<String[]> checkFieldList) {
    String resultValue = "";
    for (int i = 0; i < checkFieldList.size(); i++) {
      String[] controlField = checkFieldList.get(i);
      if (!controlField[2].toString().equals("1"))
        resultValue = String.valueOf(resultValue) + "<input type=\"hidden\" id=\"\\$" + controlField[0] + "\" name=\"\\$" + controlField[0] + "\" value=\"-1\">" + "\r\n"; 
    } 
    return resultValue;
  }
  
  public String getHiddenField(List<GovCustomFieldPO> fieldList) {
    String resultValue = "";
    for (int i = 0; i < fieldList.size(); i++) {
      GovCustomFieldPO po = fieldList.get(i);
      int type = po.getGffFieldType();
      switch (type) {
        case 0:
          resultValue = String.valueOf(resultValue) + "<input type=\"hidden\" name=\"" + po.getGffName() + "\" value=\"0\">" + "\r\n";
          break;
        case 1:
          resultValue = String.valueOf(resultValue) + "<input type=\"hidden\" name=\"" + po.getGffName() + "\" value=\"0\">" + "\r\n";
          break;
        case 2:
          resultValue = String.valueOf(resultValue) + "<input type=\"hidden\" name=\"" + po.getGffName() + "\" value=\"2008/12/12\">" + "\r\n";
          break;
        case 3:
          resultValue = String.valueOf(resultValue) + "<input type=\"hidden\" name=\"" + po.getGffName() + "\" value=\"0\">" + "\r\n";
          break;
      } 
    } 
    return resultValue;
  }
}
