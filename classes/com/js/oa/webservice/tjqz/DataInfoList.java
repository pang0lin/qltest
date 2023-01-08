package com.js.oa.webservice.tjqz;

import com.js.oa.info.channelmanager.bean.ChannelEJBBean;
import com.js.oa.info.channelmanager.service.ChannelBD;
import com.js.oa.info.infomanager.service.InformationBD;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.manager.service.ManagerService;
import com.js.system.util.StaticParam;
import java.io.StringReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

public class DataInfoList {
  public String getDataInfoList(String xml) {
    StringBuffer xmlStr = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root>");
    if (xml == null || "".equals(xml)) {
      xmlStr.append("<message result=\"0\" reason=\"获取数据参数为空\"></message>");
    } else {
      DataInfoPO po = getPOFromXml(xml);
      if ("xtgg".equals(po.getDataType()) || "xxxw".equals(po.getDataType())) {
        xmlStr.append(getXXXW(po));
      } else if ("gzjh".equals(po.getDataType())) {
        xmlStr.append(getGZJH(po));
      } else if ("xtgz".equals(po.getDataType())) {
        xmlStr.append(getXTGZ(po));
      } else if ("yfsx".equals(po.getDataType())) {
        xmlStr.append(getYFSX(po));
      } else if ("jsdb".equals(po.getDataType())) {
        xmlStr.append(getJSDB(po));
      } else if ("hysq".equals(po.getDataType())) {
        xmlStr.append(getHYSQ(po));
      } else if ("xxxw".equals(po.getDataType())) {
        xmlStr.append(getXXXW(po));
      } else {
        xmlStr.append("<message result=\"0\" reason=\"获取数据类型错误\"></message>");
      } 
    } 
    return xmlStr.append("</root>").toString();
  }
  
