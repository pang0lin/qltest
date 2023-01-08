package com.js.oa.userdb.newCode.action;

import com.js.oa.userdb.newCode.po.CodeSetPO;
import com.js.oa.userdb.newCode.po.NewCodePO;
import com.js.oa.userdb.newCode.service.CodeManageService;
import com.js.oa.userdb.newCode.service.CodeSetBD;
import com.js.oa.userdb.newCode.service.NewCodeBD;
import com.js.util.page.Page;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class NewCodeAction extends Action {
  ActionForward forward = new ActionForward();
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    String action = request.getParameter("action");
    if ("add".equals(action)) {
      String codeName = request.getParameter("codeName");
      String codeContent = request.getParameter("codeContent");
      NewCodePO newCodePO = new NewCodePO();
      newCodePO.setCodeName(codeName);
      newCodePO.setCodeContent(codeContent);
      newCodePO.setCodeStatus(1);
      NewCodeBD newCodeBD = new NewCodeBD();
      newCodeBD.addNewCode(newCodePO);
      request.setAttribute("reload", "quit");
      return mapping.findForward("add");
    } 
    if ("list".equals(action))
      return viewList(mapping, request, response); 
    if ("toModi".equals(action)) {
      String codeId = request.getParameter("codeId");
      NewCodeBD newCodeBD = new NewCodeBD();
      NewCodePO newCodePO = newCodeBD.load(codeId);
      request.setAttribute("po", newCodePO);
      this.forward = mapping.findForward("modi");
      return this.forward;
    } 
    if ("modi".equals(action)) {
      String codeName = request.getParameter("codeName");
      String codeContent = request.getParameter("codeContent");
      String codeId = request.getParameter("codeId");
      String codeStatus = request.getParameter("codeStatus");
      NewCodePO newCodePO = new NewCodePO();
      newCodePO.setCodeName(codeName);
      newCodePO.setCodeContent(codeContent);
      newCodePO.setCodeStatus(Integer.parseInt(codeStatus));
      newCodePO.setCodeId(Long.parseLong(codeId));
      NewCodeBD newCodeBD = new NewCodeBD();
      newCodeBD.modi(newCodePO);
      request.setAttribute("reload", "quit");
      return mapping.findForward("modi");
    } 
    if ("delone".equals(action)) {
      String codeId = request.getParameter("codeId");
      NewCodeBD newCodeBD = new NewCodeBD();
      newCodeBD.del(codeId);
      request.setAttribute("reload", "reload");
      return viewList(mapping, request, response);
    } 
    if ("codeSetList".equals(action))
      return viewCodeSetList(mapping, request, response); 
    if ("toAddCodeSet".equals(action)) {
      String codeId = request.getParameter("codeId");
      request.setAttribute("codeId", codeId);
      CodeSetBD codeSetBD = new CodeSetBD();
      request.setAttribute("NumRelyDateMap", codeSetBD.getRelyDate(codeId));
      request.setAttribute("codeSetFdOrder", (new StringBuilder(String.valueOf(codeSetBD.getMaxOrder(codeId)))).toString());
      this.forward = mapping.findForward("codeSetAdd");
      return this.forward;
    } 
    if ("addCodeSet".equals(action))
      return addCodeSetAction(mapping, request, response); 
    if ("toModCodeSet".equals(action)) {
      String codeSetId = request.getParameter("codeSetId");
      CodeSetBD codeSetBD = new CodeSetBD();
      CodeSetPO codeSetPO = codeSetBD.load(codeSetId);
      request.setAttribute("NumRelyDateMap", codeSetBD.getRelyDate((new StringBuilder(String.valueOf(codeSetPO.getCodeId()))).toString()));
      request.setAttribute("po", codeSetPO);
      this.forward = mapping.findForward("codeSetMod");
      return this.forward;
    } 
    if ("modCodeSet".equals(action))
      return modCodeSetAction(mapping, request, response); 
    if ("delCodeSet".equals(action)) {
      CodeSetBD codeSetBD = new CodeSetBD();
      String codeSetId = request.getParameter("codeSetId");
      codeSetBD.del(codeSetId);
      request.setAttribute("codeId", request.getParameter("codeId"));
      request.setAttribute("reload", "reload");
      return mapping.findForward("codeSetList");
    } 
    if ("getCodeNumber".equals(action)) {
      String returnVal = request.getParameter("returnVal");
      request.setAttribute("returnVal", returnVal);
      CodeManageService cmService = new CodeManageService();
      String codeId = request.getParameter("codeId");
      request.setAttribute("codeNumber", cmService.getCodeNum(codeId));
      request.setAttribute("codeId", codeId);
      request.setAttribute("reload", "quit");
      return mapping.findForward("codeSelect");
    } 
    if ("chooseCodeType".equals(action)) {
      String returnVal = request.getParameter("returnVal");
      CodeManageService cmService = new CodeManageService();
      List<NewCodePO> list = cmService.getCodeList();
      request.setAttribute("codeList", list);
      request.setAttribute("returnVal", returnVal);
      return mapping.findForward("codeSelect");
    } 
    if ("test".equals(action)) {
      String codeId = request.getParameter("codeId");
      String codeNumber = request.getParameter("codeNumber");
      CodeManageService cmService = new CodeManageService();
      String rString = cmService.checkCodeOrMarkNewCode(codeId, codeNumber, "");
      System.out.println("----------" + rString);
    } 
    return null;
  }
  
  public ActionForward viewCodeSetList(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response) {
    String codeId = request.getParameter("codeId");
    NewCodeBD newCodeBD = new NewCodeBD();
    NewCodePO newCodePO = newCodeBD.load(codeId);
    request.setAttribute("newCodePo", newCodePO);
    CodeSetBD codeSetBD = new CodeSetBD();
    List<CodeSetPO> codeSetList = codeSetBD.getCodeSetList(codeId);
    if (codeSetList != null && codeSetList.size() > 0) {
      String codeParameterFormat = getCodeParameterFormat(codeSetList);
      request.setAttribute("codeParameterFormat", codeParameterFormat);
      request.setAttribute("codeSetList", codeSetList);
    } else {
      request.setAttribute("codeParameterFormat", "");
      request.setAttribute("codeSetList", codeSetList);
    } 
    this.forward = mapping.findForward("codeSetList");
    return this.forward;
  }
  
  public ActionForward modCodeSetAction(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response) {
    CodeSetBD codeSetBD = new CodeSetBD();
    String codeSetId = request.getParameter("codeSetId");
    CodeSetPO codeSetPO = codeSetBD.load(codeSetId);
    String oldCodeSetId = request.getParameter("oldCodeSetId");
    if ("s".equals(oldCodeSetId)) {
      codeSetPO.setCodeSetContent("");
    } else if ("n".equals(oldCodeSetId)) {
      codeSetPO.setCodeSetBegin(0);
      codeSetPO.setCodeSetStep(0);
      codeSetPO.setCodeSetIsRelyDate(0);
      codeSetPO.setCodeSetRelySetId(0L);
      codeSetPO.setCodeSetIsComplete(0);
      codeSetPO.setCodeSetCompleteCnt(1);
    } else if ("d".equals(oldCodeSetId)) {
      codeSetPO.setCodeSetDateYear(0);
      codeSetPO.setCodeSetDateMonth(0);
      codeSetPO.setCodeSetDateDay(0);
      codeSetPO.setCodeSetDateIsComplete(0);
      codeSetPO.setCodeSetDateSplit("");
    } 
    String codeSetType = request.getParameter("codeSetType");
    codeSetPO.setCodeSetType(codeSetType);
    if ("s".equals(codeSetType)) {
      String codeSetContent = request.getParameter("codeSetContent");
      codeSetPO.setCodeSetContent(codeSetContent);
    } else if ("n".equals(codeSetType)) {
      String codeSetBegin = request.getParameter("codeSetBegin");
      codeSetPO.setCodeSetBegin(Integer.parseInt(codeSetBegin));
      String codeSetStep = request.getParameter("codeSetStep");
      codeSetPO.setCodeSetStep(Integer.parseInt(codeSetStep));
      int codeSetIsRelyDate = Integer.parseInt(request.getParameter("codeSetIsRelyDate"));
      codeSetPO.setCodeSetIsRelyDate(codeSetIsRelyDate);
      if (codeSetIsRelyDate == 1) {
        String codeSetRelySetId = request.getParameter("codeSetRelySetId");
        codeSetPO.setCodeSetRelySetId(Long.parseLong(codeSetRelySetId));
      } 
      int codeSetIsComplete = Integer.parseInt(request.getParameter("codeSetIsComplete"));
      codeSetPO.setCodeSetIsComplete(codeSetIsComplete);
      if (codeSetIsComplete == 1) {
        String codeSetCompleteCnt = request.getParameter("codeSetCompleteCnt");
        codeSetPO.setCodeSetCompleteCnt(Integer.parseInt(codeSetCompleteCnt));
      } 
    } else if ("d".equals(codeSetType)) {
      int codeSetDateYear = Integer.parseInt(request.getParameter("codeSetDateYear"));
      codeSetPO.setCodeSetDateYear(codeSetDateYear);
      int codeSetDateMonth = Integer.parseInt(request.getParameter("codeSetDateMonth"));
      codeSetPO.setCodeSetDateMonth(codeSetDateMonth);
      int codeSetDateDay = Integer.parseInt(request.getParameter("codeSetDateDay"));
      codeSetPO.setCodeSetDateDay(codeSetDateDay);
      int codeSetDateIsComplete = Integer.parseInt(request.getParameter("codeSetDateIsComplete"));
      codeSetPO.setCodeSetDateIsComplete(codeSetDateIsComplete);
      String codeSetDateSplit = request.getParameter("codeSetDateSplit");
      codeSetPO.setCodeSetDateSplit(codeSetDateSplit);
    } 
    codeSetBD.modi(codeSetPO);
    request.setAttribute("reload", "quit");
    return mapping.findForward("codeSetMod");
  }
  
  public ActionForward addCodeSetAction(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response) {
    CodeSetPO codeSetPO = new CodeSetPO();
    String codeId = request.getParameter("codeId");
    codeSetPO.setCodeId(Long.parseLong(codeId));
    String codeSetType = request.getParameter("codeSetType");
    codeSetPO.setCodeSetType(codeSetType);
    if ("s".equals(codeSetType)) {
      String codeSetContent = request.getParameter("codeSetContent");
      codeSetPO.setCodeSetContent(codeSetContent);
    } else if ("n".equals(codeSetType)) {
      String codeSetBegin = request.getParameter("codeSetBegin");
      codeSetPO.setCodeSetBegin(Integer.parseInt(codeSetBegin));
      String codeSetStep = request.getParameter("codeSetStep");
      codeSetPO.setCodeSetStep(Integer.parseInt(codeSetStep));
      int codeSetIsRelyDate = Integer.parseInt(request.getParameter("codeSetIsRelyDate"));
      codeSetPO.setCodeSetIsRelyDate(codeSetIsRelyDate);
      if (codeSetIsRelyDate == 1) {
        String codeSetRelySetId = request.getParameter("codeSetRelySetId");
        codeSetPO.setCodeSetRelySetId(Long.parseLong(codeSetRelySetId));
      } 
      int codeSetIsComplete = Integer.parseInt(request.getParameter("codeSetIsComplete"));
      codeSetPO.setCodeSetIsComplete(codeSetIsComplete);
      if (codeSetIsComplete == 1) {
        String codeSetCompleteCnt = request.getParameter("codeSetCompleteCnt");
        codeSetPO.setCodeSetCompleteCnt(Integer.parseInt(codeSetCompleteCnt));
      } 
    } else if ("d".equals(codeSetType)) {
      int codeSetDateYear = Integer.parseInt(request.getParameter("codeSetDateYear"));
      codeSetPO.setCodeSetDateYear(codeSetDateYear);
      int codeSetDateMonth = Integer.parseInt(request.getParameter("codeSetDateMonth"));
      codeSetPO.setCodeSetDateMonth(codeSetDateMonth);
      int codeSetDateDay = Integer.parseInt(request.getParameter("codeSetDateDay"));
      codeSetPO.setCodeSetDateDay(codeSetDateDay);
      int codeSetDateIsComplete = Integer.parseInt(request.getParameter("codeSetDateIsComplete"));
      codeSetPO.setCodeSetDateIsComplete(codeSetDateIsComplete);
      String codeSetDateSplit = request.getParameter("codeSetDateSplit");
      codeSetPO.setCodeSetDateSplit(codeSetDateSplit);
    } 
    int codeSetOrder = Integer.parseInt(request.getParameter("codeSetOrder"));
    codeSetPO.setCodeSetOrder(codeSetOrder);
    CodeSetBD codeSetBD = new CodeSetBD();
    int codeSetOrder1 = codeSetBD.getMaxOrder(codeId);
    if (codeSetOrder1 > codeSetOrder)
      codeSetPO.setCodeSetOrder(codeSetOrder1); 
    codeSetBD.addCodeSet(codeSetPO);
    request.setAttribute("reload", "quit");
    return mapping.findForward("codeSetAdd");
  }
  
  public ActionForward viewList(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response) {
    String where = "where ";
    String para = "code";
    String from = "com.js.oa.userdb.newCode.po.NewCodePO code";
    where = String.valueOf(where) + "code.codeStatus='1' ";
    if (request.getParameter("searchName") != null) {
      request.setAttribute("searchName", request.getParameter("searchName"));
      where = String.valueOf(where) + "and code.codeName like '%" + request.getParameter("searchName") + "%'";
    } 
    where = String.valueOf(where) + " order by code.codeId desc";
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
    request.setAttribute("codeList", list);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("recordCount", String.valueOf(page.getRecordCount()));
    request.setAttribute("pageParameters", "action,searchName");
  }
  
  private String getCodeParameterFormat(List<CodeSetPO> codeSetList) {
    SimpleDateFormat sdfDateFormat = null;
    StringBuffer codeParameterFormat = new StringBuffer();
    for (CodeSetPO po : codeSetList) {
      if ("s".equals(po.getCodeSetType())) {
        codeParameterFormat.append(po.getCodeSetContent());
        continue;
      } 
      if ("n".equals(po.getCodeSetType())) {
        StringBuffer temp = new StringBuffer();
        if (po.getCodeSetIsComplete() == 1) {
          int ccnt = po.getCodeSetCompleteCnt();
          for (int i = 0; i < ccnt - 1; i++)
            temp.append("0"); 
        } 
        codeParameterFormat.append(temp.toString()).append(po.getCodeSetBegin());
        po.setTempCodeSetNum(String.valueOf(temp.toString()) + po.getCodeSetBegin());
        continue;
      } 
      if ("d".equals(po.getCodeSetType())) {
        String split = po.getCodeSetDateSplit();
        if (split == null)
          split = ""; 
        StringBuffer dateFormat = new StringBuffer();
        if (po.getCodeSetDateYear() == 1)
          dateFormat.append("yyyy").append(split); 
        if (po.getCodeSetDateMonth() == 1)
          dateFormat.append("MM").append(split); 
        if (po.getCodeSetDateDay() == 1)
          dateFormat.append("dd").append(split); 
        if ("".equals(split)) {
          sdfDateFormat = new SimpleDateFormat(dateFormat.toString());
        } else {
          sdfDateFormat = new SimpleDateFormat(dateFormat.substring(0, dateFormat.length() - 1));
        } 
        if (po.getCodeSetDateIsComplete() == 1) {
          codeParameterFormat.append("(").append(sdfDateFormat.format(new Date())).append(")");
          po.setTempCodeSetDate("(" + sdfDateFormat.format(new Date()) + ")");
          continue;
        } 
        codeParameterFormat.append(sdfDateFormat.format(new Date()));
        po.setTempCodeSetDate(sdfDateFormat.format(new Date()));
      } 
    } 
    return codeParameterFormat.toString();
  }
}
