package com.js.oa.personalwork.person.action;

import com.js.oa.netdisk.service.NetdiskBD;
import com.js.oa.personalwork.person.service.PersonRelationBD;
import com.js.oa.scheme.event.service.EventBD;
import com.js.oa.scheme.worklog.service.WorkLogBD;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class PersonRelationAction extends DispatchAction {
  public ActionForward relationList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String orgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    String empId = request.getParameter("empId");
    WorkLogBD worklogBD = new WorkLogBD();
    List underEmpList = worklogBD.getDownEmployeeList(userId);
    NetdiskBD netdiskBD = new NetdiskBD();
    String whereDoc = netdiskBD.getInfoReader(userId, orgId, orgIdString, "netdisk");
    EventBD eb = new EventBD();
    eb.setVolume(Integer.parseInt(empId));
    List eventList = eb.selectAllEvent(Long.valueOf(Long.parseLong(userId)), Integer.valueOf(-1), Long.valueOf(Long.parseLong(domainId)));
    PersonRelationBD prb = new PersonRelationBD();
    Map relationMap = prb.getRelationEmpMap(userId, empId, orgId, orgIdString, domainId, null, whereDoc);
    request.setAttribute("relationMap", relationMap);
    request.setAttribute("eventList", eventList);
    request.setAttribute("underEmpList", underEmpList);
    return mapping.findForward("MyCooperateList");
  }
}
