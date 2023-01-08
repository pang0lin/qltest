package com.js.util.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

public class ZipOrUnZip {
  public static void ZIP(String source, String zipFileName) throws IOException {
    ZipOutputStream zos = new ZipOutputStream(new File(zipFileName));
    zos.setEncoding("gb2312");
    File f = new File(source);
    if (f.isDirectory()) {
      ZIPDIR(source, zos, String.valueOf(f.getName()) + "/");
    } else {
      ZIPDIR(f.getPath(), zos, String.valueOf((new File(f.getParent())).getName()) + "/");
      ZIPFile(f.getPath(), zos, String.valueOf((new File(f.getParent())).getName()) + "/" + 
          f.getName());
    } 
    zos.closeEntry();
    zos.close();
  }
  
  public static void ZIPFile(String sourceFileName, ZipOutputStream zos, String tager) throws IOException {
    ZipEntry ze = new ZipEntry(tager);
    zos.putNextEntry(ze);
    FileInputStream fis = new FileInputStream(new File(sourceFileName));
    byte[] bf = new byte[2048];
    int location = 0;
    while ((location = fis.read(bf)) != -1)
      zos.write(bf, 0, location); 
    fis.close();
  }
  
  public static void ZIPDIR(String sourceDir, ZipOutputStream zos, String tager) throws IOException {
    ZipEntry ze = new ZipEntry(tager);
    zos.putNextEntry(ze);
    File f = new File(sourceDir);
    File[] flist = f.listFiles();
    if (flist != null) {
      byte b;
      int i;
      File[] arrayOfFile;
      for (i = (arrayOfFile = flist).length, b = 0; b < i; ) {
        File fsub = arrayOfFile[b];
        if (fsub.isDirectory()) {
          ZIPDIR(fsub.getPath(), zos, String.valueOf(tager) + fsub.getName() + "/");
        } else {
          ZIPFile(fsub.getPath(), zos, String.valueOf(tager) + fsub.getName());
        } 
        b++;
      } 
    } 
  }
  
  public static void UnZIP(String sourceFileName, String desDir) throws IOException {
    ZipFile zf = new ZipFile(new File(sourceFileName));
    Enumeration<ZipEntry> en = zf.getEntries();
    int length = 0;
    byte[] b = new byte[2048];
    while (en.hasMoreElements()) {
      ZipEntry ze = en.nextElement();
      File f = new File(String.valueOf(desDir) + ze.getName());
      if (ze.isDirectory()) {
        f.mkdirs();
        continue;
      } 
      if (!f.getParentFile().exists())
        f.getParentFile().mkdirs(); 
      OutputStream outputStream = new FileOutputStream(f);
      InputStream inputStream = zf.getInputStream(ze);
      while ((length = inputStream.read(b)) > 0)
        outputStream.write(b, 0, length); 
      inputStream.close();
      outputStream.close();
    } 
    zf.close();
  }
  
  public static void main(String[] arg) {
    try {
      long starTime = 0L;
      long endTime = 0L;
      System.out.println("开始压缩...");
      starTime = System.currentTimeMillis();
      ZIP("D:\\360安全浏览器下载", "D:\\A.zip");
      endTime = System.currentTimeMillis();
      System.out.println("压缩完毕！花费时间: " + (endTime - starTime) + " 毫秒！");
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
}
