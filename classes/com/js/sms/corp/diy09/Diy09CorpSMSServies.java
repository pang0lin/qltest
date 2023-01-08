package com.js.sms.corp.diy09;

import com.js.sms.bbnyxt.BbnYxtSMS;
import com.js.sms.corp.ICorpSMSServies;
import com.js.sms.corp.MessageService;
import com.js.util.hibernate.HibernateBase;
import java.util.List;
import javax.ejb.SessionContext;

public class Diy09CorpSMSServies extends HibernateBase implements ICorpSMSServies {
  SessionContext sessionContext = null;
  
  public void sendMessage(String tmpMbgroup, String tmpMassageMan, String tmpMobilePhone, String content, Long userID, String userName, String outmobilCode, int priortiy) {
    String phoneList = "";
    if (tmpMobilePhone != null && tmpMobilePhone != "") {
      String[] tmpPhoneList = tmpMobilePhone.split(",");
      for (int i = 0; i < tmpPhoneList.length; i++)
        phoneList = String.valueOf(phoneList) + tmpPhoneList[i] + ","; 
    } 
    if (outmobilCode != null && outmobilCode != "") {
      String[] tmpPhoneList = outmobilCode.split(",");
      for (int i = 0; i < tmpPhoneList.length; i++)
        phoneList = String.valueOf(phoneList) + tmpPhoneList[i] + ","; 
    } 
    if (phoneList != "") {
      sendMessageByCorpSMS(phoneList, content, priortiy);
      MessageService messageService = new MessageService();
      messageService.addMsHistory(userID, phoneList, content, "1");
    } 
  }
  
  public void sendMessage(String tmpMbgroup, String tmpMassageMan, String tmpMobilePhone, String content, Long userID, String userName, String outmobilCode) {
    sendMessage(tmpMbgroup, tmpMassageMan, tmpMobilePhone, content, 
        userID, userName, outmobilCode, 1);
  }
  
  private void sendMessageByCorpSMS(String phoneList, String content, int priortiy) {
    BbnYxtSMS.sendSMS(phoneList, content);
  }
  
  public void modelSendMsg(String ids, String contents, String mobile, String domainId, String senderId) throws Exception {
    String empId = "0";
    MessageService messageService = new MessageService();
    if (senderId != null && !"".equals(senderId))
      empId = senderId; 
    if (mobile != null && !mobile.equals("")) {
      sendMessageByCorpSMS(ids, contents, 1);
      messageService.addMsHistory(new Long(empId), mobile, contents, "1");
    } 
    begin();
    try {
      if (senderId != null && !"".equals(senderId))
        empId = senderId; 
      String phoneList = "";
      if (ids != null && !ids.equals("")) {
        String[] idsArr = ids.split(",");
        for (int i = 0; i < idsArr.length; i++) {
          List<Object[]> list = this.session.createQuery("select emp.empId, emp.empMobilePhone,emp.empName from com.js.system.vo.usermanager.EmployeeVO emp where  emp.empId=" + 
              idsArr[i] + " and (emp.empMobilePhone is not null and emp.empMobilePhone <> ' ') ").list();
          if (list.size() != 0) {
            Object[] obj = list.get(0);
            phoneList = String.valueOf(phoneList) + obj[1].toString() + ",";
            this.session.flush();
          } 
        } 
        if (phoneList != "") {
          sendMessageByCorpSMS(phoneList, contents, 1);
          messageService.addMsHistory(new Long(empId), phoneList, contents, "1");
        } 
      } 
    } catch (Exception e) {
      System.out.println("MessageEJBBean in modelSendMsg 系统发短信 EJB Exception:" + e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
  }
}
