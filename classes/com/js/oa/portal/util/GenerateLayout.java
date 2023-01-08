package com.js.oa.portal.util;

import com.js.util.config.SystemCommon;
import com.js.util.util.JSFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class GenerateLayout {
  private static List serverList = null;
  
  public static void generLayoutFile(String path, String fileName, String layoutId) {
    Element result = new Element("result");
    Element pageTitle = new Element("pageTitle");
    pageTitle.addContent("MyDesktop");
    Element elLayoutId = new Element("layoutId");
    elLayoutId.addContent(layoutId);
    Element userFeeds = new Element("userFeeds");
    userFeeds.addContent("1");
    Element featuredFeeds = new Element("featuredFeeds");
    featuredFeeds.addContent("2");
    Element defaultFeeds = new Element("defaultFeeds");
    defaultFeeds.addContent("3");
    result.addContent((Content)pageTitle);
    result.addContent((Content)elLayoutId);
    result.addContent((Content)userFeeds);
    result.addContent((Content)featuredFeeds);
    result.addContent((Content)defaultFeeds);
    Document myDocument = new Document(result);
    File layout = new File(path, fileName);
    XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat().setEncoding("utf-8"));
    try {
      outputter.output(myDocument, new FileWriter(layout));
    } catch (IOException ex) {
      ex.printStackTrace();
    } 
    System.out.println("门户xml文件路径:" + path + "/" + fileName);
    if (SystemCommon.getUseClusterServer() == 1) {
      path = String.valueOf(path) + "/" + fileName;
      System.out.println("门户xml文件（远程）路径:" + path + "/" + fileName);
      List<E> list = getOtherServer();
      for (int i = 0; i < list.size(); i++)
        JSFile.copyToFileServer(path, list.get(i).toString()); 
    } 
  }
  
  public static List getOtherServer() {
    if (serverList == null) {
      serverList = new ArrayList();
      try {
        String path = System.getProperty("user.dir");
        String configFile = String.valueOf(path) + "/jsconfig/cluster.xml";
        FileInputStream configFileInputStream = new FileInputStream(new File(configFile));
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(configFileInputStream);
        Element root = doc.getRootElement();
        Element node = root.getChild("fileserver").getChild("otherserver");
        List<Element> list = node.getChildren("path");
        if (list != null)
          for (int i = 0; i < list.size(); i++)
            serverList.add(((Element)list.get(i)).getText());  
        configFileInputStream.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
    } 
    return serverList;
  }
}
