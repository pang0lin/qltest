package com.js.oa.hr.finance.service;

import com.js.oa.hr.finance.action.FPageActionForm;
import com.js.oa.hr.finance.bean.FFieldEJBBean;
import com.js.oa.hr.finance.bean.FPageEJBBean;
import com.js.oa.hr.finance.po.FPage;
import com.js.oa.hr.finance.util.PubUtil;
import com.js.util.page.Page;
import com.js.util.util.DataSourceBase;
import com.js.util.util.FillBean;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class FPageService {
  public boolean addPage(FPageActionForm form, HttpServletRequest request) {
    boolean re = true;
    try {
      FPageEJBBean bean = new FPageEJBBean();
      HttpSession session = request.getSession(true);
      String userId = session.getAttribute("userId").toString();
      String userName = session.getAttribute("userName").toString();
      String orgId = session.getAttribute("orgId").toString();
      String orgIdString = session.getAttribute("orgIdString").toString();
      FPage po = (FPage)FillBean.transformOTO(form, FPage.class);
      po.setCreateDate(new Date());
      po.setCreatedEmp(Long.valueOf(userId));
      po.setCreatedOrg(Long.valueOf(orgId));
      Long id = bean.addPage(po);
      if (id == null)
        re = false; 
      if (re) {
        String str = "update f_page set page_is_use=0 where page_id!=" + id + " and table_Id=" + po.getTableId();
        re = bean.updateByYourYuanShengSql(str);
        str = "update f_table set table_page=" + id + " where table_Id=" + po.getTableId();
        re = bean.updateByYourYuanShengSql(str);
      } 
      PubUtil pubUtil = new PubUtil();
      String jspContent = pubUtil.getPageJsp(form.getPageContent());
      String filePath = request.getRealPath("");
      String fileName = "/finance/jsp/page_" + po.getPageId() + "_" + pubUtil.getRandomNumber(10) + ".jsp";
      String dirPath = String.valueOf(filePath) + fileName;
      createFile(jspContent, dirPath);
      String sql = "update f_page set page_file='" + fileName + "' where page_id=" + id;
      re = bean.updateByYourYuanShengSql(sql);
    } catch (Exception e) {
      re = false;
      e.printStackTrace();
    } 
    return re;
  }
  
  public boolean modiPage(FPageActionForm form, HttpServletRequest request) {
    boolean re = true;
    try {
      FPageEJBBean bean = new FPageEJBBean();
      HttpSession session = request.getSession(true);
      String userId = session.getAttribute("userId").toString();
      String userName = session.getAttribute("userName").toString();
      String orgId = session.getAttribute("orgId").toString();
      String orgIdString = session.getAttribute("orgIdString").toString();
      FPage po = (FPage)FillBean.transformOTO(form, FPage.class);
      po.setCreateDate(new Date());
      po.setCreatedEmp(Long.valueOf(userId));
      po.setCreatedOrg(Long.valueOf(orgId));
      FPage oldPage = loadFPage(form.getPageId());
      String ifSaveAsPage = request.getParameter("ifSaveAsPage");
      String pageIsUse = request.getParameter("pageIsUse");
      if (ifSaveAsPage != null && "0".equals(ifSaveAsPage))
        po.setPageContent(oldPage.getPageContent()); 
      re = bean.modiPage(po);
      if (re && "1".equals(pageIsUse)) {
        String sql = "update f_page set page_is_use=0 where page_id!=" + po.getPageId() + " and table_Id=" + po.getTableId();
        re = bean.updateByYourYuanShengSql(sql);
        sql = "update f_table set table_page=" + po.getPageId() + " where table_Id=" + po.getTableId();
        re = bean.updateByYourYuanShengSql(sql);
      } 
      if (ifSaveAsPage != null && "1".equals(ifSaveAsPage)) {
        PubUtil pubUtil = new PubUtil();
        String jspContent = pubUtil.getPageJsp(form.getPageContent());
        String filePath = request.getRealPath("");
        String fileName = po.getPageFile();
        String dirPath = String.valueOf(filePath) + fileName;
        createFile(jspContent, dirPath);
      } 
    } catch (Exception e) {
      re = false;
      e.printStackTrace();
    } 
    return re;
  }
  
  public FPage loadFPage(Long pageId) throws Exception {
    FPage fPage = null;
    FPageEJBBean bean = new FPageEJBBean();
    try {
      fPage = bean.loadFPage(pageId);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return fPage;
  }
  
  public void createFile(String fileContent, String fileName) {
    FileWriter fw = null;
    try {
      fw = new FileWriter(fileName);
      long begin3 = System.currentTimeMillis();
      fw.write(fileContent);
      fw.close();
      long end3 = System.currentTimeMillis();
      fw.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void getPageShowInfo(HttpServletRequest request) {
    Map<Object, Object> map = new HashMap<Object, Object>();
    String id = request.getParameter("id");
    String pageId = request.getParameter("pageId");
    String tableName = request.getParameter("tableName");
    FFieldEJBBean bean = new FFieldEJBBean();
    String sql = "select po.fieldId,po.fieldName,po.fieldDesname,po.fieldType from com.js.oa.hr.finance.po.FField po  where po.tableName='" + 
      tableName + "' and po.fieldIsSys=0  order by po.fieldId asc";
    try {
      List<Object[]> tempList = bean.getListByYourSQL(sql);
      if (tempList != null && tempList.size() > 0) {
        Page page_ol = null;
        StringBuffer sb = new StringBuffer("select ");
        for (int i = 0; i < tempList.size(); i++) {
          Object[] obj = tempList.get(i);
          if (i == 0) {
            sb.append(obj[1].toString());
          } else {
            sb.append(",").append(obj[1].toString());
          } 
        } 
        sb.append(" from " + tableName + " where 1=1 and id=" + id);
        String[][] datas = bean.getArr2ByYourSql(sb.toString(), tempList.size(), "");
        if (datas != null && datas.length == 1)
          for (int j = 0; j < (datas[0]).length; j++) {
            Object[] obj = tempList.get(j);
            request.setAttribute(obj[1].toString(), datas[0][j].toString());
            map.put(obj[1].toString(), datas[0][j].toString());
          }  
        sql = "select po.pageId,po.pageFile from com.js.oa.hr.finance.po.FPage po where po.pageId=" + pageId;
        tempList = bean.getListByYourSQL(sql);
        if (tempList != null && tempList.size() > 0) {
          Object[] obj = tempList.get(0);
          request.setAttribute("pageFile_pageFile", obj[1].toString());
        } 
      } 
      if (tableName != null && "f_payable".equals(tableName)) {
        sql = "select content from f_payable where id=" + id;
        String content = "";
        DataSourceBase base = new DataSourceBase();
        ResultSet rs = null;
        try {
          base.begin();
          rs = base.executeQuery(sql);
          if (rs.next())
            content = rs.getString(1); 
          base.end();
        } catch (Exception e) {
          base.end();
          e.printStackTrace();
        } 
        request.setAttribute("content", content);
        map.put("content", content);
      } 
      request.setAttribute("map", map);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
