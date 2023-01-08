package com.snd;

public class SNDUtil {
  static {
    try {
      System.loadLibrary("DOGJava");
    } catch (UnsatisfiedLinkError e) {
      System.err.println("Can't find library DOGJava.DLL");
      System.exit(-1);
    } 
  }
  
  public native int WriteDog();
  
  public native int ReadDog();
}
