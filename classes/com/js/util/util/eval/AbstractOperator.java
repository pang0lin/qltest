package com.js.util.util.eval;

public abstract class AbstractOperator implements IOperator {
  public boolean valiIsCalMethod(String s) {
    if ("+".equals(s) || "-".equals(s) || "*".equals(s) || "/".equals(s) || ">".equals(s) || 
      "<".equals(s) || "==".equals(s) || "!=".equals(s) || ">=".equals(s) || 
      "<=".equals(s) || "%%".equals(s) || "&&".equals(s) || "||".equals(s))
      return true; 
    return false;
  }
  
  public int getCalMethodPri(String calMethod) {
    if ("%%".equals(calMethod) || "*".equals(calMethod) || "/".equals(calMethod))
      return 1; 
    if ("&&".equals(calMethod) || "||".equals(calMethod))
      return -1; 
    return 0;
  }
  
  public String calAtom(String s1, String s2, String calMethod) {
    if ("%%".equals(calMethod))
      return String.valueOf(Double.parseDouble(s1) % Double.parseDouble(s2)); 
    if ("+".equals(calMethod))
      return String.valueOf(Double.parseDouble(s1) + Double.parseDouble(s2)); 
    if ("-".equals(calMethod))
      return String.valueOf(Double.parseDouble(s1) - Double.parseDouble(s2)); 
    if ("*".equals(calMethod))
      return String.valueOf(Double.parseDouble(s1) * Double.parseDouble(s2)); 
    if ("/".equals(calMethod))
      return String.valueOf(Double.parseDouble(s1) / Double.parseDouble(s2)); 
    if (">".equals(calMethod))
      return String.valueOf((Double.parseDouble(s1) > Double.parseDouble(s2))); 
    if ("<".equals(calMethod))
      return String.valueOf((Double.parseDouble(s1) < Double.parseDouble(s2))); 
    if ("==".equals(calMethod))
      return String.valueOf(s1.equals(s2)); 
    if ("!=".equals(calMethod))
      return String.valueOf(!s1.equals(s2)); 
    if (">=".equals(calMethod))
      return String.valueOf((Double.parseDouble(s1) >= Double.parseDouble(s2))); 
    if ("<=".equals(calMethod))
      return String.valueOf((Double.parseDouble(s1) <= Double.parseDouble(s2))); 
    if ("&&".equals(calMethod))
      return String.valueOf(((new Boolean(s1)).booleanValue() && (new Boolean(s2)).booleanValue())); 
    if ("||".equals(calMethod))
      return String.valueOf(!(!(new Boolean(s1)).booleanValue() && !(new Boolean(s2)).booleanValue())); 
    return null;
  }
}
