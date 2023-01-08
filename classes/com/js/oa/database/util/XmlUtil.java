package com.js.oa.database.util;

import com.js.oa.database.po.DatabaseBackupSetPO;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XmlUtil {
  public String searchBackupType() throws DocumentException {
    String path = System.getProperty("user.dir");
    String configFile = String.valueOf(path) + "/jsconfig/databasebackup.xml";
    File file = new File(configFile);
    if (file.exists()) {
      SAXReader saxReader = new SAXReader();
      Document document1 = saxReader.read(configFile);
      Element rootElm = document1.getRootElement();
      Element backupsetElm = rootElm.element("backupset");
      Element typeElm = backupsetElm.element("type");
      String text = typeElm.getText();
      return text;
    } 
    Document document = DocumentHelper.createDocument();
    Element datebaseElement = document.addElement("datebase");
    datebaseElement.addElement("backups");
    Element backupsetElement = datebaseElement.addElement("backupset");
    Element typeElement = backupsetElement.addElement("type");
    typeElement.setText("0");
    Element amountElement = backupsetElement.addElement("amount");
    amountElement.setText("7");
    OutputFormat format = OutputFormat.createPrettyPrint();
    try {
      XMLWriter output = new XMLWriter(new FileWriter(new File(configFile)), format);
      output.write(document);
      output.close();
    } catch (IOException e) {
      e.printStackTrace();
    } 
    return "0";
  }
  
  public String searchLastBackupDate() throws DocumentException {
    String path = System.getProperty("user.dir");
    String configFile = String.valueOf(path) + "/jsconfig/databasebackup.xml";
    String lastDate = "";
    SAXReader saxReader = new SAXReader();
    Document document = saxReader.read(configFile);
    List<Element> list = document.selectNodes("//datebase/backups/backup");
    if (list.size() > 0) {
      Element backupElm = list.get(list.size() - 1);
      lastDate = backupElm.getText();
    } 
    return lastDate;
  }
  
  public boolean del() throws DocumentException {
    String path = System.getProperty("user.dir");
    String configFile = String.valueOf(path) + "/jsconfig/databasebackup.xml";
    boolean re = false;
    SAXReader saxReader = new SAXReader();
    Document document = saxReader.read(configFile);
    List list = document.selectNodes("//datebase/backups/backup");
    Element rootElm = document.getRootElement();
    Element backupsetElm = rootElm.element("backupset");
    Element amountElm = backupsetElm.element("amount");
    String amount = amountElm.getText();
    int amounti = Integer.parseInt(amount);
    if (list.size() >= amounti)
      re = true; 
    return re;
  }
  
  public String delFirstBackupNode() throws DocumentException, IOException {
    String path = System.getProperty("user.dir");
    String configFile = String.valueOf(path) + "/jsconfig/databasebackup.xml";
    String lastDate = "";
    SAXReader saxReader = new SAXReader();
    Document document = saxReader.read(configFile);
    List<Element> list = document.selectNodes("//datebase/backups/backup");
    Element rootElm = document.getRootElement();
    Element backupsElm = rootElm.element("backups");
    Element backupElm = list.get(0);
    lastDate = backupElm.getText();
    backupsElm.remove(backupElm);
    OutputFormat format = OutputFormat.createPrettyPrint();
    try {
      XMLWriter output = new XMLWriter(new FileWriter(new File(configFile)), format);
      output.write(document);
      output.close();
    } catch (IOException e) {
      e.printStackTrace();
    } 
    return lastDate;
  }
  
  public void addNode(String newdate) throws DocumentException, IOException {
    String path = System.getProperty("user.dir");
    String configFile = String.valueOf(path) + "/jsconfig/databasebackup.xml";
    SAXReader saxReader = new SAXReader();
    Document document = saxReader.read(configFile);
    Element rootElm = document.getRootElement();
    Element backupsElm = rootElm.element("backups");
    Element backupElm = backupsElm.addElement("backup");
    backupElm.addText(newdate);
    OutputFormat format = OutputFormat.createPrettyPrint();
    try {
      XMLWriter output = new XMLWriter(new FileWriter(new File(configFile)), format);
      output.write(document);
      output.close();
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public List getNodeList() throws DocumentException, IOException {
    String path = System.getProperty("user.dir");
    String configFile = String.valueOf(path) + "/jsconfig/databasebackup.xml";
    List<String> datesList = new ArrayList<String>();
    SAXReader saxReader = new SAXReader();
    Document document = saxReader.read(configFile);
    List<Element> list = document.selectNodes("//datebase/backups/backup");
    Iterator<Element> iter = list.iterator();
    while (iter.hasNext()) {
      Element backupElm = iter.next();
      datesList.add(backupElm.getText());
    } 
    return datesList;
  }
  
  public DatabaseBackupSetPO getSet() throws DocumentException, IOException {
    String path = System.getProperty("user.dir");
    String configFile = String.valueOf(path) + "/jsconfig/databasebackup.xml";
    DatabaseBackupSetPO databaseBackupSetPO = new DatabaseBackupSetPO();
    SAXReader saxReader = new SAXReader();
    Document document = saxReader.read(configFile);
    Element rootElm = document.getRootElement();
    Element backupsetElm = rootElm.element("backupset");
    Element typeElm = backupsetElm.element("type");
    Element amountElm = backupsetElm.element("amount");
    databaseBackupSetPO.setType(typeElm.getText());
    databaseBackupSetPO.setAmount(Integer.parseInt(amountElm.getText()));
    return databaseBackupSetPO;
  }
  
  public void set(String type, String amount) throws DocumentException, IOException {
    String path = System.getProperty("user.dir");
    String configFile = String.valueOf(path) + "/jsconfig/databasebackup.xml";
    SAXReader saxReader = new SAXReader();
    Document document = saxReader.read(configFile);
    Element rootElm = document.getRootElement();
    Element backupsetElm = rootElm.element("backupset");
    Element typeElm = backupsetElm.element("type");
    Element amountElm = backupsetElm.element("amount");
    typeElm.setText(type);
    amountElm.setText(amount);
    OutputFormat format = OutputFormat.createPrettyPrint();
    try {
      XMLWriter output = new XMLWriter(new FileWriter(new File(configFile)), format);
      output.write(document);
      output.close();
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public static String selectDatabase() throws DocumentException, IOException {
    String path = System.getProperty("user.dir").toString();
    String path2 = String.valueOf(path) + "/jsconfig/sysconfig.xml";
    String lastDate = "";
    SAXReader saxReader = new SAXReader();
    Document document = saxReader.read(path2);
    List<Attribute> list = document.selectNodes("//JSOA/Database/@type");
    Attribute attribute = list.get(0);
    lastDate = attribute.getText();
    return lastDate;
  }
}
