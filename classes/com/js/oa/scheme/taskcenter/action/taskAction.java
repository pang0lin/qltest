package com.js.oa.scheme.taskcenter.action;

import com.js.oa.message.action.ModelSendMsg;
import com.js.oa.scheme.taskcenter.po.TaskAccessoryPO;
import com.js.oa.scheme.taskcenter.po.TaskPO;
import com.js.oa.scheme.taskcenter.po.TaskPeriodicityPO;
import com.js.oa.scheme.taskcenter.po.TaskReportAccessoryPO;
import com.js.oa.scheme.taskcenter.po.TaskReportPO;
import com.js.oa.scheme.taskcenter.service.TaskBD;
import com.js.oa.scheme.taskcenter.vo.TaskClassVO;
import com.js.oa.scheme.taskcenter.vo.TaskReportVO;
import com.js.oa.scheme.taskcenter.vo.TaskVO;
import com.js.oa.scheme.util.ConversionString;
import com.js.system.manager.service.ManagerService;
import com.js.system.service.messages.RemindUtil;
import com.js.system.service.usermanager.UserBD;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.FillBean;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class taskAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    taskActionForm taskActionForm = (taskActionForm)actionForm;
    TaskBD taskBD = new TaskBD();
    HttpSession session = httpServletRequest.getSession(true);
    Long domainId = (session.getAttribute("domainId") == null) ? 
      Long.valueOf("0") : 
      Long.valueOf(session.getAttribute("domainId").toString());
    String action = httpServletRequest.getParameter("action");
    String saveType = httpServletRequest.getParameter("saveType");
    Long userId = new Long(session.getAttribute("userId").toString());
    Long orgId = new Long(session.getAttribute("orgId").toString());
    String userName = session.getAttribute("userName").toString();
    String orgName = session.getAttribute("orgName").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    if ("add".equals(action)) {
      TaskClassVO taskClassVO = (TaskClassVO)FillBean.transformOneToOne(
          taskActionForm, TaskClassVO.class);
      taskClassVO.setCreatedEmp(userId);
      taskClassVO.setCreatedOrg(orgId);
      taskClassVO.setClassDomainId(domainId);
      ConversionString conversionString = new ConversionString(
          taskActionForm.getUseScopeId());
      taskClassVO.setUsedScopeId(conversionString.getUserString());
      taskClassVO.setUsedScopeOrgId(conversionString.getOrgString());
      taskClassVO.setUsedScopeGroupId(conversionString.getGroupString());
      taskClassVO.setUsedScopeName(taskActionForm.getUseScope());
      taskClassVO.setClassName(taskActionForm.getClassName().trim());
      String result1 = taskBD.taskClassNameIsExist(taskActionForm
          .getClassName().trim(), null, domainId);
      if (result1 != null && "no".equals(result1)) {
        boolean result = taskBD.addTaskClass(taskClassVO);
        if (!result)
          return actionMapping.findForward("failure"); 
        if ("saveAndContinue".equals(saveType)) {
          taskActionForm.reset(actionMapping, httpServletRequest);
          return actionMapping.findForward(
              "taskclass_saveAndContinue");
        } 
        if ("saveAndExit".equals(saveType))
          return actionMapping.findForward("taskclass_saveAndExit"); 
      } else {
        return actionMapping.findForward("tasksortNoSave");
      } 
    } 
    if ("deleteTaskClass".equals(action)) {
      String classIdStr = httpServletRequest.getParameter(
          "classId");
      boolean result = true;
      String results = "0";
      if (classIdStr != null && !"".equals(classIdStr)) {
        results = taskBD.checkhastype(classIdStr);
        if ("0".equals(results)) {
          Long classId = Long.valueOf(classIdStr);
          result = taskBD.deleteTaskClass(classId);
        } 
      } 
      if (!result)
        return actionMapping.findForward("failure"); 
      int pageSize = 15;
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest
            .getParameter("pager.offset")); 
      boolean newRight = taskBD.hasRight(userId, "任务中心-任务分类", "维护");
      if (!newRight) {
        httpServletRequest.setAttribute("newRight", "false");
      } else {
        httpServletRequest.setAttribute("newRight", "true");
      } 
      int currentPage = offset / pageSize + 1;
      taskBD.setVolume(pageSize);
      List list = taskBD.selectAllTaskClass(userId, orgId, 
          new Integer(currentPage), new Integer(pageSize), 
          domainId);
      int recordCount = taskBD.getRecordCount();
      if (offset > recordCount) {
        offset = (recordCount - pageSize) / pageSize;
        currentPage = offset + 1;
        offset *= pageSize;
        list = null;
        list = taskBD.selectAllTaskClass(userId, orgId, 
            new Integer(currentPage), 
            new Integer(pageSize), domainId);
        recordCount = taskBD.getRecordCount();
        httpServletRequest.setAttribute("pager.offset", 
            String.valueOf(offset));
        httpServletRequest.setAttribute("pager.realCurrent", 
            String.valueOf(currentPage));
      } 
      httpServletRequest.setAttribute("allTaskClassList", list);
      httpServletRequest.setAttribute("recordCount", 
          String.valueOf(recordCount));
      httpServletRequest.setAttribute("maxPageItems", 
          String.valueOf(pageSize));
      httpServletRequest.setAttribute("pageParameters", "action");
      httpServletRequest.setAttribute("results", results);
      return actionMapping.findForward("goto_TaskClassList");
    } 
    if ("deleteBatchTaskClass".equals(action)) {
      String classIds = httpServletRequest.getParameter("ids");
      boolean result = taskBD.deleteBatchTaskClass(classIds);
      if (!result)
        return actionMapping.findForward("failure"); 
      int pageSize = 15;
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter(
              "pager.offset")); 
      boolean newRight = taskBD.hasRight(userId, "任务中心-任务分类", "维护");
      if (!newRight) {
        httpServletRequest.setAttribute("newRight", "false");
      } else {
        httpServletRequest.setAttribute("newRight", "true");
      } 
      int currentPage = offset / pageSize + 1;
      taskBD.setVolume(pageSize);
      List list = taskBD.selectAllTaskClass(userId, orgId, 
          new Integer(currentPage), 
          new Integer(pageSize), 
          domainId);
      int recordCount = taskBD.getRecordCount();
      if (offset >= recordCount) {
        offset = (recordCount - pageSize) / pageSize;
        currentPage = offset + 1;
        offset *= pageSize;
        list = null;
        list = taskBD.selectAllTaskClass(userId, orgId, 
            new Integer(currentPage), 
            new Integer(pageSize), 
            domainId);
        recordCount = taskBD.getRecordCount();
        httpServletRequest.setAttribute("pager.offset", 
            String.valueOf(offset));
        httpServletRequest.setAttribute("pager.realCurrent", 
            String.valueOf(currentPage));
      } 
      httpServletRequest.setAttribute("allTaskClassList", list);
      httpServletRequest.setAttribute("recordCount", 
          String.valueOf(recordCount));
      httpServletRequest.setAttribute("maxPageItems", 
          String.valueOf(pageSize));
      httpServletRequest.setAttribute("pageParameters", "action");
      return actionMapping.findForward("goto_TaskClassList");
    } 
    if ("deleteAllTaskClass".equals(action)) {
      boolean result = taskBD.deleteAllTaskClass(userId);
      if (!result)
        return actionMapping.findForward("failure"); 
      try {
        httpServletResponse.sendRedirect(
            "/jsoa/workLogAction.do?action=selectAllProjectStep");
      } catch (IOException ex1) {
        ex1.printStackTrace();
      } 
    } 
    if ("selectAllTaskClass".equals(action)) {
      int pageSize = 15;
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter(
              "pager.offset")); 
      boolean newRight = taskBD.hasRight(userId, "任务中心-任务分类", "维护");
      if (!newRight) {
        httpServletRequest.setAttribute("newRight", "false");
      } else {
        httpServletRequest.setAttribute("newRight", "true");
      } 
      int currentPage = offset / pageSize + 1;
      taskBD.setVolume(pageSize);
      List list = taskBD.selectAllTaskClass(userId, orgId, 
          new Integer(currentPage), 
          new Integer(pageSize), 
          domainId);
      String recordCount = String.valueOf(taskBD.getRecordCount());
      httpServletRequest.setAttribute("allTaskClassList", list);
      httpServletRequest.setAttribute("recordCount", recordCount);
      httpServletRequest.setAttribute("maxPageItems", 
          String.valueOf(pageSize));
      httpServletRequest.setAttribute("pageParameters", "action");
      return actionMapping.findForward("goto_AllTaskClass");
    } 
    if ("selectSingleTaskClass".equals(action)) {
      Long classId = Long.valueOf(httpServletRequest.getParameter(
            "classId"));
      TaskClassVO taskclassVO = taskBD.selectSingleTaskClass(classId);
      taskActionForm.setClassName(taskclassVO.getClassName());
      taskActionForm.setOrderCode(taskclassVO.getOrderCode());
      taskActionForm.setUseScope(taskclassVO.getUsedScopeName());
      String userIds = taskclassVO.getUsedScopeId();
      if (userIds == null || "null".equals(userIds))
        userIds = ""; 
      String orgIds = taskclassVO.getUsedScopeOrgId();
      if (orgIds == null || "null".equals(orgIds))
        orgIds = ""; 
      String groupIds = taskclassVO.getUsedScopeGroupId();
      if (groupIds == null || "null".equals(groupIds))
        groupIds = ""; 
      taskActionForm.setUseScopeId(String.valueOf(userIds) + orgIds + groupIds);
      String empName = taskclassVO.getEmpName();
      httpServletRequest.setAttribute("classId", classId);
      httpServletRequest.setAttribute("empName", empName);
      return actionMapping.findForward("goto_AllTaskClassUpdate");
    } 
    if ("modifyTaskClass".equals(action)) {
      Long classId = Long.valueOf(httpServletRequest.getParameter(
            "classId"));
      String empName = httpServletRequest.getParameter("empName");
      TaskClassVO taskclassVO = (TaskClassVO)FillBean.transformOneToOne(
          taskActionForm, TaskClassVO.class);
      taskclassVO.setClassId(classId);
      taskclassVO.setEmpName(empName);
      ConversionString conversionString = new ConversionString(
          taskActionForm.getUseScopeId());
      taskclassVO.setUsedScopeId(conversionString.getUserString());
      taskclassVO.setUsedScopeOrgId(conversionString.getOrgString());
      taskclassVO.setUsedScopeGroupId(conversionString.getGroupString());
      taskclassVO.setUsedScopeName(taskActionForm.getUseScope());
      taskclassVO.setClassName(taskActionForm.getClassName().trim());
      String result1 = taskBD.taskClassNameIsExist(taskActionForm
          .getClassName().trim(), classId, domainId);
      if (result1 != null && "no".equals(result1)) {
        boolean result = taskBD.modifyTaskClass(taskclassVO);
        if (!result)
          return actionMapping.findForward("failure"); 
        if ("updateAndExit".equals(saveType)) {
          httpServletRequest.setAttribute("classId", classId);
          httpServletRequest.setAttribute("empName", empName);
          return actionMapping.findForward("taskClass_updateAndExit");
        } 
      } else {
        httpServletRequest.setAttribute("classId", classId);
        httpServletRequest.setAttribute("empName", empName);
        return actionMapping.findForward(
            "SaveFailedCauseClassNameIsExist");
      } 
    } 
    if ("addTaskView".equals(action)) {
      List list = taskBD.selectAllTaskClass(domainId, userId, orgIdString);
      httpServletRequest.setAttribute("allTaskClassList", list);
      return actionMapping.findForward("addTaskView");
    } 
    if ("addTaskPrincipalView".equals(action)) {
      List list = taskBD.selectAllTaskClass(domainId, userId, orgIdString);
      httpServletRequest.setAttribute("allTaskClassList", list);
      taskActionForm.setTaskStatus(Integer.valueOf(1));
      return actionMapping.findForward("addTaskPrincipalView");
    } 
    if ("addTask".equals(action)) {
      TaskPO taskPO = new TaskPO();
      TaskVO taskVO = (TaskVO)FillBean.transformOneToOne(taskActionForm, TaskVO.class);
      Long taskAwakeTime = Long.valueOf(httpServletRequest.getParameter("taskAwakeTime"));
      taskVO.setCreatedEmp(userId);
      taskVO.setCreatedEmpName(userName);
      taskVO.setCreatedOrg(orgId);
      taskVO.setCreatedOrgName(orgName);
      taskVO.setIsArranged(new Integer(0));
      taskVO.setStickie(Long.valueOf(0L));
      taskVO.setAnpaiStickie(Long.valueOf(0L));
      String ifAwake = taskActionForm.getIfAwake();
      if ("1".equals(ifAwake)) {
        taskVO.setTaskAwakeTime(taskAwakeTime);
      } else {
        taskVO.setTaskAwakeTime(new Long(0L));
      } 
      taskVO.setTaskCreatedTime(new Date());
      Date startTime = new Date(httpServletRequest.getParameter(
            "start_date"));
      Date endTime = new Date(httpServletRequest.getParameter("end_date"));
      taskVO.setTaskBeginTime(startTime);
      taskVO.setTaskEndTime(endTime);
      String taskType = httpServletRequest.getParameter("taskType");
      taskVO.setTaskType(taskType);
      taskVO.setParentIdString("-1");
      taskVO.setTaskDomainId(domainId);
      String joineOrgId = (taskVO.getTaskJoinedEmp() == null) ? "0" : (
        new ConversionString(taskVO.getTaskJoinedEmp()))
        .getOrgIdString();
      String taskJoineOrg = taskBD.selectUserIds(joineOrgId);
      taskVO.setTaskJoineOrg(taskJoineOrg);
      if (httpServletRequest.getParameter("relproject") != null)
        taskVO.setRelProjectId(Long.valueOf(httpServletRequest.getParameter("relproject"))); 
      HashSet<TaskAccessoryPO> Accessory = new HashSet();
      String[] fileName = httpServletRequest.getParameterValues(
          "taskFileName");
      String[] saveName = httpServletRequest.getParameterValues(
          "taskSaveName");
      if (fileName != null)
        for (int i = 0; i < fileName.length; i++) {
          if (!"".equals(fileName[i])) {
            TaskAccessoryPO taskAccessoryPO = new TaskAccessoryPO();
            taskAccessoryPO.setAccessoryname(fileName[i]);
            taskAccessoryPO.setAccessorysavename(saveName[i]);
            taskAccessoryPO.setDomain_id(domainId);
            Accessory.add(taskAccessoryPO);
          } 
        }  
      String meetingId = (httpServletRequest.getParameter("meetingId") == null) ? "-1" : httpServletRequest.getParameter("meetingId");
      taskPO.setMeetingId(Long.valueOf(meetingId));
      taskPO.setTaskAccessory(Accessory);
      List list = taskBD.selectAllTaskClass(domainId, userId, orgIdString);
      taskVO.setTaskPO(taskPO);
      boolean result = taskBD.addTask(taskVO);
      if (!result)
        return actionMapping.findForward("failure"); 
      if ("saveAndContinue".equals(saveType)) {
        httpServletRequest.setAttribute("allTaskClassList", list);
        taskActionForm.reset(actionMapping, httpServletRequest);
        return actionMapping.findForward("task_saveAndContinue");
      } 
      if ("saveAndExit".equals(saveType)) {
        httpServletRequest.setAttribute("allTaskClassList", list);
        return actionMapping.findForward("task_saveAndExit");
      } 
    } 
    if ("selectPrincipalTask".equals(action)) {
      String type = "";
      String sortType = "";
      if (httpServletRequest.getParameter("type") != null)
        type = httpServletRequest.getParameter("type"); 
      if (httpServletRequest.getParameter("sortType") != null)
        sortType = httpServletRequest.getParameter("sortType"); 
      List allTaskClassList = taskBD.selectAllTaskClass(domainId, userId, orgIdString);
      httpServletRequest.setAttribute("allTaskClassList", 
          allTaskClassList);
      int pageSize = 15;
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter(
              "pager.offset")); 
      int currentPage = offset / pageSize + 1;
      taskBD.setVolume(pageSize);
      List list = null;
      String tag = "goto_AllTask";
      if (httpServletRequest.getParameter("toUserId") != null) {
        String toUserId = httpServletRequest.getParameter("toUserId");
        list = taskBD.selectPrincipalTask(userId, 
            new Integer(currentPage), domainId, toUserId, type, sortType);
        httpServletRequest.setAttribute("toUserId", toUserId);
        tag = "goto_UserTask";
      } else {
        list = taskBD.selectPrincipalTask(userId, 
            new Integer(currentPage), domainId, type, sortType);
      } 
      String recordCount = String.valueOf(taskBD.getRecordCount());
      httpServletRequest.setAttribute("allTaskList", list);
      httpServletRequest.setAttribute("recordCount", recordCount);
      httpServletRequest.setAttribute("maxPageItems", 
          String.valueOf(pageSize));
      if (httpServletRequest.getParameter("toUserId") != null) {
        httpServletRequest.setAttribute("pageParameters", "action,toUserId,type,sortType,taskTitle,taskFinishRate,taskPriority,taskType,taskStatus,startTaskBeginTime,endTaskBeginTime,isBeginDate,startTaskEndTime,endTaskEndTime,isEndDate");
      } else {
        httpServletRequest.setAttribute("pageParameters", "action,type,sortType,taskTitle,taskFinishRate,taskPriority,taskType,taskStatus,startTaskBeginTime,endTaskBeginTime,isBeginDate,startTaskEndTime,endTaskEndTime,isEndDate");
      } 
      httpServletRequest.setAttribute("sortType", sortType);
      httpServletRequest.setAttribute("type", type);
      return actionMapping.findForward(tag);
    } 
    if ("addTaskSubView".equals(action)) {
      List list = taskBD.selectAllTaskClass(domainId, userId, orgIdString);
      String taskId = httpServletRequest.getParameter("taskId");
      httpServletRequest.setAttribute("taskId", taskId);
      httpServletRequest.setAttribute("rightScope", 
          getRightScope(userId, orgId, 
            "任务中心-任务安排", "新增"));
      httpServletRequest.setAttribute("allTaskClassList", list);
      httpServletRequest.setAttribute("isArranged", 
          httpServletRequest
          .getParameter("isArranged"));
      return actionMapping.findForward("addTaskSubView");
    } 
    if ("addTaskSub".equals(action)) {
      TaskPO taskPO = new TaskPO();
      String meetingId = (httpServletRequest.getParameter("meetingId") == null) ? "-1" : httpServletRequest.getParameter("meetingId");
      taskPO.setMeetingId(Long.valueOf(meetingId));
      String isArranged = httpServletRequest.getParameter("isArranged");
      TaskVO taskVO = (TaskVO)FillBean.transformOneToOne(taskActionForm, 
          TaskVO.class);
      Long taskAwakeTime = Long.valueOf(httpServletRequest.getParameter(
            "taskAwakeTime"));
      Long taskId = Long.valueOf(httpServletRequest.getParameter("taskId"));
      taskVO.setCreatedEmp(userId);
      taskVO.setCreatedEmpName(userName);
      taskVO.setCreatedOrg(orgId);
      taskVO.setCreatedOrgName(orgName);
      taskVO.setAnpaiStickie(Long.valueOf(0L));
      taskVO.setStickie(Long.valueOf(0L));
      if (isArranged != null && "0".equals(isArranged)) {
        taskVO.setIsArranged(new Integer(0));
      } else {
        taskVO.setIsArranged(new Integer(1));
      } 
      String ifAwake = taskActionForm.getIfAwake();
      if ("checkbox".equals(ifAwake)) {
        taskVO.setTaskAwakeTime(taskAwakeTime);
      } else {
        taskVO.setTaskAwakeTime(new Long(0L));
      } 
      if (httpServletRequest.getParameter("relproject") != null)
        taskVO.setRelProjectId(Long.valueOf(Long.parseLong(httpServletRequest.getParameter("relproject")))); 
      taskVO.setTaskCreatedTime(new Date());
      Date startTime = new Date(httpServletRequest.getParameter(
            "start_date"));
      Date endTime = new Date(httpServletRequest.getParameter("end_date"));
      taskVO.setTaskBeginTime(startTime);
      taskVO.setTaskEndTime(endTime);
      String taskType = "";
      if (httpServletRequest.getParameter("taskType") == null) {
        taskType = "其它";
      } else {
        taskType = httpServletRequest.getParameter("taskType");
      } 
      taskVO.setTaskType(taskType);
      taskVO.setParentIdString("1");
      taskVO.setTaskParentId(taskId);
      taskVO.setTaskDomainId(domainId);
      String joineOrgId = (taskVO.getTaskJoinedEmp() == null) ? "0" : (
        new ConversionString(taskVO.getTaskJoinedEmp()))
        .getOrgIdString();
      if (!joineOrgId.equals("")) {
        String taskJoineOrg = taskBD.selectUserIds(joineOrgId);
        taskVO.setTaskJoineOrg(taskJoineOrg);
      } else {
        taskVO.setTaskJoineOrg("");
      } 
      HashSet<TaskAccessoryPO> Accessory = new HashSet();
      String[] fileName = httpServletRequest.getParameterValues(
          "taskFileName");
      String[] saveName = httpServletRequest.getParameterValues(
          "taskSaveName");
      if (fileName != null)
        for (int i = 0; i < fileName.length; i++) {
          if (!"".equals(fileName[i])) {
            TaskAccessoryPO taskAccessoryPO = new TaskAccessoryPO();
            taskAccessoryPO.setAccessoryname(fileName[i]);
            taskAccessoryPO.setAccessorysavename(saveName[i]);
            taskAccessoryPO.setDomain_id(domainId);
            Accessory.add(taskAccessoryPO);
          } 
        }  
      taskPO.setTaskAccessory(Accessory);
      taskVO.setTaskPO(taskPO);
      boolean result = taskBD.addTask(taskVO);
      if (!result)
        return actionMapping.findForward("failure"); 
      if ("saveAndContinue".equals(saveType)) {
        List list = taskBD.selectAllTaskClass(domainId, userId, orgIdString);
        httpServletRequest.setAttribute("taskId", taskId);
        httpServletRequest.setAttribute("rightScope", 
            getRightScope(userId, orgId, 
              "任务中心-任务安排", "新增"));
        httpServletRequest.setAttribute("allTaskClassList", list);
        httpServletRequest.setAttribute("isArranged", 
            httpServletRequest.getParameter(
              "isArranged"));
        taskActionForm.reset(actionMapping, httpServletRequest);
        return actionMapping.findForward("taskSub_saveAndContinue");
      } 
      if ("saveAndExit".equals(saveType))
        return actionMapping.findForward("taskSub_saveAndExit"); 
    } 
    if ("selectJoinTask".equals(action)) {
      String type = "";
      String sortType = "";
      if (httpServletRequest.getParameter("type") != null)
        type = httpServletRequest.getParameter("type"); 
      if (httpServletRequest.getParameter("sortType") != null)
        sortType = httpServletRequest.getParameter("sortType"); 
      int pageSize = 15;
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter(
              "pager.offset")); 
      int currentPage = offset / pageSize + 1;
      taskBD.setVolume(pageSize);
      List list = taskBD.selectSettleCheckTaskall(userId, 
          new Integer(currentPage), domainId, type, sortType);
      String recordCount = String.valueOf(taskBD.getRecordCount());
      List allTaskClassList = taskBD.selectAllTaskClass(domainId, userId, orgIdString);
      httpServletRequest.setAttribute("allTaskClassList", 
          allTaskClassList);
      httpServletRequest.setAttribute("allSettleCheckTaskList", list);
      httpServletRequest.setAttribute("recordCount", recordCount);
      httpServletRequest.setAttribute("maxPageItems", 
          String.valueOf(pageSize));
      httpServletRequest.setAttribute("pageParameters", "action,type,sortType");
      httpServletRequest.setAttribute("type", type);
      httpServletRequest.setAttribute("sortType", sortType);
      return actionMapping.findForward("goto_AllJoinTask");
    } 
    if ("selectAuditingTask".equals(action)) {
      int pageSize = 15;
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter(
              "pager.offset")); 
      int currentPage = offset / pageSize + 1;
      taskBD.setVolume(pageSize);
      List list = taskBD.selectAuditingTask(userId, 
          new Integer(currentPage), 
          domainId);
      String recordCount = String.valueOf(taskBD.getRecordCount());
      httpServletRequest.setAttribute("allAuditingTaskList", list);
      httpServletRequest.setAttribute("recordCount", recordCount);
      httpServletRequest.setAttribute("maxPageItems", 
          String.valueOf(pageSize));
      httpServletRequest.setAttribute("pageParameters", "action");
      return actionMapping.findForward("goto_AllAuditingTask");
    } 
    if ("selectCompleteTask".equals(action)) {
      String type = "";
      String sortType = "";
      if (httpServletRequest.getParameter("type") != null)
        type = httpServletRequest.getParameter("type"); 
      if (httpServletRequest.getParameter("sortType") != null)
        sortType = httpServletRequest.getParameter("sortType"); 
      int pageSize = 15;
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter(
              "pager.offset")); 
      int currentPage = offset / pageSize + 1;
      taskBD.setVolume(pageSize);
      List list = taskBD.selectCompleteTask(userId, 
          new Integer(currentPage), 
          domainId, type, sortType);
      String recordCount = String.valueOf(taskBD.getRecordCount());
      List allTaskClassList = taskBD.selectAllTaskClass(domainId, userId, orgIdString);
      httpServletRequest.setAttribute("allTaskClassList", 
          allTaskClassList);
      httpServletRequest.setAttribute("allCompleteTaskList", list);
      httpServletRequest.setAttribute("recordCount", recordCount);
      httpServletRequest.setAttribute("maxPageItems", 
          String.valueOf(pageSize));
      httpServletRequest.setAttribute("pageParameters", "action");
      httpServletRequest.setAttribute("type", type);
      httpServletRequest.setAttribute("sortType", sortType);
      return actionMapping.findForward("goto_AllCompleteTask");
    } 
    if ("taskPass".equals(action)) {
      Long taskId = Long.valueOf(httpServletRequest.getParameter("taskId"));
      boolean result = taskBD.taskPass(userId, taskId, domainId);
      if (!result)
        return actionMapping.findForward("failure"); 
      try {
        httpServletResponse.sendRedirect(
            "/jsoa/taskAction.do?action=selectAuditingTask");
      } catch (IOException ex) {
        ex.printStackTrace();
      } 
    } 
    if ("taskUnPass".equals(action)) {
      Long taskId = Long.valueOf(httpServletRequest.getParameter("taskId"));
      int offset1 = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset"));
      boolean result = taskBD.taskUnPass(userId, taskId, domainId);
      if (!result)
        return actionMapping.findForward("failure"); 
      int pageSize = 2;
      int offset = offset1;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter(
              "pager.offset")); 
      int currentPage = offset / pageSize + 1;
      taskBD.setVolume(pageSize);
      List list = taskBD.selectAuditingTask(userId, 
          new Integer(currentPage), domainId);
      String recordCount = String.valueOf(taskBD.getRecordCount());
      httpServletRequest.setAttribute("allAuditingTaskList", list);
      httpServletRequest.setAttribute("recordCount", recordCount);
      httpServletRequest.setAttribute("maxPageItems", 
          String.valueOf(pageSize));
      httpServletRequest.setAttribute("pageParameters", "action");
      return actionMapping.findForward("goto_TaskUnPass");
    } 
    if ("selectSingleTask".equals(action)) {
      Long taskId = Long.valueOf(httpServletRequest.getParameter("taskId"));
      taskBD.deleteremind(taskId.toString(), userId.toString(), 
          domainId.toString());
      Map map = taskBD.selectSingleTask(userId, taskId);
      TaskVO taskVO = (TaskVO)map.get("taskVO");
      taskActionForm.setTaskTitle(taskVO.getTaskTitle());
      taskActionForm.setTaskPrincipalName(taskVO.getTaskPrincipalName());
      taskActionForm.setTaskPrincipal(taskVO.getTaskPrincipal());
      taskActionForm.setTaskCheckerName(taskVO.getTaskCheckerName());
      taskActionForm.setTaskChecker(taskVO.getTaskChecker());
      taskActionForm.setTaskPriority(taskVO.getTaskPriority());
      taskActionForm.setTaskStatus(taskVO.getTaskStatus());
      httpServletRequest.setAttribute("taskStatus", taskVO.getTaskStatus());
      taskActionForm.setTaskPrincipal(taskVO.getTaskPrincipal());
      taskActionForm.setTaskPrincipalName(taskVO.getTaskPrincipalName());
      taskActionForm.setTaskJoinedEmpName(taskVO.getTaskJoinedEmpName());
      taskActionForm.setTaskJoinedEmp(taskVO.getTaskJoinedEmp());
      taskActionForm.setTaskJoinOrgName(taskVO.getTaskJoinOrgName());
      taskActionForm.setTaskJoineOrg(taskVO.getTaskJoineOrg());
      taskActionForm.setTaskDescription(taskVO.getTaskDescription());
      taskActionForm.setTaskCancelReason(taskVO.getTaskCancelReason());
      Long taskAwakeTime = taskVO.getTaskAwakeTime();
      httpServletRequest.setAttribute("relProjectId", taskVO.getRelProjectId());
      if (!taskAwakeTime.equals(new Long(0L)))
        taskActionForm.setIfAwake("checkbox"); 
      String taskType = taskVO.getTaskType();
      String start_date = taskVO.getTaskBeginTimeFormat();
      String end_date = taskVO.getTaskEndTimeFormat();
      String testes = "";
      if (httpServletRequest.getParameter("maintenance") != null)
        testes = httpServletRequest.getParameter("maintenance"); 
      Boolean maintenance = Boolean.FALSE;
      if (testes.equals("true")) {
        maintenance = Boolean.valueOf(testes);
      } else {
        maintenance = taskVO.getMaintenance();
      } 
      httpServletRequest.setAttribute("maintenance", maintenance);
      List list = taskBD.selectAllTaskClass(domainId, userId, orgIdString);
      Map map2 = taskBD.selectTaskReport(userId, taskId, domainId);
      List list2 = (List)map2.get("taskReportList");
      List accessoryList = (List)map.get("accessory");
      List reportAccessoryList = (List)map2.get("reportaccessory");
      List taskhistory = null;
      taskhistory = taskBD.getHistoryInfo(taskId.toString());
      httpServletRequest.setAttribute("taskhistory", taskhistory);
      httpServletRequest.setAttribute("accessoryList", accessoryList);
      httpServletRequest.setAttribute("reportAccessoryList", 
          reportAccessoryList);
      httpServletRequest.setAttribute("isArranged", taskVO.getIsArranged());
      httpServletRequest.setAttribute("taskId", taskId);
      httpServletRequest.setAttribute("allTaskClassList", list);
      httpServletRequest.setAttribute("allTaskReportList", list2);
      httpServletRequest.setAttribute("taskType", taskType);
      httpServletRequest.setAttribute("start_date", start_date);
      httpServletRequest.setAttribute("end_date", end_date);
      httpServletRequest.setAttribute("taskAwakeTime", taskAwakeTime);
      httpServletRequest.setAttribute("taskCheckerName", taskVO.getTaskCheckerName());
      httpServletRequest.setAttribute("taskAppend", taskVO.getTaskAppend());
      httpServletRequest.setAttribute("createdEmp", taskVO.getCreatedEmp());
      httpServletRequest.setAttribute("boardroomApplyId", taskVO.getTaskPO().getMeetingId());
      if (httpServletRequest.getParameter("toUserId") != null)
        httpServletRequest.setAttribute("toUserId", httpServletRequest.getParameter("toUserId")); 
      selectSubPrincipalTask(taskId, httpServletRequest);
      return actionMapping.findForward("goto_TaskUpdate");
    } 
    if ("selectSettleSingleTask".equals(action)) {
      String flag = httpServletRequest.getParameter("flag");
      httpServletRequest.setAttribute("flag", flag);
      Long taskId = Long.valueOf(httpServletRequest.getParameter("taskId"));
      TaskVO taskVO = taskBD.selectSettleSingleTask(userId, taskId, 
          domainId);
      taskActionForm.setTaskTitle(taskVO.getTaskTitle());
      taskActionForm.setTaskPrincipalName(taskVO.getTaskPrincipalName());
      taskActionForm.setTaskPrincipal(taskVO.getTaskPrincipal());
      taskActionForm.setTaskCheckerName(taskVO.getTaskCheckerName());
      taskActionForm.setTaskChecker(taskVO.getTaskChecker());
      taskActionForm.setTaskPriority(taskVO.getTaskPriority());
      taskActionForm.setTaskStatus(taskVO.getTaskStatus());
      httpServletRequest.setAttribute("taskStatus", taskVO.getTaskStatus());
      taskActionForm.setTaskPrincipal(taskVO.getTaskPrincipal());
      taskActionForm.setTaskPrincipalName(taskVO.getTaskPrincipalName());
      taskActionForm.setTaskJoinedEmpName(taskVO.getTaskJoinedEmpName());
      taskActionForm.setTaskJoinedEmp(taskVO.getTaskJoinedEmp());
      taskActionForm.setTaskJoinOrgName(taskVO.getTaskJoinOrgName());
      taskActionForm.setTaskJoineOrg(taskVO.getTaskJoineOrg());
      taskActionForm.setTaskDescription(taskVO.getTaskDescription());
      Long taskAwakeTime = taskVO.getTaskAwakeTime();
      if (!taskAwakeTime.equals(new Long(0L)))
        taskActionForm.setIfAwake("checkbox"); 
      String taskType = taskVO.getTaskType();
      String start_date = taskVO.getTaskBeginTimeFormat();
      String end_date = taskVO.getTaskEndTimeFormat();
      Boolean maintenance = taskVO.getMaintenance();
      List list = taskBD.selectAllTaskClass(domainId, userId, orgIdString);
      Map map2 = taskBD.selectTaskReport(userId, taskId, domainId);
      List list2 = (List)map2.get("taskReportList");
      httpServletRequest.setAttribute("taskId", taskId);
      httpServletRequest.setAttribute("allTaskClassList", list);
      httpServletRequest.setAttribute("allTaskReportList", list2);
      httpServletRequest.setAttribute("relProjectId", taskVO.getRelProjectId());
      Map map = taskBD.selectSingleTask(userId, taskId);
      List accessoryList = (List)map.get("accessory");
      List reportAccessoryList = (List)map2.get("reportaccessory");
      httpServletRequest.setAttribute("accessoryList", accessoryList);
      httpServletRequest.setAttribute("reportAccessoryList", 
          reportAccessoryList);
      httpServletRequest.setAttribute("taskType", taskType);
      httpServletRequest.setAttribute("start_date", start_date);
      httpServletRequest.setAttribute("end_date", end_date);
      httpServletRequest.setAttribute("taskAwakeTime", taskAwakeTime);
      httpServletRequest.setAttribute("maintenance", maintenance);
      httpServletRequest.setAttribute("boardroomApplyId", taskVO.getTaskPO().getMeetingId());
      String isArranged = "0";
      if (httpServletRequest.getParameter("isArranged") != null)
        isArranged = httpServletRequest.getParameter("isArranged"); 
      httpServletRequest.setAttribute("isArranged", isArranged);
      List taskhistory = null;
      taskhistory = taskBD.getHistoryInfo(taskId.toString());
      httpServletRequest.setAttribute("taskhistory", taskhistory);
      selectSubPrincipalTask(taskId, httpServletRequest);
      return actionMapping.findForward("goto_TaskUpdate");
    } 
    if ("modifyTask".equals(action)) {
      TaskVO taskVO = (TaskVO)FillBean.transformOneToOne(taskActionForm, 
          TaskVO.class);
      Long TaskChecker = taskActionForm.getTaskChecker();
      String TaskCheckerName = taskActionForm.getTaskCheckerName();
      Long taskId = Long.valueOf(httpServletRequest.getParameter("taskId"));
      taskVO.setTaskId(taskId);
      Long taskAwakeTime = Long.valueOf(httpServletRequest.getParameter(
            "taskAwakeTime"));
      taskVO.setTaskAwakeTime(taskAwakeTime);
      Date startTime = new Date(httpServletRequest.getParameter(
            "start_date"));
      taskVO.setTaskBeginTime(startTime);
      Date endTime = new Date(httpServletRequest.getParameter("end_date"));
      taskVO.setTaskEndTime(endTime);
      String taskType = httpServletRequest.getParameter("taskType");
      taskVO.setTaskType(taskType);
      if (httpServletRequest.getParameter("relproject") != null)
        taskVO.setRelProjectId(Long.valueOf(httpServletRequest.getParameter("relproject"))); 
      String ifAwake = taskActionForm.getIfAwake();
      if ("checkbox".equals(ifAwake)) {
        taskVO.setTaskAwakeTime(taskAwakeTime);
      } else {
        taskVO.setTaskAwakeTime(new Long(0L));
      } 
      String maintenance = httpServletRequest.getParameter("maintenance");
      TaskPO taskPO = new TaskPO();
      String meetingId = (httpServletRequest.getParameter("meetingId") == null) ? "-1" : httpServletRequest.getParameter("meetingId");
      taskPO.setMeetingId(Long.valueOf(meetingId));
      HashSet<TaskAccessoryPO> Accessory = new HashSet();
      String[] fileName = httpServletRequest.getParameterValues(
          "taskFileName");
      String[] saveName = httpServletRequest.getParameterValues(
          "taskSaveName");
      if (fileName != null)
        for (int i = 0; i < fileName.length; i++) {
          if (!"".equals(fileName[i])) {
            TaskAccessoryPO taskAccessoryPO = new TaskAccessoryPO();
            taskAccessoryPO.setAccessoryname(fileName[i]);
            taskAccessoryPO.setAccessorysavename(saveName[i]);
            taskAccessoryPO.setDomain_id(domainId);
            Accessory.add(taskAccessoryPO);
          } 
        }  
      taskPO.setTaskAccessory(Accessory);
      taskVO.setTaskPO(taskPO);
      String joineOrgId = "";
      String temps = "";
      if (taskVO.getTaskJoinedEmpName() != null) {
        if (!"".equals(taskVO.getTaskJoinedEmpName().toString()))
          try {
            joineOrgId = (new ConversionString(taskVO
                .getTaskJoinedEmp())).getOrgIdString();
          } catch (Exception e) {
            joineOrgId = "";
          }  
        temps = (new ConversionString(taskVO.getTaskJoinedEmp()))
          .getUserIdString();
      } 
      String taskJoineOrg = "";
      if (!joineOrgId.equals(""))
        taskJoineOrg = taskBD.selectUserIds(joineOrgId); 
      if ("".equals(taskJoineOrg)) {
        taskJoineOrg = temps;
      } else {
        taskJoineOrg = String.valueOf(taskJoineOrg) + "," + temps;
      } 
      taskVO.setTaskJoineOrg(taskJoineOrg);
      boolean results = taskBD.saveHistory(taskId.toString(), 
          userId.toString(), 
          userName.toString(), 
          orgId.toString(), 
          orgName.toString());
      String joinEmp = taskBD.selectSettleSingleTask(userId, taskId, 
          domainId).getTaskJoinedEmp();
      String princp = taskBD.selectSettleSingleTask(userId, taskId, 
          domainId).getTaskPrincipal().toString();
      ManagerService mbd = new ManagerService();
      String receivers = "";
      if (joinEmp != null && 
        !"".equals((new ConversionString(joinEmp)).getUserIdString()))
        receivers = mbd.getEmployeesAccounts((new ConversionString(
              joinEmp)).getUserIdString()); 
      Object[] obj = (new UserBD()).getUserInfo(userId).get(0);
      if (!receivers.equals("")) {
        receivers = String.valueOf(receivers) + "," + obj[2].toString();
      } else {
        receivers = obj[2].toString();
      } 
      taskBD.deleteremindall(taskId.toString(), domainId.toString());
      boolean result = taskBD.modifyTask(taskVO, userName, String.valueOf(userId));
      if (!result)
        return actionMapping.findForward("failure"); 
      if ("updateAndExit".equals(saveType)) {
        List list = taskBD.selectAllTaskClass(domainId, userId, orgIdString);
        httpServletRequest.setAttribute("taskId", taskId);
        httpServletRequest.setAttribute("allTaskClassList", list);
        httpServletRequest.setAttribute("taskType", taskType);
        httpServletRequest.setAttribute("start_date", "1900-01-01");
        httpServletRequest.setAttribute("end_date", "1900-01-01");
        httpServletRequest.setAttribute("taskAwakeTime", taskAwakeTime);
        httpServletRequest.setAttribute("maintenance", maintenance);
        if (httpServletRequest.getParameter("toUserId") != null)
          httpServletRequest.setAttribute("toUserId", httpServletRequest.getParameter("toUserId")); 
        return actionMapping.findForward("task_updateAndExit");
      } 
    } 
    if ("deleteTask".equals(action)) {
      String type = "";
      String sortType = "";
      int offset1 = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset").toString());
      boolean result = true;
      if (httpServletRequest.getParameter("taskId") != null && 
        !"".equals(httpServletRequest.getParameter("taskId").trim())) {
        Long taskId = Long.valueOf(httpServletRequest.getParameter(
              "taskId"));
        result = taskBD.deleteTask(taskId);
      } 
      if (!result)
        return actionMapping.findForward("failure"); 
      if (httpServletRequest.getParameter("deletefrom") != null && 
        httpServletRequest.getParameter("deletefrom").equals(
          "mytaskcancel")) {
        List allTaskClassList = taskBD.selectAllTaskClass(domainId, userId, orgIdString);
        httpServletRequest.setAttribute("allTaskClassList", 
            allTaskClassList);
        int i = 15;
        int j = 0;
        if (httpServletRequest.getParameter("pager.offset") != null)
          j = Integer.parseInt(httpServletRequest.getParameter(
                "pager.offset")); 
        int k = j / i + 1;
        taskBD.setVolume(i);
        List list1 = taskBD.selectCancelTask(userId, 
            new Integer(k), 
            domainId, type, sortType);
        String str = String.valueOf(taskBD.getRecordCount());
        httpServletRequest.setAttribute("allTaskList", list1);
        httpServletRequest.setAttribute("recordCount", str);
        httpServletRequest.setAttribute("maxPageItems", 
            String.valueOf(i));
        httpServletRequest.setAttribute("pageParameters", 
            "action,deletefrom");
        httpServletRequest.setAttribute("sortType", sortType);
        httpServletRequest.setAttribute("type", type);
        return actionMapping.findForward("goto_AllCancelTask");
      } 
      if (httpServletRequest.getParameter("deletefrom") != null && 
        httpServletRequest.getParameter("deletefrom").equals(
          "tasksortcancel")) {
        List allTaskClassList = taskBD.selectAllTaskClass(domainId, userId, orgIdString);
        httpServletRequest.setAttribute("allTaskClassList", 
            allTaskClassList);
        int i = 15;
        int j = 0;
        if (httpServletRequest.getParameter("pager.offset") != null)
          j = Integer.parseInt(httpServletRequest.getParameter(
                "pager.offset")); 
        int k = j / i + 1;
        taskBD.setVolume(i);
        List list1 = taskBD.selectsettleCancelTask(userId, 
            new Integer(k), domainId, type, sortType);
        String str1 = String.valueOf(taskBD.getRecordCount());
        httpServletRequest.setAttribute("allTaskList", list1);
        httpServletRequest.setAttribute("recordCount", str1);
        httpServletRequest.setAttribute("maxPageItems", 
            String.valueOf(i));
        httpServletRequest.setAttribute("pageParameters", 
            "action,deletefrom");
        httpServletRequest.setAttribute("sortType", sortType);
        httpServletRequest.setAttribute("type", type);
        String tag = "goto_AllSettleCancelTask";
        return actionMapping.findForward("goto_AllSettleCancelTask");
      } 
      int pageSize = 15;
      int offset = offset1;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter(
              "pager.offset")); 
      int currentPage = offset / pageSize + 1;
      taskBD.setVolume(pageSize);
      List list = taskBD.selectPrincipalTask(userId, 
          new Integer(currentPage), domainId, type, sortType);
      String recordCount = String.valueOf(taskBD.getRecordCount());
      httpServletRequest.setAttribute("allTaskList", list);
      httpServletRequest.setAttribute("recordCount", recordCount);
      httpServletRequest.setAttribute("maxPageItems", 
          String.valueOf(pageSize));
      httpServletRequest.setAttribute("pageParameters", "action");
      if (httpServletRequest.getParameter("flag") == null)
        return actionMapping.findForward("goto_TaskList"); 
      return actionMapping.findForward("close");
    } 
    if ("deletejoinTask".equals(action)) {
      String type = "";
      String sortType = "";
      Long taskId = Long.valueOf(httpServletRequest.getParameter("taskId"));
      int offset1 = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset"));
      boolean result = taskBD.deleteTask(taskId);
      if (!result)
        return actionMapping.findForward("failure"); 
      int pageSize = 15;
      int offset = offset1;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter(
              "pager.offset")); 
      int currentPage = offset / pageSize + 1;
      taskBD.setVolume(pageSize);
      List list = taskBD.selectPrincipalTask(userId, 
          new Integer(currentPage), domainId, type, sortType);
      String recordCount = String.valueOf(taskBD.getRecordCount());
      httpServletRequest.setAttribute("allTaskList", list);
      httpServletRequest.setAttribute("recordCount", recordCount);
      httpServletRequest.setAttribute("maxPageItems", 
          String.valueOf(pageSize));
      httpServletRequest.setAttribute("pageParameters", "action");
      return actionMapping.findForward("goto_JoinTasklist");
    } 
    if ("taskReportView".equals(action)) {
      Long taskId = Long.valueOf(httpServletRequest.getParameter("taskId"));
      boolean result = taskBD.isPrincipal(userId, taskId);
      if (!result) {
        httpServletRequest.setAttribute("isPrincipal", "No");
      } else {
        httpServletRequest.setAttribute("isPrincipal", "Yes");
      } 
      Map map = taskBD.selectSingleTask(userId, taskId);
      TaskVO taskVO = (TaskVO)map.get("taskVO");
      Map map2 = taskBD.selectTaskReport(userId, taskId, domainId);
      List list2 = (List)map2.get("taskReportList");
      httpServletRequest.setAttribute("taskVO", taskVO);
      httpServletRequest.setAttribute("taskReportList", list2);
      httpServletRequest.setAttribute("taskId", taskId);
      return actionMapping.findForward("taskReportView");
    } 
    if ("taskReport".equals(action)) {
      String isPrincipal = httpServletRequest.getParameter("isPrincipal");
      String reportInfo = httpServletRequest.getParameter("reportInfo");
      String reportAppend = httpServletRequest.getParameter(
          "reportAppend");
      Long taskId = Long.valueOf(httpServletRequest.getParameter("taskId"));
      Map m = taskBD.selectSingleTask(userId, taskId);
      TaskVO vo = (TaskVO)m.get("taskVO");
      httpServletRequest.setAttribute("taskVO", vo);
      Integer finishRate = new Integer(0);
      if (httpServletRequest.getParameter("finishRate") != null && 
        !"".equals(httpServletRequest.getParameter("finishRate")))
        finishRate = Integer.valueOf(httpServletRequest.getParameter(
              "finishRate")); 
      String res = "0";
      if (finishRate.intValue() == 100)
        res = taskBD.getchildtasksta(taskId.toString()); 
      if ("0".equals(res)) {
        TaskReportVO taskreportVO = new TaskReportVO();
        taskreportVO.setEmpId(userId);
        taskreportVO.setTrDomainId(domainId);
        HashSet<TaskReportAccessoryPO> Accessory = new HashSet();
        String[] fileName = httpServletRequest.getParameterValues(
            "taskReportFileName");
        String[] saveName = httpServletRequest.getParameterValues(
            "taskReportSaveName");
        if (fileName != null)
          for (int i = 0; i < fileName.length; i++) {
            if (!"".equals(fileName[i])) {
              TaskReportAccessoryPO taskReportAccessoryPO = 
                new TaskReportAccessoryPO();
              taskReportAccessoryPO.setAccessoryname(fileName[i]);
              taskReportAccessoryPO.setAccessorysavename(saveName[
                    i]);
              taskReportAccessoryPO.setDomain_id(domainId);
              Accessory.add(taskReportAccessoryPO);
            } 
          }  
        TaskReportPO taskReportPO = new TaskReportPO();
        taskReportPO.setTaskReportAccessory(Accessory);
        if ("Yes".equals(isPrincipal)) {
          Integer taskstatus = Integer.valueOf(httpServletRequest
              .getParameter("taskstatus"));
          String finishTime = httpServletRequest.getParameter(
              "finishTime");
          if (finishRate.intValue() != 100) {
            taskreportVO.setTaskId(taskId);
            taskreportVO.setFinishRate(finishRate);
            taskreportVO.setReportInfo(reportInfo);
            taskreportVO.setReportAppend(reportAppend);
            taskreportVO.setTrDomainId(domainId);
            boolean bool1 = taskBD.addTaskReport(taskreportVO, 
                taskstatus, "", taskReportPO);
            if (!bool1)
              return actionMapping.findForward("failure"); 
            httpServletRequest.setAttribute("isPrincipal", 
                isPrincipal);
            httpServletRequest.setAttribute("taskId", taskId);
            return actionMapping.findForward(
                "taskReportAddView");
          } 
          taskreportVO.setTaskId(taskId);
          taskreportVO.setFinishRate(finishRate);
          taskreportVO.setReportInfo(reportInfo);
          taskreportVO.setReportAppend(reportAppend);
          boolean bool = taskBD.addTaskReport(taskreportVO, 
              taskstatus, finishTime, taskReportPO);
          taskBD.deleteremindall(taskId.toString(), 
              domainId.toString());
          if (!bool)
            return actionMapping.findForward("failure"); 
          Map map = taskBD.selectSingleTask(userId, taskId);
          TaskVO taskVO = (TaskVO)map.get("taskVO");
          String userids = "";
          if (taskVO.getTaskChecker() != null && !String.valueOf(taskVO.getTaskChecker()).equals(String.valueOf(userId)))
            userids = String.valueOf(userids) + taskVO.getTaskChecker() + ","; 
          if (!"".equals(userids)) {
            userids = userids.substring(0, userids.trim().length() - 1);
            String title = String.valueOf(taskVO.getTaskTitle()) + "任务完成了等待您的考核";
            String url = "/jsoa/taskAction.do?action=selectSingleTask&cansees=1&taskId=" + taskVO.getTaskId() + "&isArranged=1";
            Calendar calendar = Calendar.getInstance();
            calendar.set(2050, 12, 12);
            RemindUtil.sendMessageToUsers(title, url, userids, "TaskKaohe", new Date(), calendar.getTime(), taskVO.getTaskPrincipalName(), taskVO.getTaskId());
          } 
          httpServletRequest.setAttribute("isPrincipal", 
              isPrincipal);
          httpServletRequest.setAttribute("taskId", taskId);
          return actionMapping.findForward(
              "taskReportAddView");
        } 
        boolean result = taskBD.addTaskReport(userId, taskId, 
            reportInfo, domainId, taskReportPO);
        if (!result)
          return actionMapping.findForward("failure"); 
        httpServletRequest.setAttribute("res", res);
        httpServletRequest.setAttribute("isPrincipal", 
            isPrincipal);
        httpServletRequest.setAttribute("taskId", taskId);
        return actionMapping.findForward("taskReportAddView");
      } 
      httpServletRequest.setAttribute("res", "1");
      httpServletRequest.setAttribute("isPrincipal", isPrincipal);
      httpServletRequest.setAttribute("taskId", taskId);
      return actionMapping.findForward("taskReportAddView");
    } 
    if ("selectSettleTask".equals(action)) {
      String type = "";
      String sortType = "";
      if (httpServletRequest.getParameter("type") != null)
        type = httpServletRequest.getParameter("type"); 
      if (httpServletRequest.getParameter("sortType") != null)
        sortType = httpServletRequest.getParameter("sortType"); 
      int pageSize = 15;
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter(
              "pager.offset")); 
      boolean newTaskSettle = taskBD.hasRight(userId, "任务中心-任务安排", "新增");
      if (!newTaskSettle) {
        httpServletRequest.setAttribute("newTaskSettle", "false");
      } else {
        httpServletRequest.setAttribute("newTaskSettle", "true");
      } 
      int currentPage = offset / pageSize + 1;
      taskBD.setVolume(pageSize);
      List list = taskBD.selectSettleTask(userId, new Integer(currentPage), 
          domainId, type, sortType);
      String recordCount = String.valueOf(taskBD.getRecordCount());
      List allTaskClassList = taskBD.selectAllTaskClass(domainId, userId, orgIdString);
      httpServletRequest.setAttribute("allTaskClassList", 
          allTaskClassList);
      httpServletRequest.setAttribute("allSettleTaskList", list);
      httpServletRequest.setAttribute("recordCount", recordCount);
      httpServletRequest.setAttribute("maxPageItems", 
          String.valueOf(pageSize));
      httpServletRequest.setAttribute("pageParameters", "action,type,sortType,taskTitle,taskFinishRate,taskPriority,taskType,taskStatus,startTaskBeginTime,endTaskBeginTime,isBeginDate,startTaskEndTime,endTaskEndTime,isEndDate");
      httpServletRequest.setAttribute("type", type);
      httpServletRequest.setAttribute("sortType", sortType);
      return actionMapping.findForward("goto_AllSettleTask");
    } 
    if ("selectSettleCheckTask".equals(action)) {
      String type = "";
      String sortType = "";
      if (httpServletRequest.getParameter("type") != null)
        type = httpServletRequest.getParameter("type"); 
      if (httpServletRequest.getParameter("sortType") != null)
        sortType = httpServletRequest.getParameter("sortType"); 
      int pageSize = 15;
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter(
              "pager.offset")); 
      int currentPage = offset / pageSize + 1;
      taskBD.setVolume(pageSize);
      List list = taskBD.selectSettleCheckTask(userId, 
          new Integer(currentPage), domainId, type, sortType);
      String recordCount = String.valueOf(taskBD.getRecordCount());
      httpServletRequest.setAttribute("allSettleCheckTaskList", list);
      List allTaskClassList = taskBD.selectAllTaskClass(domainId, userId, orgIdString);
      httpServletRequest.setAttribute("allTaskClassList", 
          allTaskClassList);
      httpServletRequest.setAttribute("recordCount", recordCount);
      httpServletRequest.setAttribute("maxPageItems", 
          String.valueOf(pageSize));
      httpServletRequest.setAttribute("pageParameters", "action,type,sortType");
      httpServletRequest.setAttribute("type", type);
      httpServletRequest.setAttribute("sortType", sortType);
      return actionMapping.findForward("goto_AllSettleCheckTask");
    } 
    if ("selectSettleFinishTask".equals(action)) {
      String type = "";
      String sortType = "";
      if (httpServletRequest.getParameter("type") != null)
        type = httpServletRequest.getParameter("type"); 
      if (httpServletRequest.getParameter("sortType") != null)
        sortType = httpServletRequest.getParameter("sortType"); 
      int pageSize = 15;
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter(
              "pager.offset")); 
      int currentPage = offset / pageSize + 1;
      taskBD.setVolume(pageSize);
      List list = taskBD.selectSettleFinishTask(userId, 
          new Integer(currentPage), domainId, type, sortType);
      String recordCount = String.valueOf(taskBD.getRecordCount());
      httpServletRequest.setAttribute("allSettleFinishTaskList", list);
      List allTaskClassList = taskBD.selectAllTaskClass(domainId, userId, orgIdString);
      httpServletRequest.setAttribute("allTaskClassList", 
          allTaskClassList);
      httpServletRequest.setAttribute("recordCount", recordCount);
      httpServletRequest.setAttribute("maxPageItems", 
          String.valueOf(pageSize));
      httpServletRequest.setAttribute("pageParameters", "action,type,sortType");
      httpServletRequest.setAttribute("type", type);
      httpServletRequest.setAttribute("sortType", sortType);
      return actionMapping.findForward("goto_AllSettleFinishTask");
    } 
    if ("addTaskSettleView".equals(action)) {
      List list = taskBD.selectAllTaskClass(domainId, userId, orgIdString);
      httpServletRequest.setAttribute("rightScope", 
          getRightScope(userId, orgId, "任务中心-任务安排", "新增"));
      httpServletRequest.setAttribute("allTaskClassList", list);
      return actionMapping.findForward("addTaskSettleView");
    } 
    if ("addTaskSettle".equals(action)) {
      TaskPO taskPO = new TaskPO();
      TaskVO taskVO = (TaskVO)FillBean.transformOneToOne(taskActionForm, TaskVO.class);
      Long taskAwakeTime = Long.valueOf(httpServletRequest.getParameter("taskAwakeTime"));
      taskVO.setCreatedEmp(userId);
      taskVO.setTaskDomainId(domainId);
      taskVO.setCreatedEmpName(userName);
      taskVO.setCreatedOrg(orgId);
      taskVO.setCreatedOrgName(orgName);
      taskVO.setIsArranged(new Integer(1));
      taskVO.setStickie(Long.valueOf(0L));
      taskVO.setAnpaiStickie(Long.valueOf(0L));
      String ifAwake = taskActionForm.getIfAwake();
      if ("checkbox".equals(ifAwake)) {
        taskVO.setTaskAwakeTime(taskAwakeTime);
      } else {
        taskVO.setTaskAwakeTime(new Long(0L));
      } 
      taskVO.setTaskCreatedTime(new Date());
      Date startTime = new Date(httpServletRequest.getParameter("start_date"));
      Date endTime = new Date(httpServletRequest.getParameter("end_date"));
      taskVO.setTaskBeginTime(startTime);
      taskVO.setTaskEndTime(endTime);
      String taskType = httpServletRequest.getParameter("taskType");
      taskVO.setTaskType(taskType);
      taskVO.setParentIdString("-1");
      String taskJoinedEmpName = httpServletRequest.getParameter(
          "taskJoinedEmpName");
      String joineOrgId = "";
      if (!taskJoinedEmpName.equals(""))
        try {
          joineOrgId = (new ConversionString(taskVO.getTaskJoinedEmp()))
            .getOrgIdString();
        } catch (Exception e) {
          joineOrgId = "";
        }  
      String taskJoineOrg = "";
      if (!joineOrgId.equals(""))
        taskJoineOrg = taskBD.selectUserIds(joineOrgId); 
      String temps = (new ConversionString(taskVO.getTaskJoinedEmp())).getUserIdString();
      if ("".equals(taskJoineOrg)) {
        taskJoineOrg = temps;
      } else {
        taskJoineOrg = String.valueOf(taskJoineOrg) + "," + temps;
      } 
      taskVO.setTaskJoineOrg(taskJoineOrg);
      if (httpServletRequest.getParameter("relproject") != null)
        taskVO.setRelProjectId(Long.valueOf(httpServletRequest.getParameter("relproject"))); 
      List list = taskBD.selectAllTaskClass(domainId, userId, orgIdString);
      HashSet<TaskAccessoryPO> Accessory = new HashSet();
      String[] fileName = httpServletRequest.getParameterValues(
          "taskFileName");
      String[] saveName = httpServletRequest.getParameterValues(
          "taskSaveName");
      if (fileName != null)
        for (int i = 0; i < fileName.length; i++) {
          if (!"".equals(fileName[i])) {
            TaskAccessoryPO taskAccessoryPO = new TaskAccessoryPO();
            taskAccessoryPO.setAccessoryname(fileName[i]);
            taskAccessoryPO.setAccessorysavename(saveName[i]);
            taskAccessoryPO.setDomain_id(domainId);
            Accessory.add(taskAccessoryPO);
          } 
        }  
      String meetingId = (httpServletRequest.getParameter("meetingId") == null) ? "-1" : httpServletRequest.getParameter("meetingId");
      taskPO.setMeetingId(Long.valueOf(meetingId));
      taskPO.setTaskAccessory(Accessory);
      taskVO.setTaskPO(taskPO);
      boolean result = taskBD.addTask(taskVO);
      if (!result)
        return actionMapping.findForward("failure"); 
      if ("saveAndContinue".equals(saveType)) {
        httpServletRequest.setAttribute("rightScope", 
            getRightScope(userId, orgId, 
              "任务中心-任务安排", "新增"));
        httpServletRequest.setAttribute("allTaskClassList", list);
        taskActionForm.reset(actionMapping, httpServletRequest);
        return actionMapping.findForward("taskSettle_saveAndContinue");
      } 
      if ("saveAndExit".equals(saveType)) {
        httpServletRequest.setAttribute("rightScope", 
            getRightScope(userId, orgId, 
              "任务中心-任务安排", "新增"));
        httpServletRequest.setAttribute("allTaskClassList", list);
        return actionMapping.findForward("taskSettle_saveAndExit");
      } 
    } 
    if ("cancelReason".equals(action)) {
      Long taskId = Long.valueOf(httpServletRequest.getParameter("taskId"));
      String taskType = httpServletRequest.getParameter("taskType");
      httpServletRequest.setAttribute("taskId", taskId);
      httpServletRequest.setAttribute("taskType", taskType);
      return actionMapping.findForward("task_cancelReason");
    } 
    if ("cancelTask".equals(action)) {
      Long taskId = Long.valueOf(httpServletRequest.getParameter("taskId"));
      String taskType = httpServletRequest.getParameter("taskType");
      String reason = httpServletRequest.getParameter("reason");
      boolean result = taskBD.cancelTask(taskId, domainId, reason);
      if (!result)
        return actionMapping.findForward("failure"); 
      String joinEmp = taskBD.selectSettleSingleTask(userId, taskId, 
          domainId).getTaskJoinedEmp();
      String princp = taskBD.selectSettleSingleTask(userId, taskId, 
          domainId).getTaskPrincipal().toString();
      ManagerService mbd = new ManagerService();
      String receivers = "";
      if (joinEmp != null && 
        !"".equals((new ConversionString(joinEmp)).getUserIdString()))
        receivers = mbd.getEmployeesAccounts((new ConversionString(
              joinEmp)).getUserIdString()); 
      Object[] obj = (new UserBD()).getUserInfo(userId)
        .get(0);
      if (!receivers.equals("")) {
        receivers = String.valueOf(receivers) + "," + obj[2].toString();
      } else {
        receivers = obj[2].toString();
      } 
      receivers.equals("");
      taskBD.deleteremindall(taskId.toString(), domainId.toString());
      httpServletRequest.setAttribute("taskId", taskId);
      httpServletRequest.setAttribute("taskType", taskType);
      httpServletRequest.setAttribute("saveType", "ok");
      return actionMapping.findForward("task_cancelReason");
    } 
    if ("cancelMyTask".equals(action)) {
      Long taskId = Long.valueOf(httpServletRequest.getParameter("taskId"));
      String taskType = httpServletRequest.getParameter("taskType");
      String reason = httpServletRequest.getParameter("reason");
      boolean result = taskBD.cancelTask(taskId, domainId, reason);
      taskBD.deleteremindall(taskId.toString(), domainId.toString());
      if (!result)
        return actionMapping.findForward("failure"); 
      try {
        TaskVO task = taskBD.selectSettleSingleTask(userId, taskId, 
            domainId);
        String joinEmp = taskBD.selectSettleSingleTask(userId, 
            taskId, 
            domainId).getTaskJoinedEmp();
        String princp = task.getTaskPrincipal().toString();
        ManagerService mbd = new ManagerService();
        String receivers = "";
        if (joinEmp != null)
          if (!"".equals((new ConversionString(joinEmp))
              .getUserIdString()))
            receivers = mbd.getEmployeesAccounts((
                new ConversionString(
                  joinEmp)).getUserIdString());  
        Object[] obj = (new UserBD()).getUserInfo(userId)
          .get(0);
        if (!receivers.equals("")) {
          receivers = String.valueOf(receivers) + "," + obj[2].toString();
        } else {
          receivers = obj[2].toString();
        } 
        receivers.equals("");
        if ("SettleTask".equals(taskType))
          return actionMapping.findForward("SettleTaskgo"); 
        return actionMapping.findForward("PrincipalTaskgo");
      } catch (Exception exception) {}
    } 
    if ("deleteTaskSettle".equals(action)) {
      Long taskId = Long.valueOf(httpServletRequest.getParameter("taskId"));
      boolean result = taskBD.deleteTask(taskId);
      taskBD.deleteremindall(taskId.toString(), domainId.toString());
      if (!result)
        return actionMapping.findForward("failure"); 
      return actionMapping.findForward("goto_TaskSettleList");
    } 
    if ("searchPrincipalTask".equals(action)) {
      int pageSize = 15;
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter(
              "pager.offset")); 
      int currentPage = offset / pageSize + 1;
      taskBD.setVolume(pageSize);
      String type = "";
      String sortType = "";
      if (httpServletRequest.getParameter("type") != null)
        type = httpServletRequest.getParameter("type"); 
      if (httpServletRequest.getParameter("sortType") != null)
        sortType = httpServletRequest.getParameter("sortType"); 
      String taskTitle = "";
      String taskFinishRate = "";
      String taskType = "";
      String taskPriority = "";
      String taskStatus = "";
      String isEndDate = "";
      String startTaskEndTime = "";
      String endTaskEndTime = "";
      String taskJoinOrgId = "";
      String taskJoinedEmpId = "";
      if (httpServletRequest.getParameter("taskTitle") != null && 
        !httpServletRequest.getParameter("taskTitle").equals(""))
        taskTitle = httpServletRequest.getParameter("taskTitle"); 
      if (httpServletRequest.getParameter("taskFinishRate") != null && 
        !httpServletRequest.getParameter("taskFinishRate").equals("") && 
        !httpServletRequest.getParameter("taskFinishRate").equals("-1"))
        taskFinishRate = httpServletRequest.getParameter(
            "taskFinishRate"); 
      if (httpServletRequest.getParameter("taskType") != null && 
        !httpServletRequest.getParameter("taskType").equals("") && 
        !httpServletRequest.getParameter("taskType").equals("-1"))
        taskType = httpServletRequest.getParameter("taskType"); 
      if (httpServletRequest.getParameter("taskPriority") != null && 
        !httpServletRequest.getParameter("taskPriority").equals("") && 
        !httpServletRequest.getParameter("taskPriority").equals("-1"))
        taskPriority = httpServletRequest.getParameter("taskPriority"); 
      if (httpServletRequest.getParameter("taskStatus") != null && 
        !httpServletRequest.getParameter("taskStatus").equals("") && 
        !httpServletRequest.getParameter("taskStatus").equals("-1"))
        taskStatus = httpServletRequest.getParameter("taskStatus"); 
      if (httpServletRequest.getParameter("isEndDate") != null && 
        !httpServletRequest.getParameter("isEndDate").equals(""))
        isEndDate = httpServletRequest.getParameter("isEndDate"); 
      if (httpServletRequest.getParameter("startTaskEndTime") != null && 
        !httpServletRequest.getParameter("startTaskEndTime").equals(""))
        startTaskEndTime = httpServletRequest.getParameter(
            "startTaskEndTime"); 
      if (httpServletRequest.getParameter("endTaskEndTime") != null && 
        !httpServletRequest.getParameter("endTaskEndTime").equals(""))
        endTaskEndTime = httpServletRequest.getParameter(
            "endTaskEndTime"); 
      if (httpServletRequest.getParameter("taskJoinOrgId") != null && 
        !httpServletRequest.getParameter("taskJoinOrgId").equals(""))
        taskJoinOrgId = httpServletRequest.getParameter(
            "taskJoinOrgId"); 
      if (httpServletRequest.getParameter("taskJoinedEmpId") != null && 
        !httpServletRequest.getParameter("taskJoinedEmpId").equals(""))
        taskJoinedEmpId = httpServletRequest.getParameter(
            "taskJoinedEmpId"); 
      String[] parameters = { 
          taskTitle, 
          taskFinishRate, 
          taskType, 
          taskPriority, 
          taskStatus, 
          isEndDate, 
          startTaskEndTime, 
          endTaskEndTime, type, sortType, 
          taskJoinOrgId, taskJoinedEmpId };
      List list = null;
      if (httpServletRequest.getParameter("toUserId") != null) {
        list = taskBD.searchPrincipalTask(userId, parameters, 
            new Integer(currentPage), domainId, httpServletRequest.getParameter("toUserId"));
        httpServletRequest.setAttribute("toUserId", httpServletRequest.getParameter("toUserId"));
      } else {
        list = taskBD.searchPrincipalTask(userId, parameters, 
            new Integer(currentPage), domainId);
      } 
      String recordCount = String.valueOf(taskBD.getRecordCount());
      List allTaskClassList = taskBD.selectAllTaskClass(domainId, userId, orgIdString);
      httpServletRequest.setAttribute("allTaskList", list);
      httpServletRequest.setAttribute("recordCount", recordCount);
      httpServletRequest.setAttribute("allTaskClassList", 
          allTaskClassList);
      httpServletRequest.setAttribute("maxPageItems", 
          String.valueOf(pageSize));
      httpServletRequest.setAttribute("taskTitle", taskTitle);
      httpServletRequest.setAttribute("taskFinishRate", taskFinishRate);
      httpServletRequest.setAttribute("taskType", taskType);
      httpServletRequest.setAttribute("taskPriority", taskPriority);
      httpServletRequest.setAttribute("taskStatus", taskStatus);
      httpServletRequest.setAttribute("isEndDate", isEndDate);
      httpServletRequest.setAttribute("startTaskEndTime", 
          startTaskEndTime);
      httpServletRequest.setAttribute("endTaskEndTime", endTaskEndTime);
      httpServletRequest.setAttribute("taskJoinOrgId", taskJoinOrgId);
      httpServletRequest.setAttribute("taskJoinedEmpId", taskJoinedEmpId);
      httpServletRequest.setAttribute("type", type);
      httpServletRequest.setAttribute("sortType", sortType);
      httpServletRequest.setAttribute("searchmode", "1");
      if (httpServletRequest.getParameter("toUserId") != null) {
        httpServletRequest.setAttribute("pageParameters", "action,taskTitle,taskFinishRate,taskType,taskPriority,taskStatus,isEndDate,startTaskEndTime,endTaskEndTime,type,sortType,toUserId,taskJoinOrgId,taskJoinedEmpId");
      } else {
        httpServletRequest.setAttribute("pageParameters", "action,taskTitle,taskFinishRate,taskType,taskPriority,taskStatus,isEndDate,startTaskEndTime,endTaskEndTime,type,sortType,taskJoinOrgId,taskJoinedEmpId");
      } 
      return actionMapping.findForward("goto_AllTask");
    } 
    if ("searchJoinTask".equals(action)) {
      int pageSize = 15;
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter(
              "pager.offset")); 
      int currentPage = offset / pageSize + 1;
      taskBD.setVolume(pageSize);
      String type = "";
      String sortType = "";
      if (httpServletRequest.getParameter("type") != null)
        type = httpServletRequest.getParameter("type"); 
      if (httpServletRequest.getParameter("sortType") != null)
        sortType = httpServletRequest.getParameter("sortType"); 
      String taskTitle = "";
      String taskFinishRate = "";
      String taskType = "";
      String taskPriority = "";
      String taskStatus = "";
      String isEndDate = "";
      String startTaskEndTime = "";
      String endTaskEndTime = "";
      if (httpServletRequest.getParameter("taskTitle") != null && 
        !httpServletRequest.getParameter("taskTitle").equals(""))
        taskTitle = httpServletRequest.getParameter("taskTitle"); 
      if (httpServletRequest.getParameter("taskFinishRate") != null && 
        !httpServletRequest.getParameter("taskFinishRate").equals("") && 
        !httpServletRequest.getParameter("taskFinishRate").equals("-1"))
        taskFinishRate = httpServletRequest.getParameter(
            "taskFinishRate"); 
      if (httpServletRequest.getParameter("taskType") != null && 
        !httpServletRequest.getParameter("taskType").equals("") && 
        !httpServletRequest.getParameter("taskType").equals("-1"))
        taskType = httpServletRequest.getParameter("taskType"); 
      if (httpServletRequest.getParameter("taskPriority") != null && 
        !httpServletRequest.getParameter("taskPriority").equals("") && 
        !httpServletRequest.getParameter("taskPriority").equals("-1"))
        taskPriority = httpServletRequest.getParameter("taskPriority"); 
      if (httpServletRequest.getParameter("taskStatus") != null && 
        !httpServletRequest.getParameter("taskStatus").equals("") && 
        !httpServletRequest.getParameter("taskStatus").equals("-1"))
        taskStatus = httpServletRequest.getParameter("taskStatus"); 
      if (httpServletRequest.getParameter("isEndDate") != null && 
        !httpServletRequest.getParameter("isEndDate").equals(""))
        isEndDate = httpServletRequest.getParameter("isEndDate"); 
      if (httpServletRequest.getParameter("startTaskEndTime") != null && 
        !httpServletRequest.getParameter("startTaskEndTime").equals(""))
        startTaskEndTime = httpServletRequest.getParameter(
            "startTaskEndTime"); 
      if (httpServletRequest.getParameter("endTaskEndTime") != null && 
        !httpServletRequest.getParameter("endTaskEndTime").equals(""))
        endTaskEndTime = httpServletRequest.getParameter(
            "endTaskEndTime"); 
      String[] parameters = { taskTitle, 
          taskFinishRate, 
          taskType, 
          taskPriority, 
          taskStatus, 
          isEndDate, 
          startTaskEndTime, 
          endTaskEndTime, type, sortType };
      List list = taskBD.searchJoinTask(userId, parameters, 
          new Integer(currentPage), 
          domainId);
      List allTaskClassList = taskBD.selectAllTaskClass(domainId, userId, orgIdString);
      String recordCount = String.valueOf(taskBD.getRecordCount());
      httpServletRequest.setAttribute("allTaskClassList", 
          allTaskClassList);
      httpServletRequest.setAttribute("allSettleCheckTaskList", list);
      httpServletRequest.setAttribute("recordCount", recordCount);
      httpServletRequest.setAttribute("maxPageItems", 
          String.valueOf(pageSize));
      httpServletRequest.setAttribute("taskTitle", taskTitle);
      httpServletRequest.setAttribute("taskFinishRate", taskFinishRate);
      httpServletRequest.setAttribute("taskType", taskType);
      httpServletRequest.setAttribute("taskPriority", taskPriority);
      httpServletRequest.setAttribute("taskStatus", taskStatus);
      httpServletRequest.setAttribute("isEndDate", isEndDate);
      httpServletRequest.setAttribute("startTaskEndTime", 
          startTaskEndTime);
      httpServletRequest.setAttribute("endTaskEndTime", endTaskEndTime);
      httpServletRequest.setAttribute("type", type);
      httpServletRequest.setAttribute("sortType", sortType);
      httpServletRequest.setAttribute("searchmode", "1");
      httpServletRequest.setAttribute("pageParameters", "action,taskTitle,taskFinishRate,taskType,taskPriority,taskStatus,isEndDate,startTaskEndTime,endTaskEndTime,type,sortType");
      return actionMapping.findForward("goto_AllJoinTask");
    } 
    if ("searchAuditingTask".equals(action)) {
      int pageSize = 15;
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter(
              "pager.offset")); 
      int currentPage = offset / pageSize + 1;
      taskBD.setVolume(pageSize);
      String searchContent = httpServletRequest.getParameter(
          "searchContent");
      List list = taskBD.searchAuditingTask(userId, searchContent, 
          new Integer(currentPage), 
          domainId);
      String recordCount = String.valueOf(taskBD.getRecordCount());
      httpServletRequest.setAttribute("allAuditingTaskList", list);
      httpServletRequest.setAttribute("searchContent", searchContent);
      httpServletRequest.setAttribute("recordCount", recordCount);
      httpServletRequest.setAttribute("maxPageItems", 
          String.valueOf(pageSize));
      httpServletRequest.setAttribute("pageParameters", 
          "action,searchContent");
      return actionMapping.findForward("goto_AllAuditingTask");
    } 
    if ("searchCompleteTask".equals(action)) {
      int pageSize = 15;
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter(
              "pager.offset")); 
      int currentPage = offset / pageSize + 1;
      taskBD.setVolume(pageSize);
      String type = "";
      String sortType = "";
      if (httpServletRequest.getParameter("type") != null)
        type = httpServletRequest.getParameter("type"); 
      if (httpServletRequest.getParameter("sortType") != null)
        sortType = httpServletRequest.getParameter("sortType"); 
      String taskTitle = "";
      String taskFinishRate = "";
      String taskType = "";
      String taskPriority = "";
      String taskStatus = "";
      String isEndDate = "";
      String startTaskEndTime = "";
      String endTaskEndTime = "";
      if (httpServletRequest.getParameter("taskTitle") != null && 
        !httpServletRequest.getParameter("taskTitle").equals(""))
        taskTitle = httpServletRequest.getParameter("taskTitle"); 
      if (httpServletRequest.getParameter("taskFinishRate") != null && 
        !httpServletRequest.getParameter("taskFinishRate").equals("") && 
        !httpServletRequest.getParameter("taskFinishRate").equals("-1"))
        taskFinishRate = httpServletRequest.getParameter(
            "taskFinishRate"); 
      if (httpServletRequest.getParameter("taskType") != null && 
        !httpServletRequest.getParameter("taskType").equals("") && 
        !httpServletRequest.getParameter("taskType").equals("-1"))
        taskType = httpServletRequest.getParameter("taskType"); 
      if (httpServletRequest.getParameter("taskPriority") != null && 
        !httpServletRequest.getParameter("taskPriority").equals("") && 
        !httpServletRequest.getParameter("taskPriority").equals("-1"))
        taskPriority = httpServletRequest.getParameter("taskPriority"); 
      if (httpServletRequest.getParameter("taskStatus") != null && 
        !httpServletRequest.getParameter("taskStatus").equals("") && 
        !httpServletRequest.getParameter("taskStatus").equals("-1"))
        taskStatus = httpServletRequest.getParameter("taskStatus"); 
      if (httpServletRequest.getParameter("isEndDate") != null && 
        !httpServletRequest.getParameter("isEndDate").equals(""))
        isEndDate = httpServletRequest.getParameter("isEndDate"); 
      if (httpServletRequest.getParameter("startTaskEndTime") != null && 
        !httpServletRequest.getParameter("startTaskEndTime").equals(""))
        startTaskEndTime = httpServletRequest.getParameter(
            "startTaskEndTime"); 
      if (httpServletRequest.getParameter("endTaskEndTime") != null && 
        !httpServletRequest.getParameter("endTaskEndTime").equals(""))
        endTaskEndTime = httpServletRequest.getParameter(
            "endTaskEndTime"); 
      String[] parameters = { taskTitle, 
          taskFinishRate, 
          taskType, 
          taskPriority, 
          taskStatus, 
          isEndDate, 
          startTaskEndTime, 
          endTaskEndTime, type, sortType };
      List list = taskBD.searchCompleteTask(userId, parameters, 
          new Integer(currentPage), 
          domainId);
      String recordCount = String.valueOf(taskBD.getRecordCount());
      httpServletRequest.setAttribute("allCompleteTaskList", list);
      List allTaskClassList = taskBD.selectAllTaskClass(domainId, userId, orgIdString);
      httpServletRequest.setAttribute("allTaskClassList", 
          allTaskClassList);
      httpServletRequest.setAttribute("recordCount", recordCount);
      httpServletRequest.setAttribute("maxPageItems", 
          String.valueOf(pageSize));
      httpServletRequest.setAttribute("taskTitle", taskTitle);
      httpServletRequest.setAttribute("taskFinishRate", taskFinishRate);
      httpServletRequest.setAttribute("taskType", taskType);
      httpServletRequest.setAttribute("taskPriority", taskPriority);
      httpServletRequest.setAttribute("taskStatus", taskStatus);
      httpServletRequest.setAttribute("isEndDate", isEndDate);
      httpServletRequest.setAttribute("startTaskEndTime", 
          startTaskEndTime);
      httpServletRequest.setAttribute("endTaskEndTime", endTaskEndTime);
      httpServletRequest.setAttribute("type", type);
      httpServletRequest.setAttribute("sortType", sortType);
      httpServletRequest.setAttribute("searchmode", "1");
      httpServletRequest.setAttribute("pageParameters", "action,taskTitle,taskFinishRate,taskType,taskPriority,taskStatus,isEndDate,startTaskEndTime,endTaskEndTime,type,sortType");
      return actionMapping.findForward("goto_AllCompleteTask");
    } 
    if ("searchSettleTask".equals(action)) {
      boolean newTaskSettle = taskBD.hasRight(userId, "任务中心-任务安排", "新增");
      if (!newTaskSettle) {
        httpServletRequest.setAttribute("newTaskSettle", "false");
      } else {
        httpServletRequest.setAttribute("newTaskSettle", "true");
      } 
      int pageSize = 15;
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter(
              "pager.offset")); 
      int currentPage = offset / pageSize + 1;
      taskBD.setVolume(pageSize);
      String type = "";
      String sortType = "";
      if (httpServletRequest.getParameter("type") != null)
        type = httpServletRequest.getParameter("type"); 
      if (httpServletRequest.getParameter("sortType") != null)
        sortType = httpServletRequest.getParameter("sortType"); 
      String taskTitle = "";
      String taskFinishRate = "";
      String taskType = "";
      String taskPriority = "";
      String taskStatus = "";
      String isEndDate = "";
      String startTaskEndTime = "";
      String endTaskEndTime = "";
      String taskJoinOrgId = "";
      String taskJoinedEmpId = "";
      if (httpServletRequest.getParameter("taskTitle") != null && 
        !httpServletRequest.getParameter("taskTitle").equals(""))
        taskTitle = httpServletRequest.getParameter("taskTitle"); 
      if (httpServletRequest.getParameter("taskFinishRate") != null && 
        !httpServletRequest.getParameter("taskFinishRate").equals("") && 
        !httpServletRequest.getParameter("taskFinishRate").equals("-1"))
        taskFinishRate = httpServletRequest.getParameter(
            "taskFinishRate"); 
      if (httpServletRequest.getParameter("taskType") != null && 
        !httpServletRequest.getParameter("taskType").equals("") && 
        !httpServletRequest.getParameter("taskType").equals("-1"))
        taskType = httpServletRequest.getParameter("taskType"); 
      if (httpServletRequest.getParameter("taskPriority") != null && 
        !httpServletRequest.getParameter("taskPriority").equals("") && 
        !httpServletRequest.getParameter("taskPriority").equals("-1"))
        taskPriority = httpServletRequest.getParameter("taskPriority"); 
      if (httpServletRequest.getParameter("taskStatus") != null && 
        !httpServletRequest.getParameter("taskStatus").equals("") && 
        !httpServletRequest.getParameter("taskStatus").equals("-1"))
        taskStatus = httpServletRequest.getParameter("taskStatus"); 
      if (httpServletRequest.getParameter("isEndDate") != null && 
        !httpServletRequest.getParameter("isEndDate").equals(""))
        isEndDate = httpServletRequest.getParameter("isEndDate"); 
      if (httpServletRequest.getParameter("startTaskEndTime") != null && 
        !httpServletRequest.getParameter("startTaskEndTime").equals(""))
        startTaskEndTime = httpServletRequest.getParameter(
            "startTaskEndTime"); 
      if (httpServletRequest.getParameter("endTaskEndTime") != null && 
        !httpServletRequest.getParameter("endTaskEndTime").equals(""))
        endTaskEndTime = httpServletRequest.getParameter(
            "endTaskEndTime"); 
      String rightSQL = "";
      String typeR = "";
      ManagerService mbd = new ManagerService();
      if (httpServletRequest.getParameter("typeR") != null && 
        !httpServletRequest.getParameter("typeR").equals("")) {
        typeR = httpServletRequest.getParameter("typeR");
        if ("org".equals(typeR)) {
          if (httpServletRequest.getParameter("taskJoinOrgId") != null && 
            !httpServletRequest.getParameter("taskJoinOrgId").equals("")) {
            String tempTaskJoinOrgId = httpServletRequest.getParameter(
                "taskJoinOrgId");
            String[] taskJoinOrgids = 
              tempTaskJoinOrgId.replace("**", ",").replace("*", "").split(",");
            for (int i = 0; i < taskJoinOrgids.length; i++) {
              taskJoinOrgId = String.valueOf(taskJoinOrgId) + "*" + taskJoinOrgids[i] + "*";
              TaskBD tb = new TaskBD();
              List<Long> list1 = tb.getSonsById(taskJoinOrgids[i]);
              for (int j = 0; j < list1.size(); j++) {
                Long obj = list1.get(j);
                taskJoinOrgId = String.valueOf(taskJoinOrgId) + "*" + obj.toString() + "*";
              } 
            } 
            rightSQL = mbd.getRightFinalWhere(session
                .getAttribute(
                  "userId").toString(), 
                session.getAttribute("orgId").toString(), 
                "04*08*01", "org.orgId", "task.taskPrincipal");
          } 
        } else if ("user".equals(typeR) && 
          httpServletRequest.getParameter("taskJoinedEmpId") != null && 
          !httpServletRequest.getParameter("taskJoinedEmpId").equals("")) {
          taskJoinedEmpId = httpServletRequest.getParameter(
              "taskJoinedEmpId");
          rightSQL = mbd.getRightFinalWhere(session
              .getAttribute(
                "userId").toString(), 
              session.getAttribute("orgId").toString(), 
              "04*08*01", "org.orgId", "task.taskPrincipal");
        } 
      } 
      String[] parameters = { 
          taskTitle, 
          taskFinishRate, 
          taskType, 
          taskPriority, 
          taskStatus, 
          isEndDate, 
          startTaskEndTime, 
          endTaskEndTime, type, sortType, 
          taskJoinOrgId, taskJoinedEmpId, "" };
      List list = taskBD.searchSettleTask(userId, parameters, 
          new Integer(currentPage), 
          domainId);
      String recordCount = String.valueOf(taskBD.getRecordCount());
      httpServletRequest.setAttribute("allSettleTaskList", list);
      List allTaskClassList = taskBD.selectAllTaskClass(domainId, userId, orgIdString);
      httpServletRequest.setAttribute("allTaskClassList", 
          allTaskClassList);
      httpServletRequest.setAttribute("recordCount", recordCount);
      httpServletRequest.setAttribute("maxPageItems", 
          String.valueOf(pageSize));
      httpServletRequest.setAttribute("taskTitle", taskTitle);
      httpServletRequest.setAttribute("taskFinishRate", taskFinishRate);
      httpServletRequest.setAttribute("taskType", taskType);
      httpServletRequest.setAttribute("taskPriority", taskPriority);
      httpServletRequest.setAttribute("taskStatus", taskStatus);
      httpServletRequest.setAttribute("isEndDate", isEndDate);
      httpServletRequest.setAttribute("startTaskEndTime", 
          startTaskEndTime);
      httpServletRequest.setAttribute("endTaskEndTime", endTaskEndTime);
      httpServletRequest.setAttribute("type", type);
      httpServletRequest.setAttribute("sortType", sortType);
      httpServletRequest.setAttribute("taskJoinedEmpId", taskJoinedEmpId);
      httpServletRequest.setAttribute("taskJoinOrgId", taskJoinOrgId);
      httpServletRequest.setAttribute("searchmode", "1");
      httpServletRequest.setAttribute("pageParameters", "action,taskTitle,taskFinishRate,taskType,taskPriority,taskStatus,isEndDate,startTaskEndTime,endTaskEndTime,type,sortType,taskJoinedEmpId,taskJoinOrgId,typeR");
      return actionMapping.findForward("goto_AllSettleTask");
    } 
    if ("searchSettleCheckTask".equals(action)) {
      int pageSize = 15;
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter(
              "pager.offset")); 
      int currentPage = offset / pageSize + 1;
      taskBD.setVolume(pageSize);
      String type = "";
      String sortType = "";
      if (httpServletRequest.getParameter("type") != null)
        type = httpServletRequest.getParameter("type"); 
      if (httpServletRequest.getParameter("sortType") != null)
        sortType = httpServletRequest.getParameter("sortType"); 
      String taskTitle = "";
      String taskFinishRate = "";
      String taskType = "";
      String taskPriority = "";
      String taskStatus = "";
      String isEndDate = "";
      String startTaskEndTime = "";
      String endTaskEndTime = "";
      if (httpServletRequest.getParameter("taskTitle") != null && 
        !httpServletRequest.getParameter("taskTitle").equals(""))
        taskTitle = httpServletRequest.getParameter("taskTitle"); 
      if (httpServletRequest.getParameter("taskFinishRate") != null && 
        !httpServletRequest.getParameter("taskFinishRate").equals("") && 
        !httpServletRequest.getParameter("taskFinishRate").equals("-1"))
        taskFinishRate = httpServletRequest.getParameter(
            "taskFinishRate"); 
      if (httpServletRequest.getParameter("taskType") != null && 
        !httpServletRequest.getParameter("taskType").equals("") && 
        !httpServletRequest.getParameter("taskType").equals("-1"))
        taskType = httpServletRequest.getParameter("taskType"); 
      if (httpServletRequest.getParameter("taskPriority") != null && 
        !httpServletRequest.getParameter("taskPriority").equals("") && 
        !httpServletRequest.getParameter("taskPriority").equals("-1"))
        taskPriority = httpServletRequest.getParameter("taskPriority"); 
      if (httpServletRequest.getParameter("taskStatus") != null && 
        !httpServletRequest.getParameter("taskStatus").equals("") && 
        !httpServletRequest.getParameter("taskStatus").equals("-1"))
        taskStatus = httpServletRequest.getParameter("taskStatus"); 
      if (httpServletRequest.getParameter("isEndDate") != null && 
        !httpServletRequest.getParameter("isEndDate").equals(""))
        isEndDate = httpServletRequest.getParameter("isEndDate"); 
      if (httpServletRequest.getParameter("startTaskEndTime") != null && 
        !httpServletRequest.getParameter("startTaskEndTime").equals(""))
        startTaskEndTime = httpServletRequest.getParameter(
            "startTaskEndTime"); 
      if (httpServletRequest.getParameter("endTaskEndTime") != null && 
        !httpServletRequest.getParameter("endTaskEndTime").equals(""))
        endTaskEndTime = httpServletRequest.getParameter(
            "endTaskEndTime"); 
      String[] parameters = { taskTitle, 
          taskFinishRate, 
          taskType, 
          taskPriority, 
          taskStatus, 
          isEndDate, 
          startTaskEndTime, 
          endTaskEndTime, type, sortType };
      List list = taskBD.searchSettleCheckTask(userId, parameters, 
          new Integer(currentPage), domainId);
      String recordCount = String.valueOf(taskBD.getRecordCount());
      httpServletRequest.setAttribute("allSettleCheckTaskList", list);
      List allTaskClassList = taskBD.selectAllTaskClass(domainId, userId, orgIdString);
      httpServletRequest.setAttribute("allTaskClassList", 
          allTaskClassList);
      httpServletRequest.setAttribute("recordCount", recordCount);
      httpServletRequest.setAttribute("maxPageItems", 
          String.valueOf(pageSize));
      httpServletRequest.setAttribute("taskTitle", taskTitle);
      httpServletRequest.setAttribute("taskFinishRate", taskFinishRate);
      httpServletRequest.setAttribute("taskType", taskType);
      httpServletRequest.setAttribute("taskPriority", taskPriority);
      httpServletRequest.setAttribute("taskStatus", taskStatus);
      httpServletRequest.setAttribute("isEndDate", isEndDate);
      httpServletRequest.setAttribute("startTaskEndTime", 
          startTaskEndTime);
      httpServletRequest.setAttribute("endTaskEndTime", endTaskEndTime);
      httpServletRequest.setAttribute("type", type);
      httpServletRequest.setAttribute("sortType", sortType);
      httpServletRequest.setAttribute("searchmode", "1");
      httpServletRequest.setAttribute("pageParameters", "action,taskTitle,taskFinishRate,taskType,taskPriority,taskStatus,isEndDate,startTaskEndTime,endTaskEndTime,type,sortType");
      return actionMapping.findForward("goto_AllSettleCheckTask");
    } 
    if ("searchSettleFinishTask".equals(action)) {
      int pageSize = 15;
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter(
              "pager.offset")); 
      int currentPage = offset / pageSize + 1;
      taskBD.setVolume(pageSize);
      String type = "";
      String sortType = "";
      if (httpServletRequest.getParameter("type") != null)
        type = httpServletRequest.getParameter("type"); 
      if (httpServletRequest.getParameter("sortType") != null)
        sortType = httpServletRequest.getParameter("sortType"); 
      String taskTitle = "";
      String taskFinishRate = "";
      String taskType = "";
      String taskPriority = "";
      String taskStatus = "";
      String isEndDate = "";
      String startTaskEndTime = "";
      String endTaskEndTime = "";
      if (httpServletRequest.getParameter("taskTitle") != null && 
        !httpServletRequest.getParameter("taskTitle").equals(""))
        taskTitle = httpServletRequest.getParameter("taskTitle"); 
      if (httpServletRequest.getParameter("taskFinishRate") != null && 
        !httpServletRequest.getParameter("taskFinishRate").equals("") && 
        !httpServletRequest.getParameter("taskFinishRate").equals("-1"))
        taskFinishRate = httpServletRequest.getParameter(
            "taskFinishRate"); 
      if (httpServletRequest.getParameter("taskType") != null && 
        !httpServletRequest.getParameter("taskType").equals("") && 
        !httpServletRequest.getParameter("taskType").equals("-1"))
        taskType = httpServletRequest.getParameter("taskType"); 
      if (httpServletRequest.getParameter("taskPriority") != null && 
        !httpServletRequest.getParameter("taskPriority").equals("") && 
        !httpServletRequest.getParameter("taskPriority").equals("-1"))
        taskPriority = httpServletRequest.getParameter("taskPriority"); 
      if (httpServletRequest.getParameter("taskStatus") != null && 
        !httpServletRequest.getParameter("taskStatus").equals("") && 
        !httpServletRequest.getParameter("taskStatus").equals("-1"))
        taskStatus = httpServletRequest.getParameter("taskStatus"); 
      if (httpServletRequest.getParameter("isEndDate") != null && 
        !httpServletRequest.getParameter("isEndDate").equals(""))
        isEndDate = httpServletRequest.getParameter("isEndDate"); 
      if (httpServletRequest.getParameter("startTaskEndTime") != null && 
        !httpServletRequest.getParameter("startTaskEndTime").equals(""))
        startTaskEndTime = httpServletRequest.getParameter(
            "startTaskEndTime"); 
      if (httpServletRequest.getParameter("endTaskEndTime") != null && 
        !httpServletRequest.getParameter("endTaskEndTime").equals(""))
        endTaskEndTime = httpServletRequest.getParameter(
            "endTaskEndTime"); 
      String[] parameters = { taskTitle, 
          taskFinishRate, 
          taskType, 
          taskPriority, 
          taskStatus, 
          isEndDate, 
          startTaskEndTime, 
          endTaskEndTime, type, sortType };
      List list = taskBD.searchSettleFinishTask(userId, parameters, 
          new Integer(currentPage), domainId);
      String recordCount = String.valueOf(taskBD.getRecordCount());
      httpServletRequest.setAttribute("allSettleFinishTaskList", list);
      List allTaskClassList = taskBD.selectAllTaskClass(domainId, userId, orgIdString);
      httpServletRequest.setAttribute("allTaskClassList", 
          allTaskClassList);
      httpServletRequest.setAttribute("recordCount", recordCount);
      httpServletRequest.setAttribute("maxPageItems", 
          String.valueOf(pageSize));
      httpServletRequest.setAttribute("taskTitle", taskTitle);
      httpServletRequest.setAttribute("taskFinishRate", taskFinishRate);
      httpServletRequest.setAttribute("taskType", taskType);
      httpServletRequest.setAttribute("taskPriority", taskPriority);
      httpServletRequest.setAttribute("taskStatus", taskStatus);
      httpServletRequest.setAttribute("isEndDate", isEndDate);
      httpServletRequest.setAttribute("startTaskEndTime", 
          startTaskEndTime);
      httpServletRequest.setAttribute("endTaskEndTime", endTaskEndTime);
      httpServletRequest.setAttribute("type", type);
      httpServletRequest.setAttribute("sortType", sortType);
      httpServletRequest.setAttribute("searchmode", "1");
      httpServletRequest.setAttribute("pageParameters", "action,taskTitle,taskFinishRate,taskType,taskPriority,taskStatus,isEndDate,startTaskEndTime,endTaskEndTime,type,sortType");
      return actionMapping.findForward("goto_AllSettleFinishTask");
    } 
    if ("taskAuditing".equals(action)) {
      String taskId = httpServletRequest.getParameter("taskId");
      String taskTitle = httpServletRequest.getParameter("taskTitle");
      String finishrate = httpServletRequest.getParameter("finishrate");
      String checkopinion = httpServletRequest.getParameter(
          "checkopinion");
      boolean result = taskBD.checkTask(Long.valueOf(taskId), 
          checkopinion, new Date(), 
          userName, userId, 
          Integer.valueOf(finishrate), 
          domainId);
      if (!result)
        return actionMapping.findForward("failure"); 
      return actionMapping.findForward("goto_TaskAuditing");
    } 
    if ("selectPeriodicityTask".equals(action)) {
      boolean newTaskSettle = taskBD.hasRight(userId, "任务中心-任务安排", "新增");
      if (!newTaskSettle) {
        httpServletRequest.setAttribute("newTaskSettle", "false");
      } else {
        httpServletRequest.setAttribute("newTaskSettle", "true");
      } 
      periodicityTaskList(httpServletRequest);
      return actionMapping.findForward("periodicityTaskList");
    } 
    if ("addPeriodicityTaskView".equals(action)) {
      List list = taskBD.selectAllTaskClass(domainId, userId, orgIdString);
      httpServletRequest.setAttribute("rightScope", 
          getRightScope(userId, orgId, 
            "任务中心-任务安排", "新增"));
      httpServletRequest.setAttribute("allTaskClassList", list);
      return actionMapping.findForward("addPeriodicityTaskView");
    } 
    if ("addPeriodicityTask".equals(action)) {
      TaskPeriodicityPO taskPeriodicityPO = 
        (TaskPeriodicityPO)FillBean.transformOneToOne(
          taskActionForm, TaskPeriodicityPO.class);
      taskPeriodicityPO.setCreatedEmp(userId);
      taskPeriodicityPO.setDomainId(domainId);
      taskPeriodicityPO.setCreatedEmpName(userName);
      taskPeriodicityPO.setCreatedOrg(orgId);
      taskPeriodicityPO.setCreatedOrgName(orgName);
      taskPeriodicityPO.setTaskCreatedTime(new Date());
      String taskType = httpServletRequest.getParameter("taskType");
      taskPeriodicityPO.setTaskType(taskType);
      Integer onTimeMode = new Integer(httpServletRequest.getParameter(
            "onTimeMode"));
      taskPeriodicityPO.setOnTimeMode(onTimeMode);
      if (onTimeMode != null && onTimeMode.equals(new Integer(1))) {
        String week = httpServletRequest.getParameter("week");
        taskPeriodicityPO.setOnTimeContent(week);
      } 
      if (onTimeMode != null && onTimeMode.equals(new Integer(2))) {
        String month_day = httpServletRequest.getParameter("month_day");
        taskPeriodicityPO.setOnTimeContent(month_day);
      } 
      if (onTimeMode != null && onTimeMode.equals(new Integer(3))) {
        String year_month = httpServletRequest.getParameter(
            "year_month");
        String year_month_day = httpServletRequest.getParameter(
            "year_month_day");
        taskPeriodicityPO.setOnTimeContent(String.valueOf(year_month) + "$" + 
            year_month_day);
      } 
      HashSet Accessory = new HashSet();
      String[] fileName = httpServletRequest.getParameterValues(
          "taskFileName");
      String[] saveName = httpServletRequest.getParameterValues(
          "taskSaveName");
      List list = taskBD.selectAllTaskClass(domainId, userId, orgIdString);
      boolean result = taskBD.addPeriodicityTask(taskPeriodicityPO, 
          fileName, saveName);
      if (!result)
        return actionMapping.findForward("failure"); 
      if ("saveAndContinue".equals(saveType)) {
        httpServletRequest.setAttribute("rightScope", 
            getRightScope(userId, orgId, 
              "任务中心-任务安排", "新增"));
        httpServletRequest.setAttribute("allTaskClassList", list);
        taskActionForm.reset(actionMapping, httpServletRequest);
        return actionMapping.findForward("taskSettle_saveAndContinues");
      } 
      if ("saveAndExit".equals(saveType)) {
        httpServletRequest.setAttribute("rightScope", 
            getRightScope(userId, orgId, 
              "任务中心-任务安排", "新增"));
        httpServletRequest.setAttribute("allTaskClassList", list);
        return actionMapping.findForward("taskSettle_saveAndExit");
      } 
    } 
    if ("selectPeriodicityTaskView".equals(action)) {
      Long periodicityId = Long.valueOf(httpServletRequest.getParameter(
            "periodicityId"));
      TaskPeriodicityPO taskPeriodicityPO = taskBD
        .selectPeriodicityTaskView(
          periodicityId, domainId);
      taskActionForm.setTaskTitle(taskPeriodicityPO.getTaskTitle());
      taskActionForm.setTaskPrincipalName(taskPeriodicityPO
          .getTaskPrincipalName());
      taskActionForm.setTaskPrincipal(taskPeriodicityPO.getTaskPrincipal());
      taskActionForm.setTaskCheckerName(taskPeriodicityPO
          .getTaskCheckerName());
      taskActionForm.setTaskChecker(taskPeriodicityPO.getTaskChecker());
      taskActionForm.setTaskPriority(taskPeriodicityPO.getTaskPriority());
      taskActionForm.setTaskJoinedEmpName(taskPeriodicityPO
          .getTaskJoinedEmpName());
      taskActionForm.setTaskJoinedEmp(taskPeriodicityPO.getTaskJoinedEmp());
      taskActionForm.setTaskJoinOrgName(taskPeriodicityPO
          .getTaskJoinOrgName());
      taskActionForm.setTaskJoineOrg(taskPeriodicityPO.getTaskJoineOrg());
      taskActionForm.setTaskDescription(taskPeriodicityPO
          .getTaskDescription());
      String taskType = taskPeriodicityPO.getTaskType();
      Integer onTimeMode = taskPeriodicityPO.getOnTimeMode();
      String OnTimeContent = taskPeriodicityPO.getOnTimeContent();
      List list = taskBD.selectAllTaskClass(domainId, userId, orgIdString);
      List list2 = new ArrayList();
      list2 = taskBD.getaccessory(periodicityId.toString());
      httpServletRequest.setAttribute("periodicityId", periodicityId);
      httpServletRequest.setAttribute("onTimeMode", onTimeMode);
      httpServletRequest.setAttribute("OnTimeContent", OnTimeContent);
      httpServletRequest.setAttribute("allTaskClassList", list);
      httpServletRequest.setAttribute("accessoryList", list2);
      httpServletRequest.setAttribute("taskType", taskType);
      httpServletRequest.setAttribute("rightScope", 
          getRightScope(userId, orgId, 
            "任务中心-任务安排", "新增"));
      return actionMapping.findForward("goto_PeriodicityTaskUpdate");
    } 
    if ("modifyPeriodicityTask".equals(action)) {
      TaskPeriodicityPO taskPeriodicityPO = 
        (TaskPeriodicityPO)FillBean.transformOneToOne(
          taskActionForm, TaskPeriodicityPO.class);
      Long periodicityId = Long.valueOf(httpServletRequest.getParameter(
            "periodicityId"));
      taskPeriodicityPO.setPeriodicityId(periodicityId);
      String taskType = httpServletRequest.getParameter("taskType");
      taskPeriodicityPO.setTaskType(taskType);
      Integer onTimeMode = new Integer(httpServletRequest.getParameter(
            "onTimeMode"));
      taskPeriodicityPO.setOnTimeMode(onTimeMode);
      if (onTimeMode != null && onTimeMode.equals(new Integer(1))) {
        String week = httpServletRequest.getParameter("week");
        taskPeriodicityPO.setOnTimeContent(week);
      } 
      if (onTimeMode != null && onTimeMode.equals(new Integer(2))) {
        String month_day = httpServletRequest.getParameter("month_day");
        taskPeriodicityPO.setOnTimeContent(month_day);
      } 
      if (onTimeMode != null && onTimeMode.equals(new Integer(3))) {
        String year_month = httpServletRequest.getParameter(
            "year_month");
        String year_month_day = httpServletRequest.getParameter(
            "year_month_day");
        taskPeriodicityPO.setOnTimeContent(String.valueOf(year_month) + "$" + 
            year_month_day);
      } 
      taskPeriodicityPO.setDomainId(domainId);
      List list = taskBD.selectAllTaskClass(domainId, userId, orgIdString);
      HashSet Accessory = new HashSet();
      String[] fileName = httpServletRequest.getParameterValues(
          "taskFileName");
      String[] saveName = httpServletRequest.getParameterValues(
          "taskSaveName");
      boolean result = taskBD.modifyPeriodicityTask(taskPeriodicityPO, 
          fileName, saveName);
      if (!result)
        return actionMapping.findForward("failure"); 
      if ("updateAndExit".equals(saveType)) {
        httpServletRequest.setAttribute("rightScope", 
            getRightScope(userId, orgId, 
              "任务中心-任务安排", "新增"));
        httpServletRequest.setAttribute("allTaskClassList", list);
        httpServletRequest.setAttribute("updateAndExit", saveType);
        return actionMapping.findForward(
            "periodicityTask_updateAndExit");
      } 
    } 
    if ("deletePeriodicityTask".equals(action)) {
      String id = httpServletRequest.getParameter("periodicityId");
      boolean result = true;
      if (id != null && !"".equals(id)) {
        Long periodicityId = Long.valueOf(id);
        result = taskBD.deletePeriodicityTask(periodicityId);
      } 
      int offsetCopy = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset"));
      if (!result)
        return actionMapping.findForward("failure"); 
      boolean newTaskSettle = taskBD.hasRight(userId, "任务中心-任务安排", 
          "新增");
      if (!newTaskSettle) {
        httpServletRequest.setAttribute("newTaskSettle", "false");
      } else {
        httpServletRequest.setAttribute("newTaskSettle", "true");
      } 
      if (offsetCopy != 0) {
        periodicityTaskList(httpServletRequest, offsetCopy);
      } else {
        periodicityTaskList(httpServletRequest);
      } 
      taskActionForm.reset(actionMapping, httpServletRequest);
      return actionMapping.findForward("periodicityTaskList");
    } 
    if ("addPeriodicitytaskSend".equals(action)) {
      Long periodicityId = Long.valueOf(httpServletRequest.getParameter(
            "periodicityId"));
      TaskPeriodicityPO taskPeriodicityPO = taskBD
        .selectPeriodicityTaskView(
          periodicityId, domainId);
      TaskPO taskPO = new TaskPO();
      TaskVO taskVO = new TaskVO();
      taskVO.setCreatedEmp(userId);
      taskVO.setTaskDomainId(domainId);
      taskVO.setCreatedEmpName(userName);
      taskVO.setCreatedOrg(orgId);
      taskVO.setCreatedOrgName(orgName);
      taskVO.setIsArranged(new Integer(1));
      taskVO.setTaskAwakeTime(new Long(0L));
      taskVO.setTaskCreatedTime(new Date());
      Date startTime = new Date();
      Date endTime = new Date(httpServletRequest.getParameter("end_date"));
      taskVO.setTaskBeginTime(startTime);
      taskVO.setTaskEndTime(endTime);
      taskVO.setTaskTitle(taskPeriodicityPO.getTaskTitle());
      taskVO.setTaskType(taskPeriodicityPO.getTaskType());
      taskVO.setTaskPrincipal(taskPeriodicityPO.getTaskPrincipal());
      taskVO.setTaskPrincipalName(taskPeriodicityPO.getTaskPrincipalName());
      taskVO.setTaskChecker(taskPeriodicityPO.getTaskChecker());
      taskVO.setTaskCheckerName(taskPeriodicityPO.getTaskCheckerName());
      taskVO.setTaskPriority(taskPeriodicityPO.getTaskPriority());
      taskVO.setTaskStatus(new Integer(0));
      taskVO.setTaskJoinedEmp(taskPeriodicityPO.getTaskJoinedEmp());
      taskVO.setTaskJoinedEmpName(taskPeriodicityPO.getTaskJoinedEmpName());
      taskVO.setTaskJoineOrg(taskPeriodicityPO.getTaskJoineOrg());
      taskVO.setTaskJoinOrgName(taskPeriodicityPO.getTaskJoinOrgName());
      taskVO.setTaskDescription(taskPeriodicityPO.getTaskDescription());
      taskVO.setParentIdString("-1");
      taskVO.setStickie(Long.valueOf(0L));
      taskVO.setAnpaiStickie(Long.valueOf(0L));
      String joineOrgId = "";
      try {
        joineOrgId = (new ConversionString(taskVO.getTaskJoinedEmp()))
          .getOrgIdString();
      } catch (Exception e) {
        joineOrgId = "";
      } 
      String taskJoineOrg = "";
      if (!joineOrgId.equals(""))
        taskJoineOrg = taskBD.selectUserIds(joineOrgId); 
      String temps = "";
      if (taskVO.getTaskJoinedEmp() != null && 
        !"".equals(taskVO.getTaskJoinedEmp()))
        temps = (new ConversionString(taskVO.getTaskJoinedEmp()))
          .getUserIdString(); 
      if ("".equals(taskJoineOrg)) {
        taskJoineOrg = temps;
      } else {
        taskJoineOrg = String.valueOf(taskJoineOrg) + "," + temps;
      } 
      taskVO.setTaskJoineOrg(taskJoineOrg);
      HashSet<TaskAccessoryPO> Accessory = new HashSet();
      List<Object[]> list2 = taskBD.getaccessory(periodicityId.toString());
      if (list2 != null && list2.size() != 0)
        for (int i = 0; i < list2.size(); i++) {
          Object[] obj = list2.get(i);
          TaskAccessoryPO taskAccessoryPO = new TaskAccessoryPO();
          taskAccessoryPO.setAccessoryname(obj[0].toString());
          taskAccessoryPO.setAccessorysavename(obj[1].toString());
          taskAccessoryPO.setDomain_id(domainId);
          Accessory.add(taskAccessoryPO);
        }  
      taskPO.setTaskAccessory(Accessory);
      taskVO.setTaskPO(taskPO);
      boolean result = taskBD.addTask(taskVO);
      if (!result)
        return actionMapping.findForward("failure"); 
      if ("saveAndExit".equals(saveType)) {
        if (httpServletRequest.getParameter("isrtx") != null && 
          httpServletRequest.getParameter("isrtx").equals("5")) {
          String joinEmp = taskVO.getTaskJoinedEmp();
          ManagerService mbd = new ManagerService();
          String receivers = "";
          if (taskJoineOrg != null && 
            !"".equals(taskJoineOrg))
            if ("".equals(temps)) {
              taskJoineOrg = taskJoineOrg.substring(0, taskJoineOrg.length() - 1);
              receivers = mbd.getEmployeesAccounts(taskJoineOrg);
            }  
          Object[] obj = (new UserBD()).getUserInfo(userId)
            .get(0);
          if (!receivers.equals("")) {
            receivers = String.valueOf(receivers) + "," + obj[2].toString();
          } else {
            receivers = obj[2].toString();
          } 
        } 
        if (httpServletRequest.getParameter("issms") != null && 
          httpServletRequest.getParameter("issms").equals("6")) {
          ModelSendMsg sendMsg = new ModelSendMsg();
          sendMsg.sendSystemMessage("任务中心", taskVO.getTaskTitle(), 
              (String)taskVO.getTaskPrincipal(), 
              "", 
              httpServletRequest);
        } 
        return actionMapping.findForward(
            "periodicitytaskSend_saveAndExit");
      } 
    } 
    if ("selectSearchTask".equals(action) || 
      "selectSearchTaskExport".equals(action)) {
      ManagerService managerBD = new ManagerService();
      HttpSession httpSession = httpServletRequest.getSession(true);
      String rightSQL = "";
      rightSQL = managerBD.getRightFinalWhere(httpSession
          .getAttribute(
            "userId").toString(), 
          httpSession.getAttribute("orgId").toString(), 
          "04*08*01", "org.orgId", "po.createdEmp");
      managerBD = new ManagerService();
      List<Object[]> rightlist = managerBD.getRightScope((String)userId, "04*08*01");
      String rang = "*AAAA*";
      if (rightlist != null && rightlist.size() > 0) {
        Object[] obj = rightlist.get(0);
        if (obj[0] != null && obj[0].toString().equals("0")) {
          rang = "*0*";
        } else if (obj[0] != null && (
          obj[0].toString().equals("2") || 
          obj[0].toString().equals("3"))) {
          rang = "*" + orgId + "*";
        } else if (obj[0] != null && obj[0].toString().equals("4")) {
          rang = obj[1].toString();
        } 
      } 
      httpServletRequest.getSession(true).setAttribute("rang", rang);
      String type = "";
      String sortType = "";
      if (httpServletRequest.getParameter("type") != null)
        type = httpServletRequest.getParameter("type"); 
      if (httpServletRequest.getParameter("sortType") != null)
        sortType = httpServletRequest.getParameter("sortType"); 
      String viewSql = " distinct po.taskId,po.taskType,po.taskTitle,po.taskPrincipalName,po.taskFinishRate,po.taskDescription,po.taskEndTime,po.taskCreatedTime,po.taskCheckerName,po.createdEmpName,po.taskRealEndTime,org.orgName";
      String fromSql = 
        
        "com.js.oa.scheme.taskcenter.po.TaskExecPO tpo join tpo.task po,com.js.system.vo.usermanager.EmployeeVO user join user.organizations org";
      String whereSql = " where 1=1 ";
      if (httpServletRequest.getParameter("receive") != null && 
        httpServletRequest.getParameter("receive").equals("receive")) {
        whereSql = String.valueOf(whereSql) + " and  tpo.execEmpId= user.empId ";
        rightSQL = managerBD.getRightFinalWhere(httpSession
            .getAttribute(
              "userId").toString(), 
            httpSession.getAttribute("orgId").toString(), 
            "04*08*01", "org.orgId", "tpo.execEmpId");
      } else {
        whereSql = String.valueOf(whereSql) + " and  po.createdEmp = user.empId ";
      } 
      whereSql = String.valueOf(whereSql) + " and " + rightSQL;
      if (httpServletRequest.getParameter("taskTitle") != null && 
        !httpServletRequest.getParameter("taskTitle").equals(""))
        whereSql = String.valueOf(whereSql) + " and po.taskTitle like '%" + 
          httpServletRequest.getParameter("taskTitle") + "%'"; 
      if (httpServletRequest.getParameter("taskFinishRate") != null && 
        !httpServletRequest.getParameter("taskFinishRate").equals("") && 
        !httpServletRequest.getParameter("taskFinishRate").equals("-1"))
        whereSql = String.valueOf(whereSql) + " and po.taskFinishRate='" + 
          httpServletRequest.getParameter("taskFinishRate") + "'"; 
      if (httpServletRequest.getParameter("typeR") != null && 
        !httpServletRequest.getParameter("typeR").equals("")) {
        String typeR = httpServletRequest.getParameter("typeR");
        if (typeR.equals("org")) {
          if (httpServletRequest.getParameter("taskJoinOrgId") != null && 
            
            !httpServletRequest.getParameter("taskJoinOrgId").equals("")) {
            String taskJoinOrgId = httpServletRequest.getParameter(
                "taskJoinOrgId");
            StringTokenizer st = new StringTokenizer(taskJoinOrgId, 
                "*");
            String tempOrgID = "";
            whereSql = String.valueOf(whereSql) + " and ( org.orgId in (";
            while (st.hasMoreTokens()) {
              tempOrgID = st.nextToken();
              whereSql = String.valueOf(whereSql) + tempOrgID + ",";
              TaskBD tb = new TaskBD();
              List<Long> list1 = tb.getSonsById(tempOrgID);
              for (int j = 0; j < list1.size(); j++) {
                Long obj = list1.get(j);
                whereSql = String.valueOf(whereSql) + obj.toString() + ",";
              } 
            } 
            whereSql = String.valueOf(whereSql.substring(0, whereSql.length() - 1)) + 
              ")) ";
          } 
        } else if (typeR.equals("user")) {
          if (httpServletRequest.getParameter("taskJoinedEmpId") != null && 
            
            !httpServletRequest.getParameter("taskJoinedEmpId").equals("")) {
            String taskJoinedEmpId = httpServletRequest
              .getParameter("taskJoinedEmpId");
            String taskJoinedEmpName = httpServletRequest
              .getParameter("taskJoinedEmpName");
            StringTokenizer st = new StringTokenizer(
                taskJoinedEmpId, "$");
            whereSql = String.valueOf(whereSql) + " and ( user.empId in (";
            while (st.hasMoreTokens())
              whereSql = String.valueOf(whereSql) + st.nextToken() + ","; 
            whereSql = String.valueOf(whereSql.substring(0, whereSql.length() - 1)) + 
              ")) ";
          } 
        } 
      } 
      String isBeginDate = (httpServletRequest.getParameter("isBeginDate") == null) ? 
        "" : 
        httpServletRequest.getParameter("isBeginDate");
      if (isBeginDate.equals("1") && 
        httpServletRequest.getParameter("startTaskBeginTime") != null && 
        
        !httpServletRequest.getParameter("startTaskBeginTime").equals("") && 
        httpServletRequest.getParameter("endTaskBeginTime") != null && 
        !httpServletRequest.getParameter("endTaskBeginTime").equals(
          "")) {
        String startTaskBeginTime = httpServletRequest.getParameter(
            "startTaskBeginTime");
        String endTaskBeginTime = httpServletRequest.getParameter(
            "endTaskBeginTime");
        String databaseType = 
          SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          whereSql = String.valueOf(whereSql) + " and po.taskBeginTime between '" + 
            startTaskBeginTime + "'  and '" + 
            endTaskBeginTime + "'";
        } else {
          whereSql = String.valueOf(whereSql) + 
            " and po.taskBeginTime between JSDB.FN_STRTODATE('" + 
            startTaskBeginTime + 
            "','S')  and JSDB.FN_STRTODATE('" + 
            endTaskBeginTime + "','S')";
        } 
      } 
      String isEndDate = (httpServletRequest.getParameter("isEndDate") == null) ? 
        "" : 
        httpServletRequest.getParameter("isEndDate");
      if (isEndDate.equals("1") && 
        httpServletRequest.getParameter("startTaskEndTime") != null && 
        !httpServletRequest.getParameter("startTaskEndTime").equals(
          ""))
        if (httpServletRequest.getParameter("endTaskEndTime") != null && 
          !httpServletRequest.getParameter("endTaskEndTime").equals(
            "")) {
          String startTaskEndTime = httpServletRequest.getParameter(
              "startTaskEndTime");
          String endTaskEndTime = httpServletRequest.getParameter(
              "endTaskEndTime");
          String databaseType = 
            SystemCommon.getDatabaseType();
          if (databaseType.indexOf("mysql") >= 0) {
            whereSql = String.valueOf(whereSql) + " and po.taskEndTime between '" + 
              startTaskEndTime + "' and '" + endTaskEndTime + 
              "'";
          } else {
            whereSql = String.valueOf(whereSql) + 
              " and po.taskEndTime between JSDB.FN_STRTODATE('" + 
              startTaskEndTime + 
              "','S')  and JSDB.FN_STRTODATE('" + 
              endTaskEndTime + "','S')";
          } 
        }  
      if (httpServletRequest.getParameter("taskStatus") != null && 
        !httpServletRequest.getParameter("taskStatus").equals("") && 
        !httpServletRequest.getParameter("taskStatus").equals("-1")) {
        if ("0".equals(httpServletRequest.getParameter("taskStatus")))
          whereSql = String.valueOf(whereSql) + 
            " and po.taskStatus=0 and po.taskFinishRate<100"; 
        if ("1".equals(httpServletRequest.getParameter("taskStatus")))
          whereSql = String.valueOf(whereSql) + 
            " and po.taskStatus=1 and po.taskFinishRate<100"; 
        if ("2".equals(httpServletRequest.getParameter("taskStatus")))
          whereSql = String.valueOf(whereSql) + " and po.taskStatus=3"; 
        if ("3".equals(httpServletRequest.getParameter("taskStatus")))
          whereSql = String.valueOf(whereSql) + 
            " and po.taskStatus in(2,4) and po.taskFinishRate=100"; 
        if ("4".equals(httpServletRequest.getParameter("taskStatus")))
          whereSql = String.valueOf(whereSql) + 
            " and po.taskStatus=1 and po.taskFinishRate=100"; 
        if ("5".equals(httpServletRequest.getParameter("taskStatus")))
          whereSql = String.valueOf(whereSql) + 
            " and po.taskStatus=4 and po.taskFinishRate<100"; 
      } 
      if (httpServletRequest.getParameter("taskPriority") != null && 
        !httpServletRequest.getParameter("taskPriority").equals("") && 
        !httpServletRequest.getParameter("taskPriority").equals("-1"))
        whereSql = String.valueOf(whereSql) + " and po.taskPriority='" + 
          httpServletRequest.getParameter("taskPriority") + "'"; 
      if (httpServletRequest.getParameter("taskType") != null && 
        !httpServletRequest.getParameter("taskType").equals("") && 
        !httpServletRequest.getParameter("taskType").equals("-1"))
        whereSql = String.valueOf(whereSql) + " and po.taskType='" + 
          httpServletRequest.getParameter("taskType") + "'"; 
      whereSql = String.valueOf(whereSql) + " and po.taskDomainId=" + domainId;
      if ("".equals(type))
        whereSql = String.valueOf(whereSql) + " order by po.taskCreatedTime desc"; 
      if ("empName".equals(type))
        whereSql = String.valueOf(whereSql) + " order by po.taskPrincipalName " + sortType; 
      if ("orgName".equals(type))
        whereSql = String.valueOf(whereSql) + " order by org." + type + " " + sortType; 
      if (!"empName".equals(type) && !"orgName".equals(type) && 
        !"".equals(type))
        whereSql = String.valueOf(whereSql) + " order by po." + type + " " + sortType; 
      int pageSize = 25;
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter(
              "pager.offset")); 
      boolean newRight = true;
      if (!newRight) {
        httpServletRequest.setAttribute("newRight", "false");
      } else {
        httpServletRequest.setAttribute("newRight", "true");
      } 
      int currentPage = offset / pageSize + 1;
      Page page = new Page(viewSql, fromSql, whereSql);
      page.setPageSize(pageSize);
      if ("selectSearchTaskExport".equals(action))
        page.setPageSize(999999999); 
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      if (list.size() == 0 && offset >= 15) {
        offset -= 15;
        currentPage = offset / pageSize + 1;
        page.setPageSize(pageSize);
        page.setcurrentPage(currentPage);
        list = page.getResultList();
        httpServletRequest.setAttribute("new.offset", (new StringBuilder(String.valueOf(offset))).toString());
        httpServletRequest.setAttribute("pager.realCurrent", (
            new StringBuilder(String.valueOf(currentPage))).toString());
      } 
      String recordCount = String.valueOf(page.getRecordCount());
      String pageCount = String.valueOf(page.getPageCount());
      httpServletRequest.setAttribute("allTask", list);
      httpServletRequest.setAttribute("recordCount", recordCount);
      httpServletRequest.setAttribute("maxPageItems", 
          String.valueOf(pageSize));
      httpServletRequest.setAttribute("pageParameters", "action,taskTitle,taskFinishRate,typeR,taskJoinOrgName,taskJoinOrgId,taskJoinedEmpName,taskJoinedEmpId,startTaskBeginTime,endTaskBeginTime,startTaskEndTime,endTaskEndTime,taskStatus,taskPriority,taskType,isEndDate,isBeginDate,type,sortType,receive");
      httpServletRequest.setAttribute("type", type);
      httpServletRequest.setAttribute("sortType", sortType);
      List allTaskClassList = taskBD.selectAllTaskClass(domainId, userId, orgIdString);
      httpServletRequest.setAttribute("allTaskClassList", 
          allTaskClassList);
      if ("selectSearchTaskExport".equals(action))
        return actionMapping.findForward("task_export"); 
      return actionMapping.findForward("task_search");
    } 
    if ("selectCancelTask".equals(action)) {
      String type = "";
      String sortType = "";
      if (httpServletRequest.getParameter("type") != null)
        type = httpServletRequest.getParameter("type"); 
      if (httpServletRequest.getParameter("sortType") != null)
        sortType = httpServletRequest.getParameter("sortType"); 
      List allTaskClassList = taskBD.selectAllTaskClass(domainId, userId, orgIdString);
      httpServletRequest.setAttribute("allTaskClassList", 
          allTaskClassList);
      int pageSize = 15;
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter(
              "pager.offset")); 
      int currentPage = offset / pageSize + 1;
      taskBD.setVolume(pageSize);
      List list = taskBD.selectCancelTask(userId, 
          new Integer(currentPage), 
          domainId, type, sortType);
      String recordCount = String.valueOf(taskBD.getRecordCount());
      httpServletRequest.setAttribute("allTaskList", list);
      httpServletRequest.setAttribute("recordCount", recordCount);
      httpServletRequest.setAttribute("maxPageItems", 
          String.valueOf(pageSize));
      httpServletRequest.setAttribute("pageParameters", "action");
      httpServletRequest.setAttribute("sortType", sortType);
      httpServletRequest.setAttribute("type", type);
      return actionMapping.findForward("goto_AllCancelTask");
    } 
    if ("searchCancelTask".equals(action)) {
      int pageSize = 15;
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter(
              "pager.offset")); 
      int currentPage = offset / pageSize + 1;
      taskBD.setVolume(pageSize);
      String type = "";
      String sortType = "";
      if (httpServletRequest.getParameter("type") != null)
        type = httpServletRequest.getParameter("type"); 
      if (httpServletRequest.getParameter("sortType") != null)
        sortType = httpServletRequest.getParameter("sortType"); 
      String taskTitle = "";
      String taskFinishRate = "";
      String taskType = "";
      String taskPriority = "";
      String taskStatus = "";
      String isEndDate = "";
      String startTaskEndTime = "";
      String endTaskEndTime = "";
      if (httpServletRequest.getParameter("taskTitle") != null && 
        !httpServletRequest.getParameter("taskTitle").equals(""))
        taskTitle = httpServletRequest.getParameter("taskTitle"); 
      if (httpServletRequest.getParameter("taskFinishRate") != null && 
        !httpServletRequest.getParameter("taskFinishRate").equals("") && 
        !httpServletRequest.getParameter("taskFinishRate").equals("-1"))
        taskFinishRate = httpServletRequest.getParameter(
            "taskFinishRate"); 
      if (httpServletRequest.getParameter("taskType") != null && 
        !httpServletRequest.getParameter("taskType").equals("") && 
        !httpServletRequest.getParameter("taskType").equals("-1"))
        taskType = httpServletRequest.getParameter("taskType"); 
      if (httpServletRequest.getParameter("taskPriority") != null && 
        !httpServletRequest.getParameter("taskPriority").equals("") && 
        !httpServletRequest.getParameter("taskPriority").equals("-1"))
        taskPriority = httpServletRequest.getParameter("taskPriority"); 
      if (httpServletRequest.getParameter("taskStatus") != null && 
        !httpServletRequest.getParameter("taskStatus").equals("") && 
        !httpServletRequest.getParameter("taskStatus").equals("-1"))
        taskStatus = httpServletRequest.getParameter("taskStatus"); 
      if (httpServletRequest.getParameter("isEndDate") != null && 
        !httpServletRequest.getParameter("isEndDate").equals(""))
        isEndDate = httpServletRequest.getParameter("isEndDate"); 
      if (httpServletRequest.getParameter("startTaskEndTime") != null && 
        !httpServletRequest.getParameter("startTaskEndTime").equals(""))
        startTaskEndTime = httpServletRequest.getParameter(
            "startTaskEndTime"); 
      if (httpServletRequest.getParameter("endTaskEndTime") != null && 
        !httpServletRequest.getParameter("endTaskEndTime").equals(""))
        endTaskEndTime = httpServletRequest.getParameter(
            "endTaskEndTime"); 
      String[] parameters = { taskTitle, 
          taskFinishRate, 
          taskType, 
          taskPriority, 
          taskStatus, 
          isEndDate, 
          startTaskEndTime, 
          endTaskEndTime, type, sortType };
      List list = taskBD.searchCancelTask(userId, parameters, 
          new Integer(currentPage), 
          domainId);
      String recordCount = String.valueOf(taskBD.getRecordCount());
      List allTaskClassList = taskBD.selectAllTaskClass(domainId, userId, orgIdString);
      httpServletRequest.setAttribute("allTaskList", list);
      httpServletRequest.setAttribute("recordCount", recordCount);
      httpServletRequest.setAttribute("allTaskClassList", 
          allTaskClassList);
      httpServletRequest.setAttribute("maxPageItems", 
          String.valueOf(pageSize));
      httpServletRequest.setAttribute("taskTitle", taskTitle);
      httpServletRequest.setAttribute("taskFinishRate", taskFinishRate);
      httpServletRequest.setAttribute("taskType", taskType);
      httpServletRequest.setAttribute("taskPriority", taskPriority);
      httpServletRequest.setAttribute("taskStatus", taskStatus);
      httpServletRequest.setAttribute("isEndDate", isEndDate);
      httpServletRequest.setAttribute("startTaskEndTime", 
          startTaskEndTime);
      httpServletRequest.setAttribute("endTaskEndTime", endTaskEndTime);
      httpServletRequest.setAttribute("type", type);
      httpServletRequest.setAttribute("sortType", sortType);
      httpServletRequest.setAttribute("searchmode", "1");
      httpServletRequest.setAttribute("pageParameters", "action,taskTitle,taskFinishRate,taskType,taskPriority,taskStatus,isEndDate,startTaskEndTime,endTaskEndTime,type,sortType");
      return actionMapping.findForward("goto_AllCancelTask");
    } 
    if ("selectsettleCancelTask".equals(action)) {
      String type = "";
      String sortType = "";
      if (httpServletRequest.getParameter("type") != null)
        type = httpServletRequest.getParameter("type"); 
      if (httpServletRequest.getParameter("sortType") != null)
        sortType = httpServletRequest.getParameter("sortType"); 
      List allTaskClassList = taskBD.selectAllTaskClass(domainId, userId, orgIdString);
      httpServletRequest.setAttribute("allTaskClassList", 
          allTaskClassList);
      int pageSize = 15;
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter(
              "pager.offset")); 
      int currentPage = offset / pageSize + 1;
      taskBD.setVolume(pageSize);
      List list = taskBD.selectsettleCancelTask(userId, 
          new Integer(currentPage), domainId, type, sortType);
      String recordCount = String.valueOf(taskBD.getRecordCount());
      httpServletRequest.setAttribute("allTaskList", list);
      httpServletRequest.setAttribute("recordCount", recordCount);
      httpServletRequest.setAttribute("maxPageItems", 
          String.valueOf(pageSize));
      httpServletRequest.setAttribute("pageParameters", "action");
      httpServletRequest.setAttribute("sortType", sortType);
      httpServletRequest.setAttribute("type", type);
      String tag = "goto_AllSettleCancelTask";
      return actionMapping.findForward("goto_AllSettleCancelTask");
    } 
    if ("searchCancelSettleTask".equals(action)) {
      int pageSize = 15;
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter(
              "pager.offset")); 
      int currentPage = offset / pageSize + 1;
      taskBD.setVolume(pageSize);
      String type = "";
      String sortType = "";
      if (httpServletRequest.getParameter("type") != null)
        type = httpServletRequest.getParameter("type"); 
      if (httpServletRequest.getParameter("sortType") != null)
        sortType = httpServletRequest.getParameter("sortType"); 
      String taskTitle = "";
      String taskFinishRate = "";
      String taskType = "";
      String taskPriority = "";
      String taskStatus = "";
      String isEndDate = "";
      String startTaskEndTime = "";
      String endTaskEndTime = "";
      if (httpServletRequest.getParameter("taskTitle") != null && 
        !httpServletRequest.getParameter("taskTitle").equals(""))
        taskTitle = httpServletRequest.getParameter("taskTitle"); 
      if (httpServletRequest.getParameter("taskFinishRate") != null && 
        !httpServletRequest.getParameter("taskFinishRate").equals("") && 
        !httpServletRequest.getParameter("taskFinishRate").equals("-1"))
        taskFinishRate = httpServletRequest.getParameter(
            "taskFinishRate"); 
      if (httpServletRequest.getParameter("taskType") != null && 
        !httpServletRequest.getParameter("taskType").equals("") && 
        !httpServletRequest.getParameter("taskType").equals("-1"))
        taskType = httpServletRequest.getParameter("taskType"); 
      if (httpServletRequest.getParameter("taskPriority") != null && 
        !httpServletRequest.getParameter("taskPriority").equals("") && 
        !httpServletRequest.getParameter("taskPriority").equals("-1"))
        taskPriority = httpServletRequest.getParameter("taskPriority"); 
      if (httpServletRequest.getParameter("taskStatus") != null && 
        !httpServletRequest.getParameter("taskStatus").equals("") && 
        !httpServletRequest.getParameter("taskStatus").equals("-1"))
        taskStatus = httpServletRequest.getParameter("taskStatus"); 
      if (httpServletRequest.getParameter("isEndDate") != null && 
        !httpServletRequest.getParameter("isEndDate").equals(""))
        isEndDate = httpServletRequest.getParameter("isEndDate"); 
      if (httpServletRequest.getParameter("startTaskEndTime") != null && 
        !httpServletRequest.getParameter("startTaskEndTime").equals(""))
        startTaskEndTime = httpServletRequest.getParameter(
            "startTaskEndTime"); 
      if (httpServletRequest.getParameter("endTaskEndTime") != null && 
        !httpServletRequest.getParameter("endTaskEndTime").equals(""))
        endTaskEndTime = httpServletRequest.getParameter(
            "endTaskEndTime"); 
      String[] parameters = { taskTitle, 
          taskFinishRate, 
          taskType, 
          taskPriority, 
          taskStatus, 
          isEndDate, 
          startTaskEndTime, 
          endTaskEndTime, type, sortType };
      List list = taskBD.searchCancel(userId, parameters, 
          new Integer(currentPage), domainId);
      String recordCount = String.valueOf(taskBD.getRecordCount());
      List allTaskClassList = taskBD.selectAllTaskClass(domainId, userId, orgIdString);
      httpServletRequest.setAttribute("allTaskList", list);
      httpServletRequest.setAttribute("recordCount", recordCount);
      httpServletRequest.setAttribute("allTaskClassList", 
          allTaskClassList);
      httpServletRequest.setAttribute("maxPageItems", 
          String.valueOf(pageSize));
      httpServletRequest.setAttribute("taskTitle", taskTitle);
      httpServletRequest.setAttribute("taskFinishRate", taskFinishRate);
      httpServletRequest.setAttribute("taskType", taskType);
      httpServletRequest.setAttribute("taskPriority", taskPriority);
      httpServletRequest.setAttribute("taskStatus", taskStatus);
      httpServletRequest.setAttribute("isEndDate", isEndDate);
      httpServletRequest.setAttribute("startTaskEndTime", 
          startTaskEndTime);
      httpServletRequest.setAttribute("endTaskEndTime", endTaskEndTime);
      httpServletRequest.setAttribute("type", type);
      httpServletRequest.setAttribute("sortType", sortType);
      httpServletRequest.setAttribute("searchmode", "1");
      httpServletRequest.setAttribute("pageParameters", "action,taskTitle,taskFinishRate,taskType,taskPriority,taskStatus,isEndDate,startTaskEndTime,endTaskEndTime,type,sortType");
      return actionMapping.findForward("goto_AllSettleCancelTask");
    } 
    if ("selectSingleTaskHistory".equals(action)) {
      Long taskId = Long.valueOf(httpServletRequest.getParameter(
            "taskhis"));
      Long taskIds = Long.valueOf(httpServletRequest.getParameter(
            "taskId"));
      httpServletRequest.setAttribute("taskId", taskIds);
      String taskType = "";
      String start_date = "";
      String end_date = "";
      Long taskAwakeTime = null;
      List<Object[]> list = null;
      list = taskBD.selectSingleTaskHis(taskId.toString());
      if (list != null && list.size() > 0) {
        Object[] obj = list.get(0);
        taskActionForm.setTaskTitle(obj[0].toString());
        taskActionForm.setTaskPrincipalName(obj[1].toString());
        taskActionForm.setTaskPrincipal(new Long(obj[2].toString()));
        taskActionForm.setTaskCheckerName((obj[3] == null) ? "" : 
            obj[3].toString());
        taskActionForm.setTaskChecker(new Long((obj[4] == null) ? "0" : 
              obj[4].toString()));
        taskActionForm.setTaskPriority(new Integer(obj[5].toString()));
        taskActionForm.setTaskStatus(new Integer(obj[6].toString()));
        httpServletRequest.setAttribute("taskStatus", obj[6].toString());
        if (obj[7] == null) {
          taskActionForm.setTaskJoinedEmpName("");
        } else {
          taskActionForm.setTaskJoinedEmpName(obj[7].toString());
        } 
        if (obj[8] == null) {
          taskActionForm.setTaskJoinedEmp("");
        } else {
          taskActionForm.setTaskJoinedEmp(obj[8].toString());
        } 
        if (obj[9] != null) {
          taskActionForm.setTaskJoinOrgName(obj[9].toString());
        } else {
          taskActionForm.setTaskJoinOrgName("");
        } 
        taskActionForm.setTaskJoineOrg((obj[10] == null) ? "" : 
            obj[10].toString());
        taskActionForm.setTaskDescription((obj[11] == null) ? "" : 
            obj[11].toString());
        taskAwakeTime = new Long(obj[12].toString());
        if (!taskAwakeTime.equals(new Long(0L)))
          taskActionForm.setIfAwake("checkbox"); 
        taskType = obj[13].toString();
        start_date = obj[14].toString();
        end_date = obj[15].toString();
        Boolean maintenance = Boolean.FALSE;
        httpServletRequest.setAttribute("maintenance", maintenance);
        List lists = taskBD.selectAllTaskClass(domainId, userId, orgIdString);
        List accessoryList = taskBD.getAccessory(httpServletRequest
            .getParameter("taskhis"), taskIds.toString());
        httpServletRequest.setAttribute("accessoryList", accessoryList);
        httpServletRequest.setAttribute("isArranged", obj[16].toString());
        httpServletRequest.setAttribute("taskId", taskId);
        httpServletRequest.setAttribute("allTaskClassList", lists);
        httpServletRequest.setAttribute("taskType", taskType);
        httpServletRequest.setAttribute("start_date", start_date);
        httpServletRequest.setAttribute("end_date", end_date);
        httpServletRequest.setAttribute("taskAwakeTime", taskAwakeTime);
        if (obj[17] != null) {
          httpServletRequest.setAttribute("taskAppend", 
              obj[17].toString());
        } else {
          httpServletRequest.setAttribute("taskAppend", "");
        } 
      } 
      return actionMapping.findForward("goto_TaskUpdate");
    } 
    throw new UnsupportedOperationException(
        "Method perform() not yet implemented.");
  }
  
  public void periodicityTaskList(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    Long userID = new Long(session.getAttribute("userId").toString());
    Long domainId = (session.getAttribute("domainId") == null) ? 
      Long.valueOf("0") : 
      Long.valueOf(session.getAttribute("domainId").toString());
    String where = " ";
    ManagerService mbd = new ManagerService();
    HttpSession httpSession = httpServletRequest.getSession(true);
    String rightSQL = "";
    rightSQL = mbd.getRightFinalWhere(httpSession
        .getAttribute(
          "userId").toString(), 
        httpSession.getAttribute("orgId").toString(), 
        "04*08*01", "org.orgId", "periodicityTaskPO.createdEmp");
    String archivesFileRightWhere = mbd.getRightFinalWhere(session
        .getAttribute("userId").toString(), 
        session.getAttribute("orgId").toString(), 
        session.getAttribute("orgIdString").toString(), "任务中心-任务安排", 
        "新增", "periodicityTaskPO.createdOrg", 
        "periodicityTaskPO.createdEmp");
    archivesFileRightWhere = rightSQL;
    String type = "";
    String sortType = "";
    if (httpServletRequest.getParameter("type") != null)
      type = httpServletRequest.getParameter("type"); 
    if (httpServletRequest.getParameter("sortType") != null)
      sortType = httpServletRequest.getParameter("sortType"); 
    String taskJoinOrgId = "";
    String taskJoinedEmpId = "";
    String taskJoinOrgName = httpServletRequest.getParameter("taskJoinOrgName");
    String taskJoinedEmpName = httpServletRequest.getParameter("taskJoinedEmpName");
    String taskTitle = "";
    String typeR = "";
    String strsql = "";
    if (httpServletRequest.getParameter("typeR") != null && 
      !httpServletRequest.getParameter("typeR").equals("")) {
      typeR = httpServletRequest.getParameter("typeR");
      if ("org".equals(typeR)) {
        if (httpServletRequest.getParameter("taskJoinOrgId") != null && 
          !httpServletRequest.getParameter("taskJoinOrgId").equals("")) {
          taskJoinOrgId = httpServletRequest.getParameter(
              "taskJoinOrgId");
          StringTokenizer st = new StringTokenizer(taskJoinOrgId, 
              "*");
          strsql = " and ( org.orgId in (";
          while (st.hasMoreTokens())
            strsql = String.valueOf(strsql) + 
              st.nextToken() + ","; 
          strsql = String.valueOf(strsql.substring(0, strsql.length() - 1)) + 
            ")) ";
        } 
      } else if ("user".equals(typeR) && 
        httpServletRequest.getParameter("taskJoinedEmpId") != null && 
        !httpServletRequest.getParameter("taskJoinedEmpId").equals("")) {
        taskJoinedEmpId = httpServletRequest.getParameter(
            "taskJoinedEmpId");
        StringTokenizer st = new StringTokenizer(
            taskJoinedEmpId, "$");
        strsql = " and  ( user.empId in (";
        while (st.hasMoreTokens())
          strsql = String.valueOf(strsql) + st.nextToken() + ","; 
        strsql = String.valueOf(strsql.substring(0, strsql.length() - 1)) + 
          ")) ";
      } 
    } 
    archivesFileRightWhere = String.valueOf(archivesFileRightWhere) + " and periodicityTaskPO.taskPrincipal=user.empId" + strsql;
    if (httpServletRequest.getParameter("taskTitle") != null && 
      !httpServletRequest.getParameter("taskTitle").equals("")) {
      taskTitle = httpServletRequest.getParameter(
          "taskTitle");
      archivesFileRightWhere = String.valueOf(archivesFileRightWhere) + " and periodicityTaskPO.taskTitle like '%" + taskTitle + "%' ";
    } 
    if (!"".equals(type) && !"".equals(sortType)) {
      where = "where " + archivesFileRightWhere + 
        " and periodicityTaskPO.domainId=" + domainId + 
        " order by periodicityTaskPO." + type + "  " + sortType;
    } else {
      where = "where " + archivesFileRightWhere + 
        " and periodicityTaskPO.domainId=" + domainId + 
        " order by periodicityTaskPO.periodicityId desc";
    } 
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        " distinct periodicityTaskPO.periodicityId,periodicityTaskPO.taskTitle,periodicityTaskPO.onTimeMode,periodicityTaskPO.onTimeContent,periodicityTaskPO.taskPrincipalName", 
        "com.js.oa.scheme.taskcenter.po.TaskPeriodicityPO periodicityTaskPO,com.js.system.vo.usermanager.EmployeeVO user join user.organizations org", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("periodicityTaskList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("sortType", sortType);
    httpServletRequest.setAttribute("type", type);
    httpServletRequest.setAttribute("taskTitle", taskTitle);
    httpServletRequest.setAttribute("taskJoinedEmpId", taskJoinedEmpId);
    httpServletRequest.setAttribute("taskJoinOrgId", taskJoinOrgId);
    httpServletRequest.setAttribute("taskJoinOrgName", taskJoinOrgName);
    httpServletRequest.setAttribute("taskJoinedEmpName", taskJoinedEmpName);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,type,sortType,taskTitle,taskJoinedEmpId,taskJoinOrgId,taskJoinOrgName,taskJoinedEmpName,typeR");
  }
  
  public void periodicityTaskList(HttpServletRequest httpServletRequest, int offsetCopy) {
    HttpSession session = httpServletRequest.getSession(true);
    Long userID = new Long(session.getAttribute("userId").toString());
    String where = " order by periodicityTaskPO.periodicityId desc";
    int pageSize = 15;
    int offset = offsetCopy;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "periodicityTaskPO.periodicityId,periodicityTaskPO.taskTitle,periodicityTaskPO.onTimeMode,periodicityTaskPO.onTimeContent,periodicityTaskPO.taskPrincipalName", 
        "com.js.oa.scheme.taskcenter.po.TaskPeriodicityPO periodicityTaskPO", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("periodicityTaskList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
  
  private String getRightScope(Long userId, Long orgId, String rightType, String rightName) {
    String scope = "";
    ManagerService managerBD = new ManagerService();
    List<Object[]> scopeList = managerBD.getRightScope(String.valueOf(userId), 
        rightType, rightName);
    if (scopeList != null && scopeList.size() > 0) {
      Object[] obj = scopeList.get(0);
      String type = obj[0].toString();
      if ("4".equals(type)) {
        scope = (String)obj[1];
      } else if ("0".equals(type)) {
        scope = "*0*";
      } else if ("3".equals(type) || "2".equals(type)) {
        scope = "*" + orgId + "*";
      } 
    } 
    return scope;
  }
  
  public void selectSubPrincipalTask(Long taskId, HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    int pageSize = 1000;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "task.taskId,task.taskPriority,task.taskOrderCode,task.isArranged,task.taskTitle,task.taskEndTime,task.taskPrincipal,task.createdEmp,task.taskJoinedEmp,task.taskStatus,task.taskFinishRate,task.createdEmpName,task.taskPrincipalName", 
        "com.js.oa.scheme.taskcenter.po.TaskPO task", 
        "where (task.taskBaseId = " + taskId + 
        " and task.taskParentId != 0) or task.taskParentId = " + taskId + " or task.taskParentId in (select task0.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task0 where task0.taskParentId = " + 
        taskId + 
        ") order by task.parentIdString desc,task.taskCreatedTime");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("subPrincipalTaskList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
}
