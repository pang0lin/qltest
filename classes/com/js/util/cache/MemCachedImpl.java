package com.js.util.cache;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import java.util.Date;

public class MemCachedImpl implements Cache {
  protected static MemCachedClient mcc = new MemCachedClient();
  
  protected static MemCachedImpl memCached = new MemCachedImpl();
  
  protected MemCachedImpl() {
    String[] servers = { "192.168.0.201:11211" };
    Integer[] weights = { Integer.valueOf(3) };
    SockIOPool pool = SockIOPool.getInstance();
    pool.setServers(servers);
    pool.setWeights(weights);
    pool.setInitConn(5);
    pool.setMinConn(5);
    pool.setMaxConn(250);
    pool.setMaxIdle(21600000L);
    pool.setMaintSleep(30L);
    pool.setNagle(false);
    pool.setSocketTO(3000);
    pool.setSocketConnectTO(0);
    pool.initialize();
    mcc.flushAll();
  }
  
  public static Cache getInstance() {
    return memCached;
  }
  
  public boolean add(String key, Object value) {
    return mcc.add(key, value);
  }
  
  public boolean add(String key, Object value, Date expiry) {
    return mcc.add(key, value, expiry);
  }
  
  public boolean delete(String key) {
    return mcc.delete(key);
  }
  
  public boolean delete(String key, Date date) {
    return mcc.delete(key, date);
  }
  
  public boolean delete(String key, Integer arg, Date date) {
    return mcc.delete(key, arg, date);
  }
  
  public Object get(String key) {
    return mcc.get(key);
  }
  
  public boolean replace(String key, Object value) {
    return mcc.replace(key, value);
  }
  
  public boolean replace(String key, Object value, Date expiry) {
    return mcc.replace(key, value, expiry);
  }
  
  public boolean flushAll() {
    return mcc.flushAll();
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
