package rtx;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

public class httptest {
  public static void main(String[] args) {
    int iRetCode = 0;
    URLDecoder urld = new URLDecoder();
    try {
      URL url = new URL(
          "http://192.168.1.51:8012/sendsms.cgi?receiver=wwjs1&msg=测试短信");
      HttpURLConnection httpConnection = (HttpURLConnection)url
        .openConnection();
      iRetCode = Integer.parseInt(httpConnection.getHeaderField(3));
      String szEncodeResult = httpConnection.getHeaderField(4);
      String szResult = URLDecoder.decode(szEncodeResult);
      System.out.println("Code:" + String.valueOf(iRetCode));
      System.out.println("Info:" + szResult);
    } catch (Exception e) {
      System.out.println("系统出错" + e);
    } 
  }
}
