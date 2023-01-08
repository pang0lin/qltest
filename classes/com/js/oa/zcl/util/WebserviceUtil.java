package com.js.oa.zcl.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class WebserviceUtil {
  public Map getLogInfo(String xml) throws JDOMException, IOException {
    SAXBuilder builder = new SAXBuilder();
    builder.setExpandEntities(false);
    ByteArrayInputStream xmlStream = null;
    Map<String, String> map = new HashMap<String, String>();
    try {
      xmlStream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
    } catch (UnsupportedEncodingException uee) {
      uee.printStackTrace();
    } 
    Document doc = builder.build(xmlStream);
    Element root = doc.getRootElement();
    List<Element> datas = root.getChildren();
    for (int i = 0; i < datas.size(); i++) {
      Element data = datas.get(i);
      map = getXmlData(data);
    } 
    return map;
  }
  
  private Map getXmlData(Element data) {
    Map<String, String> map = new HashMap<String, String>();
    Element result = data.getChild("result");
    if (result != null)
      map.put("flag", result.getValue()); 
    Element uuid = data.getChild("uuid");
    if (uuid != null)
      map.put("uuid", uuid.getValue()); 
    Element msg = data.getChild("msg");
    if (msg != null)
      map.put("msg", msg.getValue()); 
    Element sessionid = data.getChild("sessionid");
    if (sessionid != null)
      map.put("sessionid", sessionid.getValue()); 
    Element timestamp = data.getChild("timestamp");
    if (timestamp != null)
      map.put("timestamp", timestamp.getValue()); 
    Element url = data.getChild("url");
    if (url != null)
      map.put("url", url.getValue()); 
    Element type = data.getChild("type");
    if (type != null)
      map.put("type", type.getValue()); 
    Element pageSize = data.getChild("pageSize");
    if (pageSize != null)
      map.put("pageSize", pageSize.getValue()); 
    Element currentPage = data.getChild("currentPage");
    if (currentPage != null)
      map.put("currentPage", currentPage.getValue()); 
    return map;
  }
}
