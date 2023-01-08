package com.js.oa.jsflow.action;

import com.js.oa.jsflow.util.ProcessActivityUtil;
import com.js.oa.jsflow.vo.ActivityVO;
import com.js.util.config.SystemCommon;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GetNextActivityAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    ConcurrentHashMap map = null;
    String directSend = "0";
    if ("1".equals(request.getParameter("curActivityIsBranch"))) {
      request.setAttribute("directSend", "0");
      request.setAttribute("activityIds", "");
      return actionMapping.findForward("nextActivity");
    } 
    String sendType = request.getParameter("sendType");
    String tableId = request.getParameter("tableId");
    String recordId = request.getParameter("recordId");
    ActivityVO activityVO = null;
    String selectUser = "", selectUserName = "", passroundUser = "", passroundUserName = "";
    String transactType = "1";
    String activityIds = "";
    ProcessActivityUtil actUtil = new ProcessActivityUtil();
    if ("0".equals(sendType)) {
      map = actUtil.getFirstActivityInfo(request);
      if (map.get("firstActIsBranch") != null)
        request.setAttribute("firstActIsBranch", map.get("firstActIsBranch").toString()); 
      if (map.get("firstCurActivityId") != null)
        request.setAttribute("firstCurActivityId", map.get("firstCurActivityId").toString()); 
    } else {
      String curActivityId = request.getParameter("activityId");
      if (!"0".equals(request.getParameter("workPrarllelCurActId")))
        curActivityId = request.getParameter("workPrarllelCurActId"); 
      map = actUtil.getNextActivityInfo(request, curActivityId, tableId, recordId);
    } 
    if (SystemCommon.getFlowDirectSend() == 0) {
      request.setAttribute("directSend", "0");
      activityIds = (map.get("activityIds") == null) ? "" : map.get("activityIds").toString();
      request.setAttribute("activityIds", activityIds);
    } else {
      directSend = (map.get("directSend") == null) ? "0" : map.get("directSend").toString();
      activityVO = (map.get("nextActivityVO") == null) ? null : (ActivityVO)map.get("nextActivityVO");
      transactType = (map.get("transactType") == null) ? "" : map.get("transactType").toString();
      selectUser = (map.get("selectUser") == null) ? "" : map.get("selectUser").toString();
      selectUserName = (map.get("selectUserName") == null) ? "" : map.get("selectUserName").toString();
      activityIds = (map.get("activityIds") == null) ? "" : map.get("activityIds").toString();
      passroundUser = (map.get("passroundUser") == null) ? "" : map.get("passroundUser").toString();
      passroundUserName = (map.get("passroundUserName") == null) ? "" : map.get("passroundUserName").toString();
      request.setAttribute("activityIds", activityIds);
      if ("1".equals(directSend)) {
        request.setAttribute("directSend", directSend);
        request.setAttribute("nextActivityVO", activityVO);
        request.setAttribute("transactType", transactType);
        request.setAttribute("selectUser", selectUser);
        request.setAttribute("selectUserName", selectUserName);
        request.setAttribute("passroundUser", passroundUser);
        request.setAttribute("passroundUserName", passroundUserName);
      } 
      if ("end".equals(transactType))
        request.setAttribute("transactType", transactType); 
    } 
    return actionMapping.findForward("nextActivity");
  }
}
