package com.js.oa.userdb.newCode.service;

import com.js.oa.userdb.newCode.bean.CodeManageDao;
import com.js.oa.userdb.newCode.bean.CodeSetEJBBean;
import com.js.oa.userdb.newCode.po.CodeSetPO;
import com.js.oa.userdb.newCode.po.MaxCodeSetRecord;
import com.js.oa.userdb.newCode.po.NewCodePO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class CodeManageService {
  private static Logger logger = Logger.getLogger(CodeManageService.class
      .getName());
  
  private List<String> sqlOperList = new ArrayList<String>();
  
  public String getCodeNum(String codeId) {
    String codeNumber = checkNoUserCode(codeId);
    if ("".equals(codeNumber))
      return getNewCodeNum(codeId); 
    return codeNumber;
  }
  
  private String checkNoUserCode(String codeId) {
    List<CodeSetPO> codeSetList = null;
    try {
      codeSetList = (new CodeSetEJBBean()).getCodeSetList(Long.valueOf(codeId).longValue());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    if (codeSetList != null && codeSetList.size() > 0) {
      StringBuffer codeNumberFormat = new StringBuffer();
      CodeManageDao cmd = new CodeManageDao();
      for (CodeSetPO cs : codeSetList) {
        if ("s".equals(cs.getCodeSetType())) {
          codeNumberFormat.append(cs.getCodeSetContent());
          continue;
        } 
        if ("n".equals(cs.getCodeSetType())) {
          codeNumberFormat.append("%");
          continue;
        } 
        if ("d".equals(cs.getCodeSetType()))
          codeNumberFormat.append(getDateFormat(cs)); 
      } 
      return cmd.getNoUserCode(Long.valueOf(codeId).longValue(), codeNumberFormat.toString());
    } 
    return "";
  }
  
  private String getNewCodeNum(String codeId) {
    String codeNumber = createCodeNum(Long.valueOf(codeId).longValue());
    if ("".equals(codeNumber))
      return ""; 
    CodeManageDao cmDao = new CodeManageDao();
    int cnt = cmDao.checkCodeExist(Long.valueOf(codeId).longValue(), codeNumber);
    while (cnt > 0) {
      codeNumber = createCodeNum(Long.valueOf(codeId).longValue());
      cnt = cmDao.checkCodeExist(Long.valueOf(codeId).longValue(), codeNumber);
    } 
    String sql = "insert into JSF_NEWCODE_LOG(LOG_CODEID,LOG_CODENUMBER,LOG_ISUSERED) values('" + codeId + "','" + codeNumber + "','0')";
    cmDao.executeSql(sql);
    return codeNumber;
  }
  
  public String checkCodeOrMarkNewCode(String codeId, String codeNumber, String priKey) {
    String result = codeNumber;
    CodeManageDao cmDao = new CodeManageDao();
    int cnt = cmDao.checkCodeExist(Long.valueOf(codeId).longValue(), codeNumber);
    if (cnt > 0) {
      long key = -1L;
      if (priKey != null && !"".equals(priKey) && !"null".equalsIgnoreCase(priKey))
        key = Long.valueOf(priKey).longValue(); 
      int res = cmDao.checkCodeUsered(Long.valueOf(codeId).longValue(), codeNumber, key);
      if (res > 0) {
        codeNumber = getCodeNum(codeId);
        result = codeNumber;
        String sql = "insert into JSF_NEWCODE_LOG(LOG_CODEID,LOG_CODENUMBER,LOG_ISUSERED) values('" + codeId + "','" + codeNumber + "','1')";
        cmDao.executeSql(sql);
      } else {
        String sql = "update JSF_NEWCODE_LOG set LOG_ISUSERED='1' where LOG_CODEID='" + codeId + "' AND LOG_CODENUMBER='" + codeNumber + "'";
        cmDao.executeSql(sql);
      } 
    } else {
      String sql = "insert into JSF_NEWCODE_LOG(LOG_CODEID,LOG_CODENUMBER,LOG_ISUSERED) values('" + codeId + "','" + codeNumber + "','1')";
      cmDao.executeSql(sql);
    } 
    return result;
  }
  
  private String createCodeNum(long codeId) {
    List<CodeSetPO> codeSetList = null;
    try {
      codeSetList = (new CodeSetEJBBean()).getCodeSetList(Long.valueOf(codeId).longValue());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    if (codeSetList != null && codeSetList.size() > 0) {
      StringBuffer codeNumber = new StringBuffer();
      Map<String, String> csMap = new HashMap<String, String>();
      for (CodeSetPO cs : codeSetList) {
        if ("d".equals(cs.getCodeSetType()))
          csMap.put((new StringBuilder(String.valueOf(cs.getCodeSetId()))).toString(), getDateFormatToRecord(cs)); 
      } 
      CodeManageDao cmd = new CodeManageDao();
      Map<String, MaxCodeSetRecord> maxCodeSetMap = cmd.getMaxCodeSetRecord(codeId);
      for (CodeSetPO cs : codeSetList) {
        if ("s".equals(cs.getCodeSetType())) {
          codeNumber.append(cs.getCodeSetContent());
          continue;
        } 
        if ("n".equals(cs.getCodeSetType())) {
          codeNumber.append(getNumber(cs, csMap, maxCodeSetMap));
          continue;
        } 
        if ("d".equals(cs.getCodeSetType()))
          codeNumber.append(getDateFormat(cs)); 
      } 
      for (Map.Entry<String, MaxCodeSetRecord> entry : maxCodeSetMap.entrySet()) {
        MaxCodeSetRecord mcsr = entry.getValue();
        if (mcsr.getIsUsered() == 0)
          this.sqlOperList.add("delete from JSF_NEWCODE_LOG_SUB where sub_codeid='" + mcsr.getCodeId() + "' and sub_codesetid='" + mcsr.getCodeSetId() + "'"); 
      } 
      cmd.maxCodeSetRecordSaveOrUpdate(this.sqlOperList);
      return codeNumber.toString();
    } 
    return "";
  }
  
  private String getNumber(CodeSetPO po, Map<String, String> csMap, Map<String, MaxCodeSetRecord> maxCodeSetMap) {
    StringBuffer result = new StringBuffer();
    if (po.getCodeSetIsRelyDate() == 1) {
      MaxCodeSetRecord record = maxCodeSetMap.get((new StringBuilder(String.valueOf(po.getCodeSetId()))).toString());
      if (record != null) {
        if (record.getDateFormat().equals(csMap.get((new StringBuilder(String.valueOf(po.getCodeSetRelySetId()))).toString()))) {
          int resCnt = record.getNum() + po.getCodeSetStep();
          result.append(resCnt);
          this.sqlOperList.add("update JSF_NEWCODE_LOG_SUB set sub_num='" + resCnt + "' where sub_codeid='" + record.getCodeId() + "' and sub_codesetid='" + record.getCodeSetId() + "' ");
          record.setIsUsered(1);
        } else {
          result.append(po.getCodeSetBegin());
          this.sqlOperList.add("update JSF_NEWCODE_LOG_SUB set sub_num='" + po.getCodeSetBegin() + "',sub_dateformat='" + (String)csMap.get((new StringBuilder(String.valueOf(po.getCodeSetRelySetId()))).toString()) + "' where sub_codeid='" + record.getCodeId() + "' and sub_codesetid='" + record.getCodeSetId() + "' ");
          record.setIsUsered(1);
        } 
      } else {
        result.append(po.getCodeSetBegin());
        this.sqlOperList.add("insert into JSF_NEWCODE_LOG_SUB(sub_codeid,sub_codesetid,sub_dateformat,sub_num)values('" + po.getCodeId() + "','" + po.getCodeSetId() + "','" + (String)csMap.get((new StringBuilder(String.valueOf(po.getCodeSetRelySetId()))).toString()) + "','" + po.getCodeSetBegin() + "') ");
      } 
    } else {
      MaxCodeSetRecord record = maxCodeSetMap.get((new StringBuilder(String.valueOf(po.getCodeSetId()))).toString());
      if (record != null) {
        int resCnt = record.getNum() + po.getCodeSetStep();
        result.append(resCnt);
        this.sqlOperList.add("update JSF_NEWCODE_LOG_SUB set sub_dateformat='',sub_num='" + resCnt + "' where sub_codeid='" + record.getCodeId() + "' and sub_codesetid='" + record.getCodeSetId() + "' ");
        record.setIsUsered(1);
      } else {
        result.append(po.getCodeSetBegin());
        this.sqlOperList.add("insert into JSF_NEWCODE_LOG_SUB(sub_codeid,sub_codesetid,sub_dateformat,sub_num)values('" + po.getCodeId() + "','" + po.getCodeSetId() + "','','" + po.getCodeSetBegin() + "') ");
      } 
    } 
    if (po.getCodeSetIsComplete() == 1)
      if (result.length() < po.getCodeSetCompleteCnt()) {
        int def = po.getCodeSetCompleteCnt() - result.length();
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < def; i++)
          sBuffer.append("0"); 
        sBuffer.append(result);
        return sBuffer.toString();
      }  
    return result.toString();
  }
  
  private String getDateFormat(CodeSetPO po) {
    SimpleDateFormat sdfDateFormat = null;
    String split = po.getCodeSetDateSplit();
    if (split == null)
      split = ""; 
    StringBuffer dateFormat = new StringBuffer();
    if (po.getCodeSetDateYear() == 1)
      dateFormat.append("yyyy").append(split); 
    if (po.getCodeSetDateMonth() == 1)
      dateFormat.append("MM").append(split); 
    if (po.getCodeSetDateDay() == 1)
      dateFormat.append("dd").append(split); 
    if ("".equals(split)) {
      sdfDateFormat = new SimpleDateFormat(dateFormat.toString());
    } else {
      sdfDateFormat = new SimpleDateFormat(dateFormat.substring(0, dateFormat.length() - 1));
    } 
    if (po.getCodeSetDateIsComplete() == 1)
      return "(" + sdfDateFormat.format(new Date()) + ")"; 
    return sdfDateFormat.format(new Date());
  }
  
  private String getDateFormatToRecord(CodeSetPO po) {
    StringBuffer dateFormat = new StringBuffer();
    if (po.getCodeSetDateYear() == 1)
      dateFormat.append("yyyy"); 
    if (po.getCodeSetDateMonth() == 1)
      dateFormat.append("MM"); 
    if (po.getCodeSetDateDay() == 1)
      dateFormat.append("dd"); 
    SimpleDateFormat sdfDateFormat = new SimpleDateFormat(dateFormat.toString());
    return sdfDateFormat.format(new Date());
  }
  
  public List<NewCodePO> getCodeList() {
    return (new CodeManageDao()).getCodeList();
  }
}
