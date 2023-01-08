package com.js.oa.form.haier;

import com.js.oa.form.Workflow;
import com.js.oa.haier.bean.ERPSyncEJBBean;
import com.js.util.mail.Mail;
import com.js.util.mail.MailConfig;
import com.js.util.mail.MailSender;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ModelAppendDataWorkFlow extends Workflow {
  public static void main(String[] args) {
    ERPSyncEJBBean eRPSyncEJBBean = new ERPSyncEJBBean();
    String sql = "select jst_3013_f3183,jst_3013_f3182,jst_3013_f3268,jst_3013_f3269,jst_3013_f3184,jst_3013_f3238,jst_3013_f3239,jst_3013_f3325,jst_3013_f3201,jst_3013_f3278 from  jst_3013 where jst_3013_foreignkey = 2127160";
    List<String[]> MADList = eRPSyncEJBBean.getNewPrdtMaterial(sql, 10);
    for (int i = 0; i < MADList.size(); i++) {
      String[] data = MADList.get(i);
      String flag = eRPSyncEJBBean.checkPrdtNoExists(data[0], data[8]);
      if ("1".equals(flag)) {
        eRPSyncEJBBean.updateModelAppendDataToErpPrdt(data, "20403");
      } else if ("2".equals(flag)) {
        eRPSyncEJBBean.saveModelAppendDataToErpPrdt(data, "20403", true);
      } else if ("3".equals(flag)) {
        eRPSyncEJBBean.saveModelAppendDataToErpPrdt(data, "20403", false);
      } 
      eRPSyncEJBBean.saveMaterialDataToErpPrdtZ(data[0], data[8]);
    } 
  }
  
  public String complete(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String processeId = request.getParameter("processId");
    String recorded = request.getParameter("recordId");
    String userId = session.getAttribute("userId").toString();
    ERPSyncEJBBean eRPSyncEJBBean = new ERPSyncEJBBean();
    String sql = "select jst_3013_f3183,jst_3013_f3182,jst_3013_f3268,jst_3013_f3269,jst_3013_f3184,jst_3013_f3238,jst_3013_f3239,jst_3013_f3325,jst_3013_f3201,jst_3013_owner,jst_3013_f3278,jst_3013_f3277 from  jst_3013 where jst_3013_foreignkey = " + recorded;
    List<String[]> MADList = eRPSyncEJBBean.getNewPrdtMaterial(sql, 12);
    for (int i = 0; i < MADList.size(); i++) {
      String[] data = MADList.get(i);
      String flag = eRPSyncEJBBean.checkPrdtNoExists(data[0], data[8]);
      if ("1".equals(flag)) {
        System.out.println("新机型更新数据————————！");
        eRPSyncEJBBean.updateModelAppendDataToErpPrdt(data, userId);
        System.out.println("新机型更新数据完成————————！");
      } else if ("2".equals(flag)) {
        System.out.println("新机型插入数据————————！");
        eRPSyncEJBBean.saveModelAppendDataToErpPrdt(data, userId, true);
        System.out.println("新机型插入数据完成————————！");
      } else if ("3".equals(flag)) {
        System.out.println("新机型插入停用数据————————！");
        eRPSyncEJBBean.saveModelAppendDataToErpPrdt(data, userId, false);
        String[] resArr = eRPSyncEJBBean.getUserMailById(data[9]);
        if (resArr != null && !"".equals(resArr[1])) {
          try {
            Mail wm = new Mail();
            wm.setSendTo(resArr[1]);
            wm.setHtml(true);
            wm.setSubjectTitle("新品图号重复提醒");
            wm.setBoby(String.valueOf(resArr[0]) + ":<br  /><p>您好，这封信是由OA系统自动发送的，请勿回复。</p><p>图号" + data[0] + "在ERP内存在重复图号，已被停用，请与ERP录入人员补全资料重启，否则将无法使用!</p>");
            MailSender.send(wm, MailConfig.getEmailSMTP(), MailConfig.getEmailCount(), MailConfig.getEmailPWD(), MailConfig.getEmailPort(), MailConfig.getEncryptionType());
          } catch (Exception e) {
            e.printStackTrace();
          } 
        } else {
          System.out.println("*********未获取到邮件发送人或者邮件发送人邮箱为空");
        } 
      } 
      eRPSyncEJBBean.saveMaterialDataToErpPrdtZForXjx(data[1], data[8], data[11]);
    } 
    return super.complete(request);
  }
}
