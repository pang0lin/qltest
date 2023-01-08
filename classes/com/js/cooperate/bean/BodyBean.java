package com.js.cooperate.bean;

import com.js.cooperate.po.AttachPO;
import com.js.cooperate.po.BodyExPO;
import com.js.cooperate.po.BodyPO;
import com.js.cooperate.po.NodeMemberPO;
import com.js.cooperate.po.NodePO;
import com.js.cooperate.po.NodeRelPO;
import com.js.oa.pressdeal.bean.PressDealDoEJBBean;
import com.js.oa.pressdeal.po.OaPersonoaPressPO;
import com.js.oa.pressdeal.po.OaPersonoaUserPressRelatioPO;
import com.js.system.service.messages.MessagesBD;
import com.js.system.service.messages.RemindUtil;
import com.js.system.vo.messages.MessagesVO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.InfoUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BodyBean extends HibernateBase {
  public Integer add(BodyPO po) throws Exception {
    int result = 0;
    begin();
    try {
      this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      result = 2;
      throw e;
    } finally {
      this.session.close();
    } 
    return new Integer(result);
  }
  
  public boolean send(BodyPO po, String nodestr, String userId, String[] attachName, String[] attachSaveName, int issend) {
    return send(po, nodestr, userId, attachName, attachSaveName, issend, 1);
  }
  
  public boolean send(BodyPO po, String nodestr, String userId, String[] attachName, String[] attachSaveName, int issend, int sendSMS) {
    boolean res = false;
    try {
      Long bodyId;
      begin();
      Date now = new Date();
      po.setPostTime(now);
      if (issend == 1) {
        po.setStatus(Integer.valueOf(10));
      } else {
        po.setStatus(Integer.valueOf(1));
      } 
      String dataBaseType = SystemCommon.getDatabaseType();
      if (po.getId() != null && po.getId().intValue() != 0) {
        bodyId = po.getId();
        BodyPO opo = (BodyPO)this.session.load(BodyPO.class, bodyId);
        opo.setTitle(po.getTitle());
        opo.setLevel(po.getLevel());
        opo.setHasTerm(po.getHasTerm());
        opo.setTerm(po.getTerm());
        opo.setStatus(po.getStatus());
        opo.setPosterId(po.getPosterId());
        opo.setPosterName(po.getPosterName());
        opo.setPostTime(po.getPostTime());
        opo.setSendToId(po.getSendToId());
        opo.setSendToName(po.getSendToName());
        opo.setRelProjectId(po.getRelProjectId());
        opo.setInfoChannelId(po.getInfoChannelId());
        String content = po.getContent();
        if (dataBaseType.indexOf("oracle") >= 0) {
          opo.setContent("");
        } else {
          opo.setContent(po.getContent());
        } 
        this.session.delete("from com.js.cooperate.po.NodeRelPO po where po.bodyId=" + bodyId);
        this.session.delete("from com.js.cooperate.po.NodeMemberPO po where po.bodyId=" + bodyId);
        this.session.delete("from com.js.cooperate.po.NodePO po where po.bodyId =" + bodyId);
        this.session.delete("from com.js.cooperate.po.AttachPO po where po.fileType=0 and po.conId=" + bodyId);
        this.session.flush();
        if (dataBaseType.indexOf("oracle") >= 0)
          InfoUtil.insert_oracle_clob("co_body", "content", "id", bodyId, content); 
      } else {
        String content = po.getContent();
        if (dataBaseType.indexOf("oracle") >= 0) {
          po.setContent("");
          bodyId = (Long)this.session.save(po);
        } else {
          bodyId = (Long)this.session.save(po);
        } 
        this.session.flush();
        if (dataBaseType.indexOf("oracle") >= 0)
          InfoUtil.insert_oracle_clob("co_body", "content", "id", bodyId, content); 
      } 
      Long curNodeId = Long.valueOf(0L);
      Long curMemberId = Long.valueOf(0L);
      if (attachName != null && attachName.length > 0)
        for (int i = 0; i < attachName.length; i++) {
          AttachPO apo = new AttachPO();
          apo.setBodyId(bodyId);
          apo.setFileName(attachName[i]);
          apo.setSaveName(attachSaveName[i]);
          apo.setConId(bodyId);
          apo.setFileType(Integer.valueOf(0));
          this.session.save(apo);
        }  
      if (nodestr != null && !"".equals(nodestr)) {
        String[][] nodes = strToNodeArray(nodestr);
        if (nodes.length > 0) {
          Map<Object, Object> nodeMap = new HashMap<Object, Object>();
          nodeMap.put("root", Long.valueOf(0L));
          for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null && nodes[i][0] != null) {
              String empId;
              NodePO nodePO = new NodePO();
              nodePO.setBodyId(bodyId);
              nodePO.setNodeTitle(nodes[i][1]);
              nodePO.setStatus(Integer.valueOf(0));
              nodePO.setNickName(nodes[i][0]);
              nodePO.setParentNick(nodes[i][2]);
              nodePO.setIsEnd(nodes[i][5]);
              nodePO.setNodeRight(nodes[i][6]);
              if ("root".equals(nodes[i][2])) {
                nodePO.setNodeStart(Integer.valueOf(1));
                empId = nodes[i][0].substring(1);
              } else {
                nodePO.setNodeStart(Integer.valueOf(0));
                empId = nodes[i][0].substring(1, nodes[i][0].indexOf("_"));
              } 
              if ('P' == nodes[i][0].charAt(0)) {
                nodePO.setNodeType(Integer.valueOf(0));
              } else if ('D' == nodes[i][0].charAt(0)) {
                nodePO.setNodeType(Integer.valueOf(1));
              } else if ('G' == nodes[i][0].charAt(0)) {
                nodePO.setNodeType(Integer.valueOf(2));
              } 
              nodePO.setChuan(nodes[i][7]);
              Long nodeId = (Long)this.session.save(nodePO);
              if ("root".equals(nodes[i][2]))
                curNodeId = nodeId; 
              nodeMap.put(nodes[i][0], nodeId);
              if ('P' == nodes[i][0].charAt(0)) {
                NodeMemberPO memberPO = new NodeMemberPO();
                memberPO.setBodyId(bodyId);
                memberPO.setNodeId(nodeId);
                memberPO.setEmpId(Long.valueOf(empId));
                memberPO.setEmpName(nodes[i][1]);
                memberPO.setStatus(Integer.valueOf(0));
                memberPO.setIsPoster(nodePO.getNodeStart());
                if ("root".equals(nodes[i][2])) {
                  memberPO.setHandTime(now);
                  curMemberId = (Long)this.session.save(memberPO);
                  memberPO = null;
                } else {
                  this.session.save(memberPO);
                  memberPO = null;
                } 
              } else if ('D' == nodes[i][0].charAt(0)) {
                List<Object[]> userList = this.session.createQuery("select po.empId,po.empName from com.js.system.vo.usermanager.EmployeeVO po join po.organizations org where po.userIsActive=1 and po.userIsDeleted=0 and po.userAccounts is not null and po.userIsFormalUser=1 and org.orgIdString like '%$" + empId + "$%'").list();
                if (userList != null)
                  for (int k = 0; k < userList.size(); k++) {
                    Object[] obj = userList.get(k);
                    NodeMemberPO memberPO = new NodeMemberPO();
                    memberPO.setBodyId(bodyId);
                    memberPO.setNodeId(nodeId);
                    memberPO.setStatus(Integer.valueOf(0));
                    memberPO.setIsPoster(nodePO.getNodeStart());
                    memberPO.setEmpId(Long.valueOf(obj[0].toString()));
                    memberPO.setEmpName(obj[1].toString());
                    this.session.save(memberPO);
                    memberPO = null;
                  }  
              } else if ('G' == nodes[i][0].charAt(0)) {
                List<Object[]> userList = this.session.createQuery("select po.empId,po.empName from com.js.system.vo.usermanager.EmployeeVO po join po.groups gro where gro.groupId=" + empId).list();
                if (userList != null)
                  for (int k = 0; k < userList.size(); k++) {
                    Object[] obj = userList.get(k);
                    NodeMemberPO memberPO = new NodeMemberPO();
                    memberPO.setBodyId(bodyId);
                    memberPO.setNodeId(nodeId);
                    memberPO.setStatus(Integer.valueOf(0));
                    memberPO.setIsPoster(nodePO.getNodeStart());
                    memberPO.setEmpId(Long.valueOf(obj[0].toString()));
                    memberPO.setEmpName(obj[1].toString());
                    this.session.save(memberPO);
                    memberPO = null;
                  }  
              } 
            } 
          } 
          NodeRelPO relPO = null;
          for (int j = 0; j < nodes.length; j++) {
            relPO = new NodeRelPO();
            relPO.setBodyId(bodyId);
            relPO.setNodeId((Long)nodeMap.get(nodes[j][0]));
            relPO.setParentNode((Long)nodeMap.get(nodes[j][2]));
            this.session.save(relPO);
          } 
        } 
        this.session.flush();
        this.session.close();
        if (issend == 1) {
          CooperateBean coBean = new CooperateBean();
          coBean.setNextNode(bodyId, curMemberId, 1, sendSMS);
        } 
      } else {
        this.session.flush();
        this.session.close();
      } 
      res = true;
    } catch (Exception ex) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return res;
  }
  
  public boolean delBody(String bodyIds, String userId) {
    boolean res = false;
    try {
      begin();
      List<E> bodyList = this.session.createQuery("select po.id from com.js.cooperate.po.BodyPO po where po.id in(" + bodyIds + ") and po.posterId=" + userId).list();
      if (bodyList != null && bodyList.size() > 0) {
        StringBuffer buf = new StringBuffer("-1");
        for (int i = 0; i < bodyList.size(); i++)
          buf.append(",").append(bodyList.get(i).toString()); 
        bodyIds = buf.toString();
        this.session.delete("from com.js.cooperate.po.NodeMemberPO po where po.bodyId in(" + bodyIds + ")");
        this.session.delete("from com.js.cooperate.po.NodeRelPO po where po.bodyId in(" + bodyIds + ")");
        this.session.delete("from com.js.cooperate.po.NodePO po where po.bodyId in(" + bodyIds + ")");
        this.session.delete("from com.js.cooperate.po.AttachPO po where  po.bodyId in(" + bodyIds + ")");
        this.session.delete("from com.js.cooperate.po.BodyPO po where po.id in(" + bodyIds + ")");
        this.session.flush();
      } 
      this.session.close();
      res = true;
    } catch (Exception e) {
      try {
        this.session.close();
      } catch (Exception err) {
        err.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return res;
  }
  
  public boolean appendBody(BodyExPO po, String[] attachName, String[] attachSaveName, String userId, String userName) {
    boolean res = false;
    try {
      begin();
      Long bodyId = po.getBodyId();
      Long appendId = (Long)this.session.save(po);
      if (attachName != null && attachName.length > 0)
        for (int i = 0; i < attachName.length; i++) {
          AttachPO apo = new AttachPO();
          apo.setBodyId(bodyId);
          apo.setFileName(attachName[i]);
          apo.setSaveName(attachSaveName[i]);
          apo.setConId(appendId);
          apo.setFileType(Integer.valueOf(1));
          this.session.save(apo);
        }  
      String userIds = "";
      List<MessagesVO> msgList = new ArrayList();
      List<String> title_list = this.session.createQuery("select po.title from com.js.cooperate.po.BodyPO po where po.id=" + bodyId).list();
      List<Object[]> list = this.session.createQuery("select po.id,po.empId,po.nodeId,po.isPoster from com.js.cooperate.po.NodeMemberPO po where po.bodyId=" + bodyId + " and (po.tracker=1 or po.status=20)").list();
      String title = String.valueOf(userName) + "补充了协同" + title_list.get(0);
      if (list != null && list.size() > 0) {
        for (int j = 0; j < list.size(); j++) {
          Object[] obj = list.get(j);
          String postMemberId = obj[0].toString();
          String toUserId = obj[1].toString();
          String postNodeId = obj[2].toString();
          if (!toUserId.equals(userId)) {
            Date now = new Date();
            MessagesVO msgVO = new MessagesVO();
            msgVO.setMessage_date_begin(now);
            msgVO.setMessage_date_end(new Date("2050/1/1"));
            msgVO.setMessage_send_UserId(0L);
            msgVO.setMessage_send_UserName("系统提醒");
            msgVO.setMessage_show(1);
            msgVO.setMessage_status(1);
            msgVO.setMessage_time(now);
            msgVO.setMessage_title(title);
            msgVO.setMessage_toUserId(Long.valueOf(toUserId).longValue());
            msgVO.setMessage_type("Cooperate");
            msgVO.setData_id(bodyId.longValue());
            msgVO.setMessage_url("/jsoa/cooperate/jump_body.jsp?isPoster=1&bodyId=" + bodyId + "&nodeId=" + postNodeId + "&memberId=" + postMemberId);
            msgList.add(msgVO);
          } 
        } 
        (new MessagesBD()).messageArrayAdd(msgList);
      } 
      this.session.flush();
      this.session.close();
      res = true;
    } catch (Exception e) {
      try {
        this.session.close();
      } catch (Exception err) {
        err.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return res;
  }
  
  public Map getBodyInfo(String bodyId, String memberId) {
    Map<Object, Object> map = new HashMap<Object, Object>();
    try {
      begin();
      BodyPO bpo = (BodyPO)this.session.load(BodyPO.class, Long.valueOf(bodyId));
      map.put("bodyInfo", bpo);
      List opinion = this.session.createQuery("select po.id,po.isHidden,po.empName,po.sendTime,po.content,po.empId from com.js.cooperate.po.NodeOpinionPO po where po.bodyId=" + bodyId + " and po.preId=0 order by po.id").list();
      map.put("opinion", opinion);
      List subOpinion = this.session.createQuery("select po.id,po.isHidden,po.empName,po.sendTime,po.content,po.preId,po.empId from com.js.cooperate.po.NodeOpinionPO po where po.bodyId=" + bodyId + " and po.preId<>0 order by po.preId,po.sendTime").list();
      map.put("subOpinion", subOpinion);
      List bodyEx = this.session.createQuery("select po.content,po.appendTime,po.empName from com.js.cooperate.po.BodyExPO po where po.bodyId=" + bodyId + " order by po.appendTime ").list();
      map.put("bodyEx", bodyEx);
      List bodyAttach = this.session.createQuery("select po.conId,po.fileName,po.saveName,po.fileType from com.js.cooperate.po.AttachPO po where po.bodyId=" + bodyId + " and po.fileType=0 order by po.id").list();
      map.put("bodyAttach", bodyAttach);
      List bodyExAttach = this.session.createQuery("select po.conId,po.fileName,po.saveName,po.fileType from com.js.cooperate.po.AttachPO po where po.bodyId=" + bodyId + " and po.fileType=1 order by po.id").list();
      map.put("bodyExAttach", bodyExAttach);
      List opinionAttach = this.session.createQuery("select po.conId,po.fileName,po.saveName,po.fileType from com.js.cooperate.po.AttachPO po where po.bodyId=" + bodyId + " and po.fileType=3 order by po.id").list();
      map.put("opinionAttach", opinionAttach);
      if (memberId != null) {
        String nodeId = "";
        Iterator<Object[]> it = this.session.createQuery("select po.status,po.nodeId from com.js.cooperate.po.NodeMemberPO po where po.id=" + memberId).iterate();
        if (it.hasNext()) {
          Object[] obj = it.next();
          map.put("dealwithStatus", obj[0]);
          map.put("nodeRight", this.session.createQuery("select po.nodeRight from com.js.cooperate.po.NodePO po where po.id=" + obj[1]).iterate().next());
        } 
      } 
      Long relProjectId = bpo.getRelProjectId();
      if (relProjectId.longValue() != -1L) {
        Iterator it = this.session.createQuery("select po.title from com.js.oa.relproject.po.RelProjectPO po where po.id=" + relProjectId).iterate();
        if (it != null && it.hasNext())
          map.put("relProjectName", it.next()); 
      } 
      this.session.close();
    } catch (Exception e) {
      try {
        this.session.close();
      } catch (Exception err) {
        err.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return map;
  }
  
  public Map getBodyInfoSimple(String bodyId) {
    Map<Object, Object> map = new HashMap<Object, Object>();
    try {
      begin();
      BodyPO bpo = (BodyPO)this.session.load(BodyPO.class, Long.valueOf(bodyId));
      map.put("bodyInfo", bpo);
      List bodyAttach = this.session.createQuery("select po.conId,po.fileName,po.saveName,po.fileType from com.js.cooperate.po.AttachPO po where po.bodyId=" + bodyId + " and po.fileType=0 order by po.id").list();
      map.put("bodyAttach", bodyAttach);
      List nodeList = this.session.createQuery("select po.id,po.nodeTitle,po.status,po.nickName,po.parentNick,po.isEnd,po.nodeRight from com.js.cooperate.po.NodePO po where po.bodyId=" + bodyId + " order by po.id").list();
      map.put("nodeList", nodeList);
      this.session.close();
    } catch (Exception e) {
      try {
        this.session.close();
      } catch (Exception err) {
        err.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return map;
  }
  
  public Map getBodyMainInfo(String bodyId) {
    Map<Object, Object> map = new HashMap<Object, Object>();
    try {
      begin();
      BodyPO bpo = (BodyPO)this.session.load(BodyPO.class, Long.valueOf(bodyId));
      map.put("bodyInfo", bpo);
      List bodyAttach = this.session.createQuery("select po.conId,po.fileName,po.saveName,po.fileType from com.js.cooperate.po.AttachPO po where po.bodyId=" + bodyId + " and po.fileType=0 order by po.id").list();
      map.put("bodyAttach", bodyAttach);
      this.session.close();
    } catch (Exception e) {
      try {
        this.session.close();
      } catch (Exception err) {
        err.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return map;
  }
  
  public String modifyContent(String bodyId, String content, String[] attachName, String[] attachSaveName, String userId, String userName) {
    String res = "-1";
    try {
      begin();
      String dataBaseType = SystemCommon.getDatabaseType();
      BodyPO po = (BodyPO)this.session.load(BodyPO.class, Long.valueOf(bodyId));
      if (dataBaseType.indexOf("oracle") >= 0) {
        po.setContent("");
      } else {
        po.setContent(content);
      } 
      this.session.delete("from com.js.cooperate.po.AttachPO po where po.fileType=0 and po.bodyId=" + bodyId);
      if (attachName != null && attachName.length > 0)
        for (int i = 0; i < attachName.length; i++) {
          AttachPO apo = new AttachPO();
          apo.setBodyId(Long.valueOf(bodyId));
          apo.setFileName(attachName[i]);
          apo.setSaveName(attachSaveName[i]);
          apo.setConId(Long.valueOf(bodyId));
          apo.setFileType(Integer.valueOf(0));
          this.session.save(apo);
        }  
      this.session.flush();
      if (dataBaseType.indexOf("oracle") >= 0)
        InfoUtil.insert_oracle_clob("co_body", "content", "id", Long.valueOf(bodyId), content); 
      String userIds = "";
      List<MessagesVO> msgList = new ArrayList();
      List<Object[]> list = this.session.createQuery("select po.id,po.empId,po.nodeId,po.isPoster from com.js.cooperate.po.NodeMemberPO po where po.bodyId=" + bodyId + " and (po.tracker=1 or po.status=20)").list();
      String title = String.valueOf(userName) + "修改了协同" + po.getTitle();
      if (list != null && list.size() > 0) {
        for (int j = 0; j < list.size(); j++) {
          Object[] obj = list.get(j);
          String postMemberId = obj[0].toString();
          String toUserId = obj[1].toString();
          String postNodeId = obj[2].toString();
          if (!toUserId.equals(userId)) {
            Date now = new Date();
            MessagesVO msgVO = new MessagesVO();
            msgVO.setMessage_date_begin(now);
            msgVO.setMessage_date_end(new Date("2050/1/1"));
            msgVO.setMessage_send_UserId(0L);
            msgVO.setMessage_send_UserName("系统提醒");
            msgVO.setMessage_show(1);
            msgVO.setMessage_status(1);
            msgVO.setMessage_time(now);
            msgVO.setMessage_title(title);
            msgVO.setMessage_toUserId(Long.valueOf(toUserId).longValue());
            msgVO.setMessage_type("Cooperate");
            msgVO.setData_id(Long.parseLong(bodyId));
            msgVO.setMessage_url("/jsoa/cooperate/jump_body.jsp?isPoster=1&bodyId=" + bodyId + "&nodeId=" + postNodeId + "&memberId=" + postMemberId);
            msgList.add(msgVO);
          } 
        } 
        (new MessagesBD()).messageArrayAdd(msgList);
      } 
      this.session.flush();
      this.session.close();
      res = "1";
    } catch (Exception e) {
      try {
        this.session.close();
      } catch (Exception err) {
        err.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return res;
  }
  
  public List getRecordList(String bodyId) {
    List list = null;
    try {
      begin();
      list = this.session.createQuery("select po.empId,po.empName,po.handTime,po.doneTime from com.js.cooperate.po.NodeMemberPO po where po.bodyId=" + bodyId + " and po.status>=10 and po.isPoster=0 order by po.handTime").list();
      this.session.close();
    } catch (Exception e) {
      try {
        this.session.close();
      } catch (Exception err) {
        err.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return list;
  }
  
  public List selectlike(String title) {
    List list = null;
    try {
      begin();
      list = this.session.createQuery("from co_body cb where title like %" + title + "%").list();
      this.session.flush();
      this.session.close();
    } catch (Exception e) {
      try {
        this.session.close();
      } catch (Exception err) {
        err.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return list;
  }
  
  public boolean updateRecord(String id) {
    boolean isSuc = false;
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select po.handTime from co_nodemember po where po.id=" + id);
      Date handTime = null;
      if (rs.next())
        handTime = rs.getTimestamp(1); 
      rs.close();
      if (handTime == null) {
        String dataType = SystemCommon.getDatabaseType();
        if ("mysql".equals(dataType)) {
          stmt.execute("update co_nodemember set handtime=now() where id=" + id);
        } else if ("oracle".equals(dataType)) {
          stmt.execute("update co_nodemember set handtime=sysdate where id=" + id);
        } else if ("sqlserver".equals(dataType)) {
          stmt.execute("update co_nodemember set handtime=getdate() where id=" + id);
        } 
        isSuc = true;
      } 
      stmt.close();
    } catch (SQLException e1) {
      e1.printStackTrace();
    } finally {
      try {
        if (conn != null)
          conn.close(); 
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return isSuc;
  }
  
  public String[][] strToNodeArray(String nodestr) {
    String[][] nodes = (String[][])null;
    String[] nodeArr = nodestr.split(";");
    nodes = new String[nodeArr.length][8];
    for (int i = 0; i < nodeArr.length; i++) {
      if (nodeArr[i] != null && !"".equals(nodeArr[i])) {
        String[] node = nodeArr[i].split(",");
        nodes[i][0] = node[0];
        nodes[i][1] = node[1];
        nodes[i][2] = node[2];
        nodes[i][3] = node[3];
        nodes[i][4] = node[4];
        nodes[i][5] = node[5];
        if (node.length > 6) {
          nodes[i][6] = node[6];
        } else {
          nodes[i][6] = "";
        } 
        if (node.length > 7) {
          nodes[i][7] = node[7];
        } else {
          nodes[i][7] = "";
        } 
      } 
    } 
    return nodes;
  }
  
  public void closeBodyById(String bodyId) {
    Date date = new Date();
    String currentTime = new String((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date));
    DataSourceBase dataSourceBase = new DataSourceBase();
    try {
      dataSourceBase.begin();
      String dbType = SystemCommon.getDatabaseType();
      String bodySql = "UPDATE co_body  SET STATUS=100 WHERE ID=" + bodyId;
      dataSourceBase.executeUpdate(bodySql);
      if (dbType.indexOf("oracle") >= 0 || dbType.indexOf("mssqlserver") >= 0) {
        workSql = "UPDATE jsf_work  SET WORKDONEWITHDATE= FN_STRTODATE('" + currentTime + "','') " + 
          "WHERE workrecord_id=" + bodyId + " AND workfiletype ='协同工作' and WORKSTATUS=101";
      } else {
        workSql = "UPDATE jsf_work  SET WORKDONEWITHDATE= '" + currentTime + "' " + 
          "WHERE workrecord_id=" + bodyId + " AND workfiletype ='协同工作' and WORKSTATUS=101";
      } 
      dataSourceBase.executeUpdate(workSql);
      if (dbType.indexOf("oracle") >= 0 || dbType.indexOf("mssqlserver") >= 0) {
        workSql = "UPDATE jsf_work  SET WORKSTATUS =101 ,WORKDONEWITHDATE= FN_STRTODATE('" + currentTime + "',''),DEALWITHTIME= FN_STRTODATE('" + currentTime + "','')  " + 
          "WHERE workrecord_id=" + bodyId + " AND workfiletype ='协同工作' and WORKSTATUS=0";
      } else {
        workSql = "UPDATE jsf_work  SET WORKSTATUS =101 ,WORKDONEWITHDATE= '" + currentTime + "',DEALWITHTIME= '" + currentTime + "'  " + 
          "WHERE workrecord_id=" + bodyId + " AND workfiletype ='协同工作' and WORKSTATUS=0";
      } 
      dataSourceBase.executeUpdate(workSql);
      if (dbType.indexOf("oracle") >= 0 || dbType.indexOf("mssqlserver") >= 0) {
        workSql = "UPDATE jsf_work  SET WORKSTATUS =100,WORKDONEWITHDATE= FN_STRTODATE('" + currentTime + "','') WHERE workrecord_id=" + bodyId + " " + 
          " AND workfiletype ='协同工作' and WORKSTATUS=1";
      } else {
        workSql = "UPDATE jsf_work  SET WORKSTATUS =100,WORKDONEWITHDATE= '" + currentTime + "' WHERE workrecord_id=" + bodyId + " " + 
          " AND workfiletype ='协同工作' and WORKSTATUS=1";
      } 
      dataSourceBase.executeUpdate(workSql);
      String workSql = "DELETE FROM sys_messages WHERE message_type='Cooperate' AND data_id = " + bodyId;
      dataSourceBase.executeSQL(workSql);
      workSql = "UPDATE co_node SET STATUS=20 WHERE BODY_ID = " + bodyId;
      dataSourceBase.executeUpdate(workSql);
      if (dbType.indexOf("oracle") >= 0 || dbType.indexOf("mssqlserver") >= 0) {
        workSql = "UPDATE co_nodemember SET HANDTIME = FN_STRTODATE('" + currentTime + "','') , DONETIME=FN_STRTODATE('" + currentTime + "','') , " + 
          " STATUS=20 WHERE BODY_ID = " + bodyId + " and STATUS !=20";
      } else {
        workSql = "UPDATE co_nodemember SET HANDTIME = '" + currentTime + "' , DONETIME='" + currentTime + "' , " + 
          " STATUS=20 WHERE BODY_ID = " + bodyId + " and STATUS !=20";
      } 
      dataSourceBase.executeUpdate(workSql);
      dataSourceBase.end();
    } catch (Exception e) {
      if (dataSourceBase != null)
        dataSourceBase.end(); 
    } 
  }
  
  public void addViewTimeForCooperate(String bodyId, String userId) {
    Connection conn = null;
    Statement stmt = null;
    String viewFlag = "";
    ResultSet rs = null;
    String sql = "";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select workvieweddate from jsf_work where WF_CUREMPLOYEE_ID=" + userId + " and WORKRECORD_ID=" + bodyId);
      while (rs.next())
        viewFlag = rs.getString("workvieweddate"); 
      if (viewFlag == null) {
        String cs = conn.getMetaData().getDatabaseProductName().toLowerCase();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date(System.currentTimeMillis()));
        if (cs.indexOf("mysql") != -1)
          sql = "update jsf_work set workvieweddate=\"" + time + "\" ,workreadmarker = 1 where WF_CUREMPLOYEE_ID=" + userId + " and WORKRECORD_ID=" + bodyId; 
        if (cs.indexOf("oracle") != -1)
          sql = "update jsf_work set workvieweddate=to_date('" + time + "','yyyy-mm-dd hh24:mi:ss') ,workreadmarker = 1 where WF_CUREMPLOYEE_ID=" + userId + " and WORKRECORD_ID=" + bodyId; 
        stmt.executeUpdate(sql);
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (rs != null)
        try {
          rs.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      if (stmt != null)
        try {
          stmt.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
  }
  
  public List<String[]> getdealwithempid(String bodyid) {
    String sql = "SELECT a.title,b.emp_id,b.emp_name,d.orgname FROM co_body a LEFT JOIN co_nodemember b ON a.id=b.body_id  LEFT JOIN org_organization_user c ON b.emp_id=c.emp_id  LEFT JOIN org_organization d ON c.org_id=d.org_id WHERE a.id=? AND b.status=10 AND b.isposter=0";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    DataSourceBase base = new DataSourceBase();
    List<String[]> list = (List)new ArrayList<String>();
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, bodyid);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        list.add(new String[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4) });
      } 
      pstmt.close();
      rs.close();
      conn.close();
    } catch (SQLException e) {
      try {
        if (pstmt != null)
          pstmt.close(); 
        if (rs != null)
          rs.close(); 
        if (conn != null)
          conn.close(); 
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return list;
  }
  
  public void sendPressMessge(HttpServletRequest request) {
    HttpSession Httpsession = request.getSession(true);
    String[] selPressArr = request.getParameterValues("selPress");
    String press_title = request.getParameter("pressTitle");
    String press_content = request.getParameter("pressContent");
    String domainId = Httpsession.getAttribute("domainId").toString();
    String bodyId = request.getParameter("bodyId");
    Object object1 = Httpsession.getAttribute("userName");
    String sendUserDep = Httpsession.getAttribute("orgName").toString();
    Object object2 = Httpsession.getAttribute("userId");
    PressDealDoEJBBean pdb = new PressDealDoEJBBean();
    OaPersonoaPressPO press = new OaPersonoaPressPO();
    try {
      String tmpdate = pdb.getDatabaseTime();
      String receiveNameStr = "";
      for (int i = 0; i < selPressArr.length; i++)
        receiveNameStr = String.valueOf(receiveNameStr) + selPressArr[i].substring(selPressArr[i].indexOf(",") + 1) + ","; 
      press.setCatorgry("协同工作");
      press.setContent(press_content);
      press.setDispatchTime(Timestamp.valueOf(tmpdate));
      press.setDomainId(new Long(domainId));
      press.setIsNew(new Short("0"));
      press.setReceiveUsernameStr(receiveNameStr);
      press.setSendUserDep(sendUserDep);
      press.setSendUsername((String)object1);
      press.setSubcatorgryName("协同工作");
      press.setTitle(press_title);
      press.setPressStatus(Byte.valueOf("0"));
      press.setWorkflowurl(bodyId);
      begin();
      Long pressID = (Long)this.session.save(press);
      OaPersonoaUserPressRelatioPO[] opupr = new OaPersonoaUserPressRelatioPO[selPressArr.length + 1];
      opupr[0] = new OaPersonoaUserPressRelatioPO();
      opupr[0].setDomainId(new Long(domainId));
      if ("0".equals(object2)) {
        opupr[0].setOrgId(new Long((String)object2));
        opupr[0].setOrgName(sendUserDep);
      } else {
        String[] strTmp = pdb.getOrgId_name((String)object2).split("-");
        opupr[0].setOrgId(new Long(strTmp[0]));
        opupr[0].setOrgName(strTmp[1]);
      } 
      opupr[0].setPressStatus(new Byte("0"));
      opupr[0].setUserId(new Long((String)object2));
      opupr[0].setUserName((String)object1);
      if (selPressArr != null && selPressArr.length > 0)
        for (int k = 0; k < selPressArr.length; k++) {
          String[] tup = selPressArr[k].split(",");
          String[] strTmp = pdb.getOrgId_name(tup[0]).split("-");
          opupr[k + 1] = new OaPersonoaUserPressRelatioPO();
          opupr[k + 1].setDomainId(new Long(domainId));
          opupr[k + 1].setOrgId(new Long(String.valueOf(strTmp[0])));
          opupr[k + 1].setOrgName(String.valueOf(strTmp[1]));
          opupr[k + 1].setPressStatus(new Byte("1"));
          opupr[k + 1].setUserId(new Long(String.valueOf(tup[0])));
          opupr[k + 1].setUserName(String.valueOf(tup[1]));
        }  
      for (int j = 0; j < opupr.length; j++) {
        opupr[j].setPressId(press.getPressId());
        this.session.save(opupr[j]);
      } 
      this.session.flush();
      this.session.close();
      String url = "/jsoa/pressManageAction.do?action=goto_body_press&pressId=" + pressID + "&actType=receivePress&remind=Y";
      if (selPressArr != null && selPressArr.length > 0)
        for (int k = 0; k < selPressArr.length; k++) {
          String touserid = selPressArr[k].substring(0, selPressArr[k].indexOf(","));
          Calendar tmp = Calendar.getInstance();
          tmp.set(2050, 12, 12);
          Date endDate = tmp.getTime();
          String title = press.getContent();
          RemindUtil.sendMessageToUsers(title, url, touserid, "Press", new Date(), endDate, (String)object1, pressID);
        }  
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
