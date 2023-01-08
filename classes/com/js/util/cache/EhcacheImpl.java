package com.js.util.cache;

import java.util.Date;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhcacheImpl implements Cache {
  protected static Cache ehcache = null;
  
  protected static EhcacheImpl ehcacheImpl = new EhcacheImpl();
  
  protected EhcacheImpl() {
    CacheManager manager = new CacheManager("src/ehcache1.xml");
    ehcache = manager.getCache("test");
  }
  
  public static Cache getInstance() {
    return ehcacheImpl;
  }
  
  public boolean add(String key, Object value) {
    Element element = new Element(key, value);
    ehcache.put(element);
    return true;
  }
  
  public boolean add(String key, Object value, Date expiry) {
    Element element = new Element(key, value);
    ehcache.put(element);
    return true;
  }
  
  public boolean delete(String key) {
    return false;
  }
  
  public boolean delete(String key, Date date) {
    return false;
  }
  
  public boolean delete(String key, Integer arg, Date date) {
    return false;
  }
  
  public boolean replace(String key, Object value) {
    return false;
  }
  
  public boolean replace(String key, Object value, Date expiry) {
    return false;
  }
  
  public boolean flushAll() {
    return false;
  }
  
  public Object get(String key) {
    Element element = ehcache.get("key1");
    return element.getObjectValue();
  }
  
  public static void main(String[] args) {
    Cache cache = getInstance();
    System.out.println("get value : " + cache.get("hello"));
    cache.replace("hello", "dddddd");
    System.out.println("get value2 : " + cache.get("hello"));
    StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < 100; i++)
      buffer.append("123456789900" + i); 
    String xxx = buffer.toString();
    System.out.println("T1:" + (new Date()).getTime());
    int j;
    for (j = 0; j < 10000; j++)
      cache.add("xx" + j, String.valueOf(xxx) + j); 
    System.out.println("T2:" + (new Date()).getTime());
    for (j = 0; j < 10000; j++)
      Object object = cache.get("xx" + j); 
    System.out.println("T3:" + (new Date()).getTime());
  }
}
