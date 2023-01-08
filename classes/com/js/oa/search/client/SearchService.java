package com.js.oa.search.client;

import com.js.oa.search.client.disrupter.SearchBean;
import com.js.oa.search.client.disrupter.SearchContext;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class SearchService {
  private static SearchService instance;
  
  private static String ifActiveUpdateDelete;
  
  private static String iSearchSwitch;
  
  public static SearchService getInstance() {
    if (instance == null) {
      init();
      instance = new SearchService();
    } 
    return instance;
  }
  
  public static void init() {
    try {
      String path = System.getProperty("user.dir");
      String filePath = String.valueOf(path) + "/jsconfig/sysconfig.xml";
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(filePath);
      Element node = doc.getRootElement().getChild("ISearchService");
      ifActiveUpdateDelete = node.getChild("ifActiveUpdateDelete").getAttribute("value").getValue();
      if (ifActiveUpdateDelete != null && !"".equals(ifActiveUpdateDelete)) {
        ifActiveUpdateDelete = ifActiveUpdateDelete.toLowerCase();
        if (!"yes".equals(ifActiveUpdateDelete))
          ifActiveUpdateDelete = "no"; 
      } else {
        ifActiveUpdateDelete = "no";
      } 
      iSearchSwitch = node.getChild("ISearchSwitch").getAttribute("value").getValue();
      if (iSearchSwitch != null && !"".equals(iSearchSwitch)) {
        if (!"1".equals(iSearchSwitch))
          iSearchSwitch = "0"; 
      } else {
        iSearchSwitch = "0";
      } 
    } catch (Exception e) {
      ifActiveUpdateDelete = "no";
      iSearchSwitch = "0";
    } 
  }
  
  public static void addIndex(String id, String tableName) {
    try {
      SearchBean searchBean = new SearchBean();
      searchBean.setId(id);
      searchBean.setTableName(tableName);
      searchBean.setMethodName("addIndex");
      SearchContext.getInstance().sendSearch(searchBean);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public static void updateIndex(String id, String tableName) {
    try {
      SearchBean searchBean = new SearchBean();
      searchBean.setId(id);
      searchBean.setTableName(tableName);
      searchBean.setMethodName("updateIndex");
      SearchContext.getInstance().sendSearch(searchBean);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public static void deleteIndex(String id, String tableName) {
    try {
      SearchBean searchBean = new SearchBean();
      searchBean.setId(id);
      searchBean.setTableName(tableName);
      searchBean.setMethodName("deleteIndex");
      SearchContext.getInstance().sendSearch(searchBean);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public static void reConstructIndex() {
    try {
      SearchBean searchBean = new SearchBean();
      searchBean.setMethodName("reConstructIndex");
      SearchContext.getInstance().sendSearch(searchBean);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public static void commit() {
    try {
      SearchBean searchBean = new SearchBean();
      searchBean.setMethodName("commit");
      SearchContext.getInstance().sendSearch(searchBean);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public static String getIfActiveUpdateDelete() {
    return ifActiveUpdateDelete;
  }
  
  public static String getiSearchSwitch() {
    return iSearchSwitch;
  }
}
