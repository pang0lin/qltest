package com.js.oa.personalwork.netemp.action;

import com.js.oa.personalwork.netemp.po.NetEmpPO;
import com.js.oa.personalwork.netemp.service.NetEmpBD;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class NetEmpAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) throws NumberFormatException, Exception {
    String flag = request.getParameter("flag");
    if ("load".equals(flag)) {
      load(request);
    } else if ("update".equals(flag)) {
      update(request, actionForm);
    } else if ("moreRelationEmp".equals(flag)) {
      String curUserId = request.getSession(true).getAttribute("userId").toString();
      NetEmpBD netEmpBD = new NetEmpBD();
      Map map = netEmpBD.getAllRelationEmp(curUserId);
      request.setAttribute("relationEmp", map);
      return actionMapping.findForward("moreRelationEmp");
    } 
    return actionMapping.findForward("load");
  }
  
  private void update(HttpServletRequest request, ActionForm actionForm) throws NumberFormatException, Exception {
    NetEmpBD netEmpBD = new NetEmpBD();
    NetEmpPO netEmpPO = new NetEmpPO();
    String curUserId = request.getSession(true).getAttribute("userId").toString();
    netEmpPO.setUserId(Long.valueOf(curUserId).longValue());
    String id = request.getParameter("id");
    if (!"-1".equals(id))
      netEmpPO.setId(Long.valueOf(id).longValue()); 
    String superiorsName = request.getParameter("superiorsName");
    netEmpPO.setSuperiorsName(superiorsName);
    String superiorsId = request.getParameter("superiorsId");
    netEmpPO.setSuperiorsId(superiorsId);
    String underlingName = request.getParameter("underlingName");
    netEmpPO.setUnderlingName(underlingName);
    String underlingId = request.getParameter("underlingId");
    netEmpPO.setUnderlingId(underlingId);
    String netEmpName = request.getParameter("netEmpName");
    netEmpPO.setNetEmpName(netEmpName);
    String netEmpId = request.getParameter("netEmpId");
    netEmpPO.setNetEmpId(netEmpId);
    netEmpPO.setEventRelationEmpId(request.getParameter("eventRelationEmpId"));
    netEmpPO.setEventRelationEmpName(request.getParameter("eventRelationEmpName"));
    netEmpBD.update(netEmpPO);
    load(request);
  }
  
  private void load(HttpServletRequest request) throws NumberFormatException, Exception {
    String curUserId = request.getSession(true).getAttribute("userId").toString();
    NetEmpPO netEmpPO = new NetEmpPO();
    NetEmpBD netEmpBD = new NetEmpBD();
    netEmpPO = netEmpBD.load(Long.valueOf(curUserId).longValue());
    request.setAttribute("netEmpPO", netEmpPO);
  }
}
