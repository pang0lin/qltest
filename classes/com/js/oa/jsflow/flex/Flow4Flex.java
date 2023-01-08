package com.js.oa.jsflow.flex;

import com.js.oa.jsflow.po.WFActivityPO;
import com.js.oa.jsflow.service.ActivityBD;
import com.js.oa.jsflow.service.ModuleBD;
import com.js.oa.jsflow.vo.ModuleVO;
import com.js.oa.security.log.service.LogBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;

public class Flow4Flex extends DataSourceBase implements SessionBean {
  private static final long serialVersionUID = 1L;
  
  public void ejbActivate() throws EJBException, RemoteException {}
  
  public void ejbPassivate() throws EJBException, RemoteException {}
  
  public void ejbRemove() throws EJBException, RemoteException {}
  
  public void setSessionContext(SessionContext ctx) throws EJBException, RemoteException {}
  
  public String getFlowInfo(String flowID) throws Exception {
    String result = "";
    String fieldSplitStr = "!@#";
    String objectSplitStr = "$%^";
    String nodeLineSplitStr = "&*()";
    String nodeStr = "";
    String lineStr = "";
    begin();
    try {
      ResultSet rs = this.stmt.executeQuery("select * from jsf_activity where wf_workflowprocess_id=" + flowID);
      while (rs.next()) {
        nodeStr = String.valueOf(nodeStr) + objectSplitStr;
        nodeStr = String.valueOf(nodeStr) + rs.getString("wf_activity_id") + fieldSplitStr;
        String type = "";
        String name = rs.getString("activityname");
        if ("1".equals(rs.getString("activitybeginend"))) {
          type = NodeType.NODETYPE_STARTNODE;
          if (name == null || "".equals(name))
            name = "开始"; 
        } else if ("2".equals(rs.getString("activitybeginend"))) {
          type = NodeType.NODETYPE_ENDNODE;
          if (name == null || "".equals(name))
            name = "结束"; 
        } else if ("1".equals(rs.getString("activityclass"))) {
          type = NodeType.NODETYPE_WORKFLOWNODE;
        } else if ("2".equals(rs.getString("activityclass"))) {
          type = NodeType.NODETYPE_VIRTUALNODE;
        } else if ("0".equals(rs.getString("activityclass"))) {
          type = NodeType.NODETYPE_SUBWORKFLOWNODE;
        } else if ("3".equals(rs.getString("activityclass"))) {
          type = NodeType.NODETYPE_AUTOBACKNODEL;
        } 
        String x = rs.getString("positionleft");
        String y = rs.getString("positiontop");
        if (x == null)
          x = "0"; 
        if (y == null)
          y = "0"; 
        nodeStr = String.valueOf(nodeStr) + type + fieldSplitStr;
        nodeStr = String.valueOf(nodeStr) + name + fieldSplitStr;
        nodeStr = String.valueOf(nodeStr) + x + fieldSplitStr;
        nodeStr = String.valueOf(nodeStr) + y;
      } 
      if (nodeStr.startsWith(objectSplitStr))
        nodeStr = nodeStr.substring(objectSplitStr.length()); 
      rs = this.stmt.executeQuery("select * from jsf_transition where transitionfrom in(select wf_activity_id from jsf_activity where wf_workflowprocess_id=" + flowID + ")");
      while (rs.next()) {
        lineStr = String.valueOf(lineStr) + objectSplitStr;
        lineStr = String.valueOf(lineStr) + rs.getString("wf_transition_id") + fieldSplitStr;
        String name = rs.getString("transitiondescription");
        if (name == null)
          name = ""; 
        lineStr = String.valueOf(lineStr) + name + fieldSplitStr;
        lineStr = String.valueOf(lineStr) + rs.getString("transitionfrom") + fieldSplitStr;
        lineStr = String.valueOf(lineStr) + rs.getString("transitionto") + fieldSplitStr;
        lineStr = String.valueOf(lineStr) + rs.getString("linetype");
      } 
      if (lineStr.startsWith(objectSplitStr))
        lineStr = lineStr.substring(objectSplitStr.length()); 
      result = String.valueOf(nodeStr) + nodeLineSplitStr + lineStr;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return result;
  }
  
  public String addNode(String para, HttpServletRequest request) throws Exception {
    String result = "";
    String[] paras = para.split(";");
    String participantType = "9";
    String activityType = "1";
    String opType = "1";
    String flowID = paras[0];
    String activityName = paras[1];
    String activityClass = paras[2];
    if (NodeType.NODETYPE_ENDNODE.equals(activityClass)) {
      activityName = "结束";
    } else if (NodeType.NODETYPE_STARTNODE.equals(activityClass)) {
      activityName = "开始";
    } else if (NodeType.NODETYPE_VIRTUALNODE.equals(activityClass)) {
      activityName = "虚拟节点";
    } 
    if (NodeType.NODETYPE_WORKFLOWNODE.equals(activityClass)) {
      activityClass = "1";
      activityName = "流程节点";
    } else if (NodeType.NODETYPE_SUBWORKFLOWNODE.equals(activityClass)) {
      activityClass = "0";
      activityName = "子过程";
    } else if (NodeType.NODETYPE_AUTOBACKNODEL.equals(activityClass)) {
      activityClass = "3";
      activityName = "自动返回";
    } else {
      activityClass = "2";
      activityType = "0";
    } 
    String opContent = "新增“" + activityName + "“";
    String[] activityParameter = { 
        flowID, activityName, "", activityType, "1", 
        "0", "0", participantType, "", "", 
        "", "0", "0", "0", "", 
        "1", "1", "-1", "0", "-1", 
        "", "", "", "", "", 
        "", "", activityClass, "0", "0", 
        "update", "", "", "", "", 
        "back", "", "", 
        "cmdBackcmdWaitcmdFeedbackcmdPrint", "0", 
        "0", 
        "0", "3", "", "", "-1", 
        "0", "3", "", "" };
    String formID = "";
    begin();
    try {
      ResultSet rs = this.stmt.executeQuery("select accessdatabaseid from jsf_workflowprocess where wf_workflowprocess_id=" + flowID);
      if (rs.next())
        formID = rs.getString("accessdatabaseid"); 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    ActivityBD bd = new ActivityBD();
    Long id = bd.addWithoutCondition(activityParameter, null, null, null, formID);
    begin();
    try {
      this.stmt.execute("update jsf_activity   set positiontop=" + 
          paras[4] + "," + 
          "       positionleft=" + paras[3] + "," + 
          "nicknum=(select max(nicknum)+1 " + 
          "           from (select nicknum from jsf_activity " + 
          "                   where wf_workflowprocess_id=" + flowID + 
          "\t\t          union all " + 
          "                 select nicknum from jsf_transition " + 
          "                   where transitionfrom in(select wf_activity_id from jsf_activity where wf_workflowprocess_id=" + flowID + ")" + 
          "                ) aaa" + 
          "\t\t )" + 
          " where wf_activity_id=" + id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    log(request, opType, opContent, flowID);
    result = id + ";" + activityName + ";" + paras[2] + ";" + paras[3] + ";" + paras[4];
    return result;
  }
  
  public String deleteNode(String para, HttpServletRequest request) throws Exception {
    String opType = "3";
    String result = "";
    String flowID = para.split(",")[0];
    String id = para.split(",")[1];
    String opContent = "删除Id为【" + id + "】的节点";
    begin();
    try {
      String activityBegin = "0";
      ResultSet rs = this.stmt.executeQuery("select activitybeginend from jsf_activity where wf_activity_id=" + id);
      if (rs.next())
        activityBegin = rs.getString(1); 
      rs.close();
      if (!"1".equals(activityBegin) && !"2".equals(activityBegin))
        this.stmt.executeUpdate("delete from jsf_activity where wf_activity_id=" + id); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    log(request, opType, opContent, flowID);
    return result;
  }
  
  public String deleteLine(String para, HttpServletRequest request) throws Exception {
    String opType = "3";
    String result = "";
    String flowID = para.split(",")[0];
    String id = para.split(",")[1];
    String opContent = "删除Id为【" + id + "】的连线";
    begin();
    try {
      this.stmt.executeUpdate("delete from jsf_transition where wf_transition_id=" + id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    log(request, opType, opContent, flowID);
    return result;
  }
  
  public String getNodeNameByID(String id) throws Exception {
    String result = "";
    begin();
    try {
      ResultSet rs = this.stmt.executeQuery("select * from jsf_activity where wf_activity_id=" + id);
      if (rs.next())
        if ("1".equals(rs.getString("activitybeginend"))) {
          result = "开始";
        } else if ("2".equals(rs.getString("activitybeginend"))) {
          result = "结束";
        } else {
          result = rs.getString("activityname");
        }  
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return result;
  }
  
  private String updateNodePosition(String para) throws Exception {
    if (para.startsWith("obj"))
      para = para.substring(1); 
    String[] nodes = para.split("obj");
    try {
      for (int i = 0; i < nodes.length; i++) {
        String[] node = nodes[i].split(",");
        this.stmt.executeUpdate("update jsf_activity set positionleft=" + node[1] + ",positiontop=" + node[2] + " where wf_activity_id=" + node[0]);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } 
    return "";
  }
  
  public String addLine(String para, HttpServletRequest request) throws Exception {
    String result = "";
    String[] line = para.split(";");
    String from = line[0];
    String to = line[1];
    String flowID = line[2];
    String opType = "1";
    String lineType = line[3];
    ActivityBD bd = new ActivityBD();
    Long transId = Long.valueOf(0L);
    WFActivityPO fromActivityPO = bd.getActivityInfo(from);
    String opContent = "";
    if ("0".equals(lineType)) {
      opContent = "新增从节点" + from + "到节点" + to + "路径(直线)";
    } else {
      opContent = "新增从节点" + from + "到节点" + to + "路径(折线)";
    } 
    begin();
    try {
      if ("1".equals(Integer.valueOf(fromActivityPO.getActivityBeginEnd()))) {
        transId = bd.setStartActivity(to, from);
      } else {
        this.stmt.executeUpdate("update jsf_transition set defaultactivity=0 where transitionfrom=" + from);
        transId = bd.setSingelRelation(from, to, "-1", ">", "", "", "1");
      } 
      this.stmt.executeUpdate("update jsf_transition set linetype=" + 
          lineType + "," + 
          "nicknum=(select max(nicknum)+1 " + 
          "           from (select nicknum from jsf_activity " + 
          "                   where wf_workflowprocess_id=" + flowID + 
          "\t\t          union all " + 
          "                 select nicknum from jsf_transition " + 
          "                   where transitionfrom in(select wf_activity_id from jsf_activity where wf_workflowprocess_id=" + flowID + ")" + 
          "                ) aaa" + 
          "\t\t )" + 
          " where wf_transition_id=" + transId.toString());
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    log(request, opType, opContent, flowID);
    result = transId + ";" + from + ";" + to + ";" + lineType;
    return result;
  }
  
  public String saveNodeAndLine(String para) throws Exception {
    String flowid = para.split(";")[0];
    String nodeIDList = para.split(";")[1];
    if (nodeIDList.startsWith(","))
      nodeIDList = nodeIDList.substring(1); 
    String lineIDList = para.split(";")[2];
    if (lineIDList.startsWith(","))
      lineIDList = lineIDList.substring(1); 
    String nodePosition = para.split(";")[3];
    begin();
    try {
      this.stmt.executeUpdate("delete from jsf_activity  where wf_activity_ID not in(" + nodeIDList + ") and wf_workflowprocess_id = " + flowid);
      if (!"".equals(lineIDList)) {
        this.stmt.executeUpdate("delete from jsf_transition where transitionfrom in(select wf_activity_id from jsf_activity where wf_workflowprocess_id=" + flowid + ") and wf_transition_id not in (" + lineIDList + ")");
      } else {
        this.stmt.executeUpdate("delete from jsf_transition where transitionfrom in(select wf_activity_id from jsf_activity where wf_workflowprocess_id=" + flowid + ")");
      } 
      updateNodePosition(nodePosition);
      if ("1".equals(SystemCommon.getWorkflowUpdateSyncToInstance()))
        syncFlowInstance(flowid); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return "";
  }
  
  public String getModifyNodeURL(String para) throws Exception {
    String result = "";
    String[] p = para.split(";");
    String baseURL = p[0].substring(p[0].indexOf("/jsoa"));
    String flowID = p[1];
    String activityid = p[2];
    String formID = "";
    String moduleId = "1";
    String isStart = "0";
    String startNodeName = "";
    begin();
    try {
      ResultSet rs = this.stmt.executeQuery("select accessdatabaseid from jsf_workflowprocess where wf_workflowprocess_id=" + flowID);
      if (rs.next())
        formID = rs.getString("accessdatabaseid"); 
      rs = this.stmt.executeQuery("select b.wf_module_id from jsf_workflowprocess a,JSF_PACKAGE b where a.wf_package_id = b.wf_package_id and a.wf_workflowprocess_id=" + flowID);
      if (rs.next())
        moduleId = rs.getString("wf_module_id"); 
      rs = this.stmt.executeQuery("select * from jsf_activity where wf_activity_id='" + activityid + "'");
      if (rs.next() && 
        "1".equals(rs.getString("activitybeginend"))) {
        isStart = "1";
        startNodeName = rs.getString("activityname");
      } 
      if (startNodeName == null || "null".equalsIgnoreCase(startNodeName))
        startNodeName = "开始"; 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    if ("1".equals(isStart)) {
      result = String.valueOf(baseURL) + "/ActivityAction.do?action=modifyfirst&activityId=" + activityid + "&processId=" + flowID + "&tableId=" + formID + "&moduleId=" + moduleId + "&nodeId=node_" + activityid + "&activityName=" + startNodeName;
    } else {
      result = String.valueOf(baseURL) + "/ActivityAction.do?action=modify&activityId=" + activityid + "&processId=" + flowID + "&tableId=" + formID + "&moduleId=" + moduleId + "&nodeId=node_" + activityid;
    } 
    return result;
  }
  
  public String getModifyLineURL(String para) throws Exception {
    String result = "";
    String[] p = para.split(";");
    String baseURL = p[0];
    String flowID = p[1];
    String transId = p[2];
    String from = p[3];
    String to = p[4];
    String fromClass = p[5];
    String fromType = "1";
    String fromName = "";
    String toName = "";
    String formID = "";
    String moduleId = "1";
    begin();
    try {
      ResultSet rs = this.stmt.executeQuery("select accessdatabaseid from jsf_workflowprocess where wf_workflowprocess_id=" + flowID);
      if (rs.next())
        formID = rs.getString("accessdatabaseid"); 
      rs = this.stmt.executeQuery("select b.wf_module_id from jsf_workflowprocess a,JSF_PACKAGE b where a.wf_package_id = b.wf_package_id and a.wf_workflowprocess_id=" + flowID);
      if (rs.next())
        moduleId = rs.getString("wf_module_id"); 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    fromName = getNodeNameByID(from);
    toName = getNodeNameByID(to);
    if (NodeType.NODETYPE_WORKFLOWNODE.equals(fromClass)) {
      fromClass = "1";
    } else if (NodeType.NODETYPE_SUBWORKFLOWNODE.equals(fromClass)) {
      fromClass = "0";
    } else if (NodeType.NODETYPE_AUTOBACKNODEL.equals(fromClass)) {
      fromClass = "3";
    } else {
      fromClass = "2";
      fromType = "0";
    } 
    result = String.valueOf(baseURL) + "/jsflow/jsflow_relation.jsp?transId=" + transId + "&processId=" + flowID + "&tableId=" + formID + "&moduleId=" + moduleId + "&from=" + from + 
      "&to=" + to + "&fromClass=" + fromClass + "&fromType=" + fromType + "&fromName=" + fromName + "&toName=" + toName;
    return result;
  }
  
  public String getFlowRightInfo(String para, HttpServletRequest httpServletRequest) throws Exception {
    String result = "";
    String flowID = para.trim();
    String sucess = "sucess;";
    String failure = "failure;";
    String userId = httpServletRequest.getSession().getAttribute("userId").toString();
    String orgId = httpServletRequest.getSession().getAttribute("orgId").toString();
    String process_Status = "";
    try {
      Long.valueOf(flowID);
    } catch (Exception e) {
      result = String.valueOf(failure) + "该流程不存在，请核实！";
      return result;
    } 
    begin();
    try {
      ResultSet rs = this.stmt.executeQuery("select process_Status from jsf_workflowprocess where wf_workflowprocess_id=" + flowID);
      if (!rs.next()) {
        result = String.valueOf(failure) + "该流程不存在，请核实！";
        return result;
      } 
      process_Status = rs.getString("process_Status");
      String moduleId = "1";
      rs = this.stmt.executeQuery("select b.wf_module_id from jsf_workflowprocess a,JSF_PACKAGE b where a.wf_package_id = b.wf_package_id and a.wf_workflowprocess_id=" + flowID);
      if (rs.next())
        moduleId = rs.getString("wf_module_id"); 
      ModuleBD moduleBD = new ModuleBD();
      ModuleVO moduleVO = moduleBD.getModule(Integer.valueOf(moduleId).intValue());
      ManagerService managerBD = new ManagerService();
      String whereSql = "";
      if (moduleVO.isProcRight()) {
        String tmpRightCode = (moduleVO.getProcRightType().length() == 8) ? moduleVO.getProcRightType() : (String.valueOf(moduleVO.getProcRightType()) + "*02");
        if ("1".equals(moduleId) || "2".equals(moduleId) || "3".equals(moduleId)) {
          boolean hasRightWeihu = managerBD.hasRight(userId, tmpRightCode);
          boolean hasRightLiulan = false;
          if (!hasRightWeihu) {
            hasRightLiulan = managerBD.hasRight(userId, "02*02*04");
            if (hasRightLiulan)
              tmpRightCode = "02*02*04"; 
          } else {
            httpServletRequest.setAttribute("hasRightWeihu", "1");
          } 
        } 
        String where = managerBD.getRightFinalWhere(userId, orgId, 
            tmpRightCode, 
            "aaa.createdOrg", "aaa.createdEmp");
        whereSql = " where ((" + where + ") or (aaa.createdEmp = " + userId + ") or (aaa.createdEmp is null)) ";
      } else {
        whereSql = " where 1=1 ";
      } 
      whereSql = String.valueOf(whereSql) + " and aaa.wf_workflowprocess_id=" + flowID;
      rs = this.stmt.executeQuery("select * from jsf_workflowprocess aaa " + whereSql);
      if (!rs.next()) {
        result = String.valueOf(failure) + "您无权对该流程进行操作！";
        return result;
      } 
      result = String.valueOf(sucess) + process_Status;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    end();
    return result;
  }
  
  private void log(HttpServletRequest request, String opType, String opContent, String flowID) throws Exception {
    LogBD logBD = new LogBD();
    String moduleId = "1";
    String userId = request.getSession().getAttribute("userId").toString();
    String userName = request.getSession().getAttribute("userName").toString();
    String orgName = request.getSession().getAttribute("orgName").toString();
    String moduleCode = "";
    String moduleName = "";
    String Ip = request.getRemoteAddr();
    begin();
    try {
      ResultSet rs = this.stmt.executeQuery("select b.wf_module_id from jsf_workflowprocess a,JSF_PACKAGE b where a.wf_package_id = b.wf_package_id and a.wf_workflowprocess_id=" + flowID);
      if (rs.next())
        moduleId = rs.getString("wf_module_id"); 
      switch (Integer.valueOf(moduleId).intValue()) {
        case 2:
          moduleCode = "oa_gw_fw";
          moduleName = "发文管理";
          break;
        case 3:
          moduleCode = "oa_gw_fw";
          moduleName = "收文管理";
          break;
        default:
          moduleCode = "oa_workflow_set";
          moduleName = "流程设置";
          break;
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    logBD.log(userId, userName, orgName, moduleCode, moduleName, new Date(), new Date(), opType, opContent, Ip, "0");
  }
  
  private boolean syncFlowInstance(String flowId) {
    boolean result = false;
    Connection exeConn = null;
    try {
      exeConn = (new DataSourceBase()).getDataSource().getConnection();
      Statement exeStmt = exeConn.createStatement();
      List<String[]> instanceList = (List)new ArrayList<String>();
      String sql = "select workrecord_id,worktable_id,wf_submitemployee_id from jsf_work where workstatus=1 and workprocess_id=?";
      PreparedStatement pstmt = this.conn.prepareStatement(sql);
      pstmt.setString(1, flowId);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        instanceList.add(new String[] { rs.getString(1), rs.getString(2), rs.getString(3) });
      } 
      rs.close();
      pstmt.close();
      for (String[] instanceArr : instanceList) {
        StringBuffer idBuffer = new StringBuffer(1024);
        pstmt = this.conn.prepareStatement("select wf_proceedactivity_id from jsf_p_activity where wf_workflowprocess_id=? and trecord_id=?");
        pstmt.setString(1, flowId);
        pstmt.setString(2, instanceArr[0]);
        rs = pstmt.executeQuery();
        while (rs.next())
          idBuffer.append(rs.getString(1)).append(","); 
        rs.close();
        idBuffer.append("-1");
        this.stmt.clearBatch();
        sql = "delete from jsf_p_tr where wf_proceedtransition_id in(select wf_proceedtransition_id from jsf_p_transition where wf_proceedactivity_id in (" + idBuffer.toString() + ") )";
        this.stmt.addBatch(sql);
        sql = "delete from jsf_p_transition where wf_proceedactivity_id in (" + idBuffer.toString() + ")";
        this.stmt.addBatch(sql);
        sql = "delete from jsf_p_press where wf_proceedactivity_id in (" + idBuffer.toString() + ")";
        this.stmt.addBatch(sql);
        sql = "delete from jsf_p_readwritecontrol where wf_proceedactivity_id in (" + idBuffer.toString() + ")";
        this.stmt.addBatch(sql);
        sql = "delete from JSF_p_activity where wf_workflowprocess_id=" + flowId + " and trecord_id=" + instanceArr[0];
        this.stmt.addBatch(sql);
        this.stmt.executeBatch();
        this.stmt.clearBatch();
        String domainId = "0";
        ArrayList<Object[]> activitylist = new ArrayList();
        sql = "select wf_activity_id,activityName,activityDescription,activityType,allowStandFor,allowCancel,allowTransition,participantType,participantUser,participantGroup,participantUserName,participantUserField,pressType,deadLineTime,pressMotionTime,activityDocumentation,activityIcon,activityBeginEnd,transactType,actiCommField,needPassRound,passRoundUserType,passRoundUser,passRoundUserGroup,passRoundUserName,passRoundUserField,passRoundCommField,participantRole,passRoundRole,activityClass,activitySubProc,subProcType,PARTICIPANTGIVENORG,PASSROUNDGIVENORG,commentRange,domain_id,operButton,form_id,actiCommFieldType,passRoundCommFieldType,allowSmsRemind,tranType,tranCustomExtent,tranCustomExtentId,pressDealType,tranReadType,tranReadCustomExtent,tranReadCustomExtentId,allowautocheck,isDivide,isGather,isBranch,activityDelaySend from JSDB.jsf_activity where wf_workflowprocess_id = " + 







          
          flowId;
        rs = this.stmt.executeQuery(sql);
        while (rs.next()) {
          Object[] arrayOfObject = new Object[52];
          arrayOfObject[0] = rs.getString("wf_activity_id");
          arrayOfObject[1] = rs.getString("activityName");
          arrayOfObject[2] = rs.getString("activityDescription");
          arrayOfObject[3] = rs.getString("activityType");
          arrayOfObject[4] = rs.getString("allowStandFor");
          arrayOfObject[5] = rs.getString("allowCancel");
          arrayOfObject[6] = rs.getString("allowTransition");
          arrayOfObject[7] = rs.getString("participantType");
          arrayOfObject[8] = rs.getString("participantUser");
          arrayOfObject[9] = rs.getString("participantGroup");
          arrayOfObject[10] = rs.getString("participantUserName");
          arrayOfObject[11] = rs.getString("participantUserField");
          arrayOfObject[12] = (new StringBuilder(String.valueOf(rs.getInt("pressType")))).toString();
          arrayOfObject[13] = (new StringBuilder(String.valueOf(rs.getInt("deadLineTime")))).toString();
          arrayOfObject[14] = (new StringBuilder(String.valueOf(rs.getInt("pressMotionTime")))).toString();
          arrayOfObject[15] = rs.getString("activityDocumentation");
          arrayOfObject[16] = rs.getString("activityIcon");
          arrayOfObject[17] = rs.getString("activityBeginEnd");
          arrayOfObject[18] = rs.getString("transactType");
          arrayOfObject[19] = rs.getString("actiCommField");
          arrayOfObject[20] = rs.getString("needPassRound");
          arrayOfObject[21] = rs.getString("passRoundUserType");
          arrayOfObject[22] = rs.getString("passRoundUser");
          arrayOfObject[23] = rs.getString("passRoundUserGroup");
          arrayOfObject[24] = rs.getString("passRoundUserName");
          arrayOfObject[25] = rs.getString("passRoundUserField");
          arrayOfObject[26] = rs.getString("passRoundCommField");
          arrayOfObject[27] = rs.getString("participantRole");
          arrayOfObject[28] = rs.getString("passRoundRole");
          arrayOfObject[29] = rs.getString("activityClass");
          arrayOfObject[30] = rs.getString("activitySubProc");
          arrayOfObject[31] = rs.getString("subProcType");
          arrayOfObject[32] = rs.getString("PARTICIPANTGIVENORG");
          arrayOfObject[33] = rs.getString("PASSROUNDGIVENORG");
          arrayOfObject[34] = rs.getString("commentRange");
          domainId = rs.getString("domain_id");
          arrayOfObject[35] = rs.getString("operButton");
          arrayOfObject[36] = rs.getString("form_id");
          arrayOfObject[37] = rs.getString("actiCommFieldType");
          arrayOfObject[38] = rs.getString("passRoundCommFieldType");
          arrayOfObject[39] = (rs.getString("allowSmsRemind") == null) ? "0" : rs.getString("allowSmsRemind");
          arrayOfObject[40] = (rs.getString("tranType") == null) ? "0" : rs.getString("tranType");
          arrayOfObject[41] = rs.getString("tranCustomExtent");
          arrayOfObject[42] = rs.getString("tranCustomExtentId");
          arrayOfObject[43] = (rs.getString("pressDealType") == null) ? "0" : rs.getString("pressDealType");
          arrayOfObject[44] = (rs.getString("tranReadType") == null) ? "0" : rs.getString("tranReadType");
          arrayOfObject[45] = rs.getString("tranReadCustomExtent");
          arrayOfObject[46] = rs.getString("tranReadCustomExtentId");
          arrayOfObject[47] = rs.getString("allowautocheck");
          arrayOfObject[48] = rs.getString("isDivide");
          arrayOfObject[49] = rs.getString("isGather");
          arrayOfObject[50] = rs.getString("isBranch");
          arrayOfObject[51] = rs.getString("activityDelaySend");
          activitylist.add(arrayOfObject);
        } 
        rs.close();
        exeStmt.clearBatch();
        Calendar cal = Calendar.getInstance();
        Object[] obj = (Object[])null;
        for (int i = 0; i < activitylist.size(); i++) {
          obj = activitylist.get(i);
          String wfProceedActivityId = getTableId();
          String partRole = "", passRole = "", activityDelaySend = "";
          if (obj[27] != null)
            partRole = obj[27].toString(); 
          if (obj[28] != null)
            passRole = obj[28].toString(); 
          if (obj[51] != null)
            activityDelaySend = String.valueOf(cal.get(1)) + "-" + obj[51].toString() + " 08:00:00"; 
          sql = " insert into JSDB.JSF_p_Activity ( WF_ProceedActivity_Id, wf_activity_id, activityName, activityDescription, activityType, allowStandFor, allowCancel, allowTransition, participantType, participantUser, participantGroup, participantUserName, participantUserField, pressType, deadLineTime, pressMotionTime, activityDocumentation, activityIcon, activityBeginEnd, dealWithSign, wf_workflowprocess_id, employee_id,  ttable_id, trecord_id, transactType, actiCommField , needPassRound, passRoundUserType, passRoundUser, passRoundUserGroup, passRoundUserName, passRoundUserField, passRoundCommField,participantRole, passRoundRole, activityClass, activitySubProc, subProcType,PARTICIPANTGIVENORG,PASSROUNDGIVENORG, commentRange,DOMAIN_ID,operbutton,form_id,actiCommFieldType,passRoundCommFieldType,allowSmsRemind,tranType,tranCustomExtent,tranCustomExtentId, pressDealType,tranReadType,tranReadCustomExtent,tranReadCustomExtentId,allowautocheck,isDivide,isGather,isBranch,activityDelaySend) values (" + 









            
            wfProceedActivityId + "," + obj[0] + ",'" + obj[1] + "','" + obj[2] + "'," + 
            obj[3] + "," + obj[4] + "," + obj[5] + "," + obj[6] + "," + obj[7] + ",'" + 
            obj[8] + "','" + obj[9] + "','" + obj[10] + "','" + obj[11] + "'," + 
            obj[12] + "," + obj[13] + "," + obj[14] + ",'" + obj[15] + "','" + obj[16] + "'," + 
            obj[17] + ",0," + flowId + "," + instanceArr[2] + "," + instanceArr[1] + "," + instanceArr[0] + 
            ",'" + obj[18] + "', '" + obj[19] + "', " + obj[20] + ", " + obj[21] + ", '" + obj[22] + 
            "', '" + obj[23] + "','" + obj[24] + "', '" + obj[25] + "', '" + obj[26] + "', '" + partRole + "'," + 
            "'" + passRole + "', " + obj[29] + ", " + obj[30] + ", " + obj[31] + ",'" + obj[32] + "','" + obj[33] + 
            "', '" + obj[34] + "'," + domainId + ", '" + obj[35] + "', '" + obj[36] + "', '" + obj[37] + 
            "', '" + obj[38] + "', " + obj[39] + ", " + obj[40] + ", '" + obj[41] + "', '" + obj[42] + "'," + obj[43] + 
            ", " + obj[44] + ", '" + obj[45] + "', '" + obj[46] + "'," + obj[47] + "," + obj[48] + "," + obj[49] + "," + obj[50] + ",'" + activityDelaySend + "')";
          exeStmt.addBatch(sql);
          String sql2 = "";
          ArrayList<Object[]> alist = new ArrayList();
          sql2 = " select controlType, controlField from JSDB.JSF_readwritecontrol  where controltype>0 and wf_activity_id = " + 
            obj[0];
          rs = this.stmt.executeQuery(sql2);
          while (rs.next()) {
            Object[] rwControlObj = new Object[2];
            rwControlObj[0] = rs.getString("controlType");
            rwControlObj[1] = rs.getString("controlField");
            alist.add(rwControlObj);
          } 
          rs.close();
          Object[] obj2 = (Object[])null;
          int j;
          for (j = 0; j < alist.size(); j++) {
            obj2 = alist.get(j);
            String wf_proceedReadWriteControl_id = getTableId();
            sql2 = " insert into JSDB.jsf_p_readwritecontrol (  wf_proceedreadwritecontrol_id, controlType, controlField, wf_activity_id, wf_proceedactivity_id, DOMAIN_ID  ) values (" + 


              
              wf_proceedReadWriteControl_id + "," + obj2[0] + "," + obj2[1] + "," + 
              obj[0] + "," + wfProceedActivityId + "," + domainId + ")";
            exeStmt.addBatch(sql2);
          } 
          alist.clear();
          sql2 = "select conditionField, pressRelation, compareValue, pressMotionTime, motionTime from JSDB.jsf_press where wf_activity_id = " + 
            obj[0];
          rs = this.stmt.executeQuery(sql2);
          while (rs.next()) {
            Object[] pressObj = new Object[5];
            pressObj[0] = (new StringBuilder(String.valueOf(rs.getInt("conditionField")))).toString();
            pressObj[1] = rs.getString("pressRelation");
            pressObj[2] = rs.getString("compareValue");
            pressObj[3] = (new StringBuilder(String.valueOf(rs.getInt("pressMotionTime")))).toString();
            pressObj[4] = (new StringBuilder(String.valueOf(rs.getInt("motionTime")))).toString();
            alist.add(pressObj);
          } 
          rs.close();
          for (j = 0; j < alist.size(); j++) {
            obj2 = alist.get(j);
            String wf_proceedpress_id = getTableId();
            sql2 = " insert into JSDB.jsf_p_press (  wf_press_id, conditionField, pressrelation, compareValue, pressMotionTime,  motionTime, wf_activity_id, wf_proceedactivity_id,DOMAIN_ID  ) values (" + 


              
              wf_proceedpress_id + "," + obj2[0] + ",'" + obj2[1] + "','" + obj2[2] + "'," + 
              obj2[3] + "," + obj2[4] + "," + obj[0] + "," + wfProceedActivityId + "," + domainId + ")";
            exeStmt.addBatch(sql2);
          } 
          alist.clear();
          sql2 = "select wf_transition_id, transitionFrom, transitionName, transitionTo,  transitionDescription,EXPRESSION,defaultActivity,lineorder from JSDB.jsf_transition where transitionFrom = " + 
            obj[0];
          rs = this.stmt.executeQuery(sql2);
          while (rs.next()) {
            Object[] transObj = new Object[8];
            transObj[0] = (new StringBuilder(String.valueOf(rs.getInt("wf_transition_id")))).toString();
            transObj[1] = (new StringBuilder(String.valueOf(rs.getInt("transitionFrom")))).toString();
            transObj[2] = rs.getString("transitionName");
            transObj[3] = (new StringBuilder(String.valueOf(rs.getInt("transitionTo")))).toString();
            transObj[4] = rs.getString("transitionDescription");
            transObj[5] = rs.getString("EXPRESSION");
            transObj[6] = rs.getString("defaultActivity");
            transObj[7] = rs.getString("lineorder");
            alist.add(transObj);
          } 
          rs.close();
          for (j = 0; j < alist.size(); j++) {
            obj2 = alist.get(j);
            String wf_proceedtransition_id = getTableId();
            sql2 = " insert into JSDB.jsf_p_transition (  wf_proceedtransition_id, transitionFrom, transitionName, transitionTo, transitionDescription, wf_proceedactivity_id, DOMAIN_ID, EXPRESSION,defaultActivity,lineorder ) values (" + 


              
              wf_proceedtransition_id + "," + obj2[1] + ",'" + obj2[2] + "'," + obj2[3] + 
              ",'" + obj2[4] + "'," + wfProceedActivityId + ", " + domainId + ",'" + obj2[5] + "'," + ((obj2[6] == null) ? "0" : (String)obj2[6]) + "," + obj2[7] + ")";
            exeStmt.addBatch(sql2);
            sql2 = "select conditionField, compareValue, relation from JSDB.jsf_transitionrestriction  where wf_transition_id = " + 
              obj2[0];
            rs = this.stmt.executeQuery(sql2);
            ArrayList<Object[]> blist = new ArrayList();
            while (rs.next()) {
              Object[] condiObj = new Object[3];
              condiObj[0] = (new StringBuilder(String.valueOf(rs.getInt("conditionField")))).toString();
              condiObj[1] = rs.getString("compareValue");
              condiObj[2] = rs.getString("relation");
              blist.add(condiObj);
            } 
            rs.close();
            Object[] obj3 = (Object[])null;
            for (int k = 0; k < blist.size(); k++) {
              obj3 = blist.get(k);
              String wf_proceedtr_id = getTableId();
              sql2 = " insert into JSDB.jsf_p_tr ( wf_proceedtr_id, conditionField, compareValue, relation,  wf_proceedtransition_id, DOMAIN_ID  ) values (" + 


                
                wf_proceedtr_id + "," + obj3[0] + ",'" + obj3[1] + "','" + obj3[2] + "'," + 
                wf_proceedtransition_id + "," + domainId + ")";
              exeStmt.addBatch(sql2);
            } 
            blist.clear();
          } 
          alist.clear();
        } 
        exeStmt.executeBatch();
        exeStmt.clearBatch();
        activitylist.clear();
      } 
      exeStmt.close();
      exeConn.close();
    } catch (Exception ex) {
      ex.printStackTrace();
      if (exeConn != null)
        try {
          exeConn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
    } 
    return result;
  }
  
  public static void main(String[] args) {}
}
