package com.js.oa.form.hzzf;

import com.js.oa.form.Workflow;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class RS003_Service extends Workflow {
  public Map save(HttpServletRequest request) {
    Map map = super.save(request);
    String recordId = (String)map.get("id");
    String processStatus = "0";
    String xml = getXmlData(recordId, processStatus);
    String sendStatus = SendPostUtil.sendFile(xml);
    if ("F".equals(sendStatus) || "ERROR".equals(sendStatus))
      SendPostUtil.insertFailRecord(recordId, xml, processStatus); 
    return map;
  }
  
  public String complete(HttpServletRequest request) {
    String result = super.complete(request);
    String recordId = request.getParameter("recordId");
    String processStatus = "1";
    String xml = getXmlData(recordId, processStatus);
    String sendStatus = SendPostUtil.sendFile(xml);
    if ("F".equals(sendStatus) || "ERROR".equals(sendStatus))
      SendPostUtil.insertFailRecord(recordId, xml, processStatus); 
    return result;
  }
  
  public String getXmlData(String recordId, String processStatus) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String xml = "";
    DataSourceBase base = new DataSourceBase();
    String sql = "SELECT e.useraccounts,e.empname,t.jst_3010_f3094,t.jst_3010_f3095,t.jst_3010_f3093 from jst_3129 t left join org_employee e on t.jst_3129_owner=e.emp_id where t.jst_3129_id=?";
    String userAccount = "", empName = "", startTime = "", endTime = "", type = "";
    StringBuffer buffer = new StringBuffer();
    String des = "���ύ";
    if ("1".equals(processStatus))
      des = "�������"; 
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, recordId);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        userAccount = rs.getString(1);
        empName = rs.getString(2);
        startTime = rs.getString(3);
        endTime = rs.getString(4);
        type = rs.getString(5);
      } 
      rs.close();
      pstmt.close();
      conn.close();
      buffer.append("<intg_Input xmlns='http://xmlns.oracle.com/apps/cux/rest/transferdata/transfer_data/'>");
      buffer.append("<RESTHeader xmlns='http://xmlns.oracle.com/apps/cux/rest/TransferData/header'>");
      buffer.append("<Responsibility>CUX_CUSTOM</Responsibility>");
      buffer.append("<RespApplication>CUX</RespApplication>");
      buffer.append("<SecurityGroup>STANDARD</SecurityGroup>");
      buffer.append("<NLSLanguage>SIMPLIFIED CHINESE</NLSLanguage>");
      buffer.append("<Org_Id/>");
      buffer.append("</RESTHeader>");
      buffer.append("<InputParameters>");
      buffer.append("<P_INTG_BATCH_REC>");
      buffer.append("<EXT_BATCH_ID>" + recordId + "</EXT_BATCH_ID>");
      buffer.append("<INTERFACE_CODE>OAQINJIA</INTERFACE_CODE>");
      buffer.append("<SOURCE_SYSTEM>OA</SOURCE_SYSTEM>");
      buffer.append("<ROW_COUNT>1</ROW_COUNT>");
      buffer.append("<DESCRIPTION></DESCRIPTION>");
      buffer.append("</P_INTG_BATCH_REC>");
      buffer.append("<P_INTG_DATA_TBL>");
      buffer.append("<P_INTG_DATA_TBL_ITEM>");
      buffer.append("<EXT_LINE_ID>" + recordId + "</EXT_LINE_ID>");
      buffer.append("<VALUE1>RS003������뵥</VALUE1>");
      buffer.append("<VALUE2>" + userAccount + "</VALUE2>");
      buffer.append("<VALUE3>" + empName + "</VALUE3>");
      buffer.append("<VALUE4>" + startTime + "</VALUE4>");
      buffer.append("<VALUE5>" + endTime + "</VALUE5>");
      buffer.append("<VALUE6>" + des + "</VALUE6>");
      buffer.append("<VALUE7>" + type + "</VALUE7>");
      buffer.append("<VALUE8></VALUE8>");
      buffer.append("</P_INTG_DATA_TBL_ITEM>");
      buffer.append("</P_INTG_DATA_TBL>");
      buffer.append("</InputParameters>");
      buffer.append("</intg_Input>");
      xml = buffer.toString();
    } catch (Exception e) {
      SendPostUtil.free(rs, pstmt, conn);
      e.printStackTrace();
    } 
    System.out.println("xml:" + xml);
    return xml;
  }
}
