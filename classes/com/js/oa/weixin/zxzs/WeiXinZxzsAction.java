package com.js.oa.weixin.zxzs;

import com.js.oa.info.infomanager.po.InformationPO;
import com.js.oa.info.infomanager.service.InformationBD;
import com.js.oa.weixin.common.service.WeiXinBD;
import com.js.wap.service.ChannelBD;
import com.js.wap.util.WapUtil;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WeiXinZxzsAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String action = request.getParameter("action");
    HttpSession session = request.getSession(true);
    String tag = "";
    if ("list".equals(action))
      tag = list(request, session); 
    if ("info".equals(action))
      tag = info(request); 
    if ("speak".equals(action))
      tag = speak(request); 
    return mapping.findForward(tag);
  }
  
  private String list(HttpServletRequest request, HttpSession session) throws Exception {
    String userId = session.getAttribute("userId").toString();
    String orgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    String keyword = (request.getParameter("keyword") != null) ? request.getParameter("keyword") : "";
    if (keyword != null && !"".equals(keyword))
      keyword = URLDecoder.decode(keyword, "utf-8"); 
    WeiXinBD bd = new WeiXinBD();
    int beginIndex = Integer.parseInt((request.getParameter("beginIndex") == null) ? "0" : request.getParameter("beginIndex"));
    Map map = bd.getChannelList(userId, orgId, orgIdString, "0", beginIndex, WapUtil.LIMITED, keyword);
    int recordCount = ((Integer)map.get("RECORD_COUNT")).intValue();
    request.setAttribute("RECORD_COUNT", Integer.valueOf(recordCount));
    request.setAttribute("ChannelMap", map);
    request.setAttribute("beginIndex", Integer.valueOf(beginIndex));
    request.setAttribute("keyword", keyword);
    request.setAttribute("size", map.get("size"));
    return "list";
  }
  
  private String info(HttpServletRequest request) throws Exception {
    String readId = request.getParameter("infoID");
    InformationBD informationBD = new InformationBD();
    HttpSession httpSession = request.getSession(true);
    String userName = httpSession.getAttribute("userName").toString();
    String orgName = httpSession.getAttribute("orgName").toString();
    String domainId = httpSession.getAttribute("domainId").toString();
    String userId = (String)httpSession.getAttribute("userId");
    String orgId = httpSession.getAttribute("orgId").toString();
    String orgIdString = httpSession.getAttribute("orgIdString").toString();
    informationBD.setBrowserKits(userId, userName, orgName, (
        new StringBuilder(String.valueOf(readId))).toString(), 
        httpSession.getAttribute("orgIdString").toString(), domainId);
    ChannelBD bd = new ChannelBD();
    Map map = WapUtil.getInfoById(readId);
    String isAllow = "";
    List<Object[]> channelstringids = informationBD.getchannleinfo((new StringBuilder(String.valueOf(readId))).toString());
    if (channelstringids != null && channelstringids.size() > 0) {
      Object[] objid = (Object[])null;
      objid = channelstringids.get(0);
      isAllow = objid[2].toString();
    } 
    List commentList = informationBD.getOrderedComment((new StringBuilder(String.valueOf(readId))).toString());
    request.setAttribute("commentList", commentList);
    request.setAttribute("isAllow", isAllow);
    InformationPO po = bd.getInfoPOByID(readId);
    request.setAttribute("infoPO", po);
    request.setAttribute("contentMap", map);
    request.setAttribute("readId", Long.valueOf(Long.parseLong(readId)));
    return "info";
  }
  
  private String speak(HttpServletRequest request) throws Exception {
    InformationBD informationBD = new InformationBD();
    String readId = request.getParameter("infoID");
    String content = request.getParameter("contents");
    HttpSession httpSession = request.getSession(true);
    String userName = httpSession.getAttribute("userName").toString();
    String orgName = httpSession.getAttribute("orgName").toString();
    String userId = httpSession.getAttribute("userId").toString();
    informationBD.setComment(userId, userName, orgName, content, (new StringBuilder(String.valueOf(readId))).toString());
    String isAllow = "";
    List<Object[]> channelstringids = informationBD.getchannleinfo((new StringBuilder(String.valueOf(readId))).toString());
    if (channelstringids != null && channelstringids.size() > 0) {
      Object[] objid = (Object[])null;
      objid = channelstringids.get(0);
      isAllow = objid[2].toString();
    } 
    List commentList = informationBD.getOrderedComment((new StringBuilder(String.valueOf(readId))).toString());
    ChannelBD bd = new ChannelBD();
    Map map = WapUtil.getInfoById(readId);
    InformationPO po = bd.getInfoPOByID(readId);
    request.setAttribute("infoPO", po);
    request.setAttribute("contentMap", map);
    request.setAttribute("readId", readId);
    request.setAttribute("commentList", commentList);
    request.setAttribute("isAllow", isAllow);
    request.setAttribute("flag", "1");
    return "info";
  }
}
