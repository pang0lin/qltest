package com.js.oa.hr.kq.szgt.action;

import com.js.oa.hr.kq.szgt.bean.SKqParaSetBean;
import com.js.util.config.SystemCommon;
import com.js.util.page.sql.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SKqParaSetAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws NumberFormatException, Exception {
    String tag = "list";
    String action = (httpServletRequest.getParameter("action") == null) ? 
      "list" : httpServletRequest.getParameter("action");
    HttpSession session = httpServletRequest.getSession();
    Long domainId = (session.getAttribute("domainId") == null) ? 
      Long.valueOf("0") : 
      Long.valueOf(session.getAttribute("domainId").toString());
    if (action.equals("list")) {
      list(httpServletRequest);
    } else if (action.equals("add")) {
      tag = "add";
    } else if (action.equals("saveclose")) {
      String paraTitle = httpServletRequest.getParameter("paratitle");
      String annualstart = httpServletRequest.getParameter("annualstart");
      String annualperiod = httpServletRequest.getParameter("annualperiod");
      String sickannual = (httpServletRequest.getParameter("sickannual") == null) ? "0" : httpServletRequest.getParameter("sickannual");
      String leaveannual = (httpServletRequest.getParameter("leaveannual") == null) ? "0" : httpServletRequest.getParameter("leaveannual");
      String leaveinlieuannual = (httpServletRequest.getParameter("leaveinlieuannual") == null) ? "0" : httpServletRequest.getParameter("leaveinlieuannual");
      String userange = httpServletRequest.getParameter("userange");
      String userangeId = httpServletRequest.getParameter("userangeId");
      String corpId = session.getAttribute("corpId").toString();
      SKqParaSetBean bean = new SKqParaSetBean();
      bean.savePara(paraTitle, annualstart, annualperiod, sickannual, leaveannual, leaveinlieuannual, userange, userangeId, corpId);
      httpServletRequest.setAttribute("saveSuccess", "1");
      tag = "add";
    } else if (action.equals("del")) {
      SKqParaSetBean bean = new SKqParaSetBean();
      bean.deletePara(httpServletRequest.getParameter("id"));
      list(httpServletRequest);
      tag = "list";
    } else if (action.equals("update")) {
      String[] paras = (new SKqParaSetBean()).getParas(httpServletRequest.getParameter("id"));
      httpServletRequest.setAttribute("paratitle", paras[0]);
      httpServletRequest.setAttribute("annualstart", paras[1]);
      httpServletRequest.setAttribute("annualperiod", paras[2]);
      httpServletRequest.setAttribute("sickannual", paras[3]);
      httpServletRequest.setAttribute("leaveannual", paras[4]);
      httpServletRequest.setAttribute("leaveinlieuannual", paras[5]);
      httpServletRequest.setAttribute("userange", paras[6]);
      httpServletRequest.setAttribute("userangeId", paras[7]);
      tag = "update";
    } else if (action.equals("updateclose")) {
      String paraId = httpServletRequest.getParameter("paraId");
      String paraTitle = httpServletRequest.getParameter("paratitle");
      String annualstart = httpServletRequest.getParameter("annualstart");
      String annualperiod = httpServletRequest.getParameter("annualperiod");
      String sickannual = (httpServletRequest.getParameter("sickannual") == null) ? "0" : httpServletRequest.getParameter("sickannual");
      String leaveannual = (httpServletRequest.getParameter("leaveannual") == null) ? "0" : httpServletRequest.getParameter("leaveannual");
      String leaveinlieuannual = (httpServletRequest.getParameter("leaveinlieuannual") == null) ? "0" : httpServletRequest.getParameter("leaveinlieuannual");
      String userange = httpServletRequest.getParameter("userange");
      String userangeId = httpServletRequest.getParameter("userangeId");
      SKqParaSetBean bean = new SKqParaSetBean();
      bean.updatePara(paraTitle, annualstart, annualperiod, sickannual, leaveannual, leaveinlieuannual, userange, userangeId, paraId);
      httpServletRequest.setAttribute("saveSuccess", "1");
      tag = "add";
    } 
    return actionMapping.findForward(tag);
  }
  
  private void list(HttpServletRequest httpServletRequest) throws NumberFormatException, Exception {
    Long domainId = (httpServletRequest.getSession(true).getAttribute(
        "domainId") == null) ? 
      Long.valueOf("0") : 
      Long.valueOf(httpServletRequest.getSession(true)
        .getAttribute("domainId").toString());
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String para = "para_id,paratitle,userange";
    String from = "skq_para";
    String where = "";
    if (SystemCommon.getMultiDepart() == 1)
      where = "where corp_id=" + httpServletRequest.getSession(true).getAttribute("corpId").toString(); 
    Page page = new Page(para, from, where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("list", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
}
