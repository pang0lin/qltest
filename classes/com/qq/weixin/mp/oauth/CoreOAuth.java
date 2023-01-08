package com.qq.weixin.mp.oauth;

import java.net.URLEncoder;
import org.apache.log4j.Logger;

public class CoreOAuth {
  private static Logger log = Logger.getLogger(CoreOAuth.class);
  
  public static String gainGuideUserUri(String url) {
    GuideUserUri page = new GuideUserUri();
    log.debug("url = " + url);
    url = URLEncoder.encode(url);
    page.setRedirect_uri(url);
    String guideUri = page.getUri();
    log.debug("guideUri = " + guideUri);
    return guideUri;
  }
  
  public static String exchangeAccessUri(String code, String appId) {
    ExchangeUri page = new ExchangeUri(code, appId);
    String accessUri = page.getUri();
    log.debug("accessUri = " + accessUri);
    return accessUri;
  }
}
