package com.js.system.action.rssmanager;

import com.js.system.bean.rssmanager.RssShowEJBBean;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class RssShowAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    String[] queryStr = request.getQueryString().split("&");
    String userId = "";
    if (queryStr.length == 2)
      userId = queryStr[1]; 
    String action = "infoShow";
    RssShowEJBBean rssShow = new RssShowEJBBean();
    if (queryStr[0].equals("show.xml") || (
      queryStr.length >= 1 && queryStr[0].startsWith("show") && queryStr[0].endsWith(".xml"))) {
      action = "show";
      String flag = "";
      if (!queryStr[0].equals("show.xml"))
        flag = queryStr[0].replace("show", "").replace(".xml", ""); 
      Map<String, String> rssSet = getXmlSet(flag);
      Map<String, String> rqInfo = new HashMap<String, String>();
      if (!userId.equals(""))
        rqInfo = rssShow.getRqInfo(userId); 
      List<Map<String, String>> rssList = rssShow.getRssList(rssSet, rqInfo);
      Map<String, String> rssHeader = rssShow.getRssHeader(rssSet, rqInfo);
      request.setAttribute("rssList", rssList);
      request.setAttribute("rssHeader", rssHeader);
    } 
    return actionMapping.findForward(action);
  }
  
  public Map<String, String> getXmlSet(String flag) {
    Map<String, String> map = new HashMap<String, String>();
    try {
      String path = System.getProperty("user.dir");
      String configFile = String.valueOf(path) + "/jsconfig/rssconfig.xml";
      FileInputStream configFileInputStream = new FileInputStream(new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element rss = doc.getRootElement();
      String use = rss.getChildText("use");
      if ("1".equals(use)) {
        List<Element> rssShow = rss.getChildren("rssShow");
        for (int i = 0; i < rssShow.size(); i++) {
          Element rssSingle = rssShow.get(i);
          if (flag.equals(rssSingle.getAttributeValue("flag")) || "".equals(flag)) {
            Element header = rssSingle.getChild("header");
            List<Element> headerInfo = header.getChildren();
            for (int h = 0; h < headerInfo.size(); h++) {
              Element info = headerInfo.get(h);
              map.put("header." + info.getName(), info.getText());
            } 
            Element data = rssSingle.getChild("data");
            List<Element> dataInfo = data.getChildren();
            for (int j = 0; j < dataInfo.size(); j++) {
              Element info = dataInfo.get(j);
              map.put("data." + info.getName(), info.getText());
            } 
          } 
          if ("".equals(flag))
            break; 
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return map;
  }
}
