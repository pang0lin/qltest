package cn.zzy.action;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.List;
import java.util.Properties;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class Communicator {
  private static Communicator instance;
  
  private Socket socket = null;
  
  private InputStream inputStream = null;
  
  private OutputStream outputStream = null;
  
  private String ip;
  
  private int port;
  
  private BufferedReader reader = null;
  
  private String OAName;
  
  private String account;
  
  private String password;
  
  private String OAServerPath;
  
  private Communicator() {
    Properties props = new Properties();
    try {
      props.load(getClass().getResourceAsStream("conf.properties"));
      this.ip = props.getProperty("QServerIP");
      this.port = Integer.valueOf(props.getProperty("QServerPort")).intValue();
      this.OAName = props.getProperty("OAName");
      this.account = props.getProperty("account");
      this.password = props.getProperty("password");
      this.OAServerPath = props.getProperty("OAServerPath");
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public static synchronized Communicator getInstance() {
    if (instance == null)
      instance = new Communicator(); 
    return instance;
  }
  
  private void connect() throws IOException {
    this.socket = new Socket(this.ip, this.port);
    this.inputStream = this.socket.getInputStream();
    this.outputStream = this.socket.getOutputStream();
    this.reader = new BufferedReader(new InputStreamReader(this.inputStream));
    String str = this.reader.readLine();
    System.out.println(String.valueOf(this.ip) + ":" + this.port + str);
  }
  
  private void close() throws IOException {
    if (this.outputStream != null)
      this.outputStream.close(); 
    if (this.inputStream != null)
      this.inputStream.close(); 
    if (this.socket != null)
      this.socket.close(); 
  }
  
  private QResponse sendData(String xml) throws UnsupportedEncodingException, IOException, DocumentException {
    System.out.println("sendRequest:" + xml);
    this.outputStream.write(xml.getBytes("utf-8"));
    this.outputStream.flush();
    BufferedReader read = new BufferedReader(new InputStreamReader(this.inputStream));
    StringBuffer buffer = new StringBuffer();
    String readStr = null;
    if ((readStr = read.readLine()) != null)
      buffer.append(readStr); 
    System.out.println("recvResponse:" + buffer.toString());
    Document d = (new SAXReader()).read(new ByteArrayInputStream(buffer.toString().getBytes()));
    return new QResponse(d);
  }
  
  private QResponse sendData(byte[] data) throws UnsupportedEncodingException, IOException, DocumentException {
    System.out.println("sendRequest:字节流");
    this.outputStream.write(data);
    this.outputStream.flush();
    BufferedReader read = new BufferedReader(new InputStreamReader(this.inputStream));
    StringBuffer buffer = new StringBuffer();
    String readStr = null;
    if ((readStr = read.readLine()) != null)
      buffer.append(readStr); 
    System.out.println("recvResponse:" + buffer.toString());
    Document d = (new SAXReader()).read(new ByteArrayInputStream(buffer.toString().getBytes()));
    return new QResponse(d);
  }
  
  public QResponse sendRequest(BasicRequest data) {
    try {
      connect();
      String sendxml = data.toXml();
      sendxml = String.valueOf(sendxml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "").trim()) + "\r\n";
      QResponse qr = sendData(sendxml);
      return qr;
    } catch (IOException e) {
      e.printStackTrace();
      return new QResponse((new StringBuilder(String.valueOf(data.getTransid()))).toString(), "-1", "");
    } catch (DocumentException e) {
      e.printStackTrace();
      return new QResponse((new StringBuilder(String.valueOf(data.getTransid()))).toString(), "-1", "");
    } finally {
      try {
        close();
      } catch (IOException e) {
        e.printStackTrace();
      } 
    } 
  }
  
  public QResponse sendAttachmentRequest(AfficheRequest data, AttachmentRequest attachmentRequest) {
    try {
      connect();
      String attachmentRequestXml = data.toAttachmentRequestXml(attachmentRequest);
      attachmentRequestXml = String.valueOf(attachmentRequestXml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "").trim()) + "\r\n";
      QResponse response = sendData(attachmentRequestXml);
      if ("OASendFileResult".equals(response.getRoot()) && response.getCode().equals("0"))
        return response; 
      if (response.getCode().equals("0")) {
        byte[] content = (byte[])null;
        try {
          content = readFileByBytes(attachmentRequest.getPath());
        } catch (Exception e) {
          e.printStackTrace();
          return new QResponse((new StringBuilder(String.valueOf(data.getTransid()))).toString(), "-1", "读取文件[" + attachmentRequest.getPath() + "]失败");
        } 
        return sendData(content);
      } 
      return new QResponse((new StringBuilder(String.valueOf(data.getTransid()))).toString(), "-1", "");
    } catch (IOException e) {
      e.printStackTrace();
      return new QResponse((new StringBuilder(String.valueOf(data.getTransid()))).toString(), "-1", "");
    } catch (DocumentException e) {
      e.printStackTrace();
      return new QResponse((new StringBuilder(String.valueOf(data.getTransid()))).toString(), "-1", "");
    } finally {
      try {
        close();
      } catch (IOException e) {
        e.printStackTrace();
      } 
    } 
  }
  
  public QResponse sendFileRequest(AfficheRequest data, FileRequest fileRequest) {
    try {
      connect();
      String fileRequesttXml = data.toFileRequestXml(fileRequest);
      fileRequesttXml = String.valueOf(fileRequesttXml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "").trim()) + "\r\n";
      QResponse response = sendData(fileRequesttXml);
      if ("OASendFileResult".equals(response.getRoot()) && response.getCode().equals("0"))
        return response; 
      if (response.getCode().equals("0")) {
        byte[] content = (byte[])null;
        try {
          content = readFileByBytes(fileRequest.getPath());
        } catch (Exception e) {
          e.printStackTrace();
          return new QResponse((new StringBuilder(String.valueOf(data.getTransid()))).toString(), "-1", "读取文件[" + fileRequest.getPath() + "]失败");
        } 
        return sendData(content);
      } 
      return new QResponse((new StringBuilder(String.valueOf(data.getTransid()))).toString(), "-1", "");
    } catch (IOException e) {
      e.printStackTrace();
      return new QResponse((new StringBuilder(String.valueOf(data.getTransid()))).toString(), "-1", "");
    } catch (DocumentException e) {
      e.printStackTrace();
      return new QResponse((new StringBuilder(String.valueOf(data.getTransid()))).toString(), "-1", "");
    } finally {
      try {
        close();
      } catch (IOException e) {
        e.printStackTrace();
      } 
    } 
  }
  
  public QResponse sendRequest(AfficheRequest data) {
    try {
      List<AttachmentRequest> attachmentRequests = data.getAttachments();
      for (int i = 0; i < attachmentRequests.size(); ) {
        AttachmentRequest attachmentRequest = attachmentRequests.get(i);
        QResponse response = sendAttachmentRequest(data, attachmentRequest);
        if (response.getCode().equals("0")) {
          i++;
          continue;
        } 
        return response;
      } 
      List<FileRequest> fileRequests = data.getFiles();
      for (int j = 0; j < fileRequests.size(); ) {
        FileRequest fileRequest = fileRequests.get(j);
        QResponse response = sendFileRequest(data, fileRequest);
        if (response.getCode().equals("0")) {
          j++;
          continue;
        } 
        return response;
      } 
      connect();
      String sendxml = data.toXml();
      sendxml = String.valueOf(sendxml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "").trim()) + "\r\n";
      return sendData(sendxml);
    } catch (IOException e) {
      e.printStackTrace();
      return new QResponse((new StringBuilder(String.valueOf(data.getTransid()))).toString(), "-1", "");
    } catch (DocumentException e) {
      e.printStackTrace();
      return new QResponse((new StringBuilder(String.valueOf(data.getTransid()))).toString(), "-1", "");
    } finally {
      try {
        close();
      } catch (IOException e) {
        e.printStackTrace();
      } 
    } 
  }
  
  public byte[] readFileByBytes(String fileName) throws Exception {
    File sourceFile = new File(fileName);
    byte[] buffer = new byte[2048];
    InputStream fis = new FileInputStream(sourceFile);
    BufferedInputStream bis = new BufferedInputStream(fis, 2048);
    int readSize = -1;
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    while (bis != null && (readSize = bis.read(buffer)) != -1)
      output.write(buffer, 0, readSize); 
    bis.close();
    return output.toByteArray();
  }
  
  public String getOAName() {
    return this.OAName;
  }
  
  public void setOAName(String oAName) {
    this.OAName = oAName;
  }
  
  public String getAccount() {
    return this.account;
  }
  
  public void setAccount(String account) {
    this.account = account;
  }
  
  public String getPassword() {
    return this.password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public String getOAServerPath() {
    return this.OAServerPath;
  }
  
  public void setOAServerPath(String oAServerPath) {
    this.OAServerPath = oAServerPath;
  }
}
