package com.js.util.util;

import com.js.lang.NoOpEntityResolver;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class SaiTongStr {
  private static SaiTongStr singleton = null;
  
  private Map<String, String> saiTong;
  
  private SaiTongStr() {
    init();
  }
  
  public static SaiTongStr getInstance() {
    if (singleton == null)
      singleton = new SaiTongStr(); 
    return singleton;
  }
  
  public String getRtxStr(String str) {
    return this.saiTong.get(str);
  }
  
  private void init() {
    this.saiTong = new HashMap<String, String>();
    String path = "";
    path = System.getProperty("user.dir");
    if (!"".equals(path)) {
      path = path.replace("\\", "/");
      String configFile = String.valueOf(path) + "/jsconfig/othersystem.xml";
      FileInputStream configFileInputStream = null;
      try {
        configFileInputStream = new FileInputStream(new File(configFile));
        SAXBuilder builder = new SAXBuilder();
        builder.setValidation(false);
        builder.setEntityResolver(new NoOpEntityResolver());
        Document doc = builder.build(configFileInputStream);
        Element node = doc.getRootElement();
        Element saiNode = node.getChild("saitong");
        String url = saiNode.getChild("url").getText();
        String OAURL = saiNode.getChild("OAURL").getText();
        String OAServer = saiNode.getChildText("OAServer");
        String validate = saiNode.getChildText("validate");
        this.saiTong.put("url", url);
        this.saiTong.put("OAURL", OAURL);
        this.saiTong.put("OAServer", OAServer);
        this.saiTong.put("validate", validate);
        configFileInputStream.close();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        if (configFileInputStream != null)
          try {
            configFileInputStream.close();
          } catch (IOException e) {
            e.printStackTrace();
          }  
      } 
    } 
  }
}
