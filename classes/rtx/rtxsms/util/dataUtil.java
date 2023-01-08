package rtx.rtxsms.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import rtx.rtxsms.tempuri.MmsFileGroup;

public class dataUtil {
  public static String id = "000001";
  
  public String getID() {
    if ("999999".equals(id))
      id = "000001"; 
    try {
      int intid = Integer.parseInt(id);
      intid++;
      id = Integer.toString(intid);
      int size = id.length();
      if (size < 6) {
        for (int i = 0; i < 6 - size; i++)
          id = "0" + id; 
      } else {
        return id;
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return id;
  }
  
  public static String utf8Togb2312(String str) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      switch (c) {
        case '+':
          sb.append(' ');
          break;
        case '%':
          try {
            sb.append((char)Integer.parseInt(
                  str.substring(i + 1, i + 3), 16));
          } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
          } 
          i += 2;
          break;
        default:
          sb.append(c);
          break;
      } 
    } 
    String result = sb.toString();
    String res = null;
    try {
      byte[] inputBytes = result.getBytes("8859_1");
      res = new String(inputBytes, "UTF-8");
    } catch (Exception exception) {}
    return res;
  }
  
  public static MmsFileGroup[] makeMmsFile(String imgUrl, String count) {
    MmsFileGroup mm = new MmsFileGroup();
    mm.setText_FileName("aa.txt");
    try {
      mm.setText_Content(new String(count.getBytes("UTF-8")));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } 
    mm.setImage_FileData(getByteArray(imgUrl));
    String fileName = "";
    if (imgUrl.lastIndexOf(".") != -1)
      fileName = imgUrl.substring(imgUrl.lastIndexOf("."), imgUrl.length()); 
    mm.setImage_FileName("send" + fileName);
    mm.setPlayTime(0L);
    MmsFileGroup[] mmsFile = { mm };
    return mmsFile;
  }
  
  public static byte[] getByteArray(String imgurl) {
    byte[] xml = (byte[])null;
    try {
      File file = new File(imgurl);
      BufferedInputStream br = new BufferedInputStream(new FileInputStream(file));
      if (br != null) {
        int len = br.available();
        xml = new byte[len];
        br.read(xml);
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return xml;
  }
  
  public static String getTxtContent(String imgurl) {
    byte[] xml = (byte[])null;
    String str = "";
    try {
      File file = new File(imgurl);
      BufferedInputStream br = new BufferedInputStream(new FileInputStream(file));
      if (br != null) {
        int len = br.available();
        xml = new byte[len];
        br.read(xml);
        str = new String(xml, "utf-8");
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return str;
  }
}