  private String getXTGZ(DataInfoPO po) {
    StringBuffer xmlStr = new StringBuffer();
    String userId = StaticParam.getEmpIdByAccount(po.getUserId());
    if ("".equals(userId)) {
      xmlStr.append("<message result=\"0\" reason=\"用户信息不存在\"></message>");
    } else {
      String sql = "SELECT DISTINCT b.ID,a.ID,a.node_id,b.title,b.postTime,b.posterId,b.posterName FROM CO_BODY b,CO_NODEMEMBER a WHERE b.ID=a.body_id AND a.status=10 AND a.emp_id=" + 
        userId + " ORDER BY b.postTime DESC";
      DbOpt db = new DbOpt();
      try {
        String[][] objs = db.executeQueryToStrArr2(sql, 7);
        xmlStr.append("<message result=\"1\" reason=\"\"></message>");
        xmlStr.append("<datas>");
        if (objs != null && objs.length > 0) {
          String[] obj = (String[])null;
          for (int i = 0; i < objs.length; i++) {
            obj = objs[i];
            xmlStr.append("<data ID=\"" + obj[1] + "\" YHBH=\"" + po.getUserId() + "\" YJBT=\"" + obj[3] + "\" YX=\"" + obj[6] + "\" FSSJ=\"" + obj[4] + "\" SFDQ=\"\"></data>");
          } 
        } 
        xmlStr.append("</datas>");
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          db.close();
        } catch (SQLException e) {
          e.printStackTrace();
        } 
      } 
    } 
    return xmlStr.toString();
  }
  
  private String getYFSX(DataInfoPO po) {
    StringBuffer xmlStr = new StringBuffer();
    String userId = StaticParam.getEmpIdByAccount(po.getUserId());
    if ("".equals(userId)) {
      xmlStr.append("<message result=\"0\" reason=\"用户信息不存在\"></message>");
    } else {
      String sql = "SELECT WF_WORK_ID,WORKFILETYPE,WORKTITLE,WORKSUBMITTIME,WORKCURSTEP,wf_curemployee_id,workprocess_id,worktable_id,workrecord_id,tranType FROM JSF_WORK WHERE WORKSTATUS=1 AND WF_CUREMPLOYEE_ID=" + 
        userId + " AND WORKLISTCONTROL=1 AND WORKDELETE=0 ORDER BY stickie DESC,WF_WORK_ID DESC";
      DbOpt db = new DbOpt();
      try {
        String[][] objs = db.executeQueryToStrArr2(sql, 10);
        xmlStr.append("<message result=\"1\" reason=\"\"></message>");
        xmlStr.append("<datas>");
        if (objs != null && objs.length > 0) {
          String[] obj = (String[])null;
          String empName = "";
          for (int i = 0; i < objs.length; i++) {
            obj = objs[i];
            sql = "select GROUP_CONCAT(empname) from org_employee,JSF_WORK where emp_id=wf_curemployee_id and workstatus=0 and worklistcontrol=1 and workprocess_id=" + 
              obj[6] + " and worktable_id=" + obj[7] + 
              " and workrecord_id=" + obj[8];
            if (obj[9] != null && "1".equals(obj[9]))
              sql = "select GROUP_CONCAT(empname) from org_employee,JSF_WORK where emp_id=wf_curemployee_id and workstatus=0 and worklistcontrol=1 and workprocess_id=" + 
                obj[6] + " and worktable_id=" + obj[7] + 
                " and workrecord_id=" + obj[8] + " and wf_curemployee_id=" + obj[5]; 
            empName = db.executeQueryToStr(sql);
            obj = objs[i];
            xmlStr.append("<data ID=\"" + obj[0] + "\" YHBH=\"" + po.getUserId() + "\" YWMC=\"" + obj[1] + "\" BT=\"" + obj[2] + "\" TJRQ=\"" + obj[3] + 
                "\" DQSPR=\"" + empName + "\" SPHJ=\"" + obj[4] + "\"></data>");
          } 
        } 
        xmlStr.append("</datas>");
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          db.close();
        } catch (SQLException e) {
          e.printStackTrace();
        } 
      } 
    } 
    return xmlStr.toString();
  }
  
  private String getJSDB(DataInfoPO po) {
    StringBuffer xmlStr = new StringBuffer();
    String userId = StaticParam.getEmpIdByAccount(po.getUserId());
    if ("".equals(userId)) {
      xmlStr.append("<message result=\"0\" reason=\"用户信息不存在\"></message>");
    } else {
      String sql = "SELECT WF_WORK_ID,WORKFILETYPE,WORKCURSTEP,WORKTITLE,WORKSUBMITPERSON,WORKSUBMITTIME FROM JSF_WORK WHERE WORKSTATUS=0 AND WF_CUREMPLOYEE_ID=" + 
        userId + " AND WORKLISTCONTROL=1 AND WORKDELETE=0 ORDER BY stickie DESC,emergence DESC,WF_WORK_ID DESC";
      DbOpt db = new DbOpt();
      try {
        String[][] objs = db.executeQueryToStrArr2(sql, 6);
        xmlStr.append("<message result=\"1\" reason=\"\"></message>");
        xmlStr.append("<datas>");
        if (objs != null && objs.length > 0) {
          String[] obj = (String[])null;
          for (int i = 0; i < objs.length; i++) {
            obj = objs[i];
            xmlStr.append("<data ID=\"" + obj[0] + "\" YHBH=\"" + po.getUserId() + "\" LX=\"" + obj[1] + "\" DBSX=\"" + obj[3] + "\" TJR=\"" + obj[4] + "\" TJRQ=\"" + obj[5] + "\"></data>");
          } 
        } 
        xmlStr.append("</datas>");
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          db.close();
        } catch (SQLException e) {
          e.printStackTrace();
        } 
      } 
    } 
    return xmlStr.toString();
  }
  
  private String getHYSQ(DataInfoPO po) {
    StringBuffer xmlStr = new StringBuffer();
    String userId = StaticParam.getEmpIdByAccount(po.getUserId());
    if ("".equals(userId)) {
      xmlStr.append("<message result=\"0\" reason=\"用户信息不存在\"></message>");
    } else {
      String sql = "SELECT a.BOARDROOMAPPLYID,b.Name,b.LOCATION,a.MOTIF,a.APPLYDATE,a.applyempname,a.applyorgname FROM OA_BOARDROOMAPPLY a INNER JOIN OA_BOARDROOM b ON a.BOARDROOMID=b.BOARDROOMID INNER JOIN OA_BOARDROOM_MEETINGTIME c ON a.BOARDROOMAPPLYID=c.APPLYID WHERE ((a.EMCEE LIKE '%$" + 

        
        userId + "$%' )OR(a.ATTENDEEEMPID LIKE '%" + userId + 
        "%' )OR(a.ATTENDEELEADERID LIKE '%$" + userId + "$%' )" + 
        "OR(a.NONVOTINGEMPID LIKE '%$" + userId + "$%' )OR(a.NOTEPERSONID LIKE '%$" + userId + "$%' ))" + 
        "AND(a.STATUS=0 )AND(c.STATUS=0 )AND(c.beginLong<" + System.currentTimeMillis() + ") " + 
        "ORDER BY  c.MEETINGTIME DESC , c.STARTTIME DESC";
      DbOpt db = new DbOpt();
      try {
        String[][] objs = db.executeQueryToStrArr2(sql, 7);
        xmlStr.append("<message result=\"1\" reason=\"\"></message>");
        xmlStr.append("<datas>");
        if (objs != null && objs.length > 0) {
          String[] obj = (String[])null;
          for (int i = 0; i < objs.length; i++) {
            obj = objs[i];
            xmlStr.append("<data ID=\"" + obj[0] + "\" HYS=\"" + obj[1] + "\" HYSZT=\"" + obj[2] + "\" HYBT=\"" + 
                obj[3] + "\" YYSJ=\"" + obj[4] + "\" YYR=\"" + obj[5] + "\" YYBM=\"" + obj[6] + "\"></data>");
          } 
        } 
        xmlStr.append("</datas>");
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          db.close();
        } catch (SQLException e) {
          e.printStackTrace();
        } 
      } 
    } 
    return xmlStr.toString();
  }
  
  private String getGZJH(DataInfoPO po) {
    StringBuffer xmlStr = new StringBuffer();
    String userId = StaticParam.getEmpIdByAccount(po.getUserId());
    if ("".equals(userId)) {
      xmlStr.append("<message result=\"0\" reason=\"用户信息不存在\"></message>");
    } else {
      String sql = "SELECT DISTINCT a.EVENT_ID,a.EVENTTITLE,a.EVENTCONTENT,a.EVENTEMPID,a.EVENTEMPNAME,a.EVENTBEGINDATE,a.EVENTENDDATE,a.EVENTBEGINTIME,a.EVENTENDTIME,a.ATTENDNAME,a.ATTENDEMP,a.ATTENDORG,a.ATTENDGROUP FROM OA_EVENT a INNER JOIN OA_EVENTATTENDER b ON a.EVENT_ID=b.EVENT_ID WHERE b.EMP_ID=" + 
        
        userId + 
        " AND a.DOMAIN_ID=0 ORDER BY a.EVENTBEGINDATE DESC,a.EVENTBEGINTIME,a.ECHOBEGINTIME DESC,a.ECHOENDTIME DESC";
      DbOpt db = new DbOpt();
      try {
        String[][] objs = db.executeQueryToStrArr2(sql, 13);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        xmlStr.append("<message result=\"1\" reason=\"\"></message>");
        xmlStr.append("<datas>");
        if (objs != null && objs.length > 0) {
          String[] obj = (String[])null;
          String startTime = "", endTime = "";
          for (int i = 0; i < objs.length; i++) {
            obj = objs[i];
            startTime = sdf.format(new Date(Long.parseLong(obj[5]) + 1000L * Long.parseLong(obj[7])));
            endTime = sdf.format(new Date(Long.parseLong(obj[6]) + 1000L * Long.parseLong(obj[8])));
            xmlStr.append("<data ID=\"" + obj[0] + "\" YHBH=\"" + po.getUserId() + "\" RQ=\"" + startTime + "至" + endTime + "\" GZJHSM=\"" + obj[1] + "\" BZ=\"" + obj[2] + "\"></data>");
          } 
        } 
        xmlStr.append("</datas>");
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          db.close();
        } catch (SQLException e) {
          e.printStackTrace();
        } 
      } 
    } 
    return xmlStr.toString();
  }
  
  private String getXXXW(DataInfoPO po) {
    StringBuffer xmlStr = new StringBuffer();
    String userId = StaticParam.getEmpIdByAccount(po.getUserId());
    if ("".equals(userId)) {
      xmlStr.append("<message result=\"0\" reason=\"用户信息不存在\"></message>");
    } else {
      String orgId = StaticParam.getOrgIdByEmpId(userId);
      String orgIdString = StaticParam.getOrgIdStringByOrgId(orgId);
      String channelId = po.getChannelId();
      String sql = "select a.informationtitle,a.informationissuer,a.informationissuetime,a.information_id from oa_information a join oa_informationChannel b on a.channel_id=b.channel_id ";
      InformationBD informationBD = new InformationBD();
      ChannelBD channelBD = new ChannelBD();
      String includeChild = "";
      List<Object[]> lists = channelBD.getSingleChannel(channelId);
      if (lists != null && lists.size() > 0) {
        Object[] obj = (Object[])null;
        obj = lists.get(0);
        includeChild = obj[22].toString();
      } 
      Date now = new Date();
      String nowString = now.toLocaleString();
      nowString = nowString.substring(0, nowString.indexOf(" "));
      boolean canVindicate = channelBD.canVindicate(userId, orgId, channelId);
      DbOpt db = new DbOpt();
      try {
        if (!informationBD.channelCanView2(userId, orgId, "1", "1", channelId)) {
          sql = String.valueOf(sql) + " where (1>2) ";
        } else {
          if (canVindicate) {
            if (includeChild.equals("0")) {
              sql = String.valueOf(sql) + " where (b.channel_id = " + channelId + " or a.otherChannel like '%," + channelId + ",%') ";
            } else if (includeChild.equals("1")) {
              String childChannelIds = informationBD.getAllChildChannelIds(channelId);
              sql = String.valueOf(sql) + "where (a.channel_id in(" + childChannelIds + ") or ('" + childChannelIds + "' like concat('%',a.otherChannel,'%') and a.otherChannel<>''))";
            } 
          } else {
            sql = String.valueOf(sql) + " where ( a.informationValidType = 0 or '" + nowString + "' between a.validBeginTime and a.validEndTime )";
            if (includeChild.equals("1")) {
              ChannelEJBBean cbean = new ChannelEJBBean();
              String childChannelIds = "0";
              try {
                childChannelIds = cbean.getUserViewChildCh(userId, orgId, po.getChannelId());
              } catch (Exception e) {
                e.printStackTrace();
              } 
              sql = String.valueOf(sql) + " and (b.channel_id in(" + childChannelIds + ") or ";
              sql = String.valueOf(sql) + "('" + childChannelIds + "' like concat('%',a.otherChannel,'%') and a.otherChannel<>''))";
            } else {
              sql = String.valueOf(sql) + " and (b.channel_id = " + po.getChannelId() + " or a.otherChannel like '%," + po.getChannelId() + ",%') ";
            } 
          } 
          sql = String.valueOf(sql) + " and ( b.afficheChannelStatus is null or b.afficheChannelStatus='0' )";
          String readerWhere = "1<>2";
          if (!canVindicate) {
            String tempSql = "SELECT org_id FROM org_organization WHERE '" + orgIdString + "' LIKE CONCAT('%$', org_Id, '$%')";
            String[][] list = db.executeQueryToStrArr2(tempSql, 1);
            int i;
            for (i = 0; i < list.length; i++)
              readerWhere = String.valueOf(readerWhere) + " or a.informationReaderOrg like '%*" + list[i][0] + "*%'"; 
            readerWhere = String.valueOf(readerWhere) + " or a.informationReaderOrg like '%*-1*%'";
            tempSql = "SELECT group_id FROM org_group WHERE GROUPUSERSTRING LIKE '%$" + userId + "$%'";
            list = db.executeQueryToStrArr2(tempSql, 1);
            for (i = 0; i < list.length; i++)
              readerWhere = String.valueOf(readerWhere) + " or a.informationReaderGroup like '%@" + list[i][0] + "@%'"; 
          } 
          String rightWhere = "1>2";
          if ((new ChannelBD()).isChannelManager(po.getChannelId(), userId, orgId, orgIdString))
            rightWhere = (new ManagerService()).getRightFinalWhere(userId, orgId, "01*03*03", "a.informationIssueOrgId", "a.informationIssuerId"); 
          sql = String.valueOf(sql) + " and ((" + readerWhere + ")or (" + rightWhere + ")) and a.informationStatus=0 and a.domain_id=0";
        } 
        if (po.getStartTime() != null && !"".equals(po.getStartTime()))
          sql = String.valueOf(sql) + " and INFORMATIONMODIFYTIME>'" + po.getStartTime() + "'"; 
        if (po.getEndTime() != null && !"".equals(po.getEndTime()))
          sql = String.valueOf(sql) + " and INFORMATIONMODIFYTIME<'" + po.getEndTime() + "'"; 
        sql = String.valueOf(sql) + " order by INFORMATIONMODIFYTIME desc";
        String[][] objs = db.executeQueryToStrArr2(sql, 4);
        xmlStr.append("<message result=\"1\" reason=\"\"></message>");
        xmlStr.append("<datas>");
        if (objs != null && objs.length > 0) {
          String[] obj = (String[])null;
          for (int i = 0; i < objs.length; i++) {
            obj = objs[i];
            xmlStr.append("<data GGBH=\"" + obj[3] + "\" GGBT=\"" + obj[0] + "\" GGRQ=\"\" GGNR=\"" + obj[2] + "\"></data>");
          } 
        } 
        xmlStr.append("</datas>");
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return xmlStr.toString();
  }
  
  private DataInfoPO getPOFromXml(String xml) {
    System.out.println("参数信息：" + xml);
    DataInfoPO po = new DataInfoPO();
    SAXBuilder builder = new SAXBuilder();
    Document doc = null;
    try {
      doc = builder.build(new InputSource(new StringReader(xml)));
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
    Element dataInfo = doc.getRootElement();
    List<Element> fields = dataInfo.getChildren("Field");
    for (int i = 0; i < fields.size(); i++) {
      Element field = fields.get(i);
      String colName = field.getAttributeValue("ColName");
      if ("userId".equalsIgnoreCase(colName)) {
        po.setUserId(field.getAttributeValue("Value"));
      } else if ("num".equalsIgnoreCase(colName)) {
        po.setNum(field.getAttributeValue("Value"));
      } else if ("channelId".equalsIgnoreCase(colName)) {
        po.setChannelId(field.getAttributeValue("Value"));
      } else if ("dataType".equalsIgnoreCase(colName)) {
        po.setDataType(field.getAttributeValue("Value"));
      } else if ("startTime".equalsIgnoreCase(colName)) {
        po.setStartTime(field.getAttributeValue("Value"));
      } else if ("endTime".equalsIgnoreCase(colName)) {
        po.setEndTime(field.getAttributeValue("Value"));
      } 
    } 
    return po;
  }
  
  public String getUrlByInfoID(String dataType, String infoId, String userId) {
    if (dataType == null || "".equals(dataType) || infoId == null || "".equals(infoId) || userId == null || "".equals(userId)) {
      System.out.println("参数错误。。。");
      return "";
    } 
    String url = "";
    String sql = "";
    String[][] result = (String[][])null;
    DbOpt db = new DbOpt();
    try {
      if ("xtgg".equals(dataType) || "xxxw".equals(dataType)) {
        sql = "SELECT information_id,b.channel_id,channelname,channeltype,titlecolor,informationtype FROM oa_information a,oa_informationchannel b WHERE a.channel_id=b.channel_id AND information_id=" + 
          infoId;
        result = db.executeQueryToStrArr2(sql, 6);
        if (result != null && result.length > 0)
          url = "InformationAction.do?action=openInfo&channelId=" + result[0][1] + "&informationId=" + result[0][0] + 
            "&channelName=" + result[0][2] + "&userChannelName=公共信息&checkdepart=null&redHead=" + result[0][4] + 
            "&informationType=" + result[0][5] + "&channelType=" + result[0][3]; 
      } else if ("gzjh".equals(dataType)) {
        url = "eventAction.do?action=selectSingleEvent&eventId=" + infoId;
      } else if ("xtgz".equals(dataType)) {
        sql = "SELECT body_id,node_id,id,STATUS FROM CO_NODEMEMBER WHERE id=" + infoId;
        result = db.executeQueryToStrArr2(sql, 4);
        if (result != null && result.length > 0)
          url = "BodyAction.do?flag=toDealwith&bodyId=" + result[0][0] + "&nodeId=" + result[0][1] + 
            "&memberId=" + result[0][2] + "&status=" + result[0][3]; 
      } else if ("yfsx".equals(dataType) || "jsdb".equals(dataType)) {
        sql = "SELECT WF_WORK_ID,WORKMAINLINKFILE,WORKTYPE,WORKACTIVITY,WORKTABLE_ID,WORKRECORD_ID,WORKFILETYPE,WORKSUBMITPERSON,WF_SUBMITEMPLOYEE_ID,WORKCURSTEP,WORKSUBMITTIME,WORKPROCESS_ID,WORKSTEPCOUNT,ISSTANDFORWORK,STANDFORUSERID,STANDFORUSERNAME,INITACTIVITY,initActivityName,WORKSUBMITTIME,tranType,tranFromPersonId,processDeadlineDate,work_hangup,workstatus FROM jsf_work WHERE WF_WORK_ID=" + 


          
          infoId;
        result = db.executeQueryToStrArr2(sql, 24);
        if (result != null && result.length > 0)
          if ("1".equals(result[0][22])) {
            System.out.println("流程已挂起。。。");
          } else {
            url = String.valueOf(result[0][1]) + "&from=dealwith&workTitle=&activityName=" + result[0][17] + "&submitPersonId=" + result[0][8] + 
              "&submitPerson=" + result[0][7] + "&work=" + result[0][0] + "&workType=" + result[0][2] + "&activity=" + 
              result[0][3] + "&table=" + result[0][4] + "&record=" + result[0][5] + "&processName=" + result[0][6] + 
              "&workStatus=" + result[0][23] + "&submitTime=" + result[0][18] + "&processId=" + result[0][11] + 
              "&stepCount=" + result[0][12] + "&isStandForWork=" + result[0][13] + "&standForUserId=" + result[0][14] + 
              "&standForUserName=" + result[0][15] + "&initActivity=" + result[0][16] + "&initActivityName=" + result[0][17] + 
              "&submitPersonTime=" + result[0][10] + "&tranType=" + result[0][19] + "&tranFromPersonId=" + result[0][20] + 
              "&processDeadlineDate=" + result[0][21];
          }  
      } else if ("hysq".equals(dataType)) {
        sql = "SELECT a.BOARDROOMAPPLYID,b.Name,c.id FROM OA_BOARDROOMAPPLY a INNER JOIN OA_BOARDROOM b ON a.BOARDROOMID=b.BOARDROOMID INNER JOIN OA_BOARDROOM_MEETINGTIME c ON a.BOARDROOMAPPLYID=c.APPLYID WHERE ((a.EMCEE LIKE '%$" + 
          
          userId + 
          "$%' )OR(a.ATTENDEEEMPID LIKE '%" + userId + "%' )OR(a.ATTENDEELEADERID LIKE '%$" + 
          userId + "$%' )OR(a.NONVOTINGEMPID LIKE '%$" + userId + "$%' )OR(a.NOTEPERSONID LIKE '%$" + 
          userId + "$%' ))AND a.STATUS=0 AND a.DOMAIN_ID=0 AND c.STATUS=0 AND BOARDROOMAPPLYID=" + infoId;
        result = db.executeQueryToStrArr2(sql, 3);
        if (result != null && result.length > 0)
          url = "BoardRoomAction.do?action=selectBoardroomApplyView&boardroomApplyId=" + result[0][0] + 
            "&boardroomName=" + result[0][1] + "&type=view&meetingId=" + result[0][2] + "&executeStatus=false"; 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        db.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return url;
  }
}
