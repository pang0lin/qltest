package com.js.oa.webmail.util;

import com.js.oa.webmail.po.Affix;
import com.js.oa.webmail.po.Attach;
import com.js.oa.webmail.po.WebMail;
import com.js.oa.webmail.po.WebMailTemp;
import com.js.util.util.DateHelper;
import com.js.util.util.Random;
import com.sun.mail.pop3.POP3Folder;
import com.sun.mail.pop3.POP3Message;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.internet.ParseException;

public class MailUtil {
  private static List affixList = new ArrayList();
  
  private static int jumpNum = 0, countNum = 0, allMailNum = 0;
  
  public static Map CrawlEmailFromPOP(Long mailAccId, Long userId, String tempPath) throws Exception {
    affixList.clear();
    jumpNum = 0;
    countNum = 0;
    allMailNum = 0;
    Map<Object, Object> map = new HashMap<Object, Object>();
    POP3Folder folder = null;
    Store store = POPManager.buildPOPStore(mailAccId);
    try {
      if (!store.isConnected())
        store.connect(); 
    } catch (Exception e) {
      e.printStackTrace();
      map.put("ERORMESSAGE", "无法连接pop服务器,请检查用户名及密码！");
      return map;
    } 
    folder = (POP3Folder)store.getDefaultFolder().getFolder("INBOX");
    if (!folder.isOpen())
      folder.open(2); 
    try {
      Message[] message = folder.getMessages();
      int messageCounts = folder.getMessageCount();
      String uuid = "";
      String userIdTemp = "$" + userId + "$";
      List<WebMail> list = null;
      List<WebMailTemp> uuidList = null;
      if (messageCounts > 0) {
        list = new ArrayList();
        uuidList = new ArrayList();
        Map UUIDMap = UUIDManager.initUUIDMap(String.valueOf(userId));
        for (int i = 0; i < message.length; i++) {
          MimeMessage mm = (MimeMessage)message[i];
          uuid = folder.getUID((Message)mm);
          if (!UUIDMap.containsKey(String.valueOf(userIdTemp) + uuid)) {
            if (getSize(mm) < 20971520) {
              uuidList.add(setMailTemp(String.valueOf(userIdTemp) + uuid));
              dumpPart((Part)mm, tempPath, String.valueOf(userIdTemp) + uuid);
              list.add(setPOPMailList(mm, String.valueOf(userIdTemp) + uuid, userId, tempPath));
              setDelFlag(mm, mailAccId);
              ((POP3Message)message[i]).invalidate(true);
              map.put("MailList", list);
              map.put("AffixList", affixList);
              map.put("uuidList", uuidList);
              countNum++;
            } else {
              jumpNum++;
              countNum++;
            } 
            allMailNum++;
          } 
        } 
        map.put("jumpList", Integer.valueOf(jumpNum));
        map.put("countList", Integer.valueOf(countNum));
        map.put("allMailList", Integer.valueOf(allMailNum));
      } 
    } catch (Exception e) {
      e.printStackTrace();
      map.put("ERORMESSAGE", "邮箱服务器忙，请稍后重新接收");
      return map;
    } 
    if (folder != null)
      folder.close(true); 
    if (store != null)
      store.close(); 
    return map;
  }
  
