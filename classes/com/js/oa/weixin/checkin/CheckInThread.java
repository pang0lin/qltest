package com.js.oa.weixin.checkin;

import com.ibm.icu.text.SimpleDateFormat;
import com.js.oa.userdb.util.DbOpt;
import com.js.util.config.SystemCommon;
import com.qq.weixin.mp.bean.WeiXinMenuCreator;
import com.qq.weixin.mp.oauth.CoreOAuth;
import com.qq.weixin.mp.pojo.AppRoom;
import com.qq.weixin.mp.pojo.PositionInfo;
import com.qq.weixin.mp.pojo.PositionInfoRoom;
import com.qq.weixin.mp.util.MessageUtil;
import com.qq.weixin.mp.util.PositionUtil;
import com.qq.weixin.mp.util.WeiXinGlobalNames;
import java.sql.SQLException;
import java.util.Date;

public class CheckInThread extends Thread {
  private String toUserAccounts;
  
  private String toUserName;
  
  private String mark = "A2";
  
  public CheckInThread(String toUserAccounts, String toUserName) {
    this.toUserAccounts = toUserAccounts;
    this.toUserName = toUserName;
  }
  
  public void run() {
    String markUrl = String.valueOf(WeiXinMenuCreator.HOME) + "?/mark=" + this.mark;
    String url = CoreOAuth.gainGuideUserUri(markUrl);
    int getPositionCount = 0;
    PositionInfo position = null;
    getPositionCount++;
    while (getPositionCount <= 5) {
      position = PositionInfoRoom.getUserPosition(this.toUserAccounts);
      if (position == null) {
        try {
          Thread.sleep(1000L);
        } catch (InterruptedException e) {
          e.printStackTrace();
        } 
        continue;
      } 
      if (System.currentTimeMillis() - position.getCreateTime() > 20000L) {
        position = null;
        try {
          Thread.sleep(1000L);
        } catch (InterruptedException e) {
          e.printStackTrace();
        } 
        continue;
      } 
      break;
    } 
    if (position == null) {
      String content = String.valueOf(this.toUserName) + "：打卡失败，请您开启微信获取地理位置功！如果您已经开启此功能，请先退出【在线打卡】应用，然后重新进入【在线打卡】应用并在5秒钟内点击上下班打卡！";
      MessageUtil.sendNewMessageToUser(this.toUserAccounts, WeiXinGlobalNames.APP_NAME_ZXDK, "打卡失败", content, url, "");
    } else {
      String content, positionString = PositionUtil.getPosition(
          position.getLocation_X(), position.getLocation_Y());
      String sql = "";
      String dataBaseType = 
        SystemCommon.getDatabaseType();
      if (dataBaseType.indexOf("oracle") >= 0) {
        sql = "insert into kq_checkininfo(id,userid,checkintime,weidu,jingdu,position)values(hibernate_sequence.nextval,'" + 
          
          this.toUserAccounts + 
          "',sysdate,'" + 
          position.getLocation_X() + 
          "','" + 
          position.getLocation_Y() + 
          "','" + 
          positionString + 
          "')";
      } else {
        sql = "INSERT INTO kq_checkininfo(userid,checkintime,weidu,jingdu,POSITION)values('" + 
          this.toUserAccounts + 
          "',NOW(),'" + 
          position.getLocation_X() + 
          "','" + 
          position.getLocation_Y() + 
          "','" + 
          positionString + 
          "')";
      } 
      DbOpt db = new DbOpt();
      try {
        db.executeUpdate(sql);
        db.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      } 
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      if ("qyh".equals(AppRoom.getWeixinType())) {
        content = "打卡时间：[" + df.format(new Date()) + "]\n打卡位置：" + 
          positionString;
      } else {
        content = "打卡时间：[" + df.format(new Date()) + "]打卡位置：" + 
          positionString;
      } 
      MessageUtil.sendNewMessageToUser(this.toUserAccounts, WeiXinGlobalNames.APP_NAME_ZXDK, "打卡成功", content, url, "");
    } 
  }
}
