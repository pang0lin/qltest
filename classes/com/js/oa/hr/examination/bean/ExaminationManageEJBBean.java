package com.js.oa.hr.examination.bean;

import com.js.oa.hr.examination.po.ExaminationAnswerItemPO;
import com.js.oa.hr.examination.po.ExaminationAnswerPO;
import com.js.oa.hr.examination.po.ExaminationItemPO;
import com.js.oa.hr.examination.po.ExaminationManagePO;
import com.js.oa.hr.examination.po.ExaminationPersonnelPO;
import com.js.oa.hr.examination.po.ExaminationStockPO;
import com.js.oa.userdb.util.DbOpt;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class ExaminationManageEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Long save(ExaminationManagePO po) throws Exception {
    Long ret = null;
    try {
      begin();
      ret = (Long)this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public ExaminationManagePO load(Long id) throws Exception {
    ExaminationManagePO po = null;
    try {
      begin();
      po = (ExaminationManagePO)this.session.load(ExaminationManagePO.class, 
          id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return po;
  }
  
  public Boolean update(ExaminationManagePO po, Long id) throws Exception {
    Boolean ret = Boolean.FALSE;
    try {
      begin();
      ExaminationManagePO oldpo = (ExaminationManagePO)this.session.load(
          ExaminationManagePO.class, id);
      oldpo.setExamName(po.getExamName());
      oldpo.setScopeRange(po.getScopeRange());
      oldpo.setScopeEmpID(po.getScopeEmpID());
      oldpo.setScopeGroupID(po.getScopeGroupID());
      oldpo.setScopeOrgID(po.getScopeOrgID());
      oldpo.setState(po.getState());
      oldpo.setStartDate(po.getStartDate());
      oldpo.setStartTime(po.getStartTime());
      oldpo.setEndDate(po.getEndDate());
      oldpo.setEndTime(po.getEndTime());
      oldpo.setRadioAmount(po.getRadioAmount());
      oldpo.setRadioMark(po.getRadioMark());
      oldpo.setRadioIds(po.getRadioIds());
      oldpo.setCheckAmount(po.getCheckAmount());
      oldpo.setCheckMark(po.getCheckMark());
      oldpo.setCheckIds(po.getCheckIds());
      oldpo.setQuestionAmount(po.getQuestionAmount());
      oldpo.setQuestionMark(po.getQuestionMark());
      oldpo.setQuestionIds(po.getQuestionIds());
      this.session.update(oldpo);
      this.session.flush();
      this.session.delete(
          "from com.js.oa.hr.examination.po.ExaminationPersonnelPO po where po.examManagerID=" + 
          id + " and po.isAnswer='0' ");
      this.session.flush();
      String empIds = String.valueOf(po.getScopeEmpID()) + 
        getUserByGroup(po.getScopeGroupID()) + 
        getUserByOrg(po.getScopeOrgID());
      empIds = empIds.substring(1, empIds.length() - 1);
      String[] empArr = empIds.split("\\$\\$");
      for (int i = 0; i < empArr.length; i++) {
        Iterator iter = this.session.createQuery(
            "select po from com.js.oa.hr.examination.po.ExaminationPersonnelPO po where po.examManagerID=" + 
            id + " and po.empID=" + 
            empArr[i]).iterate();
        if (!iter.hasNext()) {
          ExaminationPersonnelPO personnelPO = 
            new ExaminationPersonnelPO();
          personnelPO.setExamManagerID(id);
          personnelPO.setEmpID(new Long(empArr[i]));
          personnelPO.setIsAnswer("0");
          this.session.save(personnelPO);
          this.session.flush();
        } 
      } 
      ret = Boolean.TRUE;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  private String getUserByOrg(String orgIdStr) {
    String orgIds = "";
    if (orgIdStr == null || orgIdStr.length() < 1)
      return orgIds; 
    orgIdStr = orgIdStr.substring(1, orgIdStr.length() - 1);
    String[] orgIdArr = orgIdStr.split("\\*\\*");
    DbOpt dbopt = null;
    ResultSet rs = null;
    try {
      dbopt = new DbOpt();
      for (int i = 0; i < orgIdArr.length; i++) {
        String orgCode = dbopt.executeQueryToStr(
            "select ORGIDSTRING from ORG_ORGANIZATION where ORG_ID=" + 
            orgIdArr[i]);
        rs = dbopt.executeQuery("select EMP_ID from ORG_ORGANIZATION_USER where ORG_ID in (select ORG_ID from ORG_ORGANIZATION where ORGIDSTRING like '%" + 
            orgCode + "%')");
        if (rs != null) {
          while (rs.next()) {
            Object empId = rs.getObject(1);
            if (empId != null && 
              orgIds.indexOf(empId.toString()) < 0)
              orgIds = String.valueOf(orgIds) + "$" + empId.toString() + "$"; 
          } 
          rs.close();
        } 
      } 
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } finally {}
    return orgIds;
  }
  
  private String getUserByGroup(String groupIdStr) {
    String userStr = "";
    if (groupIdStr == null || groupIdStr.length() < 1)
      return userStr; 
    groupIdStr = groupIdStr.substring(1, groupIdStr.length() - 1);
    String[] groupIdArr = groupIdStr.split("\\@\\@");
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      for (int i = 0; i < groupIdArr.length; i++) {
        String empIdStr = dbopt.executeQueryToStr(
            "select GROUPUSERSTRING from ORG_GROUP where GROUP_ID=" + 
            groupIdArr[i]);
        if (empIdStr != null && empIdStr.length() > 1)
          userStr = String.valueOf(userStr) + empIdStr; 
      } 
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } finally {}
    return userStr;
  }
  
  public Boolean savePaper(ExaminationAnswerPO po, Object[] para) throws Exception {
    Boolean ret = Boolean.FALSE;
    try {
      begin();
      Long id = (Long)this.session.save(po);
      this.session.flush();
      List<ExaminationPersonnelPO> list = this.session.createQuery("select po from com.js.oa.hr.examination.po.ExaminationPersonnelPO po where po.examManagerID=" + 


          
          po.getExaminationID() + 
          " and po.empID=" + po.getEmpID())
        .list();
      ExaminationPersonnelPO personnelPO = null;
      if (list != null && list.size() > 0) {
        personnelPO = list.get(0);
        personnelPO.setIsAnswer("1");
        this.session.flush();
      } 
      for (int i = 0; i < para.length; i++) {
        String[] itemArr = (String[])para[i];
        ExaminationAnswerItemPO itemPO = new ExaminationAnswerItemPO();
        itemPO.setExaminationAnswerPO(po);
        itemPO.setExaminationID(new Long(itemArr[0]));
        itemPO.setExaminationResult(itemArr[1]);
        itemPO.setMyResult(itemArr[2]);
        itemPO.setFullMark(new Long(itemArr[3]));
        itemPO.setIsRight(itemArr[4]);
        itemPO.setMark(new Long(itemArr[5]));
        this.session.save(itemPO);
        this.session.flush();
      } 
      ret = Boolean.TRUE;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public ExaminationAnswerItemPO loadPaper(Long examinationID, Long empID, String stockID) throws Exception {
    ExaminationAnswerItemPO po = null;
    try {
      begin();
      ExaminationAnswerPO eapo = new ExaminationAnswerPO();
      List<ExaminationAnswerPO> list = this.session.createQuery("select po from com.js.oa.hr.examination.po.ExaminationAnswerPO po where po.examinationID=" + 


          
          examinationID + 
          " and po.empID=" + empID)
        .list();
      if (list != null && list.size() > 0) {
        eapo = list.get(0);
        list = this.session.createQuery("select po from com.js.oa.hr.examination.po.ExaminationAnswerItemPO po where po.examinationAnswerPO.answerID=" + 



            
            eapo.getAnswerID() + 
            " and po.examinationID=" + stockID)
          .list();
        po = (ExaminationAnswerItemPO)list.get(0);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return po;
  }
  
  public Boolean grade(Long answerID, Object[] para) throws Exception {
    Boolean ret = Boolean.FALSE;
    try {
      begin();
      List<ExaminationAnswerItemPO> list = null;
      if (para != null)
        for (int i = 0; i < para.length; i++) {
          String[] item = (String[])para[i];
          list = this.session.createQuery("select po from com.js.oa.hr.examination.po.ExaminationAnswerItemPO po where po.examinationAnswerPO.answerID=" + 



              
              answerID + 
              " and po.examinationID=" + 
              item[0]).list();
          ExaminationAnswerItemPO examinationAnswerItemPO = list
            .get(
              0);
          examinationAnswerItemPO.setMark(new Long(item[1]));
          this.session.update(examinationAnswerItemPO);
          this.session.flush();
        }  
      list = this.session.createQuery("select sum(po.mark) from com.js.oa.hr.examination.po.ExaminationAnswerItemPO po where po.examinationAnswerPO.answerID=" + 



          
          answerID).list();
      ExaminationAnswerPO po = (ExaminationAnswerPO)this.session.load(
          ExaminationAnswerPO.class, answerID);
      po.setScore(new Long(list.get(0).toString()));
      this.session.update(po);
      this.session.flush();
      ret = Boolean.TRUE;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public Long getScore(String manageID, String empID, String style) throws Exception {
    Long score = new Long(0L);
    try {
      begin();
      ExaminationManagePO po = (ExaminationManagePO)this.session.load(
          ExaminationManagePO.class, new Long(manageID));
      ExaminationAnswerPO eapo = new ExaminationAnswerPO();
      List<ExaminationAnswerPO> list = this.session.createQuery("select po from com.js.oa.hr.examination.po.ExaminationAnswerPO po where po.examinationID=" + 


          
          manageID + 
          " and po.empID=" + empID)
        .list();
      eapo = list.get(0);
      String whereSQL = " where po.examinationAnswerPO.answerID=" + 
        eapo.getAnswerID();
      if (style.equals("1")) {
        whereSQL = String.valueOf(whereSQL) + " and po.examinationID in (" + 
          po.getRadioIds()
          .substring(1, 
            po.getRadioIds().length() - 
            1).replaceAll(",,", ",") + 
          ")";
      } else if (style.equals("2")) {
        whereSQL = String.valueOf(whereSQL) + " and po.examinationID in (" + 
          po.getCheckIds()
          .substring(1, 
            po.getCheckIds().length() - 
            1).replaceAll(",,", ",") + 
          ")";
      } else if (style.equals("3")) {
        whereSQL = String.valueOf(whereSQL) + " and po.examinationID in (" + 
          po.getQuestionIds()
          .substring(1, po.getQuestionIds().length() - 1)
          .replaceAll(",,", ",") + ")";
      } 
      list = this.session.createQuery("select sum(po.mark) from com.js.oa.hr.examination.po.ExaminationAnswerItemPO po " + 

          
          whereSQL).list();
      score = new Long(list.get(0).toString());
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return score;
  }
  
  public Boolean delete(Long id) throws Exception {
    Boolean ret = Boolean.FALSE;
    try {
      begin();
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.executeUpdate("delete from oa_examination_answer_item  where answer_id in (select answer_id from oa_examination_answer where exammanageid=" + 
          
          id + ")");
      this.session.flush();
      stmt.executeUpdate(
          "delete from oa_examination_answer where exammanageid=" + 
          id);
      this.session.flush();
      stmt.executeUpdate(
          "delete from oa_examination_personnel where exammanageid=" + 
          id);
      this.session.flush();
      stmt.executeUpdate("delete from oa_examination_manage where id=" + 
          id);
      this.session.flush();
      ret = Boolean.TRUE;
      stmt.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public Boolean deleteBatch(String ids) throws Exception {
    Boolean ret = Boolean.FALSE;
    try {
      begin();
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String[] idsArr = ids.split(",");
      for (int i = 0; i < idsArr.length; i++) {
        String id = idsArr[i];
        stmt.executeUpdate("delete from oa_examination_answer_item  where answer_id in (select answer_id from oa_examination_answer where exammanageid=" + 
            
            id + ")");
        this.session.flush();
        stmt.executeUpdate(
            "delete from oa_examination_answer where exammanageid=" + 
            id);
        this.session.flush();
        stmt.executeUpdate(
            "delete from oa_examination_personnel where exammanageid=" + 
            id);
        this.session.flush();
        stmt.executeUpdate(
            "delete from oa_examination_manage where id=" + 
            id);
        this.session.flush();
      } 
      ret = Boolean.TRUE;
      stmt.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public List voteList(String manageID, String stockID) throws Exception {
    List<String[]> retlist = new ArrayList();
    try {
      begin();
      List<E> list = this.session.createQuery("select count(*) from  com.js.oa.hr.examination.po.ExaminationPersonnelPO po where po.examManagerID=" + 


          
          manageID + " and po.isAnswer='1'").list();
      float countAll = Integer.parseInt(list.get(0).toString());
      ExaminationStockPO po = (ExaminationStockPO)this.session.load(
          ExaminationStockPO.class, new Long(stockID));
      Set examinationItem = po.getExaminationItem();
      Iterator<ExaminationItemPO> iter = examinationItem.iterator();
      ExaminationItemPO itempo = null;
      while (iter.hasNext()) {
        String[] obj = new String[3];
        itempo = iter.next();
        obj[0] = String.valueOf(itempo.getOrderCode()) + "、" + itempo.getItemOption();
        if (countAll == 0.0F) {
          obj[1] = "0";
          obj[2] = "0";
        } else {
          list = this.session.createQuery("select po.answerID from com.js.oa.hr.examination.po.ExaminationAnswerPO po where po.examinationID=" + 
              
              manageID).list();
          String answerIds = "";
          if (list != null)
            for (int i = 0; i < list.size(); i++)
              answerIds = String.valueOf(answerIds) + list.get(i).toString() + ",";  
          answerIds = String.valueOf(answerIds) + "-1";
          list = this.session.createQuery("select count(*) from com.js.oa.hr.examination.po.ExaminationAnswerItemPO po where po.examinationAnswerPO.answerID in (" + 
              
              answerIds + ") " + 
              " and po.examinationID=" + stockID + " and po.myResult like '%," + itempo.getItemID() + ",%'").list();
          float countItem = Integer.parseInt(list.get(0).toString());
          obj[1] = (new StringBuilder(String.valueOf(round(100.0F * countItem / countAll, 2)))).toString();
          obj[2] = (new StringBuilder(String.valueOf(round(countItem, 0)))).toString();
        } 
        retlist.add(obj);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return retlist;
  }
  
  private float round(float v, int scale) {
    if (scale < 0)
      return 0.0F; 
    BigDecimal b = new BigDecimal(Float.toString(v));
    BigDecimal one = new BigDecimal("1");
    return b.divide(one, scale, 4).floatValue();
  }
}
