package com.js.oa.webservice.kingosoft;

import com.js.oa.info.channelmanager.service.ChannelBD;
import com.js.oa.webservice.util.SecurityUtil;
import com.js.sso.util.DaiBanTask;
import com.js.sso.util.XMLUtil;
import com.js.system.util.StaticParam;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceUtil;
import java.util.List;

public class PortalService {
  String limitSql = "";
  
  String baseType = SystemCommon.getDatabaseType();
  
  public PortalService() {
    if ("oracle".equals(this.baseType)) {
      this.limitSql = "SELECT * FROM ( SELECT A.*, ROWNUM RN FROM (@sql@) A ) WHERE RN BETWEEN @begin@ AND @end@";
    } else if ("mysql".equals(this.baseType)) {
      this.limitSql = "@sql@ limit @begin@,@end@";
    } 
  }
  
  public String getZhishi(String key, String userAccounts, String channelId) {
    String result = SecurityUtil.checkSecurity(key, "portalService");
    String userId = StaticParam.getEmpIdByAccount(userAccounts);
    StringBuffer xmlStr = new StringBuffer();
    if ("".equals(result) && !"".equals(userId)) {
      System.out.println("获得知识信息");
      int num = 15;
      String orgId = StaticParam.getOrgIdByEmpId(userId);
      String[] groupId = StaticParam.getGroupIdByEmpId(userId).split(",");
      String[] orgIds = StaticParam.getOrgIdStringByOrgId(orgId).split("\\$");
      String channelName = "";
      channelName = getChannelName(Long.valueOf(channelId).toString());
      String moreUrl = "/jsoa/InformationAction.do?channelId=" + channelId + "&channelName=" + channelName + "&channelType=1&userChannelName=知识管理&channelShowType=0&userDefine=1&publicinfo=1";
      String channelSql = "select channel_id,channelreader,channelreaderorg,channelreadergroup from oa_informationchannel where channelidstring like '%$" + channelId + "$%'";
      DataSourceUtil ds = new DataSourceUtil();
      List<String[]> channList = ds.getListQuery(channelSql, "");
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
          String tempOrgIds = obj[2];
          if (!"".equals(tempOrgIds)) {
            tempOrgIds = tempOrgIds.substring(1, tempOrgIds.length() - 1).replace("**", ",");
            String[] tempOrgIdArr = tempOrgIds.split(",");
            String childOrgSQL = "select org_id from org_organization where 1>2";
            for (int m = 0; m < tempOrgIdArr.length; m++)
              childOrgSQL = String.valueOf(childOrgSQL) + " or orgidstring like '%$" + tempOrgIdArr[m] + "$%'"; 
            List<String[]> ids = ds.getListQuery(childOrgSQL, "");
            String tempIds = "";
            for (int k = 0; k < ids.size(); k++) {
              String[] objArr = ids.get(k);
              tempIds = String.valueOf(tempIds) + "*" + objArr[0] + "*";
            } 
            if (tempIds.contains("*" + orgId + "*")) {
              channelIds = String.valueOf(channelIds) + "," + obj[0];
              go = false;
            } 
          } 
          if (go)
            for (int t = 0; t < groupId.length; t++) {
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
      xmlStr.append("<?xml version='1.0' encoding='UTF-8'?>");
      xmlStr.append("<code>1</code>");
      xmlStr.append("<resultSet>");
      if (sqlList != null && sqlList.size() > 0) {
        for (int k = 0; k < sqlList.size(); k++) {
          String[] obj = sqlList.get(k);
          xmlStr.append("<result_" + k + ">");
          xmlStr.append(XMLUtil.getXMlString("title", (new StringBuilder(String.valueOf(obj[0]))).toString()));
          xmlStr.append(XMLUtil.getXMlString("url", "/jsoa/info/view_detail.jsp?informationId=" + obj[3]));
          xmlStr.append(XMLUtil.getXMlString("date", obj[2].substring(0, obj[2].length() - 2)));
          xmlStr.append("</result_" + k + ">");
        } 
        xmlStr.append(XMLUtil.getXMlString("moreUrl", moreUrl));
      } else {
        xmlStr.append("<result_0>");
        xmlStr.append(XMLUtil.getXMlString("title", ""));
        xmlStr.append(XMLUtil.getXMlString("url", ""));
        xmlStr.append(XMLUtil.getXMlString("date", ""));
        xmlStr.append("</result_0>");
        xmlStr.append(XMLUtil.getXMlString("moreUrl", ""));
      } 
      xmlStr.append("</resultSet>");
    } else {
      xmlStr.append(result);
    } 
    return xmlStr.toString();
  }
  
  public String getGonggao(String key, String userAccounts) {
    String result = SecurityUtil.checkSecurity(key, "portalService");
    String userId = StaticParam.getEmpIdByAccount(userAccounts);
    StringBuffer xmlStr = new StringBuffer();
    if ("".equals(result) && !"".equals(userId)) {
      System.out.println("获得公告信息");
      int num = 15;
      String orgId = StaticParam.getOrgIdByEmpId(userId);
      String[] groupId = StaticParam.getGroupIdByEmpId(userId).split(",");
      String[] orgIds = StaticParam.getOrgIdStringByOrgId(orgId).split("\\$");
      String[][] info = { { "100", "新闻" }, { "101", "公告" } };
      ChannelBD cbd = new ChannelBD();
      if (SystemCommon.getMultiDepart() == 1) {
        String[] orgString = StaticParam.getOrgIdStringByOrgId(orgId).split("\\$");
        String corpId = orgString[3];
        info = cbd.getChannelSimpleInfoByCorpId(corpId, 0);
      } 
      String moreUrl = "/jsoa/InformationAction.do?channelId=" + info[1][0] + "&channelName=" + info[1][1] + "&channelType=1&userChannelName=公共信息&channelShowType=0&userDefine=1&publicinfo=1";
      String channelSql = "select channel_id,channelreader,channelreaderorg,channelreadergroup from oa_informationchannel where channelidstring like '%$" + info[1][0] + "$%'";
      DataSourceUtil ds = new DataSourceUtil();
      List<String[]> channList = ds.getListQuery(channelSql, "");
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
          String tempOrgIds = obj[2];
          if (!"".equals(tempOrgIds)) {
            tempOrgIds = tempOrgIds.substring(1, tempOrgIds.length() - 1).replace("**", ",");
            String[] tempOrgIdArr = tempOrgIds.split(",");
            String childOrgSQL = "select org_id from org_organization where 1>2";
            for (int m = 0; m < tempOrgIdArr.length; m++)
              childOrgSQL = String.valueOf(childOrgSQL) + " or orgidstring like '%$" + tempOrgIdArr[m] + "$%'"; 
            List<String[]> ids = ds.getListQuery(childOrgSQL, "");
            String tempIds = "";
            for (int k = 0; k < ids.size(); k++) {
              String[] objArr = ids.get(k);
              tempIds = String.valueOf(tempIds) + "*" + objArr[0] + "*";
            } 
            if (tempIds.contains("*" + orgId + "*")) {
              channelIds = String.valueOf(channelIds) + "," + obj[0];
              go = false;
            } 
          } 
          if (go)
            for (int t = 0; t < groupId.length; t++) {
              if (reader.contains("*" + groupId[t] + "*")) {
                channelIds = String.valueOf(channelIds) + "," + obj[0];
                break;
              } 
            }  
        } 
      } 
      String orgSql = "";
      for (int j = 0; j < orgIds.length; j++) {
        if (j % 2 != 0)
          orgSql = String.valueOf(orgSql) + " i.informationreaderorg like '%*" + orgIds[j] + "*%' or"; 
      } 
      String sql = "select i.informationtitle,i.informationissuer,i.informationissuetime,i.information_id from oa_information i join oa_informationChannel c on i.channel_id=c.channel_id where i.channel_id in (" + 
        
        channelIds + ") and ((i.informationreader like '%$" + userId + "$%' or (" + orgSql + " 1>2) " + 
        "or(CONCAT(i.informationreaderorg,i.informationreader)='' or CONCAT(i.informationreaderorg,i.informationreader) is null)))" + 
        "order by i.informationissuetime desc";
      this.limitSql = this.limitSql.replace("@sql@", sql).replace("@begin@", "0").replace("@end@", (new StringBuilder(String.valueOf(num))).toString());
      List<String[]> sqlList = (new DataSourceUtil()).getListQuery(this.limitSql, "");
      xmlStr.append("<?xml version='1.0' encoding='UTF-8'?>");
      xmlStr.append("<code>1</code>");
      xmlStr.append("<resultSet>");
      if (sqlList != null && sqlList.size() > 0) {
        for (int k = 0; k < sqlList.size(); k++) {
          String[] obj = sqlList.get(k);
          xmlStr.append("<result_" + k + ">");
          xmlStr.append(XMLUtil.getXMlString("title", (new StringBuilder(String.valueOf(obj[0]))).toString()));
          xmlStr.append(XMLUtil.getXMlString("url", "/jsoa/info/view_detail.jsp?informationId=" + obj[3]));
          xmlStr.append(XMLUtil.getXMlString("date", obj[2].substring(0, obj[2].length() - 2)));
          xmlStr.append("</result_" + k + ">");
        } 
        xmlStr.append(XMLUtil.getXMlString("moreUrl", moreUrl));
      } else {
        xmlStr.append("<result_0>");
        xmlStr.append(XMLUtil.getXMlString("title", ""));
        xmlStr.append(XMLUtil.getXMlString("url", ""));
        xmlStr.append(XMLUtil.getXMlString("date", ""));
        xmlStr.append("</result_0>");
        xmlStr.append(XMLUtil.getXMlString("moreUrl", ""));
      } 
      xmlStr.append("</resultSet>");
    } else {
      xmlStr.append(result);
    } 
    return xmlStr.toString();
  }
  
  public String getDaiban(String key, String userAccounts) {
    String result = SecurityUtil.checkSecurity(key, "portalService");
    StringBuffer xmlStr = new StringBuffer();
    String userId = StaticParam.getEmpIdByAccount(userAccounts);
    if ("".equals(result)) {
      if (!"".equals(userId)) {
        int num = 15;
        String tempSort = " aaa.stickie desc,";
        String viewSql = "select aaa.WORKFILETYPE, aaa.WORKCURSTEP, aaa.WORKTITLE, aaa.WORKLEFTLINKFILE, aaa.WORKSUBMITPERSON, aaa.WORKSUBMITTIME, aaa.WORKTYPE, aaa.WORKACTIVITY, aaa.WORKTABLE_ID, aaa.WORKRECORD_ID, aaa.WF_WORK_ID, aaa.WORKSUBMITPERSON, aaa.WF_SUBMITEMPLOYEE_ID, aaa.WORKALLOWCANCEL, aaa.WORKPROCESS_ID, aaa.WORKSTEPCOUNT,aaa.WORKMAINLINKFILE, aaa.WORKSUBMITTIME, aaa.WORKCURSTEP, aaa.CREATORCANCELLINK, aaa.ISSTANDFORWORK, aaa.STANDFORUSERID, aaa.STANDFORUSERNAME,aaa.WORKCREATEDATE,aaa.SUBMITORG,aaa.WORKDONEWITHDATE,aaa.emergence,aaa.INITACTIVITY,aaa.initActivityName,aaa.tranType, aaa.tranFromPersonId, aaa.processDeadlineDate,aaa.WF_CUREMPLOYEE_ID,aaa.relproject_id,aaa.work_hangup  ,aaa.workDeadlineDate,aaa.stickie  ";
        String fromSql = "  FROM JSF_WORK aaa ";
        String whereSql = "  where aaa.WORKSTATUS = 0 and aaa.WF_CUREMPLOYEE_ID = " + userId + 
          " and aaa.WORKLISTCONTROL=1 and aaa.WORKDELETE=0 order by " + tempSort + " aaa.emergence desc, aaa.WF_WORK_ID desc";
        this.limitSql = this.limitSql.replace("@sql@", String.valueOf(viewSql) + fromSql + whereSql).replace("@begin@", "0").replace("@end@", (new StringBuilder(String.valueOf(num))).toString());
        List<String[]> sqlList = (new DataSourceUtil()).getListQuery(this.limitSql, "");
        xmlStr.append("<?xml version='1.0' encoding='UTF-8'?>");
        xmlStr.append("<code>1</code>");
        xmlStr.append("<resultSet>");
        if (sqlList != null && sqlList.size() > 0) {
          for (int i = 0; i < sqlList.size(); i++) {
            String[] obj = sqlList.get(i);
            xmlStr.append("<result_" + i + ">");
            xmlStr.append(XMLUtil.getXMlString("title", (new StringBuilder(String.valueOf(obj[2]))).toString()));
            String url = DaiBanTask.getDaibanUrl((new StringBuilder(String.valueOf(obj[2]))).toString(), (new StringBuilder(String.valueOf(obj[16]))).toString(), (new StringBuilder(String.valueOf(obj[10]))).toString(), (
                new StringBuilder(String.valueOf(obj[6]))).toString(), (new StringBuilder(String.valueOf(obj[7]))).toString(), (new StringBuilder(String.valueOf(obj[8]))).toString(), (
                new StringBuilder(String.valueOf(obj[9]))).toString(), (new StringBuilder(String.valueOf(obj[0]))).toString(), (new StringBuilder(String.valueOf(obj[11]))).toString(), (
                new StringBuilder(String.valueOf(obj[12]))).toString(), (new StringBuilder(String.valueOf(obj[18]))).toString(), (new StringBuilder(String.valueOf(obj[17]))).toString(), (
                new StringBuilder(String.valueOf(obj[14]))).toString(), (new StringBuilder(String.valueOf(obj[15]))).toString(), (new StringBuilder(String.valueOf(obj[20]))).toString(), (
                new StringBuilder(String.valueOf(obj[21]))).toString(), (new StringBuilder(String.valueOf(obj[22]))).toString(), (new StringBuilder(String.valueOf(obj[27]))).toString(), (
                new StringBuilder(String.valueOf(obj[28]))).toString(), (new StringBuilder(String.valueOf(obj[5]))).toString(), (new StringBuilder(String.valueOf(obj[29]))).toString(), (
                new StringBuilder(String.valueOf(obj[30]))).toString(), (new StringBuilder(String.valueOf(obj[31]))).toString(), (new StringBuilder(String.valueOf(obj[34]))).toString());
            xmlStr.append(XMLUtil.getXMlString("url", url));
            xmlStr.append(XMLUtil.getXMlString("date", obj[5].substring(0, obj[2].length() - 2)));
            xmlStr.append("</result_" + i + ">");
          } 
          xmlStr.append(XMLUtil.getXMlString("moreUrl", "/jsoa/FileDealWithAction.do?workStatus=0"));
        } else {
          xmlStr.append("<result_0>");
          xmlStr.append(XMLUtil.getXMlString("title", ""));
          xmlStr.append(XMLUtil.getXMlString("url", ""));
          xmlStr.append(XMLUtil.getXMlString("date", ""));
          xmlStr.append("</result_0>");
          xmlStr.append(XMLUtil.getXMlString("moreUrl", ""));
        } 
        xmlStr.append("</resultSet>");
      } else {
        result = "<?xml version='1.0' encoding='UTF-8'?><code>0</code><resultSet><errmsg>该用户不存在</errmsg></resultSet>";
        xmlStr.append(result);
      } 
    } else {
      xmlStr.append(result);
    } 
    return xmlStr.toString();
  }
  
  public String getTaskNum(String key, String userAccounts) {
    String result = SecurityUtil.checkSecurity(key, "portalService");
    String userId = StaticParam.getEmpIdByAccount(userAccounts);
    StringBuffer xmlStr = new StringBuffer();
    if ("".equals(result)) {
      if (!"".equals(userId)) {
        xmlStr.append("<?xml version='1.0' encoding='UTF-8'?>");
        xmlStr.append("<code>1</code>");
        xmlStr.append("<resultSet>");
        String urlyifa = "/jsoa/FileDealWithAction.do?workStatus=1";
        String yifaSql = "select COUNT(*)   FROM JSF_WORK aaa where aaa.WORKSTATUS = 1 and aaa.WF_CUREMPLOYEE_ID = " + userId + " and aaa.WORKLISTCONTROL=1 and aaa.WORKDELETE=0 ";
        int yifacount = (new DataSourceUtil()).getQueryCount(yifaSql);
        xmlStr.append("<yifa>");
        xmlStr.append(XMLUtil.getXMlString("title", "已发事项"));
        xmlStr.append(XMLUtil.getXMlString("count", (new StringBuilder(String.valueOf(yifacount))).toString()));
        xmlStr.append(XMLUtil.getXMlString("urlyifa", (new StringBuilder(String.valueOf(urlyifa))).toString()));
        xmlStr.append("</yifa>");
        String urlbanjie = "/jsoa/FileDealWithAction.do?workStatus=1012";
        String banjieSql = "select COUNT(*)   FROM JSF_WORK aaa where aaa.WORKSTATUS = 101 and aaa.WORKDONEWITHDATE is not null and aaa.WF_CUREMPLOYEE_ID = " + userId + " and aaa.WORKLISTCONTROL=1 and aaa.WORKDELETE=0 ";
        int banjiecount = (new DataSourceUtil()).getQueryCount(banjieSql);
        xmlStr.append("<banjie>");
        xmlStr.append(XMLUtil.getXMlString("title", "办结事项"));
        xmlStr.append(XMLUtil.getXMlString("count", (new StringBuilder(String.valueOf(banjiecount))).toString()));
        xmlStr.append(XMLUtil.getXMlString("urlbanjie", (new StringBuilder(String.valueOf(urlbanjie))).toString()));
        xmlStr.append("</banjie>");
        String urlzaiban = "/jsoa/FileDealWithAction.do?workStatus=1011";
        String zaibanSql = "select COUNT(*)   FROM JSF_WORK aaa where aaa.WORKSTATUS = 101 and aaa.WORKDONEWITHDATE is null and aaa.WF_CUREMPLOYEE_ID = " + userId + " and aaa.WORKLISTCONTROL=1 and aaa.WORKDELETE=0 ";
        int zaibancount = (new DataSourceUtil()).getQueryCount(zaibanSql);
        xmlStr.append("<zaiban>");
        xmlStr.append(XMLUtil.getXMlString("title", "在办事项"));
        xmlStr.append(XMLUtil.getXMlString("count", (new StringBuilder(String.valueOf(zaibancount))).toString()));
        xmlStr.append(XMLUtil.getXMlString("urlzaiban", (new StringBuilder(String.valueOf(urlzaiban))).toString()));
        xmlStr.append("</zaiban>");
        String urldaiban = "/jsoa/FileDealWithAction.do?workStatus=1011";
        String daibanSql = "select COUNT(*)   FROM JSF_WORK aaa where aaa.WORKSTATUS = 0  and aaa.WF_CUREMPLOYEE_ID = " + userId + " and aaa.WORKLISTCONTROL=1 and aaa.WORKDELETE=0 ";
        int daibancount = (new DataSourceUtil()).getQueryCount(daibanSql);
        xmlStr.append("<daiban>");
        xmlStr.append(XMLUtil.getXMlString("title", "待办事项"));
        xmlStr.append(XMLUtil.getXMlString("count", (new StringBuilder(String.valueOf(daibancount))).toString()));
        xmlStr.append(XMLUtil.getXMlString("urldaiban", (new StringBuilder(String.valueOf(urldaiban))).toString()));
        xmlStr.append("</daiban>");
        xmlStr.append("</resultSet>");
      } else {
        result = "<?xml version='1.0' encoding='UTF-8'?><code>0</code><resultSet><errmsg>该用户不存在</errmsg></resultSet>";
        xmlStr.append(result);
      } 
    } else {
      xmlStr.append(result);
    } 
    return xmlStr.toString();
  }
  
  public static String getChannelName(String channelId) {
    String channelName = "";
    String sql = "SELECT CHANNELNAME FROM OA_INFORMATIONCHANNEL WHERE CHANNEL_ID=" + channelId;
    List<String> sqlList = (new DataSourceUtil()).getQuery(sql, "");
    if (sqlList != null && sqlList.size() > 0)
      channelName = sqlList.get(0); 
    return channelName;
  }
  
  public static void main(String[] args) {}
}
