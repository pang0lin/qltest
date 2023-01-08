package com.js.oa.hr.kq.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class ImportInfo {
  public String[] importData(String flag, String filePath) throws Exception {
    String sql = "";
    String dataType = "";
    String dateString = "";
    if (!"excel".equals(flag)) {
      String[] kqInfo = getKqInfo();
      sql = kqInfo[1];
      dataType = kqInfo[0];
      if ("".equals(flag)) {
        dateString = (new SimpleDateFormat(kqInfo[2])).format(new Date((new Date()).getTime() - 86400000L));
        sql = sql.replace("@日期@", kqInfo[3].replace("@日期@", dateString));
      } else if ("all".equals(flag)) {
        sql = String.valueOf(sql.substring(0, sql.toLowerCase().indexOf(" where "))) + " " + 
          sql.substring(sql.toLowerCase().indexOf(" @日期@") + 5) + " ";
      } else if ("m".equals(flag)) {
        dateString = (new SimpleDateFormat(kqInfo[4])).format(new Date());
        sql = sql.replace("@日期@", kqInfo[5].replace("@日期@", dateString)
            .replace("《（", "<").replace("）》", ">")
            .replace("当天", (new SimpleDateFormat(kqInfo[2])).format(new Date())));
      } else if (flag.length() > 9) {
        sql = sql.replace("@日期@", "='" + flag + "'");
      } else {
        sql = sql.replace("@日期@", " like '%" + flag + "%'");
      } 
      System.out.println("获得考勤信息的sql语句：" + sql);
    } 
    String[] result = (String[])null;
    try {
      Class<?> cls = Class.forName("com.js.oa.hr.kq.util.ImportKqData");
      Constructor<?> ct = cls.getConstructor(null);
      Class[] arg = new Class[3];
      arg[0] = String[].class;
      arg[1] = String.class;
      arg[2] = String.class;
      Method meth = cls.getMethod("importData", arg);
      Object retobj = ct.newInstance(null);
      Object[] arglist = new Object[3];
      (new String[2])[0] = sql;
      (new String[2])[1] = dataType;
      arglist[0] = new String[2];
      arglist[1] = flag;
      arglist[2] = filePath;
      result = (String[])meth.invoke(retobj, arglist);
    } catch (Throwable e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  private String[] getKqInfo() {
    String[] kqInfo = new String[6];
    String path = System.getProperty("user.dir");
    String configFile = String.valueOf(path) + "/jsconfig/kqconfig.xml";
    SAXBuilder builder = new SAXBuilder();
    Document doc = null;
    try {
      doc = builder.build(configFile);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    Element kq = doc.getRootElement();
    kqInfo[0] = kq.getAttributeValue("base");
    kqInfo[1] = kq.getChildText("sql");
    Element yesterday = kq.getChild("yesterday");
    kqInfo[2] = yesterday.getAttributeValue("type");
    kqInfo[3] = yesterday.getText();
    Element month = kq.getChild("month");
    kqInfo[4] = month.getAttributeValue("type");
    kqInfo[5] = month.getText();
    return kqInfo;
  }
}
