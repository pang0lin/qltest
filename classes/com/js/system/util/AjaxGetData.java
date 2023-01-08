package com.js.system.util;

import com.js.oa.bbs.bean.ForumEJBBean;
import com.js.oa.bbs.service.ForumBD;
import com.js.oa.relproject.bean.ProNoteBean;
import com.js.oa.relproject.bean.RelProjectBean;
import com.js.oa.relproject.po.ProNotePO;
import com.js.oa.scheme.worklog.bean.WorkLogEJBBean;
import com.js.system.manager.bean.ManagerEJBBean;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.sql.DataSource;

public class AjaxGetData {
  public String getSubordinate(String userId) {
    String strJson = "";
    StringBuffer sb = new StringBuffer();
    WorkLogEJBBean workLogEJBBean = new WorkLogEJBBean();
    try {
      Object[] obj = (Object[])null;
      List list = workLogEJBBean.getDownEmployeeList(userId);
      Iterator<Object[]> iterator = list.iterator();
      while (iterator.hasNext()) {
        obj = iterator.next();
        sb.append("{");
        sb.append("\"userId\":\"");
        sb.append(obj[0].toString());
        sb.append("\",\"userName\":\"");
        sb.append(obj[1].toString());
        sb.append("\"");
        sb.append("}");
        if (iterator.hasNext())
          sb.append(","); 
      } 
      strJson = "[" + sb.toString() + "]";
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return strJson;
  }
  
  public String updatePersonalMsg(String chatId) {
    String execState = "fail";
    DataSource ds = (new DataSourceBase()).getDataSource();
    String successString = "{\"status\":\"success\"}";
    String failString = "{\"status\":\"fail\"}";
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      Long id = Long.valueOf(chatId);
      String sqlChat = "update chat set is_read='0' where chat_id=" + id.toString();
      int returnNum = stmt.executeUpdate(sqlChat);
      execState = (returnNum == 1) ? successString : failString;
      if ("fail".equals(execState))
        return execState; 
      String sqlMsg = "update sys_messages set message_status='0' where message_type='Chat' and data_id=" + id.toString();
      returnNum = stmt.executeUpdate(sqlMsg);
      execState = (returnNum == 1) ? successString : failString;
      stmt.close();
      conn.close();
    } catch (Exception e) {
      execState = failString;
      e.printStackTrace();
      if (stmt != null)
        try {
          stmt.close();
          if (conn != null)
            try {
              conn.close();
            } catch (Exception e2) {
              e2.printStackTrace();
            }  
        } catch (Exception e1) {
          e1.printStackTrace();
        }  
    } 
    return execState;
  }
  
  public String updateSysMsg(String msgId) {
    String execState = "fail";
    DataSource ds = (new DataSourceBase()).getDataSource();
    String successString = "{\"status\":\"success\"}";
    String failString = "{\"status\":\"fail\"}";
    Connection conn = null;
    Statement stmt = null;
    try {
      Long id = Long.valueOf(msgId);
      conn = ds.getConnection();
      stmt = conn.createStatement();
      String sqlMsg = "update sys_messages set message_status='0' where message_id=" + id.toString();
      int returnNum = stmt.executeUpdate(sqlMsg);
      execState = (returnNum == 1) ? successString : failString;
      stmt.close();
      conn.close();
    } catch (Exception e) {
      execState = failString;
      e.printStackTrace();
      if (stmt != null)
        try {
          stmt.close();
          if (conn != null)
            try {
              conn.close();
            } catch (Exception e2) {
              e2.printStackTrace();
            }  
        } catch (Exception e1) {
          e1.printStackTrace();
        }  
    } 
    return execState;
  }
  
