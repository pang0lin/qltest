package com.js.doc.doc.action;

import com.js.doc.doc.po.BaseInfoPO;
import com.js.doc.doc.po.SendDocumentNumPO;
import com.js.doc.doc.po.SendDocumentSeqPO;
import com.js.doc.doc.po.SendDocumentUnitPO;
import com.js.doc.doc.po.SendDocumentWordPO;
import com.js.doc.doc.po.SenddocumentTopicalPO;
import com.js.doc.doc.service.SenddocumentBD;
import com.js.oa.jsflow.service.ProcessBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SenddocumentBaseAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    SenddocumentBaseActionForm senddocumentBaseActionForm = (SenddocumentBaseActionForm)actionForm;
    String action = httpServletRequest.getParameter("action");
    Object object1 = httpServletRequest.getSession(true).getAttribute("userId");
    Object object2 = httpServletRequest.getSession(true).getAttribute("orgId");
    Object object3 = (httpServletRequest.getSession(true).getAttribute("domainId") == null) ? "0" : httpServletRequest.getSession(true).getAttribute("domainId");
    String corpId = httpServletRequest.getSession(true).getAttribute("corpId").toString();
    String editId = httpServletRequest.getParameter("editId");
    String editType = httpServletRequest.getParameter("editType");
    SenddocumentBD senddocumentBD = new SenddocumentBD();
    if ("listBaseinfo".equals(action)) {
      displayBaseInfo(httpServletRequest, senddocumentBaseActionForm);
      return actionMapping.findForward("baseInfoList");
    } 
    if ("baseInfoSave".equals(action)) {
      saveBaseInf(httpServletRequest, senddocumentBaseActionForm);
      return actionMapping.findForward("baseInfoList");
    } 
    if ("wordlist".equals(action)) {
      list(httpServletRequest);
      return actionMapping.findForward("wordList");
    } 
    if ("senddocumentwordadd".equals(action)) {
      List list1 = getSendDocumentNumId();
      List list2 = getSendfileList(httpServletRequest);
      List list3 = getTemplateList((String)object3, "", "");
      List list4 = getSendDocumentSeqId();
      httpServletRequest.setAttribute("numList", list1);
      httpServletRequest.setAttribute("sendfileList", list2);
      httpServletRequest.setAttribute("templateList", list3);
      httpServletRequest.setAttribute("seqList", list4);
      return actionMapping.findForward("wordadd");
    } 
    if ("senddocumentwordclose".equals(action) || "senddocumentwordcontinue".equals(action)) {
      savedocumentword(httpServletRequest, senddocumentBaseActionForm);
      return actionMapping.findForward("wordadd");
    } 
    if ("senddocumentwordload".equals(action)) {
      String tempId = "-1";
      if (editId != null && !editId.equals("")) {
        SendDocumentWordPO po = senddocumentBD.loadSendDocumentWordPO(editId);
        if (po != null) {
          senddocumentBaseActionForm.setProcessId(po.getProcessId());
          senddocumentBaseActionForm.setSendDocumentNumId(po.getSendDocumentNumId());
          senddocumentBaseActionForm.setSendDocumentSeqId(po.getSendDocumentSeqId());
          senddocumentBaseActionForm.setTemplateId(po.getTemplateId());
          senddocumentBaseActionForm.setUserRange(po.getUserRange());
          senddocumentBaseActionForm.setUserRangeId(po.getUserRangeId());
          senddocumentBaseActionForm.setWordName(po.getWordName());
          senddocumentBaseActionForm.setProcessName(po.getProcessName());
          senddocumentBaseActionForm.setTemplateName(po.getTemplateName());
          senddocumentBaseActionForm.setRedHeadName(po.getRedHeadName());
          senddocumentBaseActionForm.setRedHeadSaveName(po.getRedHeadSaveName());
          tempId = (po.getTemplateId() == null) ? "" : po.getTemplateId().toString();
          httpServletRequest.setAttribute("wordId", po.getId());
          if (po.getReceiveUser() != null)
            httpServletRequest.setAttribute("receiveUser", 
                po.getReceiveUser()); 
          if (po.getReceiveOrg() != null)
            httpServletRequest.setAttribute("receiveOrg", 
                po.getReceiveOrg()); 
          if (po.getReceiveGroup() != null)
            httpServletRequest.setAttribute("receiveGroup", 
                po.getReceiveGroup()); 
          if (po.getReceiveScopeId() != null)
            httpServletRequest.setAttribute("receiveScopeId", 
                po.getReceiveScopeId()); 
          if (po.getReceiveScopeName() != null)
            httpServletRequest.setAttribute("receiveScopeName", 
                po.getReceiveScopeName()); 
        } 
      } 
      List list1 = getSendDocumentNumId();
      List list2 = getSendfileList(httpServletRequest);
      List list3 = new ArrayList();
      List list4 = getSendDocumentSeqId();
      httpServletRequest.setAttribute("numList", list1);
      httpServletRequest.setAttribute("sendfileList", list2);
      httpServletRequest.setAttribute("seqList", list4);
      if (editType != null && editType.equals("1")) {
        list3 = getTemplateList((String)object3, "update", tempId);
        httpServletRequest.setAttribute("templateList", list3);
        return actionMapping.findForward("wordmodiLoad");
      } 
      list3 = getTemplateList((String)object3, "load", tempId);
      httpServletRequest.setAttribute("templateList", list3);
      return actionMapping.findForward("wordLoad");
    } 
    if ("wordUpdateClose".equals(action)) {
      String tmpId = "-1";
      String idd = httpServletRequest.getParameter("wordId");
      httpServletRequest.setAttribute("wordId", idd);
      SendDocumentWordPO po = new SendDocumentWordPO();
      if (httpServletRequest.getParameter("processId") != null && !httpServletRequest.getParameter("processId").toString().equals("")) {
        po.setProcessId(new Long(httpServletRequest.getParameter("processId")));
        po.setProcessName(httpServletRequest.getParameter("processName"));
      } else {
        po.setProcessId(new Long("-1"));
        po.setProcessName("");
      } 
      if (httpServletRequest.getParameter("sendDocumentNumId") != null && !httpServletRequest.getParameter("sendDocumentNumId").toString().equals("")) {
        po.setSendDocumentNumId(new Long(httpServletRequest.getParameter("sendDocumentNumId")));
      } else {
        po.setSendDocumentNumId(new Long(-1L));
      } 
      if (httpServletRequest.getParameter("sendDocumentSeqId") != null && !httpServletRequest.getParameter("sendDocumentSeqId").toString().equals("")) {
        po.setSendDocumentSeqId(new Long(httpServletRequest.getParameter("sendDocumentSeqId")));
      } else {
        po.setSendDocumentSeqId(new Long(-1L));
      } 
      if (httpServletRequest.getParameter("templateId") != null && !httpServletRequest.getParameter("templateId").toString().equals("")) {
        po.setTemplateId(httpServletRequest.getParameter("templateId"));
        po.setTemplateName(httpServletRequest.getParameter("templateName"));
        tmpId = (httpServletRequest.getParameter("templateId") == null) ? "" : httpServletRequest.getParameter("templateId").toString();
      } else {
        po.setTemplateName("");
      } 
      if (httpServletRequest.getParameter("receiveUser") != null && 
        !httpServletRequest.getParameter("receiveUser").toString().equals("")) {
        po.setReceiveUser(httpServletRequest.getParameter("receiveUser"));
      } else {
        po.setReceiveUser("");
      } 
      if (httpServletRequest.getParameter("receiveOrg") != null && 
        !httpServletRequest.getParameter("receiveOrg").toString().equals("")) {
        po.setReceiveOrg(httpServletRequest.getParameter("receiveOrg"));
      } else {
        po.setReceiveOrg("");
      } 
      if (httpServletRequest.getParameter("receiveGroup") != null && 
        !httpServletRequest.getParameter("receiveGroup").toString().equals("")) {
        po.setReceiveGroup(httpServletRequest.getParameter("receiveGroup"));
      } else {
        po.setReceiveGroup("");
      } 
      if (httpServletRequest.getParameter("receiveScopeName") != null && 
        !httpServletRequest.getParameter("receiveScopeName").toString().equals("")) {
        po.setReceiveScopeName(httpServletRequest.getParameter("receiveScopeName"));
      } else {
        po.setReceiveScopeName("");
      } 
      if (httpServletRequest.getParameter("receiveScopeId") != null && 
        !httpServletRequest.getParameter("receiveScopeId").toString().equals("")) {
        po.setReceiveScopeId(httpServletRequest.getParameter("receiveScopeId"));
      } else {
        po.setReceiveScopeId("");
      } 
      if (httpServletRequest.getParameter("redHeadName") != null && 
        !httpServletRequest.getParameter("redHeadName").toString().equals("")) {
        po.setRedHeadName(httpServletRequest.getParameter("redHeadName"));
        po.setRedHeadSaveName(
            httpServletRequest.getParameter("redHeadSaveName"));
      } else {
        po.setRedHeadName("");
        po.setRedHeadSaveName("");
      } 
      po.setUserRange(httpServletRequest.getParameter("userRange"));
      po.setUserRange(httpServletRequest.getParameter("userRangeId"));
      po.setWordName(httpServletRequest.getParameter("wordName"));
      String result = senddocumentBD.updateWordPO(idd, po);
      List list1 = getSendDocumentNumId();
      List list2 = getSendfileList(httpServletRequest);
      List list3 = getTemplateList((String)object3, "update", tmpId);
      List list4 = getSendDocumentSeqId();
      httpServletRequest.setAttribute("numList", list1);
      httpServletRequest.setAttribute("sendfileList", list2);
      httpServletRequest.setAttribute("templateList", list3);
      httpServletRequest.setAttribute("seqList", list4);
      if (result != null && result.equals("0")) {
        httpServletRequest.setAttribute("wordUSUS", "0");
      } else if (result != null && result.equals("-1")) {
        httpServletRequest.setAttribute("wordUSUS", "-1");
      } else {
        httpServletRequest.setAttribute("wordUSUS", "1");
      } 
      return actionMapping.findForward("wordmodiLoad");
    } 
    if ("senddocumentworddel".equals(action)) {
      String did = httpServletRequest.getParameter("ids");
      String result = senddocumentBD.deleteWordPO(did);
      list(httpServletRequest);
      return actionMapping.findForward("wordList");
    } 
    if ("senddocumentdelBatch".equals(action)) {
      String did = httpServletRequest.getParameter("ids");
      if (did != null && did.trim().length() > 1)
        did = did.substring(0, did.length() - 1); 
      String result = senddocumentBD.deleteWordPO(did);
      list(httpServletRequest);
      return actionMapping.findForward("wordList");
    } 
    if ("senddocumentnumList".equals(action)) {
      list2(httpServletRequest);
      return actionMapping.findForward("numList");
    } 
    if ("senddocumentnumadd".equals(action)) {
      List list = getAllNumType();
      httpServletRequest.setAttribute("typeList", list);
      return actionMapping.findForward("numadd");
    } 
    if ("senddocumentnumclose".equals(action) || "senddocumentnumcontinue".equals(action) || "senddocumentnumupdate".equals(action)) {
      Integer bitNum = Integer.valueOf(httpServletRequest.getParameter("bitNum").toString());
      String fileWordIds = httpServletRequest.getParameter("fileWordIds");
      Integer initValue = Integer.valueOf(httpServletRequest.getParameter("initValue").toString());
      String keyValue = httpServletRequest.getParameter("keyValue");
      String numFormat = httpServletRequest.getParameter("numFormat");
      Integer numIsYear = Integer.valueOf(httpServletRequest.getParameter("numIsYear").toString());
      Integer numIsWord = Integer.valueOf(httpServletRequest.getParameter("numIsWord").toString());
      String numMode = httpServletRequest.getParameter("numMode");
      String numName = httpServletRequest.getParameter("numName");
      String numNote = httpServletRequest.getParameter("numNote");
      String numType = httpServletRequest.getParameter("numType");
      int oldYear = Integer.parseInt(httpServletRequest.getParameter("oldYear"));
      SendDocumentNumPO po = new SendDocumentNumPO();
      po.setBitNum(bitNum);
      po.setInitValue(initValue);
      po.setKeyValue(keyValue);
      po.setNumFormat(numFormat);
      po.setNumType(numType);
      po.setNumIsYear(numIsYear);
      po.setNumMode(numMode);
      po.setNumName(numName);
      po.setNumNote(numNote);
      po.setOldYear(oldYear);
      po.setNumIsWord(numIsWord);
      po.setCreatedEmp(Long.valueOf((String)object1));
      po.setCreatedOrg(Long.valueOf((String)object2));
      po.setCorpId(Long.valueOf(corpId));
      List numList = getAllNumType();
      httpServletRequest.setAttribute("typeList", numList);
      httpServletRequest.setAttribute("oldYear", (new StringBuilder(String.valueOf(oldYear))).toString());
      if ("senddocumentnumclose".equals(action) || "senddocumentnumcontinue".equals(action)) {
        Integer integer = new Integer(initValue.intValue() - 1);
        po.setNumfig(integer);
        Long result = senddocumentBD.saveNunInfo(po);
        if (result != null && !result.toString().equals("-2"))
          if (result.toString().equals("-1")) {
            httpServletRequest.setAttribute("continue", "-1");
          } else if (fileWordIds != null && !fileWordIds.trim().equals("")) {
            String re = senddocumentBD.setWordPoNum(fileWordIds, 
                result.toString());
            if (re != null && re.equals("0"))
              if ("senddocumentnumclose".equals(action)) {
                httpServletRequest.setAttribute("continue", "0");
              } else {
                httpServletRequest.setAttribute("continue", "1");
              }  
          } else if ("senddocumentnumclose".equals(action)) {
            httpServletRequest.setAttribute("continue", "0");
          } else {
            httpServletRequest.setAttribute("continue", "1");
          }  
        return actionMapping.findForward("numadd");
      } 
      Integer nf = Integer.valueOf(httpServletRequest.getParameter(
            "numfig").toString());
      Integer nf1 = new Integer(initValue.intValue() - 1);
      if (nf1.compareTo(nf) > 0) {
        po.setNumfig(nf1);
      } else {
        po.setNumfig(nf);
      } 
      httpServletRequest.setAttribute("numfig", 
          httpServletRequest
          .getParameter("numfig").toString());
      String numId = httpServletRequest.getParameter("sendDocumentId");
      String numUSUS = "1";
      String oldWordIds = httpServletRequest.getParameter("oldWordIds");
      if (oldWordIds != null && !oldWordIds.trim().equals("")) {
        String rmrs = senddocumentBD.removeWordPoNum(oldWordIds);
        if (rmrs != null && rmrs.equals("0")) {
          String uprs = senddocumentBD.updateNumPO(numId, po);
          if (uprs != null && uprs.equals("0"))
            if (fileWordIds != null && 
              !fileWordIds.trim().equals("")) {
              String re = senddocumentBD.setWordPoNum(fileWordIds, 
                  numId);
              if (re != null && re.equals("0"))
                numUSUS = "0"; 
            } else {
              numUSUS = "0";
            }  
        } 
      } else {
        String uprs = senddocumentBD.updateNumPO(numId, po);
        if (uprs != null && uprs.equals("0")) {
          if (fileWordIds != null && !fileWordIds.trim().equals("")) {
            String re = senddocumentBD.setWordPoNum(fileWordIds, 
                numId);
            if (re != null && re.equals("0"))
              numUSUS = "0"; 
          } else {
            numUSUS = "0";
          } 
        } else {
          numUSUS = uprs;
        } 
      } 
      httpServletRequest.setAttribute("numId", numId);
      httpServletRequest.setAttribute("numUSUS", numUSUS);
      return actionMapping.findForward("nummodiload");
    } 
    if ("numload".equals(action)) {
      List numList = getAllNumType();
      httpServletRequest.setAttribute("typeList", numList);
      SendDocumentNumPO po = senddocumentBD.loadSendDocumentNumPO(editId);
      if (po != null) {
        senddocumentBaseActionForm.setBitNum(po.getBitNum());
        senddocumentBaseActionForm.setInitValue(po.getInitValue());
        senddocumentBaseActionForm.setKeyValue(po.getKeyValue());
        senddocumentBaseActionForm.setNumFormat(po.getNumFormat());
        senddocumentBaseActionForm.setNumIsYear(po.getNumIsYear());
        senddocumentBaseActionForm.setNumMode(po.getNumMode());
        senddocumentBaseActionForm.setNumName(po.getNumName());
        senddocumentBaseActionForm.setNumNote(po.getNumNote());
        senddocumentBaseActionForm.setNumType(po.getNumType());
        httpServletRequest.setAttribute("numfig", po.getNumfig());
        httpServletRequest.setAttribute("numIsYear", po.getNumIsYear());
        httpServletRequest.setAttribute("keyValue", po.getKeyValue());
        httpServletRequest.setAttribute("oldYear", po.getOldYear());
        httpServletRequest.setAttribute("numId", editId);
        httpServletRequest.setAttribute("numIsWord", po.getNumIsWord());
      } 
      String fileWords = "";
      String fileWordIds = "";
      List<Object[]> wordList = senddocumentBD.getwordBynumId(editId);
      if (wordList != null && wordList.size() > 0)
        for (int i = 0; i < wordList.size(); i++) {
          Object[] obj = wordList.get(i);
          if (obj != null && obj.length > 2) {
            fileWords = String.valueOf(fileWords) + obj[1].toString() + ",";
            fileWordIds = String.valueOf(fileWordIds) + obj[0].toString() + ",";
          } 
        }  
      if (fileWords != null && fileWords.length() > 1) {
        fileWords = fileWords.substring(0, fileWords.length() - 1);
        fileWordIds = fileWordIds.substring(0, fileWordIds.length() - 1);
      } 
      httpServletRequest.setAttribute("fileWords", fileWords);
      httpServletRequest.setAttribute("fileWordIds", fileWordIds);
      if (editType.equals("1"))
        return actionMapping.findForward("nummodiload"); 
      return actionMapping.findForward("numload");
    } 
    if ("numdelBatch".equals(action)) {
      String did = httpServletRequest.getParameter("ids");
      if (did != null && did.trim().length() > 1)
        did = did.substring(0, did.length() - 1); 
      String ders = senddocumentBD.deleteNumPO(did);
      list2(httpServletRequest);
      return actionMapping.findForward("numList");
    } 
    if ("numdel".equals(action)) {
      String did = httpServletRequest.getParameter("ids");
      String ders = senddocumentBD.deleteNumPO(did);
      list2(httpServletRequest);
      return actionMapping.findForward("numList");
    } 
    if ("senddocumentseqList".equals(action)) {
      list3(httpServletRequest);
      return actionMapping.findForward("senddocumentseqList");
    } 
    if ("senddocumentseqclose".equals(action) || "senddocumentseqcontinue".equals(action) || "senddocumentsequpdate".equals(action)) {
      Integer seqBitNum = Integer.valueOf(httpServletRequest.getParameter("seqBitNum"));
      Integer seqFileType = Integer.valueOf(httpServletRequest.getParameter("seqFileType"));
      String seqFormat = httpServletRequest.getParameter("seqFormat");
      Integer seqInitValue = Integer.valueOf(httpServletRequest.getParameter("seqInitValue"));
      Integer seqIsUse = Integer.valueOf(httpServletRequest.getParameter("seqIsUse"));
      Integer seqIsYear = Integer.valueOf(httpServletRequest.getParameter("seqIsYear"));
      String seqMode = httpServletRequest.getParameter("seqMode");
      String seqName = httpServletRequest.getParameter("seqName");
      String seqUnitName = httpServletRequest.getParameter("seqUnitName");
      String fileWordIds = httpServletRequest.getParameter("fileWordIds");
      String seqId = httpServletRequest.getParameter("seqId");
      SendDocumentSeqPO po = new SendDocumentSeqPO();
      po.setSeqBitNum(seqBitNum);
      po.setSeqFileType(seqFileType);
      po.setSeqFormat(seqFormat);
      po.setSeqInitValue(seqInitValue);
      po.setSeqIsUse(seqIsUse);
      po.setSeqIsYear(seqIsYear);
      po.setSeqMode(seqMode);
      po.setSeqName(seqName);
      po.setSeqUnitName(seqUnitName);
      po.setCreatedEmp(Long.valueOf((String)object1));
      po.setCreatedOrg(Long.valueOf((String)object2));
      po.setCorpId(Long.valueOf(corpId));
      if ("senddocumentseqclose".equals(action) || "senddocumentseqcontinue".equals(action)) {
        po.setSeqfig(new Integer(seqInitValue.intValue() - 1));
        Long result = senddocumentBD.SaveSeqPO(po);
        if (result != null && !result.toString().equals("-2"))
          if (fileWordIds != null && !fileWordIds.trim().equals("")) {
            String ssrs = senddocumentBD.setWordPoSeq(fileWordIds, result.toString());
            if (ssrs != null && ssrs.equals("0"))
              if ("senddocumentseqclose".equals(action)) {
                httpServletRequest.setAttribute("continue", "0");
              } else {
                httpServletRequest.setAttribute("continue", "1");
              }  
          } else if ("senddocumentseqclose".equals(action)) {
            httpServletRequest.setAttribute("continue", "0");
          } else {
            httpServletRequest.setAttribute("continue", "1");
          }  
        return actionMapping.findForward("seqadd");
      } 
      Integer nf = Integer.valueOf(httpServletRequest.getParameter("seqfig").toString());
      Integer nf1 = new Integer(seqInitValue.intValue() - 1);
      if (nf1.compareTo(nf) > 0) {
        po.setSeqfig(nf1);
      } else {
        po.setSeqfig(nf);
      } 
      httpServletRequest.setAttribute("seqfig", httpServletRequest.getParameter("seqfig"));
      String seqUSUS = "1";
      String oldWordIds = httpServletRequest.getParameter("oldWordIds");
      if (oldWordIds != null && !oldWordIds.trim().equals("")) {
        String rmrs = senddocumentBD.removeWordPoSeq(oldWordIds);
        if (rmrs != null && rmrs.equals("0")) {
          String uprs = senddocumentBD.updateSeqPO(seqId, po);
          if (uprs != null && uprs.equals("0"))
            if (fileWordIds != null && !fileWordIds.trim().equals("")) {
              String re = senddocumentBD.setWordPoSeq(fileWordIds, seqId);
              if (re != null && re.equals("0"))
                seqUSUS = "0"; 
            } else {
              seqUSUS = "0";
            }  
        } 
      } else {
        String uprs = senddocumentBD.updateSeqPO(seqId, po);
        if (uprs != null && uprs.equals("0"))
          if (fileWordIds != null && !fileWordIds.trim().equals("")) {
            String re = senddocumentBD.setWordPoSeq(fileWordIds, seqId);
            if (re != null && re.equals("0"))
              seqUSUS = "0"; 
          } else {
            seqUSUS = "0";
          }  
      } 
      httpServletRequest.setAttribute("seqUSUS", seqUSUS);
      return actionMapping.findForward("seqmodiload");
    } 
    if ("senddocumentseqload".equals(action)) {
      SendDocumentSeqPO po = senddocumentBD.loadSendDocumentSeqPO(editId);
      if (po != null) {
        senddocumentBaseActionForm.setSeqBitNum(po.getSeqBitNum());
        senddocumentBaseActionForm.setSeqFileType(po.getSeqFileType());
        senddocumentBaseActionForm.setSeqFormat(po.getSeqFormat());
        senddocumentBaseActionForm.setSeqInitValue(po.getSeqInitValue());
        senddocumentBaseActionForm.setSeqIsUse(po.getSeqIsUse());
        senddocumentBaseActionForm.setSeqIsYear(po.getSeqIsYear());
        senddocumentBaseActionForm.setSeqMode(po.getSeqMode());
        senddocumentBaseActionForm.setSeqName(po.getSeqName());
        senddocumentBaseActionForm.setSeqUnitName(po.getSeqUnitName());
        httpServletRequest.setAttribute("seqFileType", po.getSeqFileType());
        httpServletRequest.setAttribute("seqIsUse", po.getSeqIsUse());
        httpServletRequest.setAttribute("seqIsYear", po.getSeqIsYear());
        httpServletRequest.setAttribute("seqId", editId);
        httpServletRequest.setAttribute("seqfig", po.getSeqfig());
      } 
      String fileWords = "";
      String fileWordIds = "";
      List<Object[]> wordList = senddocumentBD.getwordBySeqId(editId);
      if (wordList != null && wordList.size() > 0)
        for (int i = 0; i < wordList.size(); i++) {
          Object[] obj = wordList.get(i);
          if (obj != null && obj.length > 2) {
            fileWords = String.valueOf(fileWords) + obj[1].toString() + ",";
            fileWordIds = String.valueOf(fileWordIds) + obj[0].toString() + ",";
          } 
        }  
      if (fileWords != null && fileWords.length() > 1) {
        fileWords = fileWords.substring(0, fileWords.length() - 1);
        fileWordIds = fileWordIds.substring(0, fileWordIds.length() - 1);
      } 
      httpServletRequest.setAttribute("fileWords", fileWords);
      httpServletRequest.setAttribute("fileWordIds", fileWordIds);
      if (editType.equals("1"))
        return actionMapping.findForward("seqmodiload"); 
      return actionMapping.findForward("seqload");
    } 
    if ("senddocumentseqadd".equals(action))
      return actionMapping.findForward("seqadd"); 
    "seqdelBatch".equals(action);
    if ("seqdelBatch".equals(action)) {
      String did = httpServletRequest.getParameter("ids");
      if (did != null && did.trim().length() > 1)
        did = did.substring(0, did.length() - 1); 
      String ders = senddocumentBD.deleteSeqPO(did);
      list3(httpServletRequest);
      return actionMapping.findForward("senddocumentseqList");
    } 
    if ("senddocumentseqdel".equals(action)) {
      String did = httpServletRequest.getParameter("ids");
      String ders = senddocumentBD.deleteSeqPO(did);
      list3(httpServletRequest);
      return actionMapping.findForward("senddocumentseqList");
    } 
    if ("zjkytopical".equals(action)) {
      zjkytopical(httpServletRequest);
      return actionMapping.findForward("topicalList");
    } 
    if ("zjkytopicaladd".equals(action)) {
      getTopicalMaps(httpServletRequest);
      return actionMapping.findForward("zjkytopicaladd");
    } 
    if ("zjkytopicalsaveclose".equals(action) || "zjkytopicalsavecontinue".equals(action) || "zjkytopicalupdate".equals(action)) {
      SenddocumentTopicalPO po = new SenddocumentTopicalPO();
      getTopicalMaps(httpServletRequest);
      setZjkyTopicalPO(po, httpServletRequest);
      if ("zjkytopicalupdate".equals(action)) {
        po.setId(new Long(httpServletRequest.getParameter("topicalId")));
        String ur = senddocumentBD.updateTopicalPO(po);
        if (ur != null) {
          if (!"0".equals(ur)) {
            loadTopical((String)po.getId(), httpServletRequest, senddocumentBaseActionForm);
            httpServletRequest.setAttribute("topicalId", po.getId());
            httpServletRequest.setAttribute("editType", "1");
          } 
          httpServletRequest.setAttribute("updateSUS", ur);
        } 
        return actionMapping.findForward("zjkytopicalmodiload");
      } 
      po.setCorpId(Long.valueOf(corpId));
      po.setCreatedEmp(Long.valueOf((String)object1));
      po.setCreatedOrg(Long.valueOf((String)object2));
      Long sr = senddocumentBD.saveTopicalInfo(po);
      if ("zjkytopicalsaveclose".equals(action))
        httpServletRequest.setAttribute("continue", "0"); 
      if ("zjkytopicalsavecontinue".equals(action)) {
        httpServletRequest.setAttribute("continue", "1");
        if (sr.longValue() > 0L) {
          senddocumentBaseActionForm.setAreaType("");
          senddocumentBaseActionForm.setSortTopical("");
        } 
      } 
      httpServletRequest.setAttribute("newId", sr);
      return actionMapping.findForward("zjkytopicaladd");
    } 
    if ("zjkyTopicalLoad".equals(action)) {
      loadTopical(editId, httpServletRequest, senddocumentBaseActionForm);
      httpServletRequest.setAttribute("topicalId", editId);
      httpServletRequest.setAttribute("editType", httpServletRequest.getParameter("editType"));
      return actionMapping.findForward("zjkytopicalmodiload");
    } 
    if ("zjkyTopicaldelete".equals(action)) {
      String ids = (httpServletRequest.getParameter("ids") == null) ? "" : httpServletRequest.getParameter("ids").toString();
      if (ids != null && !ids.equals(""))
        senddocumentBD.deleteTopicalPO(ids); 
      zjkytopical(httpServletRequest);
      return actionMapping.findForward("topicalList");
    } 
    if ("unitlist".equals(action)) {
      unitlist(httpServletRequest, 0);
      setUnitType(httpServletRequest);
      httpServletRequest.setAttribute("flag", httpServletRequest.getParameter("flag"));
      return actionMapping.findForward("unitList");
    } 
    if ("unitlist_choose".equals(action)) {
      unitlist(httpServletRequest, 1);
      setUnitType(httpServletRequest);
      return actionMapping.findForward("unitlist_choose");
    } 
    if ("unitAdd".equals(action)) {
      setUnitType(httpServletRequest);
      return actionMapping.findForward("unitAdd");
    } 
    if ("unitSave".equals(action) || "unitcontinue".equals(action) || "unitupdate".equals(action)) {
      SendDocumentUnitPO po = new SendDocumentUnitPO();
      setUnitPo(po, httpServletRequest);
      setUnitType(httpServletRequest);
      if ("unitupdate".equals(action)) {
        String id = httpServletRequest.getParameter("unitId");
        po.setId(new Long(id));
        String ur = senddocumentBD.updateUnitPO(po);
        if (ur != null && ur.equals("0")) {
          httpServletRequest.setAttribute("updateSUS", "0");
        } else {
          httpServletRequest.setAttribute("updateSUS", "1");
        } 
        return actionMapping.findForward("unitmodiload");
      } 
      po.setCorpId(Long.valueOf(corpId));
      po.setCreatedEmp(Long.valueOf((String)object1));
      po.setCreatedOrg(Long.valueOf((String)object2));
      Long sr = senddocumentBD.saveUnitInfo(po);
      if ("unitSave".equals(action) && 
        sr != null && !sr.toString().equals("-2"))
        httpServletRequest.setAttribute("continue", "0"); 
      if ("unitcontinue".equals(action) && 
        sr != null && !sr.toString().equals("-2"))
        httpServletRequest.setAttribute("continue", "1"); 
      return actionMapping.findForward("unitAdd");
    } 
    if ("unitload".equals(action)) {
      unitload(editId, httpServletRequest, senddocumentBaseActionForm);
      return actionMapping.findForward("unitmodiload");
    } 
    if ("unitdelete".equals(action)) {
      String ids = httpServletRequest.getParameter("ids");
      if (ids != null && !ids.trim().equals(""))
        senddocumentBD.deleteUnitPO(ids); 
      String flag = httpServletRequest.getParameter("flag");
      unitlist(httpServletRequest, 0);
      setUnitType(httpServletRequest);
      httpServletRequest.setAttribute("flag", flag);
      return actionMapping.findForward("unitList");
    } 
    if ("chooseSendTopical".equals(action)) {
      chooseSendTopical(httpServletRequest);
      getTopicalMaps(httpServletRequest);
      return actionMapping.findForward("chooseSendTopical");
    } 
    if ("sendtmepList".equals(action)) {
      httpServletRequest.setAttribute("tempIds", httpServletRequest.getParameter("tempIds"));
      getlist(httpServletRequest);
      return actionMapping.findForward("sendtmepList");
    } 
    return actionMapping.findForward("baseInfoList");
  }
  
  private void displayBaseInfo(HttpServletRequest request, SenddocumentBaseActionForm senddocumentBaseActionForm) {
    Object[] baseObj = (new SenddocumentBD()).getSenddocumentBaseInfo();
    String id = "";
    String contentLevel = "";
    String fileType = "";
    String keepSecretLevel = "";
    String secretLevel = "";
    String transactLevel = "";
    String unitWord = "";
    String baseUnitClass = "";
    String topicalAreaClass = "";
    String baseSorttopical = "";
    String baseQueryLevel = "";
    String openProperty = "";
    String sendDropDownSelect1 = "";
    String sendDropDownSelect2 = "";
    if (baseObj != null && baseObj.length > 0) {
      if (baseObj[0] != null)
        id = baseObj[0].toString(); 
      if (baseObj[1] != null)
        contentLevel = baseObj[1].toString(); 
      if (baseObj[2] != null)
        fileType = baseObj[2].toString(); 
      if (baseObj[3] != null)
        keepSecretLevel = baseObj[3].toString(); 
      if (baseObj[4] != null)
        secretLevel = baseObj[4].toString(); 
      if (baseObj[5] != null)
        transactLevel = baseObj[5].toString(); 
      if (baseObj[6] != null)
        unitWord = baseObj[6].toString(); 
      if (baseObj[7] != null)
        baseUnitClass = baseObj[7].toString(); 
      if (baseObj[8] != null)
        topicalAreaClass = baseObj[8].toString(); 
      if (baseObj[9] != null)
        baseSorttopical = baseObj[9].toString(); 
      if (baseObj[10] != null)
        baseQueryLevel = baseObj[10].toString(); 
      if (baseObj[11] != null)
        openProperty = baseObj[11].toString(); 
      if (baseObj[12] != null)
        sendDropDownSelect1 = baseObj[12].toString(); 
      if (baseObj[13] != null)
        sendDropDownSelect2 = baseObj[13].toString(); 
      senddocumentBaseActionForm.setContentLevel(contentLevel);
      senddocumentBaseActionForm.setFileType(fileType);
      senddocumentBaseActionForm.setKeepSecretLevel(keepSecretLevel);
      senddocumentBaseActionForm.setSecretLevel(secretLevel);
      senddocumentBaseActionForm.setTransactLevel(transactLevel);
      senddocumentBaseActionForm.setUnitWord(unitWord);
      senddocumentBaseActionForm.setBaseUnitClass(baseUnitClass);
      senddocumentBaseActionForm.setTopicalAreaClass(topicalAreaClass);
      senddocumentBaseActionForm.setBaseSorttopical(baseSorttopical);
      senddocumentBaseActionForm.setBaseQueryLevel(baseQueryLevel);
      senddocumentBaseActionForm.setOpenProperty(openProperty);
      senddocumentBaseActionForm.setSendDropDownSelect1(sendDropDownSelect1);
      senddocumentBaseActionForm.setSendDropDownSelect2(sendDropDownSelect2);
      request.setAttribute("baseId", id);
    } 
  }
  
  private void saveBaseInf(HttpServletRequest httpServletRequest, SenddocumentBaseActionForm senddocumentBaseActionForm) {
    BaseInfoPO po = new BaseInfoPO();
    String id = "";
    String contentLevel = "";
    String fileType = "";
    String keepSecretLevel = "";
    String secretLevel = "";
    String transactLevel = "";
    String unitWord = "";
    if (httpServletRequest.getParameter("contentLevel") != null)
      contentLevel = httpServletRequest.getParameter("contentLevel").toString(); 
    if (httpServletRequest.getParameter("fileType") != null)
      fileType = httpServletRequest.getParameter("fileType").toString(); 
    if (httpServletRequest.getParameter("keepSecretLevel") != null)
      keepSecretLevel = httpServletRequest.getParameter("keepSecretLevel").toString(); 
    if (httpServletRequest.getParameter("secretLevel") != null)
      secretLevel = httpServletRequest.getParameter("secretLevel").toString(); 
    if (httpServletRequest.getParameter("transactLevel") != null)
      transactLevel = httpServletRequest.getParameter("transactLevel").toString(); 
    if (httpServletRequest.getParameter("unitWord") != null)
      unitWord = httpServletRequest.getParameter("unitWord").toString(); 
    if (httpServletRequest.getParameter("baseId") != null && !httpServletRequest.getParameter("baseId").toString().equals(""))
      id = httpServletRequest.getParameter("baseId").toString(); 
    po.setContentLevel(contentLevel);
    po.setKeepSecretLevel(keepSecretLevel);
    po.setFileType(fileType);
    po.setSecretLevel(secretLevel);
    po.setTransactLevel(transactLevel);
    po.setUnitWord(unitWord);
    po.setBaseUnitClass((httpServletRequest.getParameter("baseUnitClass") == null) ? "" : httpServletRequest.getParameter("baseUnitClass"));
    po.setTopicalAreaClass((httpServletRequest.getParameter("topicalAreaClass") == null) ? "" : httpServletRequest.getParameter("topicalAreaClass"));
    po.setBaseSorttopical((httpServletRequest.getParameter("baseSorttopical") == null) ? "" : httpServletRequest.getParameter("baseSorttopical"));
    po.setBaseQueryLevel((httpServletRequest.getParameter("baseQueryLevel") == null) ? "" : httpServletRequest.getParameter("baseQueryLevel"));
    po.setOpenProperty((httpServletRequest.getParameter("openProperty") == null) ? "" : httpServletRequest.getParameter("openProperty"));
    po.setSendDropDownSelect1((httpServletRequest.getParameter("sendDropDownSelect1") == null) ? "" : httpServletRequest.getParameter("sendDropDownSelect1"));
    po.setSendDropDownSelect2((httpServletRequest.getParameter("sendDropDownSelect2") == null) ? "" : httpServletRequest.getParameter("sendDropDownSelect2"));
    if (id != null && !id.trim().equals("")) {
      po.setId(new Long(id.trim()));
      httpServletRequest.setAttribute("baseId", id);
      String result = (new SenddocumentBD()).updateBaseInfo(po);
      if (result != null && result.equals("0")) {
        httpServletRequest.setAttribute("saveBaseInfoSuccess", "0");
      } else {
        httpServletRequest.setAttribute("saveBaseInfoSuccess", "1");
      } 
    } else {
      Long result = (new SenddocumentBD()).saveBaseInfo(po);
      if (result != null && !result.toString().equals("-2")) {
        httpServletRequest.setAttribute("saveBaseInfoSuccess", "0");
        httpServletRequest.setAttribute("baseId", result.toString());
      } else {
        httpServletRequest.setAttribute("saveBaseInfoSuccess", "1");
      } 
    } 
  }
  
  private void savedocumentword(HttpServletRequest httpServletRequest, SenddocumentBaseActionForm senddocumentBaseActionForm) {
    Object object1 = httpServletRequest.getSession(true).getAttribute("userId");
    Object object2 = httpServletRequest.getSession(true).getAttribute("orgId");
    Object object3 = (httpServletRequest.getSession(true).getAttribute("domainId") == null) ? "0" : httpServletRequest.getSession(true).getAttribute("domainId");
    String action = httpServletRequest.getParameter("action");
    String corpId = httpServletRequest.getSession(true).getAttribute("corpId").toString();
    Long id = null;
    Long processId = null;
    Long sendDocumentNumId = null;
    String templateId = "";
    String userRange = "";
    String userRangeId = "";
    String wordName = "";
    Long sendDocumentSeqId = null;
    String templateName = "";
    String processName = "";
    if (httpServletRequest.getParameter("processId") != null && !httpServletRequest.getParameter("processId").toString().trim().equals("")) {
      processId = new Long(httpServletRequest.getParameter("processId").toString());
      processName = httpServletRequest.getParameter("processName");
    } else {
      processId = new Long("-1");
    } 
    if (httpServletRequest.getParameter("sendDocumentNumId") != null && !httpServletRequest.getParameter("sendDocumentNumId").toString().trim().equals("")) {
      sendDocumentNumId = new Long(httpServletRequest.getParameter("sendDocumentNumId").toString());
    } else {
      sendDocumentNumId = new Long(-1L);
    } 
    if (httpServletRequest.getParameter("sendDocumentSeqId") != null && !httpServletRequest.getParameter("sendDocumentSeqId").toString().trim().equals("")) {
      sendDocumentSeqId = new Long(httpServletRequest.getParameter("sendDocumentSeqId").toString());
    } else {
      sendDocumentSeqId = new Long(-1L);
    } 
    if (httpServletRequest.getParameter("templateId") != null && !httpServletRequest.getParameter("templateId").toString().trim().equals("")) {
      templateId = httpServletRequest.getParameter("templateId").toString();
      templateName = httpServletRequest.getParameter("templateName");
    } else {
      templateId = "-1";
    } 
    if (httpServletRequest.getParameter("userRange") != null && !httpServletRequest.getParameter("userRange").toString().trim().equals(""))
      userRange = httpServletRequest.getParameter("userRange").toString(); 
    if (httpServletRequest.getParameter("userRangeId") != null && !httpServletRequest.getParameter("userRangeId").toString().trim().equals(""))
      userRangeId = httpServletRequest.getParameter("userRangeId").toString(); 
    if (httpServletRequest.getParameter("wordName") != null && !httpServletRequest.getParameter("wordName").toString().trim().equals(""))
      wordName = httpServletRequest.getParameter("wordName").toString(); 
    SendDocumentWordPO po = new SendDocumentWordPO();
    po.setProcessId(processId);
    po.setSendDocumentNumId(sendDocumentNumId);
    po.setTemplateId(templateId);
    po.setUserRange(userRange);
    po.setUserRangeId(userRangeId);
    po.setWordName(wordName);
    po.setSendDocumentSeqId(sendDocumentSeqId);
    po.setProcessName(processName);
    po.setTemplateName(templateName);
    po.setCreatedEmp(Long.valueOf((String)object1));
    po.setCreatedOrg(Long.valueOf((String)object2));
    po.setCorpId(Long.valueOf(corpId));
    if (httpServletRequest.getParameter("receiveUser") != null && 
      !httpServletRequest.getParameter("receiveUser").toString().equals("")) {
      po.setReceiveUser(httpServletRequest.getParameter("receiveUser"));
    } else {
      po.setReceiveUser("");
    } 
    if (httpServletRequest.getParameter("receiveOrg") != null && 
      !httpServletRequest.getParameter("receiveOrg").toString().equals("")) {
      po.setReceiveOrg(httpServletRequest.getParameter("receiveOrg"));
    } else {
      po.setReceiveOrg("");
    } 
    if (httpServletRequest.getParameter("receiveGroup") != null && 
      !httpServletRequest.getParameter("receiveGroup").toString().equals("")) {
      po.setReceiveGroup(httpServletRequest.getParameter("receiveGroup"));
    } else {
      po.setReceiveGroup("");
    } 
    if (httpServletRequest.getParameter("receiveScopeName") != null && 
      !httpServletRequest.getParameter("receiveScopeName").toString().equals("")) {
      po.setReceiveScopeName(httpServletRequest.getParameter("receiveScopeName"));
    } else {
      po.setReceiveScopeName("");
    } 
    if (httpServletRequest.getParameter("receiveScopeId") != null && 
      !httpServletRequest.getParameter("receiveScopeId").toString().equals("")) {
      po.setReceiveScopeId(httpServletRequest.getParameter("receiveScopeId"));
    } else {
      po.setReceiveScopeId("");
    } 
    if (httpServletRequest.getParameter("redHeadName") != null && 
      !httpServletRequest.getParameter("redHeadName").toString().equals("")) {
      po.setRedHeadName(httpServletRequest.getParameter("redHeadName"));
      po.setRedHeadSaveName(httpServletRequest.getParameter("redHeadSaveName"));
    } else {
      po.setRedHeadName("");
      po.setRedHeadSaveName("");
    } 
    Long result = (new SenddocumentBD()).saveWordInfo(po);
    List list1 = getSendDocumentNumId();
    List list2 = getSendfileList(httpServletRequest);
    List list3 = getTemplateList((String)object3, "", "");
    List list4 = getSendDocumentSeqId();
    httpServletRequest.setAttribute("numList", list1);
    httpServletRequest.setAttribute("sendfileList", list2);
    httpServletRequest.setAttribute("templateList", list3);
    httpServletRequest.setAttribute("seqList", list4);
    if (result != null && !result.toString().equals("-2")) {
      if ("senddocumentwordcontinue".equals(action)) {
        httpServletRequest.setAttribute("continue", "1");
      } else {
        httpServletRequest.setAttribute("continue", "0");
      } 
      if (result.toString().equals("-1"))
        httpServletRequest.setAttribute("continue", "-1"); 
    } 
  }
  
  private void list(HttpServletRequest request) {
    HttpSession httpSession = request.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String orgId = httpSession.getAttribute("orgId").toString();
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String fromwhere = 
      "com.js.doc.doc.po.SendDocumentWordPO po ";
    ManagerService service = new ManagerService();
    String whereSql = service.getRightFinalWhere(userId, orgId, "03*03*01", "po.createdOrg", "po.createdEmp");
    if (request.getParameter("wordName") != null && !request.getParameter("wordName").toString().equals(""))
      try {
        String wordName = URLDecoder.decode(request.getParameter("wordName"), "UTF-8");
        whereSql = String.valueOf(whereSql) + "and  po.wordName like '%" + wordName + "%' ";
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }  
    if (request.getParameter("processName") != null && !request.getParameter("processName").toString().equals(""))
      try {
        String processName = URLDecoder.decode(request.getParameter("processName"), "UTF-8");
        whereSql = String.valueOf(whereSql) + " and  po.processName like '%" + processName + "%'";
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }  
    if (request.getParameter("templateName") != null && !request.getParameter("templateName").toString().equals(""))
      try {
        String templateName = URLDecoder.decode(request.getParameter("templateName"), "UTF-8");
        whereSql = String.valueOf(whereSql) + " and  po.templateName like '%" + templateName + "%'";
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }  
    String orderSql = "";
    String orderBy = request.getParameter("orderBy");
    String sortType = request.getParameter("sortType");
    if (sortType == null)
      sortType = "desc"; 
    if (orderBy == null) {
      orderSql = String.valueOf(orderSql) + " order by po.processName desc,po.id desc";
    } else if (orderBy.equals("processId")) {
      orderSql = String.valueOf(orderSql) + " order by po.processName " + sortType;
    } 
    whereSql = String.valueOf(whereSql) + orderSql;
    try {
      Page page = new Page("po.id, po.processId,po.sendDocumentNumId,po.templateId,po.userRange,po.userRangeId,po.wordName,po.sendDocumentSeqId,po.processName,po.templateName", 
          fromwhere, 
          " where " + whereSql);
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      if (list != null && 
        list.size() == 0 && offset >= 15) {
        offset -= 15;
        currentPage = offset / pageSize + 1;
        page.setPageSize(pageSize);
        page.setcurrentPage(currentPage);
        list = page.getResultList();
        request.setAttribute("new.offset", (new StringBuilder(String.valueOf(offset))).toString());
        request.setAttribute("pager.realCurrent", (new StringBuilder(String.valueOf(currentPage))).toString());
      } 
      String recordCount = String.valueOf(page.getRecordCount());
      page.setcurrentPage(currentPage);
      request.setAttribute("mylist", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "action,wordName,templateName,processName,queryStatus,queryEndDate,queryNumber,querySecret,queryTransPersonName,queryTitle,queryOrg,redHeadId,orderBy,sortType");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private void list2(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String orgId = session.getAttribute("orgId").toString();
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String fromwhere = "com.js.doc.doc.po.SendDocumentNumPO po ";
    ManagerService service = new ManagerService();
    String whereSql = service.getRightFinalWhere(userId, orgId, "03*03*01", "po.createdOrg", "po.createdEmp");
    if (request.getParameter("numName") != null && !request.getParameter("numName").toString().equals(""))
      try {
        String numName = URLDecoder.decode(request.getParameter("numName"), "UTF-8");
        whereSql = String.valueOf(whereSql) + " and  po.numName like '%" + numName + "%' ";
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }  
    try {
      Page page = new Page("po.id, po.bitNum,po.initValue,po.keyValue,po.numFormat,po.numType,po.numIsYear,po.numMode,po.numName,po.numNote", 
          fromwhere, 
          " where " + whereSql + 
          "  order by po.id desc ");
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      if (list != null && 
        list.size() == 0 && offset >= 15) {
        offset -= 15;
        currentPage = offset / pageSize + 1;
        page.setPageSize(pageSize);
        page.setcurrentPage(currentPage);
        list = page.getResultList();
        request.setAttribute("new.offset", (new StringBuilder(String.valueOf(offset))).toString());
        request.setAttribute("pager.realCurrent", (new StringBuilder(String.valueOf(currentPage))).toString());
      } 
      String recordCount = String.valueOf(page.getRecordCount());
      page.setcurrentPage(currentPage);
      request.setAttribute("mylist", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "action,numName,queryItem,queryBeginDate,queryStatus,queryEndDate,queryNumber,querySecret,queryTransPersonName,queryTitle,queryOrg,redHeadId");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private void list3(HttpServletRequest request) {
    HttpSession httpSession = request.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String orgId = httpSession.getAttribute("orgId").toString();
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String fromwhere = 
      "com.js.doc.doc.po.SendDocumentSeqPO po ";
    ManagerService service = new ManagerService();
    String whereSql = service.getRightFinalWhere(userId, orgId, "03*03*01", "po.createdOrg", "po.createdEmp");
    if (request.getParameter("seqName") != null && !request.getParameter("seqName").toString().equals(""))
      try {
        String seqName = URLDecoder.decode(request.getParameter("seqName"), "UTF-8");
        whereSql = String.valueOf(whereSql) + " and po.seqName like '%" + seqName + "%' ";
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }  
    try {
      Page page = new Page("po.id, po.seqBitNum,po.seqFileType,po.seqFormat,po.seqInitValue,po.seqIsUse ,po.seqIsYear,po.seqMode,po.seqName,po.seqUnitName", 
          fromwhere, 
          " where " + whereSql + 
          "  order by po.id desc ");
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      if (list != null && 
        list.size() == 0 && offset >= 15) {
        offset -= 15;
        currentPage = offset / pageSize + 1;
        page.setPageSize(pageSize);
        page.setcurrentPage(currentPage);
        list = page.getResultList();
        request.setAttribute("new.offset", (new StringBuilder(String.valueOf(offset))).toString());
        request.setAttribute("pager.realCurrent", (new StringBuilder(String.valueOf(currentPage))).toString());
      } 
      String recordCount = String.valueOf(page.getRecordCount());
      page.setcurrentPage(currentPage);
      request.setAttribute("mylist", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "action,seqName,queryItem,queryBeginDate,queryStatus,queryEndDate,queryNumber,querySecret,queryTransPersonName,queryTitle,queryOrg,redHeadId");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private List getSendfileList(HttpServletRequest request) {
    Object object1 = request.getSession(true).getAttribute("userId");
    Object object2 = request.getSession(true)
      .getAttribute("orgIdString");
    ProcessBD procbd = new ProcessBD();
    List list = new ArrayList();
    Object tmp = procbd.getAllDossProc("2");
    if (tmp != null)
      list = (List)tmp; 
    return list;
  }
  
  private List getTemplateList(String domainId, String type, String id) {
    SenddocumentBD bd = new SenddocumentBD();
    List<Object[]> rList = new ArrayList();
    Object[] dd = { "", "" };
    rList.add(dd);
    if (type.equals("load") || type.equals("update")) {
      List<Object[]> loadList = bd.getTemplateById(domainId, id);
      if (loadList != null && loadList.size() > 0) {
        Object[] loadObj = loadList.get(0);
        rList.add(loadObj);
      } 
      if ("load".equals(type))
        return rList; 
    } 
    String tepListIds = "";
    List<?> notUseList = bd.getTemplateList(domainId, "");
    if (notUseList != null && notUseList.size() > 0)
      rList.addAll(notUseList); 
    return rList;
  }
  
  private List getSendDocumentNumId() {
    SenddocumentBD senddocumentBD = new SenddocumentBD();
    List<Object[]> rList = new ArrayList();
    Object[] dd = { "", "" };
    rList.add(dd);
    List<?> list = new ArrayList();
    list = senddocumentBD.getAllSendDocumentNumPO();
    if (list != null && list.size() > 0)
      rList.addAll(list); 
    return rList;
  }
  
  private List getSendDocumentSeqId() {
    SenddocumentBD senddocumentBD = new SenddocumentBD();
    List<Object[]> rList = new ArrayList();
    Object[] dd = { "", "" };
    rList.add(dd);
    List<?> list = new ArrayList();
    list = senddocumentBD.getAllSendDocumentSeqPO();
    if (list != null && list.size() > 0)
      rList.addAll(list); 
    return rList;
  }
  
  private List getAllNumType() {
    List<String> list = new ArrayList();
    String unitWord = "";
    SenddocumentBD senddocumentBD = new SenddocumentBD();
    Object[] baseObj = senddocumentBD.getSenddocumentBaseInfo();
    if (baseObj != null && baseObj.length > 6)
      if (baseObj[6] != null)
        unitWord = baseObj[6].toString();  
    if (unitWord != null && unitWord.length() > 1) {
      String[] tmp = unitWord.split(";");
      if (tmp != null && tmp.length > 0)
        for (int i = 0; i < tmp.length; i++)
          list.add(tmp[i]);  
    } 
    return list;
  }
  
  private void zjkytopical(HttpServletRequest request) {
    HttpSession httpSession = request.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String orgId = httpSession.getAttribute("orgId").toString();
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String fromwhere = 
      "com.js.doc.doc.po.SenddocumentTopicalPO po ";
    ManagerService service = new ManagerService();
    String whereSql = service.getRightFinalWhere(userId, orgId, "03*03*01", "po.createdOrg", "po.createdEmp");
    if (request.getParameter("sortTopical") != null && !request.getParameter("sortTopical").toString().equals(""))
      try {
        String sortTopical = URLDecoder.decode(request.getParameter("sortTopical"), "UTF-8");
        whereSql = String.valueOf(whereSql) + " and po.sortTopical like '%" + sortTopical + "%' ";
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }  
    try {
      Page page = new Page("po.id, po.tableType,po.areaType,po.sortTopical,po.attributeTopical", 
          fromwhere, 
          " where " + whereSql + 
          "  order by po.id desc ");
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      if (list != null && 
        list.size() == 0 && offset >= 15) {
        offset -= 15;
        currentPage = offset / pageSize + 1;
        page.setPageSize(pageSize);
        page.setcurrentPage(currentPage);
        list = page.getResultList();
        request.setAttribute("new.offset", (new StringBuilder(String.valueOf(offset))).toString());
        request.setAttribute("pager.realCurrent", (new StringBuilder(String.valueOf(currentPage))).toString());
      } 
      String recordCount = String.valueOf(page.getRecordCount());
      page.setcurrentPage(currentPage);
      request.setAttribute("mylist", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "action,sortTopical,queryItem,queryBeginDate,queryStatus,queryEndDate,queryNumber,querySecret,queryTransPersonName,queryTitle,queryOrg,redHeadId");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private void setZjkyTopicalPO(SenddocumentTopicalPO po, HttpServletRequest request) {
    po.setTableType(new Integer(request.getParameter("tableType")));
    po.setAreaType((request.getParameter("areaType") == null) ? "" : request.getParameter("areaType").toString());
    po.setSortTopical((request.getParameter("sortTopical") == null) ? "" : request.getParameter("sortTopical").toString());
    po.setAttributeTopical((request.getParameter("attributeTopical") == null) ? "" : request.getParameter("attributeTopical").toString());
  }
  
  private void loadTopical(String editId, HttpServletRequest request, SenddocumentBaseActionForm senddocumentBaseActionForm) {
    SenddocumentBD senddocumentBD = new SenddocumentBD();
    SenddocumentTopicalPO po = senddocumentBD.loadSendTopicalPO(editId);
    if (po != null) {
      senddocumentBaseActionForm.setAreaType((po.getAreaType() == null) ? "" : po.getAreaType().toString());
      senddocumentBaseActionForm.setSortTopical((po.getSortTopical() == null) ? "" : po.getSortTopical().toString());
      senddocumentBaseActionForm.setAttributeTopical((po.getAttributeTopical() == null) ? "" : po.getAttributeTopical().toString());
      request.setAttribute("tableType", (po.getTableType() == null) ? "1" : po.getTableType());
      request.setAttribute("sortTopical", po.getSortTopical());
    } 
    getTopicalMaps(request);
  }
  
  private void getTopicalMaps(HttpServletRequest request) {
    SenddocumentBD senddocumentBD = new SenddocumentBD();
    Object[] baseObj = senddocumentBD.getSenddocumentBaseInfo();
    String[] areaType = (String[])null;
    String[] sortTopical = (String[])null;
    if (baseObj != null) {
      if (baseObj[8] != null) {
        String topicalAreaClass = baseObj[8].toString();
        if (topicalAreaClass != null && !topicalAreaClass.trim().equals(""))
          areaType = topicalAreaClass.split(";"); 
      } 
      if (baseObj[9] != null) {
        String baseSorttopical = baseObj[9].toString();
        if (baseSorttopical != null && !baseSorttopical.trim().equals(""))
          sortTopical = baseSorttopical.split(";"); 
      } 
    } 
    Map<Object, Object> mapinfo = new HashMap<Object, Object>();
    mapinfo.put("areaType", areaType);
    mapinfo.put("sortTopical", sortTopical);
    request.setAttribute("mapinfo", mapinfo);
  }
  
  private void unitlist(HttpServletRequest request, int flag) {
    HttpSession httpSession = request.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String orgId = httpSession.getAttribute("orgId").toString();
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String fromwhere = 
      "com.js.doc.doc.po.SendDocumentUnitPO po ";
    String whereSql = "";
    if (flag == 1) {
      if (SystemCommon.getMultiDepart() == 1) {
        whereSql = " po.corpId=" + httpSession.getAttribute("corpId").toString();
      } else {
        whereSql = " 1=1 ";
      } 
    } else {
      ManagerService service = new ManagerService();
      whereSql = service.getRightFinalWhere(userId, orgId, "03*03*01", "po.createdOrg", "po.createdEmp");
    } 
    String unitType = request.getParameter("unitType");
    if (unitType != null) {
      try {
        unitType = URLDecoder.decode(unitType, "UTF-8");
      } catch (UnsupportedEncodingException e1) {
        e1.printStackTrace();
      } 
      if (unitType != null && !unitType.toString().equals("")) {
        whereSql = String.valueOf(whereSql) + " and  po.unitType = '" + unitType + "' ";
        request.setAttribute("unitType", unitType);
      } else {
        request.setAttribute("unitType", "");
      } 
    } 
    String orderSql = "";
    String orderBy = request.getParameter("orderBy");
    String sortType = request.getParameter("sortType");
    if (sortType == null)
      sortType = "desc"; 
    if (orderBy == null) {
      orderSql = String.valueOf(orderSql) + " order by po.unitType desc,po.id desc";
    } else if (orderBy.equals("unitType")) {
      orderSql = String.valueOf(orderSql) + " order by po.unitType " + sortType;
    } 
    whereSql = String.valueOf(whereSql) + orderSql;
    try {
      Page page = new Page("po.id, po.unitType,po.unitWholeName,po.unitShortName", 
          fromwhere, 
          " where " + whereSql);
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      if (list != null && 
        list.size() == 0 && offset >= 15) {
        offset -= 15;
        currentPage = offset / pageSize + 1;
        page.setPageSize(pageSize);
        page.setcurrentPage(currentPage);
        list = page.getResultList();
        request.setAttribute("new.offset", (new StringBuilder(String.valueOf(offset))).toString());
        request.setAttribute("pager.realCurrent", (new StringBuilder(String.valueOf(currentPage))).toString());
      } 
      String recordCount = String.valueOf(page.getRecordCount());
      page.setcurrentPage(currentPage);
      request.setAttribute("mylist", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "action,field,fieldId,unitType,orderBy,sortType,flag");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private void setUnitPo(SendDocumentUnitPO po, HttpServletRequest request) {
    po.setUnitType((request.getParameter("unitType") == null) ? "" : request.getParameter("unitType").toString());
    po.setUnitWholeName((request.getParameter("unitWholeName") == null) ? "" : request.getParameter("unitWholeName").toString());
    po.setUnitShortName((request.getParameter("unitShortName") == null) ? "" : request.getParameter("unitShortName").toString());
  }
  
  private void setUnitType(HttpServletRequest request) {
    String[] unitType = { "" };
    SenddocumentBD bd = new SenddocumentBD();
    Object[] obj = bd.getSenddocumentBaseInfo();
    if (obj != null && obj.length > 0 && 
      obj[7] != null) {
      String baseUnitClass = obj[7].toString();
      unitType = baseUnitClass.split(";");
    } 
    request.setAttribute("unitType", unitType);
  }
  
  private void unitload(String editid, HttpServletRequest request, SenddocumentBaseActionForm senddocumentBaseActionForm) {
    SenddocumentBD bd = new SenddocumentBD();
    SendDocumentUnitPO po = bd.loadSendUnitPO(editid);
    if (po != null) {
      senddocumentBaseActionForm.setUnitType((po.getUnitType() == null) ? "" : po.getUnitType());
      senddocumentBaseActionForm.setUnitWholeName((po.getUnitWholeName() == null) ? "" : po.getUnitWholeName());
      senddocumentBaseActionForm.setUnitShortName((po.getUnitShortName() == null) ? "" : po.getUnitShortName());
    } 
    setUnitType(request);
    request.setAttribute("unitId", editid);
    request.setAttribute("editType", request.getParameter("editType"));
  }
  
  private void chooseSendTopical(HttpServletRequest request) {
    String whereSql;
    HttpSession httpSession = request.getSession(true);
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    int pageSize = 10;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String fromwhere = 
      "com.js.doc.doc.po.SenddocumentTopicalPO po ";
    if (SystemCommon.getMultiDepart() == 1) {
      whereSql = " po.corpId=" + httpSession.getAttribute("corpId").toString();
    } else {
      whereSql = " 1=1";
    } 
    try {
      if (request.getParameter("queryType") != null && request.getParameter("queryType").equals("1")) {
        whereSql = String.valueOf(whereSql) + " and ( po.tableType= 1)";
        request.setAttribute("tableType", "1");
      } else {
        if (request.getParameter("tableType") != null && !request.getParameter("tableType").toString().trim().equals("")) {
          whereSql = String.valueOf(whereSql) + " and ( po.tableType= " + request.getParameter("tableType") + ")";
          request.setAttribute("tableType", request.getParameter("tableType"));
        } else {
          whereSql = String.valueOf(whereSql) + " and ( po.tableType= 1)";
          request.setAttribute("tableType", "1");
        } 
        if (request.getParameter("areaType") != null && !request.getParameter("areaType").toString().trim().equals("")) {
          whereSql = String.valueOf(whereSql) + " and ( po.areaType like '%" + request.getParameter("areaType") + "%' )";
          request.setAttribute("areaType", request.getParameter("areaType"));
        } 
        if (request.getParameter("sortTopical") != null && !request.getParameter("sortTopical").toString().trim().equals("")) {
          whereSql = String.valueOf(whereSql) + " and ( po.sortTopical like '%" + request.getParameter("sortTopical") + "%' )";
          request.setAttribute("sortTopical", request.getParameter("sortTopical"));
        } 
      } 
      Page page = new Page("po.id, po.tableType,po.areaType,po.sortTopical,po.attributeTopical", 
          fromwhere, 
          " where " + whereSql + 
          "  order by po.id desc ");
      page.setPageSize(10000);
      page.setcurrentPage(1);
      List list = page.getResultList();
      if (list != null && 
        list.size() == 0 && offset >= 15) {
        offset -= 15;
        currentPage = offset / pageSize + 1;
        page.setPageSize(pageSize);
        page.setcurrentPage(currentPage);
        list = page.getResultList();
        request.setAttribute("new.offset", (new StringBuilder(String.valueOf(offset))).toString());
        request.setAttribute("pager.realCurrent", (new StringBuilder(String.valueOf(currentPage))).toString());
      } 
      String recordCount = String.valueOf(page.getRecordCount());
      page.setcurrentPage(currentPage);
      request.setAttribute("mylist", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "action,queryItem,queryBeginDate,queryStatus,queryEndDate,queryNumber,querySecret,queryTransPersonName,queryTitle,queryOrg,redHeadId");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private void getlist(HttpServletRequest request) {
    SenddocumentBD bd = new SenddocumentBD();
    HttpSession httpSession = request.getSession(true);
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : 
      httpSession.getAttribute("domainId").toString();
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    String fileName = "";
    if (request.getParameter("tempFileName") != null && !request.getParameter("tempFileName").toString().equals(""))
      fileName = request.getParameter("tempFileName").toString(); 
    int currentPage = offset / pageSize + 1;
    try {
      bd.setVolume(pageSize);
      List list = bd.getCanChooseTemplateList("", fileName, new Integer(currentPage), new Integer(pageSize), 
          domainId);
      int recordCount = bd.getRecordCount();
      if (offset > recordCount) {
        offset = (recordCount - pageSize) / pageSize;
        currentPage = offset + 1;
        offset *= pageSize;
        list = null;
        list = bd.getCanChooseTemplateList("", fileName, new Integer(currentPage), new Integer(pageSize), domainId);
        recordCount = bd.getRecordCount();
        request.setAttribute("pager.offset", 
            String.valueOf(offset));
        request.setAttribute("pager.realCurrent", 
            String.valueOf(currentPage));
      } 
      request.setAttribute("myList", list);
      request.setAttribute("recordCount", 
          String.valueOf(recordCount));
      request.setAttribute("maxPageItems", 
          String.valueOf(pageSize));
      request.setAttribute("pageParameters", "action,tempFileName");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
