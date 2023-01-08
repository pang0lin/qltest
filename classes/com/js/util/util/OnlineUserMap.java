package com.js.util.util;

import java.util.concurrent.ConcurrentHashMap;

public class OnlineUserMap {
  private static ConcurrentHashMap ccMap = new ConcurrentHashMap<Object, Object>();
  
  public static ConcurrentHashMap getInstance() {
    return ccMap;
  }
}
