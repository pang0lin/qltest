package com.qq.weixin.mp.util.positionservice.impl;

import com.qq.weixin.mp.util.positionservice.PositionService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class PosServiceImpl_google implements PositionService {
  private String url = "http://maps.google.com/maps/api/geocode/xml?latlng=latitude,longitude&language=zh-CN&sensor=false";
  
  public String getPosition(String latitude, String longitude) {
    String position = "";
    this.url = this.url.replace("latitude", latitude).replace("longitude", longitude);
    String locationStr = httpRequest(this.url);
    SAXBuilder builder = new SAXBuilder();
    try {
      Document doc = builder.build(new StringReader(locationStr));
      Element root = doc.getRootElement();
      position = root.getChild("result").getChild("formatted_address").getValue();
    } catch (JDOMException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } 
    return position;
  }
  
  public static void main(String[] args) {
    System.out.println((new PosServiceImpl_google()).getPosition("36.664223", "117.055122"));
  }
  
  public String httpRequest(String requestUrl) {
    String result = null;
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
      result = buffer.toString();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
}
