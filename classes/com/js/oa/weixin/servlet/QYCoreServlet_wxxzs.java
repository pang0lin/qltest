package com.js.oa.weixin.servlet;

import com.js.oa.userdb.util.DbOpt;
import com.js.oa.weixin.manage.WeixinManageAction;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.qq.weixin.mp.config.pojo.App;
import com.qq.weixin.mp.pojo.AppRoom;
import com.qq.weixin.mp.util.MessageUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URLDecoder;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class QYCoreServlet_wxxzs extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html;charset=utf-8");
    request.setCharacterEncoding("utf-8");
    String sToken = WeixinManageAction.getPropValue("C_Token");
    String sCorpID = WeixinManageAction.getPropValue("sCorpID");
    String sEncodingAESKey = WeixinManageAction.getPropValue("C_EncodingAESKey");
    try {
      WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(sToken, sEncodingAESKey, 
          sCorpID);
      String sVerifyMsgSig = URLDecoder.decode(
          request.getParameter("msg_signature"), "utf-8");
      String sVerifyTimeStamp = URLDecoder.decode(
          request.getParameter("timestamp"), "utf-8");
      String sVerifyNonce = URLDecoder.decode(
          request.getParameter("nonce"), "utf-8");
      String sVerifyEchoStr = URLDecoder.decode(
          request.getParameter("echostr"), "utf-8");
      PrintWriter out = response.getWriter();
      try {
        String sEchoStr = wxcpt.VerifyURL(sVerifyMsgSig, sVerifyTimeStamp, 
            sVerifyNonce, sVerifyEchoStr);
        out.print(sEchoStr);
        out.close();
        out = null;
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } catch (AesException e1) {
      e1.printStackTrace();
    } 
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String msg_signature = URLDecoder.decode(
        request.getParameter("msg_signature"), "utf-8");
    String timestamp = URLDecoder.decode(
        request.getParameter("timestamp"), "utf-8");
    String nonce = URLDecoder.decode(
        request.getParameter("nonce"), "utf-8");
    String bodyString = getBodyString(request.getReader());
    SAXBuilder builder = new SAXBuilder();
    try {
      Document doc = builder.build(new StringReader(bodyString));
      Element root = doc.getRootElement();
      Element encryptEle = root.getChild("Encrypt");
      if (encryptEle != null) {
        String encryptContent = encryptEle.getValue();
        String sToken = WeixinManageAction.getPropValue("C_Token");
        String sCorpID = WeixinManageAction.getPropValue("sCorpID");
        String sEncodingAESKey = 
          WeixinManageAction.getPropValue("C_EncodingAESKey");
        try {
          WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(sToken, sEncodingAESKey, 
              sCorpID);
          String content = wxcpt.VerifyURL(msg_signature, timestamp, 
              nonce, encryptContent);
          if (content.indexOf("<![CDATA[subscribe]]>") > 0) {
            doc = builder.build(new StringReader(content));
            root = doc.getRootElement();
            String fromUserAccount = root.getChild("FromUserName").getValue();
            DbOpt db = new DbOpt();
            String fromUserName = "";
            try {
              fromUserName = db.executeQueryToStr("select max(empName) from org_employee where useraccounts='" + fromUserAccount + "' and userisdeleted=0");
              db.close();
            } catch (SQLException e) {
              e.printStackTrace();
            } catch (Exception e) {
              e.printStackTrace();
            } 
            System.out.println("用户【" + fromUserName + "】关注了企业号");
            for (int i = 0; i < AppRoom.getApps().size(); i++) {
              App app = AppRoom.getApps().get(i);
              if (app.getSubscribeTip() != null && !"".equals(app.getSubscribeTip()))
                MessageUtil.sendNewMessageToUser(fromUserAccount, Integer.valueOf(app.getAppid()).intValue(), "功能介绍", app.getSubscribeTip(), "", ""); 
            } 
          } else if (content.indexOf("<![CDATA[unsubscribe]]>") > 0) {
            doc = builder.build(new StringReader(content));
            root = doc.getRootElement();
            String fromUserAccount = root.getChild("FromUserName").getValue();
            DbOpt db = new DbOpt();
            String fromUserName = "";
            try {
              fromUserName = db.executeQueryToStr("select max(empName) from org_employee where useraccounts='" + fromUserAccount + "' and userisdeleted=0");
              db.close();
            } catch (SQLException e) {
              e.printStackTrace();
            } catch (Exception e) {
              e.printStackTrace();
            } 
            System.out.println("用户【" + fromUserName + "】取消关注了企业号");
          } 
        } catch (AesException e) {
          e.printStackTrace();
        } 
      } 
    } catch (JDOMException e) {
      e.printStackTrace();
    } 
  }
  
  public static String getBodyString(BufferedReader br) {
    String str = "";
    try {
      String inputLine;
      while ((inputLine = br.readLine()) != null)
        str = String.valueOf(str) + inputLine; 
    } catch (IOException e) {
      System.out.println("IOException: " + e);
    } 
    return str;
  }
}
