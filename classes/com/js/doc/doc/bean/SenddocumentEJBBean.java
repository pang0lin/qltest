package com.js.doc.doc.bean;

import DBstep.iDBManager2000;
import com.js.doc.doc.po.BaseInfoPO;
import com.js.doc.doc.po.SendDocumentNumPO;
import com.js.doc.doc.po.SendDocumentSeqPO;
import com.js.doc.doc.po.SendDocumentUnitPO;
import com.js.doc.doc.po.SendDocumentWordPO;
import com.js.doc.doc.po.SenddocumentTopicalPO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class SenddocumentEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Object[] getSenddocumentBaseInfo(String redHeadId) throws Exception {
    Object[] result = (Object[])null;
    begin();
    try {
      Iterator<Object[]> iter = this.session.iterate("select po.id,po.contentLevel,po.fileType, po.keepSecretLevel,po.secretLevel,  po.transactLevel,po.unitWord,po.baseUnitClass,po.topicalAreaClass,po.baseSorttopical,po.baseQueryLevel,po.openProperty,po.sendDropDownSelect1,po.sendDropDownSelect2 from com.js.doc.doc.po.BaseInfoPO  po");
      if (iter.hasNext())
        result = iter.next(); 
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public Long saveBaseInfo(BaseInfoPO po) throws Exception {
    begin();
    Long result = null;
    try {
      result = (Long)this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      result = Long.valueOf("-2");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public String updateBaseInfo(BaseInfoPO po) throws Exception {
    begin();
    String result = "0";
    try {
      this.session.update(po);
      this.session.flush();
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      result = "1";
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public Long saveWordInfo(SendDocumentWordPO po) throws Exception {
    begin();
    Long result = null;
    try {
      boolean temp = true;
      if (po.getWordName() != null && !po.getWordName().equals("")) {
        Iterator iter = this.session.iterate("select po.id from com.js.doc.doc.po.SendDocumentWordPO po where po.wordName= '" + po.getWordName() + "' ");
        if (iter.hasNext()) {
          result = Long.valueOf("-1");
          temp = false;
        } 
      } 
      if (temp) {
        result = (Long)this.session.save(po);
        this.session.flush();
      } 
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      result = Long.valueOf("-2");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public SendDocumentWordPO loadSendDocumentWordPO(String editId) throws Exception {
    begin();
    SendDocumentWordPO po = new SendDocumentWordPO();
    try {
      po = (SendDocumentWordPO)this.session.load(SendDocumentWordPO.class, 
          Long.valueOf(editId));
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return po;
  }
  
  public String updateWordPO(String stringId, SendDocumentWordPO po) throws Exception {
    begin();
    String result = "0";
    try {
      boolean temp = true;
      if (po.getWordName() != null && !po.getWordName().equals("")) {
        Iterator iter = this.session.iterate("select po.id from com.js.doc.doc.po.SendDocumentWordPO po where po.wordName= '" + po.getWordName() + "' and po.id<>" + stringId);
        if (iter.hasNext()) {
          result = "-1";
          temp = false;
        } 
      } 
      if (temp) {
        SendDocumentWordPO oldPO = (SendDocumentWordPO)this.session.load(SendDocumentWordPO.class, Long.valueOf(stringId));
        oldPO.setProcessId(po.getProcessId());
        oldPO.setProcessName(po.getProcessName());
        oldPO.setReceiveGroup(po.getReceiveGroup());
        oldPO.setReceiveOrg(po.getReceiveOrg());
        oldPO.setReceiveScopeId(po.getReceiveScopeId());
        oldPO.setReceiveScopeName(po.getReceiveScopeName());
        oldPO.setReceiveUser(po.getReceiveUser());
        oldPO.setRedHeadName(po.getRedHeadName());
        oldPO.setRedHeadSaveName(po.getRedHeadSaveName());
        oldPO.setSendDocumentNumId(po.getSendDocumentNumId());
        oldPO.setSendDocumentSeqId(po.getSendDocumentSeqId());
        oldPO.setTemplateId(po.getTemplateId());
        oldPO.setTemplateName(po.getTemplateName());
        oldPO.setUserRange(po.getUserRange());
        oldPO.setUserRangeId(po.getUserRangeId());
        oldPO.setWordName(po.getWordName());
        this.session.flush();
      } 
    } catch (Exception e) {
      result = "-2";
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public String deleteWordPO(String sendFileId) throws Exception {
    begin();
    String result = "0";
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.execute(
          "DELETE from JSDB.doc_senddocumentword WHERE id in (" + 
          sendFileId + ")");
      stmt.close();
      conn.close();
    } catch (Exception e) {
      result = "-1";
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public List getNoNumWord(String corpId) throws Exception {
    List result = new ArrayList();
    begin();
    try {
      if (SystemCommon.getMultiDepart() == 1) {
        result = this.session.createQuery("select po.id,po.wordName,po.sendDocumentNumId,po.processId,po.templateId,po.userRange,po.userRangeId from com.js.doc.doc.po.SendDocumentWordPO po where (po.sendDocumentNumId= -1 or po.sendDocumentNumId is null) and po.corpId=" + corpId).list();
      } else {
        result = this.session.createQuery("select po.id,po.wordName,po.sendDocumentNumId,po.processId,po.templateId,po.userRange,po.userRangeId from com.js.doc.doc.po.SendDocumentWordPO po where po.sendDocumentNumId= -1 or po.sendDocumentNumId is null").list();
      } 
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public String setWordPoNum(String wordId, String numId) throws Exception {
    String result = "0";
    begin();
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.execute(
          "update  JSDB.doc_senddocumentword set sendDocumentNumId= " + 
          numId + " where id in (" + wordId + ")");
      stmt.close();
      conn.close();
    } catch (Exception e) {
      result = "-1";
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public Long saveNunInfo(SendDocumentNumPO po) throws Exception {
    begin();
    Long result = null;
    try {
      boolean temp = true;
      if (po.getNumName() != null && !po.getNumName().equals("")) {
        Iterator iter = this.session.iterate("select po.id from com.js.doc.doc.po.SendDocumentNumPO po where po.numName= '" + po.getNumName() + "'");
        if (iter.hasNext()) {
          result = Long.valueOf("-1");
          temp = false;
        } 
      } 
      if (temp) {
        result = (Long)this.session.save(po);
        this.session.flush();
      } 
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      result = Long.valueOf("-2");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public SendDocumentNumPO loadSendDocumentNumPO(String editId) throws Exception {
    begin();
    SendDocumentNumPO po = new SendDocumentNumPO();
    try {
      po = (SendDocumentNumPO)this.session.load(SendDocumentNumPO.class, 
          Long.valueOf(editId));
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return po;
  }
  
  public List getwordBynumId(String id) throws Exception {
    List result = new ArrayList();
    begin();
    try {
      result = this.session.createQuery("select po.id,po.wordName,po.sendDocumentNumId,po.processId,po.templateId,po.userRange,po.userRangeId from com.js.doc.doc.po.SendDocumentWordPO po where po.sendDocumentNumId=" + 
          id).list();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public String removeWordPoNum(String fileWordIds) throws Exception {
    String result = "0";
    begin();
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.execute(
          "update  JSDB.doc_senddocumentword set sendDocumentNumId= -1 where id in (" + 
          fileWordIds + ")");
      stmt.close();
      conn.close();
    } catch (Exception e) {
      result = "-1";
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public String updateNumPO(String sendFileId, SendDocumentNumPO po) throws Exception {
    begin();
    String result = "0";
    try {
      boolean temp = true;
      if (po.getNumName() != null && !po.getNumName().equals("")) {
        Iterator iter = this.session.iterate("select po.id from com.js.doc.doc.po.SendDocumentNumPO po where po.numName= '" + po.getNumName() + "' and po.id<>" + sendFileId);
        if (iter.hasNext()) {
          result = "-1";
          temp = false;
        } 
      } 
      if (temp) {
        po.setId(new Long(sendFileId));
        this.session.update(po);
        this.session.flush();
      } 
    } catch (Exception e) {
      result = "-1";
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public String deleteNumPO(String deleteIds) throws Exception {
    begin();
    String result = "0";
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.execute("update  JSDB.doc_senddocumentword set sendDocumentNumId= -1 where sendDocumentNumId in (" + 
          deleteIds + ")");
      stmt.execute(
          "DELETE from JSDB.doc_senddocumentNum  WHERE id in (" + 
          deleteIds + ")");
      stmt.close();
      conn.close();
    } catch (Exception e) {
      result = "-1";
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public Long SaveSeqPO(SendDocumentSeqPO po) throws Exception {
    begin();
    Long result = null;
    try {
      result = (Long)this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      System.out.println("-------------------------------------------");
      e.printStackTrace();
      System.out.println("--------------------------------------------");
      result = Long.valueOf("-2");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public String setWordPoSeq(String wordId, String seqId) throws Exception {
    String result = "0";
    begin();
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.execute(
          "update  JSDB.doc_senddocumentword set sendDocumentSeqId= " + 
          seqId + " where id in (" + wordId + ")");
      stmt.close();
      conn.close();
    } catch (Exception e) {
      result = "-1";
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public String updateSeqPO(String seqId, SendDocumentSeqPO po) throws Exception {
    begin();
    String result = "0";
    try {
      po.setId(new Long(seqId));
      this.session.update(po);
      this.session.flush();
    } catch (Exception e) {
      result = "-1";
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public String removeWordPoSeq(String fileWordIds) throws Exception {
    String result = "0";
    begin();
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.execute(
          "update  JSDB.doc_senddocumentword set sendDocumentSeqId= -1 where id in (" + 
          fileWordIds + ")");
      stmt.close();
      conn.close();
    } catch (Exception e) {
      result = "-1";
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public SendDocumentSeqPO loadSendDocumentSeqPO(String editId) throws Exception {
    begin();
    SendDocumentSeqPO po = new SendDocumentSeqPO();
    try {
      po = (SendDocumentSeqPO)this.session.load(SendDocumentSeqPO.class, 
          Long.valueOf(editId));
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return po;
  }
  
  public List getwordBySeqId(String id) throws Exception {
    List result = new ArrayList();
    begin();
    try {
      result = this.session.createQuery("select po.id,po.wordName,po.sendDocumentNumId,po.processId,po.templateId,po.userRange,po.userRangeId from com.js.doc.doc.po.SendDocumentWordPO po where po.sendDocumentSeqId=" + 
          id).list();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public List getNoSeqWord(String corpId) throws Exception {
    List result = new ArrayList();
    begin();
    try {
      if (SystemCommon.getMultiDepart() == 1) {
        result = this.session.createQuery("select po.id,po.wordName,po.sendDocumentNumId,po.processId,po.templateId,po.userRange,po.userRangeId from com.js.doc.doc.po.SendDocumentWordPO po where (po.sendDocumentSeqId= -1 or po.sendDocumentSeqId is null) and po.corpId=" + corpId).list();
      } else {
        result = this.session.createQuery("select po.id,po.wordName,po.sendDocumentNumId,po.processId,po.templateId,po.userRange,po.userRangeId from com.js.doc.doc.po.SendDocumentWordPO po where po.sendDocumentSeqId= -1 or po.sendDocumentSeqId is null").list();
      } 
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public String deleteSeqPO(String deleteIds) throws Exception {
    begin();
    String result = "0";
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.execute("update  JSDB.doc_senddocumentword set sendDocumentSeqId= -1 where sendDocumentSeqId in (" + 
          deleteIds + ")");
      stmt.execute(
          "DELETE from JSDB.doc_senddocumentseq  WHERE id in (" + 
          deleteIds + ")");
      stmt.close();
      conn.close();
    } catch (Exception e) {
      result = "-1";
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public List getAllSendDocumentNumPO(String type) throws Exception {
    List result = new ArrayList();
    begin();
    try {
      result = this.session.createQuery("select po.id,po.numName  from com.js.doc.doc.po.SendDocumentNumPO po ")
        .list();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public List getAllSendDocumentSeqPO(String type) throws Exception {
    List result = new ArrayList();
    begin();
    try {
      result = this.session.createQuery("select po.id,po.seqName from com.js.doc.doc.po.SendDocumentSeqPO po")
        .list();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public List getAllUserTemplateList(String type) throws Exception {
    List result = new ArrayList();
    begin();
    try {
      result = this.session.createQuery("select po.id,po.templateId from com.js.doc.doc.po.SendDocumentWordPO po where po.templateId <> -1 and po.templateId is not null ")
        .list();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public List getTemplateList(String domainId, String useTempIds) throws Exception {
    List<Object[]> list = new ArrayList();
    iDBManager2000 DbaObj = new iDBManager2000();
    try {
      DbaObj.OpenConnection();
      String sql = "Select RecordID,FileName,FileType,Descript From Template_File where DOMAIN_ID=" + domainId + " order by TemplateID desc";
      String sql2 = "Select RecordID,FileName,FileType,Descript From Template_File  order by TemplateID desc";
      if (useTempIds != null && !useTempIds.equals("")) {
        sql = "Select RecordID,FileName,FileType,Descript From Template_File where DOMAIN_ID=" + domainId + " and RecordID not in (" + useTempIds + ") order by TemplateID desc";
        sql2 = "Select RecordID,FileName,FileType,Descript From Template_File  where  RecordID not in (" + useTempIds + ") order by TemplateID desc";
      } 
      ResultSet result = DbaObj.ExecuteQuery(sql);
      while (result.next()) {
        String mRecordID = result.getString("RecordID");
        String mFileName = result.getString("FileName");
        String mFileType = result.getString("FileType");
        String mDescript = result.getString("Descript");
        Object[] dd = { mRecordID, mFileName, mFileType, mDescript };
        list.add(dd);
      } 
      result.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DbaObj.CloseConnection();
    } 
    return list;
  }
  
  public List getTemplateById(String domainId, String Id) throws Exception {
    List<Object[]> list = new ArrayList();
    iDBManager2000 DbaObj = new iDBManager2000();
    try {
      DbaObj.OpenConnection();
      ResultSet result = null;
      if (Id.endsWith(";"))
        Id = Id.substring(0, Id.length() - 1); 
      Id = Id.replace(";", ",");
      if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
        String[] split = Id.split(",");
        for (int i = 0; i < split.length; i++) {
          String sql = "Select RecordID,FileName,FileType,Descript From Template_File where DOMAIN_ID=" + domainId + " and RecordID = " + split[i];
          result = DbaObj.ExecuteQuery(sql);
          while (result.next()) {
            String mRecordID = result.getString("RecordID");
            String mFileName = result.getString("FileName");
            String mFileType = result.getString("FileType");
            String mDescript = result.getString("Descript");
            Object[] dd = { mRecordID, mFileName, mFileType, mDescript };
            list.add(dd);
          } 
        } 
      } else {
        String sql = "Select RecordID,FileName,FileType,Descript From Template_File where DOMAIN_ID=" + domainId + " and RecordID in (" + Id + ") order by TemplateID desc";
        result = DbaObj.ExecuteQuery(sql);
        while (result.next()) {
          String mRecordID = result.getString("RecordID");
          String mFileName = result.getString("FileName");
          String mFileType = result.getString("FileType");
          String mDescript = result.getString("Descript");
          Object[] dd = { mRecordID, mFileName, mFileType, mDescript };
          list.add(dd);
        } 
      } 
      result.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DbaObj.CloseConnection();
    } 
    return list;
  }
  
  public String getNunfigByNumId(String numId) throws Exception {
    String retString = "1";
    begin();
    try {
      Iterator<String> itr = this.session.createQuery("select po.numfig  from com.js.doc.doc.po.SendDocumentNumPO po where po.id= " + 
          numId).iterate();
      if (itr.hasNext()) {
        String max = itr.next();
        if (!"".equals(max) && !"null".equals(max)) {
          retString = String.valueOf(Integer.parseInt(max) + 1);
        } else {
          retString = "1";
        } 
      } 
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return retString;
  }
  
  public String setNumfigById(String numId, String numfig) throws Exception {
    begin();
    String result = "0";
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      Iterator<String> iter = this.session.iterate("select po.numfig  from com.js.doc.doc.po.SendDocumentNumPO po where po.id= " + 
          numId);
      String max = "0";
      if (iter.hasNext())
        max = iter.next(); 
      if (Integer.parseInt(max) < Integer.parseInt(numfig))
        stmt.execute("update JSDB.doc_senddocumentNum set numfig= " + numfig + " where id =" + numId); 
      stmt.close();
      conn.close();
    } catch (Exception e) {
      result = "-1";
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public String getSeqfigBySeqID(String seqId) throws Exception {
    String retString = "1";
    begin();
    try {
      Iterator<String> itr = this.session.createQuery("select po.numfig  from com.js.doc.doc.po.SendDocumentSeqPO po where po.id= " + 
          seqId).iterate();
      if (itr.hasNext()) {
        String max = itr.next();
        if (!"".equals(max) && !"null".equals(max)) {
          retString = String.valueOf(Integer.parseInt(max) + 1);
        } else {
          retString = "1";
        } 
      } 
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return retString;
  }
  
  public String setSeqfigBySeqId(String seqId, String seqfig) throws Exception {
    begin();
    String result = "0";
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.execute("update JSDB.doc_senddocumentseq set seqfig= " + seqfig + " where id =" + seqId);
      stmt.close();
      conn.close();
    } catch (Exception e) {
      result = "-1";
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public List getAllHaveTmep_Word(String type) throws Exception {
    List result = new ArrayList();
    begin();
    try {
      result = this.session.createQuery("select po.id,po.wordName,po.templateId,po.sendDocumentNumId,po.processId,po.userRange,po.userRangeId from com.js.doc.doc.po.SendDocumentWordPO po where po.templateId <> -1 and  po.templateId is not null")
        .list();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public Long saveTopicalInfo(SenddocumentTopicalPO po) throws Exception {
    begin();
    Long result = null;
    try {
      boolean temp = true;
      if (po.getAttributeTopical() != null && !po.getAttributeTopical().equals("")) {
        Iterator iter = this.session.iterate("select po.id from com.js.doc.doc.po.SenddocumentTopicalPO po where po.attributeTopical= '" + po.getAttributeTopical() + "' and po.areaType='" + po.getAreaType() + "' and po.sortTopical='" + po.getSortTopical() + "'");
        if (iter.hasNext()) {
          result = Long.valueOf("-1");
          temp = false;
        } 
      } 
      if (temp) {
        result = (Long)this.session.save(po);
        this.session.flush();
      } 
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      result = Long.valueOf("-2");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public String updateTopicalPO(SenddocumentTopicalPO po) throws Exception {
    begin();
    String result = "0";
    try {
      boolean temp = true;
      if (po.getAttributeTopical() != null && !po.getAttributeTopical().equals("")) {
        Iterator iter = this.session.iterate("select po.id from com.js.doc.doc.po.SenddocumentTopicalPO po where po.attributeTopical= '" + po.getAttributeTopical() + "' and po.areaType='" + po.getAreaType() + "' and po.sortTopical='" + po.getSortTopical() + "' and po.id<>" + po.getId());
        if (iter.hasNext()) {
          result = "-1";
          temp = false;
        } 
      } 
      if (temp) {
        this.session.update(po);
        this.session.flush();
      } 
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      result = "-2";
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public SenddocumentTopicalPO loadSendTopicalPO(String editId) throws Exception {
    begin();
    SenddocumentTopicalPO po = new SenddocumentTopicalPO();
    try {
      po = (SenddocumentTopicalPO)this.session.load(SenddocumentTopicalPO.class, 
          Long.valueOf(editId));
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return po;
  }
  
  public String deleteTopicalPO(String deleteIds) throws Exception {
    begin();
    String result = "0";
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.execute(
          "DELETE from JSDB.doc_senddocumenttopical WHERE id in (" + 
          deleteIds + ")");
      stmt.close();
      conn.close();
    } catch (Exception e) {
      result = "-1";
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public Long saveUnitInfo(SendDocumentUnitPO po) throws Exception {
    begin();
    Long result = null;
    try {
      result = (Long)this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      result = Long.valueOf("-2");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public String updateUnitPO(SendDocumentUnitPO po) throws Exception {
    begin();
    String result = "0";
    try {
      SendDocumentUnitPO modiPO = (SendDocumentUnitPO)this.session.load(SendDocumentUnitPO.class, po.getId());
      modiPO.setUnitShortName(po.getUnitShortName());
      modiPO.setUnitType(po.getUnitType());
      modiPO.setUnitWholeName(po.getUnitWholeName());
      this.session.flush();
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      result = "1";
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public SendDocumentUnitPO loadSendUnitPO(String editId) throws Exception {
    begin();
    SendDocumentUnitPO po = new SendDocumentUnitPO();
    try {
      po = (SendDocumentUnitPO)this.session.load(SendDocumentUnitPO.class, 
          Long.valueOf(editId));
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return po;
  }
  
  public String deleteUnitPO(String deleteIds) throws Exception {
    begin();
    String result = "0";
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.execute(
          "DELETE from JSDB.DOC_documentUnit WHERE id in (" + deleteIds + ")");
      stmt.close();
      conn.close();
    } catch (Exception e) {
      result = "-1";
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public List getAllSendDocumentUnitPO(String editId) throws Exception {
    List result = new ArrayList();
    begin();
    try {
      result = this.session.createQuery("select po.id,po.unitType,po.unitWholeName,po.unitShortName  from com.js.doc.doc.po.SendDocumentUnitPO po ")
        .list();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public List getAllHaveTemp_Num_Seq_Word(String type) throws Exception {
    List result = new ArrayList();
    begin();
    try {
      result = this.session.createQuery("select po.id,po.wordName,po.templateId,po.sendDocumentNumId,po.processId,po.userRange,po.userRangeId from com.js.doc.doc.po.SendDocumentWordPO po where  po.templateId is not null and po.templateId <> -1 and po.sendDocumentNumId is not null and  po.sendDocumentNumId <> -1  and po.sendDocumentSeqId is not null and po.sendDocumentSeqId <> -1 ")
        .list();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public List getWordByProceeIdAnd4Type(String processId, String type, String whereSql) throws Exception {
    List result = new ArrayList();
    begin();
    try {
      String sql = "";
      if (whereSql == null || whereSql.equals(""))
        whereSql = " and 2>1"; 
      if (type.equals("1"))
        sql = "select po.id,po.wordName,po.templateId,po.sendDocumentNumId,po.sendDocumentSeqId,po.processId,po.userRange,po.userRangeId  from com.js.doc.doc.po.SendDocumentWordPO po where po.processId= " + 
          
          processId + whereSql; 
      if (type.equals("2"))
        sql = "select po.id,po.wordName,po.templateId,po.sendDocumentNumId,po.sendDocumentSeqId,po.processId,po.userRange,po.userRangeId from com.js.doc.doc.po.SendDocumentWordPO po where po.templateId is not null and po.templateId <> -1  and po.processId= " + 

          
          processId + whereSql; 
      if (type.equals("3"))
        sql = "select po.id,po.wordName,po.templateId,po.sendDocumentNumId,po.sendDocumentSeqId,po.processId,po.userRange,po.userRangeId, numpo.numName,numpo.bitNum,numpo.keyValue,numpo.numMode,numpo.numfig,numpo.numIsYear,numpo.oldYear,  numpo.initValue ,numpo.numIsWord  from com.js.doc.doc.po.SendDocumentWordPO po,com.js.doc.doc.po.SendDocumentNumPO numpo where po.sendDocumentNumId is not null and  po.sendDocumentNumId <> -1 and po.templateId is not null and po.templateId <> '-1' and  po.sendDocumentNumId=numpo.id  and processId= " + 





          
          processId + whereSql; 
      if (type.equals("4"))
        sql = "select po.id,po.wordName,po.templateId,po.sendDocumentNumId,po.sendDocumentSeqId,po.processId,po.userRange,po.userRangeId,numpo.numName,numpo.bitNum,numpo.keyValue, numpo.numMode,numpo.numfig, numpo.numIsYear,numpo.oldYear,   numpo.initValue, seqpo.seqName ,seqpo.seqBitNum,seqpo.seqMode,seqpo.seqfig , seqpo.seqIsYear ,numpo.numIsWord,seqpo.seqIsWord   from com.js.doc.doc.po.SendDocumentWordPO  po, com.js.doc.doc.po.SendDocumentNumPO numpo ,com.js.doc.doc.po.SendDocumentSeqPO  seqpo  where po.sendDocumentNumId is not null and  po.sendDocumentNumId <> -1 and po.templateId is not null and po.templateId <> -1 and po.sendDocumentSeqId is not null and po.sendDocumentSeqId <> -1 and  po.sendDocumentNumId=numpo.id  and seqpo.id=po.sendDocumentSeqId  and processId= " + 









          
          processId + whereSql; 
      result = this.session.createQuery(sql).list();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public List getSendNumByNumClass(String numClass) throws Exception {
    List result = new ArrayList();
    begin();
    try {
      result = this.session.find("from com.js.doc.doc.po.SendDocumentNumPO po where po.numType= '" + numClass + "'");
      this.session.flush();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public String updateNumByPara(String[] para) throws Exception {
    begin();
    String result = "0";
    String numId = para[0];
    int numfig = Integer.parseInt(para[1]);
    int nowYear = (new Date()).getYear() + 1900;
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.execute("update JSDB.doc_senddocumentNum set oldyear= " + nowYear + ", numfig= " + numfig + " where id=" + numId);
      stmt.close();
      conn.close();
    } catch (Exception e) {
      result = "-1";
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public List getCommentByCommFiledList(String moduleId, String recordId, String commField) throws Exception {
    List<Object[]> list = new ArrayList();
    String comment = "", empName = "", dealwithDate = "";
    String isStandForComm = "", standForUserName = "";
    begin();
    String processId = "";
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(
          "SELECT WF_IMMOFORM_ID FROM JSDB.JSF_IMMOBILITYFORM WHERE WF_MODULE_ID=" + 
          moduleId);
      String tableId = "0";
      if (rs.next())
        tableId = rs.getString(1); 
      rs.close();
      rs = stmt.executeQuery("SELECT WORKCURSTEP,WF_SUBMITEMPLOYEE_ID,WORKSUBMITPERSON,WF_WORK_ID,WORKTYPE,WORKACTIVITY,WORKFILETYPE,WORKSUBMITTIME,WORKPROCESS_ID,WORKSTEPCOUNT,ISSTANDFORWORK,STANDFORUSERID,STANDFORUSERNAME FROM JSDB.JSF_WORK WHERE WORKTABLE_ID=" + 
          tableId + " AND WORKRECORD_ID=" + recordId + 
          " AND (WORKSTATUS=1 OR WORKSTATUS=100)");
      if (rs.next())
        processId = rs.getString(9); 
      rs.close();
      if (processId != null && !processId.equals("")) {
        rs = stmt.executeQuery("SELECT B.DEALWITHEMPLOYEECOMMENT,C.EMPNAME,B.DEALWITHTIME,B.ISSTANDFORCOMM,B.STANDFORUSERNAME FROM JSF_DEALWITH A,JSF_DEALWITHCOMMENT B,ORG_EMPLOYEE C WHERE A.WF_DEALWITH_ID=B.WF_DEALWITH_ID AND B.DEALWITHEMPLOYEE_ID=C.EMP_ID AND A.DATABASETABLE_ID=" + 
            
            tableId + 
            " AND A.DATABASERECORD_ID=" + recordId + 
            " AND B.COMMENTFIELD='" + commField + "' " + 
            "AND A.ACTIVITY_ID IN (SELECT WF_ACTIVITY_ID FROM JSF_P_ACTIVITY WHERE WF_WORKFLOWPROCESS_ID=" + 
            processId + ")");
        while (rs.next()) {
          comment = rs.getString(1);
          empName = rs.getString(2);
          dealwithDate = rs.getString(3);
          isStandForComm = rs.getString(4);
          standForUserName = rs.getString(5);
          Object[] obj = { comment, empName, dealwithDate, 
              isStandForComm, standForUserName };
          list.add(obj);
        } 
        rs.close();
        stmt.close();
        conn.close();
      } 
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public String getEmpIdsByTotal(String userIds) throws Exception {
    String empIds = "";
    begin();
    try {
      char flagCode = '0';
      int nextPos = 0;
      String str = "";
      String userId = "", orgId = "", groupId = "";
      for (int i = 0; i < userIds.length(); i++) {
        flagCode = userIds.charAt(i);
        nextPos = userIds.indexOf(flagCode, i + 1);
        str = userIds.substring(i, nextPos + 1);
        if (flagCode == '$') {
          userId = String.valueOf(userId) + str;
        } else if (flagCode == '*') {
          orgId = String.valueOf(orgId) + str;
        } else {
          groupId = String.valueOf(groupId) + str;
        } 
        i = nextPos;
      } 
      if (!userId.equals("")) {
        if (userId.indexOf("$") >= 0)
          userId = userId.substring(1, userId.length() - 1); 
        if (userId.indexOf("$") >= 0) {
          userId = userId.replace('$', ',');
          userId = userId.replaceAll(",,", ",");
        } 
      } else {
        userId = "-1";
      } 
      if (!orgId.equals("")) {
        if (orgId.indexOf("*") >= 0)
          orgId = orgId.substring(1, orgId.length() - 1); 
        if (orgId.indexOf("*") >= 0) {
          orgId = orgId.replace('*', ',');
          orgId = orgId.replaceAll(",,", ",");
        } 
        if (orgId.indexOf(",") == -1) {
          Iterator<E> itr = this.session.iterate("select po.orgId from OrganizationVO po where po.orgIdString like '%$" + orgId + "$%'");
          while (itr.hasNext())
            orgId = String.valueOf(orgId) + "," + itr.next().toString(); 
        } else if (orgId.indexOf(",") >= 0) {
          String[] orgIdArr = orgId.split(",");
          for (int j = 0; j < orgIdArr.length; j++) {
            Iterator<E> itr = this.session.iterate("select po.orgId from OrganizationVO po where po.orgIdString like '%$" + orgIdArr[j] + "$%'");
            while (itr.hasNext())
              orgId = String.valueOf(orgId) + "," + itr.next().toString(); 
          } 
        } 
      } else {
        orgId = "-1";
      } 
      if (!groupId.equals("")) {
        if (groupId.indexOf("@") >= 0)
          groupId = groupId.substring(1, groupId.length() - 1); 
        if (groupId.indexOf("@") >= 0) {
          groupId = groupId.replace('@', ',');
          groupId = groupId.replaceAll(",,", ",");
        } 
      } else {
        groupId = "-1";
      } 
      DataSourceBase dsb = new DataSourceBase();
      dsb.begin();
      Object[] obj = (Object[])null;
      String sql = null;
      Iterator<Object[]> iter = this.session.iterate("select distinct emp.empId,emp.empName,org.orgNameString from com.js.system.vo.usermanager.EmployeeVO emp join emp.organizations org left join emp.groups grp where emp.empId in (" + userId + ") or org.orgId in (" + orgId + ") or grp.groupId in (" + groupId + ")");
      while (iter.hasNext()) {
        obj = iter.next();
        empIds = String.valueOf(empIds) + "$" + obj[0] + "$";
      } 
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return empIds;
  }
  
  public List getAllFealMenBymoduleId(String moduleId, String recordId) throws Exception {
    begin();
    String tableId = "0";
    List<String[]> list = new ArrayList();
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(
          "SELECT WF_IMMOFORM_ID FROM JSDB.JSF_IMMOBILITYFORM WHERE WF_MODULE_ID=" + 
          moduleId);
      if (rs.next())
        tableId = rs.getString(1); 
      rs.close();
      String sql = "select a.activity_id,a.activityname,emp.empName,b.dealwithtime,org.orgname from jsf_dealwith a ,jsf_dealwithcomment b, org_employee emp ,org_organization_user ou ,org_organization org where a.curstepcount<>1 and  a.wf_dealwith_id=b.wf_dealwith_id and b.dealwithemployee_id = emp.emp_id and a.databasetable_id=" + tableId + " and a.databaserecord_id=" + recordId + " and ou.emp_id=emp.emp_id and ou.org_id=org.org_id  order by a.curstepcount";
      ResultSet rs2 = stmt.executeQuery(sql);
      while (rs2.next()) {
        String[] tmp = { "", "", "", "", "" };
        tmp[0] = rs2.getString(1);
        tmp[1] = rs2.getString(2);
        tmp[2] = rs2.getString(3);
        tmp[3] = rs2.getString(4);
        tmp[4] = rs2.getString(5);
        list.add(tmp);
      } 
      rs2.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public Integer getCanChooseTemplateListCount(String sqlStr, String fileName, String domainId) throws Exception {
    Integer dd = new Integer("0");
    iDBManager2000 DbaObj = new iDBManager2000();
    try {
      DbaObj.OpenConnection();
      String sql = "Select count(*) From Template_File t1 where  DOMAIN_ID=" + domainId;
      if (fileName != null && !fileName.equals(""))
        sql = "Select count(*) From Template_File t1 where t1.FileName like '%" + fileName + "%' and  DOMAIN_ID=" + domainId; 
      ResultSet result = DbaObj.ExecuteQuery(sql);
      while (result.next()) {
        int dd1 = result.getInt(1);
        dd = new Integer(dd1);
      } 
      result.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DbaObj.CloseConnection();
    } 
    return dd;
  }
  
  public List getCanChooseTemplateList(String sqlStr, String fileName, Integer currentPage, Integer volume, String domainId) throws Exception {
    List<Object[]> list = new ArrayList();
    iDBManager2000 DbaObj = new iDBManager2000();
    try {
      DbaObj.OpenConnection();
      int i = (currentPage.intValue() - 1) * volume.intValue();
      int j = (currentPage.intValue() - 1) * volume.intValue() + volume.intValue();
      String sql = "Select RecordID,FileName,FileType,Descript From Template_File t1 where flag=1 and  DOMAIN_ID=" + domainId + " and  ((SELECT   count(*)  from  Template_File t2   where t2.DOMAIN_ID=" + domainId + " and  t2.TemplateID <t1.TemplateID )>= " + i + " )   AND   ((SELECT   count(*)  from  Template_File t2   where t2.DOMAIN_ID=" + domainId + "   and   t2.TemplateID <t1.TemplateID )   <" + j + " )";
      if (fileName != null && !fileName.equals(""))
        sql = "Select RecordID,FileName,FileType,Descript From Template_File t1 where flag=1 and t1.FileName like '%" + fileName + "%' and  DOMAIN_ID=" + domainId + " and  ((SELECT   count(*)  from  Template_File t2   where t2.DOMAIN_ID=" + domainId + " and t2.FileName like '%" + fileName + "%' and   t2.TemplateID <t1.TemplateID )>= " + i + " )   AND   ((SELECT   count(*)  from  Template_File t2   where t2.DOMAIN_ID=" + domainId + " and  t2.FileName like '%" + fileName + "%'  and   t2.TemplateID <t1.TemplateID )   <" + j + " )"; 
      ResultSet result = DbaObj.ExecuteQuery(sql);
      while (result.next()) {
        String mRecordID = result.getString("RecordID");
        String mFileName = result.getString("FileName");
        String mFileType = result.getString("FileType");
        String mDescript = result.getString("Descript");
        Object[] dd = { mRecordID, mFileName, mFileType, mDescript };
        list.add(dd);
      } 
      result.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DbaObj.CloseConnection();
    } 
    return list;
  }
}
