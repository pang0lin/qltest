package com.js.util.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.apache.poi2.poifs.filesystem.DirectoryNode;
import org.apache.poi2.poifs.filesystem.DocumentEntry;
import org.apache.poi2.poifs.filesystem.POIFSFileSystem;

public class Html2Word {
  public void html2Word(String path, String fileName, String content) {
    try {
      if (!"".equals(path)) {
        File fileDir = new File(path);
        if (fileDir.exists()) {
          byte[] b = content.getBytes();
          ByteArrayInputStream bais = new ByteArrayInputStream(b);
          POIFSFileSystem poifs = new POIFSFileSystem();
          DirectoryNode directoryNode = poifs.getRoot();
          DocumentEntry documentEntry = directoryNode.createDocument("WordDocument", bais);
          FileOutputStream ostream = new FileOutputStream(String.valueOf(path) + fileName);
          poifs.writeFilesystem(ostream);
          bais.close();
          ostream.close();
        } 
      } 
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public static void main(String[] arg) {
    Html2Word h2w = new Html2Word();
    String content = h2w.readTxtFile("C:\\pdf\\111.htm");
    h2w.html2Word("C:\\pdf\\", "a.doc", content);
  }
  
  public String pageWriteToFile(String webURL) {
    String webpage = "";
    try {
      URL url = new URL(webURL);
      URLConnection conn = url.openConnection();
      conn.setDoOutput(true);
      InputStream in = conn.getInputStream();
      int c;
      while ((c = in.read()) != -1) {
        int all = in.available();
        byte[] b = new byte[all];
        in.read(b);
        webpage = new String(b, "GBK");
      } 
      in.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return webpage;
  }
  
  public String readTxtFile(String filePath) {
    StringBuffer content = new StringBuffer("");
    try {
      String encoding = "GBK";
      File file = new File(filePath);
      if (file.isFile() && file.exists()) {
        InputStreamReader read = new InputStreamReader(
            new FileInputStream(file), encoding);
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = null;
        while ((lineTxt = bufferedReader.readLine()) != null)
          content.append(lineTxt); 
        read.close();
      } else {
        System.out.println("找不到指定的文件");
      } 
    } catch (Exception e) {
      System.out.println("读取文件内容出错");
      e.printStackTrace();
    } 
    return content.toString();
  }
}
