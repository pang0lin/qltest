package com.js.util.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

public class BatchDownloadTool {
  public static String zipFile(String[] path, String[] fileNames, String filePath, String[] names) {
    String fileName = String.valueOf(System.currentTimeMillis()) + ".zip";
    String outFilePath = String.valueOf(filePath) + path[0] + File.separator + fileName;
    try {
      File file = new File(outFilePath);
      FileOutputStream outStream = new FileOutputStream(file);
      ZipOutputStream toClient = new ZipOutputStream(outStream);
      toClient.setEncoding("gbk");
      for (int i = 0; i < fileNames.length; i++) {
        File sourceFile = new File(String.valueOf(filePath.trim()) + path[i].trim() + File.separator + fileNames[i].trim());
        File toFile = new File(String.valueOf(filePath.trim()) + path[i].trim() + File.separator + names[i].trim());
        fileChannelCopy(sourceFile, toFile);
        zipFile(toFile, toClient);
        toFile.delete();
      } 
      toClient.close();
      outStream.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return fileName;
  }
  
  private static void zipFile(File inputFile, ZipOutputStream outputstream) {
    try {
      if (inputFile.exists() && 
        inputFile.isFile()) {
        FileInputStream inStream = new FileInputStream(inputFile);
        BufferedInputStream bInStream = new BufferedInputStream(inStream);
        ZipEntry entry = new ZipEntry(inputFile.getName());
        outputstream.putNextEntry(entry);
        int MAX_BYTE = 10485760;
        long streamTotal = 0L;
        int streamNum = 0;
        int leaveByte = 0;
        streamTotal = bInStream.available();
        streamNum = (int)Math.floor((streamTotal / 10485760L));
        leaveByte = (int)streamTotal % 10485760;
        if (streamNum > 0)
          for (int j = 0; j < streamNum; j++) {
            byte[] arrayOfByte = new byte[10485760];
            bInStream.read(arrayOfByte, 0, 10485760);
            outputstream.write(arrayOfByte, 0, 10485760);
          }  
        byte[] inOutbyte = new byte[leaveByte];
        bInStream.read(inOutbyte, 0, leaveByte);
        outputstream.write(inOutbyte);
        outputstream.closeEntry();
        bInStream.close();
        inStream.close();
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public static void fileChannelCopy(File s, File t) {
    FileInputStream fi = null;
    FileOutputStream fo = null;
    FileChannel in = null;
    FileChannel out = null;
    try {
      fi = new FileInputStream(s);
      fo = new FileOutputStream(t);
      in = fi.getChannel();
      out = fo.getChannel();
      in.transferTo(0L, in.size(), out);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        fi.close();
        in.close();
        fo.close();
        out.close();
      } catch (IOException e) {
        e.printStackTrace();
      } 
    } 
  }
}
