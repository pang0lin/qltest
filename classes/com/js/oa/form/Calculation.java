package com.js.oa.form;

import java.util.HashMap;
import java.util.Map;

public class Calculation {
  public Map calculateMoney(String value, String strings) {
    Map<Object, Object> map = new HashMap<Object, Object>();
    map.put("a", "8" + value);
    map.put("b", "7" + value);
    map.put("c", "56" + value);
    map.put("d", "{[44:选项4],[55:选项5],[66:选项6]}");
    return map;
  }
  
  public Map calculateMoney(String value, String strings, String aString) {
    Map<Object, Object> map = new HashMap<Object, Object>();
    map.put("a", "8" + value);
    map.put("b", "7" + value);
    map.put("c", "56" + value);
    map.put("d", "{[44:选项4],[55:选项5],[66:选项6]}");
    return map;
  }
}
