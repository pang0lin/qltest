package com.js.oa.form;

import com.js.oa.jsflow.util.InitWorkFlowData;
import com.js.util.util.DataSourceBase;
import java.net.URL;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.codehaus.xfire.client.Client;
import org.w3c.dom.Document;

public class FlowWebservice {
  public void executeWebservice(HttpServletRequest request, String node) {
    String[][] webservicInfo = (String[][])null;
    String sql = "";
    if ("node".equals(node)) {
      String curActivityId = request.getParameter("curActivityId");
      sql = "SELECT webServiceUrl,webServiceMethod,webServicePara,webServiceNameSpace FROM jsf_activity WHERE WF_ACTIVITY_ID=" + curActivityId;
    } else if ("save".equals(node)) {
      String processId = request.getParameter("processId");
      sql = "SELECT startUrl,startMethod,startPara,startNameSpace FROM jsf_workflowprocess WHERE wf_workflowprocess_id=" + processId;
    } else {
      String processId = request.getParameter("processId");
      sql = "SELECT completeUrl,completeMethod,completePara,completeNameSpace FROM jsf_workflowprocess WHERE wf_workflowprocess_id=" + processId;
    } 
    if (!"".equals(sql))
      webservicInfo = (new DataSourceBase()).queryArrayBySql(sql); 
    if (webservicInfo != null && webservicInfo.length > 0 && 
      !"".equals(webservicInfo[0][0]) && !"".equals(webservicInfo[0][1]) && 
      !"null".equals(webservicInfo[0][0]) && !"null".equals(webservicInfo[0][1])) {
      String param = InitWorkFlowData.getValueFromRequest(request, webservicInfo[0][2]);
      callWebService(webservicInfo[0][0], webservicInfo[0][1], param, webservicInfo[0][3]);
    } 
  }
  
  public void executeWebservice(Map request, String node) {
    String[][] webservicInfo = (String[][])null;
    String sql = "";
    if ("node".equals(node)) {
      String curActivityId = (String)request.get("curActivityId");
      sql = "SELECT webServiceUrl,webServiceMethod,webServicePara,webServiceNameSpace FROM jsf_activity WHERE WF_ACTIVITY_ID=" + curActivityId;
    } else if ("save".equals(node)) {
      String processId = (String)request.get("processId");
      sql = "SELECT startUrl,startMethod,startPara,startNameSpace FROM jsf_workflowprocess WHERE wf_workflowprocess_id=" + processId;
    } else {
      String processId = (String)request.get("processId");
      sql = "SELECT completeUrl,completeMethod,completePara,completeNameSpace FROM jsf_workflowprocess WHERE wf_workflowprocess_id=" + processId;
    } 
    if (!"".equals(sql))
      webservicInfo = (new DataSourceBase()).queryArrayBySql(sql); 
    if (webservicInfo != null && webservicInfo.length > 0 && 
      !"".equals(webservicInfo[0][0]) && !"".equals(webservicInfo[0][1]) && 
      !"null".equals(webservicInfo[0][0]) && !"null".equals(webservicInfo[0][1])) {
      String param = InitWorkFlowData.getValueFromRequest(request, webservicInfo[0][2]);
      callWebService(webservicInfo[0][0], webservicInfo[0][1], param, webservicInfo[0][3]);
    } 
  }
  
  public void callWebService(String url, String method, String para, String nameSpace) {
    if (!"".equals(url) && !"".equals(method)) {
      System.out.println("WebService路径：" + url);
      System.out.println("WebService函数名：" + method);
      System.out.println("WebService参数：" + para);
      System.out.println("WebService命名空间：" + nameSpace);
      if ("".equals(nameSpace)) {
        xfireWebService(url, method, para);
      } else {
        invokeWebService(url, method, para, nameSpace);
      } 
    } 
  }
  
  public static void main(String[] aaa) {
    (new FlowWebservice()).invokeWebService("http://office.pyvs.edu.cn/jsoa/services/InfoService", "getInfo", "zm5460,11", "http://util.util.js.com");
  }
  
  public void xfireWebService(String url, String method, String para) {
    Object[][] parasInfo = getParas(para);
    Object[] paras = new Object[parasInfo.length];
    for (int i = 0; i < parasInfo.length; i++)
      paras[i] = parasInfo[i][0]; 
    try {
      Client c = new Client(new URL(url));
      Object[] results = c.invoke(method, paras);
      if ("[#document: null]".equals(results[0])) {
        Document d = (Document)results[0];
        System.out.println(d.getFirstChild().getFirstChild().getTextContent());
        System.out.println(d.getFirstChild().getLastChild().getTextContent());
      } else {
        System.out.println((String)results[0]);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void invokeWebService(String url, String method, String para, String nameSpace) {
    Object[][] parasInfo = getParas(para);
    Object[] paras = new Object[parasInfo.length];
    Class[] classes = new Class[parasInfo.length];
    for (int i = 0; i < parasInfo.length; i++) {
      paras[i] = parasInfo[i][0];
      classes[i] = (Class)parasInfo[i][1];
    } 
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
  
  public Object[][] getParas(String para) {
    String[] params = para.split(",");
    Object[][] paras = new Object[params.length][2];
    for (int i = 0; i < paras.length; i++) {
      if (params[i].contains(":")) {
        if (params[i].endsWith(":")) {
          paras[i][0] = "";
          paras[i][1] = String.class;
        } else {
          String[] p = params[i].split(":");
          if (p.length > 2) {
            if ("int".equalsIgnoreCase(p[1])) {
              paras[i][0] = Integer.valueOf(p[2]);
              paras[i][1] = Integer.class;
            } else if ("float".equalsIgnoreCase(p[1])) {
              paras[i][0] = Float.valueOf(p[2]);
              paras[i][1] = Float.class;
            } else if ("long".equalsIgnoreCase(p[1])) {
              paras[i][0] = Long.valueOf(p[2]);
              paras[i][1] = Long.class;
            } else if ("double".equalsIgnoreCase(p[1])) {
              paras[i][0] = Double.valueOf(p[2]);
              paras[i][1] = Double.class;
            } 
          } else {
            paras[i][0] = p[1];
            paras[i][1] = String.class;
          } 
        } 
      } else {
        paras[i][0] = params[i];
        paras[i][1] = String.class;
      } 
    } 
    return paras;
  }
}
