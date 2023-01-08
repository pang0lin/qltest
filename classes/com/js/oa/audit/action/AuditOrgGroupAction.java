package com.js.oa.audit.action;

import com.js.oa.audit.po.AuditOrgGroup;
import com.js.oa.audit.service.AuditOrgGroupBD;
import com.js.oa.message.service.MsManageBD;
import com.js.oa.security.log.service.LogBD;
import com.js.util.util.ParameterFilter;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AuditOrgGroupAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) throws NumberFormatException, Exception {
    String action = request.getParameter("action");
    AuditOrgGroupActionForm auditOrgGroupActionForm = (AuditOrgGroupActionForm)actionForm;
    HttpSession session = request.getSession(true);
    String tag = "";
    String userId = session.getAttribute("userId").toString();
    String domainId = session.getAttribute("domainId").toString();
    if (action.equals("forshenji")) {
      tag = "shenji";
      String id = request.getParameter("id");
      String flag = request.getParameter("flag");
      MsManageBD msBD = new MsManageBD();
      List<Object[]> msList = null;
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
      sql = "select po.auditOrgGroupId,po.groupId from com.js.oa.audit.po.AuditOrgGroup po where po.auditLogId=" + id;
      msList = msBD.getListByYourSQL(sql);
      if (msList != null && msList.size() != 0)
        for (int i = 0; i < msList.size(); i++) {
          Object[] obj = msList.get(i);
          id = obj[0].toString();
        }  
      AuditOrgGroupBD auditOrgGroupBD = new AuditOrgGroupBD();
      AuditOrgGroup auditOrgGroup = auditOrgGroupBD.loadAuditOrgGroup(Long.valueOf(id).longValue());
      if (auditOrgGroup != null) {
        request.setAttribute("auditOrgGroupId", id);
        request.setAttribute("groupName", auditOrgGroup.getGroupName());
        if (auditOrgGroup.getGroupUserString() == null) {
          request.setAttribute("groupUserString", "");
        } else {
          request.setAttribute("groupUserString", auditOrgGroup.getGroupUserString());
        } 
        request.setAttribute("groupUserName", auditOrgGroup.getGroupUserNames());
        request.setAttribute("createdOrg", auditOrgGroup.getCreatedorg());
        request.setAttribute("groupType", auditOrgGroup.getGroupType());
        request.setAttribute("domainId", auditOrgGroup.getDomainId());
        request.setAttribute("groupId", auditOrgGroup.getGroupId());
        request.setAttribute("pager.offset", request.getParameter("pager.offset"));
        request.setAttribute("rangeName", auditOrgGroup.getRangename());
        request.setAttribute("rangeEmp", auditOrgGroup.getRangeemp());
        request.setAttribute("rangeOrg", auditOrgGroup.getRangeorg());
        request.setAttribute("rangeGroup", auditOrgGroup.getRangegroup());
        request.setAttribute("groupOrder", auditOrgGroup.getGroupOrder());
        request.setAttribute("operationType", auditOrgGroup.getOperationType());
        request.setAttribute("auditLogId", auditOrgGroup.getAuditLogId());
        auditOrgGroupActionForm.setGroupName(auditOrgGroup.getGroupName());
        String operationTypeCh = auditOrgGroup.getOperationType();
        if ("insert".equals(operationTypeCh))
          operationTypeCh = "新增"; 
        if ("update".equals(operationTypeCh))
          operationTypeCh = "修改"; 
        if ("delete".equals(operationTypeCh))
          operationTypeCh = "删除"; 
        request.setAttribute("operationTypeCh", operationTypeCh);
        String userName = session.getAttribute("userName").toString();
        String orgName = session.getAttribute("orgName").toString();
        LogBD logBD = new LogBD();
        Date date = new Date();
        logBD.log(userId, userName, orgName, "system_group", "审计管理", date, date, "2", auditOrgGroup.getGroupName(), session.getAttribute("userIP").toString(), domainId);
      } 
    } 
    if (action.equals("shenji")) {
      String auditOrgGroupId = request.getParameter("auditOrgGroupId");
      String checked = request.getParameter("checked");
      String auditLogId = request.getParameter("auditLogId");
      String operationType = request.getParameter("operationType");
      AuditOrgGroupBD auditOrgGroupBD = new AuditOrgGroupBD();
      auditOrgGroupBD.auditOrgGroup(auditOrgGroupId, auditLogId, checked, operationType, request);
      tag = "close";
      request.setAttribute("done", "done");
      String flag = request.getParameter("flag");
      if (flag != null && "fromRemind".equals(flag))
        flag = "fromRemind2"; 
      request.setAttribute("flag", flag);
    } 
    if ("checkGroupName".equals(action)) {
      String reStr = "0";
      PrintWriter out = null;
      try {
        httpServletResponse.setContentType("text/xml;charset=GBK");
        out = httpServletResponse.getWriter();
        String groupName = request.getParameter("groupName");
        String groupType = request.getParameter("groupType");
        long groupId = Long.parseLong(request.getParameter("groupId"));
        if (!ParameterFilter.checkParameter(groupName)) {
          reStr = "1";
        } else {
          String sqlAjax = "";
          if ("0".equals(groupType))
            sqlAjax = "select vo.groupId,vo.groupName from com.js.system.vo.groupmanager.GroupVO vo where vo.domainId=<%=domainId%> and vo.groupType=0 and vo.groupName='" + 
              groupName + "' and vo.groupId!=" + groupId; 
          MsManageBD msBD = new MsManageBD();
          List reList = msBD.getListByYourSQL(sqlAjax);
          if (reList != null)
            reStr = String.valueOf(reList.size()); 
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } 
      out.print(reStr);
      out.close();
      return null;
    } 
    return actionMapping.findForward(tag);
  }
}
