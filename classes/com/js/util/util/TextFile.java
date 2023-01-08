package com.js.util.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;

public class TextFile {
  public void writeFile(File Wfile, String str) throws FileNotFoundException {
    if (Wfile.exists())
      Wfile.delete(); 
    FileOutputStream fout = new FileOutputStream(Wfile);
    PrintWriter pWriter = new PrintWriter(fout);
    pWriter.print(str);
    pWriter.flush();
    pWriter.close();
  }
  
  public String readFile(File file) throws IOException {
    StringBuffer buf = new StringBuffer();
    Reader reader = null;
    try {
      reader = new InputStreamReader(new FileInputStream(file));
      int tempchar;
      while ((tempchar = reader.read()) != -1)
        buf.append((char)tempchar); 
      reader.close();
    } catch (FileNotFoundException ex) {
      FileNotFoundException fileNotFoundException1 = null;
      throw fileNotFoundException1;
    } catch (Exception e) {
      System.out.println("读取文件失败...");
    } 
    return buf.toString();
  }
  
  public void changeName(File file, File newFile) {
    try {
      file.renameTo(newFile);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public void deleteFile(File file) {
    try {
      file.delete();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
}
