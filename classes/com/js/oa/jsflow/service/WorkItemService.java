package com.js.oa.jsflow.service;

import com.js.system.util.StaticParam;
import com.js.util.util.DataSourceBase;
import java.io.StringBufferInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.xml.namespace.QName;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class WorkItemService {
  public String getWorkItem(String xml) {
    StringBuffer result = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?><WorkItem>");
    Connection conn = null;
    Statement stmt = null;
    String[] searchInfo = getUserInfo(xml);
    String userAccount = searchInfo[0];
    try {
      if (!"".equals(userAccount)) {
        conn = (new DataSourceBase()).getDataSource().getConnection();
        stmt = conn.createStatement();
        ResultSet rs = null;
        userAccount = StaticParam.getEmpIdByAccount(userAccount);
        String sql = "select wf_work_id,worktitle,worksubmitperson,worksubmittime,workcurstep from jsf_work where workstatus=0 and wf_curemployee_id=" + userAccount + " order by emergence desc, wf_work_id desc";
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
          String workId = rs.getString(1);
          String itemTitle = rs.getString(2);
          String submitPerson = rs.getString(3);
          String submitTime = rs.getString(4);
          String curActivity = rs.getString(5);
          result.append("<ItemData>");
          result.append("<WorkId>").append(workId).append("</WorkId>");
          result.append("<Title>").append(itemTitle).append("</Title>");
          result.append("<SubmitPerson>").append(submitPerson).append("</SubmitPerson>");
          result.append("<SubmitTime>").append(submitTime).append("</SubmitTime>");
          result.append("<CurActivity>").append(curActivity).append("</CurActivity>");
          result.append("<ItemURL>/jsoa/jsflow/item/jump_dealwith.jsp?status=0&amp;workId=").append(workId).append("</ItemURL>");
          result.append("</ItemData>");
        } 
        rs.close();
        stmt.close();
        conn.close();
      } else {
        result.append("<ErrorMessage>User Not Found</ErrorMessage>");
      } 
    } catch (Exception e) {
      try {
        if (stmt != null)
          stmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception ex) {
        System.out.println("getPreActivity close conn error:" + ex);
      } 
      e.printStackTrace();
    } 
    result.append("</WorkItem>");
    return result.toString();
  }
  
  private String[] getUserInfo(String xml) {
    String[] userInfo = { "", "" };
    try {
      StringBufferInputStream sin = new StringBufferInputStream(xml);
      SAXBuilder builder = new SAXBuilder();
      builder.setExpandEntities(false);
      Document doc = builder.build(sin);
      Element root = doc.getRootElement();
      Element node = root.getChild("UserAccount");
      userInfo[0] = node.getText();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return userInfo;
  }
  
  public static void main(String[] args) {
    WorkItemService ws = new WorkItemService();
    String xml = "<?xml version=\"1.0\" encoding=\"GBK\"?><SearchInfo><UserAccount>dingli</UserAccount></SearchInfo>";
    ws.getUserInfo(xml);
    ws.invokeWebService("http://127.0.0.1:8089/jsoa/services/WorkItemService?wsdl", "getWorkItem", xml, "http://service.jsflow.oa.js.com");
  }
  
  public void invokeWebService(String url, String method, String para, String nameSpace) {
    Object[] paras = new Object[1];
    Class[] classes = new Class[1];
    paras[0] = para;
    classes[0] = String.class;
    try {
      RPCServiceClient serviceClient = new RPCServiceClient();
      Options options = serviceClient.getOptions();
      EndpointReference targetEPR = new EndpointReference(url);
      options.setTo(targetEPR);
      QName opQName = new QName(nameSpace, method);
      Object[] obj = serviceClient.invokeBlocking(opQName, paras, classes);
      String result = (String)obj[0];
      System.out.println(result);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
