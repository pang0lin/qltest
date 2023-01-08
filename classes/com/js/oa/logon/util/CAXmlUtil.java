package com.js.oa.logon.util;

import org.dom4j.Element;

public class CAXmlUtil {
  public static final String MSG_ROOT = "message";
  
  public static final String MSG_HEAD = "head";
  
  public static final String MSG_BODY = "body";
  
  public static final String MSG_VSERSION = "version";
  
  public static final String MSG_VSERSION_VALUE = "1.0";
  
  public static final String MSG_SERVICE_TYPE = "serviceType";
  
  public static final String MSG_SERVICE_TYPE_VALUE = "AuthenService";
  
  public static final String MSG_AUTH_MODE = "authMode";
  
  public static final String MSG_AUTH_MODE_CERT_VALUE = "cert";
  
  public static final String MSG_AUTH_MODE_PASSWORD_VALUE = "password";
  
  public static final String MSG_ATTRIBUTES = "attributes";
  
  public static final String MSG_ATTRIBUTE = "attr";
  
  public static final String MSG_NAME = "name";
  
  public static final String MSG_PARENT_NAME = "parentName";
  
  public static final String MSG_NAMESPACE = "namespace";
  
  public static final String MSG_APPID = "appId";
  
  public static final String MSG_ACCESS_CONTROL = "accessControl";
  
  public static final String MSG_ACCESS_CONTROL_TRUE = "true";
  
  public static final String MSG_ACCESS_CONTROL_FALSE = "false";
  
  public static final String MSG_AUTH = "authen";
  
  public static final String MSG_AUTHCREDENTIAL = "authCredential";
  
  public static final String MSG_CLIENT_INFO = "clientInfo";
  
  public static final String MSG_CERT_INFO = "certInfo";
  
  public static final String MSG_CLIENT_IP = "clientIP";
  
  public static final String MSG_DETACH = "detach";
  
  public static final String MSG_ORIGINAL = "original";
  
  public static final String MSG_USERNAME = "username";
  
  public static final String MSG_PASSWORD = "password";
  
  public static final String MSG_ATTRIBUTE_TYPE = "attributeType";
  
  public static final String MSG_ATTRIBUTE_TYPE_PORTION = "portion";
  
  public static final String MSG_ATTRIBUTE_TYPE_ALL = "all";
  
  public static final String MSG_MESSAGE_STATE = "messageState";
  
  public static final String MSG_MESSAGE_CODE = "messageCode";
  
  public static final String MSG_MESSAGE_DESC = "messageDesc";
  
  public static final String MSG_AUTH_RESULT_SET = "authResultSet";
  
  public static final String MSG_AUTH_RESULT = "authResult";
  
  public static final String MSG_SUCCESS = "success";
  
  public static final String MSG_AUTH_MESSSAGE_CODE = "authMessageCode";
  
  public static final String MSG_AUTH_MESSSAGE_DESC = "authMessageDesc";
  
  public static final String KEY_AUTHURL = "authURL";
  
  public static final String KEY_APP_ID = "appId";
  
  public static final String KEY_CERT_AUTHEN = "certAuthen";
  
  public static final String KEY_ORIGINAL_DATA = "original_data";
  
  public static final String KEY_ORIGINAL_JSP = "original_jsp";
  
  public static final String KEY_SIGNED_DATA = "signed_data";
  
  public static final String KEY_CERT_CONTENT = "certInfo";
  
  public static void addAttribute(Element attributesElement, String name, String namespace) {
    Element attr = attributesElement.addElement("attr");
    attr.addAttribute("name", name);
    attr.addAttribute("namespace", namespace);
  }
  
  public static boolean isNotNull(String str) {
    if (str == null || str.trim().equals(""))
      return false; 
    return true;
  }
  
  public static String generateRandomNum() {
    String num = "1234567890abcdefghijklmnopqrstopqrstuvwxyz";
    int size = 6;
    char[] charArray = num.toCharArray();
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < size; i++)
      sb.append(charArray[(int)(Math.random() * 10000.0D) % charArray.length]); 
    return sb.toString();
  }
}
