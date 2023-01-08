package com.js.oa.scheme.taskcenter.action;

import com.js.oa.scheme.taskcenter.po.TaskPO;
import com.js.oa.scheme.taskcenter.po.TaskPressPO;
import com.js.oa.scheme.taskcenter.service.TaskPressBD;
import com.js.system.service.messages.RemindUtil;
import com.js.util.util.CharacterTool;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class TaskPressAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    String action = request.getParameter("action");
    TaskPressBD bd = new TaskPressBD();
    if ("save".equals(action)) {
      TaskPressPO po = new TaskPressPO();
      po.setPressContext(CharacterTool.escapeHTMLTags((new StringBuilder(String.valueOf(request.getParameter("pressContext")))).toString()));
      po.setPressUserId(Long.valueOf((new StringBuilder(String.valueOf(request.getParameter("pressUserId")))).toString()));
      po.setPressUserName(request.getParameter("pressUserName"));
      po.setToUserId(request.getParameter("toUserId"));
      po.setToUserName("");
      po.setTaskId(Long.valueOf(request.getParameter("taskId")));
      Long id = bd.saveTaskPressPO(po);
      if (id.longValue() > 0L)
        tixing(po); 
      request.setAttribute("flag", action);
      action = "close";
    } else if ("press".equals(action)) {
      Long taskId = Long.valueOf((new StringBuilder(String.valueOf(request.getParameter("taskId")))).toString());
      TaskPO tpo = bd.getTaskPO(taskId);
      String toUserId = tpo.getTaskPrincipal() + ((
        tpo.getTaskJoineOrg() == null || "null".equalsIgnoreCase(tpo.getTaskJoineOrg()) || "".equals(tpo.getTaskJoineOrg())) ? 
        "" : ("," + tpo.getTaskJoineOrg()));
      request.setAttribute("toUserId", toUserId);
      request.setAttribute("taskTitle", tpo.getTaskTitle());
    } else if ("list".equals(action)) {
      String taskId = request.getParameter("taskId");
      List<TaskPressPO> list = bd.getTaskPressPOList(taskId);
      request.setAttribute("taskPressList", list);
    } else if ("addYijian".equals(action)) {
      String taskId = request.getParameter("taskId");
      HttpSession session = request.getSession();
      TaskPressPO po = new TaskPressPO();
      po.setPressContext(CharacterTool.escapeHTMLTags((new StringBuilder(String.valueOf(request.getParameter("yijianContext")))).toString()));
      po.setPressUserId(Long.valueOf((String)session.getAttribute("userId")));
      po.setPressUserName((String)session.getAttribute("userName"));
      po.setToUserName("");
      po.setTaskId(Long.valueOf(taskId));
      po.setPresstype(Integer.valueOf(2));
      TaskPO tpo = bd.getTaskPO(Long.valueOf(taskId));
      String toUserId = tpo.getTaskPrincipal() + ((
        tpo.getTaskJoineOrg() == null || "null".equalsIgnoreCase(tpo.getTaskJoineOrg()) || "".equals(tpo.getTaskJoineOrg())) ? 
        "" : ("," + tpo.getTaskJoineOrg()));
      po.setToUserId(toUserId);
      Long id = bd.saveTaskPressPO(po);
      po.setPressContext(String.valueOf(tpo.getTaskTitle()) + "：" + po.getPressContext());
      if (id.longValue() > 0L)
        tixing(po); 
      List<TaskPressPO> list = bd.getTaskPressPOList(taskId);
      request.setAttribute("taskPressList", list);
      action = "list";
    } 
    return actionMapping.findForward(action);
  }
  
  public void tixing(TaskPressPO po) {
    String title = "任务督办：" + CharacterTool.deleteEnter(po.getPressContext());
    if (title.length() > 100)
      title = title.substring(0, 100); 
    String url = "/jsoa/taskAction.do?action=selectSettleSingleTask&taskId=" + po.getTaskId() + "&isArranged=1&flag=no";
    String userIds = po.getToUserId();
    String moduleType = "task";
    String sendUserName = po.getPressUserName();
    Long dataId = po.getTaskId();
    int showType = 1;
    RemindUtil.sendMessageToUsers2(title, url, userIds, moduleType, po.getPressDate(), 
        new Date(po.getPressDate().getTime() + 7776000000L), 
        sendUserName, dataId, showType, 1);
  }
}
