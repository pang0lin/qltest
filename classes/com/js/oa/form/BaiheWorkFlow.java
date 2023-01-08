package com.js.oa.form;

import com.js.util.util.CharacterTool;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.codehaus.xfire.client.Client;
import org.w3c.dom.Document;

public class BaiheWorkFlow extends Workflow {
  public Map save(HttpServletRequest request) {
    return super.save(request);
  }
  
  public String update(HttpServletRequest request) {
    String str = super.update(request);
    urlConnection(request, "0");
    return str;
  }
  
  public void back(HttpServletRequest request) {
    super.back(request);
    urlConnection(request, "2");
  }
  
  public String complete(HttpServletRequest request) {
    String str = super.complete(request);
    urlConnection(request, "1");
    return str;
  }
  
  private void urlConnection(HttpServletRequest request, String isEnd) {
    String urlString = "http://avatar.baihe.com/approvalProcessInfo.action?";
    StringBuffer sb = new StringBuffer();
    try {
      String paraString = "processeId=" + URLEncoder.encode(request.getParameter("processId"), "utf-8");
      paraString = String.valueOf(paraString) + "&tableId=" + request.getParameter("tableId");
      paraString = String.valueOf(paraString) + "&recordId=" + request.getParameter("recordId");
      paraString = String.valueOf(paraString) + "&operatorName=" + URLEncoder.encode(request.getSession().getAttribute("userName").toString(), "utf-8");
      String comment = (request.getParameter("include_comment") == null) ? "" : request.getParameter("include_comment");
      paraString = String.valueOf(paraString) + "&description=" + URLEncoder.encode(CharacterTool.escapeHTMLTags2(comment), "utf-8");
      paraString = String.valueOf(paraString) + "&point=" + URLEncoder.encode(request.getParameter("curActivityName"), "utf-8");
      if ("0".equals(isEnd)) {
        paraString = String.valueOf(paraString) + "&status=" + URLEncoder.encode("在办", "utf-8");
      } else if ("2".equals(isEnd)) {
        paraString = String.valueOf(paraString) + "&status=" + URLEncoder.encode("退回", "utf-8");
      } else {
        paraString = String.valueOf(paraString) + "&status=" + URLEncoder.encode("办理完毕", "utf-8");
      } 
      paraString = String.valueOf(paraString) + "&examineTime=" + URLEncoder.encode((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()), "utf-8");
      paraString = String.valueOf(paraString) + "&isEnd=" + isEnd;
      System.out.println("URL:" + urlString + paraString);
      URL url = new URL(String.valueOf(urlString) + paraString);
      URLConnection urlConnection = url.openConnection();
      BufferedReader br = new BufferedReader(new InputStreamReader(
            urlConnection.getInputStream(), "utf-8"));
      String line = null;
      while ((line = br.readLine()) != null)
        sb.append(String.valueOf(line) + "\n"); 
      br.close();
    } catch (MalformedURLException e) {
      System.out.println("不能连接到URL：" + urlString);
      e.printStackTrace();
    } catch (IOException e) {
      System.out.println("连接到URL抛出异常信息：" + urlString);
      e.printStackTrace();
    } catch (Exception err) {
      err.printStackTrace();
    } 
    System.out.println(sb.toString());
  }
  
  public void urlClient(String method, HttpServletRequest request, String isEnd) {
    Object[] paras = { Long.valueOf(request.getParameter("processId")), 
        Long.valueOf(request.getParameter("tableId")), 
        Long.valueOf(request.getParameter("recordeId")), 
        request.getSession().getAttribute("userName"), 
        CharacterTool.escapeHTMLTags2((request.getParameter("include_comment") == null) ? "" : request.getParameter("include_comment")), 
        request.getParameter("curActivityName"), 
        "0".equals(isEnd) ? "在办" : ("1".equals(isEnd) ? "办理完毕" : "退回"), (
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()), 
        Integer.valueOf(isEnd) };
    try {
      Client c = new Client(new URL("http://avatar.baihe.com/approvalProcessInfo.action"));
      Object[] results = c.invoke(method, paras);
      if ("[#document: null]".equals(results[0])) {
        Document d = (Document)results[0];
        System.out.println(d.getFirstChild().getFirstChild().getTextContent());
        System.out.println(d.getFirstChild().getLastChild().getTextContent());
      } else {
        System.out.println((String)results[0]);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
