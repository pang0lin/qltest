package sms;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
  private String serviceURL = "http://sdk2.entinfo.cn/webservice.asmx";
  
  private String sn = "";
  
  private String password = "";
  
  private String pwd = "";
  
  public Client(String sn, String password) throws UnsupportedEncodingException {
    this.sn = sn;
    this.password = password;
    this.pwd = getMD5(String.valueOf(sn) + password);
  }
  
  public String getMD5(String sourceStr) throws UnsupportedEncodingException {
    String resultStr = "";
    try {
      byte[] temp = sourceStr.getBytes();
      MessageDigest md5 = MessageDigest.getInstance("MD5");
      md5.update(temp);
      byte[] b = md5.digest();
      for (int i = 0; i < b.length; i++) {
        char[] digit = { 
            '0', '1', '2', '3', '4', '5', '6', '7', '8', 
            '9', 
            'A', 'B', 'C', 'D', 'E', 'F' };
        char[] ob = new char[2];
        ob[0] = digit[b[i] >>> 4 & 0xF];
        ob[1] = digit[b[i] & 0xF];
        resultStr = String.valueOf(resultStr) + new String(ob);
      } 
      return resultStr;
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public String register(String province, String city, String trade, String entname, String linkman, String phone, String mobile, String email, String fax, String address, String postcode) {
    String result = "";
    String soapAction = "http://tempuri.org/Register";
    String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
    xml = String.valueOf(xml) + "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">";
    xml = String.valueOf(xml) + "<soap12:Body>";
    xml = String.valueOf(xml) + "<Register xmlns=\"http://tempuri.org/\">";
    xml = String.valueOf(xml) + "<sn>" + this.sn + "</sn>";
    xml = String.valueOf(xml) + "<pwd>" + this.password + "</pwd>";
    xml = String.valueOf(xml) + "<province>" + province + "</province>";
    xml = String.valueOf(xml) + "<city>" + city + "</city>";
    xml = String.valueOf(xml) + "<trade>" + trade + "</trade>";
    xml = String.valueOf(xml) + "<entname>" + entname + "</entname>";
    xml = String.valueOf(xml) + "<linkman>" + linkman + "</linkman>";
    xml = String.valueOf(xml) + "<phone>" + phone + "</phone>";
    xml = String.valueOf(xml) + "<mobile>" + mobile + "</mobile>";
    xml = String.valueOf(xml) + "<email>" + email + "</email>";
    xml = String.valueOf(xml) + "<fax>" + fax + "</fax>";
    xml = String.valueOf(xml) + "<address>" + address + "</address>";
    xml = String.valueOf(xml) + "<postcode>" + postcode + "</postcode>";
    xml = String.valueOf(xml) + "</Register>";
    xml = String.valueOf(xml) + "</soap12:Body>";
    xml = String.valueOf(xml) + "</soap12:Envelope>";
    try {
      URL url = new URL(this.serviceURL);
      URLConnection connection = url.openConnection();
      HttpURLConnection httpconn = (HttpURLConnection)connection;
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      bout.write(xml.getBytes());
      byte[] b = bout.toByteArray();
      httpconn.setRequestProperty("Content-Length", 
          String.valueOf(b.length));
      httpconn.setRequestProperty("Content-Type", 
          "text/xml; charset=gb2312");
      httpconn.setRequestProperty("SOAPAction", soapAction);
      httpconn.setRequestMethod("POST");
      httpconn.setDoInput(true);
      httpconn.setDoOutput(true);
      OutputStream out = httpconn.getOutputStream();
      out.write(b);
      out.close();
      InputStreamReader isr = new InputStreamReader(httpconn
          .getInputStream());
      BufferedReader in = new BufferedReader(isr);
      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        Pattern pattern = 
          Pattern.compile("<RegisterResult>(.*)</RegisterResult>");
        Matcher matcher = pattern.matcher(inputLine);
        while (matcher.find())
          result = matcher.group(1); 
      } 
      in.close();
      return new String(result.getBytes(), "utf-8");
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    } 
  }
  
  public String chargeFee(String cardno, String cardpwd) {
    String result = "";
    String soapAction = "http://tempuri.org/ChargUp";
    String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
    xml = String.valueOf(xml) + "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">";
    xml = String.valueOf(xml) + "<soap12:Body>";
    xml = String.valueOf(xml) + "<ChargUp xmlns=\"http://tempuri.org/\">";
    xml = String.valueOf(xml) + "<sn>" + this.sn + "</sn>";
    xml = String.valueOf(xml) + "<pwd>" + this.password + "</pwd>";
    xml = String.valueOf(xml) + "<cardno>" + cardno + "</cardno>";
    xml = String.valueOf(xml) + "<cardpwd>" + cardpwd + "</cardpwd>";
    xml = String.valueOf(xml) + "</ChargUp>";
    xml = String.valueOf(xml) + "</soap12:Body>";
    xml = String.valueOf(xml) + "</soap12:Envelope>";
    try {
      URL url = new URL(this.serviceURL);
      URLConnection connection = url.openConnection();
      HttpURLConnection httpconn = (HttpURLConnection)connection;
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      bout.write(xml.getBytes());
      byte[] b = bout.toByteArray();
      httpconn.setRequestProperty("Content-Length", 
          String.valueOf(b.length));
      httpconn.setRequestProperty("Content-Type", 
          "text/xml; charset=gb2312");
      httpconn.setRequestProperty("SOAPAction", soapAction);
      httpconn.setRequestMethod("POST");
      httpconn.setDoInput(true);
      httpconn.setDoOutput(true);
      OutputStream out = httpconn.getOutputStream();
      out.write(b);
      out.close();
      InputStreamReader isr = new InputStreamReader(httpconn
          .getInputStream());
      BufferedReader in = new BufferedReader(isr);
      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        Pattern pattern = 
          Pattern.compile("<ChargUpResult>(.*)</ChargUpResult>");
        Matcher matcher = pattern.matcher(inputLine);
        while (matcher.find())
          result = matcher.group(1); 
      } 
      in.close();
      return new String(result.getBytes(), "utf-8");
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    } 
  }
  
  public String getBalance() {
    String result = "";
    String soapAction = "http://tempuri.org/balance";
    String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
    xml = String.valueOf(xml) + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
    xml = String.valueOf(xml) + "<soap:Body>";
    xml = String.valueOf(xml) + "<balance xmlns=\"http://tempuri.org/\">";
    xml = String.valueOf(xml) + "<sn>" + this.sn + "</sn>";
    xml = String.valueOf(xml) + "<pwd>" + this.pwd + "</pwd>";
    xml = String.valueOf(xml) + "</balance>";
    xml = String.valueOf(xml) + "</soap:Body>";
    xml = String.valueOf(xml) + "</soap:Envelope>";
    try {
      URL url = new URL(this.serviceURL);
      URLConnection connection = url.openConnection();
      HttpURLConnection httpconn = (HttpURLConnection)connection;
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      bout.write(xml.getBytes());
      byte[] b = bout.toByteArray();
      httpconn.setRequestProperty("Content-Length", 
          String.valueOf(b.length));
      httpconn.setRequestProperty("Content-Type", 
          "text/xml; charset=gb2312");
      httpconn.setRequestProperty("SOAPAction", soapAction);
      httpconn.setRequestMethod("POST");
      httpconn.setDoInput(true);
      httpconn.setDoOutput(true);
      OutputStream out = httpconn.getOutputStream();
      out.write(b);
      out.close();
      InputStreamReader isr = new InputStreamReader(httpconn
          .getInputStream());
      BufferedReader in = new BufferedReader(isr);
      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        Pattern pattern = 
          Pattern.compile("<balanceResult>(.*)</balanceResult>");
        Matcher matcher = pattern.matcher(inputLine);
        while (matcher.find())
          result = matcher.group(1); 
      } 
      in.close();
      return new String(result.getBytes());
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    } 
  }
  
  public String mt(String mobile, String content, String ext, String stime, String rrid) {
    String result = "";
    String soapAction = "http://tempuri.org/mt";
    String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
    xml = String.valueOf(xml) + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
    xml = String.valueOf(xml) + "<soap:Body>";
    xml = String.valueOf(xml) + "<mt xmlns=\"http://tempuri.org/\">";
    xml = String.valueOf(xml) + "<sn>" + this.sn + "</sn>";
    xml = String.valueOf(xml) + "<pwd>" + this.pwd + "</pwd>";
    xml = String.valueOf(xml) + "<mobile>" + mobile + "</mobile>";
    xml = String.valueOf(xml) + "<content>" + content + "</content>";
    xml = String.valueOf(xml) + "<ext>" + ext + "</ext>";
    xml = String.valueOf(xml) + "<stime>" + stime + "</stime>";
    xml = String.valueOf(xml) + "<rrid>" + rrid + "</rrid>";
    xml = String.valueOf(xml) + "</mt>";
    xml = String.valueOf(xml) + "</soap:Body>";
    xml = String.valueOf(xml) + "</soap:Envelope>";
    try {
      URL url = new URL(this.serviceURL);
      URLConnection connection = url.openConnection();
      HttpURLConnection httpconn = (HttpURLConnection)connection;
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      bout.write(xml.getBytes());
      byte[] b = bout.toByteArray();
      httpconn.setRequestProperty("Content-Length", 
          String.valueOf(b.length));
      httpconn.setRequestProperty("Content-Type", 
          "text/xml; charset=gb2312");
      httpconn.setRequestProperty("SOAPAction", soapAction);
      httpconn.setRequestMethod("POST");
      httpconn.setDoInput(true);
      httpconn.setDoOutput(true);
      OutputStream out = httpconn.getOutputStream();
      out.write(b);
      out.close();
      InputStreamReader isr = new InputStreamReader(httpconn
          .getInputStream());
      BufferedReader in = new BufferedReader(isr);
      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        Pattern pattern = Pattern.compile("<mtResult>(.*)</mtResult>");
        Matcher matcher = pattern.matcher(inputLine);
        while (matcher.find())
          result = matcher.group(1); 
      } 
      return result;
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    } 
  }
  
  public String mo() {
    String result = "";
    String soapAction = "http://tempuri.org/mo";
    String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
    xml = String.valueOf(xml) + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
    xml = String.valueOf(xml) + "<soap:Body>";
    xml = String.valueOf(xml) + "<mo xmlns=\"http://tempuri.org/\">";
    xml = String.valueOf(xml) + "<sn>" + this.sn + "</sn>";
    xml = String.valueOf(xml) + "<pwd>" + this.pwd + "</pwd>";
    xml = String.valueOf(xml) + "</mo>";
    xml = String.valueOf(xml) + "</soap:Body>";
    xml = String.valueOf(xml) + "</soap:Envelope>";
    try {
      URL url = new URL(this.serviceURL);
      URLConnection connection = url.openConnection();
      HttpURLConnection httpconn = (HttpURLConnection)connection;
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      bout.write(xml.getBytes());
      byte[] b = bout.toByteArray();
      httpconn.setRequestProperty("Content-Length", 
          String.valueOf(b.length));
      httpconn.setRequestProperty("Content-Type", 
          "text/xml; charset=gb2312");
      httpconn.setRequestProperty("SOAPAction", soapAction);
      httpconn.setRequestMethod("POST");
      httpconn.setDoInput(true);
      httpconn.setDoOutput(true);
      OutputStream out = httpconn.getOutputStream();
      out.write(b);
      out.close();
      InputStream isr = httpconn.getInputStream();
      StringBuffer buff = new StringBuffer();
      byte[] byte_receive = new byte[10240];
      for (int i = 0; (i = isr.read(byte_receive)) != -1;)
        buff.append(new String(byte_receive, 0, i)); 
      isr.close();
      String result_before = buff.toString();
      int start = result_before.indexOf("<moResult>");
      int end = result_before.indexOf("</moResult>");
      result = result_before.substring(start + 10, end);
      return result;
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    } 
  }
  
  public String gxmt(String mobile, String content, String ext, String stime, String rrid) {
    String result = "";
    String soapAction = "http://tempuri.org/gxmt";
    String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
    xml = String.valueOf(xml) + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
    xml = String.valueOf(xml) + "<soap:Body>";
    xml = String.valueOf(xml) + "<gxmt xmlns=\"http://tempuri.org/\">";
    xml = String.valueOf(xml) + "<sn>" + this.sn + "</sn>";
    xml = String.valueOf(xml) + "<pwd>" + this.pwd + "</pwd>";
    xml = String.valueOf(xml) + "<mobile>" + mobile + "</mobile>";
    xml = String.valueOf(xml) + "<content>" + content + "</content>";
    xml = String.valueOf(xml) + "<ext>" + ext + "</ext>";
    xml = String.valueOf(xml) + "<stime>" + stime + "</stime>";
    xml = String.valueOf(xml) + "<rrid>" + rrid + "</rrid>";
    xml = String.valueOf(xml) + "</gxmt>";
    xml = String.valueOf(xml) + "</soap:Body>";
    xml = String.valueOf(xml) + "</soap:Envelope>";
    try {
      URL url = new URL(this.serviceURL);
      URLConnection connection = url.openConnection();
      HttpURLConnection httpconn = (HttpURLConnection)connection;
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      bout.write(xml.getBytes());
      byte[] b = bout.toByteArray();
      httpconn.setRequestProperty("Content-Length", 
          String.valueOf(b.length));
      httpconn.setRequestProperty("Content-Type", 
          "text/xml; charset=gb2312");
      httpconn.setRequestProperty("SOAPAction", soapAction);
      httpconn.setRequestMethod("POST");
      httpconn.setDoInput(true);
      httpconn.setDoOutput(true);
      OutputStream out = httpconn.getOutputStream();
      out.write(b);
      out.close();
      InputStreamReader isr = new InputStreamReader(httpconn
          .getInputStream());
      BufferedReader in = new BufferedReader(isr);
      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        Pattern pattern = 
          Pattern.compile("<gxmtResult>(.*)</gxmtResult>");
        Matcher matcher = pattern.matcher(inputLine);
        while (matcher.find())
          result = matcher.group(1); 
      } 
      return result;
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    } 
  }
  
  public String UnRegister() {
    String result = "";
    String soapAction = "http://tempuri.org/UnRegister";
    String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
    xml = String.valueOf(xml) + "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">";
    xml = String.valueOf(xml) + "<soap12:Body>";
    xml = String.valueOf(xml) + "<UnRegister xmlns=\"http://tempuri.org/\">";
    xml = String.valueOf(xml) + "<sn>" + this.sn + "</sn>";
    xml = String.valueOf(xml) + "<pwd>" + this.password + "</pwd>";
    xml = String.valueOf(xml) + "<cardno>" + this.sn + "</cardno>";
    xml = String.valueOf(xml) + "<cardpwd>" + this.pwd + "</cardpwd>";
    xml = String.valueOf(xml) + "</UnRegister>";
    xml = String.valueOf(xml) + "</soap12:Body>";
    xml = String.valueOf(xml) + "</soap12:Envelope>";
    try {
      URL url = new URL(this.serviceURL);
      URLConnection connection = url.openConnection();
      HttpURLConnection httpconn = (HttpURLConnection)connection;
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      bout.write(xml.getBytes());
      byte[] b = bout.toByteArray();
      httpconn.setRequestProperty("Content-Length", 
          String.valueOf(b.length));
      httpconn.setRequestProperty("Content-Type", 
          "text/xml; charset=utf-8");
      httpconn.setRequestProperty("SOAPAction", soapAction);
      httpconn.setRequestMethod("POST");
      httpconn.setDoInput(true);
      httpconn.setDoOutput(true);
      OutputStream out = httpconn.getOutputStream();
      out.write(b);
      out.close();
      InputStreamReader isr = new InputStreamReader(httpconn
          .getInputStream());
      BufferedReader in = new BufferedReader(isr);
      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        Pattern pattern = 
          Pattern.compile("<UnRegisterResult>String</UnRegisterResult>");
        Matcher matcher = pattern.matcher(inputLine);
        while (matcher.find())
          result = matcher.group(1); 
      } 
      in.close();
      return new String(result.getBytes());
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    } 
  }
}
