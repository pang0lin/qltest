package com.js.oa.hr.resume.action;

import com.js.oa.hr.resume.po.ResumePO;
import com.js.oa.hr.resume.service.ResumeBD;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ResumeAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    ResumeActionForm resumeActionForm = (ResumeActionForm)actionForm;
    String action = (httpServletRequest.getParameter("action") == null) ? 
      "list" : httpServletRequest.getParameter("action");
    String tag = "";
    if ("list".equals(action)) {
      tag = "list";
      list(httpServletRequest);
    } else if ("info".equals(action)) {
      tag = "info";
      String id = httpServletRequest.getParameter("id");
      ResumeBD bd = new ResumeBD();
      ResumePO po = bd.getResumeInfo(id);
      httpServletRequest.setAttribute("id", po.getId());
      if (po.getName() != null) {
        httpServletRequest.setAttribute("name", po.getName());
      } else {
        httpServletRequest.setAttribute("name", "");
      } 
      if (po.getAge() != null) {
        httpServletRequest.setAttribute("age", po.getAge());
      } else {
        httpServletRequest.setAttribute("age", "");
      } 
      if (po.getSex() != null) {
        httpServletRequest.setAttribute("sex", po.getSex());
      } else {
        httpServletRequest.setAttribute("sex", "");
      } 
      if (po.getTel() != null) {
        httpServletRequest.setAttribute("tel", po.getTel());
      } else {
        httpServletRequest.setAttribute("tel", "");
      } 
      if (po.getApplyPosition() != null) {
        httpServletRequest.setAttribute("applyPosition", po.getApplyPosition());
      } else {
        httpServletRequest.setAttribute("applyPosition", "");
      } 
      if (po.getTopEducation() != null) {
        httpServletRequest.setAttribute("topEducation", po.getTopEducation());
      } else {
        httpServletRequest.setAttribute("topEducation", "");
      } 
      if (po.getTopEducationSchool() != null) {
        httpServletRequest.setAttribute("topEducationSchool", po.getTopEducationSchool());
      } else {
        httpServletRequest.setAttribute("topEducationSchool", "");
      } 
      if (po.getTopDegree() != null) {
        httpServletRequest.setAttribute("topDegree", po.getTopDegree());
      } else {
        httpServletRequest.setAttribute("topDegree", "");
      } 
      if (po.getTopDegreeSchool() != null) {
        httpServletRequest.setAttribute("topDegreeSchool", po.getTopDegreeSchool());
      } else {
        httpServletRequest.setAttribute("topDegreeSchool", "");
      } 
      if (po.getPoliticalStatus() != null) {
        httpServletRequest.setAttribute("politicalStatus", po.getPoliticalStatus());
      } else {
        httpServletRequest.setAttribute("politicalStatus", "");
      } 
      if (po.getProfessionType() != null) {
        httpServletRequest.setAttribute("professionType", po.getProfessionType());
      } else {
        httpServletRequest.setAttribute("professionType", "");
      } 
      if (po.getProfessionName() != null) {
        httpServletRequest.setAttribute("professionName", po.getProfessionName());
      } else {
        httpServletRequest.setAttribute("professionName", "");
      } 
      if (po.getBachelorProfession() != null) {
        httpServletRequest.setAttribute("bachelorProfession", po.getBachelorProfession());
      } else {
        httpServletRequest.setAttribute("bachelorProfession", "");
      } 
      if (po.getBachelorSchool() != null) {
        httpServletRequest.setAttribute("bachelorSchool", po.getBachelorSchool());
      } else {
        httpServletRequest.setAttribute("bachelorSchool", "");
      } 
      if (po.getUrl() != null) {
        httpServletRequest.setAttribute("url", po.getUrl());
      } else {
        httpServletRequest.setAttribute("url", "");
      } 
      if (po.getZt() != null) {
        httpServletRequest.setAttribute("zt", po.getZt());
      } else {
        httpServletRequest.setAttribute("zt", "");
      } 
      if (po.getBz() != null) {
        httpServletRequest.setAttribute("bz", po.getBz());
      } else {
        httpServletRequest.setAttribute("bz", "");
      } 
    } else if ("save".equals(action)) {
      String id = httpServletRequest.getParameter("id");
      String zt = httpServletRequest.getParameter("zt");
      String bz = httpServletRequest.getParameter("bz");
      ResumeBD bd = new ResumeBD();
      bd.save(id, zt, bz);
      return null;
    } 
    return actionMapping.findForward(tag);
  }
  
  private void list(HttpServletRequest httpServletRequest) {
    String curUserId = httpServletRequest.getSession(true).getAttribute(
        "userId")
      .toString();
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String searchSQL = "";
    if (httpServletRequest.getParameter("id") != null && 
      !"".equals(httpServletRequest.getParameter("id")) && 
      !"null".equals(httpServletRequest.getParameter("id")))
      searchSQL = String.valueOf(searchSQL) + " and po.id like '%" + 
        httpServletRequest.getParameter("id") + "%'"; 
    if (httpServletRequest.getParameter("name") != null && 
      !"".equals(httpServletRequest.getParameter("name")) && 
      !"null".equals(httpServletRequest.getParameter("name")))
      searchSQL = String.valueOf(searchSQL) + " and po.name like '%" + 
        httpServletRequest.getParameter("name") + "%'"; 
    if (httpServletRequest.getParameter("tel") != null && 
      !"".equals(httpServletRequest.getParameter("tel")) && 
      !"null".equals(httpServletRequest.getParameter("tel")))
      searchSQL = String.valueOf(searchSQL) + " and po.tel like '%" + 
        httpServletRequest.getParameter("tel") + "%'"; 
    if (httpServletRequest.getParameter("applyPosition") != null && 
      !"".equals(httpServletRequest.getParameter("applyPosition")) && 
      !"null".equals(httpServletRequest.getParameter("applyPosition")))
      searchSQL = String.valueOf(searchSQL) + " and po.applyPosition like '%" + 
        httpServletRequest.getParameter("applyPosition") + "%'"; 
    if (httpServletRequest.getParameter("topEducation") != null && 
      !"".equals(httpServletRequest.getParameter("topEducation")) && 
      !"null".equals(httpServletRequest.getParameter("topEducation")))
      searchSQL = String.valueOf(searchSQL) + " and po.topEducation like '%" + 
        httpServletRequest.getParameter("topEducation") + "%'"; 
    if (httpServletRequest.getParameter("professionType") != null && 
      !"".equals(httpServletRequest.getParameter("professionType")) && 
      !"null".equals(httpServletRequest.getParameter("professionType")))
      searchSQL = String.valueOf(searchSQL) + " and po.professionType like '%" + 
        httpServletRequest.getParameter("professionType") + "%'"; 
    if (httpServletRequest.getParameter("professionName") != null && 
      !"".equals(httpServletRequest.getParameter("professionName")) && 
      !"null".equals(httpServletRequest.getParameter("professionName")))
      searchSQL = String.valueOf(searchSQL) + " and po.professionName like '%" + 
        httpServletRequest.getParameter("professionName") + "%'"; 
    if (httpServletRequest.getParameter("zt") != null && 
      !"".equals(httpServletRequest.getParameter("zt")) && 
      !"null".equals(httpServletRequest.getParameter("zt")))
      searchSQL = String.valueOf(searchSQL) + " and po.zt like '%" + 
        httpServletRequest.getParameter("zt") + "%'"; 
    String databaseType = 
      SystemCommon.getDatabaseType();
    String whereSql = " where  1=1" + searchSQL;
    whereSql = String.valueOf(whereSql) + " order by po.id desc";
    Page page = new Page(
        "po.id,po.name,po.sex,po.age,po.tel,po.applyPosition,po.topEducation,po.topDegree,po.professionType,po.professionName,po.zt", 
        "com.js.oa.hr.resume.po.ResumePO po", 
        whereSql);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("list", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", 
        "action,id,name,tel,applyPosition,topEducation,professionType,professionName,zt");
  }
}
