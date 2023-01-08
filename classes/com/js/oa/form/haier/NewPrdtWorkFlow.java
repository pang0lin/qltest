package com.js.oa.form.haier;

import com.js.oa.form.Workflow;
import com.js.oa.haier.bean.ERPSyncEJBBean;
import com.js.util.mail.Mail;
import com.js.util.mail.MailConfig;
import com.js.util.mail.MailSender;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class NewPrdtWorkFlow extends Workflow {
  public String complete(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String processeId = request.getParameter("processId");
    String recorded = request.getParameter("recordId");
    String userId = session.getAttribute("userId").toString();
    ERPSyncEJBBean eRPSyncEJBBean = new ERPSyncEJBBean();
    String sql = "select a.jst_3009_thmc,a.jst_3009_f3127,a.jst_3009_f3152,a.jst_3009_f3143,jst_3009_f3131,jst_3009_f3132,jst_3009_f3138,jst_3009_f3139,b.jst_3006_f3083_id,jst_3009_f3148,b.jst_3005_f3207_id,jst_3009_f3145,jst_3009_f3146,jst_3009_f3155,jst_3009_f3130,jst_3009_f3263,a.jst_3009_f3212,b.jst_3005_f3235 from jst_3009 a ,View_jst_3009 b where a.jst_3009_f3123 = b.JST_3006_ID and jst_3009_foreignkey =" + recorded;
    List<String[]> materialList = eRPSyncEJBBean.getNewPrdtMaterial(sql, 18);
    for (int i = 0; i < materialList.size(); i++) {
      String[] data = materialList.get(i);
      String flag = eRPSyncEJBBean.checkPrdtNoExists2(data[0], "OA-" + data[14]);
      if ("1".equals(flag)) {
        System.out.println("原料更新数据————————！");
        eRPSyncEJBBean.updateMaterialDataToErpPrdt(data, userId);
        System.out.println("原料更新数据完成————————！");
      } else if ("2".equals(flag)) {
        System.out.println("原料插入数据————————！");
        eRPSyncEJBBean.saveMaterialDataToErpPrdt(data, userId, true);
        System.out.println("原料插入数据完成————————！");
      } else if ("3".equals(flag)) {
        System.out.println("原料插入停用数据————————！");
        eRPSyncEJBBean.saveMaterialDataToErpPrdt(data, userId, false);
        String[] resArr = eRPSyncEJBBean.getUserMailById(data[15]);
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
      eRPSyncEJBBean.saveMaterialDataToErpPrdtZ(data[0], data[14]);
      eRPSyncEJBBean.saveMaterialDataToErpPrdCusPo(data);
    } 
    return super.complete(request);
  }
}
