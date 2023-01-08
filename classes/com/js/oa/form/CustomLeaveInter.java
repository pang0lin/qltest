package com.js.oa.form;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.namespace.QName;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

public class CustomLeaveInter {
  private static String key = "ZkMhYprHJ9ZvBMCpzsT3+1YfsDXkcy6R7iEniGcg8Ug8fMlpbBDWHw==";
  
  private static String flag = "cncec";
  
  public static void main(String[] args) {
    String url = "http://192.168.0.12:8081/jsoa/services/ProcessService";
    String nameSpace = "http://form.oa.js.com";
    String method = "createNewProcess";
    String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><WorkFlow><Process processId=\"363\" /><UserName submitName=\"dingfy\" receiveName=\"dingfy\" /><Data><Table tableId=\"666\" tableName=\"jst_3479\"><Column><field name=\"jst_3479_f6953\" type=\"varchar\">2016-04-11</field><field name=\"jst_3479_f6954\" type=\"varchar\">11:03:10</field><field name=\"jst_3479_f6955\" type=\"varchar\">2016-04-11 10:03:10</field><field name=\"jst_3479_f6956\" type=\"varchar\">2016-04-13</field></Column></Table></Data></WorkFlow>";
    System.out.println("xml:" + xml);
    try {
      RPCServiceClient serviceClient = new RPCServiceClient();
      Options options = serviceClient.getOptions();
      EndpointReference targetEPR = new EndpointReference(url);
      options.setTo(targetEPR);
      Object[] paras = { xml };
      Class[] classes = { String.class };
      QName opAddEntry = new QName(nameSpace, method);
      Object[] obj = serviceClient.invokeBlocking(opAddEntry, paras, classes);
      String result = (String)obj[0];
      System.out.println(result);
    } catch (AxisFault e) {
      e.printStackTrace();
    } 
  }
  
  public String getRemainHolidays(String url, String method, String nameSpace, String userId, String xjlb, String startDate, String endDate) {
    String result = null;
    try {
      String xmlStr = getXmlStr(userId, xjlb, startDate, endDate);
      Object[] paras = { key, flag, xmlStr };
      Class[] classes = { String.class };
      String rXml = invokeWebService(url, method, nameSpace, paras, classes);
      result = parseXml(rXml);
    } catch (Exception e) {
      result = "查询可休天数失败！";
      e.printStackTrace();
    } 
    return result;
  }
  
  private String parseXml(String xmlStr) throws Exception {
    String result = null;
    StringReader read = new StringReader(xmlStr);
    InputSource source = new InputSource(read);
    SAXBuilder builder = new SAXBuilder();
    Document doc = builder.build(source);
    Element root = doc.getRootElement();
    Element infoNode = root.getChild("info");
    String info = infoNode.getText();
    if ("ok".equals(info)) {
      Element remaindaysNode = root.getChild("remaindays");
      String remaindays = remaindaysNode.getText();
      result = "可休天数为" + remaindays + "天";
    } else {
      result = info;
    } 
    return result;
  }
  
  private static String getXmlStr(String userId, String xjlb, String startDate, String endDate) {
    StringBuffer xmlStr = new StringBuffer();
    Date nowDate = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String dateString = formatter.format(nowDate);
    xmlStr.append("<?xml version='1.0' encoding='GB2312'?>");
    xmlStr.append("<ehr>");
    xmlStr.append("<userid>").append(userId).append("</userid>");
    xmlStr.append("<htype>").append(xjlb).append("</htype>");
    xmlStr.append("<hdate>").append(dateString).append("</hdate>");
    xmlStr.append("<sdate>").append(startDate).append("</sdate>");
    xmlStr.append("<edate>").append(endDate).append("</edate>");
    xmlStr.append("</ehr>");
    System.out.println(xmlStr.toString());
    return xmlStr.toString();
  }
  
  private static String invokeWebService(String url, String method, String nameSpace, Object[] paras, Class[] classes) {
    String result = null;
    try {
      RPCServiceClient serviceClient = new RPCServiceClient();
      Options options = serviceClient.getOptions();
      EndpointReference targetEPR = new EndpointReference(url);
      options.setTo(targetEPR);
      QName opQName = new QName(nameSpace, method);
      Object[] obj = serviceClient.invokeBlocking(opQName, paras, classes);
      result = (String)obj[0];
      System.out.println(result);
      return result;
    } catch (Exception e) {
      result = "查询可休天数失败！请联系管理员！";
      e.printStackTrace();
      return result;
    } 
  }
}
