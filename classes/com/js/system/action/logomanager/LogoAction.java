package com.js.system.action.logomanager;

import com.js.system.service.logomanager.LogoBD;
import com.js.system.vo.logomanager.LogoVO;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class LogoAction extends DispatchAction {
  public ActionForward goLogoShow(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    LogoBD logoBD = new LogoBD();
    request.setAttribute("showList", logoBD.getLogoList());
    return mapping.findForward("goLogoShow");
  }
  
  public ActionForward goBranchLogo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    LogoBD logoBD = new LogoBD();
    String orgId = (request.getParameter("orgId") != null) ? request.getParameter("orgId") : session.getAttribute("corpId").toString();
    request.setAttribute("showBranchList", logoBD.getBranchLogo(orgId));
    return mapping.findForward("goBranchLogo");
  }
  
  public ActionForward allLogoShow(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    LogoBD logoBD = new LogoBD();
    request.setAttribute("showAllogo", logoBD.getAllLogo());
    return mapping.findForward("allLogoShow");
  }
  
  public ActionForward saveLogo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String isDisplayCompanyName = request.getParameter("isDisplayCompanyName");
    String isDisplayLogo = request.getParameter("isDisplayLogo");
    if (isDisplayCompanyName == null) {
      isDisplayCompanyName = "1";
    } else {
      isDisplayCompanyName = "0";
    } 
    if (isDisplayLogo == null) {
      isDisplayLogo = "1";
    } else {
      isDisplayLogo = "0";
    } 
    String companyColor = request.getParameter("companyColor");
    String companyName = URLDecoder.decode(request.getParameter("companyName"), "utf-8");
    LogoVO logoVO = (new LogoBD()).loadLogo(request.getParameter("logoId"));
    logoVO.setIsdisplayCompanyName(isDisplayCompanyName);
    logoVO.setIsdisplayLogo(isDisplayLogo);
    logoVO.setCompanyColor(companyColor);
    logoVO.setCompanyName(companyName);
    (new LogoBD()).modifyLogo(logoVO);
    LogoBD logoBD = new LogoBD();
    String orgId = (request.getParameter("orgId") == null) ? session.getAttribute("corpId").toString() : request.getParameter("orgId");
    if ("0".equals(orgId))
      return goLogoShow(mapping, form, request, response); 
    request.setAttribute("showBranchList", logoBD.getBranchLogo(orgId));
    if (request.getParameter("flagType") == null)
      return mapping.findForward("goBranchLogo"); 
    return allLogoShow(mapping, form, request, response);
  }
  
  public ActionForward updateLogoComplete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    LogoBD logoBD = new LogoBD();
    request.setAttribute("showList", logoBD.getLogoList());
    return mapping.findForward("updateLogoComplete");
  }
  
  public ActionForward updateLogoFailed(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    LogoBD logoBD = new LogoBD();
    request.setAttribute("showList", logoBD.getLogoList());
    return mapping.findForward("updateLogoFailed");
  }
  
  public ActionForward reSumeLogo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String branch = (request.getParameter("branch") == null) ? "0" : "1";
    String type = (request.getParameter("type") == null) ? "0" : request.getParameter("type");
    String logoId = request.getParameter("logoId");
    LogoBD logoBD = new LogoBD();
    LogoVO logoVO = logoBD.loadLogo(logoId);
    switch (Integer.parseInt(type)) {
      case 0:
        logoVO.setBakString1("登录页logo");
        logoVO.setLogoName("index.gif");
        logoVO.setLogoPath("/images/index.gif");
        logoVO.setCompanyColor("#ff0000");
        logoVO.setCompanyName("");
        logoVO.setIsdisplayCompanyName("0");
        logoVO.setIsdisplayLogo("1");
        logoVO.setOrgId("0");
        logoVO.setLogoType("0");
        break;
      case 1:
        logoVO.setBakString1("首页左上角logo");
        logoVO.setLogoName("i think.gif");
        logoVO.setLogoPath("/imges/i think.gif");
        if (request.getParameter("orgId") != null) {
          logoVO.setOrgId(logoVO.getOrgId());
          break;
        } 
        logoVO.setOrgId("0");
        break;
    } 
    logoBD.modifyLogo(logoVO);
    if ("0".equals(branch))
      return goLogoShow(mapping, form, request, response); 
    request.setAttribute("showBranchList", logoBD.getBranchLogo(logoVO.getOrgId()));
    return mapping.findForward("goBranchLogo");
  }
}
