package cn.zzy.action;

import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class AfficheRequest extends BasicRequest {
  private String subject;
  
  private short type;
  
  private List<AttachmentRequest> attachments;
  
  private List<FileRequest> files;
  
  private String html;
  
  private String content;
  
  private long sid;
  
  public String getSubject() {
    return this.subject;
  }
  
  public void setSubject(String subject) {
    this.subject = subject;
  }
  
  public long getSid() {
    return this.sid;
  }
  
  public void setSid(long sid) {
    this.sid = sid;
  }
  
  public List<AttachmentRequest> getAttachments() {
    return this.attachments;
  }
  
  public void setAttachments(List<AttachmentRequest> attachments) {
    this.attachments = attachments;
  }
  
  public List<FileRequest> getFiles() {
    return this.files;
  }
  
  public void setFiles(List<FileRequest> files) {
    this.files = files;
  }
  
  public String getContent() {
    return this.content;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
  
  public short getType() {
    return this.type;
  }
  
  public void setType(short type) {
    this.type = type;
  }
  
  public String getHtml() {
    return this.html;
  }
  
  public void setHtml(String html) {
    this.html = html;
  }
  
  public String toXml() {
    Document document = DocumentHelper.createDocument();
    Element rootElement = document.addElement("OASendAfficheRequest");
    Element transid = rootElement.addElement("transid");
    transid.addText((new StringBuilder(String.valueOf(getTransid()))).toString());
    Element oaname = rootElement.addElement("oaname");
    oaname.addText(getOaname());
    Element account = rootElement.addElement("account");
    account.addText(getAccount());
    Element password = rootElement.addElement("password");
    password.addText(getPassword());
    Element domain = rootElement.addElement("domain");
    if (getDomain() == null || getDomain().length() == 0)
      setDomain("tjqnzyxy.cn"); 
    domain.addText(getDomain());
    if (getSender() != null && getSender().length() > 0) {
      Element sender = rootElement.addElement("sender");
      sender.addText(getSenderData());
    } 
    if (getSendername() != null && getSendername().length() > 0) {
      Element sendername = rootElement.addElement("sendername");
      sendername.addText(getSendername());
    } 
    if (getRecvlist() != null && (getRecvlist()).length > 0) {
      Element recvlist = rootElement.addElement("recvlist");
      recvlist.addText(getRecvlistData());
    } 
    Element sid = rootElement.addElement("sid");
    sid.addText(String.valueOf(getSid()));
    Element subject = rootElement.addElement("subject");
    subject.addText(getSubject());
    Element type = rootElement.addElement("type");
    type.addText(String.valueOf(getType()));
    Element content = rootElement.addElement("content");
    content.addText(getContent());
    if (getHtml() != null) {
      setHtml(getHtml().replace("/jsoa/upload/", "http://oa.tjqnzyxy.cn:85/jsoa/upload/"));
      String s = "";
      byte[] b = (byte[])null;
      try {
        b = getHtml().getBytes("utf-8");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      } 
      if (b != null)
        s = Base64.encodeBase64String(b); 
      s.replaceAll("\r\n", "");
      Element html = rootElement.addElement("html");
      html.addText(s);
    } 
    if (getAttachments() != null && getAttachments().size() > 0) {
      Element attachments = rootElement.addElement("attachments");
      for (int i = 0; i < getAttachments().size(); i++) {
        AttachmentRequest ar = getAttachments().get(i);
        Element attachment = attachments.addElement("attachment");
        attachment.addAttribute("id", String.valueOf(ar.getId()));
        attachment.addAttribute("size", String.valueOf(ar.getSize()));
        attachment.addText("");
      } 
    } 
    if (getFiles() != null && getFiles().size() > 0) {
      Element files = rootElement.addElement("files");
      for (int i = 0; i < getFiles().size(); i++) {
        FileRequest fr = getFiles().get(i);
        Element file = files.addElement("file");
        file.addAttribute("id", String.valueOf(fr.getId()));
        file.addAttribute("name", fr.getName());
        file.addAttribute("size", String.valueOf(fr.getSize()));
        file.addText("");
      } 
    } 
    return document.asXML();
  }
  
  public String toAttachmentRequestXml(AttachmentRequest attachmentRequest) {
    Document document = DocumentHelper.createDocument();
    Element rootElement = document.addElement("OASendFileRequest");
    Element transid = rootElement.addElement("transid");
    transid.addText("2");
    Element oaname = rootElement.addElement("oaname");
    oaname.addText(getOaname());
    Element account = rootElement.addElement("account");
    account.addText(getAccount());
    Element password = rootElement.addElement("password");
    password.addText(getPassword());
    Element domain = rootElement.addElement("domain");
    if (getDomain() == null || getDomain().length() == 0)
      setDomain("tjqnzyxy.cn"); 
    domain.addText(getDomain());
    Element file = rootElement.addElement("file");
    file.addAttribute("id", String.valueOf(attachmentRequest.getId()));
    file.addAttribute("size", String.valueOf(attachmentRequest.getSize()));
    file.addText("");
    return document.asXML();
  }
  
  public String toFileRequestXml(FileRequest fileRequest) {
    Document document = DocumentHelper.createDocument();
    Element rootElement = document.addElement("OASendFileRequest");
    Element transid = rootElement.addElement("transid");
    transid.addText((new StringBuilder(String.valueOf(System.currentTimeMillis()))).toString());
    Element oaname = rootElement.addElement("oaname");
    oaname.addText(getOaname());
    Element account = rootElement.addElement("account");
    account.addText(getAccount());
    Element password = rootElement.addElement("password");
    password.addText(getPassword());
    Element domain = rootElement.addElement("domain");
    if (getDomain() == null || getDomain().length() == 0)
      setDomain("tjqnzyxy.cn"); 
    domain.addText(getDomain());
    Element file = rootElement.addElement("file");
    file.addAttribute("id", String.valueOf(fileRequest.getId()));
    file.addAttribute("size", String.valueOf(fileRequest.getSize()));
    file.addText("");
    return document.asXML();
  }
}
