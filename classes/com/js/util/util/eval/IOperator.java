package com.js.util.util.eval;

public interface IOperator {
  boolean valiIsCalMethod(String paramString);
  
  int getCalMethodPri(String paramString);
  
  String calAtom(String paramString1, String paramString2, String paramString3);
}
