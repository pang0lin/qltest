package com.js.oa.webmail.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MyAuthenticator extends Authenticator {
  private String strUser;
  
  private String strPswd;
  
  public MyAuthenticator(String username, String password) {
    this.strUser = username;
    this.strPswd = password;
  }
  
  protected PasswordAuthentication getPasswordAuthentication() {
    return new PasswordAuthentication(this.strUser, this.strPswd);
  }
}
