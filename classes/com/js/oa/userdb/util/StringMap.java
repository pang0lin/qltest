package com.js.oa.userdb.util;

import java.util.HashMap;

public class StringMap {
  private HashMap map = null;
  
  public StringMap() {
    this.map = new HashMap<Object, Object>();
  }
  
  public StringMap(int initialCapacity) {
    this.map = new HashMap<Object, Object>(initialCapacity);
  }
  
  public String get(String key) {
    Object value = this.map.get(key);
    if (value == null)
      return ""; 
    return String.valueOf(value).trim();
  }
  
  public void put(String key, int value) {
    this.map.put(key, String.valueOf(value));
  }
  
  public void put(String key, long value) {
    this.map.put(key, String.valueOf(value));
  }
  
  public void put(String key, float value) {
    this.map.put(key, String.valueOf(value));
  }
  
  public void put(String key, double value) {
    this.map.put(key, String.valueOf(value));
  }
  
  public void put(String key, String value) {
    this.map.put(key, value);
  }
  
  public void put(String key, Object value) {
    this.map.put(key, value);
  }
}
