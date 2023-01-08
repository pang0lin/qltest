package com.qq.weixin.mp.util.positionservice.impl;

import com.qq.weixin.mp.util.positionservice.PositionService;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import net.sf.json.JSONObject;

public class PosServiceImpl_jvhe implements PositionService {
  private String GET_POSITION_URL = "http://lbs.juhe.cn/api/getaddressbylngb?lngx=Longitude&lngy=Latitude";
  
  public String getPosition(String latitude, String longitude) {
    String position = "";
    String url = this.GET_POSITION_URL.replace("Latitude", latitude).replace(
        "Longitude", longitude);
    JSONObject result = httpRequest(url);
    if ("1".equals(result.getString("resultcode")))
      position = result.getJSONObject("row").getJSONObject("result")
        .getString("formatted_address"); 
    return position;
  }
  
  public static JSONObject httpRequest(String requestUrl) {
    JSONObject jsonObject = null;
    StringBuffer buffer = new StringBuffer();
    try {
      URL url = new URL(requestUrl);
      HttpURLConnection httpUrlConn = (HttpURLConnection)url
        .openConnection();
      httpUrlConn.setDoOutput(true);
      httpUrlConn.setDoInput(true);
      httpUrlConn.setUseCaches(false);
      httpUrlConn.setRequestMethod("GET");
      httpUrlConn.connect();
      InputStream inputStream = httpUrlConn.getInputStream();
      InputStreamReader inputStreamReader = new InputStreamReader(
          inputStream, "utf-8");
      BufferedReader bufferedReader = new BufferedReader(
          inputStreamReader);
      String str = null;
      while ((str = bufferedReader.readLine()) != null)
        buffer.append(str); 
      bufferedReader.close();
      inputStreamReader.close();
      inputStream.close();
      httpUrlConn.disconnect();
      jsonObject = JSONObject.fromObject(buffer.toString());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return jsonObject;
  }
  
  public static void main(String[] args) {}
}
