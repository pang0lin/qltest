package com.js.util.rss;

import com.js.system.service.rssmanager.RssChannelBD;
import com.js.system.vo.rssmanager.CategoryChannelVO;
import com.js.system.vo.rssmanager.ChannelItemVO;
import com.sun.syndication.feed.synd.SyndEntry;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.TimerTask;

public class RssTask extends TimerTask {
  public void run() {
    updateAllRss();
  }
  
  private void updateAllRss() {
    RssChannelBD rcb = new RssChannelBD();
    List<CategoryChannelVO> channelList = rcb.getRssChannelList();
    if (channelList != null && channelList.size() > 0)
      for (int i = 0; i < channelList.size(); i++) {
        CategoryChannelVO ccv = channelList.get(i);
        List itemList = setChannelItemVO(ccv.getChannelId(), ccv.getChannelUrl());
        rcb.saveChannelItem(itemList);
      }  
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
