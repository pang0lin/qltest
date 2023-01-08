package com.js.oa.weixin.servlet;

import com.js.oa.weixin.logon.WeixinLogonAction;
import com.js.oa.weixin.manage.WeixinManageAction;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.qq.weixin.mp.bean.URLCodeConverter;
import com.qq.weixin.mp.pojo.PositionInfo;
import com.qq.weixin.mp.pojo.PositionInfoRoom;
import com.qq.weixin.mp.util.WeiXinGlobalNames;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URLDecoder;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class QYCoreServlet_DaKa extends QYCoreServlet {
  private static final long serialVersionUID = 1L;
  
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
}
