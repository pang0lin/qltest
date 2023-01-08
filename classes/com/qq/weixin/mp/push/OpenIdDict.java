package com.qq.weixin.mp.push;

import com.js.oa.weixin.push.OpenIdDao;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class OpenIdDict {
  private static Logger log = Logger.getLogger(OpenIdDict.class);
  
  private static Map<Integer, String> map = new HashMap<Integer, String>();
  
  public static String lookup(Integer id) {
    String openId = "";
    log.debug("id = " + id);
    if (!map.containsKey(id))
      learn(id.intValue()); 
    openId = map.get(id);
    return openId;
  }
  
  private static void learn(int id) {
    String openId = OpenIdDao.findOpenId(id);
    log.debug("openId = " + openId);
    map.put(Integer.valueOf(id), openId);
  }
}
