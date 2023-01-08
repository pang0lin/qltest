package com.js.system.action.rssmanager;

import com.js.system.service.rssmanager.RssCategoryBD;
import com.js.system.service.rssmanager.RssChannelBD;
import com.js.system.vo.rssmanager.CategoryChannelVO;
import com.js.system.vo.rssmanager.ChannelInfoVO;
import com.js.system.vo.rssmanager.ChannelItemVO;
import com.js.system.vo.rssmanager.ChannelOrderVO;
import com.js.util.page.Page;
import com.js.util.rss.RomeRss;
import com.js.util.util.DateHelper;
import com.sun.syndication.feed.rss.Channel;
import com.sun.syndication.feed.synd.SyndEntry;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class RssCategoryChannelAction extends DispatchAction {
  public ActionForward goRssChannelList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String selectType = request.getParameter("selectType");
    if (selectType == null || selectType.equals("")) {
      channelList(request, " order by ro.createTime desc ");
    } else {
      RssChannelBD rcb = new RssChannelBD();
      Map<Object, Object> map = new HashMap<Object, Object>();
      String userId = session.getAttribute("userId").toString();
      List<ChannelOrderVO> list = rcb.getMyRssList(userId);
      if (list != null && list.size() > 0)
        for (int i = 0; i < list.size(); i++) {
          ChannelOrderVO cc = list.get(i);
          map.put(cc.getChannelId(), "");
        }  
      request.setAttribute("MyOrderList", map);
      request.setAttribute("selectType", selectType);
      channelList(request, " order by ro.channelName ");
    } 
    return mapping.findForward("goRssChannelList");
  }
  
  public ActionForward goProAddRssChannel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    RssCategoryBD rssDB = new RssCategoryBD();
    List list = rssDB.getRssCategoryList();
    request.setAttribute("rssChannelList", list);
    return mapping.findForward("goProAddRssChannel");
  }
  
  public ActionForward addOrUpdateRssChannel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String categoryId = request.getParameter("categoryId");
    String channelId = request.getParameter("channelId");
    String userId = session.getAttribute("userId").toString();
    String channelName = request.getParameter("channelName");
    String channelUrl = request.getParameter("url");
    String channelDesc = request.getParameter("channelDesc");
    if (channelName != null && !channelName.equals("")) {
      CategoryChannelVO rssChannelVO = new CategoryChannelVO();
      RssChannelBD rssChannelDB = new RssChannelBD();
      rssChannelVO.setCategoryId(Long.valueOf(categoryId));
      rssChannelVO.setCreateTime(DateHelper.date2String(new Date(), null));
      rssChannelVO.setCreateUserId(Long.valueOf(Long.parseLong(userId)));
      rssChannelVO.setChannelName(channelName);
      rssChannelVO.setChannelDesc(channelDesc);
      rssChannelVO.setChannelUrl(channelUrl);
      if (channelId != null && !channelId.equals("")) {
        rssChannelVO.setChannelId(Long.valueOf(channelId));
        CategoryChannelVO rcv = rssChannelDB.getSingleRssChannel(channelId);
        if (!rcv.getChannelUrl().trim().equals(channelUrl.trim())) {
          rssChannelDB.delChannelItem(channelId);
          rssChannelDB.saveChannelItem(setChannelItemVO(rssChannelVO.getChannelId(), channelUrl.trim()));
        } 
        rssChannelDB.saveOrUpdateRssChannel(rssChannelVO);
      } else {
        rssChannelDB.saveOrUpdateRssChannel(rssChannelVO);
        rssChannelDB.saveChannelItem(setChannelItemVO(rssChannelVO.getChannelId(), channelUrl.trim()));
      } 
    } 
    return goProAddRssChannel(mapping, form, request, response);
  }
  
  public ActionForward delRssChannel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String delType = request.getParameter("delType");
    String channelId = request.getParameter("channelId");
    String channelIds = request.getParameter("channelIds");
    if (delType != null && !delType.equals("")) {
      RssChannelBD rcb = new RssChannelBD();
      if (delType.equals("single")) {
        String[] ids = (String[])null;
        if (channelIds != null && !channelIds.equals(""))
          ids = channelIds.split(","); 
        rcb.delChannel(channelId, ids);
      } else {
        rcb.delAllChannel();
      } 
    } 
    return goRssChannelList(mapping, form, request, response);
  }
  
  public ActionForward proModRssChannel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String channelId = request.getParameter("channelId");
    RssChannelBD rcb = new RssChannelBD();
    CategoryChannelVO rssChannelVO = rcb.getSingleRssChannel(channelId);
    request.setAttribute("RssChannelVO", rssChannelVO);
    RssCategoryBD rssDB = new RssCategoryBD();
    List list = rssDB.getRssCategoryList();
    request.setAttribute("rssChannelList", list);
    return mapping.findForward("goProAddRssChannel");
  }
  
  public ActionForward checkChannel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String channelName = request.getParameter("channelName");
    RssChannelBD rcb = new RssChannelBD();
    List list = null;
    if (request.getParameter("channelId") == null) {
      rcb.getSingleRssChannelByName(channelName.trim());
    } else {
      String channelId = request.getParameter("channelId");
      rcb.getSingleRssChannelByName(channelName.trim(), channelId);
    } 
    StringBuffer xml = new StringBuffer(1024);
    response.setContentType("text/xml;charset=GBK");
    PrintWriter out = response.getWriter();
    xml.append("<?xml version=\"1.0\" encoding=\"GBK\" ?>\n");
    xml.append("<result>\n");
    if (list == null || list.size() == 0) {
      xml.append("  <name>0</name>\n");
    } else {
      xml.append("  <name>1</name>\n");
    } 
    xml.append("</result>\n");
    out.print(xml.toString());
    out.close();
    return null;
  }
  
  private void channelList(HttpServletRequest request, String wherePara) {
    Object object = (request.getSession(true).getAttribute("domainId") == null) ? "0" : request.getSession(true).getAttribute("domainId");
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    try {
      Page page = new Page("ro.channelId,ro.channelName,ro.channelUrl,ro.categoryId,ro.createUserId,ro.createTime", "CategoryChannelVO ro ", wherePara);
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      request.setAttribute("rssChannelList", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "method,selectType");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private ChannelInfoVO setChannelInfoVO(Long channelId, String url) {
    ChannelInfoVO infoVO = null;
    try {
      RomeRss romeRss = new RomeRss(url);
      infoVO = new ChannelInfoVO();
      Channel channel = romeRss.getChannel();
      infoVO.setChannelId(channelId);
      infoVO.setChannelDesc(channel.getDescription());
      infoVO.setChannelLink(channel.getLink());
      infoVO.setChannelTitle(channel.getTitle());
      infoVO.setCopyRight(channel.getCopyright());
      infoVO.setPubDate(channel.getPubDate().toString());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return infoVO;
  }
  
  private List setChannelItemVO(Long channelId, String url) {
    List<ChannelItemVO> list = null;
    ChannelItemVO itemVO = null;
    String format = "yyyy-MM-dd HH:mm:ss";
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    try {
      RomeRss romeRss = new RomeRss(url);
      List<SyndEntry> listItem = romeRss.getChannelItemList();
      if (listItem != null && listItem.size() > 0) {
        list = new ArrayList();
        for (int i = 0; i < listItem.size(); i++) {
          SyndEntry entry = listItem.get(i);
          itemVO = new ChannelItemVO();
          itemVO.setChannelId(channelId);
          itemVO.setItemDesc(entry.getDescription().getValue().trim());
          itemVO.setItemLink(entry.getLink());
          itemVO.setItemTitle(entry.getTitle());
          if (entry.getPublishedDate() == null) {
            itemVO.setPubDate(sdf.format(new Date()));
          } else {
            itemVO.setPubDate(sdf.format(entry.getPublishedDate()));
          } 
          list.add(itemVO);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return list;
  }
}
