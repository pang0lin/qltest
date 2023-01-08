package com.js.oa.logon.service;

import com.js.oa.logon.service.impl.RecalPasswordImpl;
import com.js.util.mail.Mail;
import com.js.util.mail.MailSender;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;

public class RecalPassword {
  public String checkAndSendMail(String userName, String userMail, String ip, String serverIp) {
    Map<String, String> resultMap = new HashMap<String, String>();
    RecalPasswordImpl recalPassword = new RecalPasswordImpl();
    Map<String, String> map = recalPassword.getUserInfo(userName.trim());
    if (map.size() < 1) {
      resultMap.put("status", "userNameMsg");
      resultMap.put("msg", "系统中没有该用户信息");
      return JSONObject.fromObject(resultMap).toString();
    } 
    if ("0".equals(map.get("userisactive"))) {
      resultMap.put("status", "userNameMsg");
      resultMap.put("msg", "用户已被禁用不能重置密码");
      return JSONObject.fromObject(resultMap).toString();
    } 
    String email = map.get("empemail");
    if (email == null || "".equals(email) || "null".equalsIgnoreCase(email)) {
      resultMap.put("status", "userEmailMsg");
      resultMap.put("msg", "该用户没有维护邮箱，请联系管理员重置密码");
      return JSONObject.fromObject(resultMap).toString();
    } 
    if (userMail.trim().equals(email.trim())) {
      boolean res = getCodeAndSendMail(recalPassword, map, ip, serverIp);
      if (res) {
        resultMap.put("status", "succ");
        return JSONObject.fromObject(resultMap).toString();
      } 
      resultMap.put("status", "userCodeMsg");
      resultMap.put("msg", "邮件发送失败，请稍后重试");
      return JSONObject.fromObject(resultMap).toString();
    } 
    resultMap.put("status", "userEmailMsg");
    resultMap.put("msg", "输入邮箱与系统中维护邮箱不匹配");
    return JSONObject.fromObject(resultMap).toString();
  }
  
  public String checkResetInfoById(String sid, String code) {
    RecalPasswordImpl recalPassword = new RecalPasswordImpl();
    Map<String, String> map = recalPassword.getResetInfoById(sid);
    if (map.size() < 1)
      return "验证码或用户信息无效"; 
    if ("0".equals(map.get("repwd_status")))
      return "重置密码链接已使用或过期"; 
    if (!code.equals(map.get("repwd_code")))
      return "验证码错误"; 
    if (!compareDate(map.get("repwd_applyDate")))
      return "验证码过期，请重新获取"; 
    return "succ" + (String)map.get("repwd_userAccount");
  }
  
  public String checkResetInfo(String userName, String code) {
    Map<String, String> resultMap = new HashMap<String, String>();
    RecalPasswordImpl recalPassword = new RecalPasswordImpl();
    Map<String, String> map = recalPassword.getResetInfo(userName, code);
    if (map.size() < 1) {
      resultMap.put("status", "userCodeMsg");
      resultMap.put("msg", "验证码或用户信息无效");
      return JSONObject.fromObject(resultMap).toString();
    } 
    if ("0".equals(map.get("repwd_status"))) {
      resultMap.put("status", "userCodeMsg");
      resultMap.put("msg", "验证码已被使用或已过期");
      return JSONObject.fromObject(resultMap).toString();
    } 
    if (!compareDate(map.get("repwd_applyDate"))) {
      resultMap.put("status", "userCodeMsg");
      resultMap.put("msg", "验证码过期，请重新获取");
      return JSONObject.fromObject(resultMap).toString();
    } 
    resultMap.put("status", "succ");
    resultMap.put("msg", map.get("repwd_id"));
    return JSONObject.fromObject(resultMap).toString();
  }
  
  public String resetPwd(String sid, String password, String ip) {
    RecalPasswordImpl recalPassword = new RecalPasswordImpl();
    boolean res = recalPassword.resetPwd(sid, password, ip);
    Map<String, String> resultMap = new HashMap<String, String>();
    if (res) {
      resultMap.put("status", "succ");
      return JSONObject.fromObject(resultMap).toString();
    } 
    resultMap.put("status", "userPwd1");
    resultMap.put("msg", "修改失败，请稍后重试");
    return JSONObject.fromObject(resultMap).toString();
  }
  
