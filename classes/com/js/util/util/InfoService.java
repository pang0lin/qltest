package com.js.util.util;

import com.js.oa.info.channelmanager.service.ChannelBD;
import com.js.system.util.StaticParam;
import com.js.util.config.SystemCommon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONSerializer;

public class InfoService {
  String limitSql = "";
  
  String baseType = SystemCommon.getDatabaseType();
  
  public InfoService() {
    if ("oracle".equals(this.baseType)) {
      this.limitSql = "SELECT * FROM ( SELECT A.*, ROWNUM RN FROM (@sql@) A ) WHERE RN BETWEEN @begin@ AND @end@";
    } else if ("mysql".equals(this.baseType)) {
      this.limitSql = "@sql@ limit @begin@,@end@";
    } 
  }
  
  public String getInfo(String userAcc, int num) {
    System.out.println("获得公告信息");
    String userId = StaticParam.getEmpIdByAccount(userAcc);
    String orgId = StaticParam.getOrgIdByEmpId(userId);
    String[] groupId = StaticParam.getGroupIdByEmpId(userId).split(",");
    String[] orgIds = StaticParam.getOrgIdsByOrgId(orgId).split(",");
    String[][] info = { { "100", "新闻" }, { "101", "公告" } };
    ChannelBD cbd = new ChannelBD();
    if (SystemCommon.getMultiDepart() == 1) {
      String[] orgString = StaticParam.getOrgIdStringByOrgId(orgId).split("\\$");
      String corpId = orgString[3];
      info = cbd.getChannelSimpleInfoByCorpId(corpId, 0);
    } 
    String channelSql = "select channel_id,channelreader,channelreaderorg,channelreadergroup from oa_informationchannel where channelidstring like '%$" + info[1][0] + "$%'";
    List<String[]> channList = (new DataSourceUtil()).getListQuery(channelSql, "");
    String channelIds = "-1";
    for (int i = 0; i < channList.size(); i++) {
      String[] obj = channList.get(i);
      String reader = String.valueOf(obj[1]) + obj[2] + obj[3];
      if (reader.equals("") || reader.contains("*-1*")) {
        channelIds = String.valueOf(channelIds) + "," + obj[0];
      } else if (reader.contains("$" + userId + "$")) {
        channelIds = String.valueOf(channelIds) + "," + obj[0];
      } else {
        boolean go = true;
        int t;
        for (t = 0; t < orgIds.length; t++) {
          if (reader.contains("*" + orgIds[t] + "*")) {
            channelIds = String.valueOf(channelIds) + "," + obj[0];
            go = false;
            break;
          } 
        } 
        if (go)
          for (t = 0; t < groupId.length; t++) {
            if (reader.contains("*" + groupId[t] + "*")) {
              channelIds = String.valueOf(channelIds) + "," + obj[0];
              break;
            } 
          }  
      } 
    } 
    String orgSql = "";
    for (int j = 1; j < orgIds.length; j++)
      orgSql = String.valueOf(orgSql) + " i.informationreaderorg like '%*" + orgIds[j] + "*%' or"; 
    String sql = "select i.informationtitle,i.informationissuer,i.informationissuetime,i.information_id from oa_information i join oa_informationChannel c on i.channel_id=c.channel_id where i.channel_id in (" + 
      channelIds + ") and ((i.informationreader like '%$" + userId + "$%' or (" + orgSql + " i.informationreaderorg like '%*-1*%') " + 
      "or(CONCAT(i.informationreaderorg,i.informationreader)='' or CONCAT(i.informationreaderorg,i.informationreader) is null)))" + 
      "order by i.informationissuetime desc";
    this.limitSql = this.limitSql.replace("@sql@", sql).replace("@begin@", "0").replace("@end@", (new StringBuilder(String.valueOf(num))).toString());
    List<String[]> sqlList = (new DataSourceUtil()).getListQuery(this.limitSql, "");
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    for (int k = 0; k < sqlList.size(); k++) {
      String[] obj = sqlList.get(k);
      Map<String, Object> message = new HashMap<String, Object>();
      message.put("SUBJECT", obj[0]);
      message.put("DATE", obj[2]);
      message.put("USERNAME", obj[1]);
      message.put("URL", "/jsoa/info/view_detail.jsp?informationId=" + obj[3]);
      list.add(message);
    } 
    return JSONSerializer.toJSON(list).toString();
  }
  
  public String getChat(String userAcc, int num) {
    System.out.println("获得即时消息");
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    String userId = StaticParam.getEmpIdByAccount(userAcc);
    if (userId != null && !"".equals(userId)) {
      String sql = "SELECT m.message_title,m.message_time,'' USERNAME,m.message_url FROM sys_messages m JOIN org_employee e ON m.message_toUserId=e.EMP_ID WHERE e.useraccounts='" + 
        userAcc + "' AND m.message_status=1 AND m.message_date_begin<=NOW() AND m.message_date_end>=NOW() " + 
        "ORDER BY message_id DESC ";
      this.limitSql = this.limitSql.replace("@sql@", sql).replace("@begin@", "0").replace("@end@", (new StringBuilder(String.valueOf(num))).toString());
      List<String[]> sqlList = (new DataSourceUtil()).getListQuery(this.limitSql, "");
      for (int i = 0; i < sqlList.size(); i++) {
        String[] obj = sqlList.get(i);
        Map<String, Object> message = new HashMap<String, Object>();
        message.put("CONTENT", obj[0]);
        message.put("DATE", obj[1]);
        message.put("USERNAME", obj[2]);
        message.put("URL", obj[3]);
        list.add(message);
      } 
    } 
    return JSONSerializer.toJSON(list).toString();
  }
}
