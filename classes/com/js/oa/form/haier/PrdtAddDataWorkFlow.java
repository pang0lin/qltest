package com.js.oa.form.haier;

import com.js.oa.form.Workflow;
import com.js.oa.haier.bean.ERPSyncEJBBean;
import com.js.util.mail.Mail;
import com.js.util.mail.MailConfig;
import com.js.util.mail.MailSender;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class PrdtAddDataWorkFlow extends Workflow {
  public String complete(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String processeId = request.getParameter("processId");
    String recorded = request.getParameter("recordId");
    String userId = session.getAttribute("userId").toString();
    ERPSyncEJBBean eRPSyncEJBBean = new ERPSyncEJBBean();
    String sql = "select jst_3011_f3168,jst_3011_f3169,jst_3011_f3265,jst_3011_f3266,jst_3011_f3267,jst_3011_f3221,jst_3011_f3264,jst_3011_f3171,jst_3011_owner from jst_3011 where jst_3011_foreignkey =" + recorded;
    List<String[]> WipList = eRPSyncEJBBean.getNewPrdtMaterial(sql, 9);
    for (int i = 0; i < WipList.size(); i++) {
      String[] data = WipList.get(i);
      String flag = eRPSyncEJBBean.checkPrdtNoExists(data[0], data[7]);
      if ("1".equals(flag)) {
        eRPSyncEJBBean.updatePrdtAddDataToErpPrdt(data, userId);
      } else if ("2".equals(flag)) {
        eRPSyncEJBBean.savePrdtAddDataToErpPrdt(data, userId, true);
      } else if ("3".equals(flag)) {
        eRPSyncEJBBean.savePrdtAddDataToErpPrdt(data, userId, false);
        String[] resArr = eRPSyncEJBBean.getUserMailById(data[8]);
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
      eRPSyncEJBBean.saveMaterialDataToErpPrdtZBcp(data[0], data[7]);
    } 
    return super.complete(request);
  }
}
