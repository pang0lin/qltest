package com.js.system.service.messages;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceUtil;
import com.js.util.util.IO2File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONSerializer;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.math.RandomUtils;

public class MessageAppDaXing {
  public boolean getUserInfoByUserName(String mtoUserIds, int mType, String mContent, String mUrl) throws HttpException, IOException {
    String result = "";
    while (mtoUserIds.startsWith(","))
      mtoUserIds = mtoUserIds.substring(1); 
    while (mtoUserIds.endsWith(","))
      mtoUserIds = mtoUserIds.substring(0, mtoUserIds.length() - 1); 
    mUrl = URLEncoder.encode(mUrl, "utf-8");
    String jumpUlr = String.valueOf(SystemCommon.getDomainName()) + "/jsoa/CheckUser.do?flag=casSSO&TransferUrl=" + mUrl;
    IO2File.printFile(jumpUlr);
    String sql = "SELECT emp_id,empName,useraccounts FROM org_employee WHERE emp_id IN (" + mtoUserIds + ") and USERISACTIVE = 1 AND USERISDELETED = 0 and EMP_ID > 0";
    if (mtoUserIds.equals("-1"))
      sql = "SELECT emp_id,empName,useraccounts FROM org_employee WHERE USERISACTIVE = 1 AND USERISDELETED = 0 and EMP_ID > 0"; 
    IO2File.printFile("人员取值sql：" + sql, "云商店", 3);
    List<String[]> userList = (new DataSourceUtil()).getListQuery(sql, "");
    String url = SystemCommon.getAppUrl();
    if (!url.equals("")) {
      HttpClient httpClient = new HttpClient();
      PostMethod postMethod = new PostMethod(url);
      postMethod.getParams().setParameter("http.protocol.content-charset", "UTF-8");
      String timestamp = (new StringBuilder(String.valueOf((new Date()).getTime()))).toString();
      String nonce = (new StringBuilder(String.valueOf(RandomUtils.nextInt()))).toString();
      String apiKey = SystemCommon.getApiKey();
      String signature = MD5.getMD5Str(String.valueOf(apiKey) + nonce + timestamp);
      List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
      for (int i = 0; i < userList.size(); i++) {
        String[] userInfo = userList.get(i);
        Map<String, Object> message = new HashMap<String, Object>();
        message.put("touser", userInfo[2]);
        message.put("type", Integer.valueOf(mType));
        message.put("content", mContent);
        message.put("url", jumpUlr);
        list.add(message);
      } 
      NameValuePair[] data = { new NameValuePair("appId", SystemCommon.getAppId()), 
          new NameValuePair("timestamp", timestamp), 
          new NameValuePair("nonce", nonce), 
          new NameValuePair("signature", signature), 
          new NameValuePair("message", JSONSerializer.toJSON(list).toString()) };
      postMethod.setRequestBody(data);
      try {
        int statusCode = httpClient.executeMethod((HttpMethod)postMethod);
        if (statusCode != 200) {
          result = "失败";
        } else {
          String res = postMethod.getResponseBodyAsString().trim();
          result = res;
          System.out.println("返回结果:" + res);
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        postMethod.releaseConnection();
      } 
    } 
    if (result.equals("success"))
      return true; 
    return false;
  }
}
