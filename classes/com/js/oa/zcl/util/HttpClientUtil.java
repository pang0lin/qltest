package com.js.oa.zcl.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {
  public String doPost(String url, Map<String, String> map, String charset) {
    HttpClient httpClient = null;
    HttpPost httpPost = null;
    String result = null;
    try {
      SSLClient sSLClient = new SSLClient();
      httpPost = new HttpPost(url);
      List<NameValuePair> list = new ArrayList<NameValuePair>();
      Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
      while (iterator.hasNext()) {
        Map.Entry<String, String> elem = iterator.next();
        list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
      } 
      if (list.size() > 0) {
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
        httpPost.setEntity((HttpEntity)entity);
      } 
      HttpResponse response = sSLClient.execute((HttpUriRequest)httpPost);
      if (response != null) {
        HttpEntity resEntity = response.getEntity();
        if (resEntity != null)
          result = EntityUtils.toString(resEntity, charset); 
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return result;
  }
  
  public String doGet(String url, String charset) {
    if (charset == null)
      charset = "utf-8"; 
    HttpClient httpClient = null;
    HttpGet httpGet = null;
    String result = null;
    try {
      SSLClient sSLClient = new SSLClient();
      httpGet = new HttpGet(url);
      HttpResponse response = sSLClient.execute((HttpUriRequest)httpGet);
      if (response != null) {
        HttpEntity resEntity = response.getEntity();
        if (resEntity != null)
          result = EntityUtils.toString(resEntity, charset); 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
}
