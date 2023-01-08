package com.js.oa.webservice.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.util.zip.GZIPInputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;

public class TestAppServlet {
  public static final String TAG = "DataFetchFunc";
  
  public static final int SUCCESS_MORE = 0;
  
  public static final int SUCCESS_DONE = 1;
  
  public static final int ERR_INTERNET = 2;
  
  public static final int ERR_UNKNOWN = 3;
  
  public static final int PAGE_SIZE = 10;
  
  public static synchronized String doHttpPost(String serverURL, String jsonStr) {
    try {
      HttpPost httpPost = new HttpPost(serverURL);
      httpPost.setHeader("Range", "bytes=");
      httpPost.addHeader("Accept-Charset", "utf-8");
      httpPost.addHeader("Content-Type", "text/xml; charset=utf-8");
      StringEntity stringEntity = new StringEntity(jsonStr, "UTF-8");
      httpPost.setEntity((HttpEntity)stringEntity);
      DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
      defaultHttpClient.getParams().setParameter(
          "http.connection.timeout", Integer.valueOf(10000));
      defaultHttpClient.getParams().setParameter("http.socket.timeout", 
          Integer.valueOf(30000));
      HttpResponse response = defaultHttpClient.execute((HttpUriRequest)httpPost);
      InputStream instream = getUngzippedContent(response.getEntity());
      DirectByteArrayOutputStream outstream = new DirectByteArrayOutputStream();
      byte[] buffer = new byte[1024];
      int read = instream.read(buffer);
      while (read > 0) {
        outstream.write(buffer, 0, read);
        read = instream.read(buffer);
      } 
      String s = new String(outstream.getBuf());
      return s;
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (InterruptedIOException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } 
    return "";
  }
  
  public static synchronized String doHttpsPost(String serverURL, String jsonStr) throws Exception {
    BasicHttpParams basicHttpParams = new BasicHttpParams();
    HttpConnectionParams.setConnectionTimeout((HttpParams)basicHttpParams, 3000);
    HttpConnectionParams.setSoTimeout((HttpParams)basicHttpParams, 3000);
    HttpClient hc = initHttpClient((HttpParams)basicHttpParams);
    HttpPost post = new HttpPost(serverURL);
    post.setHeader("Range", "bytes=");
    post.addHeader("Content-Type", "text/xml;charset=utf-8");
    post.addHeader("Accept", "text/xml");
    StringEntity entity = new StringEntity(jsonStr, "UTF-8");
    post.setEntity((HttpEntity)entity);
    post.setParams((HttpParams)basicHttpParams);
    HttpResponse response = null;
    try {
      response = hc.execute((HttpUriRequest)post);
    } catch (UnknownHostException e) {
      throw new Exception("Unable to access " + e.getLocalizedMessage());
    } catch (SocketException e) {
      e.printStackTrace();
    } 
    int sCode = response.getStatusLine().getStatusCode();
    if (sCode == 200)
      return EntityUtils.toString(response.getEntity()); 
    throw new Exception("StatusCode is " + sCode);
  }
  
  public static synchronized HttpClient initHttpClient(HttpParams params) {
    try {
      KeyStore trustStore = KeyStore.getInstance(
          KeyStore.getDefaultType());
      trustStore.load(null, null);
      SSLSocketFactory sf = new SSLSocketFactoryImp(trustStore);
      sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
      HttpProtocolParams.setVersion(params, (ProtocolVersion)HttpVersion.HTTP_1_1);
      HttpProtocolParams.setContentCharset(params, "UTF-8");
      SchemeRegistry registry = new SchemeRegistry();
      registry.register(new Scheme("http", 
            (SocketFactory)PlainSocketFactory.getSocketFactory(), 80));
      registry.register(new Scheme("https", (SocketFactory)sf, 443));
      ThreadSafeClientConnManager threadSafeClientConnManager = new ThreadSafeClientConnManager(
          params, registry);
      return (HttpClient)new DefaultHttpClient((ClientConnectionManager)threadSafeClientConnManager, params);
    } catch (Exception e) {
      e.printStackTrace();
      return (HttpClient)new DefaultHttpClient(params);
    } 
  }
  
  public static InputStream getUngzippedContent(HttpEntity entity) throws IOException {
    InputStream responseStream = entity.getContent();
    if (responseStream == null)
      return responseStream; 
    Header header = entity.getContentEncoding();
    if (header == null)
      return responseStream; 
    String contentEncoding = header.getValue();
    if (contentEncoding == null)
      return responseStream; 
    if (contentEncoding.contains("gzip"))
      responseStream = new GZIPInputStream(responseStream); 
    return responseStream;
  }
  
  public void test() {
    String xmlString = "<?xml version=\"1.0\" encoding=\"GBK\"?><WorkFlow>\t<Action>createNewProcess</Action>    <Process processId=\"259\" />    <UserName submitName=\"zzzayj\" receiveName=\"zs\" />    <Data>        <Table tableId=\"337\" tableName=\"jst_3014\">            <Column>               <field name=\"jst_3014_f3067\" type=\"varchar\">18660118263</field>               <field name=\"jst_3014_f3068\" type=\"varchar\">60.208.159.95</field>               <field name=\"jst_3014_f3069\" type=\"varchar\">主表数据1</field>               <field name=\"jst_3014_f3070\" type=\"varchar\">1333</field>            </Column>        </Table>    </Data></WorkFlow>";
    byte[] xmlData = xmlString.getBytes();
    DataInputStream input = null;
    ByteArrayOutputStream out = null;
    try {
      String urlStr = "http://127.0.0.1:8080/jsoa/services/AppAuthServlet";
      URL url = new URL(urlStr);
      URLConnection urlCon = url.openConnection();
      urlCon.setDoOutput(true);
      urlCon.setDoInput(true);
      urlCon.setUseCaches(false);
      urlCon.setRequestProperty("Content-Type", "text/xml");
      urlCon.setRequestProperty("Content-length", String.valueOf(xmlData.length));
      DataOutputStream printout = new DataOutputStream(urlCon.getOutputStream());
      printout.write(xmlData);
      printout.flush();
      printout.close();
      input = new DataInputStream(urlCon.getInputStream());
      out = new ByteArrayOutputStream();
      byte[] bufferByte = new byte[256];
      int l = -1;
      while ((l = input.read(bufferByte)) > -1) {
        out.write(bufferByte, 0, l);
        out.flush();
      } 
      byte[] rResult = out.toByteArray();
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(new ByteArrayInputStream(rResult));
      String result = doc.getElementsByTagName("result").item(0).getFirstChild().getNodeValue();
      System.out.println("result:" + result);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        out.close();
        input.close();
      } catch (Exception exception) {}
    } 
  }
  
  public static void main(String[] args) {
    String url = "https://admin.enterplorer.yunshipei.com/cgi-bin/message/send";
    String params = "<xml><CompanyId>公司组织代码</CompanyId><CompanyCert>管理员账户电话号码/邮箱</CompanyCert><ToUser>推送目标的邮箱地址,以@公司域名结束</ToUser><ToParty>推送目标的部门/群组名称</ToParty><FromUser>推送发起人的邮箱地址,以@公司域名结束</FromUser><CreateTime>时间戳</CreateTime><MsgType>text (用以标志目标来源的消息类型)</MsgType><Content>消息的内容</Content ></xml>";
    try {
      TestAppServlet sr = new TestAppServlet();
      sr.test();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
