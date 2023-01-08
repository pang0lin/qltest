package com.js.message;

import com.active.e_uc.user.po.TblSysmsg;
import com.active.e_uc.user.service.TblSysmsgBD;
import com.js.message.lava.GKLogin;
import com.js.message.lava.GKSendMessage;
import com.js.message.lava.GKSync;
import com.js.message.lava.GKUserManager;
import com.js.message.lava.GKUtilClass;
import com.js.util.util.ReadActiveXml;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import rtx.RTXSvrApi;

public class RealTimeUtil {
  private static String use;
  
  private static String type;
  
  private static String zoneID;
  
  private static String serverDN;
  
  private static String serverLocalDN;
  
  private static String port;
  
  public RealTimeUtil() {
    if (use == null)
      init(); 
  }
  
  private void init() {
    String path = System.getProperty("user.dir");
    String filePath = String.valueOf(path) + "/jsconfig/sysconfig.xml";
    try {
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(filePath);
      Element root = doc.getRootElement();
      Element node = root.getChild("RealTimeMessage");
      use = node.getAttribute("use").getValue();
      type = node.getAttribute("type").getValue();
      if ("1".equals(use)) {
        if ("gk".equals(type)) {
          node = root.getChild("GKServer");
          serverDN = node.getChildText("Server");
          serverLocalDN = node.getChildText("LocalServer");
          port = node.getChildText("port");
          zoneID = node.getChildText("zoneID");
          GKUtilClass util = new GKUtilClass();
          util.setPort(Integer.parseInt(port));
          util.setServer(serverLocalDN);
        } else if ("rtx".equals(type)) {
          node = root.getChild("RtxServer");
          serverDN = node.getAttribute("domain").getValue();
          serverLocalDN = node.getAttribute("localDomain").getValue();
        } 
      } else {
        zoneID = "5000";
        serverDN = "127.0.0.1";
        serverLocalDN = "127.0.0.1";
        port = "8900";
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public boolean sync() {
    if ("gk".equals(type)) {
      GKSync sync = new GKSync();
      return sync.Sync();
    } 
    "rtx".equals(type);
    return false;
  }
  
  public boolean syncPart() {
    if ("gk".equals(type)) {
      GKSync sync = new GKSync();
      return sync.SyncPart();
    } 
    if ("rtx".equals(type))
      return true; 
    return false;
  }
  
  public boolean modifyPassword(String account, String password, String md5Pwd) {
    if ("gk".equals(type)) {
      GKUserManager gkUser = new GKUserManager();
      gkUser.modPwd(account, password, md5Pwd);
      gkUser.close();
    } 
    return false;
  }
  
  public String sendIM(String senderAccount, String senderImID, String receiverAccount, String receiverImID, String message, String flag) {
    return null;
  }
  
  public String sendNotify(String receiverAccounts, String title, String text, String flag, String delayTime) {
    return sendNotify(receiverAccounts, "", title, text, "", flag, delayTime);
  }
  
  public String sendNotify(String receiverAccounts, String ug_code, String title, String text, String htmltext, String flag, String delayTime) {
    try {
      if ("gk".equals(type)) {
        GKSendMessage gkSM = new GKSendMessage();
        gkSM.sendNotify(receiverAccounts, ug_code, title, text, htmltext);
        gkSM.close();
      } else {
        String usd = ReadActiveXml.getReadActive().getUse();
        if ("iactive".equals(usd)) {
          TblSysmsg tblSysmsg = new TblSysmsg();
          TblSysmsgBD tblSysmsgBD = new TblSysmsgBD();
          String[] usernames = receiverAccounts.split(",");
          String users = "";
          for (int i = 0; i < usernames.length; i++) {
            String username = usernames[i];
            users = String.valueOf(users) + username + "','";
          } 
          users = "'" + users.substring(0, users.length() - 2);
          List<Integer> useridsList = tblSysmsgBD.findUserId(users);
          String userids = "";
          String userid = "";
          for (int j = 0; j < useridsList.size(); j++) {
            userid = ((Integer)useridsList.get(j)).toString();
            userids = String.valueOf(userids) + userid + ",";
          } 
          userids = "," + userids;
          tblSysmsg.setUsageType(1);
          tblSysmsg.setContentType(0);
          tblSysmsg.setDisplayMode(0);
          tblSysmsg.setContent(String.valueOf(text) + htmltext);
          tblSysmsg.setReceiverType(24);
          tblSysmsg.setReceiver(userids);
          SimpleDateFormat si = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          tblSysmsg.setStartTime(si.format(new Date()));
          Calendar calendar = Calendar.getInstance();
          calendar.setTime(new Date());
          calendar.add(1, 5);
          tblSysmsg.setEndTime(si.format(calendar.getTime()));
          tblSysmsg.setApp(3);
          tblSysmsg.setCreatorId(3);
          tblSysmsg.setCreatorType(3);
          tblSysmsg.setOrgId(1);
          tblSysmsg.setAid(1);
          tblSysmsg.setDeptId(0);
          tblSysmsg.setOrderNum(0);
          tblSysmsgBD.addTblSysmsg(tblSysmsg);
        } else if ("rtx".equals(usd)) {
          RTXSvrApi rtxSvr = new RTXSvrApi();
          rtxSvr.sendNotify(receiverAccounts, title, "", text, flag, delayTime);
        } 
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return null;
  }
  
  public String sendSMS(String sender, String recieve, String message, String flag) {
    return null;
  }
  
  public String getLocalServerDN() {
    return serverLocalDN;
  }
  
  public String getServerDN() {
    return serverDN;
  }
  
  public String getSessionKey(String account) {
    return null;
  }
  
  public String getLogURL(String account, String pwd) {
    return GKLogin.getLoginURL(account, pwd);
  }
  
  public boolean getUsed() {
    if ("1".equals(use))
      return true; 
    return false;
  }
  
  public String getType() {
    return type;
  }
  
  public String getZoneID() {
    return zoneID;
  }
  
  public String getPort() {
    return port;
  }
}