  public String getDetailInfoByUserId(String curUserId, String userId) throws Exception {
    String userIdString = userId.split("_")[0];
    Boolean viewRightFlag = Boolean.FALSE;
    Boolean maintainRightFlag = Boolean.FALSE;
    ManagerEJBBean meb = new ManagerEJBBean();
    ForumEJBBean feb = new ForumEJBBean();
    Boolean isHasViewRight = meb.hasRight(curUserId, "08*03*01");
    StringBuffer sBuffer = new StringBuffer();
    String curUserOrgId = StaticParam.getOrgIdByEmpId(curUserId);
    String userOrgId = StaticParam.getOrgIdByEmpId(userIdString);
    String[] curUserOrgIds = curUserOrgId.split(",");
    String[] userOrgIds = userOrgId.split(",");
    ForumBD bd = new ForumBD();
    SysSetupReader ssReader = SysSetupReader.getInstance();
    boolean isViewAllLinkman = "0".equals(SysSetupReader.getLinkmanScope("0"));
    if (!isViewAllLinkman) {
      if (isHasViewRight.booleanValue()) {
        List<Object[]> scopeList = meb.getRightScope(curUserId, "08*03*01");
        String viewScope = "";
        if (scopeList.size() > 0) {
          Object[] obj = scopeList.get(0);
          viewScope = obj[0].toString();
        } 
        if ("0".equals(viewScope)) {
          viewRightFlag = Boolean.TRUE;
        } else if ("1".equals(viewScope)) {
          if (curUserId.equals(userIdString))
            viewRightFlag = Boolean.TRUE; 
        } else if ("2".equals(viewScope)) {
          String relationType = bd.getOrgRelationType(curUserOrgIds, userOrgIds);
          if ("selfOrg".equals(relationType) || "subOrg".equals(relationType))
            viewRightFlag = Boolean.TRUE; 
        } else if ("3".equals(viewScope)) {
          String relationType = bd.getOrgRelationType(curUserOrgIds, userOrgIds);
          if ("selfOrg".equals(relationType))
            viewRightFlag = Boolean.TRUE; 
        } else if ("4".equals(viewScope)) {
          String customScopeString = feb.getCustomScope(curUserId, "08*03*01", "0");
          if (customScopeString != null && !customScopeString.equals("")) {
            String orgsString = "";
            String usersString = "";
            if (customScopeString.indexOf("-") != 0)
              orgsString = customScopeString.split("-")[0]; 
            if (customScopeString.indexOf("-") != customScopeString.length() - 1)
              if ((customScopeString.split("-")).length > 1) {
                usersString = customScopeString.split("-")[1];
              } else {
                usersString = customScopeString.split("-")[0];
              }  
            if (usersString != null && !usersString.equals("")) {
              usersString = usersString.replaceAll("\\$\\$", ",").replaceAll("\\$", ",");
              String[] usersArray = usersString.split(",");
              for (int i = 0; i < usersArray.length; i++) {
                if (userIdString.equals(usersArray[i]))
                  viewRightFlag = Boolean.TRUE; 
              } 
            } 
            if (orgsString != null && !orgsString.equals("")) {
              orgsString = orgsString.replaceAll("\\*\\*", ",").replaceAll("\\*", ",");
              String[] orgsArray = orgsString.split(",");
              String scopeOrgStringTemp = "";
              String userOrgStringTemp = "";
              for (int i = 0; i < orgsArray.length; i++) {
                scopeOrgStringTemp = orgsArray[i];
                for (int j = 0; j < userOrgIds.length; j++) {
                  userOrgStringTemp = userOrgIds[j];
                  if (scopeOrgStringTemp.equals(userOrgStringTemp))
                    viewRightFlag = Boolean.TRUE; 
                } 
              } 
            } 
          } 
        } 
      } 
      if (!viewRightFlag.booleanValue()) {
        Boolean isHasMaintainRight = meb.hasRight(curUserId, "08*03*02");
        if (isHasMaintainRight.booleanValue()) {
          List<Object[]> scopeList = meb.getRightScope(curUserId, "08*03*02");
          String maintainScope = "";
          if (scopeList.size() > 0) {
            Object[] obj = scopeList.get(0);
            maintainScope = obj[0].toString();
          } 
          if ("0".equals(maintainScope)) {
            maintainRightFlag = Boolean.TRUE;
          } else if ("1".equals(maintainScope)) {
            if (curUserId.equals(userIdString))
              maintainRightFlag = Boolean.TRUE; 
          } else if ("2".equals(maintainScope)) {
            String relationType = bd.getOrgRelationType(curUserOrgIds, userOrgIds);
            if ("selfOrg".equals(relationType) || "subOrg".equals(relationType))
              maintainRightFlag = Boolean.TRUE; 
          } else if ("3".equals(maintainScope)) {
            String relationType = bd.getOrgRelationType(curUserOrgIds, userOrgIds);
            if ("selfOrg".equals(relationType))
              maintainRightFlag = Boolean.TRUE; 
          } else if ("4".equals(maintainScope)) {
            String customScopeString = feb.getCustomScope(curUserId, "08*03*02", "0");
            if (customScopeString != null && !customScopeString.equals("")) {
              String orgsString = "";
              String usersString = "";
              if (customScopeString.indexOf("-") != 0)
                orgsString = customScopeString.split("-")[0]; 
              if (customScopeString.indexOf("-") != customScopeString.length() - 1)
                if ((customScopeString.split("-")).length > 1) {
                  usersString = customScopeString.split("-")[1];
                } else {
                  usersString = customScopeString.split("-")[0];
                }  
              if (usersString != null && !usersString.equals("")) {
                usersString = usersString.replaceAll("\\$\\$", ",").replaceAll("\\$", ",");
                String[] usersArray = usersString.split(",");
                for (int i = 0; i < usersArray.length; i++) {
                  if (userIdString.equals(usersArray[i]))
                    maintainRightFlag = Boolean.TRUE; 
                } 
              } 
              if (orgsString != null && !orgsString.equals("")) {
                orgsString = orgsString.replaceAll("\\*\\*", ",").replaceAll("\\*", ",");
                String[] orgsArray = orgsString.split(",");
                String scopeOrgStringTemp = "";
                String userOrgStringTemp = "";
                for (int i = 0; i < orgsArray.length; i++) {
                  scopeOrgStringTemp = orgsArray[i];
                  for (int j = 0; j < userOrgIds.length; j++) {
                    userOrgStringTemp = userOrgIds[j];
                    if (scopeOrgStringTemp.equals(userOrgStringTemp))
                      maintainRightFlag = Boolean.TRUE; 
                  } 
                } 
              } 
            } 
          } 
        } 
      } 
    } 
    if (isViewAllLinkman || viewRightFlag.booleanValue() || maintainRightFlag.booleanValue()) {
      RelProjectBean bean = new RelProjectBean();
      EmployeeVO vo = bean.getEmployeeInfoById(userIdString);
      sBuffer.append("<div class=\"content\"><h3>联系方式</h3><a href=\"javascript:closePoped('" + userId + "');\"><img src=\"/jsoa/images/close.gif\" alt=\"关闭\" width=\"12\" height=\"12\" class=\"close\" /></a></div>");
      sBuffer.append("<div style=\"border-bottom:1px dashed #cccccc;margin:5px;\"><span style=\"display:inline-block;width:140px;\"><font style=\"font-weight:bold;color:#993366\">姓名:</font>" + ((vo.getEmpName() == null) ? "" : vo.getEmpName()) + "</span><span style=\"display:inline-block;width:140px;\"><font style=\"font-weight:bold;color:#993366\">职位:</font>" + ((vo.getEmpDuty() == null) ? "" : vo.getEmpDuty()) + "</span></div>");
      sBuffer.append("<div style=\"margin:5px;\"><span style=\"display:inline-block;\"><font style=\"font-weight:bold;color:#993366\">手机:</font>" + ((vo.getEmpMobilePhone() == null) ? "" : vo.getEmpMobilePhone()) + "</span></div>");
      sBuffer.append("<div style=\"margin:5px;\"><span style=\"display:inline-block;width:140px;\"><font style=\"font-weight:bold;color:#993366\">办公电话:</font>" + ((vo.getEmpBusinessPhone() == null) ? "" : vo.getEmpBusinessPhone()) + "</span></div>");
      sBuffer.append("<div style=\"margin:5px;\"><span style=\"display:inline-block;width:140px;\"><font style=\"font-weight:bold;color:#993366\">住宅电话:</font>" + ((vo.getEmpPhone() == null) ? "" : vo.getEmpPhone()) + "</span></div>");
      sBuffer.append("<div style=\"border-bottom:1px dashed #cccccc;margin:5px;\"><span style=\"display:inline-block;\"><font style=\"font-weight:bold;color:#993366\">电子邮件:</font>" + ((vo.getEmpEmail() == null) ? "" : vo.getEmpEmail()) + "</span></div>");
      sBuffer.append("<div style=\"margin:5px;\"><span style=\"display:inline-block\"><font style=\"font-weight:bold;color:#993366\">家庭住址:</font></span></div>");
      sBuffer.append("<div style=\"margin:5px;\"><span style=\"display:inline-block\">" + ((vo.getEmpAddress() == null) ? "" : vo.getEmpAddress()) + "</span></div>");
    } else {
      sBuffer.append("<div><h3>对不起您无权限查看该用户联系方式!</h3></div>");
    } 
    return sBuffer.toString();
  }
  
