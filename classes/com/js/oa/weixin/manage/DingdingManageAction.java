package com.js.oa.weixin.manage;

import com.qq.weixin.mp.pojo.EventKey;
import com.qq.weixin.mp.util.SignUtil;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DingdingManageAction extends Action {
  private static Logger log = Logger.getLogger(DingdingManageAction.class);
  
  private static Properties prop = null;
  
  private static String path = DingdingManageAction.class.getClassLoader().getResource("/com/js/oa/weixin/manage/dingding.properties").getPath();
  
  private static void load() {
    try {
      InputStream in = new FileInputStream(path);
      prop.load(in);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  private static void store() {
    try {
      OutputStream out = new FileOutputStream(path);
      prop.store(out, "dingding config file");
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public static String getPropValue(String propKey) {
    if (prop == null)
      prop = new Properties(); 
    load();
    return prop.getProperty(propKey);
  }
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    if (prop == null)
      prop = new Properties(); 
    WeixinManageActionForm form = (WeixinManageActionForm)actionForm;
    String action = request.getParameter("action");
    if ("query".equals(action)) {
      load();
      form.setAppId(prop.getProperty("app_id"));
      form.setAppSecret(prop.getProperty("app_secret"));
      form.setServerUrl(prop.getProperty("serverUrl"));
      form.setToken(prop.getProperty("token"));
      log.debug("load : appId = " + prop.getProperty("app_id") + 
          ", appSecret = " + prop.getProperty("app_secret") + 
          ", serverUrl = " + prop.getProperty("serverUrl") + 
          ", token = " + prop.getProperty("token"));
    } else if ("update".equals(action)) {
      prop.setProperty("app_id", form.getAppId());
      prop.setProperty("app_secret", form.getAppSecret());
      renewEventKeyHome(form.getServerUrl(), "dbsx");
      renewSignUtilToken(form.getToken());
      store();
      log.debug("load : appId = " + prop.getProperty("app_id") + 
          ", appSecret = " + prop.getProperty("app_secret") + 
          ", serverUrl = " + prop.getProperty("serverUrl") + 
          ", token = " + prop.getProperty("token"));
    } 
    return actionMapping.findForward("view");
  }
  
  private void renewEventKeyHome(String serverUrl, String app) {
    String _serverUrl = prop.getProperty("serverUrl");
    if (serverUrl != null && !serverUrl.equals(_serverUrl)) {
      prop.setProperty("serverUrl", serverUrl);
      store();
      EventKey.renewServerUrl(app);
    } 
  }
  
  private void renewSignUtilToken(String token) {
    String _token = prop.getProperty("token");
    if (token != null && !token.equals(_token)) {
      prop.setProperty("token", token);
      store();
      SignUtil.renewToken();
    } 
  }
}
