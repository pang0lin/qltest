package com.qq.weixin.mp.service.impl;

import com.js.oa.archives.util.ArchivesUtil;
import com.js.oa.userdb.util.DbOpt;
import com.js.oa.weixin.common.bean.WeiXinBean;
import com.qq.weixin.mp.service.WeixinMessageUrlGetter;
import java.sql.SQLException;

public class WeixinMessageUrlGetterImpl_Info implements WeixinMessageUrlGetter {
  public String getWeiXinUrl(String messageUrl, String dataId, String currentUserId) {
    String url = "";
    String informationId = "";
    String workId = "";
    if (messageUrl != null && !"".equals(messageUrl))
      if (messageUrl.indexOf("info_kits.jsp") > -1) {
        if (messageUrl.contains("flag=GWGL-FWGL&") || messageUrl.contains("flag=GWGL-SWGL&")) {
          String recordId = "0";
          String tableId = "0";
          if (messageUrl.indexOf("&Id=") > -1)
            recordId = messageUrl.substring(messageUrl.indexOf("&Id=") + 4); 
          DbOpt db = new DbOpt();
          try {
            String sql = "SELECT documentsendfile_id,sendfile_tableid FROM doc_documentsendfile WHERE documentsendfile_id=" + recordId;
            if (messageUrl.indexOf("flag=GWGL-SWGL&") > -1)
              sql = "SELECT receivefile_id,tableid FROM doc_receivefile WHERE receivefile_id=" + recordId; 
            String[][] result = db.executeQueryToStrArr2(sql, 2);
            if (result != null && result.length > 0)
              tableId = result[0][1]; 
          } catch (Exception e) {
            e.printStackTrace();
          } finally {
            try {
              db.close();
            } catch (SQLException e) {
              e.printStackTrace();
            } 
          } 
          url = "/weixin/doc/fileInfo.jsp?isMessage=1&editId=" + recordId + "&tableId=" + tableId + "&" + messageUrl.split("\\?")[1];
        } else if (messageUrl.contains("flag=COOP&")) {
          String[] inforIdStr = messageUrl.split("&");
          informationId = inforIdStr[3].toString().replace("Id=", "");
          if (informationId != null && !"".equals(informationId)) {
            String[] coopInfo = ArchivesUtil.getCoopInfo(informationId);
            url = "/weiXinCoopAction.do?action=toDealwith&bodyId=" + informationId + "&nodeId=" + coopInfo[1] + "&memberId=" + coopInfo[0] + "&status=1001&from=message";
          } 
        } else if (messageUrl.contains("flag=GZLC&")) {
          String[] inforIdStr = messageUrl.split("&");
          workId = inforIdStr[3].toString().replace("Id=", "");
          url = "/jsflow/workflow_listInfo.jsp?workId=" + workId + "&processStatus=0&workStatus=100&curStatus=100&fromDossierData=y";
        } 
      } else if (messageUrl.indexOf("/view_onlyfile.jsp?") < 0) {
        informationId = messageUrl.substring(messageUrl.indexOf("informationId=") + 14, messageUrl.length());
        if (informationId != null && !"".equals(informationId))
          url = "/weiXinTzggAction.do?action=readZsgl&readId=" + informationId; 
      } else if (messageUrl.indexOf("view_onlyfile.jsp") > 0) {
        informationId = messageUrl.substring(messageUrl.indexOf("informationId=") + 14, messageUrl.length());
        url = "/weixin/common/view_onlyfile.jsp?informationId=" + informationId;
      }  
    return url;
  }
  
  public String[] getRemindInfo(String title, String messageUrl, String dataid, String emp_Id) {
    String mainForumId = "";
    String url = "";
    String[] strImag = new String[3];
    WeiXinBean weixinBean = new WeiXinBean();
    if (messageUrl != null && !"".equals(messageUrl)) {
      if (messageUrl.contains("flag=COOP&")) {
        String[] inforIdStr = messageUrl.split("&");
        mainForumId = inforIdStr[1].toString().replace("infoId=", "");
      } else if (messageUrl.contains("flag=GZLC&")) {
        String[] inforIdStr = messageUrl.split("&");
        mainForumId = inforIdStr[1].toString().replace("infoId=", "");
      } else {
        mainForumId = messageUrl.substring(messageUrl.indexOf("informationId=") + 14, messageUrl.length());
      } 
      if (mainForumId != null && !"".equals(mainForumId) && messageUrl.indexOf("/view_onlyfile.jsp?") < 0)
        url = weixinBean.ImgNamePath(mainForumId); 
    } 
    strImag[0] = title;
    strImag[1] = "";
    strImag[2] = url;
    return strImag;
  }
}
