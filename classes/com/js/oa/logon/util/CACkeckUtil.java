package com.js.oa.logon.util;

import com.js.util.config.SystemCommon;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;
import sun.misc.BASE64Encoder;

public class CACkeckUtil {
  public static String checkCALogin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    String authURL = SystemCommon.gethuanbaobuAuthURL();
    String appId = SystemCommon.getHuanbaobuAppId();
    boolean isSuccess = true;
    String errCode = null, errDesc = null;
    if (authURL == null || authURL.trim().equals("") || appId == null || appId.trim().equals("")) {
      isSuccess = false;
      errDesc = "应用标识或网关认证地址不可为空";
      System.out.println("应用标识或网关认证地址不可为空\n");
    } 
    String original_data = null, signed_data = null, original_jsp = null, username = null, password = null;
    if (isSuccess) {
      System.out.println("应用标识及网关的认证地址读取成功！\n应用标识：" + appId + "\n认证地址：" + authURL + "\n");
      original_data = httpServletRequest.getParameter("original_jsp");
      original_jsp = httpServletRequest.getParameter("original_jsp");
      if (CAXmlUtil.isNotNull(original_data)) {
        if (!original_data.equalsIgnoreCase(original_jsp)) {
          isSuccess = false;
          errDesc = "客户端提供的认证原文与服务端的不一致";
          System.out.println("客户端提供的认证原文与服务端的不一致！\n");
        } else {
          signed_data = httpServletRequest.getParameter("signed_data");
          original_data = (new BASE64Encoder()).encode(original_jsp.getBytes());
          System.out.println("读取认证原文和认证请求包成功！\n认证原文：" + original_jsp + 
              "\n认证请求包：" + signed_data + "\n");
        } 
      } else {
        isSuccess = false;
        errDesc = "证书认证数据不完整";
        System.out.println("证书认证数据不完整！\n");
        httpServletRequest.setAttribute("checkCA", "false");
        httpServletRequest.setAttribute("message", errDesc);
      } 
    } 
    try {
      byte[] messagexml = (byte[])null;
      if (isSuccess) {
        Document reqDocument = DocumentHelper.createDocument();
        Element root = reqDocument.addElement("message");
        Element requestHeadElement = root.addElement("head");
        Element requestBodyElement = root.addElement("body");
        requestHeadElement.addElement("version").setText("1.0");
        requestHeadElement.addElement("serviceType").setText("AuthenService");
        Element clientInfoElement = requestBodyElement.addElement("clientInfo");
        Element clientIPElement = clientInfoElement.addElement("clientIP");
        clientIPElement.setText(httpServletRequest.getRemoteAddr());
        requestBodyElement.addElement("appId").setText(appId);
        Element authenElement = requestBodyElement.addElement("authen");
        Element authCredentialElement = authenElement.addElement("authCredential");
        authCredentialElement.addAttribute("authMode", "cert");
        authCredentialElement.addElement("detach").setText(signed_data);
        authCredentialElement.addElement("original").setText(original_data);
        requestBodyElement.addElement("accessControl").setText("false");
        Element attributesElement = requestBodyElement.addElement("attributes");
        attributesElement.addAttribute("attributeType", "portion");
        CAXmlUtil.addAttribute(attributesElement, "X509Certificate.SubjectDN", "http://www.jit.com.cn/cinas/ias/ns/saml/saml11/X.509");
        CAXmlUtil.addAttribute(attributesElement, "UMS.UserID", "http://www.jit.com.cn/ums/ns/user");
        CAXmlUtil.addAttribute(attributesElement, "机构字典", "http://www.jit.com.cn/ums/ns/user");
        StringBuffer reqMessageData = new StringBuffer();
        try {
          ByteArrayOutputStream outStream = new ByteArrayOutputStream();
          XMLWriter writer = new XMLWriter(outStream);
          writer.write(reqDocument);
          messagexml = outStream.toByteArray();
          reqMessageData.append("请求内容开始！\n");
          reqMessageData.append(String.valueOf(outStream.toString()) + "\n");
          reqMessageData.append("请求内容结束！\n");
          System.out.println(String.valueOf(reqMessageData.toString()) + "\n");
        } catch (Exception e) {
          isSuccess = false;
          errDesc = "组装请求时出现异常";
          System.out.println("组装请求时出现异常");
        } 
      } 
      int statusCode = 500;
      HttpClient httpClient = null;
      PostMethod postMethod = null;
      if (isSuccess) {
        httpClient = new HttpClient();
        postMethod = new PostMethod(authURL);
        postMethod.setRequestHeader("Content-Type", 
            "text/xml;charset=UTF-8");
        postMethod.setRequestBody(new ByteArrayInputStream(messagexml));
        try {
          statusCode = httpClient.executeMethod((HttpMethod)postMethod);
        } catch (Exception e) {
          isSuccess = false;
          errCode = String.valueOf(statusCode);
          errDesc = "与网关连接出现异常";
          System.out.println("与网关连接出现异常\n");
          httpServletRequest.setAttribute("checkCA", "false");
          httpServletRequest.setAttribute("message", "与网关连接出现异常");
        } 
      } 
      StringBuffer respMessageData = new StringBuffer();
      String respMessageXml = null;
      if (isSuccess)
        if (statusCode == 200 || 
          statusCode == 500)
          try {
            byte[] inputstr = postMethod.getResponseBody();
            ByteArrayInputStream ByteinputStream = new ByteArrayInputStream(
                inputstr);
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            int ch = 0;
            try {
              while ((ch = ByteinputStream.read()) != -1) {
                int upperCh = (char)ch;
                outStream.write(upperCh);
              } 
            } catch (Exception e) {
              isSuccess = false;
              errDesc = e.getMessage();
            } 
            if (isSuccess) {
              if (statusCode == 200) {
                respMessageData.append(
                    String.valueOf(new String(outStream.toByteArray(), "UTF-8")) + 
                    "\n");
                respMessageXml = new String(outStream
                    .toByteArray(), "UTF-8");
              } else {
                respMessageData.append(
                    String.valueOf(new String(outStream.toByteArray())) + 
                    "\n");
                isSuccess = false;
                errCode = String.valueOf(statusCode);
                errDesc = new String(outStream.toByteArray());
              } 
              String message = CAAnalysisXml.getXML(respMessageData.toString());
              if ("true".equals(message)) {
                httpServletRequest.setAttribute("checkCA", "true");
              } else {
                httpServletRequest.setAttribute("checkCA", "false");
                httpServletRequest.setAttribute("message", message);
                errDesc = message;
              } 
              System.out.println(String.valueOf(respMessageData.toString()) + "\n");
            } 
          } catch (IOException e) {
            isSuccess = false;
            errCode = String.valueOf(statusCode);
            errDesc = e.getMessage();
            System.out.println("读取认证响应报文出现异常！");
          }   
    } catch (Exception e) {
      isSuccess = false;
      errDesc = e.getMessage();
    } 
    return errDesc;
  }
}
