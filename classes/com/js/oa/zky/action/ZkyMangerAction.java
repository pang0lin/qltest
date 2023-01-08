package com.js.oa.zky.action;

import com.js.oa.zky.po.ZkyMangersPO;
import com.js.oa.zky.service.ZkyMangerBD;
import com.js.util.page.util.PageHqlUtil;
import com.js.util.page.util.PageUtil;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ZkyMangerAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    String action = request.getParameter("action");
    ZkyMangerBD zyMangerBD = new ZkyMangerBD();
    if ("list".equals(action)) {
      mangerList(request);
      return actionMapping.findForward("list");
    } 
    if ("moudleAdd".equals(action)) {
      List moduleList = new ArrayList();
      moduleList = zyMangerBD.list("1", Long.valueOf(0L));
      request.setAttribute("moduleList", moduleList);
      return actionMapping.findForward("moudleAdd");
    } 
    if ("moudelAddSubmit".equals(action)) {
      String mangerMenu = request.getParameter("mangerMenu");
      String mangerMenuName = request.getParameter("mangerMenuName");
      String mangerUsers = request.getParameter("mangerUsers");
      String mangerUsername = request.getParameter("mangerUsername");
      String mangerType = "1";
      ZkyMangersPO po = new ZkyMangersPO();
      po.setMangerMenu(mangerMenu);
      po.setMangerMenuName(mangerMenuName);
      po.setMangerUsername(mangerUsername);
      po.setMangerUsers(mangerUsers);
      po.setMangerType(mangerType);
      zyMangerBD.save(po);
      return actionMapping.findForward("moudelAddSubmit");
    } 
    if ("modify".equals(action)) {
      Long id = new Long(request.getParameter("mangerId"));
      ZkyMangersPO po = zyMangerBD.moudleMangerLoad(id);
      List moduleList = new ArrayList();
      moduleList = zyMangerBD.list("1", Long.valueOf(0L));
      request.setAttribute("moduleList", moduleList);
      request.setAttribute("mangerMenu", po.getMangerMenu());
      request.setAttribute("mangerMenuName", po.getMangerMenuName());
      request.setAttribute("mangerUsername", po.getMangerUsername());
      request.setAttribute("mangerUsers", po.getMangerUsers());
      request.setAttribute("mangerId", id);
      if (action.equals("mangerMoudleView"))
        return actionMapping.findForward("mangerMoudleView"); 
      return actionMapping.findForward("mangerMoudleModify");
    } 
    if ("moudelUpdateSubmit".equals(action)) {
      Long id = new Long(request.getParameter("mangerId"));
      String mangerMenu = request.getParameter("mangerMenu");
      String mangerMenuName = request.getParameter("mangerMenuName");
      String mangerUsers = request.getParameter("mangerUsers");
      String mangerUsername = request.getParameter("mangerUsername");
      String mangerType = "1";
      ZkyMangersPO po = new ZkyMangersPO();
      po.setMangerMenu(mangerMenu);
      po.setMangerMenuName(mangerMenuName);
      po.setMangerUsername(mangerUsername);
      po.setMangerUsers(mangerUsers);
      po.setMangerType(mangerType);
      zyMangerBD.moudleMangerUpdate(po, id);
      return actionMapping.findForward("moudelUpdateSubmit");
    } 
    if ("deleteMangerMoudle".equals(action)) {
      String id = request.getParameter("mangerId");
      zyMangerBD.deleteMoudleManger(id);
      action = "list";
    } else {
      if ("perforlist".equals(action)) {
        perforList(request);
        return actionMapping.findForward("perforlist");
      } 
      if ("perforAdd".equals(action)) {
        List moduleList = new ArrayList();
        moduleList = zyMangerBD.list("2", Long.valueOf(0L));
        request.setAttribute("moduleList", moduleList);
        return actionMapping.findForward("perforAdd");
      } 
      if ("perforAddSubmit".equals(action)) {
        String mangerMenu = request.getParameter("mangerMenu");
        String mangerMenuName = request.getParameter("mangerMenuName");
        String mangerUsers = request.getParameter("mangerUsers");
        String mangerUsername = request.getParameter("mangerUsername");
        String mangerType = "2";
        ZkyMangersPO po = new ZkyMangersPO();
        po.setMangerMenu(mangerMenu);
        po.setMangerMenuName(mangerMenuName);
        po.setMangerUsername(mangerUsername);
        po.setMangerUsers(mangerUsers);
        po.setMangerType(mangerType);
        zyMangerBD.save(po);
        return actionMapping.findForward("perforAddSubmit");
      } 
      if ("performodify".equals(action)) {
        Long id = new Long(request.getParameter("mangerId"));
        ZkyMangersPO po = zyMangerBD.moudleMangerLoad(id);
        List moduleList = new ArrayList();
        moduleList = zyMangerBD.list("2", Long.valueOf(0L));
        request.setAttribute("moduleList", moduleList);
        request.setAttribute("mangerMenu", po.getMangerMenu());
        request.setAttribute("mangerMenuName", po.getMangerMenuName());
        request.setAttribute("mangerUsername", po.getMangerUsername());
        request.setAttribute("mangerUsers", po.getMangerUsers());
        request.setAttribute("mangerId", id);
        if (action.equals("mangerPerforView"))
          return actionMapping.findForward("mangerPerforView"); 
        return actionMapping.findForward("performodify");
      } 
      if ("perforUpdateSubmit".equals(action)) {
        Long id = new Long(request.getParameter("mangerId"));
        String mangerMenu = request.getParameter("mangerMenu");
        String mangerMenuName = request.getParameter("mangerMenuName");
        String mangerUsers = request.getParameter("mangerUsers");
        String mangerUsername = request.getParameter("mangerUsername");
        String mangerType = "2";
        ZkyMangersPO po = new ZkyMangersPO();
        po.setMangerMenu(mangerMenu);
        po.setMangerMenuName(mangerMenuName);
        po.setMangerUsername(mangerUsername);
        po.setMangerUsers(mangerUsers);
        po.setMangerType(mangerType);
        zyMangerBD.moudleMangerUpdate(po, id);
        return actionMapping.findForward("perforUpdateSubmit");
      } 
      if ("deleteMangerPerfor".equals(action)) {
        String id = request.getParameter("mangerId");
        zyMangerBD.deleteMoudleManger(id);
        action = "perforlist";
      } 
    } 
    if ("perforlist".equals(action)) {
      perforList(request);
      return actionMapping.findForward("perforlist");
    } 
    if ("list".equals(action)) {
      mangerList(request);
      return actionMapping.findForward("list");
    } 
    return actionMapping.findForward("list");
  }
  
  private void mangerList(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String viewSql = "po.mangerId,po.mangerUsers,po.mangerUsername,po.mangerMenu,po.mangerMenuName ";
    String fromSql = " com.js.oa.zky.po.ZkyMangersPO po ";
    String whereSql = "po.mangerType=1 ";
    String mangerUsername = request.getParameter("mangerUsername");
    String mangerMenuName = request.getParameter("mangerMenuName");
    if (mangerUsername != null && !"".equals(mangerUsername))
      whereSql = String.valueOf(whereSql) + " and po.mangerUsername like '%" + mangerUsername + "%'"; 
    if (mangerMenuName != null && !"".equals(mangerMenuName))
      whereSql = String.valueOf(whereSql) + " and po.mangerMenuName like '%" + mangerMenuName + "%'"; 
    String orderBy = " po.mangerId desc";
    PageUtil page = new PageHqlUtil();
    List<Object> list = page.list(request, viewSql, fromSql, whereSql, orderBy);
    request.setAttribute("mangerList", list);
  }
  
  private void perforList(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String viewSql = "po.mangerId,po.mangerUsers,po.mangerUsername,po.mangerMenu,po.mangerMenuName ";
    String fromSql = " com.js.oa.zky.po.ZkyMangersPO po ";
    String whereSql = "po.mangerType=2 ";
    String mangerUsername = request.getParameter("mangerUsername");
    String mangerMenuName = request.getParameter("mangerMenuName");
    if (mangerUsername != null && !"".equals(mangerUsername))
      whereSql = String.valueOf(whereSql) + " and po.mangerUsername like '%" + mangerUsername + "%'"; 
    if (mangerMenuName != null && !"".equals(mangerMenuName))
      whereSql = String.valueOf(whereSql) + " and po.mangerMenuName like '%" + mangerMenuName + "%'"; 
    String orderBy = " po.mangerId desc";
    PageUtil page = new PageHqlUtil();
    List<Object> list = page.list(request, viewSql, fromSql, whereSql, orderBy);
    request.setAttribute("perforList", list);
  }
}
