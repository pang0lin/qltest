package com.js.exception;

public class JSException extends Exception {
  public JSException() {}
  
  public JSException(String ex) {
    super(ex);
  }
  
  public JSException(Throwable ta) {
    super(ta);
  }
  
  public JSException(String ex, Throwable ta) {
    super(ex, ta);
  }
}
