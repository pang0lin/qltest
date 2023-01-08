package com.js.util.util.json;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sf.ezmorph.Morpher;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;
import net.sf.json.xml.XMLSerializer;

public class JsonUtil {
  public static Object getDTO(String jsonString, Class clazz) {
    JSONObject jsonObject = null;
    try {
      setDataFormat2JAVA();
      jsonObject = JSONObject.fromObject(jsonString);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return JSONObject.toBean(jsonObject, clazz);
  }
  
  public static Object getDTO(String jsonString, Class clazz, Map map) {
    JSONObject jsonObject = null;
    try {
      setDataFormat2JAVA();
      jsonObject = JSONObject.fromObject(jsonString);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return JSONObject.toBean(jsonObject, clazz, map);
  }
  
  public static Object[] getDTOArray(String jsonString, Class clazz) {
    setDataFormat2JAVA();
    JSONArray array = JSONArray.fromObject(jsonString);
    Object[] obj = new Object[array.size()];
    for (int i = 0; i < array.size(); i++) {
      JSONObject jsonObject = array.getJSONObject(i);
      obj[i] = JSONObject.toBean(jsonObject, clazz);
    } 
    return obj;
  }
  
  public static Object[] getDTOArray(String jsonString, Class clazz, Map map) {
    setDataFormat2JAVA();
    JSONArray array = JSONArray.fromObject(jsonString);
    Object[] obj = new Object[array.size()];
    for (int i = 0; i < array.size(); i++) {
      JSONObject jsonObject = array.getJSONObject(i);
      obj[i] = JSONObject.toBean(jsonObject, clazz, map);
    } 
    return obj;
  }
  
  public static List getDTOList(String jsonString, Class clazz) {
    setDataFormat2JAVA();
    JSONArray array = JSONArray.fromObject(jsonString);
    List<Object> list = new ArrayList();
    for (Iterator<JSONObject> iter = array.iterator(); iter.hasNext(); ) {
      JSONObject jsonObject = iter.next();
      list.add(JSONObject.toBean(jsonObject, clazz));
    } 
    return list;
  }
  
  public static List getDTOList(String jsonString, Class clazz, Map map) {
    setDataFormat2JAVA();
    JSONArray array = JSONArray.fromObject(jsonString);
    List<Object> list = new ArrayList();
    for (Iterator<JSONObject> iter = array.iterator(); iter.hasNext(); ) {
      JSONObject jsonObject = iter.next();
      list.add(JSONObject.toBean(jsonObject, clazz, map));
    } 
    return list;
  }
  
  public static Map getMapFromJson(String jsonString) {
    setDataFormat2JAVA();
    JSONObject jsonObject = JSONObject.fromObject(jsonString);
    Map<Object, Object> map = new HashMap<Object, Object>();
    for (Iterator<String> iter = jsonObject.keys(); iter.hasNext(); ) {
      String key = iter.next();
      map.put(key, jsonObject.get(key));
    } 
    return map;
  }
  
  public static Object[] getObjectArrayFromJson(String jsonString) {
    JSONArray jsonArray = JSONArray.fromObject(jsonString);
    return jsonArray.toArray();
  }
  
  public static String getJSONString(Object object) throws Exception {
    String jsonString = null;
    JsonConfig jsonConfig = new JsonConfig();
    jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
    if (object != null)
      if (object instanceof java.util.Collection || object instanceof Object[]) {
        jsonString = JSONArray.fromObject(object, jsonConfig).toString();
      } else {
        jsonString = JSONObject.fromObject(object, jsonConfig).toString();
      }  
    return (jsonString == null) ? "{}" : jsonString;
  }
  
  public static String xml2JSON(String xml) {
    return (new XMLSerializer()).read(xml).toString();
  }
  
  public static String json2XML(String json) {
    JSONObject jobj = JSONObject.fromObject(json);
    String xml = (new XMLSerializer()).write((JSON)jobj);
    return xml;
  }
  
  private static void setDataFormat2JAVA() {
    JSONUtils.getMorpherRegistry().registerMorpher(
        (Morpher)new DateMorpher(
          new String[] { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd" }));
  }
  
  public static void main(String[] arg) throws Exception {}
}
