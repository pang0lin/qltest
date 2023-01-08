package com.js.wap.action;

import com.js.oa.info.infomanager.po.InformationPO;
import com.js.wap.service.ChannelBD;
import com.js.wap.util.WapUtil;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class ChannelAction extends DispatchAction {
  public ActionForward infoList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = (String)session.getAttribute("userId");
    String orgId = (String)session.getAttribute("orgId");
    String orgIdString = (String)session.getAttribute("orgIdString");
    String version = (String)session.getAttribute("wapVersion");
    ChannelBD bd = new ChannelBD();
    int beginIndex = Integer.parseInt((request.getParameter("beginIndex") == null) ? "0" : request.getParameter("beginIndex"));
    Map map = null;
    map = bd.getChannelList(userId, orgId, orgIdString, "0", beginIndex, WapUtil.LIMITED);
    int recordCount = ((Integer)map.get("RECORD_COUNT")).intValue();
    request.setAttribute("RECORD_COUNT", Integer.valueOf(recordCount));
    request.setAttribute("ChannelMap", map);
    request.setAttribute("beginIndex", Integer.valueOf(beginIndex));
    if (version.equals("COLOR"))
      return mapping.findForward("ChannelList"); 
    if (version.equals("3G"))
      return mapping.findForward("ChannelList_3g"); 
    return mapping.findForward("ChannelList_3g");
  }
  
  public ActionForward infoContent(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String version = (String)session.getAttribute("wapVersion");
    String infoID = request.getParameter("infoID");
    ChannelBD bd = new ChannelBD();
    Map map = WapUtil.getInfoById(infoID);
    try {
      InformationPO po = bd.getInfoPOByID(infoID);
      request.setAttribute("infoPO", po);
      request.setAttribute("contentMap", map);
    } catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("ERROR", "错误：" + e);
      if (version.equals("COLOR"))
        return mapping.findForward("error"); 
      if (version.equals("3G"))
        return mapping.findForward("error_3g"); 
      return mapping.findForward("error_3g");
    } 
    if (version.equals("COLOR"))
      return mapping.findForward("infoContent"); 
    if (version.equals("3G"))
      return mapping.findForward("infoContent_3g"); 
    return mapping.findForward("infoContent_3g");
  }
}
