package com.js.oa.hr.kq.action;

import com.js.oa.hr.kq.po.KqOffsetPO;
import com.js.oa.hr.kq.po.KqSigntimePO;
import com.js.oa.hr.kq.service.KqOffsetBD;
import com.js.oa.hr.kq.service.KqSigntimeBD;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class KqSigntimeAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws NumberFormatException, Exception {
    String tag = "load";
    String action = (httpServletRequest.getParameter("action") == null) ? 
      "load" : httpServletRequest.getParameter("action");
    if (action.equals("load")) {
      load(httpServletRequest);
    } else if (action.equals("save")) {
      Long domainId = (httpServletRequest.getSession().getAttribute("domainId") == null) ? 
        Long.valueOf("0") : 
        Long.valueOf(httpServletRequest.getSession().getAttribute("domainId").toString());
      KqSigntimeBD kqSigntimeBD = new KqSigntimeBD();
      String signid = httpServletRequest.getParameter("signid");
      String offset = httpServletRequest.getParameter("offset");
      kqSigntimeBD.set((new Long(signid)).longValue(), (new Integer(offset)).intValue(), domainId.longValue());
      KqOffsetBD kqOffsetBD = new KqOffsetBD();
      String offsetid = httpServletRequest.getParameter("offsetid");
      String offsettime = httpServletRequest.getParameter("offsettime");
      kqOffsetBD.set((new Long(offsetid)).longValue(), (new Integer(offsettime)).intValue(), domainId.longValue());
      load(httpServletRequest);
    } 
    return actionMapping.findForward("set");
  }
  
  private void load(HttpServletRequest request) throws Exception {
    Long domainId = (request.getSession().getAttribute("domainId") == null) ? 
      Long.valueOf("0") : 
      Long.valueOf(request.getSession().getAttribute("domainId").toString());
    KqSigntimeBD kqSigntimeBD = new KqSigntimeBD();
    KqSigntimePO kqSigntimePO = new KqSigntimePO();
    kqSigntimePO = kqSigntimeBD.load(domainId.longValue());
    request.setAttribute("kqSigntimePO", kqSigntimePO);
    KqOffsetBD kqOffsetBD = new KqOffsetBD();
    KqOffsetPO kqOffsetPO = new KqOffsetPO();
    kqOffsetPO = kqOffsetBD.load(domainId.longValue());
    request.setAttribute("kqOffsetPO", kqOffsetPO);
  }
}
