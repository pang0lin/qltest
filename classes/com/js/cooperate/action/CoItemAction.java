package com.js.cooperate.action;

import com.js.cooperate.bean.BodyBean;
import com.js.cooperate.bean.CooperateBean;
import com.js.oa.search.client.SearchService;
import com.js.util.page.Page;
import com.js.util.util.DataSourceBase;
import com.js.util.util.ParameterFilter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CoItemAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String status = request.getParameter("status");
    if (!ParameterFilter.isNumber(status))
      try {
        return new ActionForward("/public/jsp/inputerror.jsp");
      } catch (Exception exception) {} 
    request.setAttribute("statusDownLoad", status);
    String tag = "item";
    if (request.getParameter("toUserId") != null)
      tag = "userItem"; 
    String para = "distinct bpo.id,bpo.title,bpo.postTime,bpo.posterId,bpo.posterName,bpo.status,mpo.id,mpo.nodeId,bpo.level,bpo.hasTerm,bpo.term";
    String from = "com.js.cooperate.po.BodyPO bpo,com.js.cooperate.po.NodeMemberPO mpo";
    String where = "where bpo.id=mpo.bodyId";
    String flag = request.getParameter("flag");
    if ("del".equals(flag)) {
      String ids = request.getParameter("ids");
      SearchService.getInstance();
      String ifActiveUpdateDelete = SearchService.getIfActiveUpdateDelete();
      SearchService.getInstance();
      String isearchSwitch = SearchService.getiSearchSwitch();
      Connection conn = null;
      Statement stmt = null;
      String bodyidString = "";
      ResultSet rs = null;
      List<Object[]> idList = new ArrayList();
      Object[] objects = (Object[])null;
      if ("1".equals(isearchSwitch))
        try {
          conn = (new DataSourceBase()).getDataSource().getConnection();
          stmt = conn.createStatement();
          rs = stmt.executeQuery("select id,body_id from CO_NODEMEMBER where emp_id=" + userId + " and id in (" + ids + ")");
          while (rs.next())
            bodyidString = String.valueOf(bodyidString) + rs.getString(2) + ","; 
          rs.close();
          if (bodyidString.length() > 1) {
            rs = stmt.executeQuery("select wf_work_id,WF_CUREMPLOYEE_ID from jsf_work where WORKCURSTEP='协同' and WF_CUREMPLOYEE_ID=" + userId + " and WORKRECORD_ID in (" + bodyidString.substring(0, bodyidString.length() - 1) + ")");
            while (rs.next()) {
              objects = new Object[2];
              objects[0] = rs.getString(1);
              objects[1] = rs.getString(2);
              idList.add(objects);
            } 
            rs.close();
          } 
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
      if ("1".equals(status)) {
        BodyBean bbean = new BodyBean();
        bbean.delBody(ids, userId);
        if ("1".equals(isearchSwitch) && 
          idList != null && idList.size() != 0)
          for (int i = 0; i < idList.size(); i++) {
            Object[] ob = idList.get(i);
            String id = String.valueOf(ob[0]);
            if ("1".equals(isearchSwitch) && id != null && ifActiveUpdateDelete != null && !"".equals(id.toString()) && !"".equals(ifActiveUpdateDelete) && !"no".equals(ifActiveUpdateDelete)) {
              SearchService.getInstance();
              SearchService.deleteIndex(id.toString(), "jsf_coordination");
            } 
          }  
      } else {
        CooperateBean cbean = new CooperateBean();
        cbean.delMember(userId, ids, status);
        if ("1".equals(isearchSwitch) && 
          idList != null && idList.size() != 0)
          for (int i = 0; i < idList.size(); i++) {
            Object[] ob = idList.get(i);
            String id = String.valueOf(ob[0]);
            if ("1".equals(isearchSwitch) && id != null && ifActiveUpdateDelete != null && !"".equals(id.toString()) && !"".equals(ifActiveUpdateDelete) && !"no".equals(ifActiveUpdateDelete)) {
              SearchService.getInstance();
              SearchService.deleteIndex(id.toString(), "jsf_coordination");
            } 
          }  
      } 
    } 
    if ("1".equals(status)) {
      tag = "draft";
      para = "bpo.id,bpo.title,bpo.postTime,bpo.level";
      from = "com.js.cooperate.po.BodyPO bpo";
      where = "where bpo.status=1 and bpo.posterId=" + userId + " order by bpo.postTime desc";
    } else {
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
      } 
      String title = request.getParameter("title");
      if (title != null && !"null".equals(title) && !"".equals(title))
        where = String.valueOf(where) + " and bpo.title like '%" + title + "%'"; 
      String posterName = request.getParameter("posterName");
      if (posterName != null && !"null".equals(posterName) && !"".equals(posterName))
        where = String.valueOf(where) + " and bpo.posterName like '%" + posterName + "%'"; 
      if (request.getParameter("toUserId") != null) {
        String toUserId = request.getParameter("toUserId");
        where = String.valueOf(where) + " and (bpo.posterId=" + toUserId + " and bpo.sendToId like '%$" + userId + "$%')";
        where = String.valueOf(where) + " and mpo.empId=" + userId + " order by bpo.postTime desc";
      } else {
        where = String.valueOf(where) + " and mpo.empId=" + userId + " order by bpo.postTime desc";
      } 
    } 
    list(request, para, from, where);
    return mapping.findForward(tag);
  }
  
  public void list(HttpServletRequest request, String viewSql, String fromSql, String whereSql) {
    HttpSession httpSession = request.getSession(true);
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSql, fromSql, whereSql);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    request.setAttribute("list", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    if (request.getParameter("toUserId") != null) {
      request.setAttribute("toUserId", request.getParameter("toUserId"));
      request.setAttribute("pageParameters", "status,myItem,title,toUserId,posterName");
    } else {
      request.setAttribute("pageParameters", "status,myItem,title,posterName");
    } 
  }
}
