package com.js.oa.zky.util.word;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZkyExcelData {
  public List<Map<String, String[]>> getExcelData() {
    List<Map<String, String[]>> list = new ArrayList<Map<String, String[]>>();
    for (int i = 0; i < 10; i++) {
      Map<String, String[]> map = (Map)new HashMap<String, String>();
      map.put("1", new String[] { "姓名", "GENERAL" });
      map.put("2", new String[] { "23", "DOUBLE" });
      map.put("3", new String[] { "13", "DOUBLE" });
      map.put("4", new String[] { "1900", "INT" });
      map.put("5", new String[] { "56", "DOUBLE" });
      map.put("6", new String[] { "4800", "INT" });
      map.put("7", new String[] { (new StringBuilder(String.valueOf(i))).toString(), "INT" });
      map.put("8", new String[] { "23", "DOUBLE" });
      map.put("9", new String[] { "18", "DOUBLE" });
      map.put("10", new String[] { "1990", "INT" });
      map.put("11", new String[] { "56", "DOUBLE" });
      map.put("12", new String[] { "你好", "GENERAL" });
      map.put("13", new String[] { (new StringBuilder(String.valueOf(i))).toString(), "INT" });
      list.add(map);
    } 
    return list;
  }
}
