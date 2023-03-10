package com.js.oa.form.zgrs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class UserPhotoTran {
  public void fileChannelCopy(String sPath, String tPath) {
    File s = new File(sPath);
    File t = new File(tPath);
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
