package com.js.oa.info.infomanager.action;

import com.js.oa.info.infomanager.service.InformationBD;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InfoBrowserNoteAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
    String queryType = httpServletRequest.getParameter("queryType");
    if ("group".equals(queryType)) {
      String infoId = httpServletRequest.getParameter("infoId");
      String groupId = httpServletRequest.getParameter("groupId");
      InformationBD informationBD1 = new InformationBD();
      List list1 = informationBD1.getBrowserInGroup(groupId, Long.valueOf(Long.parseLong(infoId)));
      List list2 = informationBD1.getNoBrowserInGroup(groupId, Long.valueOf(Long.parseLong(infoId)));
      httpServletRequest.setAttribute("browserlist", list1);
      httpServletRequest.setAttribute("nobrowserlist", list2);
      return actionMapping.findForward("browser");
    } 
    if ("users".equals(queryType)) {
      String infoId = httpServletRequest.getParameter("infoId");
      String userIds = httpServletRequest.getParameter("userIds");
      InformationBD informationBD1 = new InformationBD();
      List list1 = informationBD1.getBrowserInUsers(userIds, Long.valueOf(Long.parseLong(infoId)));
      List list2 = informationBD1.getNoBrowserInUsers(userIds, Long.valueOf(Long.parseLong(infoId)));
      httpServletRequest.setAttribute("browserlist", list1);
      httpServletRequest.setAttribute("nobrowserlist", list2);
      return actionMapping.findForward("browser");
    } 
    String orgId = httpServletRequest.getParameter("orgId");
    String infoid = httpServletRequest.getParameter("infoid");
    InformationBD informationBD = new InformationBD();
    List browserlist = informationBD.getBrowser1(orgId, Long.valueOf(Long.parseLong(infoid)));
    List nobrowserlist = informationBD.getNoBrowser1(orgId, Long.valueOf(Long.parseLong(infoid)));
    httpServletRequest.setAttribute("browserlist", browserlist);
    httpServletRequest.setAttribute("nobrowserlist", nobrowserlist);
    return actionMapping.findForward("browser");
  }
}
