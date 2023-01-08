package com.yunshipei.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.util.zip.GZIPInputStream;
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

public class MessageUtil {
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
}
