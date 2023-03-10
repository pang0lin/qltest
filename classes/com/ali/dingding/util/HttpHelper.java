package com.ali.dingding.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.RedirectLocations;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class HttpHelper {
  public static JSONObject httpGet(String url) throws OApiException {
    HttpGet httpGet = new HttpGet(url);
    CloseableHttpResponse response = null;
    CloseableHttpClient httpClient = HttpClients.createDefault();
    RequestConfig requestConfig = RequestConfig.custom()
      .setSocketTimeout(2000).setConnectTimeout(2000).build();
    httpGet.setConfig(requestConfig);
    try {
      response = httpClient.execute((HttpUriRequest)httpGet, (HttpContext)new BasicHttpContext());
      if (response.getStatusLine().getStatusCode() != 200) {
        System.out.println("request url failed, http code=" + response.getStatusLine().getStatusCode() + 
            ", url=" + url);
        return null;
      } 
      HttpEntity entity = response.getEntity();
    } catch (IOException e) {
      System.out.println("request url=" + url + ", exception, msg=" + e.getMessage());
      e.printStackTrace();
    } finally {
      if (response != null)
        try {
          response.close();
        } catch (IOException e) {
          e.printStackTrace();
        }  
    } 
    if (response != null)
      try {
        response.close();
      } catch (IOException e) {
        e.printStackTrace();
      }  
    return null;
  }
  
  public static JSONObject httpPost(String url, Object data) throws OApiException {
    HttpPost httpPost = new HttpPost(url);
    CloseableHttpResponse response = null;
    CloseableHttpClient httpClient = HttpClients.createDefault();
    RequestConfig requestConfig = RequestConfig.custom()
      .setSocketTimeout(2000).setConnectTimeout(2000).build();
    httpPost.setConfig(requestConfig);
    httpPost.addHeader("Content-Type", "application/json");
    try {
      StringEntity requestEntity = new StringEntity(JSON.toJSONString(data), "utf-8");
      httpPost.setEntity((HttpEntity)requestEntity);
      response = httpClient.execute((HttpUriRequest)httpPost, (HttpContext)new BasicHttpContext());
      if (response.getStatusLine().getStatusCode() != 200) {
        System.out.println("request url failed, http code=" + response.getStatusLine().getStatusCode() + 
            ", url=" + url);
        return null;
      } 
      HttpEntity entity = response.getEntity();
    } catch (IOException e) {
      System.out.println("request url=" + url + ", exception, msg=" + e.getMessage());
      e.printStackTrace();
    } finally {
      if (response != null)
        try {
          response.close();
        } catch (IOException e) {
          e.printStackTrace();
        }  
    } 
    if (response != null)
      try {
        response.close();
      } catch (IOException e) {
        e.printStackTrace();
      }  
    return null;
  }
  
  public static JSONObject uploadMedia(String url, File file) throws OApiException {
    HttpPost httpPost = new HttpPost(url);
    CloseableHttpResponse response = null;
    CloseableHttpClient httpClient = HttpClients.createDefault();
    RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
    httpPost.setConfig(requestConfig);
    HttpEntity requestEntity = MultipartEntityBuilder.create().addPart("media", 
        (ContentBody)new FileBody(file, ContentType.APPLICATION_OCTET_STREAM, file.getName())).build();
    httpPost.setEntity(requestEntity);
    try {
      response = httpClient.execute((HttpUriRequest)httpPost, (HttpContext)new BasicHttpContext());
      if (response.getStatusLine().getStatusCode() != 200) {
        System.out.println("request url failed, http code=" + response.getStatusLine().getStatusCode() + 
            ", url=" + url);
        return null;
      } 
      HttpEntity entity = response.getEntity();
    } catch (IOException e) {
      System.out.println("request url=" + url + ", exception, msg=" + e.getMessage());
      e.printStackTrace();
    } finally {
      if (response != null)
        try {
          response.close();
        } catch (IOException e) {
          e.printStackTrace();
        }  
    } 
    if (response != null)
      try {
        response.close();
      } catch (IOException e) {
        e.printStackTrace();
      }  
    return null;
  }
  
  public static JSONObject downloadMedia(String url, String fileDir) throws OApiException {
    HttpGet httpGet = new HttpGet(url);
    CloseableHttpResponse response = null;
    CloseableHttpClient httpClient = HttpClients.createDefault();
    RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
    httpGet.setConfig(requestConfig);
    try {
      BasicHttpContext basicHttpContext = new BasicHttpContext();
      response = httpClient.execute((HttpUriRequest)httpGet, (HttpContext)basicHttpContext);
      RedirectLocations locations = (RedirectLocations)basicHttpContext.getAttribute("http.protocol.redirect-locations");
      if (locations != null) {
        URI downloadUrl = locations.getAll().get(0);
        String filename = downloadUrl.toURL().getFile();
        System.out.println("downloadUrl=" + downloadUrl);
        File downloadFile = new File(String.valueOf(fileDir) + File.separator + filename);
        FileUtils.writeByteArrayToFile(downloadFile, EntityUtils.toByteArray(response.getEntity()));
        JSONObject obj = new JSONObject();
        obj.put("downloadFilePath", downloadFile.getAbsolutePath());
        obj.put("httpcode", Integer.valueOf(response.getStatusLine().getStatusCode()));
        return obj;
      } 
      if (response.getStatusLine().getStatusCode() != 200) {
        System.out.println("request url failed, http code=" + response.getStatusLine().getStatusCode() + 
            ", url=" + url);
        return null;
      } 
      HttpEntity entity = response.getEntity();
    } catch (IOException e) {
      System.out.println("request url=" + url + ", exception, msg=" + e.getMessage());
      e.printStackTrace();
    } finally {
      if (response != null)
        try {
          response.close();
        } catch (IOException e) {
          e.printStackTrace();
        }  
    } 
    if (response != null)
      try {
        response.close();
      } catch (IOException e) {
        e.printStackTrace();
      }  
    return null;
  }
}
