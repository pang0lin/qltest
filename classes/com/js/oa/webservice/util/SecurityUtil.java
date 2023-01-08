package com.js.oa.webservice.util;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.transport.http.HTTPConstants;

public class SecurityUtil {
  public static String checkSecurity(String key, String serviceType) {
    String result = "";
    String ipaddress = getServiceIP();
    List<SecurityPojo> Security = SecurityRoom.getSecuritys();
    SecurityPojo pojo = null;
    for (int i = 0; i < Security.size(); i++) {
      if (((SecurityPojo)Security.get(i)).getServicetype().equals(serviceType)) {
        pojo = Security.get(i);
        break;
      } 
    } 
    if (pojo == null) {
      result = "<?xml version='1.0' encoding='UTF-8'?><result><code>1</code><errmsg>非法的webservice服务</errmsg></result>";
    } else {
      String use = pojo.getUse();
      String iprange = pojo.getIprange();
      String keyValue = pojo.getKey();
      if ("1".equals(use)) {
        if (!keyValue.equals(key))
          result = "<?xml version='1.0' encoding='UTF-8'?><result><code>1</code><errmsg>非法的key验证</errmsg></result>"; 
        if (iprange.indexOf(String.valueOf(ipaddress) + ";") < 0 || "".equals(ipaddress))
          result = "<?xml version='1.0' encoding='UTF-8'?><result><code>1</code><errmsg>非法的IP地址</errmsg></result>"; 
      } 
    } 
    return result;
  }
  
  public static String getServiceIP() {
    String ipaddress = "";
    MessageContext mc = MessageContext.getCurrentMessageContext();
    if (mc != null) {
      HttpServletRequest request = (HttpServletRequest)mc.getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);
      ipaddress = request.getRemoteAddr();
    } 
    return ipaddress;
  }
}
