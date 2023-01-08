package com.qq.weixin.mp.aes;

import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Sample {
  public static void main(String[] args) throws Exception {
    String sToken = "QDG6eK";
    String sCorpID = "wx5823bf96d3bd56c7";
    String sEncodingAESKey = "jWmYm7qr5nMoAUwZRjGtBxmz3KA1tkAj3ykkR6q2B2C";
    WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(sToken, sEncodingAESKey, sCorpID);
    String sVerifyMsgSig = "5c45ff5e21c57e6ad56bac8758b79b1d9ac89fd3";
    String sVerifyTimeStamp = "1409659589";
    String sVerifyNonce = "263014780";
    String sVerifyEchoStr = "P9nAzCzyDtyTWESHep1vC5X9xho/qYX3Zpb4yKa9SKld1DsH3Iyt3tP3zNdtp+4RPcs8TgAE7OaBO+FZXvnaqQ==";
    try {
      String sEchoStr = wxcpt.VerifyURL(sVerifyMsgSig, sVerifyTimeStamp, 
          sVerifyNonce, sVerifyEchoStr);
      System.out.println("verifyurl echostr: " + sEchoStr);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    String sReqMsgSig = "477715d11cdb4164915debcba66cb864d751f3e6";
    String sReqTimeStamp = "1409659813";
    String sReqNonce = "1372623149";
    String sReqData = "<xml><ToUserName><![CDATA[wx5823bf96d3bd56c7]]></ToUserName><Encrypt><![CDATA[RypEvHKD8QQKFhvQ6QleEB4J58tiPdvo+rtK1I9qca6aM/wvqnLSV5zEPeusUiX5L5X/0lWfrf0QADHHhGd3QczcdCUpj911L3vg3W/sYYvuJTs3TUUkSUXxaccAS0qhxchrRYt66wiSpGLYL42aM6A8dTT+6k4aSknmPj48kzJs8qLjvd4Xgpue06DOdnLxAUHzM6+kDZ+HMZfJYuR+LtwGc2hgf5gsijff0ekUNXZiqATP7PF5mZxZ3Izoun1s4zG4LUMnvw2r+KqCKIw+3IQH03v+BCA9nMELNqbSf6tiWSrXJB3LAVGUcallcrw8V2t9EL4EhzJWrQUax5wLVMNS0+rUPA3k22Ncx4XXZS9o0MBH27Bo6BpNelZpS+/uh9KsNlY6bHCmJU9p8g7m3fVKn28H3KDYA5Pl/T8Z1ptDAVe0lXdQ2YoyyH2uyPIGHBZZIs2pDBS8R07+qN+E7Q==]]></Encrypt><AgentID><![CDATA[218]]></AgentID></xml>";
    try {
      String sMsg = wxcpt.DecryptMsg(sReqMsgSig, sReqTimeStamp, sReqNonce, sReqData);
      System.out.println("after decrypt msg: " + sMsg);
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      StringReader sr = new StringReader(sMsg);
      InputSource is = new InputSource(sr);
      Document document = db.parse(is);
      Element root = document.getDocumentElement();
      NodeList nodelist1 = root.getElementsByTagName("Content");
      String Content = nodelist1.item(0).getTextContent();
      System.out.println("Content：" + Content);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    String sRespData = "<xml><ToUserName><![CDATA[mycreate]]></ToUserName><FromUserName><![CDATA[wx5823bf96d3bd56c7]]></FromUserName><CreateTime>1348831860</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[this is a test]]></Content><MsgId>1234567890123456</MsgId><AgentID>128</AgentID></xml>";
    try {
      String sEncryptMsg = wxcpt.EncryptMsg(sRespData, sReqTimeStamp, sReqNonce);
      System.out.println("after encrypt sEncrytMsg: " + sEncryptMsg);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
