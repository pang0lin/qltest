package com.js.util.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpClientVisitPage {
  public static void main(String[] args) throws IOException {
    Map<String, String> paraMap = new HashMap<String, String>();
    paraMap.put("userName", "用户");
    paraMap.put("userPassword", "111111");
    System.out.println(visitPage("http", "192.168.0.110", 80, "/jsoa/CheckUser.do", paraMap, "get", "gbk"));
  }
  
  public static String visitPage(String protocol, String host, int port, String method, Map<String, String> paraMap, String methodType) {
    return visitPage(protocol, host, port, method, paraMap, methodType, "utf-8");
  }
  
  public static String visitPage(String protocol, String host, int port, String method, Map<String, String> paraMap, String methodType, String charset) {
    String response = "";
    try {
      HttpClient client = new HttpClient();
      client.getHostConfiguration().setHost(host, port, protocol);
      HttpMethod httpMethod = null;
      if ("post".equalsIgnoreCase(methodType)) {
        httpMethod = getPostMethod(method, paraMap, charset);
      } else {
        if (paraMap != null && paraMap.size() > 0) {
          if (!method.contains("?"))
            method = String.valueOf(method) + "?1=1"; 
          for (String key : paraMap.keySet())
            method = String.valueOf(method) + "&" + key + "=" + URLEncoder.encode(paraMap.get(key), charset); 
        } 
        httpMethod = getGetMethod(method);
      } 
      client.executeMethod(httpMethod);
      response = new String(httpMethod.getResponseBodyAsString().getBytes(charset));
      httpMethod.releaseConnection();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return response;
  }
  
  private static HttpMethod getGetMethod(String uri) {
    return (HttpMethod)new GetMethod(String.valueOf(uri) + "&random=" + Math.random());
  }
  
  private static HttpMethod getPostMethod(String uri, Map<String, String> paraMap, String charset) {
    PostMethod post = new PostMethod(uri);
    if (paraMap != null) {
      NameValuePair[] paras = new NameValuePair[paraMap.size()];
      int i = 0;
      for (String key : paraMap.keySet()) {
        paras[i] = new NameValuePair(key, paraMap.get(key));
        i++;
      } 
      post.setRequestBody(paras);
    } 
    HttpMethodParams params = new HttpMethodParams();
    params.setContentCharset(charset);
    post.setParams(params);
    return (HttpMethod)post;
  }
}
