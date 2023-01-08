package com.qq.weixin.mp.util.positionservice.impl;

import com.js.oa.weixin.manage.WeixinManageAction;
import com.js.util.config.SystemCommon;
import com.qq.weixin.mp.util.positionservice.PositionService;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PosServiceImpl_baidu implements PositionService {
  private static final String LOCATION_CONVERT_URL = "http://api.map.baidu.com/geoconv/v1/?coords=LONGITUDE,LATITUDE&from=1&to=5&ak=AK";
  
  private static final String POSITION_GET_URL = "http://api.map.baidu.com/geocoder?output=json&location=LATITUDE,%20LONGITUDE&key=AK";
  
  private static final String POSITION_GET_URL_LBS = "http://api.map.baidu.com/cloudrgc/v1?ak=AK&coord_type=bd09ll&location=LATITUDE,LONGITUDE&geotable_id=geoID";
  
  private static final String POSITION_GET_URL_V2 = "http://api.map.baidu.com/geocoder/v2/?location=LATITUDE,LONGITUDE&output=json&pois=0&ak=AK";
  
  public String getPosition(String latitude, String longitude) {
    System.out.println("latitude:" + latitude + "---longitude:" + longitude);
    String position = "";
    String position_lbs = SystemCommon.getPosition_LBS();
    String ak = WeixinManageAction.getPropValue("sBaiduAK");
    String geotable_id = WeixinManageAction.getPropValue("geotable_id");
    String url = "http://api.map.baidu.com/geoconv/v1/?coords=LONGITUDE,LATITUDE&from=1&to=5&ak=AK".replace("LONGITUDE", longitude).replace("LATITUDE", latitude).replace("AK", ak);
    JSONObject jsonObject = httpRequest(url);
    if ("0".equals(jsonObject.getString("status"))) {
      JSONArray ja = jsonObject.getJSONArray("result");
      Double x = Double.valueOf(ja.getJSONObject(0).getDouble("x"));
      Double y = Double.valueOf(ja.getJSONObject(0).getDouble("y"));
      if ("1".equals(position_lbs)) {
        url = "http://api.map.baidu.com/cloudrgc/v1?ak=AK&coord_type=bd09ll&location=LATITUDE,LONGITUDE&geotable_id=geoID".replace("LATITUDE", (CharSequence)y).replace("LONGITUDE", (CharSequence)x).replace("AK", ak).replace("geoID", geotable_id);
        jsonObject = httpRequest(url);
        if ("0".equalsIgnoreCase(jsonObject.getString("status")))
          position = jsonObject.getString("recommended_location_description"); 
      } else {
        url = "http://api.map.baidu.com/geocoder/v2/?location=LATITUDE,LONGITUDE&output=json&pois=0&ak=AK".replace("LATITUDE", (CharSequence)y).replace("LONGITUDE", (CharSequence)x).replace("AK", ak);
        jsonObject = httpRequest(url);
        System.out.println(jsonObject.toString());
        if ("0".equalsIgnoreCase(jsonObject.getString("status")))
          position = jsonObject.getJSONObject("result").getString("formatted_address"); 
      } 
    } 
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
  
  private String getSn(Map<?, ?> map, String type) {
    String sn = "";
    String urltemp = "/geoconv/v1/?";
    if ("1".equals(type)) {
      urltemp = "/geoconv/v1/?";
    } else if ("2".equals(type)) {
      urltemp = "/cloudrgc/v1?";
    } 
    String sk = "dUw4kelB0WIrSxrnzPtnU19TqHBbQCX2";
    try {
      String paramsStr = toQueryStringOther(map);
      String wholeStr = new String(String.valueOf(urltemp) + paramsStr + sk);
      String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
      sn = MD5(tempStr);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } 
    return sn;
  }
  
  public static void main(String[] args) {
    PosServiceImpl_baidu baidu = new PosServiceImpl_baidu();
    String url = "http://api.map.baidu.com/geocoder?output=json&location=36.676804,%20117.129852&key=kZj52xdMGikvkEjZmcXOXuZT";
    JSONObject jsonObject = httpRequest(url);
    System.out.println(jsonObject.toString());
    String position = "";
  }
  
  private String toQueryString(Map<?, ?> data) throws UnsupportedEncodingException {
    StringBuffer queryString = new StringBuffer();
    for (Map.Entry<?, ?> pair : data.entrySet()) {
      queryString.append((new StringBuilder()).append(pair.getKey()).append("=").toString());
      queryString.append(String.valueOf(URLEncoder.encode((String)pair.getValue(), 
              "UTF-8")) + "&");
    } 
    if (queryString.length() > 0)
      queryString.deleteCharAt(queryString.length() - 1); 
    return queryString.toString();
  }
  
  public String toQueryStringOther(Map<?, ?> data) throws UnsupportedEncodingException {
    StringBuffer queryString = new StringBuffer();
    for (Map.Entry<?, ?> pair : data.entrySet()) {
      queryString.append((new StringBuilder()).append(pair.getKey()).append("=").toString());
      String[] ss = pair.getValue().toString().split(",");
      if (ss.length > 1) {
        byte b;
        int i;
        String[] arrayOfString;
        for (i = (arrayOfString = ss).length, b = 0; b < i; ) {
          String s = arrayOfString[b];
          queryString.append(String.valueOf(URLEncoder.encode(s, "UTF-8")) + ",");
          b++;
        } 
        queryString.deleteCharAt(queryString.length() - 1);
        queryString.append("&");
        continue;
      } 
      queryString.append(String.valueOf(URLEncoder.encode((String)pair.getValue(), 
              "UTF-8")) + "&");
    } 
    if (queryString.length() > 0)
      queryString.deleteCharAt(queryString.length() - 1); 
    return queryString.toString();
  }
  
  private String MD5(String md5) {
    try {
      MessageDigest md = 
        MessageDigest.getInstance("MD5");
      byte[] array = md.digest(md5.getBytes());
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < array.length; i++)
        sb.append(Integer.toHexString(array[i] & 0xFF | 0x100)
            .substring(1, 3)); 
      return sb.toString();
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
      return null;
    } 
  }
}
