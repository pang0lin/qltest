package com.js.oa.info.util;

import com.js.oa.userdb.util.DbOpt;
import com.js.util.config.SystemCommon;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChannelCache {
  private static Map<String, String> channelMap;
  
  public static String getChannelNameString(String channelId) {
    if (channelMap == null)
      initChannel(null); 
    return channelMap.get(channelId);
  }
  
  public static void initChannel(String channelId) {
    boolean updateChannel = false;
    if (channelId != null && !"".equals(channelId))
      updateChannel = true; 
    if (channelMap == null) {
      channelMap = new ConcurrentHashMap<String, String>(512);
    } else if (!updateChannel) {
      channelMap.clear();
    } 
    DbOpt dbopt = new DbOpt();
    try {
      String sql = "select channel_id,channelName,channelidstring,channeltype,userdefine from oa_informationchannel";
      if (updateChannel)
        sql = String.valueOf(sql) + " where channelidstring like '%$" + channelId + "$%'"; 
      String[][] channelList = dbopt.executeQueryToStrArr2(sql, 5);
      for (int i = 0; i < channelList.length; i++) {
        String[] obj = channelList[i];
        String channelNameString = "";
        if (obj[3].equals("0")) {
          channelNameString = "知识管理.";
        } else if (obj[4] != null && "1".equals(obj[4])) {
          String[] userDefineName = dbopt.executeQueryToStrArr1("select userchannelname from oa_userchannel where userchannel_id=" + obj[3]);
          if (userDefineName != null && userDefineName.length > 0) {
            channelNameString = String.valueOf(userDefineName[0]) + "." + channelNameString;
          } else {
            continue;
          } 
        } else {
          String[] userDefineName = dbopt.executeQueryToStrArr1("select orgnamestring from org_organization where org_id=" + obj[3]);
          if (userDefineName != null && userDefineName.length > 0) {
            channelNameString = String.valueOf(userDefineName[0]) + "." + channelNameString;
          } else {
            continue;
          } 
        } 
        String tmpSql = "";
        String databaseType = 
          SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "select channelName from oa_informationchannel where '" + obj[2] + "' like concat('%$',channel_id,'$%') and domain_id=0 order by channelidstring";
        } else if (databaseType.indexOf("db2") >= 0) {
          tmpSql = "select channelName from oa_informationchannel where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(channel_Id)), '$'),'" + obj[2] + "')>0 and domain_id=0 order by channelidstring";
        } else {
          tmpSql = "select channelName from oa_informationchannel where '" + obj[2] + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(channel_Id)), '$%') and domain_id=0 order by channelidstring";
        } 
        String[] names = dbopt.executeQueryToStrArr1(tmpSql);
        for (int j = 0; j < names.length; j++)
          channelNameString = String.valueOf(channelNameString) + names[j] + "."; 
        channelNameString = channelNameString.substring(0, channelNameString.length() - 1);
        if (updateChannel)
          channelMap.remove(obj[0]); 
        channelMap.put(obj[0], channelNameString);
        continue;
      } 
      dbopt.close();
    } catch (Exception err) {
      if (dbopt != null)
        try {
          dbopt.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
      err.printStackTrace();
    } 
  }
}
