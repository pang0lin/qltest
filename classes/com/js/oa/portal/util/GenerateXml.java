package com.js.oa.portal.util;

import com.js.oa.portal.po.CustomDesktopModuleVO;
import com.js.oa.portal.po.CustomdesktopItemVO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import org.jdom.Attribute;
import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class GenerateXml {
  public static void getOutputStream(CustomDesktopModuleVO cmv, PrintWriter out) {
    Element rss = new Element("rss");
    rss.setAttribute(new Attribute("version", "2.00"));
    Element channel = new Element("channel");
    channel.addContent((Content)(new Element("description")).addContent(cmv.getDescription()));
    channel.addContent((Content)(new Element("link")).addContent(cmv.getLink()));
    channel.addContent((Content)(new Element("title")).addContent(cmv.getTitle()));
    channel.addContent((Content)(new Element("imageName")).addContent(cmv.getImageName()));
    channel.addContent((Content)(new Element("imageNewsTitle")).addContent(cmv.getImageNewsTitle()));
    channel.addContent((Content)(new Element("imageNewsTitleLink")).addContent(cmv.getImageNewsTitleLink()));
    rss.addContent((Content)channel);
    Document myDocument = new Document(rss);
    List<CustomdesktopItemVO> list = cmv.getItemList();
    if (list.size() == 0) {
      Element fisrt = new Element("item");
      fisrt.addContent((Content)(new Element("title")).addContent("<font color='#333'>未查询到数据！</font>"));
      fisrt.addContent((Content)(new Element("link")).addContent("#"));
      channel.addContent((Content)fisrt);
    } 
    for (int i = 0; i < list.size(); i++) {
      CustomdesktopItemVO cd = list.get(i);
      Element item = new Element("item");
      item.addContent((Content)(new Element("title")).addContent(cd.getTitle()));
      item.addContent((Content)(new Element("link")).addContent(cd.getLink()));
      String isConf = (cd.getIsConf() == null) ? "0" : cd.getIsConf();
      item.addContent((Content)(new Element("isConf")).addContent(isConf));
      if (cd.getTime() != null && !"".equals(cd.getTime()))
        item.addContent((Content)(new Element("time")).addContent(cd.getTime())); 
      if (cd.getIsnewimg() != null && !"".equals(cd.getIsnewimg()))
        item.addContent((Content)(new Element("isnewimg")).addContent(cd.getIsnewimg())); 
      if (cd.getIstitleread() != null && !"".equals(cd.getIstitleread()))
        item.addContent((Content)(new Element("istitleread")).addContent(cd.getIstitleread())); 
      if (cd.getReadcount() != null && !"".equals(cd.getReadcount()))
        item.addContent((Content)(new Element("readcount")).addContent(cd.getReadcount())); 
      channel.addContent((Content)item);
    } 
    Format format = Format.getCompactFormat();
    format.setEncoding("GBK");
    format.setIndent("    ");
    XMLOutputter outputter = new XMLOutputter(format);
    try {
      outputter.output(myDocument, out);
    } catch (IOException ex) {
      ex.printStackTrace();
    } 
  }
}
