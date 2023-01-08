package com.js.oa.form.hzzf;

import com.js.oa.form.Workflow;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class RS002_Service extends Workflow {
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
    String sql = "select e.useraccounts ,a.jst_3002_f3014, b.jst_3089_f4007,b.jst_3089_f4008,a.jst_3002_f3018 from jst_3128 a,jst_3127 b,org_employee e where a.jst_3128_id=b.jst_3127_foreignkey and a.jst_3128_owner=e.emp_id and a.jst_3128_id=?";
    String userAccount = "", userName = "";
    List<String> beginTime = new ArrayList<String>();
    List<String> endTime = new ArrayList<String>();
    String type = "";
    StringBuffer buffer = new StringBuffer();
    String des = "���ύ";
    if ("1".equals(processStatus))
      des = "�������"; 
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, recordId);
      rs = pstmt.executeQuery();
      int x = 0;
      while (rs.next()) {
        userAccount = rs.getString(1);
        userName = rs.getString(2);
        beginTime.add(rs.getString(3));
        endTime.add(rs.getString(4));
        type = rs.getString(5);
        x++;
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
      buffer.append("<ROW_COUNT>" + x + "</ROW_COUNT>");
      buffer.append("<DESCRIPTION></DESCRIPTION>");
      buffer.append("</P_INTG_BATCH_REC>");
      buffer.append("<P_INTG_DATA_TBL>");
      for (int i = 0; i < x; i++) {
        buffer.append("<P_INTG_DATA_TBL_ITEM>");
        buffer.append("<EXT_LINE_ID>" + recordId + "</EXT_LINE_ID>");
        buffer.append("<VALUE1>RS002�Ӱ������</VALUE1>");
        buffer.append("<VALUE2>" + userAccount + "</VALUE2>");
        buffer.append("<VALUE3>" + userName + "</VALUE3>");
        buffer.append("<VALUE4>" + (String)beginTime.get(i) + "</VALUE4>");
        buffer.append("<VALUE5>" + (String)endTime.get(i) + "</VALUE5>");
        buffer.append("<VALUE6>" + des + "</VALUE6>");
        buffer.append("<VALUE7>" + type + "</VALUE7>");
        buffer.append("<VALUE8></VALUE8>");
        buffer.append("</P_INTG_DATA_TBL_ITEM>");
      } 
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
