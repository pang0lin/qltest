package com.js.oa.hgydyy.utils;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentProducer;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.impl.client.DefaultHttpClient;
import small.danfer.sso.http.HttpSingleSignOnService;

public class XmlUtils {
  public static final HttpSingleSignOnService service = new HttpSingleSignOnService();
  
  public static Map loadInfo(String url) {
    try {
      DefaultHttpClient httpClient = new DefaultHttpClient();
      HttpPost httppost = new HttpPost(url);
      File file = new File("F:/json/useradd.xml");
      InputStreamReader in = new InputStreamReader(new FileInputStream(file), "UTF-8");
      BufferedReader bfreader = new BufferedReader(in);
      final StringBuffer buffer = new StringBuffer();
      String line;
      while ((line = bfreader.readLine()) != null)
        buffer.append(line); 
      ContentProducer cp = new ContentProducer() {
          public void writeTo(OutputStream outstream) throws IOException {
            Writer writer = new OutputStreamWriter(outstream, "UTF-8");
            writer.write(buffer.toString());
            writer.flush();
          }
        };
      EntityTemplate entityTemplate = new EntityTemplate(cp);
      httppost.setEntity((HttpEntity)entityTemplate);
      CloseableHttpResponse closeableHttpResponse = httpClient.execute((HttpUriRequest)httppost);
      httpClient.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return null;
  }
  
  public static void main(String[] args) {
    loadInfo("http://localhost:8080/jsoa/services/UserSynServlet?type=dataSyn");
  }
  
  public static Map loadInfoByJson(String jsonStr) {
    Gson gson = new Gson();
    Map<String, String> map = (Map<String, String>)gson.fromJson(jsonStr, HashMap.class);
    return map;
  }
  
  public static List<Map> ConvertXMLtoJSON(String xml) {
    String jsonStr = "";
    List<Map> list = null;
    try {
      XMLSerializer xmlSerializer = new XMLSerializer();
      JSON json = xmlSerializer.read(xml);
      jsonStr = json.toString(1);
      list = (List<Map>)(new Gson()).fromJson(jsonStr, ArrayList.class);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return list;
  }
}
