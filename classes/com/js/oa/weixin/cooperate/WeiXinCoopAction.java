package com.js.oa.weixin.cooperate;

import com.js.cooperate.bean.BodyBean;
import com.js.system.service.messages.MessagesBD;
import com.js.util.page.Page;
import com.js.wap.util.WapUtil;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WeiXinCoopAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    String version = (String)session.getAttribute("wapVersion");
    String action = request.getParameter("action");
    String beginIndex = (request.getParameter("beginIndex") == null) ? "0" : request.getParameter("beginIndex").toString();
    String operate = (request.getParameter("operate") == null) ? "" : request.getParameter("operate").toString();
    request.setAttribute("beginIndex", beginIndex);
    request.setAttribute("operate", operate);
    String tag = "";
    if ("3G".equals(version)) {
      tag = "list_3g";
    } else {
      tag = "list_3g";
    } 
    if ("listseverals".equals(action)) {
      if (version.equals("3G")) {
        tag = "listseverals_3g";
      } else if (version.equals("COLOR")) {
        tag = "listseverals_3g";
      } 
      list_severals(request);
    } else if ("list".equals(action)) {
      list(request);
    } else if ("toDealwith".equals(action)) {
      tag = "toDealwith_3g";
      BodyBean bodyBean = new BodyBean();
      String bodyId = request.getParameter("bodyId");
      if (bodyId == null || "".equals(bodyId)) {
        request.setAttribute("failure", "cooprate");
        return actionMapping.findForward("failure");
      } 
      String memberId = request.getParameter("memberId");
      boolean test = bodyBean.updateRecord(memberId);
      String status = request.getParameter("status");
      request.setAttribute("status", status);
      String userId = (String)session.getAttribute("userId");
      String comeFrom = request.getParameter("comeFrom");
      request.setAttribute("comeFrom", comeFrom);
      String type = request.getParameter("type");
      request.setAttribute("type", type);
      Map map = bodyBean.getBodyInfo(bodyId, memberId);
      request.setAttribute("bodyInfo", map.get("bodyInfo"));
      request.setAttribute("bodyEx", map.get("bodyEx"));
      request.setAttribute("opinion", map.get("opinion"));
      request.setAttribute("subOpinion", map.get("subOpinion"));
      request.setAttribute("bodyAttach", map.get("bodyAttach"));
      request.setAttribute("bodyExAttach", map.get("bodyExAttach"));
      request.setAttribute("opinionAttach", map.get("opinionAttach"));
      request.setAttribute("dealwithStatus", map.get("dealwithStatus"));
      request.setAttribute("relProjectName", map.get("relProjectName"));
      request.setAttribute("nodeRight", map.get("nodeRight"));
      request.setAttribute("bodyId", bodyId);
      request.setAttribute("memberId", memberId);
      if (request.getParameter("from") != null && request.getParameter("fromAppMessage") == null)
        request.setAttribute("from", request.getParameter("from")); 
      MessagesBD messagesBD = new MessagesBD();
      try {
        messagesBD.changeMessageStatus(bodyId, userId, "Cooperate", "a");
      } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("ERROR", "错误：" + e);
        if (version.equals("3G")) {
          tag = "error_3g";
        } else if (version.equals("COLOR")) {
          tag = "error";
        } 
        return actionMapping.findForward(tag);
      } 
    } 
    return actionMapping.findForward(tag);
  }
  
  public void list(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String keyword = (request.getParameter("keyword") != null) ? request.getParameter("keyword") : "";
    if (keyword != null && !"".equals(keyword))
      try {
        keyword = URLDecoder.decode(keyword, "utf-8");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }  
    String para = "distinct bpo.id,bpo.title,bpo.postTime,bpo.posterId,bpo.posterName,bpo.status,mpo.id,mpo.nodeId,bpo.level,bpo.hasTerm,bpo.term,emp.userAccounts ";
    String from = "com.js.cooperate.po.BodyPO bpo,com.js.cooperate.po.NodeMemberPO mpo,com.js.system.vo.usermanager.EmployeeVO emp ";
    String where = "where bpo.id=mpo.bodyId and bpo.posterId=emp.empId ";
    if (keyword != null && !"".equals(keyword))
      where = String.valueOf(where) + " and bpo.title like '%" + keyword + "%' "; 
    String status = request.getParameter("status");
    request.setAttribute("status", status);
    if ("10".equals(status)) {
      where = String.valueOf(where) + " and mpo.status=10";
    } else if ("201".equals(status)) {
      where = String.valueOf(where) + " and bpo.status=10 and mpo.status<>10 and mpo.status<>0 and mpo.isPoster=0";
    } else if ("202".equals(status)) {
      where = String.valueOf(where) + " and bpo.status=10 and mpo.isPoster=1 and bpo.posterId=" + userId;
    } else if ("1001".equals(status)) {
      where = String.valueOf(where) + " and bpo.status=100 and mpo.isPoster=0";
    } else if ("1002".equals(status)) {
      where = String.valueOf(where) + " and bpo.status=100 and mpo.isPoster=1 and bpo.posterId=" + userId;
    } else if ("202and1002".equals(status)) {
      where = String.valueOf(where) + " and ((bpo.status=100 and mpo.isPoster=1 and bpo.posterId=" + userId + ") or (bpo.status=10 and mpo.isPoster=1 and bpo.posterId=" + userId + "))";
    } 
    where = String.valueOf(where) + " and mpo.empId=" + userId + " order by bpo.level desc,bpo.postTime desc";
    int pageSize = WapUtil.LIMITED;
    int offset = 0;
    if (request.getParameter("beginIndex") != null)
      offset = Integer.parseInt(request.getParameter("beginIndex")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(para, from, where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    int recordCount = page.getRecordCount();
    String pageCount = String.valueOf(page.getPageCount());
    request.setAttribute("itemList", list);
    request.setAttribute("RECORD_COUNT", Integer.valueOf(recordCount));
    request.setAttribute("keyword", keyword);
  }
  
  public void list_severals(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String para = "distinct bpo.id,bpo.title,bpo.postTime,bpo.posterId,bpo.posterName,bpo.status,mpo.id,mpo.nodeId,bpo.level,bpo.hasTerm,bpo.term";
    String from = "com.js.cooperate.po.BodyPO bpo,com.js.cooperate.po.NodeMemberPO mpo";
    String where1 = "where bpo.id=mpo.bodyId";
    String where2 = "where bpo.id=mpo.bodyId";
    where1 = String.valueOf(where1) + " and mpo.status=10";
    where2 = String.valueOf(where2) + " and ((bpo.status=100 and mpo.isPoster=1 and bpo.posterId=" + userId + ") or (bpo.status=10 and mpo.isPoster=1 and bpo.posterId=" + userId + "))";
    where1 = String.valueOf(where1) + " and mpo.empId=" + userId + " order by bpo.postTime desc";
    where2 = String.valueOf(where2) + " and mpo.empId=" + userId + " order by bpo.postTime desc";
    int pageSize = WapUtil.LIMITED;
    int offset = 0;
    if (request.getParameter("beginIndex") != null)
      offset = Integer.parseInt(request.getParameter("beginIndex")); 
    int currentPage = offset / pageSize + 1;
    Page page1 = new Page(para, from, where1);
    Page page2 = new Page(para, from, where2);
    page1.setPageSize(5);
    page2.setPageSize(5);
    page1.setcurrentPage(currentPage);
    page2.setcurrentPage(currentPage);
    List list1 = page1.getResultList();
    List list2 = page2.getResultList();
    int recordCount1 = page1.getRecordCount();
    int recordCount2 = page2.getRecordCount();
    String pageCount1 = String.valueOf(page1.getPageCount());
    String pageCount2 = String.valueOf(page2.getPageCount());
    request.setAttribute("list1", list1);
    request.setAttribute("list2", list2);
    request.setAttribute("recordCount1", Integer.valueOf(recordCount1));
    request.setAttribute("recordCount2", Integer.valueOf(recordCount2));
  }
}
