package com.js.util.util;

import java.math.BigDecimal;

public class Exchange {
  public String exchange(String v, int scale) {
    String result = "0";
    double v1 = Double.parseDouble(v);
    double v2 = 1024.0D;
    if (v1 < 1000.0D)
      result = String.valueOf(String.valueOf(div(v1, v2, scale))) + "Byte"; 
    if (div(v1, v2, scale) > 1000.0D) {
      double tmp = div(v1, v2, scale);
      if (div(tmp, v2, scale) > 1000.0D) {
        result = String.valueOf(String.valueOf(div(tmp, v2, scale))) + "MB";
      } else {
        result = String.valueOf(String.valueOf(div(tmp, v2, scale))) + "MB";
      } 
    } else {
      result = String.valueOf(String.valueOf(div(v1, v2, scale))) + "KB";
    } 
    return result;
  }
  
  public static double div(double v1, double v2, int scale) {
    BigDecimal b1 = new BigDecimal(Double.toString(v1));
    BigDecimal b2 = new BigDecimal(Double.toString(v2));
    return b1.divide(b2, scale, 4).doubleValue();
  }
}
