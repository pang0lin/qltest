package com.js.oa.form.hqzd;

import com.js.oa.form.Workflow;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public class ReimburseService extends Workflow {
  public String complete(HttpServletRequest request) {
    String result = super.complete(request);
    String recordId = request.getParameter("recordId");
    String tableId = request.getParameter("tableId");
    System.out.println("招待费用报销单");
    String json = getParamData(recordId);
    String urltemp = "http://119.254.146.199:7080/cgi_bin/order/reimburse/add";
    SignUtil ss = new SignUtil();
    String msg = ss.postData(json, urltemp);
    System.out.println("msg=" + msg);
    if (msg.indexOf("操作成功") < 0) {
      SystemCommon.setHqzdUpdata("true");
      ss.saveData("reimburse", recordId, tableId, msg);
    } else if (ss.getRows() == 0) {
      SystemCommon.setHqzdUpdata("");
    } 
    return result;
  }
  
  public String getParamData(String recordId) {
    SignUtil ss = new SignUtil();
    String sql = "select jst_3013_f3161,jst_3013_f3504,jst_3353_f4895,jst_3013_f3164,jst_3353_f4900,jst_3013_f3198 from jst_3353 where jst_3353_id = ?";
    Connection conn = null;
    PreparedStatement pstmt = null;
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    Map<String, String> map = new HashMap<String, String>();
    JSONObject json = new JSONObject();
    String requestTime = getRequestTime();
    String sign = "";
    String billNo = "";
    String date = "";
    String productLineNo = "";
    String payeeInfo = "";
    String payeeNo = "";
    String payeeName = "";
    String itemDeptInfo = "";
    String itemDeptNo = "";
    String itemDeptName = "";
    String remark = "";
    JSONObject bodyEntity = new JSONObject();
    List<JSONObject> EntityList = new ArrayList();
    String itemNo = "F001";
    String itemName = "招待费";
    double amount = 0.0D;
    String secret = "KjRn+#eazfK9jaQE";
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, recordId);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        billNo = rs.getString(1);
        date = rs.getString(2);
        productLineNo = rs.getString(3);
        payeeInfo = rs.getString(4);
        int index = payeeInfo.indexOf(";");
        if (index > 0) {
          payeeNo = payeeInfo.substring(index + 1);
          payeeName = payeeInfo.substring(0, index);
        } 
        itemDeptInfo = rs.getString(5);
        index = itemDeptInfo.indexOf(";");
        if (index > 0) {
          itemDeptNo = itemDeptInfo.substring(index + 1);
          itemDeptName = itemDeptInfo.substring(0, index);
        } 
        itemDeptNo = itemDeptInfo.substring(itemDeptInfo.indexOf(";") + 1);
        amount = Double.valueOf(rs.getString(6)).doubleValue();
        bodyEntity.put("itemNo", itemNo);
        bodyEntity.put("itemName", itemName);
        bodyEntity.put("amount", String.format("%.2f", new Object[] { Double.valueOf(amount) }));
        EntityList.add(bodyEntity);
        map.put("requestTime", requestTime);
        map.put("billNo", billNo);
        map.put("date", date);
        map.put("productLineNo", productLineNo);
        map.put("payeeNo", payeeNo);
        map.put("payeeName", payeeName);
        map.put("itemDeptNo", itemDeptNo);
        map.put("itemDeptName", itemDeptName);
        map.put("remark", remark);
        sign = SignUtil.getSign(map, secret);
        json.put("requestTime", requestTime);
        json.put("sign", sign);
        json.put("billNo", billNo);
        json.put("date", date);
        json.put("productLineNo", productLineNo);
        json.put("payeeNo", payeeNo);
        json.put("payeeName", payeeName);
        json.put("itemDeptNo", itemDeptNo);
        json.put("itemDeptName", itemDeptName);
        json.put("remark", remark);
        json.put("bodyEntity", EntityList);
      } 
      rs.close();
      pstmt.close();
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
  
  private void postData(String urltemp, String json) {
    String temp = "";
    JSONObject jsonObject = null;
    StringBuffer buffer = new StringBuffer();
    try {
      URL url = new URL(urltemp);
      HttpURLConnection connection = (HttpURLConnection)url.openConnection();
      connection.setDoOutput(true);
      connection.setRequestProperty("Content-Type", "application/json");
      OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
      out.write(json);
      out.flush();
      out.close();
      InputStream in = connection.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
      while ((temp = reader.readLine()) != null)
        buffer.append(temp); 
      jsonObject = JSONObject.fromObject(buffer.toString());
      String result = "state：" + jsonObject.get("state") + ",code：" + jsonObject.get("code") + ",msg:" + jsonObject.get("msg");
      in.close();
      reader.close();
      connection.disconnect();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public String getRequestTime() {
    Calendar nowtime = new GregorianCalendar();
    String year = String.format("%04d", new Object[] { Integer.valueOf(nowtime.get(1)) });
    String month = String.format("%02d", new Object[] { Integer.valueOf(nowtime.get(2) + 1) });
    String date = String.format("%02d", new Object[] { Integer.valueOf(nowtime.get(5)) });
    String hour = String.format("%02d", new Object[] { Integer.valueOf(nowtime.get(11)) });
    String minute = String.format("%02d", new Object[] { Integer.valueOf(nowtime.get(12)) });
    String second = String.format("%02d", new Object[] { Integer.valueOf(nowtime.get(13)) });
    String millisecond = String.format("%02d", new Object[] { Integer.valueOf(nowtime.get(14)) });
    String requestTime = String.valueOf(year) + month + date + hour + minute + second + millisecond;
    return requestTime;
  }
  
  public String getJsonData(String recordId) {
    return getParamData(recordId);
  }
}
