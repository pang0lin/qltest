package com.js.oa.archives.util;

import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;

public class ArchivesUtil {
  public static String[] getInfoChannelId(String infoId) {
    String[] channelId = new String[4];
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String sql = "SELECT a.channel_Id,a.informationtype,a.informationhead,c.CHANNELTYPE FROM oa_information a,oa_informationchannel c WHERE a.CHANNEL_ID=c.CHANNEL_ID AND information_id=" + 
      infoId;
    try {
      base.begin();
      rs = base.executeQuery(sql);
      if (rs.next()) {
        channelId[0] = rs.getString(1);
        channelId[1] = rs.getString(2);
        channelId[2] = rs.getString(3);
        channelId[3] = rs.getString(4);
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return channelId;
  }
  
  public static String[] getFlowInfo(String workId) {
    String[] flowInfo = (String[])null;
    String sql = "SELECT WORKSTATUS,wf_work_id,workprocess_id,WORKTABLE_ID,WF_SUBMITEMPLOYEE_ID,WORKSTEPCOUNT,WORKACTIVITY,workType FROM jsf_work WHERE wf_work_id=" + 
      workId + " order by wf_work_id";
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      base.begin();
      rs = base.executeQuery(sql);
      if (rs.next()) {
        flowInfo = new String[8];
        flowInfo[0] = rs.getString(1);
        flowInfo[1] = rs.getString(2);
        flowInfo[2] = rs.getString(3);
        flowInfo[3] = rs.getString(4);
        flowInfo[4] = rs.getString(5);
        flowInfo[5] = rs.getString(6);
        flowInfo[6] = rs.getString(7);
        flowInfo[7] = rs.getString(8);
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return flowInfo;
  }
  
  public static String[] getDocInfo(String docId) {
    String[] docInfo = new String[1];
    String sql = "SELECT SENDFILE_TABLEID FROM doc_documentsendfile WHERE DOCUMENTSENDFILE_ID=" + docId;
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      base.begin();
      rs = base.executeQuery(sql);
      if (rs.next())
        docInfo[0] = rs.getString(1); 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return docInfo;
  }
  
  public static String[] getReceiveInfo(String docId) {
    String[] receiveInfo = new String[1];
    String sql = "SELECT tableId FROM doc_receivefile WHERE RECEIVEFILE_ID=" + docId;
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      base.begin();
      rs = base.executeQuery(sql);
      if (rs.next())
        receiveInfo[0] = rs.getString(1); 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return receiveInfo;
  }
  
  public static String[] getCoopInfo(String coId) {
    String[] coopInfo = new String[2];
    String sql = "SELECT id,node_id FROM co_nodemember WHERE body_id=" + coId;
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      base.begin();
      rs = base.executeQuery(sql);
      if (rs.next()) {
        coopInfo[0] = rs.getString(1);
        coopInfo[1] = rs.getString(2);
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return coopInfo;
  }
  
  public static String[] getSendDocInfo(String sendId) {
    String[] sendInfo = new String[2];
    String sql = "SELECT sendfile_text,documentSendfile_wordtype FROM doc_documentsendfile WHERE documentsendfile_id=" + sendId;
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      base.begin();
      rs = base.executeQuery(sql);
      if (rs.next()) {
        sendInfo[0] = rs.getString(1);
        sendInfo[1] = rs.getString(2);
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return sendInfo;
  }
  
  public static String clickImg(String flag, String Id) {
    return clickImg(flag, Id, "", "");
  }
  
  public static String clickImg(String flag, String Id, String show) {
    return clickImg(flag, Id, show, "");
  }
  
  public static String clickImg(String flag, String Id, String show, String oId) {
    String imgClick = "";
    if (!"".equals(Id)) {
      if ("".equals(show)) {
        imgClick = String.valueOf(imgClick) + "yuanJian('";
      } else if ("1".equals(show)) {
        imgClick = String.valueOf(imgClick) + "openInfo('";
      } else {
        imgClick = (new StringBuilder(String.valueOf(imgClick))).toString();
      } 
      if (!"2".equals(show))
        if ("ZSGL".equals(flag)) {
          String[] info = getInfoChannelId(Id);
          imgClick = String.valueOf(imgClick) + "/jsoa/InformationAction.do?action=openInfo&channelId=" + info[0] + "&informationId=" + Id + 
            "&channelName=&orgId=&userChannelName=&checkdepart=&redHead=" + info[2] + "&informationType=" + info[1] + "&channelType=" + info[3];
        } else if ("GWGL-SWGL".equals(flag)) {
          String[] receiveInfo = getReceiveInfo(Id);
          imgClick = String.valueOf(imgClick) + "/jsoa/GovReceiveFileAction.do?action=listLoad&editId=" + Id + "&viewOnly=0&tableId=" + receiveInfo[0];
        } else if ("GWGL-FWGL".equals(flag)) {
          String[] docInfo = getDocInfo(Id);
          imgClick = String.valueOf(imgClick) + "/jsoa/GovSendFileAction.do?action=listLoad&editId=" + Id + "&editType=0&canEdit=0&viewOnly=0&myFile=1&tableId=" + docInfo[0];
        } else if ("GZLC".equals(flag)) {
          String[] flowInfo = getFlowInfo(Id);
          if (flowInfo == null) {
            imgClick = String.valueOf(imgClick) + "wu";
          } else {
            imgClick = String.valueOf(imgClick) + "/jsoa/jsflow/workflow_listInfo.jsp?workId=" + Id + "&processStatus=0&workStatus=1&curStatus=" + flowInfo[0] + "&fromDossierData=y";
          } 
        } else if ("COOP".equals(flag)) {
          String[] coopInfo = getCoopInfo(Id);
          imgClick = String.valueOf(imgClick) + "/jsoa/BodyAction.do?flag=toDealwith&bodyId=" + Id + "&nodeId=" + coopInfo[1] + "&memberId=" + coopInfo[0] + "&status=1001";
        } else if ("GWGL-DOC".equals(flag)) {
          String[] sendInfo = getSendDocInfo(Id);
          imgClick = String.valueOf(imgClick) + "/jsoa/iWebOfficeSign/DocumentEdit.jsp?RecordID=" + sendInfo[0] + "&EditType=0&UserName=&CanSave=1&hiddenStatus=1&showTempSign=2&showTempHead=1" + 
            "&ShowSign=0&showSignButton=0&showEditButton=0&FileType=" + sendInfo[1];
        }  
      if ("".equals(show)) {
        imgClick = String.valueOf(imgClick) + "&finish=1')";
      } else if ("1".equals(show)) {
        imgClick = String.valueOf(imgClick) + "','" + oId + "')";
      } else if ("2".equals(show)) {
        imgClick = "/jsoa/info/info_kits.jsp?flag=" + flag + "&infoId=" + oId + "&show=" + show + "&Id=" + Id;
      } else {
        imgClick = (new StringBuilder(String.valueOf(imgClick))).toString();
      } 
    } 
    return imgClick;
  }
}
