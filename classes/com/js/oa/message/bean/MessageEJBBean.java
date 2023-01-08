package com.js.oa.message.bean;

import com.js.oa.message.po.MsCountPO;
import com.js.oa.message.po.MsDescribePO;
import com.js.oa.message.po.MsInfoFlowPO;
import com.js.oa.message.po.MsInfoListPO;
import com.js.oa.userdb.util.DbOpt;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.ConversionString;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;

public class MessageEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public HashMap listView(Long userID) throws Exception {
    HashMap<Object, Object> listInfo = new HashMap<Object, Object>();
    String count = "0";
    begin();
    try {
      List describeList = this.session.createQuery(
          " from com.js.oa.message.po.MsDescribePO describe  where  describe.sendId=" + 
          userID).list();
      count = String.valueOf(describeList.size());
      listInfo.put("describe", count);
      List receivelist = this.session.createQuery(
          "from com.js.oa.message.po.MsInfoListPO receive  where  receive.receiveId=" + 
          userID + 
          " and receive.msSign=2")
        .list();
      count = String.valueOf(receivelist.size());
      listInfo.put("receivelist", count);
      List sendlist = this.session.createQuery(
          " from com.js.oa.message.po.MsInfoListPO sendlist  where  sendlist.sendId=" + 
          userID + 
          " and sendlist.msSign=1")
        .list();
      int send = sendlist.size();
      List flowlist = this.session.createQuery(
          "from com.js.oa.message.po.MsInfoFlowPO flow  where  flow.sendId=" + 
          userID)
        .list();
      int flow = flowlist.size() + send;
      count = String.valueOf(flow);
      listInfo.put("sendlist", count);
      List dellist = this.session.createQuery(
          " from com.js.oa.message.po.MsInfoListPO dellist  where  (( dellist.sendId=" + 
          userID + 
          " and (dellist.sendTime is not null and dellist.sendTime <> ' ') ) or ( dellist.receiveId=" + 
          userID + 
          " and (dellist.receiveTime is not null and dellist.receiveTime <> ' '))) and dellist.msSign=0")
        .list();
      count = String.valueOf(dellist.size());
      listInfo.put("dellist", count);
      this.session.flush();
    } catch (Exception e) {
      System.out.println(
          "MessageEJBBean in listView messageEJB Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return listInfo;
  }
  
  public boolean isSendOutMsg(Long userID, String userName) throws Exception {
    boolean result = false;
    begin();
    try {
      List sendedcount = this.session.createQuery(
          " from com.js.oa.message.po.MsOutMoPO outPO  where  outPO.sendOutId=" + 
          userID + 
          " and outPO.sendOutMan='" + userName + "'")
        .list();
      if (sendedcount.size() != 0)
        result = true; 
    } catch (Exception e) {
      System.out.println("isSendOutMsg==== MessageEJB=== Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public boolean isSendMsg(Long userID, String userName, String domainId) throws Exception {
    boolean result = true;
    begin();
    try {
      List<Object[]> sendedcount = this.session.createQuery(
          "select sendlist.countId,sendlist.sumCount from com.js.oa.message.po.MsCountPO sendlist  where  sendlist.sendCountId=" + 
          userID + 
          " and sendlist.sendCountMan='" + userName + "'")
        .list();
      long countMsg = 0L;
      if (sendedcount.size() == 0) {
        MsCountPO msCount = new MsCountPO();
        msCount.setSendCountId(userID);
        msCount.setSendCountMan(userName);
        msCount.setSumCount(new Long(0L));
        msCount.setCountDate(new Date());
        msCount.setDomainId(new Long(domainId));
        this.session.save(msCount);
        this.session.flush();
      } else {
        Object[] obj1 = sendedcount.get(0);
        countMsg = Long.parseLong(obj1[1].toString());
        List<Object[]> sendedLimit = this.session.createQuery(
            "select sendlimit.sendLimitId,sendlimit.limitCount from com.js.oa.message.po.MsLimitPO sendlimit ")
          .list();
        for (int i = 0; i < sendedLimit.size(); i++) {
          Object[] obj = sendedLimit.get(i);
          if (obj[0] != null) {
            ConversionString conversionString = 
              new ConversionString(
                obj[0].toString());
            String ids = conversionString.getUserIdString();
            String[] idsArr = ids.split(",");
            for (int j = 0; j < idsArr.length; j++) {
              if (userID.toString().equals(idsArr[j]) && 
                countMsg - Long.parseLong(obj[1].toString()) > 
                0L) {
                result = false;
                break;
              } 
            } 
          } 
        } 
      } 
    } catch (Exception e) {
      System.out.println("isSendMsg==== MessageEJB=== Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public boolean delSearchMessage(String ids, String status) throws Exception {
    boolean result = false;
    begin();
    try {
      String[] idsArr = ids.split(",");
      for (int i = 0; i < idsArr.length; i++) {
        MsInfoListPO deletedbosPO = (MsInfoListPO)this.session.load(
            MsInfoListPO.class, 
            Long.valueOf(idsArr[i]));
        deletedbosPO.setMsSign("0");
        this.session.save(deletedbosPO);
        result = true;
      } 
      this.session.flush();
    } catch (Exception e) {
      System.out.println("delSearchMessage==== MessageEJB=== Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public boolean delRealSearchMessage(String ids, String status) throws Exception {
    boolean result = false;
    begin();
    try {
      String[] idsArr = ids.split(",");
      for (int i = 0; i < idsArr.length; i++) {
        MsInfoListPO describePO = (MsInfoListPO)this.session.load(
            MsInfoListPO.class, 
            Long.valueOf(idsArr[i]));
        describePO.setMsSign("-1");
        this.session.saveOrUpdate(describePO);
      } 
      this.session.flush();
      result = true;
    } catch (Exception e) {
      System.out.println(
          "delRealSearchMessage==== MessageEJB=== Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public MsDescribePO listDescribe(String describId) throws Exception {
    MsDescribePO msdescribPO = null;
    begin();
    try {
      Long id = Long.valueOf(describId);
      msdescribPO = (MsDescribePO)this.session.load(MsDescribePO.class, id);
    } catch (Exception e) {
      System.out.println("MessageEJBBean in listDescribe EJB Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return msdescribPO;
  }
  
  public boolean addDescribe(String tmpMbgroup, String tmpMassageMan, String tmpMobilePhone, String content, Long userID, String userName, String outmobilCode) throws Exception {
    boolean result = false;
    Date curDate = new Date();
    begin();
    try {
      if (tmpMbgroup != null && !tmpMbgroup.equals("")) {
        String[] idsArr = tmpMbgroup.split(",");
        String[] tmpMan = tmpMassageMan.split(",");
        String[] tmpMobilePh = tmpMobilePhone.split(",");
        for (int i = 0; i < idsArr.length; i++) {
          MsDescribePO msdescribe = new MsDescribePO();
          msdescribe.setMsContent(content);
          msdescribe.setReceiveCode(tmpMobilePh[i]);
          msdescribe.setReceiveId(Long.valueOf(idsArr[i]));
          msdescribe.setReceiveMan(tmpMan[i]);
          msdescribe.setSendId(userID);
          msdescribe.setSendMan(userName);
          msdescribe.setSendTime(curDate);
          this.session.save(msdescribe);
          this.session.flush();
        } 
        if (outmobilCode != null && !outmobilCode.equals("")) {
          MsDescribePO msdescribeOUt = new MsDescribePO();
          msdescribeOUt.setMsContent(content);
          msdescribeOUt.setSendId(userID);
          msdescribeOUt.setSendMan(userName);
          msdescribeOUt.setSendTime(curDate);
          msdescribeOUt.setReceiveCode(outmobilCode);
          this.session.save(msdescribeOUt);
          this.session.flush();
        } 
        result = true;
      } else if (outmobilCode != null && !outmobilCode.equals("")) {
        MsDescribePO msscribe = new MsDescribePO();
        msscribe.setMsContent(content);
        msscribe.setSendId(userID);
        msscribe.setSendMan(userName);
        msscribe.setSendTime(curDate);
        msscribe.setReceiveCode(outmobilCode);
        this.session.save(msscribe);
        this.session.flush();
        result = true;
      } 
    } catch (Exception e) {
      System.out.println("MessageEJBBean in addDescribe EJB Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public boolean realClearDeletedBox(Long userID) throws Exception {
    boolean result = false;
    begin();
    try {
      List<MsInfoListPO> list = this.session.createQuery(
          "select receive from com.js.oa.message.po.MsInfoListPO receive where (receive.receiveId=" + 
          userID + " or receive.sendId=" + userID + 
          " ) and receive.msSign=0").list();
      if (list != null && list.size() > 0)
        for (int i = 0; i < list.size(); i++) {
          MsInfoListPO po = list.get(i);
          po.setMsSign("-1");
          this.session.saveOrUpdate(po);
        }  
      this.session.flush();
      result = true;
    } catch (Exception e) {
      System.out.println(
          "realClearDeletedBox==== MessageEJB=== Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public boolean delDescribeBatch(String ids) throws Exception {
    boolean result = false;
    begin();
    try {
      String[] idsArr = ids.split(",");
      for (int i = 0; i < idsArr.length; i++) {
        MsDescribePO describePO = (MsDescribePO)this.session.get(MsDescribePO.class, Long.valueOf(idsArr[i]));
        if (describePO != null)
          this.session.delete(describePO); 
      } 
      this.session.flush();
      result = true;
    } catch (Exception e) {
      System.out.println("delDescribeBatch==== MessageEJB=== Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public boolean realClearDescribeBox(Long userID) throws Exception {
    boolean result = false;
    begin();
    try {
      this.session.delete(
          "from com.js.oa.message.po.MsDescribePO describe  where  describe.sendId=" + 
          userID);
      this.session.flush();
      result = true;
    } catch (Exception e) {
      System.out.println(
          "realClearDescribeBox==== MessageEJB=== Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public boolean deletedboxBatch(String ids) throws Exception {
    boolean result = false;
    begin();
    try {
      String[] idsArr = ids.split(",");
      for (int i = 0; i < idsArr.length; i++) {
        MsInfoListPO deletedbosPO = (MsInfoListPO)this.session.load(
            MsInfoListPO.class, 
            Long.valueOf(idsArr[i]));
        deletedbosPO.setMsSign("-1");
        this.session.saveOrUpdate(deletedbosPO);
        result = true;
      } 
      this.session.flush();
    } catch (Exception e) {
      System.out.println("deletedboxBatch==== MessageEJB=== Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public boolean delRealReceiveBoxBatch(String ids) throws Exception {
    boolean result = false;
    begin();
    try {
      String[] idsArr = ids.split(",");
      for (int i = 0; i < idsArr.length; i++) {
        MsInfoListPO deletedbosPO = (MsInfoListPO)this.session.load(
            MsInfoListPO.class, 
            Long.valueOf(idsArr[i]));
        deletedbosPO.setMsSign("-1");
        this.session.saveOrUpdate(deletedbosPO);
        result = true;
      } 
      this.session.flush();
    } catch (Exception e) {
      System.out.println(
          "delRealReceiveBoxBatch==== MessageEJB=== Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public boolean receiveToDescribe(String ids) throws Exception {
    boolean result = false;
    begin();
    try {
      String[] idsArr = ids.split(",");
      for (int i = 0; i < idsArr.length; i++) {
        MsInfoListPO deletedbosPO = (MsInfoListPO)this.session.load(
            MsInfoListPO.class, 
            Long.valueOf(idsArr[i]));
        deletedbosPO.setMsSign("0");
        this.session.save(deletedbosPO);
        result = true;
      } 
      this.session.flush();
    } catch (Exception e) {
      System.out.println("receiveToDescribe==== MessageEJB=== Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public boolean receivedClearToDescribe(Long userID) throws Exception {
    boolean result = false;
    begin();
    try {
      List list = this.session.createQuery(
          "from com.js.oa.message.po.MsInfoListPO receive  where  receive.receiveId=" + 
          userID + 
          " and receive.msSign=2").list();
      Iterator<MsInfoListPO> iterator = list.iterator();
      MsInfoListPO deletedbosPO = null;
      while (iterator != null && iterator.hasNext()) {
        deletedbosPO = iterator.next();
        deletedbosPO.setMsSign("0");
        this.session.save(deletedbosPO);
      } 
      this.session.flush();
      result = true;
    } catch (Exception e) {
      System.out.println(
          "receivedClearToDescribe==== MessageEJB=== Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public boolean delRealSendedBoxBatch(String ids) throws Exception {
    boolean result = false;
    begin();
    try {
      String[] idsArr = ids.split(",");
      for (int i = 0; i < idsArr.length; i++) {
        MsInfoListPO deletedbosPO = (MsInfoListPO)this.session.load(
            MsInfoListPO.class, 
            Long.valueOf(idsArr[i]));
        deletedbosPO.setMsSign("-1");
        this.session.saveOrUpdate(deletedbosPO);
        result = true;
      } 
      this.session.flush();
    } catch (Exception e) {
      System.out.println(
          "delRealSendedBoxBatch==== MessageEJB=== Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public boolean sendedToDescribe(String ids) throws Exception {
    boolean result = false;
    begin();
    try {
      String[] idsArr = ids.split(",");
      for (int i = 0; i < idsArr.length; i++) {
        MsInfoListPO deletedbosPO = (MsInfoListPO)this.session.load(
            MsInfoListPO.class, 
            Long.valueOf(idsArr[i]));
        deletedbosPO.setMsSign("0");
        this.session.save(deletedbosPO);
        result = true;
      } 
      this.session.flush();
    } catch (Exception e) {
      System.out.println("sendedToDescribe==== MessageEJB=== Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public boolean sendedClearToDescribe(Long userID) throws Exception {
    boolean result = false;
    begin();
    try {
      List list = this.session.createQuery("from com.js.oa.message.po.MsInfoListPO sendlist where  sendlist.sendId=" + 
          userID + " and sendlist.msSign=1")
        .list();
      Iterator<MsInfoListPO> iterator = list.iterator();
      MsInfoListPO deletedbosPO = null;
      while (iterator != null && iterator.hasNext()) {
        deletedbosPO = iterator.next();
        deletedbosPO.setMsSign("0");
        this.session.save(deletedbosPO);
      } 
      this.session.flush();
      result = true;
    } catch (Exception e) {
      System.out.println(
          "sendedClearToDescribe==== MessageEJB=== Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public boolean addMsgFlow(String tmpMbgroup, String tmpMassageMan, String tmpMobilePhone, String content, Long userID, String userName, String outmobilCode) throws Exception {
    boolean result = false;
    Date curDate = new Date();
    begin();
    Connection conn = (new DataSourceBase()).getDataSource().getConnection();
    try {
      if (tmpMbgroup != null && !tmpMbgroup.equals("")) {
        String[] idsArr = tmpMbgroup.split(",");
        String[] tmpMan = tmpMassageMan.split(",");
        String[] tmpMobilePh = tmpMobilePhone.split(",");
        for (int i = 0; i < tmpMobilePh.length; i++) {
          MsInfoFlowPO msFlowMsg = new MsInfoFlowPO();
          msFlowMsg.setMsContent(content);
          String serial = null;
          String serialPwd = null;
          Statement stat = conn.createStatement();
          ResultSet rs = null;
          String Sql = "select a.EMPNAME, a.SERIAL, a.SERIALPWD from ORG_EMPLOYEE a where a.EMP_ID = '" + userID + "'";
          rs = stat.executeQuery(Sql);
          if (rs.next()) {
            String empName = rs.getString(1);
            serial = rs.getString(2);
            serialPwd = rs.getString(3);
          } 
          rs.close();
          stat.close();
          msFlowMsg.setReceiveCode(tmpMobilePh[i]);
          msFlowMsg.setReceiveId(Long.valueOf(idsArr[i]));
          msFlowMsg.setReceiveMan(tmpMan[i]);
          msFlowMsg.setSendId(userID);
          msFlowMsg.setSendMan(userName);
          msFlowMsg.setSendTime(curDate);
          msFlowMsg.setSerial(serial);
          msFlowMsg.setSerialPwd(serialPwd);
          this.session.save(msFlowMsg);
          this.session.flush();
        } 
        if (outmobilCode != null && !outmobilCode.equals("")) {
          String[] outmobil = outmobilCode.split(",");
          for (int j = 0; j < outmobil.length; j++) {
            MsInfoFlowPO msFlowOUt = new MsInfoFlowPO();
            msFlowOUt.setMsContent(content);
            msFlowOUt.setSendId(userID);
            msFlowOUt.setSendMan(userName);
            msFlowOUt.setSendTime(curDate);
            msFlowOUt.setReceiveCode(outmobil[j]);
            this.session.save(msFlowOUt);
            this.session.flush();
          } 
        } 
        result = true;
      } else if (outmobilCode != null && !outmobilCode.equals("")) {
        String[] outmobil = outmobilCode.split(",");
        String serial = null;
        String serialPwd = null;
        Statement stat = conn.createStatement();
        ResultSet rs = null;
        String Sql = "select a.EMPNAME, a.SERIAL, a.SERIALPWD from ORG_EMPLOYEE a where a.EMP_ID = '" + userID + "'";
        rs = stat.executeQuery(Sql);
        if (rs.next()) {
          String empName = rs.getString(1);
          serial = rs.getString(2);
          serialPwd = rs.getString(3);
        } 
        rs.close();
        stat.close();
        for (int i = 0; i < outmobil.length; i++) {
          MsInfoFlowPO msflow = new MsInfoFlowPO();
          msflow.setMsContent(content);
          msflow.setSendId(userID);
          msflow.setSendMan(userName);
          msflow.setSendTime(curDate);
          msflow.setReceiveCode(outmobil[i]);
          msflow.setSerial(serial);
          msflow.setSerialPwd(serialPwd);
          this.session.save(msflow);
          this.session.flush();
          result = true;
        } 
      } 
      this.session.flush();
    } catch (Exception e) {
      System.out.println("MessageEJBBean in addMsgFlow EJB Exception:" + 
          e.toString());
      throw e;
    } finally {
      if (conn != null)
        conn.close(); 
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public String selectManSign(Long manId, String manName) throws HibernateException {
    begin();
    String sign = null;
    try {
      List listInner = this.session.createQuery(
          "select employee.empId  from com.js.system.vo.usermanager.EmployeeVO employee where employee.empId=" + 
          manId + 
          " and employee.empName='" + manName + "'").list();
      if (listInner.size() != 0) {
        sign = "内部联系人";
      } else {
        List<Object[]> list = this.session.createQuery(
            "select personClass.classType,personClass.id from com.js.oa.personalwork.person.po.PersonClassPO personClass join personClass.linkmen  person where person.id=" + 
            manId + " and person.linkManName='" + manName + "'")
          .list();
        if (list.size() != 0) {
          Object[] obj = list.get(0);
          String type = obj[0].toString();
          if (type.equals("0")) {
            sign = "个人联系人";
          } else {
            sign = "公共联系人";
          } 
        } 
      } 
      this.session.flush();
    } catch (HibernateException e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return sign;
  }
  
  public boolean modelSendMsg(String ids, String contents, String mobile, String domainId, Date sendTime, Long dataId) throws Exception {
    boolean result = false;
    Date curDate = sendTime;
    begin();
    try {
      if (ids != null && !ids.equals("")) {
        String[] idsArr = ids.split(",");
        for (int i = 0; i < idsArr.length; i++) {
          List<Object[]> list = this.session.createQuery("select emp.empId, emp.empMobilePhone,emp.empName from com.js.system.vo.usermanager.EmployeeVO emp where  emp.empId=" + 
              idsArr[i] + " and (emp.empMobilePhone is not null and emp.empMobilePhone <> ' ') ")
            .list();
          if (list.size() != 0) {
            Object[] obj = list.get(0);
            String milbilecode = obj[1].toString();
            MsInfoFlowPO msFlowMsg = new MsInfoFlowPO();
            msFlowMsg.setSendId(new Long(0L));
            msFlowMsg.setSendMan("系统管理员");
            msFlowMsg.setMsContent(contents);
            msFlowMsg.setSendTime(curDate);
            msFlowMsg.setReceiveCode(milbilecode);
            msFlowMsg.setModelSend("systemSend");
            msFlowMsg.setReceiveId(Long.valueOf(obj[0].toString()));
            msFlowMsg.setReceiveMan(obj[2].toString());
            msFlowMsg.setDomainId(domainId);
            msFlowMsg.setDataId(dataId);
            this.session.save(msFlowMsg);
            this.session.flush();
          } 
          result = true;
        } 
      } 
      if (mobile != null && !mobile.equals("")) {
        String[] idArr = mobile.split(",");
        for (int i = 0; i < idArr.length; i++) {
          MsInfoFlowPO msFlowMsg = new MsInfoFlowPO();
          msFlowMsg.setSendId(new Long(0L));
          msFlowMsg.setSendMan("系统管理员");
          msFlowMsg.setMsContent(contents);
          msFlowMsg.setSendTime(curDate);
          msFlowMsg.setReceiveCode(idArr[i]);
          msFlowMsg.setDomainId(domainId);
          msFlowMsg.setDataId(dataId);
          this.session.save(msFlowMsg);
          msFlowMsg.setModelSend("systemSend");
          this.session.flush();
          result = true;
        } 
      } 
    } catch (Exception e) {
      System.out.println(
          "MessageEJBBean in modelSendMsg 系统发短信 EJB Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public boolean modSysFileMsg(String ids, String contents, String mobile, String fileSign, String domainId) throws Exception {
    boolean result = false;
    Date curDate = new Date();
    begin();
    try {
      if (ids != null && !ids.equals("")) {
        String[] idsArr = ids.split(",");
        for (int i = 0; i < idsArr.length; i++) {
          List<Object[]> list = this.session.createQuery("select emp.empId, emp.empMobilePhone,emp.empName from com.js.system.vo.usermanager.EmployeeVO emp where  emp.empId=" + 
              idsArr[i] + " and (emp.empMobilePhone is not null and emp.empMobilePhone <> ' ') ")
            .list();
          if (list.size() != 0) {
            Object[] obj = list.get(0);
            String milbilecode = obj[1].toString();
            MsInfoFlowPO msFlowMsg = new MsInfoFlowPO();
            msFlowMsg.setSendId(new Long(0L));
            msFlowMsg.setSendMan("系统管理员");
            msFlowMsg.setMsContent(contents);
            msFlowMsg.setSendTime(curDate);
            msFlowMsg.setReceiveCode(milbilecode);
            msFlowMsg.setModelSend(fileSign);
            msFlowMsg.setReceiveId(Long.valueOf(obj[0].toString()));
            msFlowMsg.setReceiveMan(obj[2].toString());
            msFlowMsg.setDomainId(domainId);
            this.session.save(msFlowMsg);
            this.session.flush();
          } 
          result = true;
        } 
      } 
      if (mobile != null && !mobile.equals("")) {
        String[] idArr = mobile.split(",");
        for (int i = 0; i < idArr.length; i++) {
          MsInfoFlowPO msFlowMsg = new MsInfoFlowPO();
          msFlowMsg.setSendId(new Long(0L));
          msFlowMsg.setSendMan("系统管理员");
          msFlowMsg.setMsContent(contents);
          msFlowMsg.setSendTime(curDate);
          msFlowMsg.setReceiveCode(idArr[i]);
          msFlowMsg.setDomainId(domainId);
          this.session.save(msFlowMsg);
          msFlowMsg.setModelSend(fileSign);
          this.session.flush();
          result = true;
        } 
      } 
    } catch (Exception e) {
      System.out.println(
          "MessageEJBBean in modelFileSendMsg 系统公文发短信 EJB Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public List selectGroupUser(String id) throws Exception {
    begin();
    List list = null;
    try {
      Query query = this.session.createQuery("select emp.empId,emp.empName, emp.empMobilePhone from com.js.system.vo.groupmanager.GroupVO groupVO join groupVO.employees emp where groupVO.groupId=" + 
          id + 
          " and (emp.empMobilePhone is not null and emp.empMobilePhone <> ' ')");
      list = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List selectGroupUserEmail(String id) throws Exception {
    begin();
    List list = null;
    try {
      Query query = this.session.createQuery("select emp.empId,emp.empName, emp.empEmail from com.js.system.vo.groupmanager.GroupVO groupVO join groupVO.employees emp where groupVO.groupId=" + 
          id + 
          " and (emp.empMobilePhone is not null and emp.empMobilePhone <> ' ') and emp.empEmail is not null and emp.empEmail <> ''");
      list = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List getFlowAndList(Long userID) throws Exception {
    begin();
    ArrayList<ArrayList<Object>> list = new ArrayList();
    List<Object[]> list1 = null;
    List<Object[]> list2 = null;
    StringBuffer whereSql1 = new StringBuffer("where 1=1 ");
    whereSql1.append(" and msflowPO.sendId=" + userID);
    whereSql1.append(" order by msflowPO.messageId desc");
    StringBuffer whereSql2 = new StringBuffer("where 1=1 ");
    whereSql2.append(" and recievePO.sendId=" + userID);
    whereSql2.append(" and  recievePO.msSign=1 ");
    whereSql2.append(" order by recievePO.listId desc");
    try {
      Query query1 = this.session.createQuery("select msflowPO.messageId,msflowPO.receiveMan,msflowPO.msContent,msflowPO.sendTime,msflowPO.sendMan  from com.js.oa.message.po.MsInfoFlowPO msflowPO " + 
          whereSql1);
      list1 = query1.list();
      Query query2 = this.session.createQuery("select recievePO.listId,recievePO.receiveMan,recievePO.msContent,recievePO.sendTime,recievePO.msSign,recievePO.receiveCode  from com.js.oa.message.po.MsInfoListPO recievePO " + 
          whereSql2);
      list2 = query2.list();
      Object[] obj = (Object[])null;
      if (list1 != null)
        for (int i = 0; i < list1.size(); i++) {
          obj = list1.get(i);
          ArrayList<Object> list11 = new ArrayList();
          list11.add(obj[0]);
          list11.add(obj[1]);
          list11.add(obj[2]);
          list11.add(obj[3]);
          list11.add(obj[4]);
          list11.add("");
          list11.add("未发送");
          list11.trimToSize();
          list.add(list11);
        }  
      if (list2 != null)
        for (int i = 0; i < list2.size(); i++) {
          ArrayList<Object> list22 = new ArrayList();
          obj = list2.get(i);
          list22.add(obj[0]);
          list22.add(obj[1]);
          list22.add(obj[2]);
          list22.add(obj[3]);
          list22.add(obj[4]);
          list22.add(obj[5]);
          list22.add("已发送");
          list22.trimToSize();
          list.add(list22);
        }  
      if (list != null)
        list.trimToSize(); 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List selectPersonUser(String id, String userId, String orgId) throws Exception {
    begin();
    List list = null;
    try {
      String ql = 
        " select personPO.id,personPO.linkManName, personPO.mobilePhone  from com.js.oa.personalwork.person.po.PersonPO personPO  join personPO.linkManClass personClassPO  where (personPO.mobilePhone is not null and personPO.mobilePhone <>' ') and personClassPO.id=" + 


        
        id;
      if (userId != null && userId.length() > 0 && orgId != null && 
        orgId.length() > 0) {
        String tmpSql = "";
        String databaseType = 
          SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "select a.orgId from com.js.system.vo.organizationmanager.OrganizationVO a where (select b.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO b where b.orgId=" + 
            orgId + ") like concat('%$', a.orgId, '$%')";
        } else {
          tmpSql = "select a.orgId from com.js.system.vo.organizationmanager.OrganizationVO a where (select b.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO b where b.orgId=" + 
            orgId + 
            ") like JSDB.FN_LINKCHAR('%$', JSDB.FN_LINKCHAR(a.orgId, '$%'))";
        } 
        List<String> orgList = this.session.createQuery(tmpSql).list();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < orgList.size(); i++)
          sb.append(" personPO.viewScope like '%*" + orgList.get(i) + 
              "*%' or "); 
        sb.append(" 1 > 1 ");
        ql = String.valueOf(ql) + 
          " and (personPO.viewScope = '0' or personPO.viewScope like '%$" + 
          userId + "$%' or " + sb.toString() + ") ";
      } 
      Query query = this.session.createQuery(ql);
      list = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List selectPersonUserEmail(String id, String userId, String orgId) throws Exception {
    begin();
    List list = null;
    try {
      String ql = 
        " select personPO.id,personPO.linkManName, personPO.linkManEmail  from com.js.oa.personalwork.person.po.PersonPO personPO  join personPO.linkManClass personClassPO  where personPO.linkManEmail is not null and personPO.linkManEmail <> '' and personClassPO.id=" + 


        
        id;
      if (userId != null && userId.length() > 0 && orgId != null && 
        orgId.length() > 0) {
        String tmpSql = "";
        String databaseType = 
          SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "select a.orgId from com.js.system.vo.organizationmanager.OrganizationVO a where (select b.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO b where b.orgId=" + 
            orgId + ") like concat('%$', a.orgId, '$%')";
        } else {
          tmpSql = "select a.orgId from com.js.system.vo.organizationmanager.OrganizationVO a where (select b.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO b where b.orgId=" + 
            orgId + 
            ") like JSDB.FN_LINKCHAR('%$', JSDB.FN_LINKCHAR(a.orgId, '$%'))";
        } 
        List<String> orgList = this.session.createQuery(tmpSql).list();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < orgList.size(); i++)
          sb.append(" personPO.viewScope like '%*" + orgList.get(i) + 
              "*%' or "); 
        sb.append(" 1 > 1 ");
        ql = String.valueOf(ql) + 
          " and (personPO.viewScope = '0' or personPO.viewScope like '%$" + 
          userId + "$%' or " + sb.toString() + ") ";
      } 
      Query query = this.session.createQuery(ql);
      list = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public Map getSubOrgAndUsers(String orgId, String currentOrg) throws Exception {
    HashMap<Object, Object> map = new HashMap<Object, Object>(2);
    begin();
    try {
      if (!"".equals(currentOrg)) {
        map.put("org", 
            this.session.createQuery("SELECT org.orgId,org.orgName FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE org.orgParentOrgId=" + 
              orgId + 
              " AND org.orgStatus=0 and org.orgId<>" + 
              currentOrg + 
              " ORDER BY org.orgOrderCode").list());
      } else {
        map.put("org", 
            this.session.createQuery("SELECT org.orgId,org.orgName FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE org.orgParentOrgId=" + 
              orgId + 
              " AND org.orgStatus=0 ORDER BY org.orgOrderCode")
            .list());
      } 
      map.put("user", 
          this.session.createQuery("SELECT emp.empId,emp.empName, emp.empMobilePhone FROM com.js.system.vo.usermanager.EmployeeVO emp join emp.organizations org WHERE org.orgId=" + orgId + 
            " AND emp.userIsActive=1 and emp.userIsDeleted=0 and (emp.empMobilePhone is not null and emp.empMobilePhone <> ' ') ORDER BY emp.userOrderCode")
          .list());
    } catch (Exception e) {
      System.out.println("error messageEJB :" + e.getMessage());
      throw e;
    } finally {
      this.session.close();
    } 
    return map;
  }
  
  public Map getSubOrgAndUsersEmail(String orgId, String currentOrg) throws Exception {
    HashMap<Object, Object> map = new HashMap<Object, Object>(2);
    begin();
    try {
      if (!"".equals(currentOrg)) {
        map.put("org", 
            this.session.createQuery("SELECT org.orgId,org.orgName FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE org.orgParentOrgId=" + 
              orgId + 
              " AND org.orgStatus=0 and org.orgId<>" + 
              currentOrg + 
              " ORDER BY org.orgOrderCode").list());
      } else {
        map.put("org", 
            this.session.createQuery("SELECT org.orgId,org.orgName FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE org.orgParentOrgId=" + 
              orgId + 
              " AND org.orgStatus=0 ORDER BY org.orgOrderCode")
            .list());
      } 
      map.put("user", 
          this.session.createQuery("SELECT emp.empId,emp.empName, emp.empEmail FROM com.js.system.vo.usermanager.EmployeeVO emp join emp.organizations org WHERE org.orgId=" + orgId + 
            " AND emp.userIsActive=1 and emp.userIsDeleted=0 and (emp.empMobilePhone is not null and emp.empMobilePhone <> ' ') and emp.empEmail is not null and emp.empEmail <> '' ORDER BY emp.userOrderCode")
          .list());
      System.out.println("------------->>>>>>>>>>>>>>" + ((List)map.get("user")).size());
    } catch (Exception e) {
      System.out.println("error messageEJB :" + e.getMessage());
      throw e;
    } finally {
      this.session.close();
    } 
    return map;
  }
  
  public List getSubOrgs(Long parentId) throws Exception {
    List orgArray = new ArrayList();
    try {
      begin();
      String sql = 
        "SELECT organization  FROM com.js.system.vo.organizationmanager.OrganizationVO organization WHERE organization.orgParentOrgId=" + 
        
        parentId.longValue() + 
        " AND organization.orgStatus=0 ORDER BY organization.orgOrderCode";
      orgArray = this.session.createQuery(sql).list();
      this.transaction.commit();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return orgArray;
  }
  
  public List getUserList(String para, String vo, String where) throws Exception {
    List userList = null;
    try {
      begin();
      StringBuffer sqlBuffer = new StringBuffer();
      sqlBuffer.append("SELECT ");
      sqlBuffer.append(para);
      sqlBuffer.append(" FROM ");
      sqlBuffer.append(vo);
      sqlBuffer.append(where);
      Query query = this.session.createQuery(sqlBuffer.toString());
      userList = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return userList;
  }
  
  public String getMsAccountInfo(String domainId) throws Exception {
    String result = "";
    begin();
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stat = conn.createStatement();
      String databaseType = 
        DbOpt.dbtype;
      if ("oracle".equals(databaseType)) {
        databaseType = "nvl";
      } else if ("mssqlserver".equals(databaseType)) {
        databaseType = "isnull";
      } else {
        databaseType = "ifnull";
      } 
      ResultSet rs = stat.executeQuery("select sum(" + databaseType + 
          "(BOOK_COUNT,0)) from MS_ACCOUNTBOOK accountBook where accountBook.DOMAIN_ID=" + 
          domainId);
      if (rs.next()) {
        result = String.valueOf(result) + rs.getObject(1) + ";";
      } else {
        result = String.valueOf(result) + "0;";
      } 
      rs = stat.executeQuery("select sum(" + databaseType + 
          "(BOOK_MONEY,0)) from MS_ACCOUNTBOOK accountBook where accountBook.DOMAIN_ID=" + 
          domainId);
      if (rs.next()) {
        result = String.valueOf(result) + rs.getObject(1) + ";";
      } else {
        result = String.valueOf(result) + "0;";
      } 
      rs = stat.executeQuery("select sum(" + databaseType + 
          "(SUNCOUTER,0)) from MS_COUNT count where count.DOMAIN_ID=" + 
          domainId);
      if (rs.next()) {
        result = String.valueOf(result) + rs.getObject(1) + ";";
      } else {
        result = String.valueOf(result) + "0;";
      } 
      result.replaceAll("null", "0");
      rs.close();
      stat.close();
      conn.close();
    } catch (Exception e) {
      System.out.println(
          "Error in MsAccountBookEJB's getMeAccountInfo Exception:" + 
          e.toString());
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public boolean modelSendMsg(String ids, String contents, String mobile, String domainId, String senderId, Date sendTime, Long dataId) throws Exception {
    boolean result = false;
    Date curDate = sendTime;
    Connection conn = null;
    Statement stat = null;
    begin();
    try {
      String empId = "0";
      String empName = "系统管理员";
      if (senderId != null && !"".equals(senderId))
        empId = senderId; 
      String serial = null;
      String serialPwd = null;
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stat = conn.createStatement();
      ResultSet rs = null;
      rs = stat.executeQuery("select a.EMPNAME, a.SERIAL, a.SERIALPWD from ORG_EMPLOYEE a where a.EMP_ID = '" + empId + "'");
      if (rs.next()) {
        empName = rs.getString(1);
        serial = rs.getString(2);
        serialPwd = rs.getString(3);
      } 
      rs.close();
      if (ids != null && !ids.equals("")) {
        String[] idsArr = ids.split(",");
        for (int i = 0; i < idsArr.length; i++) {
          List<Object[]> list = this.session.createQuery("select emp.empId, emp.empMobilePhone,emp.empName from com.js.system.vo.usermanager.EmployeeVO emp where  emp.empId=" + 
              idsArr[i] + " and (emp.empMobilePhone is not null and emp.empMobilePhone <> ' ') ")
            .list();
          if (list.size() != 0) {
            Object[] obj = list.get(0);
            String milbilecode = obj[1].toString();
            MsInfoFlowPO msFlowMsg = new MsInfoFlowPO();
            msFlowMsg.setSendId(new Long(empId));
            msFlowMsg.setSendMan(empName);
            msFlowMsg.setSerial(serial);
            msFlowMsg.setSerialPwd(serialPwd);
            msFlowMsg.setMsContent(contents);
            msFlowMsg.setSendTime(curDate);
            msFlowMsg.setReceiveCode(milbilecode);
            msFlowMsg.setModelSend("systemSend");
            msFlowMsg.setReceiveId(Long.valueOf(obj[0].toString()));
            msFlowMsg.setReceiveMan(obj[2].toString());
            msFlowMsg.setDomainId(domainId);
            msFlowMsg.setDataId(dataId);
            this.session.save(msFlowMsg);
            this.session.flush();
          } 
          result = true;
        } 
      } 
      if (mobile != null && !mobile.equals("")) {
        String[] idArr = mobile.split(",");
        for (int i = 0; i < idArr.length; i++) {
          MsInfoFlowPO msFlowMsg = new MsInfoFlowPO();
          msFlowMsg.setSendId(new Long(empId));
          msFlowMsg.setSendMan(empName);
          msFlowMsg.setSerial(serial);
          msFlowMsg.setSerialPwd(serialPwd);
          msFlowMsg.setMsContent(contents);
          msFlowMsg.setSendTime(curDate);
          msFlowMsg.setReceiveCode(idArr[i]);
          msFlowMsg.setDomainId(domainId);
          msFlowMsg.setDataId(dataId);
          this.session.save(msFlowMsg);
          msFlowMsg.setModelSend("systemSend");
          this.session.flush();
          result = true;
        } 
      } 
    } catch (Exception e) {
      System.out.println(
          "MessageEJBBean in modelSendMsg 系统发短信 EJB Exception:" + 
          e.toString());
      throw e;
    } finally {
      stat.close();
      conn.close();
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public List getSenderBySerial(String serial, String domainId) throws Exception {
    List<String> result = new ArrayList();
    begin();
    Connection conn = null;
    Statement stat = null;
    ResultSet rs = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stat = conn.createStatement();
      rs = stat.executeQuery(
          "select distinct a.EMPNAME from ORG_EMPLOYEE a where a.SERIAL = '" + 
          serial + "' " + ((domainId != null && !"".equals(domainId)) ? (" and a.DOMAIN_ID = '" + domainId + "'") : ""));
      while (rs.next())
        result.add(rs.getString(1)); 
    } catch (Exception e) {
      System.out.println(
          "MessageEJBBean in getSenderBySerial 根据分机号查找发短信人姓名 EJB Exception:" + 
          e.toString());
      throw e;
    } finally {
      rs.close();
      stat.close();
      conn.close();
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public String getSerialByUserId(String userId) throws Exception {
    String result = "";
    begin();
    Connection conn = null;
    Statement stat = null;
    ResultSet rs = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stat = conn.createStatement();
      rs = stat.executeQuery(
          "select a.SERIAL from ORG_EMPLOYEE a where a.EMP_ID = '" + userId + "' ");
      if (rs.next())
        result = (rs.getString(1) != null) ? rs.getString(1) : ""; 
    } catch (Exception e) {
      System.out.println(
          "MessageEJBBean in getSerialByUserId EJB Exception:" + 
          e.toString());
      throw e;
    } finally {
      rs.close();
      stat.close();
      conn.close();
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public String checkSendMsCount(String userId, String domainId) throws Exception {
    String result = "";
    begin();
    Connection conn = null;
    Statement stat = null;
    ResultSet rs = null;
    String where = "";
    String databaseType = 
      SystemCommon.getDatabaseType();
    long count = 0L;
    long mcount = 0L;
    long dcount = 0L;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stat = conn.createStatement();
      rs = stat.executeQuery(
          "select a.MSLIMITCOUNTER, a.MSMONTHCOUNT, a.MSDAYCOUNT from MS_LIMIT a where a.SENDID like '%$" + userId + "$%' " + ((domainId != null && !"".equals(domainId)) ? (" and a.DOMAIN_ID = '" + domainId + "'") : ""));
      if (rs.next()) {
        count = rs.getLong(1);
        mcount = rs.getLong(2);
        dcount = rs.getLong(3);
      } else {
        return "null";
      } 
      rs = null;
      rs = stat.executeQuery(
          "select count(a.messageid) from ms_infoflow a where a.SENDID='" + 
          userId + "' ");
      long ccount = 0L;
      if (rs.next())
        ccount = rs.getLong(1); 
      if (ccount >= count)
        return "c"; 
      Calendar cal = Calendar.getInstance();
      int y = cal.get(1);
      int m = cal.get(2) + 1;
      int days = cal.getActualMaximum(5);
      String mm = (m < 10) ? ("0" + m) : m;
      where = "";
      if (databaseType.indexOf("oracle") > -1) {
        where = " and to_char(a.SENDTIME, 'yyyy-MM-dd')>= '" + y + "-" + mm + "-01" + "' and to_char(a.SENDTIME, 'yyyy-MM-dd')<= '" + y + "-" + mm + "-" + days + "'";
      } else {
        where = " and convert(char(10),a.SENDTIME,20)= '" + y + "-" + mm + "-01" + "' and convert(char(10),a.SENDTIME,20)= '" + y + "-" + mm + "-" + days + "'";
      } 
      rs = null;
      rs = stat.executeQuery(
          "select count(a.messageid) from ms_infoflow a where a.SENDID='" + 
          userId + "' " + where);
      long mmcount = 0L;
      if (rs.next())
        mmcount = rs.getLong(1); 
      if (mmcount >= mcount)
        return "m"; 
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      String now = sdf.format(new Date());
      if (databaseType.indexOf("oracle") > -1) {
        where = " and to_char(a.SENDTIME, 'yyyy-MM-dd')= '" + now + "'";
      } else {
        where = " and convert(char(10),a.SENDTIME,20)= '" + now + "'";
      } 
    } catch (Exception e) {
      System.out.println(
          "MessageEJBBean in getSerialByUserId EJB Exception:" + 
          e.toString());
      throw e;
    } finally {
      rs.close();
      stat.close();
      conn.close();
      this.session.close();
      this.session = null;
    } 
    rs.close();
    stat.close();
    conn.close();
    this.session.close();
    this.session = null;
    return result;
  }
  
  public MsInfoListPO updateViewMsg(String msgId) throws Exception {
    MsInfoListPO result = null;
    begin();
    try {
      result = (MsInfoListPO)this.session.load(MsInfoListPO.class, new Long(msgId));
      if (result != null) {
        result.setIsViewed(new Integer(1));
        this.session.saveOrUpdate(result);
        this.session.flush();
        return result;
      } 
    } catch (Exception e) {
      System.out.println(
          "MessageEJBBean in updateViewMsg EJB Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public List getNewMsglist(String userIds) throws Exception {
    begin();
    List list = null;
    try {
      Query query = this.session.createQuery("select po from com.js.oa.message.po.MsInfoListPO po where po.receiveId in (" + 
          userIds + 
          ") and po.msSign = '2' and (po.isViewed is null or po.isViewed != 1)");
      list = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public String getUserPhone(String userId) {
    String userPhone = "";
    try {
      begin();
      Iterator it = this.session.createQuery("select po.empMobilePhone from com.js.system.vo.usermanager.EmployeeVO po where po.empId=" + userId).iterate();
      if (it.hasNext()) {
        Object obj = it.next();
        if (obj != null && !"null".equals(obj))
          userPhone = obj.toString(); 
      } 
      this.session.close();
    } catch (Exception e) {
      try {
        this.session.close();
      } catch (Exception exception) {}
      e.printStackTrace();
    } 
    return userPhone;
  }
}
