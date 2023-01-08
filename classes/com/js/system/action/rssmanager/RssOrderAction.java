package com.js.system.action.rssmanager;

import com.js.system.service.rssmanager.RssChannelBD;
import com.js.system.service.rssmanager.RssItemBD;
import com.js.system.vo.rssmanager.ChannelOrderVO;
import com.js.system.vo.rssmanager.ItemStateVO;
import com.js.util.page.Page;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class RssOrderAction extends DispatchAction {
  public ActionForward goItemFrame(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    return mapping.findForward("goItemFrame");
  }
  
  public ActionForward goRssOrderList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    myOrderList(request, " where po.userId=" + userId + " order by po.orderId desc ");
    return mapping.findForward("goRssChannelListRead");
  }
  
  public ActionForward orderRssChannel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String channelId = request.getParameter("channelId");
    String categoryId = request.getParameter("categoryId");
    RssChannelBD rcb = new RssChannelBD();
    ChannelOrderVO cov = new ChannelOrderVO();
    String userId = session.getAttribute("userId").toString();
    cov.setChannelId(Long.valueOf(channelId));
    cov.setOrderState("1");
    cov.setString1(categoryId);
    cov.setUserId(Long.valueOf(userId));
    rcb.orderRss(cov);
    return null;
  }
  
  public ActionForward goChannelItemList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    RssItemBD rcb = new RssItemBD();
    Map<Object, Object> map = new HashMap<Object, Object>();
    String userId = session.getAttribute("userId").toString();
    String channelId = request.getParameter("channelId");
    channelItemList(request, " where po.channelId=" + channelId + " order by po.pubDate desc,po.itemId ");
    List<ItemStateVO> list = rcb.getReadedItemList(userId);
    if (list != null && list.size() > 0)
      for (int i = 0; i < list.size(); i++) {
        ItemStateVO itemVO = list.get(i);
        map.put(itemVO.getItemId(), "");
      }  
    request.setAttribute("ItemReadedList", map);
    return mapping.findForward("channelItemList");
  }
  
  public ActionForward readRss(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String itemId = request.getParameter("itemId");
    String userId = session.getAttribute("userId").toString();
    RssItemBD rcb = new RssItemBD();
    ItemStateVO itemVO = new ItemStateVO();
    itemVO.setItemId(Long.valueOf(itemId));
    itemVO.setReadState("1");
    itemVO.setUserId(Long.valueOf(userId));
    rcb.saveOrUpdateItem(itemVO);
    return null;
  }
  
  public ActionForward removeRssOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String channelId = request.getParameter("channelId");
    String userId = session.getAttribute("userId").toString();
    RssChannelBD rcb = new RssChannelBD();
    rcb.removeRssOrder(channelId, userId);
    return goRssOrderList(mapping, form, request, response);
  }
  
  private void myOrderList(HttpServletRequest request, String wherePara) {
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    try {
      Page page = new Page("po.orderId,po.channelId,po.userId,po.orderState,po.string1", "ChannelOrderVO po ", wherePara);
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      request.setAttribute("rssChannelList", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "method,channelId");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private void channelItemList(HttpServletRequest request, String wherePara) {
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    try {
      Page page = new Page("po.itemId,po.channelId,po.itemLink,po.itemTitle,po.itemDesc,po.pubDate,po.readState", "ChannelItemVO po ", wherePara);
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      request.setAttribute("channelItemList", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "method,channelId");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
