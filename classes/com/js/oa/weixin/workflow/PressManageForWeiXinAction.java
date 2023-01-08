package com.js.oa.weixin.workflow;

import com.js.message.RealTimeUtil;
import com.js.oa.pressdeal.po.OaPersonoaFeedbackPO;
import com.js.oa.pressdeal.po.OaPersonoaPressPO;
import com.js.oa.pressdeal.service.PersonalOAPressManageBD;
import com.js.oa.pressdeal.util.ConversionString;
import com.js.oa.userdb.util.DbOpt;
import com.js.util.page.Page;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PressManageForWeiXinAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
    String forward = "";
    String action = String.valueOf(httpServletRequest.getParameter("action"));
    PersonalOAPressManageBD bd = new PersonalOAPressManageBD();
    String domainId = String.valueOf(httpServletRequest.getSession().getAttribute("domainId"));
    String userId = String.valueOf(httpServletRequest.getSession().getAttribute("userId"));
    String userName = String.valueOf(httpServletRequest.getSession().getAttribute("userName"));
    String orgId = String.valueOf(httpServletRequest.getSession().getAttribute("orgId"));
    String orgName = String.valueOf(httpServletRequest.getSession().getAttribute("orgName"));
    if ("addNewPress".equals(action)) {
      String press_title = httpServletRequest.getParameter("title");
      String press_content = httpServletRequest.getParameter("content");
      String press_mail_check = httpServletRequest.getParameter("tel_info");
      String receiveUserNameStr = httpServletRequest.getParameter("userNameStr");
      String receiveUserIdStr = httpServletRequest.getParameter("userIdStr");
      String press_category = "无";
      String press_subcategory = "无";
      ConversionString cs = new ConversionString(receiveUserIdStr);
      String grpIds = cs.getGroupIdString();
      String orgIds = cs.getOrgIdString();
      String userIds = cs.getUserIdString();
      String orginIds = receiveUserIdStr;
      String all_userIds = bd.getUserIds(orginIds);
      boolean tmpboo = false;
      if ("true".equals(press_mail_check))
        tmpboo = true; 
      bd.sendNewPress(orginIds, receiveUserNameStr, userId, userName, orgName, press_title, press_content, press_category, press_subcategory, domainId, tmpboo, httpServletRequest);
      forward = "gotopress_add";
    } 
    if ("sender_list".equals(action)) {
      String press_title = httpServletRequest.getParameter("title");
      String press_category = httpServletRequest.getParameter("category");
      String press_subcategory = httpServletRequest.getParameter("subcategory");
      String receiver_name = httpServletRequest.getParameter("receiver_name");
      String receiver_Dep = httpServletRequest.getParameter("receiver_Dep");
      String date_start = httpServletRequest.getParameter("date_start");
      String date_end = httpServletRequest.getParameter("date_end");
      String date_check = httpServletRequest.getParameter("check_date");
      if (date_start != null) {
        date_start = myReplace(date_start, '/', '-');
        date_end = myReplace(date_end, '/', '-');
      } 
      SendList_Func2(press_title, 
          press_category, 
          press_subcategory, 
          receiver_name, 
          receiver_Dep, 
          date_start, 
          date_end, 
          userId, 
          0, 
          httpServletRequest, 
          date_check);
      forward = "to_sender_list";
    } 
    if ("receive_list".equals(action)) {
      String press_title = httpServletRequest.getParameter("title");
      String press_category = httpServletRequest.getParameter("category");
      String press_subcategory = httpServletRequest.getParameter("subcategory");
      String receiver_name = httpServletRequest.getParameter("receiver_name");
      String receiver_Dep = httpServletRequest.getParameter("receiver_Dep");
      String date_start = httpServletRequest.getParameter("date_start");
      String date_end = httpServletRequest.getParameter("date_end");
      String date_check = httpServletRequest.getParameter("check_date");
      if (date_start != null) {
        date_start = myReplace(date_start, '/', '-');
        date_end = myReplace(date_end, '/', '-');
      } 
      SendList_Func2(press_title, 
          press_category, 
          press_subcategory, 
          receiver_name, 
          receiver_Dep, 
          date_start, 
          date_end, 
          userId, 
          1, 
          httpServletRequest, 
          date_check);
      forward = "receive_list";
    } 
    if ("delete_receive_press".equals(action)) {
      String pressId = String.valueOf(httpServletRequest.getParameter("pressId"));
      bd.Del_Receive_Press(pressId, userId, "1");
      forward = "del_receivePress_back";
    } 
    if ("delete_send_press".equals(action)) {
      String pressId = String.valueOf(httpServletRequest.getParameter("pressId"));
      bd.del_send_press(pressId);
      forward = "del_back";
    } 
    if ("add_feedback".equals(action)) {
      String feedback_time = bd.getDataBaseDatetime();
      String pressId = String.valueOf(httpServletRequest.getParameter("pressId"));
      String content = httpServletRequest.getParameter("content");
      String userAccount = bd.getUserAccountsByEmpId(bd.getPress_sender_userId(pressId));
      bd.addFeedback_RTXMail(feedback_time, userName, pressId, content, domainId, false);
      RealTimeUtil util = new RealTimeUtil();
      String rtx_title = String.valueOf(userName) + bd.getDataBaseDatetime().substring(0, 16) + " 对您的如下催办进行了反馈！ \r\n ";
      util.sendNotify(userAccount, "新反馈", String.valueOf(rtx_title) + bd.getPress_title(pressId), "0", "0");
      forward = "goto_addFeedback";
    } 
    if ("feedback_list".equals(action)) {
      Display_all_feedback(httpServletRequest);
      forward = "feedback_list";
    } 
    if ("goto_display_press".equals(action)) {
      String pressId = String.valueOf(httpServletRequest.getParameter("pressId"));
      String actType = String.valueOf(httpServletRequest.getParameter("actType"));
      String workflow = String.valueOf(httpServletRequest.getParameter("workflow"));
      actType = (actType == null || "".equals(actType.trim()) || "null".equals(actType.trim())) ? "" : actType.trim();
      if ("receivePress".equals(actType)) {
        bd.updatePressStatus(new Long(pressId));
        httpServletRequest.setAttribute("receivePress", "receivePress");
        String pageOffset = String.valueOf(httpServletRequest.getParameter("pager.offset"));
        httpServletRequest.setAttribute("pager.offset", myTrim(pageOffset));
        String title = String.valueOf(httpServletRequest.getParameter("title"));
        httpServletRequest.setAttribute("title", myTrim(title));
        String category = String.valueOf(httpServletRequest.getParameter("category"));
        httpServletRequest.setAttribute("category", myTrim(category));
        String subcategory = String.valueOf(httpServletRequest.getParameter("subcategory"));
        httpServletRequest.setAttribute("subcategory", myTrim(subcategory));
        String receiver_name = String.valueOf(httpServletRequest.getParameter("receiver_name"));
        httpServletRequest.setAttribute("receiver_name", myTrim(receiver_name));
        String date_start = String.valueOf(httpServletRequest.getParameter("date_start"));
        httpServletRequest.setAttribute("date_start", myTrim(date_start));
        String date_end = String.valueOf(httpServletRequest.getParameter("date_end"));
        httpServletRequest.setAttribute("date_end", myTrim(date_end));
        String check_date = String.valueOf(httpServletRequest.getParameter("check_date"));
        httpServletRequest.setAttribute("check_date", myTrim(check_date));
      } 
      OaPersonoaPressPO press = bd.getPressByIdLoad(pressId);
      httpServletRequest.setAttribute("press", press);
      String remind = (httpServletRequest.getParameter("remind") == null) ? "" : httpServletRequest.getParameter("remind");
      httpServletRequest.setAttribute("remind", remind);
      httpServletRequest.setAttribute("workflow", workflow);
      forward = "goto_display_press";
    } 
    if ("show_myallfeedback".equals(action)) {
      String press_title = httpServletRequest.getParameter("press_title");
      String feedback_user = httpServletRequest.getParameter("feedback_user");
      String date_start = httpServletRequest.getParameter("date_start");
      String date_end = httpServletRequest.getParameter("date_end");
      String date_check = httpServletRequest.getParameter("check_date");
      if (date_start != null) {
        date_start = myReplace(date_start, '/', '-');
        date_end = myReplace(date_end, '/', '-');
      } 
      bd.updateAllSendPress_Status(userId, domainId);
      do_show_receiveAllFeedback(press_title, feedback_user, date_start, date_end, userId, domainId, httpServletRequest, date_check);
      forward = "show_all_feedback";
    } 
    if ("changePressStatusByAJAX".equals(action)) {
      String tmppressId = String.valueOf(httpServletRequest.getParameter("pressId"));
      bd.updatePressIsNew(tmppressId);
      forward = "";
    } 
    if ("feedback_disp".equals(action)) {
      String feedbackId = String.valueOf(httpServletRequest.getParameter("feedback_Id"));
      String feedback_user = String.valueOf(httpServletRequest.getParameter("feedback_user"));
      String date_start = String.valueOf(httpServletRequest.getParameter("date_start"));
      String date_end = String.valueOf(httpServletRequest.getParameter("date_end"));
      String press_title = String.valueOf(httpServletRequest.getParameter("press_title"));
      String check_date = String.valueOf(httpServletRequest.getParameter("check_date"));
      String offset = String.valueOf(httpServletRequest.getParameter("pager.offset"));
      httpServletRequest.setAttribute("date_start", date_start);
      httpServletRequest.setAttribute("date_end", date_end);
      httpServletRequest.setAttribute("press_title", press_title);
      httpServletRequest.setAttribute("feedback_userName", feedback_user);
      httpServletRequest.setAttribute("check_date", check_date);
      httpServletRequest.setAttribute("pager.offset", offset);
      OaPersonoaFeedbackPO po = bd.loadFeedback(new Long(feedbackId));
      OaPersonoaPressPO ppo = bd.getPressByIdLoad(String.valueOf(po.getPressId()));
      String feedback_content = "";
      feedback_content = po.getContent();
      feedback_content = (feedback_content == null) ? "" : feedback_content;
      httpServletRequest.setAttribute("feedback_content", feedback_content);
      httpServletRequest.setAttribute("title", ppo.getTitle());
      bd.updateFeedbackStatus(new Long(feedbackId));
      forward = "feedback_disp";
    } 
    return actionMapping.findForward(forward);
  }
  
  private void Display_all_feedback(HttpServletRequest httpServletRequest) {
    String pressId = httpServletRequest.getParameter("pressId");
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int pageSize = 15;
    int currentPage = offset / pageSize + 1;
    String sql_po = " com.js.oa.pressdeal.po.OaPersonoaFeedbackPO feedback ";
    String sql_param = " feedback.feedbackTime,feedback.userName,feedback.content ";
    String sql_where = " where feedback.pressId = " + pressId + " order by feedback.feedbackTime desc";
    Page page = new Page(sql_param, sql_po, sql_where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List pagelist = page.getResultList();
    int recordCount = page.getRecordCount();
    httpServletRequest.setAttribute("page.offset", String.valueOf(offset));
    httpServletRequest.setAttribute("pressId", String.valueOf(pressId));
    httpServletRequest.setAttribute("currentPage", String.valueOf(currentPage));
    httpServletRequest.setAttribute("recordCount", String.valueOf(recordCount));
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("groupList", pagelist);
    httpServletRequest.setAttribute("pageParameters", "action,pressId");
  }
  
  private String getDateStr() {
    String str = "";
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat();
    sdf.applyPattern("yyyy-MM-dd");
    str = sdf.format(date);
    return str;
  }
  
  private String myReplace(String str, char char_old, char char_new) {
    String rslt = null;
    char[] array = str.toCharArray();
    for (int i = 0; i < array.length; i++) {
      if (array[i] == char_old)
        array[i] = char_new; 
    } 
    rslt = new String(array);
    return rslt;
  }
  
  private void SendList_Func2(String press_title, String press_category, String press_subcategory, String receiver_name, String receiver_Dep, String date_start, String date_end, String userId, int pressStatus, HttpServletRequest httpServletRequest, String check_date) {
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int pageSize = 5;
    if (pressStatus == 0) {
      pageSize = 15;
    } else {
      pageSize = 15;
    } 
    int currentPage = offset / pageSize + 1;
    String sql = "";
    String sub_sql = "";
    String sql_param = "";
    String sql_po = "";
    String sql_where = "";
    String start_date = null;
    String end_date = null;
    int flag2 = 0;
    int flag3 = 0;
    if (date_start != null && date_start.length() > 0) {
      start_date = date_start;
      end_date = date_end;
    } else {
      start_date = end_date = getDateStr();
    } 
    date_start = String.valueOf(date_start) + " 00:00:00";
    date_end = String.valueOf(date_end) + " 23:59:59";
    sql_param = " press.pressId,press.title,press.sendUsername,press.sendUserDep,press.receiveUsernameStr,press.catorgry,press.subcatorgryName,press.isNew,press.pressStatus,press.dispatchTime";
    sql_po = "com.js.oa.pressdeal.po.OaPersonoaPressPO press";
    sql = " where press.pressId in ( ";
    sub_sql = " select distinct opupr.pressId from com.js.oa.pressdeal.po.OaPersonoaUserPressRelatioPO opupr where opupr.userId =" + userId + " and opupr.pressStatus = " + pressStatus;
    if (receiver_name != null && receiver_name.trim().length() > 0) {
      if (flag3 == 0)
        sub_sql = String.valueOf(sub_sql) + " and opupr.pressStatus <> " + pressStatus + " "; 
      sub_sql = String.valueOf(sub_sql) + " and opupr.userName like '%" + receiver_name.trim() + "%" + "' ";
      flag3 = 1;
    } 
    if (receiver_Dep != null && receiver_Dep.trim().length() > 0) {
      if (flag3 == 0)
        sub_sql = String.valueOf(sub_sql) + " and opupr.pressStatus <> " + pressStatus + " "; 
      sub_sql = String.valueOf(sub_sql) + " and opupr.orgName like '%" + receiver_Dep.trim() + "%" + "' ";
    } 
    sub_sql = String.valueOf(sub_sql) + " )";
    sql = String.valueOf(sql) + sub_sql;
    if (press_title != null && press_title.trim().length() > 0)
      sql = String.valueOf(sql) + " and press.title like '%" + press_title.trim() + "%" + "'"; 
    if (press_category != null && press_category.trim().length() > 0)
      sql = String.valueOf(sql) + " and press.catorgry like '" + press_category.trim() + "'"; 
    if (press_subcategory != null && press_subcategory.trim().length() > 0)
      sql = String.valueOf(sql) + " and press.subcatorgryName like '%" + press_subcategory.trim() + "%" + "'"; 
    if (check_date != null && "true".endsWith(check_date.trim())) {
      if (date_start.length() > 14) {
        DbOpt dbtypeutil = new DbOpt();
        if (DbOpt.dbtype.equals("oracle")) {
          sql = String.valueOf(sql) + " and press.dispatchTime >= to_date('" + date_start + 
            "','YYYY-MM-DD HH24:MI:SS') and  press.dispatchTime <= to_date('" + 
            date_end + "','YYYY-MM-DD HH24:MI:SS')";
        } else {
          sql = String.valueOf(sql) + "and press.dispatchTime >= JSDB.FN_STRTODATE('" + 
            date_start + 
            "','L') and press.dispatchTime <= JSDB.FN_STRTODATE('" + 
            date_end + "','L')";
        } 
      } 
    } else {
      check_date = "false";
    } 
    sql = String.valueOf(sql) + " order by press.pressId desc";
    sql_where = sql;
    Page page = new Page(sql_param, sql_po, sql_where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List pagelist = page.getResultList();
    int recordCount = page.getRecordCount();
    httpServletRequest.setAttribute("press_title", press_title);
    httpServletRequest.setAttribute("press_category", press_category);
    httpServletRequest.setAttribute("press_subcategory", press_subcategory);
    httpServletRequest.setAttribute("receiver_name", receiver_name);
    httpServletRequest.setAttribute("receiver_Dep", receiver_Dep);
    httpServletRequest.setAttribute("date_start", start_date);
    httpServletRequest.setAttribute("date_end", end_date);
    httpServletRequest.setAttribute("recordCount", String.valueOf(recordCount));
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("groupList", pagelist);
    httpServletRequest.setAttribute("check_date", check_date);
    httpServletRequest.setAttribute("pageParameters", "action,title,category,subcategory,receiver_name,receiver_Dep,date_start,date_end,check_date");
  }
  
  private String mySplit(String str, char c) {
    String rslt = "";
    char[] ch = str.toCharArray();
    for (int i = 0; i < ch.length; i++) {
      if (ch[i] == c)
        if (i == 0) {
          ch[i] = '~';
        } else if (i < ch.length - 1) {
          if (ch[i + 1] != c) {
            ch[i] = '-';
          } else {
            ch[i] = '~';
          } 
        } else {
          ch[i] = '~';
        }  
    } 
    rslt = new String(ch);
    rslt = rslt.replaceAll("~", "");
    return rslt;
  }
  
  private String mySplit2(String str, char c) {
    String rslt = "";
    char[] ch = str.toCharArray();
    for (int i = 0; i < ch.length; i++) {
      if (ch[i] == c)
        ch[i] = ','; 
    } 
    rslt = new String(ch);
    return rslt;
  }
  
  private void do_show_receiveAllFeedback(String press_title, String feedback_username, String date_start, String date_end, String userId, String domainId, HttpServletRequest httpServletRequest, String check_date) {
    int flag = 0;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int pageSize = 15;
    int currentPage = offset / pageSize + 1;
    String tmp_date_start = "";
    String tmp_date_end = "";
    if (date_start != null && date_start.length() > 0) {
      tmp_date_start = date_start;
      tmp_date_end = date_end;
      flag = 1;
    } else {
      tmp_date_start = tmp_date_end = getDateStr();
    } 
    tmp_date_start = String.valueOf(tmp_date_start) + " 00:00:00";
    tmp_date_end = String.valueOf(tmp_date_end) + " 23:59:59";
    String sql_param = "";
    String sql_po = "";
    String sql_where = "";
    sql_param = "press.pressId,press.title,feedback.feedbackTime,feedback.userName,feedback.content,feedback.feedbackStatus,feedback.feedbackId";
    sql_po = "com.js.oa.pressdeal.po.OaPersonoaPressPO press,com.js.oa.pressdeal.po.OaPersonoaFeedbackPO feedback";
    sql_where = String.valueOf(sql_where) + " where press.pressId = feedback.pressId ";
    if (press_title != null && press_title.trim().length() > 0)
      sql_where = String.valueOf(sql_where) + " and press.title like '%" + press_title + "%" + "'"; 
    if (feedback_username != null && feedback_username.trim().length() > 0)
      sql_where = String.valueOf(sql_where) + " and feedback.userName like '%" + feedback_username + "%" + "'"; 
    if (check_date != null && "true".endsWith(check_date.trim())) {
      if (tmp_date_start.length() > 14 && flag == 1) {
        DbOpt dbtypeutil = new DbOpt();
        if (DbOpt.dbtype.equals("oracle")) {
          sql_where = String.valueOf(sql_where) + " and feedback.feedbackTime >= to_date('" + 
            tmp_date_start + 
            "','YYYY-MM-DD HH24:MI:SS') and  feedback.feedbackTime <= to_date('" + 
            tmp_date_end + "','YYYY-MM-DD HH24:MI:SS')";
        } else {
          sql_where = String.valueOf(sql_where) + "and feedback.feedbackTime >= JSDB.FN_STRTODATE('" + 
            tmp_date_start + 
            "','L') and feedback.feedbackTime <= JSDB.FN_STRTODATE('" + 
            tmp_date_end + "','L')";
        } 
      } 
    } else {
      check_date = "false";
    } 
    sql_where = String.valueOf(sql_where) + " and  press.pressId in ( select opupr.pressId from com.js.oa.pressdeal.po.OaPersonoaUserPressRelatioPO opupr ";
    sql_where = String.valueOf(sql_where) + " where opupr.userId = " + userId + " and opupr.pressStatus = 0 and opupr.domainId = " + domainId + " )  order by feedback.feedbackTime desc ";
    Page page = new Page(sql_param, sql_po, sql_where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List pagelist = page.getResultList();
    int recordCount = page.getRecordCount();
    httpServletRequest.setAttribute("press_title", press_title);
    httpServletRequest.setAttribute("feedback_userName", feedback_username);
    httpServletRequest.setAttribute("date_start", tmp_date_start);
    httpServletRequest.setAttribute("date_end", tmp_date_end);
    httpServletRequest.setAttribute("recordCount", String.valueOf(recordCount));
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("groupList", pagelist);
    httpServletRequest.setAttribute("check_date", check_date);
    httpServletRequest.setAttribute("pageParameters", "action,title,feedback_user,date_start,date_end,check_date,press_title");
  }
  
  private String myTrim(String str) {
    return (str == null || "".equals(str.trim()) || "null".equals(str.trim())) ? "" : str.trim();
  }
}
