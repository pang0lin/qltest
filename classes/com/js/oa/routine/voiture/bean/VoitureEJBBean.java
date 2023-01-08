package com.js.oa.routine.voiture.bean;

import com.js.oa.routine.voiture.po.VoitureApplyPO;
import com.js.oa.routine.voiture.po.VoitureAuditingPO;
import com.js.oa.routine.voiture.po.VoitureCancelPO;
import com.js.oa.routine.voiture.po.VoitureFeedbackPO;
import com.js.oa.routine.voiture.po.VoitureMaintainPO;
import com.js.oa.routine.voiture.po.VoiturePO;
import com.js.oa.routine.voiture.po.VoitureSendPO;
import com.js.oa.routine.voiture.po.VoitureTypePO;
import com.js.system.service.messages.MessagesBD;
import com.js.system.vo.messages.MessagesVO;
import com.js.util.config.SysConfig;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;

public class VoitureEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Integer saveVoitureType(VoitureTypePO voitureTypePO) throws Exception {
    int result = 2;
    begin();
    try {
      result = Integer.parseInt(this.session.createQuery("select count(*) from com.js.oa.routine.voiture.po.VoitureTypePO po where po.name='" + 
            voitureTypePO.getName() + "' and po.domainId=" + 
            voitureTypePO.getDomainId()).iterate().next().toString());
      if (result > 0) {
        result = 1;
        return new Integer(result);
      } 
      this.session.save(voitureTypePO);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    this.session.close();
    return new Integer(result);
  }
  
