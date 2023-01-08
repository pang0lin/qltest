package com.js.sso.eyou;

import com.js.util.util.MD5;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

public class MailAPI {
  private static String api_key;
  
  private static String api_secret;
  
  private static String api_server;
  
  private static String server;
  
  private static String defaultDomain;
  
  public MailAPI() {
    if (api_key == null)
      loadProperty(); 
  }
  
  private void loadProperty() {
    try {
      ResourceBundle resource = ResourceBundle.getBundle("eyoumail");
      api_key = resource.getString("api_key");
      String apiSecret = resource.getString("api_secret");
      api_secret = (new MD5()).getMD5ofStr(apiSecret).toLowerCase();
      api_server = resource.getString("api_server");
      server = resource.getString("server");
      defaultDomain = resource.getString("default_domain");
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public String getSSOURL(String userName, String domainName) {
    StringBuffer ssoURL = new StringBuffer();
    ssoURL.append("http://" + server + "/api/sso/login?");
    ssoURL.append("auth_type=simple");
    ssoURL.append("&auth_key=").append(api_key);
    ssoURL.append("&auth_timestamp=" + getTimestamp());
    ssoURL.append("&email=").append(userName).append("@").append(domainName);
    ssoURL.append("&auth_signature=" + getAuth_signature(userName, domainName));
    return ssoURL.toString();
  }
  
  public String getSSOURL(String userName) {
    return getSSOURL(userName, defaultDomain);
  }
  
  public boolean userIsExist(String userName, String domainName) {
    boolean exist = false;
    String serviceURL = "http://" + api_server + "/api/admin/domain/" + domainName + "/user/" + userName;
    String auth = "simple auth_key=\"" + api_key + "\",auth_timestamp=\"" + getTimestamp() + 
      "\",auth_signature=\"" + getAuth_signatureNoEmail() + "\"";
    try {
      URL url = new URL(serviceURL);
      URLConnection connection = url.openConnection();
      HttpURLConnection httpconn = (HttpURLConnection)connection;
      httpconn.setRequestProperty("Host", api_server);
      httpconn.setRequestProperty("Authorization", auth);
      httpconn.setRequestProperty("Content-Length", "0");
      httpconn.setRequestMethod("GET");
      httpconn.setDoInput(true);
      int code = httpconn.getResponseCode();
      if (code == 200)
        exist = true; 
    } catch (Exception e) {
      e.printStackTrace();
      exist = false;
    } 
    return exist;
  }
  
  public boolean userIsExist(String userName) {
    return userIsExist(userName, defaultDomain);
  }
  
  public String getDefaultPage() {
    return "http://" + server;
  }
  
  private long getTimestamp() {
    long time = System.currentTimeMillis();
    time /= 1000L;
    return time;
  }
  
  private String getAuth_signature(String userName, String domainName) {
    StringBuffer signature = new StringBuffer();
    signature.append(api_secret);
    signature.append(api_key);
    signature.append(getTimestamp());
    signature.append(userName).append("@").append(domainName);
    return (new MD5()).getMD5ofStr(signature.toString()).toLowerCase();
  }
  
  private String getAuth_signatureNoEmail() {
    StringBuffer signature = new StringBuffer();
    signature.append(api_secret);
    signature.append(api_key);
    signature.append(getTimestamp());
    return (new MD5()).getMD5ofStr(signature.toString()).toLowerCase();
  }
  
  public static void main(String[] args) {
    MailAPI eYou = new MailAPI();
    System.out.println(eYou.getSSOURL("liw", "demo.eyou.net"));
  }
}
