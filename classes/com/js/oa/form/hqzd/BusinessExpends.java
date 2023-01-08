package com.js.oa.form.hqzd;

import com.js.oa.form.Workflow;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public class BusinessExpends extends Workflow {
  public String complete(HttpServletRequest request) {
    String result = super.complete(request);
    String recordId = request.getParameter("recordId");
    String tableId = request.getParameter("tableId");
    String json = getdata(recordId);
    System.out.println("差旅费报销单");
    SignUtil ss = new SignUtil();
    String urltemp = "http://119.254.146.199:7080/cgi_bin/order/reimburse/add";
    String msg = ss.postData(json, urltemp);
    System.out.println("msg=" + msg);
    if (msg.indexOf("操作成功") < 0) {
      SystemCommon.setHqzdUpdata("true");
      ss.saveData("business", recordId, tableId, msg);
    } else if (ss.getRows() == 0) {
      SystemCommon.setHqzdUpdata("");
    } 
    return result;
  }
  
  private String getdata(String recordId) {
    String sql = "SELECT jst_3005_f3083,jst_3005_f3084,jst_3357_f4896,jst_3357_f4173,jst_3357_f4903,jst_3357_f4275,jst_3005_f3097 FROM jst_3357 where jst_3357_id=?";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    DataSourceBase base = new DataSourceBase();
    Map<String, String> map = new HashMap<String, String>();
    JSONObject json = new JSONObject();
    List<JSONObject> bodyEntity = new ArrayList();
    String secret = "KjRn+#eazfK9jaQE";
    String billNo = "", date = "", itemDeptNo = "", itemDeptName = "", payeeNo = "", payeeName = "", pruductLineNo = "", remark = "";
    SignUtil ss = new SignUtil();
    String requestTime = ss.getRequestTime();
    String md5data = "";
    double amount = 0.0D;
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, recordId);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        billNo = rs.getString(1);
        date = rs.getString(2);
        pruductLineNo = rs.getString(3);
        payeeNo = rs.getString(4).substring(rs.getString(4).indexOf(";") + 1);
        payeeName = rs.getString(4).substring(0, rs.getString(4).indexOf(";"));
        itemDeptNo = rs.getString(5).substring(rs.getString(5).indexOf(";") + 1);
        itemDeptName = rs.getString(5).substring(0, rs.getString(5).indexOf(";"));
        remark = rs.getString(7);
        map.put("requestTime", requestTime);
        map.put("billNo", billNo);
        map.put("date", date);
        map.put("productLineNo", pruductLineNo);
        map.put("payeeNo", payeeNo);
        map.put("payeeName", payeeName);
        map.put("itemDeptNo", itemDeptNo);
        map.put("itemDeptName", itemDeptName);
        map.put("remark", remark);
        md5data = SignUtil.getSign(map, secret);
        json.put("requestTime", requestTime);
        json.put("sign", md5data);
        json.put("billNo", billNo);
        json.put("date", date);
        json.put("productLineNo", pruductLineNo);
        json.put("payeeNo", payeeNo);
        json.put("payeeName", payeeName);
        json.put("itemDeptNo", itemDeptNo);
        json.put("itemDeptName", itemDeptName);
        json.put("remark", remark);
        JSONObject jsonsub = new JSONObject();
        jsonsub.put("itemNo", "F002");
        jsonsub.put("itemName", "OA差旅费");
        amount = Double.valueOf(rs.getString(6)).doubleValue();
        jsonsub.put("amount", String.format("%.2f", new Object[] { Double.valueOf(amount) }));
        bodyEntity.add(jsonsub);
        json.put("bodyEntity", bodyEntity);
      } 
      pstmt.close();
      rs.close();
      conn.close();
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
    return json.toString();
  }
  
  public String getJsonData(String recordId) {
    return getdata(recordId);
  }
}
