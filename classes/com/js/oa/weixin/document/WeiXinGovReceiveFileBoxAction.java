package com.js.oa.weixin.document;

import com.js.doc.doc.po.SendDocumentNumPO;
import com.js.doc.doc.service.SendFileBD;
import com.js.doc.doc.service.SenddocumentBD;
import com.js.oa.weixin.document.service.WeiXinReceiveFileBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SystemCommon;
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

public class WeiXinGovReceiveFileBoxAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) {
    // Byte code:
    //   0: aload_3
    //   1: ldc 'pager.offset'
    //   3: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   8: invokestatic isNumber : (Ljava/lang/String;)Z
    //   11: ifeq -> 56
    //   14: aload_3
    //   15: ldc 'viewType'
    //   17: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   22: invokestatic checkParameter : (Ljava/lang/String;)Z
    //   25: ifeq -> 56
    //   28: aload_3
    //   29: ldc 'isEdit'
    //   31: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   36: invokestatic checkParameter : (Ljava/lang/String;)Z
    //   39: ifeq -> 56
    //   42: aload_3
    //   43: ldc 'canEdit'
    //   45: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   50: invokestatic checkParameter : (Ljava/lang/String;)Z
    //   53: ifne -> 68
    //   56: new org/apache/struts/action/ActionForward
    //   59: dup
    //   60: ldc '/public/jsp/inputerror.jsp'
    //   62: invokespecial <init> : (Ljava/lang/String;)V
    //   65: areturn
    //   66: astore #5
    //   68: aload_3
    //   69: ldc 'action'
    //   71: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   76: astore #5
    //   78: aload_2
    //   79: checkcast com/js/doc/doc/action/GovSendFileActionForm
    //   82: astore #6
    //   84: ldc 'list'
    //   86: aload #5
    //   88: invokevirtual equals : (Ljava/lang/Object;)Z
    //   91: ifeq -> 146
    //   94: aload_3
    //   95: ldc 'tag'
    //   97: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   102: ifnull -> 134
    //   105: ldc 'notRead'
    //   107: aload_3
    //   108: ldc 'tag'
    //   110: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   115: invokevirtual toString : ()Ljava/lang/String;
    //   118: invokevirtual equals : (Ljava/lang/Object;)Z
    //   121: ifeq -> 134
    //   124: aload_3
    //   125: ldc 'tag'
    //   127: ldc 'notRead'
    //   129: invokeinterface setAttribute : (Ljava/lang/String;Ljava/lang/Object;)V
    //   134: aload_0
    //   135: aload_3
    //   136: invokespecial list : (Ljavax/servlet/http/HttpServletRequest;)V
    //   139: aload_1
    //   140: ldc 'list'
    //   142: invokevirtual findForward : (Ljava/lang/String;)Lorg/apache/struts/action/ActionForward;
    //   145: areturn
    //   146: ldc 'load'
    //   148: aload #5
    //   150: invokevirtual equals : (Ljava/lang/Object;)Z
    //   153: ifeq -> 170
    //   156: aload_0
    //   157: aload_3
    //   158: aload #4
    //   160: invokespecial load : (Ljavax/servlet/http/HttpServletRequest;Ljavax/servlet/http/HttpServletResponse;)V
    //   163: aload_1
    //   164: ldc 'load'
    //   166: invokevirtual findForward : (Ljava/lang/String;)Lorg/apache/struts/action/ActionForward;
    //   169: areturn
    //   170: aload_1
    //   171: ldc 'list'
    //   173: invokevirtual findForward : (Ljava/lang/String;)Lorg/apache/struts/action/ActionForward;
    //   176: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #53	-> 0
    //   #54	-> 14
    //   #55	-> 15
    //   #54	-> 22
    //   #56	-> 28
    //   #57	-> 29
    //   #56	-> 36
    //   #58	-> 42
    //   #59	-> 43
    //   #58	-> 50
    //   #61	-> 56
    //   #62	-> 66
    //   #67	-> 68
    //   #68	-> 78
    //   #70	-> 84
    //   #73	-> 94
    //   #74	-> 105
    //   #75	-> 124
    //   #78	-> 134
    //   #79	-> 139
    //   #82	-> 146
    //   #83	-> 156
    //   #84	-> 163
    //   #87	-> 170
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	177	0	this	Lcom/js/oa/weixin/document/WeiXinGovReceiveFileBoxAction;
    //   0	177	1	actionMapping	Lorg/apache/struts/action/ActionMapping;
    //   0	177	2	actionForm	Lorg/apache/struts/action/ActionForm;
    //   0	177	3	request	Ljavax/servlet/http/HttpServletRequest;
    //   0	177	4	httpServletResponse	Ljavax/servlet/http/HttpServletResponse;
    //   78	99	5	action	Ljava/lang/String;
    //   84	93	6	govSendFileActionForm	Lcom/js/doc/doc/action/GovSendFileActionForm;
    // Exception table:
    //   from	to	target	type
    //   56	65	66	java/lang/Exception
  }
  
  private void list(HttpServletRequest request) {
    HttpSession httpSesison = request.getSession(true);
    ManagerService mbd = new ManagerService();
    Object object1 = request.getSession(true).getAttribute("userId");
    Object object2 = request.getSession(true).getAttribute("orgId");
    Object object3 = 
      request.getSession(true).getAttribute("orgIdString");
    String domainId = (httpSesison.getAttribute("domainId") == null) ? "0" : 
      httpSesison.getAttribute("domainId").toString();
    int beginIndex = Integer.parseInt((request.getParameter("beginIndex") == null) ? "0" : request.getParameter("beginIndex"));
    int pageSize = WapUtil.LIMITED;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    StringBuffer sb = new StringBuffer("1=1 ");
    String keyword = request.getParameter("keyword");
    if (keyword != null && !"".equals(keyword)) {
      try {
        keyword = URLDecoder.decode(keyword, "utf-8");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      } 
      sb.append(" and po.documentSendFileTitle like '%").append(keyword)
        .append("%'");
    } 
    String databaseType = SystemCommon.getDatabaseType();
    sb.append(" and ( sendFileUser.outSeeType is null or sendFileUser.outSeeType <> '1') ");
    String findScope = mbd.getRightFinalWhere((String)object1, (String)object2, (String)object3, 
        "锟斤拷锟侥癸拷锟斤拷_锟斤拷锟斤拷员", "锟介看", "sendFileUser.orgId", "sendFileUser.empId");
    findScope = String.valueOf(findScope) + " and sendFileUser.orgId is not null ";
    if (request.getParameter("tag") != null && 
      "notRead".equals(request.getParameter("tag").toString())) {
      String findSql = "sendFileUser.empId=" + object1;
      findSql = String.valueOf(findSql) + " or ( " + 
        findScope + 
        " and (select count(userpo2.id) from com.js.doc.doc.po.GovSendFileUserPO userpo2 where userpo2.empId= " + 
        object1 + " and userpo2.outSeeType='1')=0) ";
      sb.append(" and (").append(findSql).append(")");
      sb.append(" and ( sendFileUser.isReaded is null or sendFileUser.isReaded <> 1 )");
    } else {
      String findSql = "sendFileUser.empId=" + object1;
      findSql = String.valueOf(findSql) + " or ( " + findScope + " )";
      sb.append(" and (").append(findSql).append(")");
    } 
    if (request.getParameter("redHeadId") != null && 
      
      !request.getParameter("redHeadId").toUpperCase().trim().equals("NULL"))
      sb.append(" and ( po.documentSendFileHead='" + 
          request.getParameter("redHeadId") + "') "); 
    if (request.getParameter("numId") != null && 
      !request.getParameter("numId").toString().equals("")) {
      sb.append(" and ( po.sendFilePoNumId= " + 
          request.getParameter("numId") + " )");
    } else if (request.getParameter("numType") != null && 
      !request.getParameter("numType").toString().equals("")) {
      List<SendDocumentNumPO> numList = (new SenddocumentBD())
        .getSendNumByNumClass(request.getParameter("numType"));
      String sqlStr = "";
      if (numList != null && numList.size() > 0)
        for (int i = 0; i < numList.size(); i++) {
          SendDocumentNumPO po = numList
            .get(i);
          sqlStr = String.valueOf(sqlStr) + po.getId() + ",";
        }  
      if (sqlStr != null && sqlStr.length() > 1) {
        sqlStr = sqlStr.substring(0, sqlStr.length() - 1);
        sb.append(" and ( po.sendFilePoNumId in (" + sqlStr + 
            " ) )");
      } 
    } 
    try {
      String hql = 
        "select distinct po.id,po.documentSendFileByteNumber,po.documentSendFileTitle,po.documentSendFileWriteOrg,po.createdTime,po.createdEmp,po.createdOrg,po.sendFileLink,po.sendFileOverSee,sendFileUser.id,po.goldGridId,sendFileUser.isReaded ,sendFileUser.outSeeType,sendFileUser.orgId,po.documentWordType,po.tableId,po.accessorySaveName,po.contentAccSaveName from com.js.doc.doc.po.GovDocumentSendFilePO po join po.sendFileUser sendFileUser where " + 


        
        sb + " and po.sendFileOverSee<>2" + 
        " and po.domainId=" + domainId + 
        " order by po.id desc ";
      Map map = (new WeiXinReceiveFileBD()).getReceiveFilePaperList(hql, beginIndex, pageSize);
      List list = (List)map.get("list");
      request.setAttribute("myList", list);
      request.setAttribute("size", map.get("size"));
      request.setAttribute("keyword", keyword);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private void load(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    String id = httpServletRequest.getParameter("id");
    httpServletRequest.setAttribute("id", id);
    Map<String, String> map = (new WeiXinReceiveFileBD()).getDocinfo(id);
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    SendFileBD bd = new SendFileBD();
    bd.setSendFileBrower(id, 
        httpSession.getAttribute("userId").toString(), 
        httpSession.getAttribute("userName").toString(), 
        httpSession.getAttribute("orgName").toString(), 
        domainId);
    httpServletRequest.setAttribute("map", map);
    httpServletRequest.setAttribute("from", httpServletRequest.getParameter("from"));
  }
}
