package com.js.util.util;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

public class SftpClientUtil {
  ChannelSftp sftp = null;
  
  private String host = "";
  
  private int port = 0;
  
  private String username = "";
  
  private String password = "";
  
  public SftpClientUtil(String host, int port, String username, String password) {
    this.host = host;
    this.port = port;
    this.username = username;
    this.password = password;
  }
  
  public void connect() throws Exception {
    JSch jsch = new JSch();
    Session sshSession = jsch.getSession(this.username, this.host, this.port);
    sshSession.setPassword(this.password);
    Properties sshConfig = new Properties();
    sshConfig.put("StrictHostKeyChecking", "no");
    sshSession.setConfig(sshConfig);
    sshSession.connect(20000);
    Channel channel = sshSession.openChannel("sftp");
    channel.connect();
    this.sftp = (ChannelSftp)channel;
  }
  
  public void disconnect() throws Exception {
    if (this.sftp != null)
      if (this.sftp.isConnected()) {
        this.sftp.disconnect();
      } else {
        this.sftp.isClosed();
      }  
  }
  
  public void upload(String directory, String uploadFile) throws Exception {
    this.sftp.cd(directory);
    File file = new File(uploadFile);
    this.sftp.put(new FileInputStream(file), file.getName());
  }
  
  public void uploadByDirectory(String directory) throws Exception {
    String uploadFile = "";
    List<String> uploadFileList = listFiles(directory);
    Iterator<String> it = uploadFileList.iterator();
    while (it.hasNext()) {
      uploadFile = ((String)it.next()).toString();
      upload(directory, uploadFile);
    } 
  }
  
  public void download(String directory, String downloadFile, String saveDirectory) throws Exception {
    String saveFile = String.valueOf(saveDirectory) + "//" + downloadFile;
    this.sftp.cd(directory);
    File file = new File(saveFile);
    this.sftp.get(downloadFile, new FileOutputStream(file));
  }
  
  public void downloadByDirectory(String directory, String saveDirectory) throws Exception {
    String downloadFile = "";
    List<String> downloadFileList = listFiles(directory);
    Iterator<String> it = downloadFileList.iterator();
    while (it.hasNext()) {
      downloadFile = ((String)it.next()).toString();
      if (downloadFile.toString().indexOf(".") < 0)
        continue; 
      download(directory, downloadFile, saveDirectory);
    } 
  }
  
  public void delete(String directory, String deleteFile) throws Exception {
    this.sftp.cd(directory);
    this.sftp.rm(deleteFile);
  }
  
  public List<String> listFiles(String directory) throws Exception {
    List<String> fileNameList = new ArrayList<String>();
    Vector fileList = this.sftp.ls(directory);
    Iterator<ChannelSftp.LsEntry> it = fileList.iterator();
    while (it.hasNext()) {
      String fileName = ((ChannelSftp.LsEntry)it.next()).getFilename();
      if (".".equals(fileName) || "..".equals(fileName))
        continue; 
      System.out.println("fileName:" + fileName);
      fileNameList.add(fileName);
    } 
    return fileNameList;
  }
  
  public void rename(String directory, String oldFileNm, String newFileNm) throws Exception {
    this.sftp.cd(directory);
    this.sftp.rename(oldFileNm, newFileNm);
  }
  
  public void cd(String directory) throws Exception {
    this.sftp.cd(directory);
  }
  
  public InputStream get(String directory) throws Exception {
    InputStream streatm = this.sftp.get(directory);
    return streatm;
  }
  
  public static void main(String[] args) {
    String host = "192.168.0.204";
    int port = 22;
    String user = "root";
    String pass = "123456";
    try {
      SftpClientUtil client = new SftpClientUtil(host, port, user, pass);
      client.connect();
      client.listFiles("/bin");
      client.upload("dd", "d:/test.txt");
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
}
