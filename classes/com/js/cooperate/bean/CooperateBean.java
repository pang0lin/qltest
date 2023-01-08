package com.js.cooperate.bean;

import com.js.cooperate.po.AttachPO;
import com.js.cooperate.po.NodeMemberPO;
import com.js.cooperate.po.NodeOpinionPO;
import com.js.cooperate.po.NodePO;
import com.js.cooperate.po.NodeRelPO;
import com.js.oa.info.util.InfoArchives;
import com.js.oa.search.client.SearchService;
import com.js.system.service.messages.MessagesBD;
import com.js.system.vo.messages.MessagesVO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CooperateBean extends HibernateBase {
  public boolean setNextNode(Long bodyId, Long memberId, int firstNode) {
    return setNextNode(bodyId, memberId, firstNode, 1);
  }
  
  public boolean setNextNode(Long bodyId, Long memberId, int firstNode, int sendSMS) {
    boolean result = false;
    DataSourceBase base = null;
    try {
      base = new DataSourceBase();
      base.begin();
      String sql = null;
      boolean exeNext = false;
      String dataType = SystemCommon.getDatabaseType();
      if ("mysql".equals(dataType)) {
        sql = "update co_nodemember set status=20,donetime=now() where id=" + memberId;
      } else if ("oracle".equals(dataType)) {
        sql = "update co_nodemember set status=20,donetime=sysdate where id=" + memberId;
      } else if (dataType.indexOf("sqlserver") >= 0) {
        sql = "update co_nodemember set status=20,donetime=getdate() where id=" + memberId;
      } 
      base.executeUpdate(sql);
      ResultSet rs = base.executeQuery("select node_id from co_nodemember where id=" + memberId);
      rs.next();
      Long curNodeId = Long.valueOf(rs.getLong("node_id"));
      rs.close();
      if (firstNode == 1) {
        sendWork(base, bodyId, curNodeId, memberId, 1);
      } else {
        sendWork(base, bodyId, curNodeId, memberId, 101);
      } 
      sql = "select count(id) from co_nodemember where status=10 and node_id=" + curNodeId;
      rs = base.executeQuery(sql);
      if (rs.next()) {
        int num = rs.getInt(1);
        rs.close();
        if (num == 0) {
          base.executeUpdate("update co_node set status=20 where id=" + curNodeId);
          exeNext = true;
        } 
      } else {
        rs.close();
      } 
      if (exeNext) {
        List<String> nextNodeIdList = new ArrayList();
        rs = base.executeQuery("select node_id from co_noderel where parent_node=" + curNodeId);
        while (rs.next())
          nextNodeIdList.add(rs.getString(1)); 
        rs.close();
        if (nextNodeIdList.size() > 0) {
          StringBuffer nodeIdBuffer = new StringBuffer("-2");
          int i = 0;
          for (i = 0; i < nextNodeIdList.size(); i++)
            nodeIdBuffer.append(",").append(nextNodeIdList.get(i)); 
          sql = "update co_node set status=10 where id in(" + nodeIdBuffer.toString() + ")";
          base.executeUpdate(sql);
          sql = "update co_nodemember set status=10 where node_id in(" + nodeIdBuffer.toString() + ")";
          base.executeUpdate(sql);
          sendNodeWork(base, bodyId, curNodeId, 0, nodeIdBuffer.toString(), sendSMS);
        } else {
          rs = base.executeQuery("select count(id) from co_node where status<>20 and body_id=" + bodyId);
          if (rs.next()) {
            int num = rs.getInt(1);
            rs.close();
            if (num == 0) {
              base.executeUpdate("update co_body set status=100 where id=" + bodyId);
              completeWork(base, bodyId);
            } 
          } else {
            rs.close();
          } 
        } 
      } 
      base.end();
    } catch (Exception ex) {
      if (base != null)
        base.end(); 
      ex.printStackTrace();
    } 
    return result;
  }
  
  public String saveOpinion(Long bodyId, Long nodeId, Long memberId) {
    return null;
  }
  
  public boolean dealwith(NodeOpinionPO nopo, String memberId, String tracker, String selPersonIds, String selPersonNames, String[] attachName, String[] attachSaveName, String nodestr) {
    return dealwith(nopo, memberId, tracker, selPersonIds, selPersonNames, attachName, attachSaveName, nodestr, 1);
  }
  
  public boolean dealwith(NodeOpinionPO nopo, String memberId, String tracker, String selPersonIds, String selPersonNames, String[] attachName, String[] attachSaveName, String nodestr, int sendSMS) {
    boolean res = false;
    try {
      Long opId = saveOpinion(nopo, memberId, tracker, attachName, attachSaveName, 1);
      if (selPersonIds != null && selPersonIds.length() > 0)
        appendNode(nopo.getBodyId(), memberId, selPersonIds, selPersonNames); 
      if (nodestr != null && !"".equals(nodestr))
        appendFlowNodes(nopo.getBodyId(), memberId, nodestr); 
      setNextNode(nopo.getBodyId(), Long.valueOf(memberId), 0, sendSMS);
      res = true;
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return res;
  }
  
  public String appendFlowNodes(Long bodyId, String memberId, String nodestr) {
    try {
      begin();
      Long newCurNodeId = Long.valueOf(0L);
      String newCurNodeNick = "";
      List newNodeIdList = new ArrayList();
      BodyBean bodyBean = new BodyBean();
      Long curNodeId = this.session.createQuery("select po.nodeId from com.js.cooperate.po.NodeMemberPO po where id=" + memberId).iterate().next();
      NodePO curNodePO = (NodePO)this.session.load(NodePO.class, curNodeId);
      if (nodestr != null && !"".equals(nodestr)) {
        String[][] nodes = bodyBean.strToNodeArray(nodestr);
        String sendMessStr = "";
        if (nodes.length > 0) {
          Map<Object, Object> nodeMap = new HashMap<Object, Object>();
          nodeMap.put("root", Long.valueOf(0L));
          String delnick = "";
          int i;
          for (i = 0; i < nodes.length; i++) {
            if (i == 0) {
              delnick = "'" + nodes[i][0] + "'";
            } else {
              delnick = String.valueOf(delnick) + ",'" + nodes[i][0] + "'";
            } 
          } 
          if (!"".equals(delnick)) {
            this.session.delete("from com.js.cooperate.po.NodePO po where po.bodyId =" + bodyId + " and po.nickName not in(" + delnick + ")");
            this.session.flush();
          } 
          for (i = 0; i < nodes.length; i++) {
            if (nodes[i] != null && nodes[i][0] != null) {
              String sql = "select po.id,po.parentNick from com.js.cooperate.po.NodePO po where po.bodyId=" + bodyId + " and po.nickName='" + nodes[i][0] + "'";
              List<Object[]> tempList = this.session.createQuery(sql).list();
              if (tempList != null && tempList.size() == 1) {
                Object[] obj = tempList.get(0);
                NodePO nodePO = (NodePO)this.session.load(NodePO.class, new Long(obj[0].toString()));
                nodePO.setParentNick(nodes[i][2]);
                this.session.flush();
                if ("root".equals(nodes[i][2])) {
                  nodePO.setNodeStart(Integer.valueOf(1));
                  String empId = nodes[i][0].substring(1);
                } else {
                  nodePO.setNodeStart(Integer.valueOf(0));
                  String empId = nodes[i][0].substring(1, nodes[i][0].indexOf("_"));
                } 
                Long nodeId = nodePO.getId();
                nodeMap.put(nodes[i][0], nodeId);
              } else {
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
                if ("".equals(sendMessStr)) {
                  sendMessStr = nodeId.toString();
                } else {
                  sendMessStr = String.valueOf(sendMessStr) + "," + nodeId.toString();
                } 
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
                  this.session.save(memberPO);
                  memberPO = null;
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
          } 
          NodeRelPO relPO = null;
          if (nodes.length > 0) {
            this.session.delete("from com.js.cooperate.po.NodeRelPO po where po.bodyId =" + bodyId);
            this.session.flush();
            for (int j = 0; j < nodes.length; j++) {
              relPO = new NodeRelPO();
              relPO.setBodyId(bodyId);
              relPO.setNodeId((Long)nodeMap.get(nodes[j][0]));
              relPO.setParentNode((Long)nodeMap.get(nodes[j][2]));
              this.session.save(relPO);
            } 
          } 
        } 
        this.session.flush();
        this.session.close();
      } 
      this.session.flush();
      this.session.close();
    } catch (Exception ex) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return null;
  }
  
  public Long saveOpinion(NodeOpinionPO po, String memberId, String tracker, String[] attachName, String[] attachSaveName, int opinionType) throws Exception {
    Long opId = null;
    try {
      begin();
      opId = (Long)this.session.save(po);
      if (attachName != null && attachName.length > 0)
        for (int i = 0; i < attachName.length; i++) {
          AttachPO apo = new AttachPO();
          apo.setBodyId(po.getBodyId());
          apo.setFileName(attachName[i]);
          apo.setSaveName(attachSaveName[i]);
          apo.setConId(opId);
          apo.setFileType(Integer.valueOf(3));
          this.session.save(apo);
        }  
      if (opinionType == 1) {
        if ("1".equals(tracker)) {
          NodeMemberPO memPO = (NodeMemberPO)this.session.load(NodeMemberPO.class, Long.valueOf(memberId));
          memPO.setTracker(Integer.valueOf(1));
        } 
        List<MessagesVO> msgList = new ArrayList();
        Long bodyId = po.getBodyId();
        List<Object[]> list = this.session.createQuery("select po.id,po.empId,po.nodeId,po.isPoster from com.js.cooperate.po.NodeMemberPO po where po.bodyId=" + bodyId + " and (po.isPoster=1 or po.tracker=1)").list();
        if (list != null && list.size() > 0) {
          String title = this.session.createQuery("select po.title from com.js.cooperate.po.BodyPO po where po.id=" + bodyId).iterate().next().toString();
          title = String.valueOf(po.getEmpName()) + "处理了协同工作:" + title;
          for (int i = 0; i < list.size(); i++) {
            Object[] obj = list.get(i);
            String postMemberId = obj[0].toString();
            String toUserId = obj[1].toString();
            String postNodeId = obj[2].toString();
            if (!toUserId.equals(po.getEmpId().toString())) {
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
      } 
      this.session.flush();
      this.session.close();
    } catch (Exception ex) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
      throw ex;
    } 
    return opId;
  }
  
  public Long saveOpinion(NodeOpinionPO po, String memberId, String tracker, String[] attachName, String[] attachSaveName, int opinionType, String reId, String userName, String userId) throws Exception {
    Long opId = null;
    try {
      begin();
      opId = (Long)this.session.save(po);
      if (attachName != null && attachName.length > 0)
        for (int i = 0; i < attachName.length; i++) {
          AttachPO apo = new AttachPO();
          apo.setBodyId(po.getBodyId());
          apo.setFileName(attachName[i]);
          apo.setSaveName(attachSaveName[i]);
          apo.setConId(opId);
          apo.setFileType(Integer.valueOf(3));
          this.session.save(apo);
        }  
      if (opinionType == 0) {
        List<MessagesVO> msgList = new ArrayList();
        Long bodyId = po.getBodyId();
        String empId = "", empName = "";
        List<Object[]> list_ = this.session.createQuery("select po.empName,po.empId from com.js.cooperate.po.NodeOpinionPO po where po.id=" + reId).list();
        if (list_ != null && list_.size() > 0) {
          Object[] obj_ = list_.get(0);
          empName = obj_[0].toString();
          empId = obj_[1].toString();
        } 
        List<Object[]> list = this.session.createQuery("select po.id,po.empId,po.nodeId,po.isPoster from com.js.cooperate.po.NodeMemberPO po where po.bodyId=" + bodyId + " and po.empId=" + empId).list();
        if (list != null && list.size() > 0) {
          String title = String.valueOf(userName) + "回复了您的办理意见";
          Object[] obj = list.get(0);
          String postMemberId = obj[0].toString();
          String toUserId = obj[1].toString();
          String postNodeId = obj[2].toString();
          if (!userId.equals(empId)) {
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
          (new MessagesBD()).messageArrayAdd(msgList);
        } 
      } 
      this.session.flush();
      this.session.close();
    } catch (Exception ex) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
      throw ex;
    } 
    return opId;
  }
  
  public String appendNode(Long bodyId, String memberId, String selIds, String selNames) {
    try {
      begin();
      Long newCurNodeId = Long.valueOf(0L);
      String newCurNodeNick = "";
      List<Long> newNodeIdList = new ArrayList();
      Long curNodeId = this.session.createQuery("select po.nodeId from com.js.cooperate.po.NodeMemberPO po where id=" + memberId).iterate().next();
      NodePO curNodePO = (NodePO)this.session.load(NodePO.class, curNodeId);
      String[][] nodes = nodestrToList(selIds, selNames);
      int i;
      for (i = 0; i < nodes.length; i++) {
        if (nodes[i] != null && nodes[i][0] != null) {
          NodePO nodePO = new NodePO();
          nodePO.setNodeRight("appendNode_");
          nodePO.setBodyId(bodyId);
          nodePO.setNodeTitle(nodes[i][1]);
          nodePO.setStatus(Integer.valueOf(-1));
          nodePO.setNodeStart(Integer.valueOf(1));
          nodePO.setParentNick(curNodePO.getNickName());
          newCurNodeNick = String.valueOf(Math.random());
          if (newCurNodeNick.length() > 8)
            newCurNodeNick = newCurNodeNick.substring(newCurNodeNick.length() - 8); 
          newCurNodeNick = String.valueOf(nodes[i][0]) + "_" + newCurNodeNick;
          nodePO.setNickName(newCurNodeNick);
          nodePO.setIsEnd(curNodePO.getIsEnd());
          nodePO.setNodeStart(Integer.valueOf(0));
          if ('P' == nodes[i][0].charAt(0)) {
            nodePO.setNodeType(Integer.valueOf(0));
          } else if ('D' == nodes[i][0].charAt(0)) {
            nodePO.setNodeType(Integer.valueOf(1));
          } else if ('G' == nodes[i][0].charAt(0)) {
            nodePO.setNodeType(Integer.valueOf(2));
          } 
          String empId = nodes[i][0].substring(1);
          newCurNodeId = (Long)this.session.save(nodePO);
          newNodeIdList.add(newCurNodeId);
          if ('P' == nodes[i][0].charAt(0)) {
            NodeMemberPO memberPO = new NodeMemberPO();
            memberPO.setBodyId(bodyId);
            memberPO.setNodeId(newCurNodeId);
            memberPO.setEmpId(Long.valueOf(empId));
            memberPO.setEmpName(nodes[i][1]);
            memberPO.setStatus(Integer.valueOf(0));
            memberPO.setIsPoster(Integer.valueOf(Integer.parseInt("0")));
            this.session.save(memberPO);
          } else if ('D' == nodes[i][0].charAt(0)) {
            List<Object[]> userList = this.session.createQuery("select po.empId,po.empName from com.js.system.vo.usermanager.EmployeeVO po join po.organizations org where po.userIsActive=1 and po.userIsDeleted=0 and po.userAccounts is not null and po.userIsFormalUser=1 and org.orgIdString like '%$" + empId + "$%'").list();
            if (userList != null)
              for (int k = 0; k < userList.size(); k++) {
                Object[] obj = userList.get(k);
                NodeMemberPO memberPO = new NodeMemberPO();
                memberPO.setBodyId(bodyId);
                memberPO.setNodeId(newCurNodeId);
                memberPO.setStatus(Integer.valueOf(0));
                memberPO.setIsPoster(nodePO.getNodeStart());
                memberPO.setEmpId(Long.valueOf(obj[0].toString()));
                memberPO.setEmpName(obj[1].toString());
                this.session.save(memberPO);
              }  
          } else if ('G' == nodes[i][0].charAt(0)) {
            List<Object[]> userList = this.session.createQuery("select po.empId,po.empName from com.js.system.vo.usermanager.EmployeeVO po join po.groups gro where gro.groupId=" + empId).list();
            if (userList != null)
              for (int k = 0; k < userList.size(); k++) {
                Object[] obj = userList.get(k);
                NodeMemberPO memberPO = new NodeMemberPO();
                memberPO.setBodyId(bodyId);
                memberPO.setNodeId(newCurNodeId);
                memberPO.setStatus(Integer.valueOf(0));
                memberPO.setIsPoster(nodePO.getNodeStart());
                memberPO.setEmpId(Long.valueOf(obj[0].toString()));
                memberPO.setEmpName(obj[1].toString());
                this.session.save(memberPO);
              }  
          } 
        } 
      } 
      if (newCurNodeId.longValue() != 0L) {
        List<NodeRelPO> relList = this.session.createQuery("select po from com.js.cooperate.po.NodeRelPO po where po.parentNode=" + curNodeId).list();
        if (relList != null && relList.size() > 0)
          for (i = 0; i < relList.size(); i++) {
            NodeRelPO po = relList.get(i);
            po.setParentNode(newCurNodeId);
          }  
        List<NodePO> nodeList = this.session.createQuery("select po from com.js.cooperate.po.NodePO po where po.bodyId=" + bodyId + " and po.parentNick='" + curNodePO.getNickName() + "'").list();
        if (nodeList != null && nodeList.size() > 0)
          for (i = 0; i < nodeList.size(); i++) {
            NodePO nodePO = nodeList.get(i);
            if (nodePO.getStatus().intValue() == -1) {
              nodePO.setStatus(Integer.valueOf(0));
            } else {
              nodePO.setParentNick(newCurNodeNick);
            } 
          }  
        NodeRelPO relPO = null;
        for (i = 0; i < newNodeIdList.size(); i++) {
          relPO = new NodeRelPO();
          relPO.setBodyId(bodyId);
          relPO.setNodeId(Long.valueOf(newNodeIdList.get(i).toString()));
          relPO.setParentNode(curNodePO.getId());
          this.session.save(relPO);
        } 
      } 
      this.session.flush();
      this.session.close();
    } catch (Exception ex) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return null;
  }
  
  public boolean delMember(String ids) {
    boolean res = false;
    try {
      begin();
      this.session.delete("from com.js.cooperate.po.NodeMemberPO po where po.id in(" + ids + ")");
      this.session.delete("from com.js.oa.jsflow.po.WorkVO vo where vo.");
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
  
  public boolean delMember(String userId, String ids, String status) {
    boolean res = false;
    Connection conn = null;
    try {
      String temp = "1";
      if ("201".equals(status)) {
        temp = "101";
      } else if ("202".equals(status)) {
        temp = "1";
      } else if ("1001".equals(status)) {
        temp = "101";
      } else if ("1002".equals(status)) {
        temp = "100";
      } 
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.executeUpdate("delete from jsf_work where WF_CUREMPLOYEE_ID=" + userId + " and WORKSTATUS=" + temp + " and WORKTABLE_ID=-1 and WORKRECORD_ID in(select distinct body_id from co_nodemember where id in(" + ids + "))");
      stmt.close();
      conn.close();
      begin();
      this.session.delete("from com.js.cooperate.po.NodeMemberPO po where po.empId=" + userId + " and po.id in(" + ids + ")");
      this.session.flush();
      this.session.close();
      res = true;
    } catch (Exception e) {
      try {
        this.session.close();
        if (conn != null)
          try {
            conn.close();
          } catch (Exception exception) {} 
      } catch (Exception err) {
        err.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return res;
  }
  
  public boolean sendWork(DataSourceBase base, Long bodyId, Long nodeId, Long memberId, int status) {
    boolean res = false;
    String databaseType = SystemCommon.getDatabaseType();
    try {
      String title = "", posterId = "0", posterName = "", orgName = "", projectId = "-1", sendUserId = "", sendUserName = "";
      int level = 0;
      Date postTime = new Date();
      Date deadTime = new Date();
      String hasTerm = "";
      StringBuffer sql = new StringBuffer();
      if (status == 1) {
        ResultSet rs = base.executeQuery("select title,posttime,posterid,postername,co_level,relproject_id,postorgname,term,hasterm from jsdb.co_body where id=" + bodyId);
        if (rs.next()) {
          title = rs.getString(1);
          postTime = rs.getDate(2);
          posterId = rs.getString(3);
          posterName = rs.getString(4);
          level = rs.getInt(5);
          projectId = rs.getString(6);
          orgName = rs.getString(7);
          deadTime = rs.getDate(8);
          hasTerm = rs.getString(9);
        } 
        rs.close();
        if (level == 10) {
          level = 0;
        } else if (level == 20) {
          level = 1;
        } else if (level == 30) {
          level = 3;
        } 
        rs = base.executeQuery("select emp_id,emp_name from co_nodemember where id=" + memberId);
        if (rs.next()) {
          sendUserId = rs.getString(1);
          sendUserName = rs.getString(2);
        } 
        rs.close();
        int workStartFlag = 0;
        if (status == 1)
          workStartFlag = 1; 
        String mailURL = "/jsoa/BodyAction.do?flag=toDealwith&bodyId=" + bodyId + "&nodeId=" + nodeId + "&memberId=" + memberId;
        sql.append("insert into JSDB.JSF_WORK (wf_work_id,wf_curEmployee_id,workStatus,workFileType,workCurStep,workTitle,")
          .append("workLeftLinkFile,workMainLinkFile, workListControl,workActivity, workSubmitPerson,workSubmitTime,")
          .append("wf_submitEmployee_id,workReadMarker,workType,workProcess_id,workTable_id,workRecord_id,workDeadLine,")
          .append("workPressTime,workCreateDate, workstartflag,")
          .append("workallowcancel,submitOrg,isSubProcWork,standForUserId,standForUserName,pareProcActiId,pareStepCount,")
          .append("pareTableId,pareRecordId,pareProcNextActiId,domain_id,emergence,transactType,relproject_id");
        if (hasTerm.equals("1")) {
          sql.append(",processdeadlinedate,workDeadlineDate) values (");
        } else {
          sql.append(") values (");
        } 
        sql.append(base.getTableId()).append(",").append(sendUserId).append(",").append(status).append(",'协同工作','协同','").append(title)
          .append("','','").append(mailURL).append("',1,").append(memberId).append(",'").append(posterName);
        if (databaseType.indexOf("mysql") >= 0) {
          sql.append("',now(),");
        } else if (databaseType.indexOf("oracle") >= 0) {
          sql.append("',sysdate,");
        } else {
          sql.append("',getdate(),");
        } 
        sql.append(posterId).append(",0,1,11,-1,").append(bodyId).append(",-1,")
          .append("-1,");
        if (databaseType.indexOf("mysql") >= 0) {
          sql.append("now(),");
        } else if (databaseType.indexOf("oracle") >= 0) {
          sql.append("sysdate,");
        } else {
          sql.append("getdate(),");
        } 
        sql.append(workStartFlag)
          .append(",0,'").append(orgName).append("',0,0,'',0,0,")
          .append("0,0,0,0,").append(level).append(",1,").append(projectId);
        if (hasTerm.equals("1")) {
          sql.append(",");
          if (databaseType.indexOf("mysql") >= 0) {
            sql.append("'" + deadTime + "','" + deadTime + "'");
          } else if (databaseType.indexOf("oracle") >= 0) {
            sql.append("to_date('" + deadTime + "','yyyy-mm-dd'),to_date('" + deadTime + "','yyyy-mm-dd')");
          } else {
            sql.append("'" + deadTime + "','" + deadTime + "'");
          } 
        } 
        sql.append(")");
        base.executeUpdate(sql.toString());
      } else if (status == 101) {
        long workId = 0L;
        title = "memberId=" + memberId;
        ResultSet rs = base.executeQuery("select wf_work_id from JSDB.JSF_WORK where workprocess_id=11 and workrecord_id=" + bodyId + " and workmainlinkfile like '%" + title + "'");
        if (rs.next())
          workId = rs.getLong(1); 
        rs.close();
        sql.append("update JSDB.JSF_WORK set workstatus=101 where wf_work_id=").append(workId);
        base.executeUpdate(sql.toString());
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return res;
  }
  
  public boolean sendNodeWork(DataSourceBase base, Long bodyId, Long nodeId, int status, String nextNodeIds) {
    return sendNodeWork(base, bodyId, nodeId, status, nextNodeIds, 1);
  }
  
  public boolean sendNodeWork(DataSourceBase base, Long bodyId, Long nodeId, int status, String nextNodeIds, int sendSMS) {
    boolean res = false;
    try {
      int userNum = 0;
      int i = 0;
      ResultSet rs = base.executeQuery("select count(id) from co_nodemember where node_id in(" + nextNodeIds + ")");
      if (rs.next())
        userNum = rs.getInt(1); 
      rs.close();
      String[][] users = new String[userNum][3];
      rs = base.executeQuery("select id,emp_id,emp_name from co_nodemember where node_id in(" + nextNodeIds + ")");
      while (rs.next()) {
        users[i][0] = rs.getString(1);
        users[i][1] = rs.getString(2);
        users[i][2] = rs.getString(3);
        i++;
      } 
      rs.close();
      String title = "", posterId = "0", posterName = "", orgName = "", projectId = "-1", sendUserId = "", sendUserName = "";
      int level = 0;
      Date postTime = new Date();
      Date deadTime = new Date();
      String hasTerm = "";
      rs = base.executeQuery("select title,posttime,posterid,postername,co_level,relproject_id,postorgname,term,hasterm from jsdb.co_body where id=" + bodyId);
      if (rs.next()) {
        title = rs.getString(1);
        postTime = rs.getDate(2);
        posterId = rs.getString(3);
        posterName = rs.getString(4);
        level = rs.getInt(5);
        projectId = rs.getString(6);
        orgName = rs.getString(7);
        deadTime = rs.getDate(8);
        hasTerm = rs.getString(9);
      } 
      rs.close();
      if (level == 10) {
        level = 0;
      } else if (level == 20) {
        level = 1;
      } else if (level == 30) {
        level = 3;
      } 
      String tempURL = "/jsoa/BodyAction.do?flag=toDealwith&bodyId=" + bodyId;
      MessagesBD messageBD = new MessagesBD();
      List<MessagesVO> msgList = new ArrayList();
      Date now = new Date();
      String databaseType = SystemCommon.getDatabaseType();
      for (i = 0; i < users.length; i++) {
        StringBuffer sql = new StringBuffer();
        String workId = base.getTableId();
        sql.append("insert into JSDB.JSF_WORK (wf_work_id,wf_curEmployee_id,workStatus,workFileType,workCurStep,workTitle,")
          .append("workLeftLinkFile,workMainLinkFile, workListControl,workActivity, workSubmitPerson,workSubmitTime,")
          .append("wf_submitEmployee_id,workReadMarker,workType,workProcess_id,workTable_id,workRecord_id,workDeadLine,")
          .append("workPressTime,workCreateDate, workstartflag,")
          .append("workallowcancel,submitOrg,isSubProcWork,standForUserId,standForUserName,pareProcActiId,pareStepCount,")
          .append("pareTableId,pareRecordId,pareProcNextActiId,domain_id,emergence,transactType,relproject_id");
        if (hasTerm.equals("1")) {
          sql.append(",processdeadlinedate,workDeadlineDate) values (");
        } else {
          sql.append(") values (");
        } 
        sql.append(workId).append(",").append(users[i][1]).append(",").append(status).append(",'协同工作','协同','").append(title)
          .append("','','").append(tempURL).append("&memberId=").append(users[i][0]).append("',1,").append(users[i][0]).append(",'").append(posterName);
        if (databaseType.indexOf("mysql") >= 0) {
          sql.append("',now(),");
        } else if (databaseType.indexOf("oracle") >= 0) {
          sql.append("',sysdate,");
        } else {
          sql.append("',getdate(),");
        } 
        sql.append(posterId).append(",0,1,11,-1,").append(bodyId).append(",-1,");
        if (databaseType.indexOf("mysql") >= 0) {
          sql.append("-1,now(),0");
        } else if (databaseType.indexOf("oracle") >= 0) {
          sql.append("-1,sysdate,0");
        } else {
          sql.append("-1,getdate(),0");
        } 
        sql.append(",0,'").append(orgName).append("',0,0,'',0,0,")
          .append("0,0,0,0,").append(level).append(",1,").append(projectId);
        if (hasTerm.equals("1")) {
          sql.append(",");
          if (databaseType.indexOf("mysql") >= 0) {
            sql.append("'" + deadTime + "','" + deadTime + "'");
          } else if (databaseType.indexOf("oracle") >= 0) {
            sql.append("to_date('" + deadTime + "','yyyy-mm-dd'),to_date('" + deadTime + "','yyyy-mm-dd')");
          } else {
            sql.append("'" + deadTime + "','" + deadTime + "'");
          } 
        } 
        sql.append(")");
        base.executeUpdate(sql.toString());
        MessagesVO msgVO = new MessagesVO();
        msgVO.setMessage_date_begin(now);
        msgVO.setMessage_date_end(new Date("2050/1/1"));
        msgVO.setMessage_send_UserId(Long.parseLong(posterId));
        msgVO.setMessage_send_UserName(posterName);
        msgVO.setMessage_show(1);
        msgVO.setMessage_status(1);
        msgVO.setMessage_time(now);
        msgVO.setMessage_title(title);
        msgVO.setMessage_toUserId(Long.parseLong(users[i][1]));
        msgVO.setMessage_type("Cooperate");
        msgVO.setData_id(bodyId.longValue());
        msgVO.setSendSMS(sendSMS);
        msgVO.setMessage_url("/jsoa/jsflow/item/jump_dealwith.jsp?status=100&workId=" + workId);
        msgList.add(msgVO);
      } 
      if (i > 0)
        messageBD.messageArrayAdd(msgList); 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return res;
  }
  
  public boolean completeWork(DataSourceBase base, Long bodyId) {
    boolean res = false;
    try {
      String workId = "0", title = "", toUserId = "0";
      StringBuffer sql = new StringBuffer();
      sql.append("select wf_work_id,worktitle,wf_curemployee_id from JSDB.jsf_work where workstartflag=1 and workprocess_id=11 and worktable_id=-1 and workrecord_id=")
        .append(bodyId);
      ResultSet rs = base.executeQuery(sql.toString());
      if (rs.next()) {
        workId = rs.getString(1);
        title = rs.getString(2);
        toUserId = rs.getString(3);
      } 
      rs.close();
      sql = new StringBuffer();
      sql.append("update JSDB.JSF_WORK set workstatus=100 where wf_work_id=").append(workId);
      base.addBatch(sql.toString());
      String databaseType = SystemCommon.getDatabaseType();
      sql = new StringBuffer();
      if (databaseType.indexOf("mysql") >= 0) {
        sql.append("update JSDB.JSF_WORK set workdonewithdate=now()");
      } else if (databaseType.indexOf("oracle") >= 0) {
        sql.append("update JSDB.JSF_WORK set workdonewithdate=sysdate");
      } else {
        sql.append("update JSDB.JSF_WORK set workdonewithdate=getdate()");
      } 
      sql.append(" where workprocess_id=11 and worktable_id=-1 and workrecord_id=").append(bodyId);
      base.addBatch(sql.toString());
      base.executeBatch();
      base.clearBatch();
      if (SystemCommon.getArchiveToInfo() == 1) {
        List<String> list = new ArrayList();
        String infoSql = "SELECT b.title,b.infoChannelId,b.posterName,b.posterId,o.org_id,o.orgName,o.orgIdString FROM co_body b JOIN org_organization_user e ON b.POSTERID=e.EMP_ID JOIN org_organization o ON e.ORG_ID=o.org_id WHERE b.ID=" + 
          bodyId;
        ResultSet rs1 = base.executeQuery(infoSql);
        if (rs1.next()) {
          list.add(rs1.getString(1));
          list.add(rs1.getString(2));
          list.add(rs1.getString(3));
          list.add(rs1.getString(4));
          list.add(rs1.getString(5));
          list.add(rs1.getString(6));
          list.add(rs1.getString(7));
          list.add(bodyId);
        } 
        rs1.close();
        if (!"0".equals(list.get(1).toString()) && list.get(1) != null && !"".equals(list.get(1).toString()) && !"null".equals(list.get(1).toString()))
          (new InfoArchives()).saveInfo(list, list.get(3).toString(), "COOP"); 
      } 
      res = true;
      SearchService.getInstance();
      String ifActiveUpdateDelete = SearchService.getIfActiveUpdateDelete();
      SearchService.getInstance();
      String isearchSwitch = SearchService.getiSearchSwitch();
      if ("1".equals(isearchSwitch) && workId != null && ifActiveUpdateDelete != null && !"".equals(workId) && !"".equals(ifActiveUpdateDelete) && !"no".equals(ifActiveUpdateDelete)) {
        SearchService.getInstance();
        SearchService.updateIndex(workId.toString(), "jsf_coordination");
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return res;
  }
  
  public String[][] nodestrToList(String selIds, String selNames) {
    String[][] nodes = (String[][])null;
    if (selNames != null) {
      String[] names = selNames.split(",");
      nodes = new String[names.length][2];
      int i = 0;
      while (selIds.length() > 0) {
        char flag = selIds.charAt(0);
        selIds = selIds.substring(1, selIds.length());
        String tempId = selIds.substring(0, selIds.indexOf(flag));
        selIds = selIds.substring(selIds.indexOf(flag) + 1);
        if (flag == '$') {
          tempId = "P" + tempId;
        } else if (flag == '*') {
          tempId = "D" + tempId;
        } else {
          tempId = "G" + tempId;
        } 
        nodes[i][0] = tempId;
        nodes[i][1] = names[i];
        i++;
      } 
    } 
    return nodes;
  }
  
  public boolean dealwithTurnToDo(NodeOpinionPO nopo, String memberId, String tracker, String selPersonIds, String selPersonNames, String[] attachName, String[] attachSaveName, String nodestr, int sendSMS, String turnToDoNodeId) {
    boolean res = false;
    NodePO turnToDoNode = null;
    try {
      begin();
      Long opId = (Long)this.session.save(nopo);
      if (attachName != null && attachName.length > 0)
        for (int i = 0; i < attachName.length; i++) {
          AttachPO apo = new AttachPO();
          apo.setBodyId(nopo.getBodyId());
          apo.setFileName(attachName[i]);
          apo.setSaveName(attachSaveName[i]);
          apo.setConId(opId);
          apo.setFileType(Integer.valueOf(3));
          this.session.save(apo);
        }  
      turnToDoNode = (NodePO)this.session.load(NodePO.class, Long.valueOf(turnToDoNodeId));
    } catch (Exception ex) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    DataSourceBase base = null;
    try {
      base = new DataSourceBase();
      base.begin();
      String selectSql = "select status from JSDB.co_body where id=" + nopo.getBodyId();
      ResultSet rs = base.executeQuery(selectSql);
      rs.next();
      int status = rs.getInt("status");
      rs.close();
      if (status == 100) {
        String updateSql1 = "update JSDB.co_body set status=10 where id=" + nopo.getBodyId();
        base.executeUpdate(updateSql1);
        String updateSql2 = "update JSDB.JSF_WORK set workstatus=1,workdonewithdate=null where workstartflag=1 and workprocess_id=11 and worktable_id=-1 and workrecord_id=" + nopo.getBodyId();
        base.executeUpdate(updateSql2);
      } 
      StringBuffer noDealPersonStr = new StringBuffer();
      String getNoDealPersonSql = "SELECT emp_id from co_nodemember where body_id=" + nopo.getBodyId() + " and status=10 and node_id in(select node_id from co_noderel where body_id=" + nopo.getBodyId() + " AND parent_node=" + turnToDoNode.getId() + " )";
      rs = base.executeQuery(getNoDealPersonSql);
      while (rs.next())
        noDealPersonStr.append(rs.getLong("emp_id")).append(","); 
      rs.close();
      appendNodeForTurnToDo(nopo.getBodyId(), selPersonIds, selPersonNames, turnToDoNode, noDealPersonStr.toString(), sendSMS);
    } catch (Exception e) {
      if (base != null)
        base.end(); 
      e.printStackTrace();
    } 
    return res;
  }
  
  public String appendNodeForTurnToDo(Long bodyId, String selIds, String selNames, NodePO curNodePO, String noDealPersonStr, int sendSMS) {
    try {
      begin();
      String newCurNodeNick = "";
      Long newCurNodeId = Long.valueOf(0L);
      List<Long> newNodeIdList = new ArrayList();
      String[][] nodes = nodestrToList(selIds, selNames);
      for (int i = 0; i < nodes.length; i++) {
        if (nodes[i] != null && nodes[i][0] != null) {
          NodePO nodePO = new NodePO();
          nodePO.setNodeRight("appendNode_");
          nodePO.setBodyId(bodyId);
          nodePO.setNodeTitle(nodes[i][1]);
          nodePO.setStatus(Integer.valueOf(10));
          nodePO.setNodeStart(Integer.valueOf(0));
          nodePO.setParentNick(curNodePO.getNickName());
          newCurNodeNick = String.valueOf(Math.random());
          if (newCurNodeNick.length() > 8)
            newCurNodeNick = newCurNodeNick.substring(newCurNodeNick.length() - 8); 
          newCurNodeNick = String.valueOf(nodes[i][0]) + "_" + newCurNodeNick;
          nodePO.setNickName(newCurNodeNick);
          nodePO.setIsEnd("yes");
          nodePO.setNodeStart(Integer.valueOf(0));
          if ('P' == nodes[i][0].charAt(0)) {
            nodePO.setNodeType(Integer.valueOf(0));
          } else if ('D' == nodes[i][0].charAt(0)) {
            nodePO.setNodeType(Integer.valueOf(1));
          } else if ('G' == nodes[i][0].charAt(0)) {
            nodePO.setNodeType(Integer.valueOf(2));
          } 
          String empId = nodes[i][0].substring(1);
          newCurNodeId = (Long)this.session.save(nodePO);
          newNodeIdList.add(newCurNodeId);
          if ('P' == nodes[i][0].charAt(0)) {
            NodeMemberPO memberPO = new NodeMemberPO();
            memberPO.setBodyId(bodyId);
            memberPO.setNodeId(newCurNodeId);
            memberPO.setEmpId(Long.valueOf(empId));
            memberPO.setEmpName(nodes[i][1]);
            memberPO.setStatus(Integer.valueOf(10));
            memberPO.setIsPoster(Integer.valueOf(Integer.parseInt("0")));
            if (noDealPersonStr.indexOf(empId) == -1) {
              this.session.save(memberPO);
            } else {
              System.out.println("协同因同节点下存在重复办理人" + memberPO.getEmpName() + "故不添加");
            } 
          } else if ('D' == nodes[i][0].charAt(0)) {
            List<Object[]> userList = this.session.createQuery("select po.empId,po.empName from com.js.system.vo.usermanager.EmployeeVO po join po.organizations org where po.userIsActive=1 and po.userIsDeleted=0 and po.userAccounts is not null and po.userIsFormalUser=1 and org.orgIdString like '%$" + empId + "$%'").list();
            if (userList != null)
              for (int k = 0; k < userList.size(); k++) {
                Object[] obj = userList.get(k);
                NodeMemberPO memberPO = new NodeMemberPO();
                memberPO.setBodyId(bodyId);
                memberPO.setNodeId(newCurNodeId);
                memberPO.setStatus(Integer.valueOf(10));
                memberPO.setIsPoster(nodePO.getNodeStart());
                memberPO.setEmpId(Long.valueOf(obj[0].toString()));
                memberPO.setEmpName(obj[1].toString());
                if (noDealPersonStr.indexOf(obj[0].toString()) == -1) {
                  this.session.save(memberPO);
                } else {
                  System.out.println("协同因同节点下存在重复办理人" + memberPO.getEmpName() + "故不添加");
                } 
              }  
          } else if ('G' == nodes[i][0].charAt(0)) {
            List<Object[]> userList = this.session.createQuery("select po.empId,po.empName from com.js.system.vo.usermanager.EmployeeVO po join po.groups gro where gro.groupId=" + empId).list();
            if (userList != null)
              for (int k = 0; k < userList.size(); k++) {
                Object[] obj = userList.get(k);
                NodeMemberPO memberPO = new NodeMemberPO();
                memberPO.setBodyId(bodyId);
                memberPO.setNodeId(newCurNodeId);
                memberPO.setStatus(Integer.valueOf(10));
                memberPO.setIsPoster(nodePO.getNodeStart());
                memberPO.setEmpId(Long.valueOf(obj[0].toString()));
                memberPO.setEmpName(obj[1].toString());
                if (noDealPersonStr.indexOf(obj[0].toString()) == -1) {
                  this.session.save(memberPO);
                } else {
                  System.out.println("协同因同节点下存在重复办理人" + memberPO.getEmpName() + "故不添加");
                } 
              }  
          } 
          List uList = this.session.createQuery("select po.empId,po.empName from com.js.cooperate.po.NodeMemberPO po where po.nodeId=" + newCurNodeId).list();
          if (uList == null || uList.size() == 0) {
            this.session.delete(nodePO);
            newNodeIdList.remove(newCurNodeId);
            System.out.println("协同因同节点下不存在办理人故不添加");
          } 
        } 
      } 
      StringBuffer nodeIdBuffer = new StringBuffer();
      if (newCurNodeId.longValue() != 0L) {
        NodeRelPO relPO = null;
        for (int j = 0; j < newNodeIdList.size(); j++) {
          relPO = new NodeRelPO();
          relPO.setBodyId(bodyId);
          relPO.setNodeId(Long.valueOf(newNodeIdList.get(j).toString()));
          relPO.setParentNode(curNodePO.getId());
          this.session.save(relPO);
          nodeIdBuffer.append(relPO.getNodeId()).append(",");
        } 
      } 
      this.session.flush();
      this.session.close();
      if (nodeIdBuffer.length() > 0)
        sendNodeWorkForTurnToDo(bodyId, nodeIdBuffer.toString(), 0, sendSMS); 
    } catch (Exception ex) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return null;
  }
  
  public boolean sendNodeWorkForTurnToDo(Long bodyId, String nodeIds, int status, int sendSMS) {
    boolean res = false;
    DataSourceBase base = null;
    try {
      base = new DataSourceBase();
      if (nodeIds.substring(nodeIds.length() - 1).equals(","))
        nodeIds = nodeIds.substring(0, nodeIds.length() - 1); 
      base.begin();
      int userNum = 0;
      ResultSet rs = base.executeQuery("select count(id) from co_nodemember where node_id in(" + nodeIds + ")");
      if (rs.next())
        userNum = rs.getInt(1); 
      rs.close();
      String[][] users = new String[userNum][3];
      rs = base.executeQuery("select id,emp_id,emp_name from co_nodemember where node_id in(" + nodeIds + ")");
      int j = 0;
      while (rs.next()) {
        users[j][0] = rs.getString(1);
        users[j][1] = rs.getString(2);
        users[j][2] = rs.getString(3);
        j++;
      } 
      rs.close();
      String title = "", posterId = "0", posterName = "", orgName = "", projectId = "-1", sendUserId = "", sendUserName = "";
      int level = 0;
      Date postTime = new Date();
      Date deadTime = new Date();
      String hasTerm = "";
      rs = base.executeQuery("select title,posttime,posterid,postername,co_level,relproject_id,postorgname,term,hasterm from jsdb.co_body where id=" + bodyId);
      if (rs.next()) {
        title = rs.getString(1);
        postTime = rs.getDate(2);
        posterId = rs.getString(3);
        posterName = rs.getString(4);
        level = rs.getInt(5);
        projectId = rs.getString(6);
        orgName = rs.getString(7);
        deadTime = rs.getDate(8);
        hasTerm = rs.getString(9);
      } 
      rs.close();
      if (level == 10) {
        level = 0;
      } else if (level == 20) {
        level = 1;
      } else if (level == 30) {
        level = 3;
      } 
      String tempURL = "/jsoa/BodyAction.do?flag=toDealwith&bodyId=" + bodyId;
      MessagesBD messageBD = new MessagesBD();
      List<MessagesVO> msgList = new ArrayList();
      Date now = new Date();
      String databaseType = SystemCommon.getDatabaseType();
      for (int i = 0; i < users.length; i++) {
        StringBuffer sql = new StringBuffer();
        String workId = base.getTableId();
        sql.append("insert into JSDB.JSF_WORK (wf_work_id,wf_curEmployee_id,workStatus,workFileType,workCurStep,workTitle,")
          .append("workLeftLinkFile,workMainLinkFile, workListControl,workActivity, workSubmitPerson,workSubmitTime,")
          .append("wf_submitEmployee_id,workReadMarker,workType,workProcess_id,workTable_id,workRecord_id,workDeadLine,")
          .append("workPressTime,workCreateDate, workstartflag,")
          .append("workallowcancel,submitOrg,isSubProcWork,standForUserId,standForUserName,pareProcActiId,pareStepCount,")
          .append("pareTableId,pareRecordId,pareProcNextActiId,domain_id,emergence,transactType,relproject_id");
        if (hasTerm.equals("1")) {
          sql.append(",processdeadlinedate,workDeadlineDate) values (");
        } else {
          sql.append(") values (");
        } 
        sql.append(workId).append(",").append(users[i][1]).append(",").append(status).append(",'协同工作','协同','").append(title)
          .append("','','").append(tempURL).append("&memberId=").append(users[i][0]).append("',1,").append(users[i][0]).append(",'").append(posterName);
        if (databaseType.indexOf("mysql") >= 0) {
          sql.append("',now(),");
        } else if (databaseType.indexOf("oracle") >= 0) {
          sql.append("',sysdate,");
        } else {
          sql.append("',getdate(),");
        } 
        sql.append(posterId).append(",0,1,11,-1,").append(bodyId).append(",-1,");
        if (databaseType.indexOf("mysql") >= 0) {
          sql.append("-1,now(),0");
        } else if (databaseType.indexOf("oracle") >= 0) {
          sql.append("-1,sysdate,0");
        } else {
          sql.append("-1,getdate(),0");
        } 
        sql.append(",0,'").append(orgName).append("',0,0,'',0,0,")
          .append("0,0,0,0,").append(level).append(",1,").append(projectId);
        if (hasTerm.equals("1")) {
          sql.append(",");
          if (databaseType.indexOf("mysql") >= 0) {
            sql.append("'" + deadTime + "','" + deadTime + "'");
          } else if (databaseType.indexOf("oracle") >= 0) {
            sql.append("to_date('" + deadTime + "','yyyy-mm-dd'),to_date('" + deadTime + "','yyyy-mm-dd')");
          } else {
            sql.append("'" + deadTime + "','" + deadTime + "'");
          } 
        } 
        sql.append(")");
        base.executeUpdate(sql.toString());
        MessagesVO msgVO = new MessagesVO();
        msgVO.setMessage_date_begin(now);
        msgVO.setMessage_date_end(new Date("2050/1/1"));
        msgVO.setMessage_send_UserId(Long.parseLong(posterId));
        msgVO.setMessage_send_UserName(posterName);
        msgVO.setMessage_show(1);
        msgVO.setMessage_status(1);
        msgVO.setMessage_time(now);
        msgVO.setMessage_title(title);
        msgVO.setMessage_toUserId(Long.parseLong(users[i][1]));
        msgVO.setMessage_type("Cooperate");
        msgVO.setData_id(bodyId.longValue());
        msgVO.setSendSMS(sendSMS);
        msgVO.setMessage_url("/jsoa/jsflow/item/jump_dealwith.jsp?status=100&workId=" + workId);
        msgList.add(msgVO);
      } 
      if (msgList.size() > 0)
        messageBD.messageArrayAdd(msgList); 
      base.end();
    } catch (Exception ex) {
      ex.printStackTrace();
      if (base != null)
        base.end(); 
    } 
    return res;
  }
  
  public Map<String, String> getCurrentNode(String bodyId) {
    Long bodyIdL = Long.valueOf(bodyId);
    Map<String, String> map = new LinkedHashMap<String, String>();
    DataSourceBase base = null;
    try {
      base = new DataSourceBase();
      base.begin();
      String selectSql = "select status from JSDB.co_body where id=" + bodyIdL;
      ResultSet rs = base.executeQuery(selectSql);
      rs.next();
      int status = rs.getInt("status");
      rs.close();
      StringBuffer sql = new StringBuffer();
      if (status == 100) {
        sql.append("SELECT DISTINCT N.node_id as NODE_ID,(select C.node_title from co_node C where C.id=N.node_id) as NODE_TITLE ");
        sql.append(" FROM (select node_id as NODE_ID from co_nodemember where body_id=" + bodyIdL + " and STATUS=20 and node_id not in ");
        sql.append(" (select parent_node from co_noderel where body_id=" + bodyIdL + ") ORDER BY donetime desc)N");
      } else {
        sql.append("SELECT DISTINCT N.node_id as NODE_ID,(select C.node_title from co_node C where C.id=N.node_id) as NODE_TITLE  FROM( ");
        sql.append("SELECT N1.node_id from ( ");
        sql.append("select node_id from co_nodemember where body_id=" + bodyIdL + " and STATUS=20 and node_id not in(select parent_node from co_noderel where body_id=" + bodyIdL + ") ORDER BY donetime desc)N1 ");
        sql.append(" UNION ALL ");
        sql.append("SELECT N2.node_id from ( ");
        sql.append("select node_id from co_nodemember where body_id=" + bodyIdL + " and node_id in( ");
        sql.append("SELECT DISTINCT PARENT_NODE FROM co_noderel WHERE NODE_ID IN( ");
        sql.append("SELECT NODE_ID FROM co_nodemember WHERE body_id=" + bodyIdL + " AND STATUS =10)) ORDER BY donetime desc )N2)N");
      } 
      ResultSet mapRs = base.executeQuery(sql.toString());
      while (mapRs.next())
        map.put((new StringBuilder(String.valueOf(mapRs.getLong("NODE_ID")))).toString(), mapRs.getString("NODE_TITLE")); 
      mapRs.close();
      base.end();
    } catch (Exception e) {
      if (base != null)
        base.end(); 
      e.printStackTrace();
    } 
    return map;
  }
}
