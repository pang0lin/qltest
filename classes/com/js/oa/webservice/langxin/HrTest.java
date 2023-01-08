package com.js.oa.webservice.langxin;

import javax.xml.namespace.QName;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

public class HrTest {
  public static void main(String[] args) {
    String url = "http://127.0.0.1:8081/jsoa/services/LxHRService?wsdl";
    String nameSpace = "http://tempuri.org/";
    String method = "OrgAdd";
    String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?><ORG><Field ColName=\"DEPT_CODE\" DisName=\"部门编码\" Value=\"101\"></Field><Field ColName=\"CONTENT\" DisName=\"部门名称\" Value=\"百战综合办公室\"></Field><Field ColName=\"B0110\" DisName=\"部门全称\" Value=\"\"></Field><Field ColName=\"KIND\" DisName=\"部门类型\" Value=\"2\"></Field><Field ColName=\"B0180\" DisName=\"部门负责人\" Value=\"5959\"></Field><Field ColName=\"RESTSETID\" DisName=\"休息日套\" Value=\"\"></Field><Field ColName=\"B0111\" DisName=\"所属部门\" Value=\"\"></Field><Field ColName=\"B01026\" DisName=\"部门属性\" Value=\"\"></Field><Field ColName=\"FILENOMBER\" DisName=\"文号\" Value=\"\"></Field><Field ColName=\"FILENAME\" DisName=\"文件名\" Value=\"\"></Field><Field ColName=\"MARK\" DisName=\"备注\" Value=\"\"></Field><Field ColName=\"B01023\" DisName=\"部门分管领导\" Value=\"\"></Field><Field ColName=\"B0145\" DisName=\"隶属关系\" Value=\"\"></Field><Field ColName=\"CONTENTENUS\" DisName=\"部门英文名称\" Value=\"\"></Field><Field ColName=\"B01024\" DisName=\"原编码\" Value=\"0104494\"></Field><Field ColName=\"B01025\" DisName=\"原上级编码\" Value=\"0104767\"></Field><Field ColName=\"QTOPERATER\" DisName=\"操作人\" Value=\"4585\"></Field><Field ColName=\"CONTENTZHTW\" DisName=\"部门繁体名称\" Value=\"\"></Field><Field ColName=\"B01DELETED\" DisName=\"删除标志\" Value=\"0\"></Field><Field ColName=\"CONTENTOTHERLANGUAGE\" DisName=\"部门其它语文名称\" Value=\"\"></Field><Field ColName=\"PARENTCODE\" DisName=\"父代码\" Value=\"1\"></Field><Field ColName=\"ENDMARK\" DisName=\"末级标志\" Value=\"1\"></Field><Field ColName=\"GRADE\" DisName=\"级数\" Value=\"2\"></Field><Field ColName=\"DEPT_ID\" DisName=\"部门\" Value=\"3\"></Field><Field ColName=\"PARENT_ID\" DisName=\"父级部门ID\" Value=\"1\"></Field><Field ColName=\"ORDERID\" DisName=\"排序序号\" Value=\"0\"></Field></ORG>";
    Object[] paras = { xml };
    Class[] classes = { String.class };
    invokeWebService(url, method, nameSpace, paras, classes);
  }
  
  public static void invokeWebService(String url, String method, String nameSpace, Object[] paras, Class[] classes) {
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
