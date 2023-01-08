package com.js.oa.weixin.servlet;

import com.js.oa.weixin.logon.WeixinLogonAction;
import com.js.oa.weixin.manage.WeixinManageAction;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.qq.weixin.mp.bean.URLCodeConverter;
import com.qq.weixin.mp.pojo.PositionInfo;
import com.qq.weixin.mp.pojo.PositionInfoRoom;
import com.qq.weixin.mp.util.WeiXinGlobalNames;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class QYCoreServlet extends HttpServlet {
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
        System.out.println("verifyurl echostr: " + sEchoStr);
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
          doc = builder.build(new StringReader(content));
          root = doc.getRootElement();
          Element element = null;
          String msgType = root.getChild("MsgType").getValue();
          if ("event".equalsIgnoreCase(msgType)) {
            element = root.getChild("Event");
            if ("LOCATION".equalsIgnoreCase(element.getValue())) {
              PositionInfo positionInfo = new PositionInfo();
              String userid = "";
              element = root.getChild("FromUserName");
              if (element != null)
                userid = element.getValue(); 
              positionInfo.setCreateTime(System.currentTimeMillis());
              element = root.getChild("Latitude");
              if (element != null) {
                String latitude = element.getValue();
                positionInfo.setLocation_X(latitude);
              } 
              element = root.getChild("Longitude");
              if (element != null) {
                String longitude = element.getValue();
                positionInfo.setLocation_Y(longitude);
              } 
              PositionInfoRoom.setUserPosition(userid, positionInfo);
            } else if (!"view".equalsIgnoreCase(element.getValue())) {
              element = root.getChild("EventKey");
              String eventKey = element.getValue();
              String url = URLCodeConverter.getURLByCode(eventKey);
              if (url.startsWith("?/"))
                url = url.substring(2); 
              request.setAttribute(WeiXinGlobalNames.WEIXIN_MESSAGE_BODY, content);
              element = root.getChild("FromUserName");
              String userId = element.getValue();
              request.setAttribute("weixin_UserId", userId);
              if (letUserLogin(request, response)) {
                request.getRequestDispatcher(url).forward((ServletRequest)request, (ServletResponse)response);
              } else {
                System.out.println("登录失败！");
              } 
            } 
          } else if ("image".equalsIgnoreCase(msgType)) {
            String eventKey = "A3";
            String url = URLCodeConverter.getURLByCode(eventKey);
            if (url.startsWith("?/"))
              url = url.substring(2); 
            String picurl = root.getChild("PicUrl").getValue();
            element = root.getChild("FromUserName");
            String userId = element.getValue();
            String picName = String.valueOf(root.getChild("CreateTime").getValue()) + "_" + userId;
            String showPath = "/jsoa/upload/weixinImage/" + picName + ".jpg";
            request.setAttribute("picUrl", showPath);
            request.setAttribute("weixin_UserId", userId);
            request.setAttribute(WeiXinGlobalNames.WEIXIN_MESSAGE_BODY, content);
            getPicUrl(picurl, picName, response, request);
            if (letUserLogin(request, response)) {
              request.getRequestDispatcher(url).forward((ServletRequest)request, (ServletResponse)response);
            } else {
              System.out.println("登录失败！");
            } 
          } 
        } catch (AesException e) {
          e.printStackTrace();
        } 
      } 
    } catch (JDOMException e) {
      e.printStackTrace();
    } 
  }
  
  private static boolean letUserLogin(HttpServletRequest request, HttpServletResponse response) {
    boolean isLogon = false;
    String weixin_UserId = (String)request.getAttribute("weixin_UserId");
    HttpSession session = request.getSession(false);
    if (session != null && weixin_UserId.equals(session.getAttribute("weixin_UserId"))) {
      isLogon = true;
      session.setAttribute("weixin_UserId", weixin_UserId);
    } else {
      isLogon = WeixinLogonAction.logon(request, response);
    } 
    return isLogon;
  }
  
  public static String getBodyString(BufferedReader br) {
    String str = "";
    try {
      String inputLine;
      while ((inputLine = br.readLine()) != null)
        str = String.valueOf(str) + inputLine; 
      br.close();
    } catch (IOException e) {
      System.out.println("IOException: " + e);
    } 
    return str;
  }
  
  public static String getPicUrl(String picurl, String picname, HttpServletResponse response, HttpServletRequest request) {
    String showPath = "";
    try {
      BufferedInputStream bis = null;
      HttpURLConnection httpUrl = null;
      URL url = null;
      if (!"".equals(picurl))
        try {
          url = new URL(picurl);
          httpUrl = (HttpURLConnection)url.openConnection();
          httpUrl.connect();
          showPath = "/jsoa/upload/weixinImage/" + picname + ".jpg";
          InputStream inStream = httpUrl.getInputStream();
          String path = String.valueOf(request.getSession().getServletContext().getRealPath("/upload/weixinImage/")) + System.getProperty("file.separator") + picname + ".jpg";
          try {
            OutputStream fout = new FileOutputStream(new File(path));
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = inStream.read(b)) != -1)
              fout.write(b, 0, len); 
            fout.close();
            inStream.close();
          } catch (IOException e) {
            e.printStackTrace();
          } 
        } catch (IOException iOException) {
          try {
            bis.close();
            httpUrl.disconnect();
          } catch (IOException iOException1) {
          
          } catch (NullPointerException nullPointerException) {}
        } catch (ClassCastException classCastException) {
          try {
            bis.close();
            httpUrl.disconnect();
          } catch (IOException iOException) {
          
          } catch (NullPointerException nullPointerException) {}
        } finally {
          try {
            bis.close();
            httpUrl.disconnect();
          } catch (IOException iOException) {
          
          } catch (NullPointerException nullPointerException) {}
        }  
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return showPath;
  }
}
