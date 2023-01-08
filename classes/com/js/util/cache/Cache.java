package com.js.util.cache;

import java.util.Date;

public interface Cache {
  boolean add(String paramString, Object paramObject);
  
  boolean add(String paramString, Object paramObject, Date paramDate);
  
  Object get(String paramString);
  
  boolean delete(String paramString);
  
  boolean delete(String paramString, Date paramDate);
  
  boolean delete(String paramString, Integer paramInteger, Date paramDate);
  
  boolean replace(String paramString, Object paramObject);
  
  boolean replace(String paramString, Object paramObject, Date paramDate);
  
  boolean flushAll();
}
