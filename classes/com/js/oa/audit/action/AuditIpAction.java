package com.js.oa.audit.action;

import com.js.oa.audit.po.AuditIpPO;
import com.js.oa.audit.service.AuditIpBD;
import com.js.oa.message.service.MsManageBD;
import com.js.oa.security.log.service.LogBD;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AuditIpAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) throws NumberFormatException, Exception {
    String action = request.getParameter("action");
    AuditIpActionForm auditIpActionForm = (AuditIpActionForm)actionForm;
    HttpSession session = request.getSession(true);
    String tag = "";
    String userId = session.getAttribute("userId").toString();
    String domainId = session.getAttribute("domainId").toString();
    if (action.equals("forshenji")) {
      String id = request.getParameter("id");
      String flag = request.getParameter("flag");
      MsManageBD msBD = new MsManageBD();
      List<Object[]> msList = null;
      tag = "shenji";
      String sql = "select po.logId,po.ischecked from com.js.oa.audit.po.AuditLog po where po.logId=" + id;
      msList = msBD.getListByYourSQL(sql);
      if (msList != null && msList.size() != 0)
        for (int i = 0; i < msList.size(); i++) {
          Object[] obj = msList.get(i);
          String ischecked = obj[1].toString();
          if ("1".equals(ischecked))
            tag = "shenjiDetail"; 
        }  
      if ("detail".equals(flag))
        tag = "shenjiDetail"; 
      request.setAttribute("flag", flag);
      sql = "select po.auditIpId,po.ipId from com.js.oa.audit.po.AuditIpPO po  where po.auditLogId=" + id;
      msList = msBD.getListByYourSQL(sql);
      if (msList != null && msList.size() != 0)
        for (int i = 0; i < msList.size(); i++) {
          Object[] obj = msList.get(i);
          id = obj[0].toString();
        }  
      AuditIpBD auditIpBD = new AuditIpBD();
      AuditIpPO auditIpPO = auditIpBD.loadAuditIp(Long.valueOf(id).longValue());
      if (auditIpPO != null) {
        auditIpActionForm.setAuditIpId(auditIpPO.getAuditIpId());
        auditIpActionForm.setAuditLogId(auditIpPO.getAuditLogId());
        auditIpActionForm.setConfirmEmp(auditIpPO.getConfirmEmp());
        auditIpActionForm.setConfirmOrg(auditIpPO.getConfirmOrg());
        auditIpActionForm.setDomainId(auditIpPO.getDomainId());
        auditIpActionForm.setIpAddressBegin(formatIp(auditIpPO.getIpAddressBegin()));
        auditIpActionForm.setIpAddressEnd(formatIp(auditIpPO.getIpAddressEnd()));
        auditIpActionForm.setIpApplyTime(auditIpPO.getIpApplyTime());
        auditIpActionForm.setIpId(auditIpPO.getIpId());
        auditIpActionForm.setIpIsOpen(auditIpPO.getIpIsOpen());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String ipOpenBeginTime = format.format(auditIpPO.getIpOpenBeginTime());
        String ipOpenEndTime = format.format(auditIpPO.getIpOpenEndTime());
        auditIpActionForm.setIpOpenBeginTime(auditIpPO.getIpOpenBeginTime());
        auditIpActionForm.setIpOpenEndTime(auditIpPO.getIpOpenEndTime());
        auditIpActionForm.setIpProposer(auditIpPO.getIpProposer());
        auditIpActionForm.setOperationType(auditIpPO.getOperationType());
        String operationTypeCh = auditIpPO.getOperationType();
        request.setAttribute("ipOpenBeginTime", ipOpenBeginTime);
        request.setAttribute("ipOpenEndTime", ipOpenEndTime);
        request.setAttribute("operationType", auditIpPO.getOperationType());
        if ("insert".equals(operationTypeCh))
          operationTypeCh = "新增"; 
        if ("update".equals(operationTypeCh))
          operationTypeCh = "修改"; 
        if ("delete".equals(operationTypeCh))
          operationTypeCh = "删除"; 
        if ("pass".equals(operationTypeCh))
          operationTypeCh = "开通"; 
        request.setAttribute("operationTypeCh", operationTypeCh);
        String userName = session.getAttribute("userName").toString();
        String orgName = session.getAttribute("orgName").toString();
        LogBD logBD = new LogBD();
        Date date = new Date();
        logBD.log(userId, userName, orgName, "system_IP", "IP审计管理", date, date, "2", String.valueOf(auditIpPO.getIpAddressBegin()) + "-" + auditIpPO.getIpAddressEnd(), session.getAttribute("userIP").toString(), domainId);
      } 
    } 
    if (action.equals("shenji")) {
      String auditIpId = request.getParameter("auditIpId");
      String checked = request.getParameter("checked");
      String auditLogId = request.getParameter("auditLogId");
      String operationType = request.getParameter("operationType");
      AuditIpBD auditIpBD = new AuditIpBD();
      auditIpBD.auditIp(auditIpId, auditLogId, checked, operationType, request);
      tag = "close";
      request.setAttribute("done", "done");
      String flag = request.getParameter("flag");
      if (flag != null && "fromRemind".equals(flag))
        flag = "fromRemind2"; 
      request.setAttribute("flag", flag);
    } 
    return actionMapping.findForward(tag);
  }
  
  public String formatIp(String ip) {
    Pattern p1 = Pattern.compile("(\\d+).(\\d+).(\\d+).(\\d+)");
    Matcher m1 = p1.matcher(ip);
    m1.find();
    String ip1 = m1.group(1);
    String ip2 = m1.group(2);
    String ip3 = m1.group(3);
    String ip4 = m1.group(4);
    if ("".equals(ip1)) {
      ip1 = "0";
      ip2 = "0";
      ip3 = "0";
      ip4 = "0";
    } 
    return String.valueOf(Integer.parseInt(ip1)) + "." + Integer.parseInt(ip2) + "." + Integer.parseInt(ip3) + "." + Integer.parseInt(ip4);
  }
}
