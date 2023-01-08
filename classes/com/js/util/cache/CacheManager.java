package com.js.util.cache;

public class CacheManager {
  public Cache getInstance() {
    return MemCachedImpl.getInstance();
  }
}
