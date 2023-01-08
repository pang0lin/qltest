package com.js.oa.security.ip.action;

import com.js.oa.security.ip.po.IPPO;
import com.js.oa.security.ip.service.IPBD;
import com.js.oa.security.log.service.LogBD;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.FillBean;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ipAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    HttpSession session = httpServletRequest.getSession(true);
    String action = httpServletRequest.getParameter("action");
    ipActionForm ipActionForm = (ipActionForm)actionForm;
    String saveType = httpServletRequest.getParameter("saveType");
    IPBD ipBD = new IPBD();
    if ("add".equals(action)) {
      IPPO ipPO = (IPPO)FillBean.transformOneToOne(ipActionForm, IPPO.class);
      Date ipApplyTime = new Date();
      ipPO.setIpApplyTime(ipApplyTime);
      Date ipOpenBeginTime = new Date(httpServletRequest.getParameter(
            "ipOpenBeginTime"));
      Date ipOpenEndTime = new Date(httpServletRequest.getParameter(
            "ipOpenEndTime"));
      ipPO.setIpOpenBeginTime(ipOpenBeginTime);
      ipPO.setIpOpenEndTime(ipOpenEndTime);
      ipPO.setConfirmEmp(session.getAttribute("userId").toString());
      ipPO.setConfirmOrg(session.getAttribute("orgId").toString());
      ipPO.setDomainId(session.getAttribute("domainId").toString());
      boolean result = false;
      if (1 != SystemCommon.getAudit() || (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())) {
        result = ipBD.add(ipPO);
        if (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())
          ipBD.autoAudit(ipPO, "insert", new Long(0L), httpServletRequest); 
      } 
      if (1 == SystemCommon.getAudit() && SystemCommon.getAutoAudit() == 0) {
        result = true;
        ipBD.audit(ipPO, "insert", new Long(0L), httpServletRequest);
        httpServletRequest.setAttribute("flag", "foraudit");
      } 
      String userId = session.getAttribute("userId").toString();
      String userName = session.getAttribute("userName").toString();
      String userOrgName = session.getAttribute("orgName").toString();
      String domainId = session.getAttribute("domainId").toString();
      LogBD logBD = new LogBD();
      Date date = new Date();
      String ipb = ipPO.getIpAddressBegin();
      String ipe = ipPO.getIpAddressEnd();
      if (!ipb.equals(ipe))
        ipb = String.valueOf(ipb) + "至" + ipe; 
      logBD.log(userId, userName, userOrgName, "system_ip", "系统管理", 
          date, date, "1", ipb, session.getAttribute("userIP").toString(), domainId);
      if (!result)
        return actionMapping.findForward("failure"); 
      if ("saveAndContinue".equals(saveType)) {
        ipActionForm.reset(actionMapping, httpServletRequest);
        return actionMapping.findForward("saveAndContinue");
      } 
      if ("saveAndExit".equals(saveType))
        return actionMapping.findForward("saveAndExit"); 
    } else if ("pass".equals(action)) {
      String checkboxIDS = httpServletRequest.getParameter("ids");
      String result = null;
      if (1 != SystemCommon.getAudit() || (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())) {
        result = ipBD.pass(checkboxIDS);
        if (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit()) {
          IPPO ipPO = new IPPO();
          String[] idArr = checkboxIDS.split(",");
          for (int i = 0; i < idArr.length; i++)
            ipBD.autoAudit(ipPO, "pass", Long.valueOf(idArr[i]), httpServletRequest); 
        } 
      } 
      if (1 == SystemCommon.getAudit() && SystemCommon.getAutoAudit() == 0) {
        result = checkboxIDS;
        IPPO ipPO = new IPPO();
        String[] idArr = checkboxIDS.split(",");
        for (int i = 0; i < idArr.length; i++)
          ipBD.audit(ipPO, "pass", Long.valueOf(idArr[i]), httpServletRequest); 
        httpServletRequest.setAttribute("flag", "foraudit");
      } 
      action = "view";
      String userId = session.getAttribute("userId").toString();
      String userName = session.getAttribute("userName").toString();
      String userOrgName = session.getAttribute("orgName").toString();
      String domainId = session.getAttribute("domainId").toString();
      LogBD logBD = new LogBD();
      Date date = new Date();
      logBD.log(userId, userName, userOrgName, "system_ip", "系统管理", 
          date, date, "2", result, session.getAttribute("userIP").toString(), domainId);
    } 
    if ("view".equals(action)) {
      list(httpServletRequest);
      return actionMapping.findForward("goto_ipList");
    } 
    if ("del".equals(action)) {
      String id = httpServletRequest.getParameter("id");
      if (id != null && !"".equals(id)) {
        String result = "";
        if (1 != SystemCommon.getAudit() || (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())) {
          if (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit()) {
            IPPO ipPO = new IPPO();
            ipBD.autoAudit(ipPO, "delete", Long.valueOf(id), httpServletRequest);
          } 
          result = ipBD.delete(id);
        } 
        if (1 == SystemCommon.getAudit() && SystemCommon.getAutoAudit() == 0) {
          result = "1";
          IPPO ipPO = new IPPO();
          ipBD.audit(ipPO, "delete", Long.valueOf(id), httpServletRequest);
          httpServletRequest.setAttribute("flag", "foraudit");
        } 
        String userId = session.getAttribute("userId").toString();
        String userName = session.getAttribute("userName").toString();
        String userOrgName = session.getAttribute("orgName").toString();
        String domainId = session.getAttribute("domainId").toString();
        LogBD logBD = new LogBD();
        Date date = new Date();
        logBD.log(userId, userName, userOrgName, "sytem_ip", "系统管理", 
            date, date, "3", result, session.getAttribute("userIP").toString(), domainId);
        if (!"".equals(result)) {
          list(httpServletRequest);
          return actionMapping.findForward("goto_ipList");
        } 
        return actionMapping.findForward("failure");
      } 
    } 
    if ("update".equals(action)) {
      String id = httpServletRequest.getParameter("id");
      List<Object[]> list = ipBD.selectSingle(id);
      Object[] obj = list.get(0);
      ipActionForm.setIpAddressEnd((String)obj[0]);
      ipActionForm.setIpAddressBegin((String)obj[1]);
      ipActionForm.setIpIsOpen((byte)Integer.parseInt(String.valueOf(obj[
                4])));
      ipActionForm.setIpProposer((String)obj[5]);
      httpServletRequest.setAttribute("ipOpenBeginTime", obj[2]);
      httpServletRequest.setAttribute("ipOpenEndTime", obj[3]);
      httpServletRequest.setAttribute("id", id);
      return actionMapping.findForward("goto_ipUpdate");
    } 
    if ("modify".equals(action)) {
      long id = Long.parseLong(httpServletRequest.getParameter(
            "modify_id"));
      IPPO ipPO = (IPPO)FillBean.transformOneToOne(ipActionForm, IPPO.class);
      ipPO.setId(id);
      Date ipApplyTime = new Date();
      ipPO.setIpApplyTime(ipApplyTime);
      Date ipOpenBeginTime = new Date(httpServletRequest.getParameter(
            "ipOpenBeginTime"));
      Date ipOpenEndTime = new Date(httpServletRequest.getParameter(
            "ipOpenEndTime"));
      ipPO.setIpOpenBeginTime(ipOpenBeginTime);
      ipPO.setIpOpenEndTime(ipOpenEndTime);
      ipPO.setConfirmEmp(session.getAttribute("userId").toString());
      ipPO.setConfirmOrg(session.getAttribute("orgId").toString());
      String ipb = ipPO.getIpAddressBegin();
      String ipe = ipPO.getIpAddressEnd();
      if (!ipb.equals(ipe))
        ipb = String.valueOf(ipb) + "至" + ipe; 
      boolean result = false;
      if (1 != SystemCommon.getAudit() || (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())) {
        result = ipBD.modify(ipPO);
        if (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())
          ipBD.autoAudit(ipPO, "update", Long.valueOf(id), httpServletRequest); 
      } 
      if (1 == SystemCommon.getAudit() && SystemCommon.getAutoAudit() == 0) {
        result = true;
        ipBD.audit(ipPO, "update", Long.valueOf(id), httpServletRequest);
        httpServletRequest.setAttribute("flag", "foraudit");
      } 
      String userId = session.getAttribute("userId").toString();
      String userName = session.getAttribute("userName").toString();
      String userOrgName = session.getAttribute("orgName").toString();
      String domainId = session.getAttribute("domainId").toString();
      LogBD logBD = new LogBD();
      Date date = new Date();
      logBD.log(userId, userName, userOrgName, "system_ip", "系统管理", 
          date, date, "2", ipb, session.getAttribute("userIP").toString(), domainId);
      if (!result)
        return actionMapping.findForward("failure"); 
      if ("updateAndExit".equals(saveType))
        return actionMapping.findForward("updateAndExit"); 
    } 
    if ("delBatch".equals(action)) {
      String checkboxIDS = httpServletRequest.getParameter("ids");
      String result = "";
      if (1 != SystemCommon.getAudit() || (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())) {
        if (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit()) {
          IPPO ipPO = new IPPO();
          String[] idArr = checkboxIDS.split(",");
          for (int i = 0; i < idArr.length; i++)
            ipBD.autoAudit(ipPO, "delete", Long.valueOf(idArr[i]), httpServletRequest); 
        } 
        result = ipBD.delBatch(checkboxIDS);
      } 
      if (1 == SystemCommon.getAudit() && SystemCommon.getAutoAudit() == 0) {
        result = checkboxIDS;
        IPPO ipPO = new IPPO();
        String[] idArr = checkboxIDS.split(",");
        for (int i = 0; i < idArr.length; i++)
          ipBD.audit(ipPO, "delete", Long.valueOf(idArr[i]), httpServletRequest); 
        httpServletRequest.setAttribute("flag", "foraudit");
      } 
      String userId = session.getAttribute("userId").toString();
      String userName = session.getAttribute("userName").toString();
      String userOrgName = session.getAttribute("orgName").toString();
      String domainId = session.getAttribute("domainId").toString();
      LogBD logBD = new LogBD();
      Date date = new Date();
      logBD.log(userId, userName, userOrgName, "system_ip", "系统管理", 
          date, date, "3", result, session.getAttribute("userIP").toString(), domainId);
      if ("".equals(result))
        return actionMapping.findForward("failure"); 
      list(httpServletRequest);
      ipActionForm.reset(actionMapping, httpServletRequest);
      return actionMapping.findForward("goto_ipList");
    } 
    if ("delAll".equals(action)) {
      boolean result = ipBD.delAll();
      String userId = session.getAttribute("userId").toString();
      String userName = session.getAttribute("userName").toString();
      String userOrgName = session.getAttribute("orgName").toString();
      String domainId = session.getAttribute("domainId").toString();
      LogBD logBD = new LogBD();
      Date date = new Date();
      logBD.log(userId, userName, userOrgName, "system_ip", "系统管理", 
          date, date, "3", "全部IP设置", session.getAttribute("userIP").toString(), domainId);
      if (!result)
        return actionMapping.findForward("failute"); 
      list(httpServletRequest);
      ActionForward forward = new ActionForward();
      forward.setPath("/ipAction.do?action=view");
      return forward;
    } 
    throw new UnsupportedOperationException(
        "Method perform() not yet implemented.");
  }
  
  public void list(HttpServletRequest httpServletRequest) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    HttpSession session = httpServletRequest.getSession(true);
    String where = " where po.domainId=" + session.getAttribute("domainId");
    if (!"1".equals(session.getAttribute("sysManager").toString()))
      where = String.valueOf(where) + " and po.confirmEmp=" + session.getAttribute("userId"); 
    where = String.valueOf(where) + " order by po.id desc";
    Page page = new Page(" po.id,po.ipAddressBegin,po.ipAddressEnd,po.ipOpenBeginTime,po.ipOpenEndTime,po.ipIsOpen,po.ipProposer,po.ipApplyTime", 
        " com.js.oa.security.ip.po.IPPO po ", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    int recordCount = page.getRecordCount();
    if (offset >= recordCount) {
      offset = (recordCount - pageSize) / pageSize;
      currentPage = offset + 1;
      offset *= pageSize;
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      recordCount = page.getRecordCount();
      httpServletRequest.setAttribute("pager.offset", 
          String.valueOf(offset));
      httpServletRequest.setAttribute("pager.realCurrent", 
          String.valueOf(currentPage));
    } 
    httpServletRequest.setAttribute("ipList", list);
    httpServletRequest.setAttribute("recordCount", String.valueOf(recordCount));
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
}
