package com.js.oa.form.bob;

import com.js.oa.form.Workflow;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class HdxxWorkFlow extends Workflow {
  private Map<String, String[]> map = null;
  
  public String complete(HttpServletRequest request) {
    String result = super.complete(request);
    String tableId = request.getParameter("tableId");
    return result;
  }
  
  private Map<String, String[]> getTable() {
    Map<String, String[]> map = (Map)new HashMap<String, String>();
    FileInputStream configFileInputStream = null;
    try {
      String path = System.getProperty("user.dir");
      String configFile = String.valueOf(path) + "/jsconfig/bjyhhdxx.xml";
      configFileInputStream = new FileInputStream(new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      Element node = root.getChild("fields");
      Element zonghang = node.getChild("zonghang");
      Element zMain = zonghang.getChild("main");
      Element zSub = zonghang.getChild("sub");
      map.put(zonghang.getAttributeValue("processId"), 
          new String[] { zMain.getAttributeValue("mainTableName"), zMain.getAttributeValue("addMainTableName"), 
            zSub.getAttributeValue("subTableName"), zSub.getAttributeValue("addSubTableName") });
      Element fenhang = node.getChild("fenhang");
      Element fMain = fenhang.getChild("main");
      Element fSub = fenhang.getChild("sub");
      map.put(fenhang.getAttributeValue("processId"), 
          new String[] { fMain.getAttributeValue("mainTableName"), fMain.getAttributeValue("addMainTableName"), 
            fSub.getAttributeValue("subTableName"), fSub.getAttributeValue("addSubTableName") });
      configFileInputStream.close();
    } catch (Exception ex) {
      System.out.println("请配置北京银行营销活动数据库信息   /jsconfig/bjyhhdxx.xml。");
      ex.printStackTrace();
    } 
    return map;
  }
  
  private Map<String, Map<String, String>> getTableField() {
    Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
    FileInputStream configFileInputStream = null;
    try {
      String path = System.getProperty("user.dir");
      String configFile = String.valueOf(path) + "/jsconfig/bjyhhdxx.xml";
      configFileInputStream = new FileInputStream(new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      Element node = root.getChild("fields");
      Element zonghang = node.getChild("zonghang");
      Element zMain = zonghang.getChild("main");
      List<Element> zmElement = zMain.getChildren();
      Map<String, String> zmMap = new HashMap<String, String>();
      for (int i = 0; i < zmElement.size(); i++) {
        Element element = zmElement.get(i);
        zmMap.put(element.getAttributeValue("updateTable"), element.getAttributeValue("addTable"));
      } 
      map.put(zMain.getAttributeValue("mainTableName"), zmMap);
      Element zSub = zonghang.getChild("sub");
      List<Element> zsElement = zSub.getChildren();
      Map<String, String> zsMap = new HashMap<String, String>();
      for (int j = 0; j < zsElement.size(); j++) {
        Element element = zsElement.get(j);
        zsMap.put(element.getAttributeValue("updateTable"), element.getAttributeValue("addTable"));
      } 
      map.put(zSub.getAttributeValue("subTableName"), zsMap);
      Element fenhang = node.getChild("fenhang");
      Element fMain = fenhang.getChild("main");
      List<Element> fmElement = fMain.getChildren();
      Map<String, String> fmMap = new HashMap<String, String>();
      for (int k = 0; k < fmElement.size(); k++) {
        Element element = fmElement.get(k);
        fmMap.put(element.getAttributeValue("updateTable"), element.getAttributeValue("addTable"));
      } 
      map.put(fMain.getAttributeValue("mainTableName"), fmMap);
      Element fSub = fenhang.getChild("sub");
      List<Element> fsElement = fSub.getChildren();
      Map<String, String> fsMap = new HashMap<String, String>();
      for (int m = 0; m < fsElement.size(); m++) {
        Element element = fsElement.get(m);
        fsMap.put(element.getAttributeValue("updateTable"), element.getAttributeValue("addTable"));
      } 
      map.put(fSub.getAttributeValue("subTableName"), fsMap);
      configFileInputStream.close();
    } catch (Exception ex) {
      System.out.println("请配置北京银营销活动数据库信息   /jsconfig/bjyhhdxx.xml。");
      ex.printStackTrace();
    } 
    return map;
  }
}
