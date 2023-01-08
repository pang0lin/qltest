package com.js.util.rss;

import com.sun.syndication.feed.WireFeed;
import com.sun.syndication.feed.rss.Channel;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class RomeRss {
  private URL feedUrl = null;
  
  private SyndFeed feed = null;
  
  private WireFeed wirefeed = null;
  
  private XmlReader xmlReader = null;
  
  private String rssUrl;
  
  public RomeRss(String rssUrl) {
    this.rssUrl = rssUrl;
  }
  
  public Channel getChannel() {
    Channel channel = null;
    try {
      if (isUrlEixst(this.rssUrl))
        channel = (Channel)this.wirefeed; 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return channel;
  }
  
  public List getChannelItemList() {
    List list = null;
    try {
      if (isUrlEixst(this.rssUrl) && this.feed != null)
        list = this.feed.getEntries(); 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return list;
  }
  
  private void buildWireFeed() {
    try {
      this.wirefeed = this.feed.createWireFeed();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private void buildSyndFeed() {
    try {
      SyndFeedInput input = new SyndFeedInput();
      this.xmlReader = new XmlReader(this.feedUrl);
      this.feed = input.build((Reader)this.xmlReader);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private void closeSyndFeed() {
    try {
      this.xmlReader.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private boolean isUrlEixst(String url) {
    boolean flag = false;
    try {
      this.feedUrl = new URL(url);
      HttpURLConnection httpURL = (HttpURLConnection)this.feedUrl.openConnection();
      if (httpURL.getResponseCode() == 200) {
        httpURL.setConnectTimeout(100000);
        buildSyndFeed();
        closeSyndFeed();
        buildWireFeed();
        flag = true;
      } else {
        flag = false;
      } 
    } catch (Exception e) {
      flag = false;
      e.printStackTrace();
    } 
    return flag;
  }
  
  public String getRssUrl() {
    return this.rssUrl;
  }
  
  public void setRssUrl(String rssUrl) {
    this.rssUrl = rssUrl;
  }
}
