package com.js.util.mail;

import com.js.oa.webmail.po.WebMailAcc;
import com.js.util.util.EncryptSelf;

public class MailSender {
  public static String autoSend(Mail mailVO, String smtp, String from, String pwd, int port, String encryptionType) {
    String state = "7000";
    try {
      state = send(mailVO, smtp, from, pwd, port, encryptionType);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return state;
  }
  
  public static String buildWebMail(Mail mailVO) throws Exception {
    if (MailConfig.isUseEmail() && MailConfig.getEmailSMTP() != null)
      return send(mailVO); 
    return "5000";
  }
  
  public static String send(Mail mailVO, String smtp, String from, String pwd, int port, String encryptionType) throws Exception {
    MailServer mailer = new MailServer(smtp, from, pwd, port, encryptionType);
    return sendMailSTMP(mailVO, mailer);
  }
  
  public static String send(Mail mailVO, WebMailAcc wma) throws Exception {
    MailServer mailer = new MailServer(wma.getSmtp(), wma.getMailAccUser(), EncryptSelf.selfDecoder(wma.getMailAccPwd()), wma.getSmtpPort(), wma.getSmtpJMFS());
    return sendMailSTMP(mailVO, mailer);
  }
  
  public static String send(Mail mailVO) throws Exception {
    MailServer mailer = new MailServer();
    return sendMailSTMP(mailVO, mailer);
  }
  
  public static String sendMailSTMP(Mail mailVO, MailServer mailer) {
    if (mailer.setSubject(mailVO.getSubjectTitle())) {
      if (mailer.setBody(mailVO.getBoby(), mailVO.isHtml())) {
        if (mailer.addFileAffix(mailVO.getFileAffix())) {
          if (mailVO.getSendTo() != null && mailVO.getCopyTo() != null && mailVO.getSecretTo() != null) {
            mailer.setTo(mailVO.getSendTo());
            mailer.setCopyTo(mailVO.getCopyTo());
            mailer.setSecretTo(mailVO.getSecretTo());
            mailVO.setSendType("SCS");
            if (mailer.sendout(mailVO.getSendType()))
              return "6000"; 
            return "7000";
          } 
          if (mailVO.getSendTo() != null && mailVO.getCopyTo() != null && mailVO.getSecretTo() == null) {
            mailer.setTo(mailVO.getSendTo());
            mailer.setCopyTo(mailVO.getCopyTo());
            mailVO.setSendType("SC");
            if (mailer.sendout(mailVO.getSendType()))
              return "6000"; 
            return "7000";
          } 
          if (mailVO.getSendTo() != null && mailVO.getSecretTo() != null && mailVO.getCopyTo() == null) {
            mailer.setTo(mailVO.getSendTo());
            mailer.setSecretTo(mailVO.getSecretTo());
            mailVO.setSendType("SS");
            if (mailer.sendout(mailVO.getSendType()))
              return "6000"; 
            return "7000";
          } 
          if (mailVO.getSecretTo() != null && mailVO.getCopyTo() != null && mailVO.getSendTo() != null) {
            mailer.setSecretTo(mailVO.getSecretTo());
            mailer.setCopyTo(mailVO.getCopyTo());
            mailVO.setSendType("CS");
            if (mailer.sendout(mailVO.getSendType()))
              return "6000"; 
            return "7000";
          } 
          if (mailVO.getSendTo() != null && mailVO.getSecretTo() == null && mailVO.getCopyTo() == null) {
            mailer.setTo(mailVO.getSendTo());
            mailVO.setSendType("TO");
            if (mailer.sendout(mailVO.getSendType()))
              return "6000"; 
            return "7000";
          } 
          if (mailVO.getSendTo() == null && mailVO.getSecretTo() == null && mailVO.getCopyTo() != null) {
            mailer.setCopyTo(mailVO.getCopyTo());
            mailVO.setSendType("CC");
            if (mailer.sendout(mailVO.getSendType()))
              return "6000"; 
            return "7000";
          } 
          if (mailVO.getSendTo() == null && mailVO.getSecretTo() != null && mailVO.getCopyTo() == null) {
            mailer.setSecretTo(mailVO.getSecretTo());
            mailVO.setSendType("BCC");
            if (mailer.sendout(mailVO.getSendType()))
              return "6000"; 
            return "7000";
          } 
          return "7000";
        } 
        return "4000";
      } 
      return "3000";
    } 
    return "2000";
  }
}
