package com.js.oa.security.microdog;

public class SecurityAPI {
  public int DogCascade;
  
  public int DogAddr;
  
  public int DogBytes;
  
  public int DogPassword;
  
  public long DogResult;
  
  public byte[] DogData;
  
  public int getDogAddr() {
    return this.DogAddr;
  }
  
  public int getDogBytes() {
    return this.DogBytes;
  }
  
  public int getDogCascade() {
    return this.DogCascade;
  }
  
  public byte[] getDogData() {
    return this.DogData;
  }
  
  public int getDogPassword() {
    return this.DogPassword;
  }
  
  public long getDogResult() {
    return this.DogResult;
  }
  
  public void setDogResult(long DogResult) {
    this.DogResult = DogResult;
  }
  
  public void setDogPassword(int DogPassword) {
    this.DogPassword = DogPassword;
  }
  
  public void setDogData(byte[] DogData) {
    this.DogData = DogData;
  }
  
  public void setDogCascade(int DogCascade) {
    this.DogCascade = DogCascade;
  }
  
  public void setDogBytes(int DogBytes) {
    this.DogBytes = DogBytes;
  }
  
  public void setDogAddr(int DogAddr) {
    this.DogAddr = DogAddr;
  }
  
  static {
    try {
      System.loadLibrary("DOGJava");
    } catch (UnsatisfiedLinkError e) {
      System.err.println("Can't find library DOGJava.DLL");
      e.printStackTrace();
      System.exit(-1);
    } 
  }
  
  public native int DogCheck();
  
  public native int DogConvert();
  
  public native int WriteDog();
  
  public native int ReadDog();
  
  public native int DisableShare();
  
  public native int GetCurrentNo();
}
