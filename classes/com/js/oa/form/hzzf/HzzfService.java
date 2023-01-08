package com.js.oa.form.hzzf;

import com.js.oa.form.Workflow;
import com.js.oa.userdb.util.DbOpt;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

public class HzzfService extends Workflow {
  public String complete(HttpServletRequest request) {
    String result = super.complete(request);
    String recordId = request.getParameter("recordId");
    String tableId = request.getParameter("tableId");
    String[][] mainResult = getFieldValue(recordId, tableId);
    String name = getShName(recordId, tableId);
    String rowCount = getSubRowCount(recordId, tableId);
    String xml = getXml(mainResult, name, rowCount);
    String url = "http://ebs-testapp.iboxpay.com:8020/webservices/rest/TransferData?WADL";
    String method = "transfer_data";
    String para = xml;
    String nameSpace = "http://xmlns.oracle.com/apps/cux/soaprovider/plsql/rest/cux_fnd_intg_data_pub/";
    sendFile(xml);
    return result;
  }
  
  public String[][] getFieldValue(String recordId, String tableId) {
    String Sql = "select jst_3161_id,jst_3158_f4597,jst_3161_f4666,jst_3158_f4596,jst_3158_f4598,jst_3158_f4601,jst_3158_f4602 ,jst_3158_f4603,jst_3158_f4604,jst_3158_f4605,jst_3158_f4606,jst_3161_f4639,jst_3158_f4664,jst_3158_f4619,jst_3161_f4640,jst_3161_f4641,jst_3161_f4637,jst_3161_f4662,jst_3161_f4663,jst_3161_f4636 from jst_3158 left join jst_3161 on jst_3158.jst_3158_id=jst_3161.jst_3161_foreignkey  where jst_3158_id=" + 

      
      recordId;
    DbOpt dbopt = null;
    String[][] mainResult = (String[][])null;
    try {
      dbopt = new DbOpt();
      mainResult = dbopt.executeQueryToStrArr2(Sql, 20);
      dbopt.close();
    } catch (Exception e) {
      if (dbopt != null)
        try {
          dbopt.close();
        } catch (Exception e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return mainResult;
  }
  
  public String getShName(String recordId, String tableId) {
    String sql2 = "select oe.empname,dwc.commentfield from jsf_dealwith dw right join jsf_dealwithcomment dwc on dw.wf_dealwith_id = dwc.wf_dealwith_id left join org_employee oe on dwc.dealwithemployee_id = oe.emp_id where dw.databaserecord_id =" + 
      recordId + " and dw.databasetable_id = " + tableId;
    DbOpt dbopt = null;
    String[][] nameTemp = (String[][])null;
    Object[] obj = (Object[])null;
    String shName = "", cwName = "";
    String nameStr = "";
    try {
      dbopt = new DbOpt();
      nameTemp = dbopt.executeQueryToStrArr2(sql2, 2);
      for (int i = 0; i < nameTemp.length; i++) {
        String[] arrayOfString = nameTemp[i];
        if ("jst_3158_f4620".equals(arrayOfString[1].toString()))
          cwName = arrayOfString[0].toString(); 
        if ("jst_3158_f4667".equals(arrayOfString[1].toString()))
          shName = arrayOfString[0].toString(); 
      } 
      dbopt.close();
    } catch (Exception e) {
      if (dbopt != null)
        try {
          dbopt.close();
        } catch (Exception e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
    nameStr = String.valueOf(cwName) + "," + shName;
    return nameStr;
  }
  
  public String getSubRowCount(String recordId, String tableId) {
    String rowCount = "select count(*) from jst_3158 left join jst_3161 on jst_3158.jst_3158_id = jst_3161.jst_3161_foreignkey where jst_3158_id = " + recordId;
    DbOpt dbopt = null;
    String[][] rowCouunt = (String[][])null;
    Object[] obj = (Object[])null;
    try {
      dbopt = new DbOpt();
      rowCouunt = dbopt.executeQueryToStrArr2(rowCount, 1);
      dbopt.close();
    } catch (Exception e) {
      if (dbopt != null)
        try {
          dbopt.close();
        } catch (Exception e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return rowCouunt[0][0];
  }
  
  public String getXml(String[][] fieldValue, String name, String rowCount) {
    String xml = null;
    Object[] obj = (Object[])null;
    Date date = new Date();
    Long timetemp = Long.valueOf(date.getTime());
    String cwName = name.substring(0, name.indexOf(","));
    String shName = name.substring(name.indexOf(",") + 1);
    StringBuffer buffer = new StringBuffer();
    buffer.append("<intg_Input xmlns='http://xmlns.oracle.com/apps/cux/rest/TransferData/Transfer_Data/'>");
    buffer.append("<RESTHeader xmlns='http://xmlns.oracle.com/apps/cux/rest/TransferData/header'>");
    buffer.append("<Responsibility>CUX_CUSTOM</Responsibility>");
    buffer.append("<RespApplication>CUX</RespApplication>");
    buffer.append("<SecurityGroup>STANDARD</SecurityGroup>");
    buffer.append("<NLSLanguage>SIMPLIFIED CHINESE</NLSLanguage>");
    buffer.append("<Org_Id/>");
    buffer.append("</RESTHeader>");
    buffer.append("<InputParameters>");
    buffer.append("<P_INTG_BATCH_REC>");
    buffer.append("<EXT_BATCH_ID>" + timetemp + "</EXT_BATCH_ID>");
    buffer.append("<INTERFACE_CODE>OAAPINVOICEIMP</INTERFACE_CODE>");
    buffer.append("<SOURCE_SYSTEM>OA</SOURCE_SYSTEM>");
    buffer.append("<ROW_COUNT>" + rowCount + "</ROW_COUNT>");
    buffer.append("<DESCRIPTION>TEST</DESCRIPTION>");
    buffer.append("</P_INTG_BATCH_REC>");
    buffer.append("<P_INTG_DATA_TBL>");
    for (int i = 0; i < fieldValue.length; i++) {
      String[] arrayOfString = fieldValue[i];
      buffer.append("<P_INTG_DATA_TBL_ITEM>");
      buffer.append("<EXT_LINE_ID>" + arrayOfString[0] + "</EXT_LINE_ID>");
      buffer.append("<VALUE1>" + arrayOfString[1].toString() + "</VALUE1>");
      buffer.append("<VALUE2>" + arrayOfString[2].toString() + "</VALUE2>");
      buffer.append("<VALUE3>" + arrayOfString[5].toString() + "</VALUE3>");
      buffer.append("<VALUE4>" + arrayOfString[6].toString() + "</VALUE4>");
      buffer.append("<VALUE5>" + arrayOfString[8].toString() + "</VALUE5>");
      buffer.append("<VALUE6>" + arrayOfString[9].toString() + "</VALUE6>");
      buffer.append("<VALUE7>" + arrayOfString[11].toString() + "</VALUE7>");
      buffer.append("<VALUE8>" + arrayOfString[12].toString() + "</VALUE8>");
      buffer.append("<VALUE9>" + arrayOfString[13].toString() + "</VALUE9>");
      buffer.append("<VALUE10>" + shName + "</VALUE10>");
      buffer.append("<VALUE11>" + cwName + "</VALUE11>");
      buffer.append("<VALUE12>" + arrayOfString[14].toString() + "</VALUE12>");
      buffer.append("<VALUE13>" + arrayOfString[15].toString() + "</VALUE13>");
      buffer.append("<SEGMENT1>" + arrayOfString[3].toString() + "</SEGMENT1>");
      buffer.append("<SEGMENT2>" + arrayOfString[4].toString() + "</SEGMENT2>");
      buffer.append("<SEGMENT3>" + arrayOfString[7].toString() + "</SEGMENT3>");
      buffer.append("<SEGMENT4>" + arrayOfString[10].toString() + "</SEGMENT4>");
      buffer.append("<SEGMENT6>" + arrayOfString[16].toString() + "</SEGMENT6>");
      buffer.append("<SEGMENT7>" + arrayOfString[17].toString() + "</SEGMENT7>");
      buffer.append("<SEGMENT8>" + arrayOfString[18].toString() + "</SEGMENT8>");
      buffer.append("<SEGMENT9>" + arrayOfString[19].toString() + "</SEGMENT9>");
      buffer.append("</P_INTG_DATA_TBL_ITEM>");
    } 
    buffer.append("</P_INTG_DATA_TBL>");
    buffer.append("</InputParameters>");
    buffer.append("</intg_Input>");
    xml = buffer.toString();
    return xml;
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
      System.out.println("result:" + result);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public static void sendFile(String xml) {
    try {
      PostMethod postMethod = new PostMethod("http://172.30.3.150:8020/webservices/rest/TransferData/transfer_data/");
      StringRequestEntity stringRequestEntity = new StringRequestEntity(xml, "application/xml", "utf-8");
      postMethod.setRequestEntity((RequestEntity)stringRequestEntity);
      HttpClient httpClient = new HttpClient();
      UsernamePasswordCredentials creds = new UsernamePasswordCredentials("INTERFACE_USER", "ibox123");
      httpClient.getState().setCredentials(AuthScope.ANY, (Credentials)creds);
      int statusCode = httpClient.executeMethod((HttpMethod)postMethod);
      if (statusCode == 200) {
        System.out.println("调用成功！");
        byte[] soapResponseData = postMethod.getResponseBody();
        String str = new String(soapResponseData, "UTF-8");
      } else {
        System.out.println("调用失败！错误码：" + statusCode);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
