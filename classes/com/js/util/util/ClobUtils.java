package com.js.util.util;

import java.lang.reflect.Method;
import oracle.sql.BLOB;
import oracle.sql.CLOB;

public class ClobUtils {
  public static CLOB clobToChange(Object in) {
    CLOB clob1 = null;
    try {
      if ("oracle.sql.CLOB".equals(in.getClass().getName())) {
        CLOB clob = (CLOB)in;
        return clob;
      } 
      if ("weblogic.jdbc.wrapper.Clob_oracle_sql_CLOB".equals(in.getClass().getName())) {
        Method method = in.getClass().getMethod("getVendorObj", new Class[0]);
        CLOB clob = (CLOB)method.invoke(in, new Object[0]);
        return clob;
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return clob1;
  }
  
  public static BLOB blobToChange(Object in) {
    BLOB blob1 = null;
    try {
      if ("oracle.sql.BLOB".equals(in.getClass().getName())) {
        BLOB blob = (BLOB)in;
        return blob;
      } 
      if ("weblogic.jdbc.wrapper.Blob_oracle_sql_BLOB".equals(in.getClass().getName())) {
        Method method = in.getClass().getMethod("getVendorObj", new Class[0]);
        BLOB blob = (BLOB)method.invoke(in, new Object[0]);
        return blob;
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return blob1;
  }
}
