package com.js.oa.form.hzzf;

import com.js.oa.form.Workflow;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class RS005_Service extends Workflow {
  public Map save(HttpServletRequest request) {
    Map map = super.save(request);
    String proStatus = "0";
    String recordId = (String)map.get("id");
    String xml = getXmlData(recordId, proStatus);
    String sendStatus = SendPostUtil.sendFile(xml);
    if ("F".equals(sendStatus) || "ERROR".equals(sendStatus))
      SendPostUtil.insertFailRecord(recordId, xml, proStatus); 
    return map;
  }
  
  public String complete(HttpServletRequest request) {
    String result = super.complete(request);
    String recordId = request.getParameter("recordId");
    String tableId = request.getParameter("tableId");
    String proStatus = "1";
    String xml = getXmlData(recordId, proStatus);
    String sendStatus = SendPostUtil.sendFile(xml);
    if ("F".equals(sendStatus) || "ERROR".equals(sendStatus))
      SendPostUtil.insertFailRecord(recordId, xml, proStatus); 
    return result;
  }
  
  private String getXmlData(String recordId, String proStatus) {
    String sql = "select e.useraccounts,e.empname,t.jst_3067_f3778,t.jst_3067_f3779 from jst_3067 t left join org_employee e on t.jst_3067_owner=e.emp_id where t.jst_3067_id=?";
    String xml = "";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    DataSourceBase base = new DataSourceBase();
    String useraccount = "", empname = "", reason = "", dkTime = "";
    StringBuffer buffer = new StringBuffer();
    String des = "已提交";
    if ("1".equals(proStatus))
      des = "审批完成"; 
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, recordId);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        useraccount = rs.getString(1);
        empname = rs.getString(2);
        reason = rs.getString(3);
        dkTime = rs.getString(4);
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
      buffer.append("<VALUE1>RS005未打卡补签申请表</VALUE1>");
      buffer.append("<VALUE2>" + useraccount + "</VALUE2>");
      buffer.append("<VALUE3>" + empname + "</VALUE3>");
      buffer.append("<VALUE4>" + reason + "</VALUE4>");
      buffer.append("<VALUE5>" + dkTime + "</VALUE5>");
      buffer.append("<VALUE6>" + des + "</VALUE6>");
      buffer.append("<VALUE7></VALUE7>");
      buffer.append("</P_INTG_DATA_TBL_ITEM>");
      buffer.append("</P_INTG_DATA_TBL>");
      buffer.append("</InputParameters>");
      buffer.append("</intg_Input>");
      xml = buffer.toString();
    } catch (SQLException e) {
      try {
        if (rs != null)
          rs.close(); 
        if (pstmt != null)
          pstmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    System.out.println("xml:" + xml);
    return xml;
  }
}
