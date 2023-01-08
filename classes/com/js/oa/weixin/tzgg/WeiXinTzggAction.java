package com.js.oa.weixin.tzgg;

import com.js.oa.info.channelmanager.service.ChannelBD;
import com.js.oa.info.infomanager.service.InformationBD;
import com.js.oa.weixin.common.service.WeiXinBD;
import com.js.util.config.SystemCommon;
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

public class WeiXinTzggAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String method = request.getParameter("action");
    String userId = (String)session.getAttribute("userId");
    String orgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    if ("list".equals(method)) {
      String keyword = request.getParameter("keyword");
      String keywordSQL = "";
      if (keyword != null && !"".equals(keyword)) {
        keyword = URLDecoder.decode(keyword, "utf-8");
        keywordSQL = " and bbb.informationTitle like '%" + keyword + "%'";
      } 
      int beginIndex = Integer.parseInt((request.getParameter("beginIndex") == null) ? "0" : request.getParameter("beginIndex"));
      String corpId = request.getSession().getAttribute("corpId").toString();
      String info = "101";
      if (1 == SystemCommon.getMultiDepart()) {
        ChannelBD bd = new ChannelBD();
        info = bd.getChannelSimpleInfoByCorpId(corpId, 0)[1][0];
      } 
      WeiXinBD cdb = new WeiXinBD();
      Map newsMap = cdb.getNewsList(info, userId, orgId, orgIdString, "0", beginIndex, WapUtil.LIMITED, keywordSQL);
      List showList = (List)newsMap.get("QUERY_LIST");
      int recordCount = ((Integer)newsMap.get("RECORD_COUNT")).intValue();
      request.setAttribute("SHOW_LIST", showList);
      request.setAttribute("RECORD_COUNT", Integer.valueOf(recordCount));
      request.setAttribute("cardTitle", "通知公告");
      request.setAttribute("keyword", keyword);
      request.setAttribute("size", newsMap.get("size"));
      return mapping.findForward("list");
    } 
    if ("readInfo".equals(method)) {
      Long readId = Long.valueOf(request.getParameter("readId"));
      InformationBD informationBD = new InformationBD();
      HttpSession httpSession = request.getSession(true);
      String userName = httpSession.getAttribute("userName").toString();
      String orgName = httpSession.getAttribute("orgName").toString();
      String domainId = httpSession.getAttribute("domainId").toString();
      informationBD.setBrowserKits(userId, userName, orgName, 
          (String)readId, 
          httpSession.getAttribute("orgIdString").toString(), domainId);
      Map map = WapUtil.getInfoById(String.valueOf(readId));
      String isAllow = "";
      List<Object[]> channelstringids = informationBD.getchannleinfo((String)readId);
      if (channelstringids != null && channelstringids.size() > 0) {
        Object[] objid = (Object[])null;
        objid = channelstringids.get(0);
        isAllow = objid[2].toString();
      } 
      List commentList = informationBD.getOrderedComment((String)readId);
      request.setAttribute("commentList", commentList);
      request.setAttribute("contentMap", map);
      request.setAttribute("readId", readId);
      request.setAttribute("isAllow", isAllow);
      return mapping.findForward("readInfo");
    } 
    if ("readZsgl".equals(method)) {
      Long readId = Long.valueOf(request.getParameter("readId"));
      InformationBD informationBD = new InformationBD();
      HttpSession httpSession = request.getSession(true);
      String userName = httpSession.getAttribute("userName").toString();
      String orgName = httpSession.getAttribute("orgName").toString();
      String domainId = httpSession.getAttribute("domainId").toString();
      informationBD.setBrowserKits(userId, userName, orgName, 
          (String)readId, 
          httpSession.getAttribute("orgIdString").toString(), domainId);
      Map map = WapUtil.getInfoById(String.valueOf(readId));
      String isAllow = "";
      List<Object[]> channelstringids = informationBD.getchannleinfo((String)readId);
      if (channelstringids != null && channelstringids.size() > 0) {
        Object[] objid = (Object[])null;
        objid = channelstringids.get(0);
        isAllow = objid[2].toString();
      } 
      List commentList = informationBD.getOrderedComment((String)readId);
      request.setAttribute("commentList", commentList);
      request.setAttribute("contentMap", map);
      request.setAttribute("readId", readId);
      request.setAttribute("isAllow", isAllow);
      request.setAttribute("readId", readId);
      if ("".equals(map.get("title")))
        return mapping.findForward("readZsglErr"); 
      return mapping.findForward("readZsgl");
    } 
    if ("speak".equals(method)) {
      InformationBD informationBD = new InformationBD();
      Long readId = Long.valueOf(request.getParameter("readId"));
      Map map = WapUtil.getInfoById(String.valueOf(readId));
      String content = request.getParameter("contents");
      HttpSession httpSession = request.getSession(true);
      String userName = httpSession.getAttribute("userName").toString();
      String orgName = httpSession.getAttribute("orgName").toString();
      informationBD.setComment(userId, userName, orgName, content, (String)readId);
      String isAllow = "";
      List<Object[]> channelstringids = informationBD.getchannleinfo((String)readId);
      if (channelstringids != null && channelstringids.size() > 0) {
        Object[] objid = (Object[])null;
        objid = channelstringids.get(0);
        isAllow = objid[2].toString();
      } 
      List commentList = informationBD.getOrderedComment((String)readId);
      request.setAttribute("commentList", commentList);
      request.setAttribute("contentMap", map);
      request.setAttribute("readId", readId);
      request.setAttribute("isAllow", isAllow);
      request.setAttribute("flag", "1");
      return mapping.findForward("readInfo");
    } 
    return null;
  }
}
