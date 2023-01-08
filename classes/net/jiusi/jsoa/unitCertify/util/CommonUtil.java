package net.jiusi.jsoa.unitCertify.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CommonUtil {
  public String getStrByUrl(String webURL) {
    InputStream in = null;
    String re = "";
    try {
      URL url = new URL(webURL);
      HttpURLConnection conn = (HttpURLConnection)url.openConnection();
      conn.setRequestProperty("encoding", "UTF-8");
      conn.setRequestProperty("Charset", "UTF-8");
      conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
      conn.setDoOutput(true);
      in = conn.getInputStream();
      String contentType = conn.getHeaderField("Content-Type");
      String charset = null;
      byte b;
      int i;
      String[] arrayOfString;
      for (i = (arrayOfString = contentType.replace(" ", "").split(";")).length, b = 0; b < i; ) {
        String param = arrayOfString[b];
        if (param.startsWith("charset=")) {
          charset = param.split("=", 2)[1];
          break;
        } 
        b++;
      } 
      if (charset != null) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
          reader = new BufferedReader(new InputStreamReader(in, charset));
          while ((line = reader.readLine()) != null)
            sb.append(String.valueOf(line) + "\n"); 
        } catch (IOException e) {
          e.printStackTrace();
        } finally {
          try {
            in.close();
          } catch (IOException e) {
            e.printStackTrace();
          } 
        } 
        re = sb.toString();
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return re;
  }
}
