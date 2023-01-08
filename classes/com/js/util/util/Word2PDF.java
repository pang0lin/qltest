package com.js.util.util;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import java.io.File;

public class Word2PDF {
  static final int wdDoNotSaveChanges = 0;
  
  static final int wdFormatPDF = 17;
  
  public void word2Pdf(String fileName) {
    String toFilename = String.valueOf(fileName.substring(0, fileName.indexOf(".doc"))) + ".pdf";
    ActiveXComponent app = null;
    try {
      app = new ActiveXComponent("Word.Application");
      app.setProperty("Visible", false);
      Dispatch docs = app.getProperty("Documents").toDispatch();
      Dispatch doc = Dispatch.call(docs, "Open", new Object[] { fileName, Boolean.valueOf(false), Boolean.valueOf(true) }).toDispatch();
      File tofile = new File(toFilename);
      if (tofile.exists())
        tofile.delete(); 
      Dispatch.call(doc, "SaveAs", new Object[] { toFilename, Integer.valueOf(17) });
      Dispatch.call(doc, "Close", new Object[] { Boolean.valueOf(false) });
    } catch (Exception e) {
      System.out.println("Error:文档转换失败：" + e.getMessage());
      e.printStackTrace();
    } finally {
      if (app != null)
        app.invoke("Quit", 0); 
    } 
  }
  
  public static void main(String[] args) {
    Word2PDF w2p = new Word2PDF();
    w2p.word2Pdf("c:/pdf/联动设置.docx");
  }
}