  public VoitureTypePO loadVoitureType(String id) throws Exception {
    VoitureTypePO vtpo = null;
    begin();
    try {
      vtpo = (VoitureTypePO)this.session.load(VoitureTypePO.class, 
          new Long(id));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return vtpo;
  }
  
  public List listVoitureType(String domainId) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      list = this.session.createQuery(
          "select po.id,po.name from com.js.oa.routine.voiture.po.VoitureTypePO po where po.domainId=" + 
          domainId)
        .list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public boolean delVoitureType(String ids) throws Exception {
    begin();
    boolean bl = false;
    try {
      if (ids != null && !ids.equals(""))
        this.session.delete(
            " from com.js.oa.routine.voiture.po.VoitureTypePO po where po.id in (" + 
            ids.substring(0, ids.length() - 1) + ")"); 
      this.session.flush();
      bl = true;
    } catch (Exception e) {
      e.printStackTrace();
      this.transaction.rollback();
      throw e;
    } finally {
      this.session.close();
    } 
    return bl;
  }
  
  public Integer updateVoitureType(VoitureTypePO voitureTypePO, String typeId) throws Exception {
    int result = 2;
    begin();
    try {
      VoitureTypePO vtpo = (VoitureTypePO)this.session.load(VoitureTypePO.class, 
          new Long(typeId));
      result = Integer.parseInt(this.session.createQuery("select count(*) from com.js.oa.routine.voiture.po.VoitureTypePO po where po.name='" + 
            voitureTypePO.getName() + "' and po.id<>" + 
            voitureTypePO.getId() + " and po.domainId=" + 
            vtpo.getDomainId()).iterate().next().toString());
      if (result > 0) {
        result = 1;
        return new Integer(result);
      } 
      vtpo.setName(voitureTypePO.getName());
      this.session.update(vtpo);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    this.session.close();
    return new Integer(result);
  }
  
  public Integer saveVoiture(VoiturePO voiturePO) throws Exception {
    int result = 2;
    begin();
    try {
      result = Integer.parseInt(this.session.createQuery(
            "select count(*) from com.js.oa.routine.voiture.po.VoiturePO po where po.isDelete=0 and po.num='" + voiturePO.getNum() + 
            "' and po.domainId=" + voiturePO.getDomainId() + "'")
          .iterate().next().toString());
      if (result > 0) {
        result = 1;
        return new Integer(result);
      } 
      this.session.save(voiturePO);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    this.session.close();
    return new Integer(result);
  }
  
  public boolean delVoiture(String ids) throws Exception {
    begin();
    boolean bl = false;
    try {
      String[] idList = ids.split(",");
      for (int i = 0; i < idList.length; i++) {
        if (!"".equals(idList[i].trim())) {
          VoiturePO po = (VoiturePO)this.session.load(VoiturePO.class, Long.valueOf(idList[i].trim()));
          po.setIsDelete(1);
          this.session.update(po);
        } 
      } 
      this.session.flush();
      bl = true;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return bl;
  }
  
  public boolean checkVoiture(String ids) throws Exception {
    begin();
    int result = 0;
    boolean bl = false;
    try {
      if (ids != null && !ids.equals(""))
        result = Integer.parseInt(this.session.createQuery(
              " select count(*) from com.js.oa.routine.voiture.po.VoitureApplyPO po where po.voitureId in (" + 
              ids.substring(0, ids.length() - 1) + ")").iterate().next().toString()); 
      this.session.flush();
      if (result == 0) {
        bl = false;
      } else {
        bl = true;
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return bl;
  }
  
  public Integer updateVoiture(VoiturePO voiturePO, String id) throws Exception {
    int result = 2;
    begin();
    try {
      result = Integer.parseInt(this.session.createQuery(
            "select count(*) from com.js.oa.routine.voiture.po.VoiturePO po where ( po.num='" + voiturePO.getNum() + 
            "') and po.isDelete=0 and po.domainId=" + voiturePO.getDomainId() + 
            " and po.id<>" + voiturePO.getId())
          .iterate().next().toString());
      if (result > 0) {
        result = 1;
      } else {
        VoiturePO vpo = (VoiturePO)this.session.load(VoiturePO.class, voiturePO.getId());
        vpo.setMaintainCyc(voiturePO.getMaintainCyc());
        vpo.setMaintainOdograph(voiturePO.getMaintainOdograph());
        vpo.setModel(voiturePO.getModel());
        vpo.setMotorMan(voiturePO.getMotorMan());
        vpo.setName(voiturePO.getName());
        vpo.setNum(voiturePO.getNum());
        vpo.setOilCost(voiturePO.getOilCost());
        vpo.setOrgId(voiturePO.getOrgId());
        vpo.setOrgName(voiturePO.getOrgName());
        vpo.setStatus(voiturePO.getStatus());
        vpo.setRemark(voiturePO.getRemark());
        vpo.setUseRangeId(voiturePO.getUseRangeId());
        vpo.setUseRangeName(voiturePO.getUseRangeName());
        vpo.setFixedCost(voiturePO.getFixedCost());
        vpo.setVoitureTypePO((VoitureTypePO)this.session.load(VoitureTypePO.class, voiturePO.getVoitureTypePO().getId()));
        vpo.setVehicleNum(voiturePO.getVehicleNum());
        this.session.flush();
        result = 0;
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return new Integer(result);
  }
  
  public VoiturePO loadVoiture(String id) throws Exception {
    VoiturePO vpo = null;
    begin();
    try {
      vpo = (VoiturePO)this.session.load(VoiturePO.class, new Long(id));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return vpo;
  }
  
  public Long saveVoitureApply(VoitureApplyPO voitureApplyPO) throws Exception {
    Long result = new Long(-1L);
    begin();
    try {
      result = (Long)this.session.save(voitureApplyPO);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public boolean delVoitureApply(String ids) throws Exception {
    begin();
    boolean bl = false;
    try {
      if (ids != null && !ids.equals("")) {
        Connection conn = (new DataSourceBase()).getDataSource().getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(
            "SELECT WF_IMMOFORM_ID FROM JSDB.JSF_IMMOBILITYFORM WHERE WF_MODULE_ID=11");
        String tableId = "0";
        if (rs.next())
          tableId = rs.getString(1); 
        rs.close();
        stmt.execute("DELETE from JSDB.JSF_WORK WHERE WORKTABLE_ID=" + 
            tableId + " AND WORKRECORD_ID in (" + 
            ids.substring(0, ids.length() - 1) + ")");
        this.session.delete(" from com.js.oa.routine.voiture.po.VoitureAuditingPO po where po.voitureApplyPO.id in(" + 
            ids.substring(0, ids.length() - 1) + ")");
        this.session.delete(
            " from com.js.oa.routine.voiture.po.VoitureApplyPO po where po.id in (" + 
            ids.substring(0, ids.length() - 1) + ")");
        this.session.flush();
        stmt.close();
        conn.close();
      } 
      bl = true;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return bl;
  }
  
  public Integer saveVoitureMaintain(VoitureMaintainPO voitureMaintainPO) throws Exception {
    int result = 2;
    begin();
    try {
      this.session.save(voitureMaintainPO);
      this.session.flush();
      result = 0;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return new Integer(result);
  }
  
  public boolean delVoitureMaintain(String ids) throws Exception {
    begin();
    boolean bl = false;
    try {
      if (ids != null && !ids.equals(""))
        this.session.delete(
            " from com.js.oa.routine.voiture.po.VoitureMaintainPO po where po.id in (" + 
            ids.substring(0, ids.length() - 1) + ")"); 
      this.session.flush();
      bl = true;
    } catch (Exception e) {
      e.printStackTrace();
      this.transaction.rollback();
      throw e;
    } finally {
      this.session.close();
    } 
    return bl;
  }
  
  public boolean auditingApply(String id, VoitureApplyPO voitureApplyPO, String status) throws Exception {
    begin();
    boolean bl = false;
    VoitureApplyPO vapo = new VoitureApplyPO();
    try {
      vapo = (VoitureApplyPO)this.session.load(VoitureApplyPO.class, 
          new Long(id));
      vapo.setStatus(status);
      vapo.setMotorMan(voitureApplyPO.getMotorMan());
      this.session.flush();
      bl = true;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return bl;
  }
  
  public boolean auditingApply(String id, String status) throws Exception {
    begin();
    boolean bl = false;
    VoitureApplyPO vapo = new VoitureApplyPO();
    try {
      vapo = (VoitureApplyPO)this.session.load(VoitureApplyPO.class, 
          new Long(id));
      vapo.setStatus(status);
      vapo.setCarPoolId("0");
      this.session.flush();
      bl = true;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return bl;
  }
  
  public VoitureMaintainPO loadVoitureMaintain(String id) throws Exception {
    VoitureMaintainPO vmpo = null;
    begin();
    try {
      vmpo = (VoitureMaintainPO)this.session.load(VoitureMaintainPO.class, 
          new Long(id));
    } catch (Exception e) {
      e.printStackTrace();
      this.transaction.rollback();
      throw e;
    } finally {
      this.session.close();
    } 
    return vmpo;
  }
  
  public List listVoitureInfo(String voitureId, String startDate, String endDate) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      String tmpSql = "";
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = 
          "select po.id,po.voitureId from com.js.oa.routine.voiture.po.VoitureApplyPO po  where po.voitureId=" + 
          voitureId + 
          " and po.startDate>='" + startDate + 
          "' and " + 
          "po.endDate<='" + endDate + "'";
      } else {
        tmpSql = 
          "select po.id,po.voitureId from com.js.oa.routine.voiture.po.VoitureApplyPO po  where po.voitureId=" + 
          voitureId + 
          " and po.startDate>=JSDB.FN_STRTODATE(" + startDate + 
          ") and " + 
          "po.endDate<=JSDB.FN_STRTODATE(" + endDate + ")";
      } 
      list = this.session.createQuery(tmpSql).list();
    } catch (Exception e) {
      e.printStackTrace();
      this.transaction.rollback();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public String getVoitureSendNumberMax() throws Exception {
    String maxSendNumber = "";
    begin();
    try {
      Query query = this.session.createQuery(
          "select max(po.sendNumber) from com.js.oa.routine.voiture.po.VoitureSendPO po");
      List<E> list = query.list();
      if (list.size() > 0 && list.get(0) != null)
        maxSendNumber = list.get(0).toString(); 
    } catch (Exception e) {
      e.printStackTrace();
      this.transaction.rollback();
      throw e;
    } finally {
      this.session.close();
    } 
    return maxSendNumber;
  }
  
  public Integer saveVoitureSend(VoitureSendPO voitureSendPO) throws Exception {
    int result = 2;
    begin();
    try {
      this.session.save(voitureSendPO);
      this.session.flush();
      result = 0;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return new Integer(result);
  }
  
  public VoitureApplyPO loadVoitureApply(String id) throws Exception {
    VoitureApplyPO vapo = null;
    begin();
    try {
      vapo = (VoitureApplyPO)this.session.load(VoitureApplyPO.class, new Long(id));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return vapo;
  }
  
  public Integer updateVoitureApply(VoitureApplyPO voitureApplyPO, String applyId) throws Exception {
    int result = 2;
    begin();
    try {
      VoitureApplyPO vapo = (VoitureApplyPO)this.session.load(VoitureApplyPO.class, 
          new Long(applyId));
      vapo.setEmpId(voitureApplyPO.getEmpId());
      vapo.setOrgId(voitureApplyPO.getOrgId());
      vapo.setOrgName(voitureApplyPO.getOrgName());
      vapo.setVoitureId(voitureApplyPO.getVoitureId());
      vapo.setDestination(voitureApplyPO.getDestination());
      vapo.setEmpName(voitureApplyPO.getEmpName());
      vapo.setReason(voitureApplyPO.getReason());
      vapo.setStatus(voitureApplyPO.getStatus());
      vapo.setStartDate(voitureApplyPO.getStartDate());
      vapo.setEndDate(voitureApplyPO.getEndDate());
      vapo.setStartTime(voitureApplyPO.getStartTime());
      vapo.setEndTime(voitureApplyPO.getEndTime());
      vapo.setMotorMan(voitureApplyPO.getMotorMan());
      vapo.setRemark(voitureApplyPO.getRemark());
      this.session.update(vapo);
      this.session.flush();
      result = 0;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return new Integer(result);
  }
  
  public List listVoiture(String id, String domainId, String userName, String orgName, String userId, String orgIdString) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      if (!id.equals("0")) {
        list = this.session.createQuery(
            "select po.id,po.name,po.motorMan,po.vehicleNum from com.js.oa.routine.voiture.po.VoiturePO po where po.id=" + 
            id).list();
      } else {
        String range = getVoitureUseRanges(userName, orgName, userId, orgIdString);
        String whereSql = "";
        if (!range.equals("")) {
          whereSql = String.valueOf(whereSql) + " and po.isDelete=0 and po.id in(" + range.substring(0, range.length() - 1) + ")";
          String sql = "select po.id,po.name,po.motorMan,po.vehicleNum from com.js.oa.routine.voiture.po.VoiturePO po where po.domainId=" + 
            domainId + whereSql;
          list = this.session.createQuery(sql).list();
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public VoitureSendPO loadVoitureSend(String id) throws Exception {
    VoitureSendPO vspo = null;
    begin();
    try {
      vspo = (VoitureSendPO)this.session.load(VoitureSendPO.class, 
          new Long(id));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return vspo;
  }
  
  public VoitureSendPO getVoitureSendPO(String applyId) throws Exception {
    VoitureSendPO vspo = new VoitureSendPO();
    begin();
    try {
      Query query = this.session.createQuery(
          "select po from com.js.oa.routine.voiture.po.VoitureSendPO po where po.voitureApplyPO.id=" + 
          new Long(applyId));
      vspo = query.list().get(0);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return vspo;
  }
  
  public String[] getVoitureInfo(String voitureId, String searchDate) throws Exception {
    List list = new ArrayList();
    String[] voiturePlace = new String[288];
    for (int i = 0; i < voiturePlace.length; i++)
      voiturePlace[i] = "false"; 
    Iterator<Object[]> iter = null;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm");
    begin();
    try {
      String tmpSql = "";
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "select po.startDate,po.endDate,po.startTime,po.endTime from com.js.oa.routine.voiture.po.VoitureApplyPO po where po.voitureId=" + 
          new Long(voitureId) + " and (po.startDate<='" + 
          searchDate + 
          "') and ('" + searchDate + "'<= po.endDate) " + 
          " and po.status=2";
      } else {
        tmpSql = "select po.startDate,po.endDate,po.startTime,po.endTime from com.js.oa.routine.voiture.po.VoitureApplyPO po where po.voitureId=" + 
          new Long(voitureId) + " and (JSDB.FN_STRTODATE('" + 
          searchDate + 
          "','S') between po.startDate and po.endDate) " + 
          " and po.status=2";
      } 
      Query query = this.session.createQuery(tmpSql);
      list = query.list();
      iter = list.iterator();
      while (iter.hasNext()) {
        Object[] obj = iter.next();
        if (formatter.parse(searchDate).after(formatter.parse(obj[0]
              .toString())))
          if (formatter.parse(searchDate).before(formatter.parse(obj[
                  1]
                .toString()))) {
            for (int k = 0; k < voiturePlace.length; k++)
              voiturePlace[k] = "true"; 
            continue;
          }  
        if (formatter.parse(searchDate).equals(formatter.parse(
              obj[
                0].toString())))
          if (!formatter.parse(searchDate).equals(
              formatter.parse(obj[1].toString()))) {
            String minute = "00";
            for (int k = 0; k < 24; k++) {
              minute = "00";
              for (int m = 0; m <= 11; m++) {
                String myTime = String.valueOf(k) + ":" + minute;
                if (minute.equals("00")) {
                  minute = "0" + (Integer.parseInt(minute) + 5);
                } else {
                  int n = Integer.parseInt(minute) + 5;
                } 
                if (!formatter1.parse(myTime).before(formatter1
                    .parse(obj[2].toString())))
                  voiturePlace[12 * k + m] = "true"; 
              } 
            } 
            continue;
          }  
        if (formatter.parse(searchDate).equals(formatter.parse(
              obj[
                1].toString())))
          if (!formatter.parse(searchDate).equals(
              formatter.parse(obj[
                  0].toString()))) {
            String minute = "00";
            for (int k = 0; k < 24; k++) {
              minute = "00";
              for (int m = 0; m <= 11; m++) {
                String myTime = String.valueOf(k) + ":" + minute;
                if (minute.equals("00")) {
                  minute = "0" + (Integer.parseInt(minute) + 5);
                } else {
                  int n = Integer.parseInt(minute) + 5;
                } 
                if (!formatter1.parse(myTime).after(formatter1
                    .parse(obj[3].toString())))
                  if (!formatter1.parse(myTime).equals(formatter1
                      .parse(obj[3].toString())))
                    voiturePlace[12 * k + m] = "true";  
              } 
            } 
            continue;
          }  
        if (formatter.parse(searchDate).equals(formatter.parse(
              obj[
                1].toString())))
          if (formatter.parse(searchDate).equals(formatter.parse(
                obj[
                  0].toString()))) {
            String minute = "00";
            for (int k = 0; k < 24; k++) {
              minute = "00";
              for (int m = 0; m <= 11; m++) {
                String myTime = String.valueOf(k) + ":" + minute;
                if (minute.equals("00")) {
                  minute = "0" + (Integer.parseInt(minute) + 5);
                } else {
                  int n = Integer.parseInt(minute) + 5;
                } 
                if (!formatter1.parse(myTime).before(formatter1
                    .parse(obj[2].toString())))
                  if (!formatter1.parse(myTime).after(formatter1
                      .parse(obj[3].toString())))
                    if (!formatter1.parse(myTime).equals(formatter1
                        .parse(obj[3].toString())))
                      voiturePlace[12 * k + m] = "true";   
              } 
            } 
            continue;
          }  
        for (int j = 0; j < voiturePlace.length; j++)
          voiturePlace[j] = "false"; 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return voiturePlace;
  }
  
  public VoiturePO getVoiturePO(String id) throws Exception {
    VoiturePO vpo = new VoiturePO();
    begin();
    try {
      Query query = this.session.createQuery(
          "select po from com.js.oa.routine.voiture.po.VoiturePO po where po.id=" + 
          new Long(id));
      vpo = query.list().get(0);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return vpo;
  }
  
  public VoitureApplyPO getVoitureApplyPO(String applyId) throws Exception {
    VoitureApplyPO vapo = new VoitureApplyPO();
    begin();
    try {
      Query query = this.session.createQuery(
          "select po from com.js.oa.routine.voiture.po.VoitureApplyPO po where po.id=" + 
          new Long(applyId));
      vapo = query.list().get(0);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return vapo;
  }
  
  public Long saveVoitureCancel(VoitureCancelPO voitureCancelPO) throws Exception {
    Long result = new Long(-1L);
    begin();
    try {
      result = (Long)this.session.save(voitureCancelPO);
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(
          "SELECT WF_IMMOFORM_ID FROM JSDB.JSF_IMMOBILITYFORM WHERE WF_MODULE_ID=11");
      String tableId = "0";
      if (rs.next())
        tableId = rs.getString(1); 
      rs.close();
      stmt.execute("DELETE from JSDB.JSF_WORK WHERE WORKTABLE_ID=" + 
          tableId + " AND WORKRECORD_ID in (" + 
          voitureCancelPO.getVoitureApplyPO().getId() + ")");
      this.session.flush();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public Long saveVoitureAuditing(VoitureAuditingPO voitureAuditingPO) throws Exception {
    Long result = new Long(-1L);
    begin();
    try {
      result = (Long)this.session.save(voitureAuditingPO);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public boolean isVoitureAuditingPO(String applyId) throws Exception {
    boolean bl = false;
    begin();
    try {
      Query query = this.session.createQuery(
          "select po from com.js.oa.routine.voiture.po.VoitureAuditingPO po where po.voitureApplyPO.id=" + 
          new Long(applyId));
      if (query.list().size() > 0)
        bl = true; 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return bl;
  }
  
  public VoitureAuditingPO getVoitureAuditingPO(String applyId) throws Exception {
    VoitureAuditingPO vapo = new VoitureAuditingPO();
    begin();
    try {
      Query query = this.session.createQuery(
          "select po from com.js.oa.routine.voiture.po.VoitureAuditingPO po where po.voitureApplyPO.id=" + 
          new Long(applyId));
      vapo = query.list().get(0);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return vapo;
  }
  
  public Integer updateVoitureAudting(VoitureAuditingPO voitureAuditingPO, String auditingId) throws Exception {
    int result = 2;
    begin();
    try {
      VoitureAuditingPO vapo = (VoitureAuditingPO)this.session.load(
          VoitureAuditingPO.class, 
          new Long(auditingId));
      vapo.setAuditingAccount(voitureAuditingPO.getAuditingAccount());
      vapo.setAuditingStyle(voitureAuditingPO.getAuditingStyle());
      vapo.getVoitureApplyPO().setStartDate(voitureAuditingPO
          .getVoitureApplyPO()
          .getStartDate());
      vapo.getVoitureApplyPO().setEndDate(voitureAuditingPO
          .getVoitureApplyPO().getEndDate());
      this.session.update(vapo);
      this.session.flush();
      result = 0;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return new Integer(result);
  }
  
  public boolean delVoitureCancel(String ids) throws Exception {
    begin();
    boolean bl = false;
    try {
      if (ids != null && !ids.equals(""))
        this.session.delete(
            " from com.js.oa.routine.voiture.po.VoitureCancelPO po where po.id in (" + 
            ids.substring(0, ids.length() - 1) + ")"); 
      this.session.flush();
      bl = true;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return bl;
  }
  
  public boolean delVoitureCancelByApplyId(String id) throws Exception {
    begin();
    boolean bl = false;
    try {
      if (id != null && !id.equals(""))
        this.session.delete(
            " from com.js.oa.routine.voiture.po.VoitureCancelPO po where po.voitureApplyPO.id=" + 
            id); 
      this.session.flush();
      bl = true;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return bl;
  }
  
  public boolean isVoitureSendPO(String applyId) throws Exception {
    boolean bl = false;
    begin();
    try {
      Query query = this.session.createQuery(
          "select po from com.js.oa.routine.voiture.po.VoitureSendPO po where po.voitureApplyPO.id=" + 
          new Long(applyId));
      if (query.list().size() > 0)
        bl = true; 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return bl;
  }
  
  public Integer updateVoitureSend(VoitureSendPO voitureSendPO, String sendId) throws Exception {
    int result = 2;
    begin();
    try {
      VoitureSendPO vspo = (VoitureSendPO)this.session.load(VoitureSendPO.class, new Long(sendId));
      vspo.setKeepingFee(voitureSendPO.getKeepingFee());
      vspo.setMisMealFee(voitureSendPO.getMisMealFee());
      vspo.setOverTime(voitureSendPO.getOverTime());
      vspo.setOverTimeHoliDay(voitureSendPO.getOverTimeHoliDay());
      vspo.setOverTimeWeekend(voitureSendPO.getOverTimeWeekend());
      vspo.setSendCount(voitureSendPO.getSendCount());
      vspo.setSendEndDate(voitureSendPO.getSendEndDate());
      vspo.setSendEndKilo(voitureSendPO.getSendEndKilo());
      vspo.setSendHolidayCount(voitureSendPO.getSendHolidayCount());
      vspo.setSendNumber(voitureSendPO.getSendNumber());
      vspo.setSendStartDate(voitureSendPO.getSendStartDate());
      vspo.setSendStartKilo(voitureSendPO.getSendStartKilo());
      vspo.setTravelFee(voitureSendPO.getTravelFee());
      vspo.setVoitureApplyPO(voitureSendPO.getVoitureApplyPO());
      vspo.setVoiturePO(voitureSendPO.getVoiturePO());
      vspo.setKiloPrice(voitureSendPO.getKiloPrice());
      vspo.setSendStartTime(voitureSendPO.getSendStartTime());
      vspo.setSendEndTime(voitureSendPO.getSendEndTime());
      vspo.setSendStartTimeTotal(voitureSendPO.getSendStartTimeTotal());
      vspo.setSendEndTimeTotal(voitureSendPO.getSendEndTimeTotal());
      vspo.setOtherAllowance(voitureSendPO.getOtherAllowance());
      this.session.update(vspo);
      this.session.flush();
      result = 0;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return new Integer(result);
  }
  
  public String getVoitureUseRange(String userName, String orgName, String userId, String orgIdString) throws Exception {
    StringBuffer ret = new StringBuffer();
    begin();
    try {
      String[] orgIdArray = (String[])null;
      if (orgIdString.length() > 0) {
        orgIdString = orgIdString.substring(1, orgIdString.length() - 1);
        orgIdArray = orgIdString.split("\\$\\$");
      } 
      Query query = this.session.createQuery(
          "select po.id,po.useRangeId,po.useRangeName from com.js.oa.routine.voiture.po.VoiturePO po");
      List list = query.list();
      Iterator<Object[]> iter = list.iterator();
      while (iter.hasNext()) {
        Object[] obj = iter.next();
        if (obj[1] != null && !"".equals(obj[1])) {
          String userRange = obj[1].toString();
          for (int i = 0; i < orgIdArray.length; i++) {
            if (userRange.indexOf("*" + orgIdArray[i] + "*") >= 0)
              ret.append(obj[0].toString()).append(","); 
          } 
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      this.transaction.rollback();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return ret.toString();
  }
  
  private String getVoitureUseRanges(String userName, String orgName, String userId, String orgIdString) throws Exception {
    StringBuffer ret = new StringBuffer();
    try {
      String[] orgIdArray = (String[])null;
      if (orgIdString.length() > 0) {
        orgIdString = orgIdString.substring(1, orgIdString.length() - 1);
        orgIdArray = orgIdString.split("\\$\\$");
      } 
      Query query = this.session.createQuery(
          "select po.id,po.useRangeId,po.useRangeName from com.js.oa.routine.voiture.po.VoiturePO po");
      List list = query.list();
      Iterator<Object[]> iter = list.iterator();
      while (iter.hasNext()) {
        Object[] obj = iter.next();
        if (obj[1] != null && !"".equals(obj[1])) {
          String userRange = obj[1].toString();
          if (userRange.indexOf("$" + userId + "$") >= 0) {
            ret.append(obj[0].toString()).append(",");
            continue;
          } 
          for (int i = 0; i < orgIdArray.length; i++) {
            if (userRange.indexOf("*" + orgIdArray[i] + "*") >= 0) {
              ret.append(obj[0].toString()).append(",");
              break;
            } 
          } 
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } 
    return ret.toString();
  }
  
  public VoitureFeedbackPO getVoitureFeedbackPO(String applyId) throws Exception {
    VoitureFeedbackPO vapo = new VoitureFeedbackPO();
    begin();
    try {
      List<VoitureFeedbackPO> list = new ArrayList();
      list = this.session.find(
          "from com.js.oa.routine.voiture.po.VoitureFeedbackPO po where po.voitureId =" + 
          applyId);
      this.session.flush();
      if (list != null && list.size() > 0)
        vapo = list.get(0); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return vapo;
  }
  
  public String updateVoitureFeedbackPO(VoitureFeedbackPO po) throws Exception {
    String ur = "1";
    begin();
    try {
      this.session.update(po);
      this.session.flush();
    } catch (Exception e) {
      ur = "-1";
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ur;
  }
  
  public Long saveVoitureFeedbackPO(VoitureFeedbackPO po) throws Exception {
    Long sr = null;
    begin();
    try {
      sr = (Long)this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      sr = new Long("-1");
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return sr;
  }
  
  public List getFeedbackStat(String sqlStr) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      list = this.session.find(sqlStr);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List getFeedbackStat2(String sqlStr, Date bd, Date ed) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      Query q = this.session.createQuery(sqlStr);
      q.setDate("bFate", bd);
      q.setDate("eFate", ed);
      list = q.list();
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public String isImpropriateTime(VoitureApplyPO voitureApplyPO, Long voitureId) throws HibernateException {
    String isImpropriateTime = "0";
    begin();
    try {
      String countView = "count(*)";
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String startDate = dateFormat.format(voitureApplyPO.getStartDate());
      String endDate = dateFormat.format(voitureApplyPO.getEndDate());
      String startTime = voitureApplyPO.getStartTime();
      String endTime = voitureApplyPO.getEndTime();
      StringBuffer sqlBuffer1 = new StringBuffer("");
      if ("mysql".equalsIgnoreCase(SysConfig.getDatabaseType())) {
        sqlBuffer1.append("select ").append(countView);
        sqlBuffer1.append(" from com.js.oa.routine.voiture.po.VoitureApplyPO poo  where (( str_to_date(concat(date_format(poo.startDate,'%y-%m-%d'),' ',time_format(poo.startTime, '%H:%i:%s')),'%y-%m-%d %H:%i:%s') <='")
          .append(String.valueOf(startDate) + " " + startTime).append("' and str_to_date(concat(date_format(poo.endDate,'%y-%m-%d'),' ',time_format(poo.endTime, '%H:%i:%s')),'%y-%m-%d %H:%i:%s') >= '").append(String.valueOf(endDate) + " " + endTime)
          .append("' )or (str_to_date(concat(date_format(poo.startDate,'%y-%m-%d'),' ',time_format(poo.startTime, '%H:%i:%s')),'%y-%m-%d %H:%i:%s') >='").append(String.valueOf(startDate) + " " + startTime).append("' and  str_to_date(concat(date_format(poo.endDate,'%y-%m-%d'),' ',time_format(poo.endTime, '%H:%i:%s')),'%y-%m-%d %H:%i:%s') <= '").append(String.valueOf(endDate) + " " + endTime)
          .append("') or (str_to_date(concat(date_format(poo.startDate,'%y-%m-%d'),' ',time_format(poo.startTime, '%H:%i:%s')),'%y-%m-%d %H:%i:%s') >='").append(String.valueOf(startDate) + " " + startTime).append("' and str_to_date(concat(date_format(poo.startDate,'%y-%m-%d'),' ',time_format(poo.startTime, '%H:%i:%s')),'%y-%m-%d %H:%i:%s') <= '").append(String.valueOf(endDate) + " " + endTime)
          .append("')or (str_to_date(concat(date_format(poo.endDate,'%y-%m-%d'),' ',time_format(poo.endTime, '%H:%i:%s')),'%y-%m-%d %H:%i:%s') >='").append(String.valueOf(startDate) + " " + startTime).append("' and  str_to_date(concat(date_format(poo.endDate,'%y-%m-%d'),' ',time_format(poo.endTime, '%H:%i:%s')),'%y-%m-%d %H:%i:%s') <= '").append(String.valueOf(endDate) + " " + endTime).append("'))")
          .append(" and poo.status = '2' and poo.voitureId=")
          .append(voitureId);
      } else {
        sqlBuffer1.append("select ").append(countView);
        sqlBuffer1.append(" from com.js.oa.routine.voiture.po.VoitureApplyPO poo  where ((to_date(to_char(startDate,'yyyy-mm-dd') || ' '||startTime,'yyyy-mm-dd hh24:mi') <=to_date('")
          .append(String.valueOf(startDate) + " " + startTime).append("','yyyy-mm-dd hh24:mi') and to_date(to_char(endDate,'yyyy-mm-dd') || ' '||endTime,'yyyy-MM-dd hh24-mi') >= to_date('").append(String.valueOf(endDate) + " " + endTime)
          .append("','yyyy-mm-dd hh24:mi'))or (to_date(to_char(startDate,'yyyy-mm-dd') || ' '||startTime,'yyyy-mm-dd hh24:mi') >=to_date('").append(String.valueOf(startDate) + " " + startTime).append("','yyyy-mm-dd hh24:mi') and  to_date(to_char(endDate,'yyyy-mm-dd') || ' '||endTime,'yyyy-MM-dd hh24-mi')<= to_date('").append(String.valueOf(endDate) + " " + endTime)
          .append("','yyyy-mm-dd hh24:mi')) or (to_date(to_char(startDate,'yyyy-mm-dd') || ' '||startTime,'yyyy-MM-dd hh24-mi') >=to_date('").append(String.valueOf(startDate) + " " + startTime).append("','yyyy-mm-dd hh24:mi') and to_date(to_char(startDate,'yyyy-mm-dd') || ' '||startTime,'yyyy-MM-dd hh24-mi') <= to_date('").append(String.valueOf(endDate) + " " + endTime)
          .append("','yyyy-mm-dd hh24:mi'))or (to_date(to_char(endDate,'yyyy-mm-dd') || ' '||endTime,'yyyy-MM-dd hh24-mi') >=to_date('").append(String.valueOf(startDate) + " " + startTime).append("','yyyy-mm-dd hh24:mi') and  to_date(to_char(endDate,'yyyy-mm-dd') || ' '||endTime,'yyyy-MM-dd hh24-mi') <= to_date('").append(String.valueOf(endDate) + " " + endTime).append("','yyyy-mm-dd hh24:mi')))")
          .append(" and poo.status = '2' and poo.voitureId=")
          .append(voitureId);
      } 
      Query query = this.session.createQuery(sqlBuffer1.toString());
      List<E> list = query.list();
      if (list != null && list.get(0) != null)
        isImpropriateTime = list.get(0).toString(); 
      if ("0".equals(isImpropriateTime)) {
        sqlBuffer1 = new StringBuffer("");
        if ("mysql".equalsIgnoreCase(SysConfig.getDatabaseType())) {
          sqlBuffer1.append("select ").append(countView);
          sqlBuffer1.append(" from com.js.oa.routine.voiture.po.VoitureApplyPO poo  where (( str_to_date(concat(date_format(poo.startDate,'%y-%m-%d'),' ',time_format(poo.startTime, '%H:%i:%s')),'%y-%m-%d %H:%i:%s') <='")
            .append(String.valueOf(startDate) + " " + startTime).append("' and str_to_date(concat(date_format(poo.endDate,'%y-%m-%d'),' ',time_format(poo.endTime, '%H:%i:%s')),'%y-%m-%d %H:%i:%s') >= '").append(String.valueOf(endDate) + " " + endTime)
            .append("' )or (str_to_date(concat(date_format(poo.startDate,'%y-%m-%d'),' ',time_format(poo.startTime, '%H:%i:%s')),'%y-%m-%d %H:%i:%s') >='").append(String.valueOf(startDate) + " " + startTime).append("' and  str_to_date(concat(date_format(poo.endDate,'%y-%m-%d'),' ',time_format(poo.endTime, '%H:%i:%s')),'%y-%m-%d %H:%i:%s') <= '").append(String.valueOf(endDate) + " " + endTime)
            .append("') or (str_to_date(concat(date_format(poo.startDate,'%y-%m-%d'),' ',time_format(poo.startTime, '%H:%i:%s')),'%y-%m-%d %H:%i:%s') >='").append(String.valueOf(startDate) + " " + startTime).append("' and str_to_date(concat(date_format(poo.startDate,'%y-%m-%d'),' ',time_format(poo.startTime, '%H:%i:%s')),'%y-%m-%d %H:%i:%s') <= '").append(String.valueOf(endDate) + " " + endTime)
            .append("')or (str_to_date(concat(date_format(poo.endDate,'%y-%m-%d'),' ',time_format(poo.endTime, '%H:%i:%s')),'%y-%m-%d %H:%i:%s') >='").append(String.valueOf(startDate) + " " + startTime).append("' and  str_to_date(concat(date_format(poo.endDate,'%y-%m-%d'),' ',time_format(poo.endTime, '%H:%i:%s')),'%y-%m-%d %H:%i:%s') <= '").append(String.valueOf(endDate) + " " + endTime).append("'))")
            .append(" and poo.status = '1' and poo.voitureId=")
            .append(voitureId);
        } else {
          sqlBuffer1.append("select ").append(countView);
          sqlBuffer1.append(" from com.js.oa.routine.voiture.po.VoitureApplyPO poo  where ((to_date(to_char(startDate,'yyyy-mm-dd') || ' '||startTime,'yyyy-mm-dd hh24:mi') <=to_date('")
            .append(String.valueOf(startDate) + " " + startTime).append("','yyyy-mm-dd hh24:mi') and to_date(to_char(endDate,'yyyy-mm-dd') || ' '||endTime,'yyyy-MM-dd hh24-mi') >= to_date('").append(String.valueOf(endDate) + " " + endTime)
            .append("','yyyy-mm-dd hh24:mi'))or (to_date(to_char(startDate,'yyyy-mm-dd') || ' '||startTime,'yyyy-mm-dd hh24:mi') >=to_date('").append(String.valueOf(startDate) + " " + startTime).append("','yyyy-mm-dd hh24:mi') and  to_date(to_char(endDate,'yyyy-mm-dd') || ' '||endTime,'yyyy-MM-dd hh24-mi')<= to_date('").append(String.valueOf(endDate) + " " + endTime)
            .append("','yyyy-mm-dd hh24:mi')) or (to_date(to_char(startDate,'yyyy-mm-dd') || ' '||startTime,'yyyy-MM-dd hh24-mi') >=to_date('").append(String.valueOf(startDate) + " " + startTime).append("','yyyy-mm-dd hh24:mi') and to_date(to_char(startDate,'yyyy-mm-dd') || ' '||startTime,'yyyy-MM-dd hh24-mi') <= to_date('").append(String.valueOf(endDate) + " " + endTime)
            .append("','yyyy-mm-dd hh24:mi'))or (to_date(to_char(endDate,'yyyy-mm-dd') || ' '||endTime,'yyyy-MM-dd hh24-mi') >=to_date('").append(String.valueOf(startDate) + " " + startTime).append("','yyyy-mm-dd hh24:mi') and  to_date(to_char(endDate,'yyyy-mm-dd') || ' '||endTime,'yyyy-MM-dd hh24-mi') <= to_date('").append(String.valueOf(endDate) + " " + endTime).append("','yyyy-mm-dd hh24:mi')))")
            .append(" and poo.status = '1' and poo.voitureId=")
            .append(voitureId);
        } 
        query = this.session.createQuery(sqlBuffer1.toString());
        list = query.list();
        if (list != null && list.get(0) != null && 
          !"0".equals(list.get(0).toString()))
          isImpropriateTime = "-1"; 
      } 
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {}
    this.session.close();
    return isImpropriateTime;
  }
  
  public void saveMessage(String userId, String userName, String messageTitle, String messageType, String dataId, String messageUrl) throws Exception {
    List<MessagesVO> msgList = new ArrayList();
    MessagesBD messageBD = new MessagesBD();
    String sendToUserIds = getToUserId(dataId);
    if (sendToUserIds != null && !"".equals(sendToUserIds)) {
      String[] arr = sendToUserIds.split(",");
      for (int i = 0; i < arr.length; i++) {
        MessagesVO msgVO = new MessagesVO();
        msgVO.setMessage_date_begin(new Date());
        msgVO.setMessage_date_end(new Date("2050/1/1"));
        msgVO.setMessage_send_UserId(Long.valueOf(userId).longValue());
        msgVO.setMessage_send_UserName(userName);
        msgVO.setMessage_show(1);
        msgVO.setMessage_status(1);
        msgVO.setMessage_time(new Date());
        msgVO.setMessage_title(messageTitle);
        msgVO.setMessage_toUserId(Long.valueOf(arr[i]).longValue());
        msgVO.setMessage_type(messageType);
        msgVO.setData_id(Long.valueOf(dataId).longValue());
        msgVO.setMessage_url(messageUrl);
        msgList.add(msgVO);
        if (msgList != null)
          messageBD.messageArrayAdd(msgList); 
      } 
    } 
  }
  
  public String getToUserId(String id) {
    String toUserId = "";
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String sql = "SELECT DISTINCT empId FROM VEH_APPLY WHERE STATUS='2' AND DOMAIN_ID=0 AND VOITUREID=" + id;
    try {
      base.begin();
      rs = base.executeQuery(sql);
      while (rs.next())
        toUserId = String.valueOf(toUserId) + rs.getString(1) + ","; 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return toUserId;
  }
  
  public List listVoitureCarPools(Long applyId) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      list = this.session.createQuery(
          "select po from com.js.oa.routine.voiture.po.VoitureApplyPO po where po.carPoolId=" + 
          applyId)
        .list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public boolean updateCarPoolToMainRec(Long id) throws Exception {
    begin();
    boolean bl = false;
    VoitureApplyPO vapo = new VoitureApplyPO();
    try {
      vapo = (VoitureApplyPO)this.session.load(VoitureApplyPO.class, 
          new Long(id.longValue()));
      vapo.setCarPoolId("0");
      this.session.flush();
      bl = true;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return bl;
  }
  
  public boolean updateCarPoolToOtherRec(Long id, String carPoolId) throws Exception {
    begin();
    boolean bl = false;
    VoitureApplyPO vapo = new VoitureApplyPO();
    try {
      vapo = (VoitureApplyPO)this.session.load(VoitureApplyPO.class, 
          new Long(id.longValue()));
      vapo.setCarPoolId(carPoolId);
      this.session.flush();
      bl = true;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return bl;
  }
}
