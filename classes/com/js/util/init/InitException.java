package com.js.util.init;

public class InitException extends Exception {
  public InitException() {}
  
  public InitException(String message) {
    super(message);
  }
  
  public InitException(String message, Throwable throwable) {
    super(message, throwable);
  }
  
  public InitException(Throwable throwable) {
    super(throwable);
  }
}
