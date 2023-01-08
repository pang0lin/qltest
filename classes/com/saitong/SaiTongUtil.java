package com.saitong;

import com.js.system.service.usermanager.UserBD;
import com.js.util.util.SaiTongStr;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SaiTongUtil {
  public boolean visitURL(String webURL) {
    InputStream in = null;
    boolean flag = false;
    try {
      URL url = new URL(webURL);
      URLConnection conn = url.openConnection();
      conn.setDoOutput(true);
      in = conn.getInputStream();
      byte[] tt = new byte[in.available()];
      String html = "";
      while (in.read(tt, 0, tt.length) != -1)
        html = new String(tt, "utf-8"); 
      in.close();
      if (html.contains("OK"))
        flag = true; 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return flag;
  }
  
  public void saiTongExc(String title, String empId, String url) {
    try {
      title = URLEncoder.encode(title, "utf-8");
      String username = (new UserBD()).getUserName(new Long(empId));
      SaiTongStr saiTongStr = SaiTongStr.getInstance();
      if (url != null && !"".equals(url) && !"null".equals(url)) {
        String[] strs = url.split("\\?");
        String[] strs1 = strs[0].split("\\.");
        String rtxMsgAction = strs1[0];
        String suffix = strs1[1];
        url = String.valueOf(saiTongStr.getRtxStr("OAURL")) + "rtxFlag=saiTong&rtxAccount=" + username + "&rtxMsgAction=" + 
          rtxMsgAction + "&suffix=" + suffix + "&rtxMsg=yes&" + strs[1] + "&date=" + System.currentTimeMillis() + 
          "&token=%temp%";
      } 
      url = URLEncoder.encode(url, "utf-8");
      String pathUrl = saiTongStr.getRtxStr("url");
      if (!pathUrl.endsWith("?"))
        pathUrl = String.valueOf(pathUrl) + "&"; 
      System.out.println("测试调用路径：" + pathUrl + "url=" + url + "&no=" + username + "&msg=" + title);
      visitURL(String.valueOf(pathUrl) + "url=" + url + "&no=" + username + "&msg=" + title);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
