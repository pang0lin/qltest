package com.js.util.mail;

import com.js.system.util.SysSetupReader;

public final class MailConfig {
  public static String SMTP = "smtp.163.com";
  
  public static String getEmailCount() {
    return SysSetupReader.getInstance().getEmailCount();
  }
  
  public static String getEmailPWD() {
    return SysSetupReader.getInstance().getEmailPWD();
  }
  
  public static int getEmailPort() {
    return SysSetupReader.getInstance().getEmailPort();
  }
  
  public static boolean isUseEmail() {
    return SysSetupReader.getInstance().isUseEmail();
  }
  
  public static String getEmailSMTP() {
    return SysSetupReader.getInstance().getEmailSMTP();
  }
  
  public static String getEncryptionType() {
    return SysSetupReader.getInstance().getEncryptionType();
  }
}
