package com.js.oa.portal.util;

import com.js.oa.portal.pojo.GwToDo;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLAnalyzer {
  public static void main(String[] args) {
    String xml = "<?xml version='1.0' encoding='GBK'?><DATA>  <TODO>     <TITLE>标题1</TITLE>     <HREF>链接1</HREF>     <FROM_USER>送交人1</FROM_USER>     <DATE>日期1</DATE>     <TYPE_NAME>公文类别1</TYPE_NAME>  </TODO>  <TODO>     <TITLE>标题1</TITLE>     <HREF>链接1</HREF>     <FROM_USER>送交人1</FROM_USER>     <DATE>日期1</DATE>     <TYPE_NAME>公文类别1</TYPE_NAME>  </TODO></DATA>";
    try {
      List<GwToDo> results = getToDoListForGW(xml);
      System.out.println(results.size());
    } catch (DocumentException e) {
      e.printStackTrace();
    } 
  }
  
  public static List<GwToDo> getToDoListForGW(String xml) throws DocumentException {
    List<GwToDo> results = new ArrayList<GwToDo>();
    SAXReader saxReader = new SAXReader();
    Document doc = saxReader.read(new StringReader(xml));
    Element root = doc.getRootElement();
    List<Element> list = root.elements();
    for (int i = 0; i < list.size(); i++) {
      Element todoElement = list.get(i);
      List<Element> e = todoElement.elements();
      GwToDo todo = new GwToDo();
      for (int j = 0; j < e.size(); j++) {
        Element item = e.get(j);
        if ("title".equalsIgnoreCase(item.getName())) {
          todo.setTitle(item.getText());
        } else if ("href".equalsIgnoreCase(item.getName())) {
          todo.setHref(item.getText());
        } else if ("from_user".equalsIgnoreCase(item.getName())) {
          todo.setFrom_User(item.getText());
        } else if ("date".equalsIgnoreCase(item.getName())) {
          todo.setDate(item.getText());
        } else if ("type_name".equalsIgnoreCase(item.getName())) {
          todo.setType_name(item.getText());
        } 
      } 
      results.add(todo);
    } 
    return results;
  }
}