  public String insertProNote(String content, String proId, String empId, String empName) {
    StringBuffer noteList = new StringBuffer();
    ProNoteBean bean = new ProNoteBean();
    ProNotePO po = new ProNotePO();
    po.setContent(content);
    po.setEmpId(new Long(empId));
    po.setEmpName(empName);
    po.setProjectId(new Long(proId));
    po.setSendTime(new Date());
    bean.saveNote(po);
    RelProjectBean relBean = new RelProjectBean();
    List<Object[]> list = relBean.getProNotes(proId);
    String title = "";
    String time = "";
    int i;
    for (i = 0; i < list.size(); i++) {
      Object[] objects = list.get(i);
      title = objects[3].toString();
      time = objects[2].toString();
      time = time.substring(time.indexOf("-") + 1, time.indexOf(" "));
      noteList.append("<tr><td valign=\"middle\" class=\"left_xt\">&nbsp;</td>")
        .append("<td valign=\"bottom\" class=\"border_bm\"><a style=\"cursor:hand\" onclick=\"javascript:openNote('" + objects[0] + "')\">" + objects[1] + "</a></td>")
        .append("<td valign=\"middle\" class=\"color_h\">" + time + "</td>")
        .append("<td align=\"right\" valign=\"middle\" class=\"color_right\">" + title + "</td></tr>");
    } 
    for (int j = i; j < 7; j++)
      noteList.append("<tr><td height=22 align=\"left\" valign=\"middle\" class=\"left_xt_none\">&nbsp;</td>")
        .append("<td align=\"left\" valign=\"bottom\" class=\"border_bm\">&nbsp;</td>")
        .append("<td align=\"left\" valign=\"middle\" class=\"color_h\">&nbsp;</td>")
        .append("<td align=\"left\" valign=\"middle\" class=\"color_right\">&nbsp;</td>"); 
    return noteList.toString();
  }
  
  public String getPersonalSign(String userId) {
    String returnValue = "";
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      long uId = Long.valueOf(userId).longValue();
      conn = ds.getConnection();
      stmt = conn.createStatement();
      String sql = "select empgnome from org_employee where emp_id=" + uId;
      rs = stmt.executeQuery(sql);
      if (rs.next())
        returnValue = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      if (stmt != null)
        try {
          stmt.close();
          if (conn != null)
            try {
              conn.close();
            } catch (Exception e2) {
              e2.printStackTrace();
            }  
        } catch (Exception e1) {
          e1.printStackTrace();
        }  
    } 
    return returnValue;
  }
}
