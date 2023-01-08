package com.js.sso.util;

public class DaiBanTask {
  public static String getDaibanUrl(String workTitle, String url, String work, String workType, String activity, String table, String record, String processName, String submitPerson, String submitPersonId, String activityName, String submitTime, String processId, String stepCount, String isStandForWork, String isStandForUserId, String isStandForUserName, String initActivity, String initActivityName, String submitPersonTime, String tranType, String tranFromPersonId, String processDeadlineDate, String workhangup) {
    String urlStr = "";
    if ("数据采集".equals(processName) && url.indexOf("action=fileList") > 0) {
      urlStr = url;
    } else {
      urlStr = String.valueOf(url) + "&search=" + 
        "&from=dealwith" + 
        "&workTitle=" + 
        "&activityName=" + activityName + 
        "&submitPersonId=" + submitPersonId + 
        "&submitPerson=" + submitPerson + 
        "&work=" + work + 
        "&workType=" + workType + 
        "&activity=" + activity + 
        "&table=" + table + 
        "&record=" + record + 
        "&processName=" + processName + 
        "&workStatus=0" + 
        "&submitTime=" + submitTime + 
        "&processId=" + processId + 
        "&stepCount=" + stepCount + 
        "&isStandForWork=" + isStandForWork + 
        "&standForUserId=" + isStandForUserId + 
        "&standForUserName=" + isStandForUserName + 
        "&initActivity=" + initActivity + 
        "&initActivityName=" + initActivityName + 
        "&submitPersonTime=" + submitPersonTime + 
        "&tranType=" + tranType + 
        "&tranFromPersonId=" + tranFromPersonId + 
        "&processDeadlineDate=" + processDeadlineDate;
    } 
    return urlStr;
  }
}
