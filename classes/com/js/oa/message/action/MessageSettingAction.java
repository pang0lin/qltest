package com.js.oa.message.action;

import com.js.oa.message.po.MsLimitPO;
import com.js.oa.message.po.MsModelPO;
import com.js.oa.message.service.messageSettingBD;
import com.js.util.page.Page;
import com.js.util.util.FillBean;
import com.js.util.util.StringSplit;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MessageSettingAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpservletResponse) {
    String action = httpServletRequest.getParameter("action");
    String showType = httpServletRequest.getParameter("showType");
    if (showType == null)
      showType = "messageLimit"; 
    httpServletRequest.setAttribute("showType", showType);
    MessageSettingActionForm messageSettingActionForm = (MessageSettingActionForm)actionForm;
    messageSettingBD mSettingBD = new messageSettingBD();
    String saveType = httpServletRequest.getParameter("saveType");
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    if ("AddModelJsp".equals(action))
      return actionMapping.findForward("AddModelJsp"); 
    if ("AddModel".equals(action)) {
      boolean result = false;
      MsModelPO msModelPO = (MsModelPO)FillBean.transformOneToOne(
          messageSettingActionForm, MsModelPO.class);
      String whetherExistModelName = ModelVanidateName(msModelPO
          .getModelSend());
      if (!whetherExistModelName.equals("")) {
        session.setAttribute("saveType", "existName");
        session.setAttribute("sampleName", whetherExistModelName);
        return actionMapping.findForward("AddModelJsp");
      } 
      result = mSettingBD.addModelSelf(msModelPO);
      if (!result) {
        session.setAttribute("saveType", "ModelFailure");
        return actionMapping.findForward("AddModelJsp");
      } 
      session.setAttribute("saveType", "ModelSucceed");
      return actionMapping.findForward("AddModelJsp");
    } 
    if ("AddLimitJsp".equals(action))
      return actionMapping.findForward("AddLimitJsp"); 
    if ("AddLimit".equals(action)) {
      boolean result = false;
      MsLimitPO msLimitPO = (MsLimitPO)FillBean.transformOneToOne(
          messageSettingActionForm, MsLimitPO.class);
      msLimitPO.setDomainId(domainId);
      String whetherExistName = LimitVanidateName(msLimitPO
          .getSendLimitId(), "", domainId);
      if (whetherExistName.equals("")) {
        String userOrgGroup = messageSettingActionForm.getSendLimitId();
        msLimitPO.setSendLimitId(StringSplit.splitWith(userOrgGroup, "$", "*@"));
        msLimitPO.setSendLimitOrg(StringSplit.splitWith(userOrgGroup, "*", "@$"));
        msLimitPO.setSendLimitGroup(StringSplit.splitWith(userOrgGroup, "@", "$*"));
        result = mSettingBD.addLimitMount(msLimitPO).booleanValue();
      } else {
        session.setAttribute("saveType", "existName");
        session.setAttribute("sampleName", whetherExistName);
        messageSettingActionForm.reset(actionMapping, httpServletRequest);
        return actionMapping.findForward("AddLimitJsp");
      } 
      if (!result) {
        session.setAttribute("saveType", "LimitFailure");
        messageSettingActionForm.reset(actionMapping, httpServletRequest);
        return actionMapping.findForward("AddLimitJsp");
      } 
      if (httpServletRequest.getParameter("successContinue").equals(
          "successContinue")) {
        session.setAttribute("saveType", "successContinue");
      } else {
        session.setAttribute("saveType", "LimitSucceed");
      } 
      messageSettingActionForm.reset(actionMapping, httpServletRequest);
      return actionMapping.findForward("AddLimitJsp");
    } 
    if ("ModiLimitJsp".equals(action)) {
      int offsetCopy = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset"));
      httpServletRequest.setAttribute("newoffset", (new StringBuilder(String.valueOf(offsetCopy))).toString());
      Long LimitId = Long.valueOf(httpServletRequest.getParameter(
            "LimitId"));
      String LimitSendId = httpServletRequest.getParameter("LimitSendId");
      String LimitSendMan = httpServletRequest.getParameter(
          "LimitSendMan");
      Long LimitMount = Long.valueOf(httpServletRequest.getParameter(
            "LimitMount"));
      Long monthCount = Long.valueOf(httpServletRequest.getParameter(
            "monthCount"));
      Long dayCount = Long.valueOf(httpServletRequest.getParameter(
            "dayCount"));
      messageSettingActionForm.setLimitId(LimitId);
      messageSettingActionForm.setLimitCount(LimitMount);
      messageSettingActionForm.setSendLimitId(LimitSendId);
      messageSettingActionForm.setSendLimitMan(LimitSendMan);
      messageSettingActionForm.setMonthCount(monthCount);
      messageSettingActionForm.setDayCount(dayCount);
      httpServletRequest.setAttribute("LimitId", LimitId.toString());
      return actionMapping.findForward("ModiLimitJsp");
    } 
    if ("ModiLimit".equals(action)) {
      boolean result = false;
      MsLimitPO msLimitPO = (MsLimitPO)FillBean.transformOneToOne(
          messageSettingActionForm, MsLimitPO.class);
      Long LimitId = new Long(httpServletRequest.getParameter("LimitId"));
      msLimitPO.setLimitId(LimitId);
      String whetherExistName = LimitVanidateName(msLimitPO
          .getSendLimitId(), LimitId.toString(), domainId);
      if (whetherExistName.equals("")) {
        result = mSettingBD.changeLimitMount(msLimitPO);
      } else {
        httpServletRequest.setAttribute("saveType", "existName");
        httpServletRequest.setAttribute("sampleName", whetherExistName);
        return actionMapping.findForward("ModiLimitJsp");
      } 
      if (!result) {
        httpServletRequest.setAttribute("saveType", "LimitFailure");
        return actionMapping.findForward("ModiLimitJsp");
      } 
      httpServletRequest.setAttribute("saveType", "LimitSucceed");
      return actionMapping.findForward("ModiLimitJsp");
    } 
    if ("DelLimit".equals(action)) {
      boolean result;
      String DelLimitId = httpServletRequest.getParameter("DelLimitId");
      int offsetCopy = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offsetCopy = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
      if (DelLimitId == null) {
        result = true;
      } else {
        result = mSettingBD.deletLimitMounts(DelLimitId);
      } 
      if (!result)
        return actionMapping.findForward("messageFace"); 
      messageModelMountList(httpServletRequest);
      String[] OutMoIdName = showAllManOut(domainId);
      messageSettingActionForm.setSendOutId(OutMoIdName[0]);
      messageSettingActionForm.setSendOutMan(OutMoIdName[1]);
      if (offsetCopy != 0) {
        messageLimitMountList(httpServletRequest, offsetCopy);
      } else {
        messageLimitMountList(httpServletRequest);
      } 
      return actionMapping.findForward("messageFace");
    } 
    if ("DelsLimit".equals(action)) {
      boolean result;
      String DelsLimit = httpServletRequest.getParameter("ids");
      int offsetCopy = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offsetCopy = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
      if (DelsLimit == null) {
        result = true;
      } else {
        result = mSettingBD.deletLimitMounts(DelsLimit);
      } 
      if (!result)
        return actionMapping.findForward("messageFace"); 
      messageModelMountList(httpServletRequest);
      String[] OutMoIdName = showAllManOut(domainId);
      messageSettingActionForm.setSendOutId(OutMoIdName[0]);
      messageSettingActionForm.setSendOutMan(OutMoIdName[1]);
      if (offsetCopy != 0) {
        messageLimitMountList(httpServletRequest, offsetCopy);
      } else {
        messageLimitMountList(httpServletRequest);
      } 
      return actionMapping.findForward("messageFace");
    } 
    if ("lastChangeAndExit".equals(action)) {
      if (httpServletRequest.getParameter("ModelSend") != null) {
        boolean result = true;
        if (httpServletRequest.getParameter("indexAndId") != null && 
          !httpServletRequest.getParameter("indexAndId").equals("")) {
          MsModelPO[] toBeChange = programToPO(httpServletRequest
              .getParameter("indexAndId"), 
              httpServletRequest
              .getParameter("ModelSend"), 
              httpServletRequest.getParameter(
                "redayjs1"), 
              httpServletRequest
              .getParameter("redayjs2"), 
              httpServletRequest.getParameter(
                "redayjs3"), 
              httpServletRequest
              .getParameter("redayjs4"), 
              httpServletRequest
              .getParameter(
                "redayjs5"), 
              httpServletRequest.getParameter("redayjs6"), 
              httpServletRequest.getParameter(
                "redayjs7"), 
              httpServletRequest.getParameter("redayjs8"), 
              httpServletRequest.getParameter(
                "redayjs9"), 
              httpServletRequest.getParameter("redayjs10"), 
              httpServletRequest.getParameter("redayjs11"));
          result = mSettingBD.changeModelExist(toBeChange, domainId);
        } else {
          result = mSettingBD.allNotExist();
        } 
        String sendOutId = messageSettingActionForm.getSendOutId();
        if (sendOutId != null && !sendOutId.equals(""))
          sendOutId = messageSettingActionForm.getSendOutId()
            .substring(1, 
              messageSettingActionForm.getSendOutId()
              .length() - 1); 
        String sendOutMan = messageSettingActionForm.getSendOutMan();
        Map<Object, Object> mapIdName = new HashMap<Object, Object>();
        boolean tageIdNamw = StringToMap(sendOutId, sendOutMan, 
            mapIdName);
        if (!tageIdNamw)
          return actionMapping.findForward("failure"); 
        boolean temp = mSettingBD.changeAllMembers(mapIdName, 
            sendOutId, 
            domainId);
        if (temp && result) {
          messageModelMountList(httpServletRequest);
          String[] OutMoIdName = showAllManOut(domainId);
          messageSettingActionForm.setSendOutId(OutMoIdName[0]);
          messageSettingActionForm.setSendOutMan(OutMoIdName[1]);
          messageLimitMountList(httpServletRequest);
          return actionMapping.findForward("messageFace");
        } 
        return actionMapping.findForward("failure");
      } 
      action = "settingMessage";
    } 
    if ("settingMessage".equals(action)) {
      messageModelMountList(httpServletRequest);
      messageLimitMountList(httpServletRequest);
      return actionMapping.findForward("settingMessage");
    } 
    throw new UnsupportedOperationException(
        "Method $execute() not yet implemented.");
  }
  
  private String ModelVanidateName(String vanidate) {
    String result = "";
    Page testContain = new Page("jhl.modelOutId,jhl.modelId,jhl.modelSend,jhl.content,jhl.reday1,jhl.reday2,jhl.reday3,jhl.reday4,jhl.reday5,jhl.reday6,jhl.reday7,jhl.reday8,jhl.reday9,jhl.reday10,jhl.reday11", 
        "com.js.oa.message.po.MsModelPO jhl");
    List listVanidate = testContain.getResultList();
    for (Iterator<Object[]> it = listVanidate.iterator(); it.hasNext(); ) {
      Object[] temp = it.next();
      if (temp[2].toString().equals(vanidate))
        result = vanidate; 
    } 
    return result;
  }
  
  private String LimitVanidateCount(String vanidate, String where, String domainId) {
    String result = "";
    Page testContain = null;
    if (where.equals("")) {
      testContain = new Page(
          "jhl.limitId,jhl.limitCount,jhl.sendLimitId,jhl.sendLimitMan", 
          "com.js.oa.message.po.MsLimitPO jhl where jhl.domainId=" + 
          domainId);
    } else {
      testContain = new Page(
          "jhl.limitId,jhl.limitCount,jhl.sendLimitId,jhl.sendLimitMan", 
          "com.js.oa.message.po.MsLimitPO jhl", 
          "where jhl.limitId != " + where + " and jhl.domainId=" + 
          domainId);
    } 
    List listVanidate = testContain.getResultList();
    for (Iterator<Object[]> it = listVanidate.iterator(); it.hasNext(); ) {
      Object[] temp = it.next();
      if (temp[1].toString().length() != 0 && 
        temp[1].toString().equals(vanidate))
        result = vanidate; 
    } 
    return result;
  }
  
  private String LimitVanidateName(String vanidate, String where, String domainId) {
    if (vanidate == null || vanidate.equals(""))
      return ""; 
    String[] getId = vanidate.replace('$', ',').substring(1, 
        vanidate.toString().length() - 1).split(",,");
    String lastResult = "";
    Page testContain = null;
    if (where.equals("")) {
      testContain = new Page(
          "jhl.limitId,jhl.limitCount,jhl.sendLimitId,jhl.sendLimitMan", 
          "com.js.oa.message.po.MsLimitPO jhl where jhl.domainId=" + 
          domainId);
    } else {
      testContain = new Page(
          "jhl.limitId,jhl.limitCount,jhl.sendLimitId,jhl.sendLimitMan", 
          "com.js.oa.message.po.MsLimitPO jhl", 
          " where jhl.limitId != " + where + " and jhl.domainId=" + 
          domainId);
    } 
    List listVanidate = testContain.getResultList();
    for (Iterator<Object[]> it = listVanidate.iterator(); it.hasNext(); ) {
      Object[] temp = it.next();
      if (temp[2] != null && temp[2].toString().length() != 0) {
        String[] vanidInTableId = ((String)temp[2]).replace('$', ',')
          .substring(1, 
            temp[2].toString().length() - 1).split(",,");
        String[] vanidInTableName = ((String)temp[3]).split(",");
        for (int i = 0; i < vanidInTableId.length; i++) {
          for (int j = 0; j < getId.length; j++) {
            if (getId[j].equals(vanidInTableId[i]))
              lastResult = String.valueOf(lastResult) + vanidInTableName[i] + ","; 
          } 
        } 
      } 
    } 
    return lastResult;
  }
  
  public void messageLimitMountList(HttpServletRequest httpServletRequest, int offsetCopy) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String where = "where jhl.domainId=" + domainId + 
      " order by jhl.limitId desc";
    int pageSize = 15;
    int offset = offsetCopy;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "jhl.limitId,jhl.limitCount,jhl.sendLimitId,jhl.sendLimitMan,jhl.monthCount,jhl.dayCount", 
        "com.js.oa.message.po.MsLimitPO jhl", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    if (list != null && 
      list.size() == 0 && offset >= pageSize) {
      offset -= pageSize;
      currentPage = offset / pageSize + 1;
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      httpServletRequest.setAttribute("new.offset", (new StringBuilder(String.valueOf(offset))).toString());
      httpServletRequest.setAttribute("pager.realCurrent", (new StringBuilder(String.valueOf(currentPage))).toString());
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    httpServletRequest.setAttribute("messageLimitMountList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,taxisName");
  }
  
  public void messageLimitMountList(HttpServletRequest httpServletRequest) {
    messageLimitMountList(httpServletRequest, 0);
  }
  
  public boolean StringToMap(String id, String name, Map<String, String> map) {
    String[] tempId = id.replace('$', ',').split(",,");
    String[] tempMan = name.split(",");
    if (tempId.length != tempMan.length)
      return false; 
    for (int i = 0; i < tempId.length; i++)
      map.put(tempId[i], tempMan[i]); 
    return true;
  }
  
  public String[] showAllManOut(String domainId) {
    String[] tempIdName = new String[2];
    tempIdName[0] = new String();
    tempIdName[1] = new String();
    String where = "where jhl.domainId=" + domainId + 
      " order by jhl.outMoId desc";
    Page page = new Page(
        "jhl.outMoId,jhl.sendOutId,jhl.sendOutMan", 
        "com.js.oa.message.po.MsOutMoPO jhl", 
        where);
    List list = page.getResultList();
    for (Iterator<Object[]> it = list.iterator(); it.hasNext(); ) {
      Object[] temp = it.next();
      tempIdName[1] = String.valueOf(tempIdName[1]) + temp[2] + ",";
      tempIdName[0] = String.valueOf(tempIdName[0]) + "$" + temp[1] + "$";
    } 
    return tempIdName;
  }
  
  public void messageModelMountList(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String where = "where jhl.domainId=" + domainId + 
      " order by jhl.modelOutId desc";
    int pageSize = 100;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset.model") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset.model")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "jhl.modelOutId,jhl.modelId,jhl.modelSend,jhl.content,jhl.reday1,jhl.reday2,jhl.reday3,jhl.reday4,jhl.reday5,jhl.reday6,jhl.reday7,jhl.reday8,jhl.reday9,jhl.reday10,jhl.reday11", 
        "com.js.oa.message.po.MsModelPO jhl", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCountModel = String.valueOf(page.getRecordCount());
    String pageCountModel = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("messageModelMountList", list);
    httpServletRequest.setAttribute("recordCountModel", recordCountModel);
    httpServletRequest.setAttribute("maxPageItemsModel", 
        String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
  
  private MsModelPO[] programToPO(String indexAndId, String ModelSend, String first, String second, String third, String fourth, String fifth, String sixth, String seventh, String eighth, String ninty, String tenth, String eleventh) {
    String[] id = indexAndId.split(",");
    String[] index = id[id.length - 1].split("@@");
    String[] ModelSendR = ModelSend.split("@&@&&@&&&#");
    String[] firstR = first.split("@&@&&@&&&#");
    String[] secondR = second.split("@&@&&@&&&#");
    String[] thirdR = third.split("@&@&&@&&&#");
    String[] fourthR = fourth.split("@&@&&@&&&#");
    String[] fifthR = fifth.split("@&@&&@&&&#");
    String[] sixthR = sixth.split("@&@&&@&&&#");
    String[] seventhR = seventh.split("@&@&&@&&&#");
    String[] eighthR = eighth.split("@&@&&@&&&#");
    String[] nintyR = ninty.split("@&@&&@&&&#");
    String[] tenthR = tenth.split("@&@&&@&&&#");
    String[] eleventhR = eleventh.split("@&@&&@&&&#");
    MsModelPO[] temp = new MsModelPO[index.length - 1];
    int iFortemp = 0;
    for (int i = 0; i < firstR.length; i++) {
      for (int j = 0; j < index.length - 1; j++) {
        if (index[j + 1].equals(Integer.toString(i))) {
          temp[iFortemp] = new MsModelPO();
          temp[iFortemp].setModelOutId(Long.valueOf(id[j]));
          temp[iFortemp].setModelSend(ModelSendR[i]);
          temp[iFortemp].setReday1(firstR[i]);
          temp[iFortemp].setReday2(secondR[i]);
          temp[iFortemp].setReday3(thirdR[i]);
          temp[iFortemp].setReday4(fourthR[i]);
          temp[iFortemp].setReday5(fifthR[i]);
          temp[iFortemp].setReday6(sixthR[i]);
          temp[iFortemp].setReday7(seventhR[i]);
          temp[iFortemp].setReday8(eighthR[i]);
          temp[iFortemp].setReday9(nintyR[i]);
          temp[iFortemp].setReday10(tenthR[i]);
          temp[iFortemp].setReday11(eleventhR[i]);
          if (secondR[i].equals("－空－"))
            secondR[i] = ""; 
          if (fourthR[i].equals("－空－"))
            fourthR[i] = ""; 
          if (sixthR[i].equals("－空－"))
            sixthR[i] = ""; 
          if (eighthR[i].equals("－空－"))
            eighthR[i] = ""; 
          if (tenthR[i].equals("－空－"))
            tenthR[i] = ""; 
          temp[iFortemp].setContent(String.valueOf(firstR[i]) + secondR[i] + thirdR[i] + 
              fourthR[i] + 
              fifthR[i] + sixthR[i] + 
              seventhR[i] + eighthR[i] + 
              nintyR[i] + tenthR[i] + 
              eleventhR[i]);
          iFortemp++;
        } 
      } 
    } 
    return temp;
  }
}
