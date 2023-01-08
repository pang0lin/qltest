package com.js.oa.portal.util;

import com.js.oa.portal.po.CustomdesktopItemVO;
import com.rsslibj.elements.Channel;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class GenerateRss {
  public static String getRssFeed(List<CustomdesktopItemVO> list) throws InstantiationException, ClassNotFoundException, IllegalAccessException, UnsupportedEncodingException {
    Channel channel = new Channel();
    channel.setDescription("This is my sample channel.");
    channel.setLink("http://localhost/");
    channel.setTitle("My Channel");
    channel.setImage("http://localhost/", 
        "The Channel Image", 
        "http //localhost/foo.jpg");
    channel.setTextInput("http://localhost/search", 
        "Search The Channel Image", 
        "The Channel Image", "s");
    channel.addItem("http://localhost/item1", "The First Item covers details on the first item>", "The First Item").setDcContributor("Joseph B. Ottinger");
    channel.addItem("http://localhost/item2", "The Second Item covers details on the second item", "The Second Item").setDcCreator("Jason Bell");
    for (int i = 0; i < list.size(); i++) {
      CustomdesktopItemVO cd = list.get(i);
      channel.addItem(cd.getLink(), "The discription", new String(cd.getTitle().getBytes("iso-8859-1"), "Gb2312")).setDcContributor("jiusi.net");
    } 
    return channel.getFeed("200");
  }
}
