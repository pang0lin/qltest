package com.js.oa.weixin.innernews;

import com.js.oa.info.channelmanager.service.ChannelBD;
import com.js.oa.info.infomanager.service.InformationBD;
import com.js.oa.weixin.common.service.WeiXinBD;
import com.js.util.config.SystemCommon;
import com.js.wap.util.WapUtil;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class InnerNewsAction extends DispatchAction {
  public ActionForward getDetailContent(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String conType = request.getParameter("conType");
    String userId = (String)session.getAttribute("userId");
    String orgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    getWapNewsList(request, userId, orgId, orgIdString, "0");
    return mapping.findForward("getDetailContent_3g");
  }
  
  public void getWapNewsList(HttpServletRequest request, String userId, String orgId, String orgIdString, String domainId) throws UnsupportedEncodingException {
    String keyword = request.getParameter("keyword");
    String keywordSQL = "";
    if (keyword != null && !"".equals(keyword)) {
      keyword = URLDecoder.decode(keyword, "utf-8");
      keywordSQL = " and bbb.informationTitle like '%" + keyword + "%'";
    } 
    WeiXinBD cdb = new WeiXinBD();
    int beginIndex = Integer.parseInt((request.getParameter("beginIndex") == null) ? "0" : request.getParameter("beginIndex"));
    String corpId = request.getSession().getAttribute("corpId").toString();
    String info = "100";
    if (SystemCommon.getMultiDepart() == 1) {
      ChannelBD bd = new ChannelBD();
      info = bd.getChannelSimpleInfoByCorpId(corpId, 0)[0][0];
    } 
    Map newsMap = cdb.getNewsList(info, userId, orgId, orgIdString, domainId, beginIndex, WapUtil.LIMITED, keywordSQL);
    List showList = (List)newsMap.get("QUERY_LIST");
    int recordCount = ((Integer)newsMap.get("RECORD_COUNT")).intValue();
    request.setAttribute("SHOW_LIST", showList);
    request.setAttribute("RECORD_COUNT", Integer.valueOf(recordCount));
    request.setAttribute("cardTitle", "内部新闻");
    request.setAttribute("keyword", keyword);
    request.setAttribute("size", newsMap.get("size"));
  }
  
  public ActionForward readInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    InformationBD informationBD = new InformationBD();
    Long readId = Long.valueOf(request.getParameter("readId"));
    HttpSession httpSession = request.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String userName = httpSession.getAttribute("userName").toString();
    String orgName = httpSession.getAttribute("orgName").toString();
    String orgId = httpSession.getAttribute("orgId").toString();
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
    request.setAttribute("isAllow", isAllow);
    request.setAttribute("contentMap", map);
    request.setAttribute("readId", readId);
    return mapping.findForward("readInfo_3g");
  }
  
  public ActionForward speak(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    InformationBD informationBD = new InformationBD();
    Long readId = Long.valueOf(request.getParameter("readId"));
    HttpSession httpSession = request.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String userName = httpSession.getAttribute("userName").toString();
    String orgName = httpSession.getAttribute("orgName").toString();
    String content = request.getParameter("contents");
    informationBD.setComment(userId, userName, orgName, content, (String)readId);
    String isAllow = "";
    List<Object[]> channelstringids = informationBD.getchannleinfo((String)readId);
    if (channelstringids != null && channelstringids.size() > 0) {
      Object[] objid = (Object[])null;
      objid = channelstringids.get(0);
      isAllow = objid[2].toString();
    } 
    List commentList = informationBD.getOrderedComment((String)readId);
    Map map = WapUtil.getInfoById(String.valueOf(readId));
    request.setAttribute("contentMap", map);
    request.setAttribute("readId", readId);
    request.setAttribute("commentList", commentList);
    request.setAttribute("isAllow", isAllow);
    request.setAttribute("flag", "1");
    return mapping.findForward("readInfo_3g");
  }
}
