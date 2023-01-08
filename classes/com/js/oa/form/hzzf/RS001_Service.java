package com.js.oa.form.hzzf;

import com.js.oa.form.Workflow;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class RS001_Service extends Workflow {
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
    String sql = "SELECT e.useraccounts,e.empname,t.jst_3077_f3867,t.jst_3077_f3868 from jst_3139 t left join org_employee e on t.jst_3139_owner=e.emp_id where t.jst_3139_id=?";
    String userAccount = "", userName = "", beginTime = "", endTime = "";
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
        userName = rs.getString(2);
        beginTime = rs.getString(3);
        endTime = rs.getString(4);
      } 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (SQLException e) {
      SendPostUtil.free(rs, pstmt, conn);
      e.printStackTrace();
    } 
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
    buffer.append("<VALUE1>RS001������뵥</VALUE1>");
    buffer.append("<VALUE2>" + userAccount + "</VALUE2>");
    buffer.append("<VALUE3>" + userName + "</VALUE3>");
    buffer.append("<VALUE4>" + beginTime + "</VALUE4>");
    buffer.append("<VALUE5>" + endTime + "</VALUE5>");
    buffer.append("<VALUE6>" + des + "</VALUE6>");
    buffer.append("<VALUE7></VALUE7>");
    buffer.append("</P_INTG_DATA_TBL_ITEM>");
    buffer.append("</P_INTG_DATA_TBL>");
    buffer.append("</InputParameters>");
    buffer.append("</intg_Input>");
    xml = buffer.toString();
    System.out.println("xml:" + xml);
    return xml;
  }
}