  public static void setDelFlag(MimeMessage mm, Long mailAccId) {
    try {
      String delFlag = WebMailAccManager.getInstance().getBakFlag(mailAccId);
      if (delFlag != null && !delFlag.equals("")) {
        if (delFlag.equals("1")) {
          mm.setFlag(Flags.Flag.DELETED, false);
        } else {
          mm.setFlag(Flags.Flag.DELETED, true);
        } 
      } else {
        mm.setFlag(Flags.Flag.DELETED, true);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  protected static WebMail setPOPMailList(MimeMessage mm, String uuid, Long userId, String tempPath) throws Exception {
    WebMail wm = new WebMail();
    try {
      String subjectTemp = getSubject(mm);
      if (subjectTemp != null && !subjectTemp.equals("")) {
        wm.setMailId(uuid);
        wm.setUserId(userId);
        wm.setMailBox("0");
        wm.setSubject(subjectTemp);
        wm.setSendDate(DateHelper.date2String(mm.getSentDate(), ""));
        wm.setToo(getTo(mm));
        wm.setCc(getCc(mm));
        wm.setBc(getBc(mm));
        wm.setFrm(getFrom(mm));
        wm.setMailSize(getSize(mm));
        wm.setIsReply(isReply(mm));
        wm.setIsHtml(htmlFlag((Part)mm) ? "0" : "1");
        wm.setContent(getContent(mm, tempPath, htmlFlag((Part)mm)));
        wm.setAffixId(uuid);
        wm.setMailState("0");
        wm.setHasAttach("3");
      } 
    } catch (Exception e) {
      jumpNum++;
      countNum--;
      e.printStackTrace();
    } 
    return wm;
  }
  
  protected static WebMailTemp setMailTemp(String uuid) {
    WebMailTemp wm = new WebMailTemp();
    try {
      wm.setMailId(uuid);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return wm;
  }
  
  protected static Affix dumpPart(Part p, String attachFilePath, String uuid) throws Exception {
    String realPath = null;
    Affix affix = new Affix();
    String ct = p.getContentType().toLowerCase();
    String filename = p.getFileName();
    Enumeration<Header> e1 = p.getAllHeaders();
    while (e1.hasMoreElements()) {
      Header header = e1.nextElement();
      if ("Content-ID".equals(header.getName()))
        filename = header.getValue().replace("<", "").replace(">", ""); 
    } 
    if ((!p.isMimeType("text/plain") || ct.indexOf("name") >= 0) && (
      !p.isMimeType("text/html") || ct.indexOf("name") >= 0))
      if (p.isMimeType("multipart/*")) {
        Multipart mp = (Multipart)p.getContent();
        int count = 0;
        try {
          count = mp.getCount();
        } catch (Exception e) {
          if (filename == null)
            filename = getSubject((MimeMessage)p); 
          realPath = IOManager.saveFileAsEmail(p, attachFilePath, filename);
          realPath = realPath.substring(realPath.indexOf("/upload/webmail/"));
          affix.setMailId(uuid);
          affix.setAffixName(filename);
          affix.setAffixPath(realPath);
        } 
        for (int i = 0; i < count; i++)
          affix = dumpPart((Part)mp.getBodyPart(i), attachFilePath, uuid); 
      } else {
        if (filename == null && ct.equals("message/disposition-notification"))
          filename = "noname.txt"; 
        if (filename != null) {
          filename = getDecode2(filename);
          filename = adjustFileName(filename);
          realPath = IOManager.saveFile(p.getInputStream(), attachFilePath, p.getFileName());
          Calendar cal = Calendar.getInstance();
          String yearSub = String.valueOf(cal.get(1));
          realPath = realPath.substring(realPath.indexOf("/upload/" + yearSub + "/webmail/"));
          affix.setMailId(uuid);
          affix.setAffixName(filename);
          affix.setAffixPath(realPath);
          affix.setAffixSize(IOManager.getFileSize(realPath));
          affixList.add(affix);
        } 
      }  
    return affix;
  }
  
  protected static String getContent(MimeMessage mail, String tempPath, boolean isHtmlContent) throws Exception {
    String path = tempPath;
    path = String.valueOf(path) + UniqueCode.generate();
    path = String.valueOf(path) + ".jiusi";
    try {
      FileWriter f = new FileWriter(path);
      dispText((Part)mail, f, isHtmlContent);
      f.flush();
      f.close();
    } catch (Exception e) {
      path = null;
    } 
    Calendar cal = Calendar.getInstance();
    String year = String.valueOf(cal.get(1));
    path = path.substring(path.indexOf("/upload/" + year + "/webmail/"));
    return path;
  }
  
  private static void dispText(Part p, FileWriter out, boolean htmlFlag) throws Exception {
    String mail_file = "";
    try {
      String contentType = p.getContentType();
      if ((p.isMimeType("text/plain") || p.isMimeType("text/html")) && contentType.indexOf("name") < 0) {
        try {
          if (p.getContent() == null || p.getContent().toString() == null) {
            mail_file = "无正文";
          } else {
            if (contentType.toUpperCase().indexOf("GB2312") > 0 || contentType.toUpperCase().indexOf("GBK") > 0 || contentType.toUpperCase().indexOf("GB18030") > 0) {
              mail_file = new String(p.getContent().toString());
            } else if (contentType.toUpperCase().indexOf("UTF-8") > 0) {
              mail_file = p.getContent().toString();
            } else {
              mail_file = new String(p.getContent().toString().getBytes("ISO-8859-1"), "GBK");
            } 
            if (p.isMimeType("text/html"))
              mail_file = screen(mail_file); 
            for (int ai = 0; ai < affixList.size(); ai++) {
              Affix affix = affixList.get(ai);
              mail_file = mail_file.replace("http://set1.mail.qq.comcid:" + affix.getAffixName(), "/jsoa" + affix.getAffixPath());
              mail_file = mail_file.replace("cid:" + affix.getAffixName(), "/jsoa" + affix.getAffixPath());
            } 
            out.write(mail_file);
          } 
        } catch (Exception e) {
          e.printStackTrace();
        } 
      } else if (p.isMimeType("multipart/*")) {
        if (p.isMimeType("multipart/alternative")) {
          Multipart mp = (Multipart)p.getContent();
          int count = mp.getCount();
          if (count == 1) {
            dispText((Part)mp.getBodyPart(0), out, htmlFlag);
          } else if (count > 1) {
            for (int i = 1; i < count; i++)
              dispText((Part)mp.getBodyPart(i), out, htmlFlag); 
          } 
        } else {
          Multipart mp = (Multipart)p.getContent();
          int count = mp.getCount();
          for (int i = 0; i < count; i++)
            dispText((Part)mp.getBodyPart(i), out, htmlFlag); 
        } 
      } else if (p.isMimeType("message/rfc822") && p.getContentType().indexOf("name") < 0) {
        dispText((Part)p.getContent(), out, htmlFlag);
      } 
    } catch (Throwable throwable) {}
  }
  
  private static String screen(String mail_file) {
    String http_addr = "HTTP://";
    String href = "<A HREF=";
    String hrefTail = "</A>";
    String img = "<IMG ";
    String tail = ">";
    String blank = " ";
    String enter = "\r\n";
    String rightBracket = ")";
    String jsHead = "<SCRIPT";
    String jsTail = "</SCRIPT>";
    StringBuffer temp = new StringBuffer();
    StringBuffer temp1 = new StringBuffer();
    int h = 0;
    try {
      h = mail_file.toUpperCase().indexOf(jsHead);
    } catch (Exception exception) {}
    while (h > 0) {
      String mailAppend = "";
      try {
        mailAppend = mail_file.substring(0, h);
        temp1.append(mailAppend);
        mail_file = mail_file.substring(h);
        int l = mail_file.toUpperCase().indexOf(jsTail);
        mail_file = mail_file.substring(l + jsTail.length());
        h = mail_file.toUpperCase().indexOf(jsHead);
      } catch (Exception exception) {}
    } 
    mail_file = String.valueOf(temp1.toString()) + mail_file;
    return mail_file;
  }
  
  private static boolean htmlFlag(Part mm) throws Exception {
    boolean htmlFlag = false;
    try {
      int multipartPos = -1;
      try {
        multipartPos = mm.getContentType().toUpperCase().indexOf(
            "MULTIPART");
      } catch (Exception exception) {}
      if (multipartPos != -1) {
        Multipart mp = (Multipart)mm;
        int count = mp.getCount();
        for (int i = 0; i < count; i++) {
          int namePos = -1;
          try {
            namePos = mp.getBodyPart(i).getContentType().indexOf(
                "name");
          } catch (Exception exception) {}
          if (mp.getBodyPart(i).isMimeType("text/html") && 
            namePos < 0) {
            htmlFlag = true;
            return htmlFlag;
          } 
          if (mp.getBodyPart(i).isMimeType("Multipart/*"))
            try {
              htmlFlag = htmlFlag((Part)mp.getBodyPart(i));
            } catch (Exception exception) {} 
        } 
      } 
    } catch (Exception exception) {}
    return htmlFlag;
  }
  
  protected static String getSubject(MimeMessage mail) throws Exception {
    String subj = "";
    subj = mail.getSubject();
    if (subj == null || "".equals(subj)) {
      subj = "(无)";
    } else {
      subj = mail.getHeader("subject", null);
      subj = getDecode2(subj);
    } 
    return subj;
  }
  
  protected static int getSize(MimeMessage mail) {
    try {
      return mail.getSize();
    } catch (Exception exception) {
      return 0;
    } 
  }
  
  protected static String isReply(MimeMessage mail) {
    String flag = "0";
    try {
      String[] needreply = mail.getHeader("Disposition-Notification-To");
      if (needreply != null && needreply[0].trim() != null)
        return "1"; 
      return "0";
    } catch (Exception exception) {
      return flag;
    } 
  }
  
  protected static String getReplyTo(MimeMessage mail) {
    String replyTo = "";
    try {
      InternetAddress[] ia = (InternetAddress[])mail.getReplyTo();
      replyTo = getPerNameMail(ia);
    } catch (Exception exception) {}
    return replyTo;
  }
  
  protected static String getFrom(MimeMessage mail) throws Exception {
    String from = "";
    try {
      String fromStr = mail.getHeader("From", null);
      if (fromStr != null && fromStr.length() > 0) {
        InternetAddress[] ia = (InternetAddress[])null;
        try {
          ia = InternetAddress.parse(fromStr);
          from = getPerNameMail(ia);
        } catch (Exception e) {
          from = getDecode2(fromStr);
        } 
      } 
    } catch (Exception e) {
      throw new Exception("解析邮件发件人地址错误,错误码（" + e.hashCode() + "）错误（" + e.getMessage() + "）");
    } 
    return from;
  }
  
  protected static String getTo(MimeMessage mail) throws Exception {
    String to = mail.getHeader("To", null);
    try {
      InternetAddress[] ia = (InternetAddress[])mail.getRecipients(Message.RecipientType.TO);
      to = getPerNameMail(ia);
    } catch (Exception exception) {}
    return to;
  }
  
  protected static String getCc(MimeMessage mail) throws Exception {
    String cc = mail.getHeader("Cc", null);
    try {
      InternetAddress[] ia = (InternetAddress[])mail.getRecipients(Message.RecipientType.CC);
      cc = getPerNameMail(ia);
    } catch (Exception exception) {}
    return cc;
  }
  
  protected static String getBc(MimeMessage mail) throws Exception {
    String bcc = mail.getHeader("Bcc", null);
    try {
      InternetAddress[] ia = (InternetAddress[])mail.getRecipients(Message.RecipientType.BCC);
      bcc = getPerNameMail(ia);
    } catch (Exception exception) {}
    return bcc;
  }
  
  protected static int getPriority(MimeMessage mail) {
    try {
      String ps = null;
      String[] apriority = mail.getHeader("X-Priority");
      if (apriority != null) {
        ps = apriority[0];
      } else {
        ps = "2";
      } 
      return Integer.parseInt(ps);
    } catch (Exception exception) {
      return 3;
    } 
  }
  
  private static String getPerNameMail(InternetAddress[] ia) throws ParseException {
    String from = "";
    String email = "";
    if (ia != null)
      for (int j = 0; j < ia.length; j++) {
        String personal = (ia[j] != null) ? ia[j].toString() : "";
        email = (ia[j] != null) ? ia[j].getAddress() : "";
        personal = getDecode2(personal);
        try {
          if (!personal.equals("")) {
            if (personal.charAt(0) == '"')
              personal = personal.substring(1, personal.length()); 
            if (personal.charAt(personal.length() - 1) == '"')
              personal = personal.substring(0, personal.length() - 1); 
            if (personal.indexOf("\" <") > 0)
              personal = String.valueOf(personal.substring(0, personal.indexOf("\" <"))) + personal.substring(personal.indexOf("\" <") + 1, personal.length()); 
          } 
        } catch (Exception e) {
          e.printStackTrace();
        } 
        if (personal.equals(email)) {
          personal = "";
          from = String.valueOf(from) + email;
        } else {
          from = String.valueOf(from) + personal;
        } 
        if (j < ia.length - 1)
          from = String.valueOf(from) + ","; 
      }  
    return from;
  }
  
  private static String adjustFileName(String fileName) {
    String str = fileName;
    str = System14.replace(str, "\t", "");
    str = System14.replace(str, " ", "");
    str = System14.replace(str, "\r", "");
    str = System14.replace(str, "\n", "");
    return str;
  }
  
  private static String getDecode2(String _name) throws ParseException {
    String rightName = _name;
    String temp = "";
    String codeType = "";
    try {
      if (_name != null) {
        temp = _name.toUpperCase();
        codeType = getMailEncode(temp);
        if (codeType.equals("NOENCODE")) {
          rightName = new String(_name.getBytes("ISO-8859-1"), "GBK");
        } else {
          if (codeType.equals("DEFALT"))
            _name = System14.replace(_name, "=??", "=?GBK?"); 
          String _namehead = _name.substring(0, codeType.length() + 5);
          String[] _names = _name.split("\r\n");
          String _namenew = "";
          String _namenewLast = "";
          if (_names.length > 1) {
            for (int j = 0; j < _names.length; j++) {
              _names[j] = _names[j].trim();
              if (_names[j].lastIndexOf("?=") >= 0)
                _namenew = String.valueOf(_namenew) + _names[j].substring(codeType.length() + 5, _names[j].lastIndexOf("?=")); 
              if (_names[j].substring(_names[j].lastIndexOf("?=") + 2) != "")
                _namenewLast = _names[j].substring(_names[j].lastIndexOf("?=") + 2); 
            } 
            if (!"".equals(_namenew))
              _name = String.valueOf(_namehead) + _namenew + "?=" + _namenewLast; 
          } 
          rightName = MimeUtility.decodeWord(_name);
        } 
      } 
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } 
    int iPos = -1;
    iPos = rightName.indexOf("=?");
    temp = rightName;
    if (!codeType.equals("GBK") && !codeType.equals("UTF-8")) {
      if (iPos > 0)
        rightName = rightName.substring(0, iPos); 
      rightName = toGbk(rightName, codeType);
    } 
    String fileNameString = "";
    if (rightName.indexOf("=?") >= 0)
      fileNameString = rightName.substring(0, rightName.indexOf("=?")); 
    if (iPos > 0)
      rightName = String.valueOf(fileNameString) + getDecode2(temp.substring(iPos)); 
    return rightName;
  }
  
  private static String gb2iso(String qs) throws IOException {
    if (qs == null)
      return ""; 
    return str.ChinesetoUnicode(qs);
  }
  
  protected static String toGbk(String src, String codeType) {
    try {
      if (src == null)
        return null; 
      return new String(src.getBytes(codeType), "GBK");
    } catch (Exception exception) {
      return src;
    } 
  }
  
  private static String iso2gb(String qs) throws IOException {
    if (qs == null)
      return ""; 
    return str.UnicodetoChinese(qs);
  }
  
  private static String getMailEncode(String src) {
    String enType = "NOENCODE";
    int ib = src.indexOf("=?");
    if (ib > -1) {
      int ie = src.indexOf("?", ib + 2);
      if (ie > -1)
        enType = src.substring(ib + 2, ie); 
      if (ib + 2 == ie)
        enType = "DEFALT"; 
    } 
    return enType;
  }
  
  public static MimeMessage changFormat(Map map, WebMail wm, String attachPath) throws Exception {
    Session sess = null;
    String host = (String)map.get("SmtpHost");
    String name = (String)map.get("MailAccUser");
    String pwd = (String)map.get("MailAccPwd");
    if (name == null || host == null) {
      sess = null;
    } else {
      sess = POPManager.getSmtpSession(host, name, pwd);
      if (sess == null)
        throw new Exception("连接到邮件服务器失败，请检查网络是否连接、邮件配置是否正确"); 
    } 
    return changFormat(sess, wm, attachPath);
  }
  
  public static MimeMessage changFormat(Session sess, WebMail wm, String attachPath) throws Exception {
    String tempStr = null;
    String content = null;
    MimeMessage msg = new MimeMessage(sess);
    if (wm.getIsReply().equals("1"))
      msg.addHeader("Disposition-Notification-To", wm.getReplyTo()); 
    msg.setHeader("Content-Type", "text/plain; charset=" + str.getChineseCharset());
    msg.setRecipients(Message.RecipientType.TO, (Address[])InternetAddress.parse(MimeUtility.encodeWord(wm.getToo(), str.getChineseCharset(), null), false));
    if ((tempStr = wm.getFrm()) != null && !tempStr.equals(""))
      msg.setFrom((Address)new InternetAddress(wm.getFromAdd(), wm.getFromName(), str.getChineseCharset())); 
    if ((tempStr = wm.getCc()) != null && !tempStr.equals(""))
      msg.setRecipients(Message.RecipientType.CC, (Address[])InternetAddress.parse(MimeUtility.encodeWord(tempStr, str.getChineseCharset(), null), false)); 
    if ((tempStr = wm.getBc()) != null && !tempStr.equals(""))
      msg.setRecipients(Message.RecipientType.BCC, (Address[])InternetAddress.parse(MimeUtility.encodeWord(tempStr, str.getChineseCharset(), null), false)); 
    msg.setHeader("X-Priority", Integer.toString(wm.getPriority()));
    msg.setSubject(wm.getSubject(), str.getChineseCharset());
    msg.setSentDate(DateHelper.string2Date(wm.getSendDate(), null));
    if (IOManager.isFile(wm.getContent())) {
      content = wm.getContentByIOStr(wm.getContent());
    } else {
      content = wm.getContent();
    } 
    if (wm.getMailId() != null && !wm.getMailId().equals("")) {
      if (!AffixManager.getInstance().getMailAffixFlag(wm.getMailId())) {
        msg.setText(content, str.getChineseCharset());
        msg.setHeader("Content-Type", "text/html; charset=" + str.getChineseCharset());
      } else {
        MimeMultipart mmp = new MimeMultipart();
        MimeBodyPart mbp = new MimeBodyPart();
        mbp.setText(content, str.getChineseCharset());
        mbp.setHeader("Content-Type", "text/html; charset=" + str.getChineseCharset());
        mmp.addBodyPart((BodyPart)mbp);
        Affix affix = null;
        List<Affix> affixList = AffixManager.getInstance().getAffixList(wm.getMailId());
        int len = affixList.size();
        for (int i = 0; i < len; i++) {
          affix = affixList.get(i);
          mbp = new MimeBodyPart();
          mbp.setFileName(affix.getAffixName());
          mbp.setFileName(MimeUtility.encodeWord(affix.getAffixName(), str.getChineseCharset(), null));
          String contextPath = MailUtil.class.getClassLoader().getResource("./../../").getPath();
          FileDataSource fds = new FileDataSource(String.valueOf(contextPath) + affix.getAffixPath());
          mbp.setDataHandler(new DataHandler((DataSource)fds));
          mmp.addBodyPart((BodyPart)mbp);
        } 
        msg.setContent((Multipart)mmp);
      } 
    } else {
      List<Attach> attachList = AffixManager.getInstance().getBakAttahListById(wm.getMailInfoId());
      if (attachList == null || attachList.size() <= 0) {
        msg.setText(content, str.getChineseCharset());
        msg.setHeader("Content-Type", "text/html; charset=" + str.getChineseCharset());
      } else {
        MimeMultipart mmp = new MimeMultipart();
        MimeBodyPart mbp = new MimeBodyPart();
        mbp.setText(content, str.getChineseCharset());
        mbp.setHeader("Content-Type", "text/html; charset=" + str.getChineseCharset());
        mmp.addBodyPart((BodyPart)mbp);
        Attach attach = null;
        int len = attachList.size();
        for (int i = 0; i < len; i++) {
          attach = attachList.get(i);
          mbp = new MimeBodyPart();
          mbp.setFileName(attach.getAttachName());
          mbp.setFileName(MimeUtility.encodeWord(attach.getAttachName(), str.getChineseCharset(), null));
          FileDataSource fds = new FileDataSource(String.valueOf(attachPath) + attach.getAttachDisName());
          mbp.setDataHandler(new DataHandler((DataSource)fds));
          mmp.addBodyPart((BodyPart)mbp);
        } 
        msg.setContent((Multipart)mmp);
      } 
    } 
    msg.addHeader("X-MSMail-Priority", "Normal");
    msg.addHeader("X-Mailer", "Microsoft Outlook Express 6.00.2900.2869");
    msg.addHeader("X-MimeOLE", "Produced By Microsoft MimeOLE V6.00.2900.2869");
    return msg;
  }
  
  public static String zipMail(List<WebMail> list, String tempPath, Long userId, String attachPath) throws Exception {
    IOManager.checkDir(tempPath);
    String path = String.valueOf(tempPath) + ".zip/";
    IOManager.delDirectory(path);
    IOManager.checkDir(path);
    String mailPath = String.valueOf(path) + userId + "_cur/";
    IOManager.checkDir(mailPath);
    MimeMessage mm = null;
    Map map = WebMailAccManager.getInstance().getDefaultMail(userId);
    FileOutputStream fos = null;
    for (int i = 0; i < list.size(); i++) {
      WebMail wm = list.get(i);
      mm = changFormat(map, wm, attachPath);
      String myRandom = (new Random()).getRandom();
      fos = new FileOutputStream(String.valueOf(mailPath) + myRandom);
      mm.writeTo(fos);
      fos.close();
    } 
    IOManager.setFileExtName(mailPath, "eml");
    String zipFile = String.valueOf(tempPath) + userId + "_mail.zip";
    IOManager.delDirectory(zipFile);
    try {
      IOManager.zip(zipFile, mailPath);
      IOManager.delDirectory(path);
    } catch (Exception e) {
      throw new Exception("邮件压缩错误：" + e.getMessage());
    } 
    return zipFile;
  }
  
  public static void main(String[] args) throws Exception {}
}