  private boolean compareDate(String date) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    long time1 = Calendar.getInstance().getTimeInMillis();
    Calendar recallCal = Calendar.getInstance();
    try {
      recallCal.setTime(sdf.parse(date));
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    long time2 = recallCal.getTimeInMillis();
    long between_min = (time1 - time2) / 1000L;
    if (between_min < 7200L)
      return true; 
    return false;
  }
  
  private boolean getCodeAndSendMail(RecalPasswordImpl recalPassword, Map<String, String> map, String ip, String serverIp) {
    String code = recalPassword.randomWord(16);
    map.put("code", code);
    map.put("ip", ip);
    String uid = recalPassword.setRecallPwdInfo(map);
    boolean res = false;
    if (uid.length() > 0) {
      res = true;
      map.put("uid", uid);
      serverIp = String.valueOf(serverIp.substring(0, serverIp.indexOf("/jsoa/") + 6)) + "recallPassword.jsp?sid=" + uid + "&code=" + code;
      map.put("serverIp", serverIp);
      res = SendMail(map);
    } 
    return res;
  }
  
  private boolean SendMail(Map<String, String> map) {
    Mail mailVo = new Mail();
    mailVo.setSubjectTitle("密码重置");
    mailVo.setSendTo(map.get("empemail"));
    String[] fileAffix = (String[])null;
    mailVo.setFileAffix(fileAffix);
    mailVo.setBoby(emailBody(map));
    mailVo.setHtml(true);
    try {
      MailSender.send(mailVo);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } 
    return true;
  }
  
  private String emailBody(Map<String, String> map) {
    StringBuffer sbBuffer = new StringBuffer();
    sbBuffer.append(String.valueOf(map.get("name")) + "：<br  />");
    sbBuffer.append("<p>您好，这封信是由OA系统自动发送的，请勿回复。</p>");
    sbBuffer.append("<p>如果您没有提交密码重置的请求，请立即忽略并删除这封邮件。只有在您确认需要重置密码的情况下，才需要继续阅读下面的内容。</p>");
    sbBuffer.append("<p>----------------------------------------------------------------------<br  />");
    sbBuffer.append("<strong>密码重置说明</strong><br  />");
    sbBuffer.append("----------------------------------------------------------------------</p><p></p>");
    sbBuffer.append("您可通过以下两种方式重置您的密码：<br  />");
    sbBuffer.append("<p>(1) 您的验证码为<strong><font style=\"font-size: 16px;color:red;\">" + (String)map.get("code") + "</font></strong>(请将验证码输入验证码文本框，点击重置密码按钮)</p>");
    sbBuffer.append("<p>(2) 点击下面的链接重置您的密码：<br  />");
    sbBuffer.append("<a href=\"" + (String)map.get("serverIp") + "\" target=\"_blank\">" + (String)map.get("serverIp") + "</a><br  />");
    sbBuffer.append("(如果上面不是链接形式，请将该地址手工粘贴到浏览器地址栏再访问)</p><p></p>");
    sbBuffer.append("<p>在上面的链接所打开的页面中输入新的密码后提交，您即可使用新的密码登录系统了。您可以在个人设置-密码设置中随时修改您的密码。</p>");
    sbBuffer.append("<div style=\"border-top:1px solid #c8cfda; width:270px; padding:0; line-height:1.6;  font-size:14px; margin:30px 0 0;\" >九思软件运营团队</div>");
    sbBuffer.append("<div style=\"color:#999; font-size:12px; line-height:2;\">");
    sbBuffer.append("九思OA，中国管理软件成功率第一品牌");
    sbBuffer.append("<br />九思软件门户：<a href=\"http://www.jiusi.net/\">http://www.jiusi.net/</a></div>");
    return sbBuffer.toString();
  }
}
