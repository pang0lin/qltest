package com.js.oa.weixin.checkin;

import com.ibm.icu.text.SimpleDateFormat;
import com.js.oa.userdb.util.DbOpt;
import com.js.util.config.SystemCommon;
import com.qq.weixin.mp.pojo.AppRoom;
import com.qq.weixin.mp.pojo.PositionInfo;
import com.qq.weixin.mp.pojo.PositionInfoRoom;
import com.qq.weixin.mp.util.GetUrlUtil;
import com.qq.weixin.mp.util.MessageUtil;
import com.qq.weixin.mp.util.PositionUtil;
import com.qq.weixin.mp.util.WeiXinGlobalNames;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class OutsideCkeckInThread extends Thread {
  private String toUserAccounts;
  
  private String toUserName;
  
  private String picurl;
  
  public OutsideCkeckInThread(String toUserAccounts, String toUserName, String picurl) {
    this.toUserAccounts = toUserAccounts;
    this.toUserName = toUserName;
    this.picurl = picurl;
  }
  
  public void run() {
    int getPositionCount = 0;
    PositionInfo position = null;
    String timeout = "0";
    getPositionCount++;
    while (getPositionCount <= 10) {
      position = PositionInfoRoom.getUserPosition(this.toUserAccounts);
      if (position == null) {
        try {
          Thread.sleep(1000L);
        } catch (InterruptedException e) {
          e.printStackTrace();
        } 
        continue;
      } 
      if (System.currentTimeMillis() - position.getCreateTime() > 180000L) {
        position = null;
        timeout = "1";
        try {
          Thread.sleep(1000L);
        } catch (InterruptedException e) {
          e.printStackTrace();
        } 
      } 
    } 
    if (position == null) {
      String content = String.valueOf(this.toUserName) + "：打卡失败，请您开启微信获取地理位置功能！";
      if ("1".equals(timeout))
        content = String.valueOf(this.toUserName) + "：打卡超时,请选择WIFI环境或者选择网速较快的环境打卡！"; 
      if (this.picurl != null && !"".equals(this.picurl) && !"null".equals(this.picurl))
        MessageUtil.sendNewMessageToUser(this.toUserAccounts, WeiXinGlobalNames.APP_NAME_ZXDK, "打卡失败", content, "", ""); 
    } else if (this.picurl != null && !"".equals(this.picurl) && !"null".equals(this.picurl)) {
      String content, positionString = PositionUtil.getPosition(
          position.getLocation_X(), position.getLocation_Y());
      String sql = "";
      String dateTime = "";
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      dateTime = df.format(new Date());
      String dataBaseType = 
        SystemCommon.getDatabaseType();
      if (dataBaseType.indexOf("oracle") >= 0) {
        sql = "insert into kq_OutsideCheckininfo(id,userid,checkintime,weidu,jingdu,position,imageurl,customName,customCompany,customAddress,reason,customType,customBz,ismark)values(hibernate_sequence.nextval,'" + 
          
          this.toUserAccounts + 
          "',JSDB.FN_STRTODATE('" + dateTime + "',''),'" + 
          position.getLocation_X() + 
          "','" + 
          position.getLocation_Y() + 
          "','" + 
          positionString + 
          "','" + 
          this.picurl + 
          "','" + 
          
          "','" + 
          
          "','" + 
          
          "','" + 
          
          "','" + 
          
          "','" + 
          
          "','" + 
          "0" + 
          "')";
      } else {
        sql = "INSERT INTO kq_OutsideCheckininfo(userid,checkintime,weidu,jingdu,POSITION,imageurl,customName,customCompany,customAddress,reason,customType,customBz,ismark)values('" + 
          this.toUserAccounts + 
          "','" + dateTime + "','" + 
          position.getLocation_X() + 
          "','" + 
          position.getLocation_Y() + 
          "','" + 
          positionString + 
          "','" + 
          this.picurl + 
          "','" + 
          
          "','" + 
          
          "','" + 
          
          "','" + 
          
          "','" + 
          
          "','" + 
          
          "','" + 
          "0" + 
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
      String outsideId = getOutsideId(this.toUserAccounts, position.getLocation_X(), position.getLocation_Y(), dateTime, positionString);
      String outsideIdUrl = GetUrlUtil.getOutsideLinkURL(outsideId);
      if ("qyh".equals(AppRoom.getWeixinType())) {
        content = "打卡时间：[" + dateTime + "]\n打卡位置：" + 
          positionString;
      } else {
        content = "打卡时间：[" + dateTime + "]打卡位置：" + 
          positionString;
      } 
      MessageUtil.sendNewMessageToUser(this.toUserAccounts, WeiXinGlobalNames.APP_NAME_ZXDK, "打卡成功请完善考勤信息", content, outsideIdUrl, "");
    } 
  }
  
  private static String getOutsideId(String userId, String weidu, String jingdu, String checkintime, String POSITION) {
    String outsideId = "";
    String dataBaseType = 
      SystemCommon.getDatabaseType();
    String sql = "select id from kq_OutsideCheckininfo where ";
    sql = String.valueOf(sql) + " userid='" + userId + "'";
    sql = String.valueOf(sql) + " and weidu='" + weidu + "' ";
    sql = String.valueOf(sql) + " and jingdu='" + jingdu + "' ";
    if (dataBaseType.indexOf("oracle") >= 0) {
      sql = String.valueOf(sql) + " and checkintime=JSDB.FN_STRTODATE('" + checkintime + "','') ";
    } else {
      sql = String.valueOf(sql) + " and checkintime='" + checkintime + "'";
    } 
    sql = String.valueOf(sql) + " and POSITION='" + POSITION + "' ";
    DbOpt db = new DbOpt();
    ResultSet rs = null;
    System.out.println("sql:" + sql);
    try {
      rs = db.executeQuery(sql);
      if (rs.next())
        outsideId = rs.getString(1); 
      db.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return outsideId;
  }
}
