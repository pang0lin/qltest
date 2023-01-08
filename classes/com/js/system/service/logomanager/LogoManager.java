package com.js.system.service.logomanager;

import com.js.system.vo.logomanager.LogoVO;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LogoManager {
  public static Map logoMap = new ConcurrentHashMap<Object, Object>();
  
  public static int getLogo11() {
    return logoMap.size();
  }
  
  public static String getLogo(String logoId) {
    return (String)logoMap.get(Long.valueOf(logoId));
  }
  
  public static void init() {
    try {
      logoMap.clear();
      LogoBD logoBD = new LogoBD();
      List<LogoVO> list = logoBD.getLogoList();
      if (list != null && list.size() > 0)
        for (int i = 0; i < list.size(); i++) {
          LogoVO logoVO = list.get(i);
          logoMap.put(logoVO.getLogoId(), logoVO.getLogoPath());
        }  
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public static void initManager() {
    if (logoMap.size() < 1)
      init(); 
  }
}
