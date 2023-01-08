package com.js.oa.webmail.util;

import com.js.oa.webmail.po.WebMailAcc;
import com.js.util.util.EncryptSelf;
import com.sun.net.ssl.internal.ssl.Provider;
import java.security.Security;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;

public class POPManager {
  public static Store getLocalStore(String mboxPath) {
    Properties props = new Properties();
    props.put("mail.store.maildir.autocreatedir", "true");
    props.put("mail.store.maildir.cachefolders", "true");
    Session session = Session.getInstance(props);
    session.setDebug(true);
    String URL = "maildir:" + mboxPath;
    Store store = null;
    try {
      URLName urlName = new URLName(URL);
      store = session.getStore(urlName);
      store.connect();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return store;
  }
  
  public static Session getSmtpSession(String smtphost, String username, String password) {
    Session session = null;
    try {
      Properties props = System.getProperties();
      props.put("mail.smtp.host", smtphost);
      props.put("mail.smtp.auth", "true");
      MyAuthenticator auth = new MyAuthenticator(username, password);
      session = Session.getInstance(props, auth);
      session.setDebug(false);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return session;
  }
  
  public static Store buildPOPStore(WebMailAcc webMailAcc) {
    String user = webMailAcc.getMailAccUser();
    String pwd = webMailAcc.getMailAccPwd();
    pwd = EncryptSelf.selfDecoder(pwd);
    String pop = webMailAcc.getPop();
    String popJMFS = webMailAcc.getPopJMFS();
    int popPort = webMailAcc.getPopPort();
    return buildPOPStore(user, pwd, pop, popPort, popJMFS);
  }
  
  public static Store buildPOPStore(Long mailAccId) {
    String user = "";
    String pwd = "";
    String pop = "";
    int popPort = 110;
    String popJMFS = "SSL";
    if (mailAccId != null && !mailAccId.equals("")) {
      user = WebMailAccManager.getInstance().getMyChooseAcc(mailAccId);
      pwd = WebMailAccManager.getInstance().getPWD(mailAccId);
      pop = WebMailAccManager.getInstance().getPOP(mailAccId);
      popPort = WebMailAccManager.getInstance().getPOPPort(mailAccId);
      popJMFS = WebMailAccManager.getInstance().getPopJMFS(mailAccId);
    } else {
      return null;
    } 
    return buildPOPStore(user, pwd, pop, popPort, popJMFS);
  }
  
  public static Store buildPOPStore(String user, String pwd, String pop, int popPort, String popJMFS) {
    Properties props = null;
    Session session = null;
    Store store = null;
    try {
      props = System.getProperties();
      Security.addProvider(new Provider());
      String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
      props.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
      props.setProperty("mail.pop3.socketFactory.fallback", "false");
      props.setProperty("mail.pop3.port", popPort);
      props.setProperty("mail.pop3.socketFactory.port", popPort);
      Authenticator auth = new MyAuthenticator(user, pwd);
      session = Session.getInstance(props, auth);
      session.setDebug(false);
      URLName urln = new URLName("pop3", pop, popPort, null, user, pwd);
      store = session.getStore(urln);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
    return store;
  }
  
  public static void main(String[] args) throws Exception {}
}
